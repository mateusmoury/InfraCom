/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocomunicacao.core;

import java.net.InetAddress;
import projetocomunicacao.rede.Servidor;

/**
 *
 * @author gpp
 */
public class Pingador extends Thread{
    Servidor servidor;
    
    public Pingador(Servidor servidor){
       this.servidor = servidor;
    }
    
    private boolean pingar(String host) {
      try {
        if (InetAddress.getByName(host).isReachable(3000)){
          System.out.println("Ping OK: " + host);
          return true;
        }
        else{
          System.out.println("Ping FALHOU: " + host);
          return false;
        }
      } catch (Exception e) {
        System.err.println("Ping FALHOU: " + host + " - " + e);
        return false;
      }
    }
    
    public void run(){
        while(true){
             try {
                sleep(10000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            boolean ok = false;
            int cnt = 0;
            System.out.println("ENTREI AQUI");
            if(this.servidor==null) System.out.println("NEM A PAU");
            if(this.servidor.getSalas().size()!=0){
                for(int i = 0; i < this.servidor.getSalas().size(); ++i){
                    for(int j = 0; j < 4; ++j){
                        System.out.println(this.servidor.getSalas().get(i).getQuantidadeJogadores());
                        if(this.servidor.getSalas().get(i).getJogadores()[j]!=null && !pingar(this.servidor.getSalas().get(i).getJogadores()[j].getIP())){
                           if(!this.servidor.isCaiu()[i]) {
                               ok = true;
                               this.servidor.getSalas().get(i).incrementaSeqNumber();
                           }
                           ++cnt;
                           this.servidor.isCaiu()[i] = true;
                           this.servidor.getSalas().get(i).getJogadores()[j].setStatus(false);
                        }
                    }
                    if(cnt==0&&this.servidor.isCaiu()[i]) this.servidor.isCaiu()[i] = false;
                    cnt = 0;
                }
            }
            if(ok){
                for(int i = 0; i < 100; ++i){
                    this.servidor.getMudou()[i] = true;
                }
            }
        }
    }
}
