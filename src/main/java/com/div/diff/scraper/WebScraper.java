package com.div.diff.scraper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
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
	private static int count = 0;

	public static void main(String[] args) throws ClientProtocolException, IOException, InterruptedException {
		System.out.println("Beginning site scrape of " + args[0]);
		rc = new RestClient();
		pm = new PageManager();
		cm = new ConnectionManager();
		pm.setup();
		cm.setup();
		UrlMatcher.setDomain(args[0]);

		SiteMetadata site = gatherSitemetadata(args[0]);
		pm.addSinglePage(site);
		scrapePage(site);
	}

	private static void scrapePage(SiteMetadata site)
			throws ClientProtocolException, IOException, InterruptedException {
		List<Page> sitePages = UrlMatcher.makePagesFromContent(site);
		sitePages = removeDuplicates(sitePages);
		List<Page> newPages = pm.addNewPages(sitePages, site.getSiteUrl());
		Page page = pm.getPage(site.getSiteUrl());

		cm.makeConnections(page, newPages);
		for (Page p : newPages) {
			count++;
			System.out.println("Sleeping 30 seconds... " + count + " times");
			Thread.sleep(30000);
			site = gatherSitemetadata(p.getUrl());
			scrapePage(site);
		}
	}

	private static SiteMetadata gatherSitemetadata(String url) throws ClientProtocolException, IOException {
		String content = rc.getHtmlFromSite(url);
		LocalDate now = LocalDate.now();
		SiteMetadata site = new SiteMetadata();
		site.setScrapeDate(now);
		site.setSiteContent(content);
		site.setSiteUrl(url);
		return site;
	}

	private static List<Page> removeDuplicates(List<Page> pages) {
		List<Page> noDupes = new ArrayList<>();
		for (Page p : pages) {
			if (!noDupes.contains(p)) {
				noDupes.add(p);
			}
		}
		return noDupes;
	}
}
