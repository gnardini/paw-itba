package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.manager.UserManager;
import ar.edu.itba.it.paw.manager.implementation.SessionManager;
import ar.edu.itba.it.paw.util.JspLocationUtils;

public class Login extends BaseController {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
		if ((Boolean) req.getAttribute(LOGGED)) {
			req.getRequestDispatcher(JspLocationUtils.RESTAURANTS).forward(req,  resp);
			return;
		}
		req.getRequestDispatcher(JspLocationUtils.LOGIN).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserManager manager = new SessionManager(req);
		if (!manager.login(req.getParameter("email"), req.getParameter("password"))) {
			req.setAttribute("message", "Usuario o contrasena erroneos");
		}		
		doGet(req, resp);
	}
}
