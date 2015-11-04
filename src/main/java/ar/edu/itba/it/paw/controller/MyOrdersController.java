package ar.edu.itba.it.paw.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.model.Orders;
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
		List<Orders> orders = loggedUser.getOrders();
		Collections.sort(orders, new Comparator<Orders>() {
			public int compare(Orders o1, Orders o2) {
				return -o1.getMade().compareTo(o2.getMade());
			};
		});
		mav.addObject(Parameter.ORDERS, orders);
		mav.setViewName("myOrders");
		return mav;
	}
	
	@RequestMapping(value="/orderDelivered", method = RequestMethod.POST)
	public ModelAndView showOrderDelivered(HttpServletRequest req, @RequestParam("order_id") Orders order) {
		Users user = mSessionManager.getUser();
		if (order != null && order.getUser().equals(user)) {
			order.setDelivered(true);
		}
		return new ModelAndView("redirect:myOrders");
	}
}
