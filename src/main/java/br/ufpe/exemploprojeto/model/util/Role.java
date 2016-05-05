package br.ufpe.exemploprojeto.model.util;

import java.util.Arrays;
import java.util.function.Predicate;

import br.ufpe.exemploprojeto.util.Mensagem;
import br.ufpe.exemploprojeto.util.Mensagem.MensagemEnum;

public enum Role {
	ADMIN(1),
	COLLABORATOR(2),
	MODERATE(3),
	USER(1000);
	
	private int nivel;
	
	private Role(int nivel){
		this.nivel = nivel;
	}
	
	public int getNivel() {
		return nivel;
	}
	
	public static Role valueOf(int nivel) {
		String mensagemError = 
				Mensagem.get(MensagemEnum.Mensagem_Error_Enum_Param, 
							 Role.class.getName(),String.valueOf(nivel));

		Predicate<Role> filtro = f-> f.getNivel() == nivel;
	
		Role r = Arrays.stream(Role.values())
				.filter(filtro)
				.findFirst()
				.orElseThrow(()-> new IllegalArgumentException(mensagemError));
		return r;
	}
	
	
}
