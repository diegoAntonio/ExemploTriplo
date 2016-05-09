package br.ufpe.exemploprojeto.util;

import java.io.Serializable;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe util para inject de metodos sem classe especifica.
 * @author andre.alcantara
 *
 */
public class Produtor implements Serializable{
	private static final long serialVersionUID = -3845659694293118577L;

	@Produces
	public Logger criaLogger(InjectionPoint ip) {
	return LoggerFactory.getLogger(ip.getMember()
			.getDeclaringClass().getName());
	}
	
	
	public static <T> T staticClassCDI(Class<T> clazz){
		 Instance<T> instance = CDI.current().select(clazz);
		    return instance.get();
	}
	
}


