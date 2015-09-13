package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.UserManager;
import ar.edu.itba.it.paw.manager.implementation.RestaurantManagerImpl;
import ar.edu.itba.it.paw.manager.implementation.UserManagerImpl;
import ar.edu.itba.it.paw.model.User.Role;
import ar.edu.itba.it.paw.util.JspLocationUtils;

public class AdminPanelController extends ControlPanelController {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
		if (!(Boolean) req.getAttribute(LOGGED)) return;
		UserManager userManager = new UserManagerImpl();
		RestaurantManager restaurantManager = new RestaurantManagerImpl();
		req.setAttribute("users", userManager.getUsers(Role.NORMAL));
		req.setAttribute("managers", userManager.getUsers(Role.MANAGER));
		req.setAttribute("restaurants", restaurantManager.getRestaurants());
		req.getRequestDispatcher(JspLocationUtils.ADMIN_PANEL).forward(req, resp);
	}
}
