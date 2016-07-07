package ar.edu.itba.it.paw.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import ar.edu.itba.it.paw.model.Neighbourhood;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.model.RestaurantOrderCount;

public interface RestaurantRepo {
	
	public Restaurant getRestaurant(int id);
	
	public List<Restaurant> getTopRestaurants();
	
	public List<Restaurant> getRestaurants();
	
	public List<Restaurant> getNewRestaurants(Date afterDate, Neighbourhood neighbourhood);
	
	public List<RestaurantOrderCount> getRestaurantOrdersInInterval(Date fromDate, Date toDate);
	
	public void storeRestaurant(Restaurant restaurant);

	public void deleteRestaurant(Restaurant restaurant); 
	
	public void updateRestaurant(Restaurant restaurant);

}
