<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<link type="text/css" rel="stylesheet" href="/resources/css/comment/froala_editor.css"/>
<link type="text/css" rel="stylesheet" href="/resources/css/comment/jquery.atwho.css"/>

<script type="text/javascript" src="/resources/js/editor.js"></script>
<link href="/resources/css/editor.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="/resources/js/comment/jquery.atwho.js"></script>
<script type="text/javascript" src="/resources/js/comment/jquery.caret.min.js"></script>
<script type="text/javascript" src="/resources/js/comment/task_comment.js"></script>


<script>
var pmsUrl ='${SourceLeadWeb_pmsUrl}';
</script>
<script>
	$(document).ready(function(){ 
		var actveComment = '${param.active}';
		if(actveComment == "notificationComment"){
			$("#tab-task5").show();
			$("#tab-task1").hide();
		    $('#timeEntity1').removeClass('current');
		    $('#comment5').addClass('current');
		    $(".project-tabs-menu a").click(function(event) {
		        event.preventDefault();
		        $(this).parent().addClass("current");
		        $(this).parent().siblings().removeClass("current");
		        var tab = $(this).attr("href");
		        $(".project_tabs_inn").not(tab).css("display", "none");
		        $(tab).fadeIn();
		    });
			commentTableList();
		}else{
			$("#tab-task1").show();
		    $("#tab-1").show();
		    $('#comment5').removeClass('current');
		    
		    $(".project-tabs-menu a").click(function(event) {
		        event.preventDefault();
		        $(this).parent().addClass("current");
		        $(this).parent().siblings().removeClass("current");
		        var tab = $(this).attr("href");
		        $(".project_tabs_inn").not(tab).css("display", "none");
		        $(tab).fadeIn();
		    });
		}
		var projectId = "${projectId}";
		tasksAssignList(projectId);
		getprojectTaskMembersByProjectId(projectId);
		getSubTaskList();
		getProjectTaskTagsList();
		maskme();
		var taskType = '${taskStatus}';
		if(taskType == "Bug"){
			getSelectUserStoryByBug();
		}
		if(taskType == "UserStory"){
			getSelectSubEpicsByUserStory();
		}
		priorityColorPic();
		severityColorPic(); 
	    description('${projectTask.id}');  
	    
	});
