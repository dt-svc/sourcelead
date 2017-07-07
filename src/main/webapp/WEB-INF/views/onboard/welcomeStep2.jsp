<!DOCTYPE html>
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

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="resources/js/jquery-1.11.1.min.js"></script>
<script src="resources/js/formvalidator.js"></script>
<script src="https://maps.google.com/maps/api/js?libraries=places&v=3&key=AIzaSyDzIM1uth14u-kSIN_jZ75pRelInyGnYWU" type="text/javascript"></script>

<script type="text/javascript">
		window.onload = function () {
			initLocationAuto('onBoardLocatin','onBoardCountry','onBoardState','onBoardCity','onBoardZipCode')
			getOnBoardCandidateCountry('${SourceLeadWeb_candidateOnBoarding.country}');
			getOnBoardCandidateStates('${SourceLeadWeb_candidateOnBoarding.country}','${SourceLeadWeb_candidateOnBoarding.state}');
			getOnBoardCandidateCities('${SourceLeadWeb_candidateOnBoarding.country}','${SourceLeadWeb_candidateOnBoarding.state}','${SourceLeadWeb_candidateOnBoarding.city}');
		 };
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
				  <li class="actLi"><span class="fill">1</span><b>Welcome</b><i class="line"></i></li>
				  <li class="actLi"><span>2</span><b>Step two</b><i class="line"></i></li>
				  <li><span>3</span><b>Step three</b><i class="line"></i></li>
				  <li><span>4</span><b>Finish</b><i class="line"></i></li>
				  <div class="clearfix"></div>
			  </ul>
			  <div class="clearfix"></div>
		  </div>
		  
 <form:form class="login-form clearfix"  id="onboardCandidate_form"  name="onboardCandidate_form" method="post" >
 <div id="loading"></div>
<div class="welCo1">
<h5>Employee Details</h5>
<div class="form-horizontal padBot">
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
			<div class="form-group">
			  <label class="col-md-4 control-label">First Name <span class="text-danger paddingLeft">*</span></label>
			  <div class="col-md-8">
				<input type="text" class="form-control input-sm" name="firstName" field-name="First Name" data-validation="required validate_Space validate_AlphaNumber validate_length length1-100"  id="firstName" value="${SourceLeadWeb_user.firstName}">
			  </div>
			</div>
		</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">Last Name</label>
			  <div class="col-md-8">
				<input type="text" class="form-control input-sm" name="lastName" id="lastName" data-validation-optional="true" data-validation="validate_Space validate_AlphaNumber validate_length length1-100" value="${SourceLeadWeb_user.lastName}">
			  </div>
			</div>
		</div>
	</div>
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
			<div class="form-group">
			  <label class="col-md-4 control-label">Street Address 1 <span class="text-danger paddingLeft">*</span></label>
			  <div class="col-md-8">
				<input type="text" class="form-control input-sm" name="address1" data-validation="required validate_Space"  id="address1" value="${SourceLeadWeb_candidateOnBoarding.address1}">
			  </div>
			</div>
		</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">Street Address 2</label>
			  <div class="col-md-8">
				<input type="text" class="form-control input-sm" name="address2" data-validation-optional="true" data-validation="validate_Space" id="address1" value="${SourceLeadWeb_candidateOnBoarding.address2}">
			  </div>
			</div>
		</div>
	</div>
	
	
	
	<div class="clearfix">
		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
				  <div class="form-group">
				  <label class="col-md-4 control-label">Location <span class="text-danger paddingLeft">*</span></label>
				  <div class="col-md-8">
					<input type="text" class="form-control" data-placeholder="Location" id="onBoardLocatin"  name="location" value="${SourceLeadWeb_candidateOnBoarding.location}">
					<i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('onBoardLocatin','onBoardCountry','onBoardState','onBoardCity','onBoardZipCode');"></i>
				  </div>
				</div>
		</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
			  <div class="form-group">
			  <label class="col-md-4 control-label">Country <span class="text-danger paddingLeft">*</span></label>
			  <div class="col-md-8">
				<input type="text" class="form-control"  data-placeholder="Country" id="onBoardCountry"  name="country" value="${SourceLeadWeb_candidateOnBoarding.country}" readonly>
			  </div>
			</div>
		</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">State</label>
			  <div class="col-md-8">
				 <input type="text" class="form-control" data-placeholder="State"  id="onBoardState" name="state" value="${SourceLeadWeb_candidateOnBoarding.state}" readonly>
			  </div>
			</div>
		</div>
		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">City</label>
			  <div class="col-md-8">
				 <input type="text" class="form-control"  data-placeholder="City" id="onBoardCity" name="city" name="state" value="${SourceLeadWeb_candidateOnBoarding.city}" readonly>
			  </div>
			</div>
		</div>
	</div>
	<div class="clearfix">
 		
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">                               
			<div class="form-group">
			  <label class="col-md-4 control-label">Zipcode</label>
			  <div class="col-md-8">
				 <input type="text" class="form-control input-sm" id="onBoardZipCode" name="zipCode" value="${SourceLeadWeb_candidateOnBoarding.zipCode}"  data-validation-optional="true"  data-validation="validate_Space validate_length length1-10" >
			  </div>
			</div>
		</div>	
		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">Mobile number <span class="text-danger paddingLeft">*</span></label>
			  <div class="col-md-8">
				<input type="text" class="form-control input-sm" id="mobileNumber" value="${SourceLeadWeb_candidateOnBoarding.mobileNumber}" name="mobileNumber" >
			  </div>
			</div>
		</div>
	</div>	
	<div class="clearfix">
 		
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">                               
			<div class="form-group">
			  <label class="col-md-4 control-label">Personal Email</label>
			  <div class="col-md-8">
				<input type="text" class="form-control input-sm" id="personalEmail" value="${SourceLeadWeb_candidateOnBoarding.personalEmail}" name="personalEmail" readonly="readonly">
			  </div>
			</div>
		</div>	
		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">Company Email</label>
			  <div class="col-md-8">
				<input type="text" class="form-control input-sm" id="companyEmail" value="${SourceLeadWeb_candidateOnBoarding.companyEmail}" name="companyEmail" readonly="readonly" data-validation-optional="true" >
			  </div>
			</div>
		</div>
	</div>
	 
