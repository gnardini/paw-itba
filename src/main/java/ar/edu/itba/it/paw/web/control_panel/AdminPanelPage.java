package ar.edu.itba.it.paw.web.control_panel;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

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
import org.ocpsoft.prettytime.PrettyTime;

import ar.edu.itba.it.paw.model.Neighbourhood;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.model.RestaurantOrderCount;
import ar.edu.itba.it.paw.model.Users;
import ar.edu.itba.it.paw.model.Users.Role;
import ar.edu.itba.it.paw.repository.NeighbourhoodRepo;
import ar.edu.itba.it.paw.repository.RestaurantRepo;
import ar.edu.itba.it.paw.repository.UserRepo;
import ar.edu.itba.it.paw.util.DateUtils;
import ar.edu.itba.it.paw.validator.RestaurantValidator;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.restaurant.RestaurantsPage;

public class AdminPanelPage extends BasePage {

	// Assign Manager
	private transient Users assignManagerManager;
	private transient Restaurant assignManagerRestaurant;
	
	// New Manager
	private transient Users newManagerSelected;
	
	// New Restaurant
	private transient String newRestaurantName;
	private transient String newRestaurantDescription;
	private transient String newRestaurantMenuType;
	private transient String newRestaurantAddress;
	private transient Integer newRestaurantOpeningHour;
	private transient Integer newRestaurantClosingHour;
	private transient Integer newRestaurantDeliveryCost;
	private transient Integer newRestaurantMinCost;
	private transient Neighbourhood newRestaurantNeighbourhood;
	
	// Search restaurant orders
	private ListView<RestaurantOrderCount> restaurantOrdersListView;
	private transient Integer fromDay;
	private transient Integer fromMonth;
	private transient Integer fromYear;
	private transient Integer toDay;
	private transient Integer toMonth;
	private transient Integer toYear;
	
	@SpringBean
	UserRepo userRepo;
	
	@SpringBean
	RestaurantRepo restaurantRepo;
	
	@SpringBean
	NeighbourhoodRepo neighbourhoodRepo;
	
	public AdminPanelPage() {
		setupAssignManagerForm();
		setupNewManagerForm();
		setupNewRestaurantForm();
		setupRegisteredUsersList();
		setupSearchRestaurantOrders();
		setupRestaurantOrders();
	}
	
	private void setupAssignManagerForm() {
		Form<AdminPanelPage> form = new Form<AdminPanelPage>("assignManagerForm",
				new CompoundPropertyModel<AdminPanelPage>(this)) {

			@Override
			protected void onSubmit() {
				Users user = getUser();
				if (user.getRole() != Role.ADMIN) {
					// Shouldn't happen.
					setResponsePage(new RestaurantsPage());
					return;
				}
				
				if (assignManagerManager == null || assignManagerRestaurant == null) {
					showError("Debes seleccionar un manager y un restoran a asignar");
					return;
				}
				
				Users assignManagerManager = 
						userRepo.getUser(AdminPanelPage.this.assignManagerManager.getId());
				Restaurant assignManagerRestaurant = 
						restaurantRepo.getRestaurant(AdminPanelPage.this.assignManagerRestaurant.getId());
				
				if (assignManagerManager == null || assignManagerRestaurant == null) {
					showError("Debes seleccionar un manager y un restoran para ser asignado.");
					return;
				}
				if (assignManagerManager.getRole() == Role.MANAGER) {
					if (assignManagerManager.assignRestaurant(assignManagerRestaurant)) {
						userRepo.updateUser(assignManagerManager);
						restaurantRepo.updateRestaurant(assignManagerRestaurant);
						AdminPanelPage page = new AdminPanelPage();
						page.showSuccess(String.format("Se asignó a %s al restoran %s", assignManagerManager, assignManagerRestaurant));
						setResponsePage(page);
					} else {
						showError(String.format("%s ya es gerente de %s", assignManagerManager, assignManagerRestaurant));
					}
				} else {
					// Shouldn't happen.
					showError("No se pudo completar la operación");
					return;
				}
			}
		};
		DropDownChoice<Users> managersDropDown = 
	            new DropDownChoice<Users>("assignManagerManager",
	            		new PropertyModel<Users>(this, "assignManagerManager"),
	                    userRepo.getUsers(Role.MANAGER));
		
		DropDownChoice<Restaurant> restaurantsDropDown = 
	            new DropDownChoice<Restaurant>("assignManagerRestaurant",
	            		new PropertyModel<Restaurant>(this, "assignManagerRestaurant"),
	                    restaurantRepo.getRestaurants());
		
		form.add(managersDropDown);
		form.add(restaurantsDropDown);
		form.add(new Button("assignManagerButton", new ResourceModel("assignManagerButton")));
		add(form);
	}
	
