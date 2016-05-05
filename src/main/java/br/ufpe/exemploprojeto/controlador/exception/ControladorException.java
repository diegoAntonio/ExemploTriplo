package br.ufpe.exemploprojeto.controlador.exception;

public class ControladorException extends Exception {
	private static final long serialVersionUID = -8234674411567475573L;

	public ControladorException() {
		super();
	}

	public ControladorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ControladorException(String message) {
		super(message);
	}

	public ControladorException(Throwable cause) {
		super(cause);
	}

	
}
