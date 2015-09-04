package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.UserManager;
import ar.edu.itba.it.paw.manager.implementation.RestaurantManagerImpl;
import ar.edu.itba.it.paw.manager.implementation.SessionManager;
import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.util.JspLocationUtils;

public class AdminPanel extends ControlPanel {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		super.doGet(req, resp);
		if (!(Boolean) req.getAttribute(LOGGED)) {
			resp.sendRedirect("/restaurants");
			return;
		}
		UserManager userManager = new SessionManager(req);
		String jspLocation;
		User user = userManager.getUser();
		switch (user.getRole()) {
		case ADMIN:
			jspLocation = JspLocationUtils.ADMIN_PANEL;
			break;
		case MANAGER:
			jspLocation = JspLocationUtils.MANAGER_PANEL;
			RestaurantManager restaurantManager = new RestaurantManagerImpl();
			req.setAttribute(RESTAURANTS, restaurantManager.getRestaurantsByManager(user.getEmail()));
			break;
		default:
			resp.sendRedirect("/");
			return;
		}
		req.getRequestDispatcher(jspLocation).forward(req, resp);
	}

}
