package ar.edu.itba.it.paw.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ar.edu.itba.it.paw.model.OrderDetail;

public class OrderDetailDatabase extends Database<OrderDetail> {
	
	private static final int ORDER_ID = 1;
	private static final int NAME = 2;
	private static final int AMOUNT = 3;
	private static final int PRICE = 4;

	public void storeOrderDetail(OrderDetail orderDetail) {
		insert("insert into orderdetail values (?, ?, ?, ?)", orderDetail);
	}
	
	public List<OrderDetail> getOrderDetails(long orderId) {
		return doListQuery("select * from orderdetail "
				+ "where orderid=" + orderId);
	}
	
	@Override
	protected OrderDetail generate(ResultSet rs) throws SQLException {
		return new OrderDetail(
				rs.getLong(ORDER_ID),
				rs.getString(NAME),
				rs.getInt(AMOUNT),
				rs.getInt(PRICE));
	}
	
	@Override
	protected void storeData(PreparedStatement pst, OrderDetail detail) throws SQLException {
		pst.setLong(ORDER_ID, detail.getOrderId());
		pst.setString(NAME, detail.getName());
		pst.setInt(AMOUNT, detail.getAmount());
		pst.setInt(PRICE, detail.getPrice());
	}
}
