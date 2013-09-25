package projetocomunicacao.chat;
 
import java.io.IOException;
import java.net.*;
 
public class Cliente{
	
	private int id;
	
	public Cliente(int id) {
		this.id=id;
	}
	
	public void executa() throws UnknownHostException, IOException {
		Socket sock = new Socket("192.168.1.3",3010+id);
		Read r = new Read(sock);
        Write w = new Write(sock);
        r.start();
        w.start();
	}
}