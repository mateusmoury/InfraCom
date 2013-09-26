/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocomunicacao.fachada;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import projetocomunicacao.core.PingadorServidor;
import projetocomunicacao.game.Jogador;
import projetocomunicacao.game.Sala;
import projetocomunicacao.rede.MasterServer;
import projetocomunicacao.rede.Servidor;

public class FachadaServidor {
    private MasterServer ms;
    private Lock lock;
    private Condition cv;
    private Servidor servidor;
    private PingadorServidor pingador;
    
    public FachadaServidor() throws IOException{
        this.ms = new MasterServer();
        this.lock = new ReentrantLock();
        this.cv = this.lock.newCondition();
        this.servidor = new Servidor(this.lock, this.cv);
        this.pingador = new PingadorServidor(servidor);
        System.out.println("acabei tudo que eu tinha pra fazer");
    }
    
    public void executa() throws IOException, InterruptedException{
        ms.start();
        pingador.start();
        this.servidor.inicia();
    }
    
    public boolean isAlterou(){
       return this.servidor.isAlterou();
    }
    
    public void setAlterou(boolean alterou){
        this.servidor.setAlterou(alterou);
    }
    
    public ArrayList<Sala> getSalas(){
        return this.servidor.getSalas();
    }
    
    public Jogador[] mostraJogadores(int ind){
        return this.servidor.getSalas().get(ind).getJogadores();
    }
}
