package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.helper.DishValidationHelper;
import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.manager.implementation.RestaurantManagerImpl;
import ar.edu.itba.it.paw.manager.implementation.SessionManagerImpl;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.model.User.Role;
import ar.edu.itba.it.paw.util.JspLocationUtils;
import ar.edu.itba.it.paw.util.Parameter;

public class ManagerPanelController extends ControlPanelController {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
		if (!mPermissionGranted) return;
		RestaurantManager restaurantManager = new RestaurantManagerImpl();
		SessionManager sessionManager = new SessionManagerImpl(req);
		req.setAttribute(Parameter.RESTAURANTS, restaurantManager.getRestaurantsByManager(sessionManager.getUser().getId()));
		req.getRequestDispatcher(JspLocationUtils.MANAGER_PANEL).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RestaurantManager restaurantManager = new RestaurantManagerImpl();
		SessionManager sessionManager = new SessionManagerImpl(req);
		long loggedUserId = sessionManager.getUser().getId();
		
		DishValidationHelper validator = new DishValidationHelper(req);
		if (req.getParameter(Parameter.RESTAURANT_ID)==null || !validator.isValidDish()) {
			setMessage(req, "No se pudo agregar el nuevo plato");
			setMessageType(req, Parameter.ERROR);
		} else if (!isRestaurantManager(loggedUserId, Long.valueOf(req.getParameter(Parameter.RESTAURANT_ID)), restaurantManager)) {
			setMessage(req, "Reportado, no toques el HTML");
			setMessageType(req, Parameter.ERROR);
		} else {
			restaurantManager.addDish(validator.getDish());
			setMessage(req, "Nuevo plato agregado con éxito");
			setMessageType(req, Parameter.SUCCESS);
		}
		doGet(req, resp);
	}
	
	@Override
	protected Role getRolePanel() {
		return Role.MANAGER;
	}
	
	private boolean isRestaurantManager(long loggedUserId, long restaurantId, RestaurantManager restaurantManager) {
		for (Restaurant restaurant: restaurantManager.getRestaurantsByManager(loggedUserId)) {
			if (restaurant.getId() == restaurantId) return true;
		}
		return false;
	}
}
