<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="/resources/js/jquery.maskedinput-1.3.min.js?staticTime=${staticTime}"></script>

<jsp:include page="userProjectSummary.jsp" />

<style>
.user_summary_edit_cta p{
    margin: -5px 15px;
}
.remove_user_pop input{
	color: #333;
}
#editUserDiv .user_summary_edit_cta {
min-height: 65px;
}
.edit_role_pop.editrole-custom {
height: 450px; overflow-y: auto;
}
.assignroles.changeRole {
max-height: inherit !important;
}

</style>

<div class="wraper">
	<!-- Content Area -->
	<section class="content-wraper">
		<section class="content-header clearfix">
			<div class="pull-left">
				<h2>User Summary</h2>
			</div>
			<div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="${appPath}/usersList">Users</a> &raquo; User Summary <span id = "fname"></a> </div>				
		</section>

		<!-- Timesheet -->


		<script>
	function predefineProfileImage(imageId){
		$(imageId).attr('src','/resources/images/userprofile-img.png');
	}
</script>

<script> 
$(document).ready(function(){
	 var userId = '${userId}';
	 
	 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	 // written common method in slead-common.js for date formats start 
	 var finalDateFormat = getJSDateFormat(locationDate);
	 getUserSummaryDetails(userId);
	 $('body').on('focus', '#DateofJoin', function() {
		 $('#DateofJoin').datepicker({
			 dateFormat: finalDateFormat,
			 changeMonth: true,
			 changeYear: true, 
			});
	 });
	 
	 
});
</script>
		
		<div id="projectSideDiv"></div>
		<div id="candidateSideDiv"></div> 
		
		
		 

<script>
 function getUserSummaryDetails(userId){
	 
	 var result = "";
	 var userState = '${userState}';
	 var candidateId = '${candidateId}';
	 var timesheet = '${SourceLeadWeb_timesheet}';
	 var loginUserRole = '${SourceLeadWeb_loginUserRole}'; 
	 
	 
		 result = result + ' <div class="user_summ_wrap">';
		 result = result + '<div class="project_side_wrap_inn_tabs">';
		 result  = result + '<div class="success_pop" id="selectoruser" style="display: none;">';
		 result  = result + '<i class="fa fa-check-circle"></i><span id="userMsgDiv"></span>';
		 result  = result + '</div>';
		 result = result + '<ul class="project-tabs-menu">';
		 
		 result = result + '<li class="current"><a href="#tab-18" onClick="userDetails('+userId+');" >Details</a></li>';
		 result = result + '<li><a href="#tab-19" onClick="userAssignedProjects('+userId+')" >Projects</a></li>';
		 result = result + '<li><a href="#tab-17" onClick="profileDetails('+candidateId+');" >Profile</a></li>';
		
		 result = result + '<div class="clearfix"></div>';
		 result = result + '   </ul>';
		 
		 
		 result = result + '<div class="project_tabs">';
		 result = result + ' <div id="tab-18" class="project_tabs_inn">';
		 result = result + '</div>';
		  	
		 result = result + '<div id="tab-19" class="project_tabs_inn project_tabs_inn_table datatable_filter dataTablesFilter-relative">';
		 result = result + '</div>';
		 
		 result = result + '<div id="tab-17" class="project_tabs_inn project_tabs_inn_table">';
		 result = result + '<div id="candidateSideDiv">';
		 result = result + '</div>';
		 result = result + '</div>';
	  	 result = result + '</div>';
	  	 result = result + '</div>';
	  	 result = result + '</div>'; 
	  	 $("#projectSideDiv").empty(); 
	     $("#projectSideDiv").append(result); 
	     $("#candidateSideDiv").empty();
	     $("#candidateSideDiv").append(result);
	    
	  	
	  	userDetails(userId);
 }
</script>
<script>
	function userMsgFadein() {
		$("#selectoruser").fadeIn(200);
        $('#selectoruser').delay(2000).fadeOut('slow');
	}
</script> 

<script>
 function userDetails(userId){    
	
	 var isTimeSheetEnrolled = '${SourceLeadWeb_timesheet}';
	 
	 if(isTimeSheetEnrolled == 'Y'){
		 var projectsCount = '${assignedProjectsCount}';
		 var totalOpenTimeSheetCount = '${totalOpenTimeSheetCount}';
		 var approvedTimeSheetsCount = '${approvedTimeSheetsCount}';
		 var rejectedTimeSheetsCount = '${rejectedTimeSheetsCount}';
		 var waitingTimeSheetsCount = '${waitingTimeSheetsCount}';
	 }
	
	 
	 $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getUserDetailsById?"+tokenParamName+"="+tokenValue+"&userId="+userId, 
	        success: function(data) {
			    var json = JSON.parse(data);
			    var result = "";
			    var submissionCountsResult = "";
			    
			    var reportingManagers = "";
			    var clientOwnerAndSpoc = "";
			    var vendorOwnerAndSpoc = "";
			    var managerUsers = "";
			    var approversUsers = "";
			    var teamOwnerUsers = "";
			    var projectApproverUsers = "";
			    var clientDocumentsOwnerUsers = "";
			    var vendorDocumentsOwnerUsers = "";
			    var roles = "";
			    
			    var socialLinks = "";
			    if(json.user.linkedIn != null && json.user.linkedIn != "" && json.user.linkedIn != 'undefined' ){
			    	socialLinks =  socialLinks + '&nbsp;&nbsp;<a href='+json.user.linkedIn+' title="linkedIn" target="_new"><i class="fa fa-linkedin-square text-primary fa-2x"></i></a>';
			    }
			    
			    if(json.user.facebook != null && json.user.facebook != "" && json.user.facebook != 'undefined' ){
			    	socialLinks =  socialLinks +  '&nbsp;&nbsp;<a href='+json.user.facebook+' title="facebook" target="_new"><i class="fa fa-facebook-square text-info fa-2x"></i></a>';
			    }
			    
			    if(json.user.twitter != null && json.user.twitter != "" && json.user.twitter != 'undefined' ){
			    	socialLinks =  socialLinks +  '&nbsp;&nbsp;<a href='+json.user.twitter+' title="twitter" target="_new"><i class="fa fa-twitter-square text-info fa-2x"></i></a>';
			    }
			    
			    if(json.user.googlePlus != null && json.user.googlePlus != "" && json.user.googlePlus != 'undefined' ){
			    	socialLinks =  socialLinks +  '&nbsp;&nbsp;<a href='+json.user.googlePlus+' title="google+" target="_new"><i class="fa fa-google-plus-square text-danger fa-2x"></i></a>';
			    }
			    
			    if(isTimeSheetEnrolled == 'Y'){
			    	
			    	    submissionCountsResult = '<p>Projects<strong class="label label-default">'+projectsCount+'</strong></p>';
		            	submissionCountsResult = submissionCountsResult +'<p>Approved Timesheets<strong class="label label-success">'+approvedTimeSheetsCount+'</strong></p>';
		            	submissionCountsResult = submissionCountsResult +'<p>Rejected Timesheets<strong class="label label-warning">'+rejectedTimeSheetsCount+'</strong></p>';
		            	submissionCountsResult = submissionCountsResult +'<p>Waiting for Approval<strong class="label label-primary">'+waitingTimeSheetsCount+'</strong></p>';
		            	submissionCountsResult = submissionCountsResult +'<p>Open Timesheets<strong class="label label-info">'+totalOpenTimeSheetCount+'</strong></p>';
					    
			    }
			    
			    var userName = '';
			    if(json.user.lastName != null && json.user.lastName != '' && json.user.lastName != 'undefined'){
			    	userName = json.user.firstName+" "+json.user.lastName;
			    }else{
			    	userName = json.user.firstName
			    }
			   
			   
				result = result + '<div class="project_tabs_inn_table">';
				
				result = result + '  <div class="project_details">';
				if(json.userState != null && json.userState != "" && json.userState != 'INACTIVE'){
				    result = result + ' <div class="useredit">';
				    '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_USER\')">'
				    result = result + ' <a id="edit-userRole" onclick="getRoles('+json.roles.id+');removeErrorMsg();" href="javascript:editUserRole();"><i class="fa fa-edit"></i>Edit Role</a>';
				    '</sec:authorize>';
				    
				    '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_USER\')">'
				        result = result + ' <a id="edit-user" href="javascript:editUserDetails('+userId+');"><i class="glyphicon glyphicon-pencil"></i>Edit User</a>';
				    '</sec:authorize>';
				      
				    '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_DELETE_USER\')">'
						result = result + ' <a id="remove-user" href="javascript:removeUser('+userId+');"><i class="glyphicon glyphicon-trash"></i>Terminate</a>';
						result = result + ' <form id="deleteUser_form"  name="deleteUser_form" method="post">';	
						result = result + ' <div id="remove_user_id" class="remove_user_pop terminate-popup" >';
									
									result = result + '<div class="clearfix assignroles">';
									result = result + ' <div id="deleteLoading"></div>';
									result = result + ' <div id="removeUserList"></div>';
									result = result + '<div class="clearfix"></div>';
									result = result + '</div>';	
									result = result + ' </div>';
									result = result + ' </form>';
				    '</sec:authorize>';	     
				     result = result + ' </div>';
				     
				     
				     result = result + ' <div id="edit_role_pop" class="edit_role_pop editrole-custom" >';
				     result = result + ' <div id="editRoleLoading" ></div>';
				     result = result + ' <div class="user_summary_edit_cta">';
				     result = result + ' <form id="editRole_form"  name="editRole_form" method="post">';
				     result = result + ' <label>Change Role</label>';
						 result = result + '<select class="chosen-select" id="userChangeRole"  onchange="getChangeRole('+userId+','+json.roles.id+');" data-validation="required" field-name="Role" name="userChangeRole"  >';
						 result = result + '</select>';
						
					     result = result + ' <div id="editRoleDetailsList"></div>';
						 result = result + ' </div>';
						 
						result = result + ' </form>';
					 result = result + ' </div>';
				}
				
				else{
					'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_DELETE_USER\',\'ROLE_CREATE_USER\')">'
                         result = result + '<i class="glyphicon glyphicon-user"></i><a id="edit-user" href="javascript:reActiveUser('+userId+');">&nbsp;Re-Active</a>';
                    '</sec:authorize>';	 
				}
				
				result = result + '	 <div class="user_details_cta_img">';
				result = result + '		       <div class="user_change_img">';
				if(json.user.fileUrl != null && json.user.fileUrl != "" && json.user.fileUrl !="undefiend"){
					result = result +                  "<img data-id= 'userProfileImage' src='"+json.user.fileUrl+"' alt='image'>";
				}else{
					result = result +                  "<img data-id= 'userProfileImage'  src='/resources/images/userprofile-img.png' alt='image'>";
				}
				
				result = result + ' 		  </div>';
				result = result + '             <span>'+userName+'</span>';
				result = result + ' <div class="user_badg" id="counts">';
				result = result +socialLinks;
				result = result +submissionCountsResult;
				result = result + '	</div>';
				result = result + '		 </div>';
				
				result = result + '	     <div class="user_details_cta">';
				result = result + '	     <div id="viewUserDiv">';
				if(json.employeeId != null && json.employeeId != "" && json.employeeId != 'undefined'){
					result = result + ' 		<div class="project_details_cta">';
					result = result + '				 <strong>Employee Id:</strong><span class="fix" title="'+json.employeeId+'">'+json.employeeId+'</span><div class="clearfix"></div>';
					result = result + ' 		</div>';
				}
				
				
				result = result + '			<div class="project_details_cta">';
				result = result + '				 <strong>First Name:</strong><span class="fix" title="'+json.user.firstName+'">'+json.user.firstName+'</span><div class="clearfix"></div>';
				result = result + ' 		</div>';
				
					
				
				
				if(json.user.lastName != null && json.user.lastName != "" && json.user.lastName != 'undefined'){
					result = result + ' 		<div class="project_details_cta">';
					result = result + '				 <strong>Last Name:</strong><span class="fix" title="'+json.user.lastName+'">'+json.user.lastName+'</span><div class="clearfix"></div>';
					result = result + ' 		</div>';
				}
				
				if(json.user.email != null && json.user.email != "" && json.user.email != 'undefined'){
					result = result + '			<div class="project_details_cta">';
					result = result + '				 <strong>Email Id:</strong><span class="fix" title="'+json.user.email+'">'+json.user.email+'</span><div class="clearfix"></div>';
					result = result + ' 		</div>';
				}
				
				if(json.user.mobilePhone != null && json.user.mobilePhone != "" && json.user.mobilePhone != 'undefined'){
					result = result + '			<div class="project_details_cta">';
					result = result + '				 <strong>Phone Number:</strong><span class="fix">'+json.user.mobilePhone+'</span><div class="clearfix"></div>';
					result = result + ' 		</div>';
				}
				
				
				if(json.user.extensionNumber != null && json.user.extensionNumber !='' && json.user.extensionNumber !='undefined'){
					
					result = result + '			<div class="project_details_cta">';
					result = result + '				 <strong>Extension:</strong><span class="fix">'+json.user.extensionNumber+'</span><div class="clearfix"></div>';
					result = result + ' 		</div>';
				}
				
				 if(json.userEmploymentType != null && json.userEmploymentType != "" && json.userEmploymentType != 'undefined'){
					result = result + '			<div class="project_details_cta">';
					result = result + '				<strong>Employment Type:</strong><span class="fix">'+json.userEmploymentType+'</span><div class="clearfix"></div>';
					result = result + '			</div>';
				} 
				 
				 if(json.vendorName != null && json.vendorName != "" && json.vendorName != 'undefined'){
						result = result + '			<div class="project_details_cta">';
						result = result + '				<strong>Vendor:</strong><span class="fix">'+json.vendorName+'</span><div class="clearfix"></div>';
						result = result + '			</div>';
					} 
				 
				 if(json.designationOtherType != null && json.designationOtherType != "" && json.designationOtherType != 'undefined'){
					 result = result + '			<div class="project_details_cta">';
						result = result + '				<strong>Employment Other Type:</strong><span class="fix">'+json.designationOtherType+'</span><div class="clearfix"></div>';
						result = result + '			</div>';
				 }
				 
				 if(json.vendorContactName != null && json.vendorContactName != "" && json.vendorContactName != 'undefined'){
						result = result + '			<div class="project_details_cta">';
						result = result + '				<strong>Vendor Contact:</strong><span class="fix">'+json.vendorContactName+'</span><div class="clearfix"></div>';
						result = result + '			</div>';
					} 
				
				 if(json.currentCurrency != null && json.currentCurrency != "" && json.currentCurrency != 'undefined'){
						result = result + '			<div class="project_details_cta">';
						result = result + '				 <strong>Currency:</strong><span class="fix" title="'+json.currentCurrency+'">'+json.currentCurrency+'</span><div class="clearfix"></div>';
						result = result + ' 		</div>';
					}
				 
				if(json.rate != null && json.rate != "" && json.rate != 'undefined'){
					result = result + '			<div class="project_details_cta">';
					result = result + '				 <strong>Rate:</strong><span class="fix" title="'+json.rate+'">'+json.rate+'</span><div class="clearfix"></div>';
					result = result + ' 		</div>';
				}
				
				if(json.ratePer != null && json.ratePer != "" && json.ratePer != 'undefined'){
					result = result + ' 		<div class="project_details_cta">';
					result = result + '				 <strong>Rate Per:</strong><span class="fix">'+json.ratePer+'</span><div class="clearfix"></div>';
					result = result + '			</div>';
				}
				
				result = result + '			<div class="project_details_cta">';
				result = result + '					<strong>Role:</strong><span class="fix">'+json.roles.displayName+'</span><div class="clearfix"></div>';
				result = result + '			</div>';
				
				if(json.reportingManager != null && json.reportingManager != "" && json.reportingManager != 'undefined' && json.reportingManager.firstName != null && json.reportingManager.firstName != "" && json.reportingManager.firstName != 'undefined'){
					result = result + '			<div class="project_details_cta">';
					result = result + '				 <strong>Manager:</strong><span class="fix">'+json.reportingManager.firstName+' '+json.reportingManager.lastName+'</span><div class="clearfix"></div>';
					result = result + ' 		</div>';
				}
				
				
				/* if(json.InvoiceFrequency != null && json.InvoiceFrequency != "" && json.InvoiceFrequency != 'undefined'){
					result = result + ' 		<div class="project_details_cta">';
					result = result + '				<strong>Invoice Frequency:</strong><span class="fix">'+json.InvoiceFrequency+'</span><div class="clearfix"></div>';
					result = result + '			</div>';
				} */
				
				if(json.hireDateStr != null && json.hireDateStr != "" && json.hireDateStr != 'undefined'){
					result = result + '			<div class="project_details_cta">';
					result = result + '				<strong>Date of joining:</strong><span class="fix">'+json.hireDateStr+'</span><div class="clearfix"></div>';
					result = result + '			</div>';
				}
				
				if(json.dailyHours != null && json.dailyHours != "" && json.dailyHours != 'undefined'){
					result = result + '			<div class="project_details_cta">';
					result = result + '				<strong>Day hours:</strong><span class="fix">'+json.dailyHours+'</span><div class="clearfix"></div>';
					result = result + '			</div>';
				}
				
				if(json.roleChangeReason != null && json.roleChangeReason != "" && json.roleChangeReason != 'undefined'){
					result = result + ' 		<div class="project_details_cta">';
					result = result + '				<strong>Role Change Reason:</strong><span class="fix" title="'+json.roleChangeReason+'">'+json.roleChangeReason+'</span><div class="clearfix"></div>';
					result = result + '			</div>';
				}
				
				if(json.userState != null && json.userState != "" && json.userState == 'INACTIVE'){
					result = result + '			<div class="project_details_cta">';
					result = result + '				<strong>Reason:</strong><span class="fix" title = "'+json.terminateReason+'">'+json.terminateReason+'</span><div class="clearfix"></div>';
					result = result + '			</div>';
				}
				
				 if(json.departmentName != null && json.departmentName != "" && json.departmentName != 'undefined'){
					result = result + '			<div class="project_details_cta">';
					result = result + '				 <strong>Department Name:</strong><span class="fix" title="'+json.departmentName+'">'+json.departmentName+'</span><div class="clearfix"></div>';
					result = result + ' 		</div>';
				} 
				 
				 if(json.userCustomFieldlabelNameAndValue != null && json.userCustomFieldlabelNameAndValue != "" && json.userCustomFieldlabelNameAndValue != 'undefined'){
						for (var key in json.userCustomFieldlabelNameAndValue) {
							  if (json.userCustomFieldlabelNameAndValue.hasOwnProperty(key)) {
								result = result + '	<div class="project_details_cta">';
								var value = json.userCustomFieldlabelNameAndValue[key];  
								var valueArray = value.toString().split(",");
							    result = result + '	<strong>'+valueArray[0]+':</strong><span class="fix">'+valueArray[1]+'</span><div class="clearfix"></div>';
							    result = result + '	</div>';
							  }
							}
					}
				 
				result = result + '	   </div>';				
				result = result + '	  <div class="clearfix"></div>';
				result = result + '	   </div>';
				result = result + ' <div class="user_summary_edit user_summaryEdit" id="editUserDiv"></div>';
				result = result + '	  <div class="clearfix"></div>';
				result = result + '	</div>';
				result = result + '</div>';
				$("#candidateSideDiv").empty();
				$("#tab-19").empty();
				$("#tab-17").empty();
				$("#tab-18").empty(); 
			 
			    $("#tab-18").append(result);  
			    
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
	    
	      },
	});
 }
