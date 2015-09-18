package ar.edu.itba.it.paw.db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ar.edu.itba.it.paw.model.Order;

public class OrderDatabase extends Database<Order> {

	private static final int ID = 1;
	private static final int USER_ID = 2;
	private static final int RESTAURANT_ID = 3;
	private static final int MADE = 4;
	private static final int RESTAURANT_NAME = 5;
	
	public long addOrder(Order order) {
		return insertGetId("insert into orders (userid, restaurantid, made) values (?, ?, ?)", order);
	}
	
	public List<Order> getOrders(long userId) {
		return doListQuery("select orders.id, userid, restaurantid, made, name from orders, restaurant "
				+ "where userid=" + userId + " and restaurantid=restaurant.id");
	}
	
	@Override
	protected Order generate(ResultSet rs) throws SQLException {
		return new Order(
				rs.getLong(ID),
				rs.getLong(USER_ID),
				rs.getLong(RESTAURANT_ID),
				rs.getDate(MADE).getTime(),
				rs.getString(RESTAURANT_NAME));
	}

	@Override
	protected void storeData(PreparedStatement pst, Order order) throws SQLException {
		pst.setLong(USER_ID - 1, order.getUserId());
		pst.setLong(RESTAURANT_ID - 1, order.getRestaurantId());
		pst.setDate(MADE - 1, new Date(order.getMade().getTime()));
	}
}
