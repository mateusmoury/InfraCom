package projetocomunicacao.chat;

import java.io.IOException;
import java.net.UnknownHostException;

public class Cliente2 {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Cliente cliente = new Cliente(1);
		cliente.executa();
	}
}
