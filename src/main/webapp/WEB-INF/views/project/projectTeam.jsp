<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
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
	 var postedSharedProjectOrgLocationId = '${postedSharedProjectOrgLocationId}';
	 	 
	 $("#employeesLoadingImg").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;'); 
	 var result = "";
 	 result = result + '    	<div class="col-md-12 mt">';
 	
	 result = result + '     </div>';
	 result = result + '</ul>';
	 
	 result = result + '<div class="project_tabs mb">';
	 result = result + '<input type="hidden" id="projectId" value="'+projectId+'">';
	 result = result + '<input type="hidden" id="startDateStr" value="'+startDateStr+'">';
	 result = result + '<input type="hidden" id="endDateStr" value="'+endDateStr+'">';
	 result = result + '<input type="hidden" id="status" value="'+status+'">';
	 result = result + '<input type="hidden" id="projectName" value="'+projectName+'">';
	 
	 
	//Resources Tab
	 result = result + '<div id="tab-a" class="newPrTabs_inn" style="display: block;">';
	 result = result + '    	<div class="timesheet_head clearfix anchorTag-black">';
		 result = result	+ '<ul>';
			if(status != 'COMPLETED' &&  status != '' &&  status != 'undefined' ){
			result = result +'<li>';
	 		'<sec:authorize access="hasAnyRole(\'ROLE_ASSIGN_EMPLOYEE_TO_PROJECT\')">';
	 		 
	 	/* 	if(postedOrgLocationId == loginOrgLocationId || postedSharedProjectOrgLocationId == loginOrgLocationId){
	 			  result = result + '<a href="javascript:void(0)" onclick="getAssociates();" id="addDiv">';
		 		  result = result + '<i class="fa fa-exchange paddingRight"></i>&nbsp;Transfer Emp</a>&nbsp;&nbsp;&nbsp;&nbsp;'; 
	 		 } */
	 		
	 	     if('${isClientOrgActive}' == "N" || postedOrgLocationId == loginOrgLocationId || postedSharedProjectOrgLocationId == loginOrgLocationId){   
	 			 result = result + '<a href="'+sourceleadUrl+'/addNewProjectMembers?projectUUID='+projectUUID+'&projectClientUUID='+projectClientUUID+'">';
	 		     result = result + '<i class="glyphicon glyphicon-plus"></i>Assign Emp</a></li>';
	 		}  
	 		   
	 		
 		    '</sec:authorize>';

	 		result = result +'</li>';
			} 
			
			
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
			
			result = result	+ '<div class="pull-left cand-advsrch advsrch_chosen">';
			result = result	+ '     <button type="button" class="btn btn-primary dropdown-toggle btn-sm mr" onclick="resetPorjectTeamFilter();" title="Refresh">';
			result = result	+ '	    <span class="fa fa-refresh"></span>';
			result = result	+ '	</button>';
			result = result	+ '	<a href="javascript:void(0)" class="btn btn-default btn-sm anchorBlack text-center" title="Filter Search" onclick="openFilterTeamPop();"><i class="fa fa-filter faFilter padding_right"></i>';
			result = result	+ '	</a>';
			result = result	+ ' </div>';
			
			
			result = result + '</div>';
			getFilterEmployeeDetails();
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
 function getFilterEmployeeDetails(){
	 var projectId = $("#projectId").val();
	 var startDateStr = $("#startDateStr").val();
	 var endDateStr = $("#endDateStr").val();
	 var status = $("#status");
	 var projectName = $("#projectName").val();
	 
	 var values = [];
	 var chekbox = $('input:checkbox[name=employeeStatus]:checked').val();
	 if(chekbox != 'undefined' && chekbox != '' && chekbox != null){
			$('input:checkbox[name=employeeStatus]:checked').each(function() 
				    {
						values.push($(this).val());
				    });
	 }
	 
	 var roleIds = [];
		$('input:checkbox[name=roleName]:checked').each(function() 
	     {
			roleIds.push($(this).val());
	     });
		
		var teamIds = [];
		$('input:checkbox[name=teams]:checked').each(function() 
			    {
			teamIds.push($(this).val());
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
	    	type: "GET",
	        url: sourceleadUrl+"/getProjectEmployeeListById?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&status="+values+"&roleIds="+roleIds+"&teamIds="+teamIds, 
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
	    		  if(('${postedOrgLocationId}' == '${SourceLeadWeb_orgLocations.id}') || ('${postedSharedProjectOrgLocationId}' == '${SourceLeadWeb_orgLocations.id}')){
	    			  result = result + ' <th><input type="checkbox" onclick="selectAllMembers();"  id="selectAllMembers" ></th>';
	    		  }
	    		
	            result = result + '	 	<th>Emp Name</th>';
	        /*     result = result + '	 	<th>Emp Email</th>';
	            result = result + '	 	<th>Emp Role</th>'; */
	            result = result + '	     <th>Start Date<br>(<small class="custom-small">'+projectempListDate+'</small>)</th>';
	            result = result + '	     <th>End Date<br>(<small class="custom-small">'+projectempListDate+'</small>)</th>';
	            result = result + '	     <th>Bill Rate</th>';
	            result = result + '	     <th>Pay Rate</th>';
	            result = result + '	     <th>Estimated hrs</th>';
	            result = result + '	     <th>Estimated Budget</th>';
	            result = result + '	     <th>Actual hrs</th>';
	            result = result + '	     <th>%Of Work</th>';
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
		        			projectEndDateStr = "-----";
		        		}
		        		
		        		var projectStartDateStr = "";
		        		if(opt.projectAssignedDateStr != null && opt.projectAssignedDateStr != ''){
		        			projectStartDateStr = opt.projectAssignedDateStr;
		        		}else{
		        			projectStartDateStr = "-----";
		        		}
		        		
		        		
		        		if(opt.currentBillRate != null && opt.currentBillRate != 'undefined'){
		        			billRate = opt.billRateCurrency+" "+opt.currentBillRate+" /"+opt.rateType
		        		}else{
		        			billRate = "-----";
		        		}
		        		
		        		if(opt.payRate != null && opt.payRate != 'undefined'){
		        			payRate = opt.payRateCurrency+" "+opt.payRate+" /"+opt.payRateType
		        		}else{
		        			payRate = "-----";
		        		}
		        		
		        		if(opt.estimatedHours != null && opt.estimatedHours != 'undefined' && opt.estimatedHours != ''){
		        			estimatedHours = opt.estimatedHours;
		        		}else{
		        			estimatedHours = "------";
		        		}
		        		
		        		if(opt.estimatedBudget != null && opt.estimatedBudget != 'undefined' && opt.estimatedBudget != ''){
		        			estimatedBudget = opt.estimatedBudget;
		        		}else{
		        			estimatedBudget = "------";
		        		}
		        		
		        		if(opt.percentageOfWork != null && opt.percentageOfWork != 'undefined' && opt.percentageOfWork != ''){
		        			percentageOfWork = opt.percentageOfWork;
		        		}else{
		        			percentageOfWork = "------";
		        		}
		        		result = result + '	 <tr>';
		        		 if(('${postedOrgLocationId}' == '${SourceLeadWeb_orgLocations.id}') || ('${postedSharedProjectOrgLocationId}' == '${SourceLeadWeb_orgLocations.id}')){
		        			 if(opt.status == 1){
		        				 result = result + '<td><input type="checkbox" onclick="selectedMember();"  class="projectMembersChecks" value='+opt.id+' name="projectMembers[]" ></td>';
		        			 }else{
		        				 result = result + '<td>--</td>';
		        			 }
		        		 }
		        		
		                result = result + '	 	<td><div class="tbldata-dotdot" title="'+opt.candidateName+'"><span title="'+opt.candidateName+'">'+opt.candidateName+'</span></div></td>';
		              /*   result = result + '	 	<td><div class="tbldata-dotdot" title="'+opt.empEmail+'"><span title="'+opt.empEmail+'">'+opt.empEmail+'</span></div></td>'; 
		                result = result + '	 	<td><div class="tbldata-dotdot_80" title="'+role+'"><span title="'+role+'">'+role+'</span></div></td>';*/
		                result = result + '	     <td><div class="tbldata-dotdot_80"><span title="'+projectStartDateStr+'">'+projectStartDateStr+'</span></div></td>';
		                if(status == 'COMPLETED'){
		                	result = result + '<td><div>'+projectEndDateStr+'</div></td>';
		                }else{
		                	  result = result + '	     <td><div class="tbldata-dotdot_80"><span title="'+projectEndDateStr+'">'+projectEndDateStr+'</span></div></td>';
		                }
		                
		                result = result + '	     <td><div class="tbldata-dotdot_80"><span title="'+billRate+'">'+billRate+'</span></div></td>';
		                result = result + '	     <td><div class="tbldata-dotdot_80"><span title="'+payRate+'">'+payRate+'</span></div></td>';
		                result = result + '	     <td><div class="tbldata-dotdot_80"><span title="'+estimatedHours+'">'+estimatedHours+'</span></div></td>';
		                result = result + '	     <td><div class="tbldata-dotdot_80"><span title="'+estimatedBudget+'">'+estimatedBudget+'</span></div></td>';
		                result = result + '	     <td><div class="tbldata-dotdot_80"><span title="'+opt.approvedHrs+'">'+opt.approvedHrs+'</span></div></td>';
		                result = result + '	     <td><div class="tbldata-dotdot_80"><span title="'+percentageOfWork+'">'+percentageOfWork+'</span></div></td>';
		                
		                if('${postedOrgLocationId}' == '${SourceLeadWeb_orgLocations.id}' || '${isClientOrgActive}' == "N" || '${postedSharedProjectOrgLocationId}' == '${SourceLeadWeb_orgLocations.id}'){
		                	
		                	 if(opt.status != null && opt.status != '' && opt.status !='undefined' && opt.status != 0){
				                	result = result + '	<td><div class="tbldata-dotdot_80">';
				                	if(status != 'COMPLETED'){
				                		
				                		'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_PROJECT_EMPLOYEES\')">'; 
				                		   
				                		 if(opt.rateHistroyList == 'Y'){
				                			 result = result + '<span title="Payrate History"><i class="glyphicon fa fa-money" onClick="getRateTable('+opt.id+'\,\''+opt.candidateName+'\')";></i></span>&nbsp;&nbsp;';
				                		  }  
				                		     result = result + '<span title="Edit Employee"><i class="glyphicon glyphicon-edit" onClick="editProjectAssignedMember('+opt.id+'\,\''+opt.candidateName+'\')";></i></span>&nbsp;&nbsp;';
				                		 '</sec:authorize>';
				                		
				                		'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES\')">'; 
				                		   result = result + '<span title="Delete Employee"><i class="glyphicon glyphicon-trash" onClick="deleteProject('+opt.id+'\,\''+projectId+'\',\''+startDateStr+'\',\''+endDateStr+'\')";></i></span>';
				                		'</sec:authorize>';
				                		
				                		'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES\')">'; 
				                		      result = result + '---';
				                		'</sec:authorize>';
				                	}
				                	result = result +'</div></td>';
			                     }
			                     else{
			                    	 result = result + '<td><div class="tblminwidth-50">';
			                    	 if(opt.rateHistroyList == 'Y'){
			                			 result = result + '<span title="Edit Employee"><i class="glyphicon fa fa-money" onClick="getRateTable('+opt.id+'\,\''+opt.candidateName+'\')";></i></span>&nbsp;&nbsp;';
			                		  } 
			                    	 if(status != 'COMPLETED'){
			                    		 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES\',\'ROLE_ASSIGN_EMPLOYEE_TO_PROJECT\')">'; 
			                    		
			                    		 if(opt.projectMemberStatus != 'INACTIVE'){
			                    			 result = result + '<a  onclick="reActiveProject(\''+opt.id+'\',\''+opt.empName+'\',\''+opt.projectAssignedDateStr+'\',\''+projectEndDateStr+'\',\''+opt.endDate+'\');flushReProject()" data-target="#Re-AssignProject" data-toggle="modal"><i class="fa fa-retweet" title="Re Assign"></i> &nbsp;</a>'; 
			                    		 }
			                    		
			                    		 result = result + '<span class="show-reason">';
			                    		if(opt.terminateReason != null && opt.terminateReason != '' && opt.terminateReason != 'undefined'){
				                    		result = result + '<b class="glyphicon glyphicon-comment text-success"></b><b class="reason edit-status">'+opt.terminateReason+'</b>';
				                    	}else{
				                    		result = result + '';	
				                    	}
				                      '</sec:authorize>';
				                      
				                      if(opt.projectMemberStatus == 'INACTIVE' && (opt.terminateReason == null || opt.terminateReason == '' || opt.terminateReason == 'undefined')){
				                    	  result = result + '<font color="red">&nbsp;Terminated</font>';
				                      }
				                      
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
		                	 result = result + '<td><div class="tblminwidth-50">';
			                	if(opt.status != null && opt.status != '' && opt.status !='undefined' && opt.status != 0){
			                		 result = result + '<font color="green">&nbsp;Active</font>';	 
			                	 }else{
			                		 result = result + '<font color="red">&nbsp;In-Active</font>';	
			                	 }
		                	 result = result +'</div></td>';
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
    	"scrollX": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $('#projectEmployeePagination').wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
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


<script>
function getRateTable(projectMemberId,candidateName){
	$("#projectMemberDetailsPopUp").empty();
	var sourceleadUrl = '${sourceleadUrl}';
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getProjectMemberRateTablesList?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+projectMemberId,
		    success: function(data) {
		    	
		    	  var result = "";
		    	  
		    	  result = result +' <div class="project_inn_mask"></div>'; 
		    	  result = result +' <div id="edit_member_pop" class="member_rate_history_pop">';
		    	  
		    	  result = result +' <div class="row">';
		    	  result = result +" <p class='col-md-10'>Emp Name : <strong>"+candidateName+"</strong></p>";
		    	  result = result + '<p class="col-md-2"><i class="pull-right fa fa-times text-danger pull-right cursorPointer"  onClick="closerateHistorydiv();"></i></p>';
		    	  result = result +' </div>';
		    	  
		    	  result = result + '<section class="tbl-scroll project_tabs_inn_table">';
  			      result = result + '  <table>';
                  result = result + '    <thead>';
                  result = result + '      <tr>';
                  result = result + '        <th>Pay Start Date</th>';
                  result = result + '        <th>Pay End Date</th>';
                  result = result + '        <th>Pay Rate</th>';
                  result = result + '        <th>Bill Start Date</th>';
                  result = result + '        <th>Bill End Date</th>';
                  result = result + '        <th>Bill Rate</th>';
                  result = result + '        <th>Created By</th>';
                  result = result + '      </tr>';
                  result = result + '    </thead>';
                  result = result + '    <tbody>';
                  
                 
                  for (var i=0; i<data.length; i++){
                	 
                	  var history = data[i];
                	  var payRate = '';
                	  var billRate = '';
                	  var billStartDate = '';
                	  var billEndDate = '';
                	  var payStartDate = '';
                	  var payEndDate = '';
                	  
                	  if(history.payRate != null){
                		  payRate = history.payRateCurrency+" "+history.payRate+"/ "+history.payRateType;
                	  }else{
                		  payRate = "------"
                	  }
                	  
                	  if(history.billRate != null){
                		  billRate = history.billRateCurrency+" "+history.billRate+"/ "+history.billRateType;
                	  }else{
                		  billRate = "------"
                	  }
                	  
                	  if(history.payRateStartDate != null){
                		  payStartDate = history.payRateStartDate;
                	  }else{
                		  payStartDate = "------"
                	  }
                	  
                	  if(history.payRateEndDate != null){
                		  payEndDate = history.payRateEndDate;
                	  }else{
                		  payEndDate = "------"
                	  }
                	  
                	  if(history.billRateStartDate != null){
                		  billStartDate = history.billRateStartDate;
                	  }else{
                		  billStartDate = "------"
                	  }
                	  
                	  if(history.billRateEndDate != null){
                		 billEndDate = history.billRateEndDate;
                	  }else{
                		  billEndDate = "------"
                	  }
                	  
                	  result = result + '<tr class="redtxt">';
                	  result = result + ' <td><div class="tblminwidth-80">'+payStartDate+'</div></td>';
                	  result = result + ' <td><div class="tblminwidth-80">'+payEndDate+'</div></td>';
                	  result = result + ' <td><div class="tblminwidth-80">'+payRate+'</div></td>';
                	  result = result + ' <td><div class="tblminwidth-80">'+billStartDate+'</div></td>';
                	  result = result + ' <td><div class="tblminwidth-80">'+billEndDate+'</div></td>';
                	  result = result + ' <td><div class="tblminwidth-80">'+billRate+'</div></td>';
                	  result = result + ' <td><div class="tblminwidth-80">'+history.createdBy+'</div></td>'; 
                	  result = result + '</tr>'; 
                  }
                	  
                  
                  result = result + '    </tbody>';
                  result = result + '  </table>';
                  result = result + '</section>'
                  
              	 $("#projectMemberRateHistoryPopUp").append(result);
                 $("#edit_member_pop").slideDown(200);
                 $(".project_inn_mask").fadeIn(200); 
		    }
	     });
}

function closerateHistorydiv(){
	 $("#projectMemberRateHistoryPopUp").empty();
	 $(".project_inn_mask").fadeOut(200);
}
</script>


<script>
function editProjectAssignedMember(projectMemberId,candidateName){
	var sourceleadUrl = '${sourceleadUrl}';
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/editProjectAssignedProjectMember?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+projectMemberId,
		    success: function(data) {
		    	loadProjectMembersDetails(data,candidateName);
		    }
	     });
}



function loadProjectMembersDetails(data,candidateName){
	  $("#projectMemberRateHistoryPopUp").empty();
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
	
	var payrate = '';
    var billrate = '';
    var projectStartDate = '';
    var projectEndDate = '';
    var estHours = '';
    var estBudget = '';
    var percentageOfWork = '';
    
    var payRateEstdDate = '';
    var billRateEstdDate = '';
    
    if(data.payRate != null && data.payRate != 'undefined'){
    	payrate = data.payRate;
    }
    
    if(data.billRate != null  && data.billRate != 'undefined'){
    	billrate = data.billRate;
    }
    
    if(data.projectStartDate != null && data.projectStartDate != '' && data.projectStartDate != 'undefined'){
    	projectStartDate = data.projectStartDate;
    }
    
    if(data.projectEndDate != null && data.projectEndDate != '' && data.projectEndDate != 'undefined'){
    	projectEndDate = data.projectEndDate;
    }
    
    if(data.estHours != null && data.estHours != '' && data.estHours != 'undefined'){
    	estHours = data.estHours;
    }
    
    if(data.estBudget != null && data.estBudget != '' && data.estBudget != 'undefined'){
    	estBudget = data.estBudget;
    }
    
    if(data.percentageOfWork != null && data.percentageOfWork != '' && data.percentageOfWork != 'undefined'){
    	percentageOfWork = data.percentageOfWork;
    }
    
    if(data.payRateEstimatedDate != null && data.payRateEstimatedDate != '' && data.payRateEstimatedDate != 'undefined'){
    	payRateEstdDate = data.payRateEstimatedDate;
    }
    
    if(data.billRateEstimatedDate != null && data.billRateEstimatedDate != '' && data.billRateEstimatedDate != 'undefined'){
    	billRateEstdDate = data.billRateEstimatedDate;
    }
	
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>"; 
	
	result = result +" <div id='edit_member_pop' class='edit_member_pop'>";
	result = result +"	<h4>Edit Team Member</h4>";
	result = result +"	<p>Emp Name : <strong>"+candidateName+"</strong></p>";
	result = result + '<div class="form-horizontal clearfix">';
	result = result + '<form id="editProjectMemberform" name="editProjectMemberform">';
	result = result +" <div id='editMemberLoading'></div>"; 
	result = result + '<input type="hidden" id="projectMemberId" name="projectMemberId" value="'+data.projectMemberId+'">';
	
	
	result = result + '<div class="clearfix mb10 row mt">';
	
	result = result + '<div class="col-md-8 paddingRight">';
	result = result + '<div class="row">';
	result = result + '<label class="clearfix col-md-12">Pay Rate<span class="text-danger">&nbsp;*</span></label>';
	
	result = result + '<div class="col-md-3 padding_right dropdown-select drpdownCurrency">';
	result = result + '<select id="payRateCurrency" name="payRateCurrency">';
	result = result + '<option value="INR">INR</option>';
	result = result + '<option value="AUD">AUD</option>';
	result = result + '<option value="USD">USD</option>';
	result = result + '<option value="GBP">GBP</option>';
	result = result + '<option value="SGD">SGD</option>';
	result = result + '<option value="EUR">EUR</option>';
	result = result + '</select>';
	result = result + '</div>';
	
	result = result + '<div class="col-md-4 padding_right padding_left inputRate">';
	result = result + '<input class="form-control" type="text" id="payRate" name="payRate" value = "'+payrate+'" field-name="Pay Rate" data-validation="required validate_Space validate_float validate_length length1-15">';
	result = result + '</div>';
	
	result = result + '<div class="col-md-5 padding_left drpdownMonthly dropdown-select">';
	result = result + '<select id="payRateRateType" name="payRateRateType">';
	result = result + '<option value="Hourly">Hourly</option>';
	result = result + '<option value="Weekly">Weekly</option>';
	result = result + '<option value="Bi-weekly">Bi-weekly</option>';
	result = result + '<option value="Semi-Monthly">Semi-Monthly</option>';
	result = result + '<option value="Monthly">Monthly</option>';
	result = result + '<option value="Bi-Monthly">Bi-Monthly</option>';
	result = result + '<option value="Yearly">Yearly</option>';
	result = result + '</select>';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '</div>';
	
	
	result = result + '<div class="col-md-4 paddingLeft" id="payRateEstdDiv">';
	result = result + '<label class="clearfix">Estimated Date<span class="text-danger">&nbsp;*</span></label>';
	result = result + '<div class="padding_right padding_left inputRate">';
	result = result + '<input type="text" class="form-control calendar"  id="payRateEstimatedDate" value="'+payRateEstdDate+'"  readonly name="payRateEstimatedDate" field-name="Estd Date"  data-validation="required">';
	result = result + '</div>';
	result = result + '</div>';
	
	result = result + '</div>';
	
	result = result + '<div class="clearfix mb10 row mt">';
	
	result = result + '<div class="col-md-8 paddingRight">';
	result = result + '<div class="row">';
	result = result + '<label class="clearfix col-md-12">Bill Rate<span class="text-danger">&nbsp;*</span></label>';
	result = result + '<div class="col-md-3 padding_right dropdown-select drpdownCurrency">';
	result = result + '<select id="billRateCurrency" name="billRateCurrency">';
	result = result + '<option value="INR">INR</option>';
	result = result + '<option value="AUD">AUD</option>';
	result = result + '<option value="USD">USD</option>';
	result = result + '<option value="GBP">GBP</option>';
	result = result + '<option value="SGD">SGD</option>';
	result = result + '<option value="EUR">EUR</option>';
	result = result + '</select>';
	result = result + '</div>';
	
	result = result + '<div class="col-md-4 padding_right padding_left inputRate">';
	result = result + '<input class="form-control" type="text" id="billRate" name="billRate" value = "'+billrate+'" field-name="Bill Rate"  data-validation="required validate_Space validate_float validate_length length1-15">';
	result = result + '</div>';
	
	result = result + '<div class="col-md-5 padding_left drpdownMonthly dropdown-select">';
	result = result + '<select id="billRateRateType" name="billRateRateType">';
	result = result + '<option value="Hourly">Hourly</option>';
	result = result + '<option value="Weekly">Weekly</option>';
	result = result + '<option value="Bi-weekly">Bi-weekly</option>';
	result = result + '<option value="Semi-Monthly">Semi-Monthly</option>';
	result = result + '<option value="Monthly">Monthly</option>';
	result = result + '<option value="Bi-Monthly">Bi-Monthly</option>';
	result = result + '<option value="Yearly">Yearly</option>';
	result = result + '</select>';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '</div>';
	
	
	result = result + '<div class="col-md-4 paddingLeft" id="billRateEstdDiv">';
	result = result + '<label class="clearfix">Estimated Date<span class="text-danger">&nbsp;*</span></label>';
	result = result + '<div class="padding_right padding_left inputRate">';
	result = result + '<input type="text" class="form-control calendar"  id="billRateEstimatedDate" value="'+billRateEstdDate+'" placeholder="" readonly name="billRateEstimatedDate" field-name="Estd Date"  data-validation="required">';
	result = result + '</div>';
	result = result + '</div>';
	
	result = result + '</div>';
	
	result = result + '	    <div class="row">';
	
	result = result + '	    <div class="col-md-6 mb10" id="startDateDiv">';
	result = result + '	      <label class="clearfix">Start Date<span class="text-danger">&nbsp;*</span></label>';
	result = result + '	      <div class="">';
	result = result + '	        <input type="text" class="form-control input-sm calendar" value="'+projectStartDate+'" readonly id="projectStartDate"  name="memberStartDate" field-name="Start Date"  data-validation="required">';
	result = result + '	      </div>';
	result = result + '	    </div>';
	
	result = result + '	    <div class="col-md-6 mb10" id="endDateDiv">';
	result = result + '	      <label class="clearfix">End Date</label>';
	result = result + '	      <div class="">';
	result = result + '	        <input type="text" class="form-control input-sm calendar"  value="'+projectEndDate+'" id="projectEndDate" readonly name="memberEndDate" field-name="End Date"  data-validation-optional="true">';
	result = result + '	      </div>';
	result = result + '	    </div>';
	
	result = result + '	    </div>';
	
	result = result + '	    <div class="row">';
	
	result = result + '	    <div class="clearfix mb10 col-md-6">';
	result = result + '	      <label class="clearfix">Estimated Hours</label>';
	result = result + '	      <div class="">';
	result = result + '	        <input type="text" class="form-control inlineField"  id="estHours" value="'+estHours+'" name="estHours" field-name="Estimated Hours"  data-validation-optional="true" data-validation="validate_Space validate_int">';
	result = result + '	      </div>';
	result = result + '	    </div>';
	
	result = result + '	    <div class="clearfix mb10 col-md-6">';
	result = result + '	      <label class="clearfix">Estimated Budget</label>';
	result = result + '	      <div class="">';
	result = result + '	        <input type="text" class="form-control inlineField"  id="estBudget" value="'+estBudget+'"  name="estBudget" field-name="Estimated Budget"  data-validation-optional="true" data-validation="validate_Space validate_int">';
	result = result + '	      </div>';
	result = result + '	    </div>';
	result = result + '	    </div>';
	
	
	
	result = result + '	    <div class="clearfix mb10 col-md-6">';
	result = result + '	      <label class="clearfix">%Of Work</label>';
	result = result + '	      <div class="">';
	result = result + '	        <input type="text" class="form-control inlineField"  id="percentageOfWork" value="'+percentageOfWork+'"  name="percentageOfWork" field-name="%Of Work"  data-validation-optional="true"  data-validation="validate_Space validate_percentage_of_work" >';
	result = result + '	      </div>';
	result = result + '	    </div>';
	result = result + '	    </div>';
	
	
	result = result + ' <div id="reasonForProjectDiv" class="footerBtns">';
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Update" onclick="updateProjectMember();"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelEditProjectMember();"></b>';
	result = result +"	</div>";
	result = result +"	</div>";
	
	result = result + '</form>';
	
	$("#projectMemberDetailsPopUp").append(result);
	$("#edit_member_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200); 
	
	
	if(data.billRateCurrency != null && data.billRateCurrency != '' && data.billRateCurrency != 'undefined'){
		$('#billRateCurrency').val(data.billRateCurrency);
	}else{
		$('#billRateCurrency').val('${SourceLeadWeb_currencyCode}');
	}
	
	if(data.billRateRateType != null && data.billRateRateType != '' && data.billRateRateType != 'undefined'){
		$('#billRateRateType').val(data.billRateRateType);
	}
	
	
	if(data.payRateCurrency != null && data.payRateCurrency != '' && data.payRateCurrency != 'undefined'){
		$('#payRateCurrency').val(data.payRateCurrency);
	}else{
		$('#payRateCurrency').val('${SourceLeadWeb_currencyCode}');
	}
	
	if(data.payRateRateType != null && data.payRateRateType != '' && data.payRateRateType != 'undefined'){
		$('#payRateRateType').val(data.payRateRateType);
	}
	
	  if(data.projectStartDate == null || data.projectStartDate == '' || data.projectStartDate == 'undefined'){	
			$("#projectStartDate").datepicker({
				dateFormat : finalDateFormatAssignEmpUpdate,
				changeMonth: true,
				changeYear: true, 
				minDate:projectStartDate,
		        onClose : function(selected) {
		        	var dt = $(this).datepicker('getDate', '+1d');
					$("#projectStartDate").datepicker("option", "minDate", dt);
					removeStartDateValidation();
					getEstimatedHours();
				}
			});
	  }
	  
	  if(data.memberEndDate != null && data.memberEndDate != "" && data.memberEndDate != "undefined"){
		  $("#projectEndDate").datepicker({
				dateFormat : finalDateFormatAssignEmpUpdate,
				changeMonth: true,
				changeYear: true, 
				maxDate:data.memberEndDate,
				minDate: projectStartDate,
		        onClose : function(selected) {
// 		        	 var dt = $(this).datepicker('getDate', '-1d');
// 						$("#projectEndDate").datepicker("option", "maxDate", dt);
						removeEndDateValidation();
						getEstimatedHours();
				}
			});
	  }else{
			  $("#projectEndDate").datepicker({
					dateFormat : finalDateFormatAssignEmpUpdate,
					changeMonth: true,
					changeYear: true, 
					minDate: projectStartDate,
			        onClose : function(selected) {
//	 		        	if(projectEndDate != null && projectEndDate != "" && projectEndDate != "undefined"){
//	 		        		var dt = $(this).datepicker('getDate', '-1d');
//	 	 						$("#projectEndDate").datepicker("option", "maxDate", dt);
//	 		        	}
						removeEndDateValidation();
						getEstimatedHours();
					}
				});
		
	  } 
		
		
		$("#billRateEstimatedDate").datepicker({
			dateFormat : finalDateFormatAssignEmpUpdate,
			changeMonth: true,
			changeYear: true, 
	        onClose : function(selected) {
	        	removebillRateEstdValidation();
			}
		});
		
		$("#payRateEstimatedDate").datepicker({
			dateFormat : finalDateFormatAssignEmpUpdate,
			changeMonth: true,
			changeYear: true, 
	        onClose : function(selected) {
	        	removePayRateEstdValidation();
			}
		});
		
		
		
	
}

function removeStartDateValidation(rowId){
	$('#startDateDiv').find('p.jquery_form_error_message').remove();
}

function removeEndDateValidation(rowId){
	$('#endDateDiv').find('p.jquery_form_error_message').remove();
}

function removebillRateEstdValidation(rowId){
	$('#billRateEstdDiv').find('p.jquery_form_error_message').remove();
}

function removePayRateEstdValidation(rowId){
	$('#payRateEstdDiv').find('p.jquery_form_error_message').remove();
}




function cancelEditProjectMember(){
	$("#projectMemberDetailsPopUp").empty();
	 $(".project_inn_mask").fadeOut(200);
	 $(".vendor_wrap_mask").fadeOut(200);
}



function getEstimatedHours(){
	var startDate = document.getElementById("projectStartDate").value;
	var endDate = document.getElementById("projectEndDate").value;
	var locationId = '${SourceLeadWeb_orgLocationId}';
	if(startDate && endDate){
		 var sourceleadUrl ='${sourceleadUrl}';
		 var averageWorkingHours = '${SourceLeadWeb_orgLocations.averageWorkingHours}';
		 $("#editMemberLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getEstimatedHoursForResourceRequest?${_csrf.parameterName}=${_csrf.token}&startDate="+startDate+"&endDate="+endDate+"&averageWorkingHours="+averageWorkingHours+"&locationId="+locationId, 
		        success: function(estimatedHours) {
		        	
		        	$('#estHours').val(estimatedHours);
		        	 $("#editMemberLoading").removeAttr("style"); 
		        	
		        }
		        
		 });
	}
}

</script>


<script>
function updateProjectMember(){
	
	 var validationSettings = {
				errorMessagePosition : 'element',
				borderColorOnError : '',
				scrollToTopOnError : true,
			}; 
		
		 if ($('#editProjectMemberform').validate(false, validationSettings)) {
				 var sourceleadUrl = '${sourceleadUrl}';
				 var formData = getFormData("editProjectMemberform");
				
				 var projectUUID = '${projectUUID}';
				 var projectClientUUID = '${projectClientUUID}';
				 
				 
				 $("#editMemberLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			 	 $.ajax({
							type : "POST",
							data :formData,
							url : sourceleadUrl+"/updateProjectMemberDetails?${_csrf.parameterName}=${_csrf.token}",
							success : function(result) {
							
								location.replace(sourceleadUrl+"/projectSummary?projectUUID="+projectUUID+"&projectClientUUID="+projectClientUUID+"&tabType=team");
							},
						}); 
				return true;
			} else {
				return false;
			}
	}
	$('body').on('blur', '#editProjectMemberform', function() {
		$("#editProjectMemberform").showHelpOnFocus();;
		$("#editProjectMemberform").validateOnBlur(false, validationSettings);  
	}); 
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
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Delete" onclick="saveReason('+projectMemberId+',\''+projectId+'\',\''+startDateStr+'\',\''+endDateStr+'\');"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTeamTermination();"></b>';
	result = result +"	</div>";
	$("#teampopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function cancelTeamTermination(){
	$("#teampopUp").empty();
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
		   		    	$("#teampopUp").empty();
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
		$("#teampopUp").append(result);
		$("#client_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
	 
}
</script>
<script type="text/javascript">
function empCancelTermination(){
	$("#teampopUp").empty();
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
		$("#teampopUp").append(result);
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
   
   result = result +'<input type="text" class="form-control calendar" readonly="readonly" placeholder="Enter StartDate" id="reStartDate" name="reStartDate" field-name="Start Date" data-validation="required">';

   result = result +'</div>';
   result = result +'</div>';
   
   result = result +'<div class="form-group">';
   result = result +'<label class="col-lg-3">End date</label>';
   result = result +'<div class="col-lg-9 positionRelative">';

   result = result +'<input type="text" class="form-control calendar" readonly="readonly" placeholder="Enter EndDate" id="reEndDate" name="reEndDate"  field-name="End Date" />';

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

 var reStatertDate = $('#reStartDate').val();
  if(projectEndDateStr != null && projectEndDateStr != "" && projectEndDateStr != 'undefined' && projectEndDateStr !="null"){
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
        		    var dt = $(this).datepicker('getDate', '+1d');
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
        	    var dt = $(this).datepicker('getDate', '-1d');
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
        	    var dt = $(this).datepicker('getDate', '+1d');
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
	            var dt = $(this).datepicker('getDate', '-1d');
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


<!-- ----------------------------Filter Script By Laxman -------------------------------------- -->
<script>
function closeFilterTeamPop(){
	$("div.cand-advsrch-popup").hide();
	$(".advancedsearch-mask").fadeOut();
}
function openFilterTeamPop(){
	 $("#teamFilterPopup").toggle();
	 $(".advancedsearch-mask").fadeIn();
}
</script>


<div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search-small" id="teamFilterPopup">
				<h5 class="headerAdsrch">Filter Search
					<a href="javascript:void(0)" class="close-div" onClick="closeFilterTaskPop()"><i
						class="fa fa-close"></i></a>
				</h5>
				<div class="addInner">
					<div class="row">
						<div class="col-md-3">
						<div class="row">
						   <div class="col-md-12 mb">
							  <label><strong>Status</strong></label>
								 <div class="clearfix">
									<ul class="list-unstyled" id="StatusFilter">
										 <li class="checkBox positionRelative"><input id="c6" type="checkbox"   name="employeeStatus" value ="Active" ><label for="c6">Active</label></li>     
										 <li class="checkBox positionRelative"><input id="c7" name="employeeStatus" type="checkbox" value ="In-Active" ><label for="c7">In-Active</label></li>
			                        </ul>
								 </div>
							 </div>
							 </div>
							 </div> 
							 
							 <div class="col-md-4 positionRelative">
								<div class="clearfix dynamic_search filterDynamicsearch">
									<input type="text" id="myRolesInput" onkeyup="myRolesFunction()" placeholder="Roles" title="Type in a roles">
									<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
								</div>
								 <div class="clearfix filterSearch-ul">
									<ul class="list-unstyled positionRelative filterSearch mt5" id="myRolesUL">
									<c:forEach var="roles" items="${rolesList}">  
										<li class="checkBox positionRelative"><input  id="roleName${report.id}" name="roleName" value ="${roles.id}"   type="checkbox"><label for="roleName${roles.id}">${roles.displayName}</label></li>
									  </c:forEach>
									</ul>
								 </div> 
							</div>
							
							<div class="col-md-4 mb positionRelative">
									<div class="clearfix dynamic_search filterDynamicsearch">
										<input type="text" id="myTeamInput" onkeyup="myTeamFunction()" placeholder="Team" title="Type in a managers">
										<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
									</div>
									 <div class="clearfix filterSearch-ul">
										<ul class="list-unstyled positionRelative filterSearch mt5" id="myTeamUL">
										<c:forEach var="team" items="${teamList}"> 
										<li class="checkBox positionRelative clearfix"><input value="${team.id}" name="teams"  type="checkbox" ><label>${team.name}</label></li>
										</c:forEach>
										</ul>
									 </div> 
							 </div>
							 
					</div>
					</div>
					<div class="clearfix advancedsearch-footer">
						<div class="pull-right mr">
							<input type="button" value="Apply" id="search_requiremet" onclick="closeFilterTeamPop();getFilterEmployeeDetails();" class="btn btn-sm btn-success" />
							<input type="button" value="Reset" id="clear_search" onclick="resetPorjectTeamFilter();" class="btn btn-sm btn-primary" />
						</div>
					</div>
	  </div>
	   
<script>
function resetPorjectTeamFilter(){
	$("ul#StatusFilter > li input:checkbox").prop("checked",false);
	$("ul#myRolesUL > li input:checkbox").prop("checked",false);
	$("ul#myTeamUL > li input:checkbox").prop("checked",false);
	
	getFilterEmployeeDetails();
}
function myRolesFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myRolesInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myRolesUL");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("label")[0];
        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";

        }
    }
}

function myTeamFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myTeamInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myTeamUL");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("label")[0];
        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";

        }
    }
}
</script>
