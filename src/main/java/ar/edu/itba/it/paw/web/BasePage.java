package ar.edu.itba.it.paw.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import ar.edu.itba.it.paw.manager.implementation.WicketSessionManager;

public class BasePage extends WebPage {

	@Override
	protected void onInitialize() {
		super.onInitialize();
		WicketSessionManager session = WicketSessionManager.get();

		Panel login = null;
		if (session.isLogged()) {
			//login = new LoggedPanel("headerPanel");
		} else {
			//login = new LogInPanel("headerPanel");
		}
		
		//add(login);

		add(new Link<Void>("home") {
			@Override
			public void onClick() {
				setResponsePage(getApplication().getHomePage());
			}
		});

	}
	
}
