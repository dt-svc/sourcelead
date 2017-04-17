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

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="resources/js/jquery-1.11.1.min.js"></script>
<script src="resources/js/formvalidator.js"></script>


</head>

<body>


<script type="text/javascript">
		window.onload = function () {
			getOnBoardCandidateCountry('${SourceLeadWeb_candidateOnBoarding.country}');
			getOnBoardCandidateStates('${SourceLeadWeb_candidateOnBoarding.country}','${SourceLeadWeb_candidateOnBoarding.state}');
			getOnBoardCandidateCities('${SourceLeadWeb_candidateOnBoarding.country}','${SourceLeadWeb_candidateOnBoarding.state}','${SourceLeadWeb_candidateOnBoarding.city}');
		 };
</script>


<div class="wraper"> 

  
  <!--   Welcome Screens   -->
  
	<section class="welcoBg">
		<section class="welcoInn">
		  <div class="welcoHead">
				<div class="welLogo"><img src="resources/images/w-logo.png" alt=""/></div>
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
	<div class="row clearfix">
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
			  <label class="col-md-4 control-label">Last Name <span class="text-danger paddingLeft">*</span></label>
			  <div class="col-md-8">
				<input type="text" class="form-control input-sm" name="lastName" id="lastName" data-validation-optional="true" data-validation="validate_Space validate_AlphaNumber validate_length length1-100" value="${SourceLeadWeb_user.lastName}">
			  </div>
			</div>
		</div>
	</div>
	<div class="row clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
			<div class="form-group">
			  <label class="col-md-4 control-label">Street Addess 1 <span class="text-danger paddingLeft">*</span></label>
			  <div class="col-md-8">
				<input type="text" class="form-control input-sm" name="address1" data-validation="required validate_Space"  id="address1" value="${SourceLeadWeb_candidateOnBoarding.address1}">
			  </div>
			</div>
		</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">Street Addess 2</label>
			  <div class="col-md-8">
				<input type="text" class="form-control input-sm" name="address2" data-validation-optional="true" data-validation="validate_Space" id="address1" value="${SourceLeadWeb_candidateOnBoarding.address2}">
			  </div>
			</div>
		</div>
	</div>
	<div class="row clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
			  <div class="form-group">
			  <label class="col-md-4 control-label">Country <span class="text-danger paddingLeft">*</span></label>
			  <div class="col-md-8">
				<select class="chosen-select" data-placeholder="Select Country" id="country" onchange="getStates();" name="country">
                      <option disabled>Select Country</option>
                </select>
			  </div>
			</div>
		</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">State</label>
			  <div class="col-md-8">
				 <select class="chosen-select" data-placeholder="Select State" onchange="getCities();" id="state" name="state">
                      <option disabled>Select State</option>
                 </select>
			  </div>
			</div>
		</div>
	</div>
	<div class="row clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">City</label>
			  <div class="col-md-8">
				 <select class="chosen-select" data-placeholder="Select City" id="city" name="city">
                      <option disabled>Select City</option>
                 </select>
			  </div>
			</div>
		</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">                               
			<div class="form-group">
			  <label class="col-md-4 control-label">Zipcode</label>
			  <div class="col-md-8">
				 <input type="text" class="form-control input-sm" id="zipCode" name="zipCode" value="${SourceLeadWeb_candidateOnBoarding.zipCode}"  data-validation-optional="true"  data-validation="validate_Space validate_length length1-10" >
			  </div>
			</div>
		</div>	
	</div>	
	<div class="row clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">Mobile number <span class="text-danger paddingLeft">*</span></label>
			  <div class="col-md-8">
				<input type="text" class="form-control input-sm" id="mobileNumber" value="${SourceLeadWeb_candidateOnBoarding.mobileNumber}" name="mobileNumber" >
			  </div>
			</div>
		</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">                               
			<div class="form-group">
			  <label class="col-md-4 control-label">Personal Email</label>
			  <div class="col-md-8">
				<input type="text" class="form-control input-sm" id="personalEmail" value="${SourceLeadWeb_candidateOnBoarding.personalEmail}" name="personalEmail" readonly="readonly">
			  </div>
			</div>
		</div>	
	</div>
	<div class="row clearfix">
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
	<div class="row clearfix">
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
			  <label class="col-md-4 control-label">Start date</label>
			  <div class="col-md-8">
				<input type="text" class="form-control input-sm" id="startDate" value="${SourceLeadWeb_candidateOnBoarding.joiningDateStr}"  readonly="readonly"  >
			  </div>
			</div>
		</div>
	</div>
	<div class="row clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
			<div class="form-group">
                  <label class="col-md-4 control-label">End date ${SourceLeadWeb_candidateOnBoarding.endDateStr}</label>
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
	<div class="row clearfix">
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
	<div class="row clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
                <div class="form-group">
                  <label class="col-md-4 control-label">Employment Type</label>
                  <div class="col-md-8">
                    <input type="text" class="form-control input-sm" id="employmentType" name="employmentType" readonly="readonly" value="${SourceLeadWeb_candidateOnBoarding.employmentType}">
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
	<div class="row clearfix">
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
	<div class="row clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
                  <div class="form-group">
                  <label class="col-md-4 control-label">Speical Conditions</label>
                  <div class="col-md-8">
                      <textarea rows="2" class="form-control" id="specialConditions" name="specialConditions" readonly="readonly"  value="${SourceLeadWeb_candidateOnBoarding.specialConditions}" ></textarea>
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
	        
	       
	       $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
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


</div>

</body>
</html>