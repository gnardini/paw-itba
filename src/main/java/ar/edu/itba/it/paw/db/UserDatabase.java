package ar.edu.itba.it.paw.db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.util.RoleUtils;

public class UserDatabase extends Database<User> {
	
	private static final int FIRST_NAME = 2;
	private static final int LAST_NAME = 3;
	private static final int ADDRESS = 4;
	private static final int EMAIL = 5;
	private static final int BIRTHDATE = 6;
	private static final int ROLE = 7;
	private static final int PASSWORD = 8;
	
	private static UserDatabase sUserDatabase;
	
	
	public static UserDatabase getInstance() {
		if (sUserDatabase == null) sUserDatabase = new UserDatabase();
		return sUserDatabase;
	}

	private UserDatabase() {
	}
	
	public User getUser(String email, String password) {
		User user = getUser(email);
		if (user == null) return null;
		return user.getPassword().equals(password) ? user : null;
	}
	
	public User getUser(String email) {
		return doQuery("select * from users where email='" + email + "'");
	}
	
	protected User generate(ResultSet rs) throws SQLException {
		return new User(rs.getString(FIRST_NAME),
				rs.getString(LAST_NAME),
				rs.getString(ADDRESS),
				rs.getString(EMAIL),
				rs.getDate(BIRTHDATE),
				RoleUtils.getRoleFromString(rs.getString(ROLE)),
				rs.getString(PASSWORD));
	}
	
	public User signUp(User user) {
		User userExists = getUser(user.getEmail(), user.getPassword());
		if (userExists != null) return null;
		return insert("insert into users (firstname, lastname, address, email, birthdate, pass, type) values(?, ?, ?, ?, ?, ?, ?)", user);
	}
	
	@Override
	protected void storeData(PreparedStatement pst, User user) throws SQLException {
		pst.setString(FIRST_NAME, user.getFirstName());
		pst.setString(LAST_NAME, user.getLastName());
		pst.setString(ADDRESS, user.getAddress());
		pst.setString(EMAIL, user.getEmail());
		pst.setDate(BIRTHDATE, new Date(user.getBirthdate().getTime()));
		pst.setString(ROLE, user.getRole().toString());
		pst.setString(PASSWORD, user.getPassword());
	}
	
	public void storeUser(User user) {
		System.out.println("NEW USER");
	}
}
