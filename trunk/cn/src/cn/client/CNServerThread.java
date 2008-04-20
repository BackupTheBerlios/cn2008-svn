/**
 * CNServerThread
 *
 * CN Server thread which should handle all stuff regarding server-activities.
 *
 */

package cn.client;


import java.io.*;
import java.net.*;
import java.util.*;

import cn.rudp.*;

/**
 * @author Juergen Repolusk  juergen dot repolusk at gmail dot com 
 * 
 */


public class CNServerThread extends Thread {

    protected DatagramSocket socket = null;
    protected BufferedReader in = null;
    protected boolean morePackages = true;

    public CNServerThread() throws IOException {
	this("CNServerThread");
    }

    public CNServerThread(String name) throws IOException {
        super(name);
        socket = new DatagramSocket(4445);

        try {
		// TODO Add code
        } catch (Exception e) {
		// TODO Add code
        }
    }

    public void run() {

        while (morePackages) {
            try {
	    	// TODO stuff to change since we have reliable package classes
                byte[] buf = new byte[256];

                    // receive request
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                    // figure out response
                String dString = null;
                if (in == null)
                    dString = new Date().toString();
                else
                    dString = getNextPackage();
                buf = dString.getBytes();

		    // send the response to the client at "address" and "port"
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
		morePackages = false;
            }
        }
        socket.close();
    }

    protected String getNextPackage() {
        String returnValue = null;
        try {
            if ((returnValue = in.readLine()) == null) {
                in.close();
		morePackages = false;
                returnValue = "No more packages. Goodbye.";
            }
        } catch (IOException e) {
            returnValue = "IOException occurred in server.";
        }
        return returnValue;
    }
}
