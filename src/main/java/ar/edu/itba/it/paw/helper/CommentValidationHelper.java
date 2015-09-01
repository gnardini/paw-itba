package ar.edu.itba.it.paw.helper;

import javax.servlet.http.HttpServletRequest;

import ar.edu.itba.it.paw.model.Comment;
import ar.edu.itba.it.paw.util.NumberUtils;

public class CommentValidationHelper {

	private HttpServletRequest mRequest;
	private Comment mComment;
	
	public CommentValidationHelper(HttpServletRequest request) {
		mRequest = request;
	}
	
	public boolean isValidComment() {
		String text = mRequest.getParameter("text");
		String rating = mRequest.getParameter("rating");
		if (!NumberUtils.isInteger(rating)) return false;
		int ratingNumber = Integer.valueOf(rating);
		if (text == "" 
				|| ratingNumber < 1
				|| ratingNumber > 5) return false;
		mComment = new Comment(text, ratingNumber);
		return true;
	}
	
	public Comment getComment() {
		return mComment;
	}
}
