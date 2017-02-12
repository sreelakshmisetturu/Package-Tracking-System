<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Packet History</title>
<style type="text/css">
th {
	float: left;
	padding: 5px;
}

p {
	margin: 0;
}

.greyback {
	background-color: #dedfe0;
}

#highlight {
	color: #4B1388;
}

#normalText {
	color: #88898d;
}

#headings {
	font-size: 20px;
	color: #88898d;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div style="width: 60%; margin-left: 20%">
		<div style="width: 100%; height: 30px; background-color: #4B1388"></div>
		<!-- Div for the blue line -->
		<div style="width: 100%; height: 120px;">
			<!-- Div for the Shipping details -->
			<div style="float: left; width: 60%">
				<!-- Div for the first two columns -->
				<div
					style="width: 40%; float: left; padding-left: 5%; padding-top: 2%;">
					<p id="normalText">Ship Date:</p>
					<p id="highlight">
						<b><c:out value="${start}" /></b>
					</p>
					<hr style="width: 50%; float: left;">
					<br>
					<p id="normalText">
						<c:out value="${requestScope.packetHistory.get(0).location}" />
					</p>
				</div>
				<div
					style="width: 50%; float: right; padding-left: 5%; padding-top: 3%;">
					<!-- Div for the status image -->
					<c:choose>
						<c:when
							test="${requestScope.packetHistory.get(packetHistory.size()-1).activity=='In FedEx possession'|| requestScope.packetHistory.get(packetHistory.size()-1).activity=='Picked up'}">
							<img src="tracking1.png" style="padding-top: 10%">
						</c:when>
						<c:when
							test="${requestScope.packetHistory.get(packetHistory.size()-1).activity=='Arrived at FedEx location'|| requestScope.packetHistory.get(packetHistory.size()-1).activity=='Departed at FedEx location'}">
							<img src="tracking2.JPG" style="padding-top: 10%">
						</c:when>
						<c:when
							test="${requestScope.packetHistory.get(packetHistory.size()-1).activity=='At local FedEx location'}">
							<img src="tracking3.jpg" style="padding-top: 10%">
						</c:when>
						<c:otherwise>
							<img src="tracking4.png" style="padding-top: 10%">
						</c:otherwise>
					</c:choose>
					<p style="color: #88898c; text-align: center;"><b><c:out value="${packet.status}" /></b></p>
				</div>
			</div>

			<div style="float: right; width: 30%; padding-top: 1%;">
				<!-- Div for the delivery Status. -->
				<p id="normalText">Actual delivery :</p>
				<c:choose>
					<c:when
						test="${requestScope.packetHistory.get(packetHistory.size()-1).activity=='Delivered'}">
						<p id="highlight">
							<b><c:out value="${end}" /></b>
						</p>
						<hr style="width: 50%; float: left;">
						<br>
					</c:when>
					<c:otherwise>
						<p id="highlight">
							<b>Yet to Deliver</b>
						</p>
						<hr>
					</c:otherwise>
				</c:choose>
				<p id="normalText">
					<c:out value="${packet.destination}" />
				</p>
			</div>
		</div>
		<div>
			<!-- Div for both Travel history and Shipping facts for common styling-->
			<p id="headings">Travel History</p>
			<hr>
			<div>
				<!-- Div for Travel History -->
				<table style="width: 100%">
					<!-- Table containing all travel details -->
					<td>
					<th>Time</th>
					<th>Activity</th>
					<th>Location</th>
					</td>
					<!-- Populate the table here using for each -->
					<c:forEach items="${requestScope.packetHistory}" var="item">
						<tr>
							&nbsp;
							<td><c:out value="${item.timeStamp}" />&nbsp;&nbsp;&nbsp;</td>
							&nbsp;
							<td><c:out value="${item.activity}" />&nbsp;&nbsp;&nbsp;</td>
							&nbsp;
							<td><c:out value="${item.location}" />&nbsp;&nbsp;&nbsp;</td>
						<tr>
					</c:forEach>
				</table>
			</div>
			<div class="shippingFacts">
				<!-- Div for the Shipping facts. -->
				<br>
				<p id="headings">Shipping Facts</p>
				<hr>
				<div style="width: 45%; float: right;">
					<!-- Div for each cloumn and table for each side. -->
					<table>
						<!-- table for services, dimensions and total pieces -->
						<tr class="greyback">
							<th>Tracking number: <c:out value="${ID}" /></th>
						<tr></tr>
						</tr>
						<tr>
							<th>Weight:<c:out value="${packet.weight}" /> lbs
							</th>
						<tr></tr>
						</tr>
						<tr class="greyback">
							<th>Packing:<c:out value="${packet.packaging}" /></th>
						<tr></tr>
						</tr>
					</table>
				</div>
				<div style="width: 50%; float: right;">
					<!-- Second div for the second table -->
					<table>
						<!-- Table for the contents on the right side -->
						<tr class="greyback">
							<th>Services:<c:out value="${packet.service}" /></th>
						<tr></tr>
						</tr>
						<tr>
							<th>Dimensions:<c:out value="${packet.dimensions}" /></th>
						<tr></tr>
						</tr>
						<tr class="greyback">
							<th>Total Pieces:<c:out value="${packet.pieces}" /></th>
						<tr></tr>
						</tr>
					</table>
				</div>
				<!-- Right side table -->
			</div>
			<!-- Shipping Facts -->
		</div>
		<!-- Travel details -->
	</div>
	<!-- The whole travel div -->
	 <a href = "home.jsp" style="margin-left: 40%; margin-right: auto; display: block;"> Track another package >></a>
</body>
</html>