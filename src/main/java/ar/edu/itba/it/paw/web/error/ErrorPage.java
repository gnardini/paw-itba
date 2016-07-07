package ar.edu.itba.it.paw.web.error;

import org.apache.wicket.markup.html.link.Link;

import ar.edu.itba.it.paw.web.base.BasePage;

public class ErrorPage extends BasePage {

	public ErrorPage() {
		add(new Link("goHome") {
			
			@Override
			public void onClick() {
				setResponsePage(getApplication().getHomePage());
			}
		});
	}
	
	
}
