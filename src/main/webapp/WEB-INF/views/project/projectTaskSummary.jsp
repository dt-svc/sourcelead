<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="resources/js/editor.js"></script>
<link href="resources/css/editor.css" type="text/css" rel="stylesheet"/>

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
	      <div class="row">
	          <div class="col-md-9">
	              <h4>${projectTask.taskName}</h4>
	          </div>
	      </div>
	<div class="clearfix">
          <div class="col-md-9">
              <div class="row" data-toggle="collapse" href="#collapse2" onclick="description(${projectTask.id});">
                  
              <div class="col-md-6">
                  <label class="paddingRight">Created On :</label>
                  <span>${createdDate}</span>
              </div>
              <div class="col-md-5">
                  <label class="paddingRight">Due On :</label>
                  <span>${dueDate}</span>
              </div>
                  <div class="col-md-1 text-center">
                  <span class="fa fa-caret-down"></span>
                  </div>
              </div>
              <div id="collapse2" class="row panel-collapse collapse">
                  <div class="col-md-12 clearfix">
                      <strong>Description</strong>
                  </div>
                  <div id="descrptionid"></div>
                 
              </div>
					              <!-- Tabs -->
	       <div class="clearfix mt">
	         <div class="project_side_wrap_inn_tabs">
	          <ul class="project-tabs-menu">
	            <li class="current"><a href="#tab-task1">Time Entry</a></li>
	            <li><a href="#tab-task2">Tasks</a></li>
	            <li><a href="#tab-task3">Documents</a></li>
	            <li><a href="#tab-task4">Activities</a></li>
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
						    <button type="button" class="btn btn-primary btn-sm pull-right mr" onclick="addTimeEntry()"> Add Task </button>
						  </div>
						</div>
						<!-- TimeEntry Modal Pop -->
						<div class="modal-Time clearfix"> 
						  <!-- Modal content-->
						  <div class="clearfix">
						    <button type="button" class="close" onclick="closeTimeEntry()">&times;</button>
						    <p><strong>Task Entry</strong></p>
						  </div>
						  <div class="row clearfix">
						    <div class="col-md-12 mb10">
						      <textarea class="form-control" rows="1" placeholder="Description"></textarea>
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
						            <option selected="">---Status--</option>
						            <option>New</option>
						            <option>In Progress</option>
						            <option>On hold</option>
						            <option>Done</option>
						            <option>Archived</option>
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
						        <th>Description</th>
						        <th>Assign To</th>
						        <th>Status</th>
						      </tr>
						    </thead>
						    <tbody>
						      <tr>
						        <td>
						          <div class="tbldata-dotdot-me">Lorem Ipsum is simply dummy text of the printing and typesetting industry. </div>
						        </td>
						          <td>
						          <div><b class="proj_process">In-Progress</b></div>
						        </td>
						        <td>
						          <div class="tbldata-dotdot-vsm">Murali Krishna</div>
						        </td>
						      </tr>
						    </tbody>
						  </table>
						</div>
		            </div>
	            
				   <div id="tab-task3" class="project_tabs_inn" style="display: none;">
					  <div class="clearfix mt mb mr">
						  <div class="clearfix">
						    <button type="button" class="btn btn-primary btn-sm pull-right" onclick="addTimeEntry()">Add Documents</button>
						  </div>
						</div>
						
						<!-- TimeEntry Modal Pop -->
						<div class="modal-Time clearfix"> 
						  <!-- Modal content-->
						  <div class="clearfix">
						    <button type="button" class="close" onclick="closeTimeEntry()">&times;</button>
						    <p><strong>Documents Details</strong></p>
						  </div>
						  <div class="row clearfix">
						    <div class="col-md-12 mb10">
						      <textarea class="form-control" rows="1" placeholder="Description"></textarea>
						    </div>
						    <div class="col-md-4">
						      <div class="row">
						        <div class="col-md-12">
						          <input type="text" class="form-control input-sm" placeholder="Document Name">
						        </div>
						      </div>
						    </div>
						    <div class="col-md-8">
						      <div class="row">
						        <div class="col-md-12 clearfix">
						                <div class="input-group col-md-10 pull-left">
						                  <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
						                    <input style="display: none;" multiple="" id="uploadBtn1" class="upload" type="file">
						                    </span> </label>
						                  <input class="form-control" readonly="" id="uploadFile1" placeholder="Attachment" type="text">
						                </div>
						                <div class="input-group col-md-2 pull-right text-right">
						                  <button class="btn btn-primary"><i class="fa fa-plus"></i></button>
						                </div>
						        </div>
						      </div>
						    </div>
						  </div>
						  <div class="modalFooter mt mb10">
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
							        <th>Document Name</th>
							        <th>Description</th>
							        <th>Attachment</th>
							      </tr>
							    </thead>
							    <tbody>
							      <tr>
							          <td>
							          <div class="tbldata-dotdot">Maymonth Invoice</div>
							        </td>
							          <td>
							          <div class="tbldata-dotdot-me">Lorem Ipsum is simply dummy text of the printing and typesetting industry. </div>
							        </td>
							        <td style="width: auto;">
							            <div>maymonthinvoice.pdf <i class="paddingLeft fa fa-download" title="Download"></i></div>
							        </td>
							      </tr>
							    </tbody>
						  </table>
						</div>
					  </div>
				  
					<div id="tab-task4" class="project_tabs_inn">
						<div class="clearfix mt mb">
						  <div class="clearfix mr">
						    <button type="button" class="btn btn-primary btn-sm pull-right" onclick="addTimeEntry()">Add Comment</button>
						  </div>
						</div>
						<!-- TimeEntry Modal Pop -->
						<div class="modal-Time clearfix"> 
						  <!-- Modal content-->
						  <div class="clearfix">
						    <button type="button" class="close" onclick="closeTimeEntry()">&times;</button>
						    <p><strong>Add Comment</strong></p>
						  </div>
						  <div class="row clearfix">
						    <div class="col-md-12 mb10">
						      <textarea class="form-control" rows="1" placeholder="Comment here ..."></textarea>
						    </div>
						 </div>
						  <div class="modalFooter mt5">
						    <div class="pull-right">
						      <button type="button" class="btn btn-primary btn-sm" data-dismiss="modal">Add</button>
						      <button type="button" class="btn btn-default btn-sm" data-dismiss="modal" onclick="closeTimeEntry()">Cancel</button>
						    </div>
						  </div>
						</div>
						<!-- TimeEntry Modal Pop Ends -->
						<div class="clearfix project_wrap_table taskComments">
						  <div class="media clearfix col-md-12">
						    <div class="media-left">
						      <img src="/sourcelead/displayUserImage/3.png" class="media-object" style="width:60px">
						    </div>
						    <div class="media-body">
						      <h5 class="media-heading text-primary">
						          By Surendra Kumarsahoo
						          <div class="pull-right small text-muted">One hour ago</div>
						        </h5>
						        <p>New Table Added</p>
						      <p>
						          Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
						      </p>
						    </div>
						  </div>
						    <div class="media clearfix col-md-12">
						    <div class="media-left">
						      <img src="/sourcelead/displayUserImage/3.png" class="media-object" style="width:60px">
						    </div>
						    <div class="media-body">
						      <h5 class="media-heading text-primary">
						          By Surendra Kumarsahoo
						          <div class="pull-right small text-muted">One hour ago</div>
						        </h5>
						        <p>New Table Added</p>
						      <p>
						          Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
						      </p>
						    </div>
						  </div>
						    <div class="media clearfix col-md-12">
						    <div class="media-left">
						      <img src="/sourcelead/displayUserImage/3.png" class="media-object" style="width:60px">
						    </div>
						    <div class="media-body">
						      <h5 class="media-heading text-primary">
						          By Surendra Kumarsahoo
						          <div class="pull-right small text-muted">One hour ago</div>
						        </h5>
						        <p>New Table Added</p>
						      <p>
						          Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
						      </p>
						    </div>
						  </div>
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
		              
		               <div class="row mb">
		                  <label class="col-md-12">Assign To</label>
		              <div class="col-md-12 dropdown-select">
		                  <select class="form-control chosen-select choose" multiple id="assignedUsers" name="assignedUsers" onclick="assignedUsers();">
		                  <c:forEach var="assignUser" items="${assignTask}">
								  <option  selected value="${assignUser}">${assignUser}</option>
		                  </c:forEach>
		                </select>
		              </div>
		              </div>
		              
		          </div>
		      </div>
      </div>
  </section>



