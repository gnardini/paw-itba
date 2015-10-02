package ar.edu.itba.it.paw.helper;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.implementation.RestaurantManagerImpl;
import ar.edu.itba.it.paw.model.Dish;
import ar.edu.itba.it.paw.model.Order;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.util.NumberUtils;
import ar.edu.itba.it.paw.util.Parameter;

public class OrderValidationHelper {

	private HttpServletRequest mRequest;
	private RestaurantManager mRestaurantManager;
	private Order mOrder;
	private Long userId;
	
	public OrderValidationHelper(HttpServletRequest request, long userId) {
		mRequest = request;
		mRestaurantManager = new RestaurantManagerImpl();
		this.userId=userId;
	}
	
	public Boolean isValid() {
		if(mRequest.getParameter(Parameter.RESTAURANT_ID)==null || !NumberUtils.isNumber(mRequest.getParameter(Parameter.RESTAURANT_ID)))
			return false;
		mOrder = new Order(
				userId,
				Long.valueOf(mRequest.getParameter(Parameter.RESTAURANT_ID)),
				new Date());
		Restaurant restaurant = mRestaurantManager.getRestaurant(Long.valueOf(mRequest.getParameter(Parameter.RESTAURANT_ID)));
		if(restaurant==null)
			return false;
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
					dish = mRestaurantManager.getDishFromRestaurant((Long.valueOf(dishId)), restaurant.getId());
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
