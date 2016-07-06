package ar.edu.itba.it.paw.repository.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.model.Orders;
import ar.edu.itba.it.paw.repository.OrderRepo;

@Repository
public class HibernateOrderRepo extends AbstractHibernateRepo implements OrderRepo {
	
	@Autowired
	public HibernateOrderRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public void addOrder(Orders order) {
		save(order);
	}
	
	public Orders getOrder(int id) {
		return get(Orders.class, id);
	}
}
