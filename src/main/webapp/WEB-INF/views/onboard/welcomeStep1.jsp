<link rel="icon" type="image/png" href="resources/images/fav.png" />
<title>Welcome to Source Lead :: The Recruitment Application</title>
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<!-- Bootstrap -->

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/bootstrap-select.css" rel="stylesheet">
<link href="resources/css/jquery.dataTables.min.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/jquery-ui.css">
<link rel="stylesheet" href="resources/css/chosen.min.css">
<link href="resources/css/font-awesome.min.css" rel="stylesheet">
<link href="resources/css/sourcelead-main.css" rel="stylesheet" id="myStyleSheet">
<link href="resources/css/timesheet.css" rel="stylesheet">

<link rel="stylesheet" href="resources/css/bootstrap-select.min.css">
<link href="resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">



<script>
	function predefineProfileImage(imageId){
		$(imageId).attr('src','resources/images/userprofileImg.jpg');
	}
</script>


<script type="text/javascript">
$(function(){
  $('.crsl-items').carousel({
    visible: 3,
    itemMinWidth: 180,
    itemEqualHeight: 370,
    itemMargin: 9,
  });
  
  $("a[href=#]").on('click', function(e) {
    e.preventDefault();
  });
  
});
</script>



<script>
		function logoutFormSubmit() {
			document.getElementById("logoutForm").submit();
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
				  <li class="actLi"><span>1</span><b>Welcome</b><i class="line"></i></li>
				  <li><span>2</span><b>Step two</b><i class="line"></i></li>
				  <li><span>3</span><b>Step three</b><i class="line"></i></li>
				  <li><span>4</span><b>Finish</b><i class="line"></i></li>
				  <div class="clearfix"></div>
			  </ul>
			  <div class="clearfix"></div>
		  </div>
		  
			<div class="welCo1">
			
				<c:if test="${!empty SourceLeadWeb_candidateOnBoarding.rejectedReason}">
					<div class="welcomeError">
							<p>Rejected Reason</p>
							${SourceLeadWeb_candidateOnBoarding.rejectedReason}
					</div>
				</c:if>
				
				<h2>Welcome to ${SourceLeadWeb_orgName}</h2>
				<h3>First and foremost, Welcome to the Company!</h3>
				<p>Below you can find out brief introduction about the Company and a glance at the manager, your team and support documents, next verify your personal information, documents and take a couple of minutes to view boarding undertakings </p><br>
				<h4>About the Company</h4>
				<div class="welCoInn">
					<div class="welCoInnLeft">
						<c:if test="${!empty SourceLeadWeb_org.fileContentType}">
							<img class="logo" src="<c:url value='/displayOrgImage/${SourceLeadWeb_orgCode}'/>" title="${SourceLeadWeb_orgName}" />
						</c:if>
						
						<c:if test="${empty SourceLeadWeb_org.fileContentType}">
							<span  title="${SourceLeadWeb_orgName}"><b>${SourceLeadWeb_orgName}</b></span>
						</c:if>
					</div>
					<div class="welCoInnRight">
						<p>${SourceLeadWeb_org.orgDescription}</p>
					</div>
					<div class="clearfix"></div>
				</div>
				
				
			 <c:if test="${SourceLeadWeb_OnboardingManagerUserId != null}">
					<h4>Manager</h4>
			        <div class="welMana">
						<img onerror="predefineProfileImage(this);" class='m-t-xs' src="<c:url value='${sourceleadUrl}/displayOnboardingUserImage/${SourceLeadWeb_OnboardingManagerUserId}'/>"></img>
						<span>
							<b><u>Name</u>: ${SourceLeadWeb_managerName}</b>
						</span>
						<div class="clearfix"></div>
			        </div>
			        <br>
			</c:if>	 			
		
	<c:if test="${SourceLeadWeb_OnboardingTeamOwnerUserId != null}">
		<h4>Team Owner</h4>
        <div class="welMana">
			<img onerror="predefineProfileImage(this);" class='m-t-xs' src="<c:url value='${sourceleadUrl}/displayOnboardingUserImage/${SourceLeadWebSourceLeadWeb_OnboardingTeamOwnerUserId}'/>"></img>
			<span>
				<b><u>Name</u>: ${SourceLeadWeb_teamOwnerName}</b>
				<b><u>Role</u>: ${SourceLeadWeb_teamOwnerRole}</b>
				<b><u>Team</u>: <a href="javascript:void(0)">${SourceLeadWeb_teamName}</a> 
				</b>
			</span>
			<div class="clearfix"></div>
        </div>
        
	<br><h4>Team</h4>
	<div id="liquid1" class="liquid" style="overflow: auto;">
		<span class="previous"><i class="fa fa-chevron-left"></i></span>
		<div class="wrapper">
			<ul>
				 <c:forEach var="teamMembers" items="${SourceLeadWeb_teamMembersList}">
					<li>
				        <div class="crsl-item">
							<img onerror='predefineProfileImage(this);' class='m-t-xs' src='<c:url value="${sourceleadUrl}/displayOnboardingUserImage/${teamMembers.memberUserId.id}"/>' ></img>
							<span>
								<b><u>Name</u>: ${teamMembers.memberName}</b>
								<b><u>Role</u>: ${teamMembers.roleName}</b>
							</span>
							<div class="clearfix"></div>
				        </div>			
					</li>
				 </c:forEach>
				
				
																												
			</ul>
		</div>
		<span class="next"><i class="fa fa-chevron-right"></i></span>
	</div>	
</c:if>
		
		<c:if test="${SourceLeadWeb_supportedDocumentsListSize > 0}">
		<br><h4>Support Documents</h4>
		<div class="docu">
		<c:forEach var="docFiles" items="${SourceLeadWeb_supportedDocumentsList}">
					<a href="${sourceleadUrl}/downloadTemplateDocument?documentUUID=${docFiles.documentUUID}"><i class="fa fa-file-text" aria-hidden="true"></i><span class="tbldata-dotdot_80">${docFiles.fileName}</span></a>
		</c:forEach>
	    </div>	
	    </c:if>						
  </div>		  
		  
<div class="welBtns">
<input type="button"  value="Proceed to next step"  onClick="javascript:window.location.href = '<c:url value='/onboardingCandidateSetp2'/>'"  class="Brig">
<div class="clearfix"></div>
</div>
		  
		</section>
	</section>  

</div>

 <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
 <script type="text/javascript" src="resources/js/jquery-1.11.0.min.js"></script>
 <script type="text/javascript" src="resources/js/jquery.liquidcarousel.pack.js"></script>	
<script>
	$(document).ready(function() {
		$('#liquid1').liquidcarousel({height:129, duration:100, hidearrows:false});
	});
</script>



<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
