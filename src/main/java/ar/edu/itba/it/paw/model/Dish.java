package ar.edu.itba.it.paw.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Dish extends PersistentEntity implements Serializable {

	public enum Type {
		ENTRY("Entrada"),
		MAIN("Plato Principal"),
		DESSERT("Postre"),
		DRINK("Bebida");
		
		private String name; 
		
		Type(String name){
			this.name=name;
		}
		
		public String toString() {
			return name;
		}
	}
	
	@ManyToOne
	Restaurant restaurant;
	
	String name;
	String description;
	float price;
	Type type;
	
	public Dish() {
		
	}
	
	public Dish(Restaurant restaurant, String name, String description, float price, Type type) {
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

	public float getPrice() {
		return price;
	}

	public Type getType() {
		return type;
	}
}
