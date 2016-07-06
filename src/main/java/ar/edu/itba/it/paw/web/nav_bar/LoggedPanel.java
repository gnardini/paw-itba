package ar.edu.itba.it.paw.web.nav_bar;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import ar.edu.itba.it.paw.manager.implementation.WicketSessionManager;
import ar.edu.itba.it.paw.web.orders.MyOrdersPage;
import ar.edu.itba.it.paw.web.profile.ProfilePage;

public class LoggedPanel extends Panel {

	public LoggedPanel(String panelId) {
		super(panelId);
		add(new Link<Void>("myOrders"){
			@Override
			public void onClick() {
				setResponsePage(new MyOrdersPage());
			}
		});
		add(new Link<Void>("profile"){
			@Override
			public void onClick() {
				setResponsePage(new ProfilePage());
			}
		});
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
