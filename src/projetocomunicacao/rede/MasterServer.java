package projetocomunicacao.rede;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

import projetocomunicacao.udp.ServidorUDP;

public class MasterServer extends Thread {
	final int portNumber = 3030;
	Transporte protocolo;
	
	public MasterServer() throws IOException{
                if(TipoDoProtocolo.TCP) {
                    this.protocolo = new ServidorTCP(this.portNumber);
                } else {
                    System.out.println("vou criar novo");
                    this.protocolo = new ServidorUDP(this.portNumber);
                }
	}
	
	public void run(){
		while(true){
			try {
				this.protocolo.iniciarConexao();
			} catch (IOException e) {
				e.printStackTrace();
			}
			int id = 1;
			
                        if(TipoDoProtocolo.TCP) {
                            ServerSocket aux;
                            while(true){
                                    try {
                                            aux = new ServerSocket(this.portNumber+id);
                                            System.out.println("PORTA DISPONIVEL EH " + (this.portNumber+id));
                                            break;
                                    } catch (Exception e) {
                                            ++id;
                                    }
                            }
                            try {
                                    aux.close();
                            } catch (Exception e) {
                                    System.out.println("NAO CONSEGUIU ENCERRAR CONEXAO");
                            }
                            try {
                                    this.protocolo.enviar(id);
                                    System.out.println("ENVIANDO " + id);
                                    //this.protocolo.encerrarConexao();
                            } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                            }
                         } else {
                            DatagramSocket aux;
                            while(true){
                                    try {
                                            aux = new DatagramSocket(this.portNumber+id);
                                            System.out.println("PORTA DISPONIVEL EH " + (this.portNumber+id));
                                            break;
                                    } catch (Exception e) {
                                            ++id;
                                    }
                            }
                            try {
                                    aux.close();
                            } catch (Exception e) {
                                    System.out.println("NAO CONSEGUIU ENCERRAR CONEXAO");
                            }
                            try {
                                    this.protocolo.enviar(id);
                                    System.out.println("ENVIANDO " + id);
                                    //this.protocolo.encerrarConexao();
                            } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                            }
                        }
		}
	}
}
