package ar.edu.itba.it.paw.model;

public class Dish {

	public enum Type {
		ENTRY,
		MAIN,
		DESSERT,
		DRINK;
		
		public String toString() {
			return name();
		}
	}
	
	long id;
	long restaurantId;
	String name;
	String description;
	int price;
	Type type;
	
	public Dish(long id, long restaurantId, String name, String description, int price, Type type) {
		this.id = id;
		this.restaurantId = restaurantId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.type = type;
	}
	
	public Dish(long restaurantId, String name, String description, int price, Type type) {
		this.restaurantId = restaurantId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.type = type;
	}

	public long getId() {
		return id;
	}
	
	public long getRestaurantId() {
		return restaurantId;
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
