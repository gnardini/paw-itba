package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.helper.DishValidationHelper;
import ar.edu.itba.it.paw.helper.RestaurantValidationHelper;
import ar.edu.itba.it.paw.helper.UserValidationHelper;
import ar.edu.itba.it.paw.manager.AdminManager;
import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.UserManager;
import ar.edu.itba.it.paw.manager.implementation.AdminManagerImpl;
import ar.edu.itba.it.paw.manager.implementation.RestaurantManagerImpl;
import ar.edu.itba.it.paw.manager.implementation.SessionManager;
import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.model.User.Role;
import ar.edu.itba.it.paw.util.JspLocationUtils;

public class ControlPanel extends HttpServlet {
	
	private static final String RESTAURANTS = "restaurants";
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		UserManager userManager = new SessionManager(req);
		String jspLocation;
		User user = userManager.getUser();
		switch (user.getRole()) {
		case ADMIN:
			jspLocation = JspLocationUtils.ADMIN_PANEL;
			break;
		case MANAGER:
			jspLocation = JspLocationUtils.MANAGER_PANEL;
			RestaurantManager restaurantManager = new RestaurantManagerImpl();
			req.setAttribute(RESTAURANTS, restaurantManager.getRestaurantsByManager(user.getEmail()));
			break;
		default:
			resp.sendRedirect("/");
			return;
		}
		req.getRequestDispatcher(jspLocation).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserManager manager = new SessionManager(req);
		switch (manager.getUser().getRole()) {
		case ADMIN:
			doAdminPost(req, resp);
			break;
		case MANAGER:
			doManagerPost(req, resp);
			break;
		default:
		}
		doGet(req, resp);
	}
	
	private void doAdminPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AdminManager manager = new AdminManagerImpl();
		UserValidationHelper validator = new UserValidationHelper(req);
		if (validator.isValidUser(Role.MANAGER)) {
			manager.addManager(validator.getUser());
			setMessage(req, "Se completo la operacion");
		} else {
			setMessage(req, "No se pudo completar la operacion");
		}
	}
	
	private void doManagerPost(HttpServletRequest req, HttpServletResponse resp) {
		RestaurantManager manager = new RestaurantManagerImpl();
		String create = req.getParameter("create");
		if (create.equals("dish")) createDish(req, manager);
		else createRestaurant(req, manager);		
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
	
	private void setMessage(HttpServletRequest req, String message) {
		req.setAttribute("message", message);
	}
}
