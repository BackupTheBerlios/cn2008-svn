/**
 * 
 */
package cn.rudp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * @author Gerald Scharitzer (e0127228 at student dot tuwien dot ac dot at)
 *
 */
public class ReliableSocket implements IReliableSocket {
	// TODO this is under heavy construction
	
	private DatagramSocket socket;
	private DatagramPacket datagramPacket;
	private Map<Integer,IReliablePacket> packets;
	private int sequenceNumber;
	private int acknowledgeNumber;
	private int sendBufferSize;
	private int receiveBufferSize;
	private byte[] sendBuffer;
	private byte[] receiveBuffer;
	private ByteArrayInputStream byteArrayInputStream;
	private ObjectInputStream objectInputStream;
	private ByteArrayOutputStream byteArrayOutputStream;
	private ObjectOutputStream objectOutputStream;
	private boolean connected = false;
	private float reliability = MAX_RELIABILITY;
	private Random randomizer;
	
	private static final int INITIAL_BUFFER_SIZE = 4096;
	private static final float MAX_RELIABILITY = 1.0f;
	private static final float MIN_RELIABILITY = 0.0f;
	
	public ReliableSocket() throws SocketException {
		socket = new DatagramSocket();
	}
	
	public ReliableSocket(InetAddress remoteAddress, int remotePort) throws IOException {
		socket = new DatagramSocket();
		InetSocketAddress socketAddress = new InetSocketAddress(remoteAddress,remotePort);
		connect(socketAddress);
	}
	
	public ReliableSocket(InetAddress remoteAddress, int remotePort, InetAddress localAddress, int localPort) throws IOException {
		socket = new DatagramSocket(localPort,localAddress);
		InetSocketAddress socketAddress = new InetSocketAddress(remoteAddress,remotePort);
		connect(socketAddress);
	}
	
	public ReliableSocket(String remoteHost, int remotePort) throws IOException {
		socket = new DatagramSocket();
		InetSocketAddress socketAddress = new InetSocketAddress(remoteHost,remotePort);
		connect(socketAddress);
	}

