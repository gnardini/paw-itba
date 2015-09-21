package ar.edu.itba.it.paw.helper;

import javax.servlet.http.HttpServletRequest;

import ar.edu.itba.it.paw.model.Comment;
import ar.edu.itba.it.paw.util.NumberUtils;
import ar.edu.itba.it.paw.util.Parameter;

public class CommentValidationHelper {

	private HttpServletRequest mRequest;
	private Comment mComment;
	private long mUserId;
	
	public CommentValidationHelper(HttpServletRequest request, long userId) {
		mRequest = request;
		mUserId = userId;
	}
	
	public boolean isValidComment() {
		String text = mRequest.getParameter(Parameter.TEXT);
		String rating = mRequest.getParameter(Parameter.RATING);
		if (rating=="" || rating.length()>1 || !NumberUtils.isInteger(rating)) return false;
		int ratingNumber = Integer.valueOf(rating);
		if (text == "" 
				|| ratingNumber < 1
				|| ratingNumber > 5) return false;
		mComment = new Comment(mUserId, Long.valueOf(mRequest.getParameter(Parameter.RESTAURANT_ID)), ratingNumber, text);
		return true;
	}
	
	public Comment getComment() {
		return mComment;
	}
}
