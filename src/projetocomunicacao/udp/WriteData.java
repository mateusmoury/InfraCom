package projetocomunicacao.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class WriteData extends Thread {

    private HostUDP cliente;
    public AtomicInteger reenviado;
    public AtomicBoolean naoConectou, isInit;

    public WriteData(HostUDP cliente, boolean iniciaConexao) {
        this.cliente = cliente;
        this.reenviado = new AtomicInteger(0);
        this.naoConectou = new AtomicBoolean(false);
        this.isInit = new AtomicBoolean(iniciaConexao);
        if (this.isInit.get()) {
            try {
                this.cliente.socket.setSoTimeout(cliente.socketTimeout);
            } catch (SocketException e) {
            }
        }
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
            try {
                PacketData pacote = new PacketData(data, cliente.proxNumSeq++, false, qntBytesLidos, cliente.qntTotalPacotes);
                synchronized (cliente.bufferDado) {
                    cliente.bufferDado.put(pacote.getNumSeq(), pacote);
                }
                if (pacote.getNumSeq() < cliente.windowBase + cliente.windowSize) {
                    envia(pacote);
                }
            } finally {
                cliente.mutex.unlock();
            }
        }
    }

    public synchronized void envia(PacketData packet) {
        byte[] pacote = packet.montaPacote();
        try {
            System.out.println(">>>>>> Enviando pacote num " + packet.getNumSeq() + " de " + InetAddress.getLocalHost().toString() + " pra " + cliente.ipEnviar + " " + cliente.portaEnviar);
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
            try {
                PacketData pacote = cliente.bufferDado.get(numSeq);
                if (pacote != null) {
                    System.out.println(">>>>>> Vai reenviar o pacote num " + numSeq + " para " + this.ip + " " + this.pprta);
                    if (isInit.get()) {
                        reenviado.incrementAndGet();
                        if (reenviado.get() >= 10) {
                            cliente.socket.setSoTimeout(3000);
                            naoConectou.set(true);
                            return;
                        }
                    }
                    envia(pacote);
                }
            } catch (SocketException e) {
            } finally {
                cliente.mutex.unlock();
            }
        }
    }
}
