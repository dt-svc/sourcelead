
<!DOCTYPE html>

<html>
<head>
     <meta charset="utf-8">
     <meta http-equiv="X-UA-Compatible" content="IE=edge">
     <meta name="viewport" content="width=device-width, initial-scale=1">
    
     <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
     <%@page session="true"%>
    
    <c:if test="${staticTime == null || staticTime == 0 }">
       <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
     </c:if>

    
    <title>::. Registration .::</title>
    <link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,300italic,300,400italic,500,700,900" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="resources/css/chosen.min.css?staticTime=${staticTime}">
    <link href="resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet">
    <link href="resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
    <link href="resources/css/register-style.css?staticTime=${staticTime}" rel="stylesheet">
     <link href="resources/css/crowdsourcing-main.css?staticTime=${staticTime}" rel="stylesheet" type="text/css">
    
</head>
<script>
 function getProfileDetails(response,service){
		var activeElement = $('.tab-pane.fade.in.active').attr('id');
		if(activeElement != null && activeElement != ''  && activeElement != 'undefined' &&  activeElement == 'recruiter'){
			resetCredentials('Recruiter')
			$("#recruiterRegistrationPopup").removeAttr();
			$("#professionalRegistrationPopup").removeAttr();
			$("#recruiterRegistrationPopup").attr("style","display:block");
			$("#professionalRegistrationPopup").attr("style","display:none");
			
			var details = response.data.details;
			$("#popRecruiterprofileimage").attr("src",details.photo);
			$("#popRecruiterservice").val(service);
			$("#popRecruiterprofileUrl").val(details.profileUrl);
			/* $("#aboutme").html(details.aboutMe); */
			//$("#profileurl").html(details.profileUrl).attr("href",details.profileUrl);
			//$("#loginid").val(details.id);
			/* $("#logindisplayname").val(details.displayName); */
			$("#popRecruiterimageUrl").val(details.photo);
			$("#poprecruiterFirstName").val(details.firstName);
			$("#poprecruiterLastName").val(details.lastName);
			$("#poprecruiterEmail").val(details.email);
			$("#poprecruiterUserName").val(details.email);
			/* $("#logingender").val(details.gender);
			$("#logindob").val(details.dob); */
			$("#loginlocation").val(details.location);
			
		}
		else{
			resetCredentials('Professional');
			$("#recruiterRegistrationPopup").removeAttr();
			$("#professionalRegistrationPopup").removeAttr();
			$("#recruiterRegistrationPopup").attr("style","display:none");
			$("#professionalRegistrationPopup").attr("style","display:block");
			

			var details = response.data.details;
			$("#popProfessionalprofileimage").attr("src",details.photo);
			/* $("#aboutme").html(details.aboutMe); */
			//$("#profileurl").html(details.profileUrl).attr("href",details.profileUrl);
			//$("#loginid").val(details.id);
			/* $("#logindisplayname").val(details.displayName); */
			$("#popProfessionalimageUrl").val(details.photo);
			$("#popProfessionalService").val(service);
			$("#popProfessionalprofileurl").val(details.profileUrl);
			$("#popfirstName").val(details.firstName);
			$("#poplastName").val(details.lastName);
			$("#popemail").val(details.email);
			$("#popUserName").val(details.email);
			/* $("#logingender").val(details.gender);
			$("#logindob").val(details.dob); */
			$("#loginlocation").val(details.location);
			
		}
		$(".logMask").fadeIn(200);
		$(".logPop").fadeIn(100);
 }
 </script>
<script>
window.onload = function () {
	    socialInviterScripts();
		var contactUUID = '${contactUUID}';
		if(contactUUID != null && contactUUID != '' && contactUUID != 'undefined'){
			validateEmail('Professional');
		}else{
			resetCredentials('Professional');
		}
	};
</script>

<script>
    function closeProfileDetailsPop(){
    	$(".logMask").fadeOut(200);
		$(".logPop").fadeOut(100);
		$(".terms_conditions").fadeOut(100);
    }
    
    function getFormData(formId) {
    	var $form = $("#"+formId);
    	var unindexed_array = $($form).serializeArray();
    	var indexed_array = {};
    	$.map(unindexed_array, function (n, i) {
    	indexed_array[n['name']] = n['value'];
    	});
    	return indexed_array;
    }
</script>
 <div  id="loadTime"></div> 
