<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
<link href="https://fonts.googleapis.com/css?family=Roboto:400,300italic,300,400italic,500,700,900" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/register-style.css" rel="stylesheet">
 <link href="resources/css/jquery-ui.css" rel="stylesheet">
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/register-style.css" rel="stylesheet">
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
 <style>
 
 .clsDatePicker {
   z-index: 100000 !important;
}
 </style>

<body>
      <!-- Content Area -->
       <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Projects</h2>
          </div>
           <div class="pull-right">
             <a href="<c:url value='/project'/>">Create New Project &raquo;</a>
          </div>
        </section>

        
		<!-- Project -->
<!-- <div class="project_side_wrap">
	<div class="project_side_wrap_inn">
    <h1>Project name here</h1> <div class="proj_close"><i class="glyphicon glyphicon-share-alt"></i></div>
     <div id="projectSideDiv">
     </div>   --> 
  <!-- <div class="project_side_wrap_inn_tabs">
    <ul class="project-tabs-menu">
        <li class="current"><a href="#tab-1">Employees</a></li>
        <li><a href="#tab-2">Tasks</a></li>
        <li><a href="#tab-3">Details</a></li>
        <div class="clearfix"></div>
    </ul>
    
    <div class="project_tabs">
        <div id="tab-1" class="project_tabs_inn">
        	<div class="project_tabs_inn_head">
			<a href="javascript:void(0)" data-target="#assignTeam" data-toggle="modal"><i class="glyphicon glyphicon-plus"></i>Assign Emp</a>
			<a href="javascript:void(0)" data-target="#addTeam" data-toggle="modal"><i class="glyphicon glyphicon-plus"></i>Add Emp</a>
	   <!-- Project -->
	   <div class="timesheet_out">
	   <c:if test="${empty projectsList}">
			<div class="row">
				<div class="col-lg-12">
					<div class="content-area clearfix">
						<div class="blank-pages">
						        <p><i class="fa fa-5x fa-bar-chart"></i></p>
						        <p>No Projects in our organisation... Add Projects</p>
						 </div>
					</div>
				</div>
			</div> 
		</c:if>
		<c:if test="${!empty projectsList}">
		<div class="project_wrap">
			<!-- <div class="project_wrap_head clearfix">
			<div class="pull-right mt mb mr">
			<input type="text" class="project_field" /><i class="glyphicon glyphicon-search"></i>
			</div>
			</div> -->
			
		    <div class="project_wrap_table">
		    	<table>
		        	<tbody>
					<thead>
		            	<tr>
		                	<th>Id</th>
		                    <th>Name</th>
		                    <th>Manager</th>
		                    <th>Date</th>
		                    <th>Client</th>
		                    <th>Status</th>
		                </tr>
					</thead>	
						<c:forEach var="report" items="${projectsList}" varStatus="raId">    
	                        <tr>
	                            <td data-title="Id"><div>${report.id}</div></td>
	                            <td data-title="Name"><div class="proj_show" onclick="projectSideBar('${report.id}');">${report.projectName}<i class="fa fa-binoculars"></i></div></td>
	                            <td data-title="Manager">
	                                <div>
	                                    <c:forEach var="approver" items="${report.projectApprovers}" varStatus="raId">
	                                      <c:if test="${raId.index >0}">,</c:if>
	                                        ${approver.approverUserId.firstName} ${approver.approverUserId.lastName}
	                                    </c:forEach>
	                            
	                               </div>
	                            </td>
	                            <td data-title="Date"><div>  &nbsp;<fmt:formatDate type="date"  value="${report.startDate}" pattern="dd-MM-yyyy"/>   &nbsp; <c:if test="${!empty report.endDate}">  -  &nbsp;<fmt:formatDate type="date" value="${report.endDate}" pattern="dd-MM-yyyy"/></c:if></div></td>
	                             <c:if test="${report.client != null}">
	                                 <td data-title="Client"><div>${report.client}</div></td>   
	                              </c:if>
	                              <c:if test="${empty report.client}">
	                                 <td data-title="Client"><div>---</div></td>   
	                              </c:if>
	                            <td data-title="Status"><div><b class="proj_active">${report.projectStatus}</b></div></td>
	                        </tr>
	                    </c:forEach> 
		           </tbody>
		        </table>
			</div>
		</div>
		</c:if>
		<div class="project_side_wrap">
			<div class="project_side_wrap_inn">
		    <h1>Project name here</h1> <div class="proj_close"><i class="glyphicon glyphicon-share-alt"></i></div>
		     <div id="projectSideDiv">
		     </div>   
			<div class="project_inn_mask"></div>
			<div class="project_error">
				<p>User delete message here ....</p>
				<a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> 
			</div>	
		    </div>
		   </div> 
		   </div>		
	   </section>
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
                    <div class="modal-content">
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
	                              <input type="text" class="form-control" value="" name="employeeFirstName" id="employeeFirstName" field-name="Frist Name" data-validation="required">
	                              </div>
	                            </div>
	                            <div class="form-group">
	                            <label class="col-lg-3">Last Name</label>
	                            <div class="col-lg-9">
	                              <input type="text" class="form-control" value="" name="employeeLastName" id="employeeLastName">
	                              </div>
	                            </div>
	                            <div class="form-group" id="EmailIdDiv">
	                            <label class="col-lg-3">Email <span class="text-danger">*</span></label>
	                            <div class="col-lg-9">
	                              <input type="text" class="form-control" value="" name="EmailId" id="EmailId" field-name="Email" data-validation="required validate_email" onblur="emailVerification();">
	                              </div>
	                            </div>
	                            <div class="form-group">
	                            <label class="col-lg-3">Invoice Frequency<span class="text-danger">*</span></label>
	                            <div class="col-lg-9">
	                              <select class="form-control" name="invoiceFrequency" field-name="Invoice Frequency" data-validation="required">
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
	                                <input type="checkbox" value="" checked>
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
                    
                    <!-- Modal content-->
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
                               <input type="text" id="taskName" name="taskName" placeholder="Enter Task Name" class="form-control" field-name="Task Name" data-validation="required">
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
	
