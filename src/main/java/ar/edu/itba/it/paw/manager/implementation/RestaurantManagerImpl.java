package ar.edu.itba.it.paw.manager.implementation;

import java.util.List;

import ar.edu.itba.it.paw.db.CommentDatabase;
import ar.edu.itba.it.paw.db.DishDatabase;
import ar.edu.itba.it.paw.db.RestaurantDatabase;
import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.model.Comment;
import ar.edu.itba.it.paw.model.Dish;
import ar.edu.itba.it.paw.model.Restaurant;

public class RestaurantManagerImpl implements RestaurantManager {

	private RestaurantDatabase mRestaurantDatabase;
	private DishDatabase mDishDatabase;
	private CommentDatabase mCommentDatabase;
	
	public RestaurantManagerImpl() {
		mRestaurantDatabase = new RestaurantDatabase();
		mDishDatabase = new DishDatabase();
		mCommentDatabase = new CommentDatabase();
	}
	
	public Restaurant getRestaurant(long id) {
		return mRestaurantDatabase.getRestaurant(id);
	}
	
	public List<Restaurant> getRestaurants() {
		return mRestaurantDatabase.getRestaurants();
	}
	
	@Override
	public List<Restaurant> getRestaurantsByManager(long managerId) {
		return mRestaurantDatabase.getManagersRestaurants(managerId);
	}
	
	@Override
	public List<Comment> getRestaurantComments(long restaurantId) {
		return mCommentDatabase.getRestaurantComments(restaurantId);
	}
	
	@Override
	public List<Dish> getRestaurantDishes(long restaurantId) {
		return mDishDatabase.getRestaurantDishes(restaurantId);
	}
	
	@Override
	public void addRestaurant(Restaurant restaurant) {
		mRestaurantDatabase.addRestaurant(restaurant);
	}
	
	public void addComment(Comment comment) {
		mCommentDatabase.addComment(comment);
	}
	
	@Override
	public void addDish(Dish dish) {
		mDishDatabase.addDish(dish);
	}
	
	@Override
	public Dish getDish(long dishId) {
		return mDishDatabase.getDish(dishId);
	}
	
	@Override
	public boolean canUserComment(long userId, long restaurantId) {
		return mCommentDatabase.getUserComment(userId, restaurantId) == null;
	}
	
	@Override
	public void updateRestaurant(Restaurant restaurant) {
		mRestaurantDatabase.updateRestaurant(restaurant);
	}
	
	@Override
	public void deleteRestaurant(long restaurantId) {
		mRestaurantDatabase.deleteRestaurant(restaurantId);
	}
	
	@Override
	public void deleteComment(long userId, long restaurantId) {
		mCommentDatabase.deleteComment(userId, restaurantId);
	}
}
