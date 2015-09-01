package ar.edu.itba.it.paw.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.implementation.RestaurantManagerImpl;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.util.JspLocationUtils;

public class RestaurantDetail extends HttpServlet {
	
	private static final String RESTAURANT = "restaurant";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RestaurantManager restaurantManager = new RestaurantManagerImpl();
		// TODO validate stuff
		Restaurant restaurant = restaurantManager.getRestaurant(Integer.valueOf(req.getParameter("code")));
		req.setAttribute(RESTAURANT, restaurant);
		req.getRequestDispatcher(JspLocationUtils.RESTAURANT_DETAIL).forward(req, resp);
	}

}
