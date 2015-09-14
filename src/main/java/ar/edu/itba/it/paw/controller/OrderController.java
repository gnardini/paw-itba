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

public class OrderController extends BaseController {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OrderManager orderManager = new OrderManagerImpl();
		SessionManager sessionManager = new SessionManagerImpl(req);
		OrderValidationHelper validator = new OrderValidationHelper(req, sessionManager.getUser().getId());
		if (validator.isValid()) {
			Order order = validator.getOrder();
			orderManager.addOrder(order);
		}
		resp.sendRedirect("/restaurant?code=" + req.getParameter("code"));
	}
}
