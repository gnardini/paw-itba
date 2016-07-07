package ar.edu.itba.it.paw.validator;

import ar.edu.itba.it.paw.model.Neighbourhood;
import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.repository.NeighbourhoodRepo;

public class RestaurantValidator {

	private Restaurant restaurant;
	private NeighbourhoodRepo neighbourhoodRepo;

	private String name;
	private String address;
	private Integer openingHour;
	private Integer closingHour;
	private Integer deliveryCost;
	private Integer minCost;
	private String menuType;
	private String description;
	private Neighbourhood neighbourhood;
	
	public RestaurantValidator(NeighbourhoodRepo neighbourhoodRepo, String name, String address, Integer openingHour,
			Integer closingHour, Integer deliveryCost, Integer minCost, String menuType, String description,
			Neighbourhood neighbourhood) {
		this.neighbourhoodRepo = neighbourhoodRepo;
		this.name = name;
		this.address = address;
		this.openingHour = openingHour;
		this.closingHour = closingHour;
		this.deliveryCost = deliveryCost;
		this.minCost = minCost;
		this.menuType = menuType;
		this.description = description;
		this.neighbourhood = neighbourhood;
	}

	public boolean isValidRestaurant() {
		if (name == "" 
				|| menuType == ""
				|| address == ""
				|| openingHour == null
				|| !isHour(openingHour)
				|| closingHour == null
				|| !isHour(closingHour)
				|| deliveryCost == null
				|| deliveryCost < 0
				|| minCost == null
				|| minCost < 0
				|| neighbourhood == null)
			return false;
		restaurant = new Restaurant(name, address, openingHour, closingHour, deliveryCost, minCost, menuType, 
				description, neighbourhood);
		return true;
	}
	
	public Restaurant getRestaurant() {
		return restaurant;
	}
	
	private boolean isHour(int hour) {
		return hour >= 0 && hour <= 23;
	}
	
}
