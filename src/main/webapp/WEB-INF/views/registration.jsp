
<!DOCTYPE html>

<html>
<head>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>::. Registration .::</title>
    <link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,300italic,300,400italic,500,700,900" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="resources/css/chosen.min.css">
    <link href="resources/css/font-awesome.min.css" rel="stylesheet">
    <link href="resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/register-style.css" rel="stylesheet">
     <link href="resources/css/crowdsourcing-main.css" rel="stylesheet" type="text/css">
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
     <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
    <%@page session="true"%>
</head>

<script>
window.onload = function () {
		var contactUUID = '${contactUUID}';
		if(contactUUID != null && contactUUID != '' && contactUUID != 'undefined'){
			validateEmail('Professional');
		}
	};
</script>

<body>
<div class="regis_bg">
  <div class="container">
      
    <div class="outsoucing-registaion">
        <div class="company_logo pull-left"><img src="resources/images/mail-logo.png" alt="Sourcelead Logo" class="img-responsive" width="250"></div>
      <div class="clearfix"></div>
      <ul class="nav nav-tabs">
          <li class="active"><a data-toggle="tab" href="#professional" onclick="resetCredentials()"><i class="fa fa-user"></i>&nbsp;&nbsp;Professional</a></li>
          <c:if test="${empty contactUUID}">
          <li><a data-toggle="tab" href="#recruiter" onclick="resetCredentials()"><i class="fa fa-black-tie"></i>&nbsp;&nbsp;Individual Recruiter</a></li>
          </c:if>
      </ul>
        <div class="regis_wrap_styles">
        <div class="tab-content">
        <div id="professional" class="tab-pane fade in active">
            
           <form:form class="login-form clearfix"  id="professionalRegistration_form"  name="professionalRegistration_form" method="post" >     
            <div class="row">
            <div class="form-group clearfix">
                <div class="col-xs-12 col-md-6">
                    <i class="fa fa-user-circle"></i>
                    <label>First Name <span class="text-danger">*</span></label>
                    <input type="text" name="firstName" field-name="First Name" id="firstName" value='${firstName}' data-validation="required validate_Space validate_Alphanumeric validate_length length1-100">
                    <b></b>
                </div>
                <div class="col-xs-12 col-md-6">
                    <i class="fa fa-user-circle"></i>
                    <label>Last Name</label>
                    <input type="text" name="lastName" field-name="Last Name" id="lastName" value='${lastName}'  data-validation-optional="true" data-validation="validate_Space validate_Alphanumeric validate_length length1-100">
                    <b></b>
                </div>
                </div>
                <div class="form-group clearfix">
                <div class="col-xs-12 col-md-6" id="emailDiv">
                    <i class="fa fa-envelope"></i>
                    <label>Email <span class="text-danger">*</span></label>
                    <input type="text" name="email" field-name="Email" value='${email}' onblur="validateEmail('Professional');" id="email"  data-validation="required validate_Space validate_length length2-100 validate_email" >
                    <b></b>
                </div>
                <div class="col-xs-12 col-md-6 select-field">
                    <i class="fa fa-map-marker"></i>
                    <select class="form-control chosen-select" name="location" id="location" field-name="Location" data-validation="required">
                        <option disabled selected>Location</option>
                        <option value="Australia">Australia</option>
		                <option value="France">France</option>
		                <option value="India">India</option>
		                <option value="Singapore">Singapore</option>
		                <option value="United Kingdom">United Kingdom</option>
		                <option value="United States">United States</option>
                    </select>
                    <b></b>
                </div>
                </div>
                
            </div>
            
            <div class="clearfix mt25">
                <div class="pull-left">
                    <span class="small text-muted">Already have an Account:</span><a href="${sourceleadUrl}" class="btn" title="Login">Login</a>
                </div>
                <div class="pull-right">
                    <button type="submit" id="register_button"  class="btn btn-primary btn_styles mr" title="Register">
                        <i class="fa fa-paper-plane"></i>Register</button>
                </div>
            </div>
            </form:form>
        </div>
        <div id="recruiter" class="tab-pane fade">
           <form:form class="login-form clearfix"  id="recruiterRegistration_form"  name="recruiterRegistration_form" method="post" >   
            <div class="row">
            <div class="form-group clearfix">
                <div class="col-xs-12 col-md-6">
                    <i class="fa fa-user-circle"></i>
                    <label>First Name <span class="text-danger">*</span></label>
                    <input type="text" name="firstName" field-name="First Name" id="recruiterFirstName" data-validation="required validate_Space validate_Alphanumeric validate_length length1-100">
                    <b></b>
                </div>
                <div class="col-xs-12 col-md-6">
                    <i class="fa fa-user-circle"></i>
                    <label>Last Name</label>
                    <input type="text" name="lastName" field-name="Last Name" id="recruiterLastName" data-validation-optional="true" data-validation="validate_Space validate_Alphanumeric validate_length length1-100">
                    <b></b>
                </div>
                </div>
                <div class="form-group clearfix">
                <div class="col-xs-12 col-md-6" id="recruiterEmailDiv">
                    <i class="fa fa-envelope"></i>
                    <label>Email <span class="text-danger">*</span></label>
                    <input type="text" name="email" field-name="Email"  id="recruiterEmail"  onblur="validateRecruiterEmail('Recruiter');" data-validation="required validate_Space validate_length length2-100 validate_email" >
                    <b></b>
                </div>
                <div class="col-xs-12 col-md-6 select-field">
                    <i class="fa fa-map-marker"></i>
                    <select class="form-control chosen-select" name="location" id="recruiterLocation" field-name="Location" data-validation="required">
                        <option disabled selected>Location</option>
                        <option value="Australia">Australia</option>
		                <option value="France">France</option>
		                <option value="India">India</option>
		                <option value="Singapore">Singapore</option>
		                <option value="United Kingdom">United Kingdom</option>
		                <option value="United States">United States</option>
                    </select>
                    <b></b>
                </div>
                </div>
            </div>
            
            <div class="clearfix mt25">
                                <div class="pull-left">
                    <span class="small text-muted">Already have an Account:</span><a href="${sourceleadUrl}" class="btn" title="Login">Login</a>
                </div>

                <div class="pull-right">
                    <button type="submit" class="btn btn-primary btn_styles mr" id="recruiter_register" title="Register">
                        <i class="fa fa-paper-plane"></i>Register</button>
                </div>
            </div>
      </form:form>
        </div>
      </div>
        </div>
      </div>
  </div>
