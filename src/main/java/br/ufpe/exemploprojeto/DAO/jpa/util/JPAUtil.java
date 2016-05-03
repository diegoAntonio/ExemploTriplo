package br.ufpe.exemploprojeto.DAO.jpa.util;

import java.io.IOException;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * TODO: Retirar {@link SuppressWarnings}
 * Classe auxiliar do JPA para criacao de {@link EntityManager} e {@link EntityManagerFactory}
 * @author andre.alcantara
 */
public class JPAUtil {
	
	private static String PROPERTIES_NAME = "application.properties";
	
	
	/**
	 * Fecha um EntityManger assim que seu escopo acabar
	 * @param EManager - {@link EntityManager}
	 */
	public static void fecharEntityManager(EntityManager EManager){
		if(EManager != null && EManager.isOpen()){
			EManager.close();
		}
	}
	
	/**
	 * Cria um {@link EntityManagerFactory} assim que eh solicitado.
	 * @return {@link EntityManagerFactory} 
	 */
	@Produces @ApplicationScoped
	private EntityManagerFactory createEntityManagerFactory(){
		Properties props = new Properties();
		try {
			props.load(JPAUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_NAME));
			return Persistence.createEntityManagerFactory(props.getProperty("persistenciaJPA"), props);
		}catch (IOException e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Cria um EntityManager assim que eh solicitado.
	 * @param factory - {@link EntityManagerFactory} e uma dependencia que sera injetada.
	 * @return {@link EntityManager} 
	 */
	@Produces @RequestScoped
	private EntityManager createEntityManager(EntityManagerFactory factory){
		return factory.createEntityManager();
	}
	
	/**
	 * Fecha um EntityManger assim que seu escopo acabar
	 * @param EManager - {@link EntityManager}
	 */
	@SuppressWarnings("unused")
	private void destroyerEntityManager(@Disposes EntityManager EManager){
		JPAUtil.fecharEntityManager(EManager);
	}
	
	/**
	 * Fecha a fabrica de EntityManager. Vai ser usado quando fizer Testes unitarios.
	 * @param emfactory - {@link EntityManagerFactory}
	 */
	@SuppressWarnings("unused")
	private void destroyerEntityManagerFactory(@Disposes EntityManagerFactory emfactory){
		if(emfactory != null && emfactory.isOpen()) {
			emfactory.close();
		}
	}
}
