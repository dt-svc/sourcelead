<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="resources/js/editor.js"></script>
<link href="resources/css/editor.css" type="text/css" rel="stylesheet"/>

<script>
//description script
	$(document).ready(function(){ 
		var projectId = "${projectId}";
		tasksAssignList(projectId);
		getSubTaskList();
	});
 </script>
 
	<section class="content-wraper clearfix">
	
	<section class="content-header clearfix">
		
		<div class="pull-left">
			<h2 class="pull-left paddingRight">Project Task Summary</h2>
		</div>
		<div class="pull-right brud-crum">
			  <div class="pull-right brud-crum"><a href="<c:url value='/projectDashboard'/>">Projects List</a> &raquo;<a href="<c:url value='/projectSummary?projectUUID=${projectUUID}&tabType=tasks'/>">Project Task</a>&raquo;Project Task Summary</div>
		</div>
		
	</section>
	<div class="taskview_wrapinn">
	      
	<div class="clearfix">
          <div class="col-md-9">
	          
	          <div class="bg-success taskheaader-inlineedit mb10 clearfix">
	          
	          <div class="col-md-11 paddingRight paddingLeft edit-field edit-inputfield">
		          <div class="showTaskname" id="taskedit-parent" onclick="editFun();">
		          <span class="taskgeneratedid text-muted paddingRight bg-green" title="Task Generated ID">${projectTask.generatedId}</span>
		          <span class="paddingLeft">${projectTask.taskName}</span>
		          <span class="editicon">
		          	<i class="fa fa-pencil fa-2x"></i>
		          	</span>
		          	</div>
		          	<div class="editTaskname" style="display:none;">
		          	<input type="text" id="taskName" name="taskName" value="${projectTask.taskName}"  class="form-control formcontrol-edit"/>
		          	<input readonly type="hidden" id="hiddenTaskName" name="hiddenTaskName" value="${projectTask.taskName}" />
		          	<p class="inputError" style="display:none;">The values should be between 2-250 characters</p>
		          	</div>
	          </div>
	          <div class="col-md-1 paddingLeft text-center" id="saveIcon" style="display:none;">
	          	<i class="fa fa-save fa-2x" onclick="inlineditTaskName();"></i>
	          </div>
	          
	          </div>
          
              <div class="col-md-12 alert-custom" data-toggle="collapse" href="#collapse2" onclick="description(${projectTask.id});">
                <div class="alert alert-info row">  
              <div class="col-md-3 mt5">
                  <label class="paddingRight">Created On:</label>
                  <span>${createdDate}</span>
              </div>
              <div class="col-md-5 mt5">
                  <label class="paddingRight pull-left">Created By:</label>
                  <span class="tbldata-dotdot pull-left" title="${createdBy}">${createdBy}</span>
              </div>
              <div class="col-md-3 mt5">
                  <label class="paddingRight">Due On:</label>
                  <span>${dueDate}</span>
              </div>
                  <div class="col-md-1 text-center">
                  <span class="fa fa-caret-down btn btn-sm btn-primary"></span>
                  </div>
                  </div>
              </div>
              <div id="collapse2" class="row panel-collapse collapse">
                  <div class="col-md-12 clearfix">
                      <strong>Description</strong>
                  </div>
                  <div id="descrptionid"></div>
                 
              </div>
              <div class="clearfix"></div>
					              <!-- Tabs -->
	       <div class="clearfix">
	         <div class="project_side_wrap_inn_tabs">
	          <ul class="project-tabs-menu">
