package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.UserManager;
import ar.edu.itba.it.paw.manager.implementation.RestaurantManagerImpl;
import ar.edu.itba.it.paw.manager.implementation.UserManagerImpl;

public class AssignManagerController extends BaseController {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserManager userManager = new UserManagerImpl();
		RestaurantManager restaurantManager = new RestaurantManagerImpl();
		long managerId = Long.valueOf(req.getParameter("managerId"));
		long restaurantId = Long.valueOf(req.getParameter("restaurantId"));
		if (userManager.assignManager(managerId, restaurantId)) setMessage(req, "Se completo la operacion");
		else setMessage(req, "No se pudo completar la operacion");
		resp.sendRedirect("adminPanel");
	}
}
