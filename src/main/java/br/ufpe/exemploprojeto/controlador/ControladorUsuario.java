package br.ufpe.exemploprojeto.controlador;

import br.ufpe.exemploprojeto.model.Usuario;

public class ControladorUsuario extends Controlador<Long,Usuario> {
	private static final long serialVersionUID = 2274139660861473718L;
	public ControladorUsuario() {
		super(Usuario.class);
	}
}