<!-- 	            <li class="current"><a href="#tab-task1">Time Entry</a></li> -->
	            <c:if test="${projectTask.taskType.id != 1059}">
	            <li class="current"><a href="#tab-task2" onclick="getSubTaskList();">Sub Tasks</a></li>
	            </c:if>
	            <li><a href="#tab-task3" onclick="closeTimeEntry();documentTableList();">Documents</a></li>
	            <li><a href="#tab-task4" onclick="getTaskActivities();">Activities</a></li>
	             <li><a href="#tab-task5" onclick="commentTableList();">Comments</a></li>
	            <div class="clearfix"></div>
	          </ul>
					          
			<div class="project_tabs"> 
			   <div id="tab-task1" class="project_tabs_inn" style="display: none;">
					<div class="clearfix mt mb">
	                    <div class="clearfix btnTimeEntry">
	                    <button type="button" class="btn btn-primary btn-sm pull-right" onclick="addTimeEntry()">
	                         Time Entry
	                    </button>
	                    </div>
	                    <div class="clearfix">
	                        <div class="col-md-3 paddingRight">
	                            <input type="text" class="form-control input-sm" placeholder="Estimated Hours">
	                        </div>
	                        <div class="col-md-2 paddingLeft">
	                            <button type="button" class="btn btn-primary btn-sm">
	                                <i class="fa fa-pencil paddingRight"></i>Edit
	                            </button>
	                        </div>
	                        <div class="col-md-3 paddingRight">
	                            <input type="text" class="form-control input-sm" placeholder="Actual Hours">
	                        </div>
	
	                    </div>
					                </div>
					<!-- TimeEntry Modal Pop -->
					<div class="modal-Time clearfix">
					    <!-- Modal content-->
					      <div class="clearfix">
					        <button type="button" class="close" onclick="closeTimeEntry()">&times;</button>
					          <p><strong>Time Entry</strong></p>
					      </div>
					        <div class="row clearfix">
					            <div class="col-md-12 mb10">
					                <textarea class="form-control" rows="1" placeholder="Description"></textarea>
					            </div>
					          <div class="col-md-4">
					            <div class="row">
					              <div class="col-md-12">
					                <input type="text" class="form-control input-sm calendar" placeholder="Date">
					              </div>
					            </div>
					          </div>
					          <div class="col-md-4">
					            <div class="row">
					              <div class="col-md-12">
					                <input type="text" class="form-control input-sm timeicon" placeholder="Time">
					              </div>
					            </div>
					          </div>
					          <div class="col-md-4">
					            <div class="row">
					              <div class="col-md-12 dropdown-select">
					                <select class="form-control">
					                <option selected="">---SubTask--</option>
					                <option>SubTask-1</option>
					                <option>SubTask-2</option>
					              </select>
					            </div>
					                </div>
					          </div>
					        </div>
					      <div class="modalFooter mt5">
					      <div class="pull-right">
					        <button type="button" class="btn btn-primary btn-sm" data-dismiss="modal">Save</button>
					        <button type="button" class="btn btn-default btn-sm" data-dismiss="modal" onclick="closeTimeEntry()">Cancel</button>
					       </div>
					      </div>
					</div>
					                <!-- TimeEntry Modal Pop Ends -->
					 <div class="clearfix project_wrap_table">
			                    <table>
			                        <thead>
			                            <tr>
			                                <th>Date</th>
			                                <th>SubTask</th>
			                                <th>Description</th>
			                                <th>Hours</th>
			                            </tr>
			                        </thead>
			                        <tbody>
			                            <tr>
			                                <td><div><span>23/04/2017</span></div></td>
			                                <td><div class="tbldata-dotdot-vsm">New Task</div></td>
			                                <td>
			                                    <div class="tbldata-dotdot-me">
			                                    Lorem Ipsum is simply dummy text of the printing and typesetting industry.
			                                    </div></td>
			                                <td><div><span>100 Hours</span></div></td>
			                            </tr>
			                        </tbody>
			                        <tfoot style="border-top: 1px solid #909090;">
			                        <tr>
			                            <td colspan="3">Total Hours</td>
			                            <td>100 Hours</td>
			                            </tr>
			                        </tfoot>
					          </table>
					        </div>    
					   </div>
					    
					<div id="tab-task2" class="project_tabs_inn" style="display: none;">
						 <div class="clearfix mt mb">
						  <div class="clearfix">
						    <button type="button" class="btn btn-primary btn-sm pull-right mr" onclick="addSubTasks();showtasktype();"> Add Task </button>
						  </div>
						</div>

						<div class="clearfix project_wrap_table filter_position datatable_filter">
						  <div id="subTaskListdivId"></div>
						</div>
		            </div>
	            
				   <div id="tab-task3" class="project_tabs_inn" style="display: none;">
					  <div class="clearfix mt mb mr">
						  <div class="clearfix">
						    <button type="button" class="btn btn-primary btn-sm pull-right" onclick="addDocumentEntryPopUp();">Add Documents</button>
						  </div>
						</div>
						
						<!-- Document Modal Pop -->
						<div class="modal-Time document clearfix"> 
						  <div id="addDocumentId"></div>
						  <div id="editDocumentId"></div>
						</div>
						
						<!-- document table list div -->
						<div id="documentTableId"></div>
					  </div>
				  
					<div id="tab-task4" class="project_tabs_inn">
						<!-- TimeEntry Modal Pop Ends -->
						<div class="clearfix project_wrap_table taskComments projecttask-div">
						<div id="taskActivityDivId"></div>
						</div>
					 </div>
					 
					 <div id="tab-task5" class="project_tabs_inn">
						<div class="clearfix mt mb">
						  <div class="clearfix mr">
						    <button type="button" class="btn btn-primary btn-sm pull-right" onclick="addCommentsPopUp()">Add Comment</button>
						  </div>
						</div>
						<!-- TimeEntry Modal Pop -->
						<div class="modal-Time commentPopUp clearfix"> 
						<div id="commnetPopUpDivId"></div>						  
						</div>
						
						<div class="clearfix project_wrap_table taskComments projecttask-div">
						<div id="taskCommentsListDivId"></div>
						</div>
						
					 </div>
			  </div>
            </div>
           </div>
			</div>
			
		          <div class="col-md-3">
			           <div class="row dropdown-select mb">
			              <label class="col-md-12">Task Type</label>
			              <div class="col-md-12 dropdown-select">
			              <input type="text" value="${projectTask.taskTypes}" disabled="disabled" class="form-control input-sm">
			                <%-- <select class="form-control" onchange="inlineEditTaskType(${projectTask.id});" id="taskTypeId" disabled="disabled">
			                   <c:forEach var="type" items="${taskTypeList}">         
			                    <option value="${type.id}" ${type.id  == projectTask.taskTypes.id ? 'selected' : ''} >${type.name}</option>                                                                                                     
								</c:forEach> 
			                </select> --%>
			              </div>
			              </div>
		             
		              <div class="clearfix  mb select-loader">
		                  <select class="pull-left" onchange="inlineEditTaskStatus(${projectTask.id});" id="taskStatusId" name="taskstype" field-name="Task Type"  data-validation="required">    
							<c:forEach var="status" items="${taskStatusList}">    
							 <option value="${status.id}" ${status.id  == projectTask.taskStatus.id ? 'selected' : ''} >${status.name}</option>                                                                                                 
							</c:forEach>                                                                                                                                    
						 </select>    
						 <span class="pull-right">
						 	<small class="status" id="statusLabel" style="display:block;">Status</small>
						 	<i class="fa fa-spinner fa-spin faSpin " id="statusSpin" style="display:none;"></i>
						 </span>                                                                                                                                
		              </div>
		              
		               
		              <div class="clearfix  mb select-loader">
		                <select class="pull-left" onchange="inlineEditTaskPriority(${projectTask.id});" id="taskpriorityId">
		                  <option selected value="">--Select Priority--</option>
		                  <c:forEach var="priority" items="${taskPriorityList}">  
		                      <option value="${priority.id}" ${priority.id  == projectTask.priority.id ? 'selected' : ''} >${priority.name}</option>                                                                                                      
							</c:forEach>
		                </select>
		                <span class="pull-right">
						 	<small class="status" id="priorityLabel" style="display:block;">Priority</small>
						 	<i class="fa fa-spinner fa-spin faSpin " id="prioritySpin" style="display:none;"></i>
						 </span>  
		            </div>
		              
		              
		              <div class="clearfix  mb select-loader">
		                <select class="pull-left" onchange="inlineEditTaskSeverity(${projectTask.id});" id="taskseverityId">
		                  <option selected value="">--Select Severity--</option>
		                   <c:forEach var="severity" items="${taskSeverityList}">     
		                      <option value="${severity.id}" ${severity.id  == projectTask.severity.id ? 'selected' : ''} >${severity.name}</option>                                                                                                
							</c:forEach>
		                </select>
		                <span class="pull-right">
						 	<small class="status" id="severityLabel" style="display:block;">Severity</small>
						 	<i class="fa fa-spinner fa-spin faSpin " id="severitySpin" style="display:none;"></i>
						 </span> 
		            </div>
		            
		            <div class="clearfix  mb select-loader">
		                <select class="pull-left" onchange="inlineEditTaskPercentage(${projectTask.id});" id="taskPercentageId">
		                      <option value="0" ${projectTask.percentage  == 0 ? 'selected' : ''} >0 Percentage</option>  
		                      <option value="10" ${projectTask.percentage  == 10 ? 'selected' : ''} >10 Percentage</option>  
		                      <option value="20" ${projectTask.percentage  == 20 ? 'selected' : ''} >20 Percentage</option>  
		                      <option value="30" ${projectTask.percentage  == 30 ? 'selected' : ''} >30 Percentage</option>  
		                      <option value="40" ${projectTask.percentage  == 40 ? 'selected' : ''} >40 Percentage</option>  
		                      <option value="50" ${projectTask.percentage  == 50 ? 'selected' : ''} >50 Percentage</option>  
		                      <option value="60" ${projectTask.percentage  == 60 ? 'selected' : ''} >60 Percentage</option>  
		                      <option value="70" ${projectTask.percentage  == 70 ? 'selected' : ''} >70 Percentage</option>  
		                      <option value="80" ${projectTask.percentage  == 80 ? 'selected' : ''} >80 Percentage</option>  
		                      <option value="90" ${projectTask.percentage  == 90 ? 'selected' : ''} >90 Percentage</option>  
		                      <option value="100" ${projectTask.percentage  == 100 ? 'selected' : ''} >100 Percentage</option>  
		                </select>
		                <span class="pull-right">
						 	<small class="status" id="percentageLabel" style="display:block;">Work Completed</small>
						 	<i class="fa fa-spinner fa-spin faSpin " id="percentageSpin" style="display:none;"></i>
						 </span> 
		            </div>
		            
		            
		              
		               <div class="row mb">
		                  <label class="col-md-12">Assign To </label>
		              <div class="col-md-12 dropdown-select">
		                   <select class="form-control chosen-select choose" multiple id="assignedUsers" name="assignedUsers"></select> 
		              </div>
		              </div>
		              
		          </div>
		      </div>
      </div>
  </section>


<script>
$('.chosen-select').chosen().on('change', function() {
	var assignValue = $("#assignedUsers").val();
	if(assignValue != null){
		assignValue = assignValue;
	}else{
		assignValue = "";
	}
	var sourceleadUrl ='${sourceleadUrl}';
	var taskId = '${projectTask.id}';
	var userId = '${SourceLeadWeb_userId}';
	  $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/ProjectTasks/updateAssignTaskUsers?${_csrf.parameterName}=${_csrf.token}&assignValue="+assignValue+"&taskId="+taskId+"&userId="+userId, 	   
		    success: function(data) {
		    }
	 });  
});
</script>

<script>
function tasksAssignList(projectId){
	var sourceleadUrl ='${sourceleadUrl}';
	var values = "Active";
	  var map = new Object(); // or var map = {};
      <c:forEach var="assign" items="${assignedUsers}">
              map['${assign.projectMemberId.id}'] = '${assign.assignedUserName}';
      </c:forEach>
	$.ajax({
    	type: "POST",
        url: sourceleadUrl+"/getProjectEmployeeListById?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&status="+values, 
        success: function(data) {
        	var json = JSON.parse(data);
        	var output = "";
        	for (var i=0; i<json.projectMembersList.length; i++)
			{
        		var opt = json.projectMembersList[i];

        		if(!jQuery.isEmptyObject(map)){
        			 $.each(map, function(key, value) {
                         if(opt.id == key){
                             output = output +  '<option value="'+key+'" selected> '+value+'</option>';
                         }else{
         	        		output = output + "<option value='" +opt.id+ "'>"+opt.empName+"</option>";
                         }
                       });
        		}else{
        			output = output + "<option value='" +opt.id+ "'>"+opt.empName+"</option>";
        		}
        		
			}
        	$("#assignedUsers").chosen();
        	$("#assignedUsers").empty();
     		$("#assignedUsers").append(output);
     	    $("#assignedUsers").trigger("chosen:updated");
        }
	});	
}
</script>

