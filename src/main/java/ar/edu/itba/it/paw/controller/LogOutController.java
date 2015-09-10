package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.manager.UserManager;
import ar.edu.itba.it.paw.manager.implementation.SessionManager;

public class LogOutController extends Authentication {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserManager manager = new SessionManager(req);
		manager.logout();
		resp.sendRedirect(req.getHeader("Referer"));
	}
}
