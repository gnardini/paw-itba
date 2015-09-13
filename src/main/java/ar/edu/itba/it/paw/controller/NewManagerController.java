package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.manager.UserManager;
import ar.edu.itba.it.paw.manager.implementation.SessionManagerImpl;
import ar.edu.itba.it.paw.manager.implementation.UserManagerImpl;

public class NewManagerController extends BaseController {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserManager userManager = new UserManagerImpl();
		long userId = Long.valueOf(req.getParameter("userId"));
		if (userManager.makeUserManager(userId)) setMessage(req, "Se completo la operacion");
		else setMessage(req, "No se pudo completar la operacion");
		resp.sendRedirect("adminPanel");
	}
}
