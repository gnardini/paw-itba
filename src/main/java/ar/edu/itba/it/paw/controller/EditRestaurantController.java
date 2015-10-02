package ar.edu.itba.it.paw.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.helper.RestaurantValidationHelper;
import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.manager.implementation.RestaurantManagerImpl;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.model.User.Role;
import ar.edu.itba.it.paw.util.JspLocationUtils;
import ar.edu.itba.it.paw.util.Page;
import ar.edu.itba.it.paw.util.Parameter;

@Controller
public class EditRestaurantController extends BaseController {
	
	@Autowired
	public EditRestaurantController(SessionManager sessionManager, RestaurantManager restaurantManager) {
		super(sessionManager);
	}
	
	protected void showEditRestaurant(HttpServletRequest req) {
		if (!isAdmin(req)) return;
		
		RestaurantManager restaurantManager = new RestaurantManagerImpl();
		req.setAttribute(Parameter.RESTAURANT, restaurantManager.getRestaurant(Long.valueOf(req.getParameter(Parameter.RESTAURANT_ID))));
		//req.getRequestDispatcher(JspLocationUtils.RESTAURANT_EDIT).forward(req, resp);
	}
	
	protected void showDoEditRestaurant(HttpServletRequest req) {
		ModelAndView mav = createModelAndView(req);
		if (!isAdmin(req)) return;
		long restaurantId = Long.valueOf(req.getParameter(Parameter.RESTAURANT_ID));
		
		RestaurantManager restaurantManager = new RestaurantManagerImpl();
		RestaurantValidationHelper validator = new RestaurantValidationHelper(req);
		if (validator.isValidRestaurant()) {
			Restaurant restaurant = validator.getRestaurant();
			restaurant.setId(restaurantId);
			restaurantManager.updateRestaurant(validator.getRestaurant());
			//resp.sendRedirect(String.format(Page.RESTAURANT_DETAIL, restaurantId));
		} else {
			setMessage(req, "No se pudo agregar un nuevo restoran");
			setMessageType(req, Parameter.ERROR);
			//doGet(req, resp);
		}	
	}
	
	private boolean isAdmin(HttpServletRequest req) {
		User loggedUser = mSessionManager.getUser();
		if (loggedUser == null || loggedUser.getRole() != Role.ADMIN) {
			//resp.sendRedirect(Page.HOME);
			return false;
		}
		return true;
	}
}
