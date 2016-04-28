package util;
import java.lang.reflect.Field;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.AdditionalPackages;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import br.ufpe.exemploprojeto.DAO.jpa.JPAUtil;
import br.ufpe.exemploprojeto.annotation.interceptador.TransacaoInterceptor;



@RunWith(CdiRunner.class)
@AdditionalClasses(value = { TransacaoInterceptor.class})
@AdditionalPackages(value = { JPAUtil.class })
@InRequestScope
public abstract class ExemploTestEnv {
	
	@BeforeClass
	public static void setUpClass(){
		configureEntityManager();
	}
	
	@AfterClass
	public static void tearDownClass(){
		
	}
	
	private static void configureEntityManager() {
		try {
			String banco = "application-unitTest.properties";
			Field field = JPAUtil.class.getDeclaredField("PROPERTIES_NAME");
			field.setAccessible(true);
			field.set("null", banco);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | 
				IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	
}
