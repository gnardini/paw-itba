package ar.edu.itba.it.paw.repository.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.model.OrderDetail;
import ar.edu.itba.it.paw.repository.OrderDetailRepo;

@Repository
public class HibernateOrderDetailRepo extends AbstractHibernateRepo implements OrderDetailRepo {
	
	@Autowired
	public HibernateOrderDetailRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	public void storeOrderDetail(OrderDetail orderDetail) {
		save(orderDetail);
	}
}
