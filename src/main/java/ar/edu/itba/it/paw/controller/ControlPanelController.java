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
import ar.edu.itba.it.paw.model.User.Role;

public abstract class ControlPanelController extends BaseController {
	
	protected static final String RESTAURANTS = "restaurants";
	protected static final String ORDERS = "orders";
	
	protected boolean mPermissionGranted;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		super.doGet(req, resp);
		SessionManager sessionManager = new SessionManagerImpl(req);
		OrderManager orderManager = new OrderManagerImpl();
				
		User loggedUser = sessionManager.getUser();
		if (!(Boolean) req.getAttribute(LOGGED)
				|| loggedUser.getRole() != getRolePanel()) {
			resp.sendRedirect("/restaurants");
			mPermissionGranted = false;
		} else {
			mPermissionGranted = true;
			req.setAttribute(ORDERS, orderManager.getOrders(loggedUser.getId()));
		}
	}
	
	protected abstract Role getRolePanel();
}
