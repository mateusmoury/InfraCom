package projetocomunicacao.chat;

import java.io.IOException;
import java.net.UnknownHostException;

public class Cliente1 {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Cliente cliente = new Cliente(0);
		cliente.executa();
	}
}
