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
            while(true){
              try{
                    this.socketOut = new ObjectOutputStream(sock.getOutputStream());
                     break;
                } catch(Exception e){
                    int p = this.socket.getLocalPort();
                  //  System.out.println("TO TENTANDO ENVIAR PARA + " +p.toString());
                    this.socket.close();
                    this.sock.close();
                    this.socket = new ServerSocket(this.porta);
                     this.sock = new Socket();
                    this.socket.setReuseAddress(true);
                    this.socket.bind(new InetSocketAddress("172.20.4.216", p));
                    this.sock = this.socket.accept();
                }
            }
            this.socketOut.writeObject(object);
	}

	@Override
	public Object receber() throws IOException, ClassNotFoundException {
            while(true){
              try{
                    this.socketIn = new ObjectInputStream(sock.getInputStream());
                     break;
                } catch(Exception e){
                    int p = this.socket.getLocalPort();
                    this.socket.close();
                    this.sock.close();
                    this.socket = new ServerSocket(this.porta);
                    this.sock = new Socket();
                    this.socket.setReuseAddress(true);
                    this.socket.bind(new InetSocketAddress("172.20.4.216", p));
                  //  this.socket.bind(this.sock.getLocalSocketAddress());
                    this.sock = this.socket.accept();
                }
            }
            SocketAddress p = this.socket.getLocalSocketAddress();
            System.out.println("vou tentar receber de " +p.toString());
            return socketIn.readObject();
	}
	
	public void setPortNumber(int PortNumber){}

	public Socket getSocket() {
		return this.sock;
	}
}
