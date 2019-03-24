package com.mike.maryon.camel.web.scrapper;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import com.mike.maryon.camel.web.scrapper.route.ScrapingRoute;

public class ScrapingRouteTest extends CamelTestSupport {
	
	@Override
	public RoutesBuilder createRouteBuilder() {
		return new ScrapingRoute();
	}
	
	@Test
	public void scraperTest() {
		String input = "w3schools.com";
		template.sendBody("direct:startScrape", input);
	}
}