</div>
 
 <h5>Position Details</h5>
 <div class="form-horizontal">
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
			<div class="form-group">
			  <label class="col-md-4 control-label">Role</label>
			  <div class="col-md-8">
				  <input type="text" class="form-control input-sm" id="role" value="${SourceLeadWeb_displayName}"  readonly="readonly"  >
			  </div>
			</div>
		</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">Date Of Join</label>
			  <div class="col-md-8">
				<input type="text" class="form-control input-sm" id="startDate" value="${SourceLeadWeb_candidateOnBoarding.joiningDateStr}"  readonly="readonly"  >
			  </div>
			</div>
		</div>
	</div>
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
			<div class="form-group">
                  <label class="col-md-4 control-label">End date</label>
                  <div class="col-md-8">
                   <input type="text" class="form-control input-sm" id="endDate" value="${SourceLeadWeb_candidateOnBoarding.endDateStr}"  readonly="readonly"  >
                  </div>
                </div>
			</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
                <div class="form-group">
                  <label class="col-md-4 control-label">Rate / Salary</label>
                  <div class="col-md-8">
                    <input type="text" class="form-control input-sm" id="salaryRate" name="salaryRate" readonly="readonly" value="${SourceLeadWeb_candidateOnBoarding.salaryRate}">
                  </div>
                </div>
		</div>
	</div>
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                <div class="form-group">
                  <label class="col-md-4 control-label">Manager</label>
                  <div class="col-md-8">
                   <input type="text" class="form-control input-sm" id="reportingManager" name="reportingManager" readonly="readonly" value="${SourceLeadWeb_managerName}">
                  </div>
                </div>
		</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
                <div class="form-group">
                  <label class="col-md-4 control-label">Team</label>
                  <div class="col-md-8">
                    <input type="text" class="form-control input-sm" id="team" name="team" readonly="readonly" value="${SourceLeadWeb_teamName}">
                  </div>
                </div>
		</div>
	</div>
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
                <div class="form-group">
                  <label class="col-md-4 control-label">Employment Type</label>
                  <div class="col-md-8">
                    <input type="text" class="form-control input-sm" id="employmentType" name="employmentType" readonly="readonly" value="${SourceLeadWeb_candidateOnBoarding.employmentType == 'Other' ? SourceLeadWeb_candidateOnBoarding.employmentOtherType : SourceLeadWeb_candidateOnBoarding.employmentType}">
                  </div>
                </div>
		</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">                               
                  <div class="form-group">
                  <label class="col-md-4 control-label">Probation Notice</label>
                  <div class="col-md-8">
                    <input type="text" class="form-control input-sm" id="probationNotice" name="probationNotice" readonly="readonly" value="${SourceLeadWeb_candidateOnBoarding.probationNotice}">
                  </div>
                </div>
		</div>	
	</div>	
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
                  <div class="form-group">
                  <label class="col-md-4 control-label">Termination Notice</label>
                  <div class="col-md-8">
                     <input type="text" class="form-control input-sm" id="terminationNotice" name="terminationNotice" readonly="readonly" value="${SourceLeadWeb_candidateOnBoarding.terminationNotice}">
                  </div>
                </div>
		</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">                               
                  <div class="form-group">
                  <label class="col-md-4 control-label">Due Date</label>
                  <div class="col-md-8">
                   <input type="text" class="form-control input-sm" id="dueDate" value="${SourceLeadWeb_candidateOnBoarding.dueDateStr}"  readonly="readonly"  >
                  </div>
                </div>
		</div>	
	</div>
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
                  <div class="form-group">
                  <label class="col-md-4 control-label">Special Conditions</label>
                  <div class="col-md-8">
                      <textarea rows="2" class="form-control" id="specialConditions" name="specialConditions" readonly="readonly"  >${SourceLeadWeb_candidateOnBoarding.specialConditions}</textarea>
                  </div>
                </div>
		</div>
 			
	</div>				
	
	
