package ar.edu.itba.it.paw.util;

import ar.edu.itba.it.paw.model.Users.Role;

public class RoleUtils {

	public static Role getRoleFromString(String role) {
		if ("admin".equals(role)) return Role.ADMIN;
		else if ("manager".equals(role)) return Role.MANAGER;
		else return Role.NORMAL;
	}
}
