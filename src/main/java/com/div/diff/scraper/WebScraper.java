package com.div.diff.scraper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.div.diff.scraper.domain.Page;
import com.div.diff.scraper.domain.SiteMetadata;
import com.div.diff.scraper.utils.PageManager;
import com.div.diff.scraper.utils.RestClient;
import com.div.diff.scraper.utils.UrlMatcher;

public class WebScraper {

	private static RestClient rc;
	private static PageManager pm;

	public static void main(String[] args) throws ClientProtocolException, IOException {
		System.out.println("Beginning site scrape of " + args[1]);
		rc = new RestClient();
		pm = new PageManager();
		pm.setup();

		scrapePage(args[1]);
	}

	private static void scrapePage(String url) throws ClientProtocolException, IOException {
		String content = rc.getHtmlFromSite(url);
		LocalDate now = LocalDate.now();

		SiteMetadata site = new SiteMetadata();
		site.setScrapeDate(now);
		site.setSiteContent(content);
		site.setSiteUrl(url);

		List<Page> newPages = UrlMatcher.makePagesFromContent(site);
		pm.addNewPages(newPages, url);

		for (Page p : newPages) {
			scrapePage(p.getUrl());
		}
	}
}
