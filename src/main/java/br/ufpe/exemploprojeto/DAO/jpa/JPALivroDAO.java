package br.ufpe.exemploprojeto.DAO.jpa;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;

import br.ufpe.exemploprojeto.DAO.LivroDAO;
import br.ufpe.exemploprojeto.DAO.jpa.util.JPAGenericDAO;
import br.ufpe.exemploprojeto.annotation.DAO;
import br.ufpe.exemploprojeto.model.Livro;

@ApplicationScoped @DAO(Livro.class) @Default
public class JPALivroDAO extends JPAGenericDAO<Long, Livro> implements LivroDAO {
	private static final long serialVersionUID = 5221877018677388943L;
	public JPALivroDAO() {
		super(Livro.class);
	}
}
