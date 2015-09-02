package ar.edu.itba.it.paw.manager.implementation;

import ar.edu.itba.it.paw.db.UserDatabase;
import ar.edu.itba.it.paw.manager.AdminManager;
import ar.edu.itba.it.paw.model.User;

public class AdminManagerImpl implements AdminManager {
	
	private UserDatabase mDatabase;
	
	public AdminManagerImpl() {
		mDatabase = UserDatabase.getInstance();
	}
	
	@Override
	public void addManager(User user) {
		if (mDatabase == null) return;
		mDatabase.storeUser(user);
	}
}
