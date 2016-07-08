package ar.edu.itba.it.paw.web.login;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.manager.implementation.WicketSessionManager;
import ar.edu.itba.it.paw.model.Users;
import ar.edu.itba.it.paw.repository.UserRepo;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.restaurant.RestaurantsPage;

public class ChangePasswordPage extends BasePage {

	private transient String oldPassword;
	private transient String newPassword;
	
	@SpringBean 
	UserRepo userRepo;
	
	public ChangePasswordPage(final Users user) {
		try {
			Properties properties = new Properties();
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
			if (inputStream != null) {
				properties.load(inputStream);
			}
			int passwordChangeDaysLimit = Integer.valueOf(properties.getProperty("passwordChangeDaysLimit"));
			add(new Label("passMaxDay", String.format(getString("passMaxDay"), passwordChangeDaysLimit)));
		} catch (IOException ioException) {
			
		}
		Form<ChangePasswordPage> form = new Form<ChangePasswordPage>("changePasswordForm",
				new CompoundPropertyModel<ChangePasswordPage>(this)) {

			@Override
			protected void onSubmit() {
				if (oldPassword == null 
						|| !oldPassword.equals(user.getPassword())) {
					showError(getString("invalid_password"));
					return;
				}
				if (newPassword == null
						|| newPassword.equals(oldPassword)) {
					showError(getString("same_password"));
					return;
				}
				
				WicketSessionManager session = WicketSessionManager.get();
				if (session.login(user.getEmail(), oldPassword)) {
					Users user = session.getUser();
					Date lastLogin = user.getLastLogin();
					user.setPassword(newPassword);
					user.setLastLogin(new Date());
					user.setLastPasswordChange(new Date());
					userRepo.updateUser(user);
					setResponsePage(new RestaurantsPage(lastLogin));
				} else {
					// Shouldn't happen
					setResponsePage(getApplication().getHomePage());
				}
			}
		};
		
		form.add(new PasswordTextField("oldPassword").setRequired(false));
		form.add(new PasswordTextField("newPassword").setRequired(false));
		form.add(new Button("saveButton", new ResourceModel("saveButton")));
		add(form);
	}
	
}
