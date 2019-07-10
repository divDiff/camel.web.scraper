package com.div.diff.scraper.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.div.diff.scraper.domain.Page;
import com.div.diff.scraper.domain.SiteMetadata;

public class UrlMatcher {

	private String https = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)\n";
	private String domain = "";
	private static String aTagRegex = "(<a ([a-zA-Z0-9=\\?\\\"\\'/\\.\\s\\-\\_]*>([a-zA-Z0-9\\s\\.\\+\\-\\_\\&\\;]*)<\\/a>))";
	private static String href = "(href=([a-zA-Z0-9\\\"\\'/\\.\\_\\?\\=]*))";
	
	public static List<Page> makePagesFromContent(SiteMetadata meta) {
		List<String> links = grabLinks(meta);
		List<Page> pages = new ArrayList<>();
		for (String link : links) {
			Page p = new Page();
			p.setDomain(meta.getSiteUrl());
			p.setPageName(link);
			p.setUrl(inferNewUrl(link, meta.getSiteUrl()));
			pages.add(p);
		}
		System.out.println("Total Links : " + links.size());
		return pages;
	}
	
	private static List<String> grabLinks(SiteMetadata meta) {
		List<String> aTags = new ArrayList<>();
		Pattern aTagPatt = Pattern.compile(aTagRegex);
		Matcher m = aTagPatt.matcher(meta.getSiteContent());
		while(m.find()) {
			aTags.add(m.group());
		}
		return aTags;
	}
	
	private static String inferNewUrl(String aTag, String domain) {
		Pattern hrefPatt = Pattern.compile(href);
		Matcher m = hrefPatt.matcher(aTag);
		StringBuilder newUrl = new StringBuilder("https://" + domain);
		while (m.find()) {
			String hrefContents = m.group(2);
			hrefContents = hrefContents.replace("\"", "");
			hrefContents = hrefContents.replace("\'", "");
			newUrl.append(hrefContents);
		}
		return newUrl.toString();
	}
	
	public String getHttps() {
		return https;
	}
	public void setHttps(String https) {
		this.https = https;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getaTagRegex() {
		return aTagRegex;
	}
	public void setaTagRegex(String aTagRegex) {
		this.aTagRegex = aTagRegex;
	}
}
