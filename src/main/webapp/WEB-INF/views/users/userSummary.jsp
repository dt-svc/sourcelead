<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png"
	href="resources/images/fav.png" />
<link
	href="https://fonts.googleapis.com/css?family=Roboto:400,300italic,300,400italic,500,700,900"
	rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/register-style.css" rel="stylesheet">
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="resources/js/formvalidator.js"></script>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/register-style.css" rel="stylesheet">
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/register-style.css" rel="stylesheet">
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<div class="wraper">
	<!-- Content Area -->
	<section class="content-wraper">
		<section class="content-header clearfix">
			<div class="pull-left">
				<h2>User Summary</h2>
			</div>
			<div class="pull-right brud-crum"><a href="dashboard">Home</a> &raquo; <a href="usersList">Users List</a> &raquo; <span id = "fname"></a> </div>				
		</section>

		<!-- Timesheet -->


		<script>
	function predefineProfileImage(imageId){
		$(imageId).attr('src','resources/images/userprofile-img.png');
	}
</script>

<script> 
$(document).ready(function(){
	 var userId = '${userId}';
	 getUserSummaryDetails(userId);
	 $('body').on('focus', '#DateofJoin', function() {
		 $('#DateofJoin').datepicker({
			 dateFormat: 'yy-mm-dd',
			});
	 });
});
</script>
		<div id="projectSideDiv"></div>
		<script>
 function getUserSummaryDetails(userId){
	 
	 var result = "";
	 var userState = '${userState}';
	 
	 result = result + ' <div class="user_summ_wrap">';
	 result = result + '<div class="project_side_wrap_inn_tabs">';
	 result = result + '    <ul class="project-tabs-menu">';
	 result = result + '       <li class="current"><a href="#tab-1" onClick="userDetails('+userId+');" >Details</a></li>';
	 result = result + '        <li><a href="#tab-2" onClick="userProjectDetails('+userId+',\'All\');">Projects</a></li>';
	 result = result + '        <div class="clearfix"></div>';
	 result = result + '   </ul>';
	 
	 
	 result = result + '<div class="project_tabs">';
	  	result = result + ' <div id="tab-1" class="project_tabs_inn">';
	  	result = result + '</div>';
	  	
	  	result = result + '<div id="tab-2" class="project_tabs_inn">';
	  	 result = result + '<div class="timesheet_head">';
	     result = result + '  	<ul>';
	     result = result + '          <li><i class="fa fa-filter"></i><span>All</span><i class="glyphicon glyphicon-play" id="appro-pop" onclick="filter();"></i>|';
		   result = result + '         	<div class="approved_pop" id="radioFilter">';
	     result = result + '            	<ul>';
	     result = result + '                	<li><input type="radio" value="All" id="c5" name="status" checked onClick="userStatusFilter(); filter(); checkk" ><label for="c5">All</label></li>';
	     result = result + ' 					<li><input type="radio" value="Active" id="c5" name="status" onClick="userStatusFilter(); filter();" ><label for="c5">Active</label></li>';
	     result = result + ' 					<li><input type="radio" value="In-Active" id="c5" name="status" onClick="userStatusFilter(); filter();" ><label for="c5">In-Active</label></li>';
	     result = result + '                 </ul>';
	     result = result + '                    </div>                	';
	     result = result + '                </li>';
	     result = result + '				<li id="assign_project_id"><a href="javascript:void(0)" data-target="#assignProject" onClick="flushProject();getUnAssignedProjects('+userId+');" data-toggle="modal"><i class="fa fa-plus"></i>Assign Project</a></li>'; 
	     		
	     result = result + '              <div class="clearfix"></div>';
	     result = result + '            </ul>';
	     result = result + '          <div class="clearfix"></div>';
	     result = result +'<div id="projectList_User"></div>'; 
	     result = result + '     </div>';
	  	
	  	result = result + '</div>';
	  	
	  	
	  	
	  	result = result + '</div>'; 
	  	$("#projectSideDiv").empty(); 
	    $("#projectSideDiv").append(result);  	
	  	
	  	userDetails(userId);
 }
