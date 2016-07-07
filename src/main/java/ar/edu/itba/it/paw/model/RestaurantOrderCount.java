package ar.edu.itba.it.paw.model;

import java.io.Serializable;

public class RestaurantOrderCount implements Serializable {

	private Restaurant restaurant;
	private int orderCount;

	public RestaurantOrderCount(Restaurant restaurant, int orderCount) {
		this.restaurant = restaurant;
		this.orderCount = orderCount;
	}
	
	public Restaurant getRestaurant() {
		return restaurant;
	}
	
	public int getOrderCount() {
		return orderCount;
	}
	
}
