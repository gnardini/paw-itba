package ar.edu.itba.it.paw.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ar.edu.itba.it.paw.model.Order;

public class OrderDatabase extends Database<Order> {

	public void addOrder(long restaurantId, Order order) {
		
	}
	
	@Override
	protected Order generate(ResultSet rs) throws SQLException {
		return null;
	}

	@Override
	protected void storeData(PreparedStatement pst, Order elem) throws SQLException {
	}

	@Override
	protected void updateData(PreparedStatement pst, Order elem) throws SQLException {
	}
}
