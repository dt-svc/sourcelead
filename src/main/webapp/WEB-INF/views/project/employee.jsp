 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
 <link href="resources/css/jquery-ui.css" rel="stylesheet">
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> 
 
 
 <style type="text/css">
 table#assignEmployeePagination tr th {
 width:auto !important;
 }
 </style>
 
      <!-- Modal content Assign Team-->
           <div role="dialog" id="assignTeam" class="modal fade">
             <div class="modal-dialog modal-lg">
               <div class="modal-content">
                 <div class="modal-header">
                   <button data-dismiss="modal" class="close" type="button">X</button>
                   <h4 class="modal-title">Assign Employees</h4>
                 </div>
                 <div class="modal-body clearfix">
                   <div class="form-horizontal row custom-form hidden">
                     <div class="col-xs-12 col-md-12 col-lg-12 ">
                       <div class="input-group">
                         <input type="search" class="form-control" placeholder="Name / Email" id="searchByName" >
                         <span class="input-group-addon bt-primary" onclick="getTeamMembers();"><i class="fa fa-search"></i></span> </div>
                     </div>
                   </div>
                   <div class="modal-body-custom mt clearfix over" id="employeesList">
                   </div>
                   <div class="col-md-12">exportType
                   
                   <div class="col-md-6">
					<div class="checkbox">
						<label> <input type="checkbox" id="membermailCheckbox" value="Y"
							name="isMailToTeamMembers"> Mail to Team Members
						</label>
					</div>
					</div>
					
                 <div class=" col-md-6 modal-footer">
                   <button  class="btn btn-primary" type="button" id="assignId" onclick="assignEmployee();">Assign</button>
                   <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                 </div>					
					
				</div>
                 </div>
               </div>
             </div>
           </div>
           <!-- Assign Team modal popup ends -->
           <!-- Modal content Add Team-->
           <div role="dialog" id="addTeam" class="modal fade">
             <div class="modal-dialog">
               <div class="modal-content" id="newEmployee">
                <form name="employee_form" id="employee_form" method="post">
                  <div class="modal-header">
                    <button data-dismiss="modal" class="close" type="button">X</button>
                    <h4 class="modal-title">Add Employees</h4>
                  </div>
                  <div class="modal-body clearfix">
                    <div class="form-horizontal custom-form clearfix">
                      <div class="col-xs-12 col-md-12 col-lg-12">
                        <div class="form-group">
                        <label class="col-lg-3">First Name<span class="text-danger">*</span></label>
                        <div class="col-lg-9">
                          <input type="text" class="form-control" value="" name="firstName" id="firstName" field-name="Frist Name" data-validation="required">
                          </div>
                        </div>
                        <div class="form-group">
                        <label class="col-lg-3">Last Name</label>
                        <div class="col-lg-9">
                          <input type="text" class="form-control" value="" name="lastName" id="lastName">
                          </div>
                        </div>
                        <div class="form-group" id="EmailIdDiv">
                        <label class="col-lg-3">Email <span class="text-danger">*</span></label>
                        <div class="col-lg-9">
                          <input type="text" class="form-control" value="" name="email" id="EmailId" field-name="Email" data-validation="required validate_email" onblur="emailVerification();">
                          </div>
                        </div>
                        <!-- <div class="form-group">
                        <label class="col-lg-3">Invoice Frequency<span class="text-danger">*</span></label>
                        <div class="col-lg-9">
                          <select class="form-control" name="invoiceFrequency" id="invoiceFrequency" field-name="Invoice Frequency" data-validation="required">
                      <option value="">-Select Frequency-</option>
                      <option>Weekly</option>
                      <option>Bi-Weekly</option>
                      <option>Semi-Monthly</option>
                      <option>Monthly</option>
                    </select>
                          </div>
                        </div> -->
                        <div class="form-group">
                        <label class="col-lg-3"></label>
                        <div class="col-lg-9">
                          <div class="checkbox">
                          <label>
                            <input type="checkbox" value="Y" name="isMailSent" checked="checked">
                            Send Invitation</label>
           					 </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="modal-footer">
                    <button class="btn btn-primary" type="button" id="add_employee" onclick="addEmployee();">Save</button>
                    <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                  </div>
                  </form>
               </div>
             </div>
           </div>
           <!-- Add Team modal popup ends --> 
 
 
 
 
 <!-- this script is for Emplyee Listfunctionality at Projects screen -->
