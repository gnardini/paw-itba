package ar.edu.itba.it.paw.model;

public class Dish {

	public enum Type {
		ENTRY,
		MAIN,
		DESSERT,
		DRINK;
	}
	
	int id;
	String name;
	String description;
	int price;
	Type type;
	
	public Dish(int id, String name, String description, int price, Type type) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.type = type;
	}

	public int getId() {
		return id;
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
