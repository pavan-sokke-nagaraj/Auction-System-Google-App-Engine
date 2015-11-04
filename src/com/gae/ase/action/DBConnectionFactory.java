package com.gae.ase.action;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.google.appengine.api.utils.SystemProperty;

public class DBConnectionFactory {
	static Logger logger = Logger.getLogger(DBConnectionFactory.class.getName());
	private static DBConnectionFactory factory = null;
	private DataSource ds = null;
	private String url = null;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private DBConnectionFactory() {

		try {
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
				// Load the class that provides the new "jdbc:google:mysql://"
				// prefix.
				Class.forName("com.mysql.jdbc.GoogleDriver");
				url = "jdbc:google:mysql://auctionsystem-27608144:clouddb/Auction_System";
			} else {
				// Local MySQL instance to use during development.
				try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

				url = "jdbc:mysql://173.194.226.157:3306/Auction_System?user=pavan";

				// Alternatively, connect to a Google Cloud SQL instance using:
				// jdbc:mysql://ip-address-of-google-cloud-sql-instance:3306/guestbook?user=root
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	public DataSource getDataSource() {
		return ds;
	}

	public static Connection getConnection() {

		if (factory == null)
			factory = new DBConnectionFactory();
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(factory.getUrl(), "pavan", "pavan");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

}
