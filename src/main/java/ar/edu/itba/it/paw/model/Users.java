package ar.edu.itba.it.paw.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Users extends PersistentEntity {

	public enum Role {
		NORMAL,
		MANAGER,
		ADMIN;
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
	
	@ManyToOne
	Neighbourhood neighbourhood;
	
	@ManyToMany
	List<Restaurant> restaurants;
	
	@OneToMany(mappedBy="user")
	List<Orders> orders;
	
	public Users() {
	}
	
	public Users(String firstName, String lastName, String address, String email, int birthDay, int birthMonth, int birthYear, Role role, String password, Neighbourhood neighbourhood) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
		this.birthDay = birthDay;
		this.birthMonth = birthMonth;
		this.birthYear = birthYear;
		this.role = role;
		this.password = password;
		this.neighbourhood = neighbourhood;
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
	
	public Neighbourhood getNeighbourhood() {
		return neighbourhood;
	}
	
	public void setNeighbourhood(Neighbourhood neighbourhood) {
		this.neighbourhood = neighbourhood;
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

	public boolean assignRestaurant(Restaurant restaurant) {
		if (restaurants.contains(restaurant)) return false;
		restaurants.add(restaurant);
		return true;
	}

	public boolean makeManager() {
		if (role == Role.MANAGER) return false;
		role = Role.MANAGER;
		return true;
	}

	public void addOrder(Orders order) {
		orders.add(order);
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setBirthDay(int birthDay) {
		this.birthDay = birthDay;
	}

	public void setBirthMonth(int birthMonth) {
		this.birthMonth = birthMonth;
	}

	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRestaurants(List<Restaurant> restaurants) {
		this.restaurants = restaurants;
	}

	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}
}
