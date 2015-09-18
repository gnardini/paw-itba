package ar.edu.itba.it.paw.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Order {

	private long id;
	private long userId;
	private long restaurantId;
	private Date made;
	private String price;
	private String orderDate;
	private List<OrderDetail> details;
	
	public Order(long id, long userId, long restaurantId, long madeMillis) {
		this.id = id;
		this.userId = userId;
		this.restaurantId = restaurantId;
		this.made = new Date(madeMillis);
		orderDate = new SimpleDateFormat("dd/MM").format(made);
		details = new LinkedList<>();
	}
	
	public Order(long userId, long restaurantId, Date made) {
		this.userId = userId;
		this.restaurantId = restaurantId;
		this.made = made;
		details = new LinkedList<>();
	}
	
	public void addDetail(String name, int price, int amount) {
		details.add(new OrderDetail(name, price, amount));
	}
	
	public long getId() {
		return id;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public long getRestaurantId() {
		return restaurantId;
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
