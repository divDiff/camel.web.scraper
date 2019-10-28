package com.div.diff.scraper.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class RestClient {

	private final String USER_AGENT = "Mozilla/5.0";
	private HttpClient client;

	public RestClient() {
		this.client = HttpClientBuilder.create().build();
	}

	public String getHtmlFromSite(String url, String parent) throws ClientProtocolException, IOException {
		StringBuilder html = new StringBuilder();

		HttpGet request = makeGet(url);
		HttpResponse response = client.execute(request);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		if (response.getStatusLine().getStatusCode() == 404) {
			if (parent.contains("default.asp")) {
				String branchPiece = parent.split("/default.asp")[0];
				String partToTry = branchPiece.substring(branchPiece.lastIndexOf("/"), branchPiece.length());
				System.out.println(
						"404 not found returned... Trying to append " + partToTry + " from parent page before *.asp");
				StringBuilder newUrl = new StringBuilder(url);
				newUrl.insert(newUrl.lastIndexOf("/"), partToTry);
				HttpGet nextRequest = makeGet(newUrl.toString());
				HttpResponse nextResponse = client.execute(nextRequest);
				if (nextResponse.getStatusLine().getStatusCode() == 404) {
					System.out.println("Still got a 404 for " + newUrl.toString() + "...");
				}
				rd = new BufferedReader(new InputStreamReader(nextResponse.getEntity().getContent()));
			}
		}

		String line = "";
		while ((line = rd.readLine()) != null) {
			html.append(line);
		}
		EntityUtils.consumeQuietly(response.getEntity());
		rd.close();
		return html.toString();
	}

	private HttpGet makeGet(String url) {
		HttpGet request = new HttpGet(url);

		RequestConfig config = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000)
				.setConnectionRequestTimeout(5000).build();
		request.setConfig(config);

		// add request header
		request.addHeader("User-Agent", USER_AGENT);
		return request;
	}
}
