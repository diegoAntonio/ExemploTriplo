package br.ufpe.exemploprojeto.DAO.jpa;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;

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
}
