<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="header.jsp" %>

<h3>Restaurants</h3>

<c:forEach items="${restaurants}" var="restaurant">

	<a href="restaurant?code=${restaurant.code}">${restaurant.name}</a> <br/>

</c:forEach>

<%@ include file="footer.jsp" %>