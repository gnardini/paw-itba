<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="header.jsp"%>

<ol class="breadcrumb">
	<li><a href="/">Brand</a></li>
	<li class="active">${restaurant.name}</li>
</ol>

<%@ include file="message.jsp"%>

<div class="row">
	<div class="panel panel-default col-md-offset-3 col-md-6">
		<div class="panel-body">
			<h1>${restaurant.name}</h1>
			<p>Puntuacion: ${restaurant.ranking}</p>
			<p>Descripcion: ${restaurant.description}</p>
			<p>Direccion: ${restaurant.address}</p>
			<p>Horario: ${restaurant.openingHours}</p>
			<p>Costo de envio: $${restaurant.deliveryCost}</p>
			<p>Costo minimo: $${restaurant.minCost}</p>

			<c:if test="${admin}">
				<div class="row">
					<form role="form" action="editRestaurant" method="GET">
						<input type="hidden" name="restaurant_id" value="${restaurant.id}">
						<button type="submit" class="btn btn-default">Editar
							Restoran</button>
					</form>
					<form role="form" action="deleteRestaurant" method="POST">
						<input type="hidden" name="restaurant_id" value="${restaurant.id}">
						<button type="submit" class="btn btn-default">Eliminar
							Restoran</button>
					</form>
				</div>
			</c:if>
		</div>
	</div>
</div>

<div class="container">
	<form role="form" action="order" method="POST">
		<div class="container">
			<div class="well well-sm">
				<strong>Entradas</strong>
			</div>
			<div id="products" class="row list-group">
				<c:forEach items="${restaurant.dishes}" var="dish">
					<c:if test="${dish.type == 'ENTRY' }">
						<%@ include file="dishesInfo.jsp"%>
					</c:if>
				</c:forEach>
			</div>
		</div>
		<div class="container">
			<div class="well well-sm">
				<strong>Plato Principal</strong>
			</div>
			<div id="products" class="row list-group">
				<c:forEach items="${restaurant.dishes}" var="dish">
					<c:if test="${dish.type == 'MAIN' }">
						<%@ include file="dishesInfo.jsp"%>
					</c:if>
				</c:forEach>
			</div>
		</div>
		<div class="container">
			<div class="well well-sm">
				<strong>Postre</strong>
			</div>
			<div id="products" class="row list-group">
				<c:forEach items="${restaurant.dishes}" var="dish">
					<c:if test="${dish.type == 'DESSERT' }">
						<%@ include file="dishesInfo.jsp"%>
					</c:if>
				</c:forEach>
			</div>
		</div>
		<div class="container">
			<div class="well well-sm">
				<strong>Bebidas</strong>
			</div>
			<div id="products" class="row list-group">
				<c:forEach items="${restaurant.dishes}" var="dish">
					<c:if test="${dish.type == 'DRINK' }">
						<%@ include file="dishesInfo.jsp"%>
					</c:if>
				</c:forEach>
			</div>
		</div>
		<input type="hidden" name="restaurant_id" value="${restaurant.id}" />
		<!-- <input type="submit" value="Realizar Pedido"> -->
		<button type="submit" value="Realizar Pedido" class="btn btn-default">Realizar
			Pedido</button>
	</form>
</div>
<br />

<div class="container">
	<c:forEach items="${restaurant.comments}" var="comment">

		<form role="form" action="deleteComment" method="POST">
			<input type="hidden" name="user_id" value="${comment.userId}">
			<input type="hidden" name="restaurant_id" value="${restaurant.id}">
			<div class="row">
				<div class=" col-md-4">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="row">
								<div class="col-md-10 vertical-align">Calificacion:
									${comment.rating}</div>
								<c:if test="${admin}">
									<div class="col-md-2 align-right">
										<input type="submit" value="X">
									</div>
								</c:if>
							</div>
						</div>
						<div class="panel-body">${comment.userName}:</div>
						<div class="panel-body">${comment.text}</div>
					</div>
				</div>
			</div>
		</form>
	</c:forEach>
</div>

<c:if test="${can_comment}">
	<div class="container">
		<div class="row">
			<div class="col-md-6">
				<div class="panel panel-default back-panel">
					<div class="panel-heading">Nuevo Comentario</div>
					<div class="panel-body">
						<form role="form" action="newComment" method="POST">
							<input class="form-control" placeholder="Puntaje" type="number"
								min="1" max="5" name="rating"><br /> <input
								class="form-control" placeholder="Comentario" type="text"
								name="text"> <input type="hidden" name="restaurant_id"
								value="${restaurant.id}"><br />
							<button type="submit" value="Enviar" class="btn btn-default">Enviar
								Comentario</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:if>

<%@ include file="footer.jsp"%>