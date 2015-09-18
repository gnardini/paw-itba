package ar.edu.itba.it.paw.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

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
	
	protected Float doGetFloatQuery(String sql) {
		try {
			Statement statement = mDbConnection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			if (!rs.next()) return null;
			float elem = rs.getFloat(1);
			rs.close();
			return elem;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected void delete(String sql) {
		try {
			Statement statement = mDbConnection.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected T insert(String sql, T elem) {
		return insert(sql, elem, true);
	}
	
	protected long insertGetId(String sql, T elem) {
		try {
			PreparedStatement pst =  mDbConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			storeData(pst, elem);
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
			long key = 0;
			if (rs.next()) key = rs.getLong(1);
			rs.close();
			pst.close();
			return key;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	protected T update(String sql) {
		return insert(sql, null, false);
	}
	
	private T insert(String sql, T elem, boolean newEntry) {
		try {
			PreparedStatement pst =  mDbConnection.prepareStatement(sql);
			if (newEntry) storeData(pst, elem);
			pst.executeUpdate();
			pst.close();
			return elem;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected abstract T generate(ResultSet rs) throws SQLException;
	
	protected abstract void storeData(PreparedStatement pst, T elem) throws SQLException;
}
