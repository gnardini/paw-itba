package ar.edu.itba.it.paw.model;

public class RestaurantNeighbourhoodOrderCount extends RestaurantOrderCount {
	
	private Neighbourhood neighbourhood;
	private OrderDate date;
	
	public RestaurantNeighbourhoodOrderCount(Restaurant restaurant, int orderCount,
			Neighbourhood neighbourhood, OrderDate date) {
		super(restaurant, orderCount);
		this.neighbourhood = neighbourhood;
		this.date = date;
	}
	
	public Neighbourhood getNeighbourhood() {
		return neighbourhood;
	}

	public String getDate() {
		return date.toString();
	}
	
}
