package ar.edu.itba.it.paw.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Comment {

	@ManyToOne
	User user;
	
	@ManyToOne
	Restaurant restaurant;
	
	String userName;
	int rating;
	String text;
	
	public Comment() {
		
	}
	
	public Comment(User user, Restaurant restaurant, int rating, String text) {
		this.user = user;
		this.userName = user.getFirstName();
		this.restaurant = restaurant;
		this.rating = rating;
		this.text = text;
	}
	
	public User getUser() {
		return user;
	}
	
	public Restaurant getRestaurant() {
		return restaurant;
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
