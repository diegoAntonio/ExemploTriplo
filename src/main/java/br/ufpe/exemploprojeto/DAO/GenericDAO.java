package br.ufpe.exemploprojeto.DAO;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * Classe de repositorio com implementacao em JPA 
 * basica para as entidades do sistema.
 * 
 * @author andre.alcantara
 *
 * @param <Chave> - Chave da Entidade
 * @param <Entidade> - Entidade que sera persistida.
 */
public interface GenericDAO<Chave, Entidade>  extends Serializable {

	/**
	 * Persiste uma entidade.
	 * @param e - entidade
	 * @return Entidade persistida com id atualizado.
	 */
	public Entidade save(Entidade e);

	/**
	 * Atualiza entidade
	 * @param e - entidade atualizada.
	 * @return Entidade atualizada. 
	 */
	public Entidade update(Entidade e);

	/**
	 * Remove uma entidade.
	 * @param e - entidade que será removida.
	 */
	public void remove(Entidade e);
	
	/**
	 * Atualiza a entidade com as informacoes do banco.
	 * @param e - entidade que será atualizada com as informacoes do banco.
	 */
	public void refresh(Entidade e);

	/**
	 * Busca uma entidade por uma chave.
	 * @param id - chave da entidade
	 * @return entidade encontrada no banco.
	 */
	public Entidade findById(Chave id);
	
	/**
	 * Retorna uma {@link List} com todas as entidades do banco.
	 * @return {@link List} de entidades.
	 */
	public List<Entidade> findAll();
}