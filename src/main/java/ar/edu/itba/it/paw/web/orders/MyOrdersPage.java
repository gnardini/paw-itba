package ar.edu.itba.it.paw.web.orders;

import java.util.Locale;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.ocpsoft.prettytime.PrettyTime;

import ar.edu.itba.it.paw.model.OrderDetail;
import ar.edu.itba.it.paw.model.Orders;
import ar.edu.itba.it.paw.repository.OrderRepo;
import ar.edu.itba.it.paw.web.base.BasePage;

public class MyOrdersPage extends BasePage {

	@SpringBean
	OrderRepo orderRepo;

	public MyOrdersPage() {
		if (loggedUser == null) {
			// Shouldn't happen
			setResponsePage(getApplication().getHomePage());
			return;
		}
		add(new Label("pageName", "Mis Pedidos"));
		ListView<Orders> orderListView = new ListView<Orders>("orderList", loggedUser.getOrders()) {
			@Override
			protected void populateItem(ListItem<Orders> item) {
				final Orders order = item.getModelObject();
				item.add(new Label("restaurantName", order.getRestaurant().getName()));
				item.add(new Label("price", String.format("%.02f", order.getPrice())));
				PrettyTime prettyTime = new PrettyTime(new Locale("es"));
				item.add(new Label("orderDate", prettyTime.format(order.getMade())));
				item.add(new Label("delivered", (order.isDelivered() ? "Entregado" : "No entregado")));
				WebMarkupContainer deliveredButtonContainer = new WebMarkupContainer("deliveredButtonContainer");
				deliveredButtonContainer.setVisible(!order.isDelivered());
				deliveredButtonContainer.add(new Link<Void>("deliveredButton") {
					@Override
					public void onClick() {
						order.setDelivered(true);
						orderRepo.updateOrder(order);
					}
				});
				ListView<OrderDetail> orderDetailList = new ListView<OrderDetail>("orderDetailList",
						order.getDetails()) {
					@Override
					protected void populateItem(ListItem<OrderDetail> detailItem) {
						OrderDetail detail = detailItem.getModelObject();
						detailItem.add(new Label(
								"detailName", new PropertyModel<String>(detail, "name")));
						detailItem.add(new Label(
								"detailPrice", new PropertyModel<String>(detail, "price")));
						detailItem.add(new Label(
								"detailAmount", new PropertyModel<String>(detail, "amount")));
					}
				};
				item.add(orderDetailList);
				item.add(deliveredButtonContainer);
			}
		};
		add(orderListView);
	}
}
