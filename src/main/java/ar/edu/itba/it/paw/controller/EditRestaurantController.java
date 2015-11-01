package ar.edu.itba.it.paw.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.model.Users;
import ar.edu.itba.it.paw.model.Users.Role;
import ar.edu.itba.it.paw.util.Parameter;
import ar.edu.itba.it.paw.validator.EditRestaurantValidator;
import ar.edu.itba.it.paw.validator.RestaurantValidator;

@Controller
public class EditRestaurantController extends BaseController {
	
	@Autowired
	public EditRestaurantController(SessionManager sessionManager) {
		super(sessionManager);
	}
	
	@RequestMapping(value = "/editRestaurant", method = RequestMethod.GET)
	protected ModelAndView showEditRestaurant(HttpServletRequest req, @RequestParam(Parameter.RESTAURANT_ID) Restaurant restaurant) {
		if (!isAdmin(req)) return new ModelAndView("redirect:restaurants");
		if (restaurant == null) {
			return new ModelAndView("redirect:restaurants");
		}		
		ModelAndView mav = createModelAndView(req);
		mav.addObject(Parameter.RESTAURANT, restaurant);
		mav.setViewName("editRestaurant");
		return mav;
	}
	
	@RequestMapping(value = "/editRestaurant", method = RequestMethod.POST)
	protected ModelAndView showPostEditRestaurant(HttpServletRequest req, @RequestParam(Parameter.RESTAURANT_ID) Restaurant restaurant) {
		init(req);
		if (!isAdmin(req)) return new ModelAndView("redirect:restaurants");
		if (restaurant == null) {
			return new ModelAndView("redirect:restaurants");
		}
		EditRestaurantValidator validator = new EditRestaurantValidator(req);
		if (validator.isValidRestaurant()) {
			Restaurant updatedRestaurant = validator.getRestaurant();
			restaurant.updateWithData(updatedRestaurant);
			ModelAndView mav = new ModelAndView("redirect:restaurant");
			mav.addObject("code", restaurant.getId());
			return mav;
		} else {
			setMessage(req, "No se pudo agregar un nuevo restoran");
			setMessageType(req, Parameter.ERROR);
			return showEditRestaurant(req, restaurant);
		}	
	}
	
	private boolean isAdmin(HttpServletRequest req) {
		Users loggedUser = mSessionManager.getUser();
		if (loggedUser == null || loggedUser.getRole() != Role.ADMIN) {
			//resp.sendRedirect(Page.HOME);
			return false;
		}
		return true;
	}
}
