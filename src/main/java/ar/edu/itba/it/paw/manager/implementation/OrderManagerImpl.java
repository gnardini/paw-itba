package ar.edu.itba.it.paw.manager.implementation;

import ar.edu.itba.it.paw.db.RestaurantDatabase;
import ar.edu.itba.it.paw.manager.OrderManager;
import ar.edu.itba.it.paw.model.Order;

public class OrderManagerImpl implements OrderManager {

	private RestaurantDatabase mDatabase;
	
	public OrderManagerImpl() {
		mDatabase = RestaurantDatabase.getInstance();
	}
	
	@Override
	public void addOrder(int restaurantCode, Order order) {
		if (mDatabase != null) mDatabase.addOrder(restaurantCode, order);
	}
}
