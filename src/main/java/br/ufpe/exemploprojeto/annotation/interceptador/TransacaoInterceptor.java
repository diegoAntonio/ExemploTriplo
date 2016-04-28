package br.ufpe.exemploprojeto.annotation.interceptador;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

import br.ufpe.exemploprojeto.annotation.Transacao;
import br.ufpe.exemploprojeto.annotation.interceptador.helper.CountCommit;

@Interceptor @Transacao
public class TransacaoInterceptor {

	@Inject
	private EntityManager em;
	
	@Inject
	private CountCommit countCommit;
	
	@AroundInvoke
	public Object metodoInterceptador(InvocationContext ctx) throws Exception{
		Object retorno = null;
		try {
			if(countCommit.ehZero()){
				em.getTransaction().begin();
			}
			System.out.println("Entrou");
			countCommit.incrementarCommit();
			retorno = ctx.proceed();
			countCommit.decrementarCommit();
			
			if(countCommit.ehZero()){
				em.getTransaction().commit();
			}
		} catch (Exception e) {
			countCommit.zerarCommit();
			em.getTransaction().rollback();
			throw e;
		}

		System.out.println("saiu");
		return retorno;
	}
}
