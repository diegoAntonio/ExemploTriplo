package br.ufpe.exemploprojeto.annotation.literal;

import javax.enterprise.util.AnnotationLiteral;

import br.ufpe.exemploprojeto.annotation.DAO;

public class AnnotationDAOLiteral extends AnnotationLiteral<DAO> implements DAO{
	private static final long serialVersionUID = 8268125039834083536L;

	private Class<?> value;
		
	public AnnotationDAOLiteral(Class<?> value) {
		this.value = value;
	}

	public static AnnotationDAOLiteral network(Class<?> value) {
		return new AnnotationDAOLiteral(value);
	}

	@Override
	public Class<?> value() {
		return value;
	}
}
