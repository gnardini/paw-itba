package ar.edu.itba.it.paw.repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.model.Restaurant;

@Repository
public class RestaurantRepo extends AbstractHibernateRepo {
	
	@Autowired
	public RestaurantRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	public Restaurant getRestaurant(int id) {
		return get(Restaurant.class, id);
	}
	
	public List<Restaurant> getTopRestaurants() {
		List<Restaurant> restaurants = getRestaurants();
		Collections.sort(restaurants, new Comparator<Restaurant>() {
			@Override
			public int compare(Restaurant r1, Restaurant r2) {
				return Integer.compare(r1.getOrders().size(), r2.getOrders().size());
			}
		});
		return restaurants.subList(0, 3);
	}
	
	public List<Restaurant> getRestaurants() {
		return find("from Restaurant");
	}
	
	public void storeRestaurant(Restaurant restaurant) {
		save(restaurant);
	}
	
	public void deleteRestaurant(Restaurant restaurant) {
		delete(restaurant);
	}
}
