package com.div.diff.scraper.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.div.diff.scraper.domain.Page;
import com.div.diff.scraper.domain.SiteMetadata;

public class UrlMatcher {

	private String https = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)\n";
	private String domainSpecific = "";
	private static String aTagRegex = "(<a ([a-zA-Z0-9=\\?\\\"\\'/\\.\\s\\-\\_]*>([a-zA-Z0-9\\s\\.\\+\\-\\_\\&\\;]*)<\\/a>))";
	
	public static List<Page> makePagesFromContent(SiteMetadata meta) {
		List<String> links = grabLinks(meta);
		for (String link : links) {
			Page p = new Page();
			p.setDomain(link);
			p.setPageName(link);
			p.setUrl(inferNewUrl(link));
		}
		return null;
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
	
	private static String inferNewUrl(String aTag) {
		
		return null;
	}
	
	public String getHttps() {
		return https;
	}
	public void setHttps(String https) {
		this.https = https;
	}
	public String getDomainSpecific() {
		return domainSpecific;
	}
	public void setDomainSpecific(String domainSpecific) {
		this.domainSpecific = domainSpecific;
	}
	public String getaTagRegex() {
		return aTagRegex;
	}
	public void setaTagRegex(String aTagRegex) {
		this.aTagRegex = aTagRegex;
	}
}
