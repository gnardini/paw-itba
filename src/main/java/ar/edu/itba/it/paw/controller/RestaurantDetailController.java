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
import ar.edu.itba.it.paw.util.Parameter;

public class RestaurantDetailController extends BaseController {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
		SessionManager sessionManager = new SessionManagerImpl(req);
		RestaurantManager restaurantManager = new RestaurantManagerImpl();
		
		User loggedUser = sessionManager.getUser();
		boolean canComment = loggedUser != null;
		if (canComment) canComment = restaurantManager.canUserComment(loggedUser.getId(), Long.valueOf(req.getParameter("code")));
		req.setAttribute(Parameter.CAN_COMMENT, canComment);
		
		// TODO validate stuff
		Restaurant restaurant = restaurantManager.getRestaurant(Long.valueOf(req.getParameter(Parameter.CODE)));
		restaurant.setDishes(restaurantManager.getRestaurantDishes(restaurant.getId()));
		restaurant.setComments(restaurantManager.getRestaurantComments(restaurant.getId()));
		req.setAttribute(Parameter.RESTAURANT, restaurant);
		req.getRequestDispatcher(JspLocationUtils.RESTAURANT_DETAIL).forward(req, resp);
	}
}
