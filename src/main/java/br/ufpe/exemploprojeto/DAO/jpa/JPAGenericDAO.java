package br.ufpe.exemploprojeto.DAO.jpa;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import br.ufpe.exemploprojeto.DAO.GenericDAO;
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
	
	/**
	 * Inicia atributos na classe.
	 */
	@PostConstruct
	public void init() {
		Type genericSuperClass = getClass().getGenericSuperclass();
		ParameterizedType parametrizedType;
		if (genericSuperClass instanceof ParameterizedType) { // Classe normal.
		    parametrizedType = (ParameterizedType) genericSuperClass;
		} else if (genericSuperClass instanceof Class) { // Em caso de uso de Proxy.
		    parametrizedType = (ParameterizedType) ((Class<Entidade>) genericSuperClass).getGenericSuperclass();
		} else {
		    throw new IllegalStateException("class " + getClass() + " is not subtype of ParametrizedType.");
		}
		this.entidadeClass = (Class<Entidade>) parametrizedType.getActualTypeArguments()[1];
	}
}