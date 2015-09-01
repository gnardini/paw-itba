package ar.edu.itba.it.paw.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Database {

	private Connection mDbConnection;

	protected Database() {
		//TODO remove this
		if (true) return;
		
		String dbName = "jdbc:postgresql://localhost/paw";
		String dbDriver = "org.postgresql.Driver";
		String userName = "postgres";
		String password = "paw";

		try {
			Class.forName(dbDriver);
			mDbConnection = DriverManager.getConnection(dbName, userName, password);
			System.out.println("Got Connection");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
