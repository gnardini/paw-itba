package ar.edu.itba.it.paw.manager;

import javax.servlet.http.HttpSession;

import ar.edu.itba.it.paw.model.Users;

public interface SessionManager {

	public boolean isLogged();
	
	public Users getUser();
	
	public boolean signup(Users user);
	
	public boolean login(String email, String password);
	
	public void logout();
}
