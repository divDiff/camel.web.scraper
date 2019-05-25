package com.div.diff.scraper.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SqlEngine {
	
	private Connection conn;
	
	public SqlEngine() throws SQLException {
		this.conn = establishConn(null, null, null);
	}
	
	public Connection establishConn(String dbName, String user, String password) throws SQLException {
		String url = "jdbc:postgresql://localhost/" + dbName;
		Properties props = new Properties();
		props.setProperty("user", user);
		props.setProperty("password", password);
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
