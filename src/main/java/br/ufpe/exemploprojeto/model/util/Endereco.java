package br.ufpe.exemploprojeto.model.util;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Endereco implements Serializable {
	private static final long serialVersionUID = -7803474851857846026L;

	private String rua;
	
	private int numero;
	
	private String complemento;
	
	private String bairro;
	
	private String cidade;
	
	private String cep;

	public static Endereco of(String rua, int numero, String complemento,
			String bairro, String cidade, String cep) {
		Endereco endereco = new Endereco();
		endereco.rua = rua;
		endereco.numero = numero;
		endereco.complemento = complemento;
		endereco.bairro = bairro;
		endereco.cidade = cidade;
		endereco.cep = cep;
		return endereco;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
}
