<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style type="text/css">
.taskdesp .form-control {
height: auto !important;
}
.project_tabs_inn_table.project_tabs_inn_table_a table tr td:last-child
{
width: auto !important;
}
</style>

<script type="text/javascript">
function taskNameValidation(){
	var projectId  = $("#projectId").val();
	var TaskName = document.getElementById("taskName").value.trim();
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
		type : "GET",
		url : sourceleadUrl+ "/validateTasksNameById?${_csrf.parameterName}=${_csrf.token}&TaskName="+ TaskName+"&ProjectId="+projectId,
		success : function(data) {
			 if (data == true) {
				$("#save_task").hide();
				$("input#taskName").after("<p class='jquery_form_error_message'>"+ TaskName+ " is Already Existed</span>");
				document.getElementById("taskName").setAttribute('record-exist', 'yes');
				document.getElementById("taskName").setAttribute('record-exist-errorMsg',TaskName+ ' is Already Existed');
				$('input#taskName').removeAttr( "record-exist record-exist-errorMsg");
			} else {
				$("#save_task").show();
				$("#save_task").removeAttr("disabled");
				$('input#taskName').removeAttr("record-exist record-exist-errormsg");
				$('input#taskName').attr('class','valid form-control');
				$('#taskNamesDIV').find('p.jquery_form_error_message').remove();
				$("#save_task").click(function() {
				});
			} 
		},
	});
}
</script>
 

<script>
function projectTask(projectId, status) {
	var result = "";
	result = result + '<div class="timesheet_head clearfix timesheet_headul-custom">';
	result = result + '<input type="hidden" value='+projectId+' id="projectId">';
	result = result + '<input type="hidden" value='+status+' id="projectStatus">';
	 result = result	+ '<ul>';
	if (status != 'COMPLETED' && status != '' && status != 'undefined' && status != null) {
		result = result +'<li>';
		result = result	+ '<span class="pad-sea">';
		'<sec:authorize access="hasAnyRole(\'ROLE_CREATE_PROJECT_TASKS\')">';  
		result = result	+ '<span onclick="addtask();" class="cursorPointer"><i class="glyphicon glyphicon-plus paddingRight"></i>Add Task</span>';
   		'</sec:authorize>'; 
		result = result	+ '</span>';
		result = result +'</li>';
	}
	
	
		result = result	+ '	<li class="dropdown"><div class="dropdown-toggle pull-left" data-toggle="dropdown"><i class="fa fa-filter"></i><span>Status</span><i class="glyphicon glyphicon-play" id="appro-pop"></i>|</div>';
		result = result	+ '		<div class="dropdown-menu bootstrap_dropdownMenu dropdownPadding">';
		result = result	+ '	    	<ul>';
		
		<c:forEach var="status" items="${taskStatusList}">     
			<c:if test="${taskStatusList[0] == status || taskStatusList[1] == status}">
			  result = result	+ '	 <li><input   name="taskStatusId" value ="${status.id}" checked  onclick="projectTaskList();"  type="checkbox"><label for="taskStatusId${status.id}">${status.name}</label></li>';
		    </c:if>	  
		    <c:if test="${taskStatusList[0] != status  && taskStatusList[1] != status}">
		      result = result	+ '	 <li><input   name="taskStatusId" value ="${status.id}"  onclick="projectTaskList();"  type="checkbox"><label for="taskStatusId${status.id}">${status.name}</label></li>';
		    </c:if>
		</c:forEach>  
	    result = result	+ '	        </ul>';
		result = result	+ '	    </div>';                	
		result = result	+ '	</li>';
		result = result	+ '	<li class="dropdown"><div class="dropdown-toggle pull-left" data-toggle="dropdown"><i class="fa fa-filter"></i><span>Task Type</span><i class="glyphicon glyphicon-play" id="appro-pop"></i>|</div>';
		result = result	+ '		<div class="dropdown-menu bootstrap_dropdownMenu dropdownPadding">';
		result = result	+ '	    	<ul>';   
		<c:forEach var="type" items="${taskTypeList}">        
		 
		<c:if test="${taskTypeList[1] == type || taskTypeList[2] == type}">
		result = result	+ '	 <li><input  name="taskTypeId" value ="${type.id}"  onclick="projectTaskList();" checked type="checkbox"><label for="taskTypeId${type.id}">${type.name}</label></li>';
	    </c:if>	
	    <c:if test="${taskTypeList[1] != type && taskTypeList[2] != type}">
	    result = result	+ '	 <li><input  name="taskTypeId" value ="${type.id}"  onclick="projectTaskList();"  type="checkbox"><label for="taskTypeId${type.id}">${type.name}</label></li>';
	    </c:if>
	    
		
		</c:forEach>  
		 result = result	+ '	        </ul>';                                                              
		result = result	+ '	    </div>';                	                                            
		result = result	+ '	</li>';                                                                      
		 
		 result = result	+ '</ul>';
		 result = result + '</div>';
	result = result +'<div id="taskListdivId" class="datatable_filter"> </div>';
	$("#tab-1").empty();
	$("#tab-3").empty();
	$("#tab-2").empty();
	$("#tab-4").empty();
	$("#tasksLoadingImg").empty();
	$("#tab-3").append(result);
	$("#tab-3").show();
	$("#txtEditor").Editor();
	$(".project-tabs-menu a").click(
			function(event) {
				event.preventDefault();
				$(this).parent().addClass("current");
				$(this).parent().siblings().removeClass(
						"current");
				var tab = $(this).attr("href");
				$(".project_tabs_inn").not(tab).css(
						"display", "none");
				$(tab).fadeIn();
			});
	projectTaskList();
	
}
</script>


