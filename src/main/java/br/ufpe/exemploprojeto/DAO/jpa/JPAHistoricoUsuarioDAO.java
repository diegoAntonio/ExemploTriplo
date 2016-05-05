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
		entidade.setUsuario(usuarioDAO.findById(entidade.getIdUsuario()));
		entidade.setPessoa(pessoaDAO.findById(entidade.getIdPessoa()));
	}
	
	@Override
	public HistoricoUsuario findById(Long id) {
		HistoricoUsuario historicoUsuario = super.findById(id);
		historicoUsuario.setUsuario(usuarioDAO.findById(historicoUsuario.getIdUsuario()));
		historicoUsuario.setPessoa(pessoaDAO.findById(historicoUsuario.getIdPessoa()));
		return historicoUsuario;
	}
	
	@Override
	public List<HistoricoUsuario> findAll() {
		List<HistoricoUsuario> retorno = super.findAll();
		retorno.forEach(histUser -> {
			histUser.setUsuario(usuarioDAO.findById(histUser.getIdUsuario()));
			histUser.setPessoa(pessoaDAO.findById(histUser.getIdPessoa()));
		});
		return retorno;
	}
}
