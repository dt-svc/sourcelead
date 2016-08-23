<link href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css" rel="stylesheet">

<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
 <link href="resources/css/jquery-ui.css" rel="stylesheet">
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>

<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> 

 <style>
 
 .clsDatePicker {
   z-index: 100000 !important;
}
.side-bar .side-nav ul li span{
  display: inline-block;
  float: none;
}
 </style>

<body>
      <!-- Content Area -->
       <section class="content-wraper">
        <section class="content-header clearfix">
        
        <c:if test="${not empty msg}">
	        <div class="success_pop" id="selector">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
          <div class="pull-left">
            <h2>Projects</h2>
          </div>
          <div class="pull-right brud-crum"><a href="dashboard">Home</a> &raquo;ProjectList</div>
        </section>

        
	   <div class="timesheet_out">
	   <c:if test="${empty projectsList}">
			<div class="project_wrap">
        <div class="timesheet_head">
        	<ul>
				<li><i class="fa fa-filter"></i><span>Client</span><i class="glyphicon glyphicon-play"  id="filter1ApprovePop"></i>|
                	<div id="filter1ApprovedPop" class="approved_pop">
                    	<ul>
                    	  <c:forEach var="report" items="${add_client}">  
                        	<li><input  id="clientName${report.id}" name="clientNameById" value ="${report.id}"  onclick="filter();"  type="checkbox"><label for="clientName${report.id}">${report.clientName}</label></li>
						  </c:forEach>
                        </ul>
                    </div>                	
                </li>
                <li><i class="fa fa-filter"></i><span>Status</span><i class="glyphicon glyphicon-play" id="filter2ApprovePop"></i>|
                	<div id="filter2ApprovedPop" class="approved_pop">
                    	<ul>
                        	<li><input id="c5" type="checkbox" name="projectStatus" value ="All" onclick="filter();"  ><label for="c5">All</label></li>
							<li><input id="c6" type="checkbox" name="projectStatus" value ="STARTED"  onclick="filter();"><label for="c6">STARTED</label></li>
							<li><input id="c7" name="projectStatus" type="checkbox" value ="INPROGRESS"  onclick="filter();"><label for="c7">INPROGRESS</label></li>
							<li><input id="c8" type="checkbox"  name="projectStatus"  type="checkbox" value ="COMPLETED"  onclick="filter();"><label for="c8">COMPLETED</label></li>
               				<li><input id="c8" type="checkbox"  name="projectStatus"  type="checkbox" value ="ONHOLD"  onclick="filter();"><label for="c8">ONHOLD</label></li>
                        </ul>
                    </div>                	
                </li>
				<li><a href="<c:url value='/project'/>"><i class="fa fa-plus"></i>Create Project</a></li>		
                <div class="clearfix"></div>
            </ul>
            <div class="clearfix"></div>
        </div>
			
		    <div id="projectFilterList"></div>
		    <div class="project_wrap_table">
		    				<table id="example" class="display" cellspacing="0" width="100%">
							</table>
			</div>	   
		</div>
		</c:if>
		<c:if test="${!empty projectsList}">
		<div class="project_wrap">
        <div class="timesheet_head">
        	<ul>
				<li><i class="fa fa-filter"></i><span>Client</span><i class="glyphicon glyphicon-play"  id="filter1ApprovePop"></i>|
                	<div id="filter1ApprovedPop" class="approved_pop">
                    	<ul>
                    	  <c:forEach var="report" items="${add_client}">  
                        	<li><input  id="clientName${report.id}" name="clientNameById" value ="${report.id}"  onclick="filter();"  type="checkbox"><label for="clientName${report.id}">${report.clientName}</label></li>
						  </c:forEach>
                        </ul>
                    </div>                	
                </li>
                <li><i class="fa fa-filter"></i><span>Status</span><i class="glyphicon glyphicon-play" id="filter2ApprovePop"></i>|
                	<div id="filter2ApprovedPop" class="approved_pop">
                    	<ul>
                        	<li><input id="c5" type="checkbox" name="projectStatus" value ="All" onclick="filter();"  ><label for="c5">All</label></li>
							<li><input id="c6" type="checkbox" name="projectStatus" value ="STARTED"  onclick="filter();"><label for="c6">STARTED</label></li>
							<li><input id="c7" name="projectStatus" type="checkbox" value ="INPROGRESS"  onclick="filter();"><label for="c7">INPROGRESS</label></li>
							<li><input id="c8" type="checkbox"  name="projectStatus"  type="checkbox" value ="COMPLETED"  onclick="filter();"><label for="c8">COMPLETED</label></li>
               				<li><input id="c8" type="checkbox"  name="projectStatus"  type="checkbox" value ="ONHOLD"  onclick="filter();"><label for="c8">ONHOLD</label></li>
                        </ul>
                    </div>                	
                </li>
				<li><a href="<c:url value='/project'/>"><i class="fa fa-plus"></i>Create Project</a></li>		
                <div class="clearfix"></div>
            </ul>
            <div class="clearfix"></div>
        </div>
			
		    <div id="projectFilterList"></div>
		    <div class="project_wrap_table">
		    				<table id="example" class="display" cellspacing="0" width="100%">
							</table>
			</div>	   
				
		</div>
		</c:if>
		<div class="side_mask"><div class="proj_close"><i></i></div></div>
		<div class="project_side_wrap">
			<div class="project_side_wrap_inn">
		    <h1 id="projectNameById"></h1> <div class="proj_close"><i></i></div>
		     <div id="projectSideDiv">
		     </div>   
			<div class="project_error">
				<p>Employee delete message here ....</p>
				<a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> 
			</div>	
		    </div>
		  </div> 
		   </div>		
	   </section>
	   <div id="popUp"></div>
	  </body>
<!--   Popup's   -->

