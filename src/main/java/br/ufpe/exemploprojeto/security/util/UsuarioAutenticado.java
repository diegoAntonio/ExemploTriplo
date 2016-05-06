package br.ufpe.exemploprojeto.security.util;

import java.util.Date;
import java.util.List;

import br.ufpe.exemploprojeto.model.Role;
import br.ufpe.exemploprojeto.model.Usuario;
import br.ufpe.exemploprojeto.model.util.EntidadePadrao;

public class UsuarioAutenticado implements EntidadePadrao<Long> {
	private static final long serialVersionUID = -103795365260782847L;

	private Long id;
	
	private String nome;
	
	private String cpf;
	
	private String login;
	
	private Date timestamp;
	
	private List<Role> permissoes;
	
	public static UsuarioAutenticado of(Usuario user) {
		UsuarioAutenticado userAuth = new UsuarioAutenticado();
		userAuth.id = user.getId();
		userAuth.nome = user.getPessoa().getNome();
		userAuth.nome = user.getPessoa().getNome();
		userAuth.login = user.getLogin();
		userAuth.timestamp = user.getTimestamp();
		userAuth.permissoes = user.getPermissoes();
		return userAuth;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioAutenticado other = (UsuarioAutenticado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public List<Role> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Role> permissoes) {
		this.permissoes = permissoes;
	}
}
