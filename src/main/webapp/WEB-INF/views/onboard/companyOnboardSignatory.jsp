<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>

<link rel="icon" type="image/png" href="/resources/images/fav.png" />
<title>Welcome to Source Lead :: The Recruitment Application</title>
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">


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
<script type="text/javascript" src='/resources/js/slead-common.js?staticTime=${staticTime}'></script>

<script type="text/javascript">
window.onload = function () {
	loadLeagelSignerForm();
 };
</script>

<script>
  function navtoggle(elementRef){
	  
	 if('${SourceLeadWeb_prodSupportLoginUserRole}' != 'ROLE_PRODUCTION_SUPPORT'){
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
	 
	 if('${SourceLeadWeb_prodSupportLoginUserRole}' == 'ROLE_PRODUCTION_SUPPORT'){
		 document.getElementById("spockLinksList").removeAttribute("class")
		 document.getElementById('spockLinksList').className = 'spoclinks-area'
	     document.getElementById("spockLinksList").style.display="block";
	 }
	 
	  
  }
  </script>

 <script>
	function predefineOrgImage(imageId){
		$(imageId).attr('src','/resources/images/office-image.jpg');
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

<div class="wraper"> 
	<section class="comLogBg">
			<section class="comLogInn">
			
		<div class="comLogHead">
		
		
		<jsp:include page="../userManageAccounts.jsp" />
          
				 <div class="comLogLogoText">${SourceLeadWeb_orgName}</div>
				  <div class="welSiO onboard-logoutbtn" onclick="logoutFormSubmit();" title="Sign Out"><i class="fa fa-sign-out mt5"></i></div>
				       <ul>
						  <li class="actLi">Organization details<i class="fa fa-check-circle"></i></li>
						  <li class="actLi">Locations<i class="fa fa-check-circle"></i></li>
						  <c:if test="${SourceLeadWeb_orgLocations.country == 'India'}">
						  <li class="actLi">Information<i class="fa fa-check-circle"></i></li>
						  </c:if>
						  <c:if test="${SourceLeadWeb_orgLocations.country == 'United States'}">
						  <li class="actLi">Federal & state taxes<i class="fa fa-check-circle"></i></li>
						  </c:if>
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
			  	 <div class="side_mask"></div>
			  	 <div id="popUp"></div> 
			     <h2>Organization Signatory</h2>
				     <div class="compSignatoryHelpMes">
						Person who can sign legal binding documents on behalf of your organisation.
					</div>
				     <div class="row clearfix"> 
					     <div class="col-md-6">
					     		<div class="form-group clearfix">
									<div class="col-md-8 padding_right">
				                <input id="legalSigner" name="legalSigner"  class="checkbox-custom"  value="Y" onclick="validateLegalSigner();"  type="checkbox">
		              			<label for="legalSigner" class="checkbox-custom-label">&nbsp;Make me as legal signer</label>
						            </div>
								</div>
					     </div>
				     </div>
				     
				    <div class="clearfix mt10"></div>
				  <div id="legalUserFormDiv"></div>
			     <div class="compSignatoryHelpMes mt">
						You can add more signatories in Org Settings.
					</div>
			     <div class="welBtns welBtnsA">
						<input type="button" value="Prev" onClick="javascript:window.location.href = '<c:url value='/app/companyOnboardDocuments'/>'"><input type="submit" value="Save & Finish" class="Brig" id="save_button">
						<input type="button" value="Skip & Finish" class="skipBtn" onclick="onboardMessage('Skip');"></a>
						<div class="clearfix"></div>
					</div>
			  	 </div>
			  </form:form>
	
			</section>
	</section>
</div>
					<div class="location-alert alert alert-danger alert-dismissable zInPop" id="locationAlert">
    <a href="#" class="loc-alertclose close">&times;</a>
    <strong>Alert !</strong> <span>Your are not able to Save Required data is Missing.</span>
  </div>
  
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
 <script type="text/javascript" src="/resources/js/jquery-1.11.0.min.js?staticTime=${staticTime}"></script>
 <script type="text/javascript" src="/resources/js/jquery.liquidcarousel.pack.js?staticTime=${staticTime}"></script>	
	
  <script src="/resources/js/jquery.dataTables.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/moment.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/jquery-ui.min.js?staticTime=${staticTime}"></script> 
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
</script> 
  <script src="/resources/js/formvalidator.js"></script>
  
  
  <script type="text/javascript">
 function getUserByUserEmail() {
	 var EmailId = document.getElementById("EmailId").value; 
	 var sourceleadUrl = '${sourceleadUrl}';
	 if(EmailId != null && EmailId != ""){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getUserByUserEmail?&EmailId="+EmailId, 
		        success: function(json) {
		        		
		        		if(json.firstName != null && json.firstName != ''){
		        			$("#firstName").val(json.firstName);
				        	$("#firstName").attr("readonly", "readonly");
				        	$('#firstNameDiv').find('p.jquery_form_error_message').remove();
		        		}else{
		        			$("#firstName").val('');
		        			 $("#firstName").removeAttr("readonly", "readonly");
		        		}
		        		
		        		if(json.lastName != null && json.lastName != ''){
		        			$("#lastName").attr("readonly", "readonly");
		        			$("#lastName").val(json.lastName);
		        		}else{
		        			$("#lastName").val('');
		        			 $("#lastName").removeAttr("readonly", "readonly");
		        		}
		        		
		        		if(json.mobilePhone != null && json.mobilePhone != ''){
		        			$("#phone").val(json.mobilePhone);
				        	$("#phone").attr("readonly", "readonly");
		        		}else{
		        			$("#phone").val('');
		        			 $("#phone").removeAttr("readonly", "readonly");
		        		}
		        		
		        },
		    });
	 }else{
		 $("#firstName").attr("disabled", false);
		 $("#firstName").val('');
		 $("#firstName").removeAttr("readonly", "readonly");
		 
		 $("#lastName").attr("disabled", false);
		 $("#lastName").val('');
		 $("#lastName").removeAttr("readonly", "readonly");
		 
		 $("#phone").attr("disabled", false);
		 $("#phone").val('');
		 $("#phone").removeAttr("readonly", "readonly");
	 }
 }
 $(".loc-alertclose").click(function(){
	 $(".location-alert").fadeOut();
})
 </script>  
 
 
 <script type="text/javascript">
 function validateUserEmail() {
	 var EmailId = document.getElementById("EmailId").value; 
	 $('#EmailIdDiv').find('p.jquery_form_error_message').remove();
	 var sourceleadUrl = '${sourceleadUrl}';
	 if(EmailId != null && EmailId != ""){
		 $("#save_button").attr("disabled", "disabled");
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateUserEmail?&EmailId="+EmailId, 
		        success: function(data) {
		        	if(data == 'true'){
		        		  $('#EmailIdDiv').find('p.jquery_form_error_message').remove();
						  $("input#EmailId").after("<p class='jquery_form_error_message'>"+EmailId+" is Already Existed</span>");
						  document.getElementById("EmailId").setAttribute('record-exist','yes');
						  document.getElementById("EmailId").setAttribute('record-exist-errorMsg',EmailId+' is Already Existed'); 
		        	}
		        	else{
		        		 $('input#EmailId').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#EmailIdDiv').find('p.jquery_form_error_message').remove(); 
                         $("#save_button").removeAttr("disabled");
		        	}
		        },
		    });
	 }
 }
 </script>  
 
  
  <script>
  function validateLegalSigner(){
	  if (document.getElementById('legalSigner').checked) {
		  $("#legalUserFormDiv").empty();
	  }else{
		  loadLeagelSignerForm();
	  }
  }
  </script>
 
