package projetocomunicacao.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ThreadServidor extends Thread {
	private ServerSocket welcomeSocket; //isso daqui so funciona com TCP. Deve ser usado de alguma forma numa interface?
	private Socket sock;
	private int id;
	private Servidor servidor;
	private Lock lock;
	private Condition cv;
	
	public ThreadServidor(Lock lock, Condition cv, Servidor servidor, int id) throws IOException {
		this.lock = lock;
		this.cv = cv;
		this.id=id;
		welcomeSocket = new ServerSocket(3010+id);
		sock = welcomeSocket.accept();
		this.servidor = servidor;
	}
	
	public void run() {
		System.out.println("hi");
		try {
			ReadServidor r = new ReadServidor(this.servidor, this.sock,this.id);
			WriteServidor w = new WriteServidor(this.lock, this.cv, this.servidor, this.sock,this.id);
			r.start();
			w.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
