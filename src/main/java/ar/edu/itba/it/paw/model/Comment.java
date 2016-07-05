package ar.edu.itba.it.paw.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Comment extends PersistentEntity implements Serializable  {

	@ManyToOne
	Users user;
	
	@ManyToOne
	Restaurant restaurant;
	
	int rating;
	String text;
	
	public Comment() {
		
	}
	
	public Comment(Users user, Restaurant restaurant, int rating, String text) {
		this.user = user;
		this.restaurant = restaurant;
		this.rating = rating;
		this.text = text;
	}
	
	public Users getUser() {
		return user;
	}
	
	public Restaurant getRestaurant() {
		return restaurant;
	}
	
	public String getUserName() {
		return user.firstName;
	}
	
	public int getRating() {
		return rating;
	}
	
	public String getText() {
		return text;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (restaurant == null) {
			if (other.restaurant != null)
				return false;
		} else if (!restaurant.equals(other.restaurant))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}	
}
