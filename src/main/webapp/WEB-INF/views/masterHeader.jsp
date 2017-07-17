<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" type="image/png" href="resources/images/fav.png" />
<title>Welcome to Source Lead :: The Recruitment Application</title>
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="stylesheet" href="resources/css/bootstrap-select.min.css">
<link href="resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- <link href="resources/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css"> -->
<link href="resources/css/dataTablePagination.css" rel="stylesheet" type="text/css">
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/chosen.min.css">
<link rel="stylesheet" href="resources/css/jquery.timepicker.css">
<link href="resources/css/sourcelead-main.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/timesheet.css" type="text/css" >
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <script src="https://maps.google.com/maps/api/js?libraries=places&v=3&key=AIzaSyDzIM1uth14u-kSIN_jZ75pRelInyGnYWU" type="text/javascript"></script>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
<jsp:include page="masterJs.jsp" />
</head>
<!-- <script src="resources/js/jquery-1.11.1.min.js"></script> -->
<script src="resources/js/chosen.jquery.min.js"></script>
<script src="resources/js/jquery-ui.js"></script>
<script src="resources/js/jquery-fun.js"></script>
<script src="resources/js/jquery.dataTables.min.js"></script>
<script src="resources/js/jquery.timepicker.js"></script>
<script src="resources/js/formvalidator.js"></script>
<script src="resources/js/bootstrap-select.min.js"></script>
<script src="resources/js/themeswitcher.js"></script>
<script src="resources/js/moment.min.js"></script>
<script src="resources/js/datetime-moment.js"></script>
<style>
.dashboard-widget.dashboard-widget-icons{
min-height: 225px !important;
}
.dashboard-widget .blank-pages{
padding: 0;
min-height: 100px;
}
/* .side-bar .side-nav ul li span {
display: inline-block !important;
float: none !important;
} */
.custom-datatble table{
 margin-top: 20px !important;
 }
 .custom-datatble .dataTables_filter{
   top: -35px !important;
    padding: 0 !important;
 }
 .dashboard-datepkr{
       position:relative;
}
/* .main-header span.logo i{
position: relative;
top:10px;
} */
.ui-timepicker-wrapper {
z-index: 999999;
width: 11.5%;
}
 
.addTask-widget.addTask-widget-active {
	z-index:1000 !important;
}
.addTask-widget .require_wrap_table {
	overflow: visible !important;
}

.newdate {
	padding: 0px; position: relative;
}
.newdate .fa-filter {
	position: absolute; left: 2px; top: 1px; z-index: 100; font-size: 17px; color: #666; height: 28px; width: 34px;
	background-color: #f1f1f1; text-align: center; padding: 7px 0px 0px 0px;
}
.newdate .dashboard-datepkr {
	max-width:200px !important; width:200px !important;
}
.TaskDate {
	width: 190px !important; padding: 6px 35px 6px 45px !important; background: #fff !important;
}

.newdate .fa-calendar {
	position: absolute; font-size: 16px; color: #666; top: 8px; right: 18px; 
}
.pac-container {
z-index: 1000000000 !important;
}
.pac-container::after {
	display:none;
}
</style>
<!-- <script>
$(window).load(function() {
	var url = window.location.href;
      alert("window load occurred!"+url);
});
</script> -->

<!-- <script>
var BM = 2; // button middle
var BR = 3; // button right
var msg ="MOUSE RIGHT CLICK IS NOT SUPPORTED ON THIS PAGE";
function mouseDown(e) 
{ 
  try { if (event.button==BM||event.button==BR) {return false;} }  
  catch (e) { if (e.which == BR) {return false;} } 
}
document.oncontextmenu = function() { alert(msg); return false; }
document.ondragstart   = function() {alert(msg);return false; }
document.onmousedown   = mouseDown;
</script> 
<script>
var isCtrl = false;
document.onkeyup=function(e){
    if(e.which == 17) isCtrl=false;
}
document.onkeydown=function(e){
    if(e.which == 17) isCtrl=true;
    if(e.which == 85 && isCtrl == true) {
    	e.preventDefault();
        alert('CTRL + U IS NOT SUPPORTED ON THIS PAGE');
        return false;
    }
    else if(e.which == 123){
    	e.preventDefault();
    	 alert('F12 IS NOT SUPPORTED ON THIS PAGE');
         return false;
     }
}
</script>  -->

<!--
	Key Codes events
	0 = blur
	13 = enter
	9 = tab
	32 = space
	188 = comma
-->
 
<script type="text/javascript">
function tagItBykeyForEmails(e){
	if(e.which == 0 || e.which == 13 || e.which == 9 || e.which == 32 || e.which == 188){
		 $(document).click();
		 return true;
	}
}
</script>

<script type="text/javascript">
function tagItBykeyForSkills(e){
	if(e.which == 0 || e.which == 13 || e.which == 9 || e.which == 188){
		 $(document).click();
		 return true;
	}
}
</script>

<script type="text/javascript">
function preventTabkey(e){
	 if(e.which == 9){
		 e.preventDefault();
	 }
}
</script>

<script>
	function predefineOrgImage(imageId){
		$(imageId).attr('src','resources/images/office-image.jpg');
	}
</script>

<script>
	function predefineProfileImage(imageId){
		$(imageId).attr('src','resources/images/userprofile-img.png');
	}
</script>

<script>
		function logoutFormSubmit() {
			document.getElementById("logoutForm").submit();
		}
</script>

<script>
		function linkFormSubmit(orgLocationId) {
			document.getElementById("logoutForm").action="${sourceleadUrl}/dashboard/"+orgLocationId
			document.getElementById("logoutForm").submit();
		}
</script>


