package projetocomunicacao.rede;

import java.util.logging.Level;
import java.util.logging.Logger;
import projetocomunicacao.game.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class WriteServidor extends Thread {

    private ArrayList<Sala> outToClient;
    private Transporte protocolo;
    private int id;
    private int salaId;
    private Servidor servidor;
    private Lock lock;
    private Condition cv;

    public WriteServidor(Lock lock, Condition cv, Servidor servidor, Transporte protocolo, int id) throws IOException {
        this.lock = lock;
        this.cv = cv;
        this.protocolo = protocolo;
        this.servidor = servidor;
        this.id=id;
         System.out.println("ficou com id inicial "+this.id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getID() {
        return this.id;
    }
    
    public void setSalaId(int salaId) {
        this.salaId = salaId;
    }
    

    public void run() {
        while (true) {
            this.lock.lock();
            int aux = this.id;
            if (!this.servidor.getMudou()[aux]) {
                try {
                    this.cv.awaitNanos(1000000);
                    //this.cv.notifyAll();
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            } else if (this.servidor.getMudou()[aux]) {
                System.out.println("houve mudanças id "+aux);

                this.outToClient = this.servidor.getSalas();
                System.out.println("recebeu a sala");
                try {
                    //this.socketOut = new ObjectOutputStream(sock.getOutputStream());
                    //this.socketOut.writeObject(this.outToClient);
                    System.out.println("vai mandar a sala pro cliente "+id);
                    this.protocolo.enviar(this.outToClient);
                    System.out.println("mandou a sala pro cliente "+id);
                    //Servidor.mudou[id]=false;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    System.out.println("CRIENTE QUE EU TENTEI MANDAR AGORA JA TA FORA JA");
                    //e.printStackTrace();
                    break;
                }
                this.servidor.getMudou()[aux] = false;
            }

            this.cv.signalAll();
            this.lock.unlock();
        }
    }
}
