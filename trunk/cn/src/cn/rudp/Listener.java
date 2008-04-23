/**
 * 
 */
package cn.rudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Gerald Scharitzer (e0127228 at student dot tuwien dot ac dot at)
 *
 */
public class Listener implements Runnable {
	
	private DatagramSocket socket;
	private DatagramPacket packet;
	private Map<SocketAddress,Connection<SocketAddress>> connections;
	private byte[] buffer;
	private boolean running;
	
	public Listener(DatagramSocket socket, Map<SocketAddress,Connection<SocketAddress>> connections) {
		this.socket = socket;
		this.connections = connections;
		packet = new DatagramPacket(buffer,buffer.length);
	}
	
	public void run() {
		running = true;
		while (running) {
			try {
				socket.receive(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PacketHeader header = new PacketHeader();
			// TODO read header
			SocketAddress sender = packet.getSocketAddress();
			Connection<SocketAddress> connection = connections.get(sender);
			if (connection == null) {
				if (header.isSynchronize()) {
					connection = new Connection<SocketAddress>();
					connection.setSender(sender);
					SocketAddress receiver = socket.getLocalSocketAddress();
					connection.setReceiver(receiver);
					connection.passiveOpen();
					connection.syn();
				} else {
					// TODO invalid connection request
				}
			} else {
				if (connection.getState() == ConnectionState.SYN_RECEIVED) {
					if (header.isAcknowledge()) {
						connection.ack();
					} else {
						connections.remove(sender);
					}
				} else {
					
				}
			}
		}
	}
	
	public void stop() {
		running = false;
	}

}