<div class="logMask"></div>
	<div class="logPop">
		<div id="registerPopClose" class="loPopClose" onclick="closeProfileDetailsPop();"><i class="fa fa-times"></i></div>
		<div id="registerPop" class="row mt10">
		    <div id = "professionalRegistrationPopup" style="display:block;">
				    <form:form class="login-form clearfix"  id="popprofessionalRegistration_form"  name="popprofessionalRegistration_form" method="post" >
				    <div class="col-md-3">
						<div class="userImg">
					      <img src="http://socialinviter.com/assets/img/icons/avator.JPG" id="popProfessionalprofileimage">
					   </div>
				    </div>
		            <div class="col-md-9">
		             <div class="form-group clearfix">
		                  <label  class="col-md-4">User Name <span class="text-danger">*</span></label>
		                  <span class="col-md-8">
		                     <input type="hidden" name="imageUrl" id="popProfessionalimageUrl"/>
		                     <input type="hidden" name="profileUrl" id="popProfessionalprofileurl"/>
		                     <input type="hidden" name="service" id="popProfessionalService"/>
		                     <input type="text" class="form-control" name="userName" field-name="User Name" id="popUserName" readonly="readonly" data-validation="required">
		                     <b></b>
		                  </span>
		            </div>
		            <div class="form-group clearfix" id="firstNameDivPopUp">
		                  <label  class="col-md-4">First Name <span class="text-danger">*</span></label>
		                  <span class="col-md-8">
		                    <input type="text" class="form-control" name="firstName" field-name="First Name" id="popfirstName" value='${firstName}' data-validation="required validate_Space validate_Alphanumeric validate_length length1-100" onkeydown="removeValidationNamePop();">
		                     <b></b>
		                  </span>
		            </div>
		            <div class="form-group clearfix">
		              <label class="col-md-4">Last Name</label>
		              <span class="col-md-8">
		                 <input type="text"  class="form-control" name="lastName" field-name="Last Name" id="poplastName" value='${lastName}'  data-validation-optional="true" data-validation="validate_Space validate_Alphanumeric validate_length length1-100">
		                 <b></b>
		              </span>
		            </div>
		            <div class="form-group clearfix">
		              <label class="col-md-4">Email<span class="text-danger">&nbsp;*</span></label>
		              <span class="col-md-8" id="popemailDiv">
		                 <input type="text" class="form-control" name="email" field-name="Email" onblur="validatepopEmail('Professional');" value='${email}' id="popemail"  data-validation="required validate_Space validate_length length2-100 validate_email" readonly="readonly">
		                  <b></b>
		              </span>
		            </div>
		            <div class="form-group clearfix" id="poplocationDiv">
		              <label class="col-md-4">Location<span class="text-danger">&nbsp;*</span></label>
		              <div class="col-md-8">
		                  <select class="form-control chosen-select" name="location" id="poplocation" field-name="Country" data-validation="required" onchange="removeValidationRecPop();">
		                       <option disabled selected>Country</option>
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
		            <div class="col-md-12 clearfix">
		            <div class="form-group  empCheckbox">
                        <div class="row">
                            
                            <div class="clearfix">   
                            <p class="ml mt10"><strong>Would like to be</strong></p>
						    <div class="clearfix">
					         <c:forEach var="WouldLikeToList" items="${WouldLikeToList}">
				      				<span class="col-md-4">
				                            <input type="checkbox" value="${WouldLikeToList.id}" name="Wouldliketo"><i>${WouldLikeToList.registerName}</i>
				                    </span>
						     </c:forEach>
				          </div>

                        </div>
                        </div>
	            </div>
		            
		            <div class="form-group clearfix">
		                <div class="pull-right">
		                     <button type="submit" id="popregister_button"  class="btn btn-primary btn_styles mr" title="Register">
		                        <i class="fa fa-paper-plane"></i>Register</button>
		                </div>
		            </div>                                                                                                          
		        </div>
		        </form:form>
        </div>
        <div id = "recruiterRegistrationPopup" style="display:none;">
		      <form:form class="login-form clearfix"  id="poprecruiterRegistration_form"  name="poprecruiterRegistration_form" method="post" >
				     <div class="col-md-3">
						<div class="userImg">
					      <img src="http://socialinviter.com/assets/img/icons/avator.JPG" id="popRecruiterprofileimage">
					   </div>
				    </div>   
		            <div class="col-md-9">
		             <div class="form-group clearfix">
		                  <label  class="col-md-4">User Name <span class="text-danger">*</span></label>
		                  <span class="col-md-8">
		                     <input type="hidden" name="imageUrl" id="popRecruiterimageUrl"/>
		                     <input type="hidden" name="profileUrl" id="popRecruiterprofileUrl"/>
		                     <input type="hidden" name="service" id="popRecruiterservice"/>
		                     <input type="text" class="form-control" name="userName" field-name="User Name" id="poprecruiterUserName" readonly="readonly" data-validation="required">
		                     <b></b>
		                  </span>
		            </div>
		            <div class="form-group clearfix" id="poprecruiterFirstNameDiv">
		                  <label  class="col-md-4">First Name <span class="text-danger">*</span></label>
		                  <span class="col-md-8">
		                     <input type="text" class="form-control" name="firstName" field-name="First Name" id="poprecruiterFirstName" data-validation="required validate_Space validate_Alphanumeric validate_length length1-100" onkeydown="removeValidationNameRecPop();">
		                     <b></b>
		                  </span>
		            </div>
		            <div class="form-group clearfix">
		              <label class="col-md-4">Last Name</label>
		              <span class="col-md-8">
		                 <input type="text"  class="form-control" name="lastName" field-name="Last Name" id="poprecruiterLastName" value='${lastName}'  data-validation-optional="true" data-validation="validate_Space validate_Alphanumeric validate_length length1-100">
		                 <b></b>
		              </span>
		            </div>
		            <div class="form-group clearfix">
		              <label class="col-md-4">Email<span class="text-danger">&nbsp;*</span></label>
		              <span class="col-md-8" id="poprecruiterEmailDiv">
		                 <input type="text" class="form-control" name="email" field-name="Email"  id="poprecruiterEmail" readonly="readonly" onblur="validatepopRecruiterEmail('Recruiter');" data-validation="required validate_Space validate_length length2-100 validate_email" >
		                  <b></b>
		              </span>
		            </div>
		            <div class="form-group clearfix" id="poprecruiterLocationDiv">
		              <label class="col-md-4">Location<span class="text-danger">&nbsp;*</span></label>
		              <div class="col-md-8">
		                  <select class="form-control chosen-select" name="location" id="poprecruiterLocation" field-name="Country" data-validation="required" onchange="removeCountryValidationRecPop();">
		                       <option disabled selected>Country</option>
		                       <option value="Australia">Australia</option>
			                <option value="France">France</option>
			                <option value="India">India</option>
			                <option value="Singapore">Singapore</option>
			                <option value="United Kingdom">United Kingdom</option>
			                <option value="United States">United States</option>
		                   </select>
		                   <b></b>
		              </div>
		            </div> </div>
		            <div class="col-md-12 clearfix">
		            <div class="form-group  empCheckbox">
                        <div class="row">
                            <div class="clearfix">   
                            <p class="ml mt10"><strong>Would like to</strong></p>
						  <div class="clearfix">
					         <c:forEach var="WouldLikeToList" items="${WouldLikeToList}">
				      				<span class="col-md-4">
				                            <input type="checkbox" value="${WouldLikeToList.id}" name="Wouldliketo"><i>${WouldLikeToList.registerName}</i>
				                    </span>
						     </c:forEach>
				          </div>

                        </div>
                        </div>
	            </div>
		            
		            <div class="form-group clearfix">
		                <div class="pull-right">
		                     <button type="submit" id="poprecruiter_register"  class="btn btn-primary btn_styles mr" title="Register">
		                        <i class="fa fa-paper-plane"></i>Register</button>
		                </div>
		            </div>                                                                                                          
		        </div>
		    </form:form>
        </div>
      </div>		
	</div>
