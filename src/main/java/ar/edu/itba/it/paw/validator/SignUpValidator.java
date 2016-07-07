package ar.edu.itba.it.paw.validator;

import ar.edu.itba.it.paw.model.Neighbourhood;
import ar.edu.itba.it.paw.model.Users;
import ar.edu.itba.it.paw.model.Users.Role;
import ar.edu.itba.it.paw.util.DateUtils;
import ar.edu.itba.it.paw.util.EmailUtils;

public class SignUpValidator {
	
	private String firstName;
	private String lastName;
	private String address;
	private String email;
	private Integer day;
	private Integer month;
	private Integer year;
	private String password;
	private Neighbourhood neighbourhood;
	
	public SignUpValidator(String firstName, String lastName, String address, String email, Integer day, Integer month,
			Integer year, String password, Neighbourhood neighbourhood) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
		this.day = day;
		this.month = month;
		this.year = year;
		this.password = password;
		this.neighbourhood = neighbourhood;
	}

	public boolean isValidUser() {	
		if (firstName == "" 
				|| lastName == ""
				|| address == ""
				|| email == ""
				|| !EmailUtils.isEmail(email)
				|| day == null
				|| month == null
				|| year== null
				|| neighbourhood == null
				|| !DateUtils.isDate(day, month, year)
				|| password == "") {
			return false;
		}
		return true;
	}
	
	public Users getUser() {
		return new Users(firstName, lastName, address, email, 
				day, month, year, Role.NORMAL, 
				password, neighbourhood);
	}

}
