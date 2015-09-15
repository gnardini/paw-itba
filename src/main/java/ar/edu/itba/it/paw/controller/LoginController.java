package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.manager.implementation.SessionManagerImpl;
import ar.edu.itba.it.paw.util.JspLocationUtils;
import ar.edu.itba.it.paw.util.Parameter;

public class LoginController extends Authentication {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionManager manager = new SessionManagerImpl(req);
		if (!manager.login(req.getParameter(Parameter.EMAIL), req.getParameter(Parameter.PASSWORD))) {
			req.setAttribute(Parameter.MESSAGE, "Usuario o contraseña erróneos");
			req.setAttribute(Parameter.ERROR, true);
		}
		doGet(req, resp);
	}
}