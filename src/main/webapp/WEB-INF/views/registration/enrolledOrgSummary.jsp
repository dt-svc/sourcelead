<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>::Org Details</title>
</head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<body>

<!-- Content Area -->
  <!-- Tab for Profile setting-area starts -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>${orgloc.org.orgName}</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/enrolledOrgsList'/>">Orgs List</a> &raquo; Org Summary</div>
    </section>
    <div class="content-area clearfix">
      <div class="clearfix form-area form-horizontal toggle-div remove-div mt">
      <div id="loading" style="z-index: 9999999999 !important;"></div> 
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		    <!-- Tab for Profile setting-area -->
		  <div class="accountsettings-tab org-settings-area">
		  <div id="choicepopUp"></div>
		  
		  <ul class="nav nav-tabs">
			<li class="active"><a data-toggle="tab" href="#details" onclick="flushUser(); removeAddUserPopUp();">Details</a></li>
			<li><a data-toggle="tab" href="#subscription" onclick="orgSubscriptionList();">Subscription</a></li>
			<li><a data-toggle="tab" href="#users" onclick="getUserList();">Users</a></li>
		  </ul>
		  
		  <div class="tab-content panel-body">
		  <div id="details" class="tab-pane fade in active">
			<div class="clearfix">
			  <form:form class="login-form clearfix"  id="update_module_form"  name="update_module_form" method="post" > 
			  
			  <div class="col-xs-12 col-sm-3 col-md-3">
				 <div class="well well-sm text-center">
				  <div class="profile-img-container" style="position:relative;">
				   <img id="orgImage" onerror="predefineOrgImage(this);" data-id="orgLogoImage" src="${orgloc.org.fileUrl}" alt="Org Image" class="comp-image img-responsive">
				  </div>
				  </div>
			  </div>
				<div class="col-xs-12 col-sm-9 col-md-9">
					<div class="row mrng0">
						<div class="col-md-6">
								
								<div class="form-group">
								<div class="col-md-12 clearfix">
								<h5>Org Details</h5>
								</div>
								</div>
								
								<div class="form-group">
								<label class="col-md-4">Company :</label>
								<p class="tbldata-dotdot-me">${orgloc.org.orgName}</p>
								</div>
								
								<div class="form-group">
								<label class="col-md-4">Website :</label>
								<p class="tbldata-dotdot-me">${orgloc.website}</p>
								</div>
								
								<div class="form-group">
								<label class="col-md-4">Location :</label>
								<p class="tbldata-dotdot-me">${orgloc.location}</p>
								</div>
								
								<div class="form-group">
								<label class="col-md-4">Enrolled Date :</label>
								<p><fmt:formatDate type="date"  value="${orgloc.createdDate}" /></p></p>
								</div>
								
								<div class="form-group">
								<label class="col-md-4">Time Zone :</label>
								<p class="tbldata-dotdot-me">${orgloc.timezone.timeZoneId}</p>
								</div>
								
								<div class="form-group">
								<label class="col-md-4">Date Format :</label>
								<p class="tbldata-dotdot-me">${orgloc.dateFormat}</p>
								</div>
	                  </div>
	                  
	                  
	                  <div class="col-md-6">
								<%-- <c:if test="${!empty orgloc.user.id}"> --%>
								<div class="form-group">
								<div class="col-md-12 clearfix">
								<h5>Contact Details&nbsp; <c:if test="${userState == 'NOT_LOGGEDIN'}"><a id="edit-user" href="javascript:editPrimaryUser();"><i class="fa fa-edit" title="Edit Contact Details"></i></a></c:if></h5>
								</div>
								</div>
								
								<div class="form-group">
								<label class="col-md-4">First Name : </label>
								<c:if test="${not empty orgloc.user.firstName}">
									<p class="tbldata-dotdot-me">${orgloc.user.firstName}</p>
								</c:if>
								<c:if test="${empty orgloc.user.firstName}">
									<p>--</p>
								</c:if>
								
								</div>
								
								<div class="form-group">
								<label class="col-md-4">Last Name : </label>
								<c:if test="${!empty orgloc.user.lastName}">
									<p class="tbldata-dotdot-me">${orgloc.user.lastName}</p>
								</c:if>
								<c:if test="${empty orgloc.user.lastName}">
									<p>--</p>
								</c:if>
								
								</div>
								
								<div class="form-group">
								<label class="col-md-4">Email: </label>
								<c:if test="${!empty orgloc.user.email}">
									<p class="tbldata-dotdot-me">${orgloc.user.email}</p>
								</c:if>
								<c:if test="${empty orgloc.user.email}">
									<p>--</p>
								</c:if>
								
								</div>
								
								<div class="form-group">
								<label class="col-md-4">Contact : </label>
								<c:if test="${!empty orgloc.user.mobilePhone}">
									<p>${orgloc.user.mobilePhone}</p>
								</c:if>
								<c:if test="${empty orgloc.user.mobilePhone}">
									<p>--</p>
								</c:if>
								</div>
							<%-- </c:if> --%>
								
	                  </div>
	                  
	                  
	                  
				
					
					</div>
					
					</div>
					</form:form>
					
					
				 <div id="edit_primaryUser_pop" class="edit_orgprimaryuser_pop" >
					<div id="editPrimaryUserLoading"></div>
	 						<form id="editPrimaryUser_form"  name="editPrimaryUser_form" method="post">
	 						<div class="form-group">
								<div class="col-md-12 clearfix">
								<h5>Edit Contact Details</h5>
								</div>
							</div>
							
							<div class="user_summary_edit_cta">
								 <label>First Name<span class="text-danger">&nbsp; *</span></label>
									<input type="text" value="${orgloc.user.firstName}"  id="primaryUserFirstName" name="primaryUserFirstName" field-name="First Name" data-validation="required validate_Alphanumeric validate_Space validate_length length1-100" class="form-control">
							</div>
							
							<div class="user_summary_edit_cta">
								 <label>Last Name</label>
									<input type="text" value="${orgloc.user.lastName}" id="primaryUserLastName"  name="primaryUserLastName" field-name="Last Name" data-validation="validate_Alphanumeric validate_Space validate_length length1-100" class="form-control" data-validation-optional="true">
							</div>
							
							<div class="user_summary_edit_cta" id="primaryUserEmailDiv">
								 <label>Email<span class="text-danger">&nbsp; *</span></label>
									<input type="text" value="${orgloc.user.email}" name="primaryUserEmail" id="primaryUserEmail" field-name="Email" onblur="validatePrimaryUserEmail()" data-validation="required validate_Space validate_email validate_length length2-100"  class="form-control">
							</div>
							
							<div class="user_summary_edit_cta" id="phoneNumberDiv">
								 <label>Phone Number</span></label>
									<input type="text" value="${orgloc.user.mobilePhone}" name="mobilePhone" id="mobilePhone" field-name="Phone Number"  data-validation-optional="true" data-validation="validate_Space validate_length length9-20 validate_phone" class="form-control">
							</div>
							
							 
				   			 <div class="clearfix"></div>
							 <div class="user_summary_edit_cta pull-right">
							 <input class="us-btn" value="Save" id="update_primaryuser" onclick="validatePrimaryUserEmail();savePrimaryUserDetails();" type="button" > <input class="us-btn us-btn_a" value="Cancel" onClick="editPrimaryUser();" type="button">
							 </div>
								
							</form>
				 </div>
					
				</div>
			</div>
			
			
			  <jsp:include page="orgSubscriptions.jsp" />
			  <jsp:include page="orgUsersList.jsp" />
			</div>
			</div>
		   </div>
		</div>
			<!-- Tab for Profile setting-area ends -->
		  </div>
		   
		  </div>
  </section>
  <!-- Tab for Profile setting-area starts Ends -->
  <!-- Content Area Ends-->

