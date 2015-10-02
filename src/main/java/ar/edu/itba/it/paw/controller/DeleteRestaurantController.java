package ar.edu.itba.it.paw.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.manager.implementation.RestaurantManagerImpl;
import ar.edu.itba.it.paw.model.User.Role;
import ar.edu.itba.it.paw.util.Header;
import ar.edu.itba.it.paw.util.Page;
import ar.edu.itba.it.paw.util.Parameter;

@Controller
public class DeleteRestaurantController extends BaseController {

	private RestaurantManager mRestaurantManager;
	
	@Autowired
	public DeleteRestaurantController(SessionManager sessionManager, RestaurantManager restaurantManager) {
		super(sessionManager);
		mRestaurantManager = restaurantManager;
	}
	
	@RequestMapping(value = "/deleteRestaurant", method = RequestMethod.POST)
	protected void showDeleteRestaurant(HttpServletRequest req, @RequestParam(Parameter.RESTAURANT_ID) long restaurantId) {
		if (mSessionManager.getUser().getRole() != Role.ADMIN) {
			//resp.sendRedirect(req.getHeader(Header.REFERER));
			return;
		}
		RestaurantManager restaurantManager = new RestaurantManagerImpl();
		restaurantManager.deleteRestaurant(Long.valueOf(req.getParameter(Parameter.RESTAURANT_ID)));
		//resp.sendRedirect(Page.HOME);
	}
}
