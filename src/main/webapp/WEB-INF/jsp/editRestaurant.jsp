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
				<input type="text" class="form-control" placeholder="Tipo de Menú"
					value="${restaurant.menuType}" name="menuType">
			</div>
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Descripción"
				value="${restaurant.description}" name="description">
		</div>
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Dirección"
				value="${restaurant.address}" name="address">
		</div>
		<div class="form-group">
				<div class="row">
					<div class="col-md-3">
						<input type="number" class="form-control"
							placeholder="Horario de Apertura" min="0" max="23"
							name="openingHour" value="${restaurant.openingHour}">
					</div>
					<div class="col-md-3">
						<input type="number" class="form-control"
							placeholder="Horario de Clausura" min="0" max="23"
							name="closingHour" value="${restaurant.closingHour}">
					</div>
				</div>
			</div>
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Costo de Delivery" min="0"
			value="${restaurant.deliveryCost}" name="deliveryCost">
		</div>
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Costo mínimo" min="0"
				value="${restaurant.minCost}" name="minCost">
		</div>
		<button type="submit" class="btn btn-default">Guardar Cambios</button>
	</form>
</div>

<%@ include file="footer.jsp"%>