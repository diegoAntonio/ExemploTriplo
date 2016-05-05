package br.ufpe.exemploprojeto.controlador;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;

import org.slf4j.Logger;

import br.ufpe.exemploprojeto.DAO.util.FactoryDAO;
import br.ufpe.exemploprojeto.DAO.util.GenericDAO;
import br.ufpe.exemploprojeto.annotation.Transacao;
import br.ufpe.exemploprojeto.controlador.exception.ControladorException;

@ApplicationScoped
public abstract class ControladorGeneric<Chave, Entidade> implements Serializable {
	private static final long serialVersionUID = 2487134818109452029L;

	@Inject
	protected Logger log;
	
	private GenericDAO<Chave, Entidade> genericDAO;
	
	@Inject
	private FactoryDAO fabrica;
	
	private Class<? extends Entidade> entidadeClasse;
	
	public ControladorGeneric(Class<? extends Entidade> entidadeClasse) {
		this.entidadeClasse = entidadeClasse;
	}
	
	@PostConstruct
	public void init(){
		genericDAO = fabrica.factoryDao(entidadeClasse);
	}
	
	@Transacao
	public void inserir(Entidade user) throws ControladorException {
		try {
			genericDAO.save(user);
		} catch (RollbackException e) {
			log.error(e.getMessage());
			throw new ControladorException(e);
		}
	}

	@Transacao
	public Entidade alterar(Entidade user) throws ControladorException {
		Entidade retorno = null;
		try {
			retorno = genericDAO.update(user);
		} catch (RollbackException e) {
			log.error(e.getMessage());
			throw new ControladorException(e);
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

	@Transacao
	public void remover(Entidade user) throws ControladorException {
		try {
			genericDAO.remove(user);
		} catch (RollbackException e) {
			log.error(e.getMessage());
			throw new ControladorException(e);
		}
	}
}
