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

public class RestaurantDetailController extends BaseController {
	
	private static final String RESTAURANT = "restaurant";
	private static final String CAN_COMMENT = "can_comment";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
		SessionManager sessionManager = new SessionManagerImpl(req);
		RestaurantManager restaurantManager = new RestaurantManagerImpl();
		
		User loggedUser = sessionManager.getUser();
		boolean canComment = loggedUser != null;
		if (canComment) canComment = restaurantManager.canUserComment(loggedUser.getId(), Long.valueOf(req.getParameter("code")));
		req.setAttribute(CAN_COMMENT, canComment);
		
		// TODO validate stuff
		Restaurant restaurant = restaurantManager.getRestaurant(Long.valueOf(req.getParameter("code")));
		restaurant.setDishes(restaurantManager.getRestaurantDishes(restaurant.getId()));
		restaurant.setComments(restaurantManager.getRestaurantComments(restaurant.getId()));
		req.setAttribute(RESTAURANT, restaurant);
		req.getRequestDispatcher(JspLocationUtils.RESTAURANT_DETAIL).forward(req, resp);
	}
}
