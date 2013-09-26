package projetocomunicacao.rede;

import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import projetocomunicacao.udp.ServidorUDP;

public class ThreadServidor extends Thread {
	//private ServerSocket welcomeSocket; //TODO isso daqui so funciona com TCP. Deve ser usado de alguma forma numa interface? Como?
	//private Socket sock;
	private int id;
	private Servidor servidor;
	private Lock lock;
	private Condition cv;
	private Transporte protocolo; //não tem mais o socket, e sim o protocolo de transporte
        private ReadServidor r;
        private WriteServidor w;
	
	public ThreadServidor(Lock lock, Condition cv, Servidor servidor, int id) throws IOException, InterruptedException {
		this.lock = lock;
		this.cv = cv;
		this.id=id;
		//welcomeSocket = new ServerSocket(3010+id); //TCP only
		//System.out.println("Numero porta " + (3010+id));
                sleep(100);
		this.protocolo = new ServidorTCP(3030);
		this.protocolo.iniciarConexao(); 
		this.servidor = servidor;
                System.out.println("ENVIADO A UMA LISTA DE TAMANHO " + this.servidor.getSalas().size());
                this.protocolo.enviar(this.servidor.getSalas());    
	}

        public ReadServidor getR() {
            return r;
        }
        
	public void run() {
		System.out.println("hi");
		try {
			this.w = new WriteServidor(this.lock, this.cv, this.servidor, this.protocolo, this.id);
			this.r = new ReadServidor(w, this.servidor, this.protocolo);
			r.start();
			w.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
