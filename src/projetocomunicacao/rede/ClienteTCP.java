package projetocomunicacao.rede;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClienteTCP implements Transporte {
	private Socket sock;
	private String ip;
	private ObjectOutputStream socketOut;
	private ObjectInputStream socketIn;
	private int portNumber;
	public ClienteTCP(String IPServidor, int port) {
		this.ip = IPServidor;
		this.portNumber = port;
	}
	
	@Override
	public void iniciarConexao() throws UnknownHostException, IOException{
		this.sock = new Socket(this.ip, this.portNumber);	
	}

	@Override
	public void encerrarConexao() throws IOException {
		this.sock.close();
	}

	@Override
	public void enviar(Object object) throws IOException {
		this.socketOut = new ObjectOutputStream(sock.getOutputStream());
		this.socketOut.writeObject(object);
	}

	@Override
	public Object receber() throws IOException, ClassNotFoundException {
		this.socketIn = new ObjectInputStream(sock.getInputStream());
		return socketIn.readObject();
                
	}

	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}
	
	

	/*@Override
	public Socket getSocket() {
		return this.sock;
	}*/
}
