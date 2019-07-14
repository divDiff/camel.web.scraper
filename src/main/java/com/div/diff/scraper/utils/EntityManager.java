package com.div.diff.scraper.utils;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public abstract class EntityManager {
	protected SessionFactory factory;

	public void setup() {
		// configures settings from hibernate.cfg.xml
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		try {
			factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception ex) {
			StandardServiceRegistryBuilder.destroy(registry);
			throw ex;
		}
	}

	protected List<?> executeQuery(Query<?> q, Session session) {
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

	protected Object executeGetQuery(Query<?> q, Session session) {
		Object result = new Object();
		try {
			session.beginTransaction();
			result = q.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			session.getTransaction().commit();
			session.close();
		}
		return result;
	}

	protected void addObjects(List<Object> entities, Session session) {
		try {
			session.beginTransaction();
			for (Object se : entities) {
				session.save(se);
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
