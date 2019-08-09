
package com.div.diff.scraper.utils;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.div.diff.scraper.domain.Page;
import com.div.diff.scraper.domain.SiteMetadata;

public class PageManager extends EntityManager {

	public List<Page> addNewPages(List<Page> newPages, String originalUrl) {
		Session session = factory.openSession();
		List<Page> existing = findExistingPages(newPages);
		List<Page> brandNewPages = new ArrayList<>();
		for (Page newPage : newPages) {
			if (!existing.contains(newPage)) {
				brandNewPages.add(newPage);
			}
		}
		if (brandNewPages.size() > 0) {
			System.out.println("Found " + brandNewPages.size() + " new pages on " + originalUrl);
			addPages(brandNewPages, session);
		} else {
			System.out.println("No new pages found on " + originalUrl);
		}
		return brandNewPages;
	}

	public Page getPage(String url) {
		Session session = factory.openSession();
		Query<?> q = session.createQuery("FROM Page P WHERE P.url = :url").setParameter("url", url);
		Page p = new Page();
		try {
			p = (Page) executeGetQuery(q, session);
		} catch (NonUniqueResultException e) {
			System.out.println("The following url has duplicates: " + url);
			throw e;
		}
		return p;
	}

	public void addSinglePage(SiteMetadata site) {
		Page p = new Page();
		p.setDomain("Trunk");
		p.setUrl(site.getSiteUrl());
		p.setPageName("Home Page");
		Session session = factory.openSession();

		session.beginTransaction();
		session.save(p);
		session.getTransaction().commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	private List<Page> findExistingPages(List<Page> pages) {
		Session session = factory.openSession();
		List<String> urls = new ArrayList<>();
		for (Page p : pages) {
			urls.add(p.getUrl());
		}
		Query<?> q = session.createQuery("FROM Page P WHERE P.url IN (:urls)").setParameterList("urls", urls);
		List<Page> existingPages = (List<Page>) executeQuery(q, session);

		return existingPages;
	}

	private void addPages(List<Page> pages, Session session) {
		try {
			session.beginTransaction();
			for (Page p : pages) {
				session.save(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			session.getTransaction().commit();
			session.close();
		}
	}
}
