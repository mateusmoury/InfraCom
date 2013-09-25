package projetocomunicacao.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Read extends Thread{
    private String inFromClient;
    private Socket sock;
    private BufferedReader socketIn;
   
    public Read(Socket sock) throws IOException {
            this.sock = sock;
    }
   
    public void run(){
            while(true){
                    try {
                            this.socketIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                            this.inFromClient = socketIn.readLine();
                    } catch (IOException e) {
                            e.printStackTrace();
                            break;
                    }
                    
                    System.out.print(inFromClient + '\n');
                    
            }
    }
}
