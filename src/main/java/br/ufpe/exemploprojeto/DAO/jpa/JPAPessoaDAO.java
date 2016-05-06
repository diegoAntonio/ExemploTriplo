package br.ufpe.exemploprojeto.DAO.jpa;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;

import br.ufpe.exemploprojeto.DAO.PessoaDAO;
import br.ufpe.exemploprojeto.DAO.jpa.util.JPAGenericDAO;
import br.ufpe.exemploprojeto.annotation.DAO;
import br.ufpe.exemploprojeto.model.Pessoa;

@ApplicationScoped @DAO(Pessoa.class) @Default
public class JPAPessoaDAO extends JPAGenericDAO<Long, Pessoa> implements PessoaDAO {
	private static final long serialVersionUID = 3703034992600102129L;
	public JPAPessoaDAO() {
		super(Pessoa.class);
	}
}