package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.implementation.RestaurantManagerImpl;

public class DeleteCommentController extends BaseController {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RestaurantManager restaurantManager = new RestaurantManagerImpl();
		long userId = Long.valueOf(req.getParameter("user_id"));
		long restaurantId = Long.valueOf(req.getParameter("restaurant_id"));
		restaurantManager.deleteComment(userId, restaurantId);
		resp.sendRedirect("/restaurant?code=" + req.getParameter("restaurant_id"));
	}	
}
