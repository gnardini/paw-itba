package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.manager.UserManager;
import ar.edu.itba.it.paw.manager.implementation.SessionManagerImpl;
import ar.edu.itba.it.paw.manager.implementation.UserManagerImpl;
import ar.edu.itba.it.paw.util.Page;
import ar.edu.itba.it.paw.util.Parameter;

public class NewManagerController extends AdminPanelController {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserManager userManager = new UserManagerImpl();
		long userId = Long.valueOf(req.getParameter(Parameter.USER_ID));
		if (userManager.makeUserManager(userId)) {
			setMessage(req, "Nuevo gerente agregado");
			setMessageType(req, Parameter.SUCCESS);
		} else {
			setMessage(req, "No se pudo crear un nuevo gerente");
			setMessageType(req, Parameter.ERROR);
		}
		doGet(req, resp);
	}
}
