package br.ufpe.exemploprojeto.DAO.exception;

public class BadRequestDaoException extends Exception {

	private static final long serialVersionUID = -4110967156235405485L;

	public BadRequestDaoException() {
		super();
	}

	public BadRequestDaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadRequestDaoException(String message) {
		super(message);
	}

	public BadRequestDaoException(Throwable cause) {
		super(cause);
	}

}
