package projetocomunicacao.rede;

import java.net.UnknownHostException;
import projetocomunicacao.game.*;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.LinkedList;

 public class WriteCliente extends Thread{
	private ArrayList<Sala> salas;
	private int player;
	private Transporte protocolo;
	private boolean temInstrucao;
	private int instrucao;
	private int salaId;
	private String nomeJogador;
	private int time;
	private int lado;
	private int peca;
	private boolean realizou;
	private boolean tentou;
        private boolean alterou;
        private boolean alterou2;
        private String path;
        private int novaSalaId;        
        
	public WriteCliente(Transporte protocolo){
		this.protocolo = protocolo;
		this.temInstrucao = false;
		this.realizou = false;
                this.alterou = false;
                this.alterou2 = false;
                this.salaId=-20;
                this.path="";
	}
        
        public void setNovaSalaId(int id) {
            this.novaSalaId = id;
        }
        
        public ArrayList<Sala> getSalas() {
            return this.salas;
        }
        
        public int getLado() {
            return this.salas.get(salaId).getLado();
        }

        public int getSalaId() {
            return salaId;
        }

        public boolean isAlterou2() {
            return alterou2;
        }

        public void setAlterou2(boolean alterou2) {
            this.alterou2 = alterou2;
        }
        
        public boolean isEmAndamento() {
            return this.salas.get(salaId).getEmAndamento();
        }  
        
        public int getVez() {
            return this.salas.get(salaId).getVez();
        }
        
        public int qtdJogadores(){
            return this.salas.get(salaId).getQuantidadeJogadores();
        }

        public boolean isAlterou() {
            return alterou;
        }

        public void setAlterou(boolean alterou) {
            this.alterou = alterou;
        }
	
	public int getExtremoEsquerda(){
	    return this.salas.get(salaId).getExtremo_esq();
	}
	
	public int getExtremoDireta(){
	    return this.salas.get(salaId).getExtremo_dir();
	}
	
        
	public boolean isRealizou() {
		return this.realizou;
	}
	
	
	public void setRealizou(boolean realizou) {
		this.realizou = realizou;
	}
	
	public boolean getTentou() {
		try {
			sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.tentou;
	}
	
	public void setTentou(boolean tentou) {
		this.tentou = tentou;
	}

	public void setSalaId(int salaId) {
		this.salaId = salaId;
	}


	public void setNomeJogador(String nomeJogador) {
		this.nomeJogador = nomeJogador;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void setInstrucao(int instrucao) {
		this.instrucao = instrucao;
		this.temInstrucao = true;
	}

	public boolean setSalas(ArrayList<Sala> salas) {
                if(this.salaId!=-20 && this.salas!=null && salas.size() > salaId && salas.get(salaId) != null) {
                    int seqReceived = salas.get(salaId).getSeqNumber();
                    System.out.println("recebeu array de salas novo. o numero de sequencia da sala "+salaId+" é "+seqReceived);
                    if(seqReceived>this.salas.get(salaId).getSeqNumber()) {
                        System.out.println("atualizou salas "+salaId);
                        this.salas = salas;
                        return true;
                    } else {
                        System.out.println("nao atualizou salas "+salaId);
                        return false;
                    }
                } else {
                    this.salas = salas;
                    System.out.println("Evitei exceptions (nullpointer e out of bounds");
                    return true;
                }
	}

	public void setLado(int lado) {
		this.lado = lado;
                this.salas.get(salaId).setLado(lado);
	}

	public void setPeca(int peca) {
		this.peca = peca;
	}

	private void envia(){
		try {
                    System.out.println("tamanho do array de sala: "+this.salas.size());
                    if(salaId!=-20) { 
                        this.salas.get(salaId).incrementaSeqNumber();
                        this.salas.get(salaId).aumentaRodada();
                        System.out.println("cliente vai mandar sala "+this.salaId+" com numero de sequencia "+this.salas.get(this.salaId).getSeqNumber());
                        System.out.println("quantidade de jogadores na sala" + this.salas.get(this.salaId).getQuantidadeJogadores());
                        this.alterou=true;
                        this.protocolo.enviar(this.salas.get(this.salaId));
                    } else {
                        System.out.println("salaId 20, mandando ultima sala criada");
                        this.protocolo.enviar(this.salas.get(this.novaSalaId));
                    }
                } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void criaSala(){
		this.salas.add(new Sala(this.novaSalaId));
                this.salas.get(this.novaSalaId).incrementaSeqNumber();
		this.envia();
	}
        
        public boolean podeAdicionar(int time) {
            return this.salas.get(this.salaId).podeAdicionar(this.time);
        }
        
	
	private void adicionaJogador() throws UnknownHostException{
		this.player = this.salas.get(this.salaId).adicionarJogador(new Jogador(this.nomeJogador, InetAddress.getLocalHost().toString().split("/")[1]), this.time);

		this.envia();
	}
	
	public Jogador[] mostrarJogadores(){
		return this.salas.get(salaId).getJogadores();
	}
	
	public LinkedList<Peca> mostrarPecas(){
		return this.salas.get(salaId).getJogadores()[this.player].getPecas();
	}
        
        public boolean[] mostrarUsadas() {
            return this.salas.get(salaId).getJogadores()[this.player].getUsadas();
        }
	
	public boolean tocou(){
		return this.salas.get(salaId).tocou(this.salas.get(salaId).getJogadores()[this.player]);
	}
	
	public boolean naVez(){
		return this.salas.get(salaId).getVez()==this.player;
	}
	
	public LinkedList<Peca> mostrarTabuleiro(){
		return this.salas.get(salaId).getTabuleiro();
	}
	
	public int mostrarPlacar1() {
		return this.salas.get(salaId).getPlacar1();
	}
	
	public int mostrarPlacar2() {
		return this.salas.get(salaId).getPlacar2();
	}
	
	public int getPlayer(){
	    return this.player;
	}
        
        public int getVencedor(){
            return this.salas.get(salaId).getVencedor();
        }
        
        public int getIndInicial(){
            return this.salas.get(salaId).getIndInicial();
        }
	
	public void fazJogada(){
                
            int lado_peca = this.salas.get(salaId).pode_jogar(this.salas.get(salaId).getJogadores()[this.player], this.peca, this.lado);
		System.out.println(lado_peca);
                if(lado_peca>-1){
			this.realizou = true;
			this.salas.get(salaId).joga(this.salas.get(salaId).getJogadores()[this.player], this.peca, this.lado, lado_peca);
                        this.salas.get(salaId).setPath(path);
                        System.out.println("aqui");
			this.envia();
		} else if(lado_peca==-3) {
                    System.out.println("tocou");
                    this.envia();
                }
		this.tentou=true;
	}
        
   
	
	public void incrementaVez() {
		this.salas.get(salaId).incrementaVez();
	}
	
	public boolean acabou() {
		return this.salas.get(salaId).acabouJogo();
	}
	
	public void setAcabou() {
		this.salas.get(salaId).setAcabou();
	}
        
        public boolean acabouPartida() {
            return this.salas.get(salaId).isAcabouPartida();
        }
        
       public void setAcabouPartida() {
           this.salas.get(salaId).setAcabouPartida(false);
       }
        
       public int getRodada() {
	   return this.salas.get(salaId).getRodada();
       }
       
        public String getPath(){
            return this.salas.get(salaId).getPath();
        }
        
        public void aumentaRodada() {
            this.salas.get(salaId).aumentaRodada();
        }
	
        public void setPath(String path) {
            this.path = path;
        }
        
	public void run(){
		while(true){
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(this.temInstrucao){
				if(this.instrucao==0){
					this.criaSala();
				} else if(this.instrucao==1){
                                    try {
                                        this.adicionaJogador();
                                    } catch (UnknownHostException ex) {
                                        ex.printStackTrace();
                                    }
				} else if(this.instrucao==2){
					this.fazJogada();
				} else if(this.instrucao==3){
					this.incrementaVez();
				}
				this.temInstrucao = false;
			}
		}
	}
}