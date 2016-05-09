package util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.AdditionalPackages;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.ContextController;
import org.jglue.cdiunit.InRequestScope;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import alternatives.AlternativeProduces;
import br.ufpe.exemploprojeto.DAO.jpa.JPAUsuarioDAO;
import br.ufpe.exemploprojeto.DAO.jpa.util.JPAUtil;
import br.ufpe.exemploprojeto.annotation.interceptador.TransacaoInterceptor;
import br.ufpe.exemploprojeto.controlador.ControladorGeneric;
import br.ufpe.exemploprojeto.util.Produtor;



/**
 * Classe inicial de exemplo de como devemos configurar os Testes para ficarem independente.
 * @author andre.alcantara
 *
 */
@RunWith(CdiRunner.class)
@AdditionalClasses(value = { TransacaoInterceptor.class, Produtor.class, AlternativeProduces.class})
@AdditionalPackages(value = { JPAUtil.class, JPAUsuarioDAO.class, ControladorGeneric.class })
@ActivatedAlternatives(value = { AlternativeProduces.class})
@InRequestScope
public abstract class ExemploTestEnv implements Serializable{
	private static final long serialVersionUID = -6211366961758099674L;

	@Inject
	protected EntityManager EManager;
	
	@Inject
	protected Logger logger;

	@Inject
	private ContextController contextControle;
	
	@BeforeClass
	public static void setUp(){
		//No inicio do teste da classe.
//		configureEntityManager();
	}
	
	@AfterClass
	public static void tearDown(){
		
	}
	
	@Before
	public void antesDeCadaTeste(){
		if(precisaResetarBanco()){
			resetBanco();
		}
	}
	
	public boolean precisaResetarBanco(){
		return true;
	}
	
	private void resetBanco() {
		EManager.getTransaction().begin();

		((Session)EManager.unwrap(Session.class)).doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				ScriptRunner sr = new ScriptRunner(connection);
				PrintWriter pw = new PrintWriter(new ByteArrayOutputStream());
				sr.setLogWriter(null);
				StringBuilder sb = new StringBuilder();
				new BufferedReader(new InputStreamReader(ExemploTestEnv.class.getResourceAsStream("/configs/configs.sql"), Charset.forName("ISO-8859-1"))).lines().forEach(sb::append);
				Arrays.stream(sb.toString().split(";")).forEach( l -> {
					sr.runScript(new StringReader(l+";"));
				});				
			}
		});
		
		EManager.getTransaction().commit();
		
		this.novaRequest();
	}
	
	public void novaRequest(){
		try{
			contextControle.closeRequest();
			contextControle.openRequest();
		}catch(RuntimeException e){
			logger.error(e.getMessage());
		}
	}
}
