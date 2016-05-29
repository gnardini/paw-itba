package ar.edu.itba.it.paw.repository.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.model.Dish;

@Repository
public class DishRepo extends AbstractHibernateRepo {
	
	@Autowired
	public DishRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	public void addDish(Dish dish) {
		save(dish);
	}
}
