package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.manager.UserManager;
import ar.edu.itba.it.paw.manager.implementation.UserManagerImpl;
import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.model.User.Role;

public class NewManagerController extends BaseController {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserManager manager = new UserManagerImpl();
		long userId = Long.valueOf(req.getParameter("user"));
		
		if (manager.makeUserManager(userId)) setMessage(req, "Se completo la operacion");
		else setMessage(req, "No se pudo completar la operacion");
		
		resp.sendRedirect("adminPanel");
	}
}
