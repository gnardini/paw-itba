package ar.edu.itba.it.paw.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class OrderDetail extends PersistentEntity {

	@ManyToOne
	private Orders order;
	
	private String name;
	private int amount;
	private float price;
	
	public OrderDetail() {
	}
	
	public OrderDetail(Orders order, String name, float price, int amount) {
		this.order = order;
		this.name = name;
		this.amount = amount;
		this.price = price;
	}	
	
	public void setOrder(Orders order) {
		this.order = order;
	}
	
	public Orders getOrder() {
		return order;
	}
	
	public String getName() {
		return name;
	}
	
	public float getPrice() {
		return price;
	}
	
	public int getAmount() {
		return amount;
	}
}
