package br.ufpe.exemploprojeto.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.ufpe.exemploprojeto.controlador.ControladorUsuario;
import br.ufpe.exemploprojeto.model.Usuario;
import br.ufpe.exemploprojeto.security.util.UsuarioAutenticado;
import br.ufpe.exemploprojeto.util.Produtor;

public class AutenticatorProviderExemplo implements AuthenticationProvider {

	private ControladorUsuario controladorUsuario;

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		controladorUsuario = Produtor.staticClassCDI(ControladorUsuario.class);
		String login = auth.getName();
		String senha = (String) auth.getCredentials();

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		Usuario usuario = null;
		try {
			usuario = controladorUsuario.consultarPorLogin(login);
		} catch (NoResultException e) {
			throw new BadCredentialsException("Usuário e/ou senha incorreta.");
		}
		if (usuario != null) {
			boolean senhaValidada = passwordEncoder.matches(senha, usuario.getPass());
			if (senhaValidada) {
				List<GrantedAuthority> perfis = usuario.getPermissoes().stream()
						.map(p -> new SimpleGrantedAuthority(p.toString())).collect(Collectors.toList());
				UsernamePasswordAuthenticationToken autenticacao = new UsernamePasswordAuthenticationToken(login, senha, perfis);

				UsuarioAutenticado usuarioAutenticado = UsuarioAutenticado.of(usuario);

				Map<String, UsuarioAutenticado> detalhes = new HashMap<String, UsuarioAutenticado>();
				detalhes.put("usuario", usuarioAutenticado);
				autenticacao.setDetails(detalhes);

				return autenticacao;
			} else {
				throw new BadCredentialsException("Usuário e/ou senha incorreta.");
			}
		} else {
			throw new BadCredentialsException("Usuário e/ou senha incorreta.");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
