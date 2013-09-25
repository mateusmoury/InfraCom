package projetocomunicacao.rede;

import projetocomunicacao.game.*;

import java.io.IOException;
import java.util.ArrayList;

public class ReadServidor extends Thread {
	private Sala inFromClient;
	private Transporte protocolo;
	private Servidor servidor;
        private WriteServidor WS;
	public ReadServidor(WriteServidor WS, Servidor servidor, Transporte protocolo) throws IOException {
		this.WS=WS;
                this.protocolo = protocolo;
		this.servidor = servidor;
	}

	public void run(){
		while(true){
			try {
				//this.socketIn = new ObjectInputStream(sock.getInputStream());;
				//this.inFromClient = (Sala) socketIn.readObject();
				//System.out.println("ewo");
				this.inFromClient = (Sala) protocolo.receber();
				//System.out.println("opa");
			} catch (IOException e) {
				e.printStackTrace();
				break;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.print("Recebi do cliente "+this.inFromClient + '\n');
			
                        int salaID = this.inFromClient.getId();
                       System.out.println("id da sala recebida " + salaID);
                        //cria sala
                        if(salaID == this.servidor.getSalas().size()) { //sala nova. entao eu crio
                            System.out.println("adicionar no arraylist");
                            this.servidor.getSalas().add(inFromClient);
                        } else { //muda a sala
                            System.out.println("mudando a sala");
                            this.servidor.setSalas(salaID, inFromClient);
                        }
                        
                        
                        
						
			boolean emAndamento = this.servidor.getSalas().get(salaID).getEmAndamento();
                        int quantidadeJogadores = this.servidor.getSalas().get(salaID).getQuantidadeJogadores();
			if((quantidadeJogadores==4&&!emAndamento) || (emAndamento && this.servidor.getSalas().get(salaID).isAcabouPartida())){
                                System.out.println("Distribuir as peças");
                                this.servidor.getSalas().get(salaID).reiniciarPartida();
				this.servidor.getSalas().get(salaID).setAcabouPartida(false);
				this.servidor.getSalas().get(salaID).distribuiPecas();
				this.servidor.getSalas().get(salaID).primeiraJogada();
                                this.servidor.getSalas().get(salaID).incrementaSeqNumber();
			} else if(emAndamento && this.servidor.getSalas().get(salaID).isAcabouJogo()){
                                this.servidor.getSalas().get(salaID).reiniciarJogo();
                                 System.out.println("Acabou jogo. Start again");
				this.servidor.getSalas().get(salaID).setAcabouJogo(false);
				this.servidor.getSalas().get(salaID).setAcabouPartida(false);
				this.servidor.getSalas().get(salaID).distribuiPecas();
				this.servidor.getSalas().get(salaID).primeiraJogada();
                                this.servidor.getSalas().get(salaID).incrementaSeqNumber();
			}
       
			
                        System.out.println("Servidor recebeu sala " + salaID);                        
			if(this.servidor.getSalas().get(salaID).getQuantidadeJogadores()==4){
				for(int i = 0; i < 4; ++i){
					Jogador act = this.inFromClient.getJogadores()[i];
					for(int j = 0; j < act.getPecas().size(); ++j){
                                                if(act.getUsadas()[j]) continue;
						System.out.print(act.getPecas().get(j).getValor1());
						System.out.print(act.getPecas().get(j).getValor2());
						System.out.print(" ");
					}
					System.out.println(this.inFromClient.getJogadores()[i].getNome());
				}
				System.out.println("VEZ DE " + this.inFromClient.getJogadores()[this.inFromClient.getVez()].getNome());
			}
			for(Peca peca : this.servidor.getSalas().get(salaID).getTabuleiro()) {
                            System.out.print(""+peca.getValor1()+""+peca.getValor2()+" ");
                        }
                        System.out.println("vai setar o array");
			for(int i = 0; i < 100; ++i){
				this.servidor.getMudou()[i] = true;
			}
                        System.out.println("setou o array");
                        
                        
		}
	}
}