<!-- Modal content Assign Team-->
                <div role="dialog" id="assignTeam" class="modal fade">
                  <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button data-dismiss="modal" class="close" type="button">X</button>
                        <h4 class="modal-title">Assign Employees</h4>
                      </div>
                      <div class="modal-body clearfix">
                        <div class="form-horizontal row custom-form">
                          <div class="col-xs-12 col-md-12 col-lg-12">
                            <div class="input-group">
                              <input type="search" class="form-control" value="">
                              <span class="input-group-addon bt-primary"><i class="fa fa-search"></i></span> </div>
                          </div>
                        </div>
                        <div class="modal-body-custom mt clearfix" id="employeesList">
                        </div>
                        <div class="col-md-9">
							<div class="checkbox">
								<label> <input type="checkbox" id="membermailCheckbox" value="Y"
									name="isMailToTeamMembers"> Mail to Team Members
								</label>
							</div>
						</div>
                      </div>
                      <div class="modal-footer">
                        <button data-dismiss="modal" class="btn btn-primary" type="button" onclick="assignEmployee();">Assign</button>
                        <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
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
	                            <label class="col-lg-3">Frist Name<span class="text-danger">*</span></label>
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
               <!-- Modal -->
                <div class="modal fade" id="addTask" role="dialog">
                  <div class="modal-dialog modal-lg"> 
                    
                    <!-- Modal content-->Project name here
                    <div class="modal-content">
                      <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Add Tasks</h4>
                      </div>
                      <div class="modal-body clearfix">
                      <form name="project_form" id="project_form" method="post">
                        <div class="modal-body-custom custom-table custom-table-new">
                          <table class="table table-bordered table-striped  clearfix" id="projectTaskTbl" cols="0">
                            <tr id="divTsRow0">
                              <th><input type="hidden" id="maxTsRowNum" name="maxTsRowNum" value="1"/>Task Name</th>
                              <th>Description</th>
                              <th>Action</th>
                            </tr>
                          </table>
                        </div>
                        <div class="form-horizontal row custom-form">
                          <div class="col-xs-12 col-lg-7">
                          <div class="form-group">
                            <label class="col-md-3">Task Name</label>
                            <div class="col-md-9">
                               <input type="text" id="taskName" name="taskName" placeholder="Enter Task Name" class="form-control" field-name="Task Name" data-validation="required validate_length length2-250">
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-md-3">Description</label>
                            <div class="col-md-9">
                              <textarea class="form-control" placeholder="Enter Task Description" id="taskDescription" name="taskDescription" rows="2"></textarea>
                            </div>
                          </div>
                          </div>
                          <div class="col-xs-12 col-lg-5">
                          <a href="javascript:void(0)" class="btn btn-primary" id="save_task" onclick="addTasks();" data-target="addTask"> <i class="fa fa-plus"></i> Add Task</a>
                          </div>
                        </div>
                        </form>
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="saveTasks();">Save</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- Modal contente Ends--> 
               <!--   Popup's   -->	
<jsp:include page="../masterFooter.jsp" />	
	
	
	
<script type="text/javascript">
window.onload = function () {
	$('input[name=projectStatus]').attr('checked',false);
	$('input[name=clientNameById]').attr('checked',false);
	filter();
 };
</script>



<script>
//project side bar
$(document).ready(function() {
     $(".proj_close, .side_mask").click(function(){
    	$(".project_side_wrap").removeClass("project_side_wrap_active");
 		$(".side_mask").fadeOut(200);
 		$("body").css('overflow-y', 'auto');
	});	
});
//project side bar tabs
$(document).ready(function() {
	$("#tab-1").show();
 $(".project-tabs-menu a").click(function(event) {
     event.preventDefault();
     $(this).parent().addClass("current");
     $(this).parent().siblings().removeClass("current");
     var tab = $(this).attr("href");
     $(".project_tabs_inn").not(tab).css("display", "none");
     $(tab).fadeIn();
 });
});

//project active dropdown
$(document).ready(function() {
 $("#ac-drop").click(function(){
		$(".project_details_cta ul").slideToggle(200);
	});
 $("html").click(function() {
     $(".project_details_cta ul").slideUp(200);
 });
 $(".project_details_cta ul, #ac-drop").click(function(e) {
     e.stopPropagation();
 });
});

//project error message
$(document).ready(function() {
 $(".project_tabs_inn_table i").click(function(){
		$(".project_error").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	});
 $(".project_error a").click(function() {
     $(".project_error").slideUp(200);
		$(".project_inn_mask").fadeOut(200);
 });
});
</script> 
<!-- this script is for side navigation functionality at Projects screen -->
 <script>
 function projectSideBar(projectId,projectName,startDateStr,endDateStr, status){ 
	  	    var result = "";
	  	    result = result + '<input type="hidden" name="projectId" id="projectId" value="'+projectId+'" >';
	  	    result = result + '<div class="project_side_wrap_inn_tabs">';
		  	result = result + '<ul class="project-tabs-menu">';
		  	result = result + '<li class="current"><a href="#tab-1" onClick="projectEmployee('+projectId+',\''+startDateStr+'\',\''+endDateStr+'\',\''+status+'\');" >Employees</a></li>';
		  	result = result + '<li><a href="#tab-2" onClick="projectTask('+projectId+',\''+status+'\');">Tasks</a></li>';
		  	result = result + ' <li><a href="#tab-3" onClick="projectDetails('+projectId+');" >Details</a></li>';
		  	result = result + ' <div class="clearfix"></div>';
		  	result = result + ' </ul>';
		  	 
		  	result = result + '<div class="project_tabs">';
		  	result = result + ' <div id="tab-1" class="project_tabs_inn">';
		  	result = result + '</div>';
		  	
		  	result = result + '<div id="tab-2" class="project_tabs_inn">';
		  	result = result + '</div>';
		  	
		  	result = result + '<div id="tab-3" class="project_tabs_inn">';
		  	result = result + '</div>';
		  	
		  	result = result + '</div>'; 
		  	$("#projectSideDiv").empty();  
		    $("#projectSideDiv").append(result);  
		    $("#projectNameById").empty(); 
		    $("#projectNameById").append(projectName); 
		    
		    $(".project_side_wrap").addClass("project_side_wrap_active");
		    $(".side_mask").fadeIn(200);
			$("body").css('overflow-y', 'hidden');
			
		    projectEmployee(projectId,startDateStr,endDateStr,status);
 }
 </script> 
 
 <!-- this script is for Emplyee Listfunctionality at Projects screen -->
