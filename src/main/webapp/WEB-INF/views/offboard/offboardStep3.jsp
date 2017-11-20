<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>


<link rel="icon" type="image/png" href="resources/images/fav.png" />
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="resources/css/bootstrap-select.css?staticTime=${staticTime}" rel="stylesheet">
<link href="resources/css/jquery.dataTables.min.css?staticTime=${staticTime}" rel="stylesheet">
<link rel="stylesheet" href="resources/css/jquery-ui.css?staticTime=${staticTime}">
<link rel="stylesheet" href="resources/css/chosen.min.css?staticTime=${staticTime}">
<link href="resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="resources/css/sourcelead-main.css?staticTime=${staticTime}" rel="stylesheet" id="myStyleSheet">
<link href="resources/css/timesheet.css?staticTime=${staticTime}" rel="stylesheet">
<link rel="stylesheet" href="resources/css/bootstrap-select.min.css?staticTime=${staticTime}">
<link href="resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet" type="text/css">

<script type="text/javascript">
 var sourceleadUrl ='${sourceleadUrl}';
</script>

<div class="wraper"> 
	<section class="welcoBg">
		<section class="welcoInn">
		
		   <div class="welcoHead">
		   
		   <c:if test="${!empty SourceLeadWeb_userSpocRolesList}">
			<div class="offOrg">
	             <a href="javascript:void(0)" title="Spoc Links" id="spoclinks" onclick="JavaScript: navtoggle(this);"> <i class="fa fa-globe"></i> </a>
		              <div id="spockLinksList" style="display: none;">
		              <ul class="list-unstyled">
		              <c:forEach var="links" items="${SourceLeadWeb_userSpocRolesList}">
			              <li>
				              <a href="<c:url value='${sourceleadUrl}/dashboard/${links.orgLocations.id}'/>" class="sopcSwith">
					                <div class="clearfix">
					                   <div class="pull-left image-parent positionRelative">
					                   <div class="imageParent org-imagelink ${links.orgLocations.id == SourceLeadWeb_orgLocationId ? 'active-comp' : ''}">
					                     <img onerror='predefineOrgImage(this);' src="${sourceleadUrl}/displayOrgImage/${links.orgLocations.org.orgCode}">
					                     </div>
					                   </div>
					                   <div class="pull-left mt5">
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
		            </div>
	          </c:if>
				<c:if test="${!empty SourceLeadWeb_org.fileContentType}">
					<div class="welLogo">
					<img class="logo" src="<c:url value='/displayOrgImage/${SourceLeadWeb_orgCode}'/>" title="${SourceLeadWeb_orgName}" />
					</div>
				</c:if>
				
				<c:if test="${empty SourceLeadWeb_org.fileContentType}">
					<div class="welLogoText">
					<span  title="${SourceLeadWeb_orgName}">${SourceLeadWeb_orgName}</span>
					</div>
				</c:if>
				
			 <div class="welSiO" onclick="logoutFormSubmit();" title="Sign Out"><i class="fa fa-sign-out"></i></div>
			  <ul>
				  <li class="actLi"><span class="fill">1</span><b>Details</b><i class="line"></i></li>
				  <li class="actLi"><span class="fill">2</span><b>Check List</b><i class="line"></i></li>
				  <li class="actLi"><span>3</span><b>Tasks</b><i class="line"></i></li>
				  <li><span>4</span><b>Documents</b><i class="line"></i></li>
				  <div class="clearfix"></div>
			  </ul>
			  <div class="clearfix"></div>
		  </div>
		  
		  <div class="welCo1"> 
			<div id="loading"></div>
				<div class="step4">
					<c:if test="${tasksListSize > 0}">
					<c:forEach var="tasks" items="${tasksList}">
					<ul id="task${tasks.id}">
						<li>Subject : ${tasks.subject}</li>
						<li><i class="fa fa-star-o"></i>Priority : ${tasks.priority}</li>
						<li><i class="fa fa-clock-o"></i>Task Date : ${tasks.taskDateStr}</li>
						<li>
							 <select class="form-control select-bg" onchange="changeTaskStatus(${tasks.id})">
							  <option ${tasks.taskStatus  == 'Open' ? 'selected' : ''}>Open</option>
							  <option ${tasks.taskStatus  == 'Closed' ? 'selected' : ''}>Closed</option>
							</select>
                		</li>
						<div class="clearfix"></div>
					</ul>
					</c:forEach>
					</c:if>
					
					<c:if test="${tasksListSize == 0}">
					    <ul><li>No Tasks Found</li></ul>
					</c:if>
																				
				</div>		
		  </div>	
		  
		  <div class="welBtns">
			<input type="button"  value="Previous" onClick="javascript:window.location.href = '<c:url value='/offboardStep2'/>'" >
			<input type="button"  value="Proceed to next step" id="save_button"  onclick="javascript:window.location.href = '<c:url value='/offboardStep4'/>'" class="Brig">
			<div class="clearfix"></div>
		</div>
		
		</section>
	</section>
</div>

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
  <script src="resources/js/jquery-ui.min.js?staticTime=${staticTime}"></script> 
  <script src="resources/js/chosen.jquery.min.js?staticTime=${staticTime}"></script> 
  <script src="resources/js/formvalidator.js?staticTime=${staticTime}"></script> 

<script>
function changeTaskStatus(taskId){
	$("#task"+taskId).attr('style',' height:50px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
	
	 $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/changeTaskStatusForOnboardAndOffboardCandidate?${_csrf.parameterName}=${_csrf.token}&taskId="+taskId, 
	        success: function(onboardId) {
	        	$("#task"+taskId).removeAttr("style");
	        },
	    });
	
	 
}
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
  
  $(".offOrg").click(function( event ) {
	  event.stopPropagation();
	});
  </script>
  
<script>
	function predefineOrgImage(imageId){
		$(imageId).attr('src','resources/images/office-image.jpg');
	}
</script>


<script>
		function logoutFormSubmit() {
			document.getElementById("logoutForm").submit();
		}
</script>

<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>