<!-- In-Line Edit Fields  Scripts-->
<script>
function inlineditTaskName(){
	var taskName = $("#taskName").val();
	var projectTaskId = '${projectTask.id}';
	var sourceleadUrl = '${sourceleadUrl}';
	var userId = '${SourceLeadWeb_userId}';
	var hiddenTaskName = $("#hiddenTaskName").val();
	if(taskName.length > 250){
		$(".inputError").show();
	}else{
		if(taskName.match(/^[^\s-](.*)*$/) !== null){
			$.ajax({
			   	type: "POST",
			    url: sourceleadUrl+"/ProjectTasks/updateTaskName?${_csrf.parameterName}=${_csrf.token}&taskName="+taskName+"&taskId="+projectTaskId+"&userId="+userId, 	   
			    success: function(data) {
			    	$(".showTaskname").show();
			    	$(".editTaskname").hide();
			    	document.getElementById("saveIcon").style.display="none";
			    	$("#hiddenTaskName").val(data);
			    	$(".inputError").hide();
			    }
		 }); 
		}else{
			$(".showTaskname").hide();
	    	$(".editTaskname").show();
	    	document.getElementById("saveIcon").style.display="none";
			$("#taskName").val(hiddenTaskName);
		}
	}
	
	
	
	 
}
function editFun(){
	$(".showTaskname").hide();
	$(".editTaskname").show();
// 	$("#taskName").removeClass("formcontrol-edit").removeAttr("readonly");
// 	$("#taskedit-parent").removeClass("edit-inputfield");
	document.getElementById("saveIcon").style.display="block";
}
</script>
<script> 
function inlineEditTaskStatus(taskId){
	var taskStatusId = $("#taskStatusId").val();
    $("#statusLabel").fadeOut("slow");
    $("#statusSpin").fadeIn("slow");
	var sourceleadUrl = '${sourceleadUrl}';
	var userId = '${SourceLeadWeb_userId}';
	  $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/ProjectTasks/updateTaskStatus?${_csrf.parameterName}=${_csrf.token}&taskStatusId="+taskStatusId+"&taskId="+taskId+"&userId="+userId, 	   
  		    success: function(data) {
  		    	$("#statusLabel").fadeIn("slow");
  		    	$("#statusSpin").fadeOut("slow");
  		    	getTaskActivities();
  		    }
	 });  
}
function inlineEditTaskPriority(taskId){
	var taskpriorityId = $("#taskpriorityId").val();
	var sourceleadUrl = '${sourceleadUrl}';
	var userId = '${SourceLeadWeb_userId}';
       $("#priorityLabel").fadeOut("slow");
      $("#prioritySpin").fadeIn("slow");
	  $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/ProjectTasks/updateTaskPriority?${_csrf.parameterName}=${_csrf.token}&taskpriorityId="+taskpriorityId+"&taskId="+taskId+"&userId="+userId,  	   
  		    success: function(data) {
  		    	$("#priorityLabel").fadeIn("slow");
  		    	$("#prioritySpin").fadeOut("slow");
  		    	getTaskActivities();
  		    }
	 });  
}
function inlineEditTaskSeverity(taskId){
	var taskSeverityId = $("#taskseverityId").val();
	var userId = '${SourceLeadWeb_userId}';
	var sourceleadUrl = '${sourceleadUrl}';
	$("#severityLabel").fadeOut("slow");
    $("#severitySpin").fadeIn("slow");
	  $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/ProjectTasks/updateTaskSeverity?${_csrf.parameterName}=${_csrf.token}&taskSeverityId="+taskSeverityId+"&taskId="+taskId+"&userId="+userId,  	   
  		    success: function(data) {
  		    	$("#severityLabel").fadeIn("slow");
  		    	$("#severitySpin").fadeOut("slow");
  		    	getTaskActivities();
  		    }
	 });  
}

function inlineEditDescription(taskId){
	var desc = $(".Editor-editor").text(); 
	var sourceleadUrl = '${sourceleadUrl}';
	var userId = '${SourceLeadWeb_userId}';
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/ProjectTasks/updateTaskDescription?${_csrf.parameterName}=${_csrf.token}&description="+desc+"&taskId="+taskId+"&userId="+userId,    
		    success: function(data) {
		    	$("#jobAlert2").removeAttr("style");
    			$("#jobAlert2").css({ display: "block" });
				$("#editJobAlertMsgDiv").html("Successfully Update Description");
					editAlertMsgFadeinFunction();
					getTaskActivities();
		    }
	 }); 
}
function inlineEditTaskPercentage(taskId){
	var percentageId = $("#taskPercentageId").val();
	var sourceleadUrl = '${sourceleadUrl}';
	var userId = '${SourceLeadWeb_userId}';
	$("#percentageLabel").fadeOut("slow");
    $("#percentageSpin").fadeIn("slow");
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/ProjectTasks/updateTaskPercentage?${_csrf.parameterName}=${_csrf.token}&percentage="+percentageId+"&taskId="+taskId+"&userId="+userId,    
		    success: function(data) {
		    	$("#percentageLabel").fadeIn("slow");
  		    	$("#percentageSpin").fadeOut("slow");
  		    	getTaskActivities();
		    }
	 }); 
}
</script>
 <script>
	function editAlertMsgFadeinFunction() {
		$("#jobAlert2").fadeIn(200);
        $('#jobAlert2').delay(600).fadeOut('slow');
	}
</script> 
 <script>
 function description(projectTaskId){
	 var sourceleadUrl = '${sourceleadUrl}';
	 $("#descrptionid").empty();
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/ProjectTasks/getProjectTasksDescrption?${_csrf.parameterName}=${_csrf.token}&projectTaskId="+projectTaskId, 	   
		    success: function(json) {
		    	 result = "";
		    	 result = result +' <div class="success_pop" id="jobAlert2" style="display: none;">';
				 result = result +'  <i class="fa fa-check-circle"></i><span id="editJobAlertMsgDiv"></span>';
				 result = result +' </div> ';
				 
			   	 result  = result + '<div class="col-md-12 clearfix txtEditor_p saveBtn">';
			   	 result = result + '<textarea id="txtEditor2" name="taskDescription" value="'+ json.description +'" class="form-control newContrtextarea" >' + json.description + '</textarea>';
			   	 result  = result  + '<div class="mt5 clearfix"><button type"button" class="pull-right btn btn-sm btn-warning save-btn" onclick="inlineEditDescription('+projectTaskId+');">Save</button></span>';
			   	 result  = result  + '</div>';
			   	 result  = result  + '</div>';
			     $("#descrptionid").append(result);
			     $("#txtEditor2").Editor();
			     $(".Editor-editor").html(json.description);
		    }
	 });
	 $("span.fa").toggleClass("fa-caret-down fa-caret-up");
 }
 </script>
  
