package br.ufpe.exemploprojeto.DAO.jpa;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;

import br.ufpe.exemploprojeto.DAO.HistoricoUsuarioDAO;
import br.ufpe.exemploprojeto.DAO.jpa.util.JPAGenericDAO;
import br.ufpe.exemploprojeto.annotation.DAO;
import br.ufpe.exemploprojeto.model.HistoricoUsuario;
import br.ufpe.exemploprojeto.model.Usuario;

@ApplicationScoped @DAO(HistoricoUsuario.class) @Default
public class JPAHistoricoUsuarioDAO extends JPAGenericDAO<Long, HistoricoUsuario> implements HistoricoUsuarioDAO{
	private static final long serialVersionUID = -7740162063061746339L;
	public JPAHistoricoUsuarioDAO() {
		super(HistoricoUsuario.class);
	}
	
	@Override
	public void refresh(HistoricoUsuario e) {
		super.refresh(e);
		e.setUsuario(getUsuario(e));
	}
	
	@Override
	public HistoricoUsuario findById(Long id) {
		HistoricoUsuario historicUser = super.findById(id);
		historicUser.setUsuario(getUsuario(historicUser));
		return historicUser;
	}
	
	@Override
	public List<HistoricoUsuario> findAll() {
		List<HistoricoUsuario> retorno = super.findAll();
		retorno.forEach(h -> h.setUsuario(getUsuario(h)));
		return retorno;
	}
	
	
	public Usuario getUsuario(HistoricoUsuario user){
		return super.usingEntityManger(em -> em.find(Usuario.class, user.getIdUsuario()));
	}
	
}
