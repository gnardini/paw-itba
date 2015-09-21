package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.helper.UserValidationHelper;
import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.manager.implementation.SessionManagerImpl;
import ar.edu.itba.it.paw.model.User.Role;
import ar.edu.itba.it.paw.util.Parameter;

public class SignUpController extends Authentication {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionManager manager = new SessionManagerImpl(req);
		UserValidationHelper validator = new UserValidationHelper(req);
		if (!validator.isValidUser(Role.NORMAL)) {
			setMessage(req, "Datos de registro inválidos");
			setMessageType(req, Parameter.ERROR);
		} else if (!manager.signup(validator.getUser())) {
			setMessage(req, "El usuario ya existe");
			setMessageType(req, Parameter.ERROR);
		}
		doGet(req, resp);
	}
}
