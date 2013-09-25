package projetocomunicacao.game;

import java.io.Serializable;

public class Peca implements Serializable{

	private static final long serialVersionUID = 537534665835455715L;
	private int valor1;
	private int valor2;
	
	public Peca(int num){
		int n = (-1+(int)Math.sqrt(1+4*2*num))/2;
		this.valor1 = n;
		this.valor2 = num-(n*(n+1))/2;
	}
	
	public boolean pode_jogar(int valor, int lado){
		boolean ans = false;
		if(lado==0) ans = (valor == this.valor1);
		else ans = (valor == this.valor2);
		return ans;
	}
	
	public boolean carroca() {
		return this.valor1==this.valor2;
	}

	public void setValor1(int valor1) {
		this.valor1 = valor1;
	}

	public void setValor2(int valor2) {
		this.valor2 = valor2;
	}

	public int getValor1() {
		return this.valor1;
	}

	public int getValor2() {
		return this.valor2;
	}
}
