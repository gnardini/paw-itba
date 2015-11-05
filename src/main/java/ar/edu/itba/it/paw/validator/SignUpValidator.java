package ar.edu.itba.it.paw.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.it.paw.form.SignUpForm;
import ar.edu.itba.it.paw.util.DateUtils;
import ar.edu.itba.it.paw.util.EmailUtils;
import ar.edu.itba.it.paw.util.NumberUtils;

@Component
public class SignUpValidator implements Validator {
	
	@Override
	public void validate(Object o, Errors e) {
		SignUpForm form = (SignUpForm) o;
		String firstName = form.getFirstName();
		String lastName = form.getLastName();
		String address = form.getAddress();
		String email = form.getEmail();
		String day = form.getBirthDay();
		String month = form.getBirthMonth();
		String year = form.getBirthYear();
		String password = form.getPassword();
		String neighbourhoodId = form.getNeighbourhoodId();
				
		if (firstName == "" 
				|| lastName == ""
				|| address == ""
				|| email == ""
				|| !EmailUtils.isEmail(email)
				|| !NumberUtils.isNumber(day)
				|| !NumberUtils.isNumber(month)
				|| !NumberUtils.isNumber(year)
				|| !NumberUtils.isNumber(neighbourhoodId)
				|| !DateUtils.isDate(Integer.valueOf(day), Integer.valueOf(month), Integer.valueOf(year))
				|| password == "")
			e.reject("registrationError");
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return SignUpForm.class.equals(clazz);
	}
}
