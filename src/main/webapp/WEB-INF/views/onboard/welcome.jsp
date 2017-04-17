<!DOCTYPE html>
<html lang="en" manifest="/sourcelead.appcache">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" type="image/png" href="resources/images/fav.png" />
<title>Welcome to Source Lead :: The Recruitment Application</title>
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/bootstrap-select.css" rel="stylesheet">
<link href="resources/css/jquery.dataTables.min.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/jquery-ui.css">
<link rel="stylesheet" href="resources/css/chosen.min.css">
<link href="resources/css/font-awesome.min.css" rel="stylesheet">
<link href="resources/css/sourcelead-main.css" rel="stylesheet" id="myStyleSheet">
<link href="resources/css/timesheet.css" rel="stylesheet">


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
</head>
<body>
<div class="wraper"> 

  
  <!--   Welcome Screens   -->
  
	<section class="welcoBg">
		<section class="welcoInn">
		  <div class="welcoHead">
				<div class="welLogo"><img src="resources/images/w-logo.png" alt=""/></div>
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
				<h2>Welcome to Business Intelli Solutions!</h2>
				<h3>First and foremost, Welcome to the Company!</h3>
				<p>Below you can find out brief introduction about the Company and a glance at the manager, your team and support documents, next verify your personal information, documents and take a couple of minutes to view boarding undertakings </p><br>
				<h4>About the Company</h4>
				<div class="welCoInn">
					<div class="welCoInnLeft"><img src="resources/images/w-logo.png" alt=""/></div>
					<div class="welCoInnRight">
						<p>${SourceLeadWeb_org.orgDescription}</p>
					</div>
					<div class="clearfix"></div>
				</div>
		
<c:if test="${SourceLeadWeb_teamName != null}">
		<h4>Manager</h4>
        <div class="welMana">
			<img onerror="predefineProfileImage(this);" class='m-t-xs' src="<c:url value='${sourceleadUrl}/displayOnboardingUserImage/${SourceLeadWeb_teamOwnerUserId}'/>"></img>
			<span>
				<b><u>Name</u>: ${SourceLeadWeb_managerName}</b>
				<b><u>Role</u>: ${SourceLeadWeb_teamOwnerRole}</b>
				<b><u>Team</u>: <a href="javascript:void(0)">${SourceLeadWeb_teamName}</a> 
				</b>
			</span>
			<div class="clearfix"></div>
        </div>
        
	<br><h4>Team</h4>
	<div class="welTeam">
      <div id="navbtns" class="welArrows clearfix">
        <a href="#" class="previous"><i class="fa fa-chevron-left"></i></a>
        <a href="#" class="next"><i class="fa fa-chevron-right"></i></a>
      </div>
										
	<div class="crsl-items" data-navigation="navbtns">
      <div class="crsl-wrap">
      
      <c:forEach var="teamMembers" items="${SourceLeadWeb_teamMembersList}">
        <div class="crsl-item">
			<img onerror='predefineProfileImage(this);' class='m-t-xs' src='<c:url value="${sourceleadUrl}/displayOnboardingUserImage/${teamMembers.memberUserId.id}"/>' ></img>
			<span>
				<b><u>Name</u>: ${teamMembers.memberName}</b>
				<b><u>Role</u>: ${teamMembers.roleName}</b>
			</span>
			<div class="clearfix"></div>
        </div>
        </c:forEach>
      </div>
    </div>					
</div>	
</c:if>
		
		<c:if test="${SourceLeadWeb_supportedDocumentsList != null}">
		<br><h4>Support Documents</h4>
		<div class="docu">
		<c:forEach var="docFiles" items="${SourceLeadWeb_supportedDocumentsList}">
					<a href="${sourceleadUrl}/downloadTemplateDocument?documentUUID=${docFiles.documentUUID}"><i class="fa fa-file-word-o" aria-hidden="true"></i><span class="tbldata-dotdot_80">${docFiles.fileName}</span></a>
		</c:forEach>
	    </div>	
	    </c:if>						
  </div>		  
		  
<div class="welBtns">
<input type="button"  value="Proceed to next step"  onClick="javascript:location.href = '<c:url value='/onboardingCandidateSetp2'/>'"  class="Brig">
<div class="clearfix"></div>
</div>
		  
		</section>
	</section>  
  
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 


  <script type="text/javascript" src="resources/js/jquery-1.11.0.min.js"></script>
  <script type="text/javascript" src="resources/js/responsiveCarousel.min.js"></script>

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


</div>

</body>
</html>