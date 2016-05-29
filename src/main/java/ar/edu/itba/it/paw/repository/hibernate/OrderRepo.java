package ar.edu.itba.it.paw.repository.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.model.Orders;
import ar.edu.itba.it.paw.model.Restaurant;

@Repository
public class OrderRepo extends AbstractHibernateRepo {
	
	@Autowired
	public OrderRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public void addOrder(Orders order) {
		save(order);
	}
	
	public Orders getOrder(int id) {
		return get(Orders.class, id);
	}
}
