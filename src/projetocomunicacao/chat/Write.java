package projetocomunicacao.chat;

import java.io.*;
import java.util.*;
import java.net.*;
 
public class Write extends Thread{
        private String outToClient;
        private DataOutputStream socketOut;
        private Socket sock;
       
        public Write(Socket sock){
                this.sock = sock;
        }
       
        public void run(){
                while(true){
                        outToClient = new Scanner(System.in).next();
                        try {
                                socketOut = new DataOutputStream(sock.getOutputStream());
                                socketOut.writeBytes(outToClient+'\n');
                        } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                break;
                        }
                }
        }
}
