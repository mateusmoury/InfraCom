package projetocomunicacao.rede;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

public interface Transporte {
	void iniciarConexao() throws IOException, ConnectException;
	
	void encerrarConexao() throws IOException;
	
	void enviar(Object sala) throws IOException;
	
	Object receber() throws IOException, ClassNotFoundException;
	
	void setPortNumber(int PortNumber);
	//Socket getSocket();

        public Socket getSocket();
}
