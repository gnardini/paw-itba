package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.helper.CommentValidationHelper;
import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.manager.implementation.RestaurantManagerImpl;
import ar.edu.itba.it.paw.manager.implementation.SessionManagerImpl;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.util.JspLocationUtils;

public class RestaurantDetail extends BaseController {
	
	private static final String RESTAURANT = "restaurant";
	private static final String DISHES = "dishes";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
		RestaurantManager restaurantManager = new RestaurantManagerImpl();
		// TODO validate stuff
		Restaurant restaurant = restaurantManager.getRestaurant(Integer.valueOf(req.getParameter("code")));
		restaurant.setDishes(restaurantManager.getRestaurantDishes(restaurant.getId()));
		req.setAttribute(RESTAURANT, restaurant);
		req.getRequestDispatcher(JspLocationUtils.RESTAURANT_DETAIL).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionManager sessionManager = new SessionManagerImpl(req);
		RestaurantManager restaurantManager = new RestaurantManagerImpl();
		CommentValidationHelper validator = new CommentValidationHelper(req, sessionManager.getUser().getId());
		if (validator.isValidComment()) {
			restaurantManager.addComment(validator.getComment());
		} else {
			//TODO mensajes lindos
			req.setAttribute("message", "No se pudo crear el comentario");
		}
		doGet(req, resp);
	}
}
