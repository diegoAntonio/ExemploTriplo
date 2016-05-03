package util;

import java.util.Arrays;
import java.util.Date;

import javax.inject.Inject;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import br.ufpe.exemploprojeto.DAO.HistoricoUsuarioDAO;
import br.ufpe.exemploprojeto.DAO.LivroDAO;
import br.ufpe.exemploprojeto.DAO.UsuarioDAO;
import br.ufpe.exemploprojeto.DAO.exception.BadRequestDaoException;
import br.ufpe.exemploprojeto.controlador.ControladorUsuario;
import br.ufpe.exemploprojeto.model.HistoricoUsuario;
import br.ufpe.exemploprojeto.model.Livro;
import br.ufpe.exemploprojeto.model.Role;
import br.ufpe.exemploprojeto.model.Usuario;

public class UsuarioDAOTest extends ExemploTestEnv {
	private static final long serialVersionUID = 4734711613523663825L;

	@Inject
	private UsuarioDAO usuarioDAO;
	
	@Inject
	private LivroDAO livroDAO;
	
	@Inject
	private ControladorUsuario controladorUsuario;

	@Inject
	private HistoricoUsuarioDAO historicoUsuarioDAO;
	
	@Test
	public void testeDAO(){
		Long id = 1l;
		Usuario u = Usuario.of("Andre Alcantara", "11111111111", new Date(), Arrays.asList(Role.ADMIN));
		usuarioDAO.save(u);
		livroDAO.save(Livro.of("Java 8 a gambi", "Andre Alcantara", "Nao sei o que e isso", 200, 0.5D, "E livro que nao ensina nada de bom."));
		System.out.println(u);
		Usuario u1 = usuarioDAO.findById(id);
		System.out.println(u1);
		Assert.assertThat(u1, CoreMatchers.is(u));
		Assert.assertTrue(livroDAO.findAll().size() == 1);
		System.out.println(livroDAO.findAll().get(0));
	}
	
	
	@Test
	public void testeControlador() throws BadRequestDaoException{
		Usuario u = Usuario.of("Andre Alcantara", "11111111111", new Date(), Arrays.asList(Role.ADMIN));
		controladorUsuario.inserir(u);
		Assert.assertThat(u, CoreMatchers.is(controladorUsuario.buscarPorId(u.getId())));
		System.out.println(u);
	}
	
	@Test
	public void testeEventoControlador() throws BadRequestDaoException{
		Usuario u = Usuario.of("Andre Alcantara", "11111111111", new Date(), Arrays.asList(Role.ADMIN));
		controladorUsuario.inserir(u);
		int size = historicoUsuarioDAO.findAll().size();
		Assert.assertThat(size, CoreMatchers.is(1));
	}
	

	@Test
	public void testeDaos(){
		Usuario u = Usuario.of("Andre Alcantara", "11111111111", new Date(), Arrays.asList(Role.ADMIN));
		usuarioDAO.save(u);
		
		HistoricoUsuario hu = HistoricoUsuario.of(u);
		historicoUsuarioDAO.save(hu);
	}
	
}
