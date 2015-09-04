<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="header.jsp" %>

<%@ include file="message.jsp" %>

<ol class="breadcrumb">
  <li><a href="/">Home</a></li>
  <li class="active">${restaurant.name}</li>
</ol>


<div class="panel panel-default col-md-offset-3 col-md-6">
  <div class="panel-body">
    <h1>${restaurant.name}</h1>
  <p>Descripcion: ${restaurant.description} </p>
  <p>Direccion: ${restaurant.address}</p>
  <p>Horario: ${restaurant.horario}</p>
  <p>Costo de envio: ${restaurant.deliveryCost}</p>
  <p>Costo minimo: ${restaurant.minCost}</p>
  </div>
</div>


<div class="container">
	<form role="form" action="order" method="POST">
		<c:forEach items="${restaurant.dishes}" var="dish">
			<br/>
			<div class="form-group">
			 	${dish.name} <input type="number" class="form-control" name="${dish.id}">
			</div>
		
		</c:forEach>
		<input type="hidden" name="code" value="${restaurant.code}"/>
		<input type="submit" value="Realizar Pedido"><br/>
	</form>
</div>

<c:forEach items="${restaurant.comments}" var="comment">
	
	<br/>
	Calificacion: ${comment.rating} <br/>
	Comentario: ${comment.text} <br/>

</c:forEach>

<c:if test="${logged}">
	<h4>Nuevo Comentario:</h4>
	<div class="container">
		<form role="form" action="restaurant?code=${restaurant.code}" method="POST">
			Comentario: <input type="text" name="text"><br/>
			Puntuacion: <input type="number" name="rating"><br/>
			<br/>
			<input type="submit" value="Enviar"><br/>
		</form>
	</div>
</c:if>

<%@ include file="footer.jsp" %>