<script>
function projectEmployee(projectId,startDateStr,endDateStr,status,projectName){

	$("#employeesLoadingImg").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	
	var sourceleadUrl ='${sourceleadUrl}';
    $.ajax({
    	type: "POST",
        url: sourceleadUrl+"/getProjectEmployeeListById?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId, 
        success: function(data) {
        	
        	var json = JSON.parse(data);
        	var projectStatus =  json.status;
        	
        	var result = "";
        	result = result + '<input type="hidden" name="projectId" id="projectId" value="'+projectId+'" >';
        	result = result + '<input type="hidden" name="startDateStr" id="startDateStr" value="'+startDateStr+'" >';
			result = result + '<input type="hidden" name="assignEndDateStr" id="assignEndDateStr" value="'+endDateStr+'" >';
        	result = result + '<input type="hidden" name="assignStatus" id="assignStatus" value="'+status+'" >';

        	result = result + '    	<div class="project_tabs_inn_head timesheet_head" style="min-height:35px">';
    		if(projectStatus != 'Completed' &&  projectStatus != '' &&  projectStatus != 'undefined' ){
    		result = result	+ '<ul>';

    		result = result +'<li>';
    		result = result +'<sec:authorize access="hasAnyRole(\'ROLE_EDIT_PROJECT_EMPLOYEES\')">';
    		result = result + '<a href="javascript:void(0)" data-target="#assignTeam" data-toggle="modal" onclick="getTeamMembers('+projectId+',\''+startDateStr+'\',\''+endDateStr+'\');"><i class="glyphicon glyphicon-plus"></i>Assign Emp</a>';
    		result = result +'</sec:authorize>';
    		result = result +'</li>';

    		result = result +'<li>';
    		result = result +'<sec:authorize access="hasAnyRole(\'ROLE_CREATE_PROJECT_EMPLOYEES\')">';
    		result = result + '<a href="javascript:void(0)" data-target="#addTeam" data-toggle="modal" onclick="flushEmployee();"  ><i class="glyphicon glyphicon-plus"></i>Add Emp</a>';
    		result = result +'</sec:authorize>';
    		result = result +'</li>';

    		result = result +'<li>';
    		result = result +' <a id="export-pop" onClick="exportFun()"><i class="glyphicon glyphicon-export"></i>&nbsp;Export</a>';
    		result = result +'<div class="export_pop">';
    		result = result +'<ul>';
    		result = result +'<li onclick="exportToPdf(\''+projectId+'\',\''+projectName+'\');">PDF &nbsp;&nbsp;<i class="fa fa-file-pdf-o"></i></li>';
    		result = result +'<li onclick="exportToExcel(\''+projectId+'\',\''+projectName+'\');">Excel &nbsp;&nbsp;<i class="fa fa-file-excel-o"></i></li>';
    		result = result +'</ul>';
    		result = result +'</div>';
    		result = result +'</li> ';
    		result = result +'</ul>';
           	}
    		result = result	+ '</span>';
    		result = result + '		</div>';
    		result = result + '        <div class="project_tabs_inn_table">';
    	    result = result + '        <div id="employeesLoadingImg"></div>';
    		if(json.projectMembersList.length!=null && json.projectMembersList.length!=''){
    	    result = result + '	<table id="projectEmployeePagination" class="display" cellspacing="0" width="100%">';
    		result = result + '	<thead>';
    		result = result + '		<tr>';
            result = result + '	 	<th>Emp name</th>';
            result = result + '	     <th>Start Date</th>';
            result = result + '	     <th>End Date</th>';
            result = result + '	     <th>Approved hrs</th>';
            result = result + '	     <th>Rejected hrs</th>';
            result = result + '	     <th>Wait for approval </th>';
            result = result + '	     <th>Saved hrs</th>';
            result = result + '	     <th>Action</th>';
            result = result + '	 </tr>';
            result = result + '	</thead>';
            result = result + '	<tbody>';
        	for (var i=0; i<json.projectMembersList.length; i++)
			{      
	        		var opt = json.projectMembersList[i];
	        		var projectEndDateStr = "";
	        		if(opt.projectEndDateStr != null && opt.projectEndDateStr != ''){
	        			projectEndDateStr = opt.projectEndDateStr;
	        		}else{
	        			projectEndDateStr = "--"
	        		}
	        		
	        		result = result + '	 <tr>';
	                result = result + '	 	<td><div><a href="<c:url value="/userSummary/'+opt.userId+'"/>""> '+opt.empName+' </a></div></td>';
	                result = result + '	     <td><div>'+opt.projectAssignedDateStr+'</div></td>';
	                if(status == 'COMPLETED'){
	                	result = result + '<td><div>'+projectEndDateStr+'</div></td>';
	                }else{
	                	if(opt.status != null && opt.status != '' && opt.status !='undefined' && opt.status != "INACTIVE"){
		                	result = result + '	     <td><div><input type="text" value="'+projectEndDateStr+'" class="project_inline_field project_inline_field_enddate" id="endDate'+opt.id+'" name = "endDate'+opt.id+'"  readonly="readonly"></div></td>';
		                }
		                else{
		                	  result = result + '	     <td><div>'+projectEndDateStr+'</div></td>';
		                }
	                }
	                result = result + '	     <td><div><input type="text" value='+opt.approvedHrs+' class="project_inline_field project_inline_field_time" readonly="readonly"></div></td>';
	                result = result + '	     <td><div><input type="text" value='+opt.rejectedHrs+' class="project_inline_field project_inline_field_time" readonly="readonly"></div></td>';
	                result = result + '	     <td><div><input type="text" value='+opt.submittedHrs+' class="project_inline_field project_inline_field_time" readonly="readonly"></div></td>';
	                result = result + '	     <td><div><input type="text" value='+opt.draftHrs+' class="project_inline_field project_inline_field_time" readonly="readonly"></div></td>';
	                if(opt.status != null && opt.status != '' && opt.status !='undefined' && opt.status != 0){
	                	result = result + '	<td><div>';
	                	if(status != 'COMPLETED'){
	                		result = result +'<sec:authorize access="hasAnyRole(\'ROLE_DELETE_PROJECT_EMPLOYEES\')">'; 
	                		result = result + '<i class="glyphicon glyphicon-trash" onClick="deleteProject('+opt.id+'\,\''+projectId+'\',\''+startDateStr+'\',\''+endDateStr+'\')";></i>';
	                		result = result +'</sec:authorize>';
	                	}
	                	result = result +'</div></td>';
                     }
                     else{
                    	 result = result + '<td><div>';
                    	 if(status != 'COMPLETED'){
                    	 result = result + '<a  onclick="reActiveProject(\''+opt.id+'\',\''+opt.empName+'\',\''+opt.projectAssignedDateStr+'\',\''+projectEndDateStr+'\',\''+opt.endDate+'\');flushReProject()" data-target="#Re-AssignProject" data-toggle="modal">Re-Assign &nbsp;<span class="show-reason"><b class="glyphicon glyphicon-comment"></b>';
                    	 result = result + '<b class="reason">'+opt.terminateReason+'</b>';
                    	 result = result + '</span></a>';
                    	 }else{
                    		 result = result + '<font color="red">&nbsp;Terminated</font>';	 
                    	 }
                    	 result = result +'</div></td>';
                     }
                    	 
	                
	                
	                
	                result = result + '	 </tr>';
			}
        	result = result + '	     </tbody>';
    	    result = result + '	 </table>   ';
    		 }else{
    		
    			 result = result + '<div class="table_nodata">';
    			 result = result + '<div class="table_nodata_inn">';
    			 result = result + '<img src="resources/images/no-data.png" />';
    			 result = result + '<span>Nothing is here to show <b>Click on Add Emp btn to add employess... </b>';
    			 result = result + '</span>';
    			 result = result + '<div class="clearfix"></div>';
    			 result = result + '</div>';
    			 result = result + '</div>';
    		 }
    		result = result + '	     </div>';
    		$("#tab-3").empty();  
    		$("#tab-2").empty();  
    	    $("#tab-1").empty();
    	    $("#employeesLoadingImg").empty();
    	    $("#tab-1").append(result);  
    	    
    	    //pagination function
    	    projectEmployeePagination();
    	    
    	    $("#tab-1").show();
    		$(".project-tabs-menu a").click(function(event) {
    		     event.preventDefault();
    		     $(this).parent().addClass("current");
    		     $(this).parent().siblings().removeClass("current");
    		     var tab = $(this).attr("href");
    		     $(".project_tabs_inn").not(tab).css("display", "none");
    		     $(tab).fadeIn();
    		 });
    		
    		if(json.length > 0)
	   		{
    			 for(var i=0; i<json.length; i++){
					 var jsonData = json[i];
					 if(jsonData.status != null && jsonData.status != '' && jsonData.status !='undefined' && jsonData.status == "IN-ACTIVE")
						   continue;
					 //Assign date of project member
					 var selected = jsonData.projectAssignedDateStr;
					 var dt = new Date(selected);
 		             dt.setDate(dt.getDate());
 		             
 		             //Main project end date
 		             var projectEndDate = jsonData.endDate;
 		             var endDate = "";
 		             if(projectEndDate != null && projectEndDate != "" && projectEndDate != 'undefined'){
 		            	 endDate = new Date(projectEndDate);
 	 		             endDate.setDate(endDate.getDate()); 
 		             }
 		             
					 $('#endDate'+jsonData.id).datepicker({
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
<script>
function projectEmployeePagination(){
    $('#projectEmployeePagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
     	}
    } );
}
</script>
<!-- this script is for Employee EndDate Inline functionality at Projects screen -->
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

<!-- this script is for Employee Terminate functionality at Projects screen -->
<script>
function deleteProject(projectMemberId, projectId,startDateStr,endDateStr){ 
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Project delete message here</p>";
	result = result +"		<textarea  id='reasonForProject' name='reasonForProject'  placeholder='Comments here' ></textarea>";
	result = result + ' <div id="reasonForProjectDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Terminate' onclick='saveReason("+projectMemberId+", "+projectId+","+startDateStr+","+endDateStr+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelTermination();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function cancelTermination(){
	$("#popUp").empty();
	 $(".project_inn_mask").fadeOut(200);
}

function saveReason(projectMemberId, projectId,startDateStr,endDateStr){
	var sourceleadUrl = '${sourceleadUrl}';
	var reason = document.getElementById("reasonForProject").value;
	if(reason.match(/^[^\s-](.*)*$/) !== null){
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
   			 	projectEmployee(projectId,startDateStr,endDateStr,status);
   		    }
	     }); 
		}
	  }
	}
	else{
		if(reason){
			$("#reasonForProjectDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Must not Start With Space</font>") ;
		}else{
			$("#reasonForProjectDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
		}
	}
}
</script>


<!-- this script is for TeamMembers List functionality at Projects screen -->
<script type="text/javascript">
function getTeamMembers(projectId,startDateStr,endDateStr) {
	if(projectId == null || projectId == '' || projectId == 'undefined'){
		projectId  = document.getElementById("projectId").value;
		startDateStr  = document.getElementById("startDateStr").value;
	}
	var sourceleadUrl ='${sourceleadUrl}';
	var startDateStr = startDateStr;
	var endDateStr = endDateStr;
	var searchByName = $("#searchByName").val();
	$.ajax({
    	type: "POST",
        url: sourceleadUrl+"/getTeamMembersList?${_csrf.parameterName}=${_csrf.token}&ProjectId="+projectId+"&searchByName="+searchByName, 
        success: function(data) {
        	var json = JSON.parse(data);
        	var output = "";
        	output = output+' <div class="wrap_employeeTable">';
        	output = output+' <table cols="0" id="assignEmployeePagination" class="table table-bordered table-striped  clearfix">';
            output = output+' <thead>';
            output = output+' <tr>';
            output = output+' <th>&nbsp;&nbsp;</th>';
            output = output+' <th>Name</th>';
            output = output+' <th>Email</th>';
            output = output+' <th>Role</th>';
            output = output+' <th>Start Date</th>';
            output = output+' <th>End Date</th>';
            output = output+' </tr>';
            output = output+' </thead>';
            output = output+' <tbody>';
        	for (var i=0; i<json.length; i++)
			{
     	       // var projectEndDate = $('#EndDate').val();
	        		var opt = json[i];
	        		var role = '';
	        		if(opt.userType != null && opt.userType != '' && opt.userType != 'undefined'){
	        			role = opt.userType; 
	        		}else{
	        			role = 'Employee';
	        		}
	        		output = output + "<tr>";
	        		output = output + "<td><input type='hidden' id='projectStartDateStr'  value="+startDateStr+"><input type='hidden' id='projectEndDateStr'  value="+endDateStr+"><input class='myCheckbox' id="+opt.id+" type='checkbox' name='team"+i+"' value="+opt.id+"></td>";
	        		output = output +"<td><span class='tbldata-dotdot-sm' title='"+opt.user.firstName+"&nbsp;"+opt.user.lastName+"'>"+opt.user.firstName+"&nbsp;"+opt.user.lastName+"</span></td>";
	        		output = output +"<td><span class='tbldata-dotdot-vsm' title='"+opt.user.email+"'>"+opt.user.email+"</span></td>";
	        		output = output +"<td><span class='tbldata-dotdot-vsm' title='"+role+"'>"+role+"</span></td>";
	        		output = output +"<td><div class='input-group'> <span class='input-group-addon'><i class='fa fa-calendar'></i></span>";
	        		output = output +"<input  id='startDate"+ opt.id+ "'   type='text' name='Date' value='"+startDateStr+"' class='form-control clsDatePicker' readonly='readonly'>";
	        		output = output + "</div></td>";
	        		output = output +"<td><div class='input-group'> <span class='input-group-addon'><i class='fa fa-calendar'></i></span>";
	        		output = output +"<input id='endDate" + opt.id + "'  type='text' name='Date' value='' class='form-control clsDatePicker' readonly='readonly'>";
	        		output = output + "</div></td>";
	        		output = output + "</tr>";
			}
        	output = output+' </tbody>';
	        output = output+' </table>';
	        output = output+' </div>';
        	$("#employeesList").empty();
	        $("#employeesList").append(output);
	        
	        assignEmployeespagination();
	        
	        //StartDate And EndDate Pickers
		       if(json.length > 0){
			    	for (var i = 0; i <json.length; i++) {
						var opt = json[i];
						if(endDateStr !=  null && endDateStr != '' && endDateStr != 'undefined'){
							$('#startDate'+opt.id).datepicker({
								dateFormat : 'yy-mm-dd',
								minDate:startDateStr,
								maxDate:endDateStr,
								onSelect : function(selected) {
									var dt = new Date(selected);
									var id = $(this).attr('id');
									var actualId = $(this).attr('id').split('startDate')[1];
									dt.setDate(dt.getDate() + 1);
									$("#endDate"+actualId).datepicker("option", "minDate", dt);
									
								}
							});

							$('#endDate'+opt.id).datepicker({
								dateFormat : 'yy-mm-dd',
								maxDate:endDateStr,
								minDate:startDateStr,
								onSelect : function(selected) {
									var dt = new Date(selected);
									var id = $(this).attr('id');
									var actualId = $(this).attr('id').split('endDate')[1];
									dt.setDate(dt.getDate() - 1);
									$("#startDate"+actualId).datepicker("option", "maxDate", dt);
								}
							});
							
						}else{
							$('#startDate'+opt.id).datepicker({
								dateFormat : 'yy-mm-dd',
								minDate:startDateStr,
								onSelect : function(selected) {
									var dt = new Date(selected);
									var id = $(this).attr('id');
									var actualId = $(this).attr('id').split('startDate')[1];
									dt.setDate(dt.getDate() + 1);
									$("#endDate"+actualId).datepicker("option", "minDate", dt);
									
								}
							});

							$('#endDate'+opt.id).datepicker({
								dateFormat : 'yy-mm-dd',
								minDate:startDateStr,
								onSelect : function(selected) {
									var dt = new Date(selected);
									var id = $(this).attr('id');
									var actualId = $(this).attr('id').split('endDate')[1];
									dt.setDate(dt.getDate() - 1);
									$("#startDate"+actualId).datepicker("option", "maxDate", dt);
								}
							});
						}
						
						
					}
		      }
        },
        error: function(e){
        }
    });
	$('#firstName').val('');
	$('#lastName').val('');}
	$('#employeeEmail').val('');
</script>

<script>
function assignEmployeespagination(){  
    $('#assignEmployeePagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10000, 15, 20, -1], [10000, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
     	}
		
    } );
}
</script>