	public ReliableSocket(String remoteHost, int remotePort, InetAddress localAddress, int localPort) throws IOException {
		socket = new DatagramSocket(localPort,localAddress);
		InetSocketAddress socketAddress = new InetSocketAddress(remoteHost,remotePort);
		connect(socketAddress);
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#bind(java.net.SocketAddress)
	 */
	@Override
	public void bind(SocketAddress address) throws SocketException {
		socket.bind(address);
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#close()
	 */
	@Override
	public void close() {
		socket.close();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#connect(java.net.InetAddress, int)
	 */
	@Override
	public void connect(SocketAddress address, int timeout) throws IOException {
		if (connected)
			throw new SocketException("The socket is already connected.");

		socket.connect(address);

		sequenceNumber = 0;
		acknowledgeNumber = 0;
		
		IPacketHeader header = new PacketHeader();
		
		setSendBufferSize(INITIAL_BUFFER_SIZE); // TODO coordinate this
		setReceiveBufferSize(INITIAL_BUFFER_SIZE); // TODO coordinate this
		sendBuffer = byteArrayOutputStream.toByteArray();
		datagramPacket = new DatagramPacket(sendBuffer,sendBuffer.length);

		try { // handshake
			header.setSequenceNumber(sequenceNumber);
			header.setAcknowledgeNumber(acknowledgeNumber);
			header.setAcknowledge(false);
			header.setReset(false);
			header.setSynchronize(true);
			header.setFinalize(false);
			header.setDataLength(0);
			sendHeader(header); // send SYN

			receiveHeader(header); // receive ACK
			if (header.isReset())
				throw new SocketException("The remote socket sent reset.");
			if (header.isSynchronize())
				acknowledgeNumber = header.getAcknowledgeNumber();
			
			header.setSequenceNumber(sequenceNumber);
			header.setAcknowledgeNumber(acknowledgeNumber);
			header.setAcknowledge(true);
			header.setReset(false);
			header.setSynchronize(false);
			header.setFinalize(false);
			header.setDataLength(0);
			sendHeader(header); // send ACK
		} catch (IOException e) {
			header.setSequenceNumber(sequenceNumber);
			header.setAcknowledgeNumber(acknowledgeNumber);
			header.setAcknowledge(false);
			header.setReset(true);
			header.setSynchronize(false);
			header.setFinalize(false);
			header.setDataLength(0);
			sendHeader(header); // send RST
			throw e;
		}
		
		connected = true;
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#connect(java.net.SocketAddress)
	 */
	@Override
	public void connect(SocketAddress address) throws IOException {
		connect(address,0);
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#getInetAddress()
	 */
	@Override
	public InetAddress getInetAddress() {
		return socket.getInetAddress();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#getLocalAddress()
	 */
	@Override
	public InetAddress getLocalAddress() {
		return socket.getLocalAddress();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#getLocalPort()
	 */
	@Override
	public int getLocalPort() {
		return socket.getLocalPort();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#getLocalSocketAddress()
	 */
	@Override
	public SocketAddress getLocalSocketAddress() {
		return socket.getLocalSocketAddress();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#getPort()
	 */
	@Override
	public int getPort() {
		return socket.getPort();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#getReceiveBufferSize()
	 */
	@Override
	public int getReceiveBufferSize() throws SocketException {
		return socket.getReceiveBufferSize();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#getReliability()
	 */
	@Override
	public float getReliability() {
		return reliability;
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#getRemoteSocketAddress()
	 */
	@Override
	public SocketAddress getRemoteSocketAddress() {
		return socket.getRemoteSocketAddress();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#getSendBufferSize()
	 */
	@Override
	public int getSendBufferSize() throws SocketException {
		return socket.getSendBufferSize();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#getSoTimeout()
	 */
	@Override
	public int getSoTimeout() throws SocketException {
		return socket.getSoTimeout();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#isBound()
	 */
	@Override
	public boolean isBound() {
		return socket.isBound();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#isClosed()
	 */
	@Override
	public boolean isClosed() {
		return socket.isClosed();
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#isConnected()
	 */
	@Override
	public boolean isConnected() {
		return connected;
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#isInputShutdown()
	 */
	@Override
	public boolean isInputShutdown() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#isOutputShutdown()
	 */
	@Override
	public boolean isOutputShutdown() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#receive(cn.rudp.IReliablePacket)
	 */
	@Override
	public void receive(IReliablePacket packet) throws IOException {
		if (!connected)
			throw new SocketException("The socket is not connected.");
		// TODO datagramSocket.receive(p);
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#send(cn.rudp.IReliablePacket)
	 */
	@Override
	public void send(IReliablePacket packet) throws IOException {
		if (!connected)
			throw new SocketException("The socket is not connected.");
		
		IPacketHeader packetHeader = new PacketHeader();
		packetHeader.setSequenceNumber(sequenceNumber);
		packetHeader.setAcknowledgeNumber(acknowledgeNumber);
		byte[] buffer = new byte[packetHeader.getLength() + packet.getLength()];
		datagramPacket = new DatagramPacket(buffer,buffer.length);
		socket.send(datagramPacket);
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#setReceiveBufferSize(int)
	 */
	@Override
	public void setReceiveBufferSize(int size) throws SocketException {
		receiveBufferSize = size;
		receiveBuffer = new byte[receiveBufferSize];
		byteArrayInputStream = new ByteArrayInputStream(receiveBuffer);
		try {
			objectInputStream = new ObjectInputStream(byteArrayInputStream);
		} catch (IOException e) {
			throw new SocketException(e.getMessage());
		}
		socket.setReceiveBufferSize(receiveBufferSize);
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#setReliability(float)
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
	 * @see cn.rudp.IReliableSocket#setSendBufferSize(int)
	 */
	@Override
	public void setSendBufferSize(int size) throws SocketException {
		sendBufferSize = size;
		byteArrayOutputStream = new ByteArrayOutputStream(sendBufferSize);
		try {
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		} catch (IOException e) {
			throw new SocketException(e.getMessage());
		}
		socket.setSendBufferSize(sendBufferSize);
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#setSoTimeout(int)
	 */
	@Override
	public void setSoTimeout(int timeout) throws SocketException {
		socket.setSoTimeout(timeout);
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#shutdownInput()
	 */
	@Override
	public void shutdownInput() throws IOException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IReliableSocket#shutdownOutput()
	 */
	@Override
	public void shutdownOutput() throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	private void sendHeader(IPacketHeader header) throws IOException {
		header.writeExternal(objectOutputStream);
		datagramPacket.setData(byteArrayOutputStream.toByteArray());
		socket.send(datagramPacket);
	}
	
	private void receiveHeader(IPacketHeader header) throws IOException {
		datagramPacket.setData(receiveBuffer);
		socket.receive(datagramPacket);
		
		if (datagramPacket.getLength() != header.getLength())
			throw new SocketException("The packet length was " + datagramPacket.getLength() + " instead of " + header.getLength() + ".");
		
		try {
			header.readExternal(objectInputStream);
		} catch (ClassNotFoundException e) {
			throw new IOException(e);
		}
	}
	
}
