package ar.edu.itba.it.paw.model;

public class User {

	public enum Role {
		NORMAL,
		MANAGER,
		ADMIN;
	}
	
	String firstName;
	String lastName;
	String address;
	String email;
	int age;
	Role role;
	String password;
	
	public User() {
	}
	
	public User(String firstName, String lastName, String address, String email, int age, Role role, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
		this.age = age;
		this.role = role;
		this.password = password;
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
	public int getAge() {
		return age;
	}
	public Role getRole() {
		return role;
	}
	public String getPassword() {
		return password;
	}
}
