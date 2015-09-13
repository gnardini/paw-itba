package ar.edu.itba.it.paw.db.model;

public class Manager {

	private long managerId;
	private long restaurantId;
	
	public Manager(long managerId, long restaurantId) {
		this.managerId = managerId;
		this.restaurantId = restaurantId;
	}
	
	public long getManagerId() {
		return managerId;
	}
	
	public long getRestaurantId() {
		return restaurantId;
	}
}
