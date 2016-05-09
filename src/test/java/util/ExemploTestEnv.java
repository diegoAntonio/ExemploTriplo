package util;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Arrays;

import javax.inject.Inject;
import javax.persistence.EntityManager;

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
		StringBuilder sb = new StringBuilder();
		new BufferedReader(new InputStreamReader(ExemploTestEnv.class.getResourceAsStream("/configs/configs.sql"), Charset.forName("ISO-8859-1"))).lines().forEach(sb::append);
		Arrays.stream(sb.toString().split(";")).forEach( l -> {
			String line = l.replace("\"", "\\\"");
			line = "{call BEGIN " + line.trim() + " END}";
			System.out.println(line);
			EManager.createNativeQuery(line);
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
