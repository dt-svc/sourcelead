<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>::Org Settings</title>
</head>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<meta charset="utf-8">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

 
 <script src="resources/js/formvalidator.js?staticTime=${staticTime}"></script>
    <script type="text/javascript">
		window.onload = function () {
			initLocationAuto('userLocation','','','','','','');
		 };
	</script>
<script>
	function predefineProfileImage(imageId){
		$(imageId).attr('src','resources/images/userprofile-img.png');
	}
</script>

<script>
	$(document).ready(function() {
		document.getElementById("profileSettings_form").reset();
		document.getElementById("passwordSettings_form").reset();
	});
</script>

<script>
function resetPassword(){
	
	 $('input#currentPassword').val('');
	 $('input#password').val('');
	 $('input#confirmPassword').val('');
	
    $('#currentPwdDiv').find('p.jquery_form_error_message').remove();
    $('#newPwdDiv').find('p.jquery_form_error_message').remove();
    $('#confirmPwdDiv').find('p.jquery_form_error_message').remove();
   
    $("#save_button").removeAttr("disabled");
}

function resetProfile(){
	document.getElementById("profileSettings_form").reset();
	
	$('#firstNameDiv').find('p.jquery_form_error_message').remove();
	$('#lastNameDiv').find('p.jquery_form_error_message').remove();
	$('#designationDiv').find('p.jquery_form_error_message').remove();
	$('#phoneDiv').find('p.jquery_form_error_message').remove();
	$('#loactionDiv').find('p.jquery_form_error_message').remove();
	
	$('#linkedInDiv').find('p.jquery_form_error_message').remove();
	$('#twitterDiv').find('p.jquery_form_error_message').remove();
	$('#fbDiv').find('p.jquery_form_error_message').remove();
	$('#gplusDiv').find('p.jquery_form_error_message').remove();
	
}
</script>

