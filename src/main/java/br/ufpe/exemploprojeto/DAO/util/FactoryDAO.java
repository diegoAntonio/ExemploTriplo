package br.ufpe.exemploprojeto.DAO.util;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import br.ufpe.exemploprojeto.DAO.jpa.util.JPAGenericDAO;
import br.ufpe.exemploprojeto.annotation.literal.AnnotationDAOLiteral;

@ApplicationScoped
@SuppressWarnings("rawtypes")
public class FactoryDAO {

	@Inject @Any
	private Instance<JPAGenericDAO<?, ?>> daoFactory;
	
	public GenericDAO factoryDao(Class<?> clazz){
		return daoFactory.select(AnnotationDAOLiteral.network(clazz)).get();
	}	
}
