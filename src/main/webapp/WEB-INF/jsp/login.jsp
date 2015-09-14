<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="header.jsp"%>

<ol class="breadcrumb">
	<li><a href="/">Brand</a></li>
	<li class="active">Iniciar Sesión</li>
</ol>

<%@ include file="message.jsp"%> 

<div class="container">
	<div class="row">
		<div class="col-md-6">
			<H3>Iniciar sesión</H3>

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
			<form role="form" action="signup" method="POST">
				<div class="form-group">
					<input type="email" class="form-control" placeholder="Mail"
						name="email">
				</div>
				<div class="form-group">
					<input type="password" class="form-control"
						placeholder="Contraseña" name="password">
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
					<input type="text" class="form-control" placeholder="Dirección"
						name="address">
				</div>

				<div class="row">
					<div class="col-md-4">
						<label>Fecha de Nacimiento:</label>
					</div>
					<div class="col-md-2">
						<div class="form-group">
							<input type="number" class="form-control" min="1" max="31"
								placeholder="Dia" name="day">
						</div>
					</div>
					<div class="col-md-2">
						<div class="form-group">
							<input type="number" class="form-control" min="1" max="12"
								placeholder="Mes" name="month">
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<input type="number" class="form-control" min="1900"
								placeholder="Año" name="year">
						</div>
					</div>
				</div>

				<button type="submit" value="Registrar" class="btn btn-default">Registrar</button>
			</form>
		</div>
	</div>
</div>
<%@ include file="footer.jsp"%>