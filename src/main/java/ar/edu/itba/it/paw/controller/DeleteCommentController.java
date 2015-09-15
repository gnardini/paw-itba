package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.implementation.RestaurantManagerImpl;
import ar.edu.itba.it.paw.util.Page;
import ar.edu.itba.it.paw.util.Parameter;

public class DeleteCommentController extends BaseController {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RestaurantManager restaurantManager = new RestaurantManagerImpl();
		long userId = Long.valueOf(req.getParameter(Parameter.USER_ID));
		long restaurantId = Long.valueOf(req.getParameter(Parameter.RESTAURANT_ID));
		restaurantManager.deleteComment(userId, restaurantId);
		resp.sendRedirect(String.format(Page.RESTAURANT_DETAIL, Long.valueOf(req.getParameter(Parameter.RESTAURANT_ID))));
	}	
}
