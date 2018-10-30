<!DOCTYPE html>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
<link rel="stylesheet" href="/resources/css/jquery-ui.css">
<link rel="stylesheet" href="/resources/css/chosen.min.css?staticTime=${staticTime}">
<link href="/resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/sourcelead-main.css?staticTime=${staticTime}" rel="stylesheet" id="myStyleSheet">
<link href="/resources/css/timesheet.css?staticTime=${staticTime}" rel="stylesheet">


<script src="/resources/js/jquery-1.11.1.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
<script src="https://maps.google.com/maps/api/js?libraries=places&v=3&key=${googleMapsLocationApiKey}" type="text/javascript"></script>

<script type="text/javascript">
		window.onload = function () {
			initLocationAuto('onBoardLocatin','onBoardCountry','onBoardState','onBoardCity','onBoardZipCode')
			getOnBoardCandidateCountry('${SourceLeadWeb_candidateOnBoarding.country}');
			getOnBoardCandidateStates('${SourceLeadWeb_candidateOnBoarding.country}','${SourceLeadWeb_candidateOnBoarding.state}');
			getOnBoardCandidateCities('${SourceLeadWeb_candidateOnBoarding.country}','${SourceLeadWeb_candidateOnBoarding.state}','${SourceLeadWeb_candidateOnBoarding.city}');
		 };
</script>

<script type="text/javascript">
 var sourceleadUrl ='${sourceleadUrl}';
</script>

<style>
.pac-container::after {
	display:none;
}
</style>




<div class="wraper"> 
  <!--   Welcome Screens   -->
  
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
				                     <img onerror='predefineOrgImage(this);' data-id="orgLogoImage" src="${links.orgLocations.org.fileUrl}">
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
				  <li class="actLi"><span class="fill">1</span><b>Welcome</b><i class="line"></i></li>
				  <li class="actLi"><span class="fill">2</span><b>Step two</b><i class="line"></i></li>
				  <li class="actLi"><span class="fill">3</span><b>Step three</b><i class="line"></i></li>
				  <li class="actLi"><span>4</span><b>Step four</b><i class="line"></i></li>
				  <li><span>5</span><b>Step five</b><i class="line"></i></li>
				  <li><span>6</span><b>Finish</b><i class="line"></i></li>
				  <div class="clearfix"></div>
			  </ul>
			  <div class="clearfix"></div>
		  </div>
		  
 <form:form class="login-form clearfix"  id="onboardCandidatePfEsi_form"  name="onboardCandidatePfEsi_form" method="post" >
 <div id="loading"></div>
<div class="welCo1">
<h5> PF & ESI Details </h5>
<div class="form-horizontal padBot">
<input type="hidden" id="id" name="id" value="${associatePfEsi.id}">

			<div class="clearfix row minHeightcol5">
              <div class="col-md-12 mb">
                <div class="clearfix row">
                  <div class="col-md-6 mb checkbox-new">
                  <c:if test="${associatePfEsi.eligibleForPf eq 'Y'}">
                  		<input id="pf" class="checkbox-custom" checked name="eligibleForPf" type="checkbox"   onClick="pfFunction()">
                  </c:if>
                   <c:if test="${empty associatePfEsi.eligibleForPf}">
                      	<input id="pf" class="checkbox-custom" name="eligibleForPf" type="checkbox"   onClick="pfFunction()">
                  </c:if>
					<label for="pf" class="checkbox-custom-label"><span class="ml10">Eligible for PF</span></label>
                    <div>
                    </div>
                  </div>
                  <div class="clearfix"></div>
                  <div class="clearfix" id="pfEligible" style="display: none;">
                   <div class="col-md-5" id="uanError">
                    <label>UAN Number <span class="text-danger paddingLeft">*</span></label>
                    <div>
					<input type="text" class="form-control" name="uanNumber" id="pfUan"  value="${associatePfEsi.uanNumber}" field-name="PF UAN">
                    </div>
                  </div>
                  
                  <div class="col-md-5" id="pfError">
                    <label>PF Number <span class="text-danger paddingLeft">*</span></label>
                    <div>
                       <input type="text" class="form-control" name="pfNumber"  id="pfNumber" value="${associatePfEsi.pfNumber}" field-name="PF Number">
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Enrolled Date </label>
                    <div>
                   <input type="text" class="form-control calendar" name="enroleDate"  value="${enrolledDate}" id="enrolledDate" >
                    </div>
                  </div>
                  </div>
                  <div class="clearfix"></div>
                   <div class="col-md-6 mb checkbox-new">
                   <c:if test="${associatePfEsi.eligibleForEsi eq 'Y'}">
                             <input id="esi" class="checkbox-custom"  checked name="eligibleForEsi" type="checkbox"  onClick="esiFunction()">
                   </c:if>
                   <c:if test="${empty associatePfEsi.eligibleForEsi}">
                   			<input id="esi" class="checkbox-custom" name="eligibleForEsi" type="checkbox"  onClick="esiFunction()">
                   </c:if>
					<label for="esi" class="checkbox-custom-label"><span class="ml10">Eligible for ESI</span></label>
                    <div>
                    </div>
                  </div>
                  <div class="clearfix"></div>
                  
                  
                  <div class="col-md-5" id="esiEligible" style="display: none;">
                    <label>ESI Number</label>
                    <div>
                       <input type="text" class="form-control" name="esinumber" value="${associatePfEsi.esinumber}" id="esiNumber" data-validation="validate_int validate_length length1-17" data-validation-optional="true" field-name="ESI Number">
                    </div>
                  </div>
					<div class="clearfix"></div>
                </div>
              </div>
		</div>
