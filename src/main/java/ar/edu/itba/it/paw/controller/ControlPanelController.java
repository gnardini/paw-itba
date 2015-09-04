package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.helper.DishValidationHelper;
import ar.edu.itba.it.paw.helper.RestaurantValidationHelper;
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

public class ControlPanelController extends BaseController {
	
	protected static final String RESTAURANTS = "restaurants";
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp, String jspLocation) throws ServletException ,IOException {
		super.doGet(req, resp);
		if (!(Boolean) req.getAttribute(LOGGED)) {
			resp.sendRedirect("/restaurants");
			return;
		}
		req.getRequestDispatcher(jspLocation).forward(req, resp);
	}
}
