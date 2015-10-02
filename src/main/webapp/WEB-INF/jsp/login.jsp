<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ include file="header.jsp"%>

<ol class="breadcrumb">
	<li><a href="/">Brand</a></li>
	<li class="active">Iniciar Sesión</li>
</ol>

<%@ include file="message.jsp"%> 

<div class="container">
	<div class="row">
		<div class="col-md-6">
			<H3>Iniciar Sesión</H3>

			<form role="form" action="login" method="POST">
				<div class="form-group">
					<input type="email" class="form-control" placeholder="Mail"
						name="email">
				</div>
				<div class="form-group">
					<input type="password" class="form-control"
						placeholder="Contraseña" name="password">
				</div>
				<button type="submit" value="Entrar" class="btn btn-default">Entrar</button>

			</form>
		</div>
		<div class="col-md-6">
			<H3>Registro</H3>
			<form:form role="form" action="signup" method="POST" commandName="signUpForm">
				<div class="form-group">
					<form:input type="email" class="form-control" placeholder="Mail"
						path="email" />
				</div>
				<div class="form-group">
					<form:input type="password" class="form-control"
						placeholder="Contraseña" path="password" />
				</div>
				<div class="form-group">
					<form:input type="text" class="form-control" placeholder="Nombre"
						path="firstName" />
				</div>
				<div class="form-group">
					<form:input type="text" class="form-control" placeholder="Apellido"
						path="lastName" />
				</div>
				<div class="form-group">
					<form:input type="text" class="form-control" placeholder="Dirección"
						path="address" />
				</div>

				<div class="row">
					<div class="col-md-4">
						<label>Fecha de Nacimiento:</label>
					</div>
					<div class="col-md-2">
						<div class="form-group">
							<form:input type="number" class="form-control" min="1" max="31"
								placeholder="Dia" path="birthDay" />
						</div>
					</div>
					<div class="col-md-2">
						<div class="form-group">
							<form:input type="number" class="form-control" min="1" max="12"
								placeholder="Mes" path="birthMonth" />
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<form:input type="number" class="form-control" min="1900"
								placeholder="Año" path="birthYear" />
						</div>
					</div>
				</div>
				
				<form:errors path="*" />
				<button type="submit" value="Registrar" class="btn btn-default">Registrar</button>
			</form:form>
		</div>
	</div>
</div>
<%@ include file="footer.jsp"%>