<body>
<div class="regis_bg">
  <div class="container">
      
    <div class="outsoucing-registaion registerwith">
        <div class="company_logo pull-left mb10"><img src="resources/images/SourceLead-logo.png" alt="Sourcelead Logo" title="Sourcelead Logo" class="img-responsive" width="300"></div>
      <div class="clearfix"></div>
      <ul class="nav nav-tabs">
          <li class="active"><a data-toggle="tab" href="#professional" onclick="resetCredentials('Professional')"><i class="fa fa-user"></i>&nbsp;&nbsp;Job Seeker</a></li>
          <c:if test="${empty contactUUID}">
          <li><a data-toggle="tab" href="#recruiter" onclick="resetCredentials('Recruiter')"><i class="fa fa-black-tie"></i>&nbsp;&nbsp;<span class="hidden-xs">Freelance</span> Recruiter</a></li>
          <li><a data-toggle="tab" href="#employee"><i class="fa fa-address-book-o"></i>&nbsp;&nbsp;Employee</a></li>
          </c:if>
          <input type="hidden" id="tabType" />
      </ul>
       <!--  <div class="regis_wrap_styles registered_widget_styles"> -->
        <div class="regis_wrap_styles">
        <div class="tab-content">
        <div id="professional" class="tab-pane fade in active">
           <form:form class="login-form clearfix"  id="professionalRegistration_form"  name="professionalRegistration_form" method="post" >     
            <div class="clearfix social_icons">
            <span class="col-md-12 text-muted text-center small">Continue with</span>
            <div id ="proffessionalConnect"></div>
            </div>
            <div class="clearfix"></div>
            <div class=" text-muted small mb10 text-center clearfix">
            -------------------------------------------------- ( OR ) --------------------------------------------------
            </div>
            <div class="row mt10">
            <div class="form-group clearfix">
                <div class="col-xs-12 col-md-12 mb10" id="firstNameDiv">
                    <i class="fa fa-user-circle"></i>
                    <label>First Name <span class="text-danger">*</span></label>
                    <input type="text" name="firstName" field-name="First Name" id="firstName" value='${firstName}' data-validation="required validate_Space validate_Alphanumeric validate_length length1-100" onkeydown="removeValidationNameDiv();">
                    <b></b>
                </div>
                <div class="col-xs-12 col-md-12 mb10" id="lastNameDiv">
                    <i class="fa fa-user-circle"></i>
                    <label>Last Name</label>
                    <input type="text" name="lastName" field-name="Last Name" id="lastName" value='${lastName}'  data-validation-optional="true" data-validation="validate_Space validate_Alphanumeric validate_length length1-100">
                    <b></b>
                </div>
                
                <div class="col-xs-12 col-md-12 mb10" id="emailDiv">
                    <i class="fa fa-envelope"></i>
                    <label>Email <span class="text-danger">*</span></label>
                    <input type="text" name="email" field-name="Email" value='${email}' onblur="validateEmail('Professional');" id="email"  data-validation="required validate_Space validate_length length2-100 validate_email" >
                    <b></b>
                </div>
                <div class="col-xs-12 col-md-12 select-field mb10" id="locationCountryDiv">
                    <i class="fa fa-map-marker"></i>
                    <select class="form-control chosen-select" name="location" id="location" field-name="Country" data-validation="required" onchange="removeValidation();">
                        <option disabled selected>Country</option>
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
            
            <div class="clearfix">
            <div class="clearfix row">
            <div class='col-md-12 privacy_checkbox text-center'>
                    <!-- <input type="checkbox" name="privacy" onClick="termsConditon();" id="terms"> -->
                    <input type="checkbox" name="privacy" id="professionalRegistrationCheckTermsId">
                    I agree terms of <a href="javascript:void(0)" onClick="termsConditon();" class="text-succcess">service and privacy policy</a>
                    <input type="hidden" id="professionalRegistrationErrorMsg" field-name="Terms And Conditions" data-validation='required'/>
            </div>
            <div class="col-md-12 mt10">
                    <button type="submit" id="register_button"  class="btn btn-primary btn_styles btn-block" title="Register">
                        <span class="text-center"><i class="fa fa-paper-plane"></i>Register</span></button>
            </div>
            </div>
                <div class="col-md-12 text-center mt bg-info">
                    <span class="text-muted">Already have an Account:</span><a href="${sourceleadUrl}" class="btn" title="Login">Login</a><br/>
                </div>
                
            </div>
            </form:form>
        </div>
        <div id="recruiter" class="tab-pane fade">
        
        <form:form class="login-form clearfix"  id="recruiterRegistration_form"  name="recruiterRegistration_form" method="post" >     
            <div class="clearfix social_icons">
            <span class="col-md-12 text-muted text-center small">Continue with</span>
            <div id ="recruiterConnect"></div>
            </div>
            <div class="clearfix"></div>
            <div class=" text-muted small mb10 text-center clearfix">
            -------------------------------------------------- ( OR ) --------------------------------------------------
            </div>
            <div class="row mt10">
            <div class="form-group clearfix">
                <div class="col-xs-12 col-md-12 mb10" id="firstNameDivRec">
                    <i class="fa fa-user-circle"></i>
                    <label>First Name <span class="text-danger">*</span></label>
                    <input type="text" name="firstName" field-name="First Name" id="recruiterFirstName" data-validation="required validate_Space validate_Alphanumeric validate_length length1-100" onkeydown="removeValidationNameDivRec();">
                    <b></b>
                </div>
                <div class="col-xs-12 col-md-12 mb10">
                    <i class="fa fa-user-circle"></i>
                    <label>Last Name</label>
                    <input type="text" name="lastName" field-name="Last Name" id="recruiterLastName" data-validation-optional="true" data-validation="validate_Space validate_Alphanumeric validate_length length1-100">
                    <b></b>
                </div>
                
                <div class="col-xs-12 col-md-12 mb10" id="recruiterEmailDiv">
                    <i class="fa fa-envelope"></i>
                    <label>Email <span class="text-danger">*</span></label>
                    <input type="text" name="email" field-name="Email"  id="recruiterEmail"  onblur="validateRecruiterEmail('Recruiter');" data-validation="required validate_Space validate_length length2-100 validate_email" >
                    <b></b>
                </div>
                <div class="col-xs-12 col-md-12 select-field mb10" id="locationCountryDivRec">
                    <i class="fa fa-map-marker"></i>
                    <select class="form-control chosen-select" name="location" id="location" field-name="Country" data-validation="required" onchange="removeValidationRec();">
                        <option disabled selected>Country</option>
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
            
            <div class="clearfix">
            <div class="clearfix row">
            <div class='col-md-12 privacy_checkbox text-center'>
                    <!-- <input type="checkbox" name="privacy" onClick="termsConditon();" id="terms"> -->
                    <input type="checkbox" name="privacy" id="termsCheckoxId">
                    I agree terms of <a href="javascript:void(0)" onClick="termsConditon();" class="text-succcess">service and privacy policy</a>
                    <input type="hidden" id="recruiterRegistrationErrorMsg" field-name="Terms And Conditions" data-validation='required'/>
            </div>
            <div class="col-md-12 mt10">
                    <button type="submit" id="recruiter_register"  class="btn btn-primary btn_styles btn-block" title="Register">
                        <span class="text-center"><i class="fa fa-paper-plane"></i>Register</span></button>
            </div>
            </div>
                <div class="col-md-12 text-center mt bg-info">
                    <span class="text-muted">Already have an Account:</span><a href="${sourceleadUrl}" class="btn" title="Login">Login</a><br/>
                </div>
                
            </div>
        </form:form>
        
        
          <%-- <form:form class="login-form clearfix"  id="recruiterRegistration_form"  name="recruiterRegistration_form" method="post" >    
	            <div class="row">
	            <div class="form-group clearfix">
	                <div class="col-xs-12 col-md-12">
	                    <i class="fa fa-user-circle"></i>
	                    <label>First Name <span class="text-danger">*</span></label>
	                    <input type="text" name="firstName" field-name="First Name" id="recruiterFirstName" data-validation="required validate_Space validate_Alphanumeric validate_length length1-100">
	                    <b></b>
	                </div>
	                <div class="col-xs-12 col-md-12">
	                    <i class="fa fa-user-circle"></i>
	                    <label>Last Name</label>
	                    <input type="text" name="lastName" field-name="Last Name" id="recruiterLastName" data-validation-optional="true" data-validation="validate_Space validate_Alphanumeric validate_length length1-100">
	                    <b></b>
	                </div>
	                </div>
	                <div class="form-group clearfix">
	                <div class="col-xs-12 col-md-12" id="recruiterEmailDiv">
	                    <i class="fa fa-envelope"></i>
	                    <label>Email <span class="text-danger">*</span></label>
	                    <input type="text" name="email" field-name="Email"  id="recruiterEmail"  onblur="validateRecruiterEmail('Recruiter');" data-validation="required validate_Space validate_length length2-100 validate_email" >
	                    <b></b>
	                </div>
	                <div class="col-xs-12 col-md-12 select-field">
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
	            <div class="clearfix">
	            <div class="col-md-12">
	                 <div class="pull-left">
	                    <span class="small text-muted">Already have an Account:</span><a href="${sourceleadUrl}" class="btn" title="Login">Login</a><br/>
	                     <span class="small text-muted">Register With:</span>
	                </div>
	                  <div id="recruiterConnect"></div>
	                <div class="pull-right mt10">
	                    <button type="submit" class="btn btn-primary btn_styles mr" id="recruiter_register" title="Register">
	                        <i class="fa fa-paper-plane"></i>Register</button>
	                </div>
	            </div>
	             </div>
        </form:form> --%>
        </div>
        <div id="employee" class="tab-pane fade">
        <div class="text-center employee_trail">
        <h4>FREE TRIAL</h4>
        <p>We are so glad for your interest in <strong>SourceLead</strong>. <br> Please reach us on</p>
        <p><a href="mailto:info@sourcelead.com" title="Mail Us"><span class="fa fa-envelope"></span>info@sourcelead.com</a></p>
        <p><a href="tel:040-6700-7676"><span class="fa fa-phone" title="Call Us"></span>IND : +91 40-6700-7676</b></a></p>
        <p>We will get back to you as soon as possible.</p>
        </div>
        </div>
      </div>
        </div>
      </div>
  </div>
