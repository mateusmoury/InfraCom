package projetocomunicacao.udp;
import java.io.IOException;
import java.net.DatagramPacket;
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
			cliente.socket.send(new DatagramPacket(pacote, pacote.length, cliente.ipEnviar, cliente.portaEnviar));
			cliente.timer.schedule(new Reenvia(packet.getNumSeq()), cliente.packetTimeout);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class Reenvia extends TimerTask {
		private int numSeq;

		public Reenvia(int numSeq) {
			this.numSeq = numSeq;
		}

		@Override
		public void run() {
			cliente.mutex.lock();
			synchronized (cliente.bufferDado) {
				PacketData pacote = cliente.bufferDado.get(numSeq);
				if (pacote != null) {
					envia(pacote);;
				}
			}

			cliente.mutex.unlock();
		}
	}
}
