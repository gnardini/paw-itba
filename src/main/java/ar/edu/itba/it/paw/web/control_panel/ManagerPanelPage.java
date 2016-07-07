package ar.edu.itba.it.paw.web.control_panel;

import java.util.Arrays;
import java.util.Date;

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
import ar.edu.itba.it.paw.util.DateUtils;
import ar.edu.itba.it.paw.util.Parameter;
import ar.edu.itba.it.paw.validator.DishValidationHelper;
import ar.edu.itba.it.paw.web.base.BasePage;

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
	
	@SpringBean
	DishRepo dishRepo;
	
	@SpringBean
	RestaurantRepo restaurantRepo;
	
	public ManagerPanelPage() {
		add(new Label("pageName", "Panel de Control"));
		Form<ManagerPanelPage> addDishForm = new Form<ManagerPanelPage>("addDishForm",
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
					showError("No se puede cerrar el restoran sin fecha de reapertura o razon");
					return;
				}
				if(!DateUtils.isDate(closeDay, closeMonth, closeYear)){
					showError("Fecha inválida");
				}
				if(closeReason.length()>Parameter.closeReasonStringLenght){
					showError("Razón de cierre muy larga");
					return;
				}
				Restaurant fetchedRestaurant = restaurantRepo.getRestaurant(restaurant.getId());
				fetchedRestaurant.setClosedDate(new Date(closeYear-1900, closeMonth-1, closeDay));
				fetchedRestaurant.setClosedReason(closeReason);
				restaurantRepo.updateRestaurant(fetchedRestaurant);
				showSuccess("Restoran cerrado éxitosamente");
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
	
}
