package ar.edu.itba.it.paw.db;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class HerokuDatabase {

	protected Connection mDbConnection;

	protected HerokuDatabase() {
		URI dbUri;
		try {
			dbUri = new URI(
					"postgres://qycdoftytvgugn:3SS5BQFZsq1FKJXrMLSJtX5fat@ec2-54-83-55-214.compute-1.amazonaws.com:5432/djnrag8vu0hpu");
			String username = dbUri.getUserInfo().split(":")[0];
			String password = dbUri.getUserInfo().split(":")[1];
			String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
			String dbDriver = "org.postgresql.Driver";
			Properties prop = new Properties();
			prop.setProperty("user", username);
			prop.setProperty("password", password);
			prop.setProperty("ssl", "true");

			/*
			 * String dbUrl = "jdbc:postgresql://localhost/paw";
			 * 
			 * String userName = "postgres"; String password = "paw";
			 */

			try {
				Class.forName(dbDriver);
				mDbConnection = DriverManager.getConnection(dbUrl, prop);
				//mDbConnection = DriverManager.getConnection(dbUrl, username, password);
				System.out.println("Got Connection");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (URISyntaxException e1) {
			System.out.println("No se pudo conectar con la base de datos");
			e1.printStackTrace();
		}
	}
}