package ar.edu.itba.it.paw.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.form.SignUpForm;
import ar.edu.itba.it.paw.helper.SignUpValidator;
import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.util.Header;
import ar.edu.itba.it.paw.util.Parameter;

@Controller
public class AuthenticationController extends BaseController {
	
	private SignUpValidator mSignUpValidator;
	
	@Autowired
	public AuthenticationController(SessionManager sessionManager, SignUpValidator signUpValidator) {
		super(sessionManager);
		mSignUpValidator = signUpValidator;
	}	
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	protected ModelAndView showLogin(HttpServletRequest req) {
		ModelAndView mav = createModelAndView(req);
		if (mSessionManager.isLogged()) {
			return new ModelAndView("redirect:restaurants");
		}
		mav.addObject("signUpForm", new SignUpForm());
		return mav;
	}
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	protected ModelAndView showLogin(HttpServletRequest req,
			@RequestParam(Parameter.EMAIL) String email, @RequestParam(Parameter.PASSWORD) String password) {
		mSessionManager.setSession(req.getSession());
		if (!mSessionManager.login(email, password)) {
			setMessage(req, "Usuario o contraseña erróneos");
			setMessageType(req, Parameter.ERROR);
		}
		return showLogin(req);
	}
	
	@RequestMapping(value = "signup", method = RequestMethod.POST)
	protected ModelAndView showSignUp(HttpServletRequest req, SignUpForm form, Errors errors) {
		mSessionManager.setSession(req.getSession());
		mSignUpValidator.validate(form, errors);
		System.out.println("asd");
		if (errors.hasErrors()) {
			setMessage(req, "Datos de registro inválidos");
			setMessageType(req, Parameter.ERROR);
		} else if (!mSessionManager.signup(form.build())) {
			setMessage(req, "El usuario ya existe");
			setMessageType(req, Parameter.ERROR);
		}
		return showLogin(req);
	}
	
	@RequestMapping(value = "logout", method = RequestMethod.POST)
	protected ModelAndView showLogOut(HttpServletRequest req) {
		mSessionManager.setSession(req.getSession());
		mSessionManager.logout();
		return new ModelAndView("redirect:" + req.getHeader(Header.REFERER));
	}
}
