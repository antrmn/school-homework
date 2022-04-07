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
		<form action="/Immobili/filtra">
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
			<div>
				Superficie (mq): da <input type="number" name="superficie_min" /> a <input type="number" name="superficie_max" /> 
			</div>
			<div>
				Vani: da <input type="number" name="vani_min" /> a <input type="number" name="vani_max" />
			</div>
			<div>
				Anno: da <input type="number" name="anno_min" /> a <input type="number" name="anno_max" />
			</div>
			<div>
				Prezzo (&euro;): da <input type="number" name="prezzo_min" /> a <input type="number" name="prezzo_max" />
			</div>
			<input type="submit" value="Filtra">
		</form>
	</div>
		<br>
	<h2>Immobili</h2>
	<table>
		<tr>
			<th>ID</th>
			<th>Tipo</th>
			<th>Superficie</th>
			<th>Vani</th>
			<th>Anno</th>
			<th>Prezzo</th>
			<th>Tags</th>
			<th>Proprietario</th>
		</tr>
		<c:forEach items="${immobili}" var="immobile">
		<tr>
			<td><c:out value="${immobile.value.id}"/></td>
			<td><c:out value="${immobile.value.tipo}"/></td>
			<td><c:out value="${immobile.value.superficie_mq}"/></td>
			<td><c:out value="${immobile.value.numero_vani}"/></td>
			<td><c:out value="${immobile.value.anno_fabbricazione}"/></td>
			<td><c:out value="${immobile.value.prezzo}"/></td>
			<td>
				<c:forEach items="${immobile.value.tags}" var="tag_immobili">
				<a href="/Immobili/tag/<c:out value="${tag_immobili.id}"/>">
					<c:out value="${tag_immobili.titolo}"/>
				</a> &nbsp;
				</c:forEach>
			</td>
			<c:set var="proprietario_id" value="${immobile.value.proprietario}"/>
			<c:set var="proprietario" value="${proprietari[proprietario_id]}"/>
			<td>
				<a href="/Immobili/proprietari/<c:out value="${proprietario.id}"/>">
					<c:out value="${proprietario.nome}"/> <c:out value="${proprietario.cognome}"/>
				</a>
			</td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>