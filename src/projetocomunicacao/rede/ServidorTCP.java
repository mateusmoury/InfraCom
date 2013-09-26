package projetocomunicacao.rede;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class ServidorTCP implements Transporte {
	private ServerSocket socket;
	private Socket sock;
	private ObjectOutputStream socketOut;
	private ObjectInputStream socketIn;
        private int porta;

	
	public ServidorTCP(int portNumber){
		int id = 0;
		while(true){
			try{
				this.socket = new ServerSocket(portNumber+id);
				System.out.println("ABRINDO PORTA " + (portNumber+id));
                                this.porta = portNumber+id;
				break;
			} catch(Exception e){
				++id;
			}
		}
	}

	@Override
	public void iniciarConexao() throws IOException {
		System.out.println("OI");
		this.sock = this.socket.accept();
	}

	@Override
	public void encerrarConexao() throws IOException {
		this.socket.close();
	}

	@Override
	public void enviar(Object object) throws IOException { 
            try{
            this.socketOut = new ObjectOutputStream(sock.getOutputStream());
            this.socketOut.writeObject(object);
            } catch(Exception e) {
                this.socket.close();
                this.socket = new ServerSocket(this.porta);
                this.sock = this.socket.accept();
                this.socketOut = new ObjectOutputStream(sock.getOutputStream());
                this.socketOut.writeObject(object);
            }
	}

	@Override
	public Object receber() throws IOException, ClassNotFoundException {
            this.socketIn = new ObjectInputStream(sock.getInputStream()); 
            return socketIn.readObject();
	}
	
	public void setPortNumber(int PortNumber){}

	public Socket getSocket() {
		return this.sock;
	}
}
