<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="well well-sm">
	<strong>Pedidos:</strong>
</div>

<c:forEach items="${orders}" var="order">
	<div class="row list-group">
		<c:forEach items="${order.details}" var="detail">
			<div class="item  col-xs-4 col-lg-4">
				<div class="caption">
					<h4 class="group inner list-group-item-heading">${detail.name}</h4>
					<p class="group inner list-group-item-text">Precio: ${detail.price}</p>
					<p class="group inner list-group-item-text">Cantidad: ${detail.amount}</p>
				</div>
			</div>
		</c:forEach>
	</div>
</c:forEach>