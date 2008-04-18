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
	
	public void bind(SocketAddress address) throws IOException;
	public void close() throws IOException;
	public void connect(SocketAddress address) throws IOException;
	public void connect(SocketAddress address, int timeout) throws IOException;
	public InetAddress getInetAddress();
	public InetAddress getLocalAddress();
	public int getLocalPort();
	public SocketAddress getLocalSocketAddress();
	public int getPort();
	public int getReceiveBufferSize() throws SocketException;
	public SocketAddress getRemoteSocketAddress();
	public int getSendBufferSize() throws SocketException;
	public int getSoTimeout() throws SocketException;
	public boolean isBound();
	public boolean isClosed();
	public boolean isConnected();
	public boolean isInputShutdown();
	public boolean isOutputShutdown();
	public void receive(IReliablePacket message) throws IOException;
	public void send(IReliablePacket message) throws IOException;
	public void setReceiveBufferSize(int size) throws SocketException;
	public void setSendBufferSize(int size) throws SocketException;
	public void setSoTimeout(int timeout) throws SocketException;
	public void shutdownInput() throws IOException;
	public void shutdownOutput() throws IOException;

}
