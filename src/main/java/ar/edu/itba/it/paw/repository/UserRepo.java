package ar.edu.itba.it.paw.repository;

import java.util.List;

import ar.edu.itba.it.paw.model.Users;
import ar.edu.itba.it.paw.model.Users.Role;

public interface UserRepo {

	public Users getUser(int id);
	
	public Users getUser(String email) ;
	
	public List<Users> getUsers(Role role);
	
	public void storeUser(Users user);
	
	public void updateUser(Users user);
	
}
