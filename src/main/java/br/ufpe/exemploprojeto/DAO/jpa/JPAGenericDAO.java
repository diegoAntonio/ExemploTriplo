package br.ufpe.exemploprojeto.DAO.jpa;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import br.ufpe.exemploprojeto.DAO.GenericDAO;

/**
 * Classe de repositorio com implementacao em JPA 
 * basica para as entidades do sistema.
 * 
 * @author andre.alcantara
 *
 * @param <Chave> - Chave da Entidade
 * @param <Entidade> - Entidade que sera persistida.
 */
public class JPAGenericDAO<Chave, Entidade> implements GenericDAO<Chave, Entidade> {

	private static final long serialVersionUID = -5270442135567484490L;

	@Inject
	protected EntityManager em;

	private Class<Entidade> entidadeClass;

	/**
	 * Inicia atributos na classe.
	 */
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entidadeClass = (Class<Entidade>) genericSuperclass.getActualTypeArguments()[1];
	}

	@Override
	public Entidade save(final Entidade e) {
		em.persist(e);
		return e;
	}

	@Override
	public Entidade update(Entidade e) {
			em.merge(e);

		return e;
	}

	@Override
	public void refresh(Entidade e) {
		em.refresh(e);
	}

	@Override
	public void remove(final Entidade e) {
		em.remove(em.merge(e));
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
}