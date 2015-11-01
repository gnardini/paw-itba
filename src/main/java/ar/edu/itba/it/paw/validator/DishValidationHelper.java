package ar.edu.itba.it.paw.validator;

import javax.servlet.http.HttpServletRequest;

import ar.edu.itba.it.paw.model.Dish;
import ar.edu.itba.it.paw.model.Dish.Type;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.util.NumberUtils;
import ar.edu.itba.it.paw.util.Parameter;

public class DishValidationHelper {
	
	private HttpServletRequest mRequest;
	private Dish mDish;
	private Restaurant mRestaurant;
	
	public DishValidationHelper(HttpServletRequest request, Restaurant restaurant) {
		mRequest = request;
		mRestaurant = restaurant;
	}

	public boolean isValidDish() {
		String name = mRequest.getParameter(Parameter.NAME);
		String description = mRequest.getParameter(Parameter.DESCRIPTION);
		String price = mRequest.getParameter(Parameter.PRICE);
		String menu = mRequest.getParameter(Parameter.MENU);
		if (name == "" 
				|| description == ""
				|| price == ""
				|| price.length()>7
				|| !NumberUtils.isNumber(price)
				|| menu == "")
			return false;
		Type type = getType(menu);
		if(type==null)
			return false;
		int numPrice = Integer.valueOf(price);
		mDish = new Dish(mRestaurant, name, description, numPrice, type);
		return true;
	}
	
	public Dish getDish() {
		return mDish;
	}
	
	private Type getType(String menu) {
		switch (menu) {
		case "Entrada":
			return Type.ENTRY;
		case "Principal":
			return Type.MAIN;
		case "Postre":
			return Type.DESSERT;
		case "Bebida":
			return Type.DRINK;
		}
		return null;
	}
}