package br.ufpe.exemploprojeto.controlador;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;

import org.slf4j.Logger;

import br.ufpe.exemploprojeto.DAO.exception.BadRequestDaoException;
import br.ufpe.exemploprojeto.DAO.jpa.util.GenericDAO;

@ApplicationScoped
@SuppressWarnings("unchecked")
public abstract class Controlador<Chave, Entidade> implements Serializable {
	private static final long serialVersionUID = 2487134818109452029L;

	@Inject
	protected Logger log;
	
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
			log.error(e.getMessage());
			throw new BadRequestDaoException(e);
		}
	}

	public Entidade alterar(Entidade user) throws BadRequestDaoException {
		Entidade retorno = null;
		try {
			retorno = genericDAO.update(user);
		} catch (RollbackException e) {
			log.error(e.getMessage());
			throw new BadRequestDaoException(e);
		}
		return retorno;
	}

	public Entidade buscarPorId(Chave id) {
		Entidade retorno = null;
		try {
			retorno = genericDAO.findById(id);
		} catch (NoResultException e) {
			log.error(e.getMessage());
		}
		return retorno;
	}

	public void remover(Entidade user) throws BadRequestDaoException {
		try {
			genericDAO.remove(user);
		} catch (RollbackException e) {
			log.error(e.getMessage());
			throw new BadRequestDaoException(e);
		}
	}
}
