package ar.edu.itba.it.paw.web.login;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

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
import ar.edu.itba.it.paw.repository.NeighbourhoodRepo;
import ar.edu.itba.it.paw.repository.UserRepo;
import ar.edu.itba.it.paw.validator.SignUpValidator;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.restaurant.RestaurantsPage;

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

				if (loginEmail != null 
						&& loginPassword != null 
						&& session.login(loginEmail, loginPassword)) {
					Users user = session.getUser();
					
					boolean mustChangePassword = false;
					try {
						Properties properties = new Properties();
						InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
						if (inputStream != null) {
							properties.load(inputStream);
						}
						int passwordChangeDaysLimit = Integer.valueOf(properties.getProperty("passwordChangeDaysLimit"));
						long timeSinceLastChange = new Date().getTime() - user.getLastPasswordChange().getTime();
						int daysSinceLastChange = (int) (timeSinceLastChange / (24 * 60 * 60 * 1000));
						mustChangePassword = daysSinceLastChange >= passwordChangeDaysLimit;
					} catch (IOException ioException) {
					}
					if (mustChangePassword) {
						session.softLogout();
						setResponsePage(new ChangePasswordPage(user));
					} else {
						Date lastLogin = user.getLastLogin();
						user.setLastLogin(new Date());
						userRepo.updateUser(user);
						setResponsePage(new RestaurantsPage(lastLogin));
					}
				} else {
					showError("Usuario o contraseña incorrectos");
				}
			}
		};
		
		form.add(new EmailTextField("loginEmail").setRequired(false));
		form.add(new PasswordTextField("loginPassword").setRequired(false));
		form.add(new Button("loginButton", new ResourceModel("loginButton")));
		add(form);
	}
	
	private void setupRegisterForm()  {
		Form<LoginPage> form = new Form<LoginPage>("signUpForm",
				new CompoundPropertyModel<LoginPage>(this)) {

			@Override
			protected void onSubmit() {
				WicketSessionManager session = WicketSessionManager.get();
				SignUpValidator signUpValidator = new SignUpValidator(firstName, lastName, address, email, 
						birthDay, birthMonth, birthYear, loginPassword, neighbourhood);
				if (!signUpValidator.isValidUser()) {
					showError("Datos de registro inválidos");
				} else if (session.signup(signUpValidator.getUser())) {
					setResponsePage(new RestaurantsPage(new Date(0)));
				} else {
					showError("El usuario ya existe");
				}
			}
		};
		
		DropDownChoice<Neighbourhood> neighbourhoodDropDown = 
	            new DropDownChoice<Neighbourhood>("neighbourhood", 
	                    new PropertyModel<Neighbourhood>(this, "neighbourhood"),
	                    neighbourhoodRepo.getAllNeighbourhoods());
		form.add(new EmailTextField("email").setRequired(false));
		form.add(new PasswordTextField("password").setRequired(false));
		form.add(new TextField<String>("firstName").setRequired(false));
		form.add(new TextField<String>("lastName").setRequired(false));
		form.add(new TextField<String>("address").setRequired(false));
		form.add(neighbourhoodDropDown.setRequired(false));
		form.add(new NumberTextField<Integer>("birthDay").setRequired(false));
		form.add(new NumberTextField<Integer>("birthMonth").setRequired(false));
		form.add(new NumberTextField<Integer>("birthYear").setRequired(false));
		form.add(new Button("signUpButton", new ResourceModel("signUpButton")));
		add(form);
	}
	
}
