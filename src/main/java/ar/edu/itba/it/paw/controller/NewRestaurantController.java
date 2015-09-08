package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.helper.RestaurantValidationHelper;
import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.implementation.RestaurantManagerImpl;

public class NewRestaurantController extends BaseController {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RestaurantManager manager = new RestaurantManagerImpl();
		RestaurantValidationHelper validator = new RestaurantValidationHelper(req);
		if (validator.isValidRestaurant()) {
			manager.addRestaurant(validator.getRestaurant());
			setMessage(req, "Nuevo restoran agregado con exito");
		} else {
			setMessage(req, "No se pudo agregar un nuevo restoran");
		}	
		resp.sendRedirect("adminPanel");
	}
}
