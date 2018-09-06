<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Finance Tracker</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.4/css/bootstrap.min.css" />

<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />

<script type="text/javascript" src="https://cdn.jsdelivr.net/jquery/latest/jquery.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>

</head>
<body>
	<form action="UsdRateController" method="POST" class="form-group">
	
		<input type="hidden" name="command" value="ADD" />
		<br/><br/>
		<h3>Choose the date <small class="text-muted">for which you want to download the rate</small></h3>
		<br/><br/>
		<input type="text" class="form-control" id="date" name="date" placeholder="Choose date" required />
		
		<script>
		$(function(){
		 $('#date').daterangepicker({
		  singleDatePicker: true,
		 });
		});
		</script>
		<br/><br/>
		<input type="hidden" name="command" value="ADD" />
		<input type="submit" class="btn btn-lg btn-primary" value="Add rate" />
	</form>
	
	<br/><br/>
	
	<table class="table table-striped">
		<tr>
			<th>Date</th>
			<th>Usd Rate</th>
			<th>Action</th>
		</tr>
		<c:forEach var="tempRate" items="${RATE_LIST }">
		<!-- set up a link for delete a student -->
		<c:url var="deleteLink" value="UsdRateController">
			<c:param name="command" value="DELETE" />
			<c:param name="date" value="${tempRate.date }" />
		</c:url>
			<tr>
				<td>${tempRate.date }</td>
				<td>${tempRate.usdRate }</td>
				<td>
					<a href="${deleteLink }">Delete</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	
	<p>
		<a href="TransactionController">Back to Financial operations</a>
	</p>




<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.2.0/js/tether.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.4/js/bootstrap.min.js"></script>	
</body>
</html>