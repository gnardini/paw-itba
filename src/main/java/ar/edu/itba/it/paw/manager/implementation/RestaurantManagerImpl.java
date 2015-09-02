package ar.edu.itba.it.paw.manager.implementation;

import java.util.List;

import ar.edu.itba.it.paw.db.RestaurantDatabase;
import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.model.Comment;
import ar.edu.itba.it.paw.model.Dish;
import ar.edu.itba.it.paw.model.Restaurant;

public class RestaurantManagerImpl implements RestaurantManager {

	private RestaurantDatabase mDatabase;
	
	public RestaurantManagerImpl() {
		mDatabase = RestaurantDatabase.getInstance();
	}
	
	public Restaurant getRestaurant(int code) {
		if (mDatabase == null) return null;
		return mDatabase.getRestaurant(code);
	}
	
	public List<Restaurant> getRestaurants() {
		if (mDatabase == null) return null;
		return mDatabase.getRestaurants();
	}
	
	@Override
	public List<Restaurant> getRestaurantsByManager(String email) {
		if (mDatabase == null) return null;
		return mDatabase.getRestaurantsByManager(email);
	}
	
	@Override
	public void addRestaurant(Restaurant restaurant) {
		if (mDatabase == null) return;
		mDatabase.addRestaurant(restaurant);
	}
	
	public void addComment(int code, Comment comment) {
		if (mDatabase == null) return;
		mDatabase.addComment(code, comment);
	}
	
	@Override
	public void addDish(int code, Dish dish) {
		if (mDatabase == null) return;
		mDatabase.addDish(code, dish);
	}
}
