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
	<div class="container-fluid">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<a class="navbar-brand" href="index">Brand</a>
		</div>
		<ul class="nav navbar-nav navbar-right">
			<c:if test="${admin}">
				<li><a href="/adminPanel">Panel de Control</a></li>
			</c:if>
			<c:if test="${manager}">
				<li><a href="/managerPanel">Panel de Control</a></li>
			</c:if>
			<c:if test="${logged}">
				<li><a href="/logout">Cerrar sesión</a></li>
			</c:if>
			<c:if test="${!logged}">
				<li><a href="/login">Iniciar sesión</a></li>
			</c:if>
		</ul>
	</div>
	<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid --> </nav>