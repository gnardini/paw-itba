package ar.edu.itba.it.paw.model;

public class Dish {

	public enum Type {
		ENTRY,
		MAIN,
		DESSERT,
		DRINK;
	}
	
	String name;
	String description;
	int price;
	Type type;
	
	public Dish(String name, String description, int price, Type type) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.type = type;
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
