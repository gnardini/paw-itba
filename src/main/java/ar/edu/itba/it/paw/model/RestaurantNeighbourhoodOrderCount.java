package ar.edu.itba.it.paw.model;

public class RestaurantNeighbourhoodOrderCount extends RestaurantOrderCount {
	
	private Neighbourhood neighbourhood;
	
	public RestaurantNeighbourhoodOrderCount(Restaurant restaurant, int orderCount, Neighbourhood neighbourhood) {
		super(restaurant, orderCount);
		this.neighbourhood = neighbourhood;
	}
	
	public Neighbourhood getNeighbourhood() {
		return neighbourhood;
	}

}
