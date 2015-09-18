package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.UserManager;
import ar.edu.itba.it.paw.manager.implementation.RestaurantManagerImpl;
import ar.edu.itba.it.paw.manager.implementation.UserManagerImpl;
import ar.edu.itba.it.paw.util.Parameter;

public class AssignManagerController extends AdminPanelController {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserManager userManager = new UserManagerImpl();
		RestaurantManager restaurantManager = new RestaurantManagerImpl();
		long managerId = Long.valueOf(req.getParameter(Parameter.MANAGER_ID));
		long restaurantId = Long.valueOf(req.getParameter(Parameter.RESTAURANT_ID));
		if (userManager.assignManager(managerId, restaurantId)) {
			setMessage(req, "Se completo la operacion");
			setMessageType(req, Parameter.SUCCESS);
		} else {
			setMessage(req, "No se pudo completar la operacion");
			setMessageType(req, Parameter.ERROR);
		}
		doGet(req, resp);
	}
}
