package projetocomunicacao.rede;

import java.io.IOException;

public interface Transporte {
	void iniciarConexao() throws IOException;
	
	void encerrarConexao() throws IOException;
	
	void enviar(Object sala) throws IOException;
	
	Object receber() throws IOException, ClassNotFoundException;
	
	void setPortNumber(int PortNumber);
	//Socket getSocket();
}
