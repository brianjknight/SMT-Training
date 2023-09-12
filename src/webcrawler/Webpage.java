package webcrawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;

/****************************************************************************
 * <b>Title:</b> Webpage.java
 * <b>Project:</b> brian.training
 * <b>Description:</b> Webpage class with fields for the title, URL, and HTML of a webapge.
 * <b>Copyright:</b> Copyright (c) 2023
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Brian Knight
 * @version 3.x
 * @since Aug 31, 2023
 * <b>updates:</b>
 *  
 ****************************************************************************/

public class Webpage {
	
	private String title;
	private URL url;
	private String html;
	private HtmlParser parser;
	
	/**
	 * All args constructor.
	 * @param url - URL for the webpage
	 * @param html - the pages HTML
	 * @param parser - Parser object dependency for parsing the HTML
	 */
	public Webpage(URL url, String html, HtmlParser parser) {
		this.url = url;
		this.html = html;
		this.parser = parser;
		title = parser.parseTitle(html);
	}
	
	/**
	 * Finds all elements in the pages HTML for the given tag.
	 * @param tag - HTML tag to parse for.
	 * @return
	 */
	public List<Element> parseTags(String tag) {
		return parser.parseForTags(html, tag);
	}
	
	/**
	 * Uses parseTags method to find <a> tags that have links within the webpage domain.
	 * @return
	 */
	public List<String> findDomainLinks() {
		List<String> domainLinks = new ArrayList<>();
		
		List<Element> aTags = parseTags("a");

		for (Element e : aTags) {
			String href = e.attr("href");
			if (href.startsWith("http") && href.contains(url.getHost())) {
				domainLinks.add(href);
			} else if (href.startsWith("/")) {
				domainLinks.add(url.getProtocol() + "://" + url.getHost() + href);
			}
		}
		
		return domainLinks;
	}

	public String getTitle() {
		return title;
	}

	public URL getURL() {
		return url;
	}

	public String getHtml() {
		return html;
	}	
	
}
