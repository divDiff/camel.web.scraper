package com.mike.maryon.camel.web.scrapper;

import java.io.File;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import com.div.diff.scraper.route.ScrapingRoute;

public class ScrapingRouteTest extends CamelTestSupport {
	
	@Override
	public RoutesBuilder createRouteBuilder() {
		return new ScrapingRoute();
	}
	
	@Test
	public void scraperTest() throws InterruptedException {
		String input = "w3schools.com";
		String expected = "123:mike:30MAR2019\n" + 
				"23454:joe:30MAR2019\n";
		MockEndpoint mock = getMockEndpoint("mock:output");
		mock.expectedBodiesReceived(expected);
		Thread.sleep(5000);
		
		File file = new File("data/output");
		assertTrue(file.isDirectory());
		assertMockEndpointsSatisfied();
	}
}
