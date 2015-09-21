package ar.edu.itba.it.paw.util;

import ar.edu.itba.it.paw.model.Dish.Type;

public class DishTypeUtils {

	public static Type getDishTypeFromString(String type) {
		if ("entry".equals(type)) return Type.ENTRY;
		else if ("main".equals(type)) return Type.MAIN;
		else if ("dessert".equals(type)) return Type.DESSERT;
		else if ("drink".equals(type)) return Type.DRINK;
		else return null;
	}
}
