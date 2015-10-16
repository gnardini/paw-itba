package ar.edu.itba.it.paw.repository;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.model.User.Role;

@Repository
public class UserRepo extends AbstractHibernateRepo {
	
	@Autowired
	public UserRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public User getUser(long id) {
		return get(User.class, id);
	}
	
	public User getUser(String email) {
		List<User> user = find("from user where email=?", email);
		return user.isEmpty() ? null : user.get(0);
	}
	
	public List<User> getUsers(Role role) {
		return find("from users where lower(type)=?",role.toString().toLowerCase());
	}
	
	public void storeUser(User user) {
		save(user);
	}
}
