package ar.edu.itba.it.paw.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.helper.DishValidationHelper;
import ar.edu.itba.it.paw.helper.RestaurantValidationHelper;
import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.manager.UserManager;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.model.Users;
import ar.edu.itba.it.paw.model.Users.Role;
import ar.edu.itba.it.paw.util.Parameter;

@Controller
public class ControlPanelController extends BaseController {
	
	private UserManager mUserManager;
	private RestaurantManager mRestaurantManager;
	
	@Autowired
	public ControlPanelController(SessionManager sessionManager, UserManager userManager, RestaurantManager restaurantManager) {
		super(sessionManager);
		mUserManager = userManager;
		mRestaurantManager = restaurantManager;
	}
	
	protected boolean hasPermission(HttpServletRequest req, Role requiredRole) {
		Users loggedUser = mSessionManager.getUser();
		if (loggedUser == null || loggedUser.getRole() != requiredRole) {
			return false;
		} else {
			return true;
		}
	}
	
	@RequestMapping(value="/adminPanel", method = RequestMethod.GET)
	public ModelAndView showAdminPanel(HttpServletRequest req) {
		if (!hasPermission(req, Role.ADMIN)) return new ModelAndView("redirect:restaurants");
		ModelAndView mav = createModelAndView(req);
		mav.addObject(Parameter.USERS, mUserManager.getUsers(Role.NORMAL));
		mav.addObject(Parameter.MANAGERS, mUserManager.getUsers(Role.MANAGER));
		mav.addObject(Parameter.RESTAURANTS, mRestaurantManager.getRestaurants());
		mav.setViewName("adminPanel");
		return mav;
	}
	
	@RequestMapping(value = "/assignManager", method = RequestMethod.POST)
	public ModelAndView showAssignManager(HttpServletRequest req, @RequestParam(Parameter.MANAGER_ID) Users manager, @RequestParam(Parameter.RESTAURANT_ID) Restaurant restaurant) {
		System.out.println("NOMBRE: "+manager.getFirstName());
		if (manager == null || restaurant == null) {
			return new ModelAndView("redirect:restaurants");
		}
		if (manager.getRole() == Role.MANAGER) {
			manager.assignRestaurant(restaurant);
			setMessage(req, "Se completó la operación");
			setMessageType(req, Parameter.SUCCESS);
		} else {
			setMessage(req, "No se pudo completar la operación");
			setMessageType(req, Parameter.ERROR);
		}
		return showAdminPanel(req);
	}
	
	@RequestMapping(value = "/newManager", method = RequestMethod.POST)
	public ModelAndView showNewManager(HttpServletRequest req, @RequestParam(Parameter.USER_ID) Users user) {
		System.out.println("NOMBRE: "+user.getFirstName());
		if (user == null) {
			return new ModelAndView("redirect:restaurants");
		}
		if (user.makeManager()) {
			setMessage(req, "Nuevo gerente agregado");
			setMessageType(req, Parameter.SUCCESS);
		} else {
			setMessage(req, "No se pudo crear un nuevo gerente");
			setMessageType(req, Parameter.ERROR);
		}
		return showAdminPanel(req);
	}
	
	@RequestMapping(value = "/managerPanel", method = RequestMethod.GET)
	public ModelAndView showManagerPanel(HttpServletRequest req) {
		if (!hasPermission(req, Role.MANAGER)) return new ModelAndView("redirect:restaurants");
		ModelAndView mav = createModelAndView(req);
		Users user = mSessionManager.getUser();
		mav.addObject(Parameter.RESTAURANTS, user.getRestaurants());
		mav.setViewName("managerPanel");
		return mav;
	}

	@RequestMapping(value = "/managerPanel", method = RequestMethod.POST)
	public ModelAndView showNewDish(HttpServletRequest req, @RequestParam(Parameter.RESTAURANT_ID) Restaurant restaurant) {
		if (restaurant == null) {
			return new ModelAndView("redirect:restaurants");
		}
		init(req);
		
		Users loggedUser = mSessionManager.getUser();
		DishValidationHelper validator = new DishValidationHelper(req, restaurant);
		if (!validator.isValidDish()) {
			setMessage(req, "No se pudo agregar el nuevo plato");
			setMessageType(req, Parameter.ERROR);
		} else if (!loggedUser.isManagerOf(restaurant)) {
			setMessage(req, "Reportado, no toques el HTML");
			setMessageType(req, Parameter.ERROR);
		} else {
			restaurant.addDish(validator.getDish());
			setMessage(req, "Nuevo plato agregado con éxito");
			setMessageType(req, Parameter.SUCCESS);
		}
		return showManagerPanel(req);
	}
	
	@RequestMapping(value = "/userPanel", method = RequestMethod.GET)
	public ModelAndView showUserPanel(HttpServletRequest req) {
		if (!hasPermission(req, Role.NORMAL)) return new ModelAndView("redirect:restaurants");
		ModelAndView mav = createModelAndView(req);
		mav.setViewName("userPanel");
		return mav;
	}
	
	@RequestMapping(value = "/newRestaurant", method = RequestMethod.POST)
	public ModelAndView showNewRestaurant(HttpServletRequest req) {
		RestaurantValidationHelper validator = new RestaurantValidationHelper(req);
		if (validator.isValidRestaurant()) {
			mRestaurantManager.addRestaurant(validator.getRestaurant());
			setMessage(req, "Nuevo restoran agregado con éxito");
			setMessageType(req, Parameter.SUCCESS);			
		} else {
			setMessage(req, "Campos inválidos. No se pudo agregar el restoran");
			setMessageType(req, Parameter.ERROR);
		}	
		return showAdminPanel(req);
	}
}
