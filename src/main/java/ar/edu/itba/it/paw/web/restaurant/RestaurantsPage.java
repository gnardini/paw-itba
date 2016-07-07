package ar.edu.itba.it.paw.web.restaurant;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.repository.RestaurantRepo;
import ar.edu.itba.it.paw.util.Parameter;
import ar.edu.itba.it.paw.web.base.BasePage;

@SuppressWarnings("serial")
public class RestaurantsPage extends BasePage {

	@SpringBean
	RestaurantRepo restaurantRepo;

	public RestaurantsPage() {
		this(new Date());
	}
	
	public RestaurantsPage(Date lastLogin) {
		List<Restaurant> restaurantsList = restaurantRepo.getRestaurants();
		List<Restaurant> topRestaurantsList = restaurantRepo.getTopRestaurants();

		ListView<Restaurant> topRestaurants = new ListView<Restaurant>(Parameter.TOP_RESTAURANTS, topRestaurantsList) {
			@Override
			protected void populateItem(final ListItem<Restaurant> item) {
				item.add(new Label("name", new PropertyModel<String>(item.getModel(), "name")));
				item.add(new Label("menuType", new PropertyModel<String>(item.getModel(), "menuType")));
				item.add(new Label("description", new PropertyModel<String>(item.getModel(), "description")));

				item.add(new Link<Void>("open") {
					public void onClick() {
						setResponsePage(new RestaurantPage(restaurantRepo.getRestaurant(item.getModelObject().getId())));
					}
				});
			}
		};
		add(topRestaurants);

		ListView<Restaurant> allRestaurants = new ListView<Restaurant>(Parameter.ALL_RESTAURANTS, restaurantsList) {
			@Override
			protected void populateItem(final ListItem<Restaurant> item) {
				item.add(new Label("name", new PropertyModel<String>(item.getModel(), "name")));
				item.add(new Label("menuType", new PropertyModel<String>(item.getModel(), "menuType")));
				item.add(new Label("description", new PropertyModel<String>(item.getModel(), "description")));

				item.add(new Link<Void>("open") {
					public void onClick() {
						setResponsePage(new RestaurantPage(restaurantRepo.getRestaurant(item.getModelObject().getId())));
					}
				});
			}
		};
		add(allRestaurants);
		
		List<Restaurant> newRestaurants = new LinkedList<>();
		if (isUserLogged()) {
			newRestaurants = restaurantRepo.getNewRestaurants(lastLogin, loggedUser.getNeighbourhood());
		}
		
		MarkupContainer newRestaurantsContainer = new WebMarkupContainer("newRestaurants");
		newRestaurantsContainer.setVisible(!newRestaurants.isEmpty());
		add(newRestaurantsContainer);
		
		ListView<Restaurant> newRestaurantsListView = new ListView<Restaurant>("newRestaurantsList", newRestaurants) {
			@Override
			protected void populateItem(final ListItem<Restaurant> item) {
				item.add(new Label("name", new PropertyModel<String>(item.getModel(), "name")));
				item.add(new Label("menuType", new PropertyModel<String>(item.getModel(), "menuType")));
				item.add(new Label("description", new PropertyModel<String>(item.getModel(), "description")));

				item.add(new Link<Void>("open") {
					public void onClick() {
						setResponsePage(new RestaurantPage(restaurantRepo.getRestaurant(item.getModelObject().getId())));
					}
				});
			}
		};
		newRestaurantsContainer.add(newRestaurantsListView);
	}
}
