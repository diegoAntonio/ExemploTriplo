package br.ufpe.exemploprojeto.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import br.ufpe.exemploprojeto.model.util.EntidadePadrao;

@Entity
@Table(name="role", uniqueConstraints=@UniqueConstraint(name="unique_nome",columnNames={"nome"}))
@SequenceGenerator(name="seq_role", allocationSize=1,sequenceName="role_id_seq")
public class Role implements EntidadePadrao<Long> {
	private static final long serialVersionUID = 6018708163045427529L;
	
	@Id
	@GeneratedValue(generator="seq_role",strategy=GenerationType.SEQUENCE)
	private Long id;

	private String nome;
	
	private int nivel;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	public static Role lite(Long id){
		Role role = new Role();
		role.id = id;
		return role;
	}
	
	public static Role of(Long id, String nome, int nivel) {
		Role role = new Role();
		role.id = id;
		role.nome = nome;
		role.nivel = nivel;
		role.timestamp = new Date();
		return role;
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

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
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
		Role other = (Role) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
