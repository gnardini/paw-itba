package ar.edu.itba.it.paw.db;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public abstract class Database<T> {

	protected static Connection mDbConnection = null;

	private static void init(){
		if(mDbConnection==null){
			URI dbUri;
			try {
				//dbUri = new URI("postgres://qycdoftytvgugn:3SS5BQFZsq1FKJXrMLSJtX5fat@ec2-54-83-55-214.compute-1.amazonaws.com:5432/djnrag8vu0hpu"); 
				dbUri = new URI(
						"postgres://qycdoftytvgugn:3SS5BQFZsq1FKJXrMLSJtX5fat@localhost:3000/djnrag8vu0hpu");
				String username = dbUri.getUserInfo().split(":")[0];
				String password = dbUri.getUserInfo().split(":")[1];
				String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
				String dbDriver = "org.postgresql.Driver";
				Properties prop = new Properties();
				prop.setProperty("user", username);
				prop.setProperty("password", password);
				String ssloff = "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";

				/*
				 * String dbUrl = "jdbc:postgresql://localhost/paw";
				 * 
				 * String userName = "postgres"; String password = "paw";
				 */

				try {
					Class.forName(dbDriver);
					dbUrl+=ssloff;
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
	
	protected Database() {
		init();
	}
	
	protected T doQuery(String sql) {
		try {
			Statement statement = mDbConnection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			if (!rs.next()) return null;
			T elem = generate(rs);
			rs.close();
			return elem;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected List<T> doListQuery(String sql) {
		try {
			Statement statement = mDbConnection.createStatement();
			List<T> elems = new LinkedList<>();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				elems.add(generate(rs));
			}
			rs.close();
			return elems;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected abstract T generate(ResultSet rs) throws SQLException ;
	
	protected T insert(String sql, T elem) {
		try {
			PreparedStatement pst =  mDbConnection.prepareStatement(sql);
			storeData(pst, elem);
			pst.executeUpdate();
			pst.close();
			return elem;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected abstract void storeData(PreparedStatement pst, T elem) throws SQLException;
}