<script>
function projectEmployee(projectId,startDateStr,endDateStr,status){
	var sourceleadUrl ='${sourceleadUrl}';
	var status  = status;
    $.ajax({
    	type: "POST",
        url: sourceleadUrl+"/getProjectEmployeeListById?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId, 
        success: function(data) {
        	
        	var json = JSON.parse(data);
        	var result = "";
        	result = result + '    	<div class="project_tabs_inn_head">';
    		if(status != 'COMPLETED'){
    		result = result + '		<a href="javascript:void(0)" data-target="#assignTeam" data-toggle="modal" onclick="getTeamMembers('+projectId+',\''+startDateStr+'\',\''+endDateStr+'\');"><i class="glyphicon glyphicon-plus"></i>Assign Emp</a>';
    		result = result + '		<a href="javascript:void(0)" data-target="#addTeam" data-toggle="modal" onclick="flushEmployee();"  ><i class="glyphicon glyphicon-plus"></i>Add Emp</a>';
        	}
    		result = result + '		</div>';
    		  result = result + '        <div class="project_tabs_inn_table">';
    		if(json.length!=null && json.length!=''){
    	    result = result + '	<table>';
    		result = result + '	<tbody>';
    		result = result + '		<tr>';
            result = result + '	 	<th>Emp name</th>';
            result = result + '	     <th>Start Date</th>';
            result = result + '	     <th>End Date</th>';
            result = result + '	     <th>Approved hrs</th>';
            result = result + '	     <th>Rejected hrs</th>';
            result = result + '	     <th>Saved hrs</th>';
            result = result + '	     <th>Wait for approval</th>';
            result = result + '	     <th>Action</th>';
            result = result + '	 </tr>';
        	for (var i=0; i<json.length; i++)
			{        
	        		var opt = json[i];
	        		var projectEndDateStr = "";
	        		if(json[i].projectEndDateStr != null && json[i].projectEndDateStr != ''){
	        			projectEndDateStr = json[i].projectEndDateStr;
	        		}else{
	        			projectEndDateStr = "--"
	        		}
	        		
	        		result = result + '	 <tr>';
	                result = result + '	 	<td><div><a href="<c:url value="/userSummary/'+opt.userId+'"/>""> '+json[i].empName+' </a></div></td>';
	                result = result + '	     <td><div>'+json[i].projectAssignedDateStr+'</div></td>';
	                if(status == 'COMPLETED'){
	                	result = result + '<td><div>'+projectEndDateStr+'</div></td>';
	                }else{
	                	if(json[i].status != null && json[i].status != '' && json[i].status !='undefined' && json[i].status != "IN-ACTIVE"){
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
	                if(json[i].status != null && json[i].status != '' && json[i].status !='undefined' && json[i].status != "IN-ACTIVE"){
	                	result = result + '	<td><div>';
	                	if(status != 'COMPLETED'){
	                		result = result + '<i class="glyphicon glyphicon-trash" onClick="deleteProject('+json[i].id+'\,\''+projectId+'\',\''+startDateStr+'\',\''+endDateStr+'\')";></i>';
	                	}
	                	result = result +'</div></td>';
                     }
                     else{
                          result = result + '<td><div><font color="red">Terminated</font></div></td>';
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
    	    $("#tab-1").append(result);  
    	    
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
		$("#reasonForProjectDiv").empty().append("<font class='jquery_form_error_message'>Reason is required</font>") ;
	}
}
</script>

<!-- this script is for Tasks list functionality at Projects screen -->
<script>
  function projectTask(projectId,status){
	  var result = ""; 
	  var sourceleadUrl ='${sourceleadUrl}';
	  var status = status;
	  $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getProjectTasks?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId, 	   
		    		   success: function(data) {
		    			    var json = JSON.parse(data);
		    			    
		    				
		    				  var result = ""; 
		    				  result = result + '<div class="project_tabs_inn_head">';
		    				  if(status != 'COMPLETED' && status !='' && status !='undefined' && status !=null){
		    					  result = result + '<a href="javascript:void(0)" data-toggle="modal" data-target="#addTask" onclick="flushTasks();"><i class="glyphicon glyphicon-plus"></i>Add Task</a>';
		    				  }
		    				  result = result + '</div><div class="project_tabs_inn_table project_tabs_inn_table_a">';
		    				  result = result + '<table>';
		    				  result = result + '<tbody>';
		    				  result = result + '<tr id="projectTaskTableId">';
		    				  result = result + '<th>Task name</th>';
		    				  result = result + '<th>Task description</th>';
		    				  result = result + '</tr>';
		    				  
			   	        	for (var i=0; i<json.length; i++)
			   				{
			   		        		var opt = json[i];
			   		        		result = result + '<tr>';        
			   		        		result = result + '<td><div><input type="text" onBlur="inlineEditForProjectsTasks('+opt.Id+',\'taskName\');"  id="taskName'+opt.Id+'" value="'+opt.taskName+'"  class="project_inline_field"></div></td>';
			   		        		result = result + '<td><div><input type="text" onBlur="inlineEditForProjectsTasks('+opt.Id+',\'description\');" id="description'+opt.Id+'" value="'+opt.description+'" class="project_inline_field"></div></td>';
			   		        		result = result + '</tr>'; 
			   		        		
			   				}
			   	        	result = result + '</tbody>';
			   	    	 result = result + '</table>   ';         
			   	    	 result = result + '</div>';
			   	    	 $("#tab-1").empty();  
			   	    	 $("#tab-3").empty();  
			   	    	 $("#tab-2").empty();  
			   	    	 $("#tab-2").append(result);
		    			   
		    		   }
	  });
	 
  }
</script> 

<!-- this script is for Project Details functionality at Projects screen -->
<script>
  function projectDetails(projectId){
	var sourceleadUrl ='${sourceleadUrl}';
    $.ajax({
   	type: "POST",
    url: sourceleadUrl+"/getProjectById?${_csrf.parameterName}=${_csrf.token}&ProjectId="+projectId, 	   
    success: function(data) {
   	var json = JSON.parse(data);
   	var endDateStr = "";
   	var description = "";
   	var clientName = "";
   	var projectSequeneNumber ="";
   	if(json.endDateStr != null && json.endDateStr != '' && json.endDateStr != 'undefined'){
   		endDateStr =  json.endDateStr;
   	}else{
   		endDateStr = "--";
   	}
   	if(json.description != null && json.description != '' && json.description != 'undefined'){
   		description  = json.description;
   	}else{
   		description = "NO DATA";
   	}
   	if(json.projectSequeneNumber != null && json.projectSequeneNumber != '' && json.projectSequeneNumber != 'undefiend'){
   		projectSequeneNumber = json.projectSequeneNumber;
   	}else{
   		projectSequeneNumber = "--";
   	}
   	if(json.client != null && json.client != '' && json.client != 'undefined'){
   		clientName = json.client
   	}else{
   		clientName = "--";
   	}
	var result = ""; 
    result = result + '      <div class="project_tabs_inn_table">';
    result = result + '			<div class="project_details">';
    result = result + '<div class="useredit useredit_a">';
	result = result + '<a id="edit-user" href="javascript:editProjectDetails('+projectId+')"><i class="glyphicon glyphicon-pencil"></i>Edit</a>';
	result = result + '</div>';
	result = result + '				<div class="project_details_cta">';
	result = result + '					<strong>Status:</strong><span>';
	result = result + json.projectStatus;
	result = result + '					</i></span>';
	result = result + '				</div></span><div class="clearfix"></div>';
	
	result = result + '				<div class="clearfix"></div>';
	result = result + '				<div class="project_details_cta" id="ProjectIdDiv">';
	result = result + '					<strong>Project Id:</strong>';
	result = result + '					<span>'+projectSequeneNumber+'</span><div class="clearfix"></div>';
	result = result + '				</div>';
	
	
	result = result + '				<div class="project_details_cta" id="projectNameId">';
	result = result + '					<strong>Project name:</strong>';
	result = result + '					<span>'+json.projectName+'</span><div class="clearfix"></div>';
	result = result + '				</div>';
	
	result = result + '<div class="project_details_cta">';
	result = result + '<strong>Manager 1:</strong><span>';
				
					for (var j = 0; j < json.managersList.length; j++) {
							  var manager = json.managersList[j];
							  if(json.manager1 == manager.id){
								  result = result+manager.firstName+" "+manager.lastName;
							  }
						}
	result = result + '			</span><div class="clearfix"></div>';
	result = result + '				</div>';
	
	if(json.manager2  != null && json.manager2  != "" && json.manager2  !="undefined"){
		result = result + '				<div class="project_details_cta">';
		result = result + '					<strong>Manager 2:</strong><span>';
		
						for (var j = 0; j < json.managersListwithoutManager1.length; j++) {
							  var manager = json.managersListwithoutManager1[j];
							  if(json.manager2 == manager.id){
								  result = result+manager.firstName+" "+manager.lastName;
							  }
						}
		result = result + '			</span><div class="clearfix"></div>';
		result = result + '				</div>';
	}
	
	
	result = result + '<div class="project_details_cta">';
	result = result + '<strong>Client:</strong>';
	result = result + '	<span>'+clientName+'</span><div class="clearfix"></div>';
	result = result + '<div class="clearfix"></div>';
	result = result + '</div>';
	result = result + '				<div class="project_details_cta">';
	result = result + '					<strong>Team members:</strong><span>'+json.projectMembersCount+'</span><div class="clearfix"></div>';
	result = result + '				</div>';
	result = result + '				<div class="clearfix"></div>';
	result = result + '				<div class="project_details_cta">';
	result = result + '					<strong>Start date:</strong>';
	result = result + '					<span>'+json.startDateStr+'</span><div class="clearfix"></div>';
	result = result + '				</div>';
	result = result + '				<div class="project_details_cta">';
	result = result + '					<strong>End date:</strong>';
	result = result + '					<span> '+endDateStr+'</span><div class="clearfix"></div>';
	result = result + '				</div>';
	result = result + '				<div class="clearfix"></div>';
	
	result = result + '				<div class="project_details_cta">';
	result = result + '					<strong>Created By:</strong><span>'+json.createdBy+'</span><div class="clearfix"></div>';
	result = result + '				</div>';
	result = result + '				<div class="project_details_cta">';
	result = result + '					<strong>Created date:</strong><span> '+json.createdDateStr+'</span><div class="clearfix"></div>';
                                                     
	result = result + '				</div>';
	if(json.updatedDateStr != null && json.updatedDateStr != '' && json.updatedDateStr != 'undefiend'){
	result = result + '				<div class="project_details_cta">';
	result = result + '					<strong>Last Updated:</strong><span>'+json.updatedDateStr+'</span><div class="clearfix"></div>';
	result = result + '				</div>';
	}
	result = result + '				<div class="clearfix"></div>';
	result = result + '				<p><strong>Description:</strong></p>'+description+'<div class="clearfix"></div>';
	result = result + ' <div class="user_summary_edit user_summary_edit_a" id="editProjectDetailsDiv"></div>';
	result = result + '			</div>';
	result = result + '      </div>';
	result = result + '  </div>   ';     
	result = result + '</div>'; 
		
	$("#tab-1").empty();  
	$("#tab-2").empty();  
	$("#tab-3").empty();  
	$("#tab-3").append(result);
		 
  }
   });
  }
</script>   


<script>
$(document).ready(function() {
    $("#filter1ApprovePop").click(function(){
		$("#filter1ApprovedPop").slideToggle(200);
	});
    $("html").click(function() {
        $("#filter1ApprovedPop").slideUp(200);
    });
    $("#filter1ApprovedPop, #filter1ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
    
    
    $("#filter2ApprovePop").click(function(){
		$("#filter2ApprovedPop").slideToggle(200);
	});
    $("html").click(function() {
        $("#filter2ApprovedPop").slideUp(200);
    });
    $("#filter2ApprovedPop, #filter2ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
    
  
    
}); 

</script>

<!-- this script is for Projects Filter functionality at Projects screen -->

<script>
function pagination(){  
    $('#example').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	 "ordering": false
		
    } );
}
</script>

<script>
function filter(){
	
	var sourceleadUrl ='${sourceleadUrl}';
	var values = [];
	$('input:checkbox[name=projectStatus]:checked').each(function() 
    {
			values.push($(this).val());
    });
	
	var clients = [];
	$('input:checkbox[name=clientNameById]:checked').each(function() 
		    {
		clients.push($(this).val());
		    });
	
	
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getProjectsByStatusAndClient?${_csrf.parameterName}=${_csrf.token}&values="+values+"&clients="+clients, 	   
   		    success: function(data) {
   		    	var json = JSON.parse(data);
   	        	var result = "";
   		    	result = result +'<thead>';
   		    	result = result +'	<tr>';
   		    	result = result +'	<th>Id</th>';
   		    	result = result +'    <th>Name</th>'; 
   		    	result = result +'     <th>Manager</th>';
   		    	result = result +'     <th>Date</th>';
   		    	result = result +'      <th>Client</th>';
   		    	result = result +'       <th>Status</th>';
   		    	result = result +'    </tr>';
   		    	result = result +'</thead>	';
   		    	result = result +'<tbody>';
   		    	for (var i=0; i<json.length; i++)
   				{   
   		    		var report = json[i];
   		    		var projectSequeneNumber;
   		    		if(report.projectSequeneNumber != null && report.projectSequeneNumber != '' && report.projectSequeneNumber !='undefined'){
   		    				projectSequeneNumber = report.projectSequeneNumber;
   		    		}else{
   		    			projectSequeneNumber = "---";
   		    		}
   		    		result = result +'<tr>';
   		    		result = result +'<td data-title="Id"><div><span>'+projectSequeneNumber+'</span></div></td>';
   		    		result = result +'<td data-title="Name"><div class="proj_show" onclick="projectSideBar('+report.id+',\''+report.projectName+'\',\''+report.employeeStartDateStr+'\',\''+report.employeeEndDateStr+'\',\''+report.projectStatus+'\');"><span>'+report.projectName+'</span><i class="fa fa-binoculars"></i></div></td>';
   		    		result = result +'<td data-title="Manager">';
   		    		result = result +'<div><span>';
   		    		if(report.managerLevel1 !=null && report.managerLevel1 !='' && report.managerLevel1 !='undefined'){
   		    			result = result +report.managerLevel1;
   		    		}
   		    		if(report.managerLevel2!=null && report.managerLevel2!='' && report.managerLevel2 !='undefined'){
   		    			result = result + " , ";
   		    			result = result +report.managerLevel2;
   		    		} 
   		    		result = result +'</span></div>';
   		    		result = result +'</td>';
   		    		result = result +'<td data-title="Date"><div>'+report.startDateStr+' ';
   		    		if(report.endDateStr != null && report.endDateStr != '' && report.endDateStr != 'undefined'){
   	   		    		result = result +'- '+report.endDateStr+' </div></td>';
   		    		}
   		    		if(report.client != null && report.client != '' && report.client != 'undefined'){
   		    			result = result +'<td data-title="Client"><div><span>'+report.client+'</span></div></td>';  
   		    		}else{
   		    			result = result +'<td data-title="Client"><div>---</div></td>';  
   		    		}
   		    		result = result +'<td data-title="Status"><div>';
   		    		if(report.projectStatus == 'STARTED'){
	    			  result = result +'<b>'+report.projectStatus+'</b>';
	    			}
   		    		if(report.projectStatus == 'INPROGRESS'){
  	    			  result = result +'<b class="proj_process">'+report.projectStatus+'</b>';
  	    			}
   		    		if(report.projectStatus == 'COMPLETED'){
  	    			  result = result +'<b class="proj_active">'+report.projectStatus+'</b>';
  	    			}
   		    		if(report.projectStatus == 'ONHOLD'){
  	    			  result = result +'<b class="proj_deactivate">'+report.projectStatus+'</b>';
  	    			}
   		    		
   		    		result = result +'</div></td>';
   		    		
                    result = result +'</tr>';
   				}
                result = result +'  </tbody>';
                result = result +' </table>';
                result = result +'</div>';
                $("#example").empty();  
   	    	    $("#example").append(result);
   	    	    pagination();	
   		    }
	 }); 
	
	
}	
</script> 

<!-- this script is for Project Details Inline Edit functionality at Projects screen -->
<script> 
function  inlineEditForProjects(projectId,localFieldName){
	var sourceleadUrl ='${sourceleadUrl}';
	var fieldName = localFieldName;
	var fieldValue = $("#"+localFieldName).val();
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/updateProjectById?${_csrf.parameterName}=${_csrf.token}&ProjectId="+projectId+"&fieldName="+fieldName+"&fieldValue="+fieldValue, 	   
   		    success: function(data) {
   			   
   		    }
	 }); 
}
</script> 
<script type="text/javascript">
function flushTasks() {
	var maxRowNum = parseInt($("input[name = 'maxTsRowNum']").val());
	if(maxRowNum != null && maxRowNum != '' && maxRowNum != 'undefined' && maxRowNum != 'NaN'){
		$("#maxTsRowNum").val("1");
         for(var i = 1; i <= maxRowNum ; i++){
        	    document.getElementById("projectTaskTbl").deleteRow(1);
		 } 
	}
}
</script>
  
<!-- =============================== AddTask Divs  ============================ -->
       <div id="divTaskTd1" style="display:none; float:left;">
            <input type="hidden" id="REPLACEROWIDtaskName" name="REPLACEROWIDtaskName" value="TASKNAMEVALUE"/><span id="REPLACEROWIDtaskNameValue">TASKNAMEVALUE</span> 
      </div>
       <div id="divTaskTd2" style="display:none; float:left;">
        <input type="hidden" id="REPLACEROWIDtaskDescription" name="REPLACEROWIDtaskDescription" value="TASKDESCRIPTIONVALUE"/><span id="REPLACEROWIDtaskDescriptionValue">TASKDESCRIPTIONVALUE</span> 
      </div>
       <div id="divTaskTd3" style="display:none; float:left;">
            <span><a title="Edit" href="javascript:void(0)" onclick="editTask(REPLACEROWID)"><i class="fa fa-edit"></i></a></span>&nbsp;&nbsp;&nbsp;
            <span><a title="Remove" href="javascript:void(0)" onclick="removeRow(REPLACEROWID)"><i class="fa fa-close"></i></a></span>
      </div>
  <!-- =============================== AddTask Divs  ============================ -->     
<!-- Add Task Script start -->
<script type="text/javascript">
function addTasks(){
	
	if ($('#project_form').validate(false, validationSettings)){ 
    	var tbl = document.getElementById("projectTaskTbl");
    	var maxRowNum = parseInt($("input[name = 'maxTsRowNum']").val());
    	$("input[name = 'maxTsRowNum']").val(maxRowNum + 1);
    	$("#addTaskCount").html(maxRowNum);
    	var row = tbl.insertRow(maxRowNum);
    	row.setAttribute("id", "divTsRow" + maxRowNum);
	
		var taskName = $('#taskName').val();
	    var taskDescription = $('#taskDescription').val();
			
		var cell0 = row.insertCell(0);
		var divTsTd1Str = document.getElementById("divTaskTd1").innerHTML;
		divTsTd1Str = divTsTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
		divTsTd1Str = divTsTd1Str.replace(/TASKNAMEVALUE/g, (taskName));
		cell0.innerHTML = divTsTd1Str;
		
		
		var cell1 = row.insertCell(1);
		var divTsTd2Str = document.getElementById("divTaskTd2").innerHTML;
		divTsTd2Str = divTsTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
		divTsTd2Str = divTsTd2Str.replace(/TASKDESCRIPTIONVALUE/g, (taskDescription));
		cell1.innerHTML = divTsTd2Str;
		
		
		var cell2 = row.insertCell(2);
		var divTsTd3Str = document.getElementById("divTaskTd3").innerHTML;
		divTsTd3Str = divTsTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell2.innerHTML = divTsTd3Str;
		
		$("#taskName").val('');
		$("#taskDescription").val('');
		 return true;
	 	}else{
			return false;
	 }
}

$('body').on('blur', '#project_form', function() {
	$("#project_form").showHelpOnFocus();
	$("#project_form").validateOnBlur(false, validationSettings);  
});
</script>
<script type="text/javascript">
 function editTask(rowId) {
	 var updateTaskName = $("#"+rowId+"taskName").val(); 
	 var updateTaskDescription = $("#"+rowId+"taskDescription").val(); 
	 $("#taskName").val('');
	 $("#taskDescription").val('');
	 $("#addTaskText").html('Update Task');
	 $("#taskName").val(updateTaskName);
	 $("#taskDescription").val(updateTaskDescription);
	 $("#taskId").val(rowId);
	 $("#save_task").removeAttr('onclick');
	 $("#save_task").attr('onclick','updateTask('+rowId+');');
  }
 
</script>
<script type="text/javascript">
	function removeRow(rowId) {
		document.getElementById('divTsRow' + rowId).style.display = 'none';
		var r = document.getElementById('projectTaskTbl').rows[rowId];
		var count = 2 * (r.cells.length);
		for (i = 0; i <= count; i++) {
			r.deleteCell(0);
		}
	}
</script>
<script type="text/javascript">
 function updateTask(rowId) {
	 $("#"+rowId+"taskName").val($("#taskName").val()); 
	 $("#"+rowId+"taskDescription").val($("#taskDescription").val());
	 $("#"+rowId+"taskNameValue").html($("#taskName").val()); 
	 $("#"+rowId+"taskDescriptionValue").html($("#taskDescription").val());
	 $("#taskName").val('');
	 $("#taskDescription").val('');
	 $("#addTaskText").html('Add Task');
	 $("#save_task").removeAttr('onclick');
	 $("#save_task").attr('onclick','addTasks();');
	 
 }
 </script>
<script> 
$(document).ready(function(){
	 $("#maxTsRowNum").val('1');
	document.getElementById("project_form").reset();
});
</script>
<!-- Add Task Script Ends -->

<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 
 
 <!-- this script is for Save Tasks functionality at Projects screen -->
 <script type="text/javascript">
 function saveTasks() {
	var sourceleadUrl ='${sourceleadUrl}';
    var projectId = $("#projectId").val();
    var frm = $('#project_form').serializeFormJSON();
    var con = JSON.stringify(frm);
    con = con.replace(/[{}]/g, "");
  //It will escape all the special characters
    var jsonData = encodeURIComponent(con);
  
    $.ajax({
    	type: "POST",
        url: sourceleadUrl+"/saveProjectTasks?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&projectId="+projectId, 
        success: function(data) {
        	var json = JSON.parse(data);
        	 var result = ""; 
       	  result = result + '<div class="project_tabs_inn_head">';
       	  result = result + '<a href="javascript:void(0)" data-toggle="modal" data-target="#addTask" onclick="flushTasks();"><i class="glyphicon glyphicon-plus"></i>Add Task</a></div>';
       	  result = result + '<div class="project_tabs_inn_table project_tabs_inn_table_a">';
       	  result = result + '<table>';
       	  result = result + '<tbody>';
       	  result = result + '<tr id="projectTaskTableId">';
       	  result = result + '<th>Task name</th>';
       	  result = result + '<th>Task description</th>';
       	  result = result + '</tr>';
        	for (var i=0; i<json.length; i++)
			{
	        		var opt = json[i];
	        		result = result + '<tr>';
	         		result = result + '<td><div><input type="text" value="'+opt.taskName+'" class="project_inline_field"></div></td>';
	         		result = result + '<td><div><input type="text" value="'+opt.description+'" class="project_inline_field"></div></td>';
	         		result = result + '</tr>'; 
			}
	        	result = result + '</tbody>';
	       	 result = result + '</table>   ';         
	       	 result = result + '</div>';
	       	 $("#tab-2").empty();  
	       	 $("#tab-2").append(result);
        },
    });
    
    
 }
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
function  inlineEditForProjectsTasks(taskId,localFieldName){
	var fieldName = localFieldName;
	var fieldValue = $("#"+localFieldName+""+taskId).val();
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/updateProjectTaskById?${_csrf.parameterName}=${_csrf.token}&taskId="+taskId+"&fieldName="+fieldName+"&fieldValue="+fieldValue, 	   
   		    success: function(data) {
   			   
   		    }
	 }); 
}
</script> 

