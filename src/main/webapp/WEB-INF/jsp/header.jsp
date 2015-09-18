<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>PAW</title>
<link rel="stylesheet" href="/css/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="/css/grid-view.css" type="text/css">
<link rel="stylesheet" href="/css/my-css.css" type="text/css">
</head>
<body>




	<nav class="navbar navbar-default">

	<form id="form1" role="form" action="/logout" method="POST"></form>
	<div class="container-fluid">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<a class="navbar-brand" href="/">Brand</a>
		</div>
		<ul class="nav navbar-nav navbar-right">
			<c:if test="${logged}">
				<li><a href="/myOrders">Mis Pedidos</a></li>
			</c:if>
			<c:if test="${admin}">
				<li><a href="/adminPanel">Panel de Control</a></li>
			</c:if>
			<c:if test="${manager}">
				<li><a href="/managerPanel">Panel de Control</a></li>
			</c:if>
			<c:if test="${logged}">
				<li><a href="javascript:;"
					onclick="document.getElementById('form1').submit();">Cerrar
						Sesión</a></li>
			</c:if>
			<c:if test="${!logged}">
				<li><a href="/login">Iniciar Sesión</a></li>
			</c:if>
		</ul>
	</div>
	<!-- /.navbar-collapse -->
	<!-- /.container-fluid --> </nav>