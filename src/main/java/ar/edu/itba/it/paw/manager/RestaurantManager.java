package ar.edu.itba.it.paw.manager;

import java.util.List;

import ar.edu.itba.it.paw.model.Comment;
import ar.edu.itba.it.paw.model.Dish;
import ar.edu.itba.it.paw.model.Restaurant;

public interface RestaurantManager {

	public Restaurant getRestaurant(int id);
	
	public List<Restaurant> getRestaurants();
	
	public void addComment(Comment comment);
	
	public void addDish(Dish dish);
	
	public void addRestaurant(Restaurant restaurant);
	
	public void deleteRestaurant(Restaurant restaurant);
	
	public void deleteComment(Comment comment);
}
