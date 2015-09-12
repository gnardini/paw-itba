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

public class BaseController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	protected static final String LOGGED = "logged";
	protected static final String ADMIN = "admin";
	protected static final String MANAGER = "manager";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionManager manager = new SessionManagerImpl(req);	
		req.setAttribute(LOGGED, manager.isLogged());
		if (manager.isLogged()) {
			User user = manager.getUser();
			req.setAttribute(ADMIN, user.getRole() == Role.ADMIN);
			req.setAttribute(MANAGER, user.getRole() == Role.MANAGER);
		}
	}
	
	protected void setMessage(HttpServletRequest req, String message) {
		req.setAttribute("message", message);
	}
}
