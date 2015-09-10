package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.helper.DishValidationHelper;
import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.implementation.RestaurantManagerImpl;
import ar.edu.itba.it.paw.util.JspLocationUtils;

public class ManagerPanelController extends ControlPanelController {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RestaurantManager manager = new RestaurantManagerImpl();
		req.setAttribute(RESTAURANTS, manager.getRestaurants());
		super.doGet(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RestaurantManager manager = new RestaurantManagerImpl();
		DishValidationHelper validator = new DishValidationHelper(req);
		if (validator.isValidDish()) {
			//TODO validate maybe?
			manager.addDish(Integer.valueOf(req.getParameter("code")), validator.getDish());
			setMessage(req, "Nuevo plato agregado con exito");
		} else {
			setMessage(req, "No se pudo agregar un nuevo plato");
		}
		doGet(req, resp);
	}
	
	@Override
	protected String getJspLocation() {
		return JspLocationUtils.MANAGER_PANEL;
	}
}