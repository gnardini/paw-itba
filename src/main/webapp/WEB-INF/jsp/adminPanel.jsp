<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="header.jsp" %>

<H3>Panel de Control</H3>

<%@ include file="message.jsp" %>

<h4>Registrar Nuevo Gerente</h4>

<div class="container">
	<form role="form" action="panel" method="POST">
		<div class="form-group">
			 <input type="email" class="form-control" placeholder="Mail" name="email">
		</div>
		<div class="form-group">
			 <input type="password" class="form-control" placeholder="Contrasena" name="password">
		</div>
		<div class="form-group">
			 <input type="text" class="form-control" placeholder="Nombre" name="firstName">
		</div>
		<div class="form-group">
			 <input type="text" class="form-control" placeholder="Apellido" name="lastName">
		</div>
		<div class="form-group">
			 <input type="text" class="form-control" placeholder="Direccion" name="address">
		</div>
		<div class="form-group">
			 <input type="text" class="form-control" placeholder="Edad" name="age">
		</div>
		<br/>
		<input type="submit" value="Registrar"><br/>
	</form>
</div>

<h4>Nuevo restoran</h4>
<div class="container">
	<form role="form" action="panel" method="POST">
		<input type="hidden" name="create" value="restaurant">
		<div class="form-group">
			 <input type="text" class="form-control" placeholder="Nombre" name="name">
		</div>
		<div class="form-group">
			 <input type="text" class="form-control" placeholder="Descripcion" name="age">
		</div>
		<div class="form-group">
			 <input type="text" class="form-control" placeholder="Direccion" name="address">
		</div>
		<div class="form-group">
			 <input type="text" class="form-control" placeholder="Horario" name="openingHours">
		</div>
		<div class="form-group">
			 <input type="text" class="form-control" placeholder="Costo de Delivery" name="deliveryCost">
		</div>
		<div class="form-group">
			 <input type="text" class="form-control" placeholder="Costo minimo" name="minCost">
		</div>			
		<br/>
		<input type="submit" value="Agregar"><br/>
	</form>
</div> <br/>

<%@ include file="footer.jsp" %>