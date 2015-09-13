package ar.edu.itba.it.paw.util;

import ar.edu.itba.it.paw.model.Dish.Type;

public class DishTypeUtils {

	public static Type getDishTypeFromString(String type) {
		System.out.println(type);
		if ("entry".equals(type)) return Type.ENTRY;
		else if ("main".equals(type)) return Type.MAIN;
		else if ("dessert".equals(type)) return Type.DESSERT;
		else return Type.DRINK;
	}
}
