/**
 * 
 */
package cn.rudp;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketAddress;

/**
 * @author Gerald Scharitzer (e0127228 at student dot tuwien dot ac dot at)
 *
 */
public class ReliablePacket implements IReliablePacket {
	
	private static final int ID_SIZE = 16;
	private DatagramPacket datagramPacket;
	
	public ReliablePacket(byte[] buf, int length) {
		datagramPacket = new DatagramPacket(buf,length);
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliablePacket#getAddress()
	 */
	@Override
	public InetAddress getAddress() {
		return datagramPacket.getAddress();
	}
	
	/* (non-Javadoc)
	 * @see cn.rudp.IReliablePacket#getData()
	 */
	@Override
	public byte[] getData() {
		return datagramPacket.getData();
	}

	@Override
	public byte[] getId() {
		byte[] id = new byte[ID_SIZE];
		byte[] buffer = datagramPacket.getData();
		System.arraycopy(buffer,0,id,0,ID_SIZE);
		return id;
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliablePacket#getLength()
	 */
	@Override
	public int getLength() {
		return datagramPacket.getLength();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliablePacket#getOffset()
	 */
	@Override
	public int getOffset() {
		return datagramPacket.getOffset();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliablePacket#getPort()
	 */
	@Override
	public int getPort() {
		return datagramPacket.getPort();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliablePacket#getSocketAddress()
	 */
	@Override
	public SocketAddress getSocketAddress() {
		return datagramPacket.getSocketAddress();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliablePacket#setAddress(java.net.InetAddress)
	 */
	@Override
	public void setAddress(InetAddress address) {
		datagramPacket.setAddress(address);
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliablePacket#setData(byte[])
	 */
	@Override
	public void setData(byte[] buf) {
		datagramPacket.setData(buf);
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliablePacket#setDate(byte[], int, int)
	 */
	@Override
	public void setData(byte[] buf, int offset, int length) {
		datagramPacket.setData(buf,offset,length);
	}
	
	@Override
	public void setId(byte[] id) {
		if (id.length != ID_SIZE)
			throw new IllegalArgumentException("The id was not " + ID_SIZE + " bytes long.");
		// TODO set id
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliablePacket#setLength(int)
	 */
	@Override
	public void setLength(int length) {
		datagramPacket.setLength(length);
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliablePacket#setPort(int)
	 */
	@Override
	public void setPort(int port) {
		datagramPacket.setPort(port);
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliablePacket#setSocketAddress(java.net.SocketAddress)
	 */
	@Override
	public void setSocketAddress(SocketAddress address) {
		datagramPacket.setSocketAddress(address);
	}

}