	private void setupNewManagerForm() {
		Form<AdminPanelPage> form = new Form<AdminPanelPage>("newManagerForm",
				new CompoundPropertyModel<AdminPanelPage>(this)) {

			@Override
			protected void onSubmit() {
				Users user = getUser();
				if (user.getRole() != Role.ADMIN) {
					// Shouldn't happen.
					setResponsePage(new RestaurantsPage());
					return;
				}
				
				if (newManagerSelected == null) {
					showError("Debes seleccionar un usuario para hacerlo gerente");
					return;
				}
				
				Users newManagerSelected = 
						userRepo.getUser(AdminPanelPage.this.newManagerSelected.getId());
				
				if (newManagerSelected == null) {
					// Shouldn't happen.
					showError("Gerente no encontrado. Disculpamos las molestias.");
					setResponsePage(new AdminPanelPage());
					return;
				}
				if (newManagerSelected.makeManager()) {
					userRepo.updateUser(newManagerSelected);
					AdminPanelPage page = new AdminPanelPage();
					page.showSuccess("Nuevo gerente agregado");
					setResponsePage(page);
				} else {
					showError("No se pudo crear un nuevo gerente");
				}
			}
		};
		DropDownChoice<Users> usersDropDown = 
	            new DropDownChoice<Users>("newManagerSelected",
	            		new PropertyModel<Users>(this, "newManagerSelected"),
	                    userRepo.getUsers(Role.NORMAL));
		
		form.add(usersDropDown);
		form.add(new Button("newManagerButton", new ResourceModel("newManagerButton")));
		add(form);
	}
	
	private void setupNewRestaurantForm() {
		Form<AdminPanelPage> form = new Form<AdminPanelPage>("newRestaurantForm",
				new CompoundPropertyModel<AdminPanelPage>(this)) {

			@Override
			protected void onSubmit() {
				Users user = getUser();
				if (user.getRole() != Role.ADMIN) {
					// Shouldn't happen.
					setResponsePage(new RestaurantsPage());
					return;
				}
				RestaurantValidator restaurantValidator = new RestaurantValidator(neighbourhoodRepo, newRestaurantName, 
						newRestaurantAddress, newRestaurantOpeningHour, newRestaurantClosingHour, 
						newRestaurantDeliveryCost, newRestaurantMinCost, newRestaurantMenuType, 
						newRestaurantDescription, newRestaurantNeighbourhood);
				if (restaurantValidator.isValidRestaurant()) {
					restaurantRepo.storeRestaurant(restaurantValidator.getRestaurant());
					AdminPanelPage page = new AdminPanelPage();
					page.showSuccess("Nuevo restoran agregado con éxito");
					setResponsePage(page);
				} else {
					showError("Parámetros inválidos, no se pudo crear el restoran");
				}
			}
		};
		DropDownChoice<Neighbourhood> neighbourhoodDropDown = 
	            new DropDownChoice<Neighbourhood>("newRestaurantNeighbourhood",
	            		new PropertyModel<Neighbourhood>(this, "newRestaurantNeighbourhood"),
	                    neighbourhoodRepo.getAllNeighbourhoods());
		
		form.add(new TextField<String>("newRestaurantName"));
		form.add(new TextField<String>("newRestaurantDescription"));
		form.add(new TextField<String>("newRestaurantAddress"));
		form.add(new TextField<String>("newRestaurantMenuType"));
		form.add(new NumberTextField<Integer>("newRestaurantOpeningHour"));
		form.add(new NumberTextField<Integer>("newRestaurantClosingHour"));
		form.add(new NumberTextField<Integer>("newRestaurantDeliveryCost"));
		form.add(new NumberTextField<Integer>("newRestaurantMinCost"));
		form.add(neighbourhoodDropDown);
		form.add(new Button("newRestaurantSubmit", new ResourceModel("newRestaurantSubmit")));
		add(form);
	}
	