</script>

		<script>
 function userDetails(userId){    
	 var sourceleadUrl ='${sourceleadUrl}';
	 var projectsCount = '${assignedProjectsCount}';
	 var totalOpenTimeSheetCount = '${totalOpenTimeSheetCount}';
	 var approvedTimeSheetsCount = '${approvedTimeSheetsCount}';
	 var rejectedTimeSheetsCount = '${rejectedTimeSheetsCount}';
	 var waitingTimeSheetsCount = '${waitingTimeSheetsCount}';
	 
	 $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getUserDetailsById?${_csrf.parameterName}=${_csrf.token}&userId="+userId, 
	        success: function(data) {
	        	
			    var json = JSON.parse(data);
			     
			    var result = "";
			    var submissionCountsResult = "";
			    
			    submissionCountsResult = '<p>Projects<strong class="label label-default">'+projectsCount+'</strong></p>';
            	submissionCountsResult = submissionCountsResult +'<p>Approved Timesheets<strong class="label label-success">'+approvedTimeSheetsCount+'</strong></p>';
            	submissionCountsResult = submissionCountsResult +'<p>Rejected Timesheets<strong class="label label-warning">'+rejectedTimeSheetsCount+'</strong></p>';
            	submissionCountsResult = submissionCountsResult +'<p>Waiting for Timesheets<strong class="label label-primary">'+waitingTimeSheetsCount+'</strong></p>';
            	submissionCountsResult = submissionCountsResult +'<p>Open Timesheets<strong class="label label-info">'+totalOpenTimeSheetCount+'</strong></p>';
			    
				result = result + '<div class="project_tabs_inn_table">';
				firstname(json.user.firstName);
				
				result = result + '  <div class="project_details">';
				if(json.user.userState != null && json.user.userState != "" && json.user.userState != 'INACTIVE'){
				    result = result + ' <div class="useredit">';
						result = result + ' <a id="edit-user" href="javascript:editUserDetails('+userId+');"><i class="glyphicon glyphicon-pencil"></i>Edit</a>';
						result = result + ' <a id="remove-user" href="javascript:removeUser();"><i class="glyphicon glyphicon-trash"></i>Remove</a>';
							result = result + ' <div id="remove_user_id" class="remove_user_pop" >';
									result = result +'<textarea placeholder="Comments here" id="reason" name="reason"/></textarea>';
									result = result + ' <div id="reasonDiv"/>';
									result = result + ' <b><input id="save_reason" value="Submit" onClick="terminateUser('+userId+');" type="button"/><input class="u_can_color" onclick="cancelUser();" value="Cancel" type="button"></b>';
						     result = result + ' </div>';
				     result = result + ' </div>';
				}
				
				else{
                    result = result + '<i class="glyphicon glyphicon-user"></i><a id="edit-user" href="javascript:reActiveUser('+userId+');">&nbsp;Re-Active</a>';
                }
				
				result = result + '	 <div class="user_details_cta_img">';
				result = result + '		       <div class="user_change_img">';
				result = result +                  "<img onerror='predefineProfileImage(this);' src='"+sourceleadUrl+"/displayUserImage/"+userId+"' alt='image'>";
				result = result + ' 		  </div>';
				result = result + '             <span>'+json.user.firstName+"&nbsp"+json.user.lastName+'</span>';
				result = result + ' <div class="user_badg" id="counts">';
				result = result +submissionCountsResult;
				result = result + '	</div>';
				result = result + '		 </div>';
				
				result = result + '	     <div class="user_details_cta">';
				result = result + '	     <div id="viewUserDiv">';
				if(json.employeeId != null && json.employeeId != "" && json.employeeId != 'undefined'){
					result = result + ' 		<div class="project_details_cta">';
					result = result + '				 <strong>Employee Id:</strong><span class="fix">'+json.employeeId+'</span><div class="clearfix"></div>';
					result = result + ' 		</div>';
				}
				
				
				result = result + '			<div class="project_details_cta">';
				result = result + '				 <strong>First Name:</strong><span class="fix">'+json.user.firstName+'</span><div class="clearfix"></div>';
				result = result + ' 		</div>';
				
					
				
				
				if(json.user.lastName != null && json.user.lastName != "" && json.user.lastName != 'undefined'){
					result = result + ' 		<div class="project_details_cta">';
					result = result + '				 <strong>Last Name:</strong><span class="fix">'+json.user.lastName+'</span><div class="clearfix"></div>';
					result = result + ' 		</div>';
				}
				
				if(json.user.email != null && json.user.email != "" && json.user.email != 'undefined'){
					result = result + '			<div class="project_details_cta">';
					result = result + '				 <strong>Email Id:</strong><span class="fix">'+json.user.email+'</span><div class="clearfix"></div>';
					result = result + ' 		</div>';
				}
				
				if(json.user.mobilePhone != null && json.user.mobilePhone != "" && json.user.mobilePhone != 'undefined'){
					result = result + '			<div class="project_details_cta">';
					result = result + '				 <strong>Phone Number:</strong><span class="fix">'+json.user.mobilePhone+'</span><div class="clearfix"></div>';
					result = result + ' 		</div>';
				}
				
				if(json.rate != null && json.rate != "" && json.rate != 'undefined'){
					result = result + '			<div class="project_details_cta">';
					result = result + '				 <strong>Rate:</strong><span class="fix">'+json.rate+'</span><div class="clearfix"></div>';
					result = result + ' 		</div>';
				}
				
				if(json.ratePer != null && json.ratePer != "" && json.ratePer != 'undefined'){
					result = result + ' 		<div class="project_details_cta">';
					result = result + '				 <strong>Rate Per:</strong><span class="fix">'+json.ratePer+'</span><div class="clearfix"></div>';
					result = result + '			</div>';
				}
				
				result = result + '			<div class="project_details_cta">';
				result = result + '					<strong>Role:</strong><span class="fix">'+json.user.userType+'</span><div class="clearfix"></div>';
				result = result + '			</div>';
				
				if(json.InvoiceFrequency != null && json.InvoiceFrequency != "" && json.InvoiceFrequency != 'undefined'){
					result = result + ' 		<div class="project_details_cta">';
					result = result + '				<strong>Invoice Frequency:</strong><span class="fix">'+json.InvoiceFrequency+'</span><div class="clearfix"></div>';
					result = result + '			</div>';
				}
				
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
				
				if(json.user.userState != null && json.user.userState != "" && json.user.userState == 'INACTIVE'){
					result = result + '			<div class="project_details_cta">';
					result = result + '				<strong>Reason:</strong><span class="fix">'+json.user.terminateReason+'</span><div class="clearfix"></div>';
					result = result + '			</div>';
				}
				
				
				result = result + '	   </div>';				
				result = result + ' <div class="user_summary_edit" id="editUserDiv"></div>';
				result = result + '	  <div class="clearfix"></div>';
				result = result + '	   </div>';
				result = result + '	  <div class="clearfix"></div>';
				result = result + '	</div>';
				result = result + '</div>';
				
				
				$("#tab-1").empty(); 
			    $("#tab-1").append(result);  
	    
	      },
	});
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
 function userProjectDetails(userId,filterValue){    
	 var sourceleadUrl ='${sourceleadUrl}';
	 
	 $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getUserDetailsById?${_csrf.parameterName}=${_csrf.token}&userId="+userId, 
	        success: function(data) {
			    var json = JSON.parse(data);
				if(json.user.userState != null && json.user.userState != "" && json.user.userState == 'INACTIVE'){
				  $("#assign_project_id").empty();
				}else{
					$("#assign_project_id").empty().append("<a href='javascript:void(0)' data-target='#assignProject' onClick='flushProject();getUnAssignedProjects("+userId+");' data-toggle='modal'><i class='fa fa-plus'></i>Assign Project</a>") ;
				}
	      }
	 });
	 
	 
	 $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getProjectDetailsById?${_csrf.parameterName}=${_csrf.token}&userId="+userId+"&filterValue="+filterValue, 
	        success: function(data) {
			    var jsonData = JSON.parse(data);
	    		var result = "";
	    		if(jsonData.length > 0)
		   		{
						   result = result + '        <div class="user_summ_wrap_table">';
						   result = result + '	<table>';
						   result = result + '    	<tbody>';
						   result = result + '      	<tr>';
						   result = result + '         	<th>Project Name</th>';
						   result = result + '             <th>Start Date</th>';
						   result = result + '            <th>End Date</th>';
						   result = result + '             <th>Approved hrs</th>';
						   result = result + '              <th>Rejected hrs</th>';
						   result = result + '             <th>Wait for approval</th>';
						   result = result + '             <th>Saved</th>';
						   result = result + '                 <th>Action</th>';
						   result = result + '         </tr>';
						   for(var i=0; i<jsonData.length; i++){
							   var json = jsonData[i];
							   var startDate = "";
							   var endDate = "";
							   var approvedHrs = "";
							   var rejectedHrs = "";
							   var draftHrs = "";
							   var submittedHrs = "";
						    if(json.startDate != null && json.startDate != '' && json.startDate != 'undefined'){
							 startDate = json.startDate ;
						   }
						   if(json.endDate  != null && json.endDate  != '' && json.endDate  != 'undefined'){
						    	endDate = json.endDate;
						   }
						   if(json.approvedHrs  != null && json.approvedHrs  != '' && json.approvedHrs  != 'undefined'){
							   approvedHrs = json.approvedHrs;
						   }
						   if(json.rejectedHrs  != null && json.rejectedHrs  != '' && json.rejectedHrs  != 'undefined'){
							   rejectedHrs = json.rejectedHrs;
						   }
						   if(json.draftHrs  != null && json.draftHrs  != '' && json.draftHrs  != 'undefined'){
							   draftHrs = json.draftHrs;
						   }
						   if(json.submittedHrs  != null && json.submittedHrs  != '' && json.submittedHrs  != 'undefined'){
							   submittedHrs = json.submittedHrs;
						   }
						   result = result + '       <tr>';
						   result = result + '    	<td><div>'+json.projectName+'</div></td>';
						  
						   result = result + '<td><div id="startDate'+json.id+'">'+startDate+'</div></td>';
						  result = result + ' <td><div><input type="text" value="'+endDate+'" id="endDate'+json.id+'" readonly name = "endDate'+json.id+'" class="project_inline_field"  /></div></td>';
						   result = result + ' <td><div>'+approvedHrs+'</div></td>';
						   result = result + ' <td><div>'+rejectedHrs+'</div></td>';
						   result = result + ' <td><div>'+draftHrs+'</div></td>';
						   result = result + ' <td><div>'+submittedHrs+'</div></td>';
						   if(json.status != null && json.status != '' && json.status !='undefined' && json.status != "INACTIVE"){
                               result = result + '<td><div><i id="dele-user" class="glyphicon glyphicon-trash"  onClick="deleteProject(\''+json.id+'\',\''+userId+'\',\''+json.projectName+'\')"; ></i>';
                            }
                            else{
                                 result = result + '<td><div><font color="red">Terminated</font></div></td>';
                            }
							result = result + '</div></td> '; 
						   
						   result = result + '   </tr>';
						   }
						   result = result + '        </tbody>';
						   result = result + '    </table>            ';
						   result = result + '        </div>';
							 			
						   /*  result = result + ' <nav>';
						    result = result + '<ul id="paging" class="pagination">';
						    result = result + '	<li><a href="#">Previous</a></li>';
						    result = result + '	<li><a href="#">3</a></li>';
						    result = result + '	<li><a href="#">4</a></li>';
						    result = result + '<li><a href="#">5</a></li>';
						    result = result + '<li class="disabled"><a href="#">6</a></li>';
						    result = result + '<li data-page="last"><a href="#">Next</a></li>';
						    result = result + '</ul>';
						    result = result + '<div class="clearfix"></div>';
						    result = result + '</nav>		';	 */
						
						    result = result + '   </div>';
						    
						    result = result + ' </div>';
						    result = result + '</div> ';
						
						    result = result + '<div class="project_inn_mask"></div>';
						    result = result + '<div class="project_error">';
						    result = result + '	<p>User delete message here ....</p>';
						    result = result + '	<a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> ';
						    result = result + '</div>';
						
						    result = result + '</div>';
						    $("#projectList_User").empty(); 
							$("#projectList_User").append(result);
		   		}
	    		else{
	    			result = "<div class='content-area mt text-center text-muted'><h3>Project List is Empty</h3></div>";
	    			$("#projectList_User").empty(); 
					$("#projectList_User").append(result); 
	    		}
	    		if(jsonData.length > 0)
		   		{
	    			 for(var i=0; i<jsonData.length; i++){
						   var json = jsonData[i];
	    			 
			    			var selected = json.startDate;
							 var dt = new Date(selected);
				             dt.setDate(dt.getDate());
				             
				             //Main project end date
				             var projectEndDate = json.projectEndDateStr;
				             var endDate = "";
				             if(projectEndDate != null && projectEndDate != "" && projectEndDate != 'undefined'){
				            	 endDate = new Date(projectEndDate);
			 		             endDate.setDate(endDate.getDate()); 
				             }
				             
							 $('#endDate'+json.id).datepicker({
					    		      dateFormat: 'yy-mm-dd',
					    		      minDate: dt,
					    		      maxDate: endDate,
					    		      onClose: function() {
					    		    	  	 var id = $(this).attr('id');
											 var projectMemberId = $(this).attr('id').split('endDate')[1];
					    		        	 inlineEditForEndDate(id,projectMemberId);
					    		      }
					    		  }); 
	    			 }
		   		}
	        },
		});
	 }
