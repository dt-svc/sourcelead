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
display: none;  border: none; min-width: 10px; text-align: center; cursor: pointer; max-width: 25px; 
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
function filterStatus() {
	$("#exportPop").hide();
};
 </script>
 

 
 <script>
 function projectTeam(projectId,startDateStr,endDateStr,status,projectName){
	 var projectUUID = '${projectUUID}';
	 var sourceleadUrl = '${sourceleadUrl}';
	 var postedOrgLocationId = '${postedOrgLocationId}';
	 var loginOrgLocationId = '${SourceLeadWeb_orgLocations.id}'
	 var projectClientUUID = '${projectClientUUID}';
	 	 
	 $("#employeesLoadingImg").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;'); 
	 var result = "";
 	 result = result + '    	<div class="col-md-12 mt">';
 	
	 result = result + '     </div>';
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
			
			result = result + '	<li><a onClick="filterStatus()" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-filter"></i><span>Roles</span><i class="glyphicon glyphicon-play"></i></a>';
		    result = result + '   	<div class="filterLi dropdown-menu bootstrap_dropdownMenu dropdownPadding">';
			result = result + '       	<ul>';
					<c:forEach var="roles" items="${rolesList}">  
					result = result + '	<li><input  id="roleName${report.id}" name="roleName" value ="${roles.id}"   onclick="getFilterEmployeeDetails('+projectId+',\''+startDateStr+'\',\''+endDateStr+'\',\''+status+'\',\''+projectName+'\')" type="checkbox"><label for="roleName${roles.id}">${roles.displayName}</label></li>';
				  </c:forEach>
				  
		    result = result + '           </ul>';
			result = result + '       </div>'; 
			result = result + '   </li>';
			
		    result = result + '	<li><a onClick="filterStatus()" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-filter"></i><span>Status</span><i class="glyphicon glyphicon-play"></i></a>';
		    result = result + '   	<div class="filterLi dropdown-menu bootstrap_dropdownMenu dropdownPadding">';
			result = result + '       	<ul>';
			result = result + '           	<li><input id="c5" type="checkbox" name="employeeStatus" checked="true" value ="All" onclick="getFilterEmployeeDetails('+projectId+',\''+startDateStr+'\',\''+endDateStr+'\',\''+status+'\',\''+projectName+'\');"><label for="c5">All</label></li>'; 
			result = result + '				<li><input id="c6" type="checkbox"   name="employeeStatus" value ="Active" onclick="getFilterEmployeeDetails('+projectId+',\''+startDateStr+'\',\''+endDateStr+'\',\''+status+'\',\''+projectName+'\');"><label for="c6">Active</label></li>';
			result = result + '				<li><input id="c7" name="employeeStatus" type="checkbox" value ="In-Active" onclick="getFilterEmployeeDetails('+projectId+',\''+startDateStr+'\',\''+endDateStr+'\',\''+status+'\',\''+projectName+'\');"><label for="c7">In-Active</label></li> ';
			result = result + '           </ul>';
			result = result + '       </div>'; 
			result = result + '   </li>';
	 
				'<sec:authorize access="hasAnyRole(\'ROLE_EXPORT_EMPLOYEE\')">';
					result = result +'<li class="dropdown">';
					if(status != 'COMPLETED' &&  status != '' &&  status != 'undefined' ){
						result = result +' <a title="Export" id="export-pop" class="dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-export expIcon"></i>&nbsp;</a>';	
					} 
					
					result = result +'<div class="dropdown-menu bootstrap_dropdownMenu">';
					result = result +'<ul class="ul_padding">';
					if(status != 'COMPLETED' &&  status != '' &&  status != 'undefined' ){
						result = result +'<li onclick="exportToPdf(\''+projectId+'\');"><i class="fa fa-file-pdf-o text-danger paddingRight"></i>PDF</li>';
						
						result = result +'<li onclick="exportToExcel(\''+projectId+'\');"><i class="fa fa-file-excel-o text-success paddingRight"></i>Excel</li>';
			
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
		
	    $("#tab-1").empty();
	    $("#tab-2").empty();  
		$("#tab-3").empty();  
		$("#tab-4").empty();
		$("#tab-5").empty();
		$("#tab-6").empty(); 
	    $("#employeesLoadingImg").empty();
	    $("#tab-6").append(result);  
	    
	    $("#tab-6").show();
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
		 values = "All";
	 }
	 
	 var roleIds = [];
		$('input:checkbox[name=roleName]:checked').each(function() 
	     {
			roleIds.push($(this).val());
	     });
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
	        url: sourceleadUrl+"/getProjectEmployeeListById?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&status="+values+"&roleIds="+roleIds, 
	        success: function(json) {
	        	
	        	var result = "";
	        	result = result + '        <div class="project_tabs_inn_table overflow-data">';
	    		result = result + ' <div class="success_pop" id="selector3" style="display: none;">';
	    		result = result + ' <i class="fa fa-check-circle"></i><span id="docMsgDiv3"></span>';
	    		result = result + ' </div>';
	    	    result = result + '        <div id="employeesLoadingImg"></div>';
	    	    result = result + '	<table id="projectEmployeePagination" class="display" cellspacing="0" width="100%">';
	    		result = result + '	<thead>';
	    		result = result + '		<tr>';
	    		  if('${postedOrgLocationId}' == '${SourceLeadWeb_orgLocations.id}'){
	    			  result = result + ' <th><input type="checkbox" onclick="selectAllMembers();"  id="selectAllMembers" ></th>';
	    		  }
	    		
	            result = result + '	 	<th>Emp Name</th>';
	        /*     result = result + '	 	<th>Emp Email</th>';*/
	            result = result + '	 	<th>Emp Role</th>'; 
	            result = result + '	     <th>Start Date<br>(<small class="custom-small">'+projectempListDate+'</small>)</th>';
	            result = result + '	     <th>End Date<br>(<small class="custom-small">'+projectempListDate+'</small>)</th>';
	            result = result + '	     <th>Bill Rate</th>';
	            result = result + '	     <th>Pay Rate</th>';
	            result = result + '	     <th>Estimated hrs</th>';
	            result = result + '	     <th>Estimated Budget</th>';
	            result = result + '	     <th>Actual hrs</th>';
	            result = result + '	     <th>Action</th>';
	            result = result + '	 </tr>';
	            result = result + '	</thead>';
	            result = result + '	<tbody>';
	        	for (var i=0; i<json.length; i++)
				{      
		        		var opt = json[i];
		        		var role = '';
		        		var billRate = '';
		        		var payRate = '';
		        		var estimatedHours = '';
		        		var estimatedBudget = '';
		        		
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
		        		
		        		
		        		if(opt.currentBillRate != null && opt.currentBillRate != 'undefined' && opt.currentBillRate != ''){
		        			billRate = opt.billRateCurrency+" "+opt.currentBillRate+" /"+opt.rateType
		        		}else{
		        			billRate = "-----"
		        		}
		        		
		        		if(opt.payRate != null && opt.payRate != 'undefined' && opt.payRate != ''){
		        			payRate = opt.payRateCurrency+" "+opt.payRate+" /"+opt.payRateType
		        		}else{
		        			payRate = "-----"
		        		}
		        		
		        		if(opt.estimatedHours != null && opt.estimatedHours != 'undefined' && opt.estimatedHours != ''){
		        			estimatedHours = opt.estimatedHours;
		        		}else{
		        			estimatedHours = "------"
		        		}
		        		
		        		if(opt.estimatedBudget != null && opt.estimatedBudget != 'undefined' && opt.estimatedBudget != ''){
		        			estimatedBudget = opt.estimatedBudget;
		        		}else{
		        			estimatedBudget = "------"
		        		}
		        		
		        		result = result + '	 <tr>';
		        		 if('${postedOrgLocationId}' == '${SourceLeadWeb_orgLocations.id}'){
		        			 if(opt.status == 1){
		        				 result = result + '<td><input type="checkbox" onclick="selectedMember();"  class="projectMembersChecks" value='+opt.id+' name="projectMembers[]" ></td>';
		        			 }else{
		        				 result = result + '<td>--</td>';
		        			 }
		        		 }
		        		
		                result = result + '	 	<td><div class="tbldata-dotdot" title="'+opt.candidateName+'"><span title="'+opt.candidateName+'">'+opt.candidateName+'</span></div></td>';
		              /*   result = result + '	 	<td><div class="tbldata-dotdot" title="'+opt.empEmail+'"><span title="'+opt.empEmail+'">'+opt.empEmail+'</span></div></td>'; */
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
		                
		                result = result + '	     <td><div class="tbldata-dotdot_80"><span title="'+billRate+'">'+billRate+'</span></div></td>';
		                result = result + '	     <td><div class="tbldata-dotdot_80"><span title="'+payRate+'">'+payRate+'</span></div></td>';
		                result = result + '	     <td><div class="tbldata-dotdot_80"><span title="'+estimatedHours+'">'+estimatedHours+'</span></div></td>';
		                result = result + '	     <td><div class="tbldata-dotdot_80"><span title="'+estimatedBudget+'">'+estimatedBudget+'</span></div></td>';
		                result = result + '	     <td><div class="tbldata-dotdot_80"><span title="'+opt.approvedHrs+'">'+opt.approvedHrs+'</span></div></td>';
		                
		                
		                if('${postedOrgLocationId}' == '${SourceLeadWeb_orgLocations.id}' || '${isClientOrgActive}' == "N"){
		                	
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
			    		      maxDate: projectEndDate,
			    		      minDate: selected,
			    		      changeMonth: true,
			    			  changeYear: true, 
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
	result = result +"		<textarea  id='reasonForProject' name='reasonForProject'  placeholder='Terminate reason' ></textarea>";
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
	 $(".vendor_wrap_mask").fadeOut(200);
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
		   			 	projectTeam(projectId,startDateStr,endDateStr,status);
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
   
   result = result +'<input type="text" class="form-control calendar" readonly="readonly" placeholder="DD-MM-YYYY" id="reStartDate" name="reStartDate" field-name="Start Date" data-validation="required">';

   result = result +'</div>';
   result = result +'</div>';
   
   result = result +'<div class="form-group">';
   result = result +'<label class="col-lg-3">End date</label>';
   result = result +'<div class="col-lg-9 positionRelative">';

   result = result +'<input type="text" class="form-control calendar" readonly="readonly" placeholder="DD-MM-YYYY" id="reEndDate" name="reEndDate"  field-name="End Date" />';

   result = result +'</div>';
   result = result +'</div>';

   result = result +'</div>';
   result = result +'</div>';
   result = result +'	</div>';
   
   $("#ReAssignProjectId").empty();
   $("#ReAssignProjectId").append(result);
	  
   var reStatertDate = $('#reStartDate').val();
   
	
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

 var reStatertDate = $('#reStartDate').val()
  if(projectEndDateStr != null && projectEndDateStr != "" && projectEndDateStr != 'undefined'){
	  $('#reStartDate').datepicker({
	      dateFormat: finalDateFormatRecomondedCandCertSave,
	      changeMonth: true,
	  	  changeYear: true, 
		  minDate:endDate,
		  maxDate:projectEndDateStr,
		  showOn: 'focus',
          showButtonPanel: true,
          closeText: 'Clear',
          onClose: function (selected) {
        	   if(selected != null && selected != '' && selected != 'undefined'){
        		    removevalidation();
	   	            var dt = toDate('#reStartDate');
	   	            dt.setDate(dt.getDate() + 1);
	   	            $("#reEndDate").datepicker("option", "minDate", dt);
	   	            var event = arguments.callee.caller.caller.arguments[0];
                   // If "Clear" gets clicked, then really clear it
                   if ($(event.delegateTarget).hasClass('ui-datepicker-close')) {
                       $(this).val('');
                   }
        	   }
	    	    
	        }
	  
	  });
	  
	  $('#reEndDate').datepicker({
	      dateFormat: finalDateFormatRecomondedCandCertSave,
	      changeMonth: true,
	  	  changeYear: true, 
	      minDate:endDate,
	      maxDate:projectEndDateStr,
	      showOn: 'focus',
          showButtonPanel: true,
          closeText: 'Clear',
          onClose: function (selected) {
        	  if(selected != null && selected != '' && selected != 'undefined'){
	            var dt = toDate('#reEndDate');
	            dt.setDate(dt.getDate() - 1);
	            $("#reStartDate").datepicker("option", "maxDate", dt);
	            var event = arguments.callee.caller.caller.arguments[0];
                // If "Clear" gets clicked, then really clear it
                if ($(event.delegateTarget).hasClass('ui-datepicker-close')) {
                    $(this).val('');
                }
        	  }
	        }
	  });
  }	else{
	  $('#reStartDate').datepicker({
	      dateFormat: finalDateFormatRecomondedCandCertSave,
	      changeMonth: true,
	  	  changeYear: true, 
		  minDate:endDate,
		  showOn: 'focus',
          showButtonPanel: true,
          closeText: 'Clear',
          onClose: function (selected) {
	            var dt = toDate('#reStartDate');
	            dt.setDate(dt.getDate() + 1);
	            $("#reEndDate").datepicker("option", "minDate", dt);
	            var event = arguments.callee.caller.caller.arguments[0];
                // If "Clear" gets clicked, then really clear it
                if ($(event.delegateTarget).hasClass('ui-datepicker-close')) {
                    $(this).val('');
                }
	            removevalidation();
	        }
	  });
	  
	  
	
	  $('#reEndDate').datepicker({
	      dateFormat: finalDateFormatRecomondedCandCertSave,
	      changeMonth: true,
	  	  changeYear: true, 
	      minDate:endDate,
	      maxDate:reStatertDate,
	      showOn: 'focus',
          showButtonPanel: true,
          closeText: 'Clear',
          onClose: function (selected) {
	            var dt = toDate('#reEndDate');
	            dt.setDate(dt.getDate() - 1);
	            $("#reStartDate").datepicker("option", "maxDate", dt);
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
	       /*  var frm = $('#reAssignProject_form').serializeFormJSON();
	        var con = JSON.stringify(frm);
	        con = con.replace(/[{}]/g, "");
	        var sourceleadUrl ='${sourceleadUrl}'; */
	        var formData = getFormData("reAssignProject_form");
	        $.ajax({
		    	type: "POST",
		    	data: formData,
		        url: sourceleadUrl+"/saveReAssignProject?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+projectMemberId, 
		        success: function(data) {
		        	if(data="success"){
		        	 	//$("#Re-AssignProject").modal('hide');
		        	 	$('#Re-AssignProject').hide();
		        	    $('.modal-backdrop').hide();
		        	 	projectTeam(projectId,startDateStr,endDateStr,status);
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
	$('#reStartDate').val('');
	$('#reEndDate').val('');
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

 
<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script>  