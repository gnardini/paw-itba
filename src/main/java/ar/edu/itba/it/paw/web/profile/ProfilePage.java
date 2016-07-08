package ar.edu.itba.it.paw.web.profile;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.model.Neighbourhood;
import ar.edu.itba.it.paw.model.Users;
import ar.edu.itba.it.paw.repository.NeighbourhoodRepo;
import ar.edu.itba.it.paw.repository.UserRepo;
import ar.edu.itba.it.paw.util.DateUtils;
import ar.edu.itba.it.paw.util.EmailUtils;
import ar.edu.itba.it.paw.validator.EditProfileValidator;
import ar.edu.itba.it.paw.web.base.BasePage;

public class ProfilePage extends BasePage {

	private transient String email;
	private transient String oldPassword;
	private transient String newPassword;
	private transient String firstName;
	private transient String lastName;
	private transient String address;
	private transient Integer birthDay;
	private transient Integer birthMonth;
	private transient Integer birthYear;
	private transient Neighbourhood neighbourhood;

	@SpringBean
	NeighbourhoodRepo neighbourhoodRepo;

	@SpringBean
	UserRepo userRepo;

	public ProfilePage() {
		if (loggedUser == null) {
			// Shouldn't happen
			setResponsePage(getApplication().getHomePage());
			return;
		}
		add(new Label("pageName", "Perfil"));

		Form<ProfilePage> form = new Form<ProfilePage>("editForm", new CompoundPropertyModel<ProfilePage>(this)) {

			@Override
			protected void onSubmit() {
				Users loggedUser = getUser();
				if (loggedUser == null) {
					// Shouldn't happend
					setResponsePage(getApplication().getHomePage());
					return;
				}
				if (!loggedUser.getPassword().equals(oldPassword)) {
					showError(getString("invalid_password"));
					return;
				}
				if (birthDay == null
						|| birthMonth == null
						|| birthYear == null
						|| !DateUtils.isDate(birthDay, birthMonth, birthYear)) {
					showError(getString("invalid_date"));
					return;
				}
				if (!EmailUtils.isEmail(email)) {
					showError(getString("invalid_email"));
					return;
				}
				EditProfileValidator editProfileValidator = new EditProfileValidator(firstName, lastName, address, email, 
						birthDay, birthMonth, birthYear, oldPassword, newPassword, neighbourhood);
				if (editProfileValidator.isValidUser()) {
					editProfileValidator.updateUser(loggedUser);
					userRepo.updateUser(loggedUser);
					showSuccess(getString("changes_saved"));
				} else {
					showError(getString("error_while_editing_profile"));
				}
			}
		};

		DropDownChoice<Neighbourhood> neighbourhoodDropDown = 
				new DropDownChoice<Neighbourhood>(
						"neighbourhood",
						new PropertyModel<Neighbourhood>(this, "neighbourhood"), 
						neighbourhoodRepo.getAllNeighbourhoods());

		email = loggedUser.getEmail();
		firstName = loggedUser.getFirstName();
		lastName = loggedUser.getLastName();
		address = loggedUser.getAddress();
		neighbourhood = loggedUser.getNeighbourhood();
		birthDay = loggedUser.getBirthDay();
		birthMonth = loggedUser.getBirthMonth();
		birthYear = loggedUser.getBirthYear();
		form.add(new EmailTextField("email").setRequired(false));
		form.add(new PasswordTextField("oldPassword").setRequired(false));
		form.add(new PasswordTextField("newPassword").setRequired(false));
		form.add(new TextField<String>("firstName").setRequired(false));
		form.add(new TextField<String>("lastName").setRequired(false));
		form.add(new TextField<String>("address").setRequired(false));
		form.add(neighbourhoodDropDown.setRequired(false));

		NumberTextField<Integer> birthDayField = new NumberTextField<Integer>("birthDay");
		NumberTextField<Integer> birthMonthField = new NumberTextField<Integer>("birthMonth");
		NumberTextField<Integer> birthYearField = new NumberTextField<Integer>("birthYear");
		form.add(birthDayField.setRequired(false));
		form.add(birthMonthField.setRequired(false));
		form.add(birthYearField.setRequired(false));
		form.add(new Button("saveChangesButton", new ResourceModel("saveChangesButton")));
		add(form);
	}

}
