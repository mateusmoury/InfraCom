package projetocomunicacao.udp;

public abstract class Packet {
	
	private int numSeq;
	private boolean isLast;
	
	public Packet() {
		this(0,false);
	}
	
	public Packet(int numSeq) {
		this(numSeq, false);
	}
	
	public Packet(int numSeq, boolean isLast) {
		setNumSeq(numSeq);
		setIsLast(isLast);
	}
	
	public abstract byte[] montaPacote();
	
	public void setNumSeq(int numSeq) {
		this.numSeq = numSeq;
	}
	
	public int getNumSeq() {
		return numSeq;
	}
	
	public void setIsLast(boolean isLast) {
		this.isLast = isLast;
	}
	
	public boolean getIsLast() {
		return isLast;
	}
}
