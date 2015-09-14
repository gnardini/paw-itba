package ar.edu.itba.it.paw.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.model.User.Role;
import ar.edu.itba.it.paw.util.RoleUtils;

public class UserDatabase extends Database<User> {
	
	private static final int ID = 1;
	private static final int FIRST_NAME = 2;
	private static final int LAST_NAME = 3;
	private static final int ADDRESS = 4;
	private static final int EMAIL = 5;
	private static final int DAY = 6;
	private static final int MONTH = 7;
	private static final int YEAR = 8;
	private static final int PASSWORD = 9;
	private static final int ROLE = 10;

	public User getUser(long userId) {
		return doQuery("select * from users where id='" + userId + "'");
	}
	
	public User getUser(String email) {
		return doQuery("select * from users where email='" + email + "'");
	}
	
	public List<User> getUsers(Role role) {
		return doListQuery("select * from users where lower(type)='" + role.toString().toLowerCase() + "'");
	}
	
	public User storeUser(User user) {
		return insert("insert into users (firstname, lastname, address, email, birthday, birthmonth, birthyear, pass, type) values(?, ?, ?, ?, ?, ?, ?, ?, ?)", user);
	}
	
	public void updateUserRole(User user) {
		update("update users "
				+ "set type='" + user.getRole().toString().toLowerCase() + "'"
				+ "where id='" + user.getId() + "';");
	}
	
	protected User generate(ResultSet rs) throws SQLException {
		return new User(rs.getLong(ID),
				rs.getString(FIRST_NAME),
				rs.getString(LAST_NAME),
				rs.getString(ADDRESS),
				rs.getString(EMAIL),
				rs.getInt(DAY),
				rs.getInt(MONTH),
				rs.getInt(YEAR),
				RoleUtils.getRoleFromString(rs.getString(ROLE).toLowerCase()),
				rs.getString(PASSWORD));
	}
	
	@Override
	protected void storeData(PreparedStatement pst, User user) throws SQLException {
		pst.setString(FIRST_NAME - 1, user.getFirstName());
		pst.setString(LAST_NAME - 1, user.getLastName());
		pst.setString(ADDRESS - 1, user.getAddress());
		pst.setString(EMAIL - 1, user.getEmail());
		pst.setInt(DAY - 1, user.getBirthDay());
		pst.setInt(MONTH - 1, user.getBirthMonth());
		pst.setInt(YEAR - 1, user.getBirthYear());
		pst.setString(ROLE - 1, user.getRole().toString());
		pst.setString(PASSWORD - 1, user.getPassword());
	}
}
