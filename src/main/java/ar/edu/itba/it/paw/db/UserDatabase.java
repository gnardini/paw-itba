package ar.edu.itba.it.paw.db;

import ar.edu.itba.it.paw.model.User;

public class UserDatabase extends Database {
	
	private static UserDatabase sUserDatabase;

	public static UserDatabase getInstance() {
		if (sUserDatabase == null) sUserDatabase = new UserDatabase();
		return sUserDatabase;
	}

	private UserDatabase() {
	}
	
	public void storeUser(User user) {
		System.out.println("NEW USER");
	}
}
