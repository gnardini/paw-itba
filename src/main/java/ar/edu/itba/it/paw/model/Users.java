package ar.edu.itba.it.paw.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Users extends PersistentEntity {

	public enum Role {
		NORMAL,
		MANAGER,
		ADMIN;
		
		public String toString() {
			return name();
		}
	}
	
	String firstName;
	String lastName;
	String address;
	String email;
	int birthDay;
	int birthMonth;
	int birthYear;
	Role role;
	String password;
	
	@ManyToMany
	List<Restaurant> restaurants;
	
	@OneToMany
	List<Orders> orders;
	
	public Users() {
	}
	
	public Users(String firstName, String lastName, String address, String email, int birthDay, int birthMonth, int birthYear, Role role, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
		this.birthDay = birthDay;
		this.birthMonth = birthMonth;
		this.birthYear = birthYear;
		this.role = role;
		this.password = password;
		restaurants = new LinkedList<>();
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getEmail() {
		return email;
	}
	
	public int getBirthDay() {
		return birthDay;
	}
	
	public int getBirthMonth() {
		return birthMonth;
	}
	
	public int getBirthYear() {
		return birthYear;
	}
	
	public Role getRole() {
		return role;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public List<Restaurant> getRestaurants() {
		return restaurants;
	}
	
	public List<Orders> getOrders() {
		return orders;
	}
	
	public boolean isManagerOf(Restaurant restaurant) {
		return restaurants.contains(restaurant);
	}
	
	public void addRestaurantToManage(Restaurant restaurant) {
		restaurants.add(restaurant);
	}

	public void assignRestaurant(Restaurant restaurant) {
		restaurants.add(restaurant);
	}

	public boolean makeManager() {
		if (role == Role.MANAGER) return false;
		role = Role.MANAGER;
		return true;
	}

	public void addOrder(Orders order) {
		orders.add(order);
	}
}
