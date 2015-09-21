package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.helper.RestaurantValidationHelper;
import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.implementation.RestaurantManagerImpl;
import ar.edu.itba.it.paw.util.Page;
import ar.edu.itba.it.paw.util.Parameter;

public class NewRestaurantController extends AdminPanelController {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RestaurantManager manager = new RestaurantManagerImpl();
		RestaurantValidationHelper validator = new RestaurantValidationHelper(req);
		if (validator.isValidRestaurant() && manager.addRestaurant(validator.getRestaurant())) {
			setMessage(req, "Nuevo restoran agregado con éxito");
			setMessageType(req, Parameter.SUCCESS);			
		} else {
			setMessage(req, "Campos inválidos. No se pudo agregar el restoran");
			setMessageType(req, Parameter.ERROR);
		}	
		doGet(req, resp);
	}
}
