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
	
	private static final int CODE = 1;
	private static final int NAME = 2;
	private static final int ADDRESS = 3;
	private static final int OPENING_HOURS = 4;
	private static final int DELIVERY_COST = 5;
	private static final int MIN_COST = 6;
	private static final int MENU_TYPE = 7;
	private static final int DESCRIPTION = 8;
	
	private static RestaurantDatabase sRestaurantDatabase;

	public static RestaurantDatabase getInstance() {
		if (sRestaurantDatabase == null) sRestaurantDatabase = new RestaurantDatabase();
		return sRestaurantDatabase;
	}

	private RestaurantDatabase() {
	}
	
	@Override
	protected void storeData(PreparedStatement pst, Restaurant rest) throws SQLException {
		pst.setLong(CODE, rest.getCode());
		pst.setString(NAME, rest.getName());
		pst.setString(ADDRESS, rest.getAddress());
		pst.setString(OPENING_HOURS, rest.getOpeningHours());
		pst.setInt(DELIVERY_COST, rest.getDeliveryCost());
		pst.setInt(MIN_COST, rest.getMinCost());
		pst.setString(MENU_TYPE, rest.getMenuType());
		pst.setString(DESCRIPTION, rest.getDescription());
	}
	
	@Override
	protected Restaurant generate(ResultSet rs) throws SQLException {
		return new Restaurant(
				rs.getLong(CODE),
				rs.getString(NAME),
				rs.getString(ADDRESS),
				rs.getString(OPENING_HOURS),
				rs.getInt(DELIVERY_COST),
				rs.getInt(MIN_COST),
				rs.getString(MENU_TYPE),
				rs.getString(DESCRIPTION));
	}

	public Restaurant getRestaurant(int code) {
		return doQuery("select * from restaurant where id=" + code);
		/*
		Restaurant restaurant =  new Restaurant(1, "Restaurant 1", "Direccion 112", "9-13", 10, 20, "Restaurant numero 1");
		for (int i = 1 ; i <= 5 ; i++) restaurant.addComment(new Comment("Comment " + i, i));
		for (int i = 1 ; i <= 5 ; i++) restaurant.addDish(new Dish(i, "Dish " + i, " ", 10, Dish.Type.ENTRY));
		return restaurant;
		*/
	}
	
	public List<Restaurant> getRestaurants() {
		return doListQuery("select * from restaurant");
		/*
		List<Restaurant> list = new LinkedList<Restaurant>();
		list.add(new Restaurant(1, "Restaurant 1", "Direccion 112", "9-13", 10, 20, "", "Restaurant numero 1"));
		list.add(new Restaurant(2, "Restaurant 2", "Direccion 212", "9-13", 10, 20, "",  "Restaurant numero 2"));
		list.add(new Restaurant(3, "Restaurant 3", "Direccion 312", "9-13", 10, 20, "",  "Restaurant numero 3"));
		list.add(new Restaurant(4, "Restaurant 4", "Direccion 412", "9-13", 10, 20, "",  "Restaurant numero 4"));
		return list;
		*/
	}
	
	public List<Restaurant> getRestaurantsByManager(String email) {
		return getRestaurants();
	}
	
	public void addRestaurant(Restaurant restaurant) {
		insert("insert into restaurant values (?, ?, ?, ?, ?, ?, ?, ?)", restaurant);
	}
	
	public void addComment(int code, Comment comment) {
		System.out.println("NEW COMMENT");
	}
	
	public void addDish(int code, Dish dish) {
		System.out.println("NEW DISH");
	}
	
	public void addOrder(int code, Order order) {
		System.out.println("NEW ORDER");
	}
}
