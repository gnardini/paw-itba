package ar.edu.itba.it.paw.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.helper.CommentValidationHelper;
import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.model.Comment;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.model.User.Role;
import ar.edu.itba.it.paw.util.Page;
import ar.edu.itba.it.paw.util.Parameter;

@Controller
public class RestaurantDetailController extends BaseController {
	
	protected RestaurantManager mRestaurantManager;
	
	@Autowired
	public RestaurantDetailController(SessionManager sessionManager, RestaurantManager restaurantManager) {
		super(sessionManager);
		mRestaurantManager = restaurantManager;
	}
	
	@RequestMapping(value="/restaurant", method = RequestMethod.GET)
	protected ModelAndView showRestaurant(HttpServletRequest req, Restaurant restaurant) {
		ModelAndView mav = createModelAndView(req);
		//if (restaurantIdString == null || !NumberUtils.isNumber(restaurantIdString))
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
	protected ModelAndView showNewComment(HttpServletRequest req, Restaurant restaurant) {
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
		return showRestaurant(req, restaurant);
	}
	
	@RequestMapping(value = "deleteComment", method = RequestMethod.POST)
	protected ModelAndView showDeleteComment(HttpServletRequest req, Restaurant restaurant, Comment comment) {
		if (restaurant == null || comment == null) return new ModelAndView("redirect:restaurants");
		init(req);
		
		if (!mSessionManager.isLogged() || mSessionManager.getUser().getRole() != Role.ADMIN) {
			// shouldn't happen
			return new ModelAndView("redirect:restaurants");
		}
		long userId = Long.valueOf(req.getParameter(Parameter.USER_ID));
		mRestaurantManager.deleteComment(userId, restaurant.getId());
		setMessage(req, "Comentario borrado con éxito");
		setMessageType(req, Parameter.SUCCESS);
		
		return showRestaurant(req, restaurant);
	}
}
