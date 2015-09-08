<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="header.jsp"%>

<ol class="breadcrumb">
	<li><a href="/">Brand</a></li>
	<li class="active">Iniciar Sesi�n</li>
</ol>

<c:if test="${message != null}">
   ${message}
</c:if>
<div class="container">
	<div class="row">
		<div class="col-md-6">
			<H3>Iniciar sesi�n</H3>

			<form role="form" action="login" method="POST">
				<div class="form-group">
					<input type="email" class="form-control" placeholder="Mail"
						name="email">
				</div>
				<div class="form-group">
					<input type="password" class="form-control"
						placeholder="Contrase�a" name="password">
				</div>
				<button type="submit" value="Entrar" class="btn btn-default">Entrar</button>

			</form>
		</div>
		<div class="col-md-6">
			<H3>Registro</H3>

			<form role="form" action="signup" method="POST">
				<div class="form-group">
					<input type="email" class="form-control" placeholder="Mail"
						name="email">
				</div>
				<div class="form-group">
					<input type="password" class="form-control"
						placeholder="Contrase�a" name="password">
				</div>
				<div class="form-group">
					<input type="text" class="form-control" placeholder="Nombre"
						name="firstName">
				</div>
				<div class="form-group">
					<input type="text" class="form-control" placeholder="Apellido"
						name="lastName">
				</div>
				<div class="form-group">
					<input type="text" class="form-control" placeholder="Direcci�n"
						name="address">
				</div>
				<div class="form-group">
					<input type="text" class="form-control" placeholder="Edad"
						name="age">
				</div>
				<button type="submit" value="Registrar" class="btn btn-default">Registrar</button>
			</form>
		</div>
	</div>
</div>
<%@ include file="footer.jsp"%>