<script>
//project side bar
$(document).ready(function() {
     $(".proj_close").click(function(){
		$(".project_side_wrap").removeClass("project_side_wrap_active");
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
 <script>
 function projectSideBar(projectId){
	  	    var result = "";
	  	    result = result + '<input type="hidden" name="projectId" id="projectId" value="'+projectId+'" >';
	  	    result = result + '<div class="project_side_wrap_inn_tabs">';
		  	result = result + '<ul class="project-tabs-menu">';
		  	result = result + '<li class="current"><a href="#tab-1" onClick="projectEmployee('+projectId+');" >Employees</a></li>';
		  	result = result + '<li><a href="#tab-2" onClick="projectTask('+projectId+');">Tasks</a></li>';
		  	result = result + ' <li><a href="#tab-3" onClick="pojectDetails('+projectId+');" >Details</a></li>';
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
		    $(".project_side_wrap").addClass("project_side_wrap_active");
			
		    projectEmployee(projectId);
 }
 </script> 
<script>
function projectEmployee(projectId){
	var sourceleadUrl ='${sourceleadUrl}';
    $.ajax({
    	type: "POST",
        url: sourceleadUrl+"/getProjectEmployeeListById?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId, 
        success: function(data) {
        	
        	var json = JSON.parse(data);
        	var result = "";
        	result = result + '    	<div class="project_tabs_inn_head">';
    		result = result + '		<a href="javascript:void(0)" data-target="#assignTeam" data-toggle="modal" onclick="getTeamMembers('+projectId+');"><i class="glyphicon glyphicon-plus"></i>Assign Emp</a>';
    		result = result + '		<a href="javascript:void(0)" data-target="#addTeam" data-toggle="modal"><i class="glyphicon glyphicon-plus"></i>Add Emp</a>';
    		result = result + '		</div>';
    	    result = result + '        <div class="project_tabs_inn_table">';
    		result = result + '	<table>';
    		result = result + '	<tbody>';
    		result = result + '		<tr>';
            result = result + '	 	<th>Emp name</th>';
            result = result + '	     <th>Start Date</th>';
            result = result + '	     <th>End Date</th>';
            result = result + '	     <th>Approved hrs</th>';
            result = result + '	     <th>Rejected hrs</th>';
            result = result + '	     <th>Open hrs</th>';
            result = result + '	     <th>Wait for approval</th>';
            result = result + '	     <th>Edit</th>';
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
	                result = result + '	 	<td><div><input type="text"  value="'+json[i].empName+'" class="project_inline_field"></div></td>';
	                result = result + '	     <td><div><input type="text" value="'+json[i].projectAssignedDateStr+'" class="project_inline_field"></div></td>';
	                result = result + '	     <td><div><input type="text" value="'+projectEndDateStr+'" class="project_inline_field"></div></td>';
	                result = result + '	     <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>';
	                result = result + '	     <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>';
	                result = result + '	     <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>';
	                result = result + '	     <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>';
	                result = result + '	     <td><div><i class="glyphicon glyphicon-trash"></i></div></td>';
	                result = result + '	 </tr>';
			}
        	result = result + '	     </tbody>';
    	    result = result + '	 </table>   ';         
    	    result = result + '	     </div>';
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
        },
    });
    }	
</script>

<script>
  function projectTask(projectId){
	  var result = ""; 
	  var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getProjectTasks?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId, 	   
		    		   success: function(data) {
		    			    var json = JSON.parse(data);
		    			    
		    				
		    				  var result = ""; 
		    				  result = result + '<div class="project_tabs_inn_head">';
		    				  result = result + '<a href="javascript:void(0)" data-toggle="modal" data-target="#addTask"><i class="glyphicon glyphicon-plus"></i>Add Task</a></div>';
		    				  result = result + '<div class="project_tabs_inn_table">';
		    				  result = result + '<table>';
		    				  result = result + '<tbody>';
		    				  result = result + '<tr id="projectTaskTableId">';
		    				  result = result + '<th>Task name</th>';
		    				  result = result + '<th>Task description</th>';
		    				  result = result + '<th>Edit</th>';
		    				  result = result + '</tr>';
		    				  
			   	        	for (var i=0; i<json.length; i++)
			   				{
			   		        		var opt = json[i];
			   		        		result = result + '<tr>';        
			   		        		result = result + '<td><div><input type="text" onBlur="inlineEditForProjectsTasks('+opt.Id+',\'taskName\');"  id="taskName'+opt.Id+'" value="'+opt.taskName+'"  class="project_inline_field"></div></td>';
			   		        		result = result + '<td><div><input type="text" onBlur="inlineEditForProjectsTasks('+opt.Id+',\'description\');" id="description'+opt.Id+'" value="'+opt.description+'" class="project_inline_field"></div></td>';
			   		        		result = result + '<td><div><i class="glyphicon glyphicon-trash"></i></div></td>';
			   		        		result = result + '</tr>'; 
			   		        		
			   				}
			   	        	result = result + '</tbody>';
			   	    	 result = result + '</table>   ';         
			   	    	 result = result + '</div>';
			   	    	 $("#tab-2").empty();  
			   	    	 $("#tab-2").append(result);
		    			   
		    		   }
	  });
	 
  }
</script> 

<script>
  function pojectDetails(projectId){  
	var sourceleadUrl ='${sourceleadUrl}';
    $.ajax({
   	type: "POST",
    url: sourceleadUrl+"/getProjectById?${_csrf.parameterName}=${_csrf.token}&ProjectId="+projectId, 	   
    success: function(data) {
    	var json = JSON.parse(data);
	var result = ""; 
    result = result + '      <div class="project_tabs_inn_table">';
	result = result + '			<div class="project_details">';
	result = result + '				<div class="project_details_cta">';
	result = result + '					<strong>Status:</strong><span><b>'+json.projectStatus+'</b><i id="ac-drop" class="fa fa-edit">';
	result = result + '						<ul>';
	result = result + '							<li>Option 1</li>';
	result = result + '							<li>Option 2</li>';
	result = result + '							<li>Option 3</li>';
	result = result + '							<li>Option 4</li>';
	result = result + '						</ul>';
	result = result + '					</i></span>';
	result = result + '				</div></span><div class="clearfix"></div>';
	result = result + '				<div class="project_details_cta">';
	result = result + '					<strong>Project name:</strong>';
	result = result + '					<span><input type="text" onBlur="inlineEditForProjects('+projectId+',\'projectName\');"  id="projectName" value="'+json.projectName+'" placeholder="Project name" class="project_detfield"></span><div class="clearfix"></div>';
	result = result + '				</div>';
	result = result + '				<div class="project_details_cta">';
	result = result + '					<strong>Project manager:</strong><span>';
					result = result + '<select>';
						for (var i = 0; i < json.projectManagerName.length; i++) {
							
							var managers = json.projectManagerName[i];
							var projrctmanager = managers.split('&');
					    	var name = projrctmanager[0];
					    	var id = projrctmanager[1];
							result = result+"<option value="+id+">"+name+"</option>";
						}
                result = result + '</select>';
	result = result + '			</span><div class="clearfix"></div>';
	result = result + '				</div>';
	result = result + '				<div class="project_details_cta">';
	result = result + '					<strong>Client:</strong>';
		result = result + '<select>';
					for (var i = 0; i < json.clientName.length; i++) {
						
						var managers = json.clientName[i];
						var projrctmanager = managers.split('&');
				    	var name = projrctmanager[0];
				    	var id = projrctmanager[1];
						result = result+"<option value="+id+">"+name+"</option>";
					}
		result = result + '</select>';
	
	result = result + '			  <div class="clearfix"></div>';
	result = result + '				</div>';
	if(json.projectMembersCount != null && json.projectMembersCount != '' && json.projectMembersCount != 'undefined' && json.projectMembersCount != 0){
	result = result + '				<div class="project_details_cta">';
	result = result + '					<strong>Team members:</strong><span>'+json.projectMembersCount+'</span><div class="clearfix"></div>';
	result = result + '				</div>';
	}
	result = result + '				<div class="clearfix"></div>';
	result = result + '				<div class="project_details_cta">';
	result = result + '					<strong>Start date:</strong>';
	result = result + '					<span><input type="text" value="'+json.startDateStr+'" placeholder="YYYY/MM/DD" class="project_detfield"></span><div class="clearfix"></div>';
	result = result + '				</div>';
	if(json.endDateStr != null && json.endDateStr != '' && json.endDateStr !=  'undefiend'){
	result = result + '				<div class="project_details_cta">';
	result = result + '					<strong>End date:</strong>';
	result = result + '					<span><input type="text" placeholder="DD/MM/YY" value="'+json.endDateStr+'" class="project_detfield"></span><div class="clearfix"></div>';
	result = result + '				</div>';
	}
	result = result + '				<div class="clearfix"></div>';
	result = result + '				<p>Description<i class="fa fa-edit"></i> :</p>';
	result = result + '				<textarea placeholder="Description" onBlur="inlineEditForProjects('+projectId+',\'description\');"  id="description">';
	result = result + json.description;
	result = result + '				</textarea>';
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
	result = result + '			</div>';
	result = result + '      </div>';
	result = result + '  </div>   ';     
	result = result + '</div>'; 
		
	$("#tab-3").empty();  
	$("#tab-3").append(result);
	
  }
   });
  }
</script>     

<script> 
function  inlineEditForProjects(projectId,localFieldName){
	
	var sourceleadUrl ='${sourceleadUrl}';
	var fieldName = localFieldName;
	alert("fieldName"+fieldName);
	var fieldValue = $("#"+localFieldName).val();
	alert("fieldValue"+fieldValue);
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/updateProjectById?${_csrf.parameterName}=${_csrf.token}&ProjectId="+projectId+"&fieldName="+fieldName+"&fieldValue="+fieldValue, 	   
   		    success: function(data) {
   			   
   		    }
	 }); 
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
      </div>
  <!-- =============================== AddTask Divs  ============================ -->     
