<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="header.jsp"%>

<ol class="breadcrumb">
	<li><a href="/">Brand</a></li>
	<li><a href="/restaurant?code=${restaurant.id}">${restaurant.name}</a></li>
	<li class="active">Editar Restoran</li>
</ol>

<div class="container">

	<H3>Editar Restoran</H3>
	
	<%@ include file="message.jsp"%>

	<form role="form" action="editRestaurant" method="POST">
		<input type="hidden" name="restaurant_id" value="${restaurant.id}">
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Nombre"
				value="${restaurant.name}" name="name">
		</div>
		<div class="form-group">
				<input type="text" class="form-control" placeholder="Tipo de Men�"
					value="${restaurant.menuType}" name="menuType">
			</div>
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Descripci�n"
				value="${restaurant.description}" name="description">
		</div>
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Direcci�n"
				value="${restaurant.address}" name="address">
		</div>
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Horario"
				value="${restaurant.openingHours}" name="openingHours">
		</div>
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Costo de Delivery" min="0"
			value="${restaurant.deliveryCost}" name="deliveryCost">
		</div>
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Costo m�nimo" min="0"
				value="${restaurant.minCost}" name="minCost">
		</div>
		<button type="submit" class="btn btn-default">Guardar Cambios</button>
	</form>
</div>

<%@ include file="footer.jsp"%>