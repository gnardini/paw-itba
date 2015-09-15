package ar.edu.itba.it.paw.model;

public class OrderDetail {

	private long orderId;
	private String name;
	private int amount;
	private int price;
	
	public OrderDetail(long orderId, String name, int amount, int price) {
		this.orderId = orderId;
		this.name = name;
		this.amount = amount;
		this.price = price;
	}	
	
	public OrderDetail(String name, int amount, int price) {
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
