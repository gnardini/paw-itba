package ar.edu.itba.it.paw.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import ar.edu.itba.it.paw.model.Dish;
import ar.edu.itba.it.paw.util.DishTypeUtils;

public class DishDatabase extends Database<Dish> {
	
	private static final int ID = 1;
	private static final int RESTAURANT_ID = 2;
	private static final int NAME = 3;
	private static final int DESCRIPTION = 4;
	private static final int PRICE = 5;
	private static final int TYPE = 6;
	
	public void addDish(Dish dish) {
		insert("insert into dish (restaurantid, name, description, price, type) values(?, ?, ?, ?, ?)", dish);
	}
	
	public Dish getDish(long dishId) {
		return doQuery("select * from dish where id=" + dishId);
	}
	
	public List<Dish> getRestaurantDishes(long restaurantId) {
		return doListQuery("select * from dish "
				+ "where dish.restaurantid=" + restaurantId);
	}
	
	@Override
	protected Dish generate(ResultSet rs) throws SQLException {
		return new Dish(
				rs.getLong(ID),
				rs.getLong(RESTAURANT_ID),
				rs.getString(NAME),
				rs.getString(DESCRIPTION),
				rs.getInt(PRICE),
				DishTypeUtils.getDishTypeFromString(rs.getString(TYPE).toLowerCase()));
	}
	
	@Override
	protected void storeData(PreparedStatement pst, Dish dish) throws SQLException {
		pst.setLong(RESTAURANT_ID - 1, dish.getRestaurantId());
		pst.setString(NAME - 1, dish.getName());
		pst.setString(DESCRIPTION - 1, dish.getDescription());
		pst.setInt(PRICE - 1, dish.getPrice());
		pst.setString(TYPE - 1, dish.getType().toString());
	}
}
