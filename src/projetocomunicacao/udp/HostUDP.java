package projetocomunicacao.udp;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;


public abstract class HostUDP {
	//Para enviar dados
	public ConcurrentHashMap<Integer, PacketData> bufferDado;
	public ConcurrentHashMap<Integer, PacketACK> bufferAck;

	public int qntTotalPacotes;

	public InetAddress ip;
	public DatagramSocket socket;

	public BufferedInputStream stream;

	public int proxNumSeq;
	public int windowBase;
	public int windowSize;

	public final int socketTimeout = 2000;
	public final int packetTimeout = 125;

	public Timer timer;

	public String ipServidor;
	public int porta;

	public Lock mutex;
	
	//Para receber dados
	public BufferedOutputStream streamReceber;
	public ByteArrayOutputStream baos;
	public DatagramSocket socketReceber;

	public int windowBaseRecebe;
	public int windowSizeRecebe;

	public int totalPacotes;
	public int pacotesRecebidos;

	public ConcurrentHashMap<Integer, PacketData> buffer;
	
	public InetAddress ipEnviar;
	public int portaEnviar;
	
	public Lock mutexEnviar;
	
	public AtomicBoolean enviando;
	public AtomicBoolean recebendo;
}
