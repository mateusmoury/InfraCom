package projetocomunicacao.game;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class Jogo implements Serializable{

	private static final long serialVersionUID = 8301305001906343948L;
	private Jogador jogadores[];
	private LinkedList<Peca> tabuleiro;
	private int placar1, placar2;
	private int vez;
	private int extremo_esq;
	private int extremo_dir;
	private int pecas[];
	private int quantidadeJogadores;
	private boolean emAndamento;
	private int rodada;
	private int vencedor;
	public boolean acabouPartida;
	public boolean aviso;
	private boolean acabouJogo;
	private boolean estaPausado;
        private int indInicial;
	private boolean toque;
        
	public Jogo(){
		this.jogadores = new Jogador[4];
		this.quantidadeJogadores=0;
		this.placar1 = this.placar2 = 0;
		this.tabuleiro = new LinkedList<Peca>();
                this.indInicial = -1;
		this.extremo_esq = this.extremo_dir = -1;
		this.emAndamento = false;
		this.vez = 0;
		this.rodada=0;
		this.vencedor = -1;
		this.acabouPartida = false;
		this.acabouJogo = false;
		this.estaPausado = false;
                this.toque=false;
	}
        
        public int getIndInicial() {
            return indInicial;
        }
        
	public boolean isEstaPausado() {
		return estaPausado;
	}

	public void setEstaPausado(boolean estaPausado) {
		this.estaPausado = estaPausado;
	}


	public boolean isAcabouJogo() {
		return acabouJogo;
	}


	public void setAcabouJogo(boolean acabouJogo) {
		this.acabouJogo = acabouJogo;
	}


	public boolean isAcabouPartida() {
		return acabouPartida;
	}

	public void setAcabouPartida(boolean acabouPartida) {
		this.acabouPartida = acabouPartida;
	}

	public Jogador[] getJogadores() {
		return this.jogadores;
	}
	
	public void proximaRodada() {
		this.rodada=this.rodada+1;
	}
	
	public int getRodada() {
		return this.rodada;
	}
	
	public void reiniciarJogo() {
		
                this.indInicial = -1;
	} 
	
	public void reiniciarPartida() {
            this.rodada=0;    
	    this.indInicial = -1;
	}

	public int getQuantidadeJogadores() {
		return quantidadeJogadores;
	}
	
        public boolean podeAdicionar(int time) {
            System.out.println("tentar adicionar no time "+time);
            for(int i = time; i < 4; i+=2) {
                if(this.jogadores[i]==null) {
                    System.out.println("O jogador "+i +" está nulo");
                    return true;
                }
                System.out.println("O jogador "+i+" esta ocupado");
            }
            return false;
        }
        
	public int adicionarJogador(Jogador jogador, int time) {
		for(int i = time; i<4;i+=2) {
			if(this.jogadores[i]==null) {
				this.jogadores[i]=jogador;
				++this.quantidadeJogadores;
				jogador.setId(i);
				return i;
			}
		}
		return -1;
	}
	
	public boolean checaQuantidadeJogadores() {
		return quantidadeJogadores==4;
	}
	
	public int getPlacar1() {
		return this.placar1;
	}

	public int getPlacar2() {
		return this.placar2;
	}

        public int getVencedor() {
            return vencedor;
        }

        public void setVencedor(int vencedor) {
            this.vencedor = vencedor;
        }

	//gera pecas aleatoriamente e as distribui para os jogadores. Se a quantidade de jogadores não for 4 retorna false.
	public boolean distribuiPecas(){
		if(!checaQuantidadeJogadores()) {
			return false;
		}
		this.emAndamento = true;
		this.pecas = new int[28];
		for (int i = 0; i < 28; ++i) this.pecas[i] = i;
		Random random = new Random();
		for (int i = 0; i < 28; ++i) {
			int prox = Math.abs(random.nextInt())%28;
			int aux = this.pecas[i];
			this.pecas[i] = this.pecas[prox];
			this.pecas[prox] = aux;
		}
		for(Jogador jogador : this.jogadores) {
			jogador.getPecas().clear();
                        Arrays.fill(jogador.getUsadas(),false);
		}
                
		this.tabuleiro.clear();
		for(int i = 0; i < 24; ++i){
			this.jogadores[i%4].getPecas().add(new Peca(this.pecas[i]));
		}
		return true;
	}
	
	public int getVez() {
		return this.vez;
	}
	
	public void incrementaVez(){
		this.vez = (this.vez+1)%4;
	}
	
	public int getExtremo_esq() {
		return this.extremo_esq;
	}

	public int getExtremo_dir() {
		return this.extremo_dir;
	}

	public void setExtremo_esq(int extremo_esq) {
		this.extremo_esq = extremo_esq;
	}

	public void setExtremo_dir(int extremo_dir) {
		this.extremo_dir = extremo_dir;
	}

	public LinkedList<Peca> getTabuleiro() {
		return this.tabuleiro;
	}
	
	public int pode_jogar(Jogador jogador, int ind, int lado){
                if(this.toque) {
                    toque=false;
                    return -3;
                }
		if(this.vez != jogador.getId()) return -2;
		return jogador.pode_jogar(ind, lado, this);
	}
	
	public void joga(Jogador jogador, int ind, int lado, int lado_peca){
		if (pode_jogar(jogador,ind,lado) == lado_peca) {
			jogador.joga(ind, lado, this, lado_peca);
                        if(this.indInicial==-1) this.indInicial = 0;
                        else if(lado==0) this.indInicial++;
			this.vez = (this.vez+1)%4;
		}
		int aux = this.verifica(jogador.getId(), lado);
                //if()
		if(aux>0){
			this.rodada++;
			this.vencedor = aux-1;
			this.acabouJogo = true;
			if(this.placar1 >= 7 ||this.placar2 >= 7){
				this.acabouPartida = true;
				this.placar1 = 0;
				this.placar2 = 0;
				this.rodada = 0;
			}
		} else if(aux==0) {
                    this.vencedor=-1;
                    this.rodada=0;
		    this.acabouJogo=true;
		}
	}
	
	//verifica se o jogador atual ganhou. Se nao ganhou normalmente,
	//verifica se ele ou alguem ganhou por contagem de pontos. caso 
	//isso aconteca, resultado ja eh somado no placar.
	//retorna -1 se jogo nao acabou, 0 se acabou por cancelamento
	// e ind+1 se algum jogador ganhou
	public int verifica(int ind, int extremo){
		Jogador jogador = this.jogadores[ind];
		int ans;
		int ganhaNormal = jogador.verifica(extremo, this);
		if(ganhaNormal!=0){
			if(ind%2==0) this.placar1 += ganhaNormal;
			else this.placar2 += ganhaNormal;
			ans = ind+1;
		} else {
			boolean cancela = false;
			boolean alguemJoga = false;
			int resp = 0;
			int soma = 100000000;
			for(int i = 0; i < 4 && !alguemJoga; ++i){
				int soma_temp = 0;
				for(int j = 0; j < this.jogadores[i].getPecas().size() && !alguemJoga; ++j){
                                        if(this.jogadores[i].getUsadas()[j]) continue;
					soma_temp += this.jogadores[i].getPecas().get(j).getValor1()+this.jogadores[i].getPecas().get(j).getValor2();
					if(this.jogadores[i].pode_jogar(j, 0, this) != -1 || 
							this.jogadores[i].pode_jogar(j, 1, this) != -1){
						alguemJoga = true;
					}
				}
				if(soma_temp < soma){
					soma = soma_temp;
					resp = i;
					cancela = false;
				} else if(soma_temp == soma) cancela = true; 
			}
			if(!alguemJoga && !cancela){
				if(resp%2==0) this.placar1++;
				else this.placar2++;
				ans = resp+1;
			} else if(!alguemJoga && cancela) ans = 0;
			else ans = -1;
		}
                if(ans==0) this.vencedor=-1;
		return ans;
	}
	
	public void primeiraJogada() {
                this.extremo_esq=this.extremo_dir=-1;
		if(this.rodada>0) { this.vez = vencedor; return; }
		int maior=-1,vez=-1;
		for(int i = 0; i < 4; ++i) {
			int aux = this.jogadores[i].getMaiorCarroca();
			if(aux > maior) {
				vez=i;
				maior=aux;
			}
		}
		this.vez=vez;
		LinkedList<Peca> pecasPrimeiroJogador = this.jogadores[vez].getPecas();
                boolean[] usadasPrimeiroJogador = this.jogadores[vez].getUsadas();
		boolean ok=true;
		for(int i = 0; i < 6 && ok; ++i) {
			Peca peca = pecasPrimeiroJogador.get(i);
                        boolean b = usadasPrimeiroJogador[i];
                        if(b) continue;
			if(peca.carroca() && peca.getValor1() == maior) {
				this.joga(this.jogadores[vez], i, 0, 0);
				//this.jogadores[vez].joga(i, 0, this, 0);
				ok=false;
			}
		}
	}
	
        public void aumentaRodada() {
            ++this.rodada;
        }
        
	public boolean tocou(Jogador jogador) {
		if(this.vez!=jogador.getId()) return false;
		boolean tocou = (this.tabuleiro.size()!=0);
		for(int i = 0; i < jogador.getPecas().size(); ++i){
                        if(jogador.getUsadas()[i]) continue;
			int valor1 = jogador.getPecas().get(i).getValor1();
			int valor2 = jogador.getPecas().get(i).getValor2();
			if(valor1==this.extremo_esq||valor1==this.extremo_dir ||
					valor2==this.extremo_esq||valor2==this.extremo_dir){
				tocou = false;
			}
		}
		if(tocou) {
                    ++this.rodada;
                    toque=true;
                    this.incrementaVez();
                }
		return tocou;
	}
	
	public boolean getEmAndamento() {
		return this.emAndamento;
	}
        
       public boolean isQuerSair(int ind) {
		return this.jogadores[ind].isQuerSair();
	}
       
       public void setQuerSair(int ind, boolean querSair){
           this.jogadores[ind].setQuerSair(querSair);
       }
}