</script>
		<!-- Timesheet -->

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
	<i class="fa fa-trash"></i><span>User successfully terminated</span>
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
	 var DayHours = document.getElementById("DayHours").value; 
	 $('#DayHoursDiv').find('p.jquery_form_error_message').remove();
	if(DayHours > 24){
		 $("input#DayHours").after("<p class='jquery_form_error_message'>Day Hours should less than 24 Hours</span>");
		 document.getElementById("DayHours").setAttribute('record-exist','yes');
		 document.getElementById("DayHours").setAttribute('record-exist-errorMsg','Day Hours should less than 24 Hours');
	}else{
		 $('#DayHoursDiv').find('p.jquery_form_error_message').remove();
		 $('input#DayHours').removeAttr( "record-exist record-exist-errormsg");
	}
	
 }
</script>


<script type="text/javascript">
 function validateEmployeeId(id,userId) {
	 $('input#EmployeeId').removeAttr( "record-exist record-exist-errormsg");
	 $('#EmployeeIdDiv').find('p.jquery_form_error_message').remove(); 
	 var EmployeeId = document.getElementById("EmployeeId").value; 
	 var sourceleadUrl ='${sourceleadUrl}';
        if(EmployeeId!=null&&EmployeeId!=""){
        	$("#update_user").attr("disabled", "disabled");
            $( "#update_user" ).addClass('button').val('Processing..');
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateExistingEmployeeId?${_csrf.parameterName}=${_csrf.token}&id="+id+"&userId="+userId+"&EmployeeId="+EmployeeId, 
		        success: function(data) {
		        	if(data == true){
						  $("input#EmployeeId").after("<p class='jquery_form_error_message'>"+EmployeeId+" is Already Existed</span>");
						  document.getElementById("EmployeeId").setAttribute('record-exist','yes');
						  document.getElementById("EmployeeId").setAttribute('record-exist-errorMsg',EmployeeId+' is Already Existed'); 
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
     var sourceleadUrl ='${sourceleadUrl}';
     var Rate = document.getElementById("Rate").value;
     if(Rate != null && Rate != ""){
         $('#RatePer').removeAttr('data-validation-optional');
         $('#RatePer').attr("data-validation","required");
     }else{
         $('#RatePer').removeAttr('data-validation');
         $('#RatePer').attr("data-validation-optional","true");
     }
      if ($('#editUser_form').validate(false, validationSettings)){
    	  if(EmployeeId!=null&&EmployeeId!="" && empId != EmployeeId){
          $.ajax({
                type: "POST",
                url: sourceleadUrl+"/validateExistingEmployeeId?${_csrf.parameterName}=${_csrf.token}&id="+id+"&userId="+userId+"&EmployeeId="+EmployeeId,
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
                        var sourceleadUrl ='${sourceleadUrl}';
                        $("#update_user").attr("disabled", "disabled");
                        $( "#update_user" ).addClass('button').val('Processing..');
                        $.ajax({
                            type: "POST",
                            url: sourceleadUrl+"/updateUser?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&userId="+userId+"&emailId="+emailId,
                            success: function(result) {
                                userDetails(userId);
                                
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
                      url: sourceleadUrl+"/validateExistingEmployeeEmailId?${_csrf.parameterName}=${_csrf.token}&id="+id+"&userId="+userId+"&EmailId="+EmailId,
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
                              var sourceleadUrl ='${sourceleadUrl}';
                              $("#update_user").attr("disabled", "disabled");
                              $( "#update_user" ).addClass('button').val('Processing..');
                              $.ajax({
                                  type: "POST",
                                  url: sourceleadUrl+"/updateUser?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&userId="+userId+"&emailId="+emailId,
                                  success: function(result) {
                                      userDetails(userId);
                                      
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
                  var sourceleadUrl ='${sourceleadUrl}';
                  $("#update_user").attr("disabled", "disabled");
                  $( "#update_user" ).addClass('button').val('Processing..');
                  $.ajax({
                      type: "POST",
                      url: sourceleadUrl+"/updateUser?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&userId="+userId+"&emailId="+emailId,
                      success: function(result) {
                          userDetails(userId);
                          
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
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 



<script>
function editUserDetails(userId){
 var sourceleadUrl ='${sourceleadUrl}';
	 
	 $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getUserDetailsById?${_csrf.parameterName}=${_csrf.token}&userId="+userId, 
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
				
				var selected8 = '';
				var selected9 = '';
				var selected10 = '';
				var selected11 = '';
				
				if(json.InvoiceFrequency == 'Weekly'){
					selected8 = 'selected';
					} 
				
				if(json.InvoiceFrequency == 'Bi-Weekly'){
					selected9 = 'selected';
					} 
				
				if(json.InvoiceFrequency == 'Semi-Monthly'){
					selected10 = 'selected';
					}
				
				if(json.InvoiceFrequency == 'Monthly'){
					selected11 = 'selected';
					} 
				
				var employeeId = "";
			    var lastName = "";
			    var rate = "";
			    var mobilePhone = "";
			    var dailyHours = "";
			    var emailId= "";
				
				if(json.employeeId != null && json.employeeId != "" && json.employeeId != 'undefined'){
					employeeId = json.employeeId;
				}
				
				if(json.user.lastName != null && json.user.lastName != "" && json.user.lastName != 'undefined'){
					lastName = json.user.lastName;
				}
				
				if(json.rate != null && json.rate != "" && json.rate != 'undefined'){
					rate = json.rate;
				}
				
				if(json.user.mobilePhone != null && json.user.mobilePhone != "" && json.user.mobilePhone != 'undefined'){
					mobilePhone = json.user.mobilePhone;
				}
				
				if(json.dailyHours != null && json.dailyHours != "" && json.dailyHours != 'undefined'){
					dailyHours = json.dailyHours;
				}
				
				if(json.user.email != null && json.user.email != "" && json.user.email != 'undefined'){
					emailId = json.user.email;
				}
				result = result + ' <form id="editUser_form"  name="editUser_form" method="post">';
					result = result + ' <div class="user_summary_edit_cta">';
					result = result + ' <label>First name<span class="text-danger">&nbsp; *</span></label>';
					result = result + ' <input type="text" value="'+json.user.firstName+'" name="FirstName" field-name="First Name" data-validation="required validate_Space validate_length length2-100"/>';
					result = result + ' </div>';
					
					result = result + ' <div class="user_summary_edit_cta">';
					result = result + ' <label>Last name</label>';
					result = result + ' <input type="text" value="'+lastName+'" name="LastName" data-validation="validate_Space validate_length length2-100" data-validation-optional="true">';
					result = result + ' </div>';
					
					result = result + ' <div class="user_summary_edit_cta" id="EmployeeIdDiv">';
					result = result + ' <label>Employee Id</label>';
					result = result + ' <input type="text" value="'+employeeId+'" name="EmployeeId" id="EmployeeId" data-validation="validate_Space validate_length length2-100" data-validation-optional="true" onKeyUp="validateEmployeeId('+json.id+','+json.user.id+');">';
					result = result + ' </div>';
					
					result = result + ' <div class="user_summary_edit_cta">';
					result = result + ' <label>Phone number</label>';
					result = result + ' <input type="text" value="'+mobilePhone+'" name="PhoneNumber" data-validation="validate_Space validate_phone validate_length length9-20" data-validation-optional="true">';
					result = result + ' </div>';
					
					if(json.user.userState == "NOT_LOGGEDIN"){
						
						result = result + ' <div class="user_summary_edit_cta" id="EmailIdDiv">';
						result = result + ' <label>Emaild Id<span class="text-danger">&nbsp; *</span></label>';
						result = result + ' <input type="text" value="'+json.user.email+'"  id="EmailId" name="EmailId" field-name="EmailId" data-validation="required validate_Space validate_length length2-100 validate_email"/>';
						result = result + ' </div>';
					
					}
					else{
						result = result + ' <div class="user_summary_edit_cta" id="EmailIdDiv">';
						result = result + ' <label>Emaild Id<span class="text-danger">&nbsp; *</span></label>';
						result = result + ' <input type="text" value="'+json.user.email+'" readonly="readonly" id="EmailId" name="EmailId" field-name="EmailId" data-validation="required validate_Space validate_length length2-100"/>';
						result = result + ' </div>';
					}
					result = result + ' <div class="user_summary_edit_cta">';
					result = result + ' <label>Rate</label>';
					result = result + ' <input type="text" class="rate_filed" value="'+rate+'" id="Rate"  name="Rate" data-validation="validate_Space validate_float validate_length length2-8" data-validation-optional="true">';
					result = result + ' <select class="rate_sele" id="RatePer" name="RatePer" field-name="Rate Per">';
					result = result + ' <option value="">-Rate Per-</option>';
					result = result + ' <option '+selected1+'>Hourly</option>';
					result = result + ' <option '+selected2+'>Daily</option>';
					result = result + ' <option '+selected3+'>Weekly</option>';
					result = result + ' <option '+selected4+'>Bi-Weekly</option>';
					result = result + ' <option '+selected5+'>Semi-Monthly</option>';
					result = result + ' <option '+selected6+'>Monthly</option>';
					result = result + ' <option '+selected7+'>Yearly</option>';
					result = result + ' </select>';
					result = result + ' </div>';	
					
					result = result + ' <div class="user_summary_edit_cta">';
					result = result + ' <label>Date of join</label>';
					if(json.hireDateStr != null && json.hireDateStr != "" && json.hireDateStr != "undefined"){
						result = result + ' <input type="text" value="'+json.hireDateStr+'" name="DateofJoin" readonly="readonly">';
					}else{
						result = result + ' <input type="text" value="'+json.hireDateStr+'" name="DateofJoin" id="DateofJoin" readonly="readonly">';
					}
					result = result + ' </div>';	
					
					result = result + ' <div class="user_summary_edit_cta">';
					result = result + ' <label>Invoice frequency</label>';
					result = result + ' <select id="InvoiceFrequency" name="InvoiceFrequency">';
					result = result + ' <option value="">-Select Frequency-</option>';
					result = result + ' <option '+selected8+'>Weekly</option>';
					result = result + ' <option '+selected9+'>Bi-Weekly</option>';
					result = result + ' <option '+selected10+'>Semi-Monthly</option>';
					result = result + ' <option '+selected11+'>Monthly</option>';
					result = result + ' </select>';
					result = result + ' </div>';
					
					result = result + ' <div class="user_summary_edit_cta" id="DayHoursDiv">';
					result = result + ' <label>Day hours</label>';
					result = result + ' <input type="text" value="'+dailyHours+'" name="DayHours" id="DayHours" data-validation="validate_Space validate_int validate_length length1-2" data-validation-optional="true" onblur="validateDayHours();">';
					result = result + ' </div>';
					
					result = result + ' <div class="clearfix"></div>';
					result = result + ' <div class="user_summary_edit_cta">';
					result = result + ' <input class="us-btn" value="Save" id="update_user" type="button" onclick="updateUser('+json.id+','+userId+',\''+json.user.email+'\',\''+json.employeeId+'\');" > <input class="us-btn us-btn_a" value="Cancel" onClick="editUserDetails('+userId+');" type="button">';
					result = result + ' </div>';
					
					result = result + ' <div class="clearfix"></div>';
					result = result + ' </form>';
				result = result + ' </div>';
			  
				$(".user_summary_edit").slideToggle(100);
				$("#remove_user_id").slideUp(100);
				$(".error").removeClass("error");
				$('#editUserDiv').find('p.jquery_form_error_message').remove();
			    $("#editUserDiv").empty(); 
			    $("#editUserDiv").append(result);  
	        },
		});
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
function removeUser(){
	$("#reason").val('');
	$("#reasonDiv").empty();
	$("#remove_user_id").slideToggle(100);
	$(".user_summary_edit").slideUp(100);
}
</script>
<script>
function cancelUser(){
	$("#remove_user_id").slideUp(100);
}
</script>


<script>
function terminateUser(userId){   
	var sourceleadUrl = '${sourceleadUrl}';
	var reason = document.getElementById("reason").value;
	if(reason){
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
		$("#reasonDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
	}
}

</script>


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
	$("#tab-1").show()
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
	
	var sourceleadUrl = '${sourceleadUrl}';
	var projectId = document.getElementById("proId").value;
	if(projectId != null && projectId != ""){
    $.ajax({
    	type: "POST",
        url: sourceleadUrl+"/getStartAndEndOfProject?${_csrf.parameterName}=${_csrf.token}&&projectId="+projectId, 
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
	        var sourceleadUrl ='${sourceleadUrl}';
	        $("#saveAssignProject").attr("disabled", "disabled");
     		$("#saveAssignProject" ).addClass('button').val('Processing..');
	        
	        $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/saveAssignProject?${_csrf.parameterName}=${_csrf.token}&json="+con+"&userId="+userId, 
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
	        
	        var sourceleadUrl ='${sourceleadUrl}';
	        
	        $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getUnassignedProjects?${_csrf.parameterName}=${_csrf.token}&userId="+userId, 
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
	  $('#StartDate').datepicker({
	      dateFormat: 'yy-mm-dd',
	      onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() + 1);
	            $("#EndDate").datepicker("option", "minDate", dt);
	        }
	  });
	  
	  $('#EndDate').datepicker({
	      dateFormat: 'yy-mm-dd',
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
	var sourceleadUrl = '${sourceleadUrl}';
	var reason = document.getElementById("reasonForProject").value;
	
	if(reason){
		$("#reasonForProjectDiv").empty();
		
		var r = confirm("Do you want terminate the user from Project");
		if (r == true) {
	
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/saveReason?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+projectMemberId+"&reason="+reason, 	   
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
	var sourceleadUrl = '${sourceleadUrl}';
	 var endDate = document.getElementById(id).value;
	  $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/updateEndDateByProjectMemberId?${_csrf.parameterName}=${_csrf.token}&endDate="+endDate+"&projectMemberId="+projectMemberId, 	   
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
    var sourceleadUrl = '${sourceleadUrl}';
         $.ajax({
               type: "POST",
            url: sourceleadUrl+"/reActiveUser?${_csrf.parameterName}=${_csrf.token}&userId="+userId,        
               success: function(data) {
            	   userDetails(userId);
               }
       });
    
}

</script>


<!-- end project scripts -->
