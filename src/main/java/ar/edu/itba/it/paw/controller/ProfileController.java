package ar.edu.itba.it.paw.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.form.EditProfileForm;
import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.model.Users;
import ar.edu.itba.it.paw.repository.NeighbourhoodRepo;
import ar.edu.itba.it.paw.util.Parameter;
import ar.edu.itba.it.paw.validator.EditProfileValidator;

@Controller
public class ProfileController extends BaseController {

	EditProfileValidator mEditProfileValidator;
	NeighbourhoodRepo mNeighbourhoodRepo;
	
	@Autowired
	public ProfileController(SessionManager sessionManager, EditProfileValidator editProfileValidator, NeighbourhoodRepo neighbourhoodRepo) {
		super(sessionManager);
		mEditProfileValidator = editProfileValidator;
		mNeighbourhoodRepo = neighbourhoodRepo;
	}
	
	@RequestMapping(value = "profile", method = RequestMethod.GET)
	protected ModelAndView showProfile(HttpServletRequest req, @RequestParam(value="result", required=false) String result) {
		Users user = mSessionManager.getUser();
		setResult(req, result);
		ModelAndView mav = createModelAndView(req);
		mav.addObject("editProfileForm", new EditProfileForm());
		mav.addObject("neighbourhoods", mNeighbourhoodRepo.getAllNeighbourhoods());
		mav.addObject(Parameter.USER, user);
		return mav;
	}
	
	private void setResult(HttpServletRequest req, String result) {
		if (("failure" + EditProfileValidator.ERROR_PASSWORDS).equals(result)) {
			setMessage(req, "Contrasena incorrecta");
			setMessageType(req, Parameter.ERROR);
		} else if (("failure" + EditProfileValidator.ERROR_INVALID_DATA).equals(result)) {
			setMessage(req, "Datos inválidos");
			setMessageType(req, Parameter.ERROR);
		} else if ("success".equals(result)){
			setMessage(req, "Informacion actualizada con exito");
			setMessageType(req, Parameter.SUCCESS);
		}
	}
	
	@RequestMapping(value = "editProfile", method = RequestMethod.POST)
	protected ModelAndView showEditProfile(HttpServletRequest req, EditProfileForm form, Errors errors) {
		form.setUser(mSessionManager.getUser());
		mEditProfileValidator.validate(form, errors);
		
		if (errors.hasErrors()) {
			return new ModelAndView("redirect:profile?result=failure" + mEditProfileValidator.getErrorType());
		} else {
			form.build(mNeighbourhoodRepo);
			return new ModelAndView("redirect:profile?result=success");
		}
	}
}
