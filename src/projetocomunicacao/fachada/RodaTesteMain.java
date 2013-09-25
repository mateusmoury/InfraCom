package projetocomunicacao.fachada;
import projetocomunicacao.game.*;
import projetocomunicacao.rede.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class RodaTesteMain {

		Cliente cliente;
		Scanner in;
		FachadaCliente fachada;
		RodaTesteMain() throws IOException, ClassNotFoundException {
			this.in = new Scanner(System.in);
			Cliente cliente = new Cliente("localhost");
			this.fachada = new FachadaCliente(cliente);
		}
		
		void executa() {
			while(true){
				String instruction = in.next();
				if(instruction.equalsIgnoreCase("criar")){
					System.out.println("criar sala");
					int id = in.nextInt();
					fachada.criaSala(id);
				}
				else if(instruction.equalsIgnoreCase("adicionar")){
					System.out.println("adicionar jogador");
					String nome = in.next();
					int time = in.nextInt();
					fachada.adicionaJogador(nome, time, 1);
				}
				else if(instruction.equalsIgnoreCase("tabuleiro")){
					System.out.println("ver tabuleiro");
					LinkedList<Peca> tabuleiro = fachada.mostraTabuleiro();
					for(int i = 0; i < tabuleiro.size(); ++i){
						System.out.print(tabuleiro.get(i).getValor1()+""+tabuleiro.get(i).getValor2());
						System.out.print(" ");
					}
					System.out.println();
				} else if(instruction.equalsIgnoreCase("pecas")){
					System.out.println("ver pecas");
					LinkedList<Peca> pecas = fachada.mostraPecas();
                                        boolean[] usadas = fachada.mostraUsadas();
					for(int i = 0; i < pecas.size(); ++i){
                                                if(usadas[i]) continue;
						System.out.print(pecas.get(i).getValor1()+""+pecas.get(i).getValor2());
						System.out.print(" ");
					}
					System.out.println();
				} else if(instruction.equalsIgnoreCase("jogar")){
					System.out.println("jogar");
					System.out.println("tabuleiro:");
					LinkedList<Peca> tabuleiro = fachada.mostraTabuleiro();
					for(int i = 0; i < tabuleiro.size(); ++i){
						System.out.print(tabuleiro.get(i).getValor1()+""+tabuleiro.get(i).getValor2());
						System.out.print(" ");
					}
					System.out.println();
					System.out.println("Pecas:");
					LinkedList<Peca> pecas = fachada.mostraPecas();
					for(int i = 0; i < pecas.size(); ++i){
						System.out.print(pecas.get(i).getValor1()+""+pecas.get(i).getValor2());
						System.out.print(" ");
					}
					System.out.println();
					if (fachada.tocou()) {
						System.out.println("Voce tocou muahah!");
						fachada.realizaJogada(0, 0, "");
					} else {
						int peca = in.nextInt();
						int lado = in.nextInt();
						boolean b = fachada.realizaJogada(lado, peca, "");
						if (b) System.out.println("conseguiu jogar");
						else System.out.println("nao conseguiu jogar");
					}
				} else if(instruction.equalsIgnoreCase("jogadores")) {
					System.out.println("jogadores");
					Jogador[] jogadores = fachada.mostraJogadores();
					 for(Jogador jogador : jogadores) {
						if(jogador != null) System.out.println(jogador.getNome());
					}
				} else if(instruction.equalsIgnoreCase("mostrarPlacar")) {
					System.out.printf("Time 0: %d x %d Time 1\n",fachada.mostraPlacar1(),fachada.mostraPlacar2());
				} else if(instruction.equalsIgnoreCase("fim")){					
					break;
				} else if(instruction.equalsIgnoreCase("acabou?")){
					System.out.println(fachada.acabou());
					fachada.setAcabou();
				}else {
					System.out.println("comando invalido");					
				}
			} 
			in.close();
		}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		RodaTesteMain rodaTeste = new RodaTesteMain();
		rodaTeste.executa();
	}

}
