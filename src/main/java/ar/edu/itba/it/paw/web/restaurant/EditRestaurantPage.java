package ar.edu.itba.it.paw.web.restaurant;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.repository.RestaurantRepo;
import ar.edu.itba.it.paw.web.base.BasePage;

public class EditRestaurantPage extends BasePage {

	private IModel<String> name;
	private IModel<String> address;
	private IModel<Integer> openingHour;
	private IModel<Integer> closingHour;
	private IModel<Integer> deliveryCost;
	private IModel<Integer> minCost;
	private IModel<String> menuType;
	private IModel<String> description;
	
	@SpringBean
	RestaurantRepo restaurantRepo;
	
	public EditRestaurantPage(final Restaurant restaurant) {
		Link<Void> breadcrumb = new Link<Void>("restaurantBreadcrumb") {
			@Override
			public void onClick() {
				setResponsePage(new RestaurantPage(restaurantRepo.getRestaurant(restaurant.getId())));
			}
		};
		breadcrumb.setBody(Model.of(restaurant.getName()));
		add(breadcrumb);
		
		Form<EditRestaurantPage> form = new Form<EditRestaurantPage>("editRestaurantForm",
				new CompoundPropertyModel<EditRestaurantPage>(this)) {

			@Override
			protected void onSubmit() {
				String name = EditRestaurantPage.this.name.getObject();
				String description = EditRestaurantPage.this.description.getObject();
				String address = EditRestaurantPage.this.address.getObject();
				String menuType = EditRestaurantPage.this.menuType.getObject();
				Integer openingHour = EditRestaurantPage.this.openingHour.getObject();
				Integer closingHour = EditRestaurantPage.this.closingHour.getObject();
				Integer minCost = EditRestaurantPage.this.minCost.getObject();
				Integer deliveryCost = EditRestaurantPage.this.deliveryCost.getObject();
				
				if (name == null || name.length() == 0) {
					showError("invalid_restaurant_name");
					return;
				}
				if (description == null || description.length() == 0) {
					showError(getString("invalid_restaurant_description"));
					return;
				}
				if (address == null || address.length() == 0) {
					showError(getString("invalid_restaurant_address"));
					return;
				}
				if (menuType == null || menuType.length() == 0) {
					showError(getString("invalid_menu_type"));
					return;
				}
				if (openingHour == null || !isHour(openingHour)) {
					showError(getString("invalid_opening_hour"));
					return;
				}
				if (closingHour == null || !isHour(closingHour)) {
					showError(getString("invalid_opening_hour"));
					return;
				}
				if (deliveryCost == null || deliveryCost < 0) {
					showError(getString("invalid_delivery_cost"));
					return;
				}
				if (minCost == null || minCost < 0) {
					showError(getString("invalid_min_cost"));
					return;
				}
				Restaurant newRestInfo = new Restaurant(name, address, openingHour, closingHour, deliveryCost, minCost, menuType, description);
				Restaurant editedRestaurant = restaurantRepo.getRestaurant(restaurant.getId());
				editedRestaurant.updateWithData(newRestInfo);
				restaurantRepo.updateRestaurant(editedRestaurant);
				setResponsePage(new RestaurantPage(editedRestaurant));
			}
		};
		
		name = new PropertyModel<String>(restaurant, "name");
		description = new PropertyModel<String>(restaurant, "description");
		address = new PropertyModel<String>(restaurant, "address");
		menuType = new PropertyModel<String>(restaurant, "menuType");
		openingHour = new PropertyModel<Integer>(restaurant, "openingHour");
		closingHour = new PropertyModel<Integer>(restaurant, "closingHour");
		minCost = new PropertyModel<Integer>(restaurant, "minCost");
		deliveryCost = new PropertyModel<Integer>(restaurant, "deliveryCost");
		
		form.add(new TextField<String>("name", name));
		form.add(new TextField<String>("description", description));
		form.add(new TextField<String>("address", address));
		form.add(new TextField<String>("menuType", menuType));
		form.add(new NumberTextField<Integer>("openingHour", openingHour));
		form.add(new NumberTextField<Integer>("closingHour", closingHour));
		form.add(new NumberTextField<Integer>("minCost", minCost));
		form.add(new NumberTextField<Integer>("deliveryCost", deliveryCost));
		form.add(new Button("saveChanges", new ResourceModel("saveChanges")));
		add(form);
	}
	
	private boolean isHour(int hour) {
		return hour >= 0 && hour <= 23;
	}
	
}
