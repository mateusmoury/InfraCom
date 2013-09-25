package projetocomunicacao.udp;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.concurrent.atomic.AtomicInteger;

public class ReadData extends Thread {
	private HostUDP servidor;
	private AtomicInteger qntRecebido; 
	Object objeto;

	public ReadData(HostUDP servidor) {
		this.servidor = servidor;
		this.qntRecebido = new AtomicInteger(0);		
	}

	public void run() {
		boolean loop = true;
		while (loop) {
			try {
				servidor.mutexEnviar.lock();
				
				DatagramPacket datagrama = new DatagramPacket(new byte[PacketData.tamanhoPacote], PacketData.tamanhoPacote);
				try {
					servidor.socketReceber.receive(datagrama);
					servidor.ipEnviar = datagrama.getAddress();
				} catch (IOException e) {
					servidor.socketReceber.close();
					this.objeto = Serializer.deserialize(servidor.baos.toByteArray());
					servidor.recebendo.set(false);
					break;
				}
				
				if (!ModuloEspecial.descarta()) {
					PacketData pacote = new PacketData(datagrama.getData(), datagrama.getLength());
					System.out.println(">>>>>> Recebeu pacote num " + pacote.getNumSeq() + " de " + datagrama.getAddress() + " " + datagrama.getPort());
					if (pacote.getNumSeq() >= servidor.windowBaseRecebe && !servidor.buffer.containsKey(pacote.getNumSeq())) {
						servidor.buffer.put(pacote.getNumSeq(), pacote);
						qntRecebido.incrementAndGet();
					}
					
					byte[] ack = (new PacketACK(pacote.getNumSeq())).montaPacote();
					DatagramPacket dat = new DatagramPacket(ack, ack.length, datagrama.getAddress(), datagrama.getPort());
					
					servidor.socketReceber.send(dat);
                                        
                                        System.out.println(">>>>>> Envia ack num " + pacote.getNumSeq() + " para " + datagrama.getAddress() + " " + datagrama.getPort());
					
					if (pacote.getIsLast()) {
						for (int i = 0; i < 3; ++i) {
							Thread.sleep(servidor.packetTimeout);
							servidor.socketReceber.send(dat);
						}
						servidor.socketReceber.close();
						this.objeto = Serializer.deserialize(servidor.baos.toByteArray());
						servidor.recebendo.set(false);
						break;
					}
					
					PacketData aux;
					while ((aux = servidor.buffer.get(servidor.windowBaseRecebe)) != null) {
						servidor.streamReceber.write(aux.getDados());
						servidor.streamReceber.flush();
						servidor.windowBaseRecebe++;
					}
					
					if (qntRecebido.get() == pacote.getTotalPacotes()) {
						servidor.socketReceber.setSoTimeout(servidor.socketTimeout);
					}
				}
				
				servidor.mutexEnviar.unlock();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
