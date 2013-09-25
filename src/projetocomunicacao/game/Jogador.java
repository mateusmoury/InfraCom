package projetocomunicacao.game;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;

public class Jogador implements Serializable {

	private static final long serialVersionUID = 6359777978316950869L;
	private String nome;
        private boolean[] usadas;
	private LinkedList<Peca> pecas;
	private boolean querSair;
        private boolean status;
	private int id;
	private int numPausas;
        private String IP;
	
	public Jogador(String nome, String IP){
		this.nome = nome;
		this.pecas = new LinkedList<Peca>();
                this.usadas = new boolean[6];
                Arrays.fill(this.usadas,false);
		this.querSair = false;
                this.status = true;
		this.numPausas = 3;
                this.IP = IP;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public boolean isStatus() {
            return this.status;
        }

        public String getIP() {
            return this.IP;
        }
        
	public boolean isQuerSair() {
		return this.querSair;
	}

	public void setQuerSair(boolean querSair) {
		this.querSair = querSair;
	}

	public String getNome() {
		return this.nome;
	}
	
	public boolean pausa(Jogo jogo){
		boolean ret;
		if(jogo.getVez()==this.id && this.numPausas>0){
			this.numPausas--;
			jogo.setEstaPausado(true);
			//ativa timer
			ret = true;
		} else ret = false;
		return ret;
	}
	
	public boolean despausa(Jogo jogo){
		boolean ret;
		if(jogo.isEstaPausado() && jogo.getVez()==this.id){
			jogo.setEstaPausado(false);
			ret = true;
			//desativa timer
		}
		else ret = false;
		return ret;
	}
	
	//checa se jogador pode jogar pecas[ind] no extremo esquerdo do jogo
	//se lado == 0 e no direito caso contrario. Se puder, retorna lado da peca
	//que dava ser jogado para encaixar na configuracao do tabuleiro, senao
	//retorna -1
	public int pode_jogar(int ind, int lado, Jogo jogo){
                if(ind>6 || usadas[ind]==true) return -1;
		Peca peca = this.pecas.get(ind);
		int lado_peca;
		if(jogo.getTabuleiro().isEmpty()){
			lado_peca = 0;
		} 
		else {
			lado_peca = -1;
			if(lado==0){
				if(peca.pode_jogar(jogo.getExtremo_esq(), 0)) lado_peca = 0;
				if(peca.pode_jogar(jogo.getExtremo_esq(), 1)) lado_peca = 1;
			} else {
				if(peca.pode_jogar(jogo.getExtremo_dir(), 0)) lado_peca = 0;
				if(peca.pode_jogar(jogo.getExtremo_dir(), 1)) lado_peca = 1;
			}
		}
		return lado_peca;
	}
	
	//joga a peca que pode ser jogada, especificando o lado do tabuleiro
	//e o lado da peca
	public void joga(int ind, int lado, Jogo jogo, int lado_peca){
		Peca peca = this.pecas.get(ind);
		if(jogo.getTabuleiro().isEmpty()){
			jogo.setExtremo_esq(peca.getValor1());
			jogo.setExtremo_dir(peca.getValor2());
			jogo.getTabuleiro().add(peca);
		} else {
			if(lado==0){
				if(lado_peca==0){
					jogo.setExtremo_esq(peca.getValor2());
					int temp = peca.getValor1();
					peca.setValor1(peca.getValor2());
					peca.setValor2(temp);
				} else {
					jogo.setExtremo_esq(peca.getValor1());
				}
				jogo.getTabuleiro().addFirst(peca);
			} else {
				if(lado_peca==0){
					jogo.setExtremo_dir(peca.getValor2());
				} else {
					jogo.setExtremo_dir(peca.getValor1());
					int temp = peca.getValor1();
					peca.setValor1(peca.getValor2());
					peca.setValor2(temp);
				}
				jogo.getTabuleiro().addLast(peca);
			}
		}
                this.usadas[ind]=true;
		//this.pecas.remove(ind);
		//System.out.println(this.pecas.size());
	}
	
	//verifica se o jogo acabou. caso nao, retorna 0.
	//caso sim, retornar quantos pontos o jogador ganhador
	//fez para a sua equipe. A checagem quanto a vitoria
	//por contagem de pontos esta na classe jogo
	public int verifica(int extremo, Jogo jogo){
		int ans = 0;
                int used=0;
                for(boolean b : this.usadas) {
                    if(b) ++used;
                }
		if(used==6){
			if(extremo == 0){
				Peca peca = jogo.getTabuleiro().getFirst();
				if(peca.getValor1() == peca.getValor2()){
					if(jogo.getExtremo_dir() == jogo.getExtremo_esq()) ans = 4;
					else ans = 2;
				}
				else {
					if(jogo.getExtremo_dir() == jogo.getExtremo_esq()) ans = 3;
					else ans = 1;
				}
			} else {
				Peca peca = jogo.getTabuleiro().getLast();
				if(peca.getValor1() == peca.getValor2()){
					if(jogo.getExtremo_dir() == jogo.getExtremo_esq()) ans = 4;
					else ans = 2;
				}
				else {
					if(jogo.getExtremo_dir() == jogo.getExtremo_esq()) ans = 3;
					else ans = 1;
				}
			}
		}
		return ans;
	}

	public int getMaiorCarroca() {
		int ret=-1;
		for(int i = 0; i < 6; ++i) {
                        if(this.usadas[i]) continue;
                        Peca peca = this.pecas.get(i);
			if(peca.carroca()) {
				if(ret==-1) ret=peca.getValor1();
				else {
					if(peca.getValor1()>ret) ret=peca.getValor1();
				}
			}
		}
		return ret;
	}
	
	public LinkedList<Peca> getPecas() {
		return this.pecas;
	}
        
        public boolean[] getUsadas() {
            return this.usadas;
        }
	
	boolean checaNome(String nome){
		boolean ans = (this.nome == nome);
		return ans;
	}
	
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
}

