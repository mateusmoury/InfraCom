package projetocomunicacao.rede;

import java.io.IOException;

import projetocomunicacao.udp.ClienteUDP;


public class Cliente {
	private WriteCliente writeCliente;
	private ReadCliente readCliente;
        private String IP;
	public Cliente(String IP) {
                this.IP = IP;
	}
	
	public WriteCliente getWriteCliente() {
		return writeCliente;
	}
	
	public void executa() throws IOException, ClassNotFoundException {
		System.out.println("oi, eu sou um cliente");
		final int portNumber = 3030;
		Transporte protocolo = new ClienteUDP(this.IP,portNumber);
		System.out.println("cliente contata master server");
		protocolo.iniciarConexao();
		int newPort = (Integer) protocolo.receber();
		newPort--;
		protocolo.encerrarConexao();
		System.out.println("EU RECEBI ESSA PORTA DISPONIVEL " + newPort);
		protocolo.setPortNumber(newPort+3030);
		System.out.println("vou me contatar com servidor nessa porta ai que eu recebi");
		protocolo.iniciarConexao();
		this.writeCliente = new WriteCliente(protocolo);
		this.readCliente = new ReadCliente(writeCliente,protocolo);
		this.writeCliente.start();
		this.readCliente.start();
	}
}
