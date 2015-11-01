package ar.edu.itba.it.paw.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Dish extends PersistentEntity {

	public enum Type {
		ENTRY,
		MAIN,
		DESSERT,
		DRINK;
		
		public String toString() {
			return name();
		}
	}
	
	@ManyToOne
	Restaurant restaurant;
	
	String name;
	String description;
	// TODO hacer float
	int price;
	Type type;
	
	public Dish() {
		
	}
	
	public Dish(Restaurant restaurant, String name, String description, int price, Type type) {
		this.restaurant = restaurant;
		this.name = name;
		this.description = description;
		this.price = price;
		this.type = type;
	}
	
	public Restaurant getRestaurant() {
		return restaurant;
	}
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getPrice() {
		return price;
	}

	public Type getType() {
		return type;
	}
}
