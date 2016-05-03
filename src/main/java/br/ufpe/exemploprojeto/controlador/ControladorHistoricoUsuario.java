package br.ufpe.exemploprojeto.controlador;

import javax.enterprise.event.Observes;

import br.ufpe.exemploprojeto.DAO.exception.BadRequestDaoException;
import br.ufpe.exemploprojeto.annotation.Transacao;
import br.ufpe.exemploprojeto.model.HistoricoUsuario;
import br.ufpe.exemploprojeto.model.Usuario;


public class ControladorHistoricoUsuario extends Controlador<Long, HistoricoUsuario>{
	private static final long serialVersionUID = -6362516845827914331L;
	public ControladorHistoricoUsuario() {
		super(HistoricoUsuario.class);
	}
	
	@Transacao
	public void inserirHistorico(@Observes Usuario user) throws BadRequestDaoException{
		HistoricoUsuario historico = HistoricoUsuario.of(user);
		super.inserir(historico);
	}
	

}