<!-- Add Task Script start -->
<script type="text/javascript">
function addTasks(){
	var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
   }; 
	
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
 function saveTasks() {
	var sourceleadUrl ='${sourceleadUrl}';
    var projectId = $("#projectId").val();
    var frm = $('#project_form').serializeFormJSON();
    var con = JSON.stringify(frm);
    con = con.replace(/[{}]/g, "");
    $.ajax({
    	type: "POST",
        url: sourceleadUrl+"/saveProjectTasks?${_csrf.parameterName}=${_csrf.token}&json="+con+"&projectId="+projectId, 
        success: function(data) {
        	var json = JSON.parse(data);
        	 var result = ""; 
       	  result = result + '<div class="project_tabs_inn_head">';
       	  result = result + '<a href="javascript:void(0)" data-toggle="modal" data-target="#addTask"><i class="glyphicon glyphicon-plus"></i>Add Task</a></div>';
       	  result = result + '<div class="project_tabs_inn_table">';
       	  result = result + '<table>';
       	  result = result + '<tbody>';
       	  result = result + '<tr id="projectTaskTableId">';
       	  result = result + '<th>Task name</th>';
       	  result = result + '<th>Task description</th>';
       	  result = result + '<th>Edit</th>';
       	  result = result + '</tr>';
        	for (var i=0; i<json.length; i++)
			{
	        		var opt = json[i];
	        		result = result + '<tr>';
	         		result = result + '<td><div><input type="text" value="'+opt.taskName+'" class="project_inline_field"></div></td>';
	         		result = result + '<td><div><input type="text" value="'+opt.description+'" class="project_inline_field"></div></td>';
	         		result = result + '<td><div><i class="glyphicon glyphicon-trash"></i></div></td>';
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

<script type="text/javascript">
	function addEmployee() {
		 var validationSettings = {
			        errorMessagePosition : 'element',
			       borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd' 
		   }; 
		 
		 if ($('#employee_form').validate(false, validationSettings)){
			var sourceleadUrl ='${sourceleadUrl}';
			var frm = $('#employee_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			$.ajax({
			type: "POST",
			url: sourceleadUrl+"/addEmployeeForProjectDashboard?${_csrf.parameterName}=${_csrf.token}&json="+con,
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

<script type="text/javascript">
function getTeamMembers(projectId) {
	var sourceleadUrl ='${sourceleadUrl}';
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
	        		output = output + "<tr>"+
                 "<td><input class='myCheckbox' id="+opt.id+" type='checkbox' name='team"+i+"' value="+opt.id+"></td>"+
                 "<td>"+opt.user.firstName+"&nbsp;"+opt.user.lastName+"</td>"+
                 "<td>"+opt.user.email+"</td>"+
                 "<td>"+role+"</td>"+
                 "<td><div class='input-group'> <span class='input-group-addon'><i class='fa fa-calendar'></i></span>"+
	  
                 "<input  id='startDate"+opt.id+"' onclick='getStartDatePickerById(\"startDate"+opt.id+"\","+opt.id+")'  type='text' name='Date' value='' class='form-control clsDatePicker'>"+
                 "</div></td>"+
                 "<td><div class='input-group'> <span class='input-group-addon'><i class='fa fa-calendar'></i></span>"+
                 "<input id='endDate"+opt.id+"' onclick='getEndDatePickerById(\"endDate"+opt.id+"\","+opt.id+")' type='text' name='Date' value='' class='form-control clsDatePicker'>"+
                 "</div></td>"+
                 "</tr>";
			}
        	output = output+' </tbody>';
	        output = output+' </table>';
        	$("#employeesList").empty();
	        $("#employeesList").append(output);
        },
        error: function(e){
        }
    });
    $('#add_employee').attr('data-dismiss','modal');
	$('#employeeFirstName').val('');
	$('#employeeLastName').val('');
	$('#employeeEmail').val('');
}
</script>

<script>
function assignEmployee(){
	 	var projectId = $("#projectId").val();
	    var result = "";
	    var sourceleadUrl ='${sourceleadUrl}';
	    $(".myCheckbox").each(function(){
	        var employeeId =this.id;
	        if($(this).is(':checked')){
		        var startDate = $("#startDate"+employeeId).val();
		        var endDate = $("#endDate"+employeeId).val();
				$.ajax({
		        	type: "POST",
		            url: sourceleadUrl+"/storeAssignEmployee?${_csrf.parameterName}=${_csrf.token}&ProjectId="+projectId+"&employeeId="+employeeId+"&startDate="+startDate+"&endDate="+endDate, 
		            success: function(data) {
		            	projectEmployee(projectId);
		            }
				})
	        }
	    });
}
</script>
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
 <script>
 function getStartDatePickerById(id,incrementor) {
	 $('#'+id).datepicker({
	      dateFormat: 'yy-mm-dd',
	      onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() + 1);
	        }
	  });
  } 
</script>
<script>
 function getEndDatePickerById(id,incrementor) {
	 $('#'+id).datepicker({
	      dateFormat: 'yy-mm-dd',
	      onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() + 1);
	        }
	  });
  } 
</script>
</html>