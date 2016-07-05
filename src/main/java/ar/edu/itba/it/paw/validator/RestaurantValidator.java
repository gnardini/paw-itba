package ar.edu.itba.it.paw.validator;

import javax.servlet.http.HttpServletRequest;

import ar.edu.itba.it.paw.model.Neighbourhood;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.repository.hibernate.HibernateNeighbourhoodRepo;
import ar.edu.itba.it.paw.util.NumberUtils;

public class RestaurantValidator {

	private HttpServletRequest mRequest;
	private Restaurant mRestaurant;
	private HibernateNeighbourhoodRepo mNeighbourhoodRepo;
	
	public RestaurantValidator(HttpServletRequest request, HibernateNeighbourhoodRepo neighbourhoodRepo) {
		mRequest = request;
		mNeighbourhoodRepo = neighbourhoodRepo;
	}
	
	public boolean isValidRestaurant() {
		String name = mRequest.getParameter("name");
		String address = mRequest.getParameter("address");
		String openingHour = mRequest.getParameter("openingHour");
		String closingHour = mRequest.getParameter("closingHour");
		String deliveryCost = mRequest.getParameter("deliveryCost");
		String minCost = mRequest.getParameter("minCost");
		String description = mRequest.getParameter("description");
		String menuType = mRequest.getParameter("menuType");
		String neighbourhoodId = mRequest.getParameter("neighbourhoodId");
		if (name == "" 
				|| menuType == ""
				|| address == ""
				|| !NumberUtils.isNumber(openingHour)
				|| !isHour(Integer.valueOf(openingHour))
				|| !NumberUtils.isNumber(closingHour)
				|| !isHour(Integer.valueOf(closingHour))
				|| deliveryCost == ""
				|| deliveryCost.length() > 6
				|| !NumberUtils.isNumber(deliveryCost)
				|| Integer.valueOf(deliveryCost)<0
				|| minCost == ""
				|| minCost.length() > 6
				|| !NumberUtils.isNumber(minCost)
				|| Integer.valueOf(minCost)<0
				|| !NumberUtils.isNumber(neighbourhoodId))
			return false;
		Neighbourhood neighbourhood = mNeighbourhoodRepo.getNeighbourhood(Integer.parseInt(neighbourhoodId));
		if (neighbourhood == null) return false;
		mRestaurant = new Restaurant(name, address, Integer.valueOf(openingHour), Integer.valueOf(closingHour), Integer.valueOf(deliveryCost), Integer.valueOf(minCost), menuType, description, neighbourhood);
		return true;
	}
	
	public Restaurant getRestaurant() {
		return mRestaurant;
	}
	
	private boolean isHour(int hour) {
		return hour >= 0 && hour <= 23;
	}
}
