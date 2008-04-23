/**
 * 
 */
package cn.rudp;

/**
 * enumerates the TCP connection states.
 * <p>
 * The states are incorporated from the Transmission Control Protocol (TCP),
 * which is defined in <a href="http://tools.ietf.org/html/rfc793">RFC 793</a>
 * and the states are defined in section 3.2 Terminology.
 * </p>
 * @author Gerald Scharitzer (e0127228 at student dot tuwien dot ac dot at)
 */
public enum ConnectionState {
	
	LISTEN("LISTEN"),
	SYN_SENT("SYN-SENT"),
	SYN_RECEIVED("SYN-RECEIVED"),
	ESTABLISHED("ESTABLISHED"),
	FIN_WAIT_1("FIN-WAIT-1"),
	FIN_WAIT_2("FIN-WAIT-2"),
	CLOSE_WAIT("CLOSE-WAIT"),
	CLOSING("CLOSING"),
	LAST_ACK("LAST-ACK"),
	TIME_WAIT("TIME-WAIT"),
	CLOSED("CLOSED");
	
	private String name;

	ConnectionState(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}

}
