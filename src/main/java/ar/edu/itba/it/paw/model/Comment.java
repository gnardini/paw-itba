package ar.edu.itba.it.paw.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
public class Comment extends PersistentEntity  {

	@ManyToOne
	Users user;
	
	@ManyToOne
	Restaurant restaurant;
	
	String userName;
	int rating;
	String text;
	
	public Comment() {
		
	}
	
	public Comment(Users user, Restaurant restaurant, int rating, String text) {
		this.user = user;
		this.userName = user.getFirstName();
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
		return userName;
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
