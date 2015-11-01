package ar.edu.itba.it.paw.validator;

import javax.servlet.http.HttpServletRequest;

import ar.edu.itba.it.paw.model.Neighbourhood;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.repository.NeighbourhoodRepo;
import ar.edu.itba.it.paw.util.NumberUtils;

public class RestaurantValidator {

	private HttpServletRequest mRequest;
	private Restaurant mRestaurant;
	private NeighbourhoodRepo mNeighbourhoodRepo;
	private Neighbourhood neighbourhood;
	
	public RestaurantValidator(HttpServletRequest request, NeighbourhoodRepo neighbourhoodRepo) {
		mRequest = request;
		mNeighbourhoodRepo = neighbourhoodRepo;
	}
	
	public boolean isValidRestaurant() {
		String name = mRequest.getParameter("name");
		String address = mRequest.getParameter("address");
		String openingHours = mRequest.getParameter("openingHours");
		String deliveryCost = mRequest.getParameter("deliveryCost");
		String minCost = mRequest.getParameter("minCost");
		String description = mRequest.getParameter("description");
		String menuType = mRequest.getParameter("menuType");
		String neighbourhoodId = mRequest.getParameter("neighbourhoodId");
		if (name == "" 
				|| menuType == ""
				|| address == ""
				|| openingHours == ""
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
		neighbourhood = mNeighbourhoodRepo.getNeighbourhood(Integer.parseInt(neighbourhoodId));
		if (neighbourhood == null) return false;
		mRestaurant = new Restaurant(name, address, openingHours, Integer.valueOf(deliveryCost), Integer.valueOf(minCost), menuType, description);
		return true;
	}
	
	public Restaurant getRestaurant() {
		return mRestaurant;
	}
	
	public Neighbourhood getNeighbourhood() {
		return neighbourhood;
	}
}
