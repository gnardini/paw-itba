package ar.edu.itba.it.paw.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.it.paw.model.Users;
import ar.edu.itba.it.paw.repository.hibernate.HibernateUserRepo;
import ar.edu.itba.it.paw.util.NumberUtils;

@Component
public class UserConverter implements Converter<String, Users> {
	
	private HibernateUserRepo userRepo;
	
	@Autowired
	public UserConverter(HibernateUserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	@Override
	public Users convert(String userIdString) {
		if ((userIdString == null || !NumberUtils.isNumber(userIdString))) {
			return null;
		}
		int userId = Integer.valueOf(userIdString);
		return userRepo.getUser(userId);
	}
}
