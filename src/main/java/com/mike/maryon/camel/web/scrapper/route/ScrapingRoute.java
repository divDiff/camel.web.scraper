package com.mike.maryon.camel.web.scrapper.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import com.mike.maryon.camel.web.scrapper.processor.ScrapingProcessor;

public class ScrapingRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		String url = "camel.apache.org";
		from("direct:startScrape")
			.log("Before Rest Call to " + url)
			.setHeader(Exchange.HTTP_METHOD, simple("GET"))
			.setHeader("url", simple(url))
			.to("http://" + url).process(new ScrapingProcessor())
			.log("After Rest Call");
	}
}
