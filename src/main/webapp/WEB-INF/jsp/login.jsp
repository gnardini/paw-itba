<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="header.jsp" %>

<c:if test="${message != null}">
   ${message}
</c:if>

<H3>Iniciar sesion</H3>

<div class="container">
	<form role="form" action="login" method="POST">
		<div class="form-group">
			 <input type="email" class="form-control" placeholder="Mail" name="email">
		</div><br/>
		<div class="form-group">
			 <input type="password" class="form-control" placeholder="Contrasena" name="password">
		</div><br/>
		<input type="submit" value="Entrar"><br/>
		
	</form>
</div>

<H3>Registro</H3>

<div class="container">
	<form role="form" action="signup" method="POST">
		<div class="form-group">
			 <input type="email" class="form-control" placeholder="Mail" name="email">
		</div>
		<div class="form-group">
			 <input type="password" class="form-control" placeholder="Contrasena" name="password">
		</div>
		<div class="form-group">
			 <input type="text" class="form-control" placeholder="Nombre" name="firstName">
		</div>
		<div class="form-group">
			 <input type="text" class="form-control" placeholder="Apellido" name="lastName">
		</div>
		<div class="form-group">
			 <input type="text" class="form-control" placeholder="Direccion" name="address">
		</div>
		<div class="form-group">
			 <input type="text" class="form-control" placeholder="Edad" name="age">
		</div>
		<br/>
		<input type="submit" value="Registrar"><br/>
	</form>
</div>

<%@ include file="footer.jsp" %>