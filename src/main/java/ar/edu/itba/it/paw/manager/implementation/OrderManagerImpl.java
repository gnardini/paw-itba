package ar.edu.itba.it.paw.manager.implementation;

import ar.edu.itba.it.paw.db.OrderDatabase;
import ar.edu.itba.it.paw.manager.OrderManager;
import ar.edu.itba.it.paw.model.Order;

public class OrderManagerImpl implements OrderManager {

	private OrderDatabase mDatabase;
	
	public OrderManagerImpl() {
		mDatabase = new OrderDatabase();
	}
	
	@Override
	public void addOrder(long restaurantId, Order order) {
		mDatabase.addOrder(restaurantId, order);
	}
}
