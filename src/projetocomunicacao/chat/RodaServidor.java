package projetocomunicacao.chat;

import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RodaServidor {
	public static void main(String[] args) throws IOException {
		Lock lock = new ReentrantLock();
		Condition cv = lock.newCondition();
		Servidor servidor = new Servidor(lock, cv);
		servidor.inicia();
	}
}
