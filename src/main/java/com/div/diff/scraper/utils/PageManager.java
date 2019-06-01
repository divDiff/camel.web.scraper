package com.div.diff.scraper.utils;

import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

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
	
	public Set<String> findNewPages(Set<String> pages) {
		Session session = factory.openSession();
		session.beginTransaction();
		try {
			
		} finally {
//		session.get
//			session.getTransaction().commit();
			session.close();
		}
		return null;
	}
	
}
