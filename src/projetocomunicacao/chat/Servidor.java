package projetocomunicacao.chat;

import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Servidor {
	
	private  String str;
	private boolean[] mudou;
	ThreadServidor[] threads;
	private Lock lock;
	private Condition cv;
	public Servidor(Lock lock, Condition cv){
		this.lock = lock;
		this.cv = cv;
		str="";
		mudou=new boolean[2];
		for(int i = 0; i < 2; ++i) this.mudou[i]=true;
		this.threads = new ThreadServidor[2];
	}
	
	public String getStr() {
		return this.str;
	}


	public void setStr(String str) {
		this.str = str;
	}

	public boolean[] getMudou() {
		return this.mudou;
	}


	public void setMudou(boolean[] mudou) {
		this.mudou = mudou;
	}
	
	public void inicia() throws IOException{
		for(int i = 0; i < 2; ++i) {
			this.threads[i] = new ThreadServidor(this.lock, this.cv, this, i);
			this.threads[i].start();
		}
	}
}
