package com.div.diff.scraper.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class RestClient {
	
	private final String USER_AGENT = "Mozilla/5.0";
	private HttpClient client; 
	
	public RestClient() {
		this.client = HttpClientBuilder.create().build();
	}
	
	public String getHtmlFromSite(String url) throws ClientProtocolException, IOException {
		StringBuilder html = new StringBuilder();
		HttpGet request = new HttpGet(url);

		// add request header
		request.addHeader("User-Agent", USER_AGENT);
		HttpResponse response = client.execute(request);

		System.out.println("Response Code : " 
	                + response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(
			new InputStreamReader(response.getEntity().getContent()));
		
		String line = "";
		while ((line = rd.readLine()) != null) {
			html.append(line);
		}
		return html.toString();
	}
}
