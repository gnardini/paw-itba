package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.manager.implementation.SessionManagerImpl;
import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.model.User.Role;
import ar.edu.itba.it.paw.util.Parameter;

public class OldBaseController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionManager manager = new SessionManagerImpl();
		manager.setSession(req.getSession());
		req.setAttribute(Parameter.LOGGED, manager.isLogged());
		if (manager.isLogged()) {
			User user = manager.getUser();
			req.setAttribute(Parameter.USER_ADMIN, user.getRole() == Role.ADMIN);
			req.setAttribute(Parameter.USER_MANAGER, user.getRole() == Role.MANAGER);
			req.setAttribute(Parameter.USER_NORMAL, user.getRole() == Role.NORMAL);
		}
	}
	
	protected void setMessage(HttpServletRequest req, String message) {
		req.setAttribute(Parameter.MESSAGE, message);
	}
	
	protected void setMessageType(HttpServletRequest req, String type) {
		req.setAttribute(type, true);
	}
}