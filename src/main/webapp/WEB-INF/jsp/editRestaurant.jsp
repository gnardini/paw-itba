<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="header.jsp"%>

<div class="container">
	<H3>Editar Restaurant</H3>

	<form role="form" action="editRestaurant" method="POST">
		<input type="hidden" name="restaurant_id" value="${restaurant.id}">
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Nombre"
				value="${restaurant.name}" name="name">
		</div>
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Descripcion"
				value="${restaurant.description}" name="description">
		</div>
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Direccion"
				value="${restaurant.address}" name="address">
		</div>
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Horario"
				value="${restaurant.openingHours}" name="openingHours">
		</div>
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Costo de Delivery" 
			value="${restaurant.deliveryCost}" name="deliveryCost">
		</div>
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Costo minimo"
				value="${restaurant.minCost}" name="minCost">
		</div>
		<button type="submit" class="btn btn-default">Guardar Cambios</button>
	</form>
</div>

<%@ include file="footer.jsp"%>