</div>
 
</div>

		  
<div class="welBtns">
	<input type="button"  value="Previous" onClick="javascript:window.location.href = '<c:url value='${sourceleadUrl}/onboardingCandidateBankingDetails'/>'" >
	<input type="button"  value="Proceed to next step" id="save_button"  onclick="saveOnboardingCandidatePfEsiDetails();" class="Brig">
	<div class="clearfix"></div>
</div>
</form:form>
		  
		</section>
	</section>  
  </div>
  <!--   Welcome Screens   -->

  <!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
  
  				<div class="location-alert alert alert-danger alert-dismissable zInPop" id="locationAlert">
    <a href="#" class="loc-alertclose close">&times;</a>
    <strong>Alert !</strong> <span>Your are not able to Save Required data is Missing.</span>
  </div>
  
  
  
    
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
  <script src="/resources/js/jquery.dataTables.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/moment.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/bootstrap-select.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/bootstrap.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/masterHeaderAction.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/chosen.jquery.min.js?staticTime=${staticTime}"></script> 
  <script type="text/javascript">
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});

});
			  $(".loc-alertclose").click(function(){
					 $(".location-alert").fadeOut();
				})		 
</script> 

<script src="/resources/js/formvalidator.js"></script>
<script src="../resources/js/jquery-ui.js"></script>
<script>
$(function() {
	var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormatProjectSave = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormatProjectSave = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormatProjectSave = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormatProjectSave = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormatProjectSave = 'yy-dd-mm'; 
	  }
	  $('#enrolledDate').datepicker({
			dateFormat : finalDateFormatProjectSave,
			changeMonth: true,
			changeYear: true, 
			 
		});
});
</script>
 
<script>
 function saveOnboardingCandidatePfEsiDetails(){
	 var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
	 
	 if ($('#onboardCandidatePfEsi_form').validate(false, validationSettings)){ 
	        var formData = getFormData("onboardCandidatePfEsi_form");
	        $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	         $.ajax({
		    	type: "POST",
		    	data: formData,
		    	 url: sourceleadUrl+"/saveOnboardingCandidatePfEsiDetails?${_csrf.parameterName}=${_csrf.token}",
		        success: function(response) {
		        	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
      	    		    showError(response.responseMessage);
      	    		    $("#loading").removeAttr('style');
      	    		}
		        	else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		location.replace(sourceleadUrl+"/onboardingCandidateStep3");  
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        },
		    });  
	        return true;
	  } else {
		return false;
	  }
 }
 
 $('body').on('blur', '#onboardCandidatePfEsi_form', function() {
		$("#onboardCandidatePfEsi_form").showHelpOnFocus();
		$("#onboardCandidatePfEsi_form").validateOnBlur(false, validationSettings);  
	});
 </script>
 
 
 <script type="text/javascript">
(function ($) {
   $.fn.serializeFormJSON = function () {

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
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
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };

        
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
		$(imageId).attr('src','/resources/images/office-image.jpg');
	}
</script>

<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<script>
//Pf Function
function pfFunction() {
    var checkBox = document.getElementById("pf");
    var pfel = document.getElementById("pfEligible");
    var val = checkBox.checked;
    var dbVal = '${associatePfEsi.eligibleForPf}';
    if (checkBox.checked == true){
        pfel.style.display = "block";
        $("#pfUan").attr("data-validation", "required validate_int validate_length length1-12");
        $("#pfNumber").attr("data-validation", "required validate_AlphaNumber validate_length length1-19");
        $("#pf").val('pf');
    } else {
       pfel.style.display = "none";
    	$('#pfUan').removeAttr('data-validation');
    	$('#pfNumber').removeAttr('data-validation');
    	$("#uanError").find("p.jquery_form_error_message").remove();
    	$("#pfError").find("p.jquery_form_error_message").remove();
    	$("#pfUan").val('');
    	$("#pfNumber").val('');
    	$("#enrolledDate").val('');
    }
}
		//ESI Function
function esiFunction() {
    var checkBox = document.getElementById("esi");
    var esiEl = document.getElementById("esiEligible");
    if (checkBox.checked == true){
       esiEl.style.display = "block";
       $("#esi").val('esi');
    } else {
       esiEl.style.display = "none";
       $("#esiNumber").val('');
    }
}
</script>
<script type="text/javascript">
$(document).ready(function(){
	pfFunction();
	esiFunction();
});
</script>

<script src="/resources/js/slead-common.js?staticTime=${staticTime}"></script>
