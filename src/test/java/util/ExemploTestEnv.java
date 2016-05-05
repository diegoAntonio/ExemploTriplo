package util;
import java.io.Serializable;

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
		EManager.createNativeQuery("delete from role_usuario;").executeUpdate(); 
		EManager.createNativeQuery("delete from role_historico;").executeUpdate(); 
		
		EManager.createNativeQuery("delete from historico_usuario;").executeUpdate();
		EManager.createNativeQuery("delete from usuario;").executeUpdate();
		EManager.createNativeQuery("delete from livro;").executeUpdate();
		
		EManager.createNativeQuery("ALTER SEQUENCE livro_id_seq RESTART WITH 1;").executeUpdate();
		EManager.createNativeQuery("ALTER SEQUENCE usuario_id_seq RESTART WITH 1;").executeUpdate();
		EManager.createNativeQuery("ALTER SEQUENCE historico_usuario_id_seq RESTART WITH 1;").executeUpdate();
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