</div>
<script src="resources/js/jquery-1.11.3.min.js"></script> 
<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/chosen.jquery.min.js"></script> 
<script src="resources/js/formvalidator.js"></script>
</body>

<script>
function resetCredentials(){
	$("#firstName").val('');
	$("#lastName").val('');
	$("#email").val('');
	$("#location").val('');
	
	$("#recruiterFirstName").val('');
	$("#recruiterLastName").val('');
	$("#recruiterEmail").val('');
	$("#recruiterLocation").val('');
}
</script>

<script type="text/javascript">
function validateEmail(type){
	
    var email = document.getElementById("email").value;
    var sourceleadUrl ='${sourceleadUrl}';
		if(email != null && email != ''){
			$("#register_button").attr("disabled", "disabled");
		$.ajax({
		   	type: "GET",
		       url: sourceleadUrl+"/validateCrowdSourcingEmail?${_csrf.parameterName}=${_csrf.token}&email="+email+"&type="+type,
		       success: function(data) {
		    		if(data != null && data != ''){
					     if(data == 'Invalid'){
					    	    $('#emailDiv').find('p.jquery_form_error_message').remove(); 
					    	    $("input#email").after("<p class='jquery_form_error_message'>"+email+" is Already Existed</span>");
			    				$('input#email').attr('record-exist','yes');
			    				$('input#email').attr('record-exist-errorMsg',email+' is Invalid'); 
					     } 
					     
				       	else{
				       		 $("#register_button").removeAttr("disabled");
							 $('input#email').removeAttr( "record-exist record-exist-errormsg");
			        		 $('#emailDiv').find('p.jquery_form_error_message').remove(); 
							 $( "#register_button" ).removeAttr("style");
							 $( "#register_button" ).click(function() {
							 if ($(this).validate(false, validationSettings)){
							 }
							});
				       	}
			       	}
		    	   
		      },
		       
		}); 
	   }
}
</script>


