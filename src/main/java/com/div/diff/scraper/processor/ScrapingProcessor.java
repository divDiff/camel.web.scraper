package com.div.diff.scraper.processor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.div.diff.scraper.domain.Page;
import com.div.diff.scraper.domain.SiteMetadata;
import com.div.diff.scraper.utils.UrlMatcher;

public class ScrapingProcessor implements Processor {

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
		return foundPages;
	}

}