</div>
<script src="resources/js/jquery-1.11.3.min.js?staticTime=${staticTime}"></script> 
<script src="resources/js/bootstrap.min.js?staticTime=${staticTime}"></script>
<script src="resources/js/chosen.jquery.min.js?staticTime=${staticTime}"></script> 
<script src="resources/js/formvalidator.js?staticTime=${staticTime}"></script>
</body>

<script>
function removeValidation(){
	$('#locationCountryDiv').find('p.jquery_form_error_message').remove(); 
}
function removeValidationNameDiv(){
	var namediv = document.getElementById("firstName").value;
	if(namediv.length > 0){
		$('#firstNameDiv').find('p.jquery_form_error_message').remove(); 
	}
}
function removeValidationRec(){
	$('#locationCountryDivRec').find('p.jquery_form_error_message').remove(); 
}
function removeValidationNameDivRec(){
	var namediv = document.getElementById("recruiterFirstName").value;
	if(namediv.length > 0){
		$('#firstNameDivRec').find('p.jquery_form_error_message').remove(); 
	}
}
function removeValidationRecPop(){
	$('#poplocationDiv').find('p.jquery_form_error_message').remove(); 
}
function removeValidationNamePop(){
	var namediv = document.getElementById("popfirstName").value;
	if(namediv.length > 0){
		$('#firstNameDivPopUp').find('p.jquery_form_error_message').remove(); 
	}
}
function removeCountryValidationRecPop(){
	$('#poprecruiterLocationDiv').find('p.jquery_form_error_message').remove(); 
}
function removeValidationNameRecPop(){
	var namediv = document.getElementById("poprecruiterFirstName").value;
	if(namediv.length > 0){
		$('#poprecruiterFirstNameDiv').find('p.jquery_form_error_message').remove(); 
	}
}
</script>

