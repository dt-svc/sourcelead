<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>

<link rel="icon" type="image/png" href="/resources/images/fav.png" />
<title>Welcome to Source Lead :: The Recruitment Application</title>
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<!-- Bootstrap -->


<link href="/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/bootstrap-select.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/jquery.dataTables.min.css?staticTime=${staticTime}" rel="stylesheet">
<link rel="stylesheet" href="/resources/css/jquery-ui.css?staticTime=${staticTime}">
<link rel="stylesheet" href="/resources/css/chosen.min.css?staticTime=${staticTime}">
<link href="/resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/sourcelead-main.css?staticTime=${staticTime}" rel="stylesheet" id="myStyleSheet">
<link href="/resources/css/timesheet.css?staticTime=${staticTime}" rel="stylesheet">
<link rel="stylesheet" href="/resources/css/bootstrap-select.min.css?staticTime=${staticTime}">
<link href="/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet" type="text/css">

<script type="text/javascript">
 var sourceleadUrl ='${sourceleadUrl}';
</script>

<script>
	function predefineProfileImage(imageId){
		$(imageId).attr('src','/resources/images/userprofileImg.jpg');
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
    
<div class="wraper"> 
	<section class="welcoBg">
		<section class="welcoInn">
		  <div class="welcoHead">
				 
	 <jsp:include page="../userManageAccounts.jsp" />
				 
				 <c:if test="${!empty SourceLeadWeb_org.fileContentType}">
					<div class="welLogo">
					<img class="logo" data-id="orgLogoImage" src="${SourceLeadWeb_org.fileUrl}" title="${SourceLeadWeb_orgName}" />
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
				  <li><span>4</span><b>Step four</b><i class="line"></i></li>
				  <li><span>5</span><b>Step five</b><i class="line"></i></li>
				  <li><span>6</span><b>Finish</b><i class="line"></i></li>
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
							<img class="logo" data-id="orgLogoImage" src="${SourceLeadWeb_org.fileUrl}" title="${SourceLeadWeb_orgName}" />
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
					<a  href="${docFiles.fileURL}"><i class="fa fa-file-text" aria-hidden="true"></i><span class="tbldata-dotdot_80">${docFiles.fileName}</span></a>
		</c:forEach>
	    </div>	
	    </c:if>						
  </div>		  
		  
<div class="welBtns">
<input type="button"  value="Proceed to next step"  onClick="javascript:window.location.href = '<c:url value='${sourceleadUrl}/onboardingCandidateStep2'/>'"  class="Brig">
<div class="clearfix"></div>
</div>
		  
		</section>
	</section>  

</div>

 <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
 <script type="text/javascript" src="/resources/js/jquery-1.11.0.min.js?staticTime=${staticTime}"></script>
 <script type="text/javascript" src="/resources/js/jquery.liquidcarousel.pack.js?staticTime=${staticTime}"></script>	
<script>
	$(document).ready(function() {
		$('#liquid1').liquidcarousel({height:129, duration:100, hidearrows:false});
	});
</script>




