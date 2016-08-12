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
<link href="resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="resources/css/font-awesome.min.css" rel="stylesheet">
<link href="resources/css/sourcelead-main.css" rel="stylesheet">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

 <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
 <script src="http://code.jquery.com/ui/1.11.3/jquery-ui.js"></script>
 <script src="resources/js/bootstrap.min.js"></script>
 <script src="resources/js/jquery-fun.js"></script> 
 <script src="resources/js/formvalidator.js"></script>
    
<script>
	function predefineProfileImage(imageId){
		$(imageId).attr('src','resources/images/userprofile-img.png');
	}
</script>

<body>
  <!-- Content Area -->
  <!-- Tab for Profile setting-area starts -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Account Settings</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='/dashboard'/>">Home</a> &raquo; Account Settings</div>
    </section>
    <div class="content-area clearfix">
      <section class="action-btns clearfix">
        <ul class="pull-right list-unstyled list-inline">
          <li><a href="#" class="faclose"><i aria-hidden="true" class="fa fa-close"></i></a></li>
          <li><a href="#" class="collpase-div"><i aria-hidden="true" class="fa fa-angle-down"></i></a></li>
        </ul>
      </section>
      <div class="clearfix form-area form-horizontal toggle-div remove-div">
        <div id="loading"></div> 
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		    <!-- Tab for Profile setting-area -->
		  <div class="accountsettings-tab">
		  <ul class="nav nav-tabs">
			<li class="active"><a data-toggle="tab" href="#profile">Profile</a></li>
			<li><a data-toggle="tab" href="#password">Password</a></li>
		  </ul>

		  <div class="tab-content panel-body">
			<div id="profile" class="tab-pane fade in active">
		<form:form class="login-form clearfix"  id="profileSettings_form"  name="profileSettings_form" method="post" >
			  
			  <div class="col-xs-12 col-sm-3 col-md-3">
			  <div class="well text-center">
			  <p><strong>${SourceLeadWeb_user.firstName} ${SourceLeadWeb_user.lastName}</strong></p>
				  <div class="profile-img-container profile-image" style="position:relative;">
				   <img  id="userImage" onerror="predefineProfileImage(this);" src="<c:url value='/displayUserImage/${SourceLeadWeb_userId}'/>" alt="Profile Image" class="img-circle img-responsive">
				  <div class="btn-group uploadfile">
				  <button type="button" class="btn btn-sm btn-primary">
				  <i class="fa fa-upload"></i></button>
				  <label class="btn btn-sm btn-primary">
                    Upload / Change<input type="file" name="file" id="uploadedImage"  onchange="loadFile(event)" style="display: none;">
                  </label>
                  <c:if test="${!empty SourceLeadWeb_user.fileContentType}">
                  <button type="button" class="btn btn-sm btn-danger" onclick="deleteImage()">
				  <i class="fa fa-trash"></i></button>
				  </c:if>
				  
				  </div>
				  </div>
				 </div> 
				  <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;.png and .svg images are not allowed</p>
				  <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;Image size should not more than 200 KB</p>
			  </div>
			 <div id="popUp" ></div>  
			  
		   <div class="col-xs-12 col-sm-9 col-md-9">
			   <div class="row mt">
				<div class="col-md-6">
					<div class="form-group">
					<label class="col-md-4">First Name</label>
					<div class="col-md-8">
					  <input type="text" name="firstName" value="${SourceLeadWeb_user.firstName}" field-name="First Name" class="form-control" data-validation="required validate_Alphabet validate_Space validate_length length2-100"  >
					</div>
					</div>
				   <div class="form-group">
					<label class="col-md-4">Last Name</label>
					<div class="col-md-8">
					  <input type="text" name="lastName" value="${SourceLeadWeb_user.lastName}" field-name="Last Name" class="form-control" data-validation="validate_Alphabet validate_Space validate_length length2-100" data-validation-optional="true" />
					</div>
				   </div>
				  <div class="form-group">
					<label class="col-md-4">Email</label>
					<div class="col-md-8">
					<input type="text" readonly="readonly" value="${SourceLeadWeb_user.email}" class="form-control">
					</div>
				  </div>
		
				</div>
		
				<div class="col-md-6">
						  <div class="form-group">
							<label class="col-md-4">Job Title</label>
							<div class="col-md-8">
							  <input type="text" name="jobTitle" field-name="Job Title" value="${SourceLeadWeb_user.jobTitle}" class="form-control" data-validation-optional="true" data-validation="validate_Alphabet validate_length length2-100" />
							</div>
						  </div>
						  <div class="form-group">
							<label class="col-md-4">Phone</label>
							<div class="col-md-8">
							  <input type="text" name="mobilePhone" field-name="Mobile Phone" value="${SourceLeadWeb_user.mobilePhone}" class="form-control" data-validation-optional="true" data-validation="validate_int validate_length length9-20" />
							</div>
						  </div>
						  <div class="form-group">
							<label class="col-md-4">Location</label>
							<div class="col-md-8">
							  <textarea rows="1" name="location" field-name="Location" class="form-control" data-validation-optional="true" data-validation="validate_length length2-500" />${SourceLeadWeb_user.location}</textarea>
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
			</div>
			
	</form:form>	
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
						<div class="form-group">
						<label class="col-md-4">Current Password</label>
						<div class="col-md-6">
						  <input type="password" id="currentPassword" name="currentPassword" field-name="Current Password" value="${currentPassword}"  class="form-control"  data-validation="required" />
						</div>
						</div>
						<div class="form-group">
						<label class="col-md-4">New Password</label>
						<div class="col-md-6">
						  <input type="password" name="password"  value="${password}" id="password" field-name="New Password" class="form-control" data-validation="required validate_length length6-100"   />
						</div>
				       </div>
					  <div class="form-group">
						<label class="col-md-4">Confirm Password</label>
						<div class="col-md-6">
						<input type="password"  value="" id="confirmPassword" class="form-control" field-name="Confirm Password" onblur="checkPass();" data-validation="required"/>
						</div>
				      </div>
				     </div> 
					</div>
				 <div class="col-md-7">
				 <hr class="mr">
					<div class="pull-right mr">
					  <input type="submit" value="Save" id="save_button" class="btn btn-primary mr" />
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
	 function checkPass() {   
		var pass1 =$('input#password').val();  
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
    
    if(uploadedImage.substring(uploadedImage.lastIndexOf(".")+1) == 'png' ||
    		uploadedImage.substring(uploadedImage.lastIndexOf(".")+1) == 'svg'){
    	
    	var extensionName = uploadedImage.substring(uploadedImage.lastIndexOf(".")+1);
 		var msg = "."+extensionName+" extension not allowed";
 		alertMessage(msg);
    	
    }else if(sizeinbytes > 200000){
 		var msg = "Image size should be less than 200 KB";
 		alertMessage(msg);
    	
    }else{
    	output.src = URL.createObjectURL(event.target.files[0]);
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




<script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#profileSettings_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		    e.preventDefault(); 
            		
            		var frm = $('#profileSettings_form').serializeFormJSON();
                	var con = JSON.stringify(frm);
    			    con = con.replace(/[{}]/g, ""); 
            		
            		var sourceleadUrl ='${sourceleadUrl}';
            		var form = $('#profileSettings_form')[0];
             		var formData = new FormData(form);
             		
             		$("#profile_submit").attr("disabled", "disabled");
             		$( "#profile_submit").addClass('button').val('Processing..');
             		
             		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
             		
            		
                    $.ajax({
                	      type: "POST",
                	      data: formData,
                	      contentType : 'application/json; charset=utf-8',
                	      enctype: 'multipart/form-data',
            	          processData: false,
            	          contentType:false,
                	      dataType : 'json',
                	      
                      	  url: sourceleadUrl+"/saveUserProfile?${_csrf.parameterName}=${_csrf.token}&json="+con,
                        	success: function(result) {
    			        	 if((result == 'home')){
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
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#passwordSettings_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		    e.preventDefault(); 
            		
            		var frm = $('#passwordSettings_form').serializeFormJSON();
                	var con = JSON.stringify(frm);
    			    con = con.replace(/[{}]/g, ""); 
            		
            		var sourceleadUrl ='${sourceleadUrl}';
            		
             		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
            		
                    $.ajax({
                	      type: "POST",
                	      contentType : 'application/json; charset=utf-8',
                	      dataType : 'json',
                      	  url: sourceleadUrl+"/passwordSettings?${_csrf.parameterName}=${_csrf.token}&json="+con,
                        	success: function(result) {
    			        	 if(result == 'home'){
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

</html>