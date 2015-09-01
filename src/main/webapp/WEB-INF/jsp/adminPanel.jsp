<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="header.jsp" %>

<H3>Panel de Control</H3>

<c:if test="${message != null}">
   ${message}
</c:if>

<h4>Registrar Nuevo Gerente</h4>

<div class="container">
	<form role="form" action="panel" method="POST">
		<div class="form-group">
			 <input type="email" class="form-control" id="email">
		</div>
		<div class="form-group">
			 <input type="password" class="form-control" id="password">
		</div>
		<div class="form-group">
			 <input type="text" class="form-control" id="firstName">
		</div>
		<div class="form-group">
			 <input type="text" class="form-control" id="lastName">
		</div>
		<div class="form-group">
			 <input type="text" class="form-control" id="address">
		</div>
		<div class="form-group">
			 <input type="text" class="form-control" id="age">
		</div>
		<br/>
		<input type="submit" value="Registrar"><br/>
		</form>
</div>

<a href="javascript:history.back()">Volver</a>

<%@ include file="footer.jsp" %>