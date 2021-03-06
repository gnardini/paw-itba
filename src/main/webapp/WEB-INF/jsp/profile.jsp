<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ include file="header.jsp"%>

<ol class="breadcrumb">
	<li><a href="/">Brand</a></li>
	<li class="active">Perfil</li>
</ol>

<%@ include file="message.jsp"%>

<div class="container">
	<H3>Perfil</H3>
	<form:form role="form" action="editProfile" method="POST"
		commandName="editProfileForm">

		<label>Email:</label>
		<div class="form-group">
			<form:input type="email" class="form-control" placeholder="Mail"
				path="email" value="${user.email}" />
		</div>
		<label>Contrase�a actual:</label>
		<div class="form-group">
			<form:input type="password" class="form-control"
				placeholder="Contrase�a actual" path="oldPassword" />
		</div>
		<label>Contrase�a nueva (dejela vac�a si no quiere
			modificarla):</label>
		<div class="form-group">
			<form:input type="password" class="form-control"
				placeholder="Contrase�a nueva" path="newPassword" />
		</div>
		<label>Nombre:</label>
		<div class="form-group">
			<form:input type="text" class="form-control" placeholder="Nombre"
				path="firstName" value="${user.firstName}" />
		</div>
		<label>Apellido:</label>
		<div class="form-group">
			<form:input type="text" class="form-control" placeholder="Apellido"
				path="lastName" value="${user.lastName}" />
		</div>
		<label>Direcci�n:</label>
		<div class="form-group">
			<form:input type="text" class="form-control" placeholder="Direcci�n"
				path="address" value="${user.address}" />
		</div>
		<label>Barrio:</label>
		<form:select class="form-control" path="neighbourhoodId"
			placeholder="Barrio">
			<c:forEach items="${neighbourhoods}" var="neighbourhood">
				<c:if
					test="${(not empty user.neighbourhood) && (neighbourhood.id == user.neighbourhood.id)}">
					<form:option value="${neighbourhood.id}" selected="selected">${neighbourhood.name}</form:option>
				</c:if>
				<c:if
					test="${(empty user.neighbourhood) || (neighbourhood.id != user.neighbourhood.id)}">
					<form:option value="${neighbourhood.id}">${neighbourhood.name}</form:option>
				</c:if>
			</c:forEach>
		</form:select>
		<br />
		<div class="row">
			<div class="col-md-4">
				<label>Fecha de Nacimiento:</label>
			</div>
			<div class="col-md-2">
				<div class="form-group">
					<form:input type="number" class="form-control" min="1" max="31"
						placeholder="Dia" path="birthDay" value="${user.birthDay}" />
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
						placeholder="A�o" path="birthYear" value="${user.birthYear}" />
				</div>
			</div>
		</div>


		<form:errors path="*" />
		<button type="submit" value="Editar" class="btn btn-default">Guardar
			Cambios</button>
	</form:form>
	<br />
</div>
<%@ include file="footer.jsp"%>