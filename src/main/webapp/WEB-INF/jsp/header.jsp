<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <title>PAW</title>
	    <link type="text/css" rel="stylesheet" href="css/bootstrap.min.css" >
	</head>
	<body>
	
	<c:if test="${!logged}">
	   <a href="/login">Iniciar sesion</a> <br/>
	</c:if>
	<c:if test="${logged}">
	   <a href="/panel">Panel de Control</a> <br/>
	</c:if>