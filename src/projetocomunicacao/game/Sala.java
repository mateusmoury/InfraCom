package projetocomunicacao.game;

import java.io.Serializable;
import java.util.LinkedList;

public class Sala implements Serializable{

	private static final long serialVersionUID = 1901800858210358768L;
	private Jogo jogo;
	private int id;
        private int lado;
        private String path;
        private int seqNumber;
        
	public Sala(int id) {
		this.jogo = new Jogo();
		this.id=id;
                this.lado=-1;
                this.path="";
                this.seqNumber=-1;
	}
        
        public boolean isStatus(int ind){
           return this.jogo.getJogadores()[ind].isStatus();
        }
        
        public void setStatus(int ind, boolean status){
            this.jogo.getJogadores()[ind].setStatus(status);
        }
        
        public int getIndInicial(){
            return this.jogo.getIndInicial();
        }
        
        public void incrementaSeqNumber() {
            ++this.seqNumber;
        }
        
        public int getSeqNumber() {
            return this.seqNumber;
        }

        public int isLado() {
            return lado;
        }
        
        public int getLado() {
            return this.lado;
        }

        public String getPath() {
            return path;
        }

        public void setLado(int lado) {
            this.lado = lado;
        }

        public void setPath(String path) {
            this.path = path;
        }
	
	public int getId() {
		return id;
	}
	
	public Jogador[] getJogadores() {
		return jogo.getJogadores();
	}
	
	public int getQuantidadeJogadores() {
		return jogo.getQuantidadeJogadores();
	}
	
	public boolean checaQuantidadeJogadores() {
		return jogo.checaQuantidadeJogadores();
	}
	
	public boolean distribuiPecas() {
		return jogo.distribuiPecas();
	}
	
        public boolean podeAdicionar(int time) {
            return jogo.podeAdicionar(time);
        }
        
	public int adicionarJogador(Jogador jogador, int time) {
		return jogo.adicionarJogador(jogador,time);
	}
	
	public int getPlacar1() {
		return	jogo.getPlacar1();
	}

	public int getPlacar2() {
		return jogo.getPlacar2();
	}
	
	public void incrementaVez(){
		jogo.incrementaVez();
	}
	
	public int getExtremo_esq() {
		return jogo.getExtremo_esq();
	}

	public int getExtremo_dir() {
		return jogo.getExtremo_dir();
	}

	public void setExtremo_esq(int extremo_esq) {
		jogo.setExtremo_esq(extremo_esq);
	}

	public void setExtremo_dir(int extremo_dir) {
		jogo.setExtremo_dir(extremo_dir);
	}

	public LinkedList<Peca> getTabuleiro() {
		return jogo.getTabuleiro();
	}
	
	public int pode_jogar(Jogador jogador, int ind, int lado){
		return jogo.pode_jogar(jogador,ind,lado);
	}
	
	public void joga(Jogador jogador, int ind, int lado, int lado_peca){
		jogo.joga(jogador,ind,lado,lado_peca);
	}
	
	public int verifica(int ind, int extremo){
		return jogo.verifica(ind, extremo);
	}
	
	public int getVez() {
		return jogo.getVez();
	}
	
	public void primeiraJogada() {
		jogo.primeiraJogada();
	}
	
	public boolean tocou(Jogador jogador) {
		return jogo.tocou(jogador);
	}
	
        public void aumentaRodada() {
            jogo.aumentaRodada();
        }
        
	public boolean getEmAndamento() {
		return jogo.getEmAndamento();
	}
	
	public void proximaRodada() {
		jogo.proximaRodada();
	}
	
	public int getRodada() {
		return jogo.getRodada();
	}
	
	public boolean isAcabouJogo() {
		return this.jogo.isAcabouJogo();
	}


	public void setAcabouJogo(boolean acabouJogo) {
		this.jogo.setAcabouJogo(acabouJogo);
	}
	
	public boolean isAcabouPartida() {
		return this.jogo.isAcabouPartida();
	}

	public void setAcabouPartida(boolean acabouPartida) {
		this.jogo.setAcabouPartida(acabouPartida);
	}
	public boolean acabouJogo() {
		return this.jogo.isAcabouJogo();
	}
	public void setAcabou() {
		this.jogo.setAcabouJogo(false);
	}
        
        public int getVencedor(){
            return this.jogo.getVencedor();
        }
        
        public void reiniciarJogo(){
            this.jogo.reiniciarJogo();
        }
        
        public void reiniciarPartida(){
            this.jogo.reiniciarPartida();;
        }

}
