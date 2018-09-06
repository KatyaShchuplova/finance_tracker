<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Finance Tracker</title>


</head>
<body>
	<h2>List Transactions</h2>
	<br/><br/>
		<table border="1">
		<tr>
			<th>Date</th>
			<th>Type</th>
			<th>Amount USD</th>
			<th>Transaction Rate</th>
		</tr>
		<c:forEach var="tempTransaction" items="${TRANSACTION_LIST }">
			<tr>
				<td>${tempTransaction.date }</td>
				<td>${tempTransaction.typeTransaction }</td>
				<td>${tempTransaction.transactionAmountUsd }</td>
				<td>${tempTransaction.transactionRate }</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>