<script>
function resetCredentials(type){
	$("#tabType").val(type);
	$("#firstName").val('');
	$("#lastName").val('');
	$("#email").val('');
	$("#location").val('');
	
	$('#emailDiv').find('p.jquery_form_error_message').remove(); 
	$('#recruiterEmailDiv').find('p.jquery_form_error_message').remove(); 
	
	$("#recruiterFirstName").val('');
	$("#recruiterLastName").val('');
	$("#recruiterEmail").val('');
	$("#recruiterLocation").val('');
	
	$("#popfirstName").val('');
	$("#poplastName").val('');
	$("#popemail").val('');
	$("#poplocation").val('');

	$("#poprecruiterFirstName").val('');
	$("#poprecruiterLastName").val('');
	$("#poprecruiterEmail").val('');
	$("#poprecruiterLocation").val('');
	
	
	
	 $("#popregister_button").removeAttr("disabled");
	 $('input#popemail').removeAttr( "record-exist record-exist-errormsg");
	 $('#poprecruiterEmailDiv').find('p.jquery_form_error_message').remove(); 
	 $( "#popregister_button" ).removeAttr("style");
	 
     $("#poprecruiter_register").removeAttr("disabled");
     $('input#poprecruiterEmail').removeAttr( "record-exist record-exist-errormsg");
     $('#popemailDiv').find('p.jquery_form_error_message').remove(); 
     $( "#poprecruiter_register" ).removeAttr("style");
	 
	$("#proffessionalConnect").removeAttr('class');
	$("#recruiterConnect").removeAttr('class');
	$("#proffessionalConnect").removeAttr('type');
	$("#recruiterConnect").removeAttr('type');
	
	if(type != null && type != '' && type != 'undefined' && type == 'Recruiter'){
		$("#recruiterConnect").attr('class','socialinviter holder break');
		$("#recruiterConnect").attr('type','socialconnect');
	}
	else{
		$("#proffessionalConnect").attr('class','socialinviter holder break');
		$("#proffessionalConnect").attr('type','socialconnect');
	}
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
		       url: sourceleadUrl+"/validateCrowdSourcingRegistrationEmail?${_csrf.parameterName}=${_csrf.token}&email="+email+"&type="+type,
		       success: function(data) {
		    		if(data != null && data != ''){
					     if(data == 'Invalid'){
					    	    $('#emailDiv').find('p.jquery_form_error_message').remove(); 
					    	    $("input#email").after("<p class='jquery_form_error_message'>"+email+" is Already Existed</span>");
			    				$('input#email').attr('record-exist','yes');
			    				$('input#email').attr('record-exist-errorMsg',email+' is Invalid'); 
					     } 
					     
					     else if(data == 'Invalid Domain'){
					    	    $('#emailDiv').find('p.jquery_form_error_message').remove(); 
					    	    $("input#email").after("<p class='jquery_form_error_message'>Invalid Domain Name. Domain name should not with org extension</span>");
			    				$('input#email').attr('record-exist','yes');
			    				$('input#email').attr('record-exist-errorMsg','Invalid Domain Name'); 
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
		       url: sourceleadUrl+"/validateCrowdSourcingRegistrationEmail?${_csrf.parameterName}=${_csrf.token}&email="+email+"&type="+type,
		       success: function(data) {
		    		if(data != null && data != ''){
					     if(data == 'Invalid'){
					    	    $('#recruiterEmailDiv').find('p.jquery_form_error_message').remove(); 
					    	    $("input#recruiterEmail").after("<p class='jquery_form_error_message'>"+email+" is Already Existed</span>");
			    				$('input#recruiterEmail').attr('record-exist','yes');
			    				$('input#recruiterEmail').attr('record-exist-errorMsg',email+' is Invalid'); 
					     } 
					     
					     else if(data == 'Invalid Domain'){
					    	    $('#recruiterEmailDiv').find('p.jquery_form_error_message').remove(); 
					    	    $("input#recruiterEmail").after("<p class='jquery_form_error_message'>Invalid Domain Name. Domain name should not with org extension</span>");
			    				$('input#recruiterEmail').attr('record-exist','yes');
			    				$('input#recruiterEmail').attr('record-exist-errorMsg','Invalid Domain Name'); 
					     } 
					     
				       	else{
				       		 $("#recruiter_register").removeAttr("disabled");
							 $('input#recruiterEmail').removeAttr( "record-exist record-exist-errormsg");
			        		 $('#recruiterEmailDiv').find('p.jquery_form_error_message').remove(); 
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
function validatepopEmail(type){
    var email = document.getElementById("popemail").value;
    var sourceleadUrl ='${sourceleadUrl}';
		if(email != null && email != ''){
			$("#popregister_button").attr("disabled", "disabled");
		$.ajax({
		   	type: "GET",
		       url: sourceleadUrl+"/validateCrowdSourcingEmail?${_csrf.parameterName}=${_csrf.token}&email="+email+"&type="+type,
		       success: function(data) {
		    		if(data != null && data != ''){
					     if(data == 'Invalid'){
					    	    $('#popemailDiv').find('p.jquery_form_error_message').remove(); 
					    	    $("input#popemail").after("<p class='jquery_form_error_message'>"+email+" is Already Existed</span>");
			    				$('input#popemail').attr('record-exist','yes');
			    				$('input#popemail').attr('record-exist-errorMsg',email+' is Invalid'); 
					     } 
					     
				       	else{
				       		 $("#popregister_button").removeAttr("disabled");
							 $('input#popemail').removeAttr( "record-exist record-exist-errormsg");
			        		 $('#poprecruiterEmailDiv').find('p.jquery_form_error_message').remove(); 
							 $( "#popregister_button" ).removeAttr("style");
							 $( "#popregister_button" ).click(function() {
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
function validatepopRecruiterEmail(type){
	
    var email = document.getElementById("poprecruiterEmail").value;
    var sourceleadUrl ='${sourceleadUrl}';
		if(email != null && email != ''){
			$("#poprecruiter_register").attr("disabled", "disabled");
		$.ajax({
		   	type: "GET",
		       url: sourceleadUrl+"/validateCrowdSourcingEmail?${_csrf.parameterName}=${_csrf.token}&email="+email+"&type="+type,
		       success: function(data) {
		    		if(data != null && data != ''){
					     if(data == 'Invalid'){
					    	    $('#poprecruiterEmailDiv').find('p.jquery_form_error_message').remove(); 
					    	    $("input#poprecruiterEmail").after("<p class='jquery_form_error_message'>"+email+" is Already Existed</span>");
			    				$('input#poprecruiterEmail').attr('record-exist','yes');
			    				$('input#poprecruiterEmail').attr('record-exist-errorMsg',email+' is Invalid'); 
					     } 
					    
				       	else{
				       		 $("#poprecruiter_register").removeAttr("disabled");
							 $('input#poprecruiterEmail').removeAttr( "record-exist record-exist-errormsg");
			        		 $('#popemailDiv').find('p.jquery_form_error_message').remove(); 
							 $( "#poprecruiter_register" ).removeAttr("style");
							 $( "#poprecruiter_register" ).click(function() {
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
        	  $("#professionalRegistrationErrorMsg").val('');
        	  if($("#professionalRegistrationCheckTermsId").is(':checked')){
        		  $("#professionalRegistrationErrorMsg").val('NO');
        	  }
        	 
        	  if ($(this).validate(false, validationSettings)){
        		e.preventDefault();
            	/* var frm = $('#professionalRegistration_form').serializeFormJSON();
			    
			    var con = JSON.stringify(frm);
		        con = con.replace(/[{}]/g, ""); 
		        
		        var jsonData = encodeURIComponent(con);*/
		        
		        var formData = getFormData("professionalRegistration_form");
			    
         		var sourceleadUrl ='${sourceleadUrl}';
         		var contactUUID = '${contactUUID}';
         		var requirementUUID = '${requirementUUID}';
         		var referUUID = '${referUUID}';
         		
         		var wantedToBeIds = [];
	       		$("input[name='wantedtobeList']:checked").each( function (i) {
	       			wantedToBeIds[i] = $(this).val();
	       		});
	       		var wouldliketoIds =[]; 
	       		$("input[name='Wouldliketo']:checked").each( function (i) {
	       			wouldliketoIds[i] = $(this).val();
	       		});
         		
         	 	$("#register_button").attr("disabled", "disabled");
           	 
         		$.ajax({
          	      type: "POST",
          	      data: formData,
                	  url: sourceleadUrl+"/registerAsProfessional?${_csrf.parameterName}=${_csrf.token}&contactUUID="+contactUUID,		  
                  	success: function(result) {
			        	if((result == 'success')){
			        		if(requirementUUID != null && requirementUUID != '' && requirementUUID != 'undefined' &&
			        				referUUID != null && referUUID != '' && referUUID != 'undefined'){
			        			location.replace(sourceleadUrl+"/resetloginCredentials?requirementUUID="+requirementUUID+"&referUUID="+referUUID);  
			        		}else{
			        			location.replace(sourceleadUrl+"/resetloginCredentials");  
			        		}
			        		
			        	}else if((result == 'login')){
			        		if(requirementUUID != null && requirementUUID != '' && requirementUUID != 'undefined' &&
			        				referUUID != null && referUUID != '' && referUUID != 'undefined'){
			        			location.replace(sourceleadUrl+"/login?requirementUUID="+requirementUUID+"&referUUID="+referUUID);  
			        		}else{
			        			location.replace(sourceleadUrl+"/login");  
			        		}
			        		
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
        	 $("#recruiterRegistrationErrorMsg").val('');
       	  	if($("#termsCheckoxId").is(':checked')){
       			  $("#recruiterRegistrationErrorMsg").val('NO');
       	  		}
        	  if ($(this).validate(false, validationSettings)){
        		e.preventDefault();
		        
		        var formData = getFormData("recruiterRegistration_form");
		        var requirementUUID = '${requirementUUID}';
         		var sourceleadUrl ='${sourceleadUrl}';
         	 	$("#recruiter_register").attr("disabled", "disabled");
         	 	
         	 	var wantedToBeIds = [];
	       		$("input[name='wantedtobeList']:checked").each( function (i) {
	       			wantedToBeIds[i] = $(this).val();
	       		});
	       		var wouldliketoIds =[]; 
	       		$("input[name='Wouldliketo']:checked").each( function (i) {
	       			wouldliketoIds[i] = $(this).val();
	       		});
           	 
         	 	$.ajax({
          	      type: "POST",
          	      data: formData,
                	  url: sourceleadUrl+"/registerAsIndividualRecruiter?${_csrf.parameterName}=${_csrf.token}",
                			  
                  	success: function(result) {
			        	if((result == 'success')){
			        		if(requirementUUID != null && requirementUUID != '' && requirementUUID != 'undefined'){
			        			location.replace(sourceleadUrl+"/resetloginCredentials?requirementUUID="+requirementUUID);  
			        		}else{
			        			location.replace(sourceleadUrl+"/resetloginCredentials");  
			        		}
			        	}else if((result == 'login')){
			        		if(requirementUUID != null && requirementUUID != '' && requirementUUID != 'undefined'){
			        			location.replace(sourceleadUrl+"/login?requirementUUID="+requirementUUID);  
			        		}else{
			        			location.replace(sourceleadUrl+"/login");  
			        		}
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
	
	<script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#popprofessionalRegistration_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		e.preventDefault();
        		$(".logPop").fadeOut(100);
        	    $("#loadTime").attr('style','position:fixed; width:650px; height:350px; background-color:rgba(255,255,255,0.8); top:20%; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px; background-color: #fff; z-index: 10000; margin: 0px auto; display: block; right: 0px; border-radius: 5px; box-shadow:0px 0px 5px rgba(0,0,0,0.5);');
        	    
            	var frm = $('#popprofessionalRegistration_form').serializeFormJSON();
			    
			    var con = JSON.stringify(frm);
		        con = con.replace(/[{}]/g, "");
		        
		        var jsonData = encodeURIComponent(con);
		        
         		var sourceleadUrl ='${sourceleadUrl}';
         		
         		var wantedToBeIds = [];
	       		$("input[name='wantedtobeList']:checked").each( function (i) {
	       			wantedToBeIds[i] = $(this).val();
	       		});
	       		var wouldliketoIds =[]; 
	       		$("input[name='Wouldliketo']:checked").each( function (i) {
	       			wouldliketoIds[i] = $(this).val();
	       		});
         		
         	 	$("#popregister_button").attr("disabled", "disabled"); 
         		 $.ajax({
         			  type: "POST",
              	      contentType : 'application/json; charset=utf-8',
              	      dataType : 'json',
                	  url: sourceleadUrl+"/registerAsProfessionalFromSocialInviter?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,		  
                  	success: function(result) {
			        	if((result == 'success')){
			        		$("#loadTime").removeAttr('style');
			        		location.replace(sourceleadUrl+"/dashboard");  
			        	}
			        	
			        },  
              }); 
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
         $('#popprofessionalRegistration_form').showHelpOnFocus();
            
     
	</script>
	
	
	
	<script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#poprecruiterRegistration_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		e.preventDefault();
        		$(".logPop").fadeOut(100);
        	    $("#loadTime").attr('style','position:fixed; width:650px; height:350px; background-color:rgba(255,255,255,0.8); top:20%; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px; background-color: #fff; z-index: 10000; margin: 0px auto; display: block; right: 0px; border-radius: 5px; box-shadow:0px 0px 5px rgba(0,0,0,0.5);');
        	    
            	var frm = $('#poprecruiterRegistration_form').serializeFormJSON();
			    
			    var con = JSON.stringify(frm);
		        con = con.replace(/[{}]/g, "");
		        
		        var jsonData = encodeURIComponent(con);
			    
         		var sourceleadUrl ='${sourceleadUrl}';
         	 	$("#poprecruiter_register").attr("disabled", "disabled");
         		
         	 	var wantedToBeIds = [];
	       		$("input[name='wantedtobeList']:checked").each( function (i) {
	       			wantedToBeIds[i] = $(this).val();
	       		});
	       		var wouldliketoIds =[]; 
	       		$("input[name='Wouldliketo']:checked").each( function (i) {
	       			wouldliketoIds[i] = $(this).val();
	       		});
           	 
         		$.ajax({
         			  type: "POST",
              	      contentType : 'application/json; charset=utf-8',
              	      dataType : 'json',
                	url: sourceleadUrl+"/registerAsIndividualRecruiterFromSocialInviter?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
                  	success: function(result) {
			        	if((result == 'success')){
			        		$("#loadTime").removeAttr('style');
			        		location.replace(sourceleadUrl+"/dashboard");  
			        	}
			        	
			        },  
              }); 
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
         $('#poprecruiterRegistration_form').showHelpOnFocus();
            
     
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

<div class="logMask"></div>
<div class="terms_conditions">
<div class="loPopClose" onclick="closeProfileDetailsPop();"><i class="fa fa-times"></i></div>
	<h4>Terms and Conditons</h4>
	<div class="termscondtion-content" id="includedContent">
	<jsp:include page="privacyPolicyDocument.jsp" />
	</div>
	<div class="stickyFooter row">
	<div class="pull-right">
	<button type="button" class="btn btn-danger btn-sm mr" id="rejectTermsId"  value="reject" onclick="closeProfileDetailsPop();rejectTermsCheckBox();"><i class="fa fa-close mr"></i>Reject</button>
	<button type="button" class="btn btn-success btn-sm ml" id="acceptTermsId" value="accept" onclick="acceptCheckBox();"><i class="fa fa-check mr"></i>Accept</button>
	</div>
	</div>
</div>

<script>
function acceptCheckBox(){
var tabType = document.getElementById("tabType").value;
var acceptTerms = document.getElementById("acceptTermsId").value;
if(tabType == "Recruiter"){
	if(acceptTerms == "accept"){
		$("#termsCheckoxId").prop('checked', true);
		}
}
if(tabType == "Professional"){
	if(acceptTerms == "accept"){
		$("#professionalRegistrationCheckTermsId").prop('checked', true);
	}
  }
closeProfileDetailsPop();
}
</script>

<script type="text/javascript">
function rejectTermsCheckBox(){
	var tabType = document.getElementById("tabType").value;
	var rejectTerms = document.getElementById("rejectTermsId").value;
	if(tabType == "Recruiter"){
		if(rejectTerms == "reject"){
			$("#termsCheckoxId").prop('checked', false);
		}
	}
	if(tabType == "Professional"){
		if(rejectTerms == "reject"){
			$("#professionalRegistrationCheckTermsId").prop('checked', false);
		}
	}
	closeProfileDetailsPop();
}
</script>

<script>
function termsConditon() {
	$(".logMask").fadeIn(100);
	$(".terms_conditions").fadeIn(200);
};
/* $('#terms').change(function() {
    var $check = $(this);
    if ($check.prop('checked')) {
    	$(".logMask").fadeIn(100);
    	$(".terms_conditions").fadeIn(200);
    } else {
    	$(".logMask").hide();
    	$(".terms_conditions").hide();
    }
}); */

// load div
/* $(function() {
var sourceleadUrl ='${sourceleadUrl}';
$("#includedContent").load("~/views/privacyPolicyDocument.jsp"); 
});  */
</script>


<jsp:include page="socialinviterscripts.jsp" />
<!--    Ends of validating the Company Email i,e removing duplicate values-->
</html>