<!-- this script is for Tasks list functionality at Projects screen -->
<script>
	function projectTaskList() {
		
		var projectId = $("#projectId").val();
		var status =  $("#projectStatus").val();
		var result = "";
		$("#tasksLoadingImg").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var sourceleadUrl = '${sourceleadUrl}';
		
		 var taskStatus = [];
		$('input:checkbox[name=taskStatusId]:checked').each(function() 
	    {
			taskStatus.push($(this).val());
	    });
		
		var taskType = [];
		$('input:checkbox[name=taskTypeId]:checked').each(function() 
	     {
			taskType.push($(this).val());
		 });
		$.ajax({
					type : "POST",
					url : sourceleadUrl	+ "/getProjectTasks?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&taskStatus="+taskStatus+"&taskType="+taskType,
					success : function(json) {
						var result = "";
						result = result	+ '<input type="hidden" id="projectStatus" value="'+status+'">';
						result = result	+ '</div><div class="project_tabs_inn_table project_tabs_inn_table_a">';
						result = result	+ '<div id="tasksLoadingImg"></div>';
						result = result + '<table id="projectTaskPagination" class="display" cellspacing="0" width="100%">';
						result = result + '<thead>';
						result = result + '<tr id="projectTaskTableId">';
						result = result + '<th>Task Name</th>';
						result = result + '<th>Task Type</th>';
						result = result + '<th>Priotiry</th>';
						result = result + '<th>Assigned To</th>';
						result = result + '<th>Status</th>';
						result = result + '</tr>';
						result = result + '</thead>';
						result = result + '<tbody>';
						for (var i = 0; i < json.length; i++) {
							var opt = json[i];
							if(opt.taskPriotiry != null && opt.taskPriotiry != "" && opt.taskPriotiry !="undefoned"){
								taskPriotiry = opt.taskPriotiry;
							}else{
								taskPriotiry = "--";
							}
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
							'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_PROJECT_TASKS\')">'; 
							result = result+ '<td><div><a href="'+sourceleadUrl+'/projectTaskSummary?taskId='+opt.id+'" title="'+ opt.taskName+ '" >'+ opt.taskName+ '</a></div></td>';
							result = result	+ '<td><div><span>'+opt.taskTypes+ '</span></div></td>';
							result = result	+ '<td><div><span>'+taskPriotiry+ '</span></div></td>';
							result = result	+ '<td><div><span >'+taskAssignedUsers+'</span></div></td>';
							result = result	+ '<td><div><span>'+taskStaus+'</span></div></td>';
							'</sec:authorize>'; 
							
							'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_EDIT_PROJECT_TASKS\')">';
							result = result+ '<td><div><a title="'+ opt.taskName+ '" >'+ opt.taskName+ '</a></div></td>';
							result = result	+ '<td><div><span>'+opt.taskTypes+ '</span></div></td>';
							result = result	+ '<td><div><span>'+ taskPriotiry+ '</span></div></td>';
							result = result	+ '<td><div><span >'+taskAssignedUsers+'</span></div></td>';
							result = result	+ '<td><div><span>'+opt.taskStaus+'</span></div></td>';
							'</sec:authorize>'; 
							result = result + '</tr>';

						}
						result = result + '</tbody>';
						result = result + '</table>   ';
						$("#taskListdivId").empty();  
			    		$("#taskListdivId").append(result);  
						
						taskPagination();

					}
				});
	}
