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

public class CommentController extends BaseController {
	
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
		resp.sendRedirect("/restaurant?code=" + req.getParameter("restaurant_id"));
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RestaurantManager restaurantManager = new RestaurantManagerImpl();
		long userId = Long.valueOf(req.getParameter("userId"));
		long restaurantId = Long.valueOf(req.getParameter("restaurantId"));
		restaurantManager.deleteComment(userId, restaurantId);
		resp.sendRedirect("/restaurant?code=" + req.getParameter("restaurant_id"));
	}
}
