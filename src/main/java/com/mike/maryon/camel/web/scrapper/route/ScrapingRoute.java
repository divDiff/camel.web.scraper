package com.mike.maryon.camel.web.scrapper.route;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import com.mike.maryon.camel.web.scrapper.processor.ScrapingProcessor;

public class ScrapingRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("src/main/resources/urls.txt"));
		String line = "";
		List<String> urls = new ArrayList<>();
		while ((line = br.readLine()) != null) {
			urls.add(line);
		}
		
		for (String url : urls) {
			from("direct:hitUrl")
				.log("Before Rest Call to " + url)
				.setHeader(Exchange.HTTP_METHOD, simple("GET"))
				.setHeader("url", simple(url))
				.to(url).process(new ScrapingProcessor())
				.log("After Rest Call");
		}
	}
}
