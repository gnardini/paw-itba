package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.helper.RestaurantValidationHelper;
import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.manager.implementation.RestaurantManagerImpl;
import ar.edu.itba.it.paw.manager.implementation.SessionManagerImpl;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.model.User.Role;
import ar.edu.itba.it.paw.util.JspLocationUtils;
import ar.edu.itba.it.paw.util.Page;
import ar.edu.itba.it.paw.util.Parameter;

public class EditRestaurantController extends BaseController {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
		if (!isAdmin(req, resp)) return;
		
		RestaurantManager restaurantManager = new RestaurantManagerImpl();
		req.setAttribute(Parameter.RESTAURANT, restaurantManager.getRestaurant(Long.valueOf(req.getParameter(Parameter.RESTAURANT_ID))));
		req.getRequestDispatcher(JspLocationUtils.RESTAURANT_EDIT).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (!isAdmin(req, resp)) return;
		long restaurantId = Long.valueOf(req.getParameter(Parameter.RESTAURANT_ID));
		
		RestaurantManager restaurantManager = new RestaurantManagerImpl();
		RestaurantValidationHelper validator = new RestaurantValidationHelper(req);
		if (validator.isValidRestaurant()) {
			Restaurant restaurant = validator.getRestaurant();
			restaurant.setId(restaurantId);
			restaurantManager.updateRestaurant(validator.getRestaurant());
			resp.sendRedirect(String.format(Page.RESTAURANT_DETAIL, restaurantId));
		} else {
			setMessage(req, "No se pudo agregar un nuevo restoran");
			doGet(req, resp);
		}	
	}
	
	private boolean isAdmin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionManager sessionManager = new SessionManagerImpl(req);
		User loggedUser = sessionManager.getUser();
		if (loggedUser == null || loggedUser.getRole() != Role.ADMIN) {
			resp.sendRedirect(Page.HOME);
			return false;
		}
		return true;
	}
}
