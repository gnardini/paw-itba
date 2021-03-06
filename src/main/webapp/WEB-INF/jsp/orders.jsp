<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="well well-sm">
	<strong>Pedidos:</strong>
</div>

<c:forEach items="${orders}" var="order">
	<div class="panel panel-default back-panel">
		<div class="panel-heading ">
			<div class="row">
				<div class="col-md-9">
					<p>Restoran: ${order.restaurant.name}</p>
					<p>
						Precio:
						<fmt:formatNumber type="currency" currencySymbol="$"
							maxFractionDigits="2" minFractionDigits="2"
							value="${order.price}" />
					</p>
					<p>Fecha del pedido: ${order.orderDate}</p>
					<p>
						Estado:
						<c:if test="${!order.delivered}">No</c:if>
						Entregado
					</p>
				</div>
				<div class="col-md-3">
					<c:if test="${!order.delivered}">
						<div class="row">
							<form role="form" action="orderDelivered" method="POST">
								<input type="hidden" name="order_id" value="${order.id}">
								<button type="submit" class="btn btn-default">Pedido
									recibido</button>
							</form>
						</div>
					</c:if>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<div class="row list-group">
				<c:forEach items="${order.details}" var="detail">
					<div class="item  col-xs-4 col-lg-4">
						<div class="panel panel-default elem-panel">
							<div class="panel-body">
								<div class="caption align-center">
									<h4 class="group inner list-group-item-heading">${detail.name}</h4>
									<p class="group inner list-group-item-text">Precio:
										$${detail.price}</p>
									<p class="group inner list-group-item-text">Cantidad:
										${detail.amount}</p>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</c:forEach>