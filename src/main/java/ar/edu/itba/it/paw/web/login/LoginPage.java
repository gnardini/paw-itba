package ar.edu.itba.it.paw.web.login;

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

import ar.edu.itba.it.paw.manager.implementation.WicketSessionManager;
import ar.edu.itba.it.paw.model.Neighbourhood;
import ar.edu.itba.it.paw.model.Users;
import ar.edu.itba.it.paw.model.Users.Role;
import ar.edu.itba.it.paw.repository.NeighbourhoodRepo;
import ar.edu.itba.it.paw.repository.UserRepo;
import ar.edu.itba.it.paw.util.Parameter;
import ar.edu.itba.it.paw.validator.DateValidator;
import ar.edu.itba.it.paw.web.base.BasePage;

public class LoginPage extends BasePage {

	// Login
	private transient String loginEmail;
	private transient String loginPassword;
	
	// Register
	private transient String email;
	private transient String password;
	private transient String firstName;
	private transient String lastName;
	private transient String address;
	private transient Integer birthDay;
	private transient Integer birthMonth;
	private transient Integer birthYear;
	private transient Neighbourhood neighbourhood;
	
	@SpringBean
	UserRepo userRepo;

	@SpringBean
	NeighbourhoodRepo neighbourhoodRepo;
	
	public LoginPage() {
		setupLoginForm();
		setupRegisterForm();
	}
	
	private void setupLoginForm() {
		Form<LoginPage> form = new Form<LoginPage>("logInForm",
				new CompoundPropertyModel<LoginPage>(this)) {

			@Override
			protected void onSubmit() {
				WicketSessionManager session = WicketSessionManager.get();

				if (session.login(loginEmail, loginPassword)) {
					setResponsePage(getApplication().getHomePage());
				} else {
					showMessage("Credenciales incorrectas", Parameter.ERROR);
				}
			}
		};
		
		form.add(new EmailTextField("loginEmail").setRequired(true));
		form.add(new PasswordTextField("loginPassword"));
		form.add(new Button("loginButton", new ResourceModel("loginButton")));
		add(form);
	}
	
	private void setupRegisterForm()  {
		Form<LoginPage> form = new Form<LoginPage>("signUpForm",
				new CompoundPropertyModel<LoginPage>(this)) {

			@Override
			protected void onSubmit() {
				WicketSessionManager session = WicketSessionManager.get();
				
				Users user = new Users(firstName, lastName, address, email, 
						birthDay, birthMonth, birthYear, Role.NORMAL, 
						password, neighbourhood);
				if (session.signup(user)) {
					setResponsePage(getApplication().getHomePage());
				} else {
					error("Error al registrarse");
				}
			}
		};
		
		DropDownChoice<Neighbourhood> neighbourhoodDropDown = 
	            new DropDownChoice<Neighbourhood>("neighbourhood", 
	                    new PropertyModel<Neighbourhood>(this, "neighbourhood"),
	                    neighbourhoodRepo.getAllNeighbourhoods());
		neighbourhoodDropDown.setRequired(true);
		form.add(new EmailTextField("email").setRequired(true));
		form.add(new PasswordTextField("password").setRequired(true));
		form.add(new TextField<String>("firstName").setRequired(true));
		form.add(new TextField<String>("lastName").setRequired(true));
		form.add(new TextField<String>("address").setRequired(true));
		form.add(neighbourhoodDropDown);
		
		NumberTextField<Integer> birthDayField = new NumberTextField<Integer>("birthDay");
		NumberTextField<Integer> birthMonthField = new NumberTextField<Integer>("birthMonth");
		NumberTextField<Integer> birthYearField = new NumberTextField<Integer>("birthYear");
		form.add(birthDayField.setRequired(true));
		form.add(birthMonthField.setRequired(true));
		form.add(birthYearField.setRequired(true));
		//form.add(new DateValidator(birthDayField, birthMonthField, birthYearField));
		form.add(new Button("signUpButton", new ResourceModel("signUpButton")));
		add(form);
	}
	
}
