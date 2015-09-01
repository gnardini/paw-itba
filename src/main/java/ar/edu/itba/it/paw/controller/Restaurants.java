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

public class Restaurants extends HttpServlet {
	
	private static final String RESTAURANTS = "restaurants";
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		RestaurantManager restaurantManager = new RestaurantManagerImpl();
		List<Restaurant> restaurants = restaurantManager.getRestaurants();
		req.setAttribute(RESTAURANTS, restaurants);
		req.getRequestDispatcher(JspLocationUtils.RESTAURANTS).forward(req, resp);
	}
}
