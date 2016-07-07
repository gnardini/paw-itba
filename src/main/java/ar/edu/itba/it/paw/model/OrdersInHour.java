package ar.edu.itba.it.paw.model;

import java.io.Serializable;

public class OrdersInHour implements Serializable {

	private int hour;
	private int ordersCount;
	
	public OrdersInHour(int hour) {
		this.hour = hour;
		this.ordersCount = 0;
	}
	
	public int getHour() {
		return hour;
	}
	
	public int getOrdersCount() {
		return ordersCount;
	}
	
	public void addOrder() {
		ordersCount++;
	}
	
}
