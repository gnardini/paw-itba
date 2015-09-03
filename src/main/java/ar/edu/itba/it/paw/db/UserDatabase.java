package ar.edu.itba.it.paw.db;

import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.model.User.Role;

public class UserDatabase extends Database {
	
	private static UserDatabase sUserDatabase;

	public static UserDatabase getInstance() {
		if (sUserDatabase == null) sUserDatabase = new UserDatabase();
		return sUserDatabase;
	}

	private UserDatabase() {
	}
	
	public User getUser(String email, String password) {
		return new User("Admin", "", "Direccion","admin@admin.com", 100, Role.MANAGER, "admin");
	}
	
	public User getUser(String email) {
		return new User("Admin", "", "Direccion","admin@admin.com", 100, Role.MANAGER, "admin");
	}
	
	public User signUp(User user) {
		return new User("Admin", "", "Direccion","admin@admin.com", 100, Role.MANAGER, "admin");
	}
	
	public void storeUser(User user) {
		System.out.println("NEW USER");
	}
}
