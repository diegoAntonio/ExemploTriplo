package br.ufpe.exemploprojeto.controlador;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import br.ufpe.exemploprojeto.DAO.GenericDAO;
import br.ufpe.exemploprojeto.annotation.literal.AnnotationDAOLiteral;

@ApplicationScoped
@SuppressWarnings("rawtypes")
public class Factory {

	@Inject @Any
	private Instance<GenericDAO> daoFactory;
	
	public GenericDAO factoryDao(Class<?> clazz){
		return daoFactory.select(AnnotationDAOLiteral.network(clazz)).get();
	}	
}
