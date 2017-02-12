<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Track your packet</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
input[type=text], select {
	width: 100%;
	padding: 12px 20px;
	margin: 8px 0;
	display: inline-block;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
}

input[type=submit] {
	width: 100%;
	background-color: #4B1388;
	color: white;
	padding: 14px 20px;
	margin: 8px 0;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	font-weight: bold;
}

input[type=submit]:hover {
	background-color: #472f91;
}

div {
	border-radius: 5px;
	background-color: #f2f2f2;
	padding: 20px;
	width: 30%;
	text-align: center;
	margin-left: 35%;
	margin-top: 15%;
}
</style>
</head>
<body>
	<div>
		<c:choose>
			<c:when
				test="${subscribed == 'true'}">
				<p style = "color: #F00;">Incorrect Tracking ID..</p>
			</c:when>
		</c:choose>
		<form name="trackidform" action="Controller" method="post"
			id="trackidform">
			<input type="hidden" name="action" value="track" /> <b><label
				for="fname">Enter the Tracking ID:</label></b> <input type="text"
				name="ID" placeholder="Tracking ID" required> <input
				type="submit" name="Submit" value="Submit">
		</form>
	</div>
</body>
</html>