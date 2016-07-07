package ar.edu.itba.it.paw.repository;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.model.Dish;


public interface DishRepo {
	public void addDish(Dish dish);
}
