package ar.edu.itba.it.paw.manager.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.model.Comment;
import ar.edu.itba.it.paw.model.Dish;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.repository.CommentRepo;
import ar.edu.itba.it.paw.repository.DishRepo;
import ar.edu.itba.it.paw.repository.RestaurantRepo;

@Service
public class RestaurantManagerImpl implements RestaurantManager {

	private RestaurantRepo mRestaurantRepo;
	private DishRepo mDishRepo;
	private CommentRepo mCommentRepo;
	
	@Autowired
	public RestaurantManagerImpl(RestaurantRepo restaurantRepo, DishRepo dishRepo, CommentRepo commentRepo) {
		mRestaurantRepo = restaurantRepo;
		mDishRepo = dishRepo;
		mCommentRepo = commentRepo;
	}
	
	public Restaurant getRestaurant(long id) {
		Restaurant restaurant = mRestaurantRepo.getRestaurant(id);
		return restaurant;
	}
	
	public List<Restaurant> getRestaurants() {
		return mRestaurantRepo.getRestaurants();
	}
	
	@Override
	public void addRestaurant(Restaurant restaurant) {
		mRestaurantRepo.storeRestaurant(restaurant);
	}
	
	public void addComment(Comment comment) {
		mCommentRepo.addComment(comment);
	}
	
	@Override
	public void addDish(Dish dish) {
		mDishRepo.addDish(dish);
	}
	
	@Override
	public void deleteRestaurant(Restaurant restaurant) {
		mRestaurantRepo.deleteRestaurant(restaurant);
	}
	
	@Override
	public void deleteComment(Comment comment) {
		mCommentRepo.deleteComment(comment);
	}

}
