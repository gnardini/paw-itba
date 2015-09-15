package ar.edu.itba.it.paw.manager.implementation;

import java.util.List;

import ar.edu.itba.it.paw.db.ManagerDatabase;
import ar.edu.itba.it.paw.db.UserDatabase;
import ar.edu.itba.it.paw.manager.UserManager;
import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.model.User.Role;

public class UserManagerImpl implements UserManager {

	private UserDatabase mUserDatabase;
	private ManagerDatabase mManagerDatabase;
	
	public UserManagerImpl() {
		mUserDatabase = new UserDatabase();
		mManagerDatabase = new ManagerDatabase();
	}
	
	@Override
	public User getUser(long id) {
		return mUserDatabase.getUser(id);
	}

	@Override
	public User getUser(String email) {
		return mUserDatabase.getUser(email);
	}

	@Override
	public List<User> getUsers(Role role) {
		return mUserDatabase.getUsers(role);
	}

	@Override
	public boolean makeUserManager(long id) {
		User user = getUser(id);
		if (user == null) return false;
		user.setRole(Role.MANAGER);
		mUserDatabase.updateUserRole(user);
		return true;
	}
	
	@Override
	public boolean assignManager(long managerId, long restaurantId) {
		if (mManagerDatabase.getManagerRelation(managerId, restaurantId) != null) return false;
		mManagerDatabase.assignManager(managerId, restaurantId);
		return true;
	}
}
