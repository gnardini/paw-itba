package ar.edu.itba.it.paw.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class OrderDetail {

	@ManyToOne
	private Order order;
	
	private String name;
	private int amount;
	private int price;
	
	public OrderDetail() {
		
	}
	
	public OrderDetail(Order order, String name, int amount, int price) {
		this.order = order;
		this.name = name;
		this.amount = amount;
		this.price = price;
	}	
	
	public OrderDetail(String name, int amount, int price) {
		this.name = name;
		this.price = price;
		this.amount = amount;
	}	
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public Order getOrder() {
		return order;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPrice() {
		return price;
	}
	
	public int getAmount() {
		return amount;
	}
}
