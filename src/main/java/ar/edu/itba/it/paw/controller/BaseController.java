package ar.edu.itba.it.paw.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.model.Users;
import ar.edu.itba.it.paw.model.Users.Role;
import ar.edu.itba.it.paw.util.Parameter;

public abstract class BaseController {
	
	private static final long serialVersionUID = 1L;
	
	protected SessionManager mSessionManager;

	public BaseController(SessionManager sessionManager) {
		mSessionManager = sessionManager;
	}
	
	protected ModelAndView createModelAndView(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		mSessionManager.setSession(req.getSession());
		mav.addObject(Parameter.LOGGED, mSessionManager.isLogged());
		if (mSessionManager.isLogged()) {
			Users user = mSessionManager.getUser();
			mav.addObject(Parameter.USER_ADMIN, user.getRole() == Role.ADMIN);
			mav.addObject(Parameter.USER_MANAGER, user.getRole() == Role.MANAGER);
			mav.addObject(Parameter.USER_NORMAL, user.getRole() == Role.NORMAL);
		}
		return mav;
	}
	
	protected void init(HttpServletRequest req) {
		mSessionManager.setSession(req.getSession());
	}
	
	protected void setMessage(HttpServletRequest req, String message) {
		req.setAttribute(Parameter.MESSAGE, message);
	}
	
	protected void setMessageType(HttpServletRequest req, String type) {
		req.setAttribute(type, true);
	}
}
