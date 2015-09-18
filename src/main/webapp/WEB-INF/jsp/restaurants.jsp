<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="header.jsp"%>

<div class="container">
	<div class="well well-sm">
		<strong>Restaurants</strong>
	</div>
	<div id="products" class="row list-group">
		<c:forEach items="${restaurants}" var="restaurant">
			<div class="item  col-xs-4 col-lg-4">
				<div class="thumbnail">
					<img class="group list-group-image"
						src="http://www.comohotels.com/metropolitanbangkok/sites/default/files/styles/background_image/public/images/background/metbkk_bkg_nahm_restaurant.jpg?itok=GSmnYYaU" alt="" />
					<div class="caption">
						<h4 class="group inner list-group-item-heading">${restaurant.name}</h4>
						<p class="group inner list-group-item-text">${restaurant.description}</p>
						<div class="row">
							<div class="restaurant-button">
								<a class="btn btn-success" href="restaurant?code=${restaurant.id}">Ir al Restaurant</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</div>


<%@ include file="footer.jsp"%>