package ar.edu.itba.it.paw.web.orders;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.springframework.core.annotation.Order;

import ar.edu.itba.it.paw.model.Orders;
import ar.edu.itba.it.paw.web.base.BasePage;

public class MyOrdersPage extends BasePage {

	public MyOrdersPage() {
		add(new Label("pageName", "Mis Pedidos"));
		ListView<Orders> orderListView = new ListView<Orders>("orderList", loggedUser.getOrders()) {
			@Override
			protected void populateItem(ListItem<Orders> item) {
				Orders order = item.getModelObject();
				item.add(new Label("restaurantName", order.getRestaurant().getName()));
				item.add(new Label("price", String.format("%.02f", order.getPrice())));
				item.add(new Label("orderDate", order.getOrderDate()));
				item.add(new Label("delivered", (order.isDelivered() ? "Entregado" : "No entregado")));
			/*	WebMarkupContainer deliveredButtonContainer = new WebMarkupContainer("deliveredButtonContainer");
				
				item.add(deliveredButtonContainer);*/
			}
		};
		add(orderListView);
	}
}
