package projetocomunicacao.udp;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.TimerTask;


public class WriteData extends Thread {
	private HostUDP cliente;

	public WriteData(HostUDP cliente) {
		this.cliente = cliente;
	}

	public void run() {
		while (true) {
			byte[] data = new byte[PacketData.tamanhoDados];
			int qntBytesLidos = 0;
			try {
				qntBytesLidos = cliente.stream.read(data, 0, PacketData.tamanhoDados);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (qntBytesLidos <= 0) {
				break;
			}

			cliente.mutex.lock();

			PacketData pacote = new PacketData(data, cliente.proxNumSeq++, false, qntBytesLidos, cliente.qntTotalPacotes);

			synchronized (cliente.bufferDado) {
				cliente.bufferDado.put(pacote.getNumSeq(), pacote);
			}

			if (pacote.getNumSeq() < cliente.windowBase + cliente.windowSize) {
				envia(pacote);
			}

			cliente.mutex.unlock();
		}
	}

	public synchronized void envia(PacketData packet) {
		byte[] pacote = packet.montaPacote();
		try {
                    System.out.println(">>>>>> Enviando pacote num " + packet.getNumSeq() + " pra " + cliente.ipEnviar + " " + cliente.portaEnviar);
			cliente.socket.send(new DatagramPacket(pacote, pacote.length, cliente.ipEnviar, cliente.portaEnviar));
                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Eu to escutando na porta " + cliente.socket.getLocalPort());
			cliente.timer.schedule(new Reenvia(packet.getNumSeq(), cliente.ipEnviar, cliente.portaEnviar), cliente.packetTimeout);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class Reenvia extends TimerTask {
		private int numSeq;
                InetAddress ip;
                int pprta;

		public Reenvia(int numSeq, InetAddress ip, int pprta) {
			this.numSeq = numSeq;
                        this.ip = ip;
                        this.pprta = pprta;
		}

		@Override
		public void run() {
			cliente.mutex.lock();
			synchronized (cliente.bufferDado) {
				PacketData pacote = cliente.bufferDado.get(numSeq);
				if (pacote != null) {
                                        System.out.println(">>>>>> Vai reenviar o pacote num " + numSeq + " " + this.ip + " " + this.pprta);
					envia(pacote);;
				}
			}

			cliente.mutex.unlock();
		}
	}
}
