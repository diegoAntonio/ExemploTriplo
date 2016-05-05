package br.ufpe.exemploprojeto.annotation.interceptador;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

import org.slf4j.Logger;

import br.ufpe.exemploprojeto.annotation.Transacao;
import br.ufpe.exemploprojeto.annotation.interceptador.helper.CountCommit;

/**
 * Classer interceptadora de metodos que tenham a anotacao {@link Transacao}. A
 * classe e responsavel por deixar os metodos transacionais. Iniciando o a
 * transacao do JPA e finalizando ao final de metodo anotado.
 * 
 * @author andre.alcantara
 *
 */
@Interceptor
@Transacao
public class TransacaoInterceptor {

	@Inject
	private EntityManager em;

	@Inject
	private CountCommit countCommit;

	@Inject
	private Logger log;

	/**
	 * Metodo que sera chamado durante a interceptacao.
	 * 
	 * @param ctx
	 *            - {@link InvocationContext} Contexto do weld.
	 * @return - Objeto do metodo.
	 * @throws Exception
	 *             - Erro que pode vir do fluxo do weld.
	 */
	@AroundInvoke
	public Object metodoInterceptador(InvocationContext ctx) throws Exception {
		Object retorno = null;
		try {
			if (countCommit.ehZero() && !em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			countCommit.incrementarCommit();
			retorno = ctx.proceed();
			countCommit.decrementarCommit();
			if (countCommit.ehZero() && em.getTransaction().isActive()) {
				em.getTransaction().commit();
			}
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			countCommit.zerarCommit();
			em.getTransaction().rollback();
			throw e;
		}
		return retorno;
	}
}