function priorityColorPic(){
	var colorValue= $('select#taskpriorityId option:selected').attr("colorProp");
	$('#priorityColorPic').css("background-color",colorValue);

}
function severityColorPic(){
	var colorValue= $('select#taskseverityId option:selected').attr("colorProp");
	$('#severityColorPic').css("background-color",colorValue);
}
 </script>
 
 
 <script>
 function getSelectUserStoryByBug(){
	 var projectId = '${projectId}';
	 var taskType = "UserStory";
	 var parentTaskId = '${parentTaskId}';
	 $.ajax({
	    	type: "POST",
	    	 url: pmsUrl+"/ProjectTasks/getProjectTasksByTaskType?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&taskType="+taskType,
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
			     		if(opt.id == parentTaskId){
			     			output = output + "<option "+ (opt.id == parentTaskId ? "selected" : '') +" value='" +opt.id+ "'>"+generatedId+"   "+opt.taskName+"</option>";
			     		}else{
			     			output = output + "<option value='" +opt.id+ "'> "+generatedId+"   "+opt.taskName+"</option>";
			     		}
		     		}
	    				$("#editUserStroyId").empty();
			     		$("#editUserStroyId").append(output);
			     		$('#editUserStroyId').trigger("chosen:updated");
	    		}
	 });
 }
 function getSelectSubEpicsByUserStory(){
	 var projectId =  "${projectId}";
	 var taskType = "Epic";
	 var parentTaskId = '${parentTaskId}';
	 $.ajax({
	    	type: "POST",
	    	 url: pmsUrl+"/ProjectTasks/getProjectTasksByTaskType?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&taskType="+taskType,
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
			     		
			     		if(opt.id == parentTaskId){
			     			output = output + "<option "+ (opt.id == parentTaskId ? "selected" : '') +" value='" +opt.id+ "'>"+generatedId+"   "+opt.taskName+"</option>";
			     		}else{
			     			output = output + "<option value='" +opt.id+ "'> "+generatedId+"   "+opt.taskName+"</option>";
			     		}
		     		}
	    			 
	    			$("#editEepicId").empty();
		     		$("#editEepicId").append(output);
		     		$('#editEepicId').trigger("chosen:updated");
	    		}
	 });
 }
 </script>
 
 
 
 <div id="taskDeletePopUp"></div>
	<section class="content-wraper clearfix">
	
	<section class="content-header clearfix">
		
		<div class="pull-left">
			<h2 class="pull-left paddingRight">${projectName} - Task</h2>
		</div>
		<div class="pull-right brud-crum">
			  <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${SourceLeadWeb_pmsUrl}/projectDashboard">Projects List</a> &raquo; <a href="${SourceLeadWeb_pmsUrl}/projectSummary?projectUUID=${projectUUID}&projectClientUUID=${projectClientUUID}&tabType=tasks">Project Task</a> &raquo; Project Task Summary</div>
		</div>
		
	</section>
	<div class="time_pop" id="projectTaskErrorMsg"></div>
	<div class="taskview_wrapinn">
	      
	<div class="clearfix">
	
			<c:forEach var="section" items="${sourceleadSectionListForProjectTask}" varStatus="sectionOrder">
				 <script>
				 $(document).ready(function() {
					 preapareCustomFieldDetails('${projectTask.id}','${section.id}');					 
				 });
				 </script>
			</c:forEach>
	
          <div class="col-md-9">
	          
	          <div class="bg-success taskheaader-inlineedit mb10 clearfix">
	          <form id="updateTaskName_form" name="updateTaskName_form" method="post">
	          <div class="col-md-11 paddingRight paddingLeft edit-field edit-inputfield">
		          <div class="showTaskname" id="taskedit-parent" onclick="editFun();">
		          <c:if test="${not fn:contains(projectTask.generatedId, 'null') && !empty projectTask.generatedId}">
		             <span class="taskgeneratedid text-muted paddingRight bg-green" title="Task Generated ID">${projectTask.generatedId}</span>
		          </c:if>
		          <span class="paddingLeft" id="projectTaskName">${projectTask.taskName}</span>
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
	          </form>
	          </div>
	          
	          <div class="cearfix">
				<div id="tagClientId"></div>
				  <div class="newTagsWrap">
					    <ul class="clearfix tagsWrap tagsWrapBor">
							<select multiple class="chosen-select form-control" name="tagName" id="projectTaskTagName" data-placeholder="Add Tags" data-validation="validate_Space" data-validation-optional="true">
							</select>
						</ul>
						<div class="clearfix"></div>
				  </div>
			 </div>
			 <div class="success_pop tagStyles" id="addTaskTagAlert">
					<i class="fa fa-check-circle"></i>
			   		<span></span>
			   </div>
			   <div class="remove_pop tagStyles" id="deleteTaskTagAlert">
					<i class="fa fa-times"></i>
			   		<span></span>
			   </div>
          
              <div class="col-md-12 alert-custom hidden" onclick="description(${projectTask.id});">
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
                  <%-- <label class="paddingRight">Due On:</label>
                  <span>${dueDate}</span> --%>
              </div>
                  <div class="col-md-1 text-center">
                  <span class="fa fa-caret-down btn btn-sm btn-primary"></span>
                  </div>
                  </div>
              </div>
              <div class="clearfix alert-custom">
              <div class="alert alert-info clearfix mb0">
                  <div class="col-md-2 clearfix">
                      <strong>Description</strong>
                  </div>
                  <div class="col-md-3 textBold">
                  <label class="paddingRight">Created On:</label>
                  <span>${createdDate}</span>
              </div>
              <div class="col-md-7 textBold">
                  <label class="paddingRight pull-left">Created By:</label>
                  <span class="tbldata-dotdot pull-left" title="${createdBy}">${createdBy}</span>
              </div>
              </div>    
                 
                 <div class="row clearfix"> 
                  <div id="descrptionid"></div>
                  </div>
                 
              </div>
              <div class="clearfix"></div>
					              <!-- Tabs -->
	       <div class="clearfix">
	         <div class="project_side_wrap_inn_tabs">
	          <ul class="project-tabs-menu">
 	            <li id= "timeEntity1" class="current"><a href="#tab-task1">Time Entry</a></li>
		            <c:if test="${taskTypeName != 'SubTask'}">
		            	<li><a href="#tab-task2" onclick="getSubTaskList();">Sub Tasks</a></li>
		            </c:if>
	            <li><a href="#tab-task3" onclick="closeTimeEntry();documentTableList();">Documents</a></li>
	            <li><a href="#tab-task4" onclick="getTaskActivities();">Activities</a></li>
                 <sec:authorize access="hasAnyRole('ROLE_ADD_TASK_COMMENTS','ROLE_VIEW_EDIT_TASK_COMMENTS')">
	                <li id="comment5" class="current"><a href="#tab-task5" onclick="commentTableList();">Comments</a></li>
	             </sec:authorize>
	            <div class="clearfix"></div>
	          </ul>
					          
			<div class="project_tabs"> 
					<div id="tab-task1" class="project_tabs_inn" style="display: none;">
						<jsp:include page="projectTaskEntries.jsp"></jsp:include>
					</div>
					<div id="tab-task2" class="project_tabs_inn" style="display: none;">
						 <div class="clearfix mt10 mb btn-paddingright">
						   <sec:authorize access="hasRole('ROLE_ADD_SUB_TASK')">
						     <button type="button" class="btn btn-primary btn-sm pull-right" onclick="addSubTasks();showtasktype();"> Add Task </button>
						    </sec:authorize>
						</div>

						<div class="clearfix project_wrap_table datatable_filter">
						  <div id="subTaskListdivId"></div>
						</div>
		            </div>
	            
				   <div id="tab-task3" class="project_tabs_inn" style="display: none;">
					  <div class="clearfix mt10 mb btn-paddingright">
						   <sec:authorize access="hasRole('ROLE_ADD_DOCUMENTS')">
						    <button type="button" class="btn btn-primary btn-sm pull-right" onclick="addDocumentEntryPopUp();">Add Documents</button>
						    </sec:authorize>
						</div>
						
						<!-- Document Modal Pop -->
						<div class="modal-Time document clearfix" style="margin-bottom: 45px;"> 
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
						  <sec:authorize access="hasRole('ROLE_ADD_TASK_COMMENTS')">
						    <button type="button" class="btn btn-primary btn-sm pull-right" onclick="addCommentsPopUp()">Add Comment</button>
						  </sec:authorize>
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
		          
		          <c:if test="${taskTypeName == 'SubTask'}">
		          <c:if test="${not empty parentGeneratedId}">
		          <div class="row dropdown-select mb10">
			              <label class="col-md-4">Parent Task Id :</label> 
			              <div class="col-md-8 paddingLefet textBold">
			              <a href="/pms/projectTaskSummary?taskUUID=${parentProjectTaskUUID}&projectClientUUID=${projectClientUUID}" title="${parentGeneratedId}">${parentGeneratedId}</a>
			              </div>
			              </div>
		          </c:if>
		          </c:if>
		                 
			              
		           <div class="row dropdown-select mb">
		              <label class="col-md-12">Task Type </label>
		              <div class="col-md-12 dropdown-select">
		              <input type="text" value="${projectTask.taskTypes}" disabled="disabled" class="form-control input-sm">
		                <%-- <select class="form-control" onchange="inlineEditTaskType(${projectTask.id});" id="taskTypeId" disabled="disabled">
		                   <c:forEach var="type" items="${taskTypeList}">         
		                    <option value="${type.id}" ${type.id  == projectTask.taskTypes.id ? 'selected' : ''} >${type.name}</option>                                                                                                     
							</c:forEach> 
		                </select> --%>
		              </div>
		              </div>
			              
			          <c:if test="${taskStatus == 'UserStory'}">
			          <div class="row mb">
		                  <label class="col-md-12">Epic</label>
		              <div class="col-md-12 dropdown-select">
		              <select class="chosen-select form-control" id="editEepicId" onchange="inlineEditEpic(${projectTask.id});"></select>
		              
		              </div>
		              </div>
			          </c:if>
			          
			          <c:if test="${taskStatus == 'Bug'}">
			          <div class="row mb">
		                  <label class="col-md-12">UserStory</label>
		              <div class="col-md-12 dropdown-select">
		                   <select class="chosen-select form-control" id="editUserStroyId"  onchange="inlineEditUserStory(${projectTask.id});"></select>
		              </div>
		              </div>
			          </c:if>
		             
		              
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
		              <small class="colorBox colorPosition" id="priorityColorPic"></small>
		                <select style="margin-left:20px;" class="pull-left" onchange="inlineEditTaskPriority(${projectTask.id});priorityColorPic();" id="taskpriorityId">
		                  <option selected value="">--Select Priority--</option>
		                  <c:forEach var="priority" items="${taskPriorityList}">  
		                      <option  colorProp="${priority.colorPic}"	value="${priority.id}" ${priority.id  == projectTask.priority.id ? 'selected' : ''} >${priority.name}</option>                                                                                                      
							</c:forEach>
		                </select>
		                <span class="pull-right">
						 	<small class="status" id="priorityLabel" style="display:block;">Priority</small>
						 	<i class="fa fa-spinner fa-spin faSpin " id="prioritySpin" style="display:none;"></i>
						 </span>  
		            </div>
		              
		              
		              <div class="clearfix  mb select-loader">
		              <small class="colorBox colorPosition" id="severityColorPic"></small>
		                <select style="margin-left:20px;" class="pull-left" onchange="inlineEditTaskSeverity(${projectTask.id});severityColorPic();" id="taskseverityId">
		                  <option selected value="">--Select Severity--</option>
		                   <c:forEach var="severity" items="${taskSeverityList}">     
		                      <option colorProp="${severity.colorPic}" value="${severity.id}" ${severity.id  == projectTask.severity.id ? 'selected' : ''} >${severity.name}</option>                                                                                                
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
						 	<small class="status" id="percentageLabel" style="display:block;">Completed</small>
						 	<i class="fa fa-spinner fa-spin faSpin " id="percentageSpin" style="display:none;"></i>
						 </span> 
		            </div>
		            
		            <div class="clearfix  mb select-loader">
		                <select class="pull-left" onchange="inlineEditBillingType(${projectTask.id});" id="billingId">
		                <option value="Billable" ${projectTask.billableType  == 'Billable' ? 'selected' : ''} >Billable</option>  
		                <option value="Non-Billable" ${projectTask.billableType  == 'Non-Billable' ? 'selected' : ''} >Non-Billable</option>  
		                </select>
		                <span class="pull-right">
						 	<small class="status" id="billingLabel" style="display:block;">Billing</small>
						 	<i class="fa fa-spinner fa-spin faSpin " id="billingSpin" style="display:none;"></i>
						 </span>  
		            </div>
		            
		             <div class="row mb">
		                  <label class="col-md-12">Due Date </label>
		              <div class="col-md-12">
		              <div class="input-group">
		                   <input class="form-control calendar" value="${dueDate}" name="editDueDate" id="editDueDate" placeholder="Due Date" type="text" onchange="inlineEditDueDate(${projectTask.id});" readonly>
		                   <span class="input-group-addon" onClick="clearEditDueDate();inlineEditDueDate(${projectTask.id});"><i class="colorRed fa fa-times cursorPointer"></i></span>
		                   </div>
		              </div>
		              </div>
		              
		              <div class="row mb">
		                  <label class="col-md-12">Estimated Time</label>
		              <div class="col-md-12 dropdown-select">
		                   <input class="form-control"  placeholder="Estimated Time" type="text" value="${projectTask.estimateTime}" id="editEstimatedTime" size="8" maxlength="8" onchange="inlineEditEstimatedTime(${projectTask.id});" onkeypress="return isNumberKey(event)"> 
		              </div>
		              </div>
		              
		              <div class="row mb">
		                  <label class="col-md-12">Owner</label>
		              <div class="col-md-12 dropdown-select">
		                   <select class="form-control" data-placeholder="Select Owner" id="editOwnerId" onchange="inlineEditOwner(${projectTask.id});"></select> 
		              </div>
		              </div>
		              
		               <div class="row mb">
		                  <label class="col-md-12">Assign To </label>
		              <div class="col-md-12 dropdown-select">
		                   <select class="form-control chosen-select choose" multiple id="assignedUsers" name="assignedUsers"></select> 
		              </div>
		              </div>
			              <form name="projectTaskCustomForm" id="projectTaskCustomForm">
			             	<div id="projectTaskCustomId" onClick="inlinesaveProjectTaskCustomFieldDetails();" class="formGroup100"></div>
			             </form>
		          </div>
		      </div>
      </div>
  </section>


