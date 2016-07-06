package ar.edu.itba.it.paw.web.orders;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.repository.OrderRepo;
import ar.edu.itba.it.paw.web.base.BasePage;

public class MyOrdersPage extends BasePage {

	
	public MyOrdersPage(){
		add(new Label("pageName", "Mis Pedidos"));
	}
}