<body>
  <!-- Content Area -->
  <!-- Tab for Profile setting-area starts -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Profile Settings</h2>
      </div>
    
     <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
       <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Account Settings</div>
     </c:if>
   
      <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL'}">
      <div class="pull-right brud-crum"><a href="<c:url value='/professionalDashboard'/>">Home </a> &raquo; Account Settings  </div>
      </c:if>
      
      <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
      <div class="pull-right brud-crum"><a href="<c:url value='/recruiterDashboard'/>">Home </a> &raquo; Account Settings  </div>
      </c:if>
    
    </section>
    <div class="content-area clearfix">
      <div class="clearfix form-area form-horizontal toggle-div remove-div mt">
        <div id="loading"></div> 
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		    <!-- Tab for Profile setting-area -->
		  <div class="accountsettings-tab">
		  <ul class="nav nav-tabs">
			<li class="active" onclick="resetPassword();"><a data-toggle="tab" href="#profile">Profile</a></li>
			<li onclick="resetProfile();"><a data-toggle="tab" href="#password">Password</a></li>
		  </ul>

		  <div class="tab-content panel-body">
			<div id="profile" class="tab-pane fade in active">
		
			  
			  <div class="col-xs-12 col-sm-3 col-md-3">
			  
			  <form:form class="login-form clearfix"  id="profileImage_form"  name="profileImage_form" method="post" >
		<div class="well well-sm text-center">
			  <p><strong>${SourceLeadWeb_user.firstName} ${SourceLeadWeb_user.lastName}</strong></p>
				  <div class="profile-img-container profile-image" style="position:relative;">
				   <img  id="userImage" data-id="userProfileImage" onerror="predefineProfileImage(this);" src="${SourceLeadWeb_user.fileUrl}" alt="Profile Image" class="img-circle img-responsive">
				  <div class="btn-group uploadfile">
				  <button type="button" class="btn btn-sm btn-primary">
				  <i class="fa fa-upload"></i></button>
				  <label class="btn btn-sm btn-primary">
                    Upload / Change<input type="file" name="file" id="uploadedImage"  accept="image/*" onchange="cropImage(this, 'profileSettings_form', '', true);" style="visibility:hidden;height:0;width:10px;overflow:hidden" >
                  </label>
                  <c:if test="${!empty SourceLeadWeb_user.fileContentType}">
                  <button type="button" class="btn btn-sm btn-danger" onclick="deleteImage()">
				  <i class="fa fa-trash"></i></button>
				  </c:if>
				  </div>
				  </div>
				 </div> 
				 
				 </form:form>	
			  
				  <p class="text-danger small" style="font-size: 11px; display: none;" id="errorImage" >Invalid Image/Size</p>
				  <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;png, svg and gif images are not supported</p>
				  <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;Image size should not more than 200 KB</p>
			  </div>
			 <div id="popUp" ></div>  
			  
		   <div class="col-xs-12 col-sm-9 col-md-9">
		   <form:form class="login-form clearfix"  id="profileSettings_form"  name="profileSettings_form" method="post" >
			   <div class="row mt">
				<div class="col-md-6">
					<div class="form-group" id="firstNameDiv">
					<label class="col-md-4">First Name<span class="text-danger paddingLeft">*</span></label>
					<div class="col-md-8">
					  <input type="text" name="firstName" value="${SourceLeadWeb_user.firstName}" field-name="First Name" class="form-control" data-validation="required validate_Alphanumeric validate_Space validate_length length2-100"  >
					</div>
					</div>
				   <div class="form-group" id="lastNameDiv">
					<label class="col-md-4">Last Name</label>
					<div class="col-md-8">
					  <input type="text" name="lastName" value="${SourceLeadWeb_user.lastName}" field-name="Last Name" class="form-control" data-validation="validate_Alphanumeric validate_Space validate_length length2-100" data-validation-optional="true" />
					</div>
				   </div>
				  <div class="form-group">
					<label class="col-md-4">Email<span class="text-danger paddingLeft">*</span></label>
					<div class="col-md-8">
					<input type="text" readonly="readonly" value="${SourceLeadWeb_user.email}" class="form-control">
					</div>
				  </div>
		
				</div>
		
				<div class="col-md-6">
						  <div class="form-group" id="designationDiv">
							<label class="col-md-4">Designation</label>
							<div class="col-md-8">
							  <input type="text" name="designation" field-name="Designation" value="${SourceLeadWeb_designation}" class="form-control" data-validation-optional="true" data-validation="validate_length length2-100" />
							</div>
						  </div>
						  <div class="form-group" id="phoneDiv">
							<label class="col-md-4">Phone</label>
							<div class="col-md-8">
							  <input type="text" name="mobilePhone" field-name="Mobile Phone" value="${SourceLeadWeb_user.mobilePhone}" class="form-control" data-validation-optional="true" data-validation="validate_Space validate_length length9-20 validate_phone" />
							</div>
						  </div>
						  <div class="form-group" id="loactionDiv">
							<label class="col-md-4">Location</label>
							<div class="col-md-8">
							  <%-- <textarea rows="1" name="location" field-name="Location"  id="userLocation" class="form-control"  />${SourceLeadWeb_user.location}</textarea> --%>
							  <input type="text" value="${SourceLeadWeb_user.location}" class="form-control" name="location" field-name="Location"  id="userLocation" />
							  <i class="fa fa-refresh btn btn-primary refresh-icon mr" title="Refresh" onclick="refreshLocation();"></i>
							</div>
						</div>
					</div>
			
			
			
			<div class="col-md-12 clearfix">
			<h4>Social Links</h4>
			</div>
					
			<div class="col-md-6">
					<div class="form-group" id="linkedInDiv">
					<label class="col-md-4">LinkedIn</label>
					<div class="col-md-8">
					  <input type="text" name="linkedIn" value="${SourceLeadWeb_user.linkedIn}" field-name="Linked In" class="form-control" data-validation="validate_url validate_Space" data-validation-optional="true" >
					</div>
					</div>
				   <div class="form-group" id="fbDiv">
					<label class="col-md-4">Facebook</label>
					<div class="col-md-8">
					  <input type="text" name="facebook" value="${SourceLeadWeb_user.facebook}" field-name="Facebook" class="form-control" data-validation="validate_url validate_Space" data-validation-optional="true">
					</div>
				   </div>
				</div>
				
				<div class="col-md-6" id="twitterDiv">
					<div class="form-group">
					<label class="col-md-4">Twitter</label>
					<div class="col-md-8">
					  <input type="text" name="twitter" value="${SourceLeadWeb_user.twitter}" field-name="Twiter" class="form-control" data-validation="validate_url validate_Space" data-validation-optional="true" >
					</div>
					</div>
				   <div class="form-group" id="gplusDiv">
					<label class="col-md-4">Google+</label>
					<div class="col-md-8">
					  <input type="text" name="googlePlus" value="${SourceLeadWeb_user.googlePlus}" field-name="Google+" class="form-control" data-validation="validate_url validate_Space" data-validation-optional="true">
					</div>
				   </div>
		
				</div>
					 <div class="col-md-12">
						 <hr>
							<div class="pull-right">
							  <input type="submit" class="btn btn-primary mr" id="profile_submit" value="Save">
							  <input type="button" class="btn btn-default" value="Cancel" onClick="self.location='/sourcelead/dashboard'" >
							</div>
					  </div>
			 </div>
			 </form:form>	
			</div>
			
	
	
	
			</div>
			<div id="password" class="tab-pane fade">
			  <div class="col-xs-12 col-sm-3 col-md-3 text-center well">
				  <div class="password-icon mt mb text-center">
				    <i class="fa fa-lock" aria-hidden="true"></i>
				  </div>
			  </div>
			<div class="col-xs-12 col-sm-9 col-md-9">
				  <div class="row mt">
					
					<form:form class="login-form clearfix"  id="passwordSettings_form"  name="passwordSettings_form" method="post" >
					<div class="col-md-8">
					 <div id=passwordDiv>	
						<div class="form-group" id="currentPwdDiv">
						<label class="col-md-4">Current Password<span class="text-danger paddingLeft">*</span></label>
						<div class="col-md-6">
						  <input type="password" id="currentPassword" name="currentPassword" field-name="Current Password"   class="form-control"  onblur="validateCurrentPassword();" data-validation="required" />
						</div>
						</div>
						<div class="form-group" id="newPwdDiv">
						<label class="col-md-4">New Password<span class="text-danger paddingLeft">*</span></label>
						<div class="col-md-6">
						  <input type="password" name="password" id="password" field-name="New Password" class="form-control" data-validation="required validate_length length6-100"   />
						</div>
				       </div>
					  <div class="form-group" id="confirmPwdDiv">
						<label class="col-md-4">Confirm Password<span class="text-danger paddingLeft">*</span></label>
						<div class="col-md-6">
						<input type="password"  value="" id="confirmPassword" class="form-control" field-name="Confirm Password" onblur="checkPass();" data-validation="required"/>
						</div>
				      </div>
				     </div> 
					</div>
				 <div class="col-md-7">
				 <hr class="mr">
					<div class="pull-right mr">
					  <input type="submit" value="Save" id="save_button" onclick="checkPass();" class="btn btn-primary mr" />
					  <input type="button" class="btn btn-default" value="Cancel" onClick="self.location='/sourcelead/dashboard'">
					</div>
				  </div>
				  </form:form>
				  
				 </div>
			</div>
			</div>
		   </div>
		</div>
			<!-- Tab for Profile setting-area ends -->
		  </div>
		  </div>
		  </div>
		  </div>
  </section>
  </body>
  <!-- Tab for Profile setting-area starts Ends -->
  <!-- Content Area Ends--> 