<body>
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<div class="wraper"> 
  <!-- Header Area -->
  <header class="main-header">
  <span class="logo"> 
  <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER' && SourceLeadWeb_prodSupportLoginUserRole == null}">
  <a href="dashboard/${SourceLeadWeb_orgLocationId}" title="Source Lead">
  </c:if>
  
   <c:if test="${SourceLeadWeb_prodSupportLoginUserRole == 'ROLE_PRODUCTION_SUPPORT'}">
   <a href="productionSupportdashboard/${SourceLeadWeb_orgLocationId}/${SourceLeadWeb_prodSupportOrgLocationId}" title="Source Lead">
   </c:if>
  
  
  
  <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL'}">
  <a href="${sourceleadUrl}/professionalDashboard" title="Source Lead">
  </c:if>
  
  <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
  <a href="${sourceleadUrl}/recruiterDashboard" title="Source Lead">
  </c:if>
  <i>
 	 <c:if test="${!empty SourceLeadWeb_org.fileContentType}">
 	 	<img src="<c:url value='/displayOrgImage/${SourceLeadWeb_orgCode}'/>" title="${SourceLeadWeb_orgName}" class="img-responsive mt5">
 	 </c:if>
 	 
 	 <c:if test="${empty SourceLeadWeb_org.fileContentType}">
				<div class="text-logo">
				 <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
					<c:if test="${!empty SourceLeadWeb_orgName}">
					<span class="tbldata-dotdot" title="${SourceLeadWeb_orgName}">${SourceLeadWeb_orgName}</span>
					</c:if>
				</c:if>
				<c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL' || SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
				<span class="tbldata-dotdot" title="${SourceLeadWeb_displayName}">${SourceLeadWeb_displayName}</span>
				</c:if>
				</div>
	</c:if>
 	
  </i>
    </a> </span>
    <nav class="header-nav navbar">
    <a href="javascript:void(0)" class="side-menu"> <i class="fa fa-bars pull-left"></i> </a>
      <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
      <div class="search-area searchArea_mrgn">
           <form:form name ="search_form" id="search_form">
			<div class="uasrch-selectpicker">
			<select id="searchType" name="searchType" class="selectpicker uasrch pull-left show-menu-arrow" >
			<option data-icon="fa fa-th-large" ${searchType == "All" ?"selected='selected'":""} value="All">All</option>
			<c:forEach var="userTabs" items="${SourceLeadWeb_tabsList}">
		       <c:if test="${userTabs.tabName == 'Users'}">
	                 <option data-icon="fa fa-users"  ${searchType == "Users" ?"selected='selected'":""} value="Users">Users</option>
	           </c:if>
	           <c:if test="${userTabs.tabName == 'Jobs'}"> 
	                <option data-icon="fa fa-briefcase" ${searchType == "Requirement" ?"selected='selected'":""} value="Requirement">Jobs</option>
	           </c:if>
	           <c:if test="${userTabs.tabName == 'Candidates'}">
	                <option data-icon="fa fa-user-circle" ${searchType == "Candidate" ?"selected='selected'":""} value="Candidate">Candidates</option>
	           </c:if> 
	           <c:if test="${userTabs.tabName == 'Clients'}">
                 	  <option data-icon="fa fa-user-plus" ${searchType == "Client" ?"selected='selected'":""} value="Client">Clients</option>
               </c:if>
	           <c:if test="${userTabs.tabName == 'Vendors'}">
	                  <option data-icon="fa fa-suitcase" ${searchType == "Vendor" ?"selected='selected'":""} value="Vendor">Vendors</option>
	           </c:if> 
	           <c:if test="${userTabs.tabName == 'Projects'}">
	             <option data-icon="fa fa-bar-chart" ${searchType == "Projects" ?"selected='selected'":""} value="Projects">Projects</option>   	  
	           </c:if>
	           
	           <c:if test="${userTabs.tabName == 'Associates List'}">
	           <option data-icon="fa fa-black-tie" ${searchType == "Associate" ?"selected='selected'":""} value="Associate">Associates</option>
	           </c:if>
			</c:forEach>
	             
		</select>
			
			
			<%-- <select id="searchType" name="searchType" class="selectpicker uasrch pull-left show-menu-arrow" >
			<option data-icon="fa fa-th-large" ${searchType == "All" ?"selected='selected'":""} value="All">All</option> --%>
			<%-- <option data-icon="fa fa-users"  ${searchType == "Users" ?"selected='selected'":""} value="Users">Users</option> --%>
			<%-- <option data-icon="fa fa-briefcase" ${searchType == "Requirement" ?"selected='selected'":""} value="Requirement">Requirement</option> --%>
			<%-- <option data-icon="fa fa-user-circle" ${searchType == "Candidate" ?"selected='selected'":""} value="Candidate">Candidate</option> --%>
			<%-- <option data-icon="fa fa-user-plus" ${searchType == "Client" ?"selected='selected'":""} value="Client">Clients</option> --%>
			<%-- <option data-icon="fa fa-suitcase" ${searchType == "Vendor" ?"selected='selected'":""} value="Vendor">Vendors</option> --%>
			<%-- <option data-icon="fa fa-bar-chart" ${searchType == "Projects" ?"selected='selected'":""} value="Projects">Projects</option> --%>
			<!-- </select> -->
			</div>
			
			
			
			<div class="pull-left universalSrch">
			<input type="text" id="searchKey" name="searchKey" class="form-control input-sm" placeholder="Search" onkeypress="handle(event);"/>
			 <button type="button" class="srcbutton" value="searchValue" id="searchValue" onclick="universalSearch();" ><i class="fa fa-search"></i></button>
			</div>
			</form:form>
			</div> 
     </c:if>