<!-- In-Line Edit Fields  Scripts-->
<script> 
function inlineEditTaskStatus(taskId){
	var taskStatusId = $("#taskStatusId").val();
    $("#statusLabel").fadeOut("slow");
    $("#statusSpin").fadeIn("slow");
	var sourceleadUrl = '${sourceleadUrl}';
	  $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/updateTaskStatus?${_csrf.parameterName}=${_csrf.token}&taskStatusId="+taskStatusId+"&taskId="+taskId, 	   
  		    success: function(data) {
  		    	$("#statusLabel").fadeIn("slow");
  		    	$("#statusSpin").fadeOut("slow");
  		    }
	 });  
}
function inlineEditTaskPriority(taskId){
	var taskpriorityId = $("#taskpriorityId").val();
	var sourceleadUrl = '${sourceleadUrl}';
       $("#priorityLabel").fadeOut("slow");
      $("#prioritySpin").fadeIn("slow");
	  $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/updateTaskPriority?${_csrf.parameterName}=${_csrf.token}&taskpriorityId="+taskpriorityId+"&taskId="+taskId, 	   
  		    success: function(data) {
  		    	$("#priorityLabel").fadeIn("slow");
  		    	$("#prioritySpin").fadeOut("slow");
  		    }
	 });  
}
function inlineEditTaskSeverity(taskId){
	var taskSeverityId = $("#taskseverityId").val();
	var sourceleadUrl = '${sourceleadUrl}';
	$("#severityLabel").fadeOut("slow");
    $("#severitySpin").fadeIn("slow");
	  $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/updateTaskSeverity?${_csrf.parameterName}=${_csrf.token}&taskSeverityId="+taskSeverityId+"&taskId="+taskId, 	   
  		    success: function(data) {
  		    	$("#severityLabel").fadeIn("slow");
  		    	$("#severitySpin").fadeOut("slow");
  		    }
	 });  
}
function inlineEditDescription(taskId){
	var desc = $("#txtEditor2").val();
	var sourceleadUrl = '${sourceleadUrl}';
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/updateTaskDescription?${_csrf.parameterName}=${_csrf.token}&description="+desc+"&taskId="+taskId, 	   
		    success: function(data) {
		    }
	 });  
}
</script>