</script>

<script type="text/javascript">
function removeErrorMsg(){
	$("editRole_form").validate().resetForm();
}
</script>


<!--  Script for First Name displaying right corner  -->
<script type="text/javascript">

var Name = document.getElementById('fname');
function firstname(name)
{
	var lengthName = name.length;
		if(lengthName > 10)
		{
		var startlength = 0;
		var endlength = 10;
		var trimname = name.substring(startlength, endlength) + "..." ;
		Name.innerHTML = trimname;
		}else{
			Name.innerHTML = name;	
		}
	
}
</script>

<script>
function getRoles(roleId){
	 if(roleId != null && roleId != "" && roleId != 'undefined'){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getRolesExpectSelectedRole?"+tokenParamName+"="+tokenValue+"&roleId="+roleId, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	output = output + "<option value=''>-- Select Role --</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.displayName+"</option>";
					}
		        	$("#userChangeRole").empty();
			        $("#userChangeRole").append(output);
			        $('#userChangeRole').trigger("chosen:updated");
		        },
		    });
	}
}
</script>

<script>
function getChangeRole(userId,userRoleId){
	var changeRoleId = document.getElementById("userChangeRole").value; 
	var result = "";
				
	if(changeRoleId){
	 $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getUserPermissionByRole?"+tokenParamName+"="+tokenValue+"&userId="+userId+"&userRoleId="+userRoleId+"&changeRoleId="+changeRoleId, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	var reportingManagers = "";
			    var clientOwnerAndSpoc = "";
			    var vendorOwnerAndSpoc = "";
			    var managerUsers = "";
			    var approversUsers = "";
			    var teamOwnerUsers = "";
			    var projectApproverUsers = "";
			    var clientDocumentsOwnerUsers = "";
			    var vendorDocumentsOwnerUsers = "";
			    
	        	
        	 result = result + '<div class="clearfix assignroles changeRole">';
	        	if(json.assignedUsersCountToReportingManager > 0){
					result = result + '<div class="clearfix mb10">';
					result = result + '<div class="col-md-6">';
					    result = result + '<div class="user_badg">';
						    result = result + '<p>Assigned Users as a Manager<strong class="label label-default">'+json.assignedUsersCountToReportingManager+'</strong></p>';
								result = result + '</div>';
				            result = result + '</div>';
						result = result + '<div class="col-md-6" id="changeReportingManagerDiv">';
						if(json.reportingManagers != null && json.reportingManagers != "" && json.reportingManagers != 'undefined' && json.reportingManagers.length > 0){
							result = result + '<select class="chosen-select" id="assignReportingManagers" data-validation="required" field-name="Assign To" name="assignReportingManagers"  >';
							    result = result + '<option value="">Assign to</option>';
							    for (var i=0; i<json.reportingManagers.length; i++)
		    					{ 
							    	reportingManagers = json.reportingManagers[i];
							    	var fullName = "";
							    	if(reportingManagers.lastName != null){
							    		fullName = reportingManagers.firstName+" "+reportingManagers.lastName;
							    	}else{
							    		fullName = reportingManagers.firstName;
							    	}
							    	
							    	result = result+"<option value='"+reportingManagers.id+"'>"+fullName+"&nbsp;("+reportingManagers.userType+")</option>"; 
									
		    					}
							    result = result + '</select>';
						}
						result = result + '</div>';
						result = result + '</div>';	
					  }
	        	
	        	
	        	if(json.assignedAsClientOwnerOrSpocCount > 0){
					result = result + '<div class="clearfix mb10">';
					result = result + '<div class="col-md-6">';
					    result = result + '<div class="user_badg">';
						    result = result + '<p>Assigned as Client Owner and Spoc<strong class="label label-info">'+json.assignedAsClientOwnerOrSpocCount+'</strong></p>';
								result = result + '</div>';
				            result = result + '</div>';
						result = result + '<div class="col-md-6" id="clientUserDiv">';
						if(json.clientUsersList != null && json.clientUsersList != "" && json.clientUsersList != 'undefined' && json.clientUsersList.length > 0){
						       result = result + '<select class="chosen-select" data-validation="required" field-name="Assign To" id="clientUsers" name="clientUsers" >';
							    result = result + '<option value="">Assign to</option>';
							    for (var i=0; i<json.clientUsersList.length; i++)
		    					{
							    	clientOwnerAndSpoc = json.clientUsersList[i];
							    	var fullName = "";
							    	if(clientOwnerAndSpoc.lastName != null){
							    		fullName = clientOwnerAndSpoc.firstName+" "+clientOwnerAndSpoc.lastName;
							    	}else{
							    		fullName = clientOwnerAndSpoc.firstName;
							    	}
							    	result = result+"<option value='"+clientOwnerAndSpoc.id+"'>"+fullName+"&nbsp;("+clientOwnerAndSpoc.userType+")</option>"; 
									
		    					}
							    result = result + '</select>';
						}
						result = result + '</div>';
						result = result + '</div>';	
					  }	
				
				
				if(json.assignedAsVendorOwnerOrSpocCount > 0){
					result = result + '<div class="clearfix mb10">';
					result = result + '<div class="col-md-6">';
					    result = result + '<div class="user_badg">';
						    result = result + '<p>Assigned as Vendor Owner and Spoc<strong class="label label-primary">'+json.assignedAsVendorOwnerOrSpocCount+'</strong></p>';
								result = result + '</div>';
				            result = result + '</div>';
						result = result + '<div class="col-md-6" id="vendorUserDiv">';
						if(json.vendorUsersList != null && json.vendorUsersList != "" && json.vendorUsersList != 'undefined' && json.vendorUsersList.length > 0){
						       result = result + '<select class="chosen-select" data-validation="required" field-name="Assign To" id="vendorUsers" name="vendorUsers">';
							    result = result + '<option value="">Assign to</option>';
							    for (var i=0; i<json.vendorUsersList.length; i++)
		    					{
							    	vendorOwnerAndSpoc = json.vendorUsersList[i];
							    	var fullName = "";
							    	if(vendorOwnerAndSpoc.lastName != null){
							    		fullName = vendorOwnerAndSpoc.firstName+" "+vendorOwnerAndSpoc.lastName;
							    	}else{
							    		fullName = vendorOwnerAndSpoc.firstName;
							    	}
							    	result = result+"<option value='"+vendorOwnerAndSpoc.id+"'>"+fullName+"&nbsp;("+vendorOwnerAndSpoc.userType+")</option>"; 
									
		    					}
							    result = result + '</select>';
						}
						result = result + '</div>';
						result = result + '</div>';	
					  }	
				
				
				if(json.requirementsCountAsManager > 0){
					result = result + '<div class="clearfix mb10">';
					result = result + '<div class="col-md-6">';
					    result = result + '<div class="user_badg">';
						    result = result + '<p>Assigned as Manager to Jobs<strong class="label label-success">'+json.requirementsCountAsManager+'</strong></p>';
								result = result + '</div>';
				            result = result + '</div>';
						result = result + '<div class="col-md-6" id="managerUserDiv">';
						if(json.requirementManagersList != null && json.requirementManagersList != "" && json.requirementManagersList != 'undefined' && json.requirementManagersList.length > 0){
						       result = result + '<select class="chosen-select" data-validation="required" field-name="Assign To" id="managerUsers" name="managerUsers">';
							    result = result + '<option value="">Assign to</option>';
							    for (var i=0; i<json.requirementManagersList.length; i++)
		    					{
							    	managerUsers = json.requirementManagersList[i];
							    	var fullName = "";
							    	if(managerUsers.lastName != null){
							    		fullName = managerUsers.firstName+" "+managerUsers.lastName;
							    	}else{
							    		fullName = managerUsers.firstName;
							    	}
							    	result = result+"<option value='"+managerUsers.id+"'>"+fullName+"&nbsp;("+managerUsers.userType+")</option>"; 
									
		    					}
							    result = result + '</select>';
						}
						result = result + '</div>';
						result = result + '</div>';	
					  }	
					
				if(json.requirementsCountAsApprover > 0){
					result = result + '<div class="clearfix mb10">';
					result = result + '<div class="col-md-6">';
					    result = result + '<div class="user_badg">';
						    result = result + '<p>Assigned as Approver to Jobs<strong class="label label-warning">'+json.requirementsCountAsApprover+'</strong></p>';
								result = result + '</div>';
				            result = result + '</div>';
						result = result + '<div class="col-md-6" id="approverUserDiv">';
						if(json.requirementApproversList != null && json.requirementApproversList != "" && json.requirementApproversList != 'undefined' && json.requirementApproversList.length > 0){
						       result = result + '<select class="chosen-select" data-validation="required" field-name="Assign To" name="approverUser" id="approverUser">';
							    result = result + '<option value="">Assign to</option>';
							    for (var i=0; i<json.requirementApproversList.length; i++)
		    					{
							    	approversUsers = json.requirementApproversList[i];
							    	var fullName = "";
							    	if(approversUsers.lastName != null){
							    		fullName = approversUsers.firstName+" "+approversUsers.lastName;
							    	}else{
							    		fullName = approversUsers.firstName;
							    	}
							    	result = result+"<option value='"+approversUsers.id+"'>"+fullName+"&nbsp;("+approversUsers.userType+")</option>"; 
									
		    					}
							    result = result + '</select>';
						}
						result = result + '</div>';
						result = result + '</div>';	
					  }	
				
				
					
				if(json.teamsCountAsOwner > 0){
					result = result + '<div class="clearfix mb10">';
					result = result + '<div class="col-md-6">';
					    result = result + '<div class="user_badg">';
						    result = result + '<p>Assigned as Team Owner for Jobs<strong class="label label-info">'+json.teamsCountAsOwner+'</strong></p>';
								result = result + '</div>';
				            result = result + '</div>';
						result = result + '<div class="col-md-6" id="teamOwnerUserDiv">';
						if(json.teamOwnersList != null && json.teamOwnersList != "" && json.teamOwnersList != 'undefined' && json.teamOwnersList.length > 0){
						       result = result + '<select class="chosen-select" data-validation="required" field-name="Assign To" id="teamOwnerUser" name="teamOwnerUser">';
							    result = result + '<option value="">Assign to</option>';
							    for (var i=0; i<json.teamOwnersList.length; i++)
		    					{
							    	teamOwnerUsers = json.teamOwnersList[i];
							    	var fullName = "";
							    	if(teamOwnerUsers.lastName != null){
							    		fullName = teamOwnerUsers.firstName+" "+teamOwnerUsers.lastName;
							    	}else{
							    		fullName = teamOwnerUsers.firstName;
							    	}
							    	result = result+"<option value='"+teamOwnerUsers.id+"'>"+fullName+"&nbsp;("+teamOwnerUsers.userType+")</option>"; 
									
		    					}
							    result = result + '</select>';
						}
						result = result + '</div>';
						result = result + '</div>';	
					  }	
				
				
				
				if(json.projectApproverCount > 0){
					result = result + '<div class="clearfix mb10">';
					result = result + '<div class="col-md-6">';
					    result = result + '<div class="user_badg">';
						    result = result + '<p>Assigned as Project Approver<strong class="label label-success">'+json.projectApproverCount+'</strong></p>';
								result = result + '</div>';
				            result = result + '</div>';
						result = result + '<div class="col-md-6" id="teamOwnerUserDiv">';
						if(json.projectApproversList != null && json.projectApproversList != "" && json.projectApproversList != 'undefined' && json.projectApproversList.length > 0){
						       result = result + '<select class="chosen-select" data-validation="required" field-name="Assign To" id="projectApproverUser" name="projectApproverUser">';
							    result = result + '<option value="">Assign to</option>';
							    for (var i=0; i<json.projectApproversList.length; i++)
		    					{
							    	projectApproverUsers = json.projectApproversList[i];
							    	var fullName = "";
							    	if(projectApproverUsers.lastName != null){
							    		fullName = projectApproverUsers.firstName+" "+projectApproverUsers.lastName;
							    	}else{
							    		fullName = projectApproverUsers.firstName;
							    	}
							    	result = result+"<option value='"+projectApproverUsers.id+"'>"+fullName+"&nbsp;("+projectApproverUsers.userType+")</option>"; 
									
		    					}
							    result = result + '</select>';
						}
						result = result + '</div>';
						result = result + '</div>';	
					  }	
				
				
				
				if(json.clientDocumentsOwnerCount > 0){
					result = result + '<div class="clearfix mb10">';
					result = result + '<div class="col-md-6">';
					    result = result + '<div class="user_badg">';
						    result = result + '<p>Assigned as Client Documents Owner<strong class="label label-success">'+json.clientDocumentsOwnerCount+'</strong></p>';
								result = result + '</div>';
				            result = result + '</div>';
						result = result + '<div class="col-md-6" id="clientDocOwnerUserDiv">';
						if(json.clientDocumentsOwnerList != null && json.clientDocumentsOwnerList != "" && json.clientDocumentsOwnerList != 'undefined' && json.clientDocumentsOwnerList.length > 0){
						       result = result + '<select class="chosen-select" data-validation="required" field-name="Assign To" id="clientDocsOwnerUser" name="clientDocsOwnerUser">';
							    result = result + '<option value="">Assign to</option>';
							    for (var i=0; i<json.clientDocumentsOwnerList.length; i++)
		    					{
							    	clientDocumentsOwnerUsers = json.clientDocumentsOwnerList[i];
							    	var fullName = "";
							    	if(clientDocumentsOwnerUsers.lastName != null){
							    		fullName = clientDocumentsOwnerUsers.firstName+" "+clientDocumentsOwnerUsers.lastName;
							    	}else{
							    		fullName = clientDocumentsOwnerUsers.firstName;
							    	}
							    	result = result+"<option value='"+clientDocumentsOwnerUsers.id+"'>"+fullName+"&nbsp;("+clientDocumentsOwnerUsers.userType+")</option>"; 
									
		    					}
							    result = result + '</select>';
						}
						result = result + '</div>';
						result = result + '</div>';	
					  }
				
				
				
				if(json.vendorDocumentsOwnerCount > 0){
					result = result + '<div class="clearfix mb10">';
					result = result + '<div class="col-md-6">';
					    result = result + '<div class="user_badg">';
						    result = result + '<p>Assigned as Vendor Documents Owner<strong class="label label-success">'+json.vendorDocumentsOwnerCount+'</strong></p>';
								result = result + '</div>';
				            result = result + '</div>';
						result = result + '<div class="col-md-6" id="vendorDocOwnerUserDiv">';
						if(json.vendorDocumentsOwnerList != null && json.vendorDocumentsOwnerList != "" && json.vendorDocumentsOwnerList != 'undefined' && json.vendorDocumentsOwnerList.length > 0){
						       result = result + '<select class="chosen-select" data-validation="required" field-name="Assign To" id="vendorDocOwnerUsers" name="vendorDocsOwnerUser">';
							    result = result + '<option value="">Assign to</option>';
							    for (var i=0; i<json.vendorDocumentsOwnerList.length; i++)
		    					{
							    	vendorDocumentsOwnerUsers = json.vendorDocumentsOwnerList[i];
							    	var fullName = "";
							    	if(vendorDocumentsOwnerUsers.lastName != null){
							    		fullName = vendorDocumentsOwnerUsers.firstName+" "+vendorDocumentsOwnerUsers.lastName;
							    	}else{
							    		fullName = vendorDocumentsOwnerUsers.firstName;
							    	}
							    	result = result+"<option value='"+vendorDocumentsOwnerUsers.id+"'>"+fullName+"&nbsp;("+vendorDocumentsOwnerUsers.userType+")</option>"; 
									
		    					}
							    result = result + '</select>';
						}
						result = result + '</div>';
						result = result + '</div>';	
					  }
				
				
				
				    result = result + '<div class="col-md-12 mb10" id="reasonDiv">';
				    result = result + '<textarea rows="3" class="form-control input-sm" name="reason" id="reason" data-validation="required" field-name="Reason" placeholder="Change Reason"></textarea>';
				  	result = result + '</div><br/>';
				  	
				  	 result = result + '<div class="clearfix mb10 mt">';
					  result = result + '</div>';
				      result = result + '<div class="clearfix"></div>';
					  result = result + '<div class="user_summary_edit_cta clearfix pull-right">';
					  result = result + '<input class="us-btn" type="button" value="Update" type="button" onclick="updateRole('+userId+');"/>';
					  result = result + '&nbsp;<input class="us-btn us-btn_a" onclick="cancelEditRole();" type="button" value="Cancel"/>';
					  result = result + '</div>';
	        	
	        	result = result + '</div>';	
	        	
	        	$("#editRoleDetailsList").empty(); 
	    	    $("#editRoleDetailsList").append(result);  
	    	    
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
	        }
	 
	        
	 });
	 
	}else{
		
		$("#editRoleDetailsList").empty(); 
	}
		
	
	
}
</script>

