package testPackage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/****************************************************************************
 * <b>Title:</b> URLTest.java
 * <b>Project:</b> brian.training
 * <b>Description:</b> CHANGE ME!!
 * <b>Copyright:</b> Copyright (c) 2023
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Brian Knight
 * @version 3.x
 * @since Sep 2, 2023
 * <b>updates:</b>
 *  
 ****************************************************************************/

public class URLTest {
	public static void main(String[] args) throws IOException {
		URL url;
		
		try {
			url = new URL("https://smt-stage.qa.siliconmtn.com/about");
			System.out.println("url: " + url);
			System.out.println("protocol: " + url.getProtocol());
			System.out.println("host: " + url.getHost());
			System.out.println("path: " + url.getPath());
			System.out.println("defaultPort: " + url.getDefaultPort());
			System.out.println("content: " + url.getContent());
			System.out.println("file: " + url.getFile());
			
			
		} catch (MalformedURLException e) {
			System.out.println(e);
		}
		
		
	}
	
		
}
