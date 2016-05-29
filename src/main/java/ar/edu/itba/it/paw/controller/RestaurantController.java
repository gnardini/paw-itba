package ar.edu.itba.it.paw.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.model.Comment;
import ar.edu.itba.it.paw.model.Neighbourhood;
import ar.edu.itba.it.paw.model.OrderDetail;
import ar.edu.itba.it.paw.model.Orders;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.model.Users;
import ar.edu.itba.it.paw.model.Users.Role;
import ar.edu.itba.it.paw.repository.hibernate.CommentRepo;
import ar.edu.itba.it.paw.repository.hibernate.NeighbourhoodRepo;
import ar.edu.itba.it.paw.repository.hibernate.OrderDetailRepo;
import ar.edu.itba.it.paw.repository.hibernate.OrderRepo;
import ar.edu.itba.it.paw.repository.hibernate.HibernateRestaurantRepo;
import ar.edu.itba.it.paw.util.Parameter;
import ar.edu.itba.it.paw.validator.CommentValidationHelper;
import ar.edu.itba.it.paw.validator.OrderValidationHelper;

@Controller
public class RestaurantController extends BaseController {
	
	public static final int COMMENT_SUCCESS = 0;
	public static final int COMMENT_FAILURE = 1;
	
	protected HibernateRestaurantRepo mRestaurantRepo;
	protected CommentRepo mCommentRepo;
	protected OrderRepo mOrderRepo;
	protected OrderDetailRepo mOrderDetailRepo;
	protected NeighbourhoodRepo mNeighbourhoodRepo;
	
	@Autowired
	public RestaurantController(SessionManager sessionManager, HibernateRestaurantRepo restaurantRepo, CommentRepo commentRepo, OrderRepo orderRepo, OrderDetailRepo orderDetailRepo, NeighbourhoodRepo neighbourhoodRepo) {
		super(sessionManager);
		mRestaurantRepo = restaurantRepo;
		mCommentRepo = commentRepo;
		mOrderRepo = orderRepo;
		mOrderDetailRepo = orderDetailRepo;
		mNeighbourhoodRepo = neighbourhoodRepo;
	}
	
	@RequestMapping(value="/restaurant", method = RequestMethod.GET)
	protected ModelAndView showRestaurant(HttpServletRequest req, @RequestParam(Parameter.CODE) Restaurant restaurant, @RequestParam(value="result", required=false) String result) {
		ModelAndView mav = createModelAndView(req);
		if (restaurant == null) {
			return new ModelAndView("redirect:restaurants");
		}
		setResult(req, result);
		Users loggedUser = mSessionManager.getUser();
		boolean canComment = loggedUser != null;
		if (canComment) canComment = restaurant.canUserComment(loggedUser);
		mav.addObject(Parameter.CAN_COMMENT, canComment);
		
		restaurant.setDishes(restaurant.getDishes());
		restaurant.setComments(restaurant.getComments());
		List<Neighbourhood> neighbourhoods = mNeighbourhoodRepo.getAllNeighbourhoods();
		neighbourhoods.removeAll(restaurant.getNeighbourhoods());
		mav.addObject("neighbourhoods", neighbourhoods);
		mav.addObject(Parameter.RESTAURANT, restaurant);
		mav.setViewName("restaurantDetail");
		return mav;
	}
	
	private void setResult(HttpServletRequest req, String result) {
		if (("failure" + COMMENT_FAILURE).equals(result)) {
			setMessage(req, "No se pudo crear el comentario");
			setMessageType(req, Parameter.ERROR);;
		} else if (("success" + COMMENT_SUCCESS).equals(result)){
			setMessage(req, "Comentario creado con éxito");
			setMessageType(req, Parameter.SUCCESS);
		}
	}
	
