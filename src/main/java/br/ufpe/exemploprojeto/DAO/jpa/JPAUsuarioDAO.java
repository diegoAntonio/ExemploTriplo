package br.ufpe.exemploprojeto.DAO.jpa;

import javax.enterprise.context.ApplicationScoped;

import br.ufpe.exemploprojeto.DAO.UsuarioDAO;
import br.ufpe.exemploprojeto.model.Usuario;

@ApplicationScoped
public class JPAUsuarioDAO extends JPAGenericDAO<Long, Usuario> implements UsuarioDAO  {
	private static final long serialVersionUID = -5993488566195264489L;
}
