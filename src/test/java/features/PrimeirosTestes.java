package features;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.ufpe.exemploprojeto.DAO.HistoricoUsuarioDAO;
import br.ufpe.exemploprojeto.DAO.LivroDAO;
import br.ufpe.exemploprojeto.DAO.PessoaDAO;
import br.ufpe.exemploprojeto.DAO.UsuarioDAO;
import br.ufpe.exemploprojeto.DAO.util.GenericDAO;
import br.ufpe.exemploprojeto.annotation.DAO;
import br.ufpe.exemploprojeto.controlador.ControladorUsuario;
import br.ufpe.exemploprojeto.controlador.exception.ControladorException;
import br.ufpe.exemploprojeto.model.HistoricoUsuario;
import br.ufpe.exemploprojeto.model.Livro;
import br.ufpe.exemploprojeto.model.Pessoa;
import br.ufpe.exemploprojeto.model.Usuario;
import br.ufpe.exemploprojeto.model.util.AcaoEntidade;
import br.ufpe.exemploprojeto.model.util.Endereco;
import br.ufpe.exemploprojeto.model.util.Role;
import util.ExemploTestEnv;

public class PrimeirosTestes extends ExemploTestEnv {
	private static final long serialVersionUID = 4734711613523663825L;

	@Inject
	private UsuarioDAO usuarioDAO;

	@Inject
	private LivroDAO livroDAO;
	
	@Inject
	private PessoaDAO pessoaDAO;

	@Inject
	private ControladorUsuario controladorUsuario;

	@Inject
	private HistoricoUsuarioDAO historicoUsuarioDAO;
	
	@Inject @DAO(Livro.class)
	private GenericDAO<Long, Livro> genericDAO;

	@Test
	public void teste_genericDAO() {
		Long id = 1l;
		String novaCaracteristica = "livro legalzim";
		// Teste de save
		Livro livro = Livro.of("Java 8 a gambi", "Andre Alcantara", "Nao sei o que e isso", 200, 0.5D,
				"E livro que nao ensina nada de bom.");
		genericDAO.save(livro);
		Assert.assertThat(genericDAO.findAll().size(), CoreMatchers.is(1));

		// Teste de findBYId
		this.novaRequest();
		Livro livro2 = genericDAO.findById(id);
		Assert.assertThat(livro2, CoreMatchers.is(livro));
		
		// Teste de update
		this.novaRequest();
		livro.setCaracteristicas(novaCaracteristica);
		genericDAO.update(livro);
		livro = genericDAO.findById(id);
		Assert.assertThat(livro.getCaracteristicas(), CoreMatchers.is(novaCaracteristica));
		
		// Teste de remove
		this.novaRequest();
		genericDAO.remove(livro);
		Assert.assertThat(genericDAO.findAll().size(), CoreMatchers.is(0));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void tese_role_valueof_error(){
		Role.valueOf(1001);
		Assert.assertTrue(false);
	}
	
	@Test
	public void tese_role_valueof(){
		Assert.assertThat(Role.valueOf(1), CoreMatchers.is(Role.ADMIN));
	}

	@Test
	public void teste_usuarioDAO() {
		Usuario u = get_usuario();
		pessoaDAO.save(u.getPessoa());
		usuarioDAO.save(u);
		Assert.assertTrue(usuarioDAO.findAll().size() == 1);
		Assert.assertThat(usuarioDAO.findAll().size(), CoreMatchers.is(1));
	}

	@Test
	public void teste_controlador() throws ControladorException {
		Usuario u = get_usuario();
		controladorUsuario.inserir(u);
		Assert.assertThat(u, CoreMatchers.is(controladorUsuario.buscarPorId(u.getId())));
	}

	@Test
	public void teste_evento_controlador() throws ControladorException {
		Usuario u = get_usuario();
		controladorUsuario.inserir(u);
		int size = historicoUsuarioDAO.findAll().size();
		Assert.assertThat(size, CoreMatchers.is(1));
	}

	@Test
	public void teste_usuarioDao_e_historicoDao() {
		Usuario u = get_usuario();
		pessoaDAO.save(u.getPessoa());
		usuarioDAO.save(u);
		HistoricoUsuario hu = HistoricoUsuario.of(u, AcaoEntidade.CREATE);
		historicoUsuarioDAO.save(hu);
		Assert.assertThat(usuarioDAO.findAll().size(), CoreMatchers.is(1));
		Assert.assertThat(historicoUsuarioDAO.findAll().size(), CoreMatchers.is(1));
	}
	
	@Test
	public void teste_historico_observer_usuario() throws ControladorException{
		int count = quantidadeHistorico();
		List<HistoricoUsuario> listUser = historicoUsuarioDAO.findAll();
		System.out.println(listUser);
		Assert.assertThat(listUser.size(), CoreMatchers.is(count));
	}
	
	public int quantidadeHistorico() throws ControladorException{
		int count = 0;
		Long id = 1l;
		
		String pass = (new BCryptPasswordEncoder()).encode("09987654321");
		// Usuario inserido
		Usuario u = get_usuario();
		controladorUsuario.inserir(u);
		count++;
		
		// Usuario alterado
		this.novaRequest();
		u = usuarioDAO.findById(id);
		u.setPass(pass);
		controladorUsuario.alterar(u);
		count++;

		// Usuario removido
		this.novaRequest();
		controladorUsuario.remover(u);
		count++;

		return count;
	}
	
	public Usuario get_usuario(){
		Endereco end = Endereco.of("Rua dos bobos", 0, null, "Lembra que a casa", "nao tinha nada", "Nem teto.");
		Pessoa p = Pessoa.of("Andre Alcantara", "11111111111", end, new Date(LocalDate.of(1989, 10, 4).toEpochDay()));
		BCryptPasswordEncoder password = new BCryptPasswordEncoder();
		String senha = password.encode("12345678909");
		Usuario u = Usuario.of("andre.alcantara", senha, p, Arrays.asList(Role.ADMIN));
		return u;
	}

}