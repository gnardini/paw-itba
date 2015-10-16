package ar.edu.itba.it.paw.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Order extends PersistentEntity {

	@ManyToOne
	private User user;
	
	@ManyToOne
	private Restaurant restaurant;

	@OneToMany
	private List<OrderDetail> details;
	
	private String restaurantName;
	private Date made;
	private String price;
	private String orderDate;
	
	public Order() {
		
	}
	
	public Order(User user, Restaurant restaurant, long madeMillis, String restaurantName) {
		this.user = user;
		this.restaurant = restaurant;
		this.made = new Date(madeMillis);
		this.restaurantName = restaurantName;
		orderDate = new SimpleDateFormat("dd/MM").format(made);
		details = new LinkedList<>();
	}
	
	public Order(User user, Restaurant restaurant, Date made) {
		this.user = user;
		this.restaurant = restaurant;
		this.made = made;
		details = new LinkedList<>();
	}
	
	public void addDetail(String name, int price, int amount) {
		details.add(new OrderDetail(name, price, amount));
	}
	
	public User getUser() {
		return user;
	}
	
	public Restaurant getRestaurant() {
		return restaurant;
	}
	
	public String getRestaurantName() {
		return restaurantName;
	}
	
	public Date getMade() {
		return made;
	}
	
	public String getPrice() {
		return price;
	}
	
	public void setPrice(float price) {
		this.price = String.format("$%.2f", price);
	}
	
	public String getOrderDate() {
		return orderDate;
	}
	
	public List<OrderDetail> getDetails() {
		return details;
	}
	
	public void setDetails(List<OrderDetail> details) {
		this.details = details;
	}
}