/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocomunicacao.core;

import projetocomunicacao.fachada.FachadaCliente;

public class DesenhaGUI extends Thread {

    private FachadaCliente fachada;
    private Game game;

    public DesenhaGUI(FachadaCliente fachada, Game game) {
        this.fachada = fachada;
        this.game = game;
    }

    public void run() {
        while (true) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (this.fachada.isAlterou()) {
                this.game.eraseContour();
                this.fachada.setAlterou(false);
                System.out.println("Dentro do isAlterou");
                if (this.fachada.isEmAndamento()) {
                    for(int i = 0; i < 4; ++i){
                        if(this.fachada.getQuerSair(i)){
                            this.game.mostraQuitDialog();
                        }
                    }
                    if (this.fachada.mostraTabuleiro().size() > 0) {
                        this.game.desenharTabuleiro();
                    }
                    //this.game.tiraPecas();
                    System.out.println("vai botar nome verde");
                    this.game.nomeVerde();
                    System.out.println("botou nome verde");
                }
                this.game.escreveNomes();
                System.out.println("Escreveu nomes");
                if (this.fachada.qtdJogadores() > 0) {
                    System.out.println("Vai desenhar as peças");
                    this.game.desenharPecas();
                    System.out.println("Desenhou as peças");
                    this.game.outrasPecas();
                    System.out.println("Desenhou outras peças");
                }
                System.out.println("colocar o placar");
                this.game.setarPlacar();
                System.out.println("BOUNDS: " + this.game.last_position_left + " " + this.game.last_position_right);
                if (this.fachada.isEmAndamento()) {
                    System.out.println("vai dar sleep");
                    try {
                        sleep(100);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    if (this.fachada.tocou() && this.fachada.mostraTabuleiro().size() > 0) {
                        System.out.println("entrou no tocou");
                        this.game.mostraToqueDialog();
                        this.fachada.realizaJogada(0, 0, "");
                        System.out.println("realizou jogada");
                        this.fachada.setAlterou(true);
                        System.out.println("mudou alterou");
                        this.game.nomeVerde();
                        System.out.println("botou nome verde");
                    }
                    if (this.fachada.mostraTabuleiro().size() == 0) {
                        System.out.println("ACABOOOOOOOOOOOU");
                        //this.fachada.
                        if (this.fachada.getVencedor() % 2 == this.fachada.getPlayer() % 2) {
                            this.game.mostraGanhou();
                        } else {
                            this.game.mostraPerdeu();
                        }
                        /*
                         * if(this.fachada.getVencedor()%2 ==
                         * this.fachada.getPlayer()%2){
                         * this.game.mostraGanhou(); try { sleep(2750); } catch
                         * (InterruptedException ex) { ex.printStackTrace(); }
                         * this.game.tiraGanhou(); } else {
                         * this.game.mostraPerdeu(); try { sleep(2750); } catch
                         * (InterruptedException ex) { ex.printStackTrace(); }
                         * this.game.tiraPerdeu();
}
                         */
                        this.game.resetBoard();
                        fachada.setAcabou();
                    }
                }
            }
        }
    }
}