<script type="text/javascript">
function validateRecruiterEmail(type){
	
    var email = document.getElementById("recruiterEmail").value;
    var sourceleadUrl ='${sourceleadUrl}';
		if(email != null && email != ''){
			$("#recruiter_register").attr("disabled", "disabled");
		$.ajax({
		   	type: "GET",
		       url: sourceleadUrl+"/validateCrowdSourcingEmail?${_csrf.parameterName}=${_csrf.token}&email="+email+"&type="+type,
		       success: function(data) {
		    		if(data != null && data != ''){
					     if(data == 'Invalid'){
					    	    $('#recruiterEmailDiv').find('p.jquery_form_error_message').remove(); 
					    	    $("input#recruiterEmail").after("<p class='jquery_form_error_message'>"+email+" is Already Existed</span>");
			    				$('input#recruiterEmail').attr('record-exist','yes');
			    				$('input#recruiterEmail').attr('record-exist-errorMsg',email+' is Invalid'); 
					     } 
					     
				       	else{
				       		 $("#recruiter_register").removeAttr("disabled");
							 $('input#recruiterEmail').removeAttr( "record-exist record-exist-errormsg");
			        		 $('#emailDiv').find('p.jquery_form_error_message').remove(); 
							 $( "#recruiter_register" ).removeAttr("style");
							 $( "#recruiter_register" ).click(function() {
							 if ($(this).validate(false, validationSettings)){
							 }
							});
				       	}
			       	}
		    	   
		      },
		       
		}); 
	   }
}
</script>



<script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#professionalRegistration_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		e.preventDefault();
            	var frm = $('#professionalRegistration_form').serializeFormJSON();
			    
			    var con = JSON.stringify(frm);
		        con = con.replace(/[{}]/g, "");
		        
		        var jsonData = encodeURIComponent(con);
			    
         		var sourceleadUrl ='${sourceleadUrl}';
         		var contactUUID = '${contactUUID}';
         		
         	 	$("#register_button").attr("disabled", "disabled");
         		
           	 
         		$.ajax({
          	      type: "POST",
          	      contentType : 'application/json; charset=utf-8',
          	      dataType : 'json',
                	  url: sourceleadUrl+"/registerAsProfessional?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&contactUUID="+contactUUID,		  
                  	success: function(result) {
			        	if((result == 'success')){
			        		location.replace(sourceleadUrl+"/resetloginCredentials");  
			        	}
			        	
			        },  
              }); 
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
         $('#professionalRegistration_form').showHelpOnFocus();
            
     
	</script>
	
	
	
	<script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#recruiterRegistration_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		e.preventDefault();
            	var frm = $('#recruiterRegistration_form').serializeFormJSON();
			    
			    var con = JSON.stringify(frm);
		        con = con.replace(/[{}]/g, "");
		        
		        var jsonData = encodeURIComponent(con);
		        
			    
         		var sourceleadUrl ='${sourceleadUrl}';
         	 	$("#recruiter_register").attr("disabled", "disabled");
         		
           	 
         		$.ajax({
          	      type: "POST",
          	      contentType : 'application/json; charset=utf-8',
          	      dataType : 'json',
                	  url: sourceleadUrl+"/registerAsIndividualRecruiter?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
                			  
                  	success: function(result) {
			        	if((result == 'success')){
			        		location.replace(sourceleadUrl+"/resetloginCredentials");  
			        	}
			        	
			        },  
              }); 
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
         $('#recruiterRegistration_form').showHelpOnFocus();
            
     
	</script>

<script>
$(function () {
     applyInputSlide();
});

function applyInputSlide() {
    $(".regis_wrap_styles div input").each(function (i) {
        if ($(this).val().trim() !== "")
            $(this).parent(".regis_wrap_styles div").find('label').addClass('test');
    });
    $(".regis_wrap_styles div input").on('focus', function () {
        $(this).parent(".regis_wrap_styles div").find('label').addClass('test');
    });
    $(".regis_wrap_styles div input").on('blur', function () {
        if ($(this).val().trim() === "")
            $(this).parent(".regis_wrap_styles div").find('label').removeClass('test');
    });	    
}
</script>
    
    
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
    $('#priority').trigger('chosen:updated');
</script>

<script>
    // Close Advanced Search
    function closePop(){
    $("div.cand-advsrch-popup").hide();
    };
    function openPop(){
    $("div.cand-advsrch-popup").toggle();
    };
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
<!--    Ends of validating the Company Email i,e removing duplicate values-->
</html>