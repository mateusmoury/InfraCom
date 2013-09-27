package projetocomunicacao.rede;
import projetocomunicacao.game.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Servidor {
	private ArrayList<Sala> salas;
	private boolean[] mudou;
        private boolean[] alocado;
	ThreadServidor[] threads;
	private Lock lock;
	private Condition cv;
        private boolean[] caiu;
        private boolean alterou;
        static public int lastPortNumber;
	
	public Servidor(Lock lock, Condition cv){
		this.lock = lock;
		this.cv = cv;
		this.salas=new ArrayList<Sala>();
                //this.salas.add(new Sala(0));
		this.mudou = new boolean[100];
                this.alocado = new boolean[100];
		this.threads = new ThreadServidor[1000];
                this.caiu = new boolean[25];
                this.lastPortNumber = 3030;
	}

        public boolean isAlterou() {
            return alterou;
        }

        public void setAlterou(boolean alterou) {
            this.alterou = alterou;
        }     

        public boolean[] isCaiu() {
            return this.caiu;
        }

        public boolean[] getAlocado() {
            return alocado;
        }
        
	public ArrayList<Sala> getSalas() {
		return this.salas;
	}
        
        public void setSalas(int salaID, Sala sala) {
            salas.set(salaID, sala);
        }
        
	public void setSalas(ArrayList<Sala> salas) {
		this.salas = salas;
	}
	public boolean[] getMudou() {
		return this.mudou;
	}

	public ThreadServidor[] getThreads() {
		return threads;
	}
	public void setThreads(ThreadServidor[] threads) {
		this.threads = threads;
	}
	public void inicia() throws IOException, InterruptedException{
		for(int i = 0; ; ++i) {
			this.threads[i] = new ThreadServidor(this.lock, this.cv, this,i);
			this.threads[i].start();
		}
	}
}
