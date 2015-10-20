package ar.edu.itba.it.paw.helper;

import javax.servlet.http.HttpServletRequest;

import ar.edu.itba.it.paw.model.Comment;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.model.Users;
import ar.edu.itba.it.paw.util.NumberUtils;
import ar.edu.itba.it.paw.util.Parameter;

public class CommentValidationHelper {

	private HttpServletRequest mRequest;
	private Comment mComment;
	private Users mUser;
	private Restaurant mRestaurant;
	
	public CommentValidationHelper(HttpServletRequest request, Users user, Restaurant restaurant) {
		mRequest = request;
		mUser = user;
		mRestaurant = restaurant;
	}
	
	public boolean isValidComment() {
		String text = mRequest.getParameter(Parameter.TEXT);
		String rating = mRequest.getParameter(Parameter.RATING);
		if(!mRestaurant.canUserComment(mUser)) return false;
		if (rating=="" || rating.length()>1 || !NumberUtils.isNumber(rating)) return false;
		int ratingNumber = Integer.valueOf(rating);
		if (text == "" 
				|| ratingNumber < 1
				|| ratingNumber > 5) return false;
		mComment = new Comment(mUser, mRestaurant, ratingNumber, text);
		return true;
	}
	
	public Comment getComment() {
		return mComment;
	}
}