<jsp:include page="masterFooter.jsp" />


<script type="text/javascript">
	 function validateCurrentPassword() {   
		var currentPassword = $('input#currentPassword').val();
		currentPassword = encodeURIComponent(currentPassword);
		if(currentPassword != null  && currentPassword != '' && currentPassword != 'undefined'){
			var sourceleadUrl ='${sourceleadUrl}';
			
		     $.ajax({
			    	type: "POST",
			    	 url: sourceleadUrl+"/validateCurrentPassword?${_csrf.parameterName}=${_csrf.token}&currentPassword="+currentPassword, 
			        success: function(result) {
			         	 if(result == 'valid'){
				    		$("#save_button").removeAttr("disabled");
				    		$('input#currentPassword').removeAttr( "class record-exist record-exist-errormsg");
							$('#passwordDiv').find('p.jquery_form_error_message').remove();
							$('input#currentPassword').attr('class','valid form-control');
							$( "#save_button" ).removeAttr("style");
							$( "#save_button" ).addClass('btn btn-primary mr').val('Save');
							$( "#save_button" ).click(function() {
							});
							
				    	}
				    	else if(result == 'invalid'){
			    		$("#save_button").attr("disabled", "disabled");
				    		$('#passwordDiv').find('p.jquery_form_error_message').remove(); 
				    		$('input#currentPassword').attr('class','error form-control');
				    		$("input#currentPassword").after("<p class='jquery_form_error_message'>Wrong Password Entered</p>");
							$('input#currentPassword').attr('record-exist','yes');
							$('input#currentPassword').attr('record-exist-errorMsg',' Wrong Password Entered'); 
							$("#save_button" ).addClass('btn btn-primary mr').val('Save');
				    	} 
			        },
			    }); 
				
		}
		
	 }
 </script>



