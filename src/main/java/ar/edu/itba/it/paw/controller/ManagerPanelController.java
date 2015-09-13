package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.helper.DishValidationHelper;
import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.manager.UserManager;
import ar.edu.itba.it.paw.manager.implementation.RestaurantManagerImpl;
import ar.edu.itba.it.paw.manager.implementation.SessionManagerImpl;
import ar.edu.itba.it.paw.manager.implementation.UserManagerImpl;
import ar.edu.itba.it.paw.util.JspLocationUtils;

public class ManagerPanelController extends ControlPanelController {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
		RestaurantManager manager = new RestaurantManagerImpl();
		req.setAttribute(RESTAURANTS, manager.getRestaurants());
		req.getRequestDispatcher(JspLocationUtils.MANAGER_PANEL).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RestaurantManager manager = new RestaurantManagerImpl();
		DishValidationHelper validator = new DishValidationHelper(req);
		if (validator.isValidDish()) {
			//TODO validate maybe?
			manager.addDish(validator.getDish());
			setMessage(req, "Nuevo plato agregado con exito");
		} else {
			setMessage(req, "No se pudo agregar un nuevo plato");
		}
		doGet(req, resp);
	}
}