<script> 
//project side bar tabs
$(document).ready(function() {
$("#tab-task2").show();
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
	
//  New project Tabs
$(document).ready(function() {
	$("#tab-a").show()
    $(".newPrTabsMenu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".newPrTabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});
    
</script>
<script>
//Tasktype
$(document).ready(function() {
    $("#tasktype").click(function(){
		$(".approved_pop").slideToggle(200);
        $(".export_pop").slideUp(200);
	});
    $("html").click(function() {
        $(".approved_pop").slideUp(200);
    });
    $(".approved_pop, #tasktype").click(function(e) {
        e.stopPropagation();
    });	
});	
// Approved Popup
$(document).ready(function() {
    $("#appro-pop").click(function(){
		$(".export_pop").slideToggle(200);
        $(".approved_pop").slideUp(200);
	});
    $("html").click(function() {
        $(".export_pop").slideUp(200);
    });
    $(".export_pop, #appro-pop").click(function(e) {
        e.stopPropagation();
    });	
});	
</script>
<script>
    // Description Custom Script
	$('#summernote, #description_note').summernote();
</script>      
    <script>
    // Add Task side bar
$(document).ready(function() {
    $("#addTask").click(function(){
		$(".tasks_side_wrap").addClass("tasks_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
	});
    $(".proj_close, .side_mask").click(function(){
		$(".tasks_side_wrap").removeClass("tasks_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
});
$(document).ready(function() {
    $(".proj_show").click(function(){
		$(".tasks_view_wrap").addClass("tasks_view_wrap_active");
		$(".sideMask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
	});
    $(".proj_close, .sideMask").click(function(){
		$(".tasks_view_wrap").removeClass("tasks_view_wrap_active");
		$(".sideMask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
});
//Close and open  Time entry pop
        function addTimeEntry(){
            $(".modal-Time").fadeIn();
        }
        function closeTimeEntry(){
            $(".modal-Time").fadeOut();
        }
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
 $('html').click(function(){
    $(".collapse-div-adduser").removeClass("show-div");
	$(".collapse-div").removeClass("show-div");
   });	
	</script>
	
	<script>
// Add Resources Request Pop
$(document).ready(function() {
    $("#addResReq").click(function(){
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
	});
    $(".proj_close, .side_mask").click(function(){
		$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
});	
	</script>


<!-- ============================ Document tab related script Start ============================== -->
<script>
function addDocumentEntryPopUp(){
	 $(".document").fadeIn();
	 addDocumentEntry();
}
function closeTimeEntry(){
    $(".document").fadeOut();
}
function addDocumentEntry(){
	    result = "";
		result  = result + '		<div class="clearfix">';
		result  = result + '	    <button type="button" class="close" onclick="closeTimeEntry()">&times;</button>';
		result  = result + '	    <p><strong>Documents Details</strong></p>';
		result  = result + '	  </div>';
		result  = result + '	  <div class="row clearfix">';
		result = result + '    <form id="addDocument_form" name="addDocument_form">';
		
		result  = result + '	    <div class="col-md-12 mb10">';
		result  = result + '	      <textarea class="form-control" rows="1" placeholder="Description" name="taskDescription" data-validation-optional="true" data-validation="validate_length length1-250" ></textarea>';
		result  = result + '	    </div>';
		
		result  = result + '	    <div class="col-md-5">';
		result  = result + '	      <div class="row">';
		result  = result + '	        <div class="col-md-12">';
		result  = result + '	          <input type="text" class="form-control" placeholder="Document Name" name="documentName" data-validation-optional="true" data-validation="validate_length length1-50" >';
		result  = result + '	        </div>';
		result  = result + '	      </div>';
		result  = result + '	    </div>';
		
		result  = result + '	    <div class="col-md-7" id="uploadDocDiv">';
		result  = result + '	      <div class="row">';
		result  = result + '	        <div class="col-md-12 clearfix">';
		result  = result + '	                <div class="input-group pull-left errorInptgroup">';
		result  = result + '	                  <label class="input-group-btn"> <span class="btn btn-primary" onchange="taskUploadFile();"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File <input type="file" id="uploadBtn" style="display: none;"  class="upload" >';
		result  = result + '	                    </span> </label>';
		result  = result + '	                  <input class="form-control" readonly id="uploadFile" name="docFile"  placeholder="Attachment" type="text" field-name="Document" data-validation="required validate_docimgpdf">';
		result  = result + '	                </div>';
		result  = result + '	        </div>';
		result  = result + '	      </div>';
		result  = result + '	    </div>';
		result  = result + '	  </div>';
 
		result  = result + '	  <div class="modalFooter mt mb10">';
		result  = result + '	    <div class="pull-right">';
		result  = result + '	      <button type="button" class="btn btn-primary btn-sm" id="save_document" data-dismiss="modal" onclick="saveDocument();">Save</button>';
		result  = result + '	      <button type="button" class="btn btn-default btn-sm" data-dismiss="modal" onclick="closeTimeEntry()">Cancel</button>';
		result  = result + '	    </div>';
		result  = result + '	  </div>';
		result = result + '  </form>';
		result  = result + '	</div>';
		$("#editDocumentId").empty();
	 	$("#addDocumentId").empty();
        $("#addDocumentId").append(result);
        
   	 /* upload file script to show selected file name*/
	    document.getElementById("uploadBtn").onchange = function () {
	    	$('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''))
	   }; 
	    
	    /* upload file script for size validation 2MB*/
	    var selectedFile = document.getElementById("uploadBtn");
	    selectedFile.addEventListener("change", function() {
	      var docSize = this.files[0].size;
	      $("#uploadFileGroup").find('p.jquery_form_error_message').remove();
	      if(docSize > 2097152){
	    	  document.getElementById("uploadFile").value = '';
	    	  $("#uploadFileGroup").append("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </p>");
	      }
	    });
	    
	     
	
}
function taskUploadFile(){
	var sourceleadUrl = '${sourceleadUrl}';
	fileUpload(sourceleadUrl, "uploadBtn", "tasks", "addDocument_form");
}

function documentTableList(){
	var sourceleadUrl = '${sourceleadUrl}';
	var projectTaskId = ${projectTask.id}
	$.ajax({
		type : "POST",
		url : sourceleadUrl+ "/ProjectTasks/getProjectTasksDocuments?${_csrf.parameterName}=${_csrf.token}&projectTaskId="+projectTaskId,
		success : function(json) {
			 result = "";
			    result  = result + '	<div class="clearfix project_wrap_table filter_position datatable_filter">';
				result  = result + '	  <table id="taskDocumentPagination">';
				result = result +  '    <thead>';
   		    	result = result + '	  <tr>';
   		    	result  = result + '    <th>Document Name</th>';
   				result  = result + '    <th>Description</th>';
   				result  = result + '    <th>Attachment</th>';
   				result  = result + '    <th>Action</th>';
   		    	result = result + '	  </tr>';
   		    	result = result +  '  </thead>';
   		    	result = result +'<tbody>';
   		    	for (var i=0; i<json.length; i++)
   				{
   		    		var report = json[i];
   		    		if(report.description != null && report.description != "" && report.description != "undefined"){
   		    			description = report.description
   		    		}else{
   		    			description = "--";
   		    		}
   		    		var documentName = "";
   		    		if(report.documentName != null && report.documentName != "" && report.documentName != "undefined"){
   		    			documentName = report.documentName;
   		    		}else{
   		    			documentName = report.fileName;
   		    		}
	   		    	result  = result + '		      <tr>';
	   		 		result  = result + '		          <td>';
	   		 		result  = result + '		          <div class="tbldata-dotdot" title="'+documentName+'">'+documentName+'</div>';
	   		 		result  = result + '		        </td>';
	   		 		result  = result + '		          <td>';
	   		 		result  = result + '		          <div class="tbldata-dotdot-me" title="'+description+'">'+description+'</div>';
	   		 		result  = result + '		        </td>';
	   		 	    result  = result + '               <td><div class="tblminwidth-50"><a href="javascript:void(0)" onclick="projectTaskDocumentView(\''+report.id+'\',\''+report.fileName+'\');" class="fa fa-eye ml10 paddingRight"></a><a class="paddingLeft glyphicon glyphicon-download-alt" href=\"'+report.fileURL+'\"></a></div></td>';
	   		 	    result  = result + '               <td><div><a class="glyphicon glyphicon-pencil padddigRight" title="Edit" onclick="getEditTaskDocument('+report.id+');"></a><i title="Delete" class="glyphicon glyphicon-trash paddingLeft" onclick="removeProjectTaskDocuments('+report.id+');"></i></div></td>';
	   		 		result  = result + '		      </tr>';
   				}
   		    	result  = result + '    </tbody>';
				result  = result + '   </table>';
				result  = result + ' </div>';
				$("#documentTableId").empty();
		        $("#documentTableId").append(result);
		        taskDocumentPagination();
		}
	});
		
}
</script>
<script>
function getEditTaskDocument(projectTaskDocId){
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/ProjectTasks/getProjectTaskDocument?${_csrf.parameterName}=${_csrf.token}&projectTaskDocId="+projectTaskDocId, 	   
			    success: function(json) {
			    	if(json.description != null && json.description != "" && json.description != "undefined"){
			    		description = json.description;
			    	}else{
			    		description = "";
			    	}
			    	var  documentName = "";
			    	if(json.documentName != null && json.documentName != "" && json.documentName != "undefined"){
			    		documentName = json.documentName;
			    	}else{
			    		documentName = json.fileName;
			    	}
			    	
			    	result = "";
					result  = result + '		<div class="clearfix">';
					result  = result + '	    <button type="button" class="close" onclick="closeTimeEntry()">&times;</button>';
					result  = result + '	    <p><strong>Update Documents Details</strong></p>';
					result  = result + '	  </div>';
					result  = result + '	  <div class="row clearfix">';
					result = result + '    <form id="updateDocument_form" name="updateDocument_form">';
					
					result  = result + '	    <div class="col-md-12 mb10">';
					result  = result + '	      <textarea class="form-control" rows="1" placeholder="Description" id="description" name="taskDescription" value="'+description+'" data-validation-optional="true" data-validation="validate_length length1-250" >'+description+'</textarea>';
					result  = result + '	    </div>';
					
					result  = result + '	    <div class="col-md-5">';
					result  = result + '	      <div class="row">';
					result  = result + '	        <div class="col-md-12">';
					result  = result + '	          <input type="text" class="form-control" placeholder="Document Name" id="documentName" value="'+documentName+'" name="documentName" data-validation-optional="true" data-validation="validate_length length1-50" >';
					result  = result + '	        </div>';
					result  = result + '	      </div>';
					result  = result + '	    </div>';
					
					result  = result + '	    <div class="col-md-7" id="uploadDocDiv" style="display: none;">';
					result  = result + '	      <div class="row">';
					result  = result + '	        <div class="col-md-12 clearfix">';
					result  = result + '	                <div class="input-group col-md-10 pull-left errorInptgroup">';
					result  = result + '	                  <label class="input-group-btn"> <span class="btn btn-primary" onchange="editUploadFile();"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File <input type="file"  id="uploadBtn" style="display: none;"   class="upload">';
					result  = result + '	                    </span> </label>';
					result  = result + '	                  <input class="form-control" readonly id="uploadFile" name="docFile"  placeholder="Attachment" type="text" field-name="Document" data-validation="required validate_docimgpdf">';
					result  = result + '	                </div>';
					result  = result + '	        </div>';
					result  = result + '	      </div>';
					result  = result + '	    </div>';
					
					result = result+'<div class="col-md-7" id="docNameDiv">';
		   			result = result+'	  <div class="col-md-12 clearfix form-control">';
		   			result = result + '	      <div class="input-group col-md-12 pull-left">';
		   			result = result+'		   <label id="docsName" name="docName" class="text-primary pull-left paddingRight tbldata-dotdot-verybig" title="'+json.fileName+'">'+json.fileName+'</label><span id="delete_doc" onclick="removeDocNameDiv();"><i class="cursorPointer text-danger paddingLeft fa fa-trash removeIcon" title="Delete"></i></span>';
		   			result = result+'	     </div>';
		   			result = result+'	  </div>';
		   			result = result+'</div>';
					
					result  = result + '	  </div>';
			 
					
					
		   			
					result  = result + '	  <div class="modalFooter mt mb10">';
					result  = result + '	    <div class="pull-right">';
					result  = result + '	      <button type="button" class="btn btn-primary btn-sm" id="save_document" data-dismiss="modal" onclick="updateDocument('+json.id+');">Update</button>';
					result  = result + '	      <button type="button" class="btn btn-default btn-sm" data-dismiss="modal" onclick="closeTimeEntry()">Cancel</button>';
					result  = result + '	    </div>';
					result  = result + '	  </div>';
					result = result + '  </form>';
					result  = result + '	</div>';
					
					$("#addDocumentId").empty();
				 	$("#editDocumentId").empty();
			        $("#editDocumentId").append(result);
			        $(".document").fadeIn();
			        
			        document.getElementById("uploadBtn").onchange = function () {
				    	$('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''))
				   }; 
				    
				    var selectedFile = document.getElementById("uploadBtn");
				    selectedFile.addEventListener("change", function() {
				      var docSize = this.files[0].size;
				      $("#uploadFileGroup").find('p.jquery_form_error_message').remove();
				      if(docSize > 2097152){
				    	  document.getElementById("uploadFile").value = '';
				    	  $("#uploadFileGroup").append("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </p>");
				      }
				    });
				    
				    
			    }
	 });
	 
}
</script>
<script>
function editUploadFile(){
	var sourceleadUrl ='${sourceleadUrl}';
	fileUpload(sourceleadUrl, "uploadBtn", "tasks", "updateDocument_form");
}
</script>
<script>
function removeDocNameDiv(){
	$("#documentName").val('');
	document.getElementById("uploadDocDiv").style.display="block";
	document.getElementById("docNameDiv").style.display="none";
	$("#uploadFile").attr("data-validation", "required validate_Space validate_docimgpdf");  
}
</script>
<script>
function saveDocument(){
	 if ($('#addDocument_form').validate(false, validationSettings)) {
			var frm = $('#addDocument_form').serializeFormJSON();
			var form = $('#addDocument_form')[0];
			var formData = new FormData(form);
			var projectTaskId = '${projectTask.id}';
		  	var sourceleadUrl = '${sourceleadUrl}';
		  	var userId = '${SourceLeadWeb_userId}';
		  	$("#save_document").attr("disabled", "disabled");
			$("#save_document").addClass('button').val('Processing..');
			$.ajax({
				 type : "POST",
				 data: formData,
		         processData: false,
		         contentType:false, 
				 url : sourceleadUrl + "/ProjectTasks/saveProjectTaskDocuments?${_csrf.parameterName}=${_csrf.token}&projectTaskId="+projectTaskId+"&userId="+userId,
				  success : function(data) {
					  closeTimeEntry();
					  documentTableList();
				}, 
			});
			return true;
		}else {
			return false;
		} 
}
$('body').on('blur', '#addDocument_form', function() {
	$("#addDocument_form").showHelpOnFocus();
	$("#addDocument_form").validateOnBlur(false, validationSettings);
});

</script>

<script>
function updateDocument(documentId){
	if ($('#updateDocument_form').validate(false, validationSettings)) {
		var frm = $('#updateDocument_form').serializeFormJSON();
		var jsonData = JSON.stringify(frm);
		var form = $('#updateDocument_form')[0];
		var formData = new FormData(form);
		formData.append("json",jsonData)
		var projectTaskId = '${projectTask.id}';
	  	var sourceleadUrl = '${sourceleadUrl}';
	  	var userId = '${SourceLeadWeb_userId}';
	  	$("#save_document").attr("disabled", "disabled");
		$("#save_document").addClass('button').val('Processing..');
		$.ajax({
			 type : "POST",
			 data: formData,
   	         contentType : 'application/json; charset=utf-8',
	         processData: false,
	         contentType:false, 
   	         dataType : 'json',
			 url : sourceleadUrl + "/ProjectTasks/saveProjectTaskDocuments?${_csrf.parameterName}=${_csrf.token}&projectTaskId="+projectTaskId+"&documentId="+documentId+"&userId="+userId,
			  success : function(data) {
				  closeTimeEntry();
				  documentTableList();
			}, 
		});
		
	}
	 
	/* $('body').on('blur', '#updateDocument_form', function() {
		$("#updateDocument_form").showHelpOnFocus();
		$("#updateDocument_form").validateOnBlur(false, validationSettings);
	}); */
	}
</script>
<script>
function projectTaskDocumentView(documentId,title){
	 var sourceleadUrl ='${sourceleadUrl}';
		if(documentId != null && documentId != ""){
			window.open(sourceleadUrl+'/ProjectTasks/viewProjectTaskDocument/'+title+'?documentId='+documentId,title,"height=600,width=600");
		}
}
</script>
<script>
function removeProjectTaskDocuments(documentId){
	var sourceleadUrl ='${sourceleadUrl}';
	var userId = '${SourceLeadWeb_userId}';
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/ProjectTasks/deleteProjectTaskDocument?${_csrf.parameterName}=${_csrf.token}&porjectTaskDocumentId="+documentId+"&userId="+userId, 	   
		    success: function(data) {
		    	closeTimeEntry();
		    	documentTableList();
		    },
     });

}


</script>
 <script>
function taskDocumentPagination(){  
    $('#taskDocumentPagination').DataTable( { 
    	"scrollCollapse": false,
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


<!-- ======================== Document Related script Ends ===================================== -->



<!-- ============================ SubTask related Script Start ========================================== -->
<div class="side_mask" onclick="taskClose();"><div class="proj_close"></div></div>
<div class="tasks_side_wrap">
	<div id="addSubTaskPopupDivId"></div>	
</div>
<script>
function addSubTasks(){
			var projectId =  "${projectId}";
			var result = "";
			result = result	+ ' 	<div class="tasks_wrapinn">';
			result = result	+ '     <h4 class="modal-title">Add Task</h4>';
			result = result	+ '     <div class="row clearfix">';
			result = result	+ '     <form name="projectTask_form" id="projectTask_form" method="post">';
			result = result	+ '       <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">';
			result = result	+ '         <div class="form-group">';
			result = result	+ '           <label class="col-md-4">Task Type<span class="text-danger paddingLeft">*</span></label>';
			result = result	+ '           <div class="col-md-8 dropdown-select">';
		    result = result	+ '             <select class="form-control" onchange="showtasktype()" id="taskstype" name="taskstype" field-name="Task Type"  data-validation="required">'; 
		    result = result	+ '         <c:forEach var="type" items="${taskTypeList}">  ';      
		    result = result	+ '            <option value="${type.uniqueKey}" ${type.uniqueKey  == "SubTask" ? "selected" : ''} >${type.name}</option>'; 
		    result = result	+ '  		</c:forEach>  ';                                           
			result = result	+ '             </select>'; 
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			
			result = result	+ '           <div class="form-group" style="display: block" id="epic">';
			result = result	+ '           <label class="col-md-4">Epic</label>';
			result = result	+ '           <div class="col-md-8 dropdown-select">';
			result = result	+ '             <select class="chosen-select form-control" id="epicId" name="epic">';
			result = result	+ '             </select>';
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			
			result = result	+ '           <div class="form-group" style="display: none" id="userStory">';
			result = result	+ '           <label class="col-md-4">User Story</label>';
			result = result	+ '           <div class="col-md-8 dropdown-select">';
			result = result	+ '             <select class="chosen-select form-control" id="userStroyId" name="userStory">';
			result = result	+ '             </select>';
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			
			result = result	+ '           <div class="form-group" style="display: none" id="task">';
			result = result	+ '           <label class="col-md-4">Task<span class="text-danger paddingLeft">*</span></label>';
			result = result	+ '           <div class="col-md-8 dropdown-select">';
			result = result	+ '             <select class="chosen-select form-control" id="subTasksId" name="subTask" field-name="Task">';
			result = result	+ '             </select>';
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			
			result = result	+ '         <div class="form-group">';
			result = result	+ '           <label class="col-md-4">Task Title<span class="text-danger paddingLeft">*</span></label>';
			result = result	+ '           <div class="col-md-8">';
			result = result	+ '             <input class="form-control"  placeholder="Task Title" type="text" id="taskName" name="taskName" field-name="Task Title"  data-validation="required validate_length length2-250" >';
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			
			result = result	+ '         <div class="form-group">';
			result = result	+ '           <label class="col-md-4">Assign To</label>';
			result = result	+ '           <div class="col-md-8">';
			result = result	+ '             <select class="form-control chosen-select choose" multiple  id="assignTaskId" name="assignTaskId" data-placeholder="Select Assign To">';
			result = result	+ '             </select>';
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			result = result	+ '         <div class="form-group">';
			result = result	+ '           <label class="col-md-4">Due Date</label>';
			result = result	+ '           <div class="col-md-8">';
			result = result	+ '             <input class="form-control calendar" value="" name="duDate" id="dueDate" placeholder="Due Date" type="text" readonly>';
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			result = result	+ '         <div class="form-group">';
			result = result	+ '           <label class="col-md-4">Priority</label>';
			result = result	+ '           <div class="col-md-8 dropdown-select">';
			result = result	+ '             <select class="form-control" id="priority" name="priority">';
			result = result	+ '               <option value="">-- Select Priority --</option>';
			result = result	+ '           <c:forEach var="priority" items="${taskPriorityList}"> ';        
			result = result	+ '		             <option value="${priority.id}">${priority.name}</option>';   
			result = result	+ '			</c:forEach>   ';	
			result = result	+ '             </select>'; 
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			result = result	+ '         <div class="form-group">';
			result = result	+ '           <label class="col-md-4">Estimated Date</label>';
			result = result	+ '           <div class="col-md-8">';
			result = result	+ '             <input class="form-control calendar"  id="estimatedDate" name="estimatedDate" placeholder="Estimated Date" type="text" readonly>';
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			result = result	+ '         <div class="form-group">';
			result = result	+ '           <label class="col-md-4">Severity</label>';
			result = result	+ '           <div class="col-md-8 dropdown-select">';
			result = result	+ '             <select class="form-control" id="severity" name="severity">';
			result = result	+ '               <option value="">-- Select Severity --</option>';
			result = result	+ '                 <c:forEach var="severity" items="${taskSeverityList}">  ';       
		    result = result	+ '  				  <option value="${severity.id}">${severity.name}</option>';   
			result = result	+ ' 				 </c:forEach>  ';
			result = result	+ '             </select>';  
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			result = result	+ '         <div class="form-group">';
			result = result	+ '           <label class="col-md-4">Status<span class="text-danger paddingLeft">*</span></label>';
			result = result	+ '           <div class="col-md-8 dropdown-select">';
			result = result	+ '             <select class="form-control" field-name="Status"  id="taskStatus" name="taskStatus" data-validation="required">';
			result = result	+ '              <c:forEach var="Status" items="${taskStatusList}">    ';   
			result = result	+ ' 			  <option value="${Status.id}">${Status.name}</option> ';
			result = result	+ ' 			 </c:forEach>';  
			result = result	+ '             </select>'; 
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			result = result	+ '         <div class="form-group">';
			result = result	+ '           <label class="col-md-4">Owner</label>';
			result = result	+ '           <div class="col-md-8">';
			result = result	+ '             <select class="form-control" data-placeholder="Select Owner" id="ownerId" name="taskOwner">';
			result = result	+ '             </select>';
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			result = result	+ '         <div class="form-group">';
			result = result	+ '           <label class="col-md-4">Billing Type<span class="text-danger paddingLeft">*</span></label>';
			result = result	+ '           <div class="col-md-8 dropdown-select">';
			result = result	+ '             <select class="form-control" field-name="Billing Type" id="taskBillableType" name="taskBillableType" data-validation="required">';
			result = result	+ '               <option value="Billable">Billable</option>';
			result = result	+ '               <option value="Non-Billable">Non-Billable</option>';
			result = result	+ '             </select>';
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			
			result = result	+ '         <div class="form-group">';
			result = result	+ '           <label class="col-md-4">Business Value</label>';
			result = result	+ '             <div  class="col-md-8 positionRelative">';
			result = result	+ '             <div class="input-group er ratetype">';
			result = result	+ '					<div class="input-group-btn">';
			result = result	+ '					<select class="selectpicker" id="currencyType" name="currencyType">';
			result = result	+ '						<option selected="">INR</option>';
			result = result	+ '						<option>AUD</option>';
			result = result	+ '						<option>USD</option>';
			result = result	+ '						<option>GBP</option>';
			result = result	+ '						<option>SGD</option>';
			result = result	+ '						<option>EUR</option>';
			result = result	+ '					</select>';
			result = result	+ '					</div>';
			result = result	+ '					<input type="text" class="form-control" id="businessValue" name="businessValue" data-validation="validate_Space validate_length length1-8 validate_int" data-validation-optional="true">';
			result = result	+ '					</div>';
			result = result	+ '					<div class="clearfix"></div>';
			result = result	+ '					</div>';
			result = result	+ '             </div>';
			result = result	+ '         <div class="clearfix"></div>';
			result = result	+ '         <div class="clearfix mb">';
			result = result	+ '           <label class="col-md-12">Description</label>';
			result = result	+ '           <div class="col-md-12 txtEditor_p">';
			result = result	+ '            <textarea id="txtEditor" name="taskDescription"  class="form-control newContrtextarea"></textarea>';
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			result = result	+ '         <div class="clearfix"></div>';
			result = result	+ '         <div class="form-group mt">';
			result = result	+ '           <label class="col-md-2 mt5">Attachment</label>';
			result = result	+ '           <div class="col-md-8 clearfix">';
			result = result	+ '           <input id="Button1" type="button" value="Add Document" onclick="addFileUpload()" class="btn btn-sm btn-primary" />';
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			result = result	+ '         <div class="col-md-12 clearfix">';
			result = result	+ '         <div id="FileUploadContainer"> </div>';
			result = result	+ '         </div>'; 
			result = result	+ '       </div>';
			result = result	+ '       </form>';
			result = result	+ '     </div>';
			result = result	+ '   <div class="modal-footer">';
			result = result	+ '     <button type="button" class="btn btn-primary btn-sm" id="save_task" onclick="saveTasks();" data-dismiss="modal">Add</button>';
			result = result	+ '     <button type="button" class="btn btn-default btn-sm" data-dismiss="modal" onclick="cancelTask();">Cancel</button>';
			result = result	+ '   </div>';
			result = result	+ ' </div>';
			
		    $("#addSubTaskPopupDivId").empty();
			$("#addSubTaskPopupDivId").append(result);
			$("#txtEditor").Editor();
			getOwnerList();
			subTasksAssignList(projectId);
			$(".tasks_side_wrap").addClass("tasks_side_wrap_active");
			$(".side_mask").fadeIn(200);
			$("body").css('overflow-y', 'hidden');	
			
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
				})
				$('#ownerId').chosen('refresh');
				$('#ownerId').trigger("chosen:updated");
				$('.selectpicker').selectpicker('refresh');
		
				  var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
				  var finalDateFormatProjectSave = '';
				  if(locationDate.match("yyyy-MM-dd")){
					  finalDateFormatProjectSave = 'yy-mm-dd';
				  }if(locationDate.match("dd-MM-yyyy")){
					  finalDateFormatProjectSave = 'dd-mm-yy'; 
				  }if(locationDate.match("MM-dd-yyyy")){
					  finalDateFormatProjectSave = 'mm-dd-yy'; 
				  }if(locationDate.match("yyyy-dd-MM")){
					  finalDateFormatProjectSave = 'yy-dd-mm'; 
				  }
			    $('#estimatedDate').datepicker({
					dateFormat : finalDateFormatProjectSave,
					changeMonth: true,
					changeYear: true, 
					onSelect : function(selected) {
					}
				});
			    
			    $('#dueDate').datepicker({
					dateFormat : finalDateFormatProjectSave,
					changeMonth: true,
					changeYear: true, 
					onSelect : function(selected) {
					}
				});
			     
}

var fileCounter = 0;
function addFileUpload(){
	
	$('<div>').attr('style','float:left; width:325px; border:1px solid #ddd; margin:0 15px 15px 0; padding:5px;overflow: hidden; background-color:#367fa9; color:#fff; ').append('<input id="file'+fileCounter+'"  type="file" class="pull-left" onchange="uploadButton('+fileCounter+');"/>' +
            '<a id="Button' + fileCounter + '' +
            'value="" onclick = "RemoveFileUpload(this)"><i class="fa fa-times btn btn-danger btn-sm btn-sm2 pull-right"></i></a>').appendTo($('#FileUploadContainer'));

    		fileCounter++;
}
function RemoveFileUpload(div)
{
	 document.getElementById("FileUploadContainer").removeChild(div.parentNode);
}

function cancelTask(){
	$(".chosen-select").val('').trigger("chosen:updated");
	$(".tasks_side_wrap").removeClass("tasks_side_wrap_active");
	$(".side_mask").fadeOut(200);
	$("body").css('overflow-y', 'auto');
	$("#txtEditor").val($('.Editor-editor').html('')); 
	$("div.row-fluid .Editor-container").remove();
	document.getElementById("projectTask_form").reset();
	$("projectTask_form").validate().resetForm();
	
	 
}
</script>
<script>
function uploadButton(id){
	var sourceleadUrl ='${sourceleadUrl}';
	fileUpload(sourceleadUrl, "file"+id+"", "tasks", "projectTask_form");
}
</script>
<script>
function getOwnerList(){
	var sourceleadUrl ='${sourceleadUrl}';
	var orgLocationId = '${SourceLeadWeb_orgLocations.id}';
	$.ajax({
	    	type: "GET",
	    	 url: sourceleadUrl+"/getResourceRequestApproversList?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId,
	    		success: function(data) {
	        	var json = JSON.parse(data);
	     		var output = "";
	     			output = output + "<option value=''>--Select Owner--</option>";
	     		for (var i=0; i<json.length; i++)
	     		{
		     		var opt = json[i];
		     		var fullname = '';
		     		if(opt.lastName != null && opt.lastName != '' && opt.lastName != 'undefined'){
		     			fullname = opt.firstName+" "+opt.lastName;
		     		}else{
		     			fullname = opt.firstName;
		     		}
		     			output = output + "<option value='" +opt.id+ "'>"+fullname+" ("+opt.userType+")</option>";
	     		}
	     		$("#ownerId").empty();
	     		$("#ownerId").append(output);
	     		$('#ownerId').trigger("chosen:updated"); 
	     		
	         }
	 });
}
</script>
<script>
function subTasksAssignList(projectId){
	var sourceleadUrl ='${sourceleadUrl}';
	var values = "Active";
	$.ajax({
    	type: "POST",
        url: sourceleadUrl+"/getProjectEmployeeListById?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&status="+values, 
        success: function(data) {
        	var json = JSON.parse(data);
        	var output = "";
        	for (var i=0; i<json.projectMembersList.length; i++)
			{
	        		var opt = json.projectMembersList[i];
	        		output = output + "<option value='"+opt.id+"'>"+opt.empName+"</option>";
			}
        	$("#assignTaskId").chosen();
        	$("#assignTaskId").empty();
     		$("#assignTaskId").append(output);
     	    $("#assignTaskId").trigger("chosen:updated");
        }
	});	
}
</script>

<script>
 function showtasktype(){
	 var taskType = $("#taskstype").val();
	 if(taskType ==  "General" || taskType == "Epic"){
		 document.getElementById("userStory").style.display="none";
		 document.getElementById("epic").style.display="none";
		 document.getElementById("task").style.display="none";
		 $('#taskId').removeAttr("data-validation","required");
	 }
	   if(taskType ==  "UserStory"){
		 document.getElementById("epic").style.display="block";
		 document.getElementById("userStory").style.display="none";
		 document.getElementById("task").style.display="none";
		 $('#taskId').removeAttr("data-validation","required");
		 getSubEpicsByUserStory();
	 }
	    if(taskType ==  "Bug"){
		 document.getElementById("epic").style.display="none";
		 document.getElementById("userStory").style.display="block";
		 document.getElementById("task").style.display="none";
		 $('#taskId').removeAttr("data-validation","required");
		 getUserStoryByBug();
	 }
	 if(taskType ==  "SubTask"){
		 document.getElementById("epic").style.display="none";
		 document.getElementById("userStory").style.display="none";
		 document.getElementById("task").style.display="block";
		 $('#taskId').attr("data-validation","required"); 
		 getSubTasks();
	 } 
	 
 }
 </script>
 <script>
 function getSubEpicsByUserStory(type){
	 var projectId =  "${projectId}";
	 var sourceleadUrl ='${sourceleadUrl}';
	 var taskType = "Epic";
	 $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/ProjectTasks/getProjectTasksByTaskType?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&taskType="+taskType,
	    		success: function(json) {
	    			var output = "";
	    				output = output + "<option value=''>--Select Epic--</option>";
	    			for (var i=0; i<json.length; i++)
		     		{
			     		var opt = json[i];
			     		if(opt.generatedId != null && opt.generatedId != "" && opt.generatedId != "undefined"){
			     			generatedId = opt.generatedId +" - ";
			     		}else{
			     			generatedId = "";
			     		}
			     			output = output + "<option value='" +opt.id+ "'> "+generatedId+"   "+opt.taskName+"</option>";
		     		}
	    			 
	    			$("#epicId").empty();
		     		$("#epicId").append(output);
		     		$('#epicId').trigger("chosen:updated");
	    		}
	 });
 }
 function getUserStoryByBug(){
	 var projectId =  "${projectId}";
	 var sourceleadUrl ='${sourceleadUrl}';
	 var taskType = "UserStory";
	 $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/ProjectTasks/getProjectTasksByTaskType?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&taskType="+taskType,
	    		success: function(json) {
	    			var output = "";
	    				output = output + "<option value=''>--Select User Story--</option>";
	    			 
	    			for (var i=0; i<json.length; i++)
		     		{
			     		var opt = json[i];
			     		if(opt.generatedId != null && opt.generatedId != "" && opt.generatedId != "undefined"){
			     			generatedId = opt.generatedId +" - ";
			     		}else{
			     			generatedId = "";
			     		}
			     			output = output + "<option value='" +opt.id+ "'>"+generatedId+"  "+opt.taskName+" </option>";
		     		}
	    				$("#userStroyId").empty();
			     		$("#userStroyId").append(output);
			     		$('#userStroyId').trigger("chosen:updated");
	    		}
	 });
 }
 
 </script>
 <script>
 function getSubTasks(){
	 var projectId =  "${projectId}";
	var sourceleadUrl ='${sourceleadUrl}';
	var projectTaskId = "${projectTask.id}";
	taskType = "SubTask";
	$.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/ProjectTasks/getProjectTasksBySubTaskType?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&taskType="+taskType,
	    		success: function(json) {
	     		var output = "";
	     			output = output + "<option value=''>--Select Task--</option>";
	     		for (var i=0; i<json.length; i++)
	     		{
		     		var opt = json[i];
		     		if(opt.generatedId != null && opt.generatedId != "" && opt.generatedId != "undefined"){
		     			generatedId = opt.generatedId +" - ";
		     		}else{
		     			generatedId = "";
		     		}
		     		
		     		if(opt.id == projectTaskId){
	        			output = output + "<option value='"+opt.id+"' selected>"+generatedId+"  "+opt.taskName+"</option>";
	        		}else{
	        			output = output + "<option value='"+opt.id+"'>"+generatedId+"  "+opt.taskName+"</option>";
	        		}
		     		
		     		
	     		}
	     			$("#subTasksId").empty();
		     		$("#subTasksId").append(output);
		     		$('#subTasksId').trigger("chosen:updated");
	     		
	         }
	 });
 }
 </script>
 <script>
function saveTasks(){
	 if ($('#projectTask_form').validate(false, validationSettings)) {
		var frm = $('#projectTask_form').serializeFormJSON();
		var ee = $("#txtEditor").val($('.Editor-editor').html()); 
		var form = $('#projectTask_form')[0];
		var formData = new FormData(form);
		var projectId =  "${projectId}";
	  	var sourceleadUrl = '${sourceleadUrl}';
	  	$("#save_task").attr("disabled", "disabled");
	  	var userId = '${SourceLeadWeb_userId}';
		$("#save_task").addClass('button').val('Processing..');
		$.ajax({
			 type : "POST",
			 data: formData,
	         processData: false,
	         contentType:false, 
			 url : sourceleadUrl + "/ProjectTasks/saveProjectTasks?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&userId="+userId,
			  success : function(data) {
				  getSubTaskList();
				  cancelTask();
			}, 
		});
		return true;
	}else {
		return false;
	} 
}
$('body').on('blur', '#projectTask_form', function() {
	$("#projectTask_form").showHelpOnFocus();
	$("#projectTask_form").validateOnBlur(false, validationSettings);
});
</script>


<script>
function getSubTaskList(){
	var sourceleadUrl = '${sourceleadUrl}';
	var projectId =  "${projectId}";
	var projectTaskId = "${projectTask.id}";
	$.ajax({
		type : "POST",
		url : sourceleadUrl	+ "/ProjectTasks/getsubTasksList?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&subTaskId="+projectTaskId,
		success : function(json) {
			var result = "";
			result = result + '<table id="subTasksPagination" class="display" cellspacing="0" width="100%">';
			result = result + '<thead>';
			result = result + '<tr>';
			result = result + '<th>Task Name</th>';
			result = result + '<th>Assigned To</th>';
			result = result + '<th>Status</th>';
			result = result + '</tr>';
			result = result + '</thead>';
			result = result + '<tbody>';
			for (var i = 0; i < json.length; i++) {
				var opt = json[i];
				 
				if(opt.taskStaus != null && opt.taskStaus != "" && opt.taskStaus !="undefined"){
					taskStaus = opt.taskStaus;
				}else{
					taskStaus = "--"
				}
				
				if(opt.taskAssignedUsers != null && opt.taskAssignedUsers != "" && opt.taskAssignedUsers !="undefined"){
					taskAssignedUsers = opt.taskAssignedUsers;
				}else{
					taskAssignedUsers = "--"
				}
				
				 
				result = result + '<tr>';
				result = result+ '<td><div><a href="'+sourceleadUrl+'/projectTaskSummary?taskId='+opt.id+'" title="'+ opt.taskName+ '" >'+ opt.taskName+ '</a></div></td>';
				result = result	+ '<td><div><span >'+taskAssignedUsers+'</span></div></td>';
				result = result	+ '<td><div><span>'+taskStaus+'</span></div></td>';
				result = result + '</tr>';

			}
			result = result + '</tbody>';
			result = result + '</table>   ';
			$("#subTaskListdivId").empty();  
    		$("#subTaskListdivId").append(result);  
			subTaskPagination();

		}
	});
}
</script>
<script>
function subTaskPagination(){  
    $('#subTasksPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"autoWidth": false,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    } );
}
</script>
<!-- ============================ SubTask related Script End's========================================== -->


<!-- ============================ Activity related Script Start ========================================== -->
<script>
function getTaskActivities(){
	var sourceleadUrl = '${sourceleadUrl}';
	var projectTaskId = "${projectTask.id}";
	$.ajax({
		type : "POST",
		url : sourceleadUrl	+ "/ProjectTasks/getTaskActivityList?${_csrf.parameterName}=${_csrf.token}&projectTaskId="+projectTaskId,
		success : function(json) {
			var result = "";
			if(json != null && json != 'undefined' && json != "" && json != '[]'){
				for (var i = 0; i < json.length; i++) {
					var report = json[i];
					result = result + '	<div class="media activities-projecttask clearfix col-md-12">';
					result = result + '   <div class="media-left">';
					result = result  +'	   <img onerror="predefineProfileImage(this);" src="'+sourceleadUrl+'/displayUserImage/'+report.loginUserId+'" class="media-object" style="width:60px"/>';
					result = result + '   </div>';
					result = result + '   <div class="media-body">';
					result = result + '     <h5 class="media-heading text-primary mt10 clearfix"><div class="pull-left tbldata-dotdot-big">'+report.createdUserName+'</div>';
					result = result + '         <div class="pull-right small text-muted text-right">'+report.actionTimeStr+'<br/><span class="mt5">'+report.createdStr+' &nbsp;&nbsp;'+report.activityTimezoneStr+'</span></div>';
					result = result + '       </h5>';   
					result = result + '       <div class="clearfix description-task mt5"><strong>'+report.activity+'</strong>: '+report.activityDescription+'</div>';
					result = result + '   </div>';
					result = result + ' </div>';
				}
			}else{
				result = result+"   <div class='dataTables_empty'>"; 
				result = result+"  </div> ";
			}
			
			$("#taskActivityDivId").empty();  
    		$("#taskActivityDivId").append(result); 
		}
	});
}
</script>

<!-- ============================ Activity related Script End's ========================================== -->


<!-- ============================ Comments related Script Start ========================================== -->

<script>
function addCommentsPopUp(){
	$(".commentPopUp").fadeIn();
	addComment();
}
function closeComment(){
	$(".commentPopUp").fadeOut();
}
function addComment(){
	 result = "";
	result  = result + '	<div class="clearfix">';
	result  = result + '   <button type="button" class="close" onclick="closeComment()">&times;</button>';
	result  = result + '   <p><strong>Add Comment</strong></p>';
	result  = result + ' </div>';
	result = result + '    <form id="addComment_form" name="addComment_form">';
	result  = result + ' <div class="row clearfix">';
	result  = result + '   <div class="col-md-12 mb10">';
	result  = result + '     <textarea class="form-control" rows="1" id="taskComment" name="taskComment" placeholder="Comment here ..."></textarea>';
	result  = result + '   </div>';
	result  = result + '</div>';
	result  = result + ' <div class="modalFooter mt5">';
	result  = result + '   <div class="pull-right">';
	result  = result + '     <button type="button" class="btn btn-primary btn-sm" id="save_comment" data-dismiss="modal" onclick="saveComment();">Add</button>';
	result  = result + '     <button type="button" class="btn btn-default btn-sm" data-dismiss="modal" onclick="closeComment()">Cancel</button>';
	result  = result + '   </div>';
	result  = result + ' </div>';
	result = result + ' </form>';
	$("#commnetPopUpDivId").empty();  
	$("#commnetPopUpDivId").append(result); 
}
</script>
<script>
function saveComment(){
	 if ($('#addComment_form').validate(false, validationSettings)) {
 			var projectTaskId = '${projectTask.id}';
		  	var sourceleadUrl = '${sourceleadUrl}';
		  	var userId = '${SourceLeadWeb_userId}';
		  	var comment = $("#taskComment").val();
		  	$("#save_comment").attr("disabled", "disabled");
			$("#save_comment").addClass('button').val('Processing..');
			$.ajax({
				 type : "POST",
	    	     contentType : 'application/json; charset=utf-8',
				 url : sourceleadUrl + "/ProjectTasks/saveProjectTaskComment?${_csrf.parameterName}=${_csrf.token}&projectTaskId="+projectTaskId+"&userId="+userId+"&comment="+comment,
				  success : function(data) {
					  closeComment();
					  commentTableList();
				}, 
			});
			return true;
		}else {
			return false;
		} 
}
$('body').on('blur', '#addComment_form', function() {
	$("#addComment_form").showHelpOnFocus();
	$("#addComment_form").validateOnBlur(false, validationSettings);
});
</script>

<script>
	function predefineProfileImage(imageId){   
		$(imageId).attr('src','resources/images/userprofile-img.png');
	}
</script>

<script>
function commentTableList(){
	var sourceleadUrl = '${sourceleadUrl}';
	var projectTaskId = "${projectTask.id}";
	$.ajax({
		type : "POST",
		url : sourceleadUrl	+ "/ProjectTasks/getTaskCommentList?${_csrf.parameterName}=${_csrf.token}&projectTaskId="+projectTaskId,
		success : function(json) {
			var result = "";
			if(json != null && json != 'undefined' && json != "" && json != '[]'){
				for (var i = 0; i < json.length; i++) {
					var report = json[i];
					result = result + '	<div class="media clearfix col-md-12">';
					result = result + '   <div class="media-left">';
					result = result  +'	   <img onerror="predefineProfileImage(this);" src="'+sourceleadUrl+'/displayUserImage/'+report.loginUserId+'" class="media-object" style="width:60px"/>';
					result = result + '   </div>';
					result = result + '   <div class="media-body">';
					result = result + '     <h5 class="media-heading text-primary mt10">'+report.createdUserName+'';
					result = result + '         <div class="pull-right small text-muted text-right">'+report.actionTimeStr+'<br/><span class="mt5">'+report.createdStr+' &nbsp;&nbsp;'+report.activityTimezoneStr+'</span></div>';
					result = result + '       </h5>';
					result = result + '       <p>'+report.comment+'</p>';
					result = result + '   </div>';
					result = result + ' </div>';
				}
			}else{
				result = result+"   <div class='dataTables_empty'>"; 
				result = result+"  </div> ";
			}
			$("#taskCommentsListDivId").empty();  
    		$("#taskCommentsListDivId").append(result); 
		}
	});
}
</script>

<!-- ============================ Comments related Script End's ========================================== -->







 <jsp:include page="../masterFooter.jsp" />	