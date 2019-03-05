<!doctype html>
<%-- <jsp:include page="masterSideNav.jsp" /> --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 <sec:authorize access="hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS') && hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS')" var="bothTimesheets"/>
 <sec:authorize access="!hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS') && hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS')" var="onlyTimesheets"/>
 <sec:authorize access="hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS') && !hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS')" var="onlyApprovalTimesheets"/>
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
<link href="/resources/css/sourcelead-bundle.css" rel="stylesheet">
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
<script src="/resources/js/masterSideNav.js"></script>
<script src="/resources/js/masterHeader.js"></script>
</head>

<!-- 
JQUERY DOs AND DONTs 
ONE AND ONLY ONE JQUERY READY
ALWAYS CACHE VARIABLES LIKE YOU DO IN JAVA
-->
<script>
var  tabAccessArray = '${SourceLeadWeb_tabsList}';
var  roleActionsArray= '${SourceLeadWeb_actionsList}';
var sourceleadUrl ='${sourceleadUrl}';
var atsUrl = '${SourceLeadWeb_atsUrl}';
var pmsUrl ='${SourceLeadWeb_pmsUrl}';
var vmsUrl ='${SourceLeadWeb_vmsUrl}';
var loginUserRole = '${SourceLeadWeb_loginUserRole}';
var userId =  '${SourceLeadWeb_userId}';
var orgLocationId  = '${SourceLeadWeb_orgLocationId}';
// below are related variable used in showing tabs in dashboard.
var bothTimesheets ='${bothTimesheets}';
var onlyTimesheets ='${onlyTimesheets}';
var onlyApprovalTimesheets ='${onlyApprovalTimesheets}';
console.log("bothTimesheets "+bothTimesheets);
console.log("onlyTimesheets "+onlyTimesheets);
console.log("onlyApprovalTimesheets "+onlyApprovalTimesheets);
</script>
<script>
  $(document).ready(function() {
	  $('body').load("/resources/staticHtmls/dashboardHtml.html");
	});
</script>
