package ar.edu.itba.it.paw.manager;

import ar.edu.itba.it.paw.model.User;

public interface UserManager {

	public boolean isLogged();
	
	public User getUser();
	
	public boolean login(String email, String password);
	
	public void logout();
}
