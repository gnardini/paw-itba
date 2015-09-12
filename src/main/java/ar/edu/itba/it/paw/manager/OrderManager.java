package ar.edu.itba.it.paw.manager;

import ar.edu.itba.it.paw.model.Order;

public interface OrderManager {

	public void addOrder(long restaurantId, Order order);
}
