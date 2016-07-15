<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<link href="resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="resources/css/font-awesome.min.css" rel="stylesheet">
<link href="resources/css/sourcelead-main.css" rel="stylesheet">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
    
<script>
	function predefineProfileImage(imageId){
		$(imageId).attr('src','resources/images/userprofile-img.png');
	}
</script>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>::Org Settings</title>
</head>

<body>
  <!-- Content Area -->
  <!-- Tab for Profile setting-area starts -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Account Settings</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='/home'/>">Home</a> &raquo; Account Settings</div>
    </section>
    <div class="content-area clearfix">
      <section class="action-btns clearfix">
        <ul class="pull-right list-unstyled list-inline">
          <li><a href="#" class="faclose"><i aria-hidden="true" class="fa fa-close"></i></a></li>
          <li><a href="#" class="collpase-div"><i aria-hidden="true" class="fa fa-angle-down"></i></a></li>
        </ul>
      </section>
      <div class="clearfix form-area form-horizontal toggle-div remove-div">
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
			  <div class="col-xs-12 col-sm-3 col-md-3 text-center well">
				  <p><strong>${SourceLeadWeb_user.firstName} ${SourceLeadWeb_user.lastName}</strong></p>
				  <div class="profile-image mt mb">
				    <img  id="userImage" onerror="predefineProfileImage(this);" src="<c:url value='/displayUserImage/${SourceLeadWeb_userId}'/>" alt="Profile Image" class="img-circle img-responsive">
				  </div>
				  <div class="btn-group mt">
				  <button type="button" class="btn btn-primary">
				  <i class="fa fa-upload"></i></button>
				  <label class="btn btn-primary">Upload / Change
				  <input type="file" name="file" onchange="loadFile(event)" style="display:none;">
				  </label>
				  </div>
			  </div>
		   <div class="col-xs-12 col-sm-9 col-md-9">
			   <div class="row mt">
				<div class="col-md-6">
					<div class="form-group">
					<label class="col-md-4">First Name</label>
					<div class="col-md-8">
					  <input type="text" name="firstName" value="${SourceLeadWeb_user.firstName}" class="form-control" required>
					</div>
					</div>
				   <div class="form-group">
					<label class="col-md-4">Last Name</label>
					<div class="col-md-8">
					  <input type="text" name="lastName" value="${SourceLeadWeb_user.lastName}" class="form-control" required>
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
							  <input type="text" name="jobTitle" value="${SourceLeadWeb_user.jobTitle}" class="form-control" required>
							</div>
						  </div>
						  <div class="form-group">
							<label class="col-md-4">Phone</label>
							<div class="col-md-8">
							  <input type="text" name="mobilePhone" value="${SourceLeadWeb_user.mobilePhone}" class="form-control" required>
							</div>
						  </div>
						  <div class="form-group">
							<label class="col-md-4">Location</label>
							<div class="col-md-8">
							  <textarea rows="1" name="location"  class="form-control" required>${SourceLeadWeb_user.location}</textarea>
							</div>
						</div>
					</div>
					 <div class="col-md-12">
						 <hr>
							<div class="pull-right">
							  <input type="submit" class="btn btn-primary mr" value="Save">
							  <input type="button" class="btn btn-default" value="Cancel" onClick="self.location='/sourcelead/home'" >
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
						<div class="form-group">
						<label class="col-md-4">Current Password</label>
						<div class="col-md-6">
						  <input type="password" id="currentPassword" name="currentPassword" value="${currentPassword}"  class="form-control"  required >
						</div>
						</div>
						<div class="form-group">
						<label class="col-md-4">New Password</label>
						<div class="col-md-6">
						  <input type="password" name="password"  value="${password}" id="password" class="form-control" required  onkeydown="$(this).clear();" />
						</div>
				       </div>
					  <div class="form-group">
						<label class="col-md-4">Confirm Password</label>
						<div class="col-md-6" id="passwordDiv">
						<input type="password"  value="" id="confirmPassword" class="form-control"  onblur="$(this).checkPass();" required/>
						</div>
				  </div>
					</div>
				 <div class="col-md-7">
				 <hr class="mr">
					<div class="pull-right mr">
					  <input type="submit" value="Save" id="save_button" class="btn btn-primary mr" />
					  <input type="button" class="btn btn-default" value="Cancel" onClick="self.location='/sourcelead/home'">
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
(function($) {
	$.fn.checkPass = function() {   
		var pass1 =$('input#password').val();  
	    var pass2 = $('input#confirmPassword').val();
	    if(pass1 == pass2){
	    	 $('input#confirmPassword').removeAttr( "class pass-exist pass-exist-errormsg");
			 $('#passwordDiv').find('span.jquery_form_error_message').remove(); 
			 document.getElementById("confirmPassword").setAttribute('class','valid form-control');
			 $("input#confirmPassword").removeAttr("style");
			 document.getElementById('save_button').disabled = false;
	    }
	    else{ 
		        $('#passwordDiv').find('span.jquery_form_error_message').remove();
		        document.getElementById("confirmPassword").setAttribute('class','error form-control');
		        $("input#confirmPassword").after("<span class='jquery_form_error_message'> <font color='red'>Passwords Not Matched</font></span>");
				document.getElementById("confirmPassword").setAttribute('pass-exist','yes');
				document.getElementById("confirmPassword").setAttribute('pass-exist-errorMsg', ' Passwords Not Matched'); 
				$("input#confirmPassword").removeAttr("style");
				document.getElementById('save_button').disabled = true;
	    }
   }
	
    $.fn.clear = function() {   
         $('#confirmMessage').empty(); 
    	 $('#confirmPassword').val('');
    	
	 return false;
    }
})(jQuery);
</script>



