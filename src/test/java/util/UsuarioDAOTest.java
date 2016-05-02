package util;

import java.util.Arrays;
import java.util.Date;

import javax.inject.Inject;

import org.hamcrest.CoreMatchers;
import org.jglue.cdiunit.ContextController;
import org.junit.Assert;
import org.junit.Test;

import br.ufpe.exemploprojeto.DAO.LivroDAO;
import br.ufpe.exemploprojeto.DAO.UsuarioDAO;
import br.ufpe.exemploprojeto.model.Livro;
import br.ufpe.exemploprojeto.model.Role;
import br.ufpe.exemploprojeto.model.Usuario;

public class UsuarioDAOTest extends ExemploTestEnv {

	@Inject
	private UsuarioDAO usuarioDAO;
	
	@Inject
	private LivroDAO livroDAO;
	
	@Test
	public void teste(){
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
	
}
