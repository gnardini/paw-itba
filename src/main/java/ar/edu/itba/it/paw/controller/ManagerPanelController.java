package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.helper.DishValidationHelper;
import ar.edu.itba.it.paw.helper.RestaurantValidationHelper;
import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.implementation.RestaurantManagerImpl;
import ar.edu.itba.it.paw.util.JspLocationUtils;

public class ManagerPanelController extends ControlPanelController {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RestaurantManager manager = new RestaurantManagerImpl();
		String create = req.getParameter("create");
		if (create.equals("dish")) createDish(req, manager);
		else createRestaurant(req, manager);	
		doGet(req, resp, JspLocationUtils.MANAGER_PANEL);
	}
	
	private void createDish(HttpServletRequest req, RestaurantManager manager) {
		DishValidationHelper validator = new DishValidationHelper(req);
		if (validator.isValidDish()) {
			//TODO validate maybe?
			manager.addDish(Integer.valueOf(req.getParameter("code")), validator.getDish());
			setMessage(req, "Nuevo plato agregado con exito");
		} else {
			setMessage(req, "No se pudo agregar un nuevo plato");
		}
	}
	
	private void createRestaurant(HttpServletRequest req, RestaurantManager manager) {
		RestaurantValidationHelper validator = new RestaurantValidationHelper(req);
		if (validator.isValidRestaurant()) {
			manager.addRestaurant(validator.getRestaurant());
			setMessage(req, "Nuevo restoran agregado con exito");
		} else {
			setMessage(req, "No se pudo agregar un nuevo restoran");
		}	
	}
}
