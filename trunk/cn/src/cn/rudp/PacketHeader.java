/**
 * 
 */
package cn.rudp;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * @author Gerald Scharitzer (e0127228 at student dot tuwien dot ac dot at)
 *
 */
public class PacketHeader implements IPacketHeader {
	
	private int sequenceNumber;
	private int acknowledgeNumber;
	private boolean acknowledge;
	private boolean reset;
	private boolean synchronize;
	private boolean finalize;
	private int dataLength;
	
	private static final int ACK             = 0x10000000;
	private static final int RST             = 0x04000000;
	private static final int SYN             = 0x02000000;
	private static final int FIN             = 0x01000000;
	private static final int MAX_DATA_LENGTH = 0x00ffffff;
	
	/**
	 * the length of the externalized header in bytes
	 * 
	 * <code>
	 * field             length<br/>
	 * ----------------- ------<br/>
	 * sequenceNumber         4<br/>
	 * acknowledgeNumber      4<br/>
	 * flags                  1<br/>
	 * dataLength             3<br/>
	 * ----------------- ------<br/>
	 *                       12<br/>
	 * </code> 
	 */
	private static final int LENGTH = 12;

	/* (non-Javadoc)
	 * @see cn.rudp.IPacketHeader#getAcknowledgeNumber()
	 */
	@Override
	public int getAcknowledgeNumber() {
		return acknowledgeNumber;
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IPacketHeader#getDataLength()
	 */
	@Override
	public int getDataLength() {
		return dataLength;
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IPacketHeader#getSequenceNumber()
	 */
	@Override
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IPacketHeader#isAcknowledge()
	 */
	@Override
	public boolean isAcknowledge() {
		return acknowledge;
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IPacketHeader#isFinalize()
	 */
	@Override
	public boolean isFinalize() {
		return finalize;
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IPacketHeader#isReset()
	 */
	@Override
	public boolean isReset() {
		return reset;
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IPacketHeader#isSynchronize()
	 */
	@Override
	public boolean isSynchronize() {
		return synchronize;
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IPacketHeader#setAcknowledge(boolean)
	 */
	@Override
	public void setAcknowledge(boolean b) {
		acknowledge = b;
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IPacketHeader#setAcknowledgeNumber(int)
	 */
	@Override
	public void setAcknowledgeNumber(int i) {
		acknowledgeNumber = i;
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IPacketHeader#setFinalize(boolean)
	 */
	@Override
	public void setFinalize(boolean b) {
		finalize = b;
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IPacketHeader#setDataLength(int)
	 */
	@Override
	public void setDataLength(int i) {
		if (i < 0)
			throw new IllegalArgumentException("The data length must not be negative.");
		if (i > MAX_DATA_LENGTH)
			throw new IllegalArgumentException("The data length must be less or equal to " + MAX_DATA_LENGTH + ".");
		dataLength = i;
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IPacketHeader#setReset(boolean)
	 */
	@Override
	public void setReset(boolean b) {
		reset = b;
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IPacketHeader#setSequenceNumber(int)
	 */
	@Override
	public void setSequenceNumber(int i) {
		sequenceNumber = i;
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IPacketHeader#setSynchronize(boolean)
	 */
	@Override
	public void setSynchronize(boolean b) {
		synchronize = b;
	}

	/* (non-Javadoc)
	 * @see java.io.Externalizable#readExternal(java.io.ObjectInput)
	 */
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		sequenceNumber = in.readInt();
		acknowledgeNumber = in.readInt();
		dataLength = in.readInt(); // read flags and length
		acknowledge = ((dataLength & ACK) != 0);
		reset = ((dataLength & RST) != 0);
		synchronize = ((dataLength & SYN) != 0);
		finalize = ((dataLength & FIN) != 0);
		dataLength &= MAX_DATA_LENGTH; // remove flags
	}

	/* (non-Javadoc)
	 * @see java.io.Externalizable#writeExternal(java.io.ObjectOutput)
	 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(sequenceNumber);
		out.writeInt(acknowledgeNumber);
		int flaggedLength = dataLength;
		if (acknowledge)
			flaggedLength |= ACK; // set ACK flag
		if (reset)
			flaggedLength |= RST; // set RST flag
		if (synchronize)
			flaggedLength |= SYN; // set SYN flag
		if (finalize)
			flaggedLength |= FIN; // set FIN flag
		out.writeInt(flaggedLength);
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IPacketHeader#getLength()
	 */
	@Override
	public int getLength() {
		return LENGTH;
	}

	/* (non-Javadoc)
	 * @see cn.rudp.IPacketHeader#getMaxDataLength()
	 */
	@Override
	public int getMaxDataLength() {
		return MAX_DATA_LENGTH;
	}

}
