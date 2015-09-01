package ar.edu.itba.it.paw.helper;

import javax.servlet.http.HttpServletRequest;

import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.model.User.Role;
import ar.edu.itba.it.paw.util.NumberUtils;

public class UserValidationHelper {

	private HttpServletRequest mRequest;
	private User mUser;
	
	public UserValidationHelper(HttpServletRequest req) {
		mRequest = req;
	}
	
	public boolean isValidUser(Role role) {
		String firstName = mRequest.getParameter("firstName");
		String lastName = mRequest.getParameter("lastName");
		String address = mRequest.getParameter("address");
		String email = mRequest.getParameter("email");
		String age = mRequest.getParameter("age");
		String password = mRequest.getParameter("password");
		if (firstName == "" 
				|| lastName == ""
				|| address == ""
				|| email == ""
				|| age == ""
				|| !NumberUtils.isInteger(age)
				|| password == "")
			return false;
		mUser = new User(firstName, lastName, address, email, Integer.valueOf(age), role, password);
		return true;
	}
	
	public User getUser() {
		return mUser;
	}
}
