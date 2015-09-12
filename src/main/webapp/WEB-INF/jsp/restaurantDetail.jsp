<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="header.jsp"%>

<%@ include file="message.jsp"%>

<ol class="breadcrumb">
	<li><a href="/">Brand</a></li>
	<li class="active">${restaurant.name}</li>
</ol>

<div class="row">
	<div class="panel panel-default col-md-offset-3 col-md-6">
		<div class="panel-body">
			<h1>${restaurant.name}</h1>
			<p>Descripcion: ${restaurant.description}</p>
			<p>Direccion: ${restaurant.address}</p>
			<p>Horario: ${restaurant.openingHours}</p>
			<p>Costo de envio: ${restaurant.deliveryCost}</p>
			<p>Costo minimo: ${restaurant.minCost}</p>
		</div>
	</div>
</div>

<div class="container">
	<div class="well well-sm">
		<strong>Platos</strong>
	</div>
	<form role="form" action="order" method="POST">
		<div id="products" class="row list-group">
			<c:forEach items="${restaurant.dishes}" var="dish">
				<div class="item  col-xs-4 col-lg-4">
					<div class="thumbnail">
						<img class="group list-group-image"
							src="http://placehold.it/400x250/000/fff" alt="" />
						<div class="caption">
							<h4 class="group inner list-group-item-heading">${dish.name}</h4>
							<p class="group inner list-group-item-text">${dish.description}</p>
							<div class="row">
								<div class="col-md-offset-6 col-md-6">
									Cantidad solicitada: <input type="number" class="form-control"
										name="${dish.id}">
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		<input type="hidden" name="code" value="${restaurant.id}" />
		<!-- <input type="submit" value="Realizar Pedido"> -->
		<button type="submit" value="Realizar Pedido" class="btn btn-default">Realizar
			Pedido</button>
	</form>
</div>
<br />
<div class="container">
	<c:forEach items="${restaurant.comments}" var="comment">
		<div class="row">
			<div class=" col-md-4">
				<div class="panel panel-default">
					<div class="panel-heading"><!--<span class="glyphicon glyphicon-star" aria-hidden="true"></span>-->Calificacion: ${comment.rating}</div>
					<div class="panel-body">${comment.text}</div>
				</div>
			</div>
		</div>
	</c:forEach>
</div>







<c:if test="${logged}">
	<h4>Nuevo Comentario:</h4>
	<div class="container">
		<form role="form" action="restaurant?code=${restaurant.id}"
			method="POST">
			Comentario: <input type="text" name="text"><br />
			Puntuacion: <input type="number" name="rating"><br /> <br />
			<input type="submit" value="Enviar"><br />
		</form>
	</div>
</c:if>

<%@ include file="footer.jsp"%>