<script>
 function getDepartmentsList(){
	 var updatedepartment = $("#updatedepartment").val();
	 var updateDepartmentOrgRelationId = $("#updateDepartmentOrgRelationId").val();
	 $.ajax({
	    	type: "POST",
	    	url: sourceleadUrl+"/getDepartmentsList?"+tokenParamName+"="+tokenValue+"",
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#department').append('<option value="">--select Department --</option>');
	        	for (var i=0; i<json.length; i++)
				{
	        		var opt = json[i];
	            	  if(updatedepartment != null && updatedepartment != 'undefined' && updatedepartment  != ''){
	            		  $('#department').append('<option '+ (opt.id == updateDepartmentOrgRelationId ? "selected" : '') +' value="'+opt.id+'">'+opt.departmentName+'</option>');
		            	}else{
		            		$('#department').append('<option value='+opt.id+' >'+opt.departmentName+'</option>');	
		            	}
	        		
				}
	        	
		        $('#department').trigger("chosen:updated");
		        
	        },
	    });
 }
 
 </script>  



	</section>
	<!-- Content Area Ends-->


</div>


<!-- Modal content Add Team-->
<div role="dialog" id="assignProject" class="modal fade">
	<div class="modal-dialog">
		<form:form id="assignProject_form" name="assignProject_form"
			method="post">
			<div class="modal-content">
				<div class="modal-header">
					<button data-dismiss="modal" class="close" type="button">X</button>
					<h4 class="modal-title">Assign Project</h4>
				</div>
				<div class="modal-body clearfix">
					<div class="form-horizontal custom-form clearfix">
						<div class="col-xs-12 col-md-12 col-lg-12" id="assignProjectDiv">
							<div class="form-group">
								<label class="col-lg-3">Select Project <span
									class="text-danger">*</span></label>
								<div class="col-lg-9">
									<!--  <div class="col-lg-9">
                              <select class="form-control">
                              	<option>Invoice-1</option>
                                <option>Invoice-2</option>
                                <option>Invoice-3</option>
                              </select>
                              </div> -->

									<select class="form-control" id="proId" name="projectId"
										field-name="Project Name" onchange="getStartandEndDate();"
										data-validation="required">
										<option value="">-Select Project-</option>
										<c:forEach var="option" items="${org_projectsList}">
											<option value="${option.id}">${option.projectName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-3">Start date</label>
								<div class="col-lg-9">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i></span> <input type="text"
											class="form-control" readonly="readonly"
											placeholder="DD/MM/YYYY" id="StartDate" name="StartDate"
											data-validation="required" field-name="Start Date">
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-3">End date</label>
								<div class="col-lg-9">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i></span> <input type="text"
											class="form-control" readonly="readonly"
											placeholder="DD/MM/YYYY" id="EndDate" name="EndDate" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button class="btn btn-primary" type="button"
						id="saveAssignProject" onclick="assignProject();">Select</button>
					<button data-dismiss="modal" class="btn btn-default"
						onclick="flushProject();" type="button">Cancel</button>
				</div>
			</div>
		</form:form>
	</div>
</div>
<!-- Add Team modal popup ends -->


<!-- <div class="project_inn_mask"></div>
	   <div id="remove_user_pop" class="remove_user_pop remove_user_pop_a">
		<p>User delete message here</p>
		<textarea placeholder="Comments here"></textarea>
		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Submit" onclick=""><input class="u_can_color" type="button" value="Cancel"></b>
	</div> -->

 <div class="remove_pop" style="display: none;">
	<i class="fa fa-trash"></i><span>User successfully Removed</span>
</div> 

<div id="popUp"></div>
</body>



<jsp:include page="../masterFooter.jsp" />

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
 function validateDayHours() {
	
	 var DayHours = document.getElementById("dayHoursEdit").value; 
	 $('#DayHoursDiv').find('p.jquery_form_error_message').remove();
	 var military = /^\s*([01]?\d|2[0-3]):[0-5]\d\s*$/i;
     var standard = /^\s*(0?\d|1[0-2]):[0-5]\d(\s+(AM|PM))?\s*$/i;
	
	if(DayHours.match(military) || DayHours.match(standard)){
		
		 $('#DayHoursDiv').find('p.jquery_form_error_message').remove();
		 $('input#dayHoursEdit').removeAttr( "record-exist record-exist-errormsg"); 
	/* 	$("input#DayHours").after("<p class='jquery_form_error_message'>Day Hours should less than 24 Hours</span>");
		 document.getElementById("DayHours").setAttribute('record-exist','yes');
		 document.getElementById("DayHours").setAttribute('record-exist-errorMsg','Day Hours should less than 24 Hours');  */
	}else{
		
		$("input#dayHoursEdit").after("<p class='jquery_form_error_message'>Day Hours should less than 24 Hours</span>");
		 document.getElementById("dayHoursEdit").setAttribute('record-exist','yes');
		 document.getElementById("dayHoursEdit").setAttribute('record-exist-errorMsg','Day Hours should less than 24 Hours'); 
	
		/*  $('#DayHoursDiv').find('p.jquery_form_error_message').remove();
		 $('input#DayHours').removeAttr( "record-exist record-exist-errormsg");  */
	}
	
 }
</script>


<script type="text/javascript">
 function validateEmployeeId(id,userId) {
	 $('input#EmployeeId').removeAttr( "record-exist record-exist-errormsg");
	 $('#EmployeeIdDiv').find('p.jquery_form_error_message').remove(); 
	 var EmpId = document.getElementById("EmployeeId").value; 
	 var con = document.getElementById("EmployeeId").value;
	 var EmployeeId = encodeURIComponent(con);
	 
        if(EmployeeId!=null&&EmployeeId!=""){
        	$("#update_user").attr("disabled", "disabled");
            $( "#update_user" ).addClass('button').val('Processing..');
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateExistingEmployeeId?"+tokenParamName+"="+tokenValue+"&id="+id+"&userId="+userId+"&EmployeeId="+EmployeeId, 
		        success: function(data) {
		        	if(data == true){
						  $("input#EmployeeId").after("<p class='jquery_form_error_message'>"+EmpId+" is Already Existed</span>");
						  document.getElementById("EmployeeId").setAttribute('record-exist','yes');
						  document.getElementById("EmployeeId").setAttribute('record-exist-errorMsg',EmpId+' is Already Existed'); 
		        	}
		        	else{
		        		 $('input#EmployeeId').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#EmployeeIdDiv').find('p.jquery_form_error_message').remove(); 
		        		 $("#update_user" ).addClass('us-btn').val('Save');
                         $("#update_user").removeAttr("disabled");
		        	} 
		        },
		    });
        }
	
 }
 </script>


<script type="text/javascript">
 function updateUser(id,userId,emailId,empId) {
	 $('input#EmailId').removeAttr( "record-exist record-exist-errormsg");
	 $('#EmailIdDiv').find('p.jquery_form_error_message').remove();
     var EmployeeId = document.getElementById("EmployeeId").value;
     var EmailId = document.getElementById("EmailId").value;
     var RatePer = document.getElementById("RatePer").value; 
		if(RatePer != null && RatePer != ""){
			$('#Rate').removeAttr('data-validation-optional');
			$('#Rate').attr("data-validation","required validate_Space validate_float validate_length length1-8"); 
		}else{
			$('#Rate').removeAttr('data-validation');
			$('#Rate').attr("data-validation-optional","true"); 
		}
		 
		var Rate = document.getElementById("Rate").value; 
		if(Rate != null && Rate != ""){
			$('#RatePer').removeAttr('data-validation-optional');
			$('#RatePer').attr("data-validation","required");
			$('#Rate').attr("data-validation","required validate_Space validate_float validate_length length1-8");
		}else{
			$('#RatePer').removeAttr('data-validation');
			$('#RatePer').attr("data-validation-optional","true"); 
		}
     
      if ($('#editUser_form').validate(false, validationSettings)){
    	  if(EmployeeId!=null&&EmployeeId!="" && empId != EmployeeId){
          $.ajax({
                type: "POST",
                url: sourceleadUrl+"/validateExistingEmployeeId?"+tokenParamName+"="+tokenValue+"&id="+id+"&userId="+userId+"&EmployeeId="+EmployeeId,
                success: function(data) {
                    if(data == true){
                          $("input#EmployeeId").after("<p class='jquery_form_error_message'>"+EmployeeId+" is Already Existed</span>");
                          document.getElementById("EmployeeId").setAttribute('record-exist','yes');
                          document.getElementById("EmployeeId").setAttribute('record-exist-errorMsg',EmployeeId+' is Already Existed');
                    }
                    else{
                        var frm = $('#editUser_form').serializeFormJSON();
                        var con = JSON.stringify(frm);
                        con = con.replace(/[{}]/g, "");
                        
                        //It will escape all the special characters
                        var jsonData = encodeURIComponent(con);
                        $("#update_user").attr("disabled", "disabled");
                        $( "#update_user" ).addClass('button').val('Processing..');
                        $.ajax({
                            type: "POST",
                            url: sourceleadUrl+"/updateUser?"+tokenParamName+"="+tokenValue+"&"+$($('#editUser_form')[0]).serialize()+"&userId="+userId+"&emailId="+emailId,
                            success: function(result) {
                            	 $("#selectoruser").removeAttr("style");
	                       		  $("#selectoruser").css({ display: "block" });
	                       		  $("#userMsgDiv").html("Success!! User Details Updated");
                               userDetails(userId);
                               userMsgFadein();
                                
                            },
                        });
                        return true;
                    }
                },
            });
    	  }
    	  else{
    		  if(emailId != EmailId){
    			  $.ajax({
                      type: "POST",
                      url: sourceleadUrl+"/validateExistingEmployeeEmailId?"+tokenParamName+"="+tokenValue+"&id="+id+"&userId="+userId+"&EmailId="+EmailId,
                      success: function(data) {
                          if(data == true){
                                $("input#EmailId").after("<p class='jquery_form_error_message'>"+EmailId+" is Already Existed</span>");
                                document.getElementById("EmailId").setAttribute('record-exist','yes');
                                document.getElementById("EmailId").setAttribute('record-exist-errorMsg',EmailId+' is Already Existed');
                          }
                          else{
                        	  var frm = $('#editUser_form').serializeFormJSON();
                              var con = JSON.stringify(frm);
                              con = con.replace(/[{}]/g, "");
                              //It will escape all the special characters
                              var jsonData = encodeURIComponent(con);
                              $("#update_user").attr("disabled", "disabled");
                              $( "#update_user" ).addClass('button').val('Processing..');
                              $.ajax({
                                  type: "POST",
                                  url: sourceleadUrl+"/updateUser?"+tokenParamName+"="+tokenValue+"&"+$($('#editUser_form')[0]).serialize()+"&userId="+userId+"&emailId="+emailId,
                                  success: function(result) {
                                	  $("#selectoruser").removeAttr("style");
                            		  $("#selectoruser").css({ display: "block" });
                            		  $("#userMsgDiv").html("Success!! User Details Updated");
                                      userDetails(userId);
                                      userMsgFadein();
                                      
                                  },
                              });
                              return true;
                          }
                      }
                      });
    		  }
    		  else{
    			  var frm = $('#editUser_form').serializeFormJSON();
                  var con = JSON.stringify(frm);
                  con = con.replace(/[{}]/g, "");
                  //It will escape all the special characters
                  var jsonData = encodeURIComponent(con);
                  $("#update_user").attr("disabled", "disabled");
                  $( "#update_user" ).addClass('button').val('Processing..');
                  $.ajax({
                      type: "POST",
                      url: sourceleadUrl+"/updateUser?"+tokenParamName+"="+tokenValue+"&"+$($('#editUser_form')[0]).serialize()+"&userId="+userId+"&emailId="+emailId,
                      success: function(result) {
                    	  $("#selectoruser").removeAttr("style");
                		  $("#selectoruser").css({ display: "block" });
                		  $("#userMsgDiv").html("Success!! User Details Updated");
                          userDetails(userId);
                          userMsgFadein();
                          
                      },
                  });
                  return true;
    		  }
    		 
    		 
    	  }
       }else{
        return false;
      }
    }
    $('body').on('blur', '#editUser_form', function() {
        $("#editUser_form").showHelpOnFocus();;
        $("#editUser_form").validateOnBlur(false, validationSettings);  
    });
</script>





<script type="text/javascript">
 function deleteUser(userId) {
	 var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
		   }; 
	 if ($('#deleteUser_form').validate(false, validationSettings)){ 
		 
		
         var formData = getFormData("deleteUser_form");
         $("#deleteLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
         $.post(sourceleadUrl+"/terminateUser?"+tokenParamName+"="+tokenValue+"&userId="+userId, formData, function(data) {
        	 location.replace(sourceleadUrl+"/usersList");
		});
		 
		 return true;
	 }else{
		 return false;
	 }
    }
 
 $('body').on('blur', '#deleteUser_form', function() {
		$("#deleteUser_form").showHelpOnFocus();;
		$("#deleteUser_form").validateOnBlur(false, validationSettings);  
	});
</script>




