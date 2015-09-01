package ar.edu.itba.it.paw.db;

import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.it.paw.model.Comment;
import ar.edu.itba.it.paw.model.Restaurant;

public class RestaurantDatabase extends Database {
	
	private static RestaurantDatabase sRestaurantDatabase;

	public static RestaurantDatabase getInstance() {
		if (sRestaurantDatabase == null) sRestaurantDatabase = new RestaurantDatabase();
		return sRestaurantDatabase;
	}

	private RestaurantDatabase() {
	}

	public Restaurant getRestaurant(int code) {
		Restaurant restaurant =  new Restaurant(1, "Restaurant 1", "Direccion 112", "9-13", 10, 20, "Restaurant numero 1");
		for (int i = 1 ; i <= 5 ; i++) restaurant.addComment(new Comment("Comment " + i, i));
		return restaurant;
	}
	
	public List<Restaurant> getRestaurants() {
		List<Restaurant> list = new LinkedList<Restaurant>();
		list.add(new Restaurant(1, "Restaurant 1", "Direccion 112", "9-13", 10, 20, "Restaurant numero 1"));
		list.add(new Restaurant(2, "Restaurant 2", "Direccion 212", "9-13", 10, 20, "Restaurant numero 2"));
		list.add(new Restaurant(3, "Restaurant 3", "Direccion 312", "9-13", 10, 20, "Restaurant numero 3"));
		list.add(new Restaurant(4, "Restaurant 4", "Direccion 412", "9-13", 10, 20, "Restaurant numero 4"));
		return list;
	}
	
	public void addComment(int code, Comment comment) {
		System.out.println("NEW COMMENT");
	}
}
