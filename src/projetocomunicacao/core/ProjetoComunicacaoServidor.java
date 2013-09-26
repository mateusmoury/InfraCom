/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocomunicacao.core;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import projetocomunicacao.fachada.FachadaServidor;

/**
 *
 * @author gpp
 */
public class ProjetoComunicacaoServidor {
    
     public static void main(String[] args) throws IOException, InterruptedException{
         GUIServidor gs = new GUIServidor();
         FachadaServidor fachada = new FachadaServidor();
         gs.setFachada(fachada);
         fachada.executaMasterServer();
         DesenhaGUIServidor dgs = new DesenhaGUIServidor(fachada, gs);
         dgs.start();
         gs.setLocationRelativeTo(null);
         gs.setVisible(true);
         try{
            InputStream imgStream = gs.getClass().getResourceAsStream("/projetocomunicacao/resources/logotransparente2.png");
            BufferedImage bi = ImageIO.read(imgStream);
            ImageIcon myImg = new ImageIcon(bi);
            gs.setIconImage(myImg.getImage());
        } catch(Exception e){
            System.out.println(e);
        }
         System.out.println("owoewoeowoew");
         fachada.executa();
     }
}
