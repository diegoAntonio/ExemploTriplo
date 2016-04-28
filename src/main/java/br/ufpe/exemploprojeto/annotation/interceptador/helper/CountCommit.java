package br.ufpe.exemploprojeto.annotation.interceptador.helper;

import javax.enterprise.context.RequestScoped;

/**
 * Solução proposta por Hially
 * 
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
}
