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
import ar.edu.itba.it.paw.manager.implementation.RestaurantManagerImpl;
import ar.edu.itba.it.paw.model.Order;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.model.User.Role;
import ar.edu.itba.it.paw.util.NumberUtils;
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
	protected ModelAndView showRestaurant(HttpServletRequest req, @RequestParam("code") String restaurantIdString) {
		ModelAndView mav = createModelAndView(req);
		if (restaurantIdString == null || !NumberUtils.isNumber(restaurantIdString)) {
			return new ModelAndView("redirect:restaurants");
		}
		Restaurant restaurant = mRestaurantManager.getRestaurant(Long.valueOf(restaurantIdString));
		if (restaurant == null)	return new ModelAndView("redirect:restaurants");
		
		User loggedUser = mSessionManager.getUser();
		boolean canComment = loggedUser != null;
		if (canComment) canComment = mRestaurantManager.canUserComment(loggedUser.getId(), restaurant.getId());
		mav.addObject(Parameter.CAN_COMMENT, canComment);
		
		restaurant.setDishes(mRestaurantManager.getRestaurantDishes(restaurant.getId()));
		restaurant.setComments(mRestaurantManager.getRestaurantComments(restaurant.getId()));
		mav.addObject(Parameter.RESTAURANT, restaurant);
		mav.setViewName("restaurantDetail");
		return mav;
	}
	
	@RequestMapping(value = "/newComment", method = RequestMethod.POST)
	protected ModelAndView showNewComment(HttpServletRequest req, @RequestParam("restaurant_id") String restaurantIdString) {
		if (restaurantIdString == null || !NumberUtils.isNumber(restaurantIdString)) {
			return new ModelAndView("redirect:restaurants");
		}
		Restaurant restaurant = mRestaurantManager.getRestaurant(Long.valueOf(restaurantIdString));
		if (restaurant == null) return new ModelAndView("redirect:restaurants");
		init(req);
		CommentValidationHelper validator = new CommentValidationHelper(req, mSessionManager.getUser().getId());
		if (validator.isValidComment()) {
			mRestaurantManager.addComment(validator.getComment());
			setMessage(req, "Comentario creado con éxito");
			setMessageType(req, Parameter.SUCCESS);
		} else {
			setMessage(req, "No se pudo crear el comentario");
			setMessageType(req, Parameter.ERROR);
		}
		return showRestaurant(req, String.valueOf(restaurant.getId()));
	}
	
	@RequestMapping(value = "deleteComment", method = RequestMethod.POST)
	protected ModelAndView showDeleteComment(HttpServletRequest req, @RequestParam("restaurant_id") String restaurantIdString) {
		if ((restaurantIdString == null || !NumberUtils.isNumber(restaurantIdString))) {
			return new ModelAndView("redirect:restaurants");
		}
		Restaurant restaurant = mRestaurantManager.getRestaurant(Long.valueOf(restaurantIdString));
		init(req);
		
		if (!mSessionManager.isLogged() || mSessionManager.getUser().getRole() != Role.ADMIN) {
			// shouldn't happen
			return new ModelAndView("redirect:restaurants");
		}
		long userId = Long.valueOf(req.getParameter(Parameter.USER_ID));
		mRestaurantManager.deleteComment(userId, restaurant.getId());
		setMessage(req, "Comentario borrado con éxito");
		setMessageType(req, Parameter.SUCCESS);
		
		return showRestaurant(req, String.valueOf(restaurant.getId()));
	}
	
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	protected ModelAndView showDoOrder(HttpServletRequest req, @RequestParam("restaurant_id") String restaurantIdString) {
		init(req);
		if (!mSessionManager.isLogged()) {
			return new ModelAndView("redirect:login");
		}
		OrderValidationHelper validator = new OrderValidationHelper(req, mSessionManager.getUser().getId());
		Boolean valid=validator.isValid();
		if (valid==null) {
			setMessage(req, "El costo del pedido no alcanza el costo mínimo");
			setMessageType(req, Parameter.ERROR);
		}else if(valid){
			Order order = validator.getOrder();
			mOrderManager.addOrder(order);
			setMessage(req, "Pedido realizado con éxito");
			setMessageType(req, Parameter.SUCCESS);
		} else {
			setMessage(req, "No se pudo realizar el pedido");
			setMessageType(req, Parameter.ERROR);
		}
		req.setAttribute(Parameter.RESTAURANT_ID, Long.valueOf(req.getParameter(Parameter.RESTAURANT_ID)));
		return showRestaurant(req, restaurantIdString);
	}
	
	@RequestMapping(value = "/deleteRestaurant", method = RequestMethod.POST)
	protected ModelAndView showDeleteRestaurant(HttpServletRequest req, @RequestParam(Parameter.RESTAURANT_ID) String restaurantIdString) {
		if ((restaurantIdString == null || !NumberUtils.isNumber(restaurantIdString))) {
			return new ModelAndView("redirect:restaurants");
		}
		Restaurant restaurant = mRestaurantManager.getRestaurant(Long.valueOf(restaurantIdString));
		if (restaurant == null) return new ModelAndView("redirect:restaurants");
		init(req);
		
		if (mSessionManager.getUser().getRole() != Role.ADMIN) {
			setMessage(req, "Solo el Admin puede borrar restoranes");
			setMessageType(req, Parameter.ERROR);
			return showRestaurant(req, restaurantIdString);
		}
		mRestaurantManager.deleteRestaurant(restaurant.getId());
		return new ModelAndView("redirect:restaurants");
	}
}
