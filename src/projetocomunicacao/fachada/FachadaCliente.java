package projetocomunicacao.fachada;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import projetocomunicacao.rede.*;
import projetocomunicacao.game.*;

public class FachadaCliente {
	private Cliente cliente;
	
	public FachadaCliente(Cliente cliente) throws IOException, ClassNotFoundException{
		this.cliente = cliente;
		this.cliente.executa();
	}
	
	public void criaSala(int id){
		//this.cliente.getWriteCliente().setSalaId(id);
                this.cliente.getWriteCliente().setNovaSalaId(id);
		this.cliente.getWriteCliente().setInstrucao(0);
	}
	
        public boolean podeAdicionar(int time, int id) {
            this.cliente.getWriteCliente().setSalaId(id);
            this.cliente.getWriteCliente().setTime(time);
            if(!this.cliente.getWriteCliente().podeAdicionar(time)) {
                this.cliente.getWriteCliente().setSalaId(-20);
                return false;
            }
            return true;
        }
        
	public void adicionaJogador(String nomeJogador, int time, int id){
                this.cliente.getWriteCliente().setSalaId(id);
		this.cliente.getWriteCliente().setNomeJogador(nomeJogador);
		this.cliente.getWriteCliente().setTime(time);
		this.cliente.getWriteCliente().setInstrucao(1);
	}
        
       
	public boolean realizaJogada(int lado, int peca, String path){
		this.cliente.getWriteCliente().setLado(lado);
		this.cliente.getWriteCliente().setPeca(peca);
		this.cliente.getWriteCliente().setInstrucao(2);
                this.cliente.getWriteCliente().setPath(path);
		//ta chegando aqui bem antes de jogar de fato
		while(!this.cliente.getWriteCliente().getTentou());
		this.cliente.getWriteCliente().setTentou(false);
		if(this.cliente.getWriteCliente().isRealizou()){
			this.cliente.getWriteCliente().setRealizou(false);
                        //this.cliente.getWriteCliente().setPath(path);
			return true;
		}
		return false;
	}
	
	public int getExtremoEsquerda(){
	    return this.cliente.getWriteCliente().getExtremoEsquerda();
	}
	
	public int getExtremoDireita(){
	    return this.cliente.getWriteCliente().getExtremoDireta();
	}

	public Cliente getCliente() {
	    return cliente;
	}
	
        public boolean isAlterou(){
            return this.cliente.getWriteCliente().isAlterou();
        }
        
        public boolean isAlterou2(){
            return this.cliente.getWriteCliente().isAlterou2();
        }
        
        public void setAlterou(boolean alterou){
               this.cliente.getWriteCliente().setAlterou(alterou);
        }
        
         public void setAlterou2(boolean alterou){
               this.cliente.getWriteCliente().setAlterou2(alterou);
        }
        
        public boolean isEmAndamento(){
            return this.cliente.getWriteCliente().isEmAndamento();
        }
        
        public int qtdJogadores(){
            return this.cliente.getWriteCliente().qtdJogadores();
        }
        
	public int mostraPlacar1() {
		return this.cliente.getWriteCliente().mostrarPlacar1(); 
	}
	
	public int mostraPlacar2() {
		return this.cliente.getWriteCliente().mostrarPlacar2(); 
	}
	
	public Jogador[] mostraJogadores(){
		return this.cliente.getWriteCliente().mostrarJogadores();
	}
	
	public LinkedList<Peca> mostraPecas(){
		return this.cliente.getWriteCliente().mostrarPecas();
	}
	
        public boolean[] mostraUsadas() {
            return this.cliente.getWriteCliente().mostrarUsadas();
        }
        
	public LinkedList<Peca> mostraTabuleiro(){
		return this.cliente.getWriteCliente().mostrarTabuleiro();
	}
	
	public boolean tocou(){
		return this.cliente.getWriteCliente().tocou();
	}
	
	public boolean naVez(){
		return this.cliente.getWriteCliente().naVez();
	}
	
	public boolean acabou() {
		return this.cliente.getWriteCliente().acabou();
	}
	
	public int getRodada() {
	    return this.cliente.getWriteCliente().getRodada();
	}
        
        public void aumentaRodada() {
            this.cliente.getWriteCliente().aumentaRodada();
        }
        
        public boolean acabouPartida() {
            return this.cliente.getWriteCliente().acabouPartida();
        }
	
	public void setAcabou() {
		this.cliente.getWriteCliente().setAcabou();
	}
	
	public int getPlayer(){
	    return this.cliente.getWriteCliente().getPlayer();
	}
        
        public int getLado(){
            return this.cliente.getWriteCliente().getLado();
        }
        
        public String getPath(){
            return this.cliente.getWriteCliente().getPath();
        }
        
        public int getVez() {
            return this.cliente.getWriteCliente().getVez();
        }
        
        public int getVencedor(){
            return this.cliente.getWriteCliente().getVencedor();
        }
        
        public ArrayList<Sala> mostraSalas(){
            return this.cliente.getWriteCliente().getSalas();
        }
        
        public int getIndInicial(){
            return this.cliente.getWriteCliente().getIndInicial();
        }
}
