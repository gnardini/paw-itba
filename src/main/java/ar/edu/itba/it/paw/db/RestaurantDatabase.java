package ar.edu.itba.it.paw.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.it.paw.model.Comment;
import ar.edu.itba.it.paw.model.Dish;
import ar.edu.itba.it.paw.model.Order;
import ar.edu.itba.it.paw.model.Restaurant;

public class RestaurantDatabase extends Database<Restaurant> {
	
	private static final int ID = 1;
	private static final int NAME = 2;
	private static final int ADDRESS = 3;
	private static final int OPENING_HOURS = 4;
	private static final int DELIVERY_COST = 5;
	private static final int MIN_COST = 6;
	private static final int MENU_TYPE = 7;
	private static final int DESCRIPTION = 8;

	public Restaurant getRestaurant(long id) {
		return doQuery("select * from restaurant where id=" + id);
	}
	
	public List<Restaurant> getRestaurants() {
		return doListQuery("select * from restaurant");
	}
	
	public List<Restaurant> getManagersRestaurants(String email) {
		return doListQuery("select restaurant.id, name, address, opening_hours, delivery_cost, min_cost, menu_type, description "
				+ "from restaurant join managers "
				+ "where restaurant.id = managers.restaurantid");
	}
	
	// TODO: Move these to their respective classes
	public void addRestaurant(Restaurant restaurant) {
		insert("insert into restaurant (name, address, opening_hours, delivery_cost, min_cost, menu_type, description) values (?, ?, ?, ?, ?, ?, ?)", restaurant);
	}
	
	public void addComment(Comment comment) {
		System.out.println("NEW COMMENT");
	}
	
	public void addOrder(int code, Order order) {
		System.out.println("NEW ORDER");
	}
	
	@Override
	protected Restaurant generate(ResultSet rs) throws SQLException {
		return new Restaurant(
				rs.getLong(ID),
				rs.getString(NAME),
				rs.getString(ADDRESS),
				rs.getString(OPENING_HOURS),
				rs.getInt(DELIVERY_COST),
				rs.getInt(MIN_COST),
				rs.getString(MENU_TYPE),
				rs.getString(DESCRIPTION));
	}
	
	@Override
	protected void storeData(PreparedStatement pst, Restaurant rest) throws SQLException {
		pst.setString(NAME - 1, rest.getName());
		pst.setString(ADDRESS - 1, rest.getAddress());
		pst.setString(OPENING_HOURS - 1, rest.getOpeningHours());
		pst.setInt(DELIVERY_COST - 1, rest.getDeliveryCost());
		pst.setInt(MIN_COST - 1, rest.getMinCost());
		pst.setString(MENU_TYPE - 1, rest.getMenuType());
		pst.setString(DESCRIPTION - 1, rest.getDescription());
	}
	
	@Override
	protected void updateData(PreparedStatement pst, Restaurant rest) throws SQLException {
		pst.setLong(ID, rest.getId());
		pst.setString(NAME, rest.getName());
		pst.setString(ADDRESS, rest.getAddress());
		pst.setString(OPENING_HOURS, rest.getOpeningHours());
		pst.setInt(DELIVERY_COST, rest.getDeliveryCost());
		pst.setInt(MIN_COST, rest.getMinCost());
		pst.setString(MENU_TYPE, rest.getMenuType());
		pst.setString(DESCRIPTION, rest.getDescription());
	}
}
