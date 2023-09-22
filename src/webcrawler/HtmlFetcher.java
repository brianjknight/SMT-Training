package webcrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
		URL admintoolUrl = new URL("https://smt-stage.qa.siliconmtn.com/sb/admintool?cPage=stats&actionId=FLUSH_CACHE");
				
		String admintoolHTML = fetcher.fetchHtml(admintoolUrl, username, password);
		Webpage adminttoolPage = new Webpage(admintoolUrl, admintoolHTML, new HtmlParser());
		
		System.out.println("Title: " + adminttoolPage.getTitle());
		System.out.println("HTML: \n" + adminttoolPage.getHtml());
	}

	private Map<String, Set<String>> cookies = new HashMap<>();
	/**
	 * Method to visit a webpage and return a string of HTML for the page.
	 * @param url - URL from which HTML is returned
	 * @param username - empty string or username if required for login
	 * @param password - empty string or password if required for login
	 * @return
	 */
	public String fetchHtml(URL url, String username, String password) {
		StringBuilder html = new StringBuilder();
		
		// Use socket to send POST request to login and retrieve cookies.
        try (Socket socket = createSocket(url)) {
    		OutputStream out = socket.getOutputStream();
    		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    		
    		sendPostRequest(socket, out, in, url, username, password);
    		
    		socket.close();
    		in.close();       	

        } catch (Exception e) {
            System.out.println(e);
        }
        
        // TODO refactor as helper method to call in fetchHtml
        // Once cookies are available send GET request to retrieve the HTML
        try (Socket socket = createSocket(url)) {
        	OutputStream out = socket.getOutputStream();
        	BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        	
        	out.write(("GET " + url.getFile() + " HTTP/1.1\r\n").getBytes());
        	out.write(("Host: " + url.getHost() + "\r\n").getBytes());
        	
        	// add cookies to header
        	if (!cookies.isEmpty()) {
        		StringBuilder cookieValues = new StringBuilder();
        		for (String c : cookies.get(url.getHost())) {
        			cookieValues.append(c.substring(12, c.indexOf(" ", 12)) + " ");
        		}
        		
        		out.write(("Cookie: " + cookieValues.toString() + "\r\n").getBytes());
        	}
        	
        	out.write("Connection: close\r\n\r\n".getBytes());
        	out.write("\r\n".getBytes());
        	out.flush();
        	
        	// write the html
        	String line = null;
        	while ((line = in.readLine()) != null) {
        		html.append(line);
        		html.append("\n");
        	}
        	
        	socket.close();
        	in.close();
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
	
	/**
	 * Uses a socket to send a post request with login credentials and retrieve cookies for a session.
	 * @param socket
	 * @param out
	 * @param url
	 * @param username
	 * @param password
	 * @throws IOException
	 */
	private void sendPostRequest(Socket socket, OutputStream out, BufferedReader in, URL url, String username, String password) throws IOException {
        String postRequestBody = "requestType=" + URLEncoder.encode("reqBuild", "UTF-8") +
                "&pmid=" + URLEncoder.encode("ADMIN_LOGIN", "UTF-8") +
                "&emailAddress=" + URLEncoder.encode(username, "UTF-8") +
                "&password=" + URLEncoder.encode(password, "UTF-8") +
                "&l=" + URLEncoder.encode("", "UTF-8");

        String requestHeaders = "POST " + url.getFile() + " HTTP/1.1\r\n" +
                "Host: " + url.getHost() + "\r\n" +
                "Content-Length: " + postRequestBody.length() + "\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "\r\n" + postRequestBody + "\r\n" +
                "Connection: close\r\n\r\n";

        out.write(requestHeaders.getBytes());
        out.flush();
        
        // save cookies
        String line;
        while ((line = in.readLine()) != null) {
            if (!cookies.containsKey(url.getHost())) {
                cookies.put(url.getHost(), new HashSet<>());
            }
            if (line.startsWith("Set-Cookie")) {
                cookies.get(url.getHost()).add(line);
            }
        }
    }
	
	/**
	 * Uses a socket to send a GET request with cookies to retrieve HTML of a webpage.
	 * @param socket
	 * @param out
	 * @param in
	 * @param url
	 * @param html
	 * @throws IOException
	 */
	private void sendGetRequest(Socket socket, OutputStream out, BufferedReader in, URL url, StringBuilder html) throws IOException {
		// TODO create method
    }
}
