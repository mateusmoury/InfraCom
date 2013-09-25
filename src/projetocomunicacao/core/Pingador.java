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
    
    private void pingar(String host) {
      try {
        if (InetAddress.getByName(host).isReachable(3000))
          System.out.println("Ping OK: " + host);
        else
          System.out.println("Ping FALHOU: " + host);
      } catch (Exception e) {
        System.err.println("Ping FALHOU: " + host + " - " + e);
      }
    }
    
    public void run(){
        while(true){
             try {
                sleep(10000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println("ENTREI AQUI");
            if(this.servidor==null) System.out.println("NEM A PAU");
            if(this.servidor.getSalas().size()!=0){
                for(int i = 0; i < this.servidor.getSalas().size(); ++i){
                    for(int j = 0; j < 4; ++j){
                        System.out.println(this.servidor.getSalas().get(i).getQuantidadeJogadores());
                        if(this.servidor.getSalas().get(i).getJogadores()[j]!=null){
                            System.out.println("--->" + j);
                            pingar(this.servidor.getSalas().get(i).getJogadores()[j].getIP());
                        }
                    }
                }
            }
        }
    }
}
