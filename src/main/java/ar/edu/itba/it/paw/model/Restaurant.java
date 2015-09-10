package ar.edu.itba.it.paw.model;

import java.util.LinkedList;
import java.util.List;

public class Restaurant {

	long code;
	String name;
	String address;
	String openingHours;
	int deliveryCost;
	int minCost;
	String menuType;
	String description;
	List<Dish> dishes;
	List<Comment> comments;
	
	public Restaurant(long code,
					String name,
					String address,
					String openingHours,
					int deliveryCost,
					int minCost,
					String menuType,
					String description) {
		this.code = code;
		this.name = name;
		this.address = address;
		this.openingHours = openingHours;
		this.deliveryCost = deliveryCost;
		this.minCost = minCost;
		this.menuType = menuType;
		this.description = description;
		dishes = new LinkedList<Dish>();
		comments = new LinkedList<Comment>();
	}
	
	public void addComment(Comment comment) {
		comments.add(comment);
	}
	
	public void addDish(Dish dish) {
		dishes.add(dish);
	}

	public long getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getOpeningHours() {
		return openingHours;
	}

	public int getDeliveryCost() {
		return deliveryCost;
	}

	public int getMinCost() {
		return minCost;
	}
	
	public String getMenuType() {
		return menuType;
	}

	public String getDescription() {
		return description;
	}
	
	public List<Comment> getComments() {
		return comments;
	}
	
	public List<Dish> getDishes() {
		return dishes;
	}
}
