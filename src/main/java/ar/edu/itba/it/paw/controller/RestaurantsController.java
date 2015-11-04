package ar.edu.itba.it.paw.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.repository.RestaurantRepo;
import ar.edu.itba.it.paw.util.Parameter;

@Controller
public class RestaurantsController extends BaseController {
	
	private final RestaurantRepo mRestaurantRepo;
	
	@Autowired
	public RestaurantsController(SessionManager sessionManager, RestaurantRepo restaurantRepo) {
		super(sessionManager);
		mRestaurantRepo = restaurantRepo;
	}


	@RequestMapping(value="/")
	protected ModelAndView showRedirect(HttpServletRequest req){
		return new ModelAndView("redirect:restaurants");
	}
	
	@RequestMapping(value="/restaurants", method = RequestMethod.GET)
	public ModelAndView showRestaurants(HttpServletRequest req) {
		ModelAndView mav = createModelAndView(req);
		List<Restaurant> restaurants = mRestaurantRepo.getRestaurants();
		mav.addObject(Parameter.RESTAURANTS, restaurants);
		mav.addObject("topRestaurants", mRestaurantRepo.getTopRestaurants());
		return mav;
	}
}
