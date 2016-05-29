package ar.edu.itba.it.paw.repository.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.model.Comment;

@Repository
public class CommentRepo extends AbstractHibernateRepo {
	
	@Autowired
	public CommentRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public void addComment(Comment comment) {
		save(comment);
	}
	
	public void deleteComment(Comment comment) {
		delete(comment);
	}
}
