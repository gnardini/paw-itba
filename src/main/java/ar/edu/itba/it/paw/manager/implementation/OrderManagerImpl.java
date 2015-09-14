package ar.edu.itba.it.paw.manager.implementation;

import ar.edu.itba.it.paw.db.OrderDatabase;
import ar.edu.itba.it.paw.db.OrderDetailDatabase;
import ar.edu.itba.it.paw.manager.OrderManager;
import ar.edu.itba.it.paw.model.Order;
import ar.edu.itba.it.paw.model.OrderDetail;

public class OrderManagerImpl implements OrderManager {

	private OrderDatabase mOrderDatabase;
	private OrderDetailDatabase mOrderDetailDatabase;
	
	public OrderManagerImpl() {
		mOrderDatabase = new OrderDatabase();
		mOrderDetailDatabase = new OrderDetailDatabase();
	}
	
	@Override
	public void addOrder(Order order) {
		long orderId = mOrderDatabase.addOrder(order);
		for (OrderDetail detail : order.getDetails()) {
			detail.setOrderId(orderId);
			mOrderDetailDatabase.storeOrderDetail(detail);
		}
	}
}
