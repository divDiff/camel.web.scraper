package com.div.diff.scraper.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PAGES")
public class Page {
	
	private String pageName;
	private String url;
	private String domain;
	
	@Column(name = "PAGE_NAME")
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
	    if (!(o instanceof Page)) {
	    	return false;
	    }
	     
	    Page page = (Page) o;
		if (getUrl().equals(page.getUrl())) {
			return true;
		}
		return false;
	}
}
