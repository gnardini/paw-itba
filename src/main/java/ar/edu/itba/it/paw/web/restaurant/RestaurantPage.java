package ar.edu.itba.it.paw.web.restaurant;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
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

import ar.edu.itba.it.paw.model.Comment;
import ar.edu.itba.it.paw.model.Dish;
import ar.edu.itba.it.paw.model.Dish.Type;
import ar.edu.itba.it.paw.model.Neighbourhood;
import ar.edu.itba.it.paw.model.OrderDetail;
import ar.edu.itba.it.paw.model.Orders;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.model.Users;
import ar.edu.itba.it.paw.model.Users.Role;
import ar.edu.itba.it.paw.repository.CommentRepo;
import ar.edu.itba.it.paw.repository.NeighbourhoodRepo;
import ar.edu.itba.it.paw.repository.OrderDetailRepo;
import ar.edu.itba.it.paw.repository.OrderRepo;
import ar.edu.itba.it.paw.repository.RestaurantRepo;
import ar.edu.itba.it.paw.util.Parameter;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.login.LoginPage;

public class RestaurantPage extends BasePage {

	private transient Neighbourhood newNeighbourhood;
	private transient Neighbourhood oldNeighbourhood;

	private transient Integer newCommentScore;
	private transient String newCommentComment;

	private List<DishPanel> dishPanels;
	private boolean closed;

	@SpringBean
	NeighbourhoodRepo neighbourhoodRepo;

	@SpringBean
	RestaurantRepo restaurantRepo;

	@SpringBean
	OrderRepo orderRepo;

	@SpringBean
	OrderDetailRepo orderDetailRepo;

	@SpringBean
	CommentRepo commentRepo;

