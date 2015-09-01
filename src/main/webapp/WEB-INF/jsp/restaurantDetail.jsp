<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="header.jsp" %>

<h3>${restaurant.name}</h3>

Descripcion: ${restaurant.description} <br/>
Direccion: ${restaurant.address} <br/>
Horario: ${restaurant.horario} <br/>
Costo de envio: ${restaurant.deliveryCost} <br/>
Costo minimo: ${restaurant.minCost} <br/>

<%@ include file="footer.jsp" %>