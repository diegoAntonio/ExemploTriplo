package br.ufpe.exemploprojeto.controlador;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import br.ufpe.exemploprojeto.annotation.Transacao;
import br.ufpe.exemploprojeto.annotation.literal.AnnotationAcaoLiteral;
import br.ufpe.exemploprojeto.controlador.exception.ControladorException;
import br.ufpe.exemploprojeto.model.Usuario;
import br.ufpe.exemploprojeto.model.util.AcaoEntidade;

public class ControladorUsuario extends ControladorGeneric<Long,Usuario> {
	private static final long serialVersionUID = 2274139660861473718L;
	public ControladorUsuario() {
		super(Usuario.class);
	}
	
	@Inject @Any
	private Event<Usuario> usuarioEvento;
	
	@Override
	@Transacao
	public void inserir(Usuario user) throws ControladorException{
		super.inserir(user);
		usuarioEvento.select(AnnotationAcaoLiteral.network(AcaoEntidade.CREATE)).fire(user);
	}
	
	
	@Override
	@Transacao
	public void remover(Usuario user) throws ControladorException{
		super.remover(user);
		usuarioEvento.select(AnnotationAcaoLiteral.network(AcaoEntidade.DELETE)).fire(user);
	}
	
	@Override
	@Transacao
	public Usuario alterar(Usuario user) throws ControladorException{
		Usuario retorno = super.alterar(user);
		usuarioEvento.select(AnnotationAcaoLiteral.network(AcaoEntidade.UPDATE)).fire(user);
		return retorno;
	}

}
