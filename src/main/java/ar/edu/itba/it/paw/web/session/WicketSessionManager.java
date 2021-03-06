package ar.edu.itba.it.paw.web.session;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import ar.edu.itba.it.paw.model.Users;
import ar.edu.itba.it.paw.repository.UserRepo;

public class WicketSessionManager extends WebSession implements SessionManager {

	private static String EMAIL = "email";

	private UserRepo userRepo;
	private String email;

	public static WicketSessionManager get() {
		return (WicketSessionManager) Session.get();
	}
	
	public WicketSessionManager(Request request, UserRepo userRepo) {
		super(request);
		this.userRepo = userRepo;
	}

	public boolean isLogged() {
		return email != null;
	}

	public Users getUser() {
		if (email == null) {
			return null;
		}
		return userRepo.getUser(email);
	}
	
	public boolean signup(Users user) {
		if (userExists(user.getEmail())) {
			return false;
		}
		userRepo.storeUser(user);
		email = user.getEmail();
		return true;
	}
	
	private boolean userExists(String email) {
		return userRepo.getUser(email) != null;
	}

	public boolean login(String email, String password) {
		Users user = userRepo.getUser(email);
		if (user == null || !user.getPassword().equals(password)) {
			return false;
		}
		this.email = email;
		return true;
	}

	public void softLogout() {
		email = null;
	}
	
	public void logout() {
		email = null;
		invalidate();
		clear();
	}

}
