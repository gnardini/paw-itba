package ar.edu.itba.it.paw.helper;

import javax.servlet.http.HttpServletRequest;

import ar.edu.itba.it.paw.model.Comment;
import ar.edu.itba.it.paw.util.NumberUtils;

public class CommentValidationHelper {

	private HttpServletRequest mRequest;
	private Comment mComment;
	private long mUserId;
	
	public CommentValidationHelper(HttpServletRequest request, long userId) {
		mRequest = request;
		mUserId = userId;
	}
	
	public boolean isValidComment() {
		String text = mRequest.getParameter("text");
		String rating = mRequest.getParameter("rating");
		if (!NumberUtils.isInteger(rating)) return false;
		int ratingNumber = Integer.valueOf(rating);
		if (text == "" 
				|| ratingNumber < 1
				|| ratingNumber > 5) return false;
		mComment = new Comment(mUserId, Integer.valueOf(mRequest.getParameter("code")), ratingNumber, text);
		return true;
	}
	
	public Comment getComment() {
		return mComment;
	}
}
