/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocomunicacao.core;

import projetocomunicacao.fachada.FachadaServidor;

public class DesenhaGUIServidor extends Thread {
    private FachadaServidor fachada;
    private GUIServidor gs;
    
    public DesenhaGUIServidor(FachadaServidor fachada, GUIServidor gs){
        this.fachada = fachada;
        this.gs = gs;
    }
    
    public void run(){
        while(true){
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(this.fachada.isAlterou()){
                this.gs.desenhaComboBox();
                this.gs.escreveJogadores();
                this.fachada.setAlterou(false);
            }
        }
    }
}
