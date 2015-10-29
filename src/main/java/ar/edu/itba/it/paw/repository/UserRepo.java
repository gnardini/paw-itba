package ar.edu.itba.it.paw.repository;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.model.Users;
import ar.edu.itba.it.paw.model.Users.Role;

@Repository
public class UserRepo extends AbstractHibernateRepo {
	
	@Autowired
	public UserRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Users getUser(int id) {
		return get(Users.class, id);
	}
	
	public Users getUser(String email) {
		if(email==null)
			return null;
		List<Users> user = find("from Users where email=?", email);
		return user.isEmpty() ? null : user.get(0);
	}
	
	public List<Users> getUsers(Role role) {
		return find("from Users where role=?",role);
	}
	
	public void storeUser(Users user) {
		save(user);
	}
}