<script>
function inlinesaveProjectTaskCustomFieldDetails(){
		var projectTaskId = '${projectTask.id}';
	  /*  if ($('#projectTaskCustomForm').validate(false, validationSettings)) { */
		var formData = getFormData("projectTaskCustomForm");
		$.ajax({
			type : "POST",
			 data: formData,
			 url : pmsUrl+"/ProjectTasks/saveProjectTaskCustomFieldDetails?&id="+projectTaskId,
			  success : function(data) {
			}, 
		});
		return true;
	/* } */
}
</script>


<script>
function preapareCustomFieldDetails(projecttaskId,sectionId){
	var sourcleadUrl = '${SourceLeadWeb_sourceleadUrl}';
	 $.ajax({
        type : "POST",
         url: sourcleadUrl+"/getAllASectionCustomFields?&projectTask="+projecttaskId+"&requestFrom=projectTask"+"&sectionId="+sectionId,        
         success :function(data) {
        	 $('#projectTaskCustomId').empty();
        	 $('#projectTaskCustomId').append(data);
           },
     }); 
}
</script>

<script>
	var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
     }; 
</script>
<script>
$(function() {
	var locationDate ='${SourceLeadWeb_dateFormat}'; 
	var projectStartDate = '${projectStartDate}';
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

	$('#editDueDate').datepicker({
		dateFormat : finalDateFormatProjectSave,
		changeMonth: true,
		changeYear: true, 
		minDate : projectStartDate,
		 
	});

	$('#editOwnerId').chosen('refresh');
	$('#editOwnerId').trigger("chosen:updated");
	getOwnerList();
});
function clearEditDueDate(){
	$("#editDueDate").val("");
	};
</script>
<script>
function getprojectTaskMembersByProjectId(projectId){
	var taskUUID = '${taskUUID}';
	if(projectId != null && projectId !='' && projectId !='undefined'){
		 //$("#projectTaskDiv").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
		   	type: "POST",
		    url: pmsUrl+"/ProjectTasks/getprojectTaskMembersByProjectId?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&taskUUID="+taskUUID, 	   
		    success: function(data) {
		    	$('#tab-task1').empty();
		    	$('#tab-task1').append(data);
		    	$("#projectTaskDiv").removeAttr('style');
		    	}
	 		});
	}
}

</script>

<script>
$('.chosen-select').chosen().on('change', function() {
	var assignValue = $("#assignedUsers").val();
	if(assignValue != null){
		assignValue = assignValue;
	}else{
		assignValue = "";
	}
	var projectClientUUID = '${projectClientUUID}';
	var taskId = '${projectTask.id}';
	var userId = '${SourceLeadWeb_userId}';
	var projectId =  "${projectId}";
	  $.ajax({
		   	type: "POST",
		    url: pmsUrl+"/ProjectTasks/updateAssignTaskUsers?${_csrf.parameterName}=${_csrf.token}&assignValue="+assignValue+"&taskId="+taskId+"&userId="+userId+"&projectClientUUID="+projectClientUUID+"&projectId="+projectId, 	   
	 		    success: function(data) {
		    }
	 });  
});
</script>

