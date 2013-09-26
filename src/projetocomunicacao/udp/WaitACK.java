package projetocomunicacao.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.concurrent.atomic.AtomicInteger;

public class WaitACK extends Thread {

    private HostUDP cliente;
    private WriteData write;
    private AtomicInteger pacotesConfirmados;

    public WaitACK(HostUDP cliente, WriteData write) {
        this.cliente = cliente;
        this.write = write;
        this.pacotesConfirmados = new AtomicInteger(0);
    }

    public void run() {
        while (true) {
            try {
                DatagramPacket p = new DatagramPacket(new byte[PacketACK.size], PacketACK.size);
                try {
                    cliente.socket.receive(p);
                } catch (IOException e) {
                   // cliente.socket.close();
                    System.out.println(">>>>>> Timeoutsocket");
                    cliente.enviando.set(false);
                    if (write.isInit.get()) {
                        write.naoConectou.set(true);
                    }
                    break;
                }
                
                if (write.isInit.get()) {
                    cliente.socket.setSoTimeout(0);
                    write.naoConectou.set(false);
                    write.reenviado.set(-1000000000);
                }

                if (!ModuloEspecial.descarta()) {
                    PacketACK pacote = new PacketACK(p.getData());
                    System.out.println(">>>>>>> Recebeu ACK num " + pacote.getNumSeq() + " de " + p.getAddress() + " " + p.getPort());
                    if (pacote.getIsLast()) {
                        cliente.enviando.set(false);
                        break;
                    }

                    cliente.mutex.lock();
                    try {
                        synchronized (cliente.bufferAck) {
                            cliente.bufferAck.put(pacote.getNumSeq(), pacote);
                        }
                        PacketData pacoteDados = cliente.bufferDado.remove(pacote.getNumSeq());
                        if (pacoteDados != null) {
                            this.pacotesConfirmados.incrementAndGet();
                        }
                        int proxNumSeq = Math.min(cliente.proxNumSeq, cliente.windowBase + cliente.windowSize);
                        while (cliente.bufferAck.containsKey(cliente.windowBase)) {
                            cliente.bufferAck.remove(cliente.windowBase);
                            cliente.windowBase++;
                        }
                        while (cliente.bufferDado.containsKey(proxNumSeq) && proxNumSeq < cliente.windowBase + cliente.windowSize) {
                            this.write.envia(cliente.bufferDado.get(proxNumSeq++));
                        }
                        if (this.pacotesConfirmados.get() == cliente.qntTotalPacotes) {
                            this.write.envia(new PacketData(-1, true));
                            this.cliente.socket.setSoTimeout(cliente.socketTimeout);
                        }
                    } finally {
                        cliente.mutex.unlock();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cliente.socket.close();
    }
}