</script>

 
<div class="vendor_wrap_mask"></div>
	
	<script> 	
// Edit pop
<script type="text/javascript">
$(document).ready(function() {
    $(".glyphicon-edit").click(function(){
		$(".settPop").slideDown(200);
		$(".vendor_wrap_mask").fadeIn(200);
	});
	$(".settPop i, .btn-default").click(function(){
		$(".settPop").slideUp(200);
		$(".vendor_wrap_mask").fadeOut(200);
	});
});		
	
</script> 
 
<script>
function taskPagination(){  
    $('#projectTaskPagination').DataTable( { 
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
 
<script>
function saveUpdateTask(taskId,projectId,status){
	if ($('#editTask_form').validate(false, validationSettings)) {
		var frm = $('#editTask_form').serializeFormJSON();
		var con = JSON.stringify(frm);
		con = con.replace(/[{}]/g, "");
		//It will escape all the special characters
		var jsonData = encodeURIComponent(con);
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
			type : "POST",
			url : sourceleadUrl+ "/updateEditProjectTask?${_csrf.parameterName}=${_csrf.token}&json="+ jsonData+"&taskId="+taskId,
			success : function(result) {
				editCancel();
				$("#tab-3").show();
				projectTask(projectId,status);
				
			},
		});
		return true;
	}else {
		return false;
	}
}
$('body').on('blur', '#editTask_form', function() {
	$("#editTask_form").showHelpOnFocus();
	$("#editTask_form").validateOnBlur(false, validationSettings);
});
</script>
<script>
function validateTaskName(projectId,taskId){
	var TaskName = document.getElementById("editTaskName").value.trim();
	var sourceleadUrl = '${sourceleadUrl}';
	$('#editTaskNamesDIV').find('p.jquery_form_error_message').remove();
	$.ajax({
		type : "GET",
		url : sourceleadUrl+ "/taskNameValidation?${_csrf.parameterName}=${_csrf.token}&TaskName="+TaskName+"&ProjectId="+projectId+"&taskId="+taskId,
		success : function(data) {
			 if (data == true) {
				$("input#editTaskName").after("<p class='jquery_form_error_message'>"+ TaskName+ " is Already Existed</span>");
				document.getElementById("editTaskName").setAttribute('record-exist', 'yes');
				document.getElementById("editTaskName").setAttribute('record-exist-errorMsg',TaskName+ ' is Already Existed');
				$('input#taskName').removeAttr( "record-exist record-exist-errormessage");
			} else {
				$('input#editTaskName').removeAttr("record-exist record-exist-errormsg");
			} 
		},
	});
}
</script>

<script>
function editCancel(){
	$(".settPop").slideUp(200);
	$(".vendor_wrap_mask").fadeOut(200);
}
</script>

<!-- ============================================= New Tasks adding screan by Laxman =============================== -->

<div class="side_mask" onclick="taskClose();"><div class="proj_close"></div></div>
  <div class="tasks_side_wrap">
    <div id="addTaskPopupDivId"></div>
  </div>

<script src="resources/js/editor.js"></script>
<link href="resources/css/editor.css" type="text/css" rel="stylesheet"/>

 <script>
 function showtasktype(){
	 var taskType = $("#taskstype").val();
	 //dont rempve the comments by Laxman
	 //1047 Epic
	 //1048 UserStory
	 //1049 Bug
	 //1050 SubTask these are Lookupdata TaskType id's
	 if(taskType == '1048'){
		 document.getElementById("epic").style.display="block";
		 getSubEpics(taskType);
	 }
	 if(taskType == '1049' || taskType == '1050'){
		 document.getElementById("userStory").style.display="block";
		 document.getElementById("epic").style.display="none";
		 getSubEpics(taskType)
	 }
	 if(taskType == '1047'){
		 document.getElementById("userStory").style.display="none";
		 document.getElementById("epic").style.display="none";
	 } 
 }
 </script>
 <script>
 function getSubEpics(type){
	var taskType = null;
	 if(type == '1048'){
		 taskType = "1047";
	 }else if(type == '1049' || type == '1050'){
		 taskType = "1048";
	 }
	var projectId = $("#projectId").val();
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/getProjectTasksByTaskType?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&taskType="+taskType,
	    		success: function(json) {
	     		var output = "";
	     		if(type == '1048'){
	     			output = output + "<option value=''>--Select Epic--</option>";
	     		}else{
	     			output = output + "<option value=''>--Select User Story--</option>";
	     		}
	     			
	     		for (var i=0; i<json.length; i++)
	     		{
		     		var opt = json[i];
		     		
		     			output = output + "<option value='" +opt.id+ "'>"+opt.taskName+"</option>";
	     		}
	     		if(type == '1048'){
	     			$("#epicId").empty();
		     		$("#epicId").append(output);
		     		$('#epicId').trigger("chosen:updated");
	     		}else{
	     			$("#userStroyId").empty();
		     		$("#userStroyId").append(output);
		     		$('#userStroyId').trigger("chosen:updated");
	     		}
	     		
	         }
	 });
 }
 </script>
 
