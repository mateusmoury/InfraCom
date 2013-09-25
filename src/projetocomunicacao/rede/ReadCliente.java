package projetocomunicacao.rede;
import projetocomunicacao.game.*;

import java.io.IOException;
import java.util.ArrayList;

public class ReadCliente extends Thread {
	private ArrayList<Sala> inFromClient;
	private Transporte protocolo;
	private WriteCliente WC;

	public ReadCliente(WriteCliente WC, Transporte protocolo) throws IOException {
		this.protocolo = protocolo;
		this.WC = WC;
	}

	public void run(){
		while(true){
			try {
				//this.socketIn = new ObjectInputStream(sock.getInputStream());
				//this.inFromClient = (Sala) socketIn.readObject();
				this.inFromClient  = (ArrayList<Sala>) protocolo.receber();
			} catch (IOException e) {
				e.printStackTrace();
				break;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
                        System.out.println("tamanho do array de sala recebido " +inFromClient.size());
                        for(int i = 0; i < inFromClient.size(); ++i) {
                            for(int j = 0; j < inFromClient.get(i).getQuantidadeJogadores(); ++j) {
                                System.out.println("jogador "+j);
                            }
                        }
			if(this.WC.setSalas(this.inFromClient)) {
                            //System.out.println("alterou "+this.WC.getSalas().get(this.getSalaId()));
                            System.out.println("atualizou as salas "+this.WC.getSalaId());
                            this.WC.setAlterou(true);
                            System.out.println("ativou o alterou");
                            if(this.WC.getSalaId()!=-20) System.out.println("Indice inicial " + this.inFromClient.get(this.WC.getSalaId()).getIndInicial());
                        }
                        this.WC.setAlterou2(true);
		}
	}
}