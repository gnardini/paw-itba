<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="header.jsp" %>

<H3>Panel de Control</H3>

<%@ include file="message.jsp" %>

<h4>Restaurantes</h4>

<c:forEach items="${restaurants}" var="restaurant">

	<a href="restaurant?code=${restaurant.code}">${restaurant.name}</a> <br/>

	<b>Nuevo Plato:</b>
	<div class="container">
		<form role="form" action="panel" method="POST">
			<div class="form-group">
				 <input type="text" class="form-control" placeholder="Nombre" name="name">
			</div>
			<div class="form-group">
				 <input type="text" class="form-control" placeholder="Descripcion" name="age">
			</div>
			<div class="form-group">
				 <input type="text" class="form-control" placeholder="Precio" name="price">
			</div>
			<div class="form-group">
				 <select class="form-control" placeholder="Menu" name="menu">
				 	<option>Entrada</option>
				 	<option>Principal</option>
				 	<option>Postre</option>
				 	<option>Bebida</option>
				 </select>
			</div>
			<br/>
			<input type="hidden" name="code" value="${restaurant.code}">
			<input type="submit" value="Agregar"><br/>
		</form>
	</div>
	<br/><br/>
</c:forEach>

<%@ include file="footer.jsp" %>