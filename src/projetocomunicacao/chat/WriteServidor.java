package projetocomunicacao.chat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class WriteServidor extends Thread{
	private String outToClient;
	private DataOutputStream socketOut;
	private Socket sock;
	private int id;
	private Servidor servidor;
	private Lock lock;
	private Condition cv;
	
	public WriteServidor(Lock lock, Condition cv, Servidor servidor, Socket sock, int id) {
		this.lock = lock;
		this.cv = cv;
		this.sock = sock;
		this.id=id;
		this.servidor = servidor;
	}

	public void run() {
		while(true) {
			this.lock.lock();
			if(!this.servidor.getMudou()[id])
				try {
					this.cv.awaitNanos(1000);
					//this.cv.notifyAll();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			if(this.servidor.getMudou()[id]) {
				System.out.println("houve mudanças");
				this.outToClient = this.servidor.getStr();
				try {
					this.socketOut = new DataOutputStream(sock.getOutputStream());
					this.socketOut.writeBytes(outToClient+'\n');
					//Servidor.mudou[id]=false;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					break;
				}
				this.servidor.getMudou()[id]=false;
			}
			this.cv.signalAll();
			this.lock.unlock();
		}
	}
}
