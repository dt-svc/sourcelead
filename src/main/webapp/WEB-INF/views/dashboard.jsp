<!doctype html>
<%-- <jsp:include page="masterSideNav.jsp" /> --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" type="image/png" href="/resources/images/fav.png" />
<title>Welcome to Source Lead</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<link rel="stylesheet" href="/resources/css/dashboard_3118.css" type="text/css" media="all"/>
<link rel="stylesheet" href="/resources/css/chosen.min.css">
<link href="/resources/css/sourcelead-main.css" rel="stylesheet">
<link rel="stylesheet" href="/resources/css/timesheet.css">
<script src="/resources/js/jquery-1.11.1.min.js"></script>
<script src="/resources/js/masterHeaderAction.js"></script>
<script src="/resources/js/jquery-ui.js"></script>
<script src="/resources/js/formvalidator.js"></script>
<script src="/resources/js/bootstrap-select.min.js"></script>
<script src="/resources/js/chosen.jquery.min.js"></script>
<script src="/resources/js/jquery.dataTables.min.js"></script>
<script src="/resources/js/sourcelead-app.js"></script>
<script src="/resources/js/jquery.timepicker.js"></script>
<script src="/resources/js/dashboard.js"></script>
<script src="/resources/js/masterHeaderAction.js"></script>

</head>


<script>
$(document).ready(function() {
	   getLoginUserTabsList();
		$('body').load("/resources/dashboardHtml.html");
	   //alert("hi");
		/* setTimeout(function () {
			$('body').load("/resources/dashboardHtml.html");
			}, 5000); */
	});
</script>