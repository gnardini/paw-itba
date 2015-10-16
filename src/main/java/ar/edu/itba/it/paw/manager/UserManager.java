package ar.edu.itba.it.paw.manager;

import java.util.List;

import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.model.User.Role;

public interface UserManager {

	public User getUser(long id);
	
	public User getUser(String email);
	
	public List<User> getUsers(Role role);
	
	public boolean assignManager(User user, Restaurant restaurant);
}