<!-- this script is for TeamMembers List functionality at Projects screen -->
<script type="text/javascript">
function getTeamMembers(projectId,startDateStr,endDateStr) {
	var sourceleadUrl ='${sourceleadUrl}';
	var startDateStr = startDateStr;
	var endDateStr = endDateStr;
    $.ajax({
    	type: "POST",
        url: sourceleadUrl+"/getTeamMembersList?${_csrf.parameterName}=${_csrf.token}&ProjectId="+projectId, 
        success: function(data) {
        	var json = JSON.parse(data);
        	var output = "";
        	output = output+' <table cols="0" class="table table-bordered table-striped  clearfix">';
            output = output+' <tbody>';
            output = output+' <tr>';
            output = output+' <th>&nbsp;&nbsp;</th>';
            output = output+' <th>Name</th>';
            output = output+' <th>Email</th>';
            output = output+' <th>Role</th>';
            output = output+' <th>Start Date</th>';
            output = output+' <th>End Date</th>';
            output = output+' </tr>';
        	for (var i=0; i<json.length; i++)
			{
     	       // var projectEndDate = $('#EndDate').val();
	        		var opt = json[i];
	        		var role = '';
	        		if(opt.user.userType != null && opt.user.userType != '' && opt.user.userType != 'undefined'){
	        			role = opt.user.userType; 
	        		}else{
	        			role = 'Employee';
	        		}
	        		output = output + "<tr>";
	        		output = output + "<td><input class='myCheckbox' id="+opt.id+" type='checkbox' name='team"+i+"' value="+opt.id+"></td>";
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
        	$("#employeesList").empty();
	        $("#employeesList").append(output);
	        
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
</script>
	$('#employeeEmail').val('');
}
</script>

<!-- this script is for Assign Employee functionality at Projects screen -->
<script>
function assignEmployee(){
	 	var projectId = $("#projectId").val();
	 	var startDateStr =  $("#startDateStr").val();
	 	var endDateStr =  $("#endDateStr").val();
	 	
	    var result = "";
	    var mailtoMembers = "NO";
	    if(document.getElementById('membermailCheckbox').checked) {
	    	mailtoMembers = "YES";
	    }
	    var sourceleadUrl ='${sourceleadUrl}';
	    $(".myCheckbox").each(function(){
	        var employeeId =this.id;
	        if($(this).is(':checked')){
		        var startDate = $("#startDate"+employeeId).val();
		        var endDate = $("#endDate"+employeeId).val();
				$.ajax({
		        	type: "POST",
		            url: sourceleadUrl+"/storeAssignEmployee?${_csrf.parameterName}=${_csrf.token}&ProjectId="+projectId+"&employeeId="+employeeId+"&startDate="+startDate+"&endDate="+endDate+"&mailtoMembers="+mailtoMembers, 
		            success: function(data) {
		            	projectEmployee(projectId,startDateStr,endDateStr,status);
		            }
				})
	        }
	    });
}
</script>

<!-- this script is for Email verification functionality at Projects screen -->
<script type="text/javascript">
 function emailVerification() {
	 var EmailId = document.getElementById("EmailId").value; 
	 var sourceleadUrl ='${sourceleadUrl}';
	 $('#EmailIdDiv').find('p.jquery_form_error_message').remove();
	 if(EmailId != null && EmailId != ""){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getEmailVerification?${_csrf.parameterName}=${_csrf.token}&EmailId="+EmailId, 
		        success: function(data) {
		        	if(data == 'true'){
						  $("input#EmailId").after("<p class='jquery_form_error_message'>"+EmailId+" is Already Existed</span>");
						  document.getElementById("EmailId").setAttribute('record-exist','yes');
						  document.getElementById("EmailId").setAttribute('record-exist-errorMsg',EmailId+' is Already Existed'); 
		        	}
		        	else{
		        		 $('input#EmailId').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#EmailIdDiv').find('p.jquery_form_error_message').remove(); 
		        	}
		        },
		    });
	 }
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


<script type="text/javascript">
function getLevel2Approvers(manager2){
	var currentApproverId = document.getElementById("level1Manager").value;
	getChildLevel1Approvers(currentApproverId,manager2);
} 
</script>
<script type="text/javascript">
function getChildLevel1Approvers(currentApproverId,manager2){
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(currentApproverId == manager2){
	 if(currentApproverId != null && currentApproverId != ''  && currentApproverId != 'undefined'){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getApproversByUserId?${_csrf.parameterName}=${_csrf.token}&currentApproverId="+currentApproverId,
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	output = output + "<option value=''>-Select Manager-</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"</option>";
					}
		        	$("#level2Manager").empty();
			        $("#level2Manager").append(output);
		        },
		        error: function(e){
		            
		        }
		    });
	 }
	 }else{
		 getApproversLevel2(currentApproverId,manager2);
	 }
} 
</script>