<script type="text/javascript">
	 function checkPass() {   
		var pass1 = $('input#password').val();  
	    var pass2 = $('input#confirmPassword').val();
	    if((pass1 != null && pass1 != '' && pass1 != 'undefined') && (pass2 != null && pass2 != '' && pass2 != 'undefined')){
	    	if(pass1 == pass2){
	    		$("#save_button").removeAttr("disabled");
	    		$('input#confirmPassword').removeAttr( "class record-exist record-exist-errormsg");
				$('#passwordDiv').find('p.jquery_form_error_message').remove();
				$('input#confirmPassword').attr('class','valid form-control');
				$( "#save_button" ).removeAttr("style");
				$( "#save_button" ).addClass('btn btn-primary mr').val('Save');
				$( "#save_button" ).click(function() {
				 if ($(this).validate(false, validationSettings)){
				 }
				});
				
	    	}
	    	else{
	    		$("#save_button").attr("disabled", "disabled");
	    		$('#passwordDiv').find('p.jquery_form_error_message').remove(); 
	    		$('input#confirmPassword').attr('class','error form-control');
	    		$("input#confirmPassword").closest('b').after("<p class='jquery_form_error_message'> Passwords Do Not Match</p>");
				$('input#confirmPassword').attr('record-exist','yes');
				$('input#confirmPassword').attr('record-exist-errorMsg',' Passwords Do Not Match'); 
				$( "#save_button" ).addClass('btn btn-primary mr').val('Save');
				$("#save_button").removeAttr("disabled");
	    	
	    	}
	    }
   }
	
   
</script>


<script type="text/javascript">
  var loadFile = function(event) {
    var output = document.getElementById('userImage');
    
    var uploadedImage = document.getElementById('uploadedImage').value;
    var sizeinbytes = document.getElementById('uploadedImage').files[0].size;
    document.getElementById("errorImage").style.display="none"; 
    
    var extension = uploadedImage.substring(uploadedImage.lastIndexOf(".")+1).toLowerCase();
    
    if(extension != 'jpg'){
    	output.src = URL.createObjectURL(event.target.files[0]);
    	
    	var extensionName = uploadedImage.substring(uploadedImage.lastIndexOf(".")+1);
 		var msg = "."+extensionName+" extension not supported";
 		alertMessage(msg);
 		document.getElementById("errorImage").style.display=""; 
 		$("#profile_submit").attr("disabled", "disabled");
    	
    }else if(sizeinbytes > 200000){
    	output.src = URL.createObjectURL(event.target.files[0]);
 		var msg = "Image size should be less than 200 KB";
 		alertMessage(msg);
 		document.getElementById("errorImage").style.display=""; 
 		$("#profile_submit").attr("disabled", "disabled");
    	
    }else{
    	output.src = URL.createObjectURL(event.target.files[0]);
    	$("#profile_submit").removeAttr("disabled")
    }
    
  };
