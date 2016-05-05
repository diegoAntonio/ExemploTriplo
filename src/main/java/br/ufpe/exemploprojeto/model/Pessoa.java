package br.ufpe.exemploprojeto.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.ufpe.exemploprojeto.model.util.Endereco;
import br.ufpe.exemploprojeto.model.util.Entidade;

@Entity
@Table(name="pessoa")
@SequenceGenerator(name="seq_pessoa", allocationSize=1, sequenceName="pessoa_id_seq")
public class Pessoa implements Entidade<Long> {
	private static final long serialVersionUID = -3093038371722387304L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_pessoa")
	private Long id;
	
	private String nome;
	
	@Column(length=11, unique=true)
	private String cpf;
	
	@Embedded
	private Endereco endereco;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	@Temporal(TemporalType.DATE)
	private Date dtNascimento;
	
	
	public static Pessoa of(String nome, String cpf, Endereco endereco, Date dtNascimento) {
		Pessoa pessoa = new Pessoa();
		pessoa.nome = nome;
		pessoa.cpf = cpf;
		pessoa.endereco = endereco;
		pessoa.timestamp = new Date();
		pessoa.dtNascimento = dtNascimento;
		return pessoa;
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
		Pessoa other = (Pessoa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", endereco=" + endereco + ", timestamp="
				+ timestamp + ", dtNascimento=" + dtNascimento + "]";
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}
}
