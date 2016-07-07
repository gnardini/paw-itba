package ar.edu.itba.it.paw.repository.hibernate;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.model.Neighbourhood;
import ar.edu.itba.it.paw.model.Orders;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.model.RestaurantOrderCount;
import ar.edu.itba.it.paw.repository.RestaurantRepo;

@Repository
public class HibernateRestaurantRepo extends AbstractHibernateRepo implements RestaurantRepo {
	
	@Autowired
	public HibernateRestaurantRepo(SessionFactory sessionFactory) {
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
				return -Integer.compare(r1.getOrders().size(), r2.getOrders().size());
			}
		});
		int top = Math.min(3, restaurants.size());
		return restaurants.subList(0, top);
	}
	
	public List<Restaurant> getRestaurants() {
		return find("from Restaurant");
	}
	
	public List<Restaurant> getNewRestaurants(Date afterDate, Neighbourhood neighbourhood) {
		List<Restaurant> newRestaurants = new LinkedList<>();
		for (Restaurant restaurant: getRestaurants()) {
			if (afterDate.before(restaurant.getCreatedDate()) 
					&& restaurant.getNeighbourhoods().contains(neighbourhood)) {
				newRestaurants.add(restaurant);
			}
		}
		return newRestaurants;
	}
	
	public List<RestaurantOrderCount> getRestaurantOrdersInInterval(Date fromDate, Date toDate) {
		List<RestaurantOrderCount> restaurantOrdersInInverval = new LinkedList<>();
		for (Restaurant restaurant: getRestaurants()) {
			int ordersInRange = 0;
			for (Orders order: restaurant.getOrders()) {
				Date orderDate = order.getMade();
				if (orderDate.before(toDate) && orderDate.after(fromDate)) {
					ordersInRange++;
				}
			}
			restaurantOrdersInInverval.add(new RestaurantOrderCount(restaurant, ordersInRange));
		}
		return restaurantOrdersInInverval;
	}
	
	public void storeRestaurant(Restaurant restaurant) {
		save(restaurant);
	}
	
	public void deleteRestaurant(Restaurant restaurant) {
		delete(restaurant);
	}
	
	public void updateRestaurant(Restaurant restaurant) {
		update(restaurant);
	}
	
}
