package ar.edu.itba.it.paw.manager;

import java.util.List;

import ar.edu.itba.it.paw.model.Restaurant;

public interface RestaurantManager {

	public Restaurant getRestaurant(int code);
	
	public List<Restaurant> getRestaurants();
}
