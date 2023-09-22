package webcrawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/****************************************************************************
 * <b>Title:</b> WebpageCrawler.java
 * <b>Project:</b> brian.training
 * <b>Description:</b> Class that will search a website to find all linked pages within the website's domain.
 * <b>Copyright:</b> Copyright (c) 2023
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Brian Knight
 * @version 3.x
 * @since Aug 31, 2023
 * <b>updates:</b>
 *  
 ****************************************************************************/

public class WebpageCrawler {
	public static void main(String[] args) throws MalformedURLException {
		
		WebpageCrawler crawler = new WebpageCrawler(new HtmlFetcher(), new HtmlParser());
		URL smtHome = new URL("https://smt-stage.qa.siliconmtn.com/");
		
		System.out.println("STARTING CRAWL...\n");
		
		crawler.crawl(smtHome, "", "");
		
		System.out.println("CRAWL FINISHED");
		
		List<Webpage> webpages = crawler.getWebpages();
		System.out.println("-".repeat(200));
		System.out.println("Printing the webpages found: \n");
		for (Webpage w : webpages) {
			System.out.println("URL: " + w.getURL() + "\tTITLE: " + w.getTitle());
		}
		
		System.out.println("-".repeat(200));
		System.out.println("Printing HTML for \"About Us\" page: \n");
		System.out.println(crawler.getHtmlForPage("https://smt-stage.qa.siliconmtn.com/about"));
		
	}
	
	private Set<String> visitedLinks;
	private Queue<String> notVisitedLinks;
	private List<Webpage> webpages;
	private HtmlFetcher fetcher;
	private HtmlParser parser;
	
	/**
	 * Constructor with dependencies injected.
	 * @param fetcher - class to retrieve HTML of a webpage
	 * @param parser - class to parse HTML
	 */
	public WebpageCrawler(HtmlFetcher fetcher, HtmlParser parser) {
		visitedLinks = new HashSet<>();
		notVisitedLinks = new LinkedList<>();
		webpages = new ArrayList<>();
		this.fetcher = fetcher;
		this.parser = parser;
	}
		
	public Set<String> getVisitedLinks() {
		return visitedLinks;
	}

	public List<Webpage> getWebpages() {
		return webpages;
	}
	
	/**
	 * Returns the HTML of a single webpage after the website has been crawled.
	 * @param url - URL for which to get the HTML
	 * @return
	 */
	public String getHtmlForPage(String url) {
		for (Webpage w : getWebpages()) {
			if (w.getURL().toString().equals(url)) {
				return w.getHtml();
			}
		}
		return "";
	}
 
	/**
	 * Method to call to initiate crawling the website
	 * @param url - starting URL to crawl from.
	 * @param username - empty string or username if required for login
	 * @param password - empty string or password if required for login
	 * @throws MalformedURLException
	 */
	public void crawl(URL url, String username, String password) throws MalformedURLException {
		updateCollections(url, username, password);
		
		while (!notVisitedLinks.isEmpty()) {
			URL nextUrl = new URL(notVisitedLinks.poll());
			updateCollections(nextUrl, username, password);
		}
	}
	
	/**
	 * Updates the webcrawler with new and visited links for a single webpage.
	 * @param url - URL of the page to search.
	 * @param username - empty string or username if required for login
	 * @param password - empty string or password if required for login
	 */
	private void updateCollections(URL url, String username, String password) {
		String html = fetcher.fetchHtml(url, username, password);
		Webpage page = new Webpage(url, html, parser);
		
		visitedLinks.add(url.toString());
		webpages.add(page);
		
		List<String> linksForPage = page.findDomainLinks();
		
		for (String link : linksForPage) {
			if (!visitedLinks.contains(link) && !notVisitedLinks.contains(link)) {
				notVisitedLinks.offer(link);
			}
		}
	}
}
