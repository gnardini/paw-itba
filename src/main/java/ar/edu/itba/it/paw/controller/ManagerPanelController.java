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
		req.setAttribute(Parameter.RESTAURANTS, restaurantManager.getRestaurantsByManager(sessionManager.getUser().getEmail()));
		req.getRequestDispatcher(JspLocationUtils.MANAGER_PANEL).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RestaurantManager manager = new RestaurantManagerImpl();
		DishValidationHelper validator = new DishValidationHelper(req);
		if (validator.isValidDish()) {
			manager.addDish(validator.getDish());
			setMessage(req, "Nuevo plato agregado con exito");
		} else {
			setMessage(req, "No se pudo agregar un nuevo plato");
		}
		doGet(req, resp);
	}
	
	@Override
	protected Role getRolePanel() {
		return Role.MANAGER;
	}
}
