/**
 * 
 */
package cn.rudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Map;
import java.util.UUID;

/**
 * @author Gerald Scharitzer (e0127228 at student dot tuwien dot ac dot at)
 *
 */
public class ReliableSocket implements IReliableSocket {
	
	private DatagramSocket datagramSocket;
	private Map<UUID,Integer> packets;
	
	private static final int PACKET_SENT = 0;
	private static final int PACKET_ACKNOWLEDGED = 1;

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#bind(java.net.SocketAddress)
	 */
	@Override
	public void bind(SocketAddress address) throws SocketException {
		datagramSocket.bind(address);
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#close()
	 */
	@Override
	public void close() {
		datagramSocket.close();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#connect(java.net.InetAddress, int)
	 */
	@Override
	public void connect(InetAddress address, int port) {
		datagramSocket.connect(address,port);
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#connect(java.net.SocketAddress)
	 */
	@Override
	public void connect(SocketAddress address) throws SocketException {
		datagramSocket.connect(address);
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#disconnect()
	 */
	@Override
	public void disconnect() {
		datagramSocket.disconnect();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#getInetAddress()
	 */
	@Override
	public InetAddress getInetAddress() {
		return datagramSocket.getInetAddress();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#getLocalAddress()
	 */
	@Override
	public InetAddress getLocalAddress() {
		return datagramSocket.getLocalAddress();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#getLocalPort()
	 */
	@Override
	public int getLocalPort() {
		return datagramSocket.getLocalPort();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#getLocalSocketAddress()
	 */
	@Override
	public SocketAddress getLocalSocketAddress() {
		return datagramSocket.getLocalSocketAddress();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#getPort()
	 */
	@Override
	public int getPort() {
		return datagramSocket.getPort();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#getReceiveBufferSize()
	 */
	@Override
	public int getReceiveBufferSize() throws SocketException {
		return datagramSocket.getReceiveBufferSize();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#getRemoteSocketAddress()
	 */
	@Override
	public SocketAddress getRemoteSocketAddress() {
		return datagramSocket.getRemoteSocketAddress();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#getSendBufferSize()
	 */
	@Override
	public int getSendBufferSize() throws SocketException {
		return datagramSocket.getSendBufferSize();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#getSoTimeout()
	 */
	@Override
	public int getSoTimeout() throws SocketException {
		return datagramSocket.getSoTimeout();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#isBound()
	 */
	@Override
	public boolean isBound() {
		return datagramSocket.isBound();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#isClosed()
	 */
	@Override
	public boolean isClosed() {
		return datagramSocket.isClosed();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#isConnected()
	 */
	@Override
	public boolean isConnected() {
		return datagramSocket.isConnected();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#receive(cn.rudp.IReliablePacket)
	 */
	@Override
	public void receive(IReliablePacket message) throws IOException {
		// TODO datagramSocket.receive(p);
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#send(cn.rudp.IReliablePacket)
	 */
	@Override
	public void send(IReliablePacket message) throws IOException {
		// set id
		// TODO datagramSocket.send(p);
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#setReceiveBufferSize(int)
	 */
	@Override
	public void setReceiveBufferSize(int size) throws SocketException {
		datagramSocket.setReceiveBufferSize(size);
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#setSendBufferSize(int)
	 */
	@Override
	public void setSendBufferSize(int size) throws SocketException {
		datagramSocket.setSendBufferSize(size);
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#setSoTimeout(int)
	 */
	@Override
	public void setSoTimeout(int timeout) throws SocketException {
		datagramSocket.setSoTimeout(timeout);
	}
	
	

}
