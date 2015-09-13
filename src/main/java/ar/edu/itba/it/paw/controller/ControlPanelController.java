package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ControlPanelController extends BaseController {
	
	protected static final String RESTAURANTS = "restaurants";
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		super.doGet(req, resp);
		if (!(Boolean) req.getAttribute(LOGGED)) {
			resp.sendRedirect("/restaurants");
			return;
		}
	}
}
