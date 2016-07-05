package ar.edu.itba.it.paw.web.nav_bar;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import ar.edu.itba.it.paw.web.login.LoginPage;

public class LoginPanel extends Panel {

	public LoginPanel(String panelId) {
		super(panelId);
		
		add(new Link<Void>("login") {
			@Override
			public void onClick() {
				setResponsePage(new LoginPage());
			}
		});
	}
	
}
