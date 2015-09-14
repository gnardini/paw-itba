package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.model.User.Role;
import ar.edu.itba.it.paw.util.JspLocationUtils;

public class UserPanelController extends ControlPanelController {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
		if (!mPermissionGranted) return;
		req.getRequestDispatcher(JspLocationUtils.USER_PANEL).forward(req, resp);
	}
	
	@Override
	protected Role getRolePanel() {
		return Role.NORMAL;
	}
}
