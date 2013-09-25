package projetocomunicacao.udp;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;


public class PacketData extends Packet implements Serializable{
	private static final long serialVersionUID = 2645036630731217827L;
	
	public static int tamanhoPacote = 1 << 8;
	public static int tamanhoDados = tamanhoPacote - 9;
	
	private byte[] data;
	private int totalPacotes;
	
	public PacketData(int numSeq) {
		super(numSeq);
		this.data = new byte[0];
	}
	
	public PacketData(int numSeq, boolean isLast) {
		super(numSeq, isLast);
		this.data = new byte[0];
	}
	
	public PacketData(byte[] dados, int numSeq, boolean isLast, int length, int qntTotal) {
		super(numSeq, isLast);
		this.data = new byte[length];
		this.totalPacotes = qntTotal;
		System.arraycopy(dados, 0, this.data, 0, length);
	}
	
	public PacketData(byte[] pacote, int length) {
		try {
			DataInputStream dis = new DataInputStream(new ByteArrayInputStream(pacote));
			setNumSeq(dis.readInt());
			setIsLast(dis.readBoolean());
			this.totalPacotes = dis.readInt();
			this.data = new byte[Math.max(0, length-9)];
			dis.read(data, 0, Math.max(0, length-9));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public byte[] montaPacote() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream ous = new DataOutputStream(baos);
			ous.writeInt(this.getNumSeq());
			ous.writeBoolean(this.getIsLast());
			ous.writeInt(this.getTotalPacotes());
			ous.write(data, 0, data.length);
			ous.flush();
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public byte[] getDados() {
		return this.data;
	}
	
	public int getTotalPacotes() {
		return this.totalPacotes;
	}
}
