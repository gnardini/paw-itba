package ar.edu.itba.it.paw.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.helper.RestaurantValidationHelper;
import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.manager.implementation.RestaurantManagerImpl;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.model.User.Role;
import ar.edu.itba.it.paw.util.NumberUtils;
import ar.edu.itba.it.paw.util.Parameter;

@Controller
public class EditRestaurantController extends BaseController {
	
	private RestaurantManager mRestaurantManager;
	
	@Autowired
	public EditRestaurantController(SessionManager sessionManager, RestaurantManager restaurantManager) {
		super(sessionManager);
		mRestaurantManager = restaurantManager;
	}
	
	@RequestMapping(value = "/editRestaurant", method = RequestMethod.GET)
	protected ModelAndView showEditRestaurant(HttpServletRequest req, @RequestParam(Parameter.RESTAURANT_ID) String restaurantIdString) {
		if (!isAdmin(req)) return new ModelAndView("redirect:restaurants");
		if ((restaurantIdString == null || !NumberUtils.isNumber(restaurantIdString))) {
			return new ModelAndView("redirect:restaurants");
		}
		long restaurantId = Long.valueOf(restaurantIdString);
		
		ModelAndView mav = createModelAndView(req);
		mav.addObject(Parameter.RESTAURANT, mRestaurantManager.getRestaurant(restaurantId));
		mav.setViewName("editRestaurant");
		return mav;
	}
	
	@RequestMapping(value = "/editRestaurant", method = RequestMethod.POST)
	protected ModelAndView showPostEditRestaurant(HttpServletRequest req, @RequestParam(Parameter.RESTAURANT_ID) String restaurantIdString) {
		init(req);
		if (!isAdmin(req)) return new ModelAndView("redirect:restaurants");
		
		if ((restaurantIdString == null || !NumberUtils.isNumber(restaurantIdString))) {
			return new ModelAndView("redirect:restaurants");
		}
		long restaurantId = Long.valueOf(restaurantIdString);
		
		RestaurantManager restaurantManager = new RestaurantManagerImpl();
		RestaurantValidationHelper validator = new RestaurantValidationHelper(req);
		if (validator.isValidRestaurant()) {
			Restaurant restaurant = validator.getRestaurant();
			restaurant.setId(restaurantId);
			restaurantManager.updateRestaurant(validator.getRestaurant());
			ModelAndView mav = new ModelAndView("redirect:restaurant");
			mav.addObject("code", restaurantIdString);
			return mav;
			//resp.sendRedirect(String.format(Page.RESTAURANT_DETAIL, restaurantId));
		} else {
			setMessage(req, "No se pudo agregar un nuevo restoran");
			setMessageType(req, Parameter.ERROR);
			//doGet(req, resp);
			return showEditRestaurant(req, restaurantIdString);
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
