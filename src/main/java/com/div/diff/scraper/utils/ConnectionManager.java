package com.div.diff.scraper.utils;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.div.diff.scraper.domain.Connection;
import com.div.diff.scraper.domain.Page;

public class ConnectionManager extends EntityManager {

	public void makeConnections(Page source, List<Page> newSitePages) {
		List<Connection> conns = new ArrayList<>();
		for (Page p : newSitePages) {
			Connection c = new Connection();
			c.setSourceId(source.getId());
			c.setDestId(p.getId());
			conns.add(c);
		}
		addConnections(conns, factory.openSession());
	}

	private void addConnections(List<Connection> conns, Session session) {
		try {
			session.beginTransaction();
			for (Connection conn : conns) {
				session.save(conn);
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
