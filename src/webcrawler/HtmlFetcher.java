package webcrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Base64;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/****************************************************************************
 * <b>Title:</b> HtmlFetcher.java
 * <b>Project:</b> brian.training
 * <b>Description:</b> Class to visit a webpage using a socket and retrieve its HTML.
 * <b>Copyright:</b> Copyright (c) 2023
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Brian Knight
 * @version 3.x
 * @since Aug 31, 2023
 * <b>updates:</b>
 *  
 ****************************************************************************/

public class HtmlFetcher {
	
	public static void main(String[] args) throws MalformedURLException {
		HtmlFetcher fetcher = new HtmlFetcher();
		
		String username = System.getenv("WC_USERNAME");
		String password = System.getenv("WC_PASSWORD");
		URL cacheStatsURL = new URL("https://smt-stage.qa.siliconmtn.com/sb/admintool?cPage=stats&actionId=FLUSH_CACHE");
		
		String cacheStatsHtml = fetcher.fetchHtml(cacheStatsURL, username, password);
		Webpage cacheStatsPage = new Webpage(cacheStatsURL, cacheStatsHtml, new HtmlParser());
		
		System.out.println("Title: " + cacheStatsPage.getTitle());
		System.out.println("HTML: \n" + cacheStatsPage.getHtml());
	}

	/**
	 * Method to visit a webpage and return a string of HTML for the page.
	 * @param url - URL from which HTML is returned
	 * @param username - empty string or username if required for login
	 * @param password - empty string or password if required for login
	 * @return
	 */
	public String fetchHtml(URL url, String username, String password) {
		StringBuilder html = new StringBuilder();
        
        try (Socket socket = createSocket(url)) {	
            OutputStream out = socket.getOutputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            String authHeader = "Authorization: Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
            out.write(("GET " + url.getPath() + " HTTP/1.1\r\n").getBytes());
            out.write(("Host: " + url.getHost() + "\r\n").getBytes());
            out.write("Connection: close\r\n\r\n".getBytes());
            out.write(authHeader.getBytes());
            out.write("\r\n\r\n".getBytes());
            out.flush();
            
            String line = null;
            while ((line = in.readLine()) != null) {
                html.append(line);
                html.append("\n");
            }
            
            socket.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return html.toString();
	}
	
	/**
	 * Returns socket type based on URL protocol.
	 * @param url
	 * @return
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private Socket createSocket(URL url) throws UnknownHostException, IOException {
		if (url.getProtocol().equals("https")) {
			SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			return sslSocketFactory.createSocket(url.getHost(), url.getDefaultPort());
		} else {
			return new Socket(url.getHost(), url.getDefaultPort());
		}
	}
}
