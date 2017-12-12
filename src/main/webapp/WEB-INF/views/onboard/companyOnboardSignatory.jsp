<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>

<link rel="icon" type="image/png" href="resources/images/fav.png" />
<title>Welcome to Source Lead :: The Recruitment Application</title>
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">


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
<script type="text/javascript" src='resources/js/slead-common.js?staticTime=${staticTime}'></script>

<div class="wraper"> 
	<section class="comLogBg">
			<section class="comLogInn">
			
		<div class="comLogHead">
				 <div class="comLogLogoText">${SourceLeadWeb_orgName}</div>
				  <div class="welSiO onboard-logoutbtn" onclick="logoutFormSubmit();" title="Sign Out"><i class="fa fa-sign-out mt5"></i></div>
				       <ul>
						  <li class="actLi">Organization Details<i class="fa fa-check-circle"></i></li>
						  <li class="actLi">Locations<i class="fa fa-check-circle"></i></li>
						  <li class="actLi">Federal & state taxes<i class="fa fa-check-circle"></i></li>
						  <li class="actLi">Banking details<i class="fa fa-check-circle"></i></li>
						  <li class="actLi">Documents<i class="fa fa-check-circle"></i></li>
						  <li class="hoverLi">Signatory</li>
						  <div class="clearfix"></div>
					  </ul>
				  <div class="clearfix"></div>
			  </div>
			  
			  <form:form class="login-form clearfix"  id="signatory_form"  name="signatory_form" method="post" > 
			  	 <div class="comLogContent">
			  	 <div id="loading"></div> 
			     <h2>Organization Signatory</h2>
				     <div class="compSignatoryHelpMes">
						Person who can sign legal binding documents on behalf of your organisation.
					</div>
			     <div class="row clearfix"> 
			     
			     </div>
			     
			     <div class="welBtns welBtnsA">
						<input type="button" value="Prev" onClick="javascript:window.location.href = '<c:url value='/companyOnboardDocuments'/>'"><input type="submit" value="Save & Continue" class="Brig" id="save_button">
						<div class="clearfix"></div>
					</div>
			  	 </div>
			  </form:form>
	
			</section>
	</section>
</div>

 <script type="text/javascript" src="resources/js/jquery-1.11.0.min.js?staticTime=${staticTime}"></script>
 <script type="text/javascript" src="resources/js/jquery.liquidcarousel.pack.js?staticTime=${staticTime}"></script>	
	
  <script src="resources/js/jquery.dataTables.min.js?staticTime=${staticTime}"></script> 
  <script src="resources/js/moment.min.js?staticTime=${staticTime}"></script> 
  <script src="resources/js/jquery-ui.min.js?staticTime=${staticTime}"></script> 
  <script src="resources/js/bootstrap-select.min.js?staticTime=${staticTime}"></script> 
  <script src="resources/js/bootstrap.min.js?staticTime=${staticTime}"></script> 
  <script src="resources/js/masterHeaderAction.js?staticTime=${staticTime}"></script> 
 <script src="resources/js/chosen.jquery.min.js?staticTime=${staticTime}"></script> 
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