package ar.edu.itba.it.paw.web.base;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.manager.implementation.WicketSessionManager;
import ar.edu.itba.it.paw.model.Users;
import ar.edu.itba.it.paw.model.Users.Role;
import ar.edu.itba.it.paw.repository.UserRepo;
import ar.edu.itba.it.paw.util.Parameter;
import ar.edu.itba.it.paw.web.nav_bar.LoggedPanel;
import ar.edu.itba.it.paw.web.nav_bar.LoginPanel;

public class BasePage extends WebPage {

	protected Users loggedUser;
	
	private Label successLabel;
	private Label dangerLabel;
	private Model<String> successModel;
	private Model<String> dangerModel;
	
	@SpringBean
	UserRepo userRepo;
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		WicketSessionManager session = WicketSessionManager.get();
		loggedUser = session.getUser();

		Panel login = null;
		if (session.isLogged()) {
			login = new LoggedPanel("navBarPanel");
		} else {
			login = new LoginPanel("navBarPanel");
		}
		
		add(login);

		add(new Link<Void>("home") {
			@Override
			public void onClick() {
				setResponsePage(getApplication().getHomePage());
			}
		});
		
		successModel = Model.of("");
		successLabel = new Label("successMessage", successModel);
		successLabel.setOutputMarkupId(true);
		successLabel.setVisible(false);
		add(successLabel);

		dangerModel = Model.of("");
		dangerLabel = new Label("dangerMessage", dangerModel);
		dangerLabel.setOutputMarkupId(true);
		dangerLabel.setVisible(false);
		add(dangerLabel);
	}
	
	protected boolean isUserLogged() {
		return loggedUser != null;
	}
	
	protected Users getUser() {
		if (!isUserLogged()) {
			return null;
		}
		return userRepo.getUser(loggedUser.getId());
	}
	
	protected boolean isUserAdmin() {
		Users user = getUser();
		return user != null && user.getRole() == Role.ADMIN;
	}
	
	protected void showMessage(String message, String parameter) {
		boolean isSuccess = parameter.equalsIgnoreCase(Parameter.SUCCESS);
		boolean isError = parameter.equalsIgnoreCase(Parameter.ERROR);
		successLabel.setVisible(isSuccess);
		dangerLabel.setVisible(isError);
		
		if (isSuccess) {
			successModel.setObject(message);
			add(successLabel);
		} else if (isError) {
			dangerModel.setObject(message);
			add(dangerLabel);
		}
	}
	
}
