package br.ufpe.exemploprojeto.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "livro")
@SequenceGenerator(name = "seq_livro", sequenceName = "livro_id_seq", allocationSize = 1)
public class Livro implements Serializable {
	private static final long serialVersionUID = -2170972511893383692L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_livro")	
	private Long id;
	
	private String titulo;
	
	private String autores;
	
	private String isbn;
	
	private int quantidadePaginas;
	
	private double preco;
	
	private String caracteristicas;
	
	public Livro(){}
	
	public static Livro of(String titulo, String autores, String isbn,
			int quantidadePaginas, double preco, String caracteristicas){
		Livro livro = new Livro();
		livro.autores = autores;
		livro.titulo = titulo;
		livro.isbn = isbn;
		livro.quantidadePaginas = quantidadePaginas;
		livro.preco = preco;
		livro.caracteristicas = caracteristicas;
		return livro;
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
		Livro other = (Livro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

	@Override
	public String toString() {
		return "Livro [id=" + id + ", titulo=" + titulo + ", autores=" + autores + ", isbn=" + isbn
				+ ", quantidadePaginas=" + quantidadePaginas + ", preco=" + preco + ", caracteristicas="
				+ caracteristicas + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutores() {
		return autores;
	}

	public void setAutores(String autores) {
		this.autores = autores;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getQuantidadePaginas() {
		return quantidadePaginas;
	}

	public void setQuantidadePaginas(int quantidadePaginas) {
		this.quantidadePaginas = quantidadePaginas;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public String getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}
	
}
