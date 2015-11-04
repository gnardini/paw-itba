package ar.edu.itba.it.paw.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.it.paw.form.EditProfileForm;
import ar.edu.itba.it.paw.util.DateUtils;
import ar.edu.itba.it.paw.util.EmailUtils;
import ar.edu.itba.it.paw.util.NumberUtils;

@Component
public class EditProfileValidator implements Validator {
	
	public static final int ERROR_PASSWORDS = 0;
	public static final int ERROR_INVALID_DATA = 1;
	
	private int errorType;
	
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
		String neighbourhoodId = form.getNeighbourhoodId();

		if (!form.passwordsMatch()) {
			errorType = ERROR_PASSWORDS;
			e.reject("Contrasena incorrecta");
		} else if (firstName.equals("")
				|| lastName.equals("")
				|| address.equals("")
				|| email.equals("")
				|| !EmailUtils.isEmail(email)
				|| !NumberUtils.isNumber(day)
				|| !NumberUtils.isNumber(month)
				|| !NumberUtils.isNumber(year)
				|| !NumberUtils.isNumber(neighbourhoodId)
				|| !DateUtils.isDate(Integer.valueOf(day), Integer.valueOf(month), Integer.valueOf(year))) {
			errorType = ERROR_INVALID_DATA;
			e.reject("Error al editar el perfil");
		}
	}

	public int getErrorType() {
		return errorType;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return EditProfileForm.class.equals(clazz);
	}
}
