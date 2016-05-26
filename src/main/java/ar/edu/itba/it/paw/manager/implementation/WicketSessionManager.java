package ar.edu.itba.it.paw.manager.implementation;

import java.util.List;

import javax.servlet.http.Cookie;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.it.paw.manager.SessionManager;
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
			List<Cookie> cookies = ((WebRequest) RequestCycle.get().getRequest()).getCookies();
	
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(EMAIL)) {
					email = cookie.getValue().split("/")[0];
				}
			}
		}
		return userRepo.getUser(email);
	}

	public boolean signup(Users user) {
		this.email = user.getEmail();
		return true;
	}

	public boolean login(String email, String password) {
		Users user = userRepo.getUser(email);
		if (user == null || !user.getPassword().equals(password)) {
			return false;
		}
		return true;
	}

	public void logout() {
		WebResponse wr = (WebResponse) (RequestCycle.get().getResponse());
		Cookie keepMeLogged = new Cookie(EMAIL, " ");
		keepMeLogged.setMaxAge(0);
		wr.addCookie(keepMeLogged);
		invalidate();
		clear();
	}

}
