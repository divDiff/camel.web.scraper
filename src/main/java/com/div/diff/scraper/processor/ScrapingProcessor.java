package com.div.diff.scraper.processor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.div.diff.scraper.domain.Page;
import com.div.diff.scraper.domain.SiteMetadata;
import com.div.diff.scraper.utils.PageManager;
import com.div.diff.scraper.utils.UrlMatcher;

public class ScrapingProcessor implements Processor {

	private PageManager pm;
	
	{
		pm = new PageManager();
		pm.setup();
	}
	
	@Override
	public void process(Exchange exchange) throws Exception {
//		SqlEngine sql = new SqlEngine();
		
		String content = exchange.getIn().getBody(String.class);
		LocalDate now = LocalDate.now();
		
		SiteMetadata site = new SiteMetadata();
		site.setScrapeDate(now);
		site.setSiteContent(content);
		site.setSiteUrl(exchange.getIn().getHeader("url", String.class));
		
		List<Page> newPages = findNewPages(site);
	}
	 
	private List<Page> findNewPages(SiteMetadata site) {
		List<Page> foundPages = UrlMatcher.makePagesFromContent(site);
		pm.addNewPages(foundPages, site.getSiteUrl());
		return foundPages;
	}

}
