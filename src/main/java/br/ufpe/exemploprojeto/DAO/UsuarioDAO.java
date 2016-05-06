package br.ufpe.exemploprojeto.DAO;

import br.ufpe.exemploprojeto.DAO.util.GenericDAO;
import br.ufpe.exemploprojeto.model.Usuario;

public interface UsuarioDAO extends GenericDAO<Long, Usuario> {
	
	
	public Usuario consultarPorLogin(String login);

}
