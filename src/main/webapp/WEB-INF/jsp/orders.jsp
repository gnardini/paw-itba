<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="well well-sm">
	<strong>Pedidos:</strong>
</div>

<c:forEach items="${orders}" var="order">
	<div class="panel panel-default back-panel">
		<div class="panel-heading"><p>Precio Total: ${order.price}</p><p>Fecha del pedido: ${order.orderDate}</p></div>
		<div class="panel-body">
			<div class="row list-group">
				<c:forEach items="${order.details}" var="detail">
					<div class="item  col-xs-4 col-lg-4">
						<div class="panel panel-default elem-panel">
							<div class="panel-body">
								<div class="caption align-center">
									<h4 class="group inner list-group-item-heading">${detail.name}</h4>
									<p class="group inner list-group-item-text">Precio:
										${detail.price}</p>
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