package ar.edu.itba.it.paw.web.page;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.model.Dish;
import ar.edu.itba.it.paw.model.Dish.Type;
import ar.edu.itba.it.paw.model.Neighbourhood;
import ar.edu.itba.it.paw.model.Orders;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.repository.NeighbourhoodRepo;
import ar.edu.itba.it.paw.repository.RestaurantRepo;
import ar.edu.itba.it.paw.util.Parameter;
import ar.edu.itba.it.paw.web.base.BasePage;

public class RestaurantPage extends BasePage {

	private transient Neighbourhood newNeighbourhood;
	private transient Neighbourhood oldNeighbourhood;
	
	private List<DishPanel> dishPanels;
	
	@SpringBean
	NeighbourhoodRepo neighbourhoodRepo;
	
	@SpringBean
	RestaurantRepo restaurantRepo;
	
	public RestaurantPage(IModel<Restaurant> item) {
		addLabel(item, "name");
		addLabel(item, "ranking"); // TODO: Check this
		addLabel(item, "menuType");
		addLabel(item, "description");
		addLabel(item, "address");
		addLabel(item, "deliveryCost");
		addLabel(item, "minCost");
		Restaurant restaurant = item.getObject();
		if (restaurant.getOpeningHour() == restaurant.getClosingHour()) {
			add(new Label("hour", "Todo el dia"));
		} else {
			add(new Label("hour", restaurant.getOpeningHour() + " a " + restaurant.getClosingHour() + " horas"));
		}

		ListView<Neighbourhood> neighbourhoods = new ListView<Neighbourhood>("neighbourhoods",
				restaurant.getNeighbourhoods()) {

			@Override
			protected void populateItem(ListItem<Neighbourhood> item) {
				item.add(new Label("neighbourhood", item.getModelObject().getName()));
				// item.add(new Label("neighbourhood", new
				// PropertyModel<String>(item, "name")));
			}
		};
		add(neighbourhoods);
		
		MarkupContainer formContainer = new WebMarkupContainer("adminContainer");
		formContainer.setVisible(isUserAdmin());
		add(formContainer);
		
		addOrderForm(item.getObject());
		
		if (!isUserAdmin()) {
			return;
		}
		addNewNeighbourhoodForm(formContainer, item);
		addRemoveNeighbourhoodForm(formContainer, item);
		addEditRestaurantForm(formContainer, item.getObject());
		addDeleteRestaurantForm(formContainer, item.getObject());
	}
	
	private void addNewNeighbourhoodForm(MarkupContainer formContainer, final IModel<Restaurant> item) {
		final Restaurant restaurant = item.getObject();
		List<Neighbourhood> neighbourhoods = neighbourhoodRepo.getAllNeighbourhoods();
		neighbourhoods.removeAll(restaurant.getNeighbourhoods());
		
		final DropDownChoice<Neighbourhood> neighbourhoodDropDown = 
	            new DropDownChoice<Neighbourhood>("newNeighbourhood",
	            		new PropertyModel<Neighbourhood>(this, "newNeighbourhood"),
	            		neighbourhoods);
		
		Form<RestaurantPage> form = new Form<RestaurantPage>("addNeighbourhoodForm",
				new CompoundPropertyModel<RestaurantPage>(this)) {

			@Override
			protected void onSubmit() {
				if (restaurant.reachesNeighbourhood(newNeighbourhood)) {
					showMessage("El barrio ya se encuentra agregado", Parameter.ERROR);
					return;
				}
				restaurant.addNeighbourhood(newNeighbourhood);
				restaurantRepo.updateRestaurant(restaurant);
				setResponsePage(getPage());
			}
		};
		neighbourhoodDropDown.setRequired(true);
		form.add(neighbourhoodDropDown);
		form.add(new Button("newNeightbourhoodButton", new ResourceModel("newNeightbourhoodButton")));
		formContainer.add(form);
	}
	