</script>

<script type="text/javascript">
function alertMessage(message){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script type="text/javascript">
function deleteImage(){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure ?</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='deleteProfileImage();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<script type="text/javascript">
function deleteProfileImage(){
	var sourceleadUrl ='${sourceleadUrl}';
	
	 $.ajax({
		    type: "GET",
            url: sourceleadUrl+"/deleteProfileImage",
	        success: function(data) {
	        	 var result = data;
     		if(result == "success"){
     			location.replace(sourceleadUrl+"/userProfile"); 
        	 }
	        
	       },
	        
     }); 
}
</script>

<script type="text/javascript">
function cancel(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>




<!-- <script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#profileSettings_form', function(e){
        	  if ($(profileSettings_form).validate(false, validationSettings)){
        		    e.preventDefault(); 
            		
             		var formData = getFormData('profileSettings_form');
             		
             		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
            		
                    $.ajax({
                	      type: "POST",
                	      data: formData,
                	      
                      	  url: sourceleadUrl+"/saveUserProfile?${_csrf.parameterName}=${_csrf.token}",
                        	success: function(result) {
    			        	 if(result == 'home'){
    			        		location.replace(sourceleadUrl+"/userProfile");  
    			        	}
    			        },  
                    });
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
         $('#profileSettings_form').showHelpOnFocus();
         $('#profileSettings_form').validateOnBlur(false, validationSettings); 
     
	</script>  -->
	
	
<script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#passwordSettings_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		    e.preventDefault(); 
        		    var formData = getFormData("passwordSettings_form");
            		var sourceleadUrl ='${sourceleadUrl}';
            		
             		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
            		
                    $.ajax({
                	      type: "POST",
                      	  url: sourceleadUrl+"/passwordSettings?${_csrf.parameterName}=${_csrf.token}",
                      	  data: formData,
                        	success: function(result) {
    			        	 if(result == 'success'){
    			        		location.replace(sourceleadUrl+"/dashboard");  
    			        	}else if(result == 'Invalid Password'){
    			        		 $('#passwordDiv').find('span.jquery_form_error_message').remove();
                 		         document.getElementById("currentPassword").setAttribute('class','error form-control');
                 		         $("input#currentPassword").after("<span class='jquery_form_error_message'> <font color='red'>Invalid Password</font></span>");
                 				 document.getElementById("currentPassword").setAttribute('pass-exist','yes');
                 				 document.getElementById("currentPassword").setAttribute('pass-exist-errorMsg', ' Invalid Password'); 
                 				 $("input#currentPassword").removeAttr("style");
                 				 $("#loading").removeAttr("style");
    			        	} 
    			        },  
                    });
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
         $('#passwordSettings_form').showHelpOnFocus();
         $('#passwordSettings_form').validateOnBlur(false, validationSettings); 
            
     
	</script> 
<script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#profileSettings_form', function(e){
        	 var userId = '${SourceLeadWeb_user.id}';
        	 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
        	 
        	  if ($('#profileSettings_form').validate(false, validationSettings)){
        		    e.preventDefault(); 
            		
             		var formData = getFormData('profileSettings_form');
             		
             		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
            		
                    $.ajax({
                	      type: "POST",
                	      data: formData,
                      	  url: sourceleadUrl+"/saveUserProfile?"+tokenParamName+"="+tokenValue+"&userId="+userId+"&orgLocationId="+orgLocationId,
                        	success: function(result) {
    			        	 if(result != null){
    			        		location.replace(sourceleadUrl+"/userProfile");  
    			        	}
    			        },  
                    });
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
         $('#profileSettings_form').showHelpOnFocus();
         $('#profileSettings_form').validateOnBlur(false, validationSettings); 
     
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


<script>
function refreshLocation(){
	$("#userLocation").val('');
}
</script>


</html>