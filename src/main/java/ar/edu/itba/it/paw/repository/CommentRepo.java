package ar.edu.itba.it.paw.repository;

import ar.edu.itba.it.paw.model.Comment;

public interface CommentRepo {

	public void addComment(Comment comment);
	
	public void deleteComment(Comment comment);
	
}
