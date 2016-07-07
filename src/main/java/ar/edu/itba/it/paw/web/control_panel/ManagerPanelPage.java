package ar.edu.itba.it.paw.web.control_panel;

import java.util.Arrays;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.model.Dish;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.repository.DishRepo;
import ar.edu.itba.it.paw.repository.RestaurantRepo;
import ar.edu.itba.it.paw.validator.DishValidationHelper;
import ar.edu.itba.it.paw.web.base.BasePage;

public class ManagerPanelPage extends BasePage {

	private transient Restaurant restaurant;
	private transient String dishName;
	private transient String dishDescription;
	private transient Float dishPrice;
	private transient Dish.Type menuType;
	
	@SpringBean
	DishRepo dishRepo;
	
	@SpringBean
	RestaurantRepo restaurantRepo;
	
	public ManagerPanelPage() {
		add(new Label("pageName", "Panel de Control"));
		Form<ManagerPanelPage> form = new Form<ManagerPanelPage>("addDishForm",
				new CompoundPropertyModel<ManagerPanelPage>(this)) {
			@Override
			protected void onSubmit() {
				
				DishValidationHelper validator = new DishValidationHelper(dishName,dishDescription,dishPrice,menuType,restaurant);
				if (!validator.isValidDish()) {
					showError("Plato invalido");
				} else if (!loggedUser.isManagerOf(restaurant)) {
					showError("No eres gerente del restaurant");
				} else {
					Dish dish = validator.getDish();
					dishRepo.addDish(dish);
					Restaurant fetchedRestaurant = restaurantRepo.getRestaurant(restaurant.getId());
					fetchedRestaurant.addDish(dish);
					restaurantRepo.updateRestaurant(fetchedRestaurant);
					showSuccess("Nuevo plato agregado con exito");
				}
			}
		};
		
		DropDownChoice<Restaurant> restaurantDropDown = 
	            new DropDownChoice<Restaurant>("restaurant", 
	                    new PropertyModel<Restaurant>(this, "restaurant"),
	                    loggedUser.getRestaurants());
		restaurantDropDown.setRequired(false);
		form.add(restaurantDropDown);

		form.add(new TextField<String>("dishName").setRequired(true));
		form.add(new TextField<String>("dishDescription").setRequired(true));
		form.add(new NumberTextField<Float>("dishPrice").setRequired(true));
		form.add(new Button("addDishButton", new ResourceModel("addDishButton")));
		DropDownChoice<Dish.Type> menuTypeDropDown = 
	            new DropDownChoice<Dish.Type>("menuType", 
	                    new PropertyModel<Dish.Type>(this, "menuType"),
	                    Arrays.asList(Dish.Type.values()));
		form.add(menuTypeDropDown.setRequired(true));
		add(form);
	}
	
}
