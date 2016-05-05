package br.ufpe.exemploprojeto.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.ufpe.exemploprojeto.model.util.Entidade;
import br.ufpe.exemploprojeto.model.util.Role;

@Entity
@Table(name = "usuario")
@SequenceGenerator(name = "seq_usuario", sequenceName = "usuario_id_seq", allocationSize = 1)
public class Usuario implements Entidade<Long> {
	private static final long serialVersionUID = 1079619057493715949L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
	private Long id;

	private String login;

	private String pass;
	
	@OneToOne
    @JoinColumn(name="pessoa_id")
	private Pessoa pessoa;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	@ElementCollection(targetClass = Role.class)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "role_usuario")
	@Column(name = "role")
	private List<Role> permissoes;

	public Usuario() {
	}

	public static Usuario lite(Long id) {
		Usuario usuario = new Usuario();
		usuario.setId(id);
		return usuario;
	}

	public static Usuario of(String login, String pass, Pessoa pessoa, List<Role> permissoes) {
		Usuario u = new Usuario();
		u.login = login;
		u.pass = pass;
		u.pessoa = pessoa;
		u.timestamp = new Date();
		u.permissoes = new ArrayList<>(permissoes);
		return u;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", login=" + login + ", pass=" + pass + ", timestamp=" + timestamp
				+ ", permissoes=" + permissoes + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Role> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Role> permissoes) {
		this.permissoes = permissoes;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
