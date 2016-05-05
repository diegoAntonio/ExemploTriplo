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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import br.ufpe.exemploprojeto.model.util.AcaoEntidade;
import br.ufpe.exemploprojeto.model.util.Entidade;

@Entity
@Table(name = "historico_usuario",
	   uniqueConstraints=@UniqueConstraint(columnNames={"id", "id_usuario"})
)
@SequenceGenerator(name = "seq_historico_ususario", allocationSize = 1, sequenceName = "historico_usuario_id_seq")
public class HistoricoUsuario implements Entidade<Long> {
	private static final long serialVersionUID = 8324816741279180157L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_historico_ususario")
	private Long id;

	private String nome;

	private String cpf;

	@Temporal(TemporalType.DATE)
	private Date dtNascimento;

	@ElementCollection(targetClass = Role.class)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "role_historico")
	@Column(name = "role")
	private List<Role> permissoes;

	@Column(name="id_usuario")
	private Long idUsuario;
	
	@Transient
	private Usuario usuario;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	@Enumerated(EnumType.STRING)
	@Column(name = "acao")
	private AcaoEntidade acao;

	public static HistoricoUsuario of(Usuario user, AcaoEntidade acao) {
		HistoricoUsuario historicoUsuario = new HistoricoUsuario();
		historicoUsuario.timestamp = new Date();

		historicoUsuario.nome = user.getNome();
		historicoUsuario.cpf = user.getCpf();
		historicoUsuario.dtNascimento = user.getDtNascimento();
		historicoUsuario.permissoes = new ArrayList<Role>(user.getPermissoes());
		historicoUsuario.usuario = user;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
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

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
