package ar.edu.itba.it.paw.helper;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ar.edu.itba.it.paw.model.Order;
import ar.edu.itba.it.paw.util.NumberUtils;

public class OrderValidationHelper {

	private HttpServletRequest mRequest;
	private Order mOrder;
	
	public OrderValidationHelper(HttpServletRequest request) {
		mRequest = request;
		mOrder = new Order();
	}
	
	public boolean isValid() {
		Map<String, String[]> map = mRequest.getParameterMap();
		String[] amount;
		int dishCount;
		for (String dishId: map.keySet()) {
			if (NumberUtils.isInteger(dishId)) {
				amount = map.get(dishId);
				if (amount.length == 1 && NumberUtils.isInteger(amount[0])) {
					dishCount = Integer.valueOf(amount[0]);
					if (dishCount > 0) mOrder.addDish(Integer.valueOf(dishId), dishCount);
				}
			}
		}
		return mOrder.getDishes().size() != 0;
	}
	
	public Order getOrder() {
		return mOrder;
	}
}
