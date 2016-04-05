/****************************************************************************************
 * A basic example of a TCP/IP client acting as a listener.                             *
 * This class opens a socket and listens on a port for incoming connection requests     *
 * Bill Nicholson                                                                       *
 * nicholdw@ucmail.uc.edu                                                               *
 ****************************************************************************************/
package listenerPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener extends Thread {
	private int port;
	private Socket clientSocket;
	PrintWriter out;
	String name;		// A semantic name so we have something to play with
	/**
	 * Constructor
	 * @param port The port number to listen on
	 */
	public Listener(int port, String name) {
		this.port = port;
		this.name = name;
	}
	/**
	 * The entry point for the thread
	 */
	public void run() {
		BufferedReader in;
        ServerSocket myServerSocket = null;  // A listener
        String msg = null;
        while (true) {
            try {
            	// It's probably not open, but try to close it anyway.
                try {myServerSocket.close();} catch(Exception ex){}
//    	        Grab the port. 
                myServerSocket = new ServerSocket(port); // bind to the port
            } catch (Exception ex) {
                System.out.println("run new ServerSocket: " + ex.getMessage());
                continue;
            }
            try {
//	            Listen for a connection request. If the request doesn't come
//	              within 2 seconds, this accept() will time out and throw an exception.
                myServerSocket.setSoTimeout(2000);            // Wait 2 second, then unblock
                clientSocket = myServerSocket.accept();       // Wait for a client
            } catch (Exception ex) {
//	        	The accept() timed out. The loop will start over.
                //System.out.println(name + ": Connection timeout. " + ex.getLocalizedMessage());
            	try {myServerSocket.close();} catch (IOException e) {
            		continue;
            	}
            	continue;
            }
//			If we get this far, we have accepted a connection from another host.
            System.out.println(name + ": Connection recieved from " + clientSocket.getRemoteSocketAddress());
            try {
	            out = new PrintWriter(clientSocket.getOutputStream(), true);
	            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (Exception ex) {
                System.out.println(name + ": Unable to open streams on the socket: " + ex.getLocalizedMessage());
            	continue;
            }
            //out.write("Connection received. Welcome to " + name + ". Type Quit when finished.");
            ObjectInputStream ois = null;
            try {
            	ois = new ObjectInputStream(clientSocket.getInputStream());
        		System.out.println("Waiting for incoming messages...");
            	while (true) {
           			try {msg = (String) ois.readObject();} catch (Exception ex) {}
            		if (msg != null && !(msg.equals(""))) System.out.println("Incoming message = " + msg);
            		//out.write("OK");
            		if (msg.equals("Quit")) {
            			try {myServerSocket.close();} catch (Exception ex){}	// eat it
            			break;
            		}
            		msg = "";
                	//ois = new ObjectInputStream(clientSocket.getInputStream()); 
            	}
            } catch (Exception ex) {
            	System.out.println("Error reading message from remote host: " + ex.getLocalizedMessage());	
    			try {myServerSocket.close();} catch (IOException e) {} // eat it
    		}
        }
    }
}
