package br.ufpe.exemploprojeto.util;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Produtor implements Serializable{
	private static final long serialVersionUID = -3845659694293118577L;

	@Produces
	public Logger criaLogger(InjectionPoint ip) {
	return LoggerFactory.getLogger(ip.getMember()
			.getDeclaringClass().getName());
	}
	
	
}


