/**
 * 
 */
package cn.rudp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Gerald Scharitzer (e0127228 at student dot tuwien dot ac dot at)
 *
 */
public class ReliableServerSocket implements IReliableServerSocket {
	
	private DatagramSocket datagramSocket;
	private Map<SocketAddress,Connection<SocketAddress>> connections = new HashMap<SocketAddress,Connection<SocketAddress>>();
	private Listener listener;
	private int backlog;
	private float reliability = MAX_RELIABILITY;
	private Random randomizer;
	
	private static final float MAX_RELIABILITY = 1.0f;
	private static final float MIN_RELIABILITY = 0.0f;
	
	public ReliableServerSocket() throws SocketException {
		datagramSocket = new DatagramSocket();
		backlog = 50;
		startListener();
	}
	
	public ReliableServerSocket(int localPort) throws IOException {
		datagramSocket = new DatagramSocket(localPort);
		backlog = 50;
		startListener();
	}

	public ReliableServerSocket(int localPort, int backlog) throws IOException {
		datagramSocket = new DatagramSocket(localPort);
		this.backlog = backlog;
		startListener();
	}

	public ReliableServerSocket(int localPort, int backlog, InetAddress localAddress) throws IOException {
		datagramSocket = new DatagramSocket(localPort,localAddress);
		this.backlog = backlog;
		startListener();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableServerSocket#accept()
	 */
	@Override
	public IReliableSocket accept() throws IOException {
		IReliableSocket socket = new ReliableSocket();
		return socket;
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableServerSocket#bind(java.net.SocketAddress)
	 */
	@Override
	public void bind(SocketAddress localAddress) throws IOException {
		datagramSocket.bind(localAddress);
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableServerSocket#bind(java.net.SocketAddress, int)
	 */
	@Override
	public void bind(SocketAddress localAddress, int backlog) throws IOException {
		datagramSocket.bind(localAddress);
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableServerSocket#close()
	 */
	@Override
	public void close() {
		datagramSocket.close();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableServerSocket#getInetAddress()
	 */
	@Override
	public InetAddress getInetAddress() {
		return datagramSocket.getLocalAddress();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableServerSocket#getLocalPort()
	 */
	@Override
	public int getLocalPort() {
		return datagramSocket.getLocalPort();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableServerSocket#getLocalSocketAddress()
	 */
	@Override
	public SocketAddress getLocalSocketAddress() {
		return datagramSocket.getLocalSocketAddress();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableServerSocket#getReceiveBufferSize()
	 */
	@Override
	public int getReceiveBufferSize() throws SocketException {
		return datagramSocket.getReceiveBufferSize();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableServerSocket#getReliability()
	 */
	@Override
	public float getReliability() {
		return reliability;
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableServerSocket#getReuseAddress()
	 */
	@Override
	public boolean getReuseAddress() throws SocketException {
		return datagramSocket.getReuseAddress();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableServerSocket#getSoTimeout()
	 */
	@Override
	public int getSoTimeout() throws SocketException {
		return datagramSocket.getSoTimeout();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableServerSocket#isBound()
	 */
	@Override
	public boolean isBound() {
		return datagramSocket.isBound();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableServerSocket#isClosed()
	 */
	@Override
	public boolean isClosed() {
		return datagramSocket.isClosed();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableServerSocket#setReceiveBufferSize(int)
	 */
	@Override
	public void setReceiveBufferSize(int size) throws SocketException {
		datagramSocket.setReceiveBufferSize(size);
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableServerSocket#setReliability(float)
	 */
	@Override
	public synchronized void setReliability(float f) {
		if (f < MIN_RELIABILITY || f > MAX_RELIABILITY)
			throw new IllegalArgumentException("The reliability must be an element of the interval [0..1].");
		if (f == MAX_RELIABILITY) {
			randomizer = null;
		} else if (randomizer == null) {
			randomizer = new Random();
		}
		reliability = f;
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableServerSocket#setReuseAddress(boolean)
	 */
	@Override
	public void setReuseAddress(boolean b) throws SocketException {
		datagramSocket.setReuseAddress(b);
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableServerSocket#setSoTimeout(int)
	 */
	@Override
	public void setSoTimeout(int timeout) throws SocketException {
		datagramSocket.setSoTimeout(timeout);
	}
	
	private void startListener() {
		listener = new Listener(datagramSocket,connections);
		Thread thread = new Thread(listener);
		thread.setDaemon(true);
		thread.start();
	}

}
