<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="header.jsp"%>

<ol class="breadcrumb">
	<li><a href="/">Brand</a></li>
	<li class="active">Panel De Control</li>
</ol>

<div class="container">
	<H3>Panel de Control</H3>

	<%@ include file="message.jsp"%>
	<div class="well well-sm">
		<strong>Agregar nuevo plato</strong>
	</div>
	<div class="row">
		<div class="col-md-10 col-md-offset-1">
			<form role="form" action=managerPanel method="POST">
			
				<div class="form-group">
				<select class="form-control" id="restaurants" name="restaurant_id">
					<c:forEach items="${restaurants}" var="restaurant">
						<option value="${restaurant.id}">${restaurant.name}</option>
					</c:forEach>
				</select>
				</div>
				<div class="form-group">
					<input type="text" class="form-control" placeholder="Nombre"
						name="name">
				</div>
				<div class="form-group">
					<input type="text" class="form-control" placeholder="Descripcion"
						name="description">
				</div>
				<div class="form-group">
					<input type="number" class="form-control" placeholder="Precio" min="1" max="10000"
						name="price">
				</div>
				<div class="form-group">
					<select class="form-control" name="menu">
						<option>Entrada</option>
						<option>Principal</option>
						<option>Postre</option>
						<option>Bebida</option>
					</select>
				</div>
				<button type="submit" value="Agregar" class="btn btn-default">Agregar Plato</button>	
			</form>
		</div>
	</div>
	<br/>
	
</div>

<%@ include file="footer.jsp"%>