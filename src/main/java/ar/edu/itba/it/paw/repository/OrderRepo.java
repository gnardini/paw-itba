package ar.edu.itba.it.paw.repository;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.model.Order;

@Repository
public class OrderRepo extends AbstractHibernateRepo {
	
	@Autowired
	public OrderRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public void addOrder(Order order) {
		save(order);
	}
}