<script>
  var loadFile = function(event) {
    var output = document.getElementById('userImage');
    output.src = URL.createObjectURL(event.target.files[0]);
  };
</script>


<script type="text/javascript">
        $('#profileSettings_form')
           .submit(function(e) {
        	    e.preventDefault(); 
    		    
        	    var frm = $('#profileSettings_form').serialize();
        	    frm = "testvalue=testvalue&"+frm;
        		var con = JSON.stringify(frm);
        		var sourceleadUrl ='${sourceleadUrl}';
        		var form = $('#profileSettings_form')[0];
         		var formData = new FormData(form);
        		
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
            });
</script>


<script type="text/javascript">
        $('#passwordSettings_form')
           .submit(function(e) {
        	    e.preventDefault(); 
    		    
        	    var frm = $('#passwordSettings_form').serialize();
        	    frm = "testvalue=testvalue&"+frm;
        		var con = JSON.stringify(frm);
        		var sourceleadUrl ='${sourceleadUrl}';
        		
                $.ajax({
            	      type: "POST",
            	      contentType : 'application/json; charset=utf-8',
            	      dataType : 'json',
                  	  url: sourceleadUrl+"/passwordSettings?${_csrf.parameterName}=${_csrf.token}&json="+con,
                    	success: function(result) {
			        	 if(result == 'home'){
			        		location.replace(sourceleadUrl+"/home");  
			        	}else if(result == 'Invalid Password'){
			        		 $('#passwordDiv').find('span.jquery_form_error_message').remove();
             		         document.getElementById("currentPassword").setAttribute('class','error form-control');
             		         $("input#currentPassword").after("<span class='jquery_form_error_message'> <font color='red'>Invalid Password</font></span>");
             				 document.getElementById("currentPassword").setAttribute('pass-exist','yes');
             				 document.getElementById("currentPassword").setAttribute('pass-exist-errorMsg', ' Invalid Password'); 
             				 $("input#currentPassword").removeAttr("style");
			        	} 
			        },  
                });
            });
</script>
</html>