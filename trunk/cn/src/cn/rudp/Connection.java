/**
 * 
 */
package cn.rudp;

import java.net.SocketAddress;

/**
 * represents the connection between two communication endpoints.
 * <p>
 * The states and transitions are incorporated from the
 * Transmission Control Protocol (TCP), which is defined in
 * <a href="http://tools.ietf.org/html/rfc793">RFC 793</a> and
 * the state transitions are defined in section 3.2 Terminology.
 * </p>
 * @author Gerald Scharitzer (e0127228 at student dot tuwien dot ac dot at)
 */
public class Connection<T extends SocketAddress> {
	
	private T sender;
	private T receiver;
	
	private ConnectionState state = ConnectionState.CLOSED;
	
	public void ack() {
		if (state == ConnectionState.SYN_RECEIVED) {
			state = ConnectionState.ESTABLISHED;
		} else if (state == ConnectionState.FIN_WAIT_1) {
			state = ConnectionState.FIN_WAIT_2;
		} else if (state == ConnectionState.LAST_ACK) {
			state = ConnectionState.CLOSED;
		} else {
			throw new IllegalStateException("The state was " + state + ".");
		}
	}
	
	public void activeOpen() {
		if (state == ConnectionState.CLOSED) {
			state = ConnectionState.SYN_SENT;
		} else {
			throw new IllegalStateException("The state was " + state + ".");
		}
	}
	
	public void close() {
		if (state == ConnectionState.LISTEN || state == ConnectionState.SYN_SENT) {
			state = ConnectionState.CLOSED;
		} else if (state == ConnectionState.SYN_RECEIVED || state == ConnectionState.ESTABLISHED) {
			state = ConnectionState.FIN_WAIT_1;
		} else if (state == ConnectionState.CLOSE_WAIT) {
			state = ConnectionState.LAST_ACK;
		} else {
			throw new IllegalStateException("The state was " + state + ".");
		}
	}
	
	public void fin() {
		if (state == ConnectionState.ESTABLISHED) {
			state = ConnectionState.CLOSE_WAIT;
		} else if (state == ConnectionState.FIN_WAIT_1) {
			state = ConnectionState.CLOSING;
		} else if (state == ConnectionState.FIN_WAIT_2) {
			state = ConnectionState.TIME_WAIT;
		} else {
			throw new IllegalStateException("The state was " + state + ".");
		}
	}

	public void passiveOpen() {
		if (state == ConnectionState.CLOSED) {
			state = ConnectionState.LISTEN;
		} else {
			throw new IllegalStateException("The state was " + state + ".");
		}
	}

	public void send() {
		if (state == ConnectionState.LISTEN) {
			state = ConnectionState.SYN_SENT;
		} else {
			throw new IllegalStateException("The state was " + state + ".");
		}
	}

	public void syn() {
		if (state == ConnectionState.LISTEN || state == ConnectionState.SYN_SENT) {
			state = ConnectionState.SYN_RECEIVED;
		} else {
			throw new IllegalStateException("The state was " + state + ".");
		}
	}
	
	public void synAck() {
		if (state == ConnectionState.SYN_SENT) {
			state = ConnectionState.ESTABLISHED;
		} else {
			throw new IllegalStateException("The state was " + state + ".");
		}
	}
	
	public void timeout() {
		if (state == ConnectionState.TIME_WAIT) {
			state = ConnectionState.CLOSED;
		} else {
			throw new IllegalStateException("The state was " + state + ".");
		}
	}
	
	public T getSender() {
		return sender;
	}

	public void setSender(T sender) {
		if (state != ConnectionState.CLOSED)
			throw new IllegalStateException("The state was not" + ConnectionState.CLOSED + ".");
		this.sender = sender;
	}

	public T getReceiver() {
		return receiver;
	}

	public void setReceiver(T receiver) {
		if (state != ConnectionState.CLOSED)
			throw new IllegalStateException("The state was not" + ConnectionState.CLOSED + ".");
		this.receiver = receiver;
	}

	public ConnectionState getState() {
		return state;
	}
	
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o.getClass() != this.getClass())
			return false;
		Connection<T> that = (Connection<T>) o;
		if (sender != that.sender) {
			if (sender == null)
				return false;
			else if (!sender.equals(that.sender))
				return false;
		}
		if (receiver != that.receiver) {
			if (receiver == null)
				return false;
			else if (!receiver.equals(that.receiver))
				return false;
		}
		return true;
	}

}
