package ar.edu.itba.it.paw.web.validator;

import java.util.Date;

import ar.edu.itba.it.paw.model.Neighbourhood;
import ar.edu.itba.it.paw.model.Users;
import ar.edu.itba.it.paw.util.DateUtils;
import ar.edu.itba.it.paw.util.EmailUtils;

public class EditProfileValidator {
	
	private String firstName;
	private String lastName;
	private String address;
	private String email;
	private Integer day;
	private Integer month;
	private Integer year;
	private String password;
	private String passwordConfirmation;
	private Neighbourhood neighbourhood;
	
	private String errorMessage;
	
	public EditProfileValidator(String firstName, String lastName, String address, String email, Integer day,
			Integer month, Integer year, String password, String passwordConfirmation, 
			Neighbourhood neighbourhood) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
		this.day = day;
		this.month = month;
		this.year = year;
		this.password = password;
		this.passwordConfirmation = passwordConfirmation;
		this.neighbourhood = neighbourhood;
	}

	public boolean isValidUser() {
		if (firstName == null 
				|| firstName.equals("")
				|| lastName == null
				|| lastName.equals("") 
				|| address == null
				|| address.equals("") 
				|| email == null 
				|| email.equals("") 
				|| !EmailUtils.isEmail(email)
				|| day == null
				|| month == null 
				|| year == null 
				|| neighbourhood == null 
				|| !DateUtils.isDate(day, month, year)) {
			errorMessage = "Error al editar el perfil";
			return false;
		}
		return true;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void updateUser(Users user) {
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setAddress(address);
		user.setEmail(email);
		user.setBirthDay(day);
		user.setBirthMonth(month);
		user.setBirthYear(year);
		user.setNeighbourhood(neighbourhood);
		if (passwordConfirmation != null && passwordConfirmation.length() > 0) {
			user.setPassword(passwordConfirmation);
			user.setLastPasswordChange(new Date());
		}
	}
	
}
