package br.ufpe.exemploprojeto.DAO;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<Entidade> extends Serializable {

	Entidade inserir(Entidade entity);

	Entidade alterar(Entidade entity);

	void remover(Entidade entity);

	Entidade buscaPorId(Long id);

	List<Entidade> listaTodos();

}