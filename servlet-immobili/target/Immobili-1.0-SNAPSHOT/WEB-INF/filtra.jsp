<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" 
       uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Immobili</title>
</head>
<body>
<a href="/Immobili/">Immobili</a>&nbsp<a href="/Immobili/proprietari">Proprietari</a>
	<ul>
		<li>Nome: <c:out value="${proprietario.nome}"></c:out></li>
		<li>Cognome: <c:out value="${proprietario.cognome}"></c:out></li>
		<li>Possessore di <c:out value="${proprietario.numero_possedimenti} "></c:out> immobili</li>
		<li>Superficie posseduta: <c:out value="${proprietario.totale_superficie}"></c:out>mq</li>
	</ul>

	<table>
		<tr>
			<th>ID</th>
			<th>Tipo</th>
			<th>Superficie</th>
			<th>Vani</th>
			<th>Anno</th>
			<th>Prezzo</th>
		</tr>
		<c:forEach items="${immobili}" var="immobile">
		<tr>
			<td><c:out value="${immobile.value.id}"/></td>
			<td><c:out value="${immobile.value.tipo}"/></td>
			<td><c:out value="${immobile.value.superficie_mq}"/></td>
			<td><c:out value="${immobile.value.numero_vani}"/></td>
			<td><c:out value="${immobile.value.anno_fabbricazione}"/></td>
			<td><c:out value="${immobile.value.prezzo}"/></td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>