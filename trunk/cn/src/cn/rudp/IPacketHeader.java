/**
 * 
 */
package cn.rudp;

import java.io.Externalizable;

/**
 * @author Gerald Scharitzer (e0127228 at student dot tuwien dot ac dot at)
 *
 */
public interface IPacketHeader extends Externalizable {
	
	int getSequenceNumber();
	void setSequenceNumber(int i);
	
	int getAcknowledgeNumber();
	void setAcknowledgeNumber(int i);
	
	boolean isAcknowledge();
	void setAcknowledge(boolean b);

	boolean isReset();
	void setReset(boolean b);

	boolean isSynchronize();
	void setSynchronize(boolean b);

	boolean isFinalize();
	void setFinalize(boolean b);

	int getDataLength();
	void setDataLength(int i);

	/**
	 * @return the length of the externalized header in bytes
	 */
	int getLength();
	
	/**
	 * @return the maximum value of the data length in bytes
	 */
	int getMaxDataLength();

}
