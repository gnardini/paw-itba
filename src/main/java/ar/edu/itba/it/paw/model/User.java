package ar.edu.itba.it.paw.model;

import java.util.Date;

public class User {

	public enum Role {
		NORMAL,
		MANAGER,
		ADMIN;
		
		public String toString() {
			return name();
		}
	}
	
	long id;
	String firstName;
	String lastName;
	String address;
	String email;
	Date birthdate;
	Role role;
	String password;
	
	public User() {
	}
	
	public User(long id, String firstName, String lastName, String address, String email, Date birthdate, Role role, String password) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
		this.birthdate = birthdate;
		this.role = role;
		this.password = password;
	}

	public long getId() {
		return id;
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
	public Date getBirthdate() {
		return birthdate;
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
}
