package ar.edu.itba.it.paw.manager;

import java.util.List;

import ar.edu.itba.it.paw.model.Comment;
import ar.edu.itba.it.paw.model.Dish;
import ar.edu.itba.it.paw.model.Restaurant;

public interface RestaurantManager {

	public Restaurant getRestaurant(long id);
	
	public List<Restaurant> getRestaurants();
	
	public List<Restaurant> getRestaurantsByManager(long managerId);
	
	public List<Comment> getRestaurantComments(long restaurantId);
	
	public List<Dish> getRestaurantDishes(long restaurantId);
	
	public void addComment(Comment comment);
	
	public void addDish(Dish dish);
	
	public boolean addRestaurant(Restaurant restaurant);
	
	public boolean canUserComment(long userId, long restaurantId);
	
	public Dish getDishFromRestaurant(long dishId, long restaurantId);
	
	public void updateRestaurant(Restaurant restaurant);
	
	public void deleteRestaurant(long restaurantId);
	
	public void deleteComment(long userId, long restaurantId);
}
