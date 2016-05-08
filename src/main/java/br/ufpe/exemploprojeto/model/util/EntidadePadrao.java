package br.ufpe.exemploprojeto.model.util;

import java.io.Serializable;


/**
 * Interface que reprensenta os prerequesitos de uma Entidade no sistema.
 * A implementacao da interface Serializable e o get e set de uma Chave primaria.
 * @author andre
 *
 * @param <Chave> - Tipo da chave primaria.
 */
public interface EntidadePadrao<Chave> extends Serializable {
	public Chave getId();
	public void setId(Chave id);
}
