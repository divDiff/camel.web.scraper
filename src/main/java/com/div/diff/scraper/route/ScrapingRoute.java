package com.div.diff.scraper.route;

import org.apache.camel.Exchange;
import org.apache.camel.ShutdownRunningTask;
import org.apache.camel.builder.RouteBuilder;

import com.div.diff.scraper.processor.ScrapingProcessor;

public class ScrapingRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		String url = "w3schools.com";
		from("file:data/input?noop=true&antInclude=links.txt&delay=5000")
			.log("Before Rest Call to " + url)
			.log("File contents are ${body}")
			.shutdownRunningTask(ShutdownRunningTask.CompleteAllTasks)
			.setHeader(Exchange.HTTP_METHOD, simple("GET"))
			.setHeader("url", simple(url))
			.to("http://" + url).process(new ScrapingProcessor())
			.log("After Rest Call")
			.to("file:data/output");
//			.to("mock:output");
	}
}
