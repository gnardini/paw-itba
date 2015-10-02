package ar.edu.itba.it.paw.manager;

import javax.servlet.http.HttpSession;

import ar.edu.itba.it.paw.model.User;

public interface SessionManager {

	public void setSession(HttpSession session);
	
	public boolean isLogged();
	
	public User getUser();
	
	public boolean signup(User user);
	
	public boolean login(String email, String password);
	
	public void logout();
}
