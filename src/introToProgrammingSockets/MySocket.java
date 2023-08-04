package introToProgrammingSockets;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/****************************************************************************
 * <b>Title:</b> MySocket.java
 * <b>Project:</b> brian.training
 * <b>Description:</b> CHANGE ME!!
 * <b>Copyright:</b> Copyright (c) 2023
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Brian Knight
 * @version 3.x
 * @since Aug 3, 2023
 * <b>updates:</b>
 *  
 ****************************************************************************/

public class MySocket {

	public static void main(String[] args) {
		MySocket mySocket = new MySocket();
		
		String apache2DefaultHtml = mySocket.getWebPage("localhost",80);
		System.out.println(apache2DefaultHtml);
	}
	
	public String getWebPage(String host, int portNumber) {
		StringBuilder html = new StringBuilder();
		
		try (Socket socket = new Socket(host, portNumber)) {
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			out.writeBytes("GET / HTTP/1.1\r\n");
			out.writeBytes("Host: " + host + "\r\n");
			out.writeBytes("Connection: close\r\n\r\n");
			out.flush();
			
			String line = null;
			while ((line=in.readLine())!=null) {
				html.append(line);
				html.append("\n");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return html.toString();
	}
	
}
