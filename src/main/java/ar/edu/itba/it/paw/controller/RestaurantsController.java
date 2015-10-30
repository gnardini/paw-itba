package ar.edu.itba.it.paw.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.util.Parameter;

@Controller
public class RestaurantsController extends BaseController {
	
	private final RestaurantManager mRestaurantManager;
	
	@Autowired
	public RestaurantsController(SessionManager sessionManager, RestaurantManager restaurantManager) {
		super(sessionManager);
		this.mRestaurantManager = restaurantManager;
	}


	@RequestMapping(value="/")
	protected ModelAndView showRedirect(HttpServletRequest req){
		return new ModelAndView("redirect:restaurants");
	}
	
	@RequestMapping(value="/restaurants", method = RequestMethod.GET)
	public ModelAndView showRestaurants(HttpServletRequest req) {
		ModelAndView mav = createModelAndView(req);
		List<Restaurant> restaurants = mRestaurantManager.getRestaurants();
		mav.addObject(Parameter.RESTAURANTS, restaurants);
		mav.addObject("topRestaurants", mRestaurantManager.getTopRestaurants());
		return mav;
	}
}
