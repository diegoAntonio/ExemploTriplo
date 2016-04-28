package util;
import java.lang.reflect.Field;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.AdditionalPackages;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import br.ufpe.exemploprojeto.DAO.jpa.JPAUtil;
import br.ufpe.exemploprojeto.annotation.interceptador.TransacaoInterceptor;



/**
 * Classe inicial de exemplo de como devemos configurar os Testes para ficarem independente.
 * @author andre.alcantara
 *
 */
@RunWith(CdiRunner.class)
@AdditionalClasses(value = { TransacaoInterceptor.class})
@AdditionalPackages(value = { JPAUtil.class })
@InRequestScope
public abstract class ExemploTestEnv {
		
	@Inject
	protected EntityManagerFactory entityManagerFactory;
	
	@BeforeClass
	public static void aoCarregarAClasse(){
		configureEntityManager();
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
		EntityManager EManager = entityManagerFactory.createEntityManager();
		EManager.getTransaction().begin();
		EManager.createNativeQuery("delete from role_usuario;").executeUpdate(); 
		EManager.createNativeQuery("delete from usuario;").executeUpdate(); 
		EManager.createNativeQuery("ALTER SEQUENCE usuario_id_seq RESTART WITH 1;").executeUpdate();
		EManager.getTransaction().commit();
		JPAUtil.fecharEntityManager(EManager);
	}
	
	private static void configureEntityManager() {
		try {
			String banco = "application-unitTest.properties";
			Field field = JPAUtil.class.getDeclaredField("PROPERTIES_NAME");
			field.setAccessible(true);
			field.set("null", banco);
		} catch (NoSuchFieldException |
				 SecurityException |
				 IllegalArgumentException |
				 IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
