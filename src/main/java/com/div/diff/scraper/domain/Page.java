package com.div.diff.scraper.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PAGES")
public class Page {
	
	private long id;
	private String pageName;
	private String url;
	private String domain;
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
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
