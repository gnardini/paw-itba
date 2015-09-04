package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.helper.OrderValidationHelper;
import ar.edu.itba.it.paw.manager.OrderManager;
import ar.edu.itba.it.paw.manager.implementation.OrderManagerImpl;
import ar.edu.itba.it.paw.model.Order;

public class OrderController extends BaseController {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OrderManager manager = new OrderManagerImpl();
		OrderValidationHelper validator = new OrderValidationHelper(req);
		if (validator.isValid()) {
			Order order = validator.getOrder();
			manager.addOrder(Integer.valueOf(req.getParameter("code")), order);
		}
		resp.sendRedirect("/restaurant?code=" + req.getParameter("code"));
	}
}
