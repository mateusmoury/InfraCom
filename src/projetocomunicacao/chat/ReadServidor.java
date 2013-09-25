package projetocomunicacao.chat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class ReadServidor extends Thread{
	private String inFromClient;
	private Socket sock;
	private BufferedReader socketIn;
	private int id;
	private Servidor servidor;

	public ReadServidor(Servidor servidor, Socket sock, int id) throws IOException {
		this.sock = sock;
		this.id = id;
		this.servidor = servidor;
	}

	public void run(){
		while(true){
			try {
				this.socketIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				this.inFromClient = socketIn.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}

			System.out.print("Recebi do cliente "+inFromClient + '\n');
			
			this.servidor.setStr(inFromClient);
			this.servidor.getMudou()[1-id] = true;
		}
	}
}
