package ar.edu.itba.it.paw.model;

public class OrderDetail {

	private long orderId;
	private String name;
	private int price;
	private int amount;
	
	public OrderDetail(long orderId, String name, int price, int amount) {
		this.orderId = orderId;
		this.name = name;
		this.price = price;
		this.amount = amount;
	}	
	
	public OrderDetail(String name, int price, int amount) {
		this.name = name;
		this.price = price;
		this.amount = amount;
	}	
	
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	
	public long getOrderId() {
		return orderId;
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
