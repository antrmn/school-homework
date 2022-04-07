<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
	<html>

	<head>
		<style>
			th, td {
    		padding: 10px;
			}

			table, td, th {    
    		border: 1px solid #dddddd;
			}

			table {
    		border-collapse: collapse;
    		width: 100%;
			}

			tr:hover {
			background-color:#f5f5f5;
			}

			body{
			font-family: "helvetica";
			}

		</style>
	</head>

	<body>
		<table border="1px" border-collapse="collapse">
			<tr>
				<th>Matricola</th>
				<th>Nome</th>
				<th>Cognome</th>
				<th>Data di Nascita</th>
				<th>Luogo di Nascita</th>
				<th>Residenza</th>
				<th>Sesso</th>
			</tr>

			<xsl:for-each select="studenti/studente">

			<xsl:variable name="giorno"  select="substring-before(dataDiNascita, '/')"/>
			<xsl:variable name="mese"	 select="substring-before(substring-after(dataDiNascita, '/'),'/')"/>
			<xsl:variable name="anno" 	 select="substring-after(substring-after(dataDiNascita, '/'), '/')"/>

			<xsl:if test="$anno > 2000 or $anno = 2000 and $mese > 1 and $giorno > 1">
				<tr>
					<td><xsl:value-of select="matricola"/></td>
					<td><xsl:value-of select="nome"/></td>
					<td><xsl:value-of select="cognome"/></td>
					<td><xsl:value-of select="dataDiNascita"/></td>
					<td><xsl:value-of select="luogoDiNascita"/></td>
					<td><xsl:value-of select="residenza"/></td>
					<td><xsl:value-of select="sesso"/></td>
				</tr>
			</xsl:if>
			</xsl:for-each>
		</table>
	</body>
	</html>
</xsl:template>

</xsl:stylesheet>