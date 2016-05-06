package br.ufpe.exemploprojeto.model.util;

import java.io.Serializable;

public interface EntidadePadrao<Chave> extends Serializable {
	public Chave getId();
	public void setId(Chave id);
}
