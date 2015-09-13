package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.manager.implementation.SessionManagerImpl;
import ar.edu.itba.it.paw.model.User.Role;

public abstract class ControlPanelController extends BaseController {
	
	protected static final String RESTAURANTS = "restaurants";
	
	protected boolean mPermissionGranted;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		super.doGet(req, resp);
		SessionManager sessionManager = new SessionManagerImpl(req);
		
		
		if (!(Boolean) req.getAttribute(LOGGED)
				|| sessionManager.getUser().getRole() != getRolePanel()) {
			resp.sendRedirect("/restaurants");
			mPermissionGranted = false;
		} else mPermissionGranted = true;
	}
	
	protected abstract Role getRolePanel();
}
