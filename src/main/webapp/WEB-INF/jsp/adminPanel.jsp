<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="header.jsp"%>


<ol class="breadcrumb">
	<li><a href="/">Brand</a></li>
	<li class="active">Panel De Control</li>
</ol>

<div class="container">
	<H2>Panel de Control</H2>

	<%@ include file="message.jsp"%>

	<div class="container">
		<h3>Asignar Gerente a Restoran</h3>
		<form role="form" action="assignManager" method="POST">
			<div class="form-group">

				<label for="managers">Seleccione gerente:</label> <select
					class="form-control" id="managers" name="manager_id">
					<c:forEach items="${managers}" var="manager">
						<option value="${manager.id}">${manager.email}</option>
					</c:forEach>
				</select> <label for="restaurants">Seleccione restoran:</label> <select
					class="form-control" id="restaurants" name="restaurant_id">
					<c:forEach items="${restaurants}" var="restaurant">
						<option value="${restaurant.id}">${restaurant.name}</option>
					</c:forEach>
				</select>

			</div>
			<button type="submit" value="Registrar" class="btn btn-default">Asignar
				Gerente</button>
		</form>
	</div>

	<div class="container">
		<h3>Registrar Nuevo Gerente</h3>
		<form role="form" action="newManager" method="POST">
			<div class="form-group">
				<label for="users">Seleccione de lista de usuarios:</label> <select
					class="form-control" id="users" name="user_id">
					<c:forEach items="${users}" var="user">
						<option value="${user.id}">${user.email}</option>
					</c:forEach>
				</select>
			</div>
			<button type="submit" value="Registrar" class="btn btn-default">Registrar
				Gerente</button>
		</form>
	</div>

	<div class="container">
		<h3>Nuevo Restoran</h3>
		<form role="form" action="newRestaurant" method="POST">
			<input type="hidden" name="create" value="restaurant">
			<div class="form-group">
				<input type="text" class="form-control" placeholder="Nombre"
					name="name">
			</div>
			<div class="form-group">
				<input type="text" class="form-control" placeholder="Tipo de Menú"
					name="menuType">
			</div>
			<div class="form-group">
				<input type="text" class="form-control" placeholder="Descripción"
					name="description">
			</div>
			<div class="form-group">
				<input type="text" class="form-control" placeholder="Dirección"
					name="address">
			</div>
			<div class="form-group">
				<div class="row">
					<div class="col-md-3">
						<input type="number" class="form-control"
							placeholder="Horario de Apertura" min="0" max="23"
							name="openingHour">
					</div>
					<div class="col-md-3">
						<input type="number" class="form-control"
							placeholder="Horario de Clausura" min="0" max="23"
							name="closingHour">
					</div>
				</div>
			</div>
			<div class="form-group">
				<input type="text" class="form-control"
					placeholder="Costo de Delivery" name="deliveryCost">
			</div>
			<div class="form-group">
				<input type="text" class="form-control" placeholder="Costo mínimo"
					name="minCost">
			</div>
			<div class="form-group">
				<select class="form-control" name="neighbourhoodId">
					<c:forEach items="${neighbourhoods}" var="neighbourhood">
						<option value="${neighbourhood.id}">${neighbourhood.name}</option>
					</c:forEach>
				</select>
			</div>
			<button type="submit" value="Agregar" class="btn btn-default">Agregar
				Restoran</button>
		</form>
	</div>
	<br />

</div>


<%@ include file="footer.jsp"%>