package ar.edu.itba.it.paw.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.it.paw.model.Orders;
import ar.edu.itba.it.paw.repository.OrderRepo;
import ar.edu.itba.it.paw.util.NumberUtils;

@Component
public class OrderConverter implements Converter<String, Orders> {
	
	private OrderRepo ordersRepo;
	
	@Autowired
	public OrderConverter(OrderRepo ordersRepo) {
		this.ordersRepo = ordersRepo;
	}
	
	@Override
	public Orders convert(String orderIdString) {
		if ((orderIdString == null || !NumberUtils.isNumber(orderIdString))) {
			return null;
		}
		int orderId = Integer.valueOf(orderIdString);
		return ordersRepo.getOrder(orderId);
	}
}
