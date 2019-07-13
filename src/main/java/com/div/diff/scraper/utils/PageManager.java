package com.div.diff.scraper.utils;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import com.div.diff.scraper.domain.Page;
import com.div.diff.scraper.domain.SiteMetadata;

public class PageManager {

	private SessionFactory factory;
	
	public void setup() {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
		        .configure() // configures settings from hibernate.cfg.xml
		        .build();
		try {
			factory = new MetadataSources(registry)
					.buildMetadata()
					.buildSessionFactory();
		} catch (Exception ex) {
		    StandardServiceRegistryBuilder.destroy(registry);
		    throw ex;
		}
	}
	
	public void addNewPages(List<Page> newPages, String originalUrl) {
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
	}
	
	@SuppressWarnings("unchecked")
	private List<Page> findExistingPages(List<Page> pages) {
		Session session = factory.openSession();
		StringBuilder urls = new StringBuilder();
		for (Page p: pages) {
			urls.append(p.getUrl() + " ");
		}
		Query<?> q = session.createQuery("FROM Page P WHERE P.url IN (:urls)").setParameter("urls", urls.toString());
		List<Page> existingPages = (List<Page>) executeQuery(q, session);
		
		return existingPages;
	}
	
	private List<?> executeQuery(Query<?> q, Session session) {
		List<?> results = new ArrayList<>();
		try {
			session.beginTransaction();	
			results = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			session.getTransaction().commit();
			session.close();
		}
		return results;
	}
	
	private void addPages(List<Page> pages, Session session) {
		try {
			session.beginTransaction();	
			for (Page page : pages) {
				session.save(page);
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
