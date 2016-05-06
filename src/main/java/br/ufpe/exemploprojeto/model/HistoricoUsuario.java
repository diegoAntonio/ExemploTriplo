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
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import br.ufpe.exemploprojeto.model.util.AcaoEntidade;
import br.ufpe.exemploprojeto.model.util.EntidadePadrao;
import br.ufpe.exemploprojeto.model.util.Role;

@Entity
@Table(name = "historico_usuario",
	   uniqueConstraints=@UniqueConstraint(columnNames={"id", "usuario_id","pessoa_usuario_id"}, name="unique_historico_usuario_usuario_pessoa")
)
@SequenceGenerator(name = "seq_historico_ususario", allocationSize = 1, sequenceName = "historico_usuario_id_seq")
public class HistoricoUsuario implements EntidadePadrao<Long> {
	private static final long serialVersionUID = 8324816741279180157L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_historico_ususario")
	private Long id;


	private String login;

	private String pass;

	@ElementCollection(targetClass = Role.class)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "role_historico", foreignKey=@ForeignKey(name="FK_historico_usuario_role"))
	@Column(name = "role")
	private List<Role> permissoes;

	@Column(name="usuario_id")
	private Long idUsuario;
	
	@Column(name="pessoa_usuario_id")
	private Long idPessoa;
	
	@Transient
	private Usuario usuario;
	
	@Transient
	private Pessoa pessoa;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	@Enumerated(EnumType.STRING)
	@Column(name = "acao")
	private AcaoEntidade acao;

	public static HistoricoUsuario of(Usuario user, AcaoEntidade acao) {
		HistoricoUsuario historicoUsuario = new HistoricoUsuario();
		historicoUsuario.timestamp = new Date();
		historicoUsuario.login = user.getLogin();
		historicoUsuario.pass = user.getPass();
		
		historicoUsuario.permissoes = new ArrayList<Role>(user.getPermissoes());
		
		historicoUsuario.usuario = user;
		historicoUsuario.pessoa = user.getPessoa();
		
		historicoUsuario.idPessoa = user.getPessoa().getId();
		historicoUsuario.idUsuario = user.getId();
		
		historicoUsuario.acao = acao;
		return historicoUsuario;
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
		HistoricoUsuario other = (HistoricoUsuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public AcaoEntidade getAcao() {
		return acao;
	}

	public void setAcao(AcaoEntidade acao) {
		this.acao = acao;
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

	public List<Role> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Role> permissoes) {
		this.permissoes = permissoes;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "HistoricoUsuario [id=" + id + ", login=" + login + ", pass=" + pass + ", permissoes=" + permissoes
				+ ", idUsuario=" + idUsuario + ", idPessoa=" + idPessoa + ", usuario=" + usuario + ", pessoa=" + pessoa
				+ ", timestamp=" + timestamp + ", acao=" + acao + "]";
	}
}