<script type="text/javascript">
 function updateRole(userId) {
	 var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
		   }; 
	 if ($('#editRole_form').validate(false, validationSettings)){ 
		 
         var formData = getFormData("editRole_form");
         
        $("#editRoleLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
        $.post(sourceleadUrl+"/editUserRole?"+tokenParamName+"="+tokenValue+"&userId="+userId, formData, function(data) {
        	 location.replace(sourceleadUrl+"/usersList");
		});
		 return true;
	 }else{
		 return false;
	 }
    }
 
 $('body').on('blur', '#editRole_form', function() {
		$("#editRole_form").showHelpOnFocus();;
		$("#editRole_form").validateOnBlur(false, validationSettings);  
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



<script>
function editUserDetails(userId){
$("#editRoleDetailsList").empty(); 
	 
	 $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getUserDetailsById?"+tokenParamName+"="+tokenValue+"&userId="+userId, 
	        success: function(data) {
	        	
			    var json = JSON.parse(data);
			    var result = "";
			    var selected1 = '';
				var selected2 = '';
				var selected3 = '';
				var selected4 = '';
				var selected5 = '';
				var selected6 = '';
				var selected7 = '';
				var selected8 = '';
				
				<c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
				  var sectionId = '${section.id}';
				  getCustomDependentField(sectionId);
				</c:forEach>
				
				if(json.ratePer == 'Hourly'){
						selected1 = 'selected';
					} 
				
				if(json.ratePer == 'Daily'){
						selected2 = 'selected';
					} 
				
				if(json.ratePer == 'Weekly'){
						selected3 = 'selected';
					}
				
				if(json.ratePer == 'Bi-Weekly'){
						selected4 = 'selected';
					} 
				
				if(json.ratePer == 'Semi-Monthly'){
						selected5 = 'selected';
					} 
				
				if(json.ratePer == 'Monthly'){
						selected6 = 'selected';
					} 
				
				if(json.ratePer == 'Yearly'){
						selected7 = 'selected';
					}
				if(json.ratePer == 'Bi-Monthly'){
					selected8 = 'selected';
				}
				
				var employeeId = "";
			    var lastName = "";
			    var rate = "";
			    var mobilePhone = "";
			    var extensionNumber="";
			    var dailyHours = "";
			    var emailId= "";
			    var manager = "";
			    var designationOtherType = "";
				if(json.employeeId != null && json.employeeId != "" && json.employeeId != 'undefined'){
					employeeId = json.employeeId;
				}
				
				if(json.designationOtherType != null && json.designationOtherType != "" && json.designationOtherType != 'undefined'){
					designationOtherType = json.designationOtherType;
				}
				
				/* if(json.userEmploymentType != null && json.userEmploymentType != "" && json.userEmploymentType != 'undefined'){
					userEmploymentType = json.userEmploymentType;
				} */
				
				if(json.user.lastName != null && json.user.lastName != "" && json.user.lastName != 'undefined'){
					lastName = json.user.lastName;
				}
				
				if(json.rate != null && json.rate != "" && json.rate != 'undefined'){
					rate = json.rate;
				}
				
				if(json.user.mobilePhone != null && json.user.mobilePhone != "" && json.user.mobilePhone != 'undefined'){
					mobilePhone = json.user.mobilePhone;
				}
				
				if(json.user.extensionNumber != null && json.user.extensionNumber != "" && json.user.extensionNumber != 'undefined'){
					extensionNumber = json.user.extensionNumber;
				}
				
				if(json.dailyHours != null && json.dailyHours != "" && json.dailyHours != 'undefined'){
					dailyHours = json.dailyHours;
				}
				
				if(json.user.email != null && json.user.email != "" && json.user.email != 'undefined'){
					emailId = json.user.email;
				}
				result = result + ' <form id="editUser_form"  name="editUser_form" method="post">';
					result = result + ' <div class="user_summary_edit_cta">';
					result = result + ' <label>First name<span class="text-danger">&nbsp;*</span></label>';
					result = result + ' <input type="text" value="'+json.user.firstName+'" name="FirstName" field-name="First Name" data-validation="required validate_Space validate_Alphanumeric validate_length length1-100"/>';
					result = result + ' </div>';
					
					result = result + ' <div class="user_summary_edit_cta">';
					result = result + ' <label>Last name</label>';
					result = result + ' <input type="text" value="'+lastName+'" name="LastName" data-validation="validate_Space validate_Alphanumeric validate_length length1-100" data-validation-optional="true">';
					result = result + ' </div>';
					
					result = result + ' <div class="user_summary_edit_cta">';
					result = result + ' <label><strong>Role:</strong></label>';
					result = result + '<div class="clearfix mt10"><span>'+json.roles.displayName+'</span></div>';
					/* result = result + '<strong>Role:</strong><span class="fix">'+json.roles.displayName+'</span></div>'; */
					result = result + ' </div>'; 
					result = result + ' <div class="clearfix"></div>';
					result = result + ' <div class="user_summary_edit_cta" id="EmployeeIdDiv">';
					result = result + ' <label>Employee Id</label>';
					result = result + ' <input type="text" value="'+employeeId+'" name="EmployeeId" id="EmployeeId" data-validation="validate_Space validate_length length2-100" data-validation-optional="true" onKeyUp="validateEmployeeId('+json.id+','+json.user.id+');">';
					result = result + ' </div>';
					
					result = result + ' <div class="user_summary_edit_cta">';
					
					result = result + ' <div class="row">';
					
					result = result + ' <div class="col-md-7 posSta phoneErrorvalid">';
					result = result + ' <label>Phone number</label>';
					result = result + ' <input type="text" value="'+mobilePhone+'" name="phoneNumber" data-validation="validate_Space validate_phone validate_length length9-20" data-validation-optional="true">';
					result = result + ' </div>';
					result = result + ' <div class="col-md-5 errBotValEdit">';
					result = result + ' <label>Extension</label>';
					result = result + ' <input type="text" value="'+extensionNumber+'" name="extensionNumber" data-validation="validate_Space validate_extno validate_length length1-5" data-validation-optional="true">';
					result = result + ' </div>';
					result = result + ' </div>';
					
					result = result + ' </div>';
					
/* 					if(json.userState == "NOT_LOGGEDIN"){
						
						result = result + ' <div class="user_summary_edit_cta" id="EmailIdDiv">';
						result = result + ' <label>Email Id<span class="text-danger">&nbsp; *</span></label>';
						result = result + ' <input type="text" value="'+json.user.email+'"  id="EmailId" name="email" field-name="EmailId" data-validation="required validate_Space validate_length length2-100 validate_email"/>';
						result = result + ' </div>';
					
					}
					else{
						result = result + ' <div class="user_summary_edit_cta" id="EmailIdDiv">';
						result = result + ' <label>Emaild Id<span class="text-danger">&nbsp; *</span></label>';
						result = result + ' <input type="text" value="'+json.user.email+'" readonly="readonly" id="EmailId" name="email" field-name="EmailId" data-validation="required validate_Space validate_length length2-100"/>';
						result = result + ' </div>';
					} */
					
					result = result + ' <div class="user_summary_edit_cta" id="EmailIdDiv">';
					result = result + ' <label>Emaild Id<span class="text-danger">&nbsp;*</span></label>';
					result = result + ' <input type="text" value="'+json.user.email+'" readonly="readonly" id="EmailId" name="email" field-name="EmailId" data-validation="required validate_Space validate_length length2-100"/>';
					result = result + ' </div>';
					
					
					if(json.allReportingManagers != null && json.allReportingManagers != "" && json.allReportingManagers != 'undefined' && json.allReportingManagers.length > 0){
						result = result + ' <div class="user_summary_edit_cta">';
						result = result + ' <label>Manager<span class="text-danger">&nbsp;*</span></label>';
						result = result + '  <select id="reportingManager" name="reportingManager" field-name="Manager" data-validation="required">'+
			 	     	"<option value=''>-Select Manager-</option>";    
			 	     	 for (var i=0; i<json.allReportingManagers.length; i++)
	    					{
	    		 	    	   manager = json.allReportingManagers[i];
	    		 	    	   var selected = '';
	    		 	    	  if(json.reportingManager != null && json.reportingManager != "" && json.reportingManager != 'undefined' && json.reportingManager.id == manager.id){
	    		 	    		 selected = 'selected';
	    		 	    	 }
	    		 	    	result = result+"<option value='"+manager.id+"' "+selected+">"+manager.firstName+" "+manager.lastName+"&nbsp;("+manager.userType+")</option>"; 
	    					}
			 	     	result = result+"</select>"; 
			 	     	result = result + ' </div>';
					}
					// moving date bottom to top
					 result = result + ' <div class="user_summary_edit_cta">';
					    result = result + ' <label>Department</label>';
						result = result + '<select class="form-control chosen-select" id="department" name="department"  field-name="Department" '+json.departmentName+'>';
						result = result + '</select>';
						result = result + '<input type="hidden" name="updatedepartment" id="updatedepartment" value="'+json.departmentName+'">';
						result = result + '<input type="hidden" name="updateDepartmentOrgRelationId" id="updateDepartmentOrgRelationId" value="'+json.department+'">';	
						result = result + '</div>'; 
					
						result = result + '<input type="hidden" value="'+json.userEmploymentType+'" id="hiddenId" >';

						
					   result = result + '<div class="user_summary_edit_cta">';
 					   result = result + ' <label>Employment type</label>';
						result = result + '<select class="form-control" id="designationType" name="designationType"  onchange="getEmployeementType();updateUserDesignationOtherType();" field-name="Employment type">';
						result = result + '<option value="">--Select Employment Type--</option>';
						result = result + ' <option '+(json.userEmploymentType == "C2C" ? "selected" : '') +'>C2C</option>';
						result = result + ' <option '+(json.userEmploymentType == "C2C-1099" ? "selected" : '') +'>C2C-1099</option>';
						result = result + '<option  '+ (json.userEmploymentType == "W-2" ? "selected" : '') +'>W-2</option>';
						result = result + ' <option '+(json.userEmploymentType == "C2H" ? "selected" : '') +'>C2H</option>';
						result = result + ' <option '+(json.userEmploymentType == "C2H -1099" ? "selected" : '') +'>C2H -1099</option>';
						result = result + '<option  '+(json.userEmploymentType == "Part Time" ? "selected" : '') +'>Part Time</option>';
						result = result + '<option  '+(json.userEmploymentType == "Full Time" ? "selected" : '') +'>Full Time</option>';
						result = result + '<option  '+(json.userEmploymentType == "Other" ? "selected" : '') +'>Other</option>';
						result = result + ' </select>';
						result = result + '</div>';
						result = result + '<div class="clearfix"></div>';
						result = result + '<div class="user_summary_edit_cta select-field" id="designationTypeDiv" style="display:none">';
						result = result + ' <label>Employment Other Type </label>';
						result = result + '<input type="text" class="form-control" name="designationOtherType"  id="designationOtherType" value="'+designationOtherType+'" placeholder="Employment Other Type" field-name="Employment Other Type" data-validation="validate_Space validate_length length2-50" data-validation-optional="true">';
						result = result + '</div>';
					    result = result + '<span class="" id="hideVendor" style="display:none">'; 
						result = result + '<div class="user_summary_edit_cta" id="vendor">';
						result = result + ' <label>Vendor</label>';
						result = result + '<select  class="chosen-select form-control" name="vendors"  id="vendors" value="'+json.vendorName+'" placeholder="select Vendor" field-name="Vendor" onchange="getVendorContactList();">';
						result = result + '</select>';
						result = result + '<input type="hidden" name="updateVendorName" id="updateVendorName" value="'+json.vendorName+'">';
						result = result + '<input type="hidden" name="updateVendorOrgRelationId" id="updateVendorOrgRelationId" value="'+json.vendorOrgRelationId+'">';
						result = result + '</div>'; 
						
						result = result + '<div class="user_summary_edit_cta select-field" id="vendorContacterr">';
						result = result + ' <label>Vendor Contact</label>';
						result = result + '<select  class="chosen-select form-control" name="vendorcontact"  value="'+json.vendorContactName+'" id="vendorcontact" field-name="Vendor Contact"  placeholder="select Vendor Contact">';
						result = result + '</select>';
						result = result + '<input type="hidden" name="updateVendorContactName" id="updateVendorContactName" value="'+json.vendorContactName+'" />';
						result = result + '<input type="hidden" name="updateSpocId" id="updateSpocId" value="'+json.vendorSpocId+'" />';
						result = result + '</div>';
						 
						result = result + '</span>';
					
					// moving date bottom to top
					result = result + ' <div class="user_summary_edit_cta select-field">';
					result = result + ' <label>Rate</label>';
		            result = result+'  <select class="rate_sele" id="currentCurrency" name="currentCurrency"> ';
					if(json.currentCurrency != "undefined" && json.currentCurrency != null && json.currentCurrency != ''){
						result = result + ' <option '+(json.currentCurrency == "INR" ? "selected" : '') +'>INR</option>';
						result = result + ' <option '+(json.currentCurrency == "AUD" ? "selected" : '') +'>AUD</option>';
						result = result + ' <option '+(json.currentCurrency == "USD" ? "selected" : '') +'>USD</option>';
						result = result + ' <option '+(json.currentCurrency == "GBP" ? "selected" : '') +'>GBP</option>';
						result = result + ' <option '+(json.currentCurrency == "SGD" ? "selected" : '') +'>SGD</option>';
						result = result + '<option   '+(json.currentCurrency == "EUR" ? "selected" : '') +'>EUR</option>'; 
					}else{
						result = result + ' <option '+(json.currencyConversionWithCountry == "INR" ? "selected" : '') +'>INR</option>';
						result = result + ' <option '+(json.currencyConversionWithCountry == "AUD" ? "selected" : '') +'>AUD</option>';
						result = result + ' <option '+(json.currencyConversionWithCountry == "USD" ? "selected" : '') +'>USD</option>';
						result = result + ' <option '+(json.currencyConversionWithCountry == "GBP" ? "selected" : '') +'>GBP</option>';
						result = result + ' <option '+(json.currencyConversionWithCountry == "SGD" ? "selected" : '') +'>SGD</option>';
						result = result + '<option   '+(json.currencyConversionWithCountry == "EUR" ? "selected" : '') +'>EUR</option>'; 
					
					}
					result = result+'</select>';
					result = result + ' <input type="text" class="rate_filed" value="'+rate+'" id="Rate" field-name="Rate" name="Rate" data-validation="validate_Space validate_float validate_length length1-8" data-validation-optional="true">';
					result = result + ' <select class="rate_sele" id="RatePer" name="RatePer" field-name="Rate Per">';
					result = result + ' <option value="">-Rate Per-</option>';
					result = result + ' <option '+selected1+'>Hourly</option>';
					result = result + ' <option '+selected2+'>Daily</option>';
					result = result + ' <option '+selected3+'>Weekly</option>';
					result = result + ' <option '+selected4+'>Bi-Weekly</option>';
					result = result + ' <option '+selected5+'>Semi-Monthly</option>';
					result = result + ' <option '+selected8+'>Bi-Monthly</option>';
					result = result + ' <option '+selected6+'>Monthly</option>';
					result = result + ' <option '+selected7+'>Yearly</option>';
					result = result + ' </select>';
					result = result + ' </div>';	
					
					
					result = result + ' <div class="user_summary_edit_cta">';
					result = result + ' <label>Date of join</label>';
					result = result + '<div class="input-group">';
					if(json.hireDateStr != null && json.hireDateStr != "" && json.hireDateStr != "undefined"){
						result = result + ' <input type="text" value="'+json.hireDateStr+'" name="DateofJoin" id="DateofJoin" readonly="readonly" class="calendar">';
						result = result + '   <span class="input-group-addon" onClick="clearhiredate();"><i class="colorRed fa fa-times cursorPointer"></i></span> ';

					}else{
						result = result + ' <input type="text" value="'+json.hireDateStr+'" name="DateofJoin" id="DateofJoin" readonly="readonly" class="calendar">';
						result = result + '   <span class="input-group-addon" onClick="clearhiredate1();"><i class="colorRed fa fa-times cursorPointer"></i></span> ';

					}
					result = result + ' </div></div>';	
					
					result = result + ' <div class="user_summary_edit_cta" id="DayHoursDiv">';
					result = result + ' <label>Day hours</label>';
					result = result + ' <input type="text" value="'+dailyHours+'" id="dayHoursEdit" placeholder="HH:MM" name="DayHours"  data-validation="validate_Space validate_dayHours validate_length length1-9" data-validation-optional="true" onblur="editMe(this);validateDayHours();">';
					result = result + ' </div>';
					
					 <c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
		           		if("${sectionOrder.index == 0}"){
		           		 	 var sectionID = '${section.id}';
		           		 	 result = result + ' <div class="clearfix"></div>';
		           			 result = result + ' <div id="customDiv'+sectionID+'" class="col-md-4"></div>';
		           			 result = result + ' <div class="clearfix"></div>';
		           			}
		        	</c:forEach>
					
					result = result + ' <div class="clearfix"></div>';
					result = result + ' <div class="user_summary_edit_cta pull-right">';
					result = result + ' <input class="us-btn" value="Update" id="update_user" type="button" onclick="updateUser('+json.id+','+userId+',\''+json.user.email+'\',\''+json.employeeId+'\');" > <input class="us-btn us-btn_a" value="Cancel" onClick="editUserDetails('+userId+');" type="button">';
					result = result + ' </div>';
					 
					result = result + ' <div class="clearfix"></div>';
					result = result + ' </form>';
				result = result + ' </div>';
				
				$(".user_summary_edit").slideToggle(100);
				$("#remove_user_id").slideUp(100);
				$(".edit_role_pop").slideUp(100);
				$(".error").removeClass("error");
				$('#editUserDiv').find('p.jquery_form_error_message').remove();
			    $("#editUserDiv").empty(); 
			    $("#editUserDiv").append(result);
			    
			    $("#vendorcontact").chosen();
			    $("#vendorcontact, #user_department").trigger("chosen:updated");
			    
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
			    
			    if(json.userEmploymentType == "Other"){
			    	$("#designationTypeDiv").show();
			      }
			    getEmployeementType();
			    getVendorsByOrgLocationId();
			    getDepartmentsList();
			    maskme(); 
	        },
		});
}
</script>

<script>
function getCustomDependentField(sectionId){
	$.ajax({
			type : "POST" , 
				 url: sourceleadUrl+"/getAllASectionCustomFields?sectionId="+sectionId,
				 success: function(data) {
		        	$('#customDiv'+sectionId).empty();
		        	$('#customDiv'+sectionId).append(data);
				 }
		  });
}
</script>

<script>
function updateUserDesignationOtherType(){
	var userOtherType = $('#designationType').val(); 
	if(userOtherType == 'Other' &&  userOtherType != '' && userOtherType !='undefined'){
		  document.getElementById("designationTypeDiv").style.display="block";
		  $("#designationOtherType").attr("data-validation-optional", "true");
		  $("#designationOtherType").attr("data-validation", "validate_Space validate_length length2-250");
	}else{
		   document.getElementById("designationTypeDiv").style.display="none";
		  $("#designationOtherType").attr("data-validation-optional", "true");
		  $("#designationOtherType").find('p.jquery_form_error_message').remove();
	} 
}
</script>

<script>
 function getVendorsByOrgLocationId(){
	 var vendorName = $("#updateVendorName").val();
	var vendorOrgRelationId = $("#updateVendorOrgRelationId").val();
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getVendorsByOrgLocationId?", 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	//$('#vendors').empty();
		        	$('#vendors').append('<option value="">--select Vendor --</option>');
		            for(var i=0;i<json.length;i++){
		            	  var opt = json[i];
		            	  if(vendorName != null && vendorName != 'undefined' && vendorName  != ''){
		            		  $('#vendors').append('<option '+ (opt.id == vendorOrgRelationId ? "selected" : '') +' value="'+opt.id+'">'+opt.orgName+'</option>');
			            	}else{
			            		$('#vendors').append('<option value='+opt.id+' >'+opt.orgName+'</option>');	
			            	}
		            	  
		            }
		            $('#vendors').trigger("chosen:updated");
		            getVendorContactList();
		            
		        }
		 });
 }
 </script>

<script>
 function getVendorContactList(){
	 $('#vendorcontact').val('').trigger("chosen:updated");
	 var orgRelationID = $('#vendors').val();
	 if(orgRelationID != null && orgRelationID != '' && orgRelationID != 'undefined'){
		 var vendorContactName = $("#updateVendorContactName").val();
		 var vendorSpocId = $("#updateSpocId").val();
		 $.ajax({
			    type: "POST",
		        url: sourceleadUrl+"/getOrgSpocsList?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationID+"&type=Vendor",  
		        success: function(json) {
		        	//var json = JSON.parse(data);
		        	$('#vendorcontact').empty();
		        	$('#vendorcontact').append('<option value="">--select Vendor contact--</option>');
		             for(var i=0;i<json.data.length;i++){
		            	 var opt = json.data[i];
		            	 if(vendorContactName != null && vendorContactName != 'undefined' && vendorContactName  != ''){
		            		 $('#vendorcontact').append('<option '+ (opt.id == vendorSpocId ? "selected" : '') +' value="'+opt.id+'">'+opt.spocName+'</option>');
			            	}else{
			            		$('#vendorcontact').append('<option value='+opt.id+' >'+opt.spocName+'</option>');
			            	}
		            	 
		            }
		            $('#vendorcontact').trigger("chosen:updated");
		        }
		 });
	 }
 }
 </script>

<script>
function getCalendar(){
	 $('#DateofJoin').datepicker({
	      dateFormat: 'yy-mm-dd',
	  });
}
</script>

