<!DOCTYPE html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:if test="${staticTime == null || staticTime == 0 }">
	<c:set var='staticTime' value="<%=new java.util.Date().getTime()%>" scope="application"></c:set>
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
<script src="/resources/js/jquery-1.11.1.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
<style type="text/css">
.require_wrap_table.overflowVisible {
	overflow: visible !important;
}
</style>
</head>
<script type="text/javascript">
 var sourceleadUrl ='${sourceleadUrl}';
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
						<span title="${SourceLeadWeb_orgName}">${SourceLeadWeb_orgName}</span>
					</div>
				</c:if>
				<div class="welSiO" onclick="logoutFormSubmit();" title="Sign Out">
					<i class="fa fa-sign-out"></i>
				</div>
				<ul>
					<li class="actLi"><span class="fill">1</span><b>Details</b><i class="line"></i></li>
					<li class="actLi"><span class="fill">2</span><b>Check List</b><i class="line"></i></li>
					<li class="actLi"><span class="fill">3</span><b>Tasks</b><i class="line"></i></li>
					<li class="actLi"><span>4</span><b>Documents</b><i class="line"></i></li>
					<div class="clearfix"></div>
				</ul>
				<div class="clearfix"></div>
			</div>
			<div class="welCo1">
				<div class="step3Wrap">
					<h2>Resignation / Termination Documents</h2>
					<c:if test="${resignationDocumentsListSize > 0}">
						<c:forEach var="teminationDocs" items="${resignationDocumentsList}">
							<div class="step3">
								<span><a href="${teminationDocs.fileURL}" target="_blank" class="fa fa-file-text"></a></span> <strong>
									<p>${teminationDocs.fileName}</p>
								</strong>
								<div class="clearfix"></div>
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${resignationDocumentsListSize == 0}">
						<ul>
							<li>No Document Found</li>
						</ul>
					</c:if>
					<div class="clearfix"></div>
				</div>
				<div class="step3Wrap">
					<h2 class="padTop">Offboard Pack Documents</h2>
					<c:if test="${offboardPackDocumentsListSize > 0}">
						<c:forEach var="offboardPackDocs" items="${offboardPackDocumentsList}">
							<div class="step3">
								<span><a href="${offboardPackDocs.fileURL}" target="_blank" class="fa fa-file-text"></a></span> <strong>
									<p>${offboardPackDocs.fileName}</p>
								</strong>
								<div class="clearfix"></div>
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${offboardPackDocumentsListSize == 0}">
						<ul>
							<li>No Document Found</li>
						</ul>
					</c:if>
					<div class="clearfix"></div>
				</div>
			</div>
			<div class="welBtns">
				<input type="button" onClick="javascript:window.location.href = '<c:url value='/app/offboardStep3'/>'" value="Previous"><input type="button" value="Finish" class="Brig">
				<div class="clearfix"></div>
			</div>
		</section>
	</section>
</div>
<div class="side_mask"></div>
<div class="welPop">
	<p>
		<b><i class="fa fa-smile-o"></i>Thank You!</b> Thanks for your services, we're extremely glad to have you has part of ${SourceLeadWeb_orgName}
	</p>
	<span> <input type="button" onclick="goToDashboard();" value="Ok" class="Brig" />
		<div class="clearfix"></div>
	</span>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="/resources/js/jquery-ui.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/chosen.jquery.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
<script>
	$(".Brig").click(function(){
		$(".welPop").fadeIn(200);
		$(".side_mask").fadeIn(100);
	});	
	$(".welPop input").click(function(){
		$(".welPop").fadeOut(200);
		$(".side_mask").fadeOut(100);
	});	
	</script>
<script>
function goToDashboard(){
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/sendOffboardCandidateToDashboard?${_csrf.parameterName}=${_csrf.token}", 
	        success: function(response) {
	        	if (response.response === "AWKWARD") {
	        	    location.href = "/errorPage";
	        	} else if (response.response === "SUCCESS") {
	        		location.replace(sourceleadUrl+"/offboardSuccess"); 
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
<script>
		function logoutFormSubmit() {
			document.getElementById("logoutForm").submit();
		}
</script>
<c:url var="logoutUrl" value="/logout" />
<form action="${logoutUrl}" method="post" id="logoutForm">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
<form action="${logoutUrl}" method="post" id="logoutForm">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>