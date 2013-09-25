package projetocomunicacao.udp;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketACK extends Packet {
	final static int size = 4;
	
	public PacketACK(int numSeq) {
		super(numSeq);
	}
	
	public PacketACK(int numSeq, boolean isLast) {
		super(numSeq, isLast);
	}
	
	public PacketACK(byte[] array) throws IOException {
		int numSeq = new DataInputStream(new ByteArrayInputStream(array)).readInt(); 
		this.setNumSeq(numSeq);
		this.setIsLast(numSeq == -1);
	}
	
	@Override
	public byte[] montaPacote() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(this.getNumSeq());
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}

}