<script>
function getEmployeementType(){
	
	var employementType = $('#designationType').val(); 
	//var abc = document.getElementById('hiddenId').value();
	var preval = document.getElementById("hiddenId").value;
	
		if(employementType == 'C2C' || employementType == 'C2C-1099'){
			if(employementType == preval){
				document.getElementById("hideVendor").style.display="block";
				 $('#vendors').attr("data-validation","required"); 
				 $('#vendorcontact').attr("data-validation","required");
				 getVendorsByOrgLocationId();
			}else{
				document.getElementById("hideVendor").style.display="block";
			 $('#vendors').attr("data-validation","required"); 
			 $('#vendorcontact').attr("data-validation","required"); 
			  $('#vendors').val(''); 
			 
	            $('#vendors').trigger("chosen:updated");
	            $('#vendorcontact').empty();
		//	 $('#vendorcontact').val(''); 
	           $('#vendorcontact').trigger("chosen:updated");
			} 
		}else{
			 document.getElementById("hideVendor").style.display="none";
			 $('#vendors').removeAttr("data-validation","required"); 
			 $('#vendorcontact').removeAttr("data-validation","required"); 
			 
			 $('#vendors').val(''); 
	            $('#vendors').trigger("chosen:updated");
	            $('#vendorcontact').empty();
		//	 $('#vendorcontact').val(''); 
	           $('#vendorcontact').trigger("chosen:updated");

			 
		}
		$('#designationType + p').remove();
}
</script>
      

<script>
function removeUser(userId){
	$("#editRoleDetailsList").empty(); 
	$("#reason").val('');
	$("#remove_user_id").slideToggle(100);
	$(".user_summary_edit").slideUp(100);
	$(".edit_role_pop").slideUp(100);
	
	$("#removeUserList").empty(); 
	
	
	$("#deleteLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	
	 $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getUserDetailsByIdForTermination?"+tokenParamName+"="+tokenValue+"&userId="+userId, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	 var reportingManagers = "";
				    var clientOwnerAndSpoc = "";
				    var vendorOwnerAndSpoc = "";
				    var managerUsers = "";
				    var approversUsers = "";
				    var teamOwnerUsers = "";
				    var projectApproverUsers = "";
				    var clientDocumentsOwnerUsers = "";
				    var vendorDocumentsOwnerUsers = "";
			        var result = "";
	        	
				    if(json.assignedUsersCountToReportingManager > 0){
						result = result + '<div class="clearfix mb10">';
						result = result + '<div class="col-md-6">';
						    result = result + '<div class="user_badg">';
							    result = result + '<p>Assigned Users as a Manager<strong class="label label-default">'+json.assignedUsersCountToReportingManager+'</strong></p>';
									result = result + '</div>';
					            result = result + '</div>';
							result = result + '<div class="col-md-6" id="reportingManagerDiv">';
							if(json.reportingManagers != null && json.reportingManagers != "" && json.reportingManagers != 'undefined' && json.reportingManagers.length > 0){
							       result = result + '<select class="chosen-select" id="assignReportingManagers" data-validation="required" field-name="Assign To" name="assignReportingManagers"  >';
								    result = result + '<option value="">Assign to</option>';
								    for (var i=0; i<json.reportingManagers.length; i++)
			    					{
								    	reportingManagers = json.reportingManagers[i];
								    	if(reportingManagers.lastName != null && reportingManagers.lastName != 'undefined' && reportingManagers.lastName != ''){
								    		result = result+"<option value='"+reportingManagers.id+"'>"+reportingManagers.firstName+" "+reportingManagers.lastName+"&nbsp;("+reportingManagers.userType+")</option>"; 
								    	}else{
								    		result = result+"<option value='"+reportingManagers.id+"'>"+reportingManagers.firstName+"&nbsp;("+reportingManagers.userType+")</option>"; 
								    	}
								    	
										
			    					}
								    result = result + '</select>';
							}
							result = result + '</div>';
							result = result + '</div>';	
						  }
							
						if(json.assignedAsClientOwnerOrSpocCount > 0){
							result = result + '<div class="clearfix mb10">';
							result = result + '<div class="col-md-6">';
							    result = result + '<div class="user_badg">';
								    result = result + '<p>Assigned as Vendor Owner and Spoc<strong class="label label-info">'+json.assignedAsClientOwnerOrSpocCount+'</strong></p>';
										result = result + '</div>';
						            result = result + '</div>';
								result = result + '<div class="col-md-6" id="clientUserDiv">';
								if(json.clientUsersList != null && json.clientUsersList != "" && json.clientUsersList != 'undefined' && json.clientUsersList.length > 0){
								       result = result + '<select class="chosen-select" data-validation="required" field-name="Assign To" id="clientUsers" name="clientUsers" >';
									    result = result + '<option value="">Assign to</option>';
									    for (var i=0; i<json.clientUsersList.length; i++)
				    					{
									    	clientOwnerAndSpoc = json.clientUsersList[i];
									    	var fullName = "";
									    	if(clientOwnerAndSpoc.lastName != null){
									    		fullName = clientOwnerAndSpoc.firstName+" "+clientOwnerAndSpoc.lastName;
									    	}else{
									    		fullName = clientOwnerAndSpoc.firstName;
									    	}
									    	result = result+"<option value='"+clientOwnerAndSpoc.id+"'>"+fullName+"&nbsp;("+clientOwnerAndSpoc.userType+")</option>"; 
											
				    					}
									    result = result + '</select>';
								}
								result = result + '</div>';
								result = result + '</div>';	
							  }	
						
						
						if(json.assignedAsVendorOwnerOrSpocCount > 0){
							result = result + '<div class="clearfix mb10">';
							result = result + '<div class="col-md-6">';
							    result = result + '<div class="user_badg">';
								    result = result + '<p>Assigned as Client Owner and Spoc<strong class="label label-primary">'+json.assignedAsVendorOwnerOrSpocCount+'</strong></p>';
										result = result + '</div>';
						            result = result + '</div>';
								result = result + '<div class="col-md-6" id="vendorUserDiv">';
								if(json.vendorUsersList != null && json.vendorUsersList != "" && json.vendorUsersList != 'undefined' && json.vendorUsersList.length > 0){
								       result = result + '<select class="chosen-select" data-validation="required" field-name="Assign To" id="vendorUsers" name="vendorUsers">';
									    result = result + '<option value="">Assign to</option>';
									    for (var i=0; i<json.vendorUsersList.length; i++)
				    					{
									    	vendorOwnerAndSpoc = json.vendorUsersList[i];
									    	var fullName = "";
									    	if(vendorOwnerAndSpoc.lastName != null){
									    		fullName = vendorOwnerAndSpoc.firstName+" "+vendorOwnerAndSpoc.lastName;
									    	}else{
									    		fullName = vendorOwnerAndSpoc.firstName;
									    	}
									    	result = result+"<option value='"+vendorOwnerAndSpoc.id+"'>"+fullName+"&nbsp;("+vendorOwnerAndSpoc.userType+")</option>"; 
											
				    					}
									    result = result + '</select>';
								}
								result = result + '</div>';
								result = result + '</div>';	
							  }	
						
						
						if(json.requirementsCountAsManager > 0){
							result = result + '<div class="clearfix mb10">';
							result = result + '<div class="col-md-6">';
							    result = result + '<div class="user_badg">';
								    result = result + '<p>Assigned as Manager to Jobs<strong class="label label-success">'+json.requirementsCountAsManager+'</strong></p>';
										result = result + '</div>';
						            result = result + '</div>';
								result = result + '<div class="col-md-6" id="managerUserDiv">';
								if(json.requirementManagersList != null && json.requirementManagersList != "" && json.requirementManagersList != 'undefined' && json.requirementManagersList.length > 0){
								       result = result + '<select class="chosen-select" data-validation="required" field-name="Assign To" id="managerUsers" name="managerUsers">';
									    result = result + '<option value="">Assign to</option>';
									    for (var i=0; i<json.requirementManagersList.length; i++)
				    					{
									    	managerUsers = json.requirementManagersList[i];
									    	var fullName = "";
									    	if(managerUsers.lastName != null){
									    		fullName = managerUsers.firstName+" "+managerUsers.lastName;
									    	}else{
									    		fullName = managerUsers.firstName;
									    	}
									    	result = result+"<option value='"+managerUsers.id+"'>"+fullName+"&nbsp;("+managerUsers.userType+")</option>"; 
											
				    					}
									    result = result + '</select>';
								}
								result = result + '</div>';
								result = result + '</div>';	
							  }	
							
						if(json.requirementsCountAsApprover > 0){
							result = result + '<div class="clearfix mb10">';
							result = result + '<div class="col-md-6">';
							    result = result + '<div class="user_badg">';
								    result = result + '<p>Assigned as Approver to Jobs<strong class="label label-warning">'+json.requirementsCountAsApprover+'</strong></p>';
										result = result + '</div>';
						            result = result + '</div>';
								result = result + '<div class="col-md-6" id="approverUserDiv">';
								if(json.requirementApproversList != null && json.requirementApproversList != "" && json.requirementApproversList != 'undefined' && json.requirementApproversList.length > 0){
								       result = result + '<select class="chosen-select" data-validation="required" field-name="Assign To" name="approverUser" id="approverUser">';
									    result = result + '<option value="">Assign to</option>';
									    for (var i=0; i<json.requirementApproversList.length; i++)
				    					{
									    	approversUsers = json.requirementApproversList[i];
									    	var fullName = "";
									    	if(approversUsers.lastName != null){
									    		fullName = approversUsers.firstName+" "+approversUsers.lastName;
									    	}else{
									    		fullName = approversUsers.firstName;
									    	}
									    	result = result+"<option value='"+approversUsers.id+"'>"+fullName+"&nbsp;("+approversUsers.userType+")</option>"; 
											
				    					}
									    result = result + '</select>';
								}
								result = result + '</div>';
								result = result + '</div>';	
							  }	
						
						
							
						if(json.teamsCountAsOwner > 0){
							result = result + '<div class="clearfix mb10">';
							result = result + '<div class="col-md-6">';
							    result = result + '<div class="user_badg">';
								    result = result + '<p>Assigned as Team Owner for Jobs<strong class="label label-info">'+json.teamsCountAsOwner+'</strong></p>';
										result = result + '</div>';
						            result = result + '</div>';
								result = result + '<div class="col-md-6" id="teamOwnerUserDiv">';
								if(json.teamOwnersList != null && json.teamOwnersList != "" && json.teamOwnersList != 'undefined' && json.teamOwnersList.length > 0){
								       result = result + '<select class="chosen-select" data-validation="required" field-name="Assign To" id="teamOwnerUser" name="teamOwnerUser">';
									    result = result + '<option value="">Assign to</option>';
									    for (var i=0; i<json.teamOwnersList.length; i++)
				    					{
									    	teamOwnerUsers = json.teamOwnersList[i];
									    	var fullName = "";
									    	if(teamOwnerUsers.lastName != null){
									    		fullName = teamOwnerUsers.firstName+" "+teamOwnerUsers.lastName;
									    	}else{
									    		fullName = teamOwnerUsers.firstName;
									    	}
									    	result = result+"<option value='"+teamOwnerUsers.id+"'>"+fullName+"&nbsp;("+teamOwnerUsers.userType+")</option>"; 
											
				    					}
									    result = result + '</select>';
								}
								result = result + '</div>';
								result = result + '</div>';	
							  }	
						
						
						
						if(json.projectApproverCount > 0){
							result = result + '<div class="clearfix mb10">';
							result = result + '<div class="col-md-6">';
							    result = result + '<div class="user_badg">';
								    result = result + '<p>Assigned as Project Approver<strong class="label label-success">'+json.projectApproverCount+'</strong></p>';
										result = result + '</div>';
						            result = result + '</div>';
								result = result + '<div class="col-md-6" id="teamOwnerUserDiv">';
								if(json.projectApproversList != null && json.projectApproversList != "" && json.projectApproversList != 'undefined' && json.projectApproversList.length > 0){
								       result = result + '<select class="chosen-select" data-validation="required" field-name="Assign To" id="projectApproverUser" name="projectApproverUser">';
									    result = result + '<option value="">Assign to</option>';
									    for (var i=0; i<json.projectApproversList.length; i++)
				    					{
									    	projectApproverUsers = json.projectApproversList[i];
									    	var fullName = "";
									    	if(projectApproverUsers.lastName != null){
									    		fullName = projectApproverUsers.firstName+" "+projectApproverUsers.lastName;
									    	}else{
									    		fullName = projectApproverUsers.firstName;
									    	}
									    	result = result+"<option value='"+projectApproverUsers.id+"'>"+fullName+"&nbsp;("+projectApproverUsers.userType+")</option>"; 
											
				    					}
									    result = result + '</select>';
								}
								result = result + '</div>';
								result = result + '</div>';	
							  }	
						
						
						
						if(json.clientDocumentsOwnerCount > 0){
							result = result + '<div class="clearfix mb10">';
							result = result + '<div class="col-md-6">';
							    result = result + '<div class="user_badg">';
								    result = result + '<p>Assigned as Client Documents Owner<strong class="label label-success">'+json.clientDocumentsOwnerCount+'</strong></p>';
										result = result + '</div>';
						            result = result + '</div>';
								result = result + '<div class="col-md-6" id="clientDocOwnerUserDiv">';
								if(json.clientDocumentsOwnerList != null && json.clientDocumentsOwnerList != "" && json.clientDocumentsOwnerList != 'undefined' && json.clientDocumentsOwnerList.length > 0){
								       result = result + '<select class="chosen-select" data-validation="required" field-name="Assign To" id="clientDocsOwnerUser" name="clientDocsOwnerUser">';
									    result = result + '<option value="">Assign to</option>';
									    for (var i=0; i<json.clientDocumentsOwnerList.length; i++)
				    					{
									    	clientDocumentsOwnerUsers = json.clientDocumentsOwnerList[i];
									    	var fullName = "";
									    	if(clientDocumentsOwnerUsers.lastName != null){
									    		fullName = clientDocumentsOwnerUsers.firstName+" "+clientDocumentsOwnerUsers.lastName;
									    	}else{
									    		fullName = clientDocumentsOwnerUsers.firstName;
									    	}
									    	result = result+"<option value='"+clientDocumentsOwnerUsers.id+"'>"+fullName+"&nbsp;("+clientDocumentsOwnerUsers.userType+")</option>"; 
											
				    					}
									    result = result + '</select>';
								}
								result = result + '</div>';
								result = result + '</div>';	
							  }
						
						
						
						if(json.vendorDocumentsOwnerCount > 0){
							result = result + '<div class="clearfix mb10">';
							result = result + '<div class="col-md-6">';
							    result = result + '<div class="user_badg">';
								    result = result + '<p>Assigned as Vendor Documents Owner<strong class="label label-success">'+json.vendorDocumentsOwnerCount+'</strong></p>';
										result = result + '</div>';
						            result = result + '</div>';
								result = result + '<div class="col-md-6" id="vendorDocOwnerUserDiv">';
								if(json.vendorDocumentsOwnerList != null && json.vendorDocumentsOwnerList != "" && json.vendorDocumentsOwnerList != 'undefined' && json.vendorDocumentsOwnerList.length > 0){
								       result = result + '<select class="chosen-select" data-validation="required" field-name="Assign To" id="vendorDocOwnerUsers" name="vendorDocsOwnerUser">';
									    result = result + '<option value="">Assign to</option>';
									    for (var i=0; i<json.vendorDocumentsOwnerList.length; i++)
				    					{
									    	vendorDocumentsOwnerUsers = json.vendorDocumentsOwnerList[i];
									    	var fullName = "";
									    	if(vendorDocumentsOwnerUsers.lastName != null){
									    		fullName = vendorDocumentsOwnerUsers.firstName+" "+vendorDocumentsOwnerUsers.lastName;
									    	}else{
									    		fullName = vendorDocumentsOwnerUsers.firstName;
									    	}
									    	result = result+"<option value='"+vendorDocumentsOwnerUsers.id+"'>"+fullName+"&nbsp;("+vendorDocumentsOwnerUsers.userType+")</option>"; 
											
				    					}
									    result = result + '</select>';
								}
								result = result + '</div>';
								result = result + '</div>';	
							  }
							
							
							
					result = result + '<div class="clearfix mb10 mt">';
					  result = result + '<div class="col-md-12" id="reasonDiv">';
						  result = result + '<textarea rows="3" class="form-control input-sm" name="reason" id="reason" data-validation="required validate_length length2-250" field-name="Reason" placeholder="Terminate Reason"></textarea>';
						  	result = result + '</div>';
						  result = result + '</div>';
					  result = result + '<div class="clearfix"></div>';
							result = result + '<div class="user_summary_edit_cta pull-right">';
							result = result + '<input class="us-btn us-btn_a " type="button" value="Terminate" type="button" onclick="deleteUser('+userId+');"/>';
								result = result + '&nbsp;<input class="us-btn" onclick="cancelUser();" type="button" value="Cancel"/>';
								result = result + '</div>';
	        	
	    	    $("#removeUserList").append(result);  
	    	    $("#deleteLoading").removeAttr("style"); 
	    	    
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
	        }
	 
	        
	 });
	
	
	
	
}
</script>


<script>
function editUserRole(){
	$(".edit_role_pop").slideToggle(100);
	$("#remove_user_id").slideUp(100);
	$(".user_summary_edit").slideUp(100);
	
	$("#editRoleDetailsList").empty(); 
}
</script>
<script>
function cancelUser(){
	$("#remove_user_id").slideUp(100);
}
</script>

<script>
function cancelEditRole(){
	$("#edit_role_pop").slideToggle(100);
	location.reload(); 
}
</script>


<!-- <script>
function terminateUser(userId){   
	var sourceleadUrl = '${sourceleadUrl}';
	var reason = document.getElementById("reason").value;
	if(reason.match(/^[^\s-](.*)*$/) != null){
		$("#reasonDiv").empty();
		
		//getConformation(userId,reason);
		
		 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/terminateUser?${_csrf.parameterName}=${_csrf.token}&userId="+userId+"&reason="+reason, 	   
   		    success: function(data) {
   		    	userDetails(userId);
   		    	$(".remove_pop").fadeIn(200);
   		        $('.remove_pop').delay(2000).fadeOut('slow');
   		    }
	   });
	}else{
		if(reason){
			$("#reasonDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Must not Start With Space</font>") ;
		}else{
			$("#reasonDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
		}
	}
}

</script> -->


<script>
function getConformation(userId,reason){ 
	 $(".project_error").slideDown(200);
	 $(".project_inn_mask").fadeIn(200);
}
</script>