	private void setupRegisteredUsersList() {
		List<Users> usersList = userRepo.getUsers(Role.NORMAL);
		usersList.addAll(userRepo.getUsers(Role.MANAGER));
		ListView<Users> registeredUsers = new ListView<Users>("registeredUsers", usersList) {
			@Override
			protected void populateItem(final ListItem<Users> item) {
				Users user = item.getModelObject();
				item.add(new Label("userName", user.getFullName()));
				item.add(new Label("userRole", user.getRole().getRoleName()));
				PrettyTime prettyTime = new PrettyTime(new Locale("es"));
				
				// TODO Set actual last access
				item.add(new Label("userLastAccess", prettyTime.format(user.getLastLogin())));

				Link<Void> toggleUserLink = new Link<Void>("toggleUserEnabled") {
					public void onClick() {
						
					}
				};
				// TODO Set real value
				boolean isUserEnabled = true;
				toggleUserLink.add(new Label("userEnabledText", isUserEnabled ? "Habilitado" : "Deshabilitado"));
				item.add(toggleUserLink);
			}
		};
		add(registeredUsers);
	}
	
	private void setupSearchRestaurantOrders() {
		Form<AdminPanelPage> form = new Form<AdminPanelPage>("restaurantOrdersForm",
				new CompoundPropertyModel<AdminPanelPage>(this)) {

			@Override
			protected void onSubmit() {
				if (fromDay == null
						|| fromMonth == null
						|| fromYear == null
						|| !DateUtils.isDate(fromDay, fromMonth, fromYear)) {
					showError("La fecha de inicio de búsqueda de restorans es inválida");
					return;
				}
				if (toDay == null
						|| toMonth == null
						|| toYear == null
						|| !DateUtils.isDate(toDay, toMonth, toYear)) {
					showError("La fecha de finalización de búsqueda de restorans es inválida");
					return;
				}
				Date fromDate = new Date(fromYear - 1900, fromMonth - 1, fromDay);
				Date toDate = new Date(toYear - 1900, toMonth - 1, toDay);
				populateRestaurantOrders(restaurantRepo.getRestaurantOrdersInInterval(fromDate, toDate));
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
		List<RestaurantOrderCount> restaurantEntries = new LinkedList<>();
		restaurantOrdersListView = 
				new ListView<RestaurantOrderCount>("restaurantOrders", restaurantEntries) {
			@Override
			protected void populateItem(final ListItem<RestaurantOrderCount> item) {
				RestaurantOrderCount restaurantOrderCount = item.getModelObject();
				item.add(new Label("restaurantName",restaurantOrderCount.getRestaurant().getName()));
				item.add(new Label("ordersCount", String.valueOf(restaurantOrderCount.getOrderCount())));
			}
		};
		restaurantOrdersListView.setVisible(false);
		add(restaurantOrdersListView);
	}
	
	private void populateRestaurantOrders(List<RestaurantOrderCount> restaurantWithOrderCount) {
		restaurantOrdersListView.setList(restaurantWithOrderCount);
		restaurantOrdersListView.setVisible(true);
		System.out.println(restaurantWithOrderCount.size());
	}
	
}
