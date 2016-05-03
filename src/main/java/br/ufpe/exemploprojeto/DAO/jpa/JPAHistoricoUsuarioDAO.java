package br.ufpe.exemploprojeto.DAO.jpa;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;

import br.ufpe.exemploprojeto.DAO.HistoricoUsuarioDAO;
import br.ufpe.exemploprojeto.DAO.jpa.util.JPAGenericDAO;
import br.ufpe.exemploprojeto.annotation.DAO;
import br.ufpe.exemploprojeto.model.HistoricoUsuario;

@ApplicationScoped @DAO(HistoricoUsuario.class) @Default
public class JPAHistoricoUsuarioDAO extends JPAGenericDAO<Long, HistoricoUsuario> implements HistoricoUsuarioDAO{
	private static final long serialVersionUID = -7740162063061746339L;
	public JPAHistoricoUsuarioDAO() {
		super(HistoricoUsuario.class);
	}
	
}