<script>
// project error message
$(document).ready(function() {
    $("#save_reason").click(function(){
		$(".project_error").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	});
    $(".project_error a").click(function() {
        $(".project_error").slideUp(200);
		$(".project_inn_mask").fadeOut(200);
    });
});
</script>





<script>
//project side bar tabs
$(document).ready(function() {
	$("#tab-18").show()
    $(".project-tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".project_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});



</script>

<script type="text/javascript">
function flushProject(){
	$("#proId").removeAttr("disabled");
	$("#saveAssignProject").removeAttr("disabled");
	$('#StartDate').val('');
	$('#EndDate').val('');
	$('#proId').val('');
	$(".error").removeClass("error");
	$('#assignProjectDiv').find('p.jquery_form_error_message').remove();
}
</script>
<!-- this script for inline edit -->
<!-- <script> 
function inlineEditForUsers(userId,localFieldName){
	
	var fieldName = localFieldName;
	var fieldValue = $("#"+localFieldName).val();
	 $.ajax({
		   	type: "POST",
		    url: "http://localhost:8080/sourcelead/updateUserById?${_csrf.parameterName}=${_csrf.token}&userId="+userId+"&fieldName="+fieldName+"&fieldValue="+fieldValue, 	   
   		    success: function(data) {
   			   
   		    }
	 }); 
}
</script> -->
<!-- this script for inline edit -->


<!-- Scripts for project details -->


<script>
function getStartandEndDate(){
	
	var projectId = document.getElementById("proId").value;
	if(projectId != null && projectId != ""){
    $.ajax({
    	type: "POST",
        url: sourceleadUrl+"/getStartAndEndOfProject?"+tokenParamName+"="+tokenValue+"&projectId="+projectId, 
        success: function(data) {
        	var json = JSON.parse(data);
        	$("#StartDate").val(json.startDateStr);
        	$("#EndDate").val(json.endDateStr);
        	date();
        },
    });
	}

}
</script>
<script type="text/javascript">
 function assignProject() {
	 var userId = '${userId}';
	 var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
		   }; 
	 if ($('#assignProject_form').validate(false, validationSettings)){ 
	        var frm = $('#assignProject_form').serializeFormJSON();
	        var con = JSON.stringify(frm);
	        con = con.replace(/[{}]/g, "");
	        $("#saveAssignProject").attr("disabled", "disabled");
     		$("#saveAssignProject" ).addClass('button').val('Processing..');
	        
	        $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/saveAssignProject?"+tokenParamName+"="+tokenValue+"&json="+con+"&userId="+userId, 
		        success: function(data) {
		        	if(data="success"){
		        	 	$("#assignProject").modal('hide');
		        		userProjectDetails(userId,"All");
		        		getUnAssignedProjects(userId);
		        	}
		        },
		        error: function(e){
		            
		        }
		    }); 
	       return true;
	  }else{
		 $("#saveAssignProject").removeAttr('data-dismiss','modal');
		return false;
	  }
    }

    $('body').on('blur', '#addProject_form', function() {
		$("#assignProject_form").showHelpOnFocus();;
		$("#assignProject_form").validateOnBlur(false, validationSettings);  
	});
 
</script>



<script type="text/javascript">
 function getUnAssignedProjects(userId) {
	        
	        $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getUnassignedProjects?"+tokenParamName+"="+tokenValue+"&userId="+userId, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	output = output + "<option value=''>-- Select Project --</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.projectName+"</option>";
					}
		        	$("#proId").empty();
			        $("#proId").append(output);
		        },
		        error: function(e){
		            
		        }
		    });
	  
    }
</script>



<script>
function date() {
	var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	
	  if($("#EndDate").val()==""){
		  var selected = $("#StartDate").val();
			 var dt = new Date(selected);
		     dt.setDate(dt.getDate() + 1);
		     $("#StartDate").datepicker("option", "minDate", dt);
		     
		  var selected = $("#StartDate").val();
		  var dt = new Date(selected);
		  dt.setDate(dt.getDate() + 1);
		  $("#EndDate").datepicker("option", "minDate", dt);
	 	 $('#EndDate').datepicker({
	      dateFormat: 'yy-mm-dd',
	        onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() - 1);
	            $("#StartDate").datepicker("option", "maxDate", dt);
	        }
	  });
	}
	  else{
		  
		   var selected = $("#StartDate").val();
		   var dt = new Date(selected);
           dt.setDate(dt.getDate());
           //Main project end date
           var projectEndDate = $("#EndDate").val();
           var endDate1 = "";
           if(projectEndDate != null && projectEndDate != "" && projectEndDate != 'undefined'){
          	 endDate1 = new Date(projectEndDate);
	          endDate1.setDate(endDate1.getDate()); 
           }
           var selected = $("#StartDate").val();
 		   var dt = new Date(selected);
 		   dt.setDate(dt.getDate() + 1);
 		   $("#EndDate").datepicker("option", "minDate", dt);
 		   $("#EndDate").datepicker("option", "maxDate", endDate1);
 		   
 		 	
		     $("#StartDate").datepicker("option", "minDate", dt);
		     $("#StartDate").datepicker("option", "maxDate", endDate1);
		  
	  }
  } 
</script>

<script>
  $( function() {
	  
	  /* $('#endDate').datepicker({
	      dateFormat: 'yy-mm-dd',
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	   */
	   var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}';
	  $('#StartDate').datepicker({
	      dateFormat: locationDate,
	      onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() + 1);
	            $("#EndDate").datepicker("option", "minDate", dt);
	        }
	  });
	  
	  $('#EndDate').datepicker({
	      dateFormat: locationDate,
	        onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() - 1);
	            $("#StartDate").datepicker("option", "maxDate", dt);
	        }
	  });
	  
  } );
  
  function deleteProject(local,userId,projectName){ 
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Project : <strong>"+projectName+"</strong></p>";
		result = result +"		<p>User delete message here</p>";
		result = result +"		<textarea  id='reasonForProject' name='reasonForProject'  placeholder='Comments here'></textarea>";
		result = result + ' <div id="reasonForProjectDiv"/>';
		result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Submit' onclick='saveReason("+local+","+userId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelTermination();'></b>";
		result = result +"	</div>";
		$("#popUp").append(result);
		$("#remove_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
		
	}
  function cancelTermination(){
		$("#popUp").empty();
		 $(".project_inn_mask").fadeOut(200);
	}
 
  
</script>
<script> 
function saveReason(projectMemberId,userId){
	var reason = document.getElementById("reasonForProject").value;
	
	if(reason){
		$("#reasonForProjectDiv").empty();
		
		var r = confirm("Do you want terminate the user from Project");
		if (r == true) {
	
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/saveReason?"+tokenParamName+"="+tokenValue+"&projectMemberId="+projectMemberId+"&reason="+reason, 	   
   		    success: function(data) {
   		    	$("#popUp").empty();
   			 	$(".project_inn_mask").fadeOut(200);
   		    	userProjectDetails(userId,"All");
   		    }
	 }); 
		}
	}
	else{
		$("#reasonForProjectDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
	}
}
</script>
<script> 
function inlineEditForEndDate(id,projectMemberId){
	 var endDate = document.getElementById(id).value;
	  $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/updateEndDateByProjectMemberId?"+tokenParamName+"="+tokenValue+"&endDate="+endDate+"&projectMemberId="+projectMemberId, 	   
   		    success: function(data) {
   			         
   		    }
	 });  
}
</script>



<script>
 function filter() {
		$(".approved_pop").toggle();
  } 
 
</script>

<script>
function userStatusFilter() { 
	var selectedVal = "";
	var selected = $("#radioFilter input[type='radio']:checked");
	if (selected.length > 0) {
	    selectedVal = selected.val();
	}
	if(selectedVal == "All"){
		var userId = '${userId}';
		  userProjectDetails(userId,"All");
	}
	if(selectedVal == "Active"){
		var userId = '${userId}';
		 userProjectDetails(userId,"Active");
	}
	if(selectedVal == "In-Active"){
		var userId = '${userId}';
		 userProjectDetails(userId,"In-Active");
	}
}
</script>
<script>
function reActiveUser(userId){   
         $.ajax({
               type: "POST",
            url: sourceleadUrl+"/reActiveUser?"+tokenParamName+"="+tokenValue+"&userId="+userId,        
               success: function(data) {
            	   $("#selectoruser").removeAttr("style");
        		   $("#selectoruser").css({ display: "block" });
        		   $("#userMsgDiv").html("Success!! User reactivated for sourcelead");
               	   location.replace(sourceleadUrl+"/usersList");
            	   userDetails(userId);
               }
       });
    
}

</script>

<!-- end project scripts -->


<!-- Modal content Re-Asign Project-->
<div role="dialog" id="Re-AssignProject" class="modal fade">
	<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button data-dismiss="modal" class="close" type="button">X</button>
					<h4 class="modal-title">Re-Assign Project</h4>
				</div>
				<form:form id="reAssignProject_form" name="reAssignProject_form" method="post">
				<div class="modal-body clearfix">
					<div id ="ReAssignProjectId"></div>
				</div>
				</form:form>
				<div class="modal-footer">
					<button class="btn btn-primary" type="button" id="saveReAssignProject" onclick="reAssignProject();">Select</button>
					<button data-dismiss="modal" class="btn btn-default" onclick="flushProject();" type="button">Cancel</button>
				</div>
			</div>
	</div>
</div>
<!-- Add Team modal popup ends -->
<script>
function reActiveProject(projectMemberId,ProjectName,startDate,endDate,projectEndDateStr,userId){  
  
   var result = "";
   result = result + '<input type="hidden" name="projectMemberId" id="projectMemberId" value="'+projectMemberId+'" >';
   result = result + '<input type="hidden" name="projectMemberUserId" id="projectMemberUserId" value="'+userId+'" >';
   result = result +'<div class="modal-body clearfix">';
   result = result +'<div class="form-horizontal custom-form clearfix">';
   result = result +'<div class="col-xs-12 col-md-12 col-lg-12" >';
   result = result +'<div class="form-group">';
   result = result +'	<label class="col-lg-3">Project Name </label>';
   result = result +'	<div class="col-lg-9">';
   result = result +'		<input id="reAssignProjectName" type="text" class="form-control" value="'+ProjectName+'"  name="reAssignProjectName" readonly="readonly" class="form-control"></span>';
   result = result +'	</div>';
   result = result +'	</div>';
   
   
   result = result +'<div class="form-group">';
   result = result +'<label class="col-lg-3">Start date</label>';
   result = result +'<div class="col-lg-9">';
   result = result +'<div class="input-group">';
   result = result +'	<span class="input-group-addon"><i class="fa fa-calendar"></i></span> <input type="text" class="form-control" readonly="readonly" placeholder="DD/MM/YYYY" id="Re-StartDate" name="Re-StartDate" field-name="Start Date">';
   result = result +'</div>';
   result = result +'</div>';
   result = result +'</div>';
   
   result = result +'<div class="form-group">';
   result = result +'<label class="col-lg-3">End date</label>';
   result = result +'<div class="col-lg-9">';
   result = result +'<div class="input-group">';
   result = result +'<span class="input-group-addon"><i class="fa fa-calendar"></i></span> <input type="text" class="form-control" readonly="readonly" placeholder="DD/MM/YYYY" id="Re-EndDate" name="Re-EndDate" />';
   result = result +'</div>';
   result = result +'</div>';
   result = result +'</div>';

   result = result +'</div>';
   result = result +'</div>';
   result = result +'	</div>';
   
   $("#ReAssignProjectId").empty();
   $("#ReAssignProjectId").append(result);
	  
   var reStatertDate = $('#Re-StartDate').val();
   
		  
	  var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}';

  var reStatertDate = $('#Re-StartDate').val()
  if(projectEndDateStr != null && projectEndDateStr != "" && projectEndDateStr != 'undefined'){
	  $('#Re-StartDate').datepicker({
	      dateFormat: locationDate,
		  minDate:endDate,
		  maxDate:projectEndDateStr,
	      onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() + 1);
	            $("#Re-EndDate").datepicker("option", "minDate", dt);
	        }
	  });
	  
	  $('#Re-EndDate').datepicker({
	      dateFormat: locationDate,
	      minDate:endDate,
	      maxDate:projectEndDateStr,
	        onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() - 1);
	            $("#Re-StartDate").datepicker("option", "maxDate", dt);
	        }
	  });
  }
  else{
	  
	  $('#Re-StartDate').datepicker({
	      dateFormat: locationDate,
		  minDate:endDate,
	      onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() + 1);
	            $("#Re-EndDate").datepicker("option", "minDate", dt);
	        }
	  });
	  
	  
	
	  $('#Re-EndDate').datepicker({
	      dateFormat: locationDate,
	      minDate:endDate,
	      maxDate:reStatertDate,
	        onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() - 1);
	            $("#Re-StartDate").datepicker("option", "maxDate", dt);
	        }
	  });
  }	  
   
}

</script>

<script type="text/javascript">
 function reAssignProject() {
	 var projectMemberId = '${projectMemberId}';
	 var projectMemberUserId = document.getElementById("projectMemberUserId").value; 
	 var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
		   }; 
	 if ($('#reAssignProject_form').validate(false, validationSettings)){ 
		 var projectMemberId = $("#projectMemberId").val();
	        var frm = $('#reAssignProject_form').serializeFormJSON();
	        var con = JSON.stringify(frm);
	        con = con.replace(/[{}]/g, "");
	        $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/saveReAssignProject?"+tokenParamName+"="+tokenValue+"&json="+con+"&projectMemberId="+projectMemberId, 
		        success: function(data) {
		        	if(data="success"){
		        	 	$("#Re-AssignProject").modal('hide');
		        		userProjectDetails(projectMemberUserId,"All");
		        		getUnAssignedProjects(projectMemberUserId);
		        	}
		        },
		        error: function(e){
		            
		        }
		    }); 
	       return true;
	  }else{
		 $("#saveReAssignProject").removeAttr('data-dismiss','modal');
		return false;
	  }
    }

    $('body').on('blur', '#reAssignProject_form', function() {
		$("#reAssignProject_form").showHelpOnFocus();;
		$("#reAssignProject_form").validateOnBlur(false, validationSettings);  
	});
 
</script>



<script>
function maskme(){

	$.mask.definitions['H'] = "[0-9]";
    $.mask.definitions['h'] = "[0-9]";
    $.mask.definitions['M'] = "[0-9]";
    $.mask.definitions['m'] = "[0-9]";
    $.mask.definitions['P'] = "[AaPp]";
    $.mask.definitions['p'] = "[Mm]";
    $("#dayHoursEdit").mask("Hh:Mm"); 
}
</script>



<script type="text/javascript">
function flushReProject(){
	$('#Re-StartDate').val('');
	$('#Re-EndDate').val('');
	$(".error").removeClass("error");
	$('#assignProjectDiv').find('p.jquery_form_error_message').remove();
}
</script>


<script>
function editMe(local){

 var id = local.id;
	 var inputValue =  $("#"+id).val();
	 var split = inputValue.split(':');
	 var hours = split[0];
	 var minutes = split[1];
	 var hours1 = hours.charAt(0);
	 var hours2 = hours.charAt(1);
	 var min1 = minutes.charAt(0);
	 var min2 = minutes.charAt(1);
	
	 if(!isNaN(hours1) && isNaN(hours2) && isNaN(min1) && isNaN(min2)){
		 $("#"+id).val("0"+hours1+":00");
	 }
	 if(isNaN(hours1) && !isNaN(hours2) && isNaN(min1) && isNaN(min2)){
		 $("#"+id).val("0"+hours2+":00");
	 }
	 else if(isNaN(hours)  && !isNaN(min1) && isNaN(min2)){
		 $("#"+id).val("00:"+min1+"0");
	 }
	 else if(isNaN(hours)  && isNaN(min1) && !isNaN(min2)){
		 $("#"+id).val("00:0"+min2);
	 }
	 else if(!isNaN(hours)  && isNaN(min1) && isNaN(min2)){
		 $("#"+id).val(hours+":00");
	 }
	 else if(!isNaN(hours)&& !isNaN(min1) && isNaN(min2)){
		 $("#"+id).val(hours+":"+"0"+min1);
	 }
	 else if(!isNaN(hours)&& isNaN(min1) && !isNaN(min2)){
		 $("#"+id).val(hours+":"+"0"+min2);
	 }
	 else if(!isNaN(hours)&& !isNaN(minutes)){
		
		  var hrmin= hours * 60;
		  var totalMin = parseInt(hrmin) + parseInt(minutes);
		  hours = Math.floor(totalMin / 60); 
		  minutes = totalMin % 60;
		  if(hours<=9){
			  hours = "0"+hours;
		  }
		  if(minutes<=9){
			  minutes = "0"+minutes;
		  }
		  $("#"+id).val(hours+":"+minutes);
	 }
	 }
	 </script>
	 
 <script>
function clearhiredate(){
	$("#DateofJoin").val("");
	};
function clearhiredate1(){
	$("#DateofJoin").val("");
	};
	
</script>


<!-- Details View  Scripts Starts -->

