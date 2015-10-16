package ar.edu.itba.it.paw.helper;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ar.edu.itba.it.paw.model.Dish;
import ar.edu.itba.it.paw.model.Order;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.util.NumberUtils;
import ar.edu.itba.it.paw.util.Parameter;

public class OrderValidationHelper {

	private HttpServletRequest mRequest;
	private Order mOrder;
	private User user;
	private Restaurant restaurant;
	
	public OrderValidationHelper(HttpServletRequest request, User user, Restaurant restaurant) {
		mRequest = request;
		this.user = user;
		this.restaurant = restaurant;
	}
	
	public Boolean isValid() {
		mOrder = new Order(user,restaurant, new Date());
		Map<String, String[]> map = mRequest.getParameterMap();
		String[] amount;
		int dishCount;
		Dish dish;
		long totalAmount=0;
		for (String dishId: map.keySet()) {
			if (NumberUtils.isNumber(dishId)) {
				amount = map.get(dishId);
				if (amount.length == 1 && NumberUtils.isNumber(amount[0]) && amount[0].length() <= 3) {
					dishCount = Integer.valueOf(amount[0]);
					dish = restaurant.getDish(Long.valueOf(dishId));
					if (dishCount > 0){
						if(dish==null)
							return false;
						mOrder.addDetail(dish.getName(), dishCount, dish.getPrice());
						totalAmount+=(dish.getPrice()*dishCount);
					}
				}
			}
		}
		if(totalAmount<restaurant.getMinCost())
			return null;
		return mOrder.getDetails().size() != 0;
	}
	
	public Order getOrder() {
		return mOrder;
	}
}
