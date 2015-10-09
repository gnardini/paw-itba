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
import ar.edu.itba.it.paw.manager.OrderManager;
import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.manager.UserManager;
import ar.edu.itba.it.paw.manager.implementation.RestaurantManagerImpl;
import ar.edu.itba.it.paw.manager.implementation.SessionManagerImpl;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.model.User.Role;
import ar.edu.itba.it.paw.util.JspLocationUtils;
import ar.edu.itba.it.paw.util.NumberUtils;
import ar.edu.itba.it.paw.util.Parameter;

@Controller
public class ControlPanelController extends BaseController {
	
	private UserManager mUserManager;
	private RestaurantManager mRestaurantManager;
	private OrderManager mOrderManager;
	
	@Autowired
	public ControlPanelController(SessionManager sessionManager, UserManager userManager, RestaurantManager restaurantManager, OrderManager orderManager) {
		super(sessionManager);
		mUserManager = userManager;
		mRestaurantManager = restaurantManager;
		mOrderManager = orderManager;
	}
	
	protected boolean hasPermission(HttpServletRequest req, Role requiredRole) {
		User loggedUser = mSessionManager.getUser();
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
	public ModelAndView showAssignManager(HttpServletRequest req, @RequestParam(Parameter.MANAGER_ID) String managerIdString, @RequestParam(Parameter.RESTAURANT_ID) String restaurantIdString) {
		if ((restaurantIdString == null || !NumberUtils.isNumber(restaurantIdString))
				|| (managerIdString == null || !NumberUtils.isNumber(managerIdString))) {
			return new ModelAndView("redirect:restaurants");
		}
		long managerId = Long.valueOf(managerIdString);
		long restaurantId = Long.valueOf(restaurantIdString);
		User manager = mUserManager.getUser(managerId);
		if (mRestaurantManager.getRestaurant(restaurantId)!=null && manager!=null && manager.getRole()==Role.MANAGER && mUserManager.assignManager(managerId, restaurantId)) {
			setMessage(req, "Se completó la operación");
			setMessageType(req, Parameter.SUCCESS);
		} else {
			setMessage(req, "No se pudo completar la operación");
			setMessageType(req, Parameter.ERROR);
		}
		return showAdminPanel(req);
	}
	
	@RequestMapping(value = "/newManager", method = RequestMethod.POST)
	public ModelAndView showNewManager(HttpServletRequest req, @RequestParam(Parameter.USER_ID) String userIdString) {
		if (userIdString == null || !NumberUtils.isNumber(userIdString)) {
			return new ModelAndView("redirect:restaurants");
		}
		long userId = Long.valueOf(userIdString);
		if (mUserManager.makeUserManager(userId)) {
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
		mav.addObject(Parameter.RESTAURANTS, mRestaurantManager.getRestaurantsByManager(mSessionManager.getUser().getId()));
		mav.setViewName("managerPanel");
		return mav;
	}

	@RequestMapping(value = "/managerPanel", method = RequestMethod.POST)
	public ModelAndView showNewDish(HttpServletRequest req, @RequestParam(Parameter.RESTAURANT_ID) String restaurantIdString) {
		if (restaurantIdString == null || !NumberUtils.isNumber(restaurantIdString)) {
			return new ModelAndView("redirect:restaurants");
		}
		init(req);
		long restaurantId = Long.valueOf(restaurantIdString);
		long loggedUserId = mSessionManager.getUser().getId();
		
		DishValidationHelper validator = new DishValidationHelper(req);
		if (req.getParameter(Parameter.RESTAURANT_ID)==null || !validator.isValidDish()) {
			setMessage(req, "No se pudo agregar el nuevo plato");
			setMessageType(req, Parameter.ERROR);
		} else if (!isRestaurantManager(loggedUserId, restaurantId, mRestaurantManager)) {
			setMessage(req, "Reportado, no toques el HTML");
			setMessageType(req, Parameter.ERROR);
		} else {
			mRestaurantManager.addDish(validator.getDish());
			setMessage(req, "Nuevo plato agregado con éxito");
			setMessageType(req, Parameter.SUCCESS);
		}
		return showManagerPanel(req);
	}
	
	private boolean isRestaurantManager(long loggedUserId, long restaurantId, RestaurantManager restaurantManager) {
		for (Restaurant restaurant: restaurantManager.getRestaurantsByManager(loggedUserId)) {
			if (restaurant.getId() == restaurantId) return true;
		}
		return false;
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
		if (validator.isValidRestaurant() && mRestaurantManager.addRestaurant(validator.getRestaurant())) {
			setMessage(req, "Nuevo restoran agregado con éxito");
			setMessageType(req, Parameter.SUCCESS);			
		} else {
			setMessage(req, "Campos inválidos. No se pudo agregar el restoran");
			setMessageType(req, Parameter.ERROR);
		}	
		return showAdminPanel(req);
	}
}
