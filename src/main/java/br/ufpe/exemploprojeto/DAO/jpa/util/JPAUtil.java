package br.ufpe.exemploprojeto.DAO.jpa.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.CDI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Classe auxiliar do JPA para criacao de {@link EntityManager} e {@link EntityManagerFactory}
 * @author andre.alcantara
 */
public class JPAUtil implements Serializable{
	private static final long serialVersionUID = -1544019806786181998L;
	
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
	 * Busca um {@link EntityManager} dentro do {@link CDI}
	 * <pre>Em @RequestScoped</pre>
	 * @return - {@link EntityManager}
	 */
	public static EntityManager staticEntityManage(){
		 return JPAUtil.staticClassCDI(EntityManager.class);
	}
	
	/**
	 * Busca um {@link EntityManagerFactory} dentro do {@link CDI}
	 * <pre>Em @ApplicationScoped</pre>
	 * @return - {@link EntityManagerFactory}
	 */
	public static EntityManagerFactory staticEntityManageFactory(){
		 return JPAUtil.staticClassCDI(EntityManagerFactory.class);
	}
	
	/**
	 * Cria um {@link Properties} com o properties de persistencia.
	 * 
	 * @return {@link Properties}
	 * @throws IOException - Erro ao ler o arquivo.
	 */
	@Produces
	private Properties createProperties(){
		Properties props = new Properties();
		try {
			props.load(JPAUtil.class.getClassLoader().getResourceAsStream("application.properties"));
			return props;
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	/**
	 * Cria um {@link EntityManagerFactory} assim que eh solicitado.
	 * @return {@link EntityManagerFactory} 
	 */
	@Produces @ApplicationScoped
	private EntityManagerFactory createEntityManagerFactory(Properties props){
		return Persistence.createEntityManagerFactory(props.getProperty("persistenciaJPA"), props);
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
	private void destroyerEntityManager(@Disposes EntityManager EManager){
		JPAUtil.fecharEntityManager(EManager);
	}
	
	/**
	 * Fecha a fabrica de EntityManager. Vai ser usado quando fizer Testes unitarios.
	 * @param emfactory - {@link EntityManagerFactory}
	 */
	private void destroyerEntityManagerFactory(@Disposes EntityManagerFactory emfactory){
		if(emfactory != null && emfactory.isOpen()) {
			emfactory.close();
		}
	}
	
	private static <T> T staticClassCDI(Class<T> clazz){
		 Instance<T> instance = CDI.current().select(clazz);
		    return instance.get();
	}
}
