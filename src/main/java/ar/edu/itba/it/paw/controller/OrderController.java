package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.helper.OrderValidationHelper;
import ar.edu.itba.it.paw.manager.OrderManager;
import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.manager.implementation.OrderManagerImpl;
import ar.edu.itba.it.paw.manager.implementation.SessionManagerImpl;
import ar.edu.itba.it.paw.model.Order;
import ar.edu.itba.it.paw.util.Page;
import ar.edu.itba.it.paw.util.Parameter;

public class OrderController extends RestaurantDetailController {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OrderManager orderManager = new OrderManagerImpl();
		SessionManager sessionManager = new SessionManagerImpl(req);
		if (!sessionManager.isLogged()) {
			resp.sendRedirect(Page.LOGIN);
			return;
		}
		OrderValidationHelper validator = new OrderValidationHelper(req, sessionManager.getUser().getId());
		Boolean valid=validator.isValid();
		if (valid==null) {
			setMessage(req, "El costo del pedido no alcanza el costo mínimo");
			setMessageType(req, Parameter.ERROR);
		}else if(valid){
			Order order = validator.getOrder();
			orderManager.addOrder(order);
			setMessage(req, "Pedido realizado con éxito");
			setMessageType(req, Parameter.SUCCESS);
		} else {
			setMessage(req, "No se pudo realizar el pedido");
			setMessageType(req, Parameter.ERROR);
		}
		req.setAttribute(Parameter.RESTAURANT_ID, Long.valueOf(req.getParameter(Parameter.RESTAURANT_ID)));
		doGet(req, resp);
	}
}
