<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="header.jsp" %>

<%@ include file="message.jsp" %>

<h3>${restaurant.name}</h3>

Descripcion: ${restaurant.description} <br/>
Direccion: ${restaurant.address} <br/>
Horario: ${restaurant.horario} <br/>
Costo de envio: ${restaurant.deliveryCost} <br/>
Costo minimo: ${restaurant.minCost} <br/>

<div class="container">
	<form role="form" action="order" method="POST">
		<c:forEach items="${restaurant.dishes}" var="dish">
			
			<div class="form-group">
			 	${dish.name} <input type="number" class="form-control" name="${dish.id}">
			</div><br/>
		
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