<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
<style type="text/css">
.side-bar-active .subMenu{
    display: none;
}
.side-bar-active .fa-fw {
  position: absolute !important; right: 0 !important;
}
.countShow {
display: none;  border: none; min-width: 10px; text-align: center; cursor: pointer; max-width: 12px; 
}
.remove_user_pop.remove_user_pop_a {
top:20% !important;
left: 115px !important;
}
.prRight.prRight_new {
    right: 280px; top: 40px;
}
</style>
 <style type="text/css">
 table#assignEmployeePagination tr th {
 width:auto !important; height: auto; line-height: auto; vertical-align: middle; text-align:center;
 }
 #assignEmployeePagination_wrapper .dataTables_filter {
 	top:75px !important;
 }
 </style>
 
      <!-- Modal content Assign Team-->
           <div role="dialog" id="assignTeam" class="modal fade modalAssignTeam">
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
                   <div class="col-md-12">
                   
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
           
           
           
           
<div class="side_mask">
    <div class="proj_close"></div>
</div> 
<div id="associatesList"></div>
 <input type="hidden" id="employeeEmptyListId" name="employeeEmptyListId">
 
 <script>
 /* function filterStatus() {
 $(".export_pipline").toggle();
 //$("#exportPop").slideUp();
}; */
function filterStatus() {
	$("#exportPop").hide();
};
 </script>
 

 
 <script>
 function projectEmployee(projectId,startDateStr,endDateStr,status,projectName){
	 
	 var projectUUID = '${projectUUID}';
	 var sourceleadUrl = '${sourceleadUrl}';
	 var postedOrgLocationId = '${postedOrgLocationId}';
	 var loginOrgLocationId = '${SourceLeadWeb_orgLocations.id}'
	 var projectClientUUID = '${projectClientUUID}';
	 	 
	   $("#employeesLoadingImg").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;'); 
 	 
	   var resourceRequestsCount = '${resourceRequestsCount}';
	   var projectMembersCount = '${projectMembersCount}';
	   var projectBudgetCount = '${projectBudgetCount}';
	   
	   var result = "";
 	 
 	result = result + '    	<div class="col-md-12 mt">';
 	
 	/* Dont remove this commented code. Need to implement functionality */

 	/*  result = result + '    	<div class="require_one">';
	result = result + '       <div class="require_wrap_details_cta noPad">';
	result = result + '   		<div class="NeDe"><b>Estimated Budget:</b> <span><strong>INR</strong> 250000</span><div class="clearfix"></div></div>';
	result = result + '        	<div class="NeDe"><b>Estimated Hours:</b> <span>250</span><div class="clearfix"></div></div>';
	result = result + '       </div>';
	result = result + '       <div class="require_wrap_details_cta noPad">';
	result = result + '           <div class="NeDe"><b>Actual Budget:</b> <span><strong class="pull-left">INR</strong> <div class="text-success pull-left">150000</div></span><div class="clearfix"></div></div>';
	result = result + '        	<div class="NeDe"><b>Actual Hours:</b> <span>250</span><div class="clearfix"></div></div>';
	result = result + '        	<div class="NeDe"><b>Expenses:</b> <span><strong>INR</strong> 50000</span><div class="clearfix"></div></div>';
	result = result + '       </div>';
	result = result + '       <div class="clearfix"></div>';
	result = result + '     </div>'; */
	result = result + '     </div>';
	result = result + '       <div class="clearfix"></div>';
	
	
result = result + '<div class="newPrTabs col-md-12">';
result = result + '<ul class="newPrTabsMenu">';
result = result + '<li class="current"><a href="#tab-a" id="projectMembersTab" >Resources ('+projectMembersCount+')</a></li>';

/* Dont delete this commented code. Functionality is in progress '+projectBudgetCount+' */
 result = result + '<li class=""><a href="#tab-b" id="resourceTab" onclick="resourceRequestsTab();">Resource Requests ('+resourceRequestsCount+')</a></li>'; 

result = result + '<li class=""><a href="#tab-c" id="budgetRequestsTab" onclick="budgetRequests();">Budget Requests ';
result = result + '(<b class="countClose">'+projectBudgetCount+'</b><input class="countShow" type="text" id="projectBudgetCount" readonly/>)';
result = result + '</a></li>';
result = result + '<div class="clearfix"></div>';
result = result + '</ul>';

result = result + '<div class="project_tabs mb">';
	
	//Resources Tab
	result = result + '<div id="tab-a" class="newPrTabs_inn" style="display: block;">';
	result = result + '    	<div class="timesheet_head clearfix anchorTag-black">';
		 result = result	+ '<ul>';
			if(status != 'COMPLETED' &&  status != '' &&  status != 'undefined' ){
			result = result +'<li>';
	 		'<sec:authorize access="hasAnyRole(\'ROLE_ASSIGN_EMPLOYEE_TO_PROJECT\')">';
	 		 
	 		if(postedOrgLocationId == loginOrgLocationId){
	 			  result = result + '<a href="javascript:void(0)" onclick="getAssociates();" id="addDiv">';
		 		  result = result + '<i class="fa fa-exchange paddingRight"></i>&nbsp;Transfer Emp</a>&nbsp;&nbsp;&nbsp;&nbsp;'; 
	 		 }
	 		
	 	     if('${isClientOrgActive}' == "N" || postedOrgLocationId == loginOrgLocationId){   
	 			 result = result + '<a href="'+sourceleadUrl+'/addNewProjectMembers?projectUUID='+projectUUID+'&projectClientUUID='+projectClientUUID+'">';
	 		     result = result + '<i class="glyphicon glyphicon-plus"></i>Assign Emp</a></li>';
	 		}  
	 		   
	 		
 		    '</sec:authorize>';

	 		result = result +'</li>';
			} 
		    result = result + '	<li><a onClick="filterStatus()" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-filter"></i><span>Status</span><i class="glyphicon glyphicon-play"></i></a>';
		    result = result + '   	<div class="filterLi dropdown-menu bootstrap_dropdownMenu dropdownPadding">';
			result = result + '       	<ul>';
			/* result = result + '           	<li><input id="c5" type="checkbox" name="employeeStatus" value ="All" onclick="getFilterEmployeeDetails('+projectId+',\''+startDateStr+'\',\''+endDateStr+'\',\''+status+'\',\''+projectName+'\');"><label for="c5">All</label></li>'; */
			result = result + '				<li><input id="c6" type="checkbox"  checked="true" name="employeeStatus" value ="Active" onclick="getFilterEmployeeDetails('+projectId+',\''+startDateStr+'\',\''+endDateStr+'\',\''+status+'\',\''+projectName+'\');"><label for="c6">Active</label></li>';
			result = result + '				<li><input id="c7" name="employeeStatus" type="checkbox" value ="In-Active" onclick="getFilterEmployeeDetails('+projectId+',\''+startDateStr+'\',\''+endDateStr+'\',\''+status+'\',\''+projectName+'\');"><label for="c7">In-Active</label></li> ';
			result = result + '           </ul>';
			result = result + '       </div>'; 
			result = result + '   </li>';
	 
				'<sec:authorize access="hasAnyRole(\'ROLE_EXPORT_EMPLOYEE\')">';
					result = result +'<li>';
					if(status != 'COMPLETED' &&  status != '' &&  status != 'undefined' ){
						result = result +' <a title="Export" id="export-pop" onClick="exportFun()"><i class="glyphicon glyphicon-export expIcon"></i>&nbsp;</a>';	
					} 
					
					result = result +'<div class="" id="exportPop" style="display:none;">';
					result = result +'<ul>';
					if(status != 'COMPLETED' &&  status != '' &&  status != 'undefined' ){
						result = result +'<li onclick="exportToPdf(\''+projectId+'\');"><i class="fa fa-file-pdf-o"></i>&nbsp;&nbsp;PDF</li>';
						
						result = result +'<li onclick="exportToExcel(\''+projectId+'\');"><i class="fa fa-file-excel-o">&nbsp;&nbsp;Excel</i></li>';
			
					} 
					result = result +'</ul>';
					result = result +'</div>';
					result = result +'</li> ';
				'</sec:authorize>';
			
			result = result +'</ul>';
			result = result + '</div>';
			getFilterEmployeeDetails(projectId,startDateStr,endDateStr,status,projectName);
			result = result +'<div id="employeeDetailsDiv" class="datatable_filter"> </div>';
			result = result +'</div>';
		
		
	//	Resource Requests Tab
		result = result + '<div id="tab-b" class="newPrTabs_inn">';
		result = result + ' <div id="resourceRequestsTabDiv"></div>';
		result = result + '</div>';
		
		result = result + '<div id="tab-c" class="newPrTabs_inn">';
		result = result + ' <div id="budgetRequestsTabDiv"></div>';
		result = result + '</div>';
	
	result = result + '</div>';
	
	result = result + '</div>';

	result = result + '</div>';
	result = result + ' <div class="clearfix"></div>';
		
		$("#tab-3").empty();  
		$("#tab-2").empty();  
	    $("#tab-1").empty();
	    $("#tab-4").empty();
	    $("#employeesLoadingImg").empty();
	    $("#tab-2").append(result);  
	    
	    $("#tab-2").show();
		$(".project-tabs-menu a").click(function(event) {
		     event.preventDefault();
		     $(this).parent().addClass("current");
		     $(this).parent().siblings().removeClass("current");
		     var tab = $(this).attr("href");
		     $(".project_tabs_inn").not(tab).css("display", "none");
		     $(tab).fadeIn();
		 });
		$("#tab-a").show();
	    $(".newPrTabsMenu a").click(function(event) {
	        event.preventDefault();
	        $(this).parent().addClass("current");
	        $(this).parent().siblings().removeClass("current");
	        var tab = $(this).attr("href");
	        $(".newPrTabs_inn").not(tab).css("display", "none");
	        $(tab).fadeIn();
	    });		
		
 }
 </script>
 
 
 
 <script>
 function getFilterEmployeeDetails(projectId,startDateStr,endDateStr,status,projectName){
	 var values = [];
	 var chekbox = $('input:checkbox[name=employeeStatus]:checked').val();
	 if(chekbox != 'undefined' && chekbox != '' && chekbox != null){
			$('input:checkbox[name=employeeStatus]:checked').each(function() 
				    {
						values.push($(this).val());
				    });
	 }else{
		 values = "Active";
	 }
	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  projectempListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  projectempListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  projectempListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  projectempListDate = 'YYYY-DD-MM'; 
	  }
	 var sourceleadUrl ='${sourceleadUrl}';
	    $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getProjectEmployeeListById?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&status="+values, 
	        success: function(json) {
	        	
	        	var result = "";
	        	result = result + '        <div class="project_tabs_inn_table overflow-data">';
	    		result = result + ' <div class="success_pop" id="selector3" style="display: none;">';
	    		result = result + ' <i class="fa fa-check-circle"></i><span id="docMsgDiv3"></span>';
	    		result = result + ' </div>';
	    	    result = result + '        <div id="employeesLoadingImg"></div>';
	    		/* if(json.projectMembersList.length!=null && json.projectMembersList.length!=''){ */
	    	    result = result + '	<table id="projectEmployeePagination" class="display" cellspacing="0" width="100%">';
	    		result = result + '	<thead>';
	    		result = result + '		<tr>';
	    		  if('${postedOrgLocationId}' == '${SourceLeadWeb_orgLocations.id}'){
	    			  result = result + ' <th><input type="checkbox" onclick="selectAllMembers();"  id="selectAllMembers" ></th>';
	    		  }
	    		
	            result = result + '	 	<th>Emp Name</th>';
	            result = result + '	 	<th>Emp Email</th>';
	            result = result + '	 	<th>Emp Role</th>';
	            result = result + '	     <th>Start Date<br>(<small class="custom-small">'+projectempListDate+'</small>)</th>';
	            result = result + '	     <th>End Date<br>(<small class="custom-small">'+projectempListDate+'</small>)</th>';
	            result = result + '	     <th>Approved hrs</th>';
	            result = result + '	     <th>Rejected hrs</th>';
	            result = result + '	     <th>Wait for Approval </th>';
	            result = result + '	     <th>Saved hrs</th>';
	            result = result + '	     <th>Action</th>';
	            result = result + '	 </tr>';
	            result = result + '	</thead>';
	            result = result + '	<tbody>';
	        	for (var i=0; i<json.length; i++)
				{      
		        		var opt = json[i];
		        		var role = '';
		        		
						if (opt.userRoleType != null && opt.userRoleType != '' && opt.userRoleType != 'undefined') {
							role = opt.userRoleType;
						} else {
							role = 'Employee';
						}
		        		
		        		var projectEndDateStr = "";
		        		if(opt.projectEndDateStr != null && opt.projectEndDateStr != ''){
		        			projectEndDateStr = opt.projectEndDateStr;
		        		}else{
		        			projectEndDateStr = "--"
		        		}
		        		result = result + '	 <tr>';
		        		 if('${postedOrgLocationId}' == '${SourceLeadWeb_orgLocations.id}'){
		        			 result = result + '<td><input type="checkbox" onclick="selectedMember();"  class="projectMembersChecks" value='+opt.id+' name="projectMembers[]" ></td>';
		        		 }
		        		
		                result = result + '	 	<td><div class="tbldata-dotdot" title="'+opt.candidateName+'"><span title="'+opt.candidateName+'">'+opt.candidateName+'</span></div></td>';
		                result = result + '	 	<td><div class="tbldata-dotdot" title="'+opt.empEmail+'"><span title="'+opt.empEmail+'">'+opt.empEmail+'</span></div></td>';
		                result = result + '	 	<td><div class="tbldata-dotdot_80" title="'+role+'"><span title="'+role+'">'+role+'</span></div></td>';
		                result = result + '	     <td><div class="tbldata-dotdot_80"><span title="'+opt.projectAssignedDateStr+'">'+opt.projectAssignedDateStr+'</span></div></td>';
		                if(status == 'COMPLETED'){
		                	result = result + '<td><div>'+projectEndDateStr+'</div></td>';
		                }else{
		                	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_PROJECT_EMPLOYEES\')">'; 
		                	if(opt.status != null && opt.status != '' && opt.status !='undefined' && opt.status != 0){
			                	result = result + '	     <td><div><input type="text" value="'+projectEndDateStr+'" class="project_inline_field project_inline_field_enddate" id="endDate'+opt.id+'" name = "endDate'+opt.id+'"  readonly="readonly"></div></td>';
			                }
			                else{
			                	  result = result + '	     <td><div class="tbldata-dotdot_80"><span title="'+projectEndDateStr+'">'+projectEndDateStr+'</span></div></td>';
			                }
		                	'</sec:authorize>';
		                	
		                	'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_EDIT_PROJECT_EMPLOYEES\')">'; 
		                	  result = result + '	     <td><div class="tbldata-dotdot_80"><span title="'+projectEndDateStr+'">'+projectEndDateStr+'</span></div></td>';
	                	   '</sec:authorize>';
		                	
		                }
		                result = result + '	     <td><div class="tbldata-dotdot_80"><span title="'+opt.approvedHrs+'">'+opt.approvedHrs+'</span></div></td>';
		                result = result + '	     <td><div class="tbldata-dotdot_80"><span title="'+opt.rejectedHrs+'">'+opt.rejectedHrs+'</span></div></td>';
		                result = result + '	     <td><div class="tbldata-dotdot_80"><span title="'+opt.submittedHrs+'">'+opt.submittedHrs+'</span></div></td>';
		                result = result + '	     <td><div class="tbldata-dotdot_80"><span title="'+opt.draftHrs+'">'+opt.draftHrs+'</span></div></td>';
		                
		                
		                if('${postedOrgLocationId}' == '${SourceLeadWeb_orgLocations.id}'){
		                	
		                	 if(opt.status != null && opt.status != '' && opt.status !='undefined' && opt.status != 0){
				                	result = result + '	<td><div>';
				                	if(status != 'COMPLETED'){
				                		'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES\')">'; 

				                		result = result + '<span title="delete employee"><i class="glyphicon glyphicon-trash" onClick="deleteProject('+opt.id+'\,\''+projectId+'\',\''+startDateStr+'\',\''+endDateStr+'\')";></i></span>';
				                		'</sec:authorize>';
				                		
				                		'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES\')">'; 
				                		result = result + '---';
				                		'</sec:authorize>';
				                	}
				                	result = result +'</div></td>';
			                     }
			                     else{
			                    	 result = result + '<td><div class="tblminwidth-50">';
			                    	 if(status != 'COMPLETED'){
			                    		 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES\',\'ROLE_ASSIGN_EMPLOYEE_TO_PROJECT\')">'; 
			                    		    result = result + '<a  onclick="reActiveProject(\''+opt.id+'\',\''+opt.empName+'\',\''+opt.projectAssignedDateStr+'\',\''+projectEndDateStr+'\',\''+opt.endDate+'\');flushReProject()" data-target="#Re-AssignProject" data-toggle="modal">Re-Assign &nbsp;</a><span class="show-reason">';
				                    	if(opt.terminateReason != null && opt.terminateReason != '' && opt.terminateReason != 'undefined'){
				                    		result = result + '<b class="glyphicon glyphicon-comment text-success"></b><b class="reason edit-status">'+opt.terminateReason+'</b>';
				                    	}else{
				                    		result = result + '';	
				                    	}
				                      '</sec:authorize>';
				                      
				                      '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES\',\'ROLE_ASSIGN_EMPLOYEE_TO_PROJECT\')">'; 
				                        result = result + '---';
				                      '</sec:authorize>';
			                    	 
			                    	 result = result + '</span>';
			                    	 }else{
			                    		 result = result + '<font color="red">&nbsp;Terminated</font>';	 
			                    	 }
			                    	 result = result +'</div></td>';
			                     }
		                	
		                }else{
		                	result = result + '<td><div class="tblminwidth-50">---</div></td>';
		                }
		                
		               
	                    	 
		                
		                
		                
		                result = result + '	 </tr>';
				}
	        	result = result + '	     </tbody>';
	    	    result = result + '	 </table>   ';
	    		result = result + '	     </div>';
	    		$("#employeeDetailsDiv").empty();  
	    		$("#employeeDetailsDiv").append(result);  
	    		$("#employeeEmptyListId").val(json.length);  
	    		 //pagination function
	    	    projectEmployeePagination();
	    		
	    	    if(json.length > 0)
		   		{
	    			for (var i=0; i<json.length; i++)
	    			{      
	    	             var jsonData = json[i];
						 if(jsonData.status != null && jsonData.status != '' && jsonData.status !='undefined' && jsonData.status == 0)
							   continue;
						 //Assign date of project member
						 var selected = jsonData.projectAssignedDateStr;
						 var from = selected.split("-");
						 var dt = new Date(from[2], from[1] - 1, from[0]);
	 		             dt.setDate(dt.getDate());
	 		             
	 		             //Main project end date
	 		             var projectEndDate = jsonData.endDate;
	 		             //var endDate = "";
	 		             var endDate = jsonData.projectEndDateStr
	 		            /*  if(projectEndDate != null && projectEndDate != "" && projectEndDate != 'undefined'){
	 		            	 var fromEnd = projectEndDate.split("-");
	 		            	 endDate = new Date(fromEnd[2], fromEnd[1] - 1, fromEnd[0]);
	 	 		             endDate.setDate(endDate.getDate()); 
	 		             } */
	 		            var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	 					  var finalDateFormatResourceListDisplay = '';
	 					  if(locationDate.match("yyyy-MM-dd")){
	 						 finalDateFormatResourceListDisplay = 'yy-mm-dd';
	 					  }if(locationDate.match("dd-MM-yyyy")){
	 						 finalDateFormatResourceListDisplay = 'dd-mm-yy'; 
	 					  }if(locationDate.match("MM-dd-yyyy")){
	 						 finalDateFormatResourceListDisplay = 'mm-dd-yy'; 
	 					  }if(locationDate.match("yyyy-dd-MM")){
	 						 finalDateFormatResourceListDisplay = 'yy-dd-mm'; 
	 					  }
	 					 $('#endDate'+jsonData.id).datepicker({
			    		      dateFormat: finalDateFormatResourceListDisplay,
//			    		      minDate: dt,
			    		      maxDate: endDate,
			    		      minDate: selected,
			    		      showOn: 'focus',
		    	              showButtonPanel: true,
		    	              closeText: 'Clear',
			    		      onClose: function() {
			    		        	 var id = $(this).attr('id');
									 var projectMemberId = $(this).attr('id').split('endDate')[1];
			    		        	 var event = arguments.callee.caller.caller.arguments[0];
			    		                // If "Clear" gets clicked, then really clear it
			    		                if ($(event.delegateTarget).hasClass('ui-datepicker-close')) {
			    		                    $(this).val('');
			    		                }
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
    	"scrollX": true,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>

<script>
function selectAllMembers(){
	
	var checked = document.getElementById("selectAllMembers").checked  
	
        if(checked) {
            $('.projectMembersChecks').each(function() {
                this.checked = true;   
            });
        }else{
            $('.projectMembersChecks').each(function() {
                this.checked = false;      
            });         
        } 
	
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
function deleteProject(projectMemberId,projectId,startDateStr,endDateStr){ 
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";

	result = result +"		<p>Are you sure you want to delete ?</p>";
	result = result +"		<textarea  id='reasonForProject' name='reasonForProject'  placeholder='Comments here' ></textarea>";
	result = result + ' <div id="reasonForProjectDiv"/>';
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Delete" onclick="saveReason('+projectMemberId+',\''+projectId+'\',\''+startDateStr+'\',\''+endDateStr+'\');"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function cancelTermination(){
	$("#popUp").empty();
	 $(".project_inn_mask").fadeOut(200);
}

function saveReason(projectMemberId,projectId,startDateStr,endDateStr){
	var sourceleadUrl = '${sourceleadUrl}';
	var reason = $("#reasonForProject").val();
	var con = JSON.stringify(reason);
	con = con.replace(/[{}]/g, "");
	//It will escape all the special characters
	var jsonData = encodeURIComponent(con);
	if(reason != null && reason != ''){
		 $("#reasonForProjectDiv").empty();
		if(reason.length > 250){
		$("#reasonForProjectDiv").empty().append("<font color='red' style='padding: 5px 10px; '>The Entered values should not be more than 250 characters</font>") ; 
		}else{
			 $.ajax({
				   	type: "POST",
				    url: sourceleadUrl+"/saveReason?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+projectMemberId+"&projectId="+projectId+"&reason="+jsonData,
		   		    success: function(data) {
		   		    	$("#popUp").empty();
		   			 	$(".project_inn_mask").fadeOut(200);
		   				$("#selector3").removeAttr("style");
		 				$("#selector3").css({ display: "block" });
		 				$("#docMsgDiv3").html("Project deleted");
		 				var status = $("#assignStatus").val();
		   			 	projectEmployee(projectId,startDateStr,endDateStr,status);
		   			 docMsgFadein3();
		   		    }
			     });
		}
	}
	else{
			$("#reasonForProjectDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
	}
	
}
</script>


<!-- this script is for TeamMembers List functionality at Projects screen -->
<!-- <script type="text/javascript">
function getTeamMembers(projectId,startDateStr,endDateStr) {
	if(projectId == null || projectId == '' || projectId == 'undefined'){
		projectId  = document.getElementById("projectId").value;
		startDateStr  = document.getElementById("startDateStr").value;
	}
	var sourceleadUrl ='${sourceleadUrl}';
	var startDateStr = startDateStr;
	if(endDateStr == null ||  endDateStr == "" || endDateStr == "undefined"){
		endDateStr = "";
	}
	var searchByName = $("#searchByName").val();
	
	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  teamMembersDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  teamMembersDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  teamMembersDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  teamMembersDate = 'YYYY-DD-MM'; 
	  }  
	
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
            output = output+' <th><input type="checkbox" name="emp" id="empChekId" onclick="toggleCheck();"></th>';
            output = output+' <th>Name</th>';
            output = output+' <th>Email</th>';
            output = output+' <th>Role</th>';
            output = output+' <th>Start Date <br>(<small class="custom-small">'+teamMembersDate+'</small>)</th>';
            output = output+' <th>End Date <br>(<small class="custom-small">'+teamMembersDate+'</small>)</th>';
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
	        		//output = output + '<td><input type="checkbox"  class="submissionsChecks" name="submissionList[]" ></td>';
	        		output = output + "<td><input type='hidden' id='projectStartDateStr'  value="+startDateStr+"><input type='hidden' id='projectEndDateStr'  value="+endDateStr+"><input class='myCheckbox' id="+opt.id+" type='checkbox' name='team"+i+"' value="+opt.id+"></td>";
	        		if(opt.user.lastName != null && opt.user.lastName !='Undefined' && opt.user.lastName != ''){
						output = output + "<td><span class='tbldata-dotdot-sm' title='"+opt.user.firstName+"&nbsp;"+opt.user.lastName+"'>" + opt.user.firstName+ "&nbsp;" + opt.user.lastName+ "</span></td>";	
					}else{
						output = output + "<td><span class='tbldata-dotdot-sm' title='"+opt.user.firstName+"'>" + opt.user.firstName+ "</span></td>";
					}
	        		output = output +"<td><span class='tbldata-dotdot-vsm' title='"+opt.user.email+"'>"+opt.user.email+"</span></td>";
	        		output = output +"<td><span class='tbldata-dotdot-vsm' title='"+role+"'>"+role+"</span></td>";
	        		output = output +"<td><div class='input-group'> <span class='input-group-addon'><i class='fa fa-calendar'></i></span>";
	        		output = output +"<input  id='startDate"+ opt.id+ "'   type='text' name='Date' value='"+startDateStr+"' class='form-control clsDatePicker' readonly='readonly'>";
	        		output = output + "</div></td>";
	        		output = output +"<td><div class='input-group'> <span class='input-group-addon'><i class='fa fa-calendar'></i></span>";
	        		output = output +"<input id='endDate" + opt.id + "' value='"+endDateStr+"'  type='text' name='Date' value='' class='form-control clsDatePicker' readonly='readonly'>";
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
	     	var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  	  var finalDateFormatAssignEmpUpdate = '';
		  	  if(locationDate.match("yyyy-MM-dd")){
		  		  finalDateFormatAssignEmpUpdate = 'yy-mm-dd';
		  	  }if(locationDate.match("dd-MM-yyyy")){
		  		finalDateFormatAssignEmpUpdate = 'dd-mm-yy'; 
		  	  }if(locationDate.match("MM-dd-yyyy")){
		  		finalDateFormatAssignEmpUpdate = 'mm-dd-yy'; 
		  	  }if(locationDate.match("yyyy-dd-MM")){
		  		finalDateFormatAssignEmpUpdate = 'yy-dd-mm'; 
		  	  }
	        
	        var from = startDateStr.split("-");
	        var datepickerStartDate =  new Date(from[2], from[1] - 1, from[0]);
	        datepickerStartDate.setDate(datepickerStartDate.getDate() + 1);
		       if(json.length > 0){
			    	for (var i = 0; i <json.length; i++) {
						var opt = json[i];
						if(endDateStr !=  null && endDateStr != '' && endDateStr != 'undefined'){
							$('#startDate'+opt.id).datepicker({
								dateFormat : finalDateFormatAssignEmpUpdate,
								changeMonth: true,
								changeYear: true, 
								minDate:startDateStr,
								maxDate:endDateStr,
								showOn: 'focus',
						        showButtonPanel: true,
						        closeText: 'Clear',
						        onClose : function(selected) {
									var actualId = $(this).attr('id').split('startDate')[1];
									//var dt = toDate('#startDate'+actualId);
									var id = $(this).attr('id');
									  var dt = $(this).datepicker('getDate', '+1d');
									dt.setDate(dt.getDate() + 1);
									$("#endDate"+actualId).datepicker("option", "minDate", dt);
									var event = arguments.callee.caller.caller.arguments[0];
					                // If "Clear" gets clicked, then really clear it
					                if ($(event.delegateTarget).hasClass('ui-datepicker-close')) {
					                    $(this).val('');
					                }
								}
							});

							$('#endDate'+opt.id).datepicker({
								dateFormat : finalDateFormatAssignEmpUpdate,
								changeMonth: true,
								changeYear: true, 
								maxDate:endDateStr,
								minDate: datepickerStartDate,
								showOn: 'focus',
						        showButtonPanel: true,
						        closeText: 'Clear',
						        onClose : function(selected) {
									var actualId = $(this).attr('id').split('endDate')[1];
								//	var dt = toDate('#endDate'+actualId);
									var id = $(this).attr('id');
									  var dt = $(this).datepicker('getDate', '-1d');
									dt.setDate(dt.getDate() - 1);
									$("#startDate"+actualId).datepicker("option", "maxDate", dt);
									var event = arguments.callee.caller.caller.arguments[0];
					                // If "Clear" gets clicked, then really clear it
					                if ($(event.delegateTarget).hasClass('ui-datepicker-close')) {
					                    $(this).val('');
					                }
								}
							});
							
						}else{
							$('#startDate'+opt.id).datepicker({
								dateFormat : finalDateFormatAssignEmpUpdate,
								changeMonth: true,
								changeYear: true, 
								minDate:startDateStr,
								showOn: 'focus',
						        showButtonPanel: true,
						        closeText: 'Clear',
						        onClose : function(selected) {
									var actualId = $(this).attr('id').split('startDate')[1];
									//var dt = toDate('#startDate'+actualId);
									var id = $(this).attr('id');
									  var dt = $(this).datepicker('getDate', '+1d');
									dt.setDate(dt.getDate() + 1);
									$("#endDate"+actualId).datepicker("option", "minDate", dt);
									var event = arguments.callee.caller.caller.arguments[0];
					                // If "Clear" gets clicked, then really clear it
					                if ($(event.delegateTarget).hasClass('ui-datepicker-close')) {
					                    $(this).val('');
					                }
									
								}
							});

							$('#endDate'+opt.id).datepicker({
								dateFormat : finalDateFormatAssignEmpUpdate,
								changeMonth: true,
								changeYear: true, 
								minDate:datepickerStartDate,
								showOn: 'focus',
						        showButtonPanel: true,
						        closeText: 'Clear',
						        onClose : function(selected) {
									var actualId = $(this).attr('id').split('endDate')[1];
									//var dt = toDate('#endDate'+actualId);
									var id = $(this).attr('id');
									  var dt = $(this).datepicker('getDate', '-1d');
									dt.setDate(dt.getDate() - 1);
									$("#startDate"+actualId).datepicker("option", "maxDate", dt);
									var event = arguments.callee.caller.caller.arguments[0];
					                // If "Clear" gets clicked, then really clear it
					                if ($(event.delegateTarget).hasClass('ui-datepicker-close')) {
					                    $(this).val('');
					                }
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
 -->
 
 
<!-- <script>
function assignEmployeespagination(){  
    $('#assignEmployeePagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10000, 15, 20, -1], [10000, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"aaSorting": [[1,'desc']],
    	"aoColumnDefs": [{
	           'bSortable': false,
	           'aTargets': 0
			}],
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
		
    } );
}
</script> -->


<!-- this script is for Assign Employee functionality at Projects screen -->
<!-- <script>
function assignEmployee(){
	    $('#assignEmployeePagination').DataTable().search('').draw();
	 	var projectId = $("#projectId").val();
	 	var projectStartDateStr =  $("#projectStartDateStr").val();
	 	var projectEndDateStr =  $("#projectEndDateStr").val(); 
	 	var status = $("#assignStatus").val();
	    var result = "";
	    var mailtoMembers = "NO";
	    if(document.getElementById('membermailCheckbox').checked) {
	    	mailtoMembers = "YES";
	    }
	    var sourceleadUrl ='${sourceleadUrl}'; 
	    //$('#assignTeam').modal('hide');
	    $('#assignTeam').hide();
	    $('.modal-backdrop').hide();
	    
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
	    
	    $.ajax({
        	type: "POST",
            url: sourceleadUrl+"/storeAssignEmployeeIntoELK?${_csrf.parameterName}=${_csrf.token}&ProjectId="+projectId, 
            success: function(data) {
            }
		})
	    
}
</script> -->

<!--  <script>
function toggleCheck() {
	var checked = document.getElementById("empChekId").checked  
	
    if(checked) {
        $('.myCheckbox').each(function() {
            this.checked = true;   
        });
    }else{
        $('.myCheckbox').each(function() {
            this.checked = false;      
        });         
    } 


}
</script> -->

<!-- <script type="text/javascript">
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


this script is for Add Employee functionality at Projects screen
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
</script> -->


<script>
  $('#selector').delay(2000).fadeOut('slow');
  function exportFun(){
	  //$("#export-pop").next("div.export_pop").toggle();
	  $("#exportPop").toggle();
	  $(".export_pipline").slideUp();
  }
</script> 

<!--  -->

<script>
function exportToPdf(projectId){
	var sourceleadUrl ='${sourceleadUrl}';
	var employeeEmptyCount = $("#employeeEmptyListId").val();
	if(employeeEmptyCount > 0 ){
		var values = [];
		 var chekbox = $('input:checkbox[name=employeeStatus]:checked').val();
		 if(chekbox != 'undefined' && chekbox != '' && chekbox != null){
				$('input:checkbox[name=employeeStatus]:checked').each(function() 
					    {
							values.push($(this).val());
					    });
		 }else{
			 values = "Active";
		 } 
		 
	   window.location.href = sourceleadUrl+"/exportProjectEmployeeList?&projectId="+projectId+"&status="+values+"&exportType=pdf";
	}else{
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='client_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Employee List is Empty</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='empCancelTermination();'><input class='u_can_color' type='button'  value='Cancel' onClick='empCancelTermination();'></b>";
		result = result +"	</div>";
		$("#popUp").append(result);
		$("#client_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
	 
}
</script>
<script type="text/javascript">
function empCancelTermination(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
	$("#exportPop").toggle();
	$(".export_pipline").slideUp();
}
</script>
<script>
function exportToExcel(projectId){
	var sourceleadUrl ='${sourceleadUrl}';
	var employeeEmptyCount = $("#employeeEmptyListId").val();
	if(employeeEmptyCount > 0 ){
		var values = [];
		 var chekbox = $('input:checkbox[name=employeeStatus]:checked').val();
		 if(chekbox != 'undefined' && chekbox != '' && chekbox != null){
				$('input:checkbox[name=employeeStatus]:checked').each(function() 
					    {
							values.push($(this).val());
					    });
		 }else{
			 values = "Active";
		 }
		window.location.href = sourceleadUrl+"/exportProjectEmployeeList?&projectId="+projectId+"&status="+values+"&exportType=xls";
	}else{
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='client_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Employee List is Empty</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='empCancelTermination();'><input class='u_can_color' type='button'  value='Cancel' onClick='empCancelTermination();'></b>";
		result = result +"	</div>";
		$("#popUp").append(result);
		$("#client_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
	
}
</script>



<!-- Modal content Re-Asign Project-->
<div role="dialog" id="Re-AssignProject" class="modal fade">
	<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button data-dismiss="modal" class="close" type="button">X</button>
					<h4 class="modal-title">Re-Assign Employee</h4>
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
   result = result +'	<div class="col-lg-9 ">';
   result = result +'		<input id="reAssignProjectName" type="text" class="form-control" value="'+empName+'"  name="reAssignProjectName" readonly="readonly" class="form-control"></span>';
   result = result +'	</div>';
   result = result +'	</div>';
   
   
   result = result +'<div class="form-group" >';
   result = result +'<label class="col-lg-3" >Start date<span <span class="text-danger">*</span></label>';
   result = result +'<div class="col-lg-9 positionRelative" id="empDatePicker">';
   
   result = result +'<input type="text" class="form-control calendar" readonly="readonly" placeholder="DD-MM-YYYY" id="Re-StartDate" name="Re-StartDate" field-name="Start Date" data-validation="required">';

   result = result +'</div>';
   result = result +'</div>';
   
   result = result +'<div class="form-group">';
   result = result +'<label class="col-lg-3">End date</label>';
   result = result +'<div class="col-lg-9 positionRelative">';

   result = result +'<input type="text" class="form-control calendar" readonly="readonly" placeholder="DD-MM-YYYY" id="Re-EndDate" name="Re-EndDate"  field-name="End Date" />';

   result = result +'</div>';
   result = result +'</div>';

   result = result +'</div>';
   result = result +'</div>';
   result = result +'	</div>';
   
   $("#ReAssignProjectId").empty();
   $("#ReAssignProjectId").append(result);
	  
   var reStatertDate = $('#Re-StartDate').val();
   
	
   var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormatRecomondedCandCertSave = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormatRecomondedCandCertSave = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormatRecomondedCandCertSave = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormatRecomondedCandCertSave = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormatRecomondedCandCertSave = 'yy-dd-mm'; 
	  }
   
  var reStatertDate = $('#Re-StartDate').val()
  if(projectEndDateStr != null && projectEndDateStr != "" && projectEndDateStr != 'undefined'){
	  $('#Re-StartDate').datepicker({
	      dateFormat: finalDateFormatRecomondedCandCertSave,
	      changeMonth: true,
	  	  changeYear: true, 
		  minDate:endDate,
		  maxDate:projectEndDateStr,
		  showOn: 'focus',
          showButtonPanel: true,
          closeText: 'Clear',
          onClose: function (selected) {
	    	    removevalidation();
	            var dt = toDate('#Re-StartDate');
	            dt.setDate(dt.getDate() + 1);
	            $("#Re-EndDate").datepicker("option", "minDate", dt);
	            var event = arguments.callee.caller.caller.arguments[0];
                // If "Clear" gets clicked, then really clear it
                if ($(event.delegateTarget).hasClass('ui-datepicker-close')) {
                    $(this).val('');
                }
	        }
	  
	  });
	  
	  $('#Re-EndDate').datepicker({
	      dateFormat: finalDateFormatRecomondedCandCertSave,
	      changeMonth: true,
	  	  changeYear: true, 
	      minDate:endDate,
	      maxDate:projectEndDateStr,
	      showOn: 'focus',
          showButtonPanel: true,
          closeText: 'Clear',
          onClose: function (selected) {
	            var dt = toDate('#Re-EndDate');
	            dt.setDate(dt.getDate() - 1);
	            $("#Re-StartDate").datepicker("option", "maxDate", dt);
	            var event = arguments.callee.caller.caller.arguments[0];
                // If "Clear" gets clicked, then really clear it
                if ($(event.delegateTarget).hasClass('ui-datepicker-close')) {
                    $(this).val('');
                }
	        }
	  });
  }
  else{
	  $('#Re-StartDate').datepicker({
	      dateFormat: finalDateFormatRecomondedCandCertSave,
	      changeMonth: true,
	  	  changeYear: true, 
		  minDate:endDate,
		  showOn: 'focus',
          showButtonPanel: true,
          closeText: 'Clear',
          onClose: function (selected) {
	            var dt = toDate('#Re-StartDate');
	            dt.setDate(dt.getDate() + 1);
	            $("#Re-EndDate").datepicker("option", "minDate", dt);
	            var event = arguments.callee.caller.caller.arguments[0];
                // If "Clear" gets clicked, then really clear it
                if ($(event.delegateTarget).hasClass('ui-datepicker-close')) {
                    $(this).val('');
                }
	            removevalidation();
	        }
	  });
	  
	  
	
	  $('#Re-EndDate').datepicker({
	      dateFormat: finalDateFormatRecomondedCandCertSave,
	      changeMonth: true,
	  	  changeYear: true, 
	      minDate:endDate,
	      maxDate:reStatertDate,
	      showOn: 'focus',
          showButtonPanel: true,
          closeText: 'Clear',
          onClose: function (selected) {
	            var dt = toDate('#Re-EndDate');
	            dt.setDate(dt.getDate() - 1);
	            $("#Re-StartDate").datepicker("option", "maxDate", dt);
	            var event = arguments.callee.caller.caller.arguments[0];
                // If "Clear" gets clicked, then really clear it
                if ($(event.delegateTarget).hasClass('ui-datepicker-close')) {
                    $(this).val('');
                }
	            removevalidation();
	        }
	  });
  }	  
   
}

</script>
<script>
function removevalidation(){
	$('#empDatePicker').find('p.jquery_form_error_message').remove();	
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
		        	 	//$("#Re-AssignProject").modal('hide');
		        	 	$('#Re-AssignProject').hide();
		        	    $('.modal-backdrop').hide();
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
<script>
	function docMsgFadein3() {
		$("#selector3").fadeIn(200);
        $('#selector3').delay(2000).fadeOut('slow');
	}
</script> 

<!-- script for converting dateformat in datepicker -->
<script>
function toDate(selector) {
    var from = $(selector).val().split("-");
    return new Date(from[2], from[1] - 1, from[0]);
}
</script>
<script> 
//  New project Tabs
	$("#tab-a").show();
    $(".newPrTabsMenu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".newPrTabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
</script>

<!-- script for dateformat in datepicker End -->


<!-- ====================================Resource RequestsTab Start=============================================== -->
<script>
function resourceRequestsTab(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var projectUUID = '${projectUUID}';
	 var projectClientUUID = '${projectClientUUID}';
	 
	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  resourceReqListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  resourceReqListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  resourceReqListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  resourceReqListDate = 'YYYY-DD-MM'; 
	  }
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getPlanningRequestListByProjectUUID?${_csrf.parameterName}=${_csrf.token}&projectUUID="+projectUUID, 
	        success: function(json) {
	        	
	        	
	        	var result = "";
	        	
	    		result = result + '	<div class="timesheet_head anchorTag-black">';
	    		result = result + '	<ul>';
	    		result = result + '	      <li><a href="'+sourceleadUrl+'/requestAssociates?projectUUID='+projectUUID+'&projectClientUUID='+projectClientUUID+'">';
	    		result = result + '	       <i class="glyphicon glyphicon-plus"></i>Request Associates</a></li>';
	    		result = result + '	      <li><a href="'+sourceleadUrl+'/newResourceRequest?projectUUID='+projectUUID+'&projectClientUUID='+projectClientUUID+'">';
	    		result = result + '	       <i class="glyphicon glyphicon-plus"></i>New Resource Request</a>&nbsp; &nbsp;</li>';

	    		result = result + '	</ul>';
	    		result = result + '	       </div>';
	    		result = result + '		<div class="project_tabs_inn_table vendor_wrap_inn_table datatable_filter overflow_inherint">';
	    		result = result + '		<table id="planningRequestsPaginationList" cellspacing="0" width="100%">';
	    		result = result + '	    <thead>';
	    		result = result + '	     	<tr>';
	    		result = result + '	         	<th>Request Id</th>';
	    		result = result + '	             <th>Approver</th>';
	    		result = result + '	             <th>Req. Resources</th>';
	    		result = result + '	             <th>Alloted Resources</th>';
	    		result = result + '	             <th>Estimated Hours<br>(<small class="custom-small">'+resourceReqListDate+'</small>)</th>';
	    		result = result + '	             <th>Estimated Budget<br>(<small class="custom-small">'+resourceReqListDate+'</small>)</th>';
	    		result = result + '	             <th>Req. On</th>';
	    		result = result + '	             <th>Req. By</th>';
	    		result = result + '	             <th>Action</th>';
	    		result = result + '	         </tr>';
	    		result = result + '	    </thead>';
	    		result = result + '	 	<tbody>';
	    		
	    		for (var i=0; i<json.length; i++){
	    			var report = json[i];
	    			result = result + '<tr>';
	    			if(report.requestType == 'Associate Request'){
						if(report.requestStatus == 'Draft' || report.requestStatus == 'Rejected'){
							result = result + '<td><div><a href="'+sourceleadUrl+'/editResourceRequest?requestUUID='+report.requestUUID+'&projectUUID='+projectUUID+'&projectClientUUID='+projectClientUUID+'">'+report.requestId+'</a></div></td>';
						}else{
							result = result + '<td><div><a href="'+sourceleadUrl+'/viewResourceRequest?requestUUID='+report.requestUUID+'&projectUUID='+projectUUID+'&projectClientUUID='+projectClientUUID+'">'+report.requestId+'</a></div></td>';
						}
	    			}else{
	    				if(report.requestStatus == 'Draft' || report.requestStatus == 'Rejected'){
	    					result = result + '<td><div><a href="'+sourceleadUrl+'/editNewResourceRequest?requestUUID='+report.requestUUID+'&projectUUID='+projectUUID+'&projectClientUUID='+projectClientUUID+'">'+report.requestId+'</a></div></td>';
						}else{
							result = result + '<td><div><a href="'+sourceleadUrl+'/viewNewResourceRequest?requestUUID='+report.requestUUID+'&projectUUID='+projectUUID+'&projectClientUUID='+projectClientUUID+'">'+report.requestId+'</a></div></td>';
						}
	    				
	    			}
	    			result = result + '	             <td><div class="tbldata-dotdot-vsm">'+report.approverName+'</div></td>';
		    		result = result + '	             <td><div>'+report.requestedResources+'</div></td>';
		    		result = result + '	             <td><div>'+report.allocatedResources+'</div></td>';
		    		result = result + '	             <td><div>'+report.estimatedHours+' hrs</div></td>';
		    		result = result + '	             <td><i class="pull-left"></i>'+report.estimatedBudgetCurrency+'<div class="pull-right">'+report.estimatedBudget+'</div></td>';
		    		result = result + '	             <td><div class="tblminwidth-75">'+report.requestedDateStr+'</div></td>';
		    		result = result + '	             <td><div class="tbldata-dotdot-vsm">'+report.requestedUser+'</div></td>';
		    		result = result + '	             <td><div><b>'+report.requestStatus+'</b></div>';
		    		if(report.requestStatus == 'Rejected' && report.rejectedReason != null && report.rejectedReason != '' && report.rejectedReason != 'undefined'){
		    			 result = result +"<i class='glyphicon glyphicon-comment edit-reason'><div class='edit-status'><p>"+report.rejectedReason+"</p></div></i>";
		    		}
		    		result = result + '</td>';
		    		result = result + '	         </tr>';
	    		}
	    		
	    		
	    		result = result + '	     </tbody>';
	    		result = result + '	 </table>';
	    		result = result + '</div>';
	    	    $("#resourceRequestsTabDiv").empty();
	    	    $("#resourceRequestsTabDiv").append(result); 
	    	    planningRequestsPagination();
	        }
	   
     });
		 
}
</script>


<script>
function getAssociates(){
	var result = '';
	
	result = result +'<div class="clients_side_wrap">';
	result = result +'<div id="projectDetailsLoading"></div>';
	result = result +'<div class="new_side_wrap_inn">';
	result = result +'<div class="timesheet_head" style="border-bottom: 1px solid #ededed; background: #f5f5f5;">';
	      
	result = result +'<ul class="pull-right">';
	

	        
	result = result +'<li class="dropdown pull-left">';
	result = result +'<span class="dropdown-toggle mt5" id="menu1" data-toggle="dropdown">';
	result = result +'<i class="fa fa-filter mt5"></i><span>Location</span><i class="glyphicon glyphicon-play mt10"></i></span>|';
	result = result +'<div class="dropdown-menu bootstrap_dropdownMenu bootstrap_dropdownMenu-right" role="menu" aria-labelledby="menu1">';
	result = result +'<ul class="location-filter">';
	result = result +'<c:forEach var="locations" items="${orgLocationsList}">';
	result = result +'<li>';
	result = result +'<input class="ml10" value="${locations.id}" onclick="getOrgLocationProjects();"  name="orgLocations" ${SourceLeadWeb_orgLocations.id  == locations.id ? "checked" : ''} type="radio">';
	result = result +'<label class="radioAlign"><img src="resources/images/${locations.country}.gif">${locations.orgLocationName}</label>';
	result = result +'</li>';
	result = result +'</c:forEach>';
	result = result +'</ul>';
	result = result +'</div>';
	result = result +'</li>';
	
	result = result +'<li class="pull-left">';  
	result = result +'<select class="form-control chosen-select" name="orgLocationProjects" id="orgLocationProjects" onchange="getProjectDetails();">';
	result = result +'<option value="">-- Select Project --</option>';
	result = result +'</select>';
	result = result +'</li>';  
	

	
	result = result +'<div class="clearfix"></div>';
	result = result +'</ul>';
	result = result +'<div class="clearfix"></div>';
	result = result +'</div>';
	
	result = result +'<div id="projectDetailsDiv"></div>';
	result = result +'<div id="membersChoicepopUp"></div>';
	result = result +'</div>';
	result = result +'</div>';
	
	
	 $("#associatesList").empty();
     $("#associatesList").append(result);
     
     $(".clients_side_wrap").addClass("clients_side_wrap_active");
	 $(".side_mask").fadeIn(200);
	 $("body").css('overflow-y', 'hidden');
    
	 prepareChosenScripts();
     getOrgLocationProjects();
     getProjectDetails();
 	
}
</script>


<script type="text/javascript">
function getOrgLocationProjects(){
	emptyRecords();
	var orgLocations = [];
	$('input:radio[name=orgLocations]:checked').each(function() 
		    {
		orgLocations.push($(this).val());
	    });
	
	 var sourceleadUrl = '${sourceleadUrl}';
	 $("#projectDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getProjectsListByOrgLocationForTransfer?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocations, 
	        success: function(json) {
	        
	        	var output = "";
	        	output = output + '<option value="">--Select Project--</option>';
	        	for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		output = output + '<option value="'+opt.id+'">'+opt.projectName+'</option>';
				}
	        	
	        	$("#orgLocationProjects").empty();
		        $("#orgLocationProjects").append(output);
		        $('#orgLocationProjects').trigger("chosen:updated");
		        $("#projectDetailsLoading").removeAttr("style"); 
	        }
	   
     });
		 
}
</script>

<script>
function getProjectDetails(){
	var orgLocationProjects = document.getElementById("orgLocationProjects").value;
	var orgLocations = [];
	$('input:radio[name=orgLocations]:checked').each(function() 
		    {
		orgLocations.push($(this).val());
	    });
	
	if(orgLocationProjects){
		$("#projectDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var sourceleadUrl = '${sourceleadUrl}';
				$.ajax({
					type : "POST",
					url : sourceleadUrl	+ "/getProjectDetailsForResouceTransfer?${_csrf.parameterName}=${_csrf.token}&projectId="+ orgLocationProjects+"&orgLocationId="+orgLocations,
					success : function(data) {
						var json = JSON.parse(data);
						var endDateStr = "";
						var description = "";
						var clientName = "";
						var projectSequeneNumber = "";
						var invoiceFrequency = "";
						
						
					
						if (json.endDateStr != null && json.endDateStr != ''
								&& json.endDateStr != 'undefined') {
							endDateStr = json.endDateStr;
						} else {
							endDateStr = "--";
						}
						if (json.description != null && json.description != ''
								&& json.description != 'undefined') {
							description = json.description;
						} else {
							description = "----";
						}
						
						if (json.invoiceFrequency != null && json.invoiceFrequency != '' && json.invoiceFrequency != 'undefined') {
							invoiceFrequency = json.invoiceFrequency;
					     } else {
					    	 invoiceFrequency = "----";
					    }
						
						if (json.projectSequeneNumber != null
								&& json.projectSequeneNumber != ''
								&& json.projectSequeneNumber != 'undefiend') {
							projectSequeneNumber = json.projectSequeneNumber;
						} else {
							projectSequeneNumber = "--";
						}
						if (json.client != null && json.client != ''
								&& json.client != 'undefined') {
							clientName = json.client
						} else {
							clientName = "--";
						}
						
						if (json.overtimeRateType != null && json.overtimeRateType != '' && json.overtimeRateType != 'undefined') {
							flatPercentageId = json.overtimeRateType
						} else {
							flatPercentageId = "";
						}
						
						var proStatus ='';
						if(json.projectStatus == 'NOTSTARTED'){
							proStatus = 'Not Started';
						}else if(json.projectStatus == 'INPROGRESS'){
							proStatus = 'In-Progress';
						}else if(json.projectStatus == 'ONHOLD'){
							proStatus = 'On-Hold';
						}else if(json.projectStatus == 'COMPLETED'){
							proStatus = 'Completed';
						}else if(json.projectStatus == 'DROPPED'){
							proStatus = 'Dropped';
						}
						
						
						
						var result = "";
						result = result	+ '<div class="project_tabs_inn_table">';
	   		    		
						result = result	+ '<div class="project_details_cta">';
						result = result + '<strong>Status:</strong><span>';
						result = result +  proStatus;
						result = result + '</span>';
						result = result	+ '</div><div class="clearfix"></div>';
						
						result = result + '<input type="hidden" name="dbProjectStatus" id="dbProjectStatus" value="'+json.projectStatus+'" >';
						result = result + '<div class="clearfix"></div>';

						result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
						result = result + '<strong>Project Id:</strong>';
						result = result + '<span>'+projectSequeneNumber+'</span>';
						result = result + '<div class="clearfix"></div>';
						result = result + '</div>';

						result = result	+ '	<div class="project_details_cta" id="projectNameId">';
						result = result + '	<strong>Project name:</strong>';
						result = result + '	<span title="'+json.projectName+'">'+json.projectName+'</span><div class="clearfix"></div>';
						result = result + '	</div>';

						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Manager 1:</strong><span>';

						for (var j = 0; j < json.managersList.length; j++) {
							var manager = json.managersList[j];
							if (json.manager1 == manager.id) {
								result = result + manager.firstName + " "
										+ manager.lastName;
							}
						}
						result = result	+ '</span><div class="clearfix"></div>';
						result = result + '	</div>';

						if (json.manager2 != null && json.manager2 != "" && json.manager2 != "undefined") {
							result = result	+ '<div class="project_details_cta">';
							result = result	+ '	<strong>Manager 2:</strong><span>';

							for (var j = 0; j < json.managersList.length; j++) {
								var manager = json.managersList[j];
								if (json.manager2 == manager.id) {
									result = result + manager.firstName + " "
											+ manager.lastName;
								}
							}
							result = result	+ '</span><div class="clearfix"></div>';
							result = result + '	</div>';
						}
						
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Client:</strong>';
						result = result + '<span title="'+clientName+'">'+clientName+'</span><div class="clearfix"></div>';
						result = result + '<div class="clearfix"></div>';
						result = result + '</div>';
						
						
						
						result = result + '<div class="clearfix"></div>';
						result = result	+ '<div class="project_details_cta">';
						result = result + '<strong>Start date:</strong>';
						result = result + '<span title="'+json.startDateStr+'">'+json.startDateStr+'</span><div class="clearfix"></div>';
						result = result + '</div>';
						result = result	+ '<div class="project_details_cta">';
						result = result + '<strong>End date:</strong>';
						result = result + '<span title="'+endDateStr+'">'+endDateStr+'</span><div class="clearfix"></div>';
						result = result + '</div>';
						result = result + '<div class="clearfix"></div>';
						
						result = result + '<div class="clearfix"></div>';
						
						result = result + '<div class="form-footer clearfix miniFoot">';
						result = result + '<div class="col-lg-12">';
						result = result + '<div class="pull-right">';
						result = result + '<input type="button" value="Transfer" onClick="transferToProject()" class="btn btn-success ml10">';
						result = result + '</div>';
						result = result + '</div>';
						result = result + '</div>';
						
						result = result + '</div>';
						result = result + ' </div>';
						result = result + '</div>';

						$("#projectDetailsDiv").empty();
						$("#projectDetailsDiv").append(result);
						$("#projectDetailsLoading").removeAttr("style"); 

					}
				});
	}else{
		emptyRecords();
	}
	
}
</script>

<script>
function emptyRecords(){
	var result = '';
	 result = result+"   <div class='row'>"; 
	 result = result+"	<div class='col-lg-12'>"; 
	 result = result+"		<div class='dataTables_empty'>"; 
	 result = result+"		</div>"; 
	 result = result+"	</div>"; 
	 result = result+"  </div> ";
	 
	 $("#projectDetailsDiv").empty();
    $("#projectDetailsDiv").append(result);
}
</script>


<script>
function transferToProject(){
	var projectMembers = [];
	 $("input[name='projectMembers[]']:checked").each( function (i) {
		 projectMembers.push($(this).val());
	});
	 
	var orgLocations = [];
	$('input:radio[name=orgLocations]:checked').each(function() 
		    {
		orgLocations.push($(this).val());
	    });
	 
	 var projectUUID = '${projectUUID}';
	 var projectOrgLocationId = document.getElementById("orgLocationProjects").value;
	 var projectId = document.getElementById("orgLocationProjects").value;
	 if(projectMembers.length > 0){
		 
		 $("#projectDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 var projectUUID = '${projectUUID}';
		 var sourceleadUrl = '${sourceleadUrl}';
		 var projectClientUUID = '${projectClientUUID}';
		 
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/transferProjectMembers?${_csrf.parameterName}=${_csrf.token}&projectUUID="+projectUUID+"&projectId="+projectId+"&projectOrgLocationId="+orgLocations+"&projectMembers="+projectMembers, 
		        success: function() {
		        
		        	location.replace(sourceleadUrl+"/projectSummary?projectUUID="+projectUUID+"&projectClientUUID="+projectClientUUID+"&tabType=projectMembers");
			        $("#projectDetailsLoading").removeAttr("style"); 
		        }
		   
	     });
			 
		 
	 }else{
		 alertProjectMembersMessage();
	 }
	
}
</script>

<script>
function alertProjectMembersMessage(){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Select atleast one project member</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='popupClose();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#membersChoicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<script type="text/javascript">
function cancel(){
	$("#membersChoicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

<script>
function popupClose(){
	$(".clients_side_wrap").removeClass("clients_side_wrap_active");
	$(".side_mask").fadeOut(200);
	$("body").css('overflow-y', 'auto');
    $('.addcandidate-popup').fadeOut(200);
}
</script>
	


<script type="text/javascript">
 function prepareChosenScripts(){ 
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
</script>


  <script type="text/javascript">
    $(document).ready(function() {
    $(".proj_close, .side_mask").click(function(){
		$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
        $('.addcandidate-popup').fadeOut(200);
	});	
});
</script>

<script>
function planningRequestsPagination(){  
    $('#planningRequestsPaginationList').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 25, 30, -1], [5, 15, 25, 30, "All"]],
		"aaSorting": [[4,'desc']],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		"sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $('#planningRequestsList').wrap("<div class='overflow_Auto'></div>");
}
</script>
<!-- ====================================Resource RequestsTab End=============================================== -->




<!-- ====================================Budget Requests Tab End=============================================== -->



<script>
function budgetRequests(){
	$(".countClose").hide();
	$(".countShow").show();
	 var sourceleadUrl ='${sourceleadUrl}';
	 var projectUUID = '${projectUUID}';
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getBudgetRequestListByProjectUUID?${_csrf.parameterName}=${_csrf.token}&projectUUID="+projectUUID, 
	        success: function(json) {
	        	var result = "";
	    		result = result + '	<div class="timesheet_head anchorTag-black">';
	    		result = result + '	<ul>';
	    		result = result + '	<li><a href="javascript:void(0)" onclick="addBudgetRequestPopUp();getApproversList();"><i class="glyphicon glyphicon-plus paddingRight"></i>Budget Request</a></li>';
	    		result = result + '	</ul>';
	    		result = result + '	       </div>';
	    		result = result + '	       <div id="budgetRequestPopUpid"></div>';
	    		result = result + '	       <div id="editBudgetRequestPopUpid"></div>';
	    		result = result + '		<div class="project_tabs_inn_table vendor_wrap_inn_table datatable_filter overflow_inherint">';
	    		result = result + '		<table id="projectBudgetRequestList" cellspacing="0" width="100%">';
	    		result = result + '	    <thead>';
	    		result = result + '	     	<tr>';
	            result = result + '	 	<th>Request Id</th>';
	            result = result + '	   <th>Base Line</th>';
	            result = result + '	   <th>Budget Total</th>';
	            result = result + '	   <th>Estimated Hours</th>';
	            result = result + '	   <th>Date</th>';
	            result = result + '	   <th>Initiator</th>';
	            result = result + '	   <th>Action</th>';
	    		result = result + '	         </tr>';
	    		result = result + '	    </thead>';
	    		result = result + '	 	<tbody>';
	    		for(i=0;i<json.length;i++){
	    			var report = json[i];
	    			if(report.estimatedBudgetValue != null  && report.estimatedBudgetValue != "" && report.estimatedBudgetValue != "undefined"){
	    				estimatedBudgetValue = report.estimatedBudgetValue;
	    			}else{
	    				estimatedBudgetValue = "--";
	    			}
	    			
	    			if(report.estimatedHours != null && report.estimatedHours != "" && report.estimatedHours !="undefined"){
	    				estimatedHours = report.estimatedHours;
	    			}else{
	    				estimatedHours = "--";
	    			}
	    			if(report.budgetDescription != null && report.budgetDescription != "" && report.budgetDescription !="undefined"){
	    				budgetDescription = report.budgetDescription;
	    			}else{
	    				budgetDescription = "--";
	    			}
	    			result = result + '	<tr>';
	    			if((report.approverUserIdlLong == '${SourceLeadWeb_user.id}') && (report.budgetStatus == "On Hold" || report.budgetStatus == "Rejected" || report.budgetStatus == "Waiting For Approval")){
	    				result = result + ' 	<td><div class="tbldata-dotdot-sm"> <div class="tbldata-dotdot-100 pull-left">'+report.budgetRequestedId+'</div> <i class="fa fa-pencil ml10 pull-right" onclick="editBudgetrequest('+report.id+')"></i></div></td>';
	    			}else{
	    				result = result + '  <td><div class="tbldata-dotdot-sm"> <div class="tbldata-dotdot-100 pull-left">'+report.budgetRequestedId+'</div></div></td>';
	    			}
					
					result = result + '     <td><div class="tbldata-dotdot-me" title="'+budgetDescription+'">'+budgetDescription+'</div></td>';
					result = result + '     <td><div class="tbldata-dotdot_80" title="'+estimatedBudgetValue+'">'+estimatedBudgetValue+'</div></td>';
					result = result + '     <td><div  class="tbldata-dotdot_80">'+estimatedHours+'</div></td>';
					result = result + '     <td><div  class="tbldata-dotdot_80">'+report.createdStr+'</div></td>';
					result = result + '     <td><div  class="tbldata-dotdot-vsm" title="'+report.createdUserName+'">'+report.createdUserName+'</div></td>';
					if(report.budgetStatus == "Waiting For Approval"){
						result = result + '     <td><div class="tblminwidth-75">';
						result = result + '     <i class="fa fa-check" alt="Approve" title="Approved" onclick="acceptBudgetRequset('+report.id+')"></i>|';
						result = result + '     <i class="fa fa-times" alt="Rejected" title="Rejected" onclick="deleteBudgetRequaet('+report.id+');"></i>';
						result = result + '     <i class="fa fa-clock-o" alt="On hold" title="On hold" onclick="onholdBudgetRequaet('+report.id+');"></i></div></td>';
					}
					if(report.budgetStatus == "Rejected"){
						result = result + '<td><div class="tblminwidth-90 paTp"><b class="Rejec">Rejected</b>';
						 result = result+"  <i class='glyphicon glyphicon-comment colB' id='"+report.id+"' onclick='rejectedMessage(\""+report.rejectedReason+"\");'>";
					 	 result = result+" <div class='app_error_mes' id='rejectedMessage"+report.id+"'>"+report.rejectedReason+"</div></i>";
					 	 result = result+" <br><span class='rejNam' title='"+report.updatedUserName+"'>By : "+report.updatedUserName+"</span>";
						result = result + '</div></td>';
					}
					if(report.budgetStatus == "Approved"){
						result = result + '<td><div class="tblminwidth-90 paTp"><b>Approved</b>';
						 result = result+" <br><span class='rejNam' title='"+report.updatedUserName+"'>By : "+report.updatedUserName+"</span>";
						 result = result + '</div></td>';
					}
					
					if((report.approverUserIdlLong ==  '${SourceLeadWeb_user.id}') &&  report.budgetStatus == "On Hold"){
						result = result + '     <td><div class="tblminwidth-75">';
						result = result + '     <i class="fa fa-check" alt="Approve" title="Approved" onclick="acceptBudgetRequset('+report.id+')"></i>|';
						result = result + '     <i class="fa fa-times" alt="Rejected" title="Rejected" onclick="deleteBudgetRequaet('+report.id+');"></i>';
						result = result + '     </div></td>';
						
					}
					if(report.budgetStatus == "On Hold" && (report.approverUserIdlLong !=  '${SourceLeadWeb_user.id}')){
						result = result + '<td><div class="tblminwidth-90 paTp"><b class="processs-hold">On Hold</b>';
						 result = result+"  <i class='glyphicon glyphicon-comment colB' id='"+report.id+"' onclick='rejectedMessage(\""+report.rejectedReason+"\");'>";
					 	 result = result+" <div class='app_error_mes' id='rejectedMessage"+report.id+"'>"+report.rejectedReason+"</div></i>";
					 	 result = result+" <br><span class='rejNam' title='"+report.updatedUserName+"'>By : "+report.updatedUserName+"</span>";
						result = result + '</div></td>';
					}
					
					result = result + ' </tr>';
	    		}
	    		result = result + '	     </tbody>';
	    		result = result + '	 </table>';
	    		result = result + '</div>';
	    	    $("#budgetRequestsTabDiv").empty();
	    	    $("#budgetRequestsTabDiv").append(result); 
	    	    $("#projectBudgetCount").val(json.length); 
	    	    
	    	    budgetRequestPagination();
	        }
	   
     });
}
$(document).on("mouseover", ".glyphicon-comment", function() { 
	var localId = $(this).attr('id');
	$("#rejectedMessage"+localId).addClass('app_error_mes_active');
	});	
	$(document).on("mouseleave", ".glyphicon-comment", function() { 
	var localId = $(this).attr('id');
	$("#rejectedMessage"+localId).removeClass('app_error_mes_active');
});	
	
function rejectedMessage(local){ 
	   $("#"+local).slideToggle(200);
	   $(".timesheet_appr_table .glyphicon strong, .timesheet_appr_table .glyphicon").click(function(e) {
	       e.stopPropagation();
	   });	
	   
	}
</script>
<script>
function deleteBudgetRequaet(budgetRequestedId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"<form id='deleteBudget_form' name='deleteBudget_form' >";
	result = result +"<input type='hidden' id='budgetRequestedId' name='budgetRequestedId' value='"+budgetRequestedId+"'>";
	result = result +"<input type='hidden' id='loginUserid' name='loginUserid' value='${SourceLeadWeb_user.id}'>";
	result = result +"		<p>Are you sure you want to delete ?</p>";
	result = result +"		<textarea  id='reasonForProjectBudget' name='reasonForProjectBudget'  placeholder='Comments here' field-name='Reason' data-validation='validate_length length2-5000' data-validation-optional='true'></textarea>";
	result = result +"</form>";
	result = result +'		<b href="javascript:void(0)"><input id="delete_budget-id" type="button" value="Delete" onclick="saveBudgetReason('+budgetRequestedId+');"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";

	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
function cancelTermination(){
	$("#popUp").empty();
	 $(".project_inn_mask").fadeOut(200);
}
function saveBudgetReason(budgetRequestedId){
	var sourceleadUrl = '${sourceleadUrl}';
     $("#delete_budget").attr("disabled", "disabled");
	 $("#delete_budget").attr("disabled", "disabled");
	 var formData = getFormData("deleteBudget_form");
	 $.ajax({
				type : "POST",
				data :formData,
				url : sourceleadUrl+"/saveBudgetRequestReason?${_csrf.parameterName}=${_csrf.token}",
				success : function(result) {
					$("#popUp").empty();
	   			 	$(".project_inn_mask").fadeOut(200);
	   			 	budgetRequests();
				},
			}); 
}
$('body').on('blur', '#deleteBudget_form', function() {
	$("#deleteBudget_form").showHelpOnFocus();;
	$("#deleteBudget_form").validateOnBlur(false, validationSettings);  
}); 

function acceptBudgetRequset(budgetRequestedId){
	var sourceleadUrl = '${sourceleadUrl}';
	var userId = '${SourceLeadWeb_user.id}';
	 $.ajax({
				type : "POST",
				url : sourceleadUrl+"/acceptBudgetRequest?${_csrf.parameterName}=${_csrf.token}&budgetRequestedId="+budgetRequestedId+"&userId="+userId,
				success : function(result) {
					budgetRequests();
				},
			}); 
}
function onholdBudgetRequaet(budgetRequestedId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"<form id='deleteBudget_form' name='deleteBudget_form' >";
	result = result +"<input type='hidden' id='budgetRequestedId' name='budgetRequestedId' value='"+budgetRequestedId+"'>";
	result = result +"<input type='hidden' id='loginUserid' name='loginUserid' value='${SourceLeadWeb_user.id}'>";
	result = result +"		<p>Are you sure you want to reject ?</p>";
	result = result +"		<textarea  id='reasonForProjectBudget' name='reasonForProjectBudget'  placeholder='Comments here' field-name='Reason' data-validation='validate_length length2-5000' data-validation-optional='true'></textarea>";
	result = result +"</form>";
	result = result +'		<b href="javascript:void(0)"><input id="delete_budget-id" type="button" value="Delete" onclick="saveBudgetReason('+budgetRequestedId+');"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";

	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".vendor_wrap_mask").fadeIn(200);
	
	var sourceleadUrl = '${sourceleadUrl}';
	var userId = '${SourceLeadWeb_user.id}';
	 $.ajax({
				type : "POST",
				url : sourceleadUrl+"/onHoldBudgetRequest?${_csrf.parameterName}=${_csrf.token}&budgetRequestedId="+budgetRequestedId+"&userId="+userId,
				success : function(result) {
					budgetRequests();
				},
			}); 
}

function editBudgetrequest(budgetRequestedId){
	 $("#budgetRequestPopUpid").empty();
	var sourceleadUrl = '${sourceleadUrl}';
	 
	 $.ajax({
				type : "POST",
				url : sourceleadUrl+"/getBudgetRequestDetailsById?${_csrf.parameterName}=${_csrf.token}&budgetRequestedId="+budgetRequestedId,
				success : function(json) {
					var result = "";
					result = result + '	<div class="collapse-div-adduser prRight col-md-4 show-div prRight_new"> <i class="fa fa-close close-div" onClick="editCloseDiv();"></i>';
					result = result + '		<div class="form-horizontal clearfix">';
					result = result + '<form id="budgetRequest-form" name="budgetRequest-form">';
					result = result + '	    <input type="hidden" id="projectUUID" name="projectUUID" value="${projectUUID}">';
					result = result + '	    <input type="hidden" id="userId" name="loginUserid" value="${SourceLeadWeb_user.id}">';
					result = result + '	    <input type="hidden" id="budgetRequestedId" name="budgetRequestedId" value="'+json.id+'">';
					result = result + '	    <div class="clearfix mb10">';
					result = result + '	    <div class="clearfix mb10">';
					result = result + '	      <div class="col-md-12">';
					result = result + '	        <input type="text" class="form-control" value="'+json.budgetDescription+'" id="baseLine" name="baseLine" placeholder="Descrption *" field-name="Description" data-validation="required validate_Space validate_length length1-250">';
					result = result + '	      </div>';
					result = result + '	    </div>    ';                        
				 
					result = result + '	    <div class="clearfix mb10">';
					result = result + '	      <div class="col-md-12" id="bgApErrMsg">';
					result = result + '	        <select class="chosen-select" field-name="Approver" id="budgetApprover" name="budgetApprover" data-validation="required" onchange="removeErrMsg();">';
					result = result + '	        </select>';
					result = result + '	        </div>';
					result = result + '	      </div>'; 
					 
					result = result + '	    <div class="clearfix mb10">';
					result = result + '	      <div class="col-md-12">';
					result = result + '	        <input type="text" class="form-control inlineField" value="'+json.estimatedHours+'" id="estimatedHours" placeholder="Estimated Hours *" name="estimatedHours" field-name="Estimated Hours"  data-validation="required">';
					result = result + '	      </div>';
					result = result + '	    </div>';
				  
					result = result + '	    <div class="clearfix mb10">';
					result = result + '	      <div class="col-md-12">';
					result = result + ' <div class="input-group">';
					result = result + '  <div class="input-group-btn tblminwidth-75" style="min-width: 65px !important;">';
					result = result + '  <input type="text" class="pull-left btn btn-primary btn-block" id="estimatedBudgetCurrency" name="estimatedBudgetCurrency" value="${projectCurrency}" readonly>';
					result = result + ' </div>';
					result = result + '	        <input type="text" class="form-control " value="'+json.estimatedBudgetValue+'" id="estimatedBudget" name="estimatedBudget" placeholder="Estimated Budget *" field-name="Estimated Budget" data-validation="required validate_Space validate_float validate_length length1-16">';
					result = result + ' </div>';
					result = result + ' </div>';
					
					result = result + '	    <div class="clearfix mt10 mb10"></div>';
					 
					result = result + '	    <div class="clearfix mr">';
					result = result + '	      <button type="button" class="btn btn-sm btn-primary pull-right" id="save_budget" onclick="saveBudgetRequest();">Update</button>';
					result = result + '	    </div>';
					result = result + '	  </div>';
					result = result + '</form>';
					result = result + '	</div>';
					 $("#editBudgetRequestPopUpid").empty();
					 $("#editBudgetRequestPopUpid").append(result);
					 $("#editBudgetRequestPopUpid").show();
					 getApproversList(json.approverUserIdlLong);
					 $('#estimatedBudgetCurrency').selectpicker('refresh');
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
function editCloseDiv(){
	$("#editBudgetRequestPopUpid").hide();
}
</script>
<script>
function budgetRequestPagination(){  
    $('#projectBudgetRequestList').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 25, 30, -1], [5, 15, 25, 30, "All"]],
		"aaSorting": [[4,'desc']],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		"sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $('#planningRequestsList').wrap("<div class='overflow_Auto'></div>");
}
</script>
<script>
function addBudgetRequestPopUp(){
	var result = "";
	result = result + '	<div class="collapse-div-adduser prRight col-md-4 show-div prRight_new"> <i class="fa fa-close close-div" onClick="closeDiv();"></i>';
	result = result + '		<div class="form-horizontal clearfix">';
	result = result + '<form id="budgetRequest-form" name="budgetRequest-form">';
	result = result + '	    <input type="hidden" id="projectUUID" name="projectUUID" value="${projectUUID}">';
	result = result + '	    <input type="hidden" id="userId" name="loginUserid" value="${SourceLeadWeb_user.id}">';
	result = result + '	    <div class="clearfix mb10">';
	result = result + '	    <div class="clearfix mb10">';
	result = result + '	      <div class="col-md-12">';
	result = result + '	        <input type="text" class="form-control input-sm" value="" id="baseLine" name="baseLine" placeholder="Description *" field-name="Description" data-validation="required validate_Space validate_length length1-250">';
	result = result + '	      </div>';
	result = result + '	    </div>    ';                        
 
	result = result + '	    <div class="clearfix mb10">';
	result = result + '	      <div class="col-md-12" id="bgApErrMsg">';
	result = result + '	        <select class="chosen-select" field-name="Approver" id="budgetApprover" name="budgetApprover" data-validation="required" onchange="removeErrMsg()">';
	result = result + '	        </select>';
	result = result + '	        </div>';
	result = result + '	      </div>'; 
	 
	result = result + '	    <div class="clearfix mb10">';
	result = result + '	      <div class="col-md-12">';
	result = result + '	        <input type="text" class="form-control input-sm inlineField" value="" id="estimatedHours"  placeholder="Estimated Hours *" name="estimatedHours"  field-name="Estimated Hours"  data-validation="required">';
	result = result + '	      </div>';
	result = result + '	    </div>';
 
	result = result + '	    <div class="clearfix mb10">';
	result = result + '	      <div class="col-md-12">';
	result = result + ' <div class="input-group">';
	result = result + '  <div class="input-group-btn tblminwidth-75" style="min-width: 65px !important;">';
	result = result + '  <input type="text" class="pull-left btn btn-primary btn-block" id="estimatedBudgetCurrency" name="estimatedBudgetCurrency" value="${projectCurrency}" readonly>';
	result = result + ' </div>';
	result = result + ' 	<input type="text" value="" id="estimatedBudget" name="estimatedBudget" class="form-control" placeholder="Estimated Budget *"';
	result = result + ' 		data-validation="required validate_Space validate_float validate_length length1-16" data-validation-optional="true" class="form-control" field-name="Estimated Budget" >';
	result = result + ' </div>';
	result = result + ' </div>';
	
	result = result + '	    </div>';                                                                                    
	 
	result = result + '	    <div class="clearfix mr">';
	result = result + '	      <button type="button" class="btn btn-sm btn-primary pull-right" id="save_budget" onclick="saveBudgetRequest();">Add</button>';
	result = result + '	    </div>';
	result = result + '	  </div>';
	result = result + '</form>';
	result = result + '	</div>';
	 $("#budgetRequestPopUpid").empty();
	 $("#budgetRequestPopUpid").append(result);
	 $("#budgetRequestPopUpid").show();
	 $("#editBudgetRequestPopUpid").empty();
	 document.getElementById("editBudgetRequestPopUpid").style.display="none";
	 $('#estimatedBudgetCurrency').selectpicker('refresh');
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
function closeDiv(){
	$("#budgetRequestPopUpid").hide();
}
function removeErrMsg(){
	$("#bgApErrMsg").find("p.jquery_form_error_message").remove(); 
}
</script>
<script>
function getApproversList(approverUserId){
var sourceleadUrl ='${sourceleadUrl}';
var orgLocationId = '${SourceLeadWeb_orgLocations.id}';
	$.ajax({
	    	type: "GET",
	    	 url: sourceleadUrl+"/getResourceRequestApproversList?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId,
	    		success: function(json) {
    			var output = "";
     			output = output + "<option value=''>--Select Approver--</option>";
	     		for (var i=0; i<json.length; i++)
	     		{
		     		var opt = json[i];
		     		
		     		var fullname = '';
		     		if(opt.lastName != null && opt.lastName != '' && opt.lastName != 'undefined'){
		     			fullname = opt.firstName+" "+opt.lastName;
		     		}else{
		     			fullname = opt.firstName;
		     		}
		     		 
		     		 if(opt.id == approverUserId){
		        			output = output + "<option value='"+opt.id+"' selected>"+fullname+" ("+opt.userType+")</option>";
		        		}else{
		        			output = output + "<option value='"+opt.id+"'> "+fullname+" ("+opt.userType+")</option>";
		        		}
		     		 
	     		}
	     		$("#budgetApprover").empty();
	     		$("#budgetApprover").append(output);
	     		$('#budgetApprover').trigger("chosen:updated");
	         }
	 });
}
</script>
<script>
function saveBudgetRequest(){
	 var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
		}; 
	
	 if ($('#budgetRequest-form').validate(false, validationSettings)) {
			 var sourceleadUrl = '${sourceleadUrl}';
			 $("#save_budget").attr("disabled", "disabled");
			 $("#save_budget").attr("disabled", "disabled");
			 var formData = getFormData("budgetRequest-form");
			 $.ajax({
						type : "POST",
						data :formData,
						url : sourceleadUrl+"/saveBudgetRequest?${_csrf.parameterName}=${_csrf.token}",
						success : function(result) {
							budgetRequests();
						},
					}); 
			return true;
		} else {
			return false;
		}
}
$('body').on('blur', '#budgetRequest-form', function() {
	$("#budgetRequest-form").showHelpOnFocus();;
	$("#budgetRequest-form").validateOnBlur(false, validationSettings);  
}); 
</script>
 


