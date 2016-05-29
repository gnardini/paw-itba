package ar.edu.itba.it.paw.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.model.Dish;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.model.Users;
import ar.edu.itba.it.paw.model.Users.Role;
import ar.edu.itba.it.paw.repository.hibernate.DishRepo;
import ar.edu.itba.it.paw.repository.hibernate.NeighbourhoodRepo;
import ar.edu.itba.it.paw.repository.hibernate.HibernateRestaurantRepo;
import ar.edu.itba.it.paw.repository.hibernate.HibernateUserRepo;
import ar.edu.itba.it.paw.util.Parameter;
import ar.edu.itba.it.paw.validator.DishValidationHelper;
import ar.edu.itba.it.paw.validator.RestaurantValidator;

@Controller
public class ControlPanelController extends BaseController {
	
	private HibernateUserRepo mUserRepo;
	private HibernateRestaurantRepo mRestaurantRepo;
	private DishRepo mDishRepo;
	private NeighbourhoodRepo mNeighbourhoodRepo;
	
	@Autowired
	public ControlPanelController(SessionManager sessionManager, HibernateUserRepo userRepo, HibernateRestaurantRepo restaurantRepo, DishRepo dishRepo, NeighbourhoodRepo neighbourhoodRepo) {
		super(sessionManager);
		mUserRepo = userRepo;
		mRestaurantRepo = restaurantRepo;
		mDishRepo = dishRepo;
		mNeighbourhoodRepo = neighbourhoodRepo;
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
		mav.addObject(Parameter.USERS, mUserRepo.getUsers(Role.NORMAL));
		mav.addObject(Parameter.MANAGERS, mUserRepo.getUsers(Role.MANAGER));
		mav.addObject(Parameter.RESTAURANTS, mRestaurantRepo.getRestaurants());
		mav.addObject("neighbourhoods", mNeighbourhoodRepo.getAllNeighbourhoods());
		mav.setViewName("adminPanel");
		return mav;
	}
	
	@RequestMapping(value = "/assignManager", method = RequestMethod.POST)
	public ModelAndView showAssignManager(HttpServletRequest req, @RequestParam(Parameter.MANAGER_ID) Users manager, @RequestParam(Parameter.RESTAURANT_ID) Restaurant restaurant) {
		if (!hasPermission(req, Role.ADMIN)) return new ModelAndView("redirect:restaurants");
		if (manager == null || restaurant == null) {
			return new ModelAndView("redirect:restaurants");
		}
		if (manager.getRole() == Role.MANAGER) {
			if (manager.assignRestaurant(restaurant)) {
				setMessage(req, "Se completó la operación");
				setMessageType(req, Parameter.SUCCESS);
			} else {
				setMessage(req, manager.getEmail() + " ya es gerente de " + restaurant.getName());
				setMessageType(req, Parameter.ERROR);
			}			
		} else {
			setMessage(req, "No se pudo completar la operación");
			setMessageType(req, Parameter.ERROR);
		}
		return showAdminPanel(req);
	}
	
	@RequestMapping(value = "/newManager", method = RequestMethod.POST)
	public ModelAndView showNewManager(HttpServletRequest req, @RequestParam(Parameter.USER_ID) Users user) {
		if (!hasPermission(req, Role.ADMIN)) return new ModelAndView("redirect:restaurants");
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
		if (!hasPermission(req, Role.MANAGER)) return new ModelAndView("redirect:restaurants");
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
			Dish dish = validator.getDish();
			mDishRepo.addDish(dish);
			restaurant.addDish(dish);
			setMessage(req, "Nuevo plato agregado con éxito");
			setMessageType(req, Parameter.SUCCESS);
		}
		return showManagerPanel(req);
	}
	
	@RequestMapping(value = "/newRestaurant", method = RequestMethod.POST)
	public ModelAndView showNewRestaurant(HttpServletRequest req) {
		if (!hasPermission(req, Role.ADMIN)) return new ModelAndView("redirect:restaurants");
		RestaurantValidator validator = new RestaurantValidator(req, mNeighbourhoodRepo);
		if (validator.isValidRestaurant()) {
			Restaurant restaurant = validator.getRestaurant();
			mRestaurantRepo.storeRestaurant(restaurant);
			setMessage(req, "Nuevo restoran agregado con éxito");
			setMessageType(req, Parameter.SUCCESS);			
		} else {
			setMessage(req, "Campos inválidos. No se pudo agregar el restoran");
			setMessageType(req, Parameter.ERROR);
		}	
		return showAdminPanel(req);
	}
}