</div>
</div>

		  
<div class="welBtns">
	<input type="button"  value="Previous" onClick="javascript:location.href = '<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>'" >
	<input type="button"  value="Proceed to next step" id="save_button"  onclick="saveOnboardingCandidateDetails();" class="Brig">
	<div class="clearfix"></div>
</div>
</form:form>
		  
		</section>
	</section>  
  </div>
  <!--   Welcome Screens   -->

  <!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
  
  
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
  <script src="resources/js/jquery.dataTables.min.js"></script> 
  <script src="resources/js/moment.min.js"></script> 
  <script src="resources/js/jquery-ui.min.js"></script> 
  <script src="resources/js/bootstrap-select.min.js"></script> 
  <script src="resources/js/bootstrap.min.js"></script> 
  <script src="resources/js/masterHeaderAction.js"></script> 
  <script src="resources/js/chosen.jquery.min.js"></script> 
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

<script src="resources/js/formvalidator.js"></script>




<script type="text/javascript">
function getOnBoardCandidateCountry(country){
	$.ajax({
    	type: "POST",
    	dataType: "json",
    	 url: '${sourceleadUrl}'+"/countryAutoComplete?${_csrf.parameterName}=${_csrf.token}",
          success: function(data) {
        	var json = JSON.parse(data);
        	$('#country').empty();
        	$('#country').append('<option value="">---Select---</option>');
            for(var i=0;i<json.length;i++){
            	if(json[i] == country){
            		$('#country').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
            	}
            	else{
            		$('#country').append('<option value="'+json[i]+'">'+json[i]+'</option>');
            	}
            	
            }
            $('#country').trigger("chosen:updated");
        },
        error: function(e){
        }
    }); 
} 
</script>

<script>
  function getStates(){
	  var countryName = $("#country").val();
	  getOnBoardCandidateStates(countryName);
  }
  </script>
  
  <script>
