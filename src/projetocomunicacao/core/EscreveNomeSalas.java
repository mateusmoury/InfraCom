/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocomunicacao.core;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import projetocomunicacao.fachada.FachadaCliente;
import projetocomunicacao.game.Sala;

/**
 *
 * @author mmfrb
 */
public class EscreveNomeSalas extends Thread {
    private TelaInicial telaInicial;
    private FachadaCliente fachada;
    
    public EscreveNomeSalas(TelaInicial telaInicial, FachadaCliente fachada){
        this.telaInicial = telaInicial;
        this.fachada = fachada;
    }
    
    public void run(){
        while(true){
             try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } 
            if(this.fachada.isAlterou2()){
                System.out.println("ENTREI AQUI");
                DefaultListModel model = new DefaultListModel();
                ArrayList<Sala> salas = this.fachada.mostraSalas();
                for(int i = 0; i < salas.size(); ++i) model.addElement(salas.get(i).getId());
                this.telaInicial.salas_list.setModel(model);
                this.fachada.setAlterou2(false);
                
            }
        }
    }
}
