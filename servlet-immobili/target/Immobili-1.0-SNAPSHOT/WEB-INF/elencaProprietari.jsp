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
	<div>
		<h2>Filtra</h2>
		<form action="/Immobili/filtra/proprietari">
			<select name="tipo">
			    <option disabled selected value> -- Tipo -- </option>
			    <c:forEach var="entry" items="${tipi}">
			    <option value="${entry}">${entry}</option>
			    </c:forEach>
			</select>
			<select name="tag">
			    <option disabled selected value> -- Tag -- </option>
			    <c:forEach var="tag" items="${tags_lista}">
			    <option value="${tag.value.id}">${tag.value.titolo}</option>
			    </c:forEach>
			</select>
			<input type="submit" value="Filtra">
		</form>
	</div>
	<br>
	
	<h2>Proprietari</h2>
	<table>
		<tr>
			<th>Nome</th>
			<th>Cognome</th>
			<th>mq posseduti</th>
			<th>Immobili</th>
		</tr>
		<c:forEach items="${proprietari}" var="proprietario">
		<tr> 
			<td><c:out value="${proprietario.value.nome}"/></td>
			<td><c:out value="${proprietario.value.cognome}"/></td>
			<td><c:out value="${proprietario.value.totale_superficie}"/></td>
			<td><a href="/Immobili/proprietari/<c:out value="${proprietario.value.id}"/>"><c:out value="${proprietario.value.numero_possedimenti}"/></a></td>
			
		</tr>
		</c:forEach>
	</table>
</html>