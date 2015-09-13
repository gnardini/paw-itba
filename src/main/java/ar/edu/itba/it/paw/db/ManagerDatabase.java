package ar.edu.itba.it.paw.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ar.edu.itba.it.paw.db.model.Manager;

public class ManagerDatabase extends Database<Manager> {

	private static final int USER_ID = 1;
	private static final int RESTAURANT_ID = 2;
	
	public void assignManager(long managerId, long restaurantId) {
		insert("insert into managers values (?, ?)", new Manager(managerId, restaurantId));
	}
	
	@Override
	protected Manager generate(ResultSet rs) throws SQLException {
		return new Manager(
				rs.getLong(USER_ID),
				rs.getLong(RESTAURANT_ID));
	}
	
	@Override
	protected void storeData(PreparedStatement pst, Manager manager) throws SQLException {
		pst.setLong(USER_ID, manager.getManagerId());
		pst.setLong(RESTAURANT_ID, manager.getRestaurantId());
	}
}
