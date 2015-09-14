<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="well well-sm">
	<strong>Pedidos:</strong>
</div>

<c:forEach items="${orders}" var="order">
	<div class="container row">
		<c:forEach items="${order.details}" var="detail">
			<div class="container">
				Plato: ${detail.name}<br/>
				Precio: ${detail.price}<br/>
				Cantidad: ${detail.amount}<br/>
			</div>
		</c:forEach>
		
	</div>
</c:forEach>