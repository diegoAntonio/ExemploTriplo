package br.ufpe.exemploprojeto.DAO.jpa;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.ufpe.exemploprojeto.DAO.UsuarioDAO;
import br.ufpe.exemploprojeto.DAO.jpa.util.JPAGenericDAO;
import br.ufpe.exemploprojeto.annotation.DAO;
import br.ufpe.exemploprojeto.model.Usuario;

@ApplicationScoped @DAO(Usuario.class) @Default
public class JPAUsuarioDAO extends JPAGenericDAO<Long, Usuario> implements UsuarioDAO  {
	private static final long serialVersionUID = -5993488566195264489L;
	public JPAUsuarioDAO() {
		super(Usuario.class);
	}
	@Override
	public Usuario consultarPorLogin(String login) {
		Usuario retorno = this.usingEntityManger(em -> {
			Usuario usuario = null;
			TypedQuery<Usuario> typedQuery = em.createQuery("select t from Usuario t where t.login=:login", Usuario.class);
			typedQuery.setParameter("login", login);
			
			try {
				usuario = typedQuery.getSingleResult();
			} catch (NoResultException e) {
				log.warn("Not found, login[{}]", login);
				throw e;
			} catch (Exception e) {
				log.error("Postgres connection error");
			}
			return usuario;
		});
		return retorno;
	}
}
