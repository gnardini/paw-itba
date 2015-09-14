<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${message != null}">
	<div class="container">
		<c:if test="${error}">
			<div class="alert alert-danger" role="alert">${message}</div>
		</c:if>
		<c:if test="${success}">
			<div class="alert alert-success" role="alert">${message}</div>
		</c:if>
		<c:if test="${warning}">
			<div class="alert alert-warning" role="alert">${message}</div>
		</c:if>
		<c:if test="${info}">
			<div class="alert alert-info" role="alert">${message}</div>
		</c:if>
	</div>
</c:if>