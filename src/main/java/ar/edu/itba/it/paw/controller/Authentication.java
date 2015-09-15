package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.util.JspLocationUtils;
import ar.edu.itba.it.paw.util.Page;
import ar.edu.itba.it.paw.util.Parameter;

public class Authentication extends BaseController {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
		if ((Boolean) req.getAttribute(Parameter.LOGGED)) {
			resp.sendRedirect(Page.HOME);
			return;
		}
		req.getRequestDispatcher(JspLocationUtils.LOGIN).forward(req, resp);
	}
}
