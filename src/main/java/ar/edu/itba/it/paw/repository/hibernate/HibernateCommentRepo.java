package ar.edu.itba.it.paw.repository.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.model.Comment;
import ar.edu.itba.it.paw.repository.CommentRepo;

@Repository
public class HibernateCommentRepo extends AbstractHibernateRepo implements CommentRepo {
	
	@Autowired
	public HibernateCommentRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public void addComment(Comment comment) {
		save(comment);
	}
	
	public void deleteComment(Comment comment) {
		delete(comment);
	}
}