<script>
var candidateStatusGlobal ="";
function profileDetails(candidateId){

	  var loginUserId = '${SourceLeadWeb_userId}';
	  var candidateType = "Associates";
	  var locationCode = '${SourceLeadWeb_locationCode}';
	  $.ajax({
		  type: "GET",
		    url: sourceleadUrl+"/getProfessionalInfo?candidateId="+candidateId+"&locationCode="+locationCode,
           success :function(json) {  
                if(json != null && json != 'undefined' && json != ""){  
                	 //var json = JSON.parse(data);
              			 candidateStatusGlobal = json.status;
	                	 $("#candidateSideDiv").empty();
	                	 var skills = [];
	                	 var skillsList ='';
	                	 if(json.skillsList != null && json.skillsList != '' && json.skillsList != 'undefined'){
	                		 skillsList = json.skillsList;
				             skills = skillsList.split(",");
	                	 }
			            var experienceMonths = '';
			            var designationType = '';
			            var country = '';
			            var state = '';
			            var city = '';
			            var currencyCodes = '';
			            
			            var currencyCode = '';
         			 	if(json.experienceMonths != null && json.experienceMonths != '' && json.experienceMonths != 'undefined'){
         			 		experienceMonths = json.experienceMonths+'-'+'Month/s';            			 		
         			 	}else{
         			 		experienceMonths = ' ';  
         			 	 }
         			 	
         			 	if(json.designation != null && json.designation != '' && json.designation != 'undefined'){
             			 	designation = 'Designation'+'-'+json.designation;
             			 	}else{
             			 		designation = '';
             			 	}
         			 	
         			 	if(json.designationType != null && json.designationType != '' && json.designationType != 'undefined'){
         			 	designationType = 'Employment Type'+ ' - '+json.designationType;
         			 	}else{
         			 		designationType = '';
         			 	}

         			 	if(json.lastName != null && json.lastName !='' && json.lastName != 'undefined'){
         			 		lastName = json.lastName;
         			 	}else{
         			 		lastName = "";
         			 	}

         			 	
         			 	if(json.country != null && json.country != '' && json.country != 'undefined'){
         			 		country =json.country;
             			 	}else{
             			 		country = '';
             			 	}
         			 	
         			 	if(json.state != null && json.state != '' && json.state != 'undefined'){
         			 		state =json.state+',';
             			 	}else{
             			 		state = '';
             			 	}
         			 	
         			 	if(json.city != null && json.city != '' && json.city != 'undefined'){
         			 		city =json.city+',';
             			 	}else{
             			 		city = '';
             			 	}
         			 	
         			 	 if(json.currentRate != null && json.currentRate != '' && json.currentRate != 'undefined'){
         			 		currentRate = json.currentRate;
         			 	 }else{
         			 		currentRate = '';
         			 	 }
                           
         			 	if(json.currentRateType != null && json.currentRateType != '' && json.currentRateType != 'undefined'){
         			 		currentRateType = json.currentRateType;
         			 	}else{
         			 		currentRateType = '';
         			 	}
         			 	
         			 	if(json.expectedRate != null && json.expectedRate != '' && json.expectedRate != 'undefined'){
         			 		expectedRate = json.expectedRate;
         			 	}else{
         			 		expectedRate = '';
         			 	}
         			 	
         			 	if(json.rateType != null && json.rateType != '' && json.rateType != 'undefined'){
         			 		rateType = json.rateType;
         			 	}else{
         			 		rateType = '';
         			 	}
         			 	
         			 	if(json.currentRateCurrency != null && json.currentRateCurrency != '' && json.currentRateCurrency != 'undefined'){
         			 		currentRateCurrency = '/'+json.currentRateCurrency;
         			 	}else{
         			 		currentRateCurrency = '';
         			 	}
         			 	
         			 	if(json.expectedRateCurrencey != null && json.expectedRateCurrencey != '' && json.expectedRateCurrencey != 'undefined'){
         			 		expectedRateCurrencey = '/'+json.expectedRateCurrencey;
         			 	}else{
         			 		expectedRateCurrencey = '';
         			 	}
         			 	
         			 	if(json.alternateMobilePhone != null && json.alternateMobilePhone != '' && json.alternateMobilePhone != 'undefined'){
         			 		alternateMobilePhone = ' , ' +json.alternateMobilePhone;
         			 	}else{
         			 		alternateMobilePhone = '';
         			 	}
         			 	
         			 	 if(json.mobilePhone != null && json.mobilePhone != '' && json.mobilePhone != 'undefined'){
         			 		mobilePhone =json.mobilePhone;
         			 	 }else{
         			 		 
         			 		mobilePhone = '--';
         			 	 }
         			 	 
	                	 var result = "";
	                	 result = result+'<div id="popUp" ></div>  ';
	                	
	                	 
	                	 result = result+'<form id="candidate_form" name="candidate_form" method="post" >';
			             result = result+'  <div class="profile_details clearfix mb">';
			             result = result+'               <div class="col-md-6">';
			             result = result+'                   <div class="media">';
			             
			             result = result+'<div class="col-xs-12 col-sm-5 col-md-5">';
			             result = result+'<div id="candidateImageId">';
			             result = result+'<div class="well well-sm text-center clearfix">';
						 result = result+' <div class="profile-img-container profile-image text-center" style="position:relative;">';
						 if(json.fileUrl != null && json.fileUrl !='' && json.fileUrl !="undefined"){
						 result = result+'   <img  id="userImage" data-id="profileImage" src="'+json.fileUrl+'" alt="Profile Image" class=" img-responsive center-block img-circle">';
						 }else{
						 result = result+'<img data-id="profileImage" id="userImage" src="/resources/images/userprofileImg.jpg" alt="Profile Image" class=" img-responsive center-block img-circle">';

						 }
						 result = result+'  <div class="btn-group uploadfile uploadfile-candidate">';
						 //result = result+'  <button type="button" class="btn btn-sm btn-primary">';
						 //result = result+'  <i class="fa fa-upload"></i></button>';
						 result = result+'  <label class="btn btn-sm btn-primary">';
					     result = result+'  <i class="fa fa-upload paddingRight"></i>Upload / Change<input type="file" name="file" id="uploadedImage"  accept="image/*" onchange="cropImage(this,\'candidate_form\','+candidateId+',false);"  style="display: none;">';
					     result = result+'  </label>';
					     if(json.fileContentType != null && json.fileContentType != '' && json.fileContentType != 'undefined'){
						     result = result+'  <button type="button" class="btn btn-sm btn-danger" onclick="deleteImage('+candidateId+')">';
							 result = result+'  <i class="fa fa-trash"></i></button>';
					     } 
						 result = result+'  </div>';
						 result = result+'  </div>';
						 result = result+'  <div class="mb mt5 UsProBar">';                                                                                                                                        
							result = result+' <div class="progress nomr">  ';
						if(json.profilePercentage>=50){ 
							result = result+'   <div class="progress-bar  progress-bar-success  ';
						}
						else{
							result = result+'   <div class="progress-bar  progress-bar-danger  ';
						}
						result = result+' progress-bar-striped active" role="progressbar" id="perVal" aria-valuenow="'+json.profilePercentage+'" aria-valuemin="0" aria-valuemax="100" style="width:'+json.profilePercentage+'%">  ';
						result = result+'     <p id="disVal">'+json.profilePercentage+'%</p>                                                                                                                                                                      ';
						result = result+'   </div>  ';
						result = result+' </div>                 ';                                                                                                                                                      
						result = result+' </div> ';
						 
						 /*  result = result+'  <div class="pull-right" style="position:relative; top:10px; right:-10px;">';
						 result = result+'  <a href="#" title="Save"><i class="fa fa-check-circle" onClick="saveCandidateImage('+candidateId+');" ></i></a>';
						 result = result+'  <a href="#" title="Close"><i class="fa fa-close text-danger"></i></a>';
						 result = result+'  </div>'; */
						 result = result+'  </div>';
						 result = result+' <p class="text-danger small" style="font-size: 11px; display: none;" id="errorImage" >Invalid Image/Size</p>';
						 result = result+' <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;png, svg and gif are not supported</p>';
						 result = result+' <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;Size should not more than 200kb</p>';
						 result = result+' </div>';
						 result = result+' </div>';
						 result = result+'</form>';
						  
			             /*  result = result+'                     <div class="media-left image-parent">';
			             result = result+'                     <div class="imageParent">'; */
			             /* result = result+'                     	<img src="./images/24.jpg" alt="candidate" title="Candidate">'; */
			             /* result = result+'<img  id="userImage" onerror="predefineProfileImage(this);" src="<c:url value="/displayUserImage/${SourceLeadWeb_userId}"/>"  alt="Profile Image" class="img-circle img-responsive">';
			             result = result+'                     </div>';
			             result = result+'                     </div>'; */
			             result = result+'                     <div class="media-body candidate-details">';
			             
			             result = result+'                       <h4 class="media-heading" title = "'+json.firstName+' '+lastName+'">'+json.firstName+' '+lastName+'</h4>';
			            
			             result = result+'                       <ul class="list-unstyled">';
			             
// 			             result = result+'                        <li>Type - '+json.candidateType+'</li>';  
			             
                         
			             result = result+'                       </ul>';

			             result = result+' <ul class="list-unstyled mrng0">';
						 result = result+'<li title="'+designation+'" class="tbldata-dotdot">'+designation+'</li>';
						 result = result+'</ul>';
						 
			             result = result+' <ul class="list-unstyled">';
						 result = result+'<li>'+designationType+'</li>';
						 result = result+'</ul>';
			             
			             result = result+'<ul class="list-unstyled">';
			              if(json.experienceYears != null && json.experienceYears != '' && json.experienceYears != 'undefined' && json.experienceYears != '0'){
			                    	 
			             result = result+'<li>Experience -'+json.experienceYears+' Year/s '+experienceMonths+'</li>';
			             
              		       }else{
              		                                                	  
              		     result = result+'<li>Experience ----</li>';  
              		     
              		       }
			             result = result+'</ul>';
			             
			             result = result+'<ul class="list-unstyled">';
			              if(json.noticePeriod != null && json.noticePeriod != '' && json.noticePeriod != 'undefined'){
			                    	 
			             result = result+'<li>Notice Period -'+json.noticePeriod+' </li>';
			             
             		       }else{
             		                                                	  
             		     result = result+'<li>Notice Period ----</li>';  
             		     
             		       }
			             result = result+'</ul>';
			             
			             result = result+'<div class="clearfix">';
			             result = result+'<div class="">';
			             
			            
			             result = result+'<ul class="list-inline list-unstyled">';
			            /*  if(json.skillsList != null && json.skillsList != '' && json.skillsList != 'undefined'){
			              for (i=0;i < skills.length;i++)
			             {
			            	  result = result+'<li>'+skills[i]+'</li>';
			             } 
			             } */
			             
			             if(json.primarySkill != null && json.primarySkill != '' && json.primarySkill != 'undefined'){
			            	 result = result+'<li>Primary Skill -'+json.primarySkill+' </li>';
			             }else{
			            	 result = result+'<li>Primary Skill ----</li>';  
			             }
			             
			             result = result+'</ul>';
			             
			             result = result+'</div>';
			             result = result+'<div class="socialnetwork-details">';
			             result = result+'<ul class="list-inline list-unstyled">';
			             if(json.consultantUser !=null && json.consultantUser.id != null && json.consultantUser.facebook != null && json.consultantUser.facebook !="undefined" && json.consultantUser.facebook != ""){
				             result = result+'<li class="bg-facebook" title="Facebook"><a target="_blank" href=\"'+json.consultantUser.facebook+'\" > <i class="fa fa-facebook-square" ></i></a></li>';
				             }
				             if(json.consultantUser !=null && json.consultantUser.id != null && json.consultantUser.twitter != null && json.consultantUser.twitter !="undefined" && json.consultantUser.twitter != ""){
				             result = result+'<li class="bg-twitter" title="Twitter"><a target="_blank" href=\"'+json.consultantUser.twitter+'\"> <i class="fa fa-twitter-square" ></i></a></li>';
				             }
				             if(json.consultantUser !=null && json.consultantUser.id != null && json.consultantUser.googlePlus != null && json.consultantUser.googlePlus !="undefined" && json.consultantUser.googlePlus != ""){
				             result = result+'<li class="bg-google " title="Google-Plus"><a target="_blank" href=\"'+json.consultantUser.googlePlus+'\"> <i class="fa fa-google-plus-square" ></i></a></li>';
				             }
				             if(json.consultantUser !=null && json.consultantUser.id != null && json.consultantUser.linkedIn != null && json.consultantUser.linkedIn !="undefined" && json.consultantUser.linkedIn != ""){
				             result = result+'<li class="bg-linked" title="Linkedin"><a target="_blank" href=\"'+json.consultantUser.linkedIn+'\"> <i class="fa fa-linkedin-square" ></i></a></li>';
				             }
			             result = result+'</ul>';
			             result = result+'                 </div>';
			             result = result+'                 </div>';
			             result = result+'               </div>';
			             result = result+'                   </div>';
			             result = result+'                 </div>';
			             result = result+'               <div class="col-md-6">';
			             result = result+'                 <ul class="list-unstyled can-perdt">';
			             result = result+'                   <li><i class="fa fa-envelope-o" title= "Email"></i><a href="#" title="'+json.email+'">'+json.email+'</a></li>';
			             result = result+'<li><i class="fa fa-phone" title= "Mobile"></i>'+mobilePhone+''+alternateMobilePhone+'</li>';
			                                                	  
			             result = result+' <li><i class="fa fa-location-arrow" title= "Location"></i>'+city+''+state+''+country+'</li>';
			             
			             result = result+'<li><i class="fa fa-money" title= "Current Rate"></i><b>Current Rate:</b>';
			             
			             result = result+''+currentRate+' '+currentRateType+''+currentRateCurrency+'';
			             
			             result = result+'</li>';
			             
			             result = result+'<li><i class="fa fa-money" title= "Expected Rate"></i><b>Expected Rate:</b>';
			             
			             result = result+''+expectedRate+' '+rateType+''+expectedRateCurrencey+'';
			             
			             result = result+'</li>';
			             
			             result = result+'</ul>';
			             
			            /* New comments div creation Nov 8th, 2016 */ 
			            if(json.status == '1' ){
			             '<sec:authorize access="hasAnyRole(\'ROLE_ASSOCIATE_COMMENTS\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';  
			            result = result+'<div class="comments-div clearfix" id="latestMessage">';
			            result = result+'    <div class="commentsimg">';
			            result = result+'    <i class="fa fa-comments"></i>';
			            result = result+'        <div class="commentsimg-inner">';
			            
			            result = result+'        	<img data-id="userProfileImage" onerror="predefineProfileImage(this);" src="${SourceLeadWeb_user.fileUrl}"/>';
			            result = result+'        </div>';
			            result = result+'    </div>';
			            result = result+'     <div class="comments-content">';
			            result = result+'         <div class="pull-left">';
			            result = result+'         <p class="comment-by"></p>';
			            result = result+'         <p class="comment-text"></p>';
			            result = result+'         </div>';
			            result = result+'         <div class="pull-right cmt-button">';
			            result = result+'          <span class="comments-button">';
			            
			            result = result+'          	<button type="button" class="btn btn-sm btn-primary" onClick="showComment('+candidateId+')">';
			            result = result+'            	<i class="fa fa-plus"></i>&nbsp;Comments';
			            result = result+'            </button>';
			            result = result+'            <div class="add-comment">';
			            result = result+'            </div>';
			            result = result+'          </span>';
			            result = result+'          <a href="javascript:void(0)" class="comments-view" onclick="candidateCommentsList('+candidateId+');">View more <span class="label label-warning">0</span></a>';
			 			result = result+'		</div>';
			            result = result+'     </div>';
			            result = result+'</div>';
			             '</sec:authorize>'; 
			            }
			 		    /* New comments div creation Nov 8th, 2016 */
			             
			             
			             
			             result = result+'               </div>';
			             result = result+'			</div>';
			             result = result+'   <div class="proj_close"></div>';
			             result = result+'   <div class="project_side_wrap_inn_tabs">';
			             result = result+'     <ul class="project-tabs-menu custom_tab">'; 
			            	 
			              '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_ASSOCIATE\',\'ROLE_VIEW_EDIT_ASSOCIATE\',\'ROLE_VIEW_DELETE_ASSOCIATE\',\'ROLE_CROWDSOURCING_CANDIDATES\')">'; 
			              result = result+'<li id="candidateProfileTab" class="current"><a href="#tab-1" onclick="candidateProfile('+candidateId+',\''+candidateType+'\');">Detailed View</a></li>';
			              '</sec:authorize>'; 
			             
			              '<sec:authorize access="hasAnyRole(\'ROLE_ADD_ASSOCIATE_RESUMES\',\'ROLE_VIEW_ASSOCIATE_RESUMES\',\'ROLE_VIEW_EDIT_ASSOCIATE_RESUMES\',\'ROLE_VIEW_DELETE_ASSOCIATE_RESUMES\',\'ROLE_CROWDSOURCING_CANDIDATES\')">'; 
			               result = result+'<li id="candidateResumeTab"><a href="#tab-2" onclick="candidateResumesList('+candidateId+'); candidateResume('+candidateId+','+json.status+');" >Resume</a></li>';
			              '</sec:authorize>'; 
			             
			              '<sec:authorize access="hasAnyRole(\'ROLE_ADD_ASSOCIATE_EMPLOYER\',\'ROLE_VIEW_ASSOCIATE_EMPLOYER\',\'ROLE_VIEW_EDIT_ASSOCIATE_EMPLOYER\',\'ROLE_VIEW_DELETE_ASSOCIATE_EMPLOYER\',\'ROLE_CROWDSOURCING_CANDIDATES\')">'; 
			               result = result+'<li id="candidateEmployersTab"><a href="#tab-3" onclick="candidateExperience('+candidateId+');">Employers</a></li>';
			              '</sec:authorize>'; 
			             
			              '<sec:authorize access="hasAnyRole(\'ROLE_ADD_ASSOCIATE_EDUCATION_DETAILS\',\'ROLE_VIEW_ASSOCIATE_EDUCATION_DETAILS\',\'ROLE_VIEW_EDIT_ASSOCIATE_EDUCATION_DETAILS\',\'ROLE_VIEW_DELETE_ASSOCIATE_EDUCATION_DETAILS\',\'ROLE_CROWDSOURCING_CANDIDATES\')">'; 
			              result = result+'<li id="candidateEducationTab"><a href="#tab-4" onclick="candidateEducation('+candidateId+');">Education</a></li>';
			              '</sec:authorize>'; 
			             
			              '<sec:authorize access="hasAnyRole(\'ROLE_ADD_ASSOCIATE_CERTIFICATION_DETAILS\',\'ROLE_VIEW_ASSOCIATE_CERTIFICATION_DETAILS\',\'ROLE_VIEW_EDIT_ASSOCIATE_CERTIFICATION_DETAILS\',\'ROLE_VIEW_DELETE_ASSOCIATE_CERTIFICATION_DETAILS\',\'ROLE_CROWDSOURCING_CANDIDATES\')">'; 
			               result = result+'<li id="candidateCertificationsTab"><a href="#tab-5" onclick="candidateCertificate('+candidateId+');">Certifications</a></li>';
			              '</sec:authorize>'; 
			             
			             <%--- This is for Documents(tab-9) Tab in Candidate Summary -----%>
			              '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_ASSOCIATE_DOCUMENT\',\'ROLE_VIEW_EDIT_ASSOCIATE_DOCUMENT\',\'ROLE_DELETE_ASSOCIATE_DOCUMENT\',\'ROLE_CREATE_ASSOCIATE_DOCUMENT\')">'; 
			             result = result+'<li id="candidateDocumentsTab"><a href="#tab-9" onclick="candidateDocuments('+candidateId+');">Documents</a></li>';
			              '</sec:authorize>'; 
			             
			             
			             result = result+'<li id="candidateReferencesTab"><a href="#tab-10" onclick="candidateReferences('+candidateId+');">References</a></li>';
			              
			             '<sec:authorize access="hasAnyRole(\'ROLE_ASSOCIATE_COMMENTS\',\'ROLE_CROWDSOURCING_CANDIDATES\')">'; 
			                result = result+'<li id="candidateCommentTab"><a href="#tab-7" onclick="candidateCommentsList('+candidateId+');">Comments</a></li>';
			               '</sec:authorize>'; 
			              
			              
			              '<sec:authorize access="hasAnyRole(\'ROLE_ASSOCIATE_REQUIREMENT_HISTORY\')">';
			              result = result+'<li id="candidateHistoryTab"><a href="#tab-8" onclick="requirementHistory('+candidateId+');">Job History</a></li>';
			              '</sec:authorize>';
			             result = result+'       <div class="clearfix"></div>';
			             result = result+'     </ul>';
			             result = result+'     <div class="project_tabs">';
				         result = result + ' <div id="tab-1" class="project_tabs_inn">';
						 result = result + '</div>';
						 
						 result = result + '<div id="tab-2" class="project_tabs_inn">';
						 result = result + '</div>';
						 
						 result = result + '<div id="tab-3" class="project_tabs_inn">';
						 result = result + '</div>';
						 
						 result = result + ' <div id="tab-4" class="project_tabs_inn">';
						 result = result + '</div>';
						 
						 result = result + '<div id="tab-5" class="project_tabs_inn">';
						 result = result + '</div>';
						 
						 result = result + '<div id="tab-9" class="project_tabs_inn">';
			             result = result+'     </div>';
			             
			             result = result + '<div id="tab-10" class="project_tabs_inn">';
			             result = result+'     </div>';
						 
						 result = result + '<div id="tab-8" class="project_tabs_inn" style="min-height:450px">';
						 result = result + '</div>';
						 
						 result = result + '<div id="tab-6" class="project_tabs_inn">';
						 result = result + '</div>';
						 result = result + '<div id="tab-7" class="project_tabs_inn">';
						 result = result + '</div>';
			             result = result+'     </div>';
			             result = result+'   </div>';
			             $("#tab-18").empty();  
			             $("#tab-19").empty();
			             $("#candidateSideDiv").empty();  
			 		     $("#candidateSideDiv").append(result);  
				 		/*  $(".clients_side_wrap").addClass("clients_side_wrap_active");
						 $(".side_mask").fadeIn(200); */
					//	 $("body").css('overflow-y', 'hidden');
						 candidateProfile(candidateId,candidateType);
			 			 
                }else{
               	 $("#candidateSideDiv").removeAttr("style");
 	    	         var result ="";
		    	     result = result+"   <div class='row'>"; 
					 result = result+"	<div class='col-lg-12'>"; 
					 result = result+"		<div class='content-area clearfix'>"; 
					 result = result+"			<div class='blank-pages'>";
					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"			        <p>No Candidates Found</p>";
					 result = result+"			 </div>";
					 result = result+"		</div>"; 
					 result = result+"	</div>"; 
					 result = result+"  </div> "; 
					$("#candidateSideDiv").empty();
				    $("#candidateSideDiv").append(result); 
                }
         },
           
      }); 
	  
	  savedCommentAndCount(candidateId);
				
}
</script>





