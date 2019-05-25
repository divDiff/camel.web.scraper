package com.div.diff.scraper.domain;

import java.time.LocalDate;

public class SiteMetadata {
	
	private String siteUrl;
	private LocalDate scrapeDate;
	private String siteContent;
	
	public String getSiteUrl() {
		return siteUrl;
	}
	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}
	public LocalDate getScrapeDate() {
		return scrapeDate;
	}
	public void setScrapeDate(LocalDate scrapeDate) {
		this.scrapeDate = scrapeDate;
	}
	public String getSiteContent() {
		return siteContent;
	}
	public void setSiteContent(String siteContent) {
		this.siteContent = siteContent;
	}
}
