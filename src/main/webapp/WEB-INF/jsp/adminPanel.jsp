<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="header.jsp"%>
<div class="container">
	<H2>Panel de Control</H2>

	<%@ include file="message.jsp"%>

	<div class="container">
		<h4>Registrar Nuevo Gerente</h4>
		<form role="form" action="newManager" method="POST">
			<div class="form-group">
				<label for="users">Seleccione de lista de usuarios:</label> 
				<select class="form-control" id="users" name="userId">
					<c:forEach items="${users}" var="user">
						<option value="${user.id}">${user.email}</option>
					</c:forEach>
				</select>
			</div>
			<button type="submit" value="Registrar" class="btn btn-default">Registrar
				Gerente</button>
		</form>
	</div>
	<!-- 

<form role="form" action="newManager" method="POST">
	<div class="form-group">
		<input type="email" class="form-control" placeholder="Mail"
			name="email">
	</div>
	<div class="form-group">
		<input type="password" class="form-control" placeholder="Contrasena"
			name="password">
	</div>
	<div class="form-group">
		<input type="text" class="form-control" placeholder="Nombre"
			name="firstName">
	</div>
	<div class="form-group">
		<input type="text" class="form-control" placeholder="Apellido"
			name="lastName">
	</div>
	<div class="form-group">
		<input type="text" class="form-control" placeholder="Direccion"
			name="address">
	</div>
	<div class="form-group">
		<input type="text" class="form-control" placeholder="Edad" name="age">
	</div>
	<br /> <input type="submit" value="Registrar"><br />
</form>
 -->
	<div class="container">
		<h3>Nuevo restaurant</h3>
		<form role="form" action="newRestaurant" method="POST">
			<input type="hidden" name="create" value="restaurant">
			<div class="form-group">
				<input type="text" class="form-control" placeholder="Nombre"
					name="name">
			</div>
			<div class="form-group">
				<input type="text" class="form-control" placeholder="Descripcion"
					name="description">
			</div>
			<div class="form-group">
				<input type="text" class="form-control" placeholder="Direccion"
					name="address">
			</div>
			<div class="form-group">
				<input type="text" class="form-control" placeholder="Horario"
					name="openingHours">
			</div>
			<div class="form-group">
				<input type="text" class="form-control"
					placeholder="Costo de Delivery" name="deliveryCost">
			</div>
			<div class="form-group">
				<input type="text" class="form-control" placeholder="Costo minimo"
					name="minCost">
			</div>
			<button type="submit" value="Agregar" class="btn btn-default">Agregar
				Restaurant</button>
		</form>
	</div>
</div>

<%@ include file="footer.jsp"%>