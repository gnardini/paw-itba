package ar.edu.itba.it.paw.manager.implementation;

import java.util.List;

import ar.edu.itba.it.paw.db.RestaurantDatabase;
import ar.edu.itba.it.paw.manager.RestaurantManager;
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
}