function getOnBoardCandidateStates(country,state){
	
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getStatesByCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+country, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#state').empty();
	        	$('#state').append('<option value="">---Select---</option>');
	            for(var i=0;i<json.length;i++){
	            	if(state != null && state != 'undefined' && json[i] == state){
	            		$('#state').append('<option value="'+state+'" selected>'+json[i]+'</option>');
	            	}
	            	else{
	            		$('#state').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            	}
	            }
	            $('#state').trigger("chosen:updated");
	            
	            $('#city').empty();
	            $('#city').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	
}
</script>

 <script>
  function getCities(){
	  var countryName = $("#country").val();
	  var stateName = $("#state").val();
	  getOnBoardCandidateCities(countryName,stateName);
  }
  </script>
  
 <script>
function getOnBoardCandidateCities(country,state,city){
	
	if(country != null && country != "" && country !="undefined" && state != null && state != "" && state !="undefined" ){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getCitiesByStateAndCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+country+"&stateName="+state, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#city').empty();
	        	$('#city').append('<option value="">---Select---</option>');
	            for(var i=0;i<json.length;i++){
	            	var selected = '';
	            	if(city != null && city != "" && city !="undefined" && json[i] == city){
	            		selected = 'selected';
	            	}
	            	$('#city').append('<option value="'+json[i]+'" '+selected+'>'+json[i]+'</option>');
	            }
	            $('#city').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#city').empty();
    	$('#city').append('<option value="">---Select---</option>');
    	$('#city').trigger("chosen:updated");
	}
}
</script> 


<script>
 function saveOnboardingCandidateDetails(){
	 var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
	 
	 if ($('#onboardCandidate_form').validate(false, validationSettings)){ 
		    var frm = $('#onboardCandidate_form').serializeFormJSON();
	        var con = JSON.stringify(frm);
	        con = con.replace(/[{}]/g, "");
	        
	        var jsonData = encodeURIComponent(con);
	        
	        var sourceleadUrl ='${sourceleadUrl}';
	        
	       
	       $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	         $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/saveOnboardingCandidateStepDetails?${_csrf.parameterName}=${_csrf.token}&json="+jsonData, 
		        success: function(onboardId) {
		        	
		        	location.replace(sourceleadUrl+"/onboardingCandidateSetp3");  
		        },
		    });  
	        return true;
		
	  }else{
		return false;
	  }
 }
 
 $('body').on('blur', '#onboardCandidate_form', function() {
		$("#onboardCandidate_form").showHelpOnFocus();
		$("#onboardCandidate_form").validateOnBlur(false, validationSettings);  
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
function initLocationAuto(locationId,countryId,stateId,cityId,zipCodeId) {
    initialize(locationId,countryId,stateId,cityId,zipCodeId);
}
function initialize(locationId,countryId,stateId,cityId,zipCodeId) {
 
    	autocomplete = new google.maps.places.Autocomplete((document.getElementById(locationId)), {
            types: ['geocode'],
      
        });
   
  google.maps.event.addListener(autocomplete, 'place_changed', function() {
  place = autocomplete.getPlace();
  var address = {};
  for (var i = 0; i < place.address_components.length; i++) {
    var addressType = place.address_components[i].types[0];
    switch (addressType) {
      case "locality":
        address.city = place.address_components[i].long_name;
        break;
      case "administrative_area_level_1":
        address.state = place.address_components[i].long_name;
        break;
      case "country":
        address.country = place.address_components[i].long_name;
        break;
      case "postal_code":
        address.zipcode = place.address_components[i].long_name;
        
    }
  }
  
  $("#"+countryId).val(address.country).change();
  $("#"+stateId).val(address.state).change();
  $("#"+cityId).val(address.city).change();
  $("#"+zipCodeId).val(address.zipcode).change();
  
});
}
</script>
<script>
function refreshGoogleApiLocation(locationId,countryId,stateId,cityId,zipCodeId){
	$("#"+locationId).val('');
	$("#"+countryId).val('');
	$("#"+stateId).val('');
	$("#"+cityId).val('');
	$("#"+zipCodeId).val('');
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

<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
