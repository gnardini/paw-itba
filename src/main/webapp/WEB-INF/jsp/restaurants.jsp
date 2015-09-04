<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="header.jsp"%>

<div class="container">
	<div class="well well-sm">
		<strong>Restaurants</strong>
		<div class="btn-group">
			<a href="#" id="list" class="btn btn-default btn-sm"><span
				class="glyphicon glyphicon-th-list"> </span>List</a> <a href="#"
				id="grid" class="btn btn-default btn-sm"><span
				class="glyphicon glyphicon-th"></span>Grid</a>
		</div>
	</div>
	<div id="products" class="row list-group">


		<c:forEach items="${restaurants}" var="restaurant">

			<div class="item  col-xs-4 col-lg-4">
				<div class="thumbnail">
					<img class="group list-group-image"
						src="http://placehold.it/400x250/000/fff" alt="" />
					<div class="caption">
						<h4 class="group inner list-group-item-heading">${restaurant.name}</h4>
						<p class="group inner list-group-item-text">${restaurant.description}</p>
						<div class="row">
							<div class="restaurant-button">
								<a class="btn btn-success" href="restaurant?code=${restaurant.code}">Ir al Restaurant</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</div>


<%@ include file="footer.jsp"%>