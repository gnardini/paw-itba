package ar.edu.itba.it.paw.helper;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.it.paw.form.EditProfileForm;
import ar.edu.itba.it.paw.form.SignUpForm;
import ar.edu.itba.it.paw.util.DateUtils;
import ar.edu.itba.it.paw.util.EmailUtils;
import ar.edu.itba.it.paw.util.NumberUtils;

@Component
public class EditProfileValidator implements Validator {
	
	@Override
	public void validate(Object o, Errors e) {
		EditProfileForm form = (EditProfileForm) o;
		String firstName = form.getFirstName();
		String lastName = form.getLastName();
		String address = form.getAddress();
		String email = form.getEmail();
		String day = form.getBirthDay();
		String month = form.getBirthMonth();
		String year = form.getBirthYear();
		String newPassword = form.getNewPassword();

		if (firstName.equals("")
				|| lastName.equals("")
				|| address.equals("")
				|| email.equals("")
				|| !EmailUtils.isEmail(email)
				|| !NumberUtils.isNumber(day)
				|| !NumberUtils.isNumber(month)
				|| !NumberUtils.isNumber(year)
				|| !DateUtils.isDate(Integer.valueOf(day), Integer.valueOf(month), Integer.valueOf(year))
				|| !form.passwordsMatch())
			e.reject("Error al editar el perfil");
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return EditProfileForm.class.equals(clazz);
	}
}
