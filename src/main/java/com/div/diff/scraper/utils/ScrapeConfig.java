package com.div.diff.scraper.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ScrapeConfig {

	public static Properties props;
	public static final String database = "jdbc.database";
	public static final String user = "jdbc.user";
	public static final String passwd = "jdbc.password";
	
	{
		init();
	}
	
	private void init() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/jdbc.properties"));
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] parts = line.split("=");
				switch (parts[0]) {
				case database: 
					props.setProperty(database, parts[1]);
					break;
				case user:
					props.setProperty(user, parts[1]);
					break;
				case passwd:
					props.setProperty(passwd, parts[1]);
					break;
				default:
					throw new IllegalArgumentException();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