</body>


<script>
function editPrimaryUser(){
	$("#edit_primaryUser_pop").slideToggle(100);
	
	$("#primaryUserFirstName").val('');
	$("#primaryUserLastName").val('');
	$("#primaryUserEmail").val('');
	
	$("#primaryUserFirstName").val('${orgloc.user.firstName}');
	$("#primaryUserLastName").val('${orgloc.user.lastName}');
	$("#primaryUserEmail").val('${orgloc.user.email}');
	
	
	
	
}
</script>

<script>
function validatePrimaryUserEmail(){
	var email = document.getElementById("primaryUserEmail").value;
	var sourceleadUrl ='${sourceleadUrl}';
	var orgLocationId = '${registeredOrgLocationId}';
	if(null != email  && '' != email){
		$("#update_primaryuser").attr("disabled", "disabled");
		$.ajax({
		   	type: "GET",
		    url: sourceleadUrl+"/validateOrgPrimaryUserEmail?${_csrf.parameterName}=${_csrf.token}&email="+email+"&orgLocationId="+orgLocationId,
		       success: function(data) {
		    		if(data != null && data != ''){
		    			if(data == 'Existed Email'){
			        		  $('#primaryUserEmailDiv').find('p.jquery_form_error_message').remove();
							  $("input#primaryUserEmail").after("<p class='jquery_form_error_message'>"+email+" is Already Existed</span>");
							  document.getElementById("primaryUserEmail").setAttribute('record-exist','yes');
							  document.getElementById("primaryUserEmail").setAttribute('record-exist-errorMsg',email+' is Already Existed'); 
			        	}
			        	else{
			        		 $('input#primaryUserEmail').removeAttr( "record-exist record-exist-errormsg");
			        		 $('#primaryUserEmailDiv').find('p.jquery_form_error_message').remove(); 
	                         $("#update_primaryuser").removeAttr("disabled");
			        	}
					     
		    			
			       	}
		    	   
		      },
		       
		}); 
	}
	
}
</script>


