package ar.edu.itba.it.paw.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.helper.CommentValidationHelper;
import ar.edu.itba.it.paw.helper.OrderValidationHelper;
import ar.edu.itba.it.paw.manager.OrderManager;
import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.model.Orders;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.model.Users;
import ar.edu.itba.it.paw.model.Users.Role;
import ar.edu.itba.it.paw.util.Parameter;

@Controller
public class RestaurantDetailController extends BaseController {
	
	protected RestaurantManager mRestaurantManager;
	protected OrderManager mOrderManager;
	
	@Autowired
	public RestaurantDetailController(SessionManager sessionManager, RestaurantManager restaurantManager, OrderManager orderManager) {
		super(sessionManager);
		mRestaurantManager = restaurantManager;
		mOrderManager = orderManager;
	}
	
	@RequestMapping(value="/restaurant", method = RequestMethod.GET)
	protected ModelAndView showRestaurant(HttpServletRequest req, @RequestParam(Parameter.CODE) Restaurant restaurant) {
		ModelAndView mav = createModelAndView(req);
		if (restaurant == null) {
			return new ModelAndView("redirect:restaurants");
		}
		
		Users loggedUser = mSessionManager.getUser();
		boolean canComment = loggedUser != null;
		if (canComment) canComment = restaurant.canUserComment(loggedUser);
		mav.addObject(Parameter.CAN_COMMENT, canComment);
		
		restaurant.setDishes(restaurant.getDishes());
		restaurant.setComments(restaurant.getComments());
		mav.addObject(Parameter.RESTAURANT, restaurant);
		mav.setViewName("restaurantDetail");
		return mav;
	}
	
	@RequestMapping(value = "/newComment", method = RequestMethod.POST)
	protected ModelAndView showNewComment(HttpServletRequest req, @RequestParam(Parameter.RESTAURANT_ID) Restaurant restaurant) {
		if (restaurant == null) {
			return new ModelAndView("redirect:restaurants");
		}
		init(req);
		CommentValidationHelper validator = new CommentValidationHelper(req, mSessionManager.getUser(), restaurant);
		if (validator.isValidComment()) {
			restaurant.addComment(validator.getComment());
			setMessage(req, "Comentario creado con éxito");
			setMessageType(req, Parameter.SUCCESS);
		} else {
			setMessage(req, "No se pudo crear el comentario");
			setMessageType(req, Parameter.ERROR);
		}
		return showRestaurant(req, restaurant);
	}
	
	@RequestMapping(value = "deleteComment", method = RequestMethod.POST)
	protected ModelAndView showDeleteComment(HttpServletRequest req, @RequestParam(Parameter.RESTAURANT_ID) Restaurant restaurant, @RequestParam(Parameter.USER_ID) Users user) {
		if (restaurant == null) {
			return new ModelAndView("redirect:restaurants");
		}
		init(req);
		
		if (!mSessionManager.isLogged() || mSessionManager.getUser().getRole() != Role.ADMIN) {
			// shouldn't happen
			return new ModelAndView("redirect:restaurants");
		}
		if (user != null) {
			restaurant.deleteUserComment(user);
			setMessage(req, "Comentario borrado con éxito");
			setMessageType(req, Parameter.SUCCESS);
		} else {
			setMessage(req, "No se pudo borrar el comentario");
			setMessageType(req, Parameter.ERROR);
		}
		return showRestaurant(req, restaurant);
	}
	
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	protected ModelAndView showDoOrder(HttpServletRequest req, @RequestParam(Parameter.RESTAURANT_ID) Restaurant restaurant) {
		init(req);
		if (!mSessionManager.isLogged()) {
			return new ModelAndView("redirect:login");
		}
		Users user = mSessionManager.getUser();
		OrderValidationHelper validator = new OrderValidationHelper(req, user, restaurant);
		Boolean valid = validator.isValid();
		if (valid == null) {
			setMessage(req, "El costo del pedido no alcanza el costo mínimo");
			setMessageType(req, Parameter.ERROR);
		} else if (valid) {
			Orders order = validator.getOrder();
			user.addOrder(order);
			setMessage(req, "Pedido realizado con éxito");
			setMessageType(req, Parameter.SUCCESS);
		} else {
			setMessage(req, "No se pudo realizar el pedido");
			setMessageType(req, Parameter.ERROR);
		}
		req.setAttribute(Parameter.RESTAURANT_ID, restaurant.getId());
		return showRestaurant(req, restaurant);
	}
	
	@RequestMapping(value = "/deleteRestaurant", method = RequestMethod.POST)
	protected ModelAndView showDeleteRestaurant(HttpServletRequest req, @RequestParam(Parameter.RESTAURANT_ID) Restaurant restaurant) {
		if (restaurant == null) {
			return new ModelAndView("redirect:restaurants");
		}
		init(req);
		
		if (mSessionManager.getUser().getRole() != Role.ADMIN) {
			setMessage(req, "Solo el Admin puede borrar restoranes");
			setMessageType(req, Parameter.ERROR);
			return showRestaurant(req, restaurant);
		}
		mRestaurantManager.deleteRestaurant(restaurant);
		return new ModelAndView("redirect:restaurants");
	}
}
