package br.ufpe.exemploprojeto.controlador;

import javax.enterprise.event.Observes;

import br.ufpe.exemploprojeto.annotation.Acao;
import br.ufpe.exemploprojeto.controlador.exception.ControladorException;
import br.ufpe.exemploprojeto.model.Pessoa;
import br.ufpe.exemploprojeto.model.util.AcaoEntidade;

public class ControladorPessoa extends ControladorGeneric<Long, Pessoa> {
	private static final long serialVersionUID = 9194116625953010182L;
	public ControladorPessoa() {
		super(Pessoa.class);
	}

	@Override
	public void inserir(@Observes @Acao(AcaoEntidade.CREATE)Pessoa pessoa) 
			throws ControladorException {
		super.inserir(pessoa);
	}
	

}
