package ar.edu.itba.it.paw.manager.implementation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ar.edu.itba.it.paw.db.UserDatabase;
import ar.edu.itba.it.paw.manager.UserManager;
import ar.edu.itba.it.paw.model.User;

public class SessionManager implements UserManager {
	
	private static String EMAIL = "email";
	
	private HttpSession mSession;
	private UserDatabase mDatabase;
	
	public SessionManager(HttpServletRequest request) {
		mSession = request.getSession();
		mDatabase = UserDatabase.getInstance();
	}
	
	public boolean isLogged() {
		return mSession.getAttribute(EMAIL) != null;
	}
	
	public User getUser() {
		if (mDatabase != null) return mDatabase.getUser((String) mSession.getAttribute(EMAIL));
		return null;
	}
	
	public boolean login(String email, String password) {
		User user = null;
		if (mDatabase != null) user = mDatabase.getUser(email, password);
		setUserInSession(user);
		return user != null;
	}
	
	public boolean signup(User user) {
		if (mDatabase != null) user = mDatabase.signUp(user);
		setUserInSession(user);
		return user != null;
	}
	
	private void setUserInSession(User user) {
		if (user != null) mSession.setAttribute(EMAIL, user.getEmail());
	}
	
	public void logout() {
		mSession.invalidate();
	}
}

