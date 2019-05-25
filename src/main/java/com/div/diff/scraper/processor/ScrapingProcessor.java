package com.div.diff.scraper.processor;

import java.time.LocalDate;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.div.diff.scraper.domain.SiteMetadata;
import com.div.diff.scraper.jdbc.SqlEngine;

public class ScrapingProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		SqlEngine sql = new SqlEngine();
		
		String content = exchange.getIn().getBody(String.class);
		LocalDate now = LocalDate.now();
		
		SiteMetadata site = new SiteMetadata();
		site.setScrapeDate(now);
		site.setSiteContent(content);
		site.setSiteUrl(exchange.getIn().getHeader("url", String.class));
		
		System.out.println("Site content is : " + content);
	}

}
