package util;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import br.ufpe.exemploprojeto.DAO.HistoricoUsuarioDAO;
import br.ufpe.exemploprojeto.DAO.LivroDAO;
import br.ufpe.exemploprojeto.DAO.UsuarioDAO;
import br.ufpe.exemploprojeto.DAO.exception.BadRequestDaoException;
import br.ufpe.exemploprojeto.DAO.jpa.util.GenericDAO;
import br.ufpe.exemploprojeto.annotation.DAO;
import br.ufpe.exemploprojeto.controlador.ControladorUsuario;
import br.ufpe.exemploprojeto.model.HistoricoUsuario;
import br.ufpe.exemploprojeto.model.Livro;
import br.ufpe.exemploprojeto.model.Role;
import br.ufpe.exemploprojeto.model.Usuario;
import br.ufpe.exemploprojeto.model.util.AcaoEntidade;

public class PrimeirosTestes extends ExemploTestEnv {
	private static final long serialVersionUID = 4734711613523663825L;

	@Inject
	private UsuarioDAO usuarioDAO;

	@Inject
	private LivroDAO livroDAO;

	@Inject
	private ControladorUsuario controladorUsuario;

	@Inject
	private HistoricoUsuarioDAO historicoUsuarioDAO;
	
	@Inject @DAO(Usuario.class)
	private GenericDAO<Long, Usuario> genericDAO;

	@Test
	public void teste_genericDAO() {
		Long id = 1l;
		String novoCpf = "22222222222";
		// Teste de save
		Usuario u = Usuario.of("Andre Alcantara", "11111111111", new Date(), Arrays.asList(Role.ADMIN));
		genericDAO.save(u);
		Assert.assertThat(genericDAO.findAll().size(), CoreMatchers.is(1));

		// Teste de findBYId
		this.novaRequest();
		Usuario u1 = genericDAO.findById(id);
		Assert.assertThat(u1, CoreMatchers.is(u));
		
		// Teste de update
		this.novaRequest();
		u.setCpf(novoCpf);
		genericDAO.update(u);
		u = genericDAO.findById(id);
		Assert.assertThat(u.getCpf(), CoreMatchers.is(novoCpf));
		
		// Teste de remove
		this.novaRequest();
		genericDAO.remove(u);
		Assert.assertThat(genericDAO.findAll().size(), CoreMatchers.is(0));
	}

	public void teste_livroDAO() {
		Livro livro = Livro.of("Java 8 a gambi", "Andre Alcantara", "Nao sei o que e isso", 200, 0.5D,
				"E livro que nao ensina nada de bom.");
		livroDAO.save(livro);
		Assert.assertTrue(livroDAO.findAll().size() == 1);
	}

	@Test
	public void teste_controlador() throws BadRequestDaoException {
		Usuario u = Usuario.of("Andre Alcantara", "11111111111", new Date(), Arrays.asList(Role.ADMIN));
		controladorUsuario.inserir(u);
		Assert.assertThat(u, CoreMatchers.is(controladorUsuario.buscarPorId(u.getId())));
	}

	@Test
	public void teste_evento_controlador() throws BadRequestDaoException {
		Usuario u = Usuario.of("Andre Alcantara", "11111111111", new Date(), Arrays.asList(Role.ADMIN));
		controladorUsuario.inserir(u);
		int size = historicoUsuarioDAO.findAll().size();
		Assert.assertThat(size, CoreMatchers.is(1));
	}

	@Test
	public void teste_usuarioDao_e_historicoDao() {
		Usuario u = Usuario.of("Andre Alcantara", "11111111111", new Date(), Arrays.asList(Role.ADMIN));
		usuarioDAO.save(u);
		HistoricoUsuario hu = HistoricoUsuario.of(u, AcaoEntidade.CREATE);
		historicoUsuarioDAO.save(hu);
		Assert.assertThat(usuarioDAO.findAll().size(), CoreMatchers.is(1));
		Assert.assertThat(historicoUsuarioDAO.findAll().size(), CoreMatchers.is(1));
	}
	
	@Test
	public void teste_historico_observer_usuario() throws BadRequestDaoException{
		int count = quantidadeHistorico();
		List<HistoricoUsuario> listUser = historicoUsuarioDAO.findAll();
		
		Assert.assertThat(listUser.size(), CoreMatchers.is(count));
	}
	
	public int quantidadeHistorico() throws BadRequestDaoException{
		int count = 0;
		Long id = 1l;
		String novoCpf = "22222222222";
		// Usuario inserido
		Usuario u = Usuario.of("Andre Alcantara", "11111111111", new Date(), Arrays.asList(Role.ADMIN));
		controladorUsuario.inserir(u);
		count++;
		
		// Usuario alterado
		this.novaRequest();
		u = genericDAO.findById(id);
		u.setCpf(novoCpf);
		controladorUsuario.alterar(u);
		count++;

		// Usuario removido
		this.novaRequest();
		controladorUsuario.remover(u);
		count++;

		return count;
	}

}
