package ar.edu.itba.it.paw.helper;

import javax.servlet.http.HttpServletRequest;

import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.model.User.Role;
import ar.edu.itba.it.paw.util.DateUtils;
import ar.edu.itba.it.paw.util.EmailUtils;
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
		String day = mRequest.getParameter("day");
		String month = mRequest.getParameter("month");
		String year = mRequest.getParameter("year");
		String password = mRequest.getParameter("password");
				
		if (firstName == "" 
				|| lastName == ""
				|| address == ""
				|| email == ""
				|| !EmailUtils.isEmail(email)
				|| !NumberUtils.isInteger(day)
				|| !NumberUtils.isInteger(month)
				|| !NumberUtils.isInteger(year)
				|| !DateUtils.isDate(Integer.valueOf(day), Integer.valueOf(month), Integer.valueOf(year))
				|| password == "")
			return false;
		mUser = new User(firstName, lastName, address, email, Integer.valueOf(day), Integer.valueOf(month), Integer.valueOf(year), role, password);
		return true;
	}
	
	public User getUser() {
		return mUser;
	}
}