<script>
function loadLeagelSignerForm(){
	$("#legalUserFormDiv").empty();
	var country  = '${SourceLeadWeb_orgLocations.country}';
	var result = "";
	result = result +"<div class='row clearfix'>";
	
	result = result +"<div class='col-md-6'>";
	result = result +"<div class='form-group clearfix' id='firstNameDiv'>";
	result = result +"<label class='col-md-4 noPad'>First Name<span class='text-danger'>&nbsp;*</span></label>";
	result = result +"<div class='col-md-8 padding_right'>";
	result = result +"<input class='form-control' type='text' name='firstName' id='firstName' field-name='First Name'  pattern='[A-Za-z\s]*' maxlength='100' data-validation='required validate_Space validate_AlphaNumber validate_length length1-100'>";
	result = result +"</div>";
	result = result +"</div>";
	result = result +"</div>";
	
	result = result +"<div class='col-md-6'>";
	result = result +"<div class='form-group clearfix'>";
	result = result +"<label class='col-md-4 noPad'>Last Name</label>";
	result = result +"<div class='col-md-8 padding_right'>";
	result = result +"<input class='form-control' type='text' name='lastName' id='lastName' field-name='Last Name' data-validation-optional='true' pattern='[A-Za-z\s]*' maxlength='100' data-validation='validate_Space validate_AlphaNumber validate_length length1-100'>";
	result = result +"</div>";
	result = result +"</div>";
	result = result +"</div>";

	result = result +"<div class='col-md-6' id='EmailIdDiv'>";
	result = result +"<div class='form-group clearfix'>";
	result = result +"<label class='col-md-4 noPad'>Email<span class='text-danger'>&nbsp;*</span></label>";
	result = result +"<div class='col-md-8 padding_right'>";
	result = result +"<input class='form-control' type='text' name='email' id='EmailId'  field-name='Email' data-validation='required validate_Space validate_length length2-100 validate_email'  onblur='getUserByUserEmail(); validateUserEmail();' onKeyUp='validateUserEmail();'>";
	result = result +"</div>";
	result = result +"</div>";
	result = result +"</div>";
	
	result = result +"<div class='col-md-6'>";
	result = result +"<div class='form-group clearfix'>";
	result = result +"<label class='col-md-4 noPad'>Phone Number</label>";
	result = result +"<div class='col-md-8 padding_right'>";
	result = result +"<input class='form-control' type='text' name='phoneNumber' id='phone'  field-name='Phone Number' data-validation='validate_Space validate_phone validate_length length9-20' data-validation-optional='true'>";
	result = result +"</div>";
	result = result +"</div>";
	result = result +"</div>";
	
	if(country == "United States"){
	result = result +"<div class='col-md-6'>";
	result = result +"<div class='form-group clearfix'>";
	result = result +"<label class='col-md-4 noPad'>SSN<span class='text-danger'>&nbsp;*</span></label>";
	result = result +"<div class='col-md-8 padding_right'>";
	result = result +"<input class='form-control' type='text' name='ssn' id='ssn' field-name='SSN'  maxlength='9' data-validation='required validate_Space validate_int validate_length length9-9'>";
	result = result +"</div>";
	result = result +"</div>";
	result = result +"</div>";
	}
	
	result = result +"<div class='col-md-6'>";
	result = result +"<div class='form-group clearfix'>";
	result = result +"<label class='col-md-4 noPad'>Role<span class='text-danger'>&nbsp;*</span></label>";
	result = result +"<div class='col-md-8 padding_right'>";
	result = result +"<select class='chosen-select form-control' id='role' name='roleType' field-name='Role' data-validation='required'>";
	result = result +"<option value=''>--Select Role--</option>";
		<c:forEach var="option" items="${rolesList}">
		 result = result +'<option value="${option.id}">${option.displayName}</option>';
		</c:forEach>
	result = result +"</select>";
	result = result +"</div>";
	result = result +"</div>";
	result = result +"</div>";
	
	
	result = result +"<div class='col-md-6'>";
	result = result +"<div class='form-group clearfix' id='firstNameDiv'>";
	result = result +"<label class='col-md-4 noPad'>Designation</label>";
	result = result +"<div class='col-md-8 padding_right'>";
	result = result +"<input class='form-control' type='text' name='designation' id='designation' field-name='Designation' data-validation-optional='true' data-validation='validate_Space validate_AlphaNumber validate_length length1-100'>";
	result = result +"</div>";
	result = result +"</div>";
	result = result +"</div>";
	
	if(country == "India"){
	result = result +"<div class='col-md-6'>";
	result = result +"<div class='form-group clearfix'>";
	result = result +"<label class='col-md-4 noPad'>Aadhar Number<span class='text-danger'>&nbsp;*</span></label>";
	result = result +"<div class='col-md-8 padding_right'>";
	result = result +"<input class='form-control' type='text' name='aadharNumber' id='aadharNumber' field-name='Aadhar Number'   data-validation='required validate_Space validate_aadharNumber'>";
	result = result +"</div>";
	result = result +"</div>";
	result = result +"</div>";
	
	
	
	result = result +"<div class='col-md-6'>";
	result = result +"<div class='form-group clearfix'>";
	result = result +"<label class='col-md-4 noPad'>PAN Number<span class='text-danger'>&nbsp;*</span></label>";
	result = result +"<div class='col-md-8 padding_right'>";
	result = result +"<input class='form-control' type='text' name='panNumber' id='panNumber' field-name='PAN Number'  data-validation='required validate_Space validate_panNumber'>";
	result = result +"</div>";
	result = result +"</div>";
	result = result +"</div>";
	}
	
	
	/* result = result +"<div class='col-md-6'>";
	result = result +"<div class='form-group clearfix'>";
	result = result +"<label class='col-md-4 noPad'>Designation</label>";
	result = result +"<div class='col-md-8 padding_right'>";
	result = result +"<select class='chosen-select form-control' id='designation' nSSNSSName='designationType' field-name='Designation' data-validation-optional='true' onchange='getDesignationType()'>";
	result = result +"<option value=''>--Select Designation--</option>";
	result = result +'<option value="CEO">CEO</option>';
	result = result +'<option value="CFO">CFO</option>';
	result = result +'<option value="COO">COO</option>';
	result = result +'<option value="President">President</option>';
	result = result +'<option value="Other">Other</option>';
	result = result +"</select>";
	result = result +"</div>";
	result = result +"</div>";
	result = result +"</div>"; */
	
	
	
	result = result +"<div class='col-md-6' id='designationOtherDiv'  style='display:none'>";
	result = result +"<div class='form-group clearfix'>";
	result = result +"<label class='col-md-4 noPad'>Designation Other Type<span class='text-danger'>&nbsp;*</span></label>";
	result = result +"<div class='col-md-8 padding_right'>";
	result = result +"<input class='form-control' type='text' name='designationOtherType' id='designationOtherType' field-name='Designation Other Type' data-validation-optional='true'>";
	result = result +"</div>";
	result = result +"</div>";
	result = result +"</div>";
	
	
	result = result +"</div>";
	
	$("#legalUserFormDiv").append(result);
	
	
}
</script>


