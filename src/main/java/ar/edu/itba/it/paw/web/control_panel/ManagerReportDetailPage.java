package ar.edu.itba.it.paw.web.control_panel;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import ar.edu.itba.it.paw.model.Orders;
import ar.edu.itba.it.paw.model.OrdersInHour;
import ar.edu.itba.it.paw.model.RestaurantNeighbourhoodOrderCount;
import ar.edu.itba.it.paw.web.base.BasePage;

public class ManagerReportDetailPage extends BasePage {
	
	public ManagerReportDetailPage(RestaurantNeighbourhoodOrderCount restaurantNeighbourhoodOrderCount, Date fromDate, Date toDate) {
		add(new Link<Void>("controlPanel") {
			@Override
			public void onClick() {
				setResponsePage(new ManagerPanelPage());
			}
		});
		add(new Label("restaurantName", restaurantNeighbourhoodOrderCount.getRestaurant().getName()));
		add(new Label("neighbourhoodName", restaurantNeighbourhoodOrderCount.getNeighbourhood().getName()));
		
		List<Orders> allOrders = restaurantNeighbourhoodOrderCount
				.getRestaurant()
				.getOrdersInNeighbourhood(restaurantNeighbourhoodOrderCount.getNeighbourhood());
		
		Map<Integer, OrdersInHour> ordersPerHour = new HashMap<>();
		for (Orders order: allOrders) {
			if(order.getMade().compareTo(fromDate)<0 ||
					order.getMade().compareTo(toDate)>0){
				continue;
			}
			int hour = order.getMade().getHours();
			OrdersInHour orderForHour = ordersPerHour.get(hour);
			if (orderForHour == null) {
				orderForHour = new OrdersInHour(hour);
			}
			orderForHour.addOrder();
			ordersPerHour.put(hour, orderForHour);
		}
	/*	List<OrdersInHour> sortedList = new LinkedList<OrdersInHour>(ordersPerHour.values());
		Collections.sort(sortedList, new Comparator<OrdersInHour>() {
			public int compare(OrdersInHour o1, OrdersInHour o2) {
				return Integer.compare(o1.getHour(), o2.getHour());
			}
		});*/
		add(new ListView<OrdersInHour>("restaurantOrders", new LinkedList<OrdersInHour>(ordersPerHour.values())) {
			@Override
			protected void populateItem(final ListItem<OrdersInHour> item) {
				final OrdersInHour ordersInHour = item.getModelObject();
				item.add(new Label("hour", String.valueOf(ordersInHour.getHour())));
				item.add(new Label("ordersCount", String.valueOf(ordersInHour.getOrdersCount())));
			}
		});
	}

}
