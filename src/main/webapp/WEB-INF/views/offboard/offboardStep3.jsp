<link rel="icon" type="image/png" href="resources/images/fav.png" />
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

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<div class="wraper"> 
	<section class="welcoBg">
		<section class="welcoInn">
		
		   <div class="welcoHead">
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
				  <li class="actLi"><span class="fill">1</span><b>Welcome</b><i class="line"></i></li>
				  <li class="actLi"><span class="fill">2</span><b>Step two</b><i class="line"></i></li>
				  <li class="actLi"><span>3</span><b>Step three</b><i class="line"></i></li>
				  <li><span>4</span><b>Finish</b><i class="line"></i></li>
				  <div class="clearfix"></div>
			  </ul>
			  <div class="clearfix"></div>
		  </div>
		  
		  
		  <div class="welBtns">
			<input type="button"  value="Previous" onClick="javascript:location.href = '<c:url value='/offboardStep2'/>'" >
			<input type="button"  value="Proceed to next step" id="save_button"  onclick="saveOffboardingCheckList();" class="Brig">
			<div class="clearfix"></div>
		</div>
		
		</section>
	</section>

</div>

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
  <script src="resources/js/jquery-ui.min.js"></script> 
  <script src="resources/js/chosen.jquery.min.js"></script> 
  

<script src="resources/js/formvalidator.js"></script> 


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