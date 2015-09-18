<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="header.jsp"%>

<ol class="breadcrumb">
	<li><a href="/">Brand</a></li>
	<li class="active">Mis Pedidos</li>
</ol>

<div class="container">
	<H3>Panel de Control</H3>

	<%@ include file="message.jsp"%>
		
	<%@ include file="orders.jsp"%>
	
</div>

<%@ include file="footer.jsp"%>