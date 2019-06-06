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

public class PageManager {

	private SessionFactory factory;
	
	public void setup() {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
		        .configure() // configures settings from hibernate.cfg.xml
		        .build();
		try {
			factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception ex) {
		    StandardServiceRegistryBuilder.destroy(registry);
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
//			logger.info("Found " + brandNewPages.size() + " new pages on X");
			System.out.println("Found " + brandNewPages.size() + " new pages on " + originalUrl);
//			Query q = session.createQuery("FROM PAGES P WHERE E.URL IN (:urls)");
			for (Page page : brandNewPages) {
				session.save(page);
			}
		} else {
//			logger.info("No new pages found on " + originalUrl);
			System.out.println("No new pages found on " + originalUrl);
		}
	}
	
	public List<Page> findExistingPages(List<Page> pages) {
		Session session = factory.openSession();
		Query q = session.createQuery("FROM PAGES P WHERE E.URL IN (:urls)");
		List<String> urls = new ArrayList<>();
		for (Page p: pages) {
			urls.add(p.getUrl());
		}
		List<?> existingPages = executeQuery(q, session);
		
		return (List<Page>) existingPages;
	}
	
	private List<?> executeQuery(Query q, Session session) {
		List<?> results = new ArrayList<>();
		try {
			session.beginTransaction();	
			results = q.getResultList();
		} finally {
			session.getTransaction().commit();
			session.close();
		}
		return results;
	}
}
