<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />



<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script> 
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>

<link rel="stylesheet" href="resources/css/chosen.min.css">
<script src="resources/js/chosen.jquery.min.js"></script> 


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>::Org Details</title>
<style type="text/css">
.comp-textarea textarea {
min-height: 90px;
} 
#loading {
z-index: 9999999999 !important;
}
</style>
</head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


 <link href="resources/css/jquery-ui.css" rel="stylesheet">
<script src="resources/js/formvalidator.js"></script>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>

<body>

<!-- Content Area -->
  <!-- Tab for Profile setting-area starts -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>${orgloc.org.orgName}</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='/enrolledOrgsList'/>">Orgs List</a> &raquo; Org Summary</div>
    </section>
    <div class="content-area clearfix">
      <div class="clearfix form-area form-horizontal toggle-div remove-div mt">
      <div id="loading"></div> 
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		    <!-- Tab for Profile setting-area -->
		  <div class="accountsettings-tab org-settings-area">
		  <div id="choicepopUp"></div>
		  
		  <ul class="nav nav-tabs">
			<li class="active"><a data-toggle="tab" href="#details" onclick="flushUser(); removeAddUserPopUp();">Details</a></li>
			<li><a data-toggle="tab" href="#users" onclick="getUserList();">Users</a></li>
		  </ul>
		  
		  <div class="tab-content panel-body">
		  <div id="details" class="tab-pane fade in active">
			<div class="clearfix">
			  <form:form class="login-form clearfix"  id="update_module_form"  name="update_module_form" method="post" > 
			  
			  <div class="col-xs-12 col-sm-3 col-md-3">
				 <div class="well well-sm text-center">
				  <div class="profile-img-container" style="position:relative;">
				   <img id="orgImage" onerror="predefineOrgImage(this);" src="<c:url value='/displayOrgImage/${orgloc.org.orgCode}'/>" alt="Org Image" class="comp-image img-responsive">
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
								<p class="tbldata-dotdot-me">${orgloc.org.webSite}</p>
								</div>
								
								<div class="form-group">
								<label class="col-md-4">Country :</label>
								<p class="tbldata-dotdot-me">${orgloc.country}</p>
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
								<c:if test="${!empty orgloc.user.id}">
								<div class="form-group">
								<div class="col-md-12 clearfix">
								<h5>Contact Details&nbsp; <c:if test="${userState == 'NOT_LOGGEDIN'}"><a id="edit-user" href="javascript:editPrimaryUser();"><i class="fa fa-edit" title="Edit Contact Details"></i></a></c:if></h5>
								</div>
								</div>
								
								<div class="form-group">
								<label class="col-md-4">First Name : </label>
								<p class="tbldata-dotdot-me">${orgloc.user.firstName}</p>
								</div>
								
								<div class="form-group">
								<label class="col-md-4">Last Name : </label>
								<c:if test="${!empty orgloc.user.lastName}">
									<p class="tbldata-dotdot-me">${orgloc.user.lastName}</p>
								</c:if>
								<c:if test="${empty orgloc.user.lastName}">
									<p>-----</p>
								</c:if>
								
								</div>
								
								<div class="form-group">
								<label class="col-md-4">Email : </label>
								<p class="tbldata-dotdot-me">${orgloc.user.email}</p>
								</div>
								
								<div class="form-group">
								<label class="col-md-4">Contact : </label>
								<c:if test="${!empty orgloc.user.mobilePhone}">
									<p>${orgloc.user.mobilePhone}</p>
								</c:if>
								<c:if test="${empty orgloc.user.mobilePhone}">
									<p>-----</p>
								</c:if>
								</div>
							</c:if>
								
	                  </div>
	                  
	                  
	                  <div class="col-md-6">
								
								<div class="form-group">
								<div class="col-md-12 clearfix">
								<h5>Registered Modules</h5>
								</div>
								</div>
								
								<div class="form-group">
						            <div class="col-md-12 checkbox">
							         <c:forEach var="module" items="${registeredModulesList}">
										<ul class="list-unstyled pull-left">
				               				 <li>
										    <c:if test="${module.id == '1'}">
									         <label class="mr pull-left NoPad">
							           		     <p>${module.moduleName}</p>
							           		  </label>
							           		   <ul class="list-unstyled pull-left tree-checkbox">
							                    	 <c:forEach var="submodules" items="${registeredSubModulesList}">
							                    	  <label>
							                    	    <c:if test="${submodules.isModuleConfigured  == 'Y'}">
							                    		  <input type="checkbox" class="allsubmodules" id="submodules"  ${submodules.isModuleConfigured  == 'Y' ? 'checked' : ''} ${submodules.isModuleConfigured  == 'Y' ? 'disabled' : 'true'}  >
							                              ${submodules.moduleName}
							                             </c:if>
							                             <c:if test="${submodules.isModuleConfigured  != 'Y'}">
							                    		  <input type="checkbox" class="allsubmodules" id="submodules" value="${submodules.id}"  ${submodules.isModuleConfigured  == 'Y' ? 'checked' : ''} ${submodules.isModuleConfigured  == 'Y' ? 'disabled' : 'true'} name="submodulesList[]" >
							                              ${submodules.moduleName}
							                             </c:if>
							                         </label><br/>
							                    	 </c:forEach>
							                    </ul> 
							                </c:if> 
								          </li>
								          <li>
								           <label class="mr pull-left">      
											    <c:if test="${module.id != '1' && module.isModuleConfigured  == 'Y'}">
									           		 <input type="checkbox" id="isRecruiting" ${module.isModuleConfigured  == 'Y' ? 'checked' : ''} ${module.isModuleConfigured  == 'Y' ? 'disabled' : 'true'}  >&nbsp;${module.moduleName}
								                </c:if>  
								                <c:if test="${module.id != '1' && module.isModuleConfigured  != 'Y'}">
									           		 <input type="checkbox" id="isRecruiting" value="${module.id}"  ${module.isModuleConfigured  == 'Y' ? 'checked' : ''} ${module.isModuleConfigured  == 'Y' ? 'disabled' : 'true'} name="modulesList[]" >&nbsp;${module.moduleName}
								                </c:if>  
								             </label>
								             </li>
						                </ul>
									</c:forEach>
						            </div>
					            </div> 
	                  </div>
				
					
					</div>
					<div class="clearfix">
					<div class="pull-right">
					<input type="button" value="Upgrade" onclick="updateModules();" id="save_button" class="btn btn-primary mr">
					<input type="button" value="Cancel" class="btn btn-default" onClick="self.location='/sourcelead/enrolledOrgsList'">
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
			
			<div id="users" class="tab-pane fade">
			 
			 
			 <div class="success_pop" id="mailSendSuccessMsg"  style="display: none;">
	         		<i class="fa fa-check-circle"></i><span>Mail Send Successfully.</span>
		      </div>
			 
			 <div class="project_tabs_inn_table vendor_wrap_inn_table mt">
			 <a href="javascript:void(0)" class="addcon-pop btn btn-sm btn-primary mb10" onclick="getRegisteredOrgRoles();"><i class="glyphicon glyphicon-plus"></i>&nbsp;Add User</a>
			 <!-- Popup Div -->
                    <div class="collapse-div-adduser orgsummaryuser-pop orgsummary-adduser col-md-4" id="addNewUserPopup"> <i class="fa fa-close close-div closediv-pop" onclick="flushUser();"></i>
                          <div class="form-horizontal clearfix">
                          <div id="userLoading"></div> 
                        <form:form id="adduser_form"  name="adduser_form" method="post">                             
                            
                            <div class="clearfix mb10">
	                              <div class="col-md-12" id="userEmailDiv">
	                                <input type="text" data-validation="required validate_Space validate_length length2-100 validate_email"  field-name="Email" onblur="getUserByUserEmail(); validateUserEmail();" onKeyUp="validateUserEmail();"  name="userEmail" id="userEmail" class="form-control input-sm"  placeholder="Email*">
	                              	<input type="hidden" name="registeredOrgLocationId" value="${registeredOrgLocationId}">
	                              	<input type="hidden" name="registredOrgId" value="${orgId}">
	                              </div>
                              </div>
                              
                             <div class="clearfix mb10">
	                              <div class="col-md-12" id="firstNameDiv">
	                                <input type="text" data-validation="required validate_Space validate_Alphanumeric validate_length length1-100" field-name="First Name" name="firstName" id="firstName" class="form-control input-sm"  placeholder="First Name*">
	                              </div>
                              </div>
                              
                              <div class="clearfix mb10">
	                              <div class="col-md-12" id="lastNameDiv">
	                                <input type="text" data-validation="validate_Space validate_Alphanumeric validate_length length1-100" data-validation-optional="true"  field-name="Last Name"  name="lastName" id="lastName" class="form-control input-sm"  placeholder="Last Name">
	                              </div>
                              </div>
                              
                            
                            <div class="clearfix mb10">
                              <div class="col-md-12" id="userRoleDiv">
                                <select class="form-control chosen-select" data-validation="required" field-name="Role" id="userRole" name="userRole">
                                	 <option value="">-Select Role-</option>
                                </select>
                              </div>
                            </div>
                            <div class="clearfix mr">
                              <button type="submit" id="add_user"  class="btn btn-sm btn-primary pull-right">Add</button>
                            </div>
                          </form:form>  
                            
                          </div>
                        </div>
				<!-- Popup Div --> 
				
			 <div id="userList"></div>
			 <div id="loading"></div>
			 </div>
			</div>
			
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
 function getUserByUserEmail() {
	 var EmailId = document.getElementById("userEmail").value; 
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(EmailId != null && EmailId != ""){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getUserByUserEmail?${_csrf.parameterName}=${_csrf.token}&EmailId="+EmailId, 
		        success: function(data) {
		        	if(data != null && data != ''){
		        		var json = JSON.parse(data);
		        		if(json.firstName != null && json.firstName != ''){
		        			$("#firstName").val(json.firstName);
				        	$("#firstName").attr("readonly", "readonly");
				        	$('#firstNameDiv').find('p.jquery_form_error_message').remove();
			        		
			        		if(json.lastName != null && json.lastName != ''){
			        			$("#lastName").val(json.lastName);
					        	$("#lastName").attr("readonly", "readonly");
			        		}else{
			        			 $("#lastName").removeAttr("readonly");
			        			 $("#lastName").val('');
			        		}
			        		
			        		
		        		}else{
		        			 $("#firstName").removeAttr("readonly");
		        			 $("#firstName").val('');
		        			 
		        			 $("#lastName").removeAttr("readonly");
		        			 $("#lastName").val('');
		        		}
		        		
		        	}
		        },
		    });
	 }else{
		 $("#FirstName").attr("disabled", false);
		 $("#FirstName").val('');
		 
		 $("#lastName").attr("disabled", false);
		 $("#lastName").val('');
		 
		
	 }
 }
 </script>     
 
 
 <script type="text/javascript">
 function validateUserEmail() {
	 var emailId = document.getElementById("userEmail").value; 
	 var orgLocationId = '${registeredOrgLocationId}';
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(emailId != null && emailId != ""){
		 $("#add_user").attr("disabled", "disabled");
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateRegisteredUserEmail?${_csrf.parameterName}=${_csrf.token}&emailId="+emailId+"&orgLocationId="+orgLocationId, 
		        success: function(data) {
		        	if(data == 'true'){
		        		  $('#userEmailDiv').find('p.jquery_form_error_message').remove();
						  $("input#userEmail").after("<p class='jquery_form_error_message'>"+emailId+" is Already Existed</span>");
						  document.getElementById("EmailId").setAttribute('record-exist','yes');
						  document.getElementById("EmailId").setAttribute('record-exist-errorMsg',emailId+' is Already Existed'); 
		        	}
		        	else{
		        		 $('input#userEmail').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#userEmailDiv').find('p.jquery_form_error_message').remove(); 
                         $("#add_user").removeAttr("disabled");
		        	}
		        },
		    });
	 }
 }
 </script> 
 



