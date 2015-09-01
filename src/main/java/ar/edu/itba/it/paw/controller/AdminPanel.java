package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.db.UserDatabase;
import ar.edu.itba.it.paw.helper.UserValidatorHelper;
import ar.edu.itba.it.paw.model.User.Role;
import ar.edu.itba.it.paw.util.JspLocationUtils;

public class AdminPanel extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		req.getRequestDispatcher(JspLocationUtils.CONTROL_PANEL).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserValidatorHelper validator = new UserValidatorHelper(req);
		if (!validator.isValidUser(Role.MANAGER)) {
			req.setAttribute("message", "No se pudo completar la operacion");
			doGet(req, resp);
		} else {
			UserDatabase.getInstance().storeUser(validator.getUser());
			req.setAttribute("message", "Se completo la operacion");
			doGet(req, resp);
		}
	}
}
