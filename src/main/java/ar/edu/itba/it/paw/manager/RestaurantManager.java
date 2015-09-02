package ar.edu.itba.it.paw.manager;

import java.util.List;

import ar.edu.itba.it.paw.model.Comment;
import ar.edu.itba.it.paw.model.Dish;
import ar.edu.itba.it.paw.model.Restaurant;

public interface RestaurantManager {

	public Restaurant getRestaurant(int code);
	
	public List<Restaurant> getRestaurants();
	
	public List<Restaurant> getRestaurantsByManager(String email);
	
	public void addComment(int code, Comment comment);
	
	public void addDish(int code, Dish dish);
	
	public void addRestaurant(Restaurant restaurant);
}
