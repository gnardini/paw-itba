package ar.edu.itba.it.paw.model;

public class Comment {

	long userId;
	long restaurantId;
	String userName;
	int rating;
	String text;
	
	public Comment(long userId, long restaurantId, int rating, String text) {
		this.userId = userId;
		this.restaurantId = restaurantId;
		this.rating = rating;
		this.text = text;
	}
	
	public Comment(long userId, String userName, long restaurantId, int rating, String text) {
		this.userId = userId;
		this.userName = userName;
		this.restaurantId = restaurantId;
		this.rating = rating;
		this.text = text;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public long getRestaurantId() {
		return restaurantId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public int getRating() {
		return rating;
	}
	
	public String getText() {
		return text;
	}
}
