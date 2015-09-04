package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.helper.UserValidationHelper;
import ar.edu.itba.it.paw.manager.AdminManager;
import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.UserManager;
import ar.edu.itba.it.paw.manager.implementation.AdminManagerImpl;
import ar.edu.itba.it.paw.manager.implementation.RestaurantManagerImpl;
import ar.edu.itba.it.paw.manager.implementation.SessionManager;
import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.model.User.Role;
import ar.edu.itba.it.paw.util.JspLocationUtils;

public class AdminPanelController extends ControlPanelController {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AdminManager manager = new AdminManagerImpl();
		UserValidationHelper validator = new UserValidationHelper(req);
		if (validator.isValidUser(Role.MANAGER)) {
			manager.addManager(validator.getUser());
			setMessage(req, "Se completo la operacion");
		} else {
			setMessage(req, "No se pudo completar la operacion");
		}
		doGet(req, resp, JspLocationUtils.ADMIN_PANEL);
	}
}