<script>
function getDesignationType(){
	var designation = document.getElementById("designation").value;
	if(designation == 'Other'){
		 document.getElementById("designationOtherDiv").style.display="";
		 $("#designationOtherType").removeAttr("data-validation-optional");
		 $("#designationOtherType").attr("data-validation","required validate_Space validate_AlphaNumber validate_length length1-100");
	}else{
		 document.getElementById("designationOtherDiv").style.display="none";
		$("#designationOtherType").removeAttr("data-validation-optional");
		 $("#designationOtherType").attr("data-validation-optional","true");
		 $("#designationOtherType").removeAttr("data-validation");
		 $("#designationOtherType").attr("data-validation","required validate_Space validate_AlphaNumber validate_length length1-100");
	}
}
</script>


<script type="text/javascript">
$('body').on('submit', '#signatory_form', function(e){
	var validationSettings = {
	        errorMessagePosition : 'element',
	        borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         }; 
	
	if ($('#signatory_form').validate(false, validationSettings)){
      e.preventDefault();
      var sourceleadUrl ='${sourceleadUrl}';
      var formData = getFormData("signatory_form");
     
      if (document.getElementById('legalSigner').checked) {
    	 saveLoginUserAsOrgLocationSignatory(formData);
      }else{
        var isMailSent = "Y";
   	 	 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	       $.ajax({
		    	type: "POST",
		    	data : formData,
		    	  url: sourceleadUrl+"/createSingnatoryUser?${_csrf.parameterName}=${_csrf.token}&isMailSent="+isMailSent,		
		        success: function(result) {
		        	if (result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
      	    		    showError(result.responseMessage);
      	    		    $("#loading").removeAttr('style');
      	    		}
		        	else if(result.response === "AWKWARD"){
		        		 location.href = "/errorPage";
		        	}
		        	else if(result.response === "SUCCESS"){
		        		$("#loading").removeAttr('style');
		        	saveOrgLocationSignatory(formData);
		        	
		        	}else if (result.indexOf('Sign In') !== -1) {
      					location.replace(sourceleadUrl);
      				} else {
      				    showError("Operation failed, please try again");
      				    $("#loading").removeAttr('style');
      				}
		        },
		    });   
      }
		    
      return true; 
    }
    else{
    	return false;	
       }
  });

	$('body').on('blur', '#signatory_form', function() {
		$("#signatory_form").showHelpOnFocus();
		$("#signatory_form").validateOnBlur(false, validationSettings);
	});
  </script>
  
  
  <script>
  function saveOrgLocationSignatory(formData){
		var sourceleadUrl = '${sourceleadUrl}';
		   $.ajax({
		    	type: "POST",
		    	data : formData,
		    	 url: sourceleadUrl+"/saveCompanyOnBoardSignatory?${_csrf.parameterName}=${_csrf.token}", 
		        success: function(result) {
		        	if (result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
      	    		    showError(result.responseMessage);
      	    		    $("#loading").removeAttr('style');
      	    		}
		        	else if(result.response === "AWKWARD"){
		        		 location.href = "/errorPage";
		        	}
		        	else if(result.response === "SUCCESS"){
		        		$("#loading").removeAttr("style");
		        		onboardMessage();
		        	
		        	}else if (result.indexOf('Sign In') !== -1) {
      					location.replace(sourceleadUrl);
      				} else {
      				    showError("Operation failed, please try again");
      				    $("#loading").removeAttr('style');
      				}
		        
		        },
		    });
  }
  </script>
  
  
  <script>
  function saveLoginUserAsOrgLocationSignatory(){
	  var sourceleadUrl = '${sourceleadUrl}';
	  $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/saveLoginUserAsOrgLocationSignatory?${_csrf.parameterName}=${_csrf.token}", 
	        success: function(result) {
	        	if (result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
	    		    showError(result.responseMessage);
	    		    $("#loading").removeAttr('style');
	    		}
	        	else if(result.response === "AWKWARD"){
	        		 location.href = "/errorPage";
	        	}
	        	else if(result.response === "SUCCESS"){
	        		$("#loading").removeAttr("style");
	        		onboardMessage();
	        	
	        	}else if (result.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
				    showError("Operation failed, please try again");
				    $("#loading").removeAttr('style');
				}
	        
	        },
	    });
  }
  </script>
  
  <script>
  function onboardMessage(value){
	  var result = '';
	  result = result +'<div class="welPop">';
	  result = result +'<p><b><i class="fa fa-smile-o"></i>Congrats!</b>'; 
	  result = result +'You have effectively given all necessary information to onboarding and you formally acknowledged all archives</p>';
	  result = result +'<span class="clearfix">';
	  if(value == 'Skip'){
		  result = result +'<input type="button" value="Cancel" onclick="cancel();"/><input type="button" onclick="skipSignatory();" value="Goto Dashboard " class="Brig mb"/>';
	  }else{
		  result = result +'<input type="button" onclick="goToDashboard();" value="Goto Dashboard " class="Brig mb"/>';
	  }
	 
	  result = result +'</span>';
	  result = result +'</div>';
	  $("#popUp").append(result);
	  $(".welPop").fadeIn(200);
	  $(".side_mask").fadeIn(100);
  }
  </script>
  
  <script>
  function skipSignatory(){
	  var sourceleadUrl = '${sourceleadUrl}';
	  $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	   $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/skipAndSaveOrglocationSignatory?${_csrf.parameterName}=${_csrf.token}", 
	        success: function(data) {
	        	if(data == 'success'){
	        		$("#loading").removeAttr("style");
	        		goToDashboard()
	        	}
	        },
	    });
  }
  </script>
  
  <script>
function goToDashboard(){
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	var sourceleadUrl = '${sourceleadUrl}';
	
	 if('${SourceLeadWeb_prodSupportLoginUserRole}' != 'ROLE_PRODUCTION_SUPPORT'){
		 location.replace(sourceleadUrl+"/dashboard/${SourceLeadWeb_orgLocationId}"); 
	 }else{
		 location.replace(sourceleadUrl+"/dashboard/${SourceLeadWeb_prodSupportOrgLocationId}"); 
	 }
	 
}
</script>

<script>
function cancel(){
	$("#popUp").empty();
	$(".welPop").fadeOut(200);
	$(".side_mask").fadeOut(100);
}
</script>

<script>
  $(document).ready(function(){
  	  $('html').click(function(){
	  $('#spockLinksList').hide();
  });
  
  $(".offOrg").click(function( event ) {
	  event.stopPropagation();
	});
  });
</script>

