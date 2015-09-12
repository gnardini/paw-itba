package ar.edu.itba.it.paw.manager.implementation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ar.edu.itba.it.paw.db.UserDatabase;
import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.model.User;

public class SessionManagerImpl implements SessionManager {
	
	private static String EMAIL = "email";
	
	private HttpSession mSession;
	private UserDatabase mDatabase;
	
	public SessionManagerImpl(HttpServletRequest request) {
		mSession = request.getSession();
		mDatabase = new UserDatabase();
	}
	
	public boolean isLogged() {
		return mSession.getAttribute(EMAIL) != null;
	}
	
	public User getUser() {
		return mDatabase.getUser((String) mSession.getAttribute(EMAIL));
	}
	
	public boolean login(String email, String password) {
		User user = mDatabase.getUser(email);
		if (!user.getPassword().equals(password)) return false;
		setUserInSession(user);
		return user != null;
	}
	
	public boolean signup(User user) {
		if (userExists(user.getEmail())) return false;
		user = mDatabase.storeUser(user);
		setUserInSession(user);
		return user != null;
	}
	
	private boolean userExists(String email) {
		return mDatabase.getUser(email) != null;
	}
	
	private void setUserInSession(User user) {
		if (user != null) mSession.setAttribute(EMAIL, user.getEmail());
	}
	
	public void logout() {
		mSession.invalidate();
	}
}

