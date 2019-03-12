<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>


<link rel="icon" type="image/png" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/fav.png" />
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<!-- Bootstrap -->
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/bootstrap-select.css?staticTime=${staticTime}" rel="stylesheet">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/jquery.dataTables.min.css?staticTime=${staticTime}" rel="stylesheet">
<link rel="stylesheet" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/jquery-ui.css?staticTime=${staticTime}">
<link rel="stylesheet" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/chosen.min.css?staticTime=${staticTime}">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/sourcelead-main.css?staticTime=${staticTime}" rel="stylesheet" id="myStyleSheet">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/timesheet.css?staticTime=${staticTime}" rel="stylesheet">
<link rel="stylesheet" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/bootstrap-select.min.css?staticTime=${staticTime}">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet" type="text/css">

<script type="text/javascript">
 /* var sourceleadUrl ='${sourceleadUrl}'; */
 var atsUrl = '${SourceLeadWeb_atsUrl}';
</script>

<div class="wraper"> 
	<section class="welcoBg">
		<section class="welcoInn">
		
		   <div class="welcoHead">
		   
		   <jsp:include page="../userManageAccounts.jsp" />
	          
				<c:if test="${!empty SourceLeadWeb_fileUrl}">
					<div class="welLogo">
					<img class="logo" data-id="orgLogoImage" src="${SourceLeadWeb_fileUrl}" title="${SourceLeadWeb_orgLocationName}" />
					</div>
				</c:if>
				
				<c:if test="${empty SourceLeadWeb_fileUrl}">
					<div class="welLogoText">
					<span  title="${SourceLeadWeb_orgLocationName}">${SourceLeadWeb_orgLocationName}</span>
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
			<h5>Offboard Task List</h5>
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
			<input type="button"  value="Previous" onClick="javascript:window.location.href = '<c:url value='${SourceLeadWeb_atsUrl}/offboardStep2'/>'" >
			<input type="button"  value="Proceed to next step" id="save_button"  onclick="javascript:window.location.href = '<c:url value='${SourceLeadWeb_atsUrl}/offboardStep4'/>'" class="Brig">
			<div class="clearfix"></div>
		</div>
		
		</section>
	</section>
</div>

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
  <script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/jquery-ui.min.js?staticTime=${staticTime}"></script> 
  <script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/chosen.jquery.min.js?staticTime=${staticTime}"></script> 
  <script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/formvalidator.js?staticTime=${staticTime}"></script> 

<script>
function changeTaskStatus(taskId){
	$("#task"+taskId).attr('style',' height:50px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
	 $.ajax({
	    	type: "POST",
	    	 url: atsUrl+"/changeTaskStatusForOnboardAndOffboardCandidate?${_csrf.parameterName}=${_csrf.token}&taskId="+taskId, 
	        success: function(response) {
	        	if (response.response === "AWKWARD") {
	        	    location.href = "/errorPage";
	        	} else if (response.response === "SUCCESS") {
	        		$("#task"+taskId).removeAttr("style");
	        	} else if (response.indexOf('Sign In') !== -1) {
	        		location.replace(sourceleadUrl);
	        	} else {
	        	    showError("Operation failed, please try again");
	        	}
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
		$(imageId).attr('src','/resources/images/office-image.jpg');
	}
</script>


