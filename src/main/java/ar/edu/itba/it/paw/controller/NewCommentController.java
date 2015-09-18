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
import ar.edu.itba.it.paw.util.Page;
import ar.edu.itba.it.paw.util.Parameter;

public class NewCommentController extends RestaurantDetailController {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionManager sessionManager = new SessionManagerImpl(req);
		RestaurantManager restaurantManager = new RestaurantManagerImpl();
		CommentValidationHelper validator = new CommentValidationHelper(req, sessionManager.getUser().getId());
		if (validator.isValidComment()) {
			restaurantManager.addComment(validator.getComment());
			setMessage(req, "Comentario creado con exito");
			setMessageType(req, Parameter.SUCCESS);
		} else {
			setMessage(req, "No se pudo crear el comentario");
			setMessageType(req, Parameter.ERROR);
		}
		req.setAttribute(Parameter.RESTAURANT_ID, Long.valueOf(req.getParameter(Parameter.RESTAURANT_ID)));
		doGet(req, resp);
	}
}