<script type="text/javascript">
function getApproversLevel2(currentApproverId,manager2){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var currentApproverId = currentApproverId;
	 var manager2 =manager2;
	 $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getApproversByUserId?${_csrf.parameterName}=${_csrf.token}&currentApproverId="+currentApproverId,
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	var output = "";
	        	output = output + "<option value=''>-Select Manager-</option>"; 
	        	for (var i=0; i<json.length; i++)
				{

	        		var opt = json[i];
	        		 if(manager2 == opt.id){
					   selected = 'selected'; 
		        		output = output + "<option "+selected+" value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"</option>";
				  }else{
		        		output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"</option>";
   		 	      } 
				
				}
	        	$("#level2Manager").empty();
		        $("#level2Manager").append(output);
	        },
	    });
	
} 
</script>



<script>
  $('#selector').delay(2000).fadeOut('slow');
</script> 

 <script type="text/javascript">
        function projectNameValidate(projectId,localFieldName) {
	           
	            var ProjectName = document.getElementById("projectName").value;
	            $('#projectNameId').find('p.jquery_form_error_message').remove();
	      		if ($('#projectName').val() == '') {
	      			$("input#projectName").after("<p class='jquery_form_error_message'>Project Name is required</span>");
	      			return true;
	      		} else{
	      			if(ProjectName.trim().match(/^\S*$/) == ''){
	      				$("input#projectName").after("<p class='jquery_form_error_message'>Must not start with spaces</span>");
		      			return true;
	               }else{
	            	   inlineEditForProjects(projectId,localFieldName);
	               }
               	}
	          }
        
    </script>
    <script type="text/javascript">
        function projectIdValidate(projectId,localFieldName) {
	            var ProjectSequeneNumber = document.getElementById("projectSequeneNumber").value;
	            var sourceleadUrl = '${sourceleadUrl}';
	            $('#ProjectIdDiv').find('p.jquery_form_error_message').remove();
	            if(ProjectSequeneNumber.trim().match(/^\S*$/) == ''){
	            	if(ProjectSequeneNumber != ""){
	            		$("input#ProjectSequeneNumber").after("<p class='jquery_form_error_message'>Must not start with spaces</span>");
	            	}
	            }else{
	            	if (ProjectSequeneNumber != null && ProjectSequeneNumber != "") {	
	            		$.ajax({
								type : "POST",
								url : sourceleadUrl+ "/validateProjectSequeneNumberById?${_csrf.parameterName}=${_csrf.token}&ProjectSequeneNumber="+ ProjectSequeneNumber,
								success : function(data) {
									if (data == true) {
										$("input#projectSequeneNumber").after("<p class='jquery_form_error_message'>"+ ProjectSequeneNumber+ " is Already Existed</span>");
										document.getElementById("projectSequeneNumber").setAttribute('record-exist', 'yes');
										document.getElementById("projectSequeneNumber").setAttribute('record-exist-errorMsg',ProjectSequeneNumber+ ' is Already Existed');
										$('input#projectSequeneNumber').removeAttr( "record-exist record-exist-errorMsg");
									} else {
										$('input#projectSequeneNumber').removeAttr("record-exist record-exist-errormsg");
										$('#ProjectIdDiv').find('p.jquery_form_error_message').remove();
										inlineEditForProjects(projectId,localFieldName);
									}
								},
							});
	            	}
	            	
	            	
	            }
	          }
        
    </script>
    
    <script type="text/javascript">
    function editProjectDetails(projectId) {
    	
    	var sourceleadUrl ='${sourceleadUrl}';
        $.ajax({
       	type: "POST",
        url: sourceleadUrl+"/getProjectById?${_csrf.parameterName}=${_csrf.token}&ProjectId="+projectId, 	   
        success: function(data) {
       	var json = JSON.parse(data);
       	
       	var endDateStr = "";
       	var description = "";
       	var projectSequeneNumber ="";
       	if(json.endDateStr != null && json.endDateStr != '' && json.endDateStr != 'undefined'){
       		endDateStr =  json.endDateStr;
       	}else{
       		endDateStr = "";
       	}
       	if(json.description != null && json.description != '' && json.description != 'undefined'){
       		description  = json.description;
       	}else{
       		description = "";
       	}
       	if(json.projectSequeneNumber != null && json.projectSequeneNumber != '' && json.projectSequeneNumber != 'undefiend'){
       		projectSequeneNumber = json.projectSequeneNumber;
       	}else{
       		projectSequeneNumber = "";
       	}
       	
       	var result = "";
       	result = result + ' <form id="editProject_form"  name="editProject_form" method="post">';
       	result = result + '<div class="user_summary_edit_cta" id="ProjectIdDiv">';
		result = result + '	<label>Project Id</label>';
        result = result + '	<input type="text"  id="ProjectSequeneNumber" name="ProjectSequeneNumber" value="'+projectSequeneNumber+'" placeholder="ProjectSequene Number" data-validation="validate_Space validate_length length2-50" data-validation-optional="true" onKeyUp="validateProjectSequeneNumber();" class="project_detfield">';
		result = result + '</div>	';
		
       	result = result + '<div class="user_summary_edit_cta">';
		result = result + '	<label>Status</label>';
		result = result +'<select id="projectStatusId" name="projectStatusId">';
				if(json.projectStatus == 'STARTED'){
					result = result+'<option selected>Started</option>';
				}else{
					result = result+'<option>Started</option>';
				}
				
				if(json.projectStatus == 'INPROGRESS'){
					result = result+'<option selected>In-Progress</option>';
				}else{
					result = result+'<option>In-Progress</option>';
				}
				
				if(json.projectStatus == 'COMPLETED'){
					result = result+'<option selected>Completed</option>';
				}else{
					result = result+'<option>Completed</option>';
				}
			
				if(json.projectStatus == 'ONHOLD'){
					result = result+'<option selected>On-Hold</option>';
				}else{
					result = result+'<option>On-Hold</option>';
				}
		
		result = result + '</select>';
		result = result + '</div>';
		result = result + '<div class="user_summary_edit_cta">';
		result = result + '<label>Project name<span	class="text-danger">*</span></label>';
		result = result + '		<span><input type="text" id="projectName" name="projectName" value="'+json.projectName+'" placeholder="Project name" class="project_detfield" data-validation="required validate_Space validate_length length2-100" field-name="Project Name" ></span><span id=fname_msg></span><div class="clearfix"></div>';
		result = result + '</div>';
		result = result + '<div class="user_summary_edit_cta">';
		result = result + '	<label>Manager 1</label>';
		result = result + '<select onchange="getLevel2Approvers('+json.manager2+');" id="level1Manager" name="level1Manager" >';
		for (var j = 0; j < json.managersList.length; j++) {
				  var manager = json.managersList[j];
				  if(json.manager1 == manager.id){
					   selected = 'selected';
					   result = result+"<option value="+manager.id+" "+selected+" >"+manager.firstName+" "+manager.lastName+"</option>";
				  }else{
						  result = result+"<option value="+manager.id+" >"+manager.firstName+" "+manager.lastName+"</option>";
   		 	    	 }
			}
        result = result + '</select>';
		result = result + '</div>';
		
		if(json.manager2  != null && json.manager2  != "" && json.manager2  !="undefined"){
			
		result = result + '<div class="user_summary_edit_cta">';
		result = result + '<label> Manager 2: </label>';
		result = result + '<select id="level2Manager" name="level2Manager">';
		for (var j = 0; j < json.managersListwithoutManager1.length; j++) {
			  var manager = json.managersListwithoutManager1[j];
			  if(json.manager2 == manager.id){
				   selected = 'selected';
				   result = result+"<option value="+manager.id+" "+selected+" >"+manager.firstName+" "+manager.lastName+"</option>";
			  }else{
				  
		 	    		result = result+"<option value="+manager.id+" >"+manager.firstName+" "+manager.lastName+"</option>";
		 	    	 }
		}
       result = result + '</select>';
	   result = result + '</div>';
		
        }
		
		
		result = result + '<div class="user_summary_edit_cta">';
		result = result + '	<label>Client</label>';
		
		 if(json.relationId != null){
			result = result + '<select id="clientId" disabled="disabled" name="clientId">';
			 }else{
				 result = result + '<select id="clientId" name="clientId">';
			 }
			 result = result+"<option value=''>Select</option>";
						for (var i = 0; i < json.clientName.length; i++) {
							var managers = json.clientName[i];
							var projrctmanager = managers.split('&');
					    	var name = projrctmanager[0];
					    	var id = projrctmanager[1];
					    	 var selected = '';
		   		 	    	 if(json.relationId==id){
		   		 	    		 selected = 'selected';
		   		 	    		result = result+"<option value="+id+" "+selected+" >"+name+"</option>";
		   		 	    	 }else{
		   		 	    		result = result+"<option value="+id+" >"+name+"</option>";
		   		 	    	 }
		   		 	    	 
		   		 	    	 
			}
		result = result + '</select>';
		result = result + '</div>';                                 
		
		result = result + '<div class="user_summary_edit_cta">';
		result = result + '<label>Team members</label>';
		result = result + '<input type="text" value='+json.projectMembersCount+' disabled="disabled">';
		result = result + '</div>';
		
		result = result + '<div class="user_summary_edit_cta">';
		result = result + '	<label>Start date</label>';
        result = result + '	<input class="calendar" value='+json.startDateStr+' type="text" disabled="disabled">';
		result = result + '</div>	';
		
		result = result + '<div class="user_summary_edit_cta">';
		result = result + '<label>End date</label>';
		result = result + '	<input class="calendar" type="text" placeholder="DD/MM/YY" value="'+endDateStr+'" id="endDateStr1" name="endDateStr1" >';
		result = result + '</div>';
		
		result = result + '<div class="user_summary_edit_cta">';
		result = result + '<label>Description </label>';
		result = result + '<textarea class="edit_desc" placeholder="Enter Description" id="description" name="description">'+description+'</textarea>';
		result = result + '</div>       ';                 
		result = result + '<div class="clearfix"></div>';
		result = result + '<div class="user_summary_edit_cta">';
		result = result + '	<input class="us-btn" value="Save" onclick = "updateProjectDetails('+projectId+');"type="button"> <input id="btnClose" class="us-btn us-btn_a" value="Cancel" onClick="editProjectDetails('+projectId+');" type="button">';
		result = result + '</div>		';
		result = result + '<div class="clearfix"></div>';
		result = result + ' </form>';
		
		
		$(".user_summary_edit").slideToggle(100);
		$("#editProjectDetailsDiv").empty();
        $("#editProjectDetailsDiv").append(result);
        
        
        
      //Assign date of project member
   	 var selected = json.startDateStr;
   	 var dt = new Date(selected);
        dt.setDate(dt.getDate());

   	 //Main project end date
        var projectEndDate = json.endDateStr;
       	 $('#endDateStr1').datepicker({
   			  dateFormat: 'yy-mm-dd',
   			  minDate: dt,
   		      onClose: function() {
   	          }
   		  });
   		 
   		 
       
          },
        });       
	        
    }
    </script>
       
       
 <script type="text/javascript">
	function updateProjectDetails(projectId) {
         if ($('#editProject_form').validate(false, validationSettings)){
    		 var frm = $('#editProject_form').serializeFormJSON();
             var con = JSON.stringify(frm);
             con = con.replace(/[{}]/g, "");
             //It will escape all the special characters
             var jsonData = encodeURIComponent(con);
             var sourceleadUrl ='${sourceleadUrl}';
	 	 $.ajax({
			   	type: "POST",
			    url: sourceleadUrl+"/updateProjectByProjectId?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&ProjectId="+projectId, 	   
	   		    success: function(data) {
	   		    	projectDetails(projectId);
	   		    }
		 }); 
	     	return true;
         }else{
             return false;
           }
		
    }
	$('body').on('blur', '#editProject_form', function() {
        $("#editProject_form").showHelpOnFocus();;
        $("#editProject_form").validateOnBlur(false, validationSettings);  
    });
