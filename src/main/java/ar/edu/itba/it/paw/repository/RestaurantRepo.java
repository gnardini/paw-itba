package ar.edu.itba.it.paw.repository;

import java.util.List;

import ar.edu.itba.it.paw.model.Restaurant;

public interface RestaurantRepo {
	
	public Restaurant getRestaurant(int id);
	
	public List<Restaurant> getTopRestaurants();
	
	public List<Restaurant> getRestaurants();
	
	public void storeRestaurant(Restaurant restaurant);

	public void deleteRestaurant(Restaurant restaurant); 

}
