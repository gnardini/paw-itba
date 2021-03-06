<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="item  col-xs-4 col-lg-4">
	<div class="thumbnail">
		<img class="group list-group-image"
			src="http://www.salud180.com/sites/www.salud180.com/files/134218877.jpg" alt="" />
		<div class="caption">
			<h4 class="group inner list-group-item-heading align-center">${dish.name}</h4>
			<p class="group inner list-group-item-text align-center">${dish.description}</p>
			<div class="row">
				<div class="col-md-4 price"><fmt:formatNumber type="currency" currencySymbol="$"
							maxFractionDigits="2" minFractionDigits="2"
							value="${dish.price}" /></div>
				<div class="col-md-8">
					<input type="number" class="form-control"
						placeholder="Cantidad solicitada" min="0" max="100" name="${dish.id}">
				</div>
			</div>
		</div>
	</div>
</div>