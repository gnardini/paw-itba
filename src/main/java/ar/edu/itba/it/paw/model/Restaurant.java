package ar.edu.itba.it.paw.model;

import java.util.List;

public class Restaurant {

	long id;
	String name;
	String address;
	String openingHours;
	int deliveryCost;
	int minCost;
	String menuType;
	String description;
	List<Dish> dishes;
	String ranking;
	List<Comment> comments;
	
	public Restaurant(long id,
					String name,
					String address,
					String openingHours,
					int deliveryCost,
					int minCost,
					String menuType,
					String description) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.openingHours = openingHours;
		this.deliveryCost = deliveryCost;
		this.minCost = minCost;
		this.menuType = menuType;
		this.description = description;
	}
	
	public Restaurant() {
	}
	
	public void addComment(Comment comment) {
		comments.add(comment);
	}
	
	public void addDish(Dish dish) {
		dishes.add(dish);
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
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
	
	public String getRanking() {
		return ranking;
	}
	
	public void setRanking(float ranking) {
		this.ranking = String.format("%.2f", ranking);
	}
	
	public List<Comment> getComments() {
		return comments;
	}
	
	public List<Dish> getDishes() {
		return dishes;
	}
	
	public void setDishes(List<Dish> dishes) {
		this.dishes = dishes;
	}
	
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}
