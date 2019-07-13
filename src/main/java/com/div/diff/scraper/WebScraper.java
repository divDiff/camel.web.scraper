package com.div.diff.scraper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.div.diff.scraper.domain.Page;
import com.div.diff.scraper.domain.SiteMetadata;
import com.div.diff.scraper.utils.ConnectionManager;
import com.div.diff.scraper.utils.PageManager;
import com.div.diff.scraper.utils.RestClient;
import com.div.diff.scraper.utils.UrlMatcher;

public class WebScraper {

	private static RestClient rc;
	private static PageManager pm;
	private static ConnectionManager cm;

	public static void main(String[] args) throws ClientProtocolException, IOException {
		System.out.println("Beginning site scrape of " + args[1]);
		rc = new RestClient();
		pm = new PageManager();
		cm = new ConnectionManager();
		pm.setup();
		cm.setup();

		scrapePage(args[1]);
	}

	private static void scrapePage(String url) throws ClientProtocolException, IOException {
		String content = rc.getHtmlFromSite(url);
		LocalDate now = LocalDate.now();

		SiteMetadata site = new SiteMetadata();
		site.setScrapeDate(now);
		site.setSiteContent(content);
		site.setSiteUrl(url);

		List<Page> sitePages = UrlMatcher.makePagesFromContent(site);
		List<Page> newPages = pm.addNewPages(sitePages, url);
		cm.makeConnections(site, newPages);
		for (Page p : newPages) {
			scrapePage(p.getUrl());
		}
	}
}
