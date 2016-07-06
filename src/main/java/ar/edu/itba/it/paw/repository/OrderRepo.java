package ar.edu.itba.it.paw.repository;

import ar.edu.itba.it.paw.model.Orders;

public interface OrderRepo {

	public void addOrder(Orders order);
	
	public Orders getOrder(int id);
	
	public void updateOrder(Orders order);
	
}
