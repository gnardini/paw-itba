package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.manager.UserManager;
import ar.edu.itba.it.paw.manager.implementation.UserManagerImpl;
import ar.edu.itba.it.paw.model.User.Role;
import ar.edu.itba.it.paw.util.JspLocationUtils;

public class AdminPanelController extends ControlPanelController {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
		if (!(Boolean) req.getAttribute(LOGGED)) return;
		UserManager manager = new UserManagerImpl();
		req.setAttribute("users", manager.getUsers(Role.NORMAL));
	}
	
	@Override
	protected String getJspLocation() {
		return JspLocationUtils.ADMIN_PANEL;
	}
}
