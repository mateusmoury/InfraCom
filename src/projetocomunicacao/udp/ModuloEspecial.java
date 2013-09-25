package projetocomunicacao.udp;
import java.util.Random;


public class ModuloEspecial {
	private static int numero;
	private static Random random = new Random();
	
	public static void setNumero(int num) {
		numero = num;
	}
	
	public static int getNumer() {
		return numero;
	}
	
	public static boolean descarta() {
		int k = Math.abs(random.nextInt())%100;
		return k < numero;
	}
}
