package ar.edu.itba.it.paw.web.control_panel;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.model.Dish;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.model.RestaurantNeighbourhoodOrderCount;
import ar.edu.itba.it.paw.repository.DishRepo;
import ar.edu.itba.it.paw.repository.RestaurantRepo;
import ar.edu.itba.it.paw.util.DateUtils;
import ar.edu.itba.it.paw.util.Parameter;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.validator.DishValidationHelper;

public class ManagerPanelPage extends BasePage {

	private transient Restaurant restaurant;
	private transient String dishName;
	private transient String dishDescription;
	private transient Float dishPrice;
	private transient Dish.Type menuType;
	private transient Integer closeDay;
	private transient Integer closeMonth;
	private transient Integer closeYear;
	private transient String closeReason;
	
	// Search restaurant orders
	private ListView<RestaurantNeighbourhoodOrderCount> restaurantOrdersListView;
	private transient Integer fromDay;
	private transient Integer fromMonth;
	private transient Integer fromYear;
	private transient Integer toDay;
	private transient Integer toMonth;
	private transient Integer toYear;
	
	@SpringBean
	DishRepo dishRepo;
	
	@SpringBean
	RestaurantRepo restaurantRepo;
	
	public ManagerPanelPage() {
		add(new Label("pageName", getString("control_panel")));
		
		setupSearchRestaurantOrders();
		setupRestaurantOrders();

		Form<ManagerPanelPage> addDishForm = new Form<ManagerPanelPage>("addDishForm",
				new CompoundPropertyModel<ManagerPanelPage>(this)) {
			@Override
			protected void onSubmit() {
				
				DishValidationHelper validator = new DishValidationHelper(dishName,dishDescription,dishPrice,menuType,restaurant);
				if (!validator.isValidDish()) {
					showError(getString("invalid_dish"));
				} else if (!loggedUser.isManagerOf(restaurant)) {
					showError(getString("not_manager"));
				} else {
					Dish dish = validator.getDish();
					dishRepo.addDish(dish);
					Restaurant fetchedRestaurant = restaurantRepo.getRestaurant(restaurant.getId());
					fetchedRestaurant.addDish(dish);
					restaurantRepo.updateRestaurant(fetchedRestaurant);
					showSuccess(getString("new_dish_success"));
				}
			}
		};
		DropDownChoice<Restaurant> addDishRestaurantDropDown = 
	            new DropDownChoice<Restaurant>("addDishRestaurant", 
	                    new PropertyModel<Restaurant>(this, "restaurant"),
	                    loggedUser.getRestaurants());
		addDishRestaurantDropDown.setRequired(false);
		addDishForm.add(addDishRestaurantDropDown);
		addDishForm.add(new TextField<String>("dishName").setRequired(true));
		addDishForm.add(new TextField<String>("dishDescription").setRequired(true));
		addDishForm.add(new NumberTextField<Float>("dishPrice").setRequired(true));
		addDishForm.add(new Button("addDishButton", new ResourceModel("addDishButton")));
		DropDownChoice<Dish.Type> menuTypeDropDown = 
	            new DropDownChoice<Dish.Type>("menuType", 
	                    new PropertyModel<Dish.Type>(this, "menuType"),
	                    Arrays.asList(Dish.Type.values()));
		addDishForm.add(menuTypeDropDown.setRequired(true));
		add(addDishForm);
		

		Form<ManagerPanelPage> closeRestaurantForm = new Form<ManagerPanelPage>("closeRestaurantForm",
				new CompoundPropertyModel<ManagerPanelPage>(this)){
			@Override
			protected void onSubmit() {
				if(closeDay==null || closeMonth==null || closeYear==null || closeReason==null || closeReason==""){
					showError(getString("close_restaurant_failed"));
					return;
				}
				if(!DateUtils.isDate(closeDay, closeMonth, closeYear)){
					showError(getString("invalid_date"));
				}
				if(closeReason.length()>Parameter.closeReasonStringLenght){
					showError(getString("closing_reason_too_long"));
					return;
				}
				Restaurant fetchedRestaurant = restaurantRepo.getRestaurant(restaurant.getId());
				fetchedRestaurant.setClosedDate(new Date(closeYear-1900, closeMonth-1, closeDay));
				fetchedRestaurant.setClosedReason(closeReason);
				restaurantRepo.updateRestaurant(fetchedRestaurant);
				showSuccess(getString("restaurant_closing_success"));
			}
		};
		
		DropDownChoice<Restaurant> closeRestaurantRestaurantDropDown = 
	            new DropDownChoice<Restaurant>("closeRestaurantRestaurant", 
	                    new PropertyModel<Restaurant>(this, "restaurant"),
	                    loggedUser.getRestaurants());
		closeRestaurantRestaurantDropDown.setRequired(false);
		closeRestaurantForm.add(closeRestaurantRestaurantDropDown);
		
		closeRestaurantForm.add(new NumberTextField<Float>("closeDay").setRequired(false));
		closeRestaurantForm.add(new NumberTextField<Float>("closeMonth").setRequired(false));
		closeRestaurantForm.add(new NumberTextField<Float>("closeYear").setRequired(false));
		closeRestaurantForm.add(new TextField<String>("closeReason").setRequired(false));
		closeRestaurantForm.add(new Button("closeRestaurantButton", new ResourceModel("closeRestaurantButton")));
		add(closeRestaurantForm);
	}
	