<!-- <script>
//description script
	$(document).ready(function() {
		$("#txtEditor112").Editor();
// 		$("#txtEditor").find(".Editor-editor").data-validation('validate_length length2-5000');
// 		$("#txtEditor").find(".Editor-editor").data-validation-optional('rule-required',true);
	});
 </script>  -->
 
 <script>
 function description(projectTaskId){
	 var sourceleadUrl = '${sourceleadUrl}';
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getProjectTasksDescrption?${_csrf.parameterName}=${_csrf.token}&projectTaskId="+projectTaskId, 	   
		    success: function(json) {
		    	 result = "";
			   	 result  = result + '<div class="col-md-12 mt5 clearfix txtEditor_p">';
			   	result = result + '<textarea id="txtEditor2" name="description" value="'+ json.description +'" class="form-control newContrtextarea" onblur="inlineEditDescription('+projectTaskId+');">' + json.description + '</textarea>';
			   	 result  = result  + '</div>';
			   	 $("#descrptionid").empty();
			     $("#descrptionid").append(result);
// 			     $("#txtEditor2").Editor();
		    }
	 });  
	 
	/*  result = "";
	 result  = result + '<div class="col-md-12 mt5 clearfix txtEditor_p">';
	 result  = result  +'<textarea name="description" id="txtEditor112" value="${projectTask.description}">${projectTask.description}</textarea>';
	 result  = result  + '</div>';
	 $("#descrptionid").empty();
     $("#descrptionid").append(result);
     $("#txtEditor112").Editor(); */

// 	 $("#txtEditor").html("${projectTask.description}");
 }
 </script>
  
<script> 
//project side bar tabs
$(document).ready(function() {
$("#tab-task1").show();
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


 <jsp:include page="../masterFooter.jsp" />	