<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ include file="header.jsp"%>

<ol class="breadcrumb">
	<li><a href="/">Brand</a></li>
	<li class="active">Perfil</li>
</ol>

<%@ include file="message.jsp"%> 

<div class="container">
		<H3>Registro</H3>
			<form:form role="form" action="editProfile" method="POST" commandName="editProfileForm">
				<div class="form-group">
					<form:input type="email" class="form-control" placeholder="Mail"
						path="email" value="${user.email}"/>
				</div>
				<div class="form-group">
					<form:input type="password" class="form-control"
						placeholder="Contraseña vieja" path="oldPassword" />
				</div>
				<div class="form-group">
					<form:input type="password" class="form-control"
						placeholder="Contraseña nueva" path="newPassword" />
				</div>
				<div class="form-group">
					<form:input type="text" class="form-control" placeholder="Nombre"
						path="firstName" value="${user.firstName}"/>
				</div>
				<div class="form-group">
					<form:input type="text" class="form-control" placeholder="Apellido"
						path="lastName"  value="${user.lastName}"/>
				</div>
				<div class="form-group">
					<form:input type="text" class="form-control" placeholder="Dirección"
						path="address"  value="${user.address}"/>
				</div>

				<div class="row">
					<div class="col-md-4">
						<label>Fecha de Nacimiento:</label>
					</div>
					<div class="col-md-2">
						<div class="form-group">
							<form:input type="number" class="form-control" min="1" max="31"
								placeholder="Dia" path="birthDay"  value="${user.birthDay}"/>
						</div>
					</div>
					<div class="col-md-2">
						<div class="form-group">
							<form:input type="number" class="form-control" min="1" max="12"
								placeholder="Mes" path="birthMonth" value="${user.birthMonth}" />
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<form:input type="number" class="form-control" min="1900"
								placeholder="Año" path="birthYear" value="${user.birthYear}"/>
						</div>
					</div>
				</div>
				
				<form:errors path="*" />
				<button type="submit" value="Editar" class="btn btn-default">Guardar Cambios</button>
			</form:form>
</div>
<%@ include file="footer.jsp"%>