<!-- this script is for Assign Employee functionality at Projects screen -->
<script>
function assignEmployee(){
	    $('#assignEmployeePagination').DataTable().search('').draw();
	 	var projectId = $("#projectId").val();
	 	var projectStartDateStr =  $("#projectStartDateStr").val();
	 	var projectEndDateStr =  $("#projectEndDateStr").val(); 
	 	
	    var result = "";
	    var mailtoMembers = "NO";
	    if(document.getElementById('membermailCheckbox').checked) {
	    	mailtoMembers = "YES";
	    }
	    var sourceleadUrl ='${sourceleadUrl}'; 
	    $('#assignTeam').modal('hide');
	    $(".myCheckbox").each(function(){
	        var employeeId =this.id;
	        if($(this).is(':checked')){
		        var startDate = $("#startDate"+employeeId).val();
		        var endDate = $("#endDate"+employeeId).val();
				$.ajax({
		        	type: "POST",
		            url: sourceleadUrl+"/storeAssignEmployee?${_csrf.parameterName}=${_csrf.token}&ProjectId="+projectId+"&employeeId="+employeeId+"&startDate="+startDate+"&endDate="+endDate+"&mailtoMembers="+mailtoMembers, 
		            success: function(data) {
		            	projectEmployee(projectId,projectStartDateStr,projectEndDateStr,status);
		            }
				})
	        }
    	}); 
}
</script>