	public RestaurantPage(Restaurant restaurant) {
		if (restaurant == null) {
			showError(getString("invalid_restaurant"));
			return;
		}
		if (restaurant.getClosedDate().compareTo(new Date()) > 0) {
			closed = true;
		}
		PrettyTime prettyTime = new PrettyTime(new Locale("es"));
		Label closeReasonLabel = new Label("closedReason", getString("close_until")+" "
				+ prettyTime.format(restaurant.getClosedDate()) + " "+getString("because")+" " + restaurant.getClosedReason());
		closeReasonLabel.setVisible(closed);
		add(closeReasonLabel);
		addLabel(restaurant, "name");
		add(new Label("ranking", String.format("%.02f", restaurant.getRanking())));
		addLabel(restaurant, "menuType");
		addLabel(restaurant, "description");
		addLabel(restaurant, "address");
		addLabel(restaurant, "deliveryCost");
		addLabel(restaurant, "minCost");
		if (restaurant.getOpeningHour() == restaurant.getClosingHour()) {
			add(new Label("hour", getString("all_day")));
		} else {
			add(new Label("hour", restaurant.getOpeningHour() + " - " + restaurant.getClosingHour() + " "+getString("hours")));
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

		MarkupContainer newCommentContainer = new WebMarkupContainer("newCommentContainer");
		newCommentContainer.setVisible(isUserLogged() && restaurant.canUserComment(loggedUser));
		add(newCommentContainer);

		addOrderForm(restaurant);
		addCommentsList(restaurant);
		addNewCommentForm(restaurant.getId(), newCommentContainer);

		if (!isUserAdmin()) {
			return;
		}
		addNewNeighbourhoodForm(formContainer, restaurant);
		addRemoveNeighbourhoodForm(formContainer, restaurant);
		addEditRestaurantForm(formContainer, restaurant.getId());
		addDeleteRestaurantForm(formContainer, restaurant.getId());
	}

	private void addNewNeighbourhoodForm(MarkupContainer formContainer, final Restaurant restaurant) {
		List<Neighbourhood> neighbourhoods = neighbourhoodRepo.getAllNeighbourhoods();
		neighbourhoods.removeAll(restaurant.getNeighbourhoods());

		final DropDownChoice<Neighbourhood> neighbourhoodDropDown = new DropDownChoice<Neighbourhood>(
				"newNeighbourhood", new PropertyModel<Neighbourhood>(this, "newNeighbourhood"), neighbourhoods);

		Form<RestaurantPage> form = new Form<RestaurantPage>("addNeighbourhoodForm",
				new CompoundPropertyModel<RestaurantPage>(this)) {

			@Override
			protected void onSubmit() {
				if (newNeighbourhood == null) {
					showError(getString("select_neighbourhood_to_add"));
					return;
				}
				if (restaurant.reachesNeighbourhood(newNeighbourhood)) {
					showError(getString("neighbourhood_already_added"));
					return;
				}
				restaurant.addNeighbourhood(newNeighbourhood);
				restaurantRepo.updateRestaurant(restaurant);
				setResponsePage(getPage());
			}
		};
		neighbourhoodDropDown.setRequired(false);
		form.add(neighbourhoodDropDown);
		form.add(new Button("newNeightbourhoodButton", new ResourceModel("newNeightbourhoodButton")));
		formContainer.add(form);
	}

	private void addRemoveNeighbourhoodForm(MarkupContainer formContainer, final Restaurant restaurant) {
		List<Neighbourhood> neighbourhoods = restaurant.getNeighbourhoods();

		final DropDownChoice<Neighbourhood> neighbourhoodDropDown = new DropDownChoice<Neighbourhood>(
				"oldNeighbourhood", new PropertyModel<Neighbourhood>(this, "oldNeighbourhood"), neighbourhoods);

		Form<RestaurantPage> form = new Form<RestaurantPage>("removeNeighbourhoodForm",
				new CompoundPropertyModel<RestaurantPage>(this)) {

			@Override
			protected void onSubmit() {
				if (oldNeighbourhood == null) {
					showError(getString("select_neighbourhood_to_remove"));
					return;
				}
				if (!restaurant.reachesNeighbourhood(oldNeighbourhood)) {
					showError(getString("neighbourhood_not_added"));
					return;
				}
				if (restaurant.getNeighbourhoods().size() <= 1) {
					showError(getString("cant_delete_neighbourhood"));
					return;
				}
				restaurant.removeNeighbourhood(oldNeighbourhood);
				restaurantRepo.updateRestaurant(restaurant);
				setResponsePage(getPage());
			}
		};
		neighbourhoodDropDown.setRequired(false);
		form.add(neighbourhoodDropDown);
		form.add(new Button("removeNeighbourhoodButton", new ResourceModel("removeNeighbourhoodButton")));
		formContainer.add(form);
	}

	private void addEditRestaurantForm(MarkupContainer formContainer, final int restaurantId) {
		Form<RestaurantPage> form = new Form<RestaurantPage>("editRestaurantForm",
				new CompoundPropertyModel<RestaurantPage>(this)) {

			@Override
			protected void onSubmit() {
				setResponsePage(new EditRestaurantPage(restaurantRepo.getRestaurant(restaurantId)));
			}
		};
		form.add(new Button("editRestaurantButton", new ResourceModel("editRestaurantButton")));
		formContainer.add(form);
	}

	private void addDeleteRestaurantForm(MarkupContainer formContainer, final int restaurantId) {
		Form<RestaurantPage> form = new Form<RestaurantPage>("deleteRestaurantForm",
				new CompoundPropertyModel<RestaurantPage>(this)) {

			@Override
			protected void onSubmit() {
				Restaurant restaurant = restaurantRepo.getRestaurant(restaurantId);
				Users user = getUser();
				if (user.getRole() != Role.ADMIN) {
					showError(getString("only_admin_can_delete_restaurant"));
					setResponsePage(getPage());
				}
				restaurantRepo.deleteRestaurant(restaurant);
				setResponsePage(new RestaurantsPage());
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

				if (closed) {
					showError(getString("restaurant_closed"));
					return;
				}
				Restaurant updatedRestaurant = restaurantRepo.getRestaurant(restaurant.getId());
				Date currentDate = new Date();
				int roh = updatedRestaurant.getOpeningHour();
				int rch = updatedRestaurant.getClosingHour();
				int cdh = currentDate.getHours();
				if((roh<rch && (roh>cdh || cdh>rch)) ||
					(roh>rch && (roh>cdh && cdh>rch))){
					showError(getString("restaurant_closed_by_schedule"));
					return;
				}
				
				if (!isUserLogged()) {
					setResponsePage(new LoginPage());
					return;
				}
				
				Users user = getUser();

				if (!user.isEnabled()) {
					showError(getString("user_not_enable"));
					return;
				}

				Orders order = new Orders(user, updatedRestaurant, new Date());
				int totalPrice = 0;
				for (DishPanel dishPanel : dishPanels) {
					int dishCount = dishPanel.getDishCount();
					if (dishCount > 0) {
						Dish dish = dishPanel.getDish();
						// TODO validate
						order.addDetail(dish.getName(), dish.getPrice(), dishCount);
						totalPrice += dish.getPrice() * dishCount;
					} else if (dishCount == -1) {
						showError(getString("invalid_dish_amount"));
						setResponsePage(getPage());
						return;
					}
				}
				if (totalPrice < updatedRestaurant.getMinCost()) {
					showError(getString("restaurant_min_cost_is")+" "+updatedRestaurant.getMinCost());
					setResponsePage(getPage());
					return;
				}
				totalPrice += updatedRestaurant.getDeliveryCost();
				for (OrderDetail detail : order.getDetails()) {
					orderDetailRepo.storeOrderDetail(detail);
				}
				order.setPrice(totalPrice);
				order.setOnDependants();

				orderRepo.addOrder(order);
				restaurantRepo.updateRestaurant(updatedRestaurant);
				userRepo.updateUser(user);
				showSuccess(getString("successful_order"));
				setResponsePage(getPage());
			}
		};

		Map<Type, List<Dish>> dishMap = new HashMap<>();
		for (Type type : Type.values()) {
			dishMap.put(type, new LinkedList<Dish>());
		}
		List<Dish> dishes = restaurant.getDishes();
		for (Dish dish : dishes) {
			dishMap.get(dish.getType()).add(dish);
		}
		dishLists.add(createDishList("orderEntries", dishMap.get(Type.ENTRY)));
		dishLists.add(createDishList("orderMain", dishMap.get(Type.MAIN)));
		dishLists.add(createDishList("orderDesserts", dishMap.get(Type.DESSERT)));
		dishLists.add(createDishList("orderDrinks", dishMap.get(Type.DRINK)));

		for (ListView<Dish> dishListView : dishLists) {
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

	private void addCommentsList(final Restaurant restaurant) {
		ListView<Comment> commentsListView = new ListView<Comment>("commentsList", restaurant.getComments()) {
			@Override
			protected void populateItem(final ListItem<Comment> item) {
				Comment comment = item.getModelObject();
				item.add(new Label("commentRating", "Calificacion: " + comment.getRating()));
				item.add(new Label("commentUsername", comment.getUserName()));
				item.add(new Label("commentText", comment.getText()));

				MarkupContainer deleteCommentContainer = new WebMarkupContainer("deleteCommentContainer");
				deleteCommentContainer.setVisible(isUserLogged() && isUserAdmin());
				item.add(deleteCommentContainer);

				final Users userComment = item.getModelObject().getUser();

				deleteCommentContainer.add(new Link<Void>("deleteCommentButton") {
					@Override
					public void onClick() {
						if (!isUserAdmin()) {
							setResponsePage(getPage());
							return;
						}
						Restaurant updatedRestaurant = restaurantRepo.getRestaurant(restaurant.getId());
						if (userComment != null) {
							updatedRestaurant.deleteUserComment(userComment);
							restaurantRepo.updateRestaurant(updatedRestaurant);
							showSuccess(getString("successful_comment_delete"));
						} else {
							showError(getString("couldnt_delete_comment"));
						}
						setResponsePage(getPage());
					}
				});
			}
		};
		add(commentsListView);
	}

	private void addNewCommentForm(final int restaurantId, MarkupContainer newCommentContainer) {
		Form<RestaurantPage> form = new Form<RestaurantPage>("newCommentForm",
				new CompoundPropertyModel<RestaurantPage>(this)) {

			@Override
			protected void onSubmit() {
				Restaurant updatedRestaurant = restaurantRepo.getRestaurant(restaurantId);
				Users user = getUser();

				if (!updatedRestaurant.canUserComment(user)) {
					showError(getString("not_enable_to_comment"));
					return;
				}
				if (newCommentScore == null || newCommentScore < 1 || newCommentScore > 5) {
					showError(getString("invalid_ranking"));
					return;
				}
				if (newCommentComment == null || newCommentComment.equals("")) {
					showError(getString("invalid_comment"));
					return;
				}
				Comment comment = new Comment(user, updatedRestaurant, newCommentScore, newCommentComment);

				commentRepo.addComment(comment);
				updatedRestaurant.addComment(comment);
				restaurantRepo.updateRestaurant(updatedRestaurant);
				RestaurantPage restaurantPage = new RestaurantPage(updatedRestaurant);
				restaurantPage.showSuccess(getString("thank_you_for_your_comment"));
				setResponsePage(restaurantPage);
			}
		};

		form.add(new NumberTextField<Integer>("newCommentScore").setRequired(false));
		form.add(new TextField<String>("newCommentComment").setRequired(false));
		form.add(new Button("newCommentButton", new ResourceModel("newCommentButton")));

		newCommentContainer.add(form);
	}

	public void addLabel(Restaurant restaurant, String string) {
		add(new Label(string, new PropertyModel<String>(restaurant, string)));
	}

}
