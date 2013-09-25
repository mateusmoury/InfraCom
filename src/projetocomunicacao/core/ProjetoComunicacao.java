/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocomunicacao.core;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import projetocomunicacao.rede.Cliente;
import projetocomunicacao.fachada.FachadaCliente;

/**
 *
 * @author Vinícius
 */
public class ProjetoComunicacao {

    /**
     * @param args the command line arguments
     */
    
    public static void WelcomeWindow() throws InterruptedException{
        WelcomeFrame wf = new WelcomeFrame();
	wf.setLocationRelativeTo(null);
	try{
            InputStream imgStream = wf.getClass().getResourceAsStream("/projetocomunicacao/resources/logotransparente2.png");
            BufferedImage bi = ImageIO.read(imgStream);
            ImageIcon myImg = new ImageIcon(bi);
            wf.setIconImage(myImg.getImage());
        } catch(Exception e){
            System.out.println(e);
        }
        wf.setVisible(true);
        Thread t = new Thread();
        t.start();
        t.sleep(3000);
        wf.setVisible(false);
    }
    
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        // TODO code application logic here
        /*Cliente cliente = new Cliente(0); //  <-- peixoto comentou daqui
        FachadaCliente fachada = new FachadaCliente(cliente);
	Game gui = new Game(fachada);
        DesenhaGUI desenha = new DesenhaGUI(fachada, gui);
        desenha.start();
	String inst;
	Scanner in = new Scanner(System.in);
	while(true) {
	    inst = in.next();
	    if(inst.equalsIgnoreCase("cria")) {
		fachada.criaSala(0);
	    } else if(inst.equalsIgnoreCase("adicionar")) {
		inst = in.next();
		int time = in.nextInt();
		fachada.adicionaJogador(inst, time);
	    } else if(inst.equalsIgnoreCase("fim")) {
		break;
	    } else {
		System.out.println("Comando invalido");
	    }
	}*/ //peixoto comentou até aqui<--
        
        //gui.setVisible(true);  <-- peixoto comentou isso
        //fachada.mostraPecas(); //<-- peixoto comentou isso
        WelcomeWindow();
        TelaInicial gui = new TelaInicial();
	gui.setLocationRelativeTo(null);
        gui.setVisible(true);
        try{
            InputStream imgStream = gui.getClass().getResourceAsStream("/projetocomunicacao/resources/logotransparente2.png");
            BufferedImage bi = ImageIO.read(imgStream);
            ImageIcon myImg = new ImageIcon(bi);
            gui.setIconImage(myImg.getImage());
        } catch(Exception e){
            System.out.println(e);
        }
      
    }
}
