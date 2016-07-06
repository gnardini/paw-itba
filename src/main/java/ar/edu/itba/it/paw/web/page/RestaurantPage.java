package ar.edu.itba.it.paw.web.page;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.it.paw.model.Neighbourhood;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.web.base.BasePage;

public class RestaurantPage extends BasePage {
	
	public RestaurantPage(IModel<Restaurant> item){
		addLabel(item, "name");
		addLabel(item, "ranking"); // TODO: Check this
		addLabel(item, "menuType");
		addLabel(item, "description");
		addLabel(item, "address");
		/*addLabel(item, "openingHour");
		addLabel(item, "closingHour");*/
		addLabel(item, "deliveryCost");
		addLabel(item, "minCost");		
		Restaurant restaurant = item.getObject();
		if(restaurant.getOpeningHour()==restaurant.getClosingHour()){
			add(new Label("hour", "Todo el dia"));
		}else{
			add(new Label("hour", restaurant.getOpeningHour()+" a "+restaurant.getClosingHour()+" horas"));
		}
		/*ListView<Neighbourhood> neighbourhoods = new ListView<Neighbourhood>("neighbourhoods", restaurant.getNeighbourhoods()) {
			
			@Override
			protected void populateItem(ListItem<Neighbourhood> item) {
				item.add(new Label("neighbourhood", new PropertyModel<String>(item, "name")));
			}
		};
		add(neighbourhoods);*/
	}
	
	public void addLabel(IModel<Restaurant> restaurant, String string){
		add(new Label(string, new PropertyModel<String>(restaurant, string)));
	}

}
