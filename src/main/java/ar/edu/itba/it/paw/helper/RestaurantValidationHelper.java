package ar.edu.itba.it.paw.helper;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.util.NumberUtils;

public class RestaurantValidationHelper {

	private HttpServletRequest mRequest;
	private Restaurant mRestaurant;
	
	public RestaurantValidationHelper(HttpServletRequest request) {
		mRequest = request;
	}
	
	public boolean isValidRestaurant() {
		
		String name = mRequest.getParameter("name");
		String address = mRequest.getParameter("address");
		String openingHours = mRequest.getParameter("openingHours");
		String deliveryCost = mRequest.getParameter("deliveryCost");
		String minCost = mRequest.getParameter("minCost");
		String description = mRequest.getParameter("description");
		String menuType = mRequest.getParameter("menuType");
		if (name == "" 
				|| menuType == ""
				|| address == ""
				|| openingHours == ""
				|| deliveryCost == ""
				|| deliveryCost.length() > 6
				|| !NumberUtils.isInteger(deliveryCost)
				|| Integer.valueOf(deliveryCost)<0
				|| minCost == ""
				|| minCost.length() > 6
				|| !NumberUtils.isInteger(minCost)
				|| Integer.valueOf(minCost)<0)
			return false;
		mRestaurant = new Restaurant(-1, name, address, openingHours, Integer.valueOf(deliveryCost), Integer.valueOf(minCost), menuType, description);
		return true;
	}
	
	public Restaurant getRestaurant() {
		return mRestaurant;
	}
}
