package ar.edu.itba.it.paw.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ar.edu.itba.it.paw.model.Dish;

public class DishDatabase extends Database<Dish> {
	
	public void addDish(Dish dish) {
		
	}
	
	@Override
	protected Dish generate(ResultSet rs) throws SQLException {
		return null;
	}
	
	@Override
	protected void storeData(PreparedStatement pst, Dish elem) throws SQLException {
		
	}
}
