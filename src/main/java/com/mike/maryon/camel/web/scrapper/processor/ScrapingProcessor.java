package com.mike.maryon.camel.web.scrapper.processor;

import java.time.LocalDate;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.mike.maryon.camel.web.scrapper.domain.SiteMetadata;

public class ScrapingProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		String content = exchange.getIn().getBody(String.class);
		LocalDate now = LocalDate.now();
		
		SiteMetadata site = new SiteMetadata();
		site.setScrapeDate(now);
		site.setSiteContent(content);
		site.setSiteUrl(exchange.getIn().getHeader("url", String.class));
		
		System.out.println("Site content is : " + content);
	}

}
