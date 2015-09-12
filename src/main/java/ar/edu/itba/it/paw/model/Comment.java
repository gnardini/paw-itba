package ar.edu.itba.it.paw.model;

public class Comment {

	long userId;
	long restaurantId;
	int rating;
	String text;
	
	public Comment(long userId, long restaurantId, int rating, String text) {
		this.userId = userId;
		this.restaurantId = restaurantId;
		this.text = text;
		this.rating = rating;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public long getRestaurantId() {
		return restaurantId;
	}
	
	public int getRating() {
		return rating;
	}
	
	public String getText() {
		return text;
	}
}
