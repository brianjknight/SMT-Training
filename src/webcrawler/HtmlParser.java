package webcrawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


/****************************************************************************
 * <b>Title:</b> HtmlParser.java
 * <b>Project:</b> brian.training
 * <b>Description:</b> Class to parse a string of HTML.
 * <b>Copyright:</b> Copyright (c) 2023
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Brian Knight
 * @version 3.x
 * @since Aug 31, 2023
 * <b>updates:</b>
 *  
 ****************************************************************************/

public class HtmlParser {
	
	/**
	 * Finds all elements for the given tag in the given HTML string. 
	 * @param html
	 * @param tag
	 * @return
	 */
	public List<Element> parseForTags(String html, String tag) {
		Document doc = Jsoup.parse(html);
		
		return doc.getElementsByTag(tag);
	}
	
	/**
	 * Find the title of the page for the HTML.
	 * @param html
	 * @return
	 */
	public String parseTitle(String html) {
		Document doc = Jsoup.parse(html);
		
		return doc.selectFirst("title").text();
	}
	
	
}