	private void setupSearchRestaurantOrders() {
		Form<ManagerPanelPage> form = new Form<ManagerPanelPage>("restaurantOrdersForm",
				new CompoundPropertyModel<ManagerPanelPage>(this)) {

			@Override
			protected void onSubmit() {
				if (fromDay == null
						|| fromMonth == null
						|| fromYear == null
						|| !DateUtils.isDate(fromDay, fromMonth, fromYear)) {
					showError(getString("invalid_from_date"));
					return;
				}
				if (toDay == null
						|| toMonth == null
						|| toYear == null
						|| !DateUtils.isDate(toDay, toMonth, toYear)) {
					showError(getString("invalid_to_date"));
					return;
				}
				Date fromDate = new Date(fromYear - 1900, fromMonth - 1, fromDay);
				Date toDate = new Date(toYear - 1900, toMonth - 1, toDay);
				
				populateRestaurantOrdersByNeighbourhood(
						getUser().getRestaurantOrdersByNeighbourhoodInInterval(fromDate, toDate));
			}
		};
		
		form.add(new NumberTextField<Integer>("fromDay").setRequired(false));
		form.add(new NumberTextField<Integer>("fromMonth").setRequired(false));
		form.add(new NumberTextField<Integer>("fromYear").setRequired(false));
		form.add(new NumberTextField<Integer>("toDay").setRequired(false));
		form.add(new NumberTextField<Integer>("toMonth").setRequired(false));
		form.add(new NumberTextField<Integer>("toYear").setRequired(false));
		form.add(new Button("searchOrdersButton", new ResourceModel("searchOrdersButton")));
		add(form);
	}
	
	private void setupRestaurantOrders() {
		List<RestaurantNeighbourhoodOrderCount> restaurantEntries = new LinkedList<>();
		restaurantOrdersListView = 
				new ListView<RestaurantNeighbourhoodOrderCount>("restaurantOrders", restaurantEntries) {
			@Override
			protected void populateItem(final ListItem<RestaurantNeighbourhoodOrderCount> item) {
				final RestaurantNeighbourhoodOrderCount restaurantNeighbourhoodOrderCount = item.getModelObject();
				item.add(new Label("restaurantName",restaurantNeighbourhoodOrderCount.getRestaurant().getName()));
				item.add(new Label("neighbourhoodName", restaurantNeighbourhoodOrderCount.getNeighbourhood().getName()));
				item.add(new Label("orderDay", restaurantNeighbourhoodOrderCount.getDate()));
				item.add(new Label("ordersCount", String.valueOf(restaurantNeighbourhoodOrderCount.getOrderCount())));
				item.add(new Link<Void>("ordersDetail") {
					@Override
					public void onClick() {
						Date fromDate = new Date(fromYear - 1900, fromMonth - 1, fromDay);
						Date toDate = new Date(toYear - 1900, toMonth - 1, toDay);
						setResponsePage(new ManagerReportDetailPage(restaurantNeighbourhoodOrderCount, fromDate, toDate));
					}
				});
			}
		};
		restaurantOrdersListView.setVisible(false);
		add(restaurantOrdersListView);
	}
	
	private void populateRestaurantOrdersByNeighbourhood(List<RestaurantNeighbourhoodOrderCount> ordersList) {
		restaurantOrdersListView.setList(ordersList);
		restaurantOrdersListView.setVisible(true);
	}
	
}
