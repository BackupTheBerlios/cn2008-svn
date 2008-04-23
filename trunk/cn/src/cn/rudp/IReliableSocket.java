/**
 * 
 */
package cn.rudp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;

/**
 *  
 * @author Gerald Scharitzer (e0127228 at student dot tuwien dot ac dot at)
 *
 */
public interface IReliableSocket {
	// TODO this is under heavy construction
	
	void bind(SocketAddress address) throws IOException;
	void close() throws IOException;
	void connect(SocketAddress address) throws IOException;
	void connect(SocketAddress address, int timeout) throws IOException;
	InetAddress getInetAddress();
	InetAddress getLocalAddress();
	int getLocalPort();
	SocketAddress getLocalSocketAddress();
	int getPort();
	int getReceiveBufferSize() throws SocketException;
	float getReliability();
	SocketAddress getRemoteSocketAddress();
	int getSendBufferSize() throws SocketException;
	int getSoTimeout() throws SocketException;
	boolean isBound();
	boolean isClosed();
	boolean isConnected();
	boolean isInputShutdown();
	boolean isOutputShutdown();
	void receive(IReliablePacket message) throws IOException;
	void send(IReliablePacket message) throws IOException;
	void setReceiveBufferSize(int size) throws SocketException;
	void setReliability(float f);
	void setSendBufferSize(int size) throws SocketException;
	void setSoTimeout(int timeout) throws SocketException;
	void shutdownInput() throws IOException;
	void shutdownOutput() throws IOException;

}