<div class="custom-navbar">
        <!-- login User details -->
        <div class="login-user">
        <span class="image">
        <img class="img-circle" alt="${SourceLeadWeb_userType}" title="${SourceLeadWeb_userName}" onerror="predefineProfileImage(this);"  src="<c:url value='/displayUserImage/${SourceLeadWeb_userId}'/>"> </span> <span class=" hidden pull-left login-user-xs tbldata-dotdot-vsm" title="${SourceLeadWeb_userName}"> ${SourceLeadWeb_userName} </span>
          <div class="user-details">
            <div class="user-media">
              <div class="logn-image">
              <img class="img-circle" title="${SourceLeadWeb_userName}" alt="${SourceLeadWeb_userType}" onerror="predefineProfileImage(this);" src="<c:url value='/displayUserImage/${SourceLeadWeb_userId}'/>"> </div>
              <div class="user-media-body">
                <p><span>${SourceLeadWeb_userName}</span></p>
                <p>${SourceLeadWeb_displayName}</p>
              </div>
            </div>
            <ul class="list-unstyled">
              <li><a href="<c:url value='/userProfile'/>"><span><i class="fa fa fa-user"></i></span>&nbsp;Profile</a></li>
              <li><a href="javascript:logoutFormSubmit()"><span><i class="fa fa-sign-out"></i></span>&nbsp;Log Out</a></li>
            </ul>
          </div>
        </div>
        <!-- login User details -->
        <ul class="list-inline list-unstyled pull-left custom-navbar-inner">
          <li class="srch-btn-xs"> <a title="Search"><i class="fa fa-search"></i></a></li>
           <%-- <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_ADMIN' || SourceLeadWeb_loginUserRole == 'SOURCELEAD_ADMIN'}">
	          <li> <a href="javascript:void(0)" title="Quick Links" id="qlinks"> <i class="fa fa-link"></i> </a>
	              <div class="qlinks-area ">
	              <ul class="list-unstyled">
	                <li><a href="${sourceleadUrl}/userCreation"><i class="fa fa-users"></i>&nbsp;Create User</a></li>
	                <li><a href="${sourceleadUrl}/client"><i class="fa fa-user-plus"></i>&nbsp;Create Client</a></li>
	                <li><a href="${sourceleadUrl}/project"><i class="fa fa-bar-chart"></i>&nbsp;Create Project</a></li>
	                <li><a href="${sourceleadUrl}/uploadForm"><i class="fa fa-download"></i>&nbsp;Import Users</a></li>
	              </ul>
	            </div>
	          </li>
            </c:if> --%>
            
            <li class="reportsH">
					<a title="Invite" data-toggle="modal" id="invitePop"><i class="fa fa-send"></i></a>
	        </li>
            
            <li class="reportsH">
					<a title="Contacts" href="<c:url value='${sourceleadUrl}/referRequirementList'/>"><i class="fa fa-newspaper-o"></i></a>
	        </li>
            
            <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
	            <li class="reportsH">
					<a title="Reports" href="<c:url value='${sourceleadUrl}/recruitersReports'/>"><i class="fa fa-pie-chart"></i></a>
	            </li>
            </c:if>
            
            <sec:authorize access="hasAnyRole('ROLE_MAIL_BOX','ROLE_HOTLIST_CAMPAIGN')">
            	<li><a href="<c:url value='${sourceleadUrl}/mailBox'/>" title="Mailbox" id="mailbox"><i class="fa fa-envelope"></i>  <span class="label label-success" id="unreadMailsCount">${SourceLeadWeb_unReadMailsCount > 0 ?SourceLeadWeb_unReadMailsCount : ''}</span> </a></li>
            </sec:authorize>
            <c:if test="${!empty SourceLeadWeb_userSpocRolesList}">
            <li> <a href="javascript:void(0)" title="Locations" id="spoclinks" onclick="JavaScript: navtoggle(this);"> <i class="fa fa-globe"></i> </a>
	              <div id="spockLinksList" style="display: none;">
	              <ul class="list-unstyled">
	              <c:forEach var="links" items="${SourceLeadWeb_userSpocRolesList}">
		              <li>
			              
			              <c:if test="${SourceLeadWeb_prodSupportLoginUserRole == null && SourceLeadWeb_loginUserRole != 'ROLE_PRODUCTION_SUPPORT'}">
			              
				              <c:if test="${links.roles.displayName != 'Professional' && links.roles.displayName != 'Independent Recruiter'}">
					              <a href="<c:url value='${sourceleadUrl}/dashboard/${links.orgLocations.id}'/>" class="sopcSwith">
						                <div class="clearfix">
						                   <div class="pull-left image-parent positionRelative">
						                   <div class="imageParent org-imagelink ${links.orgLocations.id == SourceLeadWeb_orgLocationId && SourceLeadWeb_switchUserType == null ? 'active-comp' : ''}">
						                     <img onerror='predefineOrgImage(this);' src="${sourceleadUrl}/displayOrgImage/${links.orgLocations.org.orgCode}">
						                     </div>
						                   </div>
						                   <div class="pull-left">
						                     <p class="tbldata-dotdot"><i class="fa fa-building"></i>&nbsp;${links.orgLocations.orgLocationName}</p>
						                     <p class="tbldata-dotdot"><i class="fa fa-map-marker"></i>&nbsp;${!empty links.orgLocations.city ? links.orgLocations.city : ""} ${links.orgLocations.country}</p>
						                     <p><i class="fa fa-user"></i>&nbsp;${links.roles.displayName}</p>
						                   </div>
						                </div>
					                </a>
				              </c:if>
				              
				              <c:if test="${links.roles.displayName == 'Professional'}">
				              	<a href="<c:url value='${sourceleadUrl}/dashboard/${links.orgLocations.id}/${links.userType}'/>" class="sopcSwith">
						                <div class="clearfix">
						                   <div class="pull-left image-parent positionRelative">
						                   <div class="imageParent org-imagelink ${links.orgLocations.id == SourceLeadWeb_orgLocationId && SourceLeadWeb_switchUserType == 'Professional' ? 'active-comp' : ''}">
						                     <img onerror='predefineOrgImage(this);' src="${sourceleadUrl}/displayOrgImage/${links.orgLocations.org.orgCode}">
					                       </div>
						                   </div>
						                   <div class="pull-left">
						                     <p class="tbldata-dotdot"><i class="fa fa-building"></i>&nbsp;${links.orgLocations.orgLocationName}</p>
						                     <p><i class="fa fa-user"></i>&nbsp;Job Seeker</p>
						                   </div>
						                </div>
					                </a>
				              </c:if>
				              
				              <c:if test="${links.roles.displayName == 'Independent Recruiter'}">
				              	<a href="<c:url value='${sourceleadUrl}/dashboard/${links.orgLocations.id}/${links.userType}'/>" class="sopcSwith">
						                <div class="clearfix">
						                   <div class="pull-left image-parent positionRelative">
						                   <div class="imageParent org-imagelink ${links.orgLocations.id == SourceLeadWeb_orgLocationId && SourceLeadWeb_switchUserType == 'Recruiter' ? 'active-comp' : ''}">
						                     <img onerror='predefineOrgImage(this);' src="${sourceleadUrl}/displayOrgImage/${links.orgLocations.org.orgCode}">
					                       </div>
						                   </div>
						                   <div class="pull-left">
						                     <p class="tbldata-dotdot"><i class="fa fa-building"></i>&nbsp;${links.orgLocations.orgLocationName}</p>
						                     <p><i class="fa fa-user"></i>&nbsp;Freelance Recruiter</p>
						                   </div>
						                </div>
					                </a>
				              </c:if>
				              
			              </c:if>
			              
			              <c:if test="${SourceLeadWeb_prodSupportLoginUserRole == 'ROLE_PRODUCTION_SUPPORT'}">
		              			<a href="<c:url value='${sourceleadUrl}/productionSupportdashboard/${links.id}/${SourceLeadWeb_prodSupportOrgLocationId}'/>" class="sopcSwith">
					                <div class="clearfix">
					                   <div class="pull-left image-parent positionRelative">
					                   <div class="imageParent org-imagelink ${links.id == SourceLeadWeb_orgLocationId  ? 'active-comp' : ''}">
					                     <img onerror='predefineOrgImage(this);' src="${sourceleadUrl}/displayOrgImage/${links.org.orgCode}">
					                     </div>
					                   </div>
					                   <div class="pull-left">
					                     <p class="tbldata-dotdot"><i class="fa fa-building"></i>&nbsp;${links.orgLocationName}</p>
					                     <p class="tbldata-dotdot"><i class="fa fa-map-marker"></i>&nbsp;${!empty links.city ? links.city : ""} ${links.country}</p>
					                   </div>
					                </div>
				                </a>
			              </c:if>
			              
		              </li>
	              </c:forEach>
	              </ul>
	            </div>
	          </li>
	          </c:if>
         
          <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
          <li id="alert"> 
          <a href="#" title="Alerts" id="alert" onmouseover="notificationStatusAsRead();" > <i class="fa fa-bell bell" ></i> <span class="label label-warning">${SourceLeadWeb_notificationCount > 0 ? SourceLeadWeb_notificationCount : ''}</span></a>
            <div class="header-widget alert-area">
              <ul class="list-unstyled">
              <c:if test="${empty SourceLeadWeb_notificationList}">
              	<div class="clearfix msg_blank">
                     All Caught UP!!! 
                  </div>
              	</c:if>
               <c:if test="${!empty SourceLeadWeb_notificationList}">
                <c:forEach var="report" items="${SourceLeadWeb_notificationList}" end="4">
                <li>
                  <div class="clearfix msg">
                    <div class="msg-img"> <span>
                    <img class="img-circle" title="${report.notificationFrom.firstName} ${report.notificationFrom.lastName}" alt="${report.notificationFrom.userType}" onerror="predefineProfileImage(this);" src="<c:url value='/displayUserImage/${report.notificationFrom.id}'/>">
                    </span> </div>
                    <div class="msg-content">
                    <c:if test="${report.actionLink != ''  && report.actionLink !='undefined' && ! empty report.actionLink}">
                     <a href="${report.actionLink}"><p>${report.body}</p>
                     <c:out value="${userId}" />
                      <%-- <span>${report.calDays} days ago at ${report.time} ${report.dateValue}</span></a> --%>
                      <span>${report.calDays}</span></a> 
                      </c:if>
                    <c:if test="${empty report.actionLink}">
                     <a href="#"><p>${report.body}</p>
                     <c:out value="${userId}" />
                      <%-- <span>${report.calDays} days ago at ${report.time} ${report.dateValue}</span></a>  --%>
                      <span>${report.calDays}</span></a> 
                  	</c:if>
                   </div>
                  </div>
                </li>
                </c:forEach>
                </c:if>
              </ul>
              <div class="headerwidget-footer"> <a href="<c:url value='/notificationsList'/>">View All</a> </div>
            </div>
          </li>
          </c:if>
          
          <sec:authorize access="!hasRole('ROLE_CROWDSOURCING_REQUIREMENTS')"> 
           <sec:authorize access="hasAnyRole('ROLE_ENROLL_ORG','ROLE_ENROLL_NEW_ROLE','ROLE_VIEW_EDIT_ORG_SETTINGS','ROLE_CREATE_APPLICANT_TEMPLATE','ROLE_VIEW_APPLICANT_TEMPLATE','ROLE_VIEW_EDIT_APPLICANT_TEMPLATE','ROLE_ADD_CONTRACT_TEMPLATE','ROLE_VIEW_EDIT_CONTRACT_TEMPLATE','ROLE_CREATE_ON_BOARD_TEMPLATE','ROLE_VIEW_EDIT_ON_BOARD_TEMPLATE','ROLE_CREATE_OFF_BOARD_TEMPLATE','ROLE_VIEW_EDIT_OFF_BOARD_TEMPLATE')"> 
	            <li id="settingArea"> <a href="#" title="Settings" class="settings"> <i class="fa fa-cog fa-spin fa-fw"></i></a>
	            <div class="setting-area">
	              <ul class="list-unstyled">
	                     <sec:authorize access="hasAnyRole('ROLE_ENROLL_ORG')"> 
	                     <li><a href="<c:url value="/enrolledOrgsList" />"><i class="fa fa-cubes" aria-hidden="true"></i>View Orgs</a></li>
	                     </sec:authorize>
	                     
	                     <sec:authorize access="hasAnyRole('ROLE_ENROLL_NEW_ROLE')"> 
	                        <li><a href="<c:url value="/orgRolesList" />"><i class="fa fa-key" aria-hidden="true"></i>Role Configuration</a></li>
	                     </sec:authorize>
	                      <sec:authorize access="hasRole('ROLE_VIEW_EDIT_ORG_SETTINGS')"> 
	               		      <li><a href="<c:url value="/orgSettings" />"><i class="fa fa-gears" aria-hidden="true"></i>Org Settings</a></li>
	               		 </sec:authorize>
	                     
	                     <sec:authorize access="hasAnyRole('ROLE_CREATE_APPLICANT_TEMPLATE','ROLE_VIEW_APPLICANT_TEMPLATE','ROLE_VIEW_EDIT_APPLICANT_TEMPLATE','ROLE_ADD_CONTRACT_TEMPLATE','ROLE_VIEW_EDIT_CONTRACT_TEMPLATE','ROLE_CREATE_ON_BOARD_TEMPLATE','ROLE_VIEW_EDIT_ON_BOARD_TEMPLATE','ROLE_CREATE_OFF_BOARD_TEMPLATE','ROLE_VIEW_EDIT_OFF_BOARD_TEMPLATE')"> 
	                     <li><a href="<c:url value="/orgTemplates" />"><i class="fa fa-newspaper-o" aria-hidden="true"></i>Templates</a></li>
						 </sec:authorize>

						  <sec:authorize access="hasAnyRole('ROLE_ADD_EXPENSE_TYPE','ROLE_VIEW_EDIT_EXPENSE_TYPE','ROLE_DELETE_EXPENSE_TYPE')">
						  	<li><a href="<c:url value="/expenseCategory" />"><i class="fa fa-file-text" aria-hidden="true"></i>Expense Category</a></li>
						  </sec:authorize>
						  <li><a href="<c:url value="/bankAccountDetails" />"><i class="fa fa-university" aria-hidden="true"></i>Account</a></li>
	              </ul>
	            </div>
	          </li>
          </sec:authorize>
         </sec:authorize>
         
         <sec:authorize access="hasRole('ROLE_CROWDSOURCING_REQUIREMENTS')">
            	<li id="settingArea"><a href="#" title="Settings" class="settings"><i class="fa fa-cog fa-spin fa-fw"></i></a>
            	<div class="setting-area">
            	<ul class="list-unstyled">
            	<li><a href="<c:url value="/bankAccountDetails" />"><i class="fa fa-university" aria-hidden="true"></i>Account</a></li>
            	
            	<li><a href="<c:url value="/jobAlertList" />"><i class="fa fa-bell-o" aria-hidden="true"></i>Alerts</a></li>
            	</ul>
            	</div>
            	</li>
         </sec:authorize>
         
          <%-- <c:if test="${SourceLeadWeb_orgSettingsVisible == 'Y' && SourceLeadWeb_enrollOrgVisible != 'Y'}">
	          <li> <a href="#" title="Settings" class="settings"> <i class="fa fa-cog fa-spin fa-fw"></i></a>
	            <div class="setting-area">
	              <ul class="list-unstyled">
                     <li><a href="<c:url value="/orgSettings" />"><i class="fa fa-gears" aria-hidden="true"></i>Org Settings</a></li>
                     <li><a href="<c:url value="/orgRolesList" />"><i class="fa fa-key" aria-hidden="true"></i>Enroll Roles</a></li>
                     <li><a href="<c:url value="/orgSubmissionTemplate" />"><i class="fa fa-newspaper-o" aria-hidden="true"></i>Templates</a></li>
	                     
	              </ul>
	            </div>
	          </li>
          </c:if> --%>
          
          <li class="flag-image"><a><img src="resources/images/${SourceLeadWeb_country}.gif" title="${SourceLeadWeb_country}"></a></li>

        </ul>
      </div>
    </nav>
  </header>
  
  
  <script>
	$('.sopcSwith').each(function() {
		var $this = $(this);
		$this.on("contextmenu",function(e){
			  return false;
		});
	})
  </script>
  
  <script>
  function navtoggle(elementRef){
	 if('${SourceLeadWeb_userSpocRolesListCount}' < 6){
		 document.getElementById("spockLinksList").removeAttribute("class")
		 document.getElementById('spockLinksList').className = 'spoclinks-area'
	     document.getElementById("spockLinksList").style.display="block";
	 }else{
		 document.getElementById("spockLinksList").removeAttribute("class")
		 document.getElementById('spockLinksList').className = 'spoclinks-area spoclinksMore'
		 document.getElementById("spockLinksList").style.display="block";
	 }
  }
  $('html').click(function(){
	  $('#spockLinksList').hide();
  });
  </script>
  
  <script>
function notificationStatusAsRead(){   
	var sourceleadUrl = '${sourceleadUrl}';
	var  orgCode ='${SourceLeadWeb_orgCode}';
	 var locationCode = '${SourceLeadWeb_locationCode}';
	 $.ajax({
	      type: "POST",
	  	  url: sourceleadUrl+"/notificationStatusAsRead?${_csrf.parameterName}=${_csrf.token}&orgCode="+orgCode+"&locationCode="+locationCode,
	  			  success :function(data) {
	  				 //$("span.label-warning").text(0);
      			 }							     
	}); 
	 /* $("#alert").click(function(){
         $("span.label-warning").text(0);
	 }); */
}
</script>

  <!-- Header Area --> 
  
  
  <!-- Task related code Start -->
  
  <!-- Add Task Div -->
		 <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
		 <div class="addTask"> <span class="icon-addTask" title="Add Task"> <i class="fa fa-gears"></i> </span>
		    <div class="clearfix col-xs-12 col-md-12 addTask-widget">
		    <i class="fa fa-close close-div taskpop-close"></i>  
		      <!-- Assign Task Widget tabs -->
		      <div class="clearfix require_wrap_table dashboard-data" id="myTask">
		        <div class="addtsk-btn"> <span class="btn btn-success btn-sm" onClick="showtaskdiv()"><i class="fa fa-plus"></i>&nbsp;&nbsp;Add Task</span> </div>
		        <ul class="nav nav-tabs">
		          <li id="myTaskDivId"><a data-toggle="tab" href="#my-task" onclick="tasksList('myTask');">My Tasks</a></li>
		          <li id="assignedTasksDivId"><a data-toggle="tab" href="#other-task" onclick="assignedTasksList('assignedTasks');">Assigned Tasks</a></li>
		          <li id="closedTasksDivId"><a data-toggle="tab" href="#closed-task" onclick="closedTasksList('closedTasks');">Closed Task</a></li>
		        </ul>
				        <div class="tab-content clearfix">
				          <div id="my-task" class="tab-pane fade in active">
				               <div class="col-md-6 clearfix newdate">
				                    <i class="fa fa-filter"></i>
									 <span class="dashboard-datepkr"><input class="form-control TaskDate input-sm"  name="myTaskDatpicker" id="myTaskDatpicker"  type="text" readonly="readonly">
									<i class="fa fa-calendar mr"></i></span>
				               </div>	
				          
				            <div class="clearfix require_wrap_table">
				               <div id="myTasksId" class="datatable_filter"></div>
				              
				            </div>
				          </div>
				          
				          <div id="other-task" class="tab-pane fade">
							 <div class="col-md-6 clearfix newdate">
			                    <i class="fa fa-filter"></i>
								 <span class="dashboard-datepkr"><input class="form-control TaskDate input-sm calendar"  name="assignedTaskDatpicker" id="assignedTaskDatpicker"  type="text" readonly="readonly" >
								<i class="fa fa-calendar mr"></i></span>
				               </div>								
					            <div class="clearfix require_wrap_table">
					              <div id="assignedTasksId" class="datatable_filter"></div>
					            </div>
				          </div>
				          
				          <div id="closed-task" class="tab-pane fade">
				             <div class="col-md-6 clearfix newdate">
				                    <i class="fa fa-filter"></i>
									 <span class="dashboard-datepkr"><input class="form-control TaskDate input-sm"  name="closedTaskDatpicker" id="closedTaskDatpicker" type="text" readonly="readonly">
									<i class="fa fa-calendar mr"></i></span>
				               </div>
				            <div class="clearfix require_wrap_table">
				               <div id="closedTasksId" class="datatable_filter"></div>
				            </div>
				          </div>
				        </div>
		      </div>
		      <!-- Assign Task Widget tabs Ends --> 
		      
		      <!-- Add New Task Widget -->
			      <div class="newTask-widget clearfix">
			      <form id="task_form" name="task_form" method="post">
			        <div class="col-md-12"> <a class="pull-right text-danger" onClick="taskwidget();"><i class="fa fa-times"></i></a>
			          <form class="form-horizontal clearfix">
			            <h4>Add Task</h4>
			            <div class="row">
			              <div class="col-md-6" >
			                <label>Assign To<span class="text-danger">*</span></label>
			                <select class="chosen-select" multiple data-placeholder="Select User" id="assignTo" name="assignTo" field-name="AssignTo"  data-validation="required">
				                <%--  <c:forEach var="user" items="${SourceLeadWeb_userRoleList}">
					                     <option value="${user.userId}" >${user.userName}</option>
				                </c:forEach> --%>
			                </select>
			              </div>
			              <div class="col-md-6 dropdown-select">
			                <label>Priority</label>
			                <select class="form-control" id="priority" name="priority">
			                  <option selected disabled>-- Select Priority --</option>
				              <option>Urgent</option>
							  <option>High</option>
							  <option>Normal</option>
							  <option>Low</option>
			                </select>
			              </div>
			              <div class="col-md-6">
			                  <div class="row">
				                  <div class="col-md-6" id="taskDatpickerDiv" >
					                <label>Date<span class="text-danger">*</span></label>
						                <div class="positionRelative"> 
						                  <input class="form-control input-sm" value="" name="taskDatpicker"  readonly="readonly" id="taskDatpicker" type="text" field-name="Date"  data-validation="required">
						                  <span class="input-group-addon input-groupAddon icon-Addon"><i class="fa fa-calendar"></i></span>
						                </div>
				                   </div>
				                  <div class="col-md-6">
					                <label>Time<span class="text-danger">*</span></label>
						                <div class="positionRelative">
						                  <input class="form-control input-sm"  name="timepicker" id="timepicker"  field-name="Time" type="text" data-validation="required">
						                  <span class="input-group-addon input-groupAddon icon-Addon" ><i class="fa fa-clock-o"></i></span>
						                </div>
				                  </div>
			                  </div>
			              </div>
			                
			                <div class="col-md-6 dropdown-select">
			                <label>Repeat</label>
			                <select class="form-control input-sm" name="repeat" id="repeat">
			                  <option selected disabled>-- Select Repeat --</option>
			                  <option>Never</option>
			                  <option>Daily</option>
			                  <option>Weekly</option>
			                  <option>Monthly</option>
			                  <option>Yearly</option>
			                </select>
			              </div>
			              <div class="col-md-6">
			                <label>Description</label>
			                <textarea rows="2" placeholder="Description" class="form-control" name="description" id="description" data-validation="validate_length length10-500" data-validation-optional="true"></textarea>
			              </div>
			                <div class="col-md-6">
			                <label>Subject <span class="text-danger">*</span></label>
			                <input type="text" class="form-control input-sm" placeholder="Subject"  id="subject" name="subject" data-validation="required validate_Space validate_length length2-100" field-name="Subject">
			              </div>
			              <div class="col-md-6">
			                <label class="checkbox-inline">
			                	<input type="checkbox" value="Email" name="email" checked>Email
			                 </label>
			                <label class="checkbox-inline">
			                    <input type="checkbox" name="notification" value="Notification">Notification
			                </label>
			                 <!-- <label class="checkbox-inline">
			                  <input type="checkbox" name="chkbox">Pop-up WIndow
			                </label> -->
			              </div>
			              <div class="clearfix"></div>
			              <div class="clearfix mt col-md-12">
			                <div class="pull-right">
			                  <input type="button" class="btn btn-primary btn-sm" value="Submit" onclick="saveTask();">
			                  <input type="button" class="btn btn-default btn-sm ml10" value="Cancel"  onclick="taskwidget();">
			                </div>
			              </div>
			            </div>
			          </form>
			        </div>
			       </form>
			      </div>
		      <!-- Add New Task Widget --> 
		    </div>
		  </div>
		  </c:if>

<div class="addtAsk_popmask"></div>


<!--  Theme Switcher div -->
  <div class="theme-color">
      <span class="icon-theme theme-styels" title="click here">
          <figure class="rotate">
            <img src="resources/images/theme-icon.png" class="img-responsive" title="Themes">
          </figure>
      </span>
    <ul class="list-unstyled">
      <li><a href="#" class="" id="btn1"><span class="dark-blue"></span><span class="light-grey"></span></a></li>
      <li><a href="#" class="" id="btn2"><span class="flat-green"></span><span class="dark-purle"></span></a></li>
      <li><a href="#" class="" id="btn3"><span class="hdr-blue"></span><span class="sidenav-blue"></span></a></li>
      <li><a href="#" class="" id="btn4"><span class="hdr-grey"></span><span class="dark-grey"></span></a></li>
      <li><a href="#" class="" id="btn5"><span class="hdr-greydrk"></span><span class="sidenav-purle"></span></a></li>
      <li><a href="#" class="" id="btn6"><span class="hdr-grey"></span><span class="sidenav-red"></span></a></li>
    </ul>
  </div>
<!--  Theme Swithcer div ends  -->


<script type="text/javascript">
	function saveTask() {
		if ($('#task_form').validate(false, validationSettings)) {
			var frm = $('#task_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			var jsonData = encodeURIComponent(con);
			var sourceleadUrl = '${sourceleadUrl}';
			$.ajax({
						type : "POST",
						url : sourceleadUrl+ "/storeTasks?${_csrf.parameterName}=${_csrf.token}&json="+ jsonData,
						success : function(result) {
							taskwidget();
							    document.getElementById("assignedTasksDivId").className = 'active'
								document.getElementById("closedTasksDivId").removeAttribute("class")
								document.getElementById("myTaskDivId").removeAttribute("class")
								$("#closed-task, #my-task").hide();
							    $("#other-task").show().css("opacity", "1");
							assignedTasksList('assignedTasks');
							/* tasksList('myTask');
							assignTasksList(); */
						},
					});
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#task_form', function() {
		$("#task_form").showHelpOnFocus();
		$("#task_form").validateOnBlur(false, validationSettings);
	});
	
</script>
<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 

<script type="text/javascript">
$('#taskDatpicker').datepicker({
	dateFormat : 'dd-mm-yy',
	 changeMonth: true,
	changeYear: true, 
	minDate : 0,
	onSelect : function(selected) {
		var dt = new Date(selected);
		dt.setDate(dt.getDate() + 1);
		removevalidation();
	}
	});
	
$('#myTaskDatpicker').datepicker({
	dateFormat : 'dd-mm-yy',
	changeMonth: true,
	changeYear: true, 
	onSelect : function(selected) {
		tasksList('myTask');
    	}
	});
	
$('#assignedTaskDatpicker').datepicker({
	dateFormat : 'dd-mm-yy',
	changeMonth: true,
	changeYear: true, 
	onSelect : function(selected) {
		assignedTasksList('assignedTasks');
	   }
	});
$('#closedTaskDatpicker').datepicker({
	dateFormat : 'dd-mm-yy',
	changeMonth: true,
	changeYear: true, 
	onSelect : function(selected) {
		closedTasksList('closedTasks');
	  }
	});
	
$("#timepicker").timepicker({
	'showDuration': true,
	'disableTextInput': true
}); 
</script>
<script>
function removevalidation(){
	$('#taskDatpickerDiv').find('p.jquery_form_error_message').remove();	
}

</script>

<script>
//Collapse div
$(document).ready(function() {
$(".collpase-div").click(function(){
	$(this).next().slideToggle();
});
});

//Add Task Div
 $(".icon-addTask").click(function(){
	$('.addtAsk_popmask').toggle();
	$(".addTask-widget").toggleClass("addTask-widget-active");
	$(".newTask-widget").hide();
	$("#myTask").show();
	tasksList('myTask');
		document.getElementById("myTaskDivId").className = 'active'
		document.getElementById("closedTasksDivId").removeAttribute("class")
		document.getElementById("assignedTasksDivId").removeAttribute("class")
 });
 

 // Close New Task div
 function taskwidget(){
	 $(".newTask-widget").hide();
	  $("#myTask").show();
 };
 function showtaskdiv(){
	 assignedUserList()
	 $("#myTask").hide();
	 $(".newTask-widget").fadeIn(200);
	 $(".newTask-widget").val();
	 $('.newTask-widget').find('p.jquery_form_error_message').remove();	
	 document.getElementById("task_form").reset();
	 
 };
</script>
<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {
			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		};
	})(jQuery);
