<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" type="image/png" href="resources/images/fav.png" />
<title>Welcome to Source Lead :: The Recruitment Application</title>
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<link href="resources/css/font-awesome.min.css" rel="stylesheet">
<link href="resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="resources/css/dataTablePagination.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="resources/css/bootstrap-select.min.css" type="text/css" >
<link href="resources/css/sourcelead-main.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/timesheet.css" type="text/css" >
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="resources/js/submissionFormBuilder.js" type="text/javascript" ></script> <!-- form builder -->
<script src="resources/js/submissionForm-html-generator.js" type="text/javascript" ></script> <!-- form generator -->

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
<jsp:include page="masterJs.jsp" />
</head>


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
  <span class="logo"> <a href="dashboard/${SourceLeadWeb_orgLocationId}" title="Source Lead">
  <i>
 <img src="resources/images/logo-sourceld.png" alt="Source Lead"> </i>
    <h1>Source Lead</h1>
    </a> </span>
    <nav class="header-nav navbar"> <a href="javascript:void(0)" class="side-menu"> <i class="fa fa-bars pull-left"></i> </a>
      <div class="custom-navbar">
        <div class="search-area">
          <div class="dropdown hidden">
            <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown"> <i class="fa fa-list-ul"></i> <span class="caret"></span></button>
            <ul class="dropdown-menu">
              <li><a href="javascript:void(0)">Candiate</a></li>
              <li><a href="javascript:void(0)">Professional</a></li>
            </ul>
          </div>
          <div class="search-area-txt"> <i class="fa fa-search"></i>
            <input type="text" placeholder="Search ..." class="form-control">
          </div>
        </div>
        <!-- login User details -->
        <div class="login-user">
        <span class="image"> <img class="img-circle" alt="${SourceLeadWeb_userType}" title="${SourceLeadWeb_userName}" onerror="predefineProfileImage(this);"  src="<c:url value='/displayUserImage/${SourceLeadWeb_userId}'/>"> </span> <span class="pull-left login-user-xs tbldata-dotdot-vsm" title="${SourceLeadWeb_userName}"> ${SourceLeadWeb_userName} </span>
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
            
            <c:if test="${!empty SourceLeadWeb_userSpocRolesList}">
            <li> <a href="javascript:void(0)" title="Spoc Links" id="spoclinks" onclick="JavaScript: navtoggle(this);"> <i class="fa fa-globe"></i> </a>
	              <div id="spockLinksList" style="display: none;">
	              <ul class="list-unstyled">
	              <c:forEach var="links" items="${SourceLeadWeb_userSpocRolesList}">
		              <li>
			              <a href="${sourceleadUrl}/dashboard/${links.orgLocations.id}">
				                <div class="clearfix">
				                   <div class="pull-left image-parent positionRelative">
				                   <div class="imageParent org-imagelink ${links.orgLocations.id == SourceLeadWeb_orgLocationId ? 'active-comp' : ''}">
				                     <img onerror='predefineOrgImage(this);' src="${sourceleadUrl}/displayOrgImage/${links.orgLocations.org.orgCode}">
				                     </div>
				                   </div>
				                   <div class="pull-left">
				                     <p class="tbldata-dotdot"><i class="fa fa-building"></i>&nbsp;${links.orgLocations.org.orgName}</p>
				                     <p class="tbldata-dotdot"><i class="fa fa-map-marker"></i>&nbsp;${!empty links.orgLocations.city ? links.orgLocations.city : ""} ${links.orgLocations.country}</p>
				                     <p><i class="fa fa-user"></i>&nbsp;${links.roles.displayName}</p>
				                   </div>
				                </div>
			                </a>
		              </li>
	              </c:forEach>
	              </ul>
	            </div>
	          </li>
	          </c:if>
           
          <!-- <li> <a href="#" title="Notifications" id="notification"> <i class="fa fa-envelope"></i> <span class="label label-success">4</span> </a>
            <div class="header-widget note-area">
              <ul class="list-unstyled">
                <li>
                  <div class="clearfix msg">
                    <div class="msg-img"> <span> <img src="resources/images/pratap-bis.jpg" title="Pratap Reddy" alt="Admin" class="img-circle"> </span> </div>
                    <div class="msg-content">
                      <p>Earlier this week, I was doing some work on a project that was using</p>
                      <span>3 days ago at 7:58 pm - 2.07.2016</span> </div>
                    <div class="msg-time"> <span> <i class="fa fa-clock-o"></i><br>
                      1hr ago </span> </div>
                  </div>
                </li>
                <li>
                  <div class="clearfix msg">
                    <div class="msg-img"> <span> <img src="resources/images/pratap-bis.jpg" title="Pratap Reddy" alt="Admin" class="img-circle"> </span> </div>
                    <div class="msg-content">
                      <p>Earlier this week, I was doing some work on a project that was using</p>
                      <span>3 days ago at 7:58 pm - 2.07.2016</span> </div>
                    <div class="msg-time"> <span> <i class="fa fa-clock-o"></i><br>
                      1hr ago </span> </div>
                  </div>
                </li>
                <li>
                  <div class="clearfix msg">
                    <div class="msg-img"> <span> <img src="resources/images/pratap-bis.jpg" title="Pratap Reddy" alt="Admin" class="img-circle"> </span> </div>
                    <div class="msg-content">
                      <p>Earlier this week, I was doing some work on a project that was using</p>
                      <span>3 days ago at 7:58 pm - 2.07.2016</span> </div>
                    <div class="msg-time"> <span> <i class="fa fa-clock-o"></i><br>
                      1hr ago </span> </div>
                  </div>
                </li>
                <li>
                  <div class="clearfix msg">
                    <div class="msg-img"> <span> <img src="resources/images/pratap-bis.jpg" title="Pratap Reddy" alt="Admin" class="img-circle"> </span> </div>
                    <div class="msg-content">
                      <p>Earlier this week, I was doing some work on a project that was using</p>
                      <span>3 days ago at 7:58 pm - 2.07.2016</span> </div>
                    <div class="msg-time"> <span> <i class="fa fa-clock-o"></i><br>
                      1hr ago </span> </div>
                  </div>
                </li>
                <li>
                  <div class="clearfix msg">
                    <div class="msg-img"> <span> <img src="resources/images/pratap-bis.jpg" title="Pratap Reddy" alt="Admin" class="img-circle"> </span> </div>
                    <div class="msg-content">
                      <p>Earlier this week, I was doing some work on a project that was using</p>
                      <span>3 days ago at 7:58 pm - 2.07.2016</span> </div>
                    <div class="msg-time"> <span> <i class="fa fa-clock-o"></i><br>
                      1hr ago </span> </div>
                  </div>
                </li>
              </ul>
              <div class="headerwidget-footer"> <a href="javascript:void(0)" >View All</a> </div>
            </div>
          </li> -->
          <li> <a href="#" title="Alerts" id="alert" onclick="notificationStatusAsRead();" > <i class="fa fa-bell bell" ></i> <span class="label label-warning">${SourceLeadWeb_notificationCount}</span></a>
            <div class="header-widget alert-area">
              <ul class="list-unstyled">
              <c:if test="${empty SourceLeadWeb_notificationList}">
              	<div class="clearfix msg">
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
                      <p>${report.body}</p>
                     <c:out value="${userId}" />
                      <span>${report.calDays} days ago at ${report.time} ${report.dateValue}</span> </div>
                  </div>
                </li>
                </c:forEach>
                </c:if>
              </ul>
              <div class="headerwidget-footer"> <a href="<c:url value='/notificationsList'/>">View All</a> </div>
            </div>
          </li>
          
          <sec:authorize access="hasAnyRole('ROLE_ENROLL_ORG','ROLE_ENROLL_NEW_ROLE','ROLE_VIEW_ORG_SETTINGS','ROLE_EDIT_ORG_SETTINGS','ROLE_ORG_SUBMISSION_TEMPLATE')"> 
	            <li> <a href="#" title="Settings" class="settings"> <i class="fa fa-cog fa-spin fa-fw"></i></a>
	            <div class="setting-area">
	              <ul class="list-unstyled">
	                     <sec:authorize access="hasAnyRole('ROLE_ENROLL_ORG')"> 
	                     <li><a href="<c:url value="/enrolledOrgsList" />"><i class="fa fa-cubes" aria-hidden="true"></i>Enroll Org</a></li>
	                     </sec:authorize>
	                     
	                     <sec:authorize access="hasAnyRole('ROLE_ENROLL_NEW_ROLE')"> 
	                        <li><a href="<c:url value="/orgRolesList" />"><i class="fa fa-key" aria-hidden="true"></i>Role Configuration</a></li>
	                     </sec:authorize>
	                     <sec:authorize access="hasAnyRole('ROLE_VIEW_ORG_SETTINGS','ROLE_EDIT_ORG_SETTINGS')"> 
	               		      <li><a href="<c:url value="/orgSettings" />"><i class="fa fa-gears" aria-hidden="true"></i>Org Settings</a></li>
	               		 </sec:authorize>
	               		 <sec:authorize access="hasAnyRole('ROLE_CREATE_APPLICANT_TEMPLATE','ROLE_VIEW_APPLICANT_TEMPLATE','ROLE_VIEW_EDIT_APPLICANT_TEMPLATE')"> 
	                    	   <li><a href="<c:url value="/orgSubmissionTemplate" />"><i class="fa fa-newspaper-o" aria-hidden="true"></i>Templates</a></li>
	                     </sec:authorize>
	                     
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
	    	      			
      			 }							     
	}); 
	 $("#alert").click(function(){
         $("span.label-warning").text(0);
	 });
}
</script>

  <!-- Header Area --> 