/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocomunicacao.core;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PingadorCliente extends Thread {
    private Socket socket;
    private String hostIP;
    private boolean tavaOn;
    private int porta;
    
    private boolean pingar(String host) {
      try {
        if (InetAddress.getByName(host).isReachable(3000)){
          System.out.println("Ping CLIENTE PRA SERVIDOR OK: " + host);
          return true;
        }
        else{
          System.out.println("Ping CLIENTE PRA SERVIDOR FALHOU: " + host);
          return false;
        }
      } catch (Exception e) {
        System.err.println("Ping FALHOU: " + host + " - " + e);
        return false;
      }
    }
    
    public PingadorCliente(Socket socket, String hostIP, int porta){
        this.hostIP = hostIP;
        this.socket = socket;
        this.porta = porta;
        this.tavaOn = true;
    }
    
    public void run(){
        while(true){
            try {
                sleep(5000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            if(pingar(this.hostIP)){
                if(!this.tavaOn){
                    System.out.println("EH AGORA!!! ABRE ESSA PORRA FILHO DA PUTA!!");
                    try {
                        this.socket.close();
                        try {
                            sleep(2000);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        while(true){
                            try{
                                this.socket = new Socket(this.hostIP, this.porta+100);
                                 System.out.println("CHUPA PORRA, ABRI!!!!");
                                 break;
                            } catch (Exception e){
                                   System.out.println("AINDA TAVA FECHADO");
                            }
                        }
                    } catch (UnknownHostException ex) {
                        Logger.getLogger(PingadorCliente.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(PingadorCliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                this.tavaOn = true;
            } else {
                this.tavaOn = false;
            }
        }
    }
}