<script type="text/javascript">
function flushEmployee(){
	 $("#add_employee").removeAttr("disabled");
     $('#firstName').val('');
     $('#lastName').val('');
	 $('#EmailId').val('');
	 $('#invoiceFrequency').val('');
	 $(".error").removeClass("error");
	 $('#newEmployee').find('p.jquery_form_error_message').remove();
}
</script>


<!-- this script is for Add Employee functionality at Projects screen -->
<script type="text/javascript">
	function addEmployee() {
		 if ($('#employee_form').validate(false, validationSettings)){
			var sourceleadUrl ='${sourceleadUrl}';
			var frm = $('#employee_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			//It will escape all the special characters
		    var jsonData = encodeURIComponent(con);
		    $("#add_employee").attr("disabled", "disabled");
			$.ajax({
			type: "POST",
			url: sourceleadUrl+"/addEmployeeForProjectDashboard?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
				success: function(data) {
					 $('#addTeam').modal('hide');
				},
			});
			  return true;
			 }else{
			  return false;
			 }
	}
	$('body').on('blur', '#employee_form', function() {
		$("#employee_form").showHelpOnFocus();;
		$("#employee_form").validateOnBlur(false, validationSettings);  
	});
</script>


<script>
  $('#selector').delay(2000).fadeOut('slow');
  function exportFun(){
	  $("#export-pop").next("div.export_pop").toggle();
  }
</script> 

<!--  -->

<script>
function exportToPdf(projectId,projectName){
	var sourceleadUrl ='${sourceleadUrl}';
	window.location.href = sourceleadUrl+"/exportProjectEmployeeList?&projectId="+projectId+"&projectName="+projectName+"&exportType=pdf";
}
</script>

<script>
function exportToExcel(projectId,projectName){
	var sourceleadUrl ='${sourceleadUrl}';
	window.location.href = sourceleadUrl+"/exportProjectEmployeeList?&projectId="+projectId+"&projectName="+projectName+"&exportType=xls";
}
</script>



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
function reActiveProject(projectMemberId,empName,startDate,endDate,projectEndDateStr){  
   var result = "";
   result = result + '<input type="hidden" name="projectMemberId" id="projectMemberId" value="'+projectMemberId+'" >';
   result = result +'<div class="modal-body clearfix">';
   result = result +'<div class="form-horizontal custom-form clearfix">';
   result = result +'<div class="col-xs-12 col-md-12 col-lg-12" >';
    result = result +'<div class="form-group">';
   result = result +'	<label class="col-lg-3">Employee Name </label>';
   result = result +'	<div class="col-lg-9">';
   result = result +'		<input id="reAssignProjectName" type="text" class="form-control" value="'+empName+'"  name="reAssignProjectName" readonly="readonly" class="form-control"></span>';
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
   
		  
   
  var reStatertDate = $('#Re-StartDate').val()
  if(projectEndDateStr != null && projectEndDateStr != "" && projectEndDateStr != 'undefined'){
	  $('#Re-StartDate').datepicker({
	      dateFormat: 'yy-mm-dd',
		  minDate:endDate,
		  maxDate:projectEndDateStr,
	      onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() + 1);
	            $("#Re-EndDate").datepicker("option", "minDate", dt);
	        }
	  });
	  
	  $('#Re-EndDate').datepicker({
	      dateFormat: 'yy-mm-dd',
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
	      dateFormat: 'yy-mm-dd',
		  minDate:endDate,
	      onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() + 1);
	            $("#Re-EndDate").datepicker("option", "minDate", dt);
	        }
	  });
	  
	  
	
	  $('#Re-EndDate').datepicker({
	      dateFormat: 'yy-mm-dd',
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
	 var projectMemberId =  $("#projectMemberId").val();
	 var projectId =  $("#projectId").val();
	 var projectMemberId =  $("#projectId").val();
	 var startDateStr =  $("#startDateStr").val();
	 var endDateStr =  $("#assignEndDateStr").val();
	 var status =  $("#assignStatus").val();
	 alert("status"+status);
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
	        var sourceleadUrl ='${sourceleadUrl}';
	        $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/saveReAssignProject?${_csrf.parameterName}=${_csrf.token}&json="+con+"&projectMemberId="+projectMemberId, 
		        success: function(data) {
		        	if(data="success"){
		        	 	$("#Re-AssignProject").modal('hide');
		        	 	projectEmployee(projectId,startDateStr,endDateStr,status);
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
<script type="text/javascript">
function flushReProject(){
	$('#Re-StartDate').val('');
	$('#Re-EndDate').val('');
	$(".error").removeClass("error");
	$('#assignProjectDiv').find('p.jquery_form_error_message').remove();
}
</script>