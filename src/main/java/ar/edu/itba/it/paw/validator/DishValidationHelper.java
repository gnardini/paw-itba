package ar.edu.itba.it.paw.validator;

import ar.edu.itba.it.paw.model.Dish;
import ar.edu.itba.it.paw.model.Dish.Type;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.util.NumberUtils;

public class DishValidationHelper {

	private String name;
	private String description;
	private Float price;
	private Type menu;
	private Restaurant mRestaurant;
	private Dish mDish;

	public DishValidationHelper(String name, String descrition, Float price, Type menu, Restaurant mRestaurant) {
		this.name = name;
		this.description = descrition;
		this.price = price;
		this.menu = menu;
		this.mRestaurant = mRestaurant;
	}

	public boolean isValidDish() {
		if (name == null || name == "" ||
				description == null || description == "" ||
				price == null || price<=0 || menu == null)
			return false;
		mDish = new Dish(mRestaurant, name, description, price, menu);
		return true;
	}

	public Dish getDish() {
		return mDish;
	}
}
