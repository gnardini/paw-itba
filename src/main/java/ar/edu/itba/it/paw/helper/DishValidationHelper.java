package ar.edu.itba.it.paw.helper;

import javax.servlet.http.HttpServletRequest;

import ar.edu.itba.it.paw.model.Dish;
import ar.edu.itba.it.paw.model.Dish.Type;
import ar.edu.itba.it.paw.util.NumberUtils;

public class DishValidationHelper {
	
	private HttpServletRequest mRequest;
	private Dish mDish;
	
	public DishValidationHelper(HttpServletRequest request) {
		mRequest = request;
	}

	public boolean isValidDish() {
		String name = mRequest.getParameter("name");
		String description = mRequest.getParameter("description");
		String price = mRequest.getParameter("price");
		String menu = mRequest.getParameter("menu");
		if (name == "" 
				|| description == ""
				|| price == ""
				|| !NumberUtils.isInteger(price)
				|| menu == "")
			return false;
		Type type = getType(menu);
		mDish = new Dish(Long.valueOf(mRequest.getParameter("restaurantId")), name, description, Integer.valueOf(price), type);
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
