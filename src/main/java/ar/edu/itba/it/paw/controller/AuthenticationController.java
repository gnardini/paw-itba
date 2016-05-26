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
import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.repository.NeighbourhoodRepo;
import ar.edu.itba.it.paw.util.Header;
import ar.edu.itba.it.paw.util.Parameter;
import ar.edu.itba.it.paw.validator.SignUpValidator;

@Controller
public class AuthenticationController extends BaseController {
	
	private SignUpValidator mSignUpValidator;
	private NeighbourhoodRepo mNeighbourhoodRepo;
	
	@Autowired
	public AuthenticationController(SessionManager sessionManager, SignUpValidator signUpValidator, NeighbourhoodRepo neighbourhoodRepo) {
		super(sessionManager);
		mSignUpValidator = signUpValidator;
		mNeighbourhoodRepo = neighbourhoodRepo;
	}	
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	protected ModelAndView showLogin(HttpServletRequest req) {
		ModelAndView mav = createModelAndView(req);
		if (mSessionManager.isLogged()) {
			return new ModelAndView("redirect:restaurants");
		}
		mav.addObject("neighbourhoods", mNeighbourhoodRepo.getAllNeighbourhoods());
		mav.addObject("signUpForm", new SignUpForm());
		return mav;
	}
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	protected ModelAndView showLogin(HttpServletRequest req,
			@RequestParam(Parameter.EMAIL) String email, @RequestParam(Parameter.PASSWORD) String password) {
		//mSessionManager.setSession(req.getSession());
		if (!mSessionManager.login(email, password)) {
			setMessage(req, "Usuario o contraseña erróneos");
			setMessageType(req, Parameter.ERROR);
		}
		return showLogin(req);
	}
	
	@RequestMapping(value = "signup", method = RequestMethod.POST)
	protected ModelAndView showSignUp(HttpServletRequest req, SignUpForm form, Errors errors) {
		//mSessionManager.setSession(req.getSession());
		mSignUpValidator.validate(form, errors);

		ModelAndView mav = createModelAndView(req);
		mav.setViewName("login");
		mav.addObject("neighbourhoods", mNeighbourhoodRepo.getAllNeighbourhoods());
		mav.addObject("signUpForm", new SignUpForm());
		if (errors.hasErrors()) {
			setMessage(req, "Datos de registro inválidos");
			setMessageType(req, Parameter.ERROR);
			return mav;
		} else if (!mSessionManager.signup(form.build(mNeighbourhoodRepo))) {
			setMessage(req, "El usuario ya existe");
			setMessageType(req, Parameter.ERROR);
			return mav;
		}
		return new ModelAndView("redirect:restaurants");
	}
	
	@RequestMapping(value = "logout", method = RequestMethod.POST)
	protected ModelAndView showLogOut(HttpServletRequest req) {
		//mSessionManager.setSession(req.getSession());
		mSessionManager.logout();
		return new ModelAndView("redirect:restaurants");
	}
}
