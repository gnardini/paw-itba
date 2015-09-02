<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="header.jsp" %>

<c:if test="${message != null}">
   ${message}
</c:if>

<H3>Iniciar sesion</H3>

<div class="container">
	<form role="form" action="login" method="POST">
		<div class="form-group">
			 <input type="email" class="form-control" name="email">
		</div><br/>
		<div class="form-group">
			 <input type="password" class="form-control" name="password">
		</div><br/>
		<input type="submit" value="Entrar"><br/>
		
	</form>
</div>

<%@ include file="footer.jsp" %>