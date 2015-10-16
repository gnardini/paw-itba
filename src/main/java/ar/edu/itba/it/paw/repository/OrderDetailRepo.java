package ar.edu.itba.it.paw.repository;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.model.OrderDetail;

@Repository
public class OrderDetailRepo extends AbstractHibernateRepo {
	
	@Autowired
	public OrderDetailRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	public void storeOrderDetail(OrderDetail orderDetail) {
		save(orderDetail);
	}
}
