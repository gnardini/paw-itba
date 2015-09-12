package ar.edu.itba.it.paw.manager.implementation;

import java.util.List;

import ar.edu.itba.it.paw.db.UserDatabase;
import ar.edu.itba.it.paw.manager.UserManager;
import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.model.User.Role;

public class UserManagerImpl implements UserManager {

	private UserDatabase mDatabase;
	
	public UserManagerImpl() {
		mDatabase = new UserDatabase();
	}
	
	@Override
	public User getUser(long id) {
		return mDatabase.getUser(id);
	}

	@Override
	public User getUser(String email) {
		return mDatabase.getUser(email);
	}

	@Override
	public List<User> getUsers(Role role) {
		return mDatabase.getUsers(role);
	}

	@Override
	public boolean makeUserManager(long id) {
		User user = getUser(id);
		if (user == null) return false;
		user.setRole(Role.MANAGER);
		mDatabase.updateUser(user);
		return true;
	}
}
