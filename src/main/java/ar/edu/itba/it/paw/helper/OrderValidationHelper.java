package ar.edu.itba.it.paw.helper;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ar.edu.itba.it.paw.manager.RestaurantManager;
import ar.edu.itba.it.paw.manager.implementation.RestaurantManagerImpl;
import ar.edu.itba.it.paw.model.Dish;
import ar.edu.itba.it.paw.model.Order;
import ar.edu.itba.it.paw.util.NumberUtils;

public class OrderValidationHelper {

	private HttpServletRequest mRequest;
	private RestaurantManager mRestaurantManager;
	private Order mOrder;
	
	public OrderValidationHelper(HttpServletRequest request, long userId) {
		mRequest = request;
		mRestaurantManager = new RestaurantManagerImpl();
		mOrder = new Order(
				userId,
				Long.valueOf(mRequest.getParameter("restaurant_id")),
				new Date());
	}
	
	public boolean isValid() {
		Map<String, String[]> map = mRequest.getParameterMap();
		String[] amount;
		int dishCount;
		Dish dish;
		for (String dishId: map.keySet()) {
			if (NumberUtils.isInteger(dishId)) {
				amount = map.get(dishId);
				if (amount.length == 1 && NumberUtils.isInteger(amount[0])) {
					dishCount = Integer.valueOf(amount[0]);
					dish = mRestaurantManager.getDish(Long.valueOf(dishId));
					if (dishCount > 0) mOrder.addDetail(dish.getName(), dish.getPrice(), dishCount);
				}
			}
		}
		return mOrder.getDetails().size() != 0;
	}
	
	public Order getOrder() {
		return mOrder;
	}
}
