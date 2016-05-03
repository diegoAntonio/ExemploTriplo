package br.ufpe.exemploprojeto.annotation.interceptador.helper;

import javax.enterprise.context.RequestScoped;

/**
 * Classe eh um contador.
 * E usada quando as trasancoes de metodos sao abertas.
 * Para evitar iniciar uma transacao(entityManager.getTransaction().begin()) quando ela ja estiver aberta.
 * E evitar fechar uma transacao(entityManager.getTransaction().commit()) quando ela ja estiver fechada.
 * 
 * Solução proposta por Hially
 * @author andre.alcantara
 *
 */
@RequestScoped
public class CountCommit {

	private int count = 0;

	public void incrementarCommit() {
		++count;
	}

	public void decrementarCommit() {
		--count;
	}
	
	public void zerarCommit(){
		count = 0;
	}
	
	public boolean ehZero(){
		return count == 0;
	}
	
	public int getCount(){
		return count;
	}
}
