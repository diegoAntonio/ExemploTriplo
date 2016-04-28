package util;

import java.util.Arrays;
import java.util.Date;

import javax.inject.Inject;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import br.ufpe.exemploprojeto.DAO.UsuarioDAO;
import br.ufpe.exemploprojeto.model.Role;
import br.ufpe.exemploprojeto.model.Usuario;

public class UsuarioDAOTest extends ExemploTestEnv {

	@Inject
	private UsuarioDAO usuarioDAO;
	
	@Test
	public void teste(){
		Long id = 1l;
		Usuario u = Usuario.of("Andre Alcantara", "11111111111", new Date(), Arrays.asList(Role.ADMIN));
		usuarioDAO.inserir(u);
		System.out.println(u);
		Usuario u1 = usuarioDAO.buscaPorId(id);
		System.out.println(u1);
		Assert.assertThat(u1, CoreMatchers.is(u));
	}
}
