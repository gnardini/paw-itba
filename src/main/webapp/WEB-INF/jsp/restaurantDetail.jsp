<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ include file="header.jsp"%>

<ol class="breadcrumb">
	<li><a href="/">Brand</a></li>
	<li class="active">${restaurant.name}</li>
</ol>

<%@ include file="message.jsp"%>

<div class="row">
	<div class="panel panel-default col-md-offset-3 col-md-6">
		<div class="panel-body">
			<div class="row">
				<div class="col-md-9">
					<h1>${restaurant.name}</h1>
					<p>Puntuación: <fmt:formatNumber type="number" currencySymbol="$"
							maxFractionDigits="2" minFractionDigits="2"
							value="${restaurant.ranking}" /></p>
					<p>Tipo de Menú: ${restaurant.menuType}</p>
					<p>Descripción: ${restaurant.description}</p>
					<p>Dirección: ${restaurant.address}</p>
					<c:if test="${restaurant.openingHour == restaurant.closingHour}">
						<p>Horario: Todo el dia</p>
					</c:if>
					<c:if test="${restaurant.openingHour != restaurant.closingHour}">
						<p>Horario: ${restaurant.openingHour} a ${restaurant.closingHour} horas</p>
					</c:if>
					<p>Costo de envío: $${restaurant.deliveryCost}</p>
					<p>Costo mínimo: $${restaurant.minCost}</p>
					<p>Barrios:</p>
					<c:forEach items="${restaurant.neighbourhoods}" var="neighbourhood">
						<p>	- ${neighbourhood.name}</p>
					</c:forEach>
				</div>
				<div class="col-md-3">
					<c:if test="${admin}">
						<div class="row">
							<br />
							<form role="form" action="addNeighbourhood" method="POST">
								<input type="hidden" name="restaurant_id"
									value="${restaurant.id}"> <select class="form-control"
									id="neighbourboodId" name="neighbourboodId">
									<c:forEach items="${neighbourhoods}" var="neighbourhood">
										<option value="${neighbourhood.id}">${neighbourhood.name}</option>
									</c:forEach>
								</select>
								<br/>
								<button type="submit" class="btn btn-default">Agregar
									Barrio</button>
							</form>
							<br />
							<form role="form" action="removeNeighbourhood" method="POST">
								<input type="hidden" name="restaurant_id"
									value="${restaurant.id}"> <select class="form-control"
									id="neighbourboodId" name="neighbourboodId">
									<c:forEach items="${restaurant.neighbourhoods}" var="neighbourhood">
										<option value="${neighbourhood.id}">${neighbourhood.name}</option>
									</c:forEach>
								</select>
								<br/>
								<button type="submit" class="btn btn-default">Quitar
									Barrio</button>
							</form>
							<br />
							<form role="form" action="editRestaurant" method="GET">
								<input type="hidden" name="restaurant_id"
									value="${restaurant.id}">
								<button type="submit" class="btn btn-default">Editar
									Restoran</button>
							</form>
							<br />
							<form role="form" action="deleteRestaurant" method="POST">
								<input type="hidden" name="restaurant_id"
									value="${restaurant.id}">
								<button type="submit" class="btn btn-default">Eliminar
									Restoran</button>
							</form>
						</div>
					</c:if>
				</div>
			</div>
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
			<input type="hidden" name="user_id" value="${comment.user.id}">
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