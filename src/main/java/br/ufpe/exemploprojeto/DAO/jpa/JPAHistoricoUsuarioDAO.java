package br.ufpe.exemploprojeto.DAO.jpa;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import br.ufpe.exemploprojeto.DAO.HistoricoUsuarioDAO;
import br.ufpe.exemploprojeto.DAO.PessoaDAO;
import br.ufpe.exemploprojeto.DAO.UsuarioDAO;
import br.ufpe.exemploprojeto.DAO.jpa.util.JPAGenericDAO;
import br.ufpe.exemploprojeto.annotation.DAO;
import br.ufpe.exemploprojeto.model.HistoricoUsuario;
import br.ufpe.exemploprojeto.model.Pessoa;
import br.ufpe.exemploprojeto.model.Usuario;

@ApplicationScoped @DAO(HistoricoUsuario.class) @Default
public class JPAHistoricoUsuarioDAO extends JPAGenericDAO<Long, HistoricoUsuario> implements HistoricoUsuarioDAO{
	private static final long serialVersionUID = -7740162063061746339L;
	public JPAHistoricoUsuarioDAO() {
		super(HistoricoUsuario.class);
	}
	
	
	@Inject
	private UsuarioDAO usuarioDAO;
	
	@Inject
	private PessoaDAO pessoaDAO;
	
	@Override
	public void refresh(HistoricoUsuario entidade) {
		super.refresh(entidade);
		this.ajusteHistoricoUsuario(entidade);
	}
	
	@Override
	public HistoricoUsuario findById(Long id) {
		HistoricoUsuario entidade = super.findById(id);
		this.ajusteHistoricoUsuario(entidade);
		return entidade;
	}
	
	@Override
	public List<HistoricoUsuario> findAll() {
		List<HistoricoUsuario> retorno = super.findAll();
		retorno.forEach(this::ajusteHistoricoUsuario);
		return retorno;
	}
	
	private void ajusteHistoricoUsuario(HistoricoUsuario entidade){
		Usuario user = null;
		Pessoa pess = null;
		try{
			pess = pessoaDAO.findById(entidade.getIdPessoa());
		}catch(IllegalArgumentException e){}

		try{
			user = usuarioDAO.findById(entidade.getIdUsuario());
		}catch(IllegalArgumentException e){
			user = Usuario.of(entidade.getLogin(), entidade.getPass(), pess, entidade.getPermissoes());
		}

		entidade.setUsuario(user);
		entidade.setPessoa(pess);
	}
}
