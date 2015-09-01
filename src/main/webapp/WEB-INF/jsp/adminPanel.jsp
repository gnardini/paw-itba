<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="header.jsp" %>

<H3>Admin Panel</H3>


<c:if test="${message != null}">
   ${message}
</c:if>


<h4>Resgistrar Nuevo Gerente</h4>

<div class=”container”>
	<form role=”form” action="panel" method="POST">
		<div class="form-group">
			 <input type="email" class="form-control" id="email">
		</div>
		Password: <input type="password" name="password"><br/>
		First Name: <input type="text" name="firstName"><br/>
		Last Name: <input type="text" name="lastName"><br/>
		Address: <input type="text" name="address"><br/>
		Age: <input type="text" name="age"><br/>
		<br/>
		<input type="submit" value="Registrar"><br/>
	</form>
</div>

<a href="javascript:history.back()">Volver</a>

<%@ include file="footer.jsp" %>