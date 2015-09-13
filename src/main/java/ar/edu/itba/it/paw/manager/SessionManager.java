package ar.edu.itba.it.paw.manager;

import ar.edu.itba.it.paw.model.User;

public interface SessionManager {

	public boolean isLogged();
	
	public User getUser();
	
	public boolean signup(User user);
	
	public boolean login(String email, String password);
	
	public void logout();
}