package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.manager.OrderManager;
import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.manager.implementation.OrderManagerImpl;
import ar.edu.itba.it.paw.manager.implementation.SessionManagerImpl;
import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.util.JspLocationUtils;
import ar.edu.itba.it.paw.util.Page;
import ar.edu.itba.it.paw.util.Parameter;

public class MyOrdersController extends OldBaseController {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
		SessionManager sessionManager = new SessionManagerImpl();
		sessionManager.setSession(req.getSession());
		OrderManager orderManager = new OrderManagerImpl();
				
		User loggedUser = sessionManager.getUser();
		
		if (!(Boolean) req.getAttribute(Parameter.LOGGED)) resp.sendRedirect(Page.RESTAURANTS);
		else {
			req.setAttribute(Parameter.ORDERS, orderManager.getOrders(loggedUser.getId()));
			req.getRequestDispatcher(JspLocationUtils.ORDERS).forward(req, resp);			
		}
	}
}