<script>
function tasksAssignList(projectId){
    $("#assignedUsers").trigger("chosen:updated");
	var values = "Active";
// 	  var map = new Object(); 
	  var assignedUsers = [];
      <c:forEach var="assign" items="${assignedUsers}">
      	assignedUsers.push('${assign.projectMemberId.id}');
      </c:forEach>
   
	$.ajax({
    	type: "GET",
        url: pmsUrl+"/getProjectEmployeeListById?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&status="+values, 
        success: function(json) {
        	var output = "";
        	for (var i=0; i<json.length; i++)
			{
        		var opt = json[i];
        		if(assignedUsers.length > 0){
        			var selected = "";
        			 for(var j=0; j<assignedUsers.length; j++) {
                         if(opt.id == assignedUsers[j]){
                        	 selected = "selected";
                        	 break;
                         }else{
                        	 selected = "";
                         }
                       }
        			 output = output +  '<option value="'+opt.id+'" '+selected+'> '+opt.empName+'</option>';
        		}else{
        			output = output + "<option value='" +opt.id+ "'>"+opt.empName+"</option>";
        		}
			}
        	$("#assignedUsers").empty();
     		$("#assignedUsers").append(output);
     		$("#assignedUsers").chosen();
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
	var userId = '${SourceLeadWeb_userId}';
	var hiddenTaskName = $("#hiddenTaskName").val();
	if(taskName.length > 250){
		$(".inputError").show();
	}else{
		if(taskName.match(/^[^\s-](.*)*$/) !== null){
			 var formData = getFormData("updateTaskName_form");
			$.ajax({
			   	type: "POST",
			   	data: formData,
			    url: pmsUrl+"/ProjectTasks/updateTaskName?${_csrf.parameterName}=${_csrf.token}&taskId="+projectTaskId+"&userId="+userId, 	   
			    success: function(data) {
			    	$(".showTaskname").show();
			    	$(".editTaskname").hide();
			    	document.getElementById("saveIcon").style.display="none";
			    	$("#hiddenTaskName").val(data);
			    	$("#projectTaskName").text(data);
			    	$(".inputError").hide();
			    	$("#temptxt").text(data);
			    }
		 }); 
		}else{
			$(".showTaskname").show();
	    	$(".editTaskname").hide();
	    	document.getElementById("saveIcon").style.display="none";
			$("#taskName").val(hiddenTaskName);
			$("#projectTaskName").val(hiddenTaskName);
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
	var assignValue = $("#assignedUsers").val();
	var editOwnerId = $("#editOwnerId").val();
	if(assignValue != null){
		assignValue = assignValue;
	}else{
		assignValue = "";
	}
	if(editOwnerId != null){
		editOwnerId = editOwnerId;
	}else{
		editOwnerId = "";
	}
	var projectClientUUID = '${projectClientUUID}';
	var taskStatusId = $("#taskStatusId").val();
    $("#statusLabel").fadeOut("slow");
    $("#statusSpin").fadeIn("slow");
	var userId = '${SourceLeadWeb_userId}';
	  $.ajax({
		   	type: "POST",
		    url: pmsUrl+"/ProjectTasks/updateTaskStatus?${_csrf.parameterName}=${_csrf.token}&taskStatusId="+taskStatusId+"&taskId="+taskId+"&userId="+userId+"&projectClientUUID="+projectClientUUID+"&editOwnerId="+editOwnerId+"&assignValue="+assignValue, 	   
  		    success: function(data) {
  		    	$("#statusLabel").fadeIn("slow");
  		    	$("#statusSpin").fadeOut("slow");
  		    	getTaskActivities();
  		    }
	 });  
}
function inlineEditTaskPriority(taskId){
	var assignValue = $("#assignedUsers").val();
	var editOwnerId = $("#editOwnerId").val();
	if(assignValue != null){
		assignValue = assignValue;
	}else{
		assignValue = "";
	}
	if(editOwnerId != null){
		editOwnerId = editOwnerId;
	}else{
		editOwnerId = "";
	}
	var projectClientUUID = '${projectClientUUID}';
	var taskpriorityId = $("#taskpriorityId").val();
	var userId = '${SourceLeadWeb_userId}';
       $("#priorityLabel").fadeOut("slow");
      $("#prioritySpin").fadeIn("slow");
	  $.ajax({
		   	type: "POST",
		    url: pmsUrl+"/ProjectTasks/updateTaskPriority?${_csrf.parameterName}=${_csrf.token}&taskpriorityId="+taskpriorityId+"&taskId="+taskId+"&userId="+userId+"&projectClientUUID="+projectClientUUID+"&editOwnerId="+editOwnerId+"&assignValue="+assignValue,  	   
  		    success: function(data) {
  		    	$("#priorityLabel").fadeIn("slow");
  		    	$("#prioritySpin").fadeOut("slow");
  		    	getTaskActivities();
  		    }
	 });  
}
function inlineEditTaskSeverity(taskId){
	var assignValue = $("#assignedUsers").val();
	var editOwnerId = $("#editOwnerId").val();
	if(assignValue != null){
		assignValue = assignValue;
	}else{
		assignValue = "";
	}
	if(editOwnerId != null){
		editOwnerId = editOwnerId;
	}else{
		editOwnerId = "";
	}
	var projectClientUUID = '${projectClientUUID}';
	
	var taskSeverityId = $("#taskseverityId").val();
	var userId = '${SourceLeadWeb_userId}';
	$("#severityLabel").fadeOut("slow");
    $("#severitySpin").fadeIn("slow");
	  $.ajax({
		   	type: "POST",
		    url: pmsUrl+"/ProjectTasks/updateTaskSeverity?${_csrf.parameterName}=${_csrf.token}&taskSeverityId="+taskSeverityId+"&taskId="+taskId+"&userId="+userId+"&projectClientUUID="+projectClientUUID+"&editOwnerId="+editOwnerId+"&assignValue="+assignValue, 	   
  		    success: function(data) {
  		    	$("#severityLabel").fadeIn("slow");
  		    	$("#severitySpin").fadeOut("slow");
  		    	getTaskActivities();
  		    }
	 });  
}

function inlineEditDescription(taskId){
	var ee = $("#txtEditor2").val($('.Editor-editor').html()); 
	 if ($('#taskdescription_form').validate(false, validationSettings)) {
		var formData = getFormData1("taskdescription_form");
		var userId = '${SourceLeadWeb_userId}';
		$.ajax({
			 type : "POST",
			 data: formData,
	         processData: false,
	         contentType:false, 
	         url : pmsUrl+"/ProjectTasks/updateTaskDescription?${_csrf.parameterName}=${_csrf.token}&id="+taskId+"&loginUserId="+userId,
			  success : function(data) {
				   $("#jobAlert2").removeAttr("style");
	    			$("#jobAlert2").css({ display: "block" });
					$("#editJobAlertMsgDiv").html("Description Updated Successfully!!");
						editAlertMsgFadeinFunction();
						getTaskActivities(); 
			}, 
		});
		return true;
	}else {
		return false;
	} 
	
}
function inlineEditTaskPercentage(taskId){
	var percentageId = $("#taskPercentageId").val();
	var userId = '${SourceLeadWeb_userId}';
	$("#percentageLabel").fadeOut("slow");
    $("#percentageSpin").fadeIn("slow");
	 $.ajax({
		   	type: "POST",
		    url: pmsUrl+"/ProjectTasks/updateTaskPercentage?${_csrf.parameterName}=${_csrf.token}&percentage="+percentageId+"&taskId="+taskId+"&userId="+userId,    
		    success: function(data) {
		    	$("#percentageLabel").fadeIn("slow");
  		    	$("#percentageSpin").fadeOut("slow");
  		    	getTaskActivities();
		    }
	 }); 
}
function inlineEditBillingType(taskId){
	var billingId = $("#billingId").val();
	var userId = '${SourceLeadWeb_userId}';
	$("#billingLabel").fadeOut("slow");
    $("#billingSpin").fadeIn("slow");
	 $.ajax({
		   	type: "POST",
		    url: pmsUrl+"/ProjectTasks/updateBillingType?${_csrf.parameterName}=${_csrf.token}&billingId="+billingId+"&taskId="+taskId+"&userId="+userId,    
		    success: function(data) {
		    	$("#billingLabel").fadeIn("slow");
  		    	$("#billingSpin").fadeOut("slow");
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
	 $("#descrptionid").empty();
	 $.ajax({
		   	type: "POST",
		    url: pmsUrl+"/ProjectTasks/getProjectTasksDescrption?${_csrf.parameterName}=${_csrf.token}&projectTaskId="+projectTaskId, 	   
		    success: function(json) {
		    	 result = "";
		    	 result = result +' <div class="success_pop" id="jobAlert2" style="display: none;">';
				 result = result +'  <i class="fa fa-check-circle"></i><span id="editJobAlertMsgDiv"></span>';
				 result = result +' </div> ';
				 result  = result + '<form id="taskdescription_form" name="taskdescription_form" method="post">';
			   	 result  = result + '<div class="col-md-12 clearfix txtEditor_p saveBtn">';
			   	 result = result + '<textarea id="txtEditor2" name="taskDescription" value="'+ json.description +'" class="form-control newContrtextarea">' + json.description + '</textarea>';
			   	 result  = result  + '<div class="mt5 clearfix"><button type"button" class="pull-right btn btn-sm btn-warning save-btn" onclick="inlineEditDescription('+projectTaskId+');">Save</button></span>';
			   	 result  = result  + '</div>';
			   	 result  = result + '</form>';
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
        function closeTimeEntryTasks(){
            $(".modal-Time").fadeOut();
            $('#time_entry_form').find('p.jquery_form_error_message').remove();
            $('#timesheetDate').val('');
        	$('#timesheetComments').val('');
        	$('#timesheetHours').val('');
        	$("#projectTaskErrorMsgDiv").find('p.jquery_form_error_message').remove();
        	$('#savetask').removeAttr('disabled','disabled');
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


<script>
function inlineEditDueDate(taskId){
	var assignValue = $("#assignedUsers").val();
	var editOwnerId = $("#editOwnerId").val();
	if(assignValue != null){
		assignValue = assignValue;
	}else{
		assignValue = "";
	}
	if(editOwnerId != null){
		editOwnerId = editOwnerId;
	}else{
		editOwnerId = "";
	}
	var projectClientUUID = '${projectClientUUID}';
	var userId = '${SourceLeadWeb_userId}';
	var editDueDate = $("#editDueDate").val();
	$.ajax({
	   	type: "POST",
	    url: pmsUrl+"/ProjectTasks/updateDueDate?${_csrf.parameterName}=${_csrf.token}&editDueDate="+editDueDate+"&taskId="+taskId+"&userId="+userId+"&projectClientUUID="+projectClientUUID+"&editOwnerId="+editOwnerId+"&assignValue="+assignValue,    
	    success: function(data) {
		    	getTaskActivities();
	    }
 }); 
}
function inlineEditEstimatedTime(taskId){
	var assignValue = $("#assignedUsers").val();
	var editOwnerId = $("#editOwnerId").val();
	if(assignValue != null){
		assignValue = assignValue;
	}else{
		assignValue = "";
	}
	if(editOwnerId != null){
		editOwnerId = editOwnerId;
	}else{
		editOwnerId = "";
	}
	var projectClientUUID = '${projectClientUUID}';
	var userId = '${SourceLeadWeb_userId}';
	var editEstimatedTime = $("#editEstimatedTime").val();
	$.ajax({
	   	type: "POST",
	    url: pmsUrl+"/ProjectTasks/updateEstimatedTime?${_csrf.parameterName}=${_csrf.token}&estimatedTime="+editEstimatedTime+"&taskId="+taskId+"&userId="+userId+"&projectClientUUID="+projectClientUUID+"&editOwnerId="+editOwnerId+"&assignValue="+assignValue,
	    success: function(data) {
		    	getTaskActivities();
	    }
 }); 
}
function isNumberKey(evt)
{
   var charCode = (evt.which) ? evt.which : event.keyCode
   if (charCode > 31 && (charCode < 48 || charCode > 57))
      return false;

   return true;
}
function inlineEditOwner(taskId){
	var userId = '${SourceLeadWeb_userId}';
	var editOwnerId = $("#editOwnerId").val();
	$.ajax({
	   	type: "POST",
	    url: pmsUrl+"/ProjectTasks/updateTaskOwner?${_csrf.parameterName}=${_csrf.token}&ownerId="+editOwnerId+"&taskId="+taskId+"&userId="+userId,    
	    success: function(data) {
		    	getTaskActivities();
	    }
 });
}

function inlineEditEpic(taskId){
	var userId = '${SourceLeadWeb_userId}';
	var editEepicId = $("#editEepicId").val();
	$.ajax({
	   	type: "POST",
	    url: pmsUrl+"/ProjectTasks/updateEpic?${_csrf.parameterName}=${_csrf.token}&epicId="+editEepicId+"&taskId="+taskId+"&userId="+userId,    
	    success: function(data) {
		    	getTaskActivities();
	    }
 });
}

function inlineEditUserStory(taskId){
	var userId = '${SourceLeadWeb_userId}';
	var editUserStroyId = $("#editUserStroyId").val();
	$.ajax({
	   	type: "POST",
	    url: pmsUrl+"/ProjectTasks/updateUserStroy?${_csrf.parameterName}=${_csrf.token}&userStoryId="+editUserStroyId+"&taskId="+taskId+"&userId="+userId,    
	    success: function(data) {
		    	getTaskActivities();
	    }
 });
}

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
		result = result + ' <input type="hidden" name="tabType" value="attachment">';
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
		result  = result + '	                <div class="input-group positionRelative errorInptgroup" id="uploadFileGroup">';
		result  = result + '	                  <label class="input-group-btn"> <span class="btn btn-primary" onchange="taskUploadFile();"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File <input type="file" id="uploadBtn" style="display: none;"  class="upload" >';
		result  = result + '	                    </span> </label>';
		result  = result + '	                  <input class="form-control" readonly id="uploadFile" name="docFile"  placeholder="Attachment" type="text" field-name="Document" data-validation="required validate_docimgpdf">';
		result  = result + '	                </div>';
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
	fileUpload("uploadBtn", "tasks", "addDocument_form");
}

function documentTableList(){
	var projectTaskId = ${projectTask.id}
	$.ajax({
		type : "POST",
		url : pmsUrl+ "/ProjectTasks/getProjectTasksDocuments?${_csrf.parameterName}=${_csrf.token}&projectTaskId="+projectTaskId,
		success : function(data) {
			 if (data && data.response === "INVALID_DATA") {
                 showError(result.responseMessage);
             } else if (data.response === "AWKWARD") {
                 location.href = "/errorPage";
             } else if (data.response === "SUCCESS") {
            	 var json = data.data;
			    result = "";
			    result  = result + '	<div class="clearfix project_wrap_table datatable_filter">';
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
	   		 	    result  = result + '               <td><div class="tblminwidth-50"><a href="javascript:void(0)" onclick="viewFile(\''+report.fileURL+'\',\''+report.fileName+'\', \''+report.fileType+'\');" class="fa fa-eye ml10 paddingRight" title="view"></a><a class="paddingLeft glyphicon glyphicon-download-alt" href=\"'+report.fileURL+'\" title="Download" download></a></div></td>';
	   		 	    result  = result + '               <td><div>';
	   		 	    '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_DOCUMENTS\')">';
	   		 	    result  = result + '    <a class="glyphicon glyphicon-pencil padddigRight" title="Edit" onclick="getEditTaskDocument('+report.id+');"></a>';
	   		 	    '</sec:authorize>'; 
	   		 	    '<sec:authorize access="hasAnyRole(\'ROLE_DELETE_TASK_DOCUMENTS\')">';
	   		 	     result  = result + ' <i title="Delete" class="glyphicon glyphicon-trash paddingLeft" onclick="removeProjectTaskDocuments('+report.id+');"></i>';
	   		 	    '</sec:authorize>'; 
	   		 	    
	   		 	    '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_EDIT_DOCUMENTS\',\'ROLE_DELETE_TASK_DOCUMENTS\')">';
	   		 	       result  = result + '----';
	   		 	     '</sec:authorize>';
	   		 	    result  = result + '  </div></td>';
	   		 		result  = result + '		      </tr>';
   				}
   		    	result  = result + '    </tbody>';
				result  = result + '   </table>';
				result  = result + ' </div>';
				$("#documentTableId").empty();
		        $("#documentTableId").append(result);
		        taskDocumentPagination();
             }else if (result.indexOf('Sign In') !== -1) {
					location.replace(pmsUrl);
				} else {
                 showError("Operation failed, please try again");
             }
		}
	});
		
}
</script>
<script>
function getEditTaskDocument(projectTaskDocId){
	
	 $.ajax({
		   	type: "POST",
		    url: pmsUrl+"/ProjectTasks/getProjectTaskDocument?${_csrf.parameterName}=${_csrf.token}&projectTaskDocId="+projectTaskDocId, 	   
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
					result = result + ' <input type="hidden" name="tabType" value="attachment">';
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
					result  = result + '	                <div class="input-group positionRelative errorInptgroup" id="uploadEditFileGroup">';
					result  = result + '	                  <label class="input-group-btn"> <span class="btn btn-primary" onchange="editUploadFile();"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File <input type="file"  id="uploadBtn" style="display: none;"   class="upload">';
					result  = result + '	                    </span> </label>';
					result  = result + '	                  <input class="form-control" readonly id="uploadFile" name="docFile"  placeholder="Attachment" type="text" field-name="Document" >';
					result  = result + '	                </div>';
					result  = result + '	        </div>';
					result  = result + '	      </div>';
					result  = result + '	    </div>';
					
					result = result+'<div class="col-md-7" id="docNameDiv">';
		   			result = result+'	  <div class="col-md-12 clearfix form-control">';
		   			result = result + '	      <div class="input-group col-md-12 pull-left">';
		   			result = result + '	      <input type="hidden" id="validDcoument" name="validDcoument" value="true">';
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
				      $("#uploadEditFileGroup").find('p.jquery_form_error_message').remove();
				      if(docSize > 2097152){
				    	  document.getElementById("uploadFile").value = '';
				    	  $("#uploadEditFileGroup").append("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </p>");
				      }
				    });
				    
				    
			    }
	 });
	 
}
</script>
<script>
function editUploadFile(){
	fileUpload("uploadBtn", "tasks", "updateDocument_form");
}
</script>
<script>
function removeDocNameDiv(){
	$("#validDcoument").val('');
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
		  	var userId = '${SourceLeadWeb_userId}';
		  	$("#save_document").attr("disabled", "disabled");
			$("#save_document").addClass('button').val('Processing..');
			$.ajax({
				 type : "POST",
				 data: formData,
		         processData: false,
		         contentType:false, 
				 url : pmsUrl + "/ProjectTasks/saveProjectTaskDocuments?${_csrf.parameterName}=${_csrf.token}&projectTaskId="+projectTaskId+"&loginUserId="+userId,
				  success : function(result) {
					  if (result && result.response === "INVALID_DATA") {
		                    showError(result.responseMessage);
		                } else if (result.response === "AWKWARD") {
		                    location.href = "/errorPage";
		                } else if (result.response === "SUCCESS") {
		                	  closeTimeEntry();
							  documentTableList();
		                }else if (result.indexOf('Sign In') !== -1) {
							location.replace(pmsUrl);
						} else {
		                    showError("Operation failed, please try again");
		                }
					
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
		var projectTaskId = '${projectTask.id}';
	  	var userId = '${SourceLeadWeb_userId}';
	  	$("#save_document").attr("disabled", "disabled");
		$("#save_document").addClass('button').val('Processing..');
		var formData = getFormData1("updateDocument_form");
		$.ajax({
			 type : "POST",
			 data: formData,
	         processData: false,
	         contentType:false, 
   	         dataType : 'json',
			 url : pmsUrl + "/ProjectTasks/saveProjectTaskDocuments?${_csrf.parameterName}=${_csrf.token}&projectTaskId="+projectTaskId+"&documentId="+documentId+"&loginUserId="+userId,
			  success : function(result) {
				  if (result && result.response === "INVALID_DATA") {
	                    showError(result.responseMessage);
	                } else if (result.response === "AWKWARD") {
	                    location.href = "/errorPage";
	                } else if (result.response === "SUCCESS") {
	                	closeTimeEntry();
	  				  documentTableList();
	                }else if (result.indexOf('Sign In') !== -1) {
						location.replace(pmsUrl);
					} else {
	                    showError("Operation failed, please try again");
	                }
				  
				  
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
	 
		if(documentId != null && documentId != ""){
			window.open(pmsUrl+'/ProjectTasks/viewProjectTaskDocument/'+title+'?documentId='+documentId,title,"height=600,width=600");
		}
}
</script>
<script>
function removeProjectTaskDocuments(documentId){
	
	var userId = '${SourceLeadWeb_userId}';
	$.ajax({
	   	type: "POST",
	    url: pmsUrl+"/ProjectTasks/deleteProjectTaskDocument?${_csrf.parameterName}=${_csrf.token}&porjectTaskDocumentId="+documentId+"&userId="+userId, 	   
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
	 <c:forEach var="section" items="${sourceleadSectionListForProjectTask}" varStatus="sectionOrder">
	  var sectionId = '${section.id}';
	  getCustomDependentFieldProjectSubTask(sectionId);
  </c:forEach> 
			var projectId =  "${projectId}";
			var projectStartDate = '${projectStartDate}';
			var result = "";
			result = result	+ ' 	<div class="tasks_wrapinn">';
			result = result	+ '     <h4 class="modal-title">Add Task</h4>';
			result = result	+ '     <div class="row clearfix">';
			result = result	+ '     <form name="projectTask_form" id="projectTask_form" method="post">';
			result = result	+ '       <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">';
			result = result	+ '         <div class="form-group">';
			result = result	+ '           <label class="col-md-3">Task Type<span class="text-danger paddingLeft">*</span></label>';
			result = result	+ '           <div class="col-md-9 dropdown-select">';
		    result = result	+ '             <select class="form-control" onchange="showtasktype()" id="taskstype" name="taskstype" field-name="Task Type"  data-validation="required">'; 
		    result = result	+ '         <c:forEach var="type" items="${taskTypeList}">  ';      
		    result = result	+ '            <option value="${type.uniqueKey}" ${type.uniqueKey  == "SubTask" ? "selected" : ''} >${type.name}</option>'; 
		    result = result	+ '  		</c:forEach>  ';                                           
			result = result	+ '             </select>'; 
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			
			result = result	+ '           <div class="form-group" style="display: block" id="epic">';
			result = result	+ '           <label class="col-md-3">Epic</label>';
			result = result	+ '           <div class="col-md-9 dropdown-select">';
			result = result	+ '             <select class="chosen-select form-control" id="epicId" name="epic">';
			result = result	+ '             </select>';
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			
			result = result	+ '           <div class="form-group" style="display: none" id="userStory">';
			result = result	+ '           <label class="col-md-3">User Story</label>';
			result = result	+ '           <div class="col-md-9 dropdown-select">';
			result = result	+ '             <select class="chosen-select form-control" id="userStroyId" name="userStory">';
			result = result	+ '             </select>';
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			
			result = result	+ '           <div class="form-group" style="display: none" id="task">';
			result = result	+ '           <label class="col-md-3">Task<span class="text-danger paddingLeft">*</span></label>';
			result = result	+ '           <div class="col-md-9 dropdown-select">';
			result = result	+ '             <select class="chosen-select form-control" id="subTasksId" name="subTask" field-name="Task">';
			result = result	+ '             </select>';
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			
			result = result	+ '         <div class="form-group">';
			result = result	+ '           <label class="col-md-3">Task Title<span class="text-danger paddingLeft">*</span></label>';
			result = result	+ '           <div class="col-md-9">';
			result = result	+ '             <input class="form-control"  placeholder="Task Title" type="text" id="taskName" name="taskName" field-name="Task Title"  data-validation="required validate_length length2-250" >';
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			
			result = result	+ '         <div class="form-group">';
			result = result	+ '           <label class="col-md-3">Assign To</label>';
			result = result	+ '           <div class="col-md-9">';
			result = result	+ '             <select class="form-control chosen-select choose" multiple  id="assignTaskId" name="assignTaskId" data-placeholder="Select Assign To">';
			result = result	+ '             </select>';
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			result = result	+ '         <div class="form-group">';
			result = result	+ '           <label class="col-md-3">Due Date</label>';
			result = result	+ '           <div class="col-md-9 clearDate" onClick="cleartprojectdueDate();">';
			result = result	+ '             <input class="form-control calendar" value="" name="duDate" id="dueDate" placeholder="Due Date" type="text" readonly>';
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			result = result	+ '         <div class="form-group">';
			result = result	+ '           <label class="col-md-3">Priority</label>';
			result = result	+ '           <div class="col-md-9 dropdown-select">';
			result = result	+ '             <select class="form-control" id="priority" name="priority">';
			result = result	+ '               <option value="">-- Select Priority --</option>';
			result = result	+ '           <c:forEach var="priority" items="${taskPriorityList}"> ';        
			result = result	+ '		            <option value="${priority.id}" ${priority.name  == "P2" ? "selected" : ''} >${priority.name}</option>';   
			result = result	+ '			</c:forEach>   ';	
			result = result	+ '             </select>'; 
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			
			result = result	+ '         <div class="form-group">';
			result = result	+ '           <label class="col-md-3">Estimated Time</label>';
			result = result	+ '           <div class="col-md-9">';
			result = result	+ '             <input class="form-control"  placeholder="Estimated Time" type="text" id="estimatedTime" name="estimatedTime" field-name="Estimated Time"  data-validation="validate_Space validate_float validate_length length1-10" data-validation-optional="true" >';
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			
		/* 	result = result	+ '         <div class="form-group">';
			result = result	+ '           <label class="col-md-4">Estimated Date</label>';
			result = result	+ '           <div class="col-md-8">';
			result = result	+ '             <input class="form-control calendar"  id="estimatedDate" name="estimatedDate" placeholder="Estimated Date" type="text" readonly>';
			result = result	+ '           </div>';
			result = result	+ '         </div>'; */
			result = result	+ '         <div class="form-group">';
			result = result	+ '           <label class="col-md-3">Severity</label>';
			result = result	+ '           <div class="col-md-9 dropdown-select">';
			result = result	+ '             <select class="form-control" id="severity" name="severity">';
			result = result	+ '               <option value="">-- Select Severity --</option>';
			result = result	+ '                 <c:forEach var="severity" items="${taskSeverityList}">  ';       
		    result = result	+ '  				  <option value="${severity.id}">${severity.name}</option>';   
			result = result	+ ' 				 </c:forEach>  ';
			result = result	+ '             </select>';  
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			result = result	+ '         <div class="form-group">';
			result = result	+ '           <label class="col-md-3">Status<span class="text-danger paddingLeft">*</span></label>';
			result = result	+ '           <div class="col-md-9 dropdown-select">';
			result = result	+ '             <select class="form-control" field-name="Status"  id="taskStatus" name="taskStatus" data-validation="required">';
			result = result	+ '              <c:forEach var="Status" items="${taskStatusList}">    ';   
			result = result	+ ' 			  <option value="${Status.id}">${Status.name}</option> ';
			result = result	+ ' 			 </c:forEach>';  
			result = result	+ '             </select>'; 
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			result = result	+ '         <div class="form-group">';
			result = result	+ '           <label class="col-md-3">Owner</label>';
			result = result	+ '           <div class="col-md-9">';
			result = result	+ '             <select class="form-control" data-placeholder="Select Owner" id="ownerId" name="taskOwner">';
			result = result	+ '             </select>';
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			result = result	+ '         <div class="form-group">';
			result = result	+ '           <label class="col-md-3">Billing Type<span class="text-danger paddingLeft">*</span></label>';
			result = result	+ '           <div class="col-md-9 dropdown-select">';
			result = result	+ '             <select class="form-control" field-name="Billing Type" id="taskBillableType" name="taskBillableType" data-validation="required">';
			result = result	+ '               <option value="Billable">Billable</option>';
			result = result	+ '               <option value="Non-Billable">Non-Billable</option>';
			result = result	+ '             </select>';
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			
			result = result	+ '         <div class="form-group">';
			result = result	+ '           <label class="col-md-3">Business Value</label>';
			result = result	+ '             <div  class="col-md-9 positionRelative">';
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
			
			result = result	+ '           <c:if test= "${!empty sourceleadSectionListForProjectTask}">  ';
			result = result	+ '           <c:forEach var="section" items="${sourceleadSectionListForProjectTask}" varStatus="sectionOrder"> ';
			result = result	+ '	          <c:if test="${sectionOrder.index == 0}">';
			result = result	+ '           <div class="clearfix" id="customDiv${section.id}"></div>';
			result = result	+ '           </c:if>';
			result = result	+ '           </c:forEach>';
			result = result	+ '           </c:if>';
			
			
			result = result	+ '         <div class="clearfix"></div>';
			result = result	+ '         <div class="clearfix mb">';
			result = result	+ '           <label class="col-md-12">Description</label>';
			result = result	+ '           <div class="col-md-12 txtEditor_p">';
			result = result	+ '            <textarea id="txtEditor" name="taskDescription"  class="form-control newContrtextarea" data-validation="validate_length length2-5000" data-validation-optional="true"></textarea>';
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
		
				  var locationDate ='${SourceLeadWeb_dateFormat}'; 
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
			    
			    
			    $('#dueDate').datepicker({
					dateFormat : finalDateFormatProjectSave,
					changeMonth: true,
					changeYear: true, 
					minDate : projectStartDate,
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
	fileUpload("file"+id+"", "tasks", "projectTask_form");
}
</script>

<script>
function cleartprojectdueDate(){
	$("#dueDate").val("");
	};

 </script>
<script>
function getOwnerList(){
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var ownerUserId = '${projectTask.ownerUserId.id}';
	$.ajax({
	    	type: "GET",
	    	 url: pmsUrl+"/getResourceRequestApproversList?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId,
	    		success: function(json) {
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
		     		if(opt.id == ownerUserId){
		     			output = output + "<option "+ (opt.id == ownerUserId ? "selected" : '') +" value='" +opt.id+ "'>"+fullname+" ("+opt.userType+")</option>";
		     		}else{
		     			output = output + "<option value='" +opt.id+ "'>"+fullname+" ("+opt.userType+")</option>";
		     		}
		     			
	     		}
	     		$("#ownerId").empty();
	     		$("#ownerId").append(output);
	     		$('#ownerId').trigger("chosen:updated"); 
	     		
	     		$("#editOwnerId").empty();
	     		$("#editOwnerId").append(output);
	     		$('#editOwnerId').trigger("chosen:updated"); 
	     		
	     		
	     		
	         }
	 });
}
</script>
<script>
function subTasksAssignList(projectId){
	
	var values = "Active";
	$.ajax({
    	type: "GET",
        url: pmsUrl+"/getProjectEmployeeListById?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&status="+values, 
        success: function(json) {
        	var output = "";
        	for (var i=0; i<json.length; i++)
			{
        		    var opt = json[i];
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
		 $('#taskId').removeAttr("data-validation","required");
		 getSubTasks();
	 } 
	 
 }
 </script>
 <script>
 function getSubEpicsByUserStory(type){
	 var projectId =  "${projectId}";
	 
	 var taskType = "Epic";
	 $.ajax({
	    	type: "POST",
	    	 url: pmsUrl+"/ProjectTasks/getProjectTasksByTaskType?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&taskType="+taskType,
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
	 
	 var taskType = "UserStory";
	 $.ajax({
	    	type: "POST",
	    	 url: pmsUrl+"/ProjectTasks/getProjectTasksByTaskType?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&taskType="+taskType,
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
	
	var projectTaskId = "${projectTask.id}";
	taskType = "SubTask";
	$.ajax({
	    	type: "POST",
	    	 url: pmsUrl+"/ProjectTasks/getProjectTasksBySubTaskType?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&taskType="+taskType,
	    		success: function(json) {
	     		var output = "";
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
		 var ee = $("#txtEditor").val($('.Editor-editor').html()); 
		var frm = $('#projectTask_form').serializeFormJSON();
		var form = $('#projectTask_form')[0];
		var formData = new FormData(form);
		var projectId =  "${projectId}";
	  	$("#save_task").attr("disabled", "disabled");
	  	var userId = '${SourceLeadWeb_userId}';
	  	var projectClientUUID = '${projectClientUUID}';
		$("#save_task").addClass('button').val('Processing..');
		$.ajax({
			 type : "POST",
			 data: formData,
	         processData: false,
	         contentType:false, 
			 url : pmsUrl + "/ProjectTasks/saveProjectTasks?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&userId="+userId+"&projectClientUUID="+projectClientUUID,
			  success : function(result) {
				  if (result && result.response === "INVALID_DATA") {
	                    showError(result.responseMessage);
	                } else if (result.response === "AWKWARD") {
	                    location.href = "/errorPage";
	                } else if (result.response === "SUCCESS") {
	  				  getSubTaskList();
	  				  cancelTask();
	                }else if (result.indexOf('Sign In') !== -1) {
						location.replace(pmsUrl);
					} else {
	                    showError("Operation failed, please try again");
	                }
				  
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
	var projectClientUUID = '${projectClientUUID}';
	var projectId =  "${projectId}";
	var projectTaskId = "${projectTask.id}";
	$.ajax({
		type : "POST",
		url : pmsUrl	+ "/ProjectTasks/getsubTasksList?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&subTaskId="+projectTaskId,
		success : function(json) {
			var result = "";
			result = result + '<table id="subTasksPagination" class="display" cellspacing="0" width="100%">';
			result = result + '<thead>';
			result = result + '<tr>';
			result = result + '<th>Task Name</th>';
			result = result + '<th>Assigned To</th>';
			result = result + '<th>Status</th>';
			result = result + '<th>Action</th>';
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
				
				'<sec:authorize access="hasRole(\'ROLE_VIEW_EDIT_SUB_TASK\')">'; 
				   result = result+ '<td><div class="proj_show tblminwidth-100"><a href="'+pmsUrl+'/projectTaskSummary?taskUUID='+opt.projectTasksUUID+'&projectClientUUID='+projectClientUUID+'" title="'+ opt.taskName+ '" class="pull-left anchorBlack" >'+ opt.taskName+ '<i class="fa fa-binoculars" style="position:absolute; right:0;"></i></a></div></td>';
				'</sec:authorize>';
				
				'<sec:authorize access="!hasRole(\'ROLE_VIEW_EDIT_SUB_TASK\')">'; 
				   result = result+ '<td><div><a title="'+ opt.taskName+ '" >'+ opt.taskName+ '</a></div></td>';
				'</sec:authorize>';
				result = result	+ '<td><div><span >'+taskAssignedUsers+'</span></div></td>';
				result = result	+ '<td><div><span>'+taskStaus+'</span></div></td>';
				result = result	+ '<td>';
				result = result	+ '<div class="tbldata-dotdot-100 text-center">';
				result = result	+ '<a class="glyphicon glyphicon-edit" title="Edit" href="'+pmsUrl+'/projectTaskSummary?taskUUID='+opt.projectTasksUUID+'&projectClientUUID='+projectClientUUID+'" ></a> <a class="glyphicon glyphicon-trash text-danger ml" title="Delete" onclick="deleteProjectTaskPopUp('+opt.id+');"></a>';
				result = result	+ '</div></td>';
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


function deleteProjectTaskPopUp(projectTaskId){ 
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p style='text-align: center;'>Do you want to delete a task ?</p>";
	result = result + ' <div id="reasonForProjectDiv"/>';
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Delete" onclick="deleteProjectTask('+projectTaskId+')"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";
	$("#taskDeletePopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function deleteProjectTask(projectTaskId){
	$.ajax({
		type : "POST",
		url : pmsUrl	+ "/ProjectTasks/deleteProjectSubTasks?${_csrf.parameterName}=${_csrf.token}&projectTaskId="+projectTaskId,
		success : function(json) {
			    $("#taskDeletePopUp").empty();
			 	$(".project_inn_mask").fadeOut(200);
			    getSubTaskList();
		}
	});
}
function cancelTermination(){
	$("#taskDeletePopUp").empty();
	 $(".project_inn_mask").fadeOut(200);
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
	var projectTaskId = "${projectTask.id}";
	$.ajax({
		type : "POST",
		url : pmsUrl	+ "/ProjectTasks/getTaskActivityList?${_csrf.parameterName}=${_csrf.token}&projectTaskId="+projectTaskId,
		success : function(json) {
			var result = "";
			if(json != null && json != 'undefined' && json != "" && json != '[]'){
				for (var i = 0; i < json.length; i++) {
					var report = json[i];
					result = result + '	<div class="media activities-projecttask clearfix col-md-12">';
					result = result + '   <div class="media-left">';
					if(report.fileUrl != null){
						result = result  +'	   <img onerror="predefineProfileImage(this);" src="'+report.fileUrl+'" class="media-object img-circle" style="width:60px"/>';
					}else{
						result = result  +'	   <img onerror="predefineProfileImage(this);" src="" class="media-object" style="width:60px"/>';
					}
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
	var projectId = "${projectId}";
	 result = "";
	result  = result + '	<div class="clearfix">';
	result  = result + '   <button type="button" class="close mr5 mt5" onclick="closeComment()">&times;</button>';
	result  = result + '   <p class="ml5 mt10 mb0"><strong>Add Comment</strong></p>';
	result  = result + ' </div>';
	result = result + '    <form id="addComment_form" name="addComment_form">';
	result = result + ' <input type="hidden" name="tabType" value="comment">';
	result  = result + ' <div class="row clearfix">';
	result  = result + '	<div class="ticketCommentCont">';
	result  = result + '	<div class="clearfix">';
	result  = result + '		<div contenteditable="true" id="taskComment" class="tkt_comment_div tkt_comment_custom"></div>';
	result  = result + '	</div>';
	result  = result + '</div>';
	result  = result + '</div>';
	result  = result + ' <div class="modalFooter  mb10 clearfix">';
	result  = result + '   <div class="pull-right">';
	result  = result + '     <button type="button" class="btn btn-primary btn-sm" id="save_comment" data-dismiss="modal" onclick="saveComment();">Add</button>';
	result  = result + '     <button type="button" class="btn btn-default btn-sm" data-dismiss="modal" onclick="closeComment()">Cancel</button>';
	result  = result + '   </div>';
	result  = result + ' </div>';
	result = result + ' </form>';
	$("#commnetPopUpDivId").empty();  
	$("#commnetPopUpDivId").append(result); 
	 
	commentMembers(projectId);
	 
}

</script>
<script>
function saveComment(){
	 if ($('#addComment_form').validate(false, validationSettings)) {
		 var assignValue = $("#assignedUsers").val();
			var editOwnerId = $("#editOwnerId").val();
			if(assignValue != null){
				assignValue = assignValue;
			}else{
				assignValue = "";
			}
			if(editOwnerId != null){
				editOwnerId = editOwnerId;
			}else{
				editOwnerId = "";
			}
			var projectClientUUID = '${projectClientUUID}';
			
 			var projectTaskId = '${projectTask.id}';
		  	var userId = '${SourceLeadWeb_userId}';
		  	var formData = {};
			var mentionedUserIds = selectedUsers.toString();
			formData["taskComment"] =  $("#taskComment").html();
			formData["projectTaskId"] =  projectTaskId;
			formData["loginUserId"] =  userId;
			formData["projectClientUUID"] =  projectClientUUID;
			formData["editOwnerId"] =  editOwnerId;
			formData["mentionedUserIds"] =  mentionedUserIds;
		  	$("#save_comment").attr("disabled", "disabled");
			$("#save_comment").addClass('button').val('Processing..');
			$.ajax({
				 type : "POST",
				 data: formData,
				 url : pmsUrl + "/ProjectTasks/saveProjectTaskComment?&assignValue="+assignValue,
				  success : function(result) {
					  if (result && result.response === "INVALID_DATA") {
		                    showError(result.responseMessage);
		                } else if (result.response === "AWKWARD") {
		                    location.href = "/errorPage";
		                } else if (result.response === "SUCCESS") {
		                	 closeComment();
							  commentTableList();
		                }else if (result.indexOf('Sign In') !== -1) {
							location.replace(pmsUrl);
						} else {
		                    showError("Operation failed, please try again");
		                }
					  
					 
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
		$(imageId).attr('src','/resources/images/userprofile-img.png');
	}
</script>

<script>
function commentTableList(){
	var projectTaskId = "${projectTask.id}";
	$.ajax({
		type : "POST",
		url : pmsUrl	+ "/ProjectTasks/getTaskCommentList?${_csrf.parameterName}=${_csrf.token}&projectTaskId="+projectTaskId,
		success : function(json) {
			var result = "";
			if(json != null && json != 'undefined' && json != "" && json != '[]'){
				for (var i = 0; i < json.length; i++) {
					var report = json[i];
					result = result + '	<div class="media clearfix col-md-12">';
					result = result + '   <div class="media-left">';
					if(report.fileUrl != null){
						result = result  +'	   <img onerror="predefineProfileImage(this);" src="'+report.fileUrl+'" class="media-object img-circle" style="width:60px"/>';
					}else{
						result = result  +'	   <img onerror="predefineProfileImage(this);" src="" class="media-object" style="width:60px"/>';
					}
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
<script>
function getCustomDependentFieldProjectSubTask(sectionId){
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
			type : "POST" , 
				 url: sourceleadUrl+"/getAllASectionCustomFields?sectionId="+sectionId,
				 success: function(data) {
		        	$('#customDiv'+sectionId).empty();
		        	var result ='';
		        	result = result + data;
		        	$('#customDiv'+sectionId).append(result);
		        	
				 }
		  }); 
}
</script>



<!-- ============================ Comments related Script End's ========================================== -->


<!--  ======================== Tags Related Scripts written by Laxman Start =============================== -->
 <script>
var select, chosen;
select = $("#projectTaskTagName");
select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
chosen = select.data('chosen');
chosen.search_field.on('keyup', function(e)
{
	var anyKeyword = this.value.replace(",","");
    if ((e.which == 13 || e.which == 188) && (anyKeyword != null  && anyKeyword != '' && anyKeyword != 'undefined'))
    {
    	var par = this.parent;
        var option = $("<option>").val(anyKeyword).text(anyKeyword);
        select.append(option);
        select.find(option).prop('selected', true);
        $("#projectTaskTagName option").each(function(){
              $(this).siblings("[value='"+ this.value+"']").remove();
        });
		$("#projectTaskTagName").trigger("chosen:updated");
		saveTaskTags("add");
    }
});

$('.chosen-select').chosen().on('change', function(evt, params) {
	if (params.selected && params.selected != undefined) {
		saveTaskTags("add");
	}
	if (params.deselected && params.deselected != undefined) {
		saveTaskTags("delete");
	}
	
});
$("#projectTaskTagName_chosen").find('ul.chosen-choices > li input').attr("placeholder", "Add Tag")
</script>
<script>
function getProjectTaskTagsList(){
	var projectTaskId = "${projectTask.id}";
	$.ajax({  
        type : "POST",
        url: pmsUrl+"/getTaskTags?"+tokenParamName+"="+tokenValue+"&projectTaskId="+projectTaskId,        
        success :function(json) {
        	var result = "";
			$.each( json.tagsList, function( i, tags ) {
			  var existed = 0;
                if(json.taskTags != null && json.taskTags != 'undefined' && json.taskTags != '') {
	                var res = [];
	                res = json.taskTags.split(",");
	                res.forEach(function(item) {
	                	if(tags == item){
	                		existed = 1;
	                		result = result + ' <option selected value="'+tags+'">'+tags+'</option>';
	                	}
	                });
                }
                if(existed == 0){
                	result = result + ' <option value="'+tags+'">'+tags+'</option>';
                }
			});
            $("#projectTaskTagName").empty();
            $("#projectTaskTagName").append(result);
            $("#projectTaskTagName").chosen().trigger("chosen:updated")
        },
	});
}
</script>
<script>
function saveTaskTags(type){
	var projectTaskId = "${projectTask.id}";
	 var tagName = $("#projectTaskTagName").val();
	 var jsonData = encodeURIComponent(tagName);
	 var loginUserId = '${SourceLeadWeb_userId}';
	 if(tagName != null && tagName != '' && tagName !='undefined') {
		 Urlcheck = pmsUrl+"/saveOrUpdateTagsByProjectTaskId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&projectTaskId="+projectTaskId+"&tagName="+jsonData
	 } else {
		 Urlcheck = pmsUrl+"/saveOrUpdateTagsByProjectTaskId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&projectTaskId="+projectTaskId+"&tagName="+""
	 } 
	 $.ajax({  
         type : "POST",
         url: Urlcheck,        
         success :function(result) {
			if(result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
        		showError(result.responseMessage);
        	} else if (result.response === "AWKWARD") {
        	    location.href = "/errorPage";
        	} else if (result.response === "SUCCESS") {
        		if(type == "add"){
        			showSuccessStatusPopup("Tag added successfully");
        		}else if(type == "delete"){
        			showDeleteStatusPopup("Tag deleted successfully");
        		}
        		getProjectTaskTagsList(clientId);
        	} else if (result.indexOf('Sign In') !== -1) {
        		location.replace(pmsUrl);
        	} else {
        	    showError("Operation failed, please try again");
        	}
       },
    }); 
}
function showSuccessStatusPopup(status) {
	$("#addTaskTagAlert").css("display","block");
	$("#addTaskTagAlert span").html(status);
    $('#addTaskTagAlert').delay(2000).fadeOut('slow');
}
function showDeleteStatusPopup(status) {
	$("#deleteTaskTagAlert").css("display","block");
	$("#deleteTaskTagAlert span").html(status);
    $('#deleteTaskTagAlert').delay(2000).fadeOut('slow');
}
</script>
<!--  ======================== Tags Related Scripts written by Laxman Ends =============================== -->


 <jsp:include page="../masterFooter.jsp" />	