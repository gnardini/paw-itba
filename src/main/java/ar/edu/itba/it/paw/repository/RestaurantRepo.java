package ar.edu.itba.it.paw.repository;

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
