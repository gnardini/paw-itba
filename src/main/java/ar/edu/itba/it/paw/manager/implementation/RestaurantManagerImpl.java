package ar.edu.itba.it.paw.manager.implementation;

import java.util.List;

import ar.edu.itba.it.paw.db.DishDatabase;
import ar.edu.itba.it.paw.db.RestaurantDatabase;
import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.model.Comment;
import ar.edu.itba.it.paw.model.Dish;
import ar.edu.itba.it.paw.model.Restaurant;

public class RestaurantManagerImpl implements RestaurantManager {

	private RestaurantDatabase mRestaurantDatabase;
	private DishDatabase mDishDatabase;
	
	public RestaurantManagerImpl() {
		mRestaurantDatabase = new RestaurantDatabase();
		mDishDatabase = new DishDatabase();
	}
	
	public Restaurant getRestaurant(long id) {
		return mRestaurantDatabase.getRestaurant(id);
	}
	
	public List<Restaurant> getRestaurants() {
		return mRestaurantDatabase.getRestaurants();
	}
	
	@Override
	public List<Restaurant> getRestaurantsByManager(String email) {
		return mRestaurantDatabase.getManagersRestaurants(email);
	}
	
	@Override
	public List<Dish> getRestaurantDishes(long restaurantId) {
		return mDishDatabase.getRestaurantDishes(restaurantId);
	}
	
	@Override
	public void addRestaurant(Restaurant restaurant) {
		mRestaurantDatabase.addRestaurant(restaurant);
	}
	
	public void addComment(Comment comment) {
		mRestaurantDatabase.addComment(comment);
	}
	
	@Override
	public void addDish(Dish dish) {
		mDishDatabase.addDish(dish);
	}
}
