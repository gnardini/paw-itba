package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.manager.implementation.RestaurantManagerImpl;
import ar.edu.itba.it.paw.manager.implementation.SessionManagerImpl;
import ar.edu.itba.it.paw.model.User.Role;
import ar.edu.itba.it.paw.util.Page;
import ar.edu.itba.it.paw.util.Parameter;

public class DeleteCommentController extends RestaurantDetailController {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionManager sessionManager = new SessionManagerImpl(req);
		if (!sessionManager.isLogged() || sessionManager.getUser().getRole() != Role.ADMIN) {
			// shouldn't happen
			resp.sendRedirect(Page.HOME);
			return;
		}
		
		RestaurantManager restaurantManager = new RestaurantManagerImpl();
		long userId = Long.valueOf(req.getParameter(Parameter.USER_ID));
		System.out.println("userid: " + userId);
		long restaurantId = Long.valueOf(req.getParameter(Parameter.RESTAURANT_ID));
		restaurantManager.deleteComment(userId, restaurantId);
		setMessage(req, "Comentario borrado con exito");
		setMessageType(req, Parameter.SUCCESS);
		req.setAttribute(Parameter.RESTAURANT_ID, restaurantId);
		doGet(req, resp);
	}	
}
