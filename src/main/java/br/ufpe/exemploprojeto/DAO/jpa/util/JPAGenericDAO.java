package br.ufpe.exemploprojeto.DAO.jpa.util;

import java.util.List;
import java.util.function.Function;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import br.ufpe.exemploprojeto.annotation.Transacao;

/**
 * Classe de repositorio com implementacao em JPA 
 * basica para as entidades do sistema.
 * 
 * @author andre.alcantara
 *
 * @param <Chave> - Chave da Entidade
 * @param <Entidade> - Entidade que sera persistida.
 */
public abstract class JPAGenericDAO<Chave, Entidade> implements GenericDAO<Chave, Entidade> {

	private static final long serialVersionUID = -5270442135567484490L;

	@Inject
	protected EntityManager em;
	

	private Class<Entidade> entidadeClass;

	public JPAGenericDAO(Class<Entidade> e) {
		this.entidadeClass = e;
	}
	
	@Override
	@Transacao
	public Entidade save(final Entidade e) {
		em.persist(e);
		return e;
	}

	@Override
	@Transacao
	public Entidade update(Entidade e) {
		em.merge(e);
		return e;
	}

	@Override
	public void refresh(Entidade e) {
		em.refresh(e);
	}

	@Override
	@Transacao
	public void remove(Entidade e) {
		Entidade e1 = em.merge(e);
		em.remove(e1);
	}

	@Override
	public Entidade findById(final Chave id) {
		Entidade e = em.find(this.entidadeClass, id);
		return e;
	}

	@Override
	public List<Entidade> findAll() {
		CriteriaQuery<Entidade> query = em.getCriteriaBuilder().createQuery(entidadeClass);
		query.from(entidadeClass);
		List<Entidade> list = em.createQuery(query).getResultList();
		return list;
	}
	
	public Class<Entidade> getEntidadeClass(){
		return this.entidadeClass;
	}
	
	protected Entidade usingEntityManger(Function<EntityManager, Entidade> funcao){
		Entidade entidade = null;
		try{
			entidade = funcao.apply(em);
		} catch(Throwable e) {
			throw new RuntimeException(e);
		}
		return entidade;
	}
}