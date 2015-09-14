package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.manager.implementation.RestaurantManagerImpl;
import ar.edu.itba.it.paw.manager.implementation.SessionManagerImpl;
import ar.edu.itba.it.paw.model.User.Role;
import ar.edu.itba.it.paw.util.Page;
import ar.edu.itba.it.paw.util.Parameter;

public class DeleteRestaurantController extends BaseController {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionManager sessionManager = new SessionManagerImpl(req);
		if (sessionManager.getUser().getRole() != Role.ADMIN) {
			resp.sendRedirect(req.getHeader("Referer"));
			return;
		}
		RestaurantManager restaurantManager = new RestaurantManagerImpl();
		restaurantManager.deleteRestaurant(Long.valueOf(req.getParameter(Parameter.RESTAURANT_ID)));
		resp.sendRedirect(Page.HOME);
	}
}
