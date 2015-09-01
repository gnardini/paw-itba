package ar.edu.itba.it.paw.manager.implementation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ar.edu.itba.it.paw.manager.UserManager;
import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.model.User.Role;

public class SessionManager implements UserManager {
	
	private static String NAME_ID = "name";
	
	private HttpServletRequest mRequest;
	
	public SessionManager(HttpServletRequest request) {
		mRequest = request;
	}
	
	public boolean isLogged() {
		return false;//session().getAttribute(NAME_ID) != null;
	}
	
	public User getUser() {
		return new User("Admin", "", "Direccion","admin@admin.com", 100, Role.ADMIN, "admin");
	}
	
	public void login(String email, String password) {
		
	}
	
	public void logout() {
		
	}
	
	private HttpSession session() {
		return mRequest.getSession();
	}
}

