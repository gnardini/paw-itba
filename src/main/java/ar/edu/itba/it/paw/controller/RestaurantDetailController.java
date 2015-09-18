package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.manager.implementation.RestaurantManagerImpl;
import ar.edu.itba.it.paw.manager.implementation.SessionManagerImpl;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.util.JspLocationUtils;
import ar.edu.itba.it.paw.util.NumberUtils;
import ar.edu.itba.it.paw.util.Parameter;

public class RestaurantDetailController extends BaseController {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
		SessionManager sessionManager = new SessionManagerImpl(req);
		RestaurantManager restaurantManager = new RestaurantManagerImpl();
		
		User loggedUser = sessionManager.getUser();
		long restaurantId = getRestaurantId(req);
		boolean canComment = loggedUser != null;
		if (canComment) canComment = restaurantManager.canUserComment(loggedUser.getId(), restaurantId);
		req.setAttribute(Parameter.CAN_COMMENT, canComment);
		
		Restaurant restaurant = restaurantManager.getRestaurant(restaurantId);
		restaurant.setDishes(restaurantManager.getRestaurantDishes(restaurant.getId()));
		restaurant.setComments(restaurantManager.getRestaurantComments(restaurant.getId()));
		req.setAttribute(Parameter.RESTAURANT, restaurant);
		req.getRequestDispatcher(JspLocationUtils.RESTAURANT_DETAIL).forward(req, resp);
	}
	
	private long getRestaurantId(HttpServletRequest req) {
		String id = req.getParameter(Parameter.CODE);
		if (id == null || id == "") id = req.getParameter(Parameter.RESTAURANT_ID);
		if (id == null || id == "") id = (String) req.getAttribute(Parameter.RESTAURANT_ID);
		if (id == null || !NumberUtils.isInteger(id)) return 0;
		return Long.valueOf(id);
	}
}
