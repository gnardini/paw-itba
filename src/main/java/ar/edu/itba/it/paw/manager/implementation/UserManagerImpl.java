package ar.edu.itba.it.paw.manager.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.it.paw.manager.UserManager;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.model.Users;
import ar.edu.itba.it.paw.model.Users.Role;
import ar.edu.itba.it.paw.repository.UserRepo;

@Service
public class UserManagerImpl implements UserManager {

	private UserRepo userRepo;
	
	@Autowired
	public UserManagerImpl(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	@Override
	public Users getUser(int id) {
		return userRepo.getUser(id);
	}

	@Override
	public Users getUser(String email) {
		return userRepo.getUser(email);
	}

	@Override
	public List<Users> getUsers(Role role) {
		return userRepo.getUsers(role);
	}
	
	@Override
	public boolean assignManager(Users user, Restaurant restaurant) {
		if (user.isManagerOf(restaurant)) return false;
		user.addRestaurantToManage(restaurant);
		return true;
	}
}