<script>
    function addTimeEntry(){
        $(".modal-Time").fadeIn();
    }
    function closeTimeEntry(){
        $(".modal-Time").fadeOut();
    } 
    function addtask(){
    	var projectId =  $("#projectId").val();
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
		    result = result	+ '     <c:forEach var="type" items="${taskTypeList}">  ';        
		    result = result	+ '  	 	  <option value="${type.id}">${type.name}</option>';   
		    result = result	+ '  		</c:forEach>  ';                                           
			result = result	+ '             </select>'; 
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			result = result	+ '           <div class="form-group" style="display: none" id="epic">';
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
			result = result	+ '         <div class="form-group">';
			result = result	+ '           <label class="col-md-4">Task Title<span class="text-danger paddingLeft">*</span></label>';
			result = result	+ '           <div class="col-md-8">';
			result = result	+ '             <input class="form-control"  placeholder="Task Title" type="text" id="taskName" name="taskName" field-name="Task Title"  data-validation="required validate_length length2-250" >';
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			result = result	+ '         <div class="form-group">';
			result = result	+ '           <label class="col-md-4">Assign To</label>';
			result = result	+ '           <div class="col-md-8">';
			result = result	+ '             <select class="form-control chosen-select choose" multiple  id="assignTaskId" name="assignToTask" data-placeholder="Select Assign To">';
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
			result = result	+ '					<input type="text" class="form-control" id="businessValue" name="businessValue">';
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
			
			$("#addTaskPopupDivId").empty();
     		$("#addTaskPopupDivId").append(result);
     		$("#txtEditor").Editor();
     		getOwnerList();
     		tasksAssignList(projectId);
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
    $(".proj_close, .side_mask").click(function(){
		$(".tasks_side_wrap").removeClass("tasks_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	 
    
  
    
    
    
	var fileCounter = 0;
	function addFileUpload(){
		
		$('<div>').attr('style','float:left; width:325px; border:1px solid #ddd; margin:0 15px 15px 0; padding:5px;overflow: hidden; background-color:#367fa9; color:#fff; ').append('<input id="file' +  '" name = "project_files' + '" type="file" class="pull-left" />' +
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
function tasksAssignList(projectId){
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
	        		output = output + "<option value='" +opt.id+ "'>"+opt.empName+"</option>";
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
function saveTasks(){
	 if ($('#projectTask_form').validate(false, validationSettings)) {
		var frm = $('#projectTask_form').serializeFormJSON();
		var ee = $("#txtEditor").val($('.Editor-editor').html()); 
		var jsonData = JSON.stringify(frm);
		var form = $('#projectTask_form')[0];
		var formData = new FormData(form);
		formData.append("json",jsonData)
		var projectId = $("#projectId").val();
		var userId = '${SourceLeadWeb_userId}';
	  	var sourceleadUrl = '${sourceleadUrl}';
	  	$("#save_task").attr("disabled", "disabled");
		$("#save_task").addClass('button').val('Processing..');
		$.ajax({
			 type : "POST",
			 data: formData,
    	     contentType : 'application/json; charset=utf-8',
	         processData: false,
	         contentType:false, 
    	     dataType : 'json',
    	     url : sourceleadUrl + "/ProjectTasks/saveProjectTasks?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&userId="+userId,
			  success : function(data) {
				var status = $("#projectStatus").val();
				projectTask(projectId, status);
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



<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 
<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {

			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
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
 