package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.manager.implementation.SessionManagerImpl;
import ar.edu.itba.it.paw.util.JspLocationUtils;

public class LoginController extends Authentication {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionManager manager = new SessionManagerImpl(req);
		if (!manager.login(req.getParameter("email"), req.getParameter("password"))) {
			boolean error=true;
			req.setAttribute("message", "Usuario o contraseña erróneos");
			req.setAttribute("error", error);
		}
		doGet(req, resp);
	}
}