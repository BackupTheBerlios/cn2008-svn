/**
 * 
 */
package cn.rudp;

import java.net.InetAddress;
import java.net.SocketAddress;

/**
 * @author Gerald Scharitzer (e0127228 at student dot tuwien dot ac dot at)
 *
 */
public interface IReliablePacket {
	// TODO this is under heavy construction
	
	public InetAddress getAddress();
	public byte[] getData();
	public int getLength();
	public int getOffset();
	public int getPort();
	public SocketAddress getSocketAddress();
	public void setAddress(InetAddress address);
	public void setData(byte[] buf);
	public void setData(byte[] buf, int offset, int length);
	public void setLength(int length);
	public void setPort(int port);
	public void setSocketAddress(SocketAddress address);

}
