package ar.edu.itba.it.paw.model;

import java.util.HashMap;
import java.util.Map;

public class Order {

	private Map<Integer, Integer> dishes;
	
	public Order() {
		dishes = new HashMap<>();
	}
	
	public void addDish(int dishId, int amount) {
		dishes.put(dishId, amount);
	}
	
	public Map<Integer, Integer> getDishes() {
		return dishes;
	}
}
