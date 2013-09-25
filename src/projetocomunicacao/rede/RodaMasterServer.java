package projetocomunicacao.rede;

import java.io.IOException;

public class RodaMasterServer {
	public static void main(String[] args) throws IOException {
		MasterServer ms = new MasterServer();
		ms.start();
	}
}
