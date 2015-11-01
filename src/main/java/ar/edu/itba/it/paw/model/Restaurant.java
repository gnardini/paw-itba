package ar.edu.itba.it.paw.model;

import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
public class Restaurant extends PersistentEntity {

	String name;
	String address;
	String openingHours;
	int deliveryCost;
	int minCost;
	String menuType;
	String description;
	String ranking;
	
	@OneToMany
	List<Dish> dishes;
	
	@OneToMany(mappedBy="restaurant")
	List<Comment> comments;
	
	@OneToMany(mappedBy="restaurant")
	List<Orders> orders;
	
	@ManyToMany(mappedBy="restaurants")
	List<Users> managers;
	
	public Restaurant(String name,
					String address,
					String openingHours,
					int deliveryCost,
					int minCost,
					String menuType,
					String description) {
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

	public boolean canUserComment(Users user) {
		for (Comment comment: comments) {
			if (comment.getUser().equals(user)) return false;
		}
		return true;
	}
	
	public Dish getDish(long dishId) {
		for (Dish dish: dishes) {
			if (dish.getId() == dishId) return dish;
		}
		return null;
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
	
	public List<Orders> getOrders() {
		return orders;
	}

	public void updateWithData(Restaurant updatedRestaurant) {
		name = updatedRestaurant.getName();
		address = updatedRestaurant.getAddress();
		openingHours = updatedRestaurant.getOpeningHours();
		deliveryCost = updatedRestaurant.getDeliveryCost();
		minCost = updatedRestaurant.getMinCost();
		menuType = updatedRestaurant.getMenuType();
		description = updatedRestaurant.getDescription();
		ranking = updatedRestaurant.getRanking();
	}

	public void deleteUserComment(Users user) {
		Iterator<Comment> it = comments.iterator();	
		while (it.hasNext()) {
			Comment comment = it.next();
			if (comment.getUser().equals(user)) {
				it.remove();
				return;
			}
		}
	}

	public void addOrder(Orders order) {
		orders.add(order);
	}
}
