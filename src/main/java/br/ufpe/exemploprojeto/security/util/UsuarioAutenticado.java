package br.ufpe.exemploprojeto.security.util;

import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.ufpe.exemploprojeto.model.util.Role;

public class UsuarioAutenticado {
	
	private Long id;
	
	private String nome;
	
	private String cpf;
	
	private Date dtNascimento;
	
	private Date timestamp;
	
	private List<Role> permissoes;

}
