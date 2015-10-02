package ar.edu.itba.it.paw.db;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
	
	private static Connection sDbConnection;
	private Properties prop;
	
	
	public DatabaseConnection() throws IOException{
		prop = new Properties();
		String propFileName = "config.properties";
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

		if (inputStream != null) {
			prop.load(inputStream);
		}
	}
	
	private String getSSLOff(){
		return prop.getProperty("ssloff"); 
	}
	
	private String getURI() throws IOException{
		return prop.getProperty("uri");
	}
	
	
	public synchronized static Connection getConnection() {
		if (sDbConnection == null) initConnection();
		return sDbConnection;
	}
	
	private static void initConnection() {
		URI dbUri;
		try {
			DatabaseConnection db = new DatabaseConnection();
			String URI=db.getURI();
			dbUri = new URI("postgres://qycdoftytvgugn:3SS5BQFZsq1FKJXrMLSJtX5fat@ec2-54-83-55-214.compute-1.amazonaws.com:5432/djnrag8vu0hpu"); 
			//dbUri = new URI("postgres://qycdoftytvgugn:3SS5BQFZsq1FKJXrMLSJtX5fat@localhost:3000/djnrag8vu0hpu");
			String username = dbUri.getUserInfo().split(":")[0];
			String password = dbUri.getUserInfo().split(":")[1];
			String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
			String dbDriver = "org.postgresql.Driver";
			Properties prop = new Properties();
			prop.setProperty("user", username);
			prop.setProperty("password", password);
			String ssloff = db.getSSLOff();

			/*
			 * String dbUrl = "jdbc:postgresql://localhost/paw";
			 * 
			 * String userName = "postgres"; String password = "paw";
			 */

			try {
				Class.forName(dbDriver);
				dbUrl+=ssloff;
				sDbConnection = DriverManager.getConnection(dbUrl, prop);
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
		} catch (IOException e1) {
			System.out.println("No se pudo conectar con la base de datos");
			e1.printStackTrace();
		}
	}
}
