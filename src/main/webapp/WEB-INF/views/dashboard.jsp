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
<link rel="stylesheet" href="/resources/css/chosen.min.css">
<link href="/resources/css/sourcelead-main.css" rel="stylesheet">
<link href="/resources/css/sourcelead-bundle.css" rel="stylesheet">
<link rel="stylesheet" href="/resources/css/timesheet.css">

<script src="https://maps.google.com/maps/api/js?libraries=places&v=3&key=${SourceLeadWeb_googleMapsLocationApiKey}" type="text/javascript"></script>
<!-- Don't change js file order due to some overlapping issues ordered by prasadk-->

<script src="/resources/js/jquery-1.11.1.min.js"></script>
<script src="/resources/js/masterHeaderAction.js"></script>

<script src="/resources/js/jquery-ui.js"></script>
<script src="/resources/js/chosen.jquery.min.js"></script>
<script src="/resources/js/jquery.dataTables.min.js"></script>
<script src="/resources/js/jquery.timepicker.js"></script>

<script src="/resources/js/formvalidator.js"></script>
<script src="/resources/js/bootstrap-select.min.js"></script>

<script src="/resources/js/sourcelead-app.js"></script>
<script src="/resources/js/masterHeaderAction.js"></script>
<script src="/resources/js/slead-common.js"></script>
<style>
.pac-container {
z-index: 1000000000 !important;
}
.pac-container::after {
display:none;
}
</style>
</head>
<%@page session="true"%>
    <c:if test="${staticTime == null || staticTime == 0 }">
    	<c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
    </c:if>
<!-- 
JQUERY DOs AND DONTs 
ONE AND ONLY ONE JQUERY READY
ALWAYS CACHE VARIABLES LIKE YOU DO IN JAVA
-->
<script>
var  tabAccessArray = '${SourceLeadWeb_tabsList}';
var roleActionsArray = '${SourceLeadWeb_actionsList}';
var sourceleadUrl ='${sourceleadUrl}';
var atsUrl = '${SourceLeadWeb_atsUrl}';
var pmsUrl ='${SourceLeadWeb_pmsUrl}';
var vmsUrl ='${SourceLeadWeb_vmsUrl}';
var loginUserRole = '${SourceLeadWeb_loginUserRole}';
var userId =  '${SourceLeadWeb_userId}';
var orgLocationId  = '${SourceLeadWeb_orgLocationId}';

var orgLocationCode = '${SourceLeadWeb_locationCode}';// snigitha copied from the dashboard.js
var orgCode = '${SourceLeadWeb_orgCode}';// snigitha copied from the dashboard.js

var isAuthenticated = '${SourceLeadWeb_isAuthenticated}'
var userSessionId = '${SourceLeadWeb_actionUUID}';
var userImgUrl =  '${SourceLeadWeb_userImgUrl}'; 
var locationCode ='${SourceLeadWeb_locationCode}';
var loginUser = '${SourceLeadWeb_loginUserFullName}';
var locationList = '${SourceLeadWeb_dateFormat}';	
var country = '${SourceLeadWeb_country}';
var timezone = '${SourceLeadWeb_timezone}';
var timezoneId ='${SourceLeadWeb_timeZoneId}'; 
var candidateId = '${SourceLeadWeb_candidateId}';
var isGlobalAccess = '${SourceLeadWeb_isGlobalAccess}';
var userType =  '${SourceLeadWeb_displayName}'; 
var userSessionId = '${SourceLeadWeb_actionUUID}';
var loginOrgLocationName = '${SourceLeadWeb_orgName}'; 
// below are related variable used in showing tabs in dashboard.
var bothTimesheets ='${bothTimesheets}';
var onlyTimesheets ='${onlyTimesheets}';
var onlyApprovalTimesheets ='${onlyApprovalTimesheets}';
var tokenParamName = "${_csrf.parameterName}";
var tokenValue = "${_csrf.token}";
// reruirement list variables.
var orgATS = '${SourceLeadWeb_ats}';
var orgVMS = '${SourceLeadWeb_vms}';
var requirementId = '${requirementId}'; 
var locationDate = '${SourceLeadWeb_dateFormat}';	
var searchType = '${searchType}';
</script>
<script>
  $(document).ready(function() {
	  $('body').load("/resources/sourceleadHtmls/dashboardHtml.html?staticTime=${staticTime}");
	});
</script>

