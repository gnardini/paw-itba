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

	protected Connection mDbConnection;

	protected Database() {
		mDbConnection = DatabaseConnection.getConnection();
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
