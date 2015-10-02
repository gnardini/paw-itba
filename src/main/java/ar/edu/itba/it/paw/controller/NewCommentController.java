package ar.edu.itba.it.paw.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.helper.CommentValidationHelper;
import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.SessionManager;
import ar.edu.itba.it.paw.util.Parameter;

@Controller
public class NewCommentController extends BaseController {
	
	private RestaurantManager mRestaurantManager;
	
	@Autowired
	public NewCommentController(SessionManager sessionManager, RestaurantManager restaurantManager) {
		super(sessionManager);
		mRestaurantManager = restaurantManager;
	}
	
	@RequestMapping(value = "/newComment", method = RequestMethod.POST)
	protected ModelAndView showNewComment(HttpServletRequest req, @RequestParam(Parameter.RESTAURANT_ID) long restaurantId) {
		ModelAndView mav = createModelAndView(req);
		CommentValidationHelper validator = new CommentValidationHelper(req, mSessionManager.getUser().getId());
		if (validator.isValidComment()) {
			mRestaurantManager.addComment(validator.getComment());
			setMessage(req, "Comentario creado con éxito");
			setMessageType(req, Parameter.SUCCESS);
		} else {
			setMessage(req, "No se pudo crear el comentario");
			setMessageType(req, Parameter.ERROR);
		}
		mav.addObject(Parameter.RESTAURANT_ID, Long.valueOf(req.getParameter(Parameter.RESTAURANT_ID)));
		return mav;//showRestaurant(req, restaurantId);
	}
}
