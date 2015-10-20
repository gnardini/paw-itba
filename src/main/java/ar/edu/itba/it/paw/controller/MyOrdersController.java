package ar.edu.itba.it.paw.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.model.Users;
import ar.edu.itba.it.paw.util.Parameter;

@Controller
public class MyOrdersController extends BaseController {
	
	@Autowired
	public MyOrdersController(SessionManager sessionManager) {
		super(sessionManager);
	}
	
	@RequestMapping(value="/myOrders", method = RequestMethod.GET)
	public ModelAndView showMyOrders(HttpServletRequest req) {
		ModelAndView mav = createModelAndView(req);
		Users loggedUser = mSessionManager.getUser();
		if (loggedUser == null) return new ModelAndView("redirect:restaurants");

		mav.addObject(Parameter.ORDERS, loggedUser.getOrders());
		mav.setViewName("myOrders");
		return mav;
	}
}
