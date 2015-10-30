package ar.edu.itba.it.paw.manager.implementation;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.model.Users;
import ar.edu.itba.it.paw.model.Users.Role;
import ar.edu.itba.it.paw.repository.UserRepo;

@Service
public class SessionManagerImpl implements SessionManager {
	
	private static String EMAIL = "email";
	
	private HttpSession mSession;
	private UserRepo mUserRepo;
	
	@Autowired
	public SessionManagerImpl(UserRepo userRepo) {
		mUserRepo = userRepo;
	}
	
	public void setSession(HttpSession session) {
		mSession = session;
	}
	
	public boolean isLogged() {
		return mSession.getAttribute(EMAIL) != null;
	}
	
	public Users getUser() {
		return mUserRepo.getUser((String) mSession.getAttribute(EMAIL));
	}
	
	public boolean login(String email, String password) {
		Users user = mUserRepo.getUser(email);
		if (user == null || !user.getPassword().equals(password)) return false;
		setUserInSession(user);
		return true;
	}
	
	public boolean signup(Users user) {
		if (userExists(user.getEmail())) return false;
		user.setRole(Role.ADMIN);
		mUserRepo.storeUser(user);
		setUserInSession(user);
		return user != null;
	}
	
	private boolean userExists(String email) {
		return mUserRepo.getUser(email) != null;
	}
	
	private void setUserInSession(Users user) {
		if (user != null) mSession.setAttribute(EMAIL, user.getEmail());
	}
	
	public void logout() {
		mSession.invalidate();
	}
}

