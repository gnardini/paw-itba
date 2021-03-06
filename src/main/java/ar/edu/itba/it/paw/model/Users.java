package ar.edu.itba.it.paw.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Users extends PersistentEntity implements Serializable {

	public enum Role {
		NORMAL("Cliente"),
		MANAGER("Gerente"),
		ADMIN("Administrador");
		
		private String roleName;
		
		private Role(String roleName) {
			this.roleName = roleName;
		}
		
		public String getRoleName() {
			return roleName;
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
	Date lastLogin;
	Date lastPasswordChange;
	boolean enabled;
	
	@ManyToOne
	Neighbourhood neighbourhood;
	
	@ManyToMany
	@JoinTable(
            name = "users_restaurant",
            joinColumns = {@JoinColumn(name = "managers_id")},
            inverseJoinColumns = {@JoinColumn(name = "restaurants_id")}
    )
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
		this.lastLogin = new Date();
		this.lastPasswordChange = new Date();
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
	
	public List<RestaurantNeighbourhoodOrderCount> getRestaurantOrdersByNeighbourhoodInInterval(Date fromDate, Date toDate) {
		List<RestaurantNeighbourhoodOrderCount> restaurantOrdersInInverval = new LinkedList<>();
		for (Restaurant restaurant: getRestaurants()) {
			for (Neighbourhood neighbourhood: restaurant.getNeighbourhoods()) {
				Map<OrderDate, Integer> ordersInRange = new HashMap<>(); 
				for (Orders order: restaurant.getOrders()) {
					Date orderDate = order.getMade();
					if (orderDate.before(toDate) 
							&& orderDate.after(fromDate)
							&& order.getUser().getNeighbourhood().equals(neighbourhood)) {
						OrderDate updateOrderDate = new OrderDate(orderDate);
						if (!ordersInRange.containsKey(updateOrderDate)) {
							ordersInRange.put(updateOrderDate, 1);
						} else {
							ordersInRange.put(updateOrderDate, ordersInRange.get(updateOrderDate) + 1);
						}
					}
				}
				for (Map.Entry<OrderDate, Integer> entry: ordersInRange.entrySet()) {
					restaurantOrdersInInverval.add(new RestaurantNeighbourhoodOrderCount(
							restaurant, entry.getValue(), neighbourhood, entry.getKey()));
				}
			}
		}
		return restaurantOrdersInInverval;
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
	
	public String getFullName() {
		return firstName + " " + lastName;
	}
	
	public Date getLastLogin() {
		return lastLogin;
	}
	
	public Date getLastPasswordChange() {
		return lastPasswordChange;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	public void setLastPasswordChange(Date lastPasswordChange) {
		this.lastPasswordChange = lastPasswordChange;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	@Override
	public String toString() {
		return getFullName();
	}
	
}
