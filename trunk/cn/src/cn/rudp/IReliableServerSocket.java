/**
 * 
 */
package cn.rudp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;

/**
 * @author Gerald Scharitzer (e0127228 at student dot tuwien dot ac dot at)
 *
 */
public interface IReliableServerSocket {
	
	IReliableSocket accept() throws IOException;
	void bind(SocketAddress localAddress) throws IOException;
	void bind(SocketAddress localAddress, int backlog) throws IOException;
	void close();
	InetAddress getInetAddress();
	int getLocalPort();
	SocketAddress getLocalSocketAddress();
	int getReceiveBufferSize() throws SocketException;
	float getReliability();
	boolean getReuseAddress() throws SocketException;
	int getSoTimeout() throws SocketException;
	boolean isBound();
	boolean isClosed();
	void setReceiveBufferSize(int size) throws SocketException;
	void setReliability(float f);
	void setReuseAddress(boolean b) throws SocketException;
	void setSoTimeout(int timeout) throws SocketException;

}
