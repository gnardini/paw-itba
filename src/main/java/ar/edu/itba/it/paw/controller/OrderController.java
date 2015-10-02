package ar.edu.itba.it.paw.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.helper.OrderValidationHelper;
import ar.edu.itba.it.paw.manager.OrderManager;
import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.model.Order;
import ar.edu.itba.it.paw.util.Parameter;

@Controller
public class OrderController extends BaseController {
	
	private RestaurantManager mRestaurantManager;
	private OrderManager mOrderManager;
	
	@Autowired
	public OrderController(SessionManager sessionManager, RestaurantManager restaurantManager, OrderManager orderManager) {
		super(sessionManager);
		mRestaurantManager = restaurantManager;
		mOrderManager = orderManager;
	}
	
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	protected ModelAndView showDoOrder(HttpServletRequest req) {
		ModelAndView mav = createModelAndView(req);
		if (!mSessionManager.isLogged()) {
			//resp.sendRedirect(Page.LOGIN);
			return mav;
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
		return mav;
	}
}