	@RequestMapping(value = "/newComment", method = RequestMethod.POST)
	protected ModelAndView showNewComment(HttpServletRequest req, @RequestParam(Parameter.RESTAURANT_ID) Restaurant restaurant) {
		if (restaurant == null) {
			return new ModelAndView("redirect:restaurants");
		}
		init(req);
		String result = "";
		CommentValidationHelper validator = new CommentValidationHelper(req, mSessionManager.getUser(), restaurant);
		if (validator.isValidComment()) {
			Comment comment = validator.getComment();
			mCommentRepo.addComment(comment);
			restaurant.addComment(comment);
			result = "success" + COMMENT_SUCCESS;
		} else {
			result = "failure" + COMMENT_FAILURE;
		}
		return new ModelAndView("redirect:restaurant?code=" + restaurant.getId()
						+ "&result=" + result);
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
		return showRestaurant(req, restaurant, "");
	}
	
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	protected ModelAndView showDoOrder(HttpServletRequest req, @RequestParam(Parameter.RESTAURANT_ID) Restaurant restaurant) {
		init(req);
		if (!mSessionManager.isLogged()) {
			return new ModelAndView("redirect:login");
		}
		Users user = mSessionManager.getUser();
		if (user.getNeighbourhood() == null || !restaurant.reachesNeighbourhood(user.getNeighbourhood())) {
			setMessage(req, "El restoran no tiene delivery en tu barrio");
			setMessageType(req, Parameter.ERROR);
			return showRestaurant(req, restaurant, "");
		}
		OrderValidationHelper validator = new OrderValidationHelper(req, user, restaurant);
		Boolean valid = validator.isValid();
		if (valid == null) {
			setMessage(req, "El costo del pedido no alcanza el costo mínimo");
			setMessageType(req, Parameter.ERROR);
		} else if (valid) {
			Orders order = validator.getOrder();
			if (!restaurant.canOrder(order)) {
				setMessage(req, "El restaurant no se encuentra abierto. Abre a las " + restaurant.getOpeningHour() + " horas.");
				setMessageType(req, Parameter.ERROR);
				return showRestaurant(req, restaurant, "");
			}
			mOrderRepo.addOrder(order);
			for (OrderDetail detail: order.getDetails()) mOrderDetailRepo.storeOrderDetail(detail);
			order.setOnDependants();
			setMessage(req, "Pedido realizado con éxito");
			setMessageType(req, Parameter.SUCCESS);
		} else {
			setMessage(req, "No se pudo realizar el pedido");
			setMessageType(req, Parameter.ERROR);
		}
		req.setAttribute(Parameter.RESTAURANT_ID, restaurant.getId());
		return showRestaurant(req, restaurant, "");
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
			return showRestaurant(req, restaurant, "");
		}
		mRestaurantRepo.deleteRestaurant(restaurant);
		return new ModelAndView("redirect:restaurants");
	}
	
	@RequestMapping(value = "/addNeighbourhood", method = RequestMethod.POST)
	protected ModelAndView showAddNeighbourhood(HttpServletRequest req, @RequestParam(Parameter.RESTAURANT_ID) Restaurant restaurant, @RequestParam(value="neighbourboodId", required=false) Neighbourhood neighbourhood) {
		if (restaurant == null) {
			return new ModelAndView("redirect:restaurants");
		}
		if(neighbourhood == null){
			setMessage(req, "No se seleccionó barrio para agregar");
			setMessageType(req, Parameter.ERROR);
			return showRestaurant(req, restaurant, "");
		}
		init(req);
		if (mSessionManager.getUser().getRole() != Role.ADMIN) {
			setMessage(req, "Solo el Admin puede agregar barrios a los restoranes");
			setMessageType(req, Parameter.ERROR);
			return showRestaurant(req, restaurant, "");
		}
		if (restaurant.reachesNeighbourhood(neighbourhood)) {
			setMessage(req, "El barrio ya se encuentra agregado");
			setMessageType(req, Parameter.ERROR);
			return showRestaurant(req, restaurant, "");
		}
		restaurant.addNeighbourhood(neighbourhood);
		return new ModelAndView("redirect:restaurant?code=" + restaurant.getId());
	}
	
	@RequestMapping(value = "/removeNeighbourhood", method = RequestMethod.POST)
	protected ModelAndView showRemoveNeighbourhood(HttpServletRequest req, @RequestParam(Parameter.RESTAURANT_ID) Restaurant restaurant, @RequestParam(value="neighbourboodId", required=false) Neighbourhood neighbourhood) {
		if (restaurant == null) {
			return new ModelAndView("redirect:restaurants");
		}
		if(neighbourhood == null){
			setMessage(req, "No se seleccionó barrio para remover");
			setMessageType(req, Parameter.ERROR);
			return showRestaurant(req, restaurant, "");
		}
		init(req);
		
		if (mSessionManager.getUser().getRole() != Role.ADMIN) {
			setMessage(req, "Solo el Admin puede remover barrios de los restoranes");
			setMessageType(req, Parameter.ERROR);
			return showRestaurant(req, restaurant, "");
		}
		if (!restaurant.reachesNeighbourhood(neighbourhood)) {
			setMessage(req, "El barrio no se encuentra agregado");
			setMessageType(req, Parameter.ERROR);
			return showRestaurant(req, restaurant, "");
		}
		if (restaurant.getNeighbourhoods().size() <= 1) {
			setMessage(req, "No se puede quitar el restoran (tiene que haber por lo menos uno)");
			setMessageType(req, Parameter.ERROR);
			return showRestaurant(req, restaurant, "");
		}
		restaurant.removeNeighbourhood(neighbourhood);
		return new ModelAndView("redirect:restaurant?code=" + restaurant.getId());
	}
}
