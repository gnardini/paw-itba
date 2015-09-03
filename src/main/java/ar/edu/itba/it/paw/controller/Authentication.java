package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.manager.UserManager;
import ar.edu.itba.it.paw.manager.implementation.SessionManager;
import ar.edu.itba.it.paw.util.JspLocationUtils;

public class Authentication extends BaseController {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
		if ((Boolean) req.getAttribute(LOGGED)) {
			resp.sendRedirect("/");
			return;
		}
		req.getRequestDispatcher(JspLocationUtils.LOGIN).forward(req, resp);
	}
}
