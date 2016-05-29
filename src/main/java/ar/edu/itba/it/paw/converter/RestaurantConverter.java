package ar.edu.itba.it.paw.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.it.paw.model.Restaurant;
import ar.edu.itba.it.paw.repository.hibernate.HibernateRestaurantRepo;
import ar.edu.itba.it.paw.util.NumberUtils;

@Component
public class RestaurantConverter implements Converter<String, Restaurant> {
	
	private HibernateRestaurantRepo restaurantRepo;
	
	@Autowired
	public RestaurantConverter(HibernateRestaurantRepo restaurantRepo) {
		this.restaurantRepo = restaurantRepo;
	}
	
	@Override
	public Restaurant convert(String restaurantIdString) {
		if ((restaurantIdString == null || !NumberUtils.isNumber(restaurantIdString))) {
			return null;
		}
		int restaurantId = Integer.valueOf(restaurantIdString);
		return restaurantRepo.getRestaurant(restaurantId);
	}
}