</script>

<script type="text/javascript">
function assignedUserList(){
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/assignedUserList?${_csrf.parameterName}=${_csrf.token}",	    	   
	    success: function(data) {
	    	 var json = JSON.parse(data);
        	 var result = "";
        	for (var i=0; i<json.length; i++)
			{
        		var report = json[i];
        		result = result +'<option value='+report.userId+' >'+report.userName+'</option>';
			}
        	 $("#assignTo").empty();
           	 $("#assignTo").append(result); 
           	$('#assignTo').trigger("chosen:updated");
	    }
   });
}

</script>

<script type="text/javascript">
function tasksList(status){
	$("#closed-task, #other-task").hide();
    $("#my-task").show().css("opacity", "1");
	var sourceleadUrl ='${sourceleadUrl}';
	var date = $("#myTaskDatpicker").val();
	$.ajax({
    	type: "POST",
        url: sourceleadUrl+"/getTasksList?${_csrf.parameterName}=${_csrf.token}&status="+status+"&date="+date,
        success: function(data) {
        	 var json = JSON.parse(data);
        	 var result = "";
        	 
       		result = result + '	 <table id="myTasksPagination">'; 
        	result = result + '     <thead>';
        	result = result + '       <tr>';
        	result = result + '         <th>Subject</th>';
        	result = result + '         <th>Date &amp; Time</th>';
        	result = result + '         <th>Owner</th>';
        	result = result + '         <th>Assigned to</th>';
        	result = result + '         <th>Description</th>';
        	result = result + '         <th>Mode</th>';
        	result = result + '         <th>Status</th>';
        	result = result + '       </tr>';
        	result = result + '     </thead>';
        	result = result + '     <tbody>';
        	for (var i=0; i<json.length; i++)
			{
        		var report = json[i];
        	 
        		if(report.alertType != null && report.alertType != '' && report.alertType != 'undefined'){
        			alertType = report.alertType;
        		}else{
        			alertType = "---";
        		}
        		if(report.taskDateStr != null && report.taskDateStr != '' && report.taskDateStr != 'undefined'){
        			taskDateStr = report.taskDateStr
        		}else{
        			taskDateStr = "---";
        		}
        		if(report.taskTime != null && report.taskTime != '' && report.taskTime != 'undefined'){
        			taskTime = report.taskTime;
        		}else{
        			taskTime = "---";
        		}
        		if(report.description != null && report.description != '' && report.description != 'undefined'){
        			description = report.description;
        		}else{
        			description = "---";
        		}
        		result = result + '       <tr>';
        		result = result + '         <td><div title="'+report.subject+'" class="tbldata-dotdot-vsm">'+report.subject+'</div></td>';
            	result = result + '         <td><div class="text-center tbldata-dotdot-sm" title="'+taskDateStr+' '+taskTime+'">'+taskDateStr+'  '+taskTime+'</div></td>';
            	result = result + '         <td><div title="'+report.taskOwnerName+'" class="tbldata-dotdot-100">'+report.taskOwnerName+'</div></td>';
            	result = result + '         <td><div title="'+report.assignedName+'" class="tbldata-dotdot-100">'+report.assignedName+'</div></td>';
            	result = result + '         <td><div title="'+description+'" class="tbldata-dotdot-100">'+description+'</div></td>';
            	result = result + '         <td><div class="text-center tbldata-dotdot_80" title="'+alertType+'">'+alertType+'</div></td>';
            	result = result + '         <td><div class="dropdown-select tblminwidth-100">';
            	
       			result = result + ' <select id="myTaskState'+report.id+'" name="myTaskState" onChange="updateMyTaskStatus('+report.id+');">';
       			result = result  +' <option '+(report.taskStatus == "Open" ? "selected" : '') +'>Open</option>';
	            result = result  +'	<option '+(report.taskStatus == "Closed" ? "selected" : '') +'>Closed</option>';
               	result = result + ' </select>';
            		 
            	result = result + '           </div></td>';
            	result = result + '       </tr>';
			}
        	result = result + '     </tbody>';
        	result = result + '   </table>';
        	
        	 $("#myTasksId").empty();
           	 $("#myTasksId").append(result); 
           	 myTasksPagination();
        	 
        }
	});
	 
}
</script>


<script type="text/javascript">
function assignedTasksList(status){
	$("#my-task, #closed-task").hide();
    $("#other-task").show().css("opacity", "1");
	var sourceleadUrl ='${sourceleadUrl}';
	var date = $("#assignedTaskDatpicker").val();
	$.ajax({
    	type: "POST",
        url: sourceleadUrl+"/getTasksList?${_csrf.parameterName}=${_csrf.token}&status="+status+"&date="+date,
        success: function(data) {
        	 var json = JSON.parse(data);
        	 var result = "";
        	 
       		 
       		result = result + '	 <table id="assignedTasksPagination">'; 
        	result = result + '     <thead>';
        	result = result + '       <tr>';
        	result = result + '         <th>Subject</th>';
        	result = result + '         <th>Date &amp; Time</th>';
        	result = result + '         <th>Owner</th>';
        	result = result + '         <th>Assigned to</th>';
        	result = result + '         <th>Description</th>';
        	result = result + '         <th>Mode</th>';
        	result = result + '         <th>Status</th>';
        	result = result + '       </tr>';
        	result = result + '     </thead>';
        	result = result + '     <tbody>';
        	for (var i=0; i<json.length; i++)
			{
        		var report = json[i];
        	 
        		if(report.alertType != null && report.alertType != '' && report.alertType != 'undefined'){
        			alertType = report.alertType;
        		}else{
        			alertType = "---";
        		}
        		if(report.taskDateStr != null && report.taskDateStr != '' && report.taskDateStr != 'undefined'){
        			taskDateStr = report.taskDateStr
        		}else{
        			taskDateStr = "---";
        		}
        		if(report.taskTime != null && report.taskTime != '' && report.taskTime != 'undefined'){
        			taskTime = report.taskTime;
        		}else{
        			taskTime = "---";
        		}
        		if(report.description != null && report.description != '' && report.description != 'undefined'){
        			description = report.description;
        		}else{
        			description = "---";
        		}
        		result = result + ' <tr>';
        		result = result + ' <td><div title="'+report.subject+'" class="tbldata-dotdot-vsm">'+report.subject+'</div></td>';
            	result = result + ' <td><div class="text-center tbldata-dotdot-sm" title="'+taskDateStr+' '+taskTime+'">'+taskDateStr+'  '+taskTime+'</div></td>';
            	result = result + ' <td><div title="'+report.taskOwnerName+'" class="tbldata-dotdot-100">'+report.taskOwnerName+'</div></td>';
            	result = result + ' <td><div title="'+report.assignedName+'" class="tbldata-dotdot-100">'+report.assignedName+'</div></td>';
            	result = result + ' <td><div title="'+description+'" class="tbldata-dotdot-100">'+description+'</div></td>';
            	result = result + ' <td><div class="text-center tbldata-dotdot_80" title="'+alertType+'">'+alertType+'</div></td>';
            	result = result + ' <td><div class="dropdown-select tblminwidth-100">';
            	
		        result = result + '<select id="assignTaskState'+report.id+'" name="assignTaskState" onchange="updateAssignTaskStatus('+report.id+');">';
		        result = result  +'<option '+(report.taskStatus == "Open" ? "selected" : '') +'>Open</option>';
				result = result  +'<option '+(report.taskStatus == "Closed" ? "selected" : '') +'>Closed</option>';
		        result = result + '</select>';
            	
            	result = result + '</div></td>';
            	result = result + '</tr>';
			}
        	result = result + '</tbody>';
        	result = result + '</table>';
        	
       		 $("#assignedTasksId").empty();
           	 $("#assignedTasksId").append(result); 
           	 assignedTasksPagination();
        	
        	 
        }
	});
	 
}
</script>

<script type="text/javascript">
function closedTasksList(status){
	$("#my-task, #other-task").hide();
    $("#closed-task").show().css("opacity", "1");
	var sourceleadUrl ='${sourceleadUrl}';
	var date = $("#closedTaskDatpicker").val();
	$.ajax({
    	type: "POST",
        url: sourceleadUrl+"/getTasksList?${_csrf.parameterName}=${_csrf.token}&status="+status+"&date="+date,
        success: function(data) {
        	 var json = JSON.parse(data);
        	 var result = "";
        	 
       		 
        	result = result + '	 <table id="closedTasksPagination">'; 
        	result = result + '     <thead>';
        	result = result + '       <tr>';
        	result = result + '         <th>Subject</th>';
        	result = result + '         <th>Date &amp; Time</th>';
        	result = result + '         <th>Owner</th>';
        	result = result + '         <th>Assigned to</th>';
        	result = result + '         <th>Description</th>';
        	result = result + '         <th>Mode</th>';
        	result = result + '         <th>Status</th>';
        	result = result + '       </tr>';
        	result = result + '     </thead>';
        	result = result + '     <tbody>';
        	for (var i=0; i<json.length; i++)
			{
        		var report = json[i];
        	 
        		if(report.alertType != null && report.alertType != '' && report.alertType != 'undefined'){
        			alertType = report.alertType;
        		}else{
        			alertType = "---";
        		}
        		if(report.taskDateStr != null && report.taskDateStr != '' && report.taskDateStr != 'undefined'){
        			taskDateStr = report.taskDateStr
        		}else{
        			taskDateStr = "---";
        		}
        		if(report.taskTime != null && report.taskTime != '' && report.taskTime != 'undefined'){
        			taskTime = report.taskTime;
        		}else{
        			taskTime = "---";
        		}
        		if(report.description != null && report.description != '' && report.description != 'undefined'){
        			description = report.description;
        		}else{
        			description = "---";
        		}
        		result = result + ' <tr>';
        		result = result + ' <td><div title="'+report.subject+'" class="tbldata-dotdot-vsm">'+report.subject+'</div></td>';
            	result = result + ' <td><div class="text-center tbldata-dotdot-sm" title="'+taskDateStr+' '+taskTime+'">'+taskDateStr+'  '+taskTime+'</div></td>';
            	result = result + ' <td><div title="'+report.taskOwnerName+'" class="tbldata-dotdot-100">'+report.taskOwnerName+'</div></td>';
            	result = result + ' <td><div title="'+report.assignedName+'" class="tbldata-dotdot-100">'+report.assignedName+'</div></td>';
            	result = result + ' <td><div title="'+description+'" class="tbldata-dotdot-100">'+description+'</div></td>';
            	result = result + ' <td><div class="text-center tbldata-dotdot_80" title="'+alertType+'">'+alertType+'</div></td>';
            	result = result + ' <td><div class="dropdown-select tblminwidth-100">';
		        result = result + report.taskStatus;
            	
            	result = result + '</div></td>';
            	result = result + '</tr>';
			}
        	result = result + '</tbody>';
        	result = result + '</table>';
        	
        	$("#closedTasksId").empty();
          	$("#closedTasksId").append(result); 
          	 closedTasksPagination();
        	
        	 
        }
	});
	 
}
</script>

<script>
   function handle(e){
       if(e.keyCode === 13){
           e.preventDefault(); // Ensure it is only this code that rusn
           universalSearch();
       }
   }
</script>
 
<script>
function updateMyTaskStatus(taskId){
 	var taskStatus = $('#myTaskState'+taskId).val();	
	var sourceleadUrl = '${sourceleadUrl}';

	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/changeTaskStatus?${_csrf.parameterName}=${_csrf.token}&taskId="+taskId+"&taskStatus="+taskStatus,	    	   
	    success: function(data) {
	    	assignedTasks();
	    }
   });
}
</script>

<script>
function updateAssignTaskStatus(taskId){
   var taskStatus = $('#assignTaskState'+taskId).val();	
	var sourceleadUrl = '${sourceleadUrl}';

	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/changeTaskStatus?${_csrf.parameterName}=${_csrf.token}&taskId="+taskId+"&taskStatus="+taskStatus,	    	   
	    success: function(data) {
	    	assignedTasks();
	    }
   });
}
</script>

<script>
function universalSearch(){
	 var searchType = document.getElementById('searchType').value;
	 var searchValue = document.getElementById('searchKey').value;
	 var sourceleadUrl = '${sourceleadUrl}';
	 if(sourceleadUrl == ''){
		 sourceleadUrl = '${SourceLeadWeb_sourceleadUrl}';
	 }
	window.location.href = sourceleadUrl + "/universalSearchForm?&searchType="+searchType+"&searchValue="+searchValue;
}
</script>


<script>
function myTasksPagination(){
    $('#myTasksPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 20, -1], [5, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>

<script>
function assignedTasksPagination(){
    $('#assignedTasksPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 20, -1], [5, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>
<script>
function closedTasksPagination(){
    $('#closedTasksPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 20, -1], [5, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>
<script>
$(".nav-tabs").click(function(){
	$("#myTaskDatpicker, #assignedTaskDatpicker, #closedTaskDatpicker").val('');
});
	
</script>

<script type="text/javascript">
/* function closePop(){
	alert('naaistam');
	 $(".addTask-widget").removeClass("addTask-widget-active");
	$(".addtAsk_popmask").fadeOut();
} */
$('.taskpop-close').click(function (){
	$(".addTask-widget").removeClass("addTask-widget-active");
	$(".addtAsk_popmask").fadeOut();
});
</script>

<!-- <script>
//scroll top top fix Header
jQuery(window).scroll(function(){
var top = jQuery(window).scrollTop();
if(top>25){
jQuery('.main-header').addClass('navbar-fixed-top');
} else {
jQuery('.main-header').removeClass('navbar-fixed-top');}
});
</script> -->
<script>
/* jQuery(window).scroll(function () {
if( jQuery(window).scrollTop() > jQuery('.main-header').offset().top + 100 && !(jQuery('.main-header').hasClass('navbar-fixed-top'))){
jQuery('.main-header').addClass('navbar-fixed-top').animate({"top":"0%"}, 500);
} else if (jQuery(window).scrollTop() <= 50){
jQuery('.main-header').removeClass('navbar-fixed-top').css({"top":"-20%"});
}
}); */

// Select picker updated script for universalSerch

$("#searchType").selectpicker('refresh');
</script>

<!-- Task related code Ends -->
<!--  Company Invite pop -->
<div class="sideMask"></div>
<div class="modal modalInvite" class="invite" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Invite Company</h4>
        </div>
        <form:form id="inviteCompany_form" name="inviteCompany_form" method="post">
        <div class="modal-body invite_class">
            <div class="form-group clearfix">
                <label class="col-md-4 paddingRight" title="Company Name"><i class="fa fa-building paddingRight text-info"></i>Company Name <span class="text-danger paddingLeft">*</span></label>
                <div class="col-md-8 paddingLeft">
                    <input type="text" class="form-control input-sm" placeholder="Company Name" id="compnayName" name="companyName" field-name="Company Name" data-validation="required validate_Space validate_length length2-100">
                </div>
            </div>
            <div class="form-group clearfix">
                <label class="col-md-4 paddingRight"><i class="fa fa-map-marker paddingRight text-info"></i>Location</label>
                <div class="col-md-8 paddingLeft">
                    <input type="text" class="form-control input-sm" placeholder="Location" name="location" id="inviteLocation" onclick="initLocationAuto('inviteLocation');">
                </div>
            </div>
            <div class="form-group clearfix">
                <label class="col-md-4 paddingRight"><i class="fa fa-user-o paddingRight text-info"></i>Name<span class="text-danger paddingLeft">*</span></label>
                <div class="col-md-8 paddingLeft">
                    <input type="text" class="form-control input-sm" placeholder="Name" id="name" name="name" field-name="Name" data-validation="required validate_Space validate_AlphaNumber validate_length length1-100">
                </div>
            </div>
            <div class="form-group clearfix">
                <label class="col-md-4 paddingRight">
                    <i class="fa fa-envelope paddingRight text-info"></i>Email<span class="text-danger paddingLeft">*</span></label>
                <div class="col-md-8 paddingLeft">
                    <input type="text" class="form-control input-sm" placeholder="Email" id="inviteCompanyemail" name="email" field-name="Email" data-validation="required validate_Space validate_length length2-100 validate_email">
                </div>
            </div>
                        <div class="form-group clearfix">
                <label class="col-md-4 paddingRight"><i class="fa fa-phone paddingRight text-info"></i>Phone</label>
                <div class="col-md-8 paddingLeft">
                    <input type="text" class="form-control input-sm" placeholder="Phone" id="phone" name="phoneNumber" value="" data-validation="validate_Space validate_phone validate_length length9-20" data-validation-optional="true">
                </div>
            </div>
        </div>
        <div class="clearfix form-footer">
            <div class="pull-right mr">
          <button type="button" class="btn btn-primary btn-sm" data-dismiss="modal" onclick="saveInviteCompany();"> <i class="fa fa-send paddingRight"></i>Send </button>
          <button type="button" class="btnClose btn btn-default btn-sm ml10"><i class="fa fa-close paddingRight" onclick="flushInviteCompany();"></i>Cancel
            </button>
                </div>
        </div>
        </form:form>
        
      </div>
      
    </div>
  </div>
  <script>
  function flushInviteCompany(){
	  document.getElementById("inviteCompany_form").reset();
	  $("inviteCompany_form").validate().resetForm();
  }
  </script> 
  <script>
  $("#invitePop").click(function(){
  $(".modalInvite").fadeIn();
  $(".sideMask").fadeIn();
  });
  $(".close").click(function(){
	  $(".modalInvite").fadeOut();
	  $(".sideMask").fadeOut();
	  flushInviteCompany();
	  });
  $(".btnClose").click(function(){
	  $(".modalInvite").fadeOut();
	  $(".sideMask").fadeOut();
	  flushInviteCompany();
	  });
  </script>
<!--  Company invite pop ends -->


<script>
function saveInviteCompany() {
	if ($('#inviteCompany_form').validate(false, validationSettings)) {
		var frm = $('#inviteCompany_form').serializeFormJSON();
		var con = JSON.stringify(frm);
		con = con.replace(/[{}]/g, "");
		var jsonData = encodeURIComponent(con);
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
					type : "POST",
					url : sourceleadUrl+ "/storeInviteCompany?${_csrf.parameterName}=${_csrf.token}&json="+ jsonData,
					success : function(result) {
						 $(".modalInvite").fadeOut();
						  $(".sideMask").fadeOut();
					},
				});
		return true;
	} else {
		return false;
	}
}
$('body').on('blur', '#inviteCompany_form', function() {
	$("#inviteCompany_form").showHelpOnFocus();
	$("#inviteCompany_form").validateOnBlur(false, validationSettings);
});
</script>

<!-- 
<script>
// This example displays an address form, using the autocomplete feature
// of the Google Places API to help users fill in the information.
function initLocationAuto(id) {
// 	geolocate();
    initialize(id);
}
/* $("#autocomplete").on('focus', function () {
    
});
 */
var placeSearch, autocomplete;
var componentForm = {
    locality: 'long_name',
    administrative_area_level_1: 'long_name',
    country: 'long_name',
    postal_code: 'short_name'
};

function initialize(id) {
	   autocomplete = new google.maps.places.Autocomplete((document.getElementById('autocomplete')), {
			        types: ['geocode']
			    });
	   
    /* autocomplete = new google.maps.places.Autocomplete((document.getElementById(id)), {
    	types: ['(cities)']
    }); */
    google.maps.event.addListener(autocomplete, 'place_changed', function () {
        fillInAddress();
    });
}
function fillInAddress() {
    var place = autocomplete.getPlace();
	document.getElementById("latitude").value = place.geometry.location.lat();
    document.getElementById("longitude").value = place.geometry.location.lng();
	
    for (var component in componentForm) {
        document.getElementById(component).value = '';
        document.getElementById(component).disabled = false;
    }

    for (var i = 0; i < place.address_components.length; i++) {
        var addressType = place.address_components[i].types[0];
        if (componentForm[addressType]) {
            var val = place.address_components[i][componentForm[addressType]];
            document.getElementById(addressType).value = val;
        }
    }
}
function geolocate() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            var geolocation = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
            var latitude = position.coords.latitude;
            var longitude = position.coords.longitude;
            document.getElementById("latitude").value = latitude;
            document.getElementById("longitude").value = longitude;
            autocomplete.setBounds(new google.maps.LatLngBounds(geolocation, geolocation));
        });
    }
}
</script> -->

<script>
function initLocationAuto(locationId,countryId,stateId,cityId,zipCodeId,latitudeId,longtitudeId,type) {
    initialize(locationId,countryId,stateId,cityId,zipCodeId,type);
    geolocate(latitudeId,longtitudeId);
}
function initialize(locationId,countryId,stateId,cityId,zipCodeId,type) {
    if(type == "invite"){
    	 var input = (document.getElementById(locationId));
    	    var options = {
    	    		types: ['(cities)'],
    	        componentRestrictions: {
    	        	country: ["IN","US","AU","SG","GB","FR"]
    	        }
    	    };
    	    autocomplete = new google.maps.places.Autocomplete(input, options);
    }else{
    	autocomplete = new google.maps.places.Autocomplete((document.getElementById(locationId)), {
    		types: ['(cities)'],
      
        });
    }
   
    
    
  google.maps.event.addListener(autocomplete, 'place_changed', function() {
  place = autocomplete.getPlace();
  var address = {};
  for (var i = 0; i < place.address_components.length; i++) {
    var addressType = place.address_components[i].types[0];
    switch (addressType) {
      case "locality":
        address.city = place.address_components[i].long_name;
        break;
      case "administrative_area_level_1":
        address.state = place.address_components[i].long_name;
        break;
      case "country":
        address.country = place.address_components[i].long_name;
        break;
      case "postal_code":
        address.zipcode = place.address_components[i].long_name;
        
    }
  }
  
  $("#"+countryId).val(address.country).change();
  $("#"+stateId).val(address.state).change();
  $("#"+cityId).val(address.city).change();
  $("#"+zipCodeId).val(address.zipcode).change();
  if(type == "invite"){
	  getTimezonesList();
  }
});
}
function geolocate(latitudeId,longtitudeId) {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            var geolocation = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
            var latitude = position.coords.latitude;
            var longitude = position.coords.longitude;
            document.getElementById("latitude").value = latitude;
            document.getElementById("longitude").value = longitude;
            autocomplete.setBounds(new google.maps.LatLngBounds(geolocation, geolocation));
        });
    }
}
</script>
<script>
function refreshGoogleApiLocation(locationId,countryId,stateId,cityId,zipCodeId){
	$("#"+locationId).val('');
	$("#"+countryId).val('');
	$("#"+stateId).val('');
	$("#"+cityId).val('');
	$("#"+zipCodeId).val('');
}
</script>