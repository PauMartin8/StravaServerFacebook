package es.deusto.ingenieria.sd.strava.socketServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * This class process the request of each client as a separated Thread.
 */
public class EchoService extends Thread {
	private DataInputStream in;
	private DataOutputStream out;
	private Socket tcpSocket;
	private Map<String, String> mapa;

	public EchoService(Socket socket) {
		try {
			this.tcpSocket = socket;
		    this.in = new DataInputStream(socket.getInputStream());
			this.out = new DataOutputStream(socket.getOutputStream());
			this.start();
			mapa = new HashMap<>();
			//mapa.put("1@gmail.com", "12345");
			mapa.put("2@gmail.com", "12445");
		} catch (Exception e) {
			System.out.println("# Facebook Server - TCPConnection IO error:" + e.getMessage());
		}
	}

	public void run() {
		//Echo server
		try {
			//Read request from the client
			String data = this.in.readUTF();	
			String data1 = this.in.readUTF();
			System.out.println("   - Facebook Server - Received data from '" + tcpSocket.getInetAddress().getHostAddress() + ":" + tcpSocket.getPort() + "' -> '" + data + "'");		
			//Send response to the client
			if(mapa.containsKey(data)) {
				if(mapa.get(data).equals(data1)) {
					data = "true";
				} else {
					data = "false";
				}
			} else {
				data = "false";
			}
			/*
			 * Comprobramos el mail y contr en el mapa de mails y contraseñas y 
			 * si todo da bien devuelve true o false	
			 * 
			 * data = true/false
			 * 
			 this.out.writeUTF(data);
			 */
			this.out.writeUTF(data);
			System.out.println("   - Facebook Server - Sent data to '" + tcpSocket.getInetAddress().getHostAddress() + ":" + tcpSocket.getPort() + "' -> '" + data.toUpperCase() + "'");
		} catch (Exception e) {
			System.out.println("   # Facebook Server error" + e.getMessage());
		} finally {
			try {
				tcpSocket.close();
			} catch (Exception e) {
				System.out.println("   # Facebook Server error:" + e.getMessage());
			}
		}
	}
}