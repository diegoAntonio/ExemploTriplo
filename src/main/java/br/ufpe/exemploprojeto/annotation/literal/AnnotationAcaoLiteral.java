package br.ufpe.exemploprojeto.annotation.literal;

import javax.enterprise.util.AnnotationLiteral;

import br.ufpe.exemploprojeto.annotation.Acao;
import br.ufpe.exemploprojeto.model.util.AcaoEntidade;

public class AnnotationAcaoLiteral extends AnnotationLiteral<Acao> implements Acao {
	private static final long serialVersionUID = 8598722629113151469L;

	private AcaoEntidade value;

	public AnnotationAcaoLiteral(AcaoEntidade acao) {
		this.value = acao;
	}
	
	public static AnnotationAcaoLiteral network(AcaoEntidade acao){
		return new AnnotationAcaoLiteral(acao);
	}
	
	@Override
	public AcaoEntidade value() {
		return value;
	}

}