<script type="text/javascript">
 function savePrimaryUserDetails() {
	 var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
		   }; 
	 if ($('#editPrimaryUser_form').validate(false, validationSettings)){ 
		 
		 var sourceleadUrl ='${sourceleadUrl}';
         var formData = getFormData("editPrimaryUser_form");
         var userId = '${orgloc.user.id}';
         var orgId = '${orgId}';
         var orgCode = '${orgCode}';
         var orgLocationId = '${registeredOrgLocationId}'
         
        $("#editPrimaryUserLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
          
          $.ajax({
             type: "POST",
             data: formData,
             url: sourceleadUrl+"/editOrgPrimaryUser?${_csrf.parameterName}=${_csrf.token}&userId="+userId+"&orgLocationId="+orgLocationId,
             success: function(result) {
                 
            	 if (result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
 	    		    showError(result.responseMessage);
 	    		    $("#editPrimaryUserLoading").removeAttr('style');
 	    		}
           		else if (result.response === "AWKWARD") {
 	    		    location.href = "/errorPage";
 	    		}
           		else if(result.response === "SUCCESS"){
           			 $("#editPrimaryUserLoading").removeAttr("style");
           			 location.replace(sourceleadUrl+"/enrolledOrgSummary?orgCode="+orgCode);
		        	    }
           		else if (result.indexOf('Sign In') !== -1) {
 					location.replace(sourceleadUrl);
 				} else {
 				    showError("Operation failed, please try again");
 				    $("#editPrimaryUserLoading").removeAttr('style');
 				}
            	 
                 
             }
             });  
         
		 
		 return true;
	 }else{
		 return false;
	 }
    }
 
 $('body').on('blur', '#editPrimaryUser_form', function() {
		$("#editPrimaryUser_form").showHelpOnFocus();;
		$("#editPrimaryUser_form").validateOnBlur(false, validationSettings);  
	});
</script>





 		

	



<jsp:include page="../masterFooter.jsp" />