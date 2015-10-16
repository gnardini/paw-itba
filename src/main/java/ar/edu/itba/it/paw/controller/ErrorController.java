package ar.edu.itba.it.paw.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.manager.SessionManager;

@Controller
public class ErrorController extends BaseController {

	@Autowired
	public ErrorController(SessionManager sessionManager) {
		super(sessionManager);
	}
	
	@RequestMapping(value="/error", method = RequestMethod.GET)
	protected ModelAndView showError(HttpServletRequest req) {
		ModelAndView mav = createModelAndView(req);
		mav.setViewName("error");
		return mav;
	}
}
