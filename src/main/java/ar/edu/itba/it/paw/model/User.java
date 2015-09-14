package ar.edu.itba.it.paw.model;
	
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
	int birthDay;
	int birthMonth;
	int birthYear;
	Role role;
	String password;
	
	public User() {
	}
	
	public User(long id, String firstName, String lastName, String address, String email, int birthDay, int birthMonth, int birthYear, Role role, String password) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
		this.birthDay = birthDay;
		this.birthMonth = birthMonth;
		this.birthYear = birthYear;
		this.role = role;
		this.password = password;
	}
	
	public User(String firstName, String lastName, String address, String email, int birthDay, int birthMonth, int birthYear, Role role, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
		this.birthDay = birthDay;
		this.birthMonth = birthMonth;
		this.birthYear = birthYear;
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
}
