package ar.edu.itba.it.paw.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class Restaurant extends PersistentEntity implements Serializable {

	String name;
	String address;
	int openingHour;
	int closingHour;
	int deliveryCost;
	int minCost;
	String menuType;
	String description;
	Date closedDate;
	Date createdDate;
	String closedReason;
	
	@OneToMany
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	List<Dish> dishes;

	@OneToMany
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	List<Comment> comments;

	@OneToMany(mappedBy = "restaurant")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	List<Orders> orders;

	@ManyToMany
	@Cascade({CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
            name = "users_restaurant",
            inverseJoinColumns = {@JoinColumn(name = "managers_id")},
            joinColumns = {@JoinColumn(name = "restaurants_id")}
    )
	List<Users> managers;

	@ManyToMany
	List<Neighbourhood> neighbourhoods;

	public Restaurant(String name, String address, int openingHour, int closingHour, int deliveryCost, int minCost,
			String menuType, String description) {
		this.name = name;
		this.address = address;
		this.openingHour = openingHour;
		this.closingHour = closingHour;
		this.deliveryCost = deliveryCost;
		this.minCost = minCost;
		this.menuType = menuType;
		this.description = description;
	}

	public Restaurant(String name, String address, int openingHour, int closingHour, int deliveryCost, int minCost,
			String menuType, String description, Neighbourhood neighbourhood, Date createdDate) {
		this.name = name;
		this.address = address;
		this.openingHour = openingHour;
		this.closingHour = closingHour;
		this.deliveryCost = deliveryCost;
		this.minCost = minCost;
		this.menuType = menuType;
		this.description = description;
		neighbourhoods = new LinkedList<>();
		neighbourhoods.add(neighbourhood);
		this.createdDate = createdDate;
		this.closedDate = new Date(0);
		this.closedReason = "";
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
		for (Comment comment : comments) {
			if (comment.getUser().equals(user))
				return false;
		}
		return true;
	}

	public Dish getDish(long dishId) {
		for (Dish dish : dishes) {
			if (dish.getId() == dishId)
				return dish;
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public int getDeliveryCost() {
		return deliveryCost;
	}

	public int getOpeningHour() {
		return openingHour;
	}

	public void setOpeningHour(int openingHour) {
		this.openingHour = openingHour;
	}

	public int getClosingHour() {
		return closingHour;
	}

	public void setClosingHour(int closingHour) {
		this.closingHour = closingHour;
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

	public float getRanking() {
		if (comments.isEmpty()) {
			return 0;
		}
		float total = 0;
		for (Comment comment: comments) {
			total += comment.rating;
		}
		return total / comments.size();
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
	
	public List<Orders> getOrdersInNeighbourhood(Neighbourhood neighbourhood) {
		List<Orders> orders = new LinkedList<>();
		for (Orders order: getOrders()) {
			if (order.getUser().getNeighbourhood().equals(neighbourhood)) {
				orders.add(order);
			}
		}
		return orders;
	}

	public void updateWithData(Restaurant updatedRestaurant) {
		name = updatedRestaurant.getName();
		address = updatedRestaurant.getAddress();
		openingHour = updatedRestaurant.getOpeningHour();
		closingHour = updatedRestaurant.getClosingHour();
		deliveryCost = updatedRestaurant.getDeliveryCost();
		minCost = updatedRestaurant.getMinCost();
		menuType = updatedRestaurant.getMenuType();
		description = updatedRestaurant.getDescription();
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

	public void addNeighbourhood(Neighbourhood neighbourhood) {
		neighbourhoods.add(neighbourhood);
	}

	public List<Neighbourhood> getNeighbourhoods() {
		return neighbourhoods;
	}

	public boolean reachesNeighbourhood(Neighbourhood neighbourhood) {
		return neighbourhoods.contains(neighbourhood);
	}

	public void removeNeighbourhood(Neighbourhood neighbourhood) {
		neighbourhoods.remove(neighbourhood);
	}

	public boolean canOrder(Orders order) {
		if (openingHour == closingHour)
			return true;
		int orderHour = order.getMade().getHours();
		if (openingHour > closingHour) {
			return orderHour >= openingHour || orderHour < closingHour;
		} else {
			return orderHour >= openingHour && orderHour < closingHour;
		}
	}
	
	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public String getClosedReason() {
		return closedReason;
	}

	public void setClosedReason(String closedReason) {
		this.closedReason = closedReason;
	}

	public Date getCreatedDate() {
		return createdDate;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
