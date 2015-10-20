package ar.edu.itba.it.paw.manager;

import java.util.List;

import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.model.Users;
import ar.edu.itba.it.paw.model.Users.Role;

public interface UserManager {

	public Users getUser(long id);
	
	public Users getUser(String email);
	
	public List<Users> getUsers(Role role);
	
	public boolean assignManager(Users user, Restaurant restaurant);
}