<%-- 
     @author : Laxman.
     this script is for delete image for candidate.
     1.usage     : this script is used to delete image
--%>
<script type="text/javascript">
function deleteImage(candidateId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure you want to delete this image ?</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='deleteProfileImage("+candidateId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel("+candidateId+");'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<%-- 
     @author : Laxman.
     this script is for delete image for candidate.
     1.usage : this script is used to delete image
--%>
<script type="text/javascript">
function deleteProfileImage(candidateId){   
	var loginUserId = '${SourceLeadWeb_userId}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	 $.ajax({
		    type: "GET",
            url: sourceleadUrl+"/deleteCandidateImage?candidateId="+candidateId+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId,
	        success: function(data) {
	        	 var result = data;
	        	 
	        	 $("#popUp").empty();
	        	 $(".project_inn_mask").fadeOut(200);
	        	 $("[data-id=profileImage]").attr('src', 'resources/images/userprofileImg.jpg');
	        	 
	        	 //refreshImageBlock(candidateId);
	        	 //candidateQuickViewProfile(candidateId);
	       },
	        
     }); 
}
</script>
s')
<%-- 
     @author : Laxman.
     this script is for refresh image block
     1.usage     : 
--%>
<script>
function refreshImageBlock(candidateId){   
	  $.ajax({
          type : "POST",
           url: sourceleadUrl+"/getCandidateById?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
           success :function(data) {   
                if(data != null && data != 'undefined' && data != ""){  
                	 var json = JSON.parse(data);
	                	 $("#candidateImageId").empty();
	                	 var result = '';
			             result = result+'<div class="well well-sm text-center clearfix">';
						 result = result+' <div class="profile-img-container profile-image text-center" style="position:relative;">';
						 result = result+'   <img  id="userImage"  data-id="profileImage" onerror="predefineProfileImage(this);" src="'+json.fileUrl+'" alt="Profile Image" class=" img-responsive center-block img-circle">';
						 result = result+'  <div class="btn-group uploadfile uploadfile-candidate">';
						 //result = result+'  <button type="button" class="btn btn-sm btn-primary">';
						 //result = result+'  <i class="fa fa-upload"></i></button>';
						 result = result+'  <label class="btn btn-sm btn-primary">';
					     result = result+'  <i class="fa fa-upload paddingRight"></i>Upload / Change<input type="file" name="file" id="uploadedImage"  accept="image/*" onchange="cropImage(this,\'candidate_form\','+candidateId+',false);"  style="display: none;">';
					     result = result+'  </label>';
					     if(json.fileName != null && json.fileName != '' && json.fileName != 'undefined'){
						     result = result+'  <button type="button" class="btn btn-sm btn-danger" onclick="deleteImage('+candidateId+')">';
							 result = result+'  <i class="fa fa-trash"></i></button>';
					     } 
						 result = result+'  </div>';
						 result = result+'  </div>';
						 
						 
						 result = result+'  </div>';
						 
						 result = result+' <div class="mb mt5 UsProBar">';
						 result = result+' <div class="progress nomr"> ';
						 if(json.profilePercentage>=50){
						 result = result+' <div class="progress-bar progress-bar-success ';
						 }
						 else{
						 result = result+' <div class="progress-bar progress-bar-danger ';
						 }
						 result = result+' progress-bar-striped active" role="progressbar" id="perVal" aria-valuenow="'+json.profilePercentage+'" aria-valuemin="0" aria-valuemax="100" style="width:'+json.profilePercentage+'%"> ';
						 result = result+' <p id="disVal">'+json.profilePercentage+'%</p> ';
						 result = result+' </div> ';
						 result = result+' </div> ';
						 result = result+' <p class="text-danger small" style="font-size: 11px; display: none;" id="errorImage" >Invalid Image/Size</p>';
						 result = result+' <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;png, svg and gif images are not supported</p>';
						 result = result+' <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;Image size should not more than 200 KB</p>';
						 result = result+' </div>';
						 $("#candidateImageId").append(result);
                }
         	},
      }); 
   }
</script>


<script>
function addFavourite(candidateId){
	
	var checked = document.getElementById("fav"+candidateId).checked;
	
	 	$.ajax({
			type : "POST",
			url : sourceleadUrl+"/saveAssociateAsFavourite?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,
			success : function(result) {
				
				var td = cacheLastJson.data;
				for(var e in td) {
					if(td[e].candidateId === candidateId) {
						td[e]
						if(isAssociate == "N"){
							td[e].isFavourite = checked ? 1 : 0;
		            	} else if(isAssociate == "Y") {
		            		td[e].isFavouriteAssociate = checked ? 1 : 0;
		            	}
						break;
					}
				}
				cacheLastJson.data = td;
				
				if(checked){
					 document.getElementById("favourite"+candidateId).removeAttribute("class")
					 document.getElementById("favourite"+candidateId).className = 'star-rating activestar-rating' 
				}else{
					 //document.getElementById("favourite"+candidateId).removeAttribute("class")
					 document.getElementById("favourite"+candidateId).classList.remove("activestar-rating");
					 document.getElementById("favourite"+candidateId).className = 'star-rating' 
				}
				
			},
		}); 
		 
		
	
}
</script>


<%-- 
     @author : Laxman.
     this script is for validate image for candidate.
     1.usage     : used in profileDetails() function line number :171.
--%>
<script type="text/javascript">
function loadFile(event,candidateId){
	    var output = document.getElementById('userImage');
	    var uploadedImage = document.getElementById('uploadedImage').value;
	    var sizeinbytes = document.getElementById('uploadedImage').files[0].size;
	    document.getElementById("errorImage").style.display="none"; 
	    
	    var extension = uploadedImage.substring(uploadedImage.lastIndexOf(".")+1).toLowerCase();
	    
	    if(extension != 'jpg'){
	    	output.src = URL.createObjectURL(event.target.files[0]);
	    	
	    	var extensionName = uploadedImage.substring(uploadedImage.lastIndexOf(".")+1);
	 		var msg = "."+extensionName+" extension not supported";
	 		alertMessage(msg,candidateId);
	    }else if(sizeinbytes > 200000){
	    	output.src = URL.createObjectURL(event.target.files[0]);
	 		var msg = "Image size should be less than 200 KB";
	 		alertMessage(msg,candidateId);
	    }else{
	    	output.src = URL.createObjectURL(event.target.files[0]);
	    	var msg = "Do you want to save the picture.";
	    	saveOrCancel(msg,candidateId);
	   } 
}
</script>

<%-- 
     @author : Laxman.
     this script is for show  alert message for candidate image format.
     1.usage     : used in loadFile() function line number :422.
--%>

<script type="text/javascript">
function alertMessage(message,candidateId){
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
	refreshImageBlock(candidateId);

}
</script>

<%-- 
     @author : Laxman.
     this script is for conformation message to save file / not .
     1.usage     : used in loadFile() function line number :439.
--%>
<script type="text/javascript">
function saveOrCancel(message,candidateId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onClick='saveCandidateImage("+candidateId+");' ><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<%-- 
     @author : Laxman.
     this script is for close alert popup.
     1.usage     : used in saveOrCancel() / alertMessage function line number : 435 / 439.
--%>
<script type="text/javascript">
function cancel(candidateId){ 
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
	refreshImageBlock(candidateId);
}
</script>


<script>
function showComment(candidateId){
	var result = "";
		result = result+'	        <form role="form" id="candidateComments_form" name="candidateComments_form" method="post">';
	    result = result+'              <textarea rows="1" class="form-control clearfix mb10" placeholder="Comments here..." id="comment" name="comment" data-validation="required validate_length length2-500" field-name="Comment" onkeypress="associateListCommentRemoveSpaces();"></textarea>';
	    result = result+'              <span class="clearfix pull-right">';
	    result = result+'              		<input type="button" id="submit_comment" value="Add" class="btn btn-sm btn-success" onClick="addComment('+candidateId+')">';
	    result = result+'              		<input type="button" value="Cancel" class="btn btn-sm btn-default" onClick="closeComment()">';
	    result = result+'              </span>';
		result = result+'	        </form>';
	$('.add-comment').empty();
	$('.add-comment').append(result);
	$('.add-comment').toggle(200);
}
function closeComment(){
	$('.add-comment').toggle(200);
}
</script>

<script>
   function associateListCommentRemoveSpaces() {
	    var output = document.getElementById("comment").value;
	    output = output.replace(/\s/g, "") 
	    if(output.length != 0){
	    $("#comment").removeAttr("data-validation");
	    }
	}
</script>
<%-- 
     @author : Venkat Kesav
     this script is for saving Comment
     1.usage     : onclick
     2.parameter : candidateId.
     3.ajax url implementation in CandidateResumeRestController.
--%>
<script type="text/javascript">
	function addComment(candidateId){
			var validationSettings = {
			        errorMessagePosition : 'element',
			        borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd' 
		         }; 
			 if ($('#candidateComments_form').validate(false, validationSettings)){
				var frm = $('#candidateComments_form').serializeFormJSON();
				var con = JSON.stringify(frm);
				con = con.replace(/[{}]/g, "");
				var form = $('#candidateComments_form')[0];
	      		var formData = new FormData(form); 
				$("#submit_comment").attr("disabled", "disabled");
         		$("#submit_comment" ).addClass('button').val('Processing..');
				//It will escape all the special characters
		        var jsonData = encodeURIComponent(con);
				$.ajax({
						type : "POST",
		        	    data: formData,
		        	    contentType : 'application/json; charset=utf-8',
		        	    enctype: 'multipart/form-data',
		    	        processData: false,
		    	        contentType:false,
		        	    dataType : 'json',
						url : sourceleadUrl+"/saveCandidateComment?"+tokenParamName+"="+tokenValue+"&json="+jsonData+"&candidateId="+candidateId,
						success : function(result) {
							savedCommentAndCount(candidateId);
							 /* if( result.indexOf('Sign In') >= 0){
					    		location.replace(sourceleadUrl);
					    	 }
		            		 else if (result.indexOf('exception') >= 0) {
		            			 location.replace(sourceleadUrl+"/errorPage");
		            		 }
		            		 else{
						 		$("#popUpDivResume").removeClass("edit-details-active");
						 		candidateResumesList(candidateId);
		            		 } */
						},
					});
					$('.add-comment').toggle(200);
					return true;
			 }else{ 
					return false;
			 }
		}
		$('body').on('blur', '#candidateComments_form', function() {
		$("#candidateComments_form").showHelpOnFocus();
		$("#candidateComments_form").validateOnBlur(false, validationSettings);
		
	});
</script>




<script>
function savedCommentAndCount(candidateId){
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getAllCommentsOnCandidateByCandidateIdAndOrgLocation?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
         success :function(data) {
        	 var result = "";
              if(data != null && data != 'undefined' && data != ""){
              	 	 var json = JSON.parse(data);
            			 var totalComments = 0;
            			 if(json.length>0){
            				totalComments = json.length;
         	            	var report = json[0];
	 			            
	 			            result = result+'    <div class="commentsimg">';
				            result = result+'    <i class="fa fa-comments"></i>';
				            result = result+'        <div class="commentsimg-inner">';
				            
				            result = result+'        	<img data-id="userProfileImage" onerror="predefineProfileImage(this);" src="'+report.commentAuthorUserImage+'"/>';
				            result = result+'        </div>';
				            result = result+'    </div>';
				            result = result+'     <div class="comments-content">';
				            result = result+'         <div class="pull-left" id="latestMessage">';
				            result = result+'         <p class="comment-by">';
				            result = result+'            By <span class="text-danger">'+report.commentAuthor+'</span>';
				            result = result+'            <span class="small">('+report.commentTime+')</span></p>';
				            result = result+'         <p class="comment-text">'+report.comment+'</p>';
				            result = result+'         </div>';
				            result = result+'         <div class="pull-right cmt-button">';
				            result = result+'          <span class="comments-button">';
				            
				            result = result+'          	<button type="button" class="btn btn-sm btn-primary" onClick="showComment('+candidateId+')">';
				            result = result+'            	<i class="fa fa-plus"></i>&nbsp;Comments';
				            result = result+'            </button>';
				            result = result+'            <div class="add-comment">';
				            result = result+'            </div>';
				            result = result+'          </span>';
				            result = result+'          <a href="javascript:void(0)" class="comments-view" onclick="candidateCommentsList('+candidateId+');">View more <span class="label label-warning">'+totalComments+'</span></a>';
				 			result = result+'		</div>';
				            result = result+'     </div>';
	 			           
	 			           $("#latestMessage").empty();
	 			           $("#latestMessage").append(result);
            			 }
            			 
              }
			},
      });
}
</script>

<jsp:include page="candidatesProfile.jsp"></jsp:include>
<jsp:include page="candidatesResumes.jsp"></jsp:include>
<jsp:include page="candidatesExperience.jsp"></jsp:include>
<jsp:include page="candidatesEducation.jsp"></jsp:include>
<jsp:include page="candidatesCertificates.jsp"></jsp:include>
<jsp:include page="candidateDocuments.jsp"></jsp:include>
<jsp:include page="candidateReferences.jsp"></jsp:include>
<jsp:include page="requirementHistory.jsp"></jsp:include> 
<%--  <sec:authorize access="hasAnyRole(\'ROLE_ASSOCIATE_COMMENTS\',\'ROLE_CROWDSOURCING_CANDIDATES\')"> --%>
<jsp:include page="candidatesCommentsList.jsp"></jsp:include>
 <%-- </sec:authorize>  --%>