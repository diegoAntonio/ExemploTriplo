package br.ufpe.exemploprojeto.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
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

import br.ufpe.exemploprojeto.model.util.Entidade;

@Entity
@Table(name = "usuario")
@SequenceGenerator(name = "seq_usuario", sequenceName = "usuario_id_seq", allocationSize = 1)
public class Usuario implements Entidade<Long> {
	private static final long serialVersionUID = 1079619057493715949L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
	private Long id;
	
	private String nome;
	
	private String cpf;
	
	@Temporal(TemporalType.DATE)
	private Date dtNascimento;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	@ElementCollection(targetClass=Role.class)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name="role_usuario")
	@Column(name="role")
	private List<Role> permissoes;
	
	public Usuario(){}
	
	public static Usuario lite(Long id){
		Usuario usuario = new Usuario();
		usuario.setId(id);
		return usuario;
	}
	
	public static Usuario of(String nome, String cpf, Date dtNascimento, List<Role> permissoes) {
		Usuario u = new Usuario();
		u.nome = nome;
		u.cpf = cpf;
		u.dtNascimento = dtNascimento;
		u.permissoes = permissoes;
		u.timestamp = new Date();
		return u;
	}

	public String toString(){
		return "Id: " + id + ", Nome:" + nome + ", Idade: " + getIdade() + 
				" Permissoes: " + permissoes + ".";
	}
	
	public int getIdade(){
		int retorno = -1;
		if(dtNascimento != null){
			Instant dtNascimentoInstant = Instant.ofEpochMilli(dtNascimento.getTime());
//			Instant dtNascimentoInstant = dtNascimento.toInstant();
			Period p = Period.between(dtNascimentoInstant
					.atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now());
			retorno = p.normalized().getYears();
		}	
		return retorno;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
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