<script type="text/javascript">
	function flushUser() {
		$("#add_user").removeAttr("disabled");
		$('#firstName').val('');
		$('#lastName').val('');
		$('#userEmail').val('');
		$('#userRole').val('');
		$(".error").removeClass("error");
		$('#firstNameDiv').find('p.jquery_form_error_message').remove();
		$('#lastNameDiv').find('p.jquery_form_error_message').remove();
		$('#userEmailDiv').find('p.jquery_form_error_message').remove();
		$('#userRoleDiv').find('p.jquery_form_error_message').remove();
		
	}
</script>


<script>
function removeAddUserPopUp(){
	 document.getElementById("addNewUserPopup").removeAttribute("class")
	 document.getElementById('addNewUserPopup').className = 'collapse-div-adduser orgsummaryuser-pop orgsummary-adduser col-md-4'
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
		 
		 var frm = $('#editPrimaryUser_form').serializeFormJSON();
         var con = JSON.stringify(frm);
         con = con.replace(/[{}]/g, "");
         var jsonData = encodeURIComponent(con);
         var sourceleadUrl ='${sourceleadUrl}';
         var userId = '${orgloc.user.id}';
         var orgId = '${orgId}';
         
         var orgLocationId = '${registeredOrgLocationId}'
         
        $("#editPrimaryUserLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
          
          $.ajax({
             type: "POST",
             url: sourceleadUrl+"/editOrgPrimaryUser?${_csrf.parameterName}=${_csrf.token}&userId="+userId+"&orgLocationId="+orgLocationId+"&json="+jsonData,
             success: function(data) {
                 if(data == "success"){
                	 location.replace(sourceleadUrl+"/enrolledOrgSummary?orgId="+orgId);
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

<script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#adduser_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		    e.preventDefault(); 
	          		
        		    var frm = $('#adduser_form').serializeFormJSON();
        	        var con = JSON.stringify(frm);
        	        con = con.replace(/[{}]/g, "");
        	        
        	        //It will escape all the special characters
        	        var jsonData = encodeURIComponent(con);
				    
	          		var sourceleadUrl ='${sourceleadUrl}';
	          		
	          		 $("#add_user").attr("disabled", "disabled");
	          		 $("#userLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	          		
	          	      $.ajax({
	     		    	type: "POST",
	     		    	 url: sourceleadUrl+"/createUesrBySourceleadAdmin?${_csrf.parameterName}=${_csrf.token}&json="+jsonData, 
	     		        success: function(data) {
	     		        	if(data != null && data != '' && data == 'success'){
	     		        		$("#userLoading").removeAttr("style");
	     	   	        		getUserList();
	     		        	}
	     			        
	     		        },
	     		    }); 
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
         
         $('body').on('blur', '#adduser_form', function() {
     		$("#adduser_form").showHelpOnFocus();
     		$("#adduser_form").validateOnBlur(false, validationSettings);
     	});
      
</script> 

<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
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

<script>
function getUserList(){  
	 flushUser(); 
	 removeAddUserPopUp();
	  var sourceleadUrl ='${sourceleadUrl}';
      var orgId = '${orgId}';
      var registeredOrgLocationId = '${registeredOrgLocationId}';
	  
	  $("#loading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
		  type : "POST",
		  url: sourceleadUrl+"/getOrgRegisteredUsers?${_csrf.parameterName}=${_csrf.token}&registeredOrgLocationId="+registeredOrgLocationId,      
            success :function(data) {
                 if(data != null && data != 'undefined' && data != "" && data != '[]'){
			    	     $("#userList").removeAttr("style");
	                	 $("#userList").empty();
	                	 var result = "";
	                	 result = result +"<table id='example' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"<tr>"; 
	                     result = result +'<th>Name</th>';
                		 result = result +'<th>Email</th>';
                		 result = result +'<th>Phone</th>';
                		 result = result +'<th>Role</th>';
                		 result = result +'<th>Status</th>';
                		 result = result +'<th>Action</th>';
	                     result = result +"</tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     var json = JSON.parse(data);
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i]; 
	                    	 var mobilePhone = '';
	                    	 var checked = '';
	                    	 var name = '';
	                    	 
                    		if(opt.isPrimaryAdmin == 'Y'){
    							checked = "checked"+" "+"value = 'Y'"+" "+"disabled = 'disabled'";
    						}else{
    							checked = "value = 'N'";
    						}
	                    	 
	                    	 
	                    	 if(null != opt.mobilePhone){
	                    		 mobilePhone = opt.mobilePhone;
	                    	 }else{
	                    		 mobilePhone = '------'
	                    	 }
	                    	 
	                    	 if(opt.lastName && opt.lastName != '' && opt.lastName != 'undefined'){
	                    		 name = opt.firstName +" "+ opt.lastName;
	                    	 }
	                    	 else{
	                    		 name = opt.firstName;
	                    	 }
	                    	 
	                    	 result = result +'<tr>';
	                    	 result = result +'<td><div class="tbldata-dotdot-me" title='+name+'>'+name+'</div></td>';
	                    	 result = result +'<td><div class="tbldata-dotdot-me" title='+opt.email+'>'+opt.email+'</div></td>';
	                    	 result = result +'<td><div class="tbldata-dotdot-100" title='+mobilePhone+'>'+mobilePhone+'</div></td>';
	                    	 result = result +'<td>'+opt.role+'</td>';
	                    	 
	                    	 if(opt.userState == 'NOT_LOGGEDIN'){
				 			    	 result = result +"<td><i class='fa fa-envelope' aria-hidden='true'></i><a href='javascript:void(0)' onClick='resendInvitation("+opt.id+");' >&nbsp;&nbsp;Resend</a></td>";
			 			      }else if(opt.userState == 'ACTIVE'){
			 			    	  result = result +'<td>Active</td>';
				 			    }else if(opt.userState == 'INACTIVE'){
				 			    	result = result +'<td>In-active</td>';
				 			    } 
	                    	 
	                    	 if(opt.userState == 'ACTIVE' && opt.role == 'Admin'){
		                    	 result = result +'<td>'
			                    	 result = result +'<div class="col-md-7 orgrole-switch">';
			                    	 result = result +'<label class="switch">';
			                    	 result = result +'<input type="checkbox" onclick="checkUser('+opt.id+',\''+name+'\')"  name="check'+opt.id+'"  id="check'+opt.id+'" '+checked+'  >';
			                    	 result = result +'<div class="slider round" title="set as primary contact"></div>';
			                    	 result = result +'</label>';
			                    	 result = result +'</div>';
		                    	
		                         result = result +'</td>'; 
	                    	 }else{
	                    		 result = result +'<td>-----</td>';
	                    	 }
	                    	 
	                    	 result = result +'</tr>';
	                    	
                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#userList").append(result); 
	                     $("#loading").removeAttr("style");
	                     pagination(); 
                 }else{
                	 $("#loading").removeAttr("style");
  	    	         var result ="";
		    	    
					 result = result+"			<div class='blank-pages'>";
					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"			        <p>No User Found</p>";
					 result = result+"			 </div>";
					
					$("#userList").empty();
				    $("#userList").append(result); 
                }
          },
            
       }); 
  } 
</script>


<script>
function checkUser(userId,name){
	var checked = document.getElementById("check"+userId).checked
	if(checked == true){
		alertMessage("Do you want to make "+name+" as admin?",userId);
	}
}
</script>

<script type="text/javascript">
function cancel(userId){
	$("#choicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
	document.getElementById("check"+userId).checked = false;
}
</script>

<script type="text/javascript">
function alertMessage(message,userId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='makeAsPrimaryAdmin("+userId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel("+userId+");'></b>";
	result = result +"	</div>";
	$("#choicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script>
function makeAsPrimaryAdmin(userId){
	$("#choicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
	
	var orgLocationId = '${registeredOrgLocationId}';
	var orgId = '${orgId}';
	
	$("#loading").attr('style','position:absolute; width:100%; height:450px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	var sourceleadUrl ='${sourceleadUrl}';
	if(userId != null && userId != "" && orgLocationId != null && orgLocationId != ""){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/changePrimaryAdmin?${_csrf.parameterName}=${_csrf.token}&userId="+userId+"&orgLocationId="+orgLocationId, 
		        success: function(result) {
		        	location.replace(sourceleadUrl+"/enrolledOrgSummary?orgId="+orgId);
		        },
		    });
	 }
	
}
</script>



<script type="text/javascript">
function resendInvitation(userId){
	var sourceleadUrl ='${sourceleadUrl}';
	var orgId = '${orgId}';
	if(userId != null && userId != ""){
			 $.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/resendInvitationEmail?${_csrf.parameterName}=${_csrf.token}&userId="+userId+"&orgId="+orgId, 
			        success: function(result) {
			        	
			        	document.getElementById("mailSendSuccessMsg").style.display = 'block';
			         	$("#mailSendSuccessMsg").fadeIn(150);
			        	$('#mailSendSuccessMsg').delay(1000).fadeOut('slow'); 
			        },
			    });
		 }
	}
  
</script>


<script>
function pagination(){
    $("#example").DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>




<script type="text/javascript">
 function getRegisteredOrgRoles() {
	 var sourceleadUrl ='${sourceleadUrl}';
	 var orgLocationId = '${registeredOrgLocationId}';
	 
	 var orgId = '${orgId}';
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getRegisteredOrgRoles?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	output = output + "<option value=''>-- Select Role --</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.displayName+"</option>";
					}
		        	$("#userRole").empty();
			        $("#userRole").append(output);
			        $('#userRole').trigger("chosen:updated");
		        },
		    });
	
 }
</script>


<script type="text/javascript">
	function updateModules() {
		
		var roleId = '${roleId}';
		var sourceleadUrl = '${sourceleadUrl}';
			var sourceleadUrl ='${sourceleadUrl}'; 
			var orgId = '${orgId}';
			var orgLocationId = '${registeredOrgLocationId}';
			
			 var modulesList = [];
			 $("input[name='modulesList[]']:checked").each( function (i) {
				modulesList[i] = $(this).val();
			});
			 
			
			 var submodulesList = [];
			 $("input[name='submodulesList[]']:checked").each( function (i) {
				submodulesList[i] = $(this).val();
			});
			 
		 
			 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.ajax({ 
		    	type: "POST",
		    	 url: sourceleadUrl+"/updateOrgModules?${_csrf.parameterName}=${_csrf.token}&modulesList="+modulesList+"&submodulesList="+submodulesList+"&orgLocationId="+orgLocationId,
		        success: function(result) {
		        	if((result == 'success')){
		        		location.replace(sourceleadUrl+"/enrolledOrgSummary?orgId="+orgId);
			        } 
		        },
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
 		</script> 

 		
<script>
$(".addcon-pop").click(function(){
	 $(".collapse-div-adduser").toggleClass("show-div");
	 $(".collapse-div").removeClass("show-div");
 });
 $(".close-div").click(function(){
	 $(this).parent().removeClass("show-div");
	 });
 $(".addcon-pop, .adddoc-pop, .collapse-div-adduser, .collapse-div").click(function(e){
	 e.stopPropagation();
 });
</script>
	



<jsp:include page="../masterFooter.jsp" />