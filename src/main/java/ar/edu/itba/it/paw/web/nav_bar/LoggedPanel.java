package ar.edu.itba.it.paw.web.nav_bar;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import ar.edu.itba.it.paw.manager.implementation.WicketSessionManager;

public class LoggedPanel extends Panel {

	public LoggedPanel(String panelId) {
		super(panelId);
		
		add(new Link<Void>("logout") {
			@Override
			public void onClick() {
				WicketSessionManager wicketSessionManager = WicketSessionManager.get();
				wicketSessionManager.logout();
				setResponsePage(getPage());
			}
		});
	}
	
}
