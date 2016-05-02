package br.ufpe.exemploprojeto.controlador;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;

import br.ufpe.exemploprojeto.DAO.GenericDAO;
import br.ufpe.exemploprojeto.DAO.exception.BadRequestDaoException;

@ApplicationScoped
@SuppressWarnings("unchecked")
public abstract class Controlador<Chave, Entidade> implements Serializable {
	private static final long serialVersionUID = 2487134818109452029L;

	private GenericDAO<Chave, Entidade> genericDAO;
	
	@Inject
	private Factory fabrica;
	
	private Class<? extends Entidade> entidadeClasse;
	
	public Controlador(Class<? extends Entidade> entidadeClasse) {
		this.entidadeClasse = entidadeClasse;
	}
	
	@PostConstruct
	public void init(){
		genericDAO = fabrica.factoryDao(entidadeClasse);
	}

	public void inserir(Entidade user) throws BadRequestDaoException {
		try {
			genericDAO.save(user);
		} catch (RollbackException e) {
			throw new BadRequestDaoException();
		}
	}

	public Entidade alterar(Entidade user) throws BadRequestDaoException {
		Entidade retorno = null;
		try {
			retorno = genericDAO.update(user);
		} catch (RollbackException e) {
			throw new BadRequestDaoException();
		}
		return retorno;
	}

	public Entidade buscarPorId(Chave id) {
		Entidade retorno = null;
		try {
			retorno = genericDAO.findById(id);
		} catch (NoResultException e) {
		}
		return retorno;
	}

	public void remover(Entidade user) throws BadRequestDaoException {
		try {
			genericDAO.remove(user);
		} catch (RollbackException e) {
			throw new BadRequestDaoException();
		}
	}
}
