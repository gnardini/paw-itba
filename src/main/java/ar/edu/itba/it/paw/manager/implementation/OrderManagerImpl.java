package ar.edu.itba.it.paw.manager.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.it.paw.manager.OrderManager;
import ar.edu.itba.it.paw.model.Orders;
import ar.edu.itba.it.paw.model.OrderDetail;
import ar.edu.itba.it.paw.repository.OrderDetailRepo;
import ar.edu.itba.it.paw.repository.OrderRepo;

@Service
public class OrderManagerImpl implements OrderManager {

	private OrderRepo orderRepo;
	private OrderDetailRepo orderDetailRepo;
	
	@Autowired
	public OrderManagerImpl(OrderRepo orderRepo, OrderDetailRepo orderDetailRepo) {
		this.orderRepo = orderRepo;
		this.orderDetailRepo = orderDetailRepo;
	}
	
	@Override
	public void addOrder(Orders order) {
		orderRepo.addOrder(order);
		for (OrderDetail detail : order.getDetails()) {
			detail.setOrder(order);
			orderDetailRepo.storeOrderDetail(detail);
		}
	}
}
