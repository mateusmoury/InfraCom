package projetocomunicacao.rede;

import projetocomunicacao.game.Sala;

import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import projetocomunicacao.core.Pingador;

public class RodaServidor {
	public static void main(String[] args) throws IOException, InterruptedException {
		Lock lock = new ReentrantLock();
		Condition cv = lock.newCondition();
		Servidor servidor = new Servidor(lock,cv);
                Pingador pingador = new Pingador(servidor);
                pingador.start();
		servidor.inicia();
	}
}