	private void addRemoveNeighbourhoodForm(MarkupContainer formContainer, final IModel<Restaurant> item) {
		final Restaurant restaurant = item.getObject();
		List<Neighbourhood> neighbourhoods = restaurant.getNeighbourhoods();
		
		final DropDownChoice<Neighbourhood> neighbourhoodDropDown = 
	            new DropDownChoice<Neighbourhood>("oldNeighbourhood",
	            		new PropertyModel<Neighbourhood>(this, "oldNeighbourhood"),
	            		neighbourhoods);
		
		Form<RestaurantPage> form = new Form<RestaurantPage>("removeNeighbourhoodForm",
				new CompoundPropertyModel<RestaurantPage>(this)) {

			@Override
			protected void onSubmit() {
				if (!restaurant.reachesNeighbourhood(oldNeighbourhood)) {
					showMessage("El barrio no se encuentra agregado", Parameter.ERROR);
					return;
				}
				if (restaurant.getNeighbourhoods().size() <= 1) {
					showMessage("No se puede quitar el restoran (tiene que haber por lo menos uno)", Parameter.ERROR);
					return;
				}
				restaurant.removeNeighbourhood(oldNeighbourhood);
				restaurantRepo.updateRestaurant(restaurant);
				setResponsePage(getPage());
			}
		};
		neighbourhoodDropDown.setRequired(true);
		form.add(neighbourhoodDropDown);
		form.add(new Button("removeNeighbourhoodButton", new ResourceModel("removeNeighbourhoodButton")));
		formContainer.add(form);
	}
	
	private void addEditRestaurantForm(MarkupContainer formContainer, final Restaurant restaurant) {
		Form<RestaurantPage> form = new Form<RestaurantPage>("editRestaurantForm",
				new CompoundPropertyModel<RestaurantPage>(this)) {

			@Override
			protected void onSubmit() {
				//setResponsePage(new EditRestaurantPage());
			}
		};
		form.add(new Button("editRestaurantButton", new ResourceModel("editRestaurantButton")));
		formContainer.add(form);
	}
	
	private void addDeleteRestaurantForm(MarkupContainer formContainer, final Restaurant restaurant) {
		Form<RestaurantPage> form = new Form<RestaurantPage>("deleteRestaurantForm",
				new CompoundPropertyModel<RestaurantPage>(this)) {

			@Override
			protected void onSubmit() {
				//setResponsePage(new DeleteRestaurantPage());
			}
		};
		form.add(new Button("deleteRestaurantButton", new ResourceModel("deleteRestaurantButton")));
		formContainer.add(form);
	}

	private void addOrderForm(final Restaurant restaurant) {
		dishPanels = new LinkedList<>();
		final List<ListView<Dish>> dishLists = new LinkedList<>();
		Form<RestaurantPage> form = new Form<RestaurantPage>("orderForm",
				new CompoundPropertyModel<RestaurantPage>(this)) {

			@Override
			protected void onSubmit() {
				Orders order = new Orders(getUser(), restaurant, new Date());
				for (DishPanel dishPanel: dishPanels) {
					if (dishPanel.getDishCount() > 0) {
						Dish dish = dishPanel.getDish();
						// TODO validate
						order.addDetail(dish.getName(), dish.getPrice(), dishPanel.getDishCount());
					}
				}
			}
		};
		
		Map<Type, List<Dish>> dishMap = new HashMap<>();
		for (Type type: Type.values()) {
			dishMap.put(type, new LinkedList<Dish>());
		}
		List<Dish> dishes = restaurant.getDishes();
		for (Dish dish: dishes) {
			dishMap.get(dish.getType()).add(dish); 
		}
		dishLists.add(createDishList("orderEntries", dishMap.get(Type.ENTRY)));
		dishLists.add(createDishList("orderMain", dishMap.get(Type.MAIN)));
		dishLists.add(createDishList("orderDesserts", dishMap.get(Type.DESSERT)));
		dishLists.add(createDishList("orderDrinks", dishMap.get(Type.DRINK)));
		
		for (ListView<Dish> dishListView: dishLists) {
			form.add(dishListView);
		}
		
		form.add(new Button("orderButton", new ResourceModel("orderButton")));
		add(form);
	}
	
	private ListView<Dish> createDishList(String id, List<Dish> dishes) {
		return new ListView<Dish>(id, dishes) {
			@Override
			protected void populateItem(final ListItem<Dish> item) {
				DishPanel dishPanel = new DishPanel("dishPanel", item);
				dishPanels.add(dishPanel);
				item.add(dishPanel);
			}
		};
	}
	
	public void addLabel(IModel<Restaurant> restaurant, String string) {
		add(new Label(string, new PropertyModel<String>(restaurant, string)));
	}

}
