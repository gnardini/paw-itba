package ar.edu.itba.it.paw.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Orders extends PersistentEntity implements Serializable {

	@ManyToOne
	private Users user;
	
	@ManyToOne
	private Restaurant restaurant;

	@OneToMany(mappedBy="order")
	private List<OrderDetail> details;
	
	private String restaurantName;
	private Date made;
	private float price;
	private boolean delivered;
	
	public Orders() {
	}
	
	public Orders(Users user, Restaurant restaurant, Date made) {
		this.user = user;
		this.restaurant = restaurant;
		this.made = made;
		details = new LinkedList<>();
	}
	
	public void addDetail(String name, float price, int amount) {
		details.add(new OrderDetail(this, name, price, amount));
	}
	
	public Users getUser() {
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
	
	public float getPrice() {
		return price;
	}
	
	public void setPrice(float price) {
		this.price = price;
	}
	
	public String getOrderDate() {
		return new SimpleDateFormat("dd/MM").format(made);
	}
	
	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}
	
	public boolean isDelivered() {
		return delivered;
	}
	
	public List<OrderDetail> getDetails() {
		return details;
	}
	
	public void setDetails(List<OrderDetail> details) {
		this.details = details;
	}

	public void setOnDependants() {
		user.addOrder(this);
		restaurant.addOrder(this);
	}
}
