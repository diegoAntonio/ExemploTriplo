package br.ufpe.exemploprojeto.controlador;

import javax.enterprise.event.Observes;

import br.ufpe.exemploprojeto.DAO.exception.BadRequestDaoException;
import br.ufpe.exemploprojeto.annotation.Acao;
import br.ufpe.exemploprojeto.annotation.Transacao;
import br.ufpe.exemploprojeto.model.HistoricoUsuario;
import br.ufpe.exemploprojeto.model.Usuario;
import br.ufpe.exemploprojeto.model.util.AcaoEntidade;


public class ControladorHistoricoUsuario extends Controlador<Long, HistoricoUsuario>{
	private static final long serialVersionUID = -6362516845827914331L;
	public ControladorHistoricoUsuario() {
		super(HistoricoUsuario.class);
	}

	@Transacao
	public void inserirHistoricoUsuarioInserirUsuario(@Observes @Acao(AcaoEntidade.CREATE) Usuario user)
			throws BadRequestDaoException{
		HistoricoUsuario historico = HistoricoUsuario.of(user, AcaoEntidade.CREATE);
		super.inserir(historico);
	}
	
	@Transacao
	public void inserirHistoricoUsuarioAlterarUsuario(@Observes @Acao(AcaoEntidade.UPDATE) Usuario user)
			throws BadRequestDaoException{
		HistoricoUsuario historico = HistoricoUsuario.of(user, AcaoEntidade.UPDATE);
		super.inserir(historico);
	}
	
	@Transacao
	public void inserirHistoricoUsuarioRemoverUsuario(@Observes @Acao(AcaoEntidade.DELETE) Usuario user)
			throws BadRequestDaoException{
		HistoricoUsuario historico = HistoricoUsuario.of(user, AcaoEntidade.DELETE);
		historico.setUsuario(null);
		super.inserir(historico);
	}
	

}