</script>
       
       
       
<script type="text/javascript">
	function validateProjectSequeneNumber() {
		var ProjectSequeneNumber = document.getElementById("ProjectSequeneNumber").value;
		 //space value  consider this condition 
		if(ProjectSequeneNumber.match(/^[^\s-](.*)*$/) != null){
		var sourceleadUrl = '${sourceleadUrl}';
		$('#ProjectIdDiv').find('p.jquery_form_error_message').remove();
		if (ProjectSequeneNumber != null && ProjectSequeneNumber != "") {
			$.ajax({
						type : "POST",
						url : sourceleadUrl+ "/validateProjectSequeneNumberById?${_csrf.parameterName}=${_csrf.token}&ProjectSequeneNumber="+ ProjectSequeneNumber,
						success : function(data) {
							if (data == true) {
								$("input#ProjectSequeneNumber").after("<p class='jquery_form_error_message'>"+ ProjectSequeneNumber+ " is Already Existed</span>");
								document.getElementById("ProjectSequeneNumber").setAttribute('record-exist', 'yes');
								document.getElementById("ProjectSequeneNumber").setAttribute('record-exist-errorMsg',ProjectSequeneNumber+ ' is Already Existed');
							} else {
								$('input#ProjectSequeneNumber').removeAttr("record-exist record-exist-errormsg");
								$('#ProjectIdDiv').find('p.jquery_form_error_message').remove();
							}
						},
					});
	     	}
		}
	}
</script>

 <script>
// Edit pop
$(document).ready(function() {	
	
    $("#edit-user, #btnClose").click(function() {
        $(".user_summary_edit").slideUp(100);
    });
		
});
</script> 
    
</html>