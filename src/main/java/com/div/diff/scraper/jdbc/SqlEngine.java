package com.div.diff.scraper.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.div.diff.scraper.utils.ScrapeConfig;

public class SqlEngine {
	
	private Connection conn;
	
	public SqlEngine() throws SQLException {
		this.conn = establishConn();
	}
	
	private Connection establishConn() throws SQLException {
		String url = "jdbc:postgresql://localhost/" + ScrapeConfig.props.getProperty(ScrapeConfig.database);
		Properties props = new Properties();
		props.setProperty("user", ScrapeConfig.props.getProperty(ScrapeConfig.user));
		props.setProperty("password", ScrapeConfig.props.getProperty(ScrapeConfig.passwd));
		props.setProperty("ssl", "true");
		return DriverManager.getConnection(url, props);
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}
}
