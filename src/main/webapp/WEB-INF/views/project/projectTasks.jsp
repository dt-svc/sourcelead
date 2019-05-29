<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
var sourceleadUrl = '${sourceleadUrl}';
</script>
<script type="text/javascript">
function taskNameValidation(){
	var projectId  = $("#projectId").val();
	var TaskName = document.getElementById("taskName").value.trim();
	
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
	$(document).ready(function() {
// 		document.getElementById("addProjectTasks_form").reset();
		/* <c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
		  var sectionId = '${section.id}';
		  getCustomDependentField(sectionId);
		 </c:forEach> */
	});
</script>


<script>
function projectTask(projectId, status) {
	var projectClientUUID = '${projectClientUUID}';
	var projectUUID = '${projectUUID}';
	
	var result = "";
	result = result + '<div class="timesheet_head clearfix timesheet_headul-custom timeHd_minHeight">';
	result = result + '<input type="hidden" value='+projectId+' id="projectId">';
	result = result + '<input type="hidden" value='+status+' id="projectStatus">';
	result = result	+ '<ul>';
	 
	result = result +'<li title="Import"><a href="<c:url value="${appPath}/uploadProjectTasks?projectUUID='+projectUUID+'&projectClientUUID='+projectClientUUID+'"/>"><i class="glyphicon glyphicon-import expIcon"></i>&nbsp;</a></li>';

	
	result = result +'<li class="dropdown">';
	result = result +' <a title="Export" id="export-pop" class="dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-export expIcon"></i>&nbsp;</a>';	
	result = result +'<div class="dropdown-menu bootstrap_dropdownMenu">';
	result = result +'<ul class="ul_padding">';
	result = result +'<li onclick="projectTaskExport(\'Pdf\');"><i class="fa fa-file-pdf-o text-danger paddingRight"></i>PDF</li>';
	result = result +'<li onclick="projectTaskExport(\'Excel\');"><i class="fa fa-file-excel-o text-success paddingRight"></i>Excel</li>';
	result = result +'</ul>';
	result = result +'</div>';
	result = result +'</li> ';
	 
	if (status != 'COMPLETED' && status != '' && status != 'undefined' && status != null) {
		result = result +'<li>';
		result = result	+ '<span class="padding_right">';
		'<sec:authorize access="hasAnyRole(\'ROLE_CREATE_PROJECT_TASKS\')">';  
		result = result	+ '<span onclick="addtask();" class="cursorPointer padding_right mr5"><i class="glyphicon glyphicon-plus paddingRight"></i>Add Task</span>';
   		'</sec:authorize>'; 
		result = result	+ '</span>';
		result = result +'</li>';
	}
		
		result = result	+ '</ul>';
		result = result	+ '<div class="pull-left cand-advsrch advsrch_chosen">';
		result = result	+ '     <button type="button" class="btn btn-primary dropdown-toggle btn-sm mr" onclick="resetTaskFilter();" title="Refresh">';
		result = result	+ '	    <span class="fa fa-refresh"></span>';
		result = result	+ '	</button>';
		result = result	+ '	<a href="javascript:void(0)" class="btn btn-default btn-sm anchorBlack text-center" title="Filter Search" onclick="openFilterTaskPop();"><i class="fa fa-filter faFilter padding_right"></i>';
		result = result	+ '	</a>';
		result = result	+ ' </div>';
		 
		 result = result + '</div>';
// 	result = result +'<div id="taskListdivId" class="datatable_filter"> </div>';
	result = result +'<div id="taskTitledivId" class="datatable_filter"> </div>';
	result = result + '<div id="taskDeletePopUp"></div>';
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
<script>
//Export filter
function exportpop(){
  $(".userExp").toggle();
}
/* $("#export-pop").click(function(){
   $(".export_pop").toggle();
}); */
  /* $("html").click(function() {
   $(".userExp").hide();
}); 
$("#exportTaskPop, #export-pop, .userExp").click(function(e) {
   e.stopPropagation();
}); */

</script>

<!-- this script is for Tasks list functionality at Projects screen -->
<script>
	function projectTaskList() {
		var projectClientUUID = '${projectClientUUID}';
		var projectId = $("#projectId").val();
		var status =  $("#projectStatus").val();
		 var projectTaskStatus = [];
		$('input:checkbox[name=taskStatusId]:checked').each(function() 
	    {
			projectTaskStatus.push($(this).val());
	    });
		
		var projectTaskType = [];
		$('input:checkbox[name=taskTypeId]:checked').each(function() 
	     {
			projectTaskType.push($(this).val());
		 });
		
	    var assignedMembers = [];
		$('input:checkbox[name=assignedtaskId]:checked').each(function() 
	    {
			assignedMembers.push($(this).val());
	    });
		
		var teams = [];
		$('input:checkbox[name=teams]:checked').each(function() 
	    {
			teams.push($(this).val());
		});
		
		var tags = [];
		$('input:checkbox[name=tags]:checked').each(function() 
	    {
			tags.push($(this).val());
		});
		
		var priorityFilter = [];
		$('input:checkbox[name=priorityFilter]:checked').each(function() 
	    {
			priorityFilter.push($(this).val());
		});
		
		var severityFilter = [];
		$('input:checkbox[name=severityFilter]:checked').each(function() 
	    {
			severityFilter.push($(this).val());
		});
		
		var createdByFilter = [];
		$('input:checkbox[name=createdByFilter]:checked').each(function() 
	    {
			createdByFilter.push($(this).val());
		});
		
		var tasksFilter = [];
		$('input:radio[name=tasksFilter]:checked').each(function() 
	    {
			tasksFilter.push($(this).val());
		});
		
		var searchValue = $("#taskDataTableSearch").val();
		 searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl	+"/getAllProjectTasks?searchValue="+searchValue+"&projectId="+projectId+"&projectTaskStatus="+projectTaskStatus+"&projectTaskType="+projectTaskType+"&assignedMembers="+assignedMembers+"&teams="+teams+"&tags="+tags+"&tasksFilter="+tasksFilter+"&createdByFilter="+createdByFilter+"&severityFilter="+severityFilter+"&priorityFilter="+priorityFilter;
		prepareTasksList(url);
	}
</script>
<script>
function prepareTasksList(url){
	var status =  $("#projectStatus").val();
	var result = "";
	result = result	+ '<input type="hidden" id="projectStatus" value="'+status+'">';
    result = result	+ '<div class="dynamic_search dynamic_search-new">';
    result = result +" <input type='text' placeholder='Search' id='taskDataTableSearch'/>";
 	result = result +"  <span class='cursorPointer' title='Search'><i class='fa fa-search' onclick='projectTaskList();'></i></span>";     

	result = result	+ '</div><div class="project_tabs_inn_table project_tabs_inn_table_a widthAutoTD">';
	result = result	+ '<div id="tasksLoadingImg"></div>';
	result = result + '<table id="projectTaskPagination" class="display" cellspacing="0" width="100%">';
	result = result + '<thead>';
	result = result + '<tr">';
	result = result +'	<th>Id</th>';
	result = result + '<th>Task Name</th>';
	result = result + '<th>Task Type</th>';
	result = result + '<th>Priority</th>';
	result = result + '<th>Assigned To</th>';
	result = result + '<th>Status</th>';
	result = result + '<th>Created Date</th>'; 
	result = result + '<th>Action</th>';
	result = result + '</tr>';
	result = result + '</thead>';
	result = result + '</table>';
	result = result	+ '</div>';
	$("#taskTitledivId").empty();  
	$("#taskTitledivId").append(result);  
	projectTaskPaginations(url);
}
function projectTaskPaginations(url){
	var colIndex = 0;
	var projectClientUUID = '${projectClientUUID}';
    $('#projectTaskPagination').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 10
        }),
        "columns": [
        	{ "data": "Id" },
            { "data": "taskName" },
            { "data": "taskType"},
            { "data": "priority"},
            { "data": "assignedTo" },
            { "data": "createdDate" },
            { "data": "projectTaskStatus" },
            { "data": "projectTaskActin" }
        ],
        "columnDefs": [{
	    	  "targets": colIndex++,
              "data": "taskType",
              "sortable": false,
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  result = result	+ '<td><div class="tblminwidth-30">'+opt.id+ '</div></td>';
	              return result;
                }
	          }, {
	    	  	"targets": colIndex++,
	    	  	"data": "taskName",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.generatedId != null && opt.generatedId != ""  && opt.generatedId != "undefined"){
						generatedId = opt.generatedId + " - ";
					}else{
						generatedId = "";
					}
	    	  		result = result+ '<td><div class="proj_show width215px"><a href="'+sourceleadUrl+'/projectTaskSummary?taskUUID='+opt.projectTasksUUID+'&projectClientUUID='+projectClientUUID+'" title="'+generatedId+' '+ opt.taskName+ '" class="tbldata-dotdot pull-left anchorBlack">'+generatedId+' '+ opt.taskName+ '<i class="fa fa-binoculars pull-right"></i></a></div></td>';
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "taskType",
              "sortable": false,
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  result = result	+ '<td><div class="tbldata-dotdot_80" title="'+opt.taskTypes+'">'+opt.taskTypes+ '</div></td>';
	              return result;
              		
              }
	          },{
            "targets": colIndex++,
            "data": "priority",
            "render": function ( data, type, opt, meta ) {
            	if(opt.taskPriotiry != null && opt.taskPriotiry != "" && opt.taskPriotiry !="undefoned"){
					taskPriotiry = opt.taskPriotiry;
				}else{
					taskPriotiry = "--";
				}
            	var result = "";
            	result = result	+ '<td><div class="tbldata-dotdot_50">'+taskPriotiry+ '</div></td>';
            	 return result;
            }
          }, {
              "targets": colIndex++,
              "data": "assignedTo",
              "sortable": false,
              "render": function ( data, type, opt, meta ) {
            	  if(opt.assignedTaskUsers != null && opt.assignedTaskUsers != "" && opt.assignedTaskUsers !="undefined"){
						taskAssignedUsers = opt.assignedTaskUsers;
					}else{
						taskAssignedUsers = "--"
					}
            	  var result = "";
            	  result = result	+ '<td><div class="tbldata-dotdot" title="'+taskAssignedUsers+'"><span >'+taskAssignedUsers+'</span ></div></td>';
            	  return result;
              }
            }, {
                "targets": colIndex++,
                "data": "projectTaskStatus",
                "sortable": false,
                "render": function ( data, type, opt, meta ) {
                	if(opt.taskStaus != null && opt.taskStaus != "" && opt.taskStaus !="undefined"){
						taskStaus = opt.taskStaus;
					}else{
						taskStaus = "--"
					}
                	var result = "";
                	result = result	+ '<td><div class="tbldata-dotdot_100" title="'+opt.taskStaus+'">';
                	result = result	+ '             <select class="form-control sel" id="taskStatusId'+opt.id+'" onchange="changeTaskStatus('+opt.id+');">';
        			result = result	+ '              <c:forEach var="Status" items="${taskStatusList}">    ';   
        			result = result	+ '		             <option value="${Status.id}" ' + (parseInt("${Status.id}") === opt.taskStausId ? "selected='selected'" : "") + '>${Status.name}</option>';   
        			result = result	+ ' 			 </c:forEach>';  
        			result = result	+ '             </select>'; 
        			result = result	+ '             </div></td>';  
    	              return result;
                }
          },
            {
                "targets": colIndex++,
                "data": "createdDate",
                "sortable": false,
                "render": function ( data, type, opt, meta ) {
                 
                	var result = "";
                	result = result	+ '<td><div class="tblminwidth-30" title="'+opt.createdDateStr+'">'+opt.createdDateStr+'</div></td>';
    	              return result;
                }
          },{
              "targets": colIndex++,
              "data": "projectTaskActin",
              "sortable": false,
              "render": function ( data, type, opt, meta ) {
               
              	var result = "";
              	result = result	+ '<td><div class="tbldata-dotdot_50" title="'+opt.taskStaus+'">';
              	'<sec:authorize access="hasRole(\'ROLE_VIEW_EDIT_PROJECT_TASKS\')">'
	              	result = result	+ '<a class="glyphicon glyphicon-edit" title="Edit" href="'+sourceleadUrl+'/projectTaskSummary?taskUUID='+opt.projectTasksUUID+'&projectClientUUID='+projectClientUUID+'" ></a>'; 
	            '</sec:authorize>';
	            
              	'<sec:authorize access="hasAnyRole(\'ROLE_DELETE_PROJECT_TASK\')">'
              		result = result	+ '<a class="glyphicon glyphicon-trash text-danger ml" title="Delete" onclick="deleteProjectTaskPopUp('+opt.id+');"></a>';
              	'</sec:authorize>';
              	result = result	+ '</div></td>';
  	              return result;
              }
        }],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[0,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#projectTaskPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $('#projectTaskPagination').DataTable().column(0).visible(false);
    $("#taskDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   projectTaskList();
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
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
		type : "POST",
		url : sourceleadUrl	+ "/ProjectTasks/deleteProjectSubTasks?${_csrf.parameterName}=${_csrf.token}&projectTaskId="+projectTaskId,
		success : function(json) {
			    var projectId = $("#projectId").val();
			    var projectStatus = $("#projectStatus").val();
			    $("#taskDeletePopUp").empty();
			 	$(".project_inn_mask").fadeOut(200);
			 	projectTask(projectId,projectStatus);
		}
	});
}
function cancelTermination(){
	$("#taskDeletePopUp").empty();
	 $(".project_inn_mask").fadeOut(200);
}
function changeTaskStatus(projectTaskId){
	var sourceleadUrl = '${sourceleadUrl}';
	var projectClientUUID = '${projectClientUUID}';
	var taskStatusId = $("#taskStatusId"+projectTaskId+"").val();
	  $.ajax({
		type : "POST",
		url : sourceleadUrl	+ "/ProjectTasks/changeTaskStatusByList?${_csrf.parameterName}=${_csrf.token}&taskId="+projectTaskId+"&taskStatusId="+taskStatusId+"&projectClientUUID="+projectClientUUID,
		success : function(json) {
			projectTaskList();
		}
	});  
}
</script>
 
<div class="vendor_wrap_mask"></div>
	
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

<script src="/resources/js/editor.js"></script>
<link href="/resources/css/editor.css" type="text/css" rel="stylesheet"/>

 <script>
 function showtasktype(){
	 var taskType = $("#taskstype").val();
	  if(taskType ==  "General" || taskType == "Epic"){
		 document.getElementById("userStory").style.display="none";
		 document.getElementById("epic").style.display="none";
		 document.getElementById("task").style.display="none";
		 $('#subTasksId').removeAttr("data-validation","required"); 
	 } 
	   if(taskType ==  "UserStory"){
		 document.getElementById("epic").style.display="block";
		 document.getElementById("userStory").style.display="none";
		 document.getElementById("task").style.display="none";
		 $('#subTasksId').removeAttr("data-validation","required"); 
		 getSubEpicsByUserStory();
	 }
	    if(taskType ==  "Bug"){
		 document.getElementById("epic").style.display="none";
		 document.getElementById("userStory").style.display="block";
		 document.getElementById("task").style.display="none";
		 $('#subTasksId').removeAttr("data-validation","required"); 
		 getUserStoryByBug();
	 }
	 if(taskType ==  "SubTask"){
		 document.getElementById("epic").style.display="none";
		 document.getElementById("userStory").style.display="none";
		 document.getElementById("task").style.display="block";
		 $('#subTasksId').attr("data-validation","required"); 
		 getSubTasks(taskType);
	 } 
	 
 }
 </script>
 <script>
 function getSubEpicsByUserStory(type){
	 var projectId = $("#projectId").val();
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
			     			output = output + "<option value='" +opt.id+ "'>"+generatedId+"  "+opt.taskName+"</option>";
		     		}
	    			 
	    			$("#epicId").empty();
		     		$("#epicId").append(output);
		     		$('#epicId').trigger("chosen:updated");
	    		}
	 });
 }
 function getUserStoryByBug(){
	 var projectId = $("#projectId").val();
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
			     			output = output + "<option value='" +opt.id+ "'>"+generatedId+" "+opt.taskName+"</option>";
		     		}
	    				$("#userStroyId").empty();
			     		$("#userStroyId").append(output);
			     		$('#userStroyId').trigger("chosen:updated");
	    		}
	 });
 }
 </script>
 <script>
 function getSubTasks(taskType){
	 var projectId =  "${projectId}";
	var sourceleadUrl ='${sourceleadUrl}';
	var projectTaskId = "${projectTask.id}";
	taskType = "SubTask";
	$.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/ProjectTasks/getProjectTasksBySubTaskType?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&taskType="+taskType,
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
	        			output = output + "<option value='"+opt.id+"'>"+generatedId+" "+opt.taskName+"</option>";
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
    function addTimeEntry(){
        $(".modal-Time").fadeIn();
    }
    function closeTimeEntry(){
        $(".modal-Time").fadeOut();
    } 
    function addtask(){
	   <c:forEach var="section" items="${sourceleadSectionListForProjectTask}" varStatus="sectionOrder">
		  var sectionId = '${section.id}';
		  getCustomDependentFieldProjectTask(sectionId);
	   </c:forEach> 
    	
    	var projectId =  $("#projectId").val();
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
		    result = result	+ '     <c:forEach var="type" items="${taskTypeList}">  ';        
		    result = result	+ '  	 	  <option value="${type.uniqueKey}">${type.name}</option>';   
		    result = result	+ '  		</c:forEach>  ';                                           
			result = result	+ '             </select>'; 
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			result = result	+ '           <div class="form-group" style="display: none" id="epic">';
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
			result = result	+ '           <label class="col-md-3">Assign To <i class="fa fa-info-circle" title="Select the project team members who will handle this task (can be assigned to multiple members)"></i></label>';
			result = result	+ '           <div class="col-md-9">';
			result = result	+ '             <select class="form-control chosen-select choose" multiple  id="assignTaskId" name="assignTaskId" data-placeholder="Select Assign To">';
			result = result	+ '             </select>';
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			result = result	+ '         <div class="form-group">';
			result = result	+ '           <label class="col-md-3">Due Date <i class="fa fa-info-circle" title="The deadline for the assigned project task"></i></label>';
			result = result	+ '           <div class="col-md-9">';
			result = result +'<div class="input-group">';
			result = result	+ '             <input class="form-control calendar" value="" name="duDate" id="dueDate" placeholder="Due Date" type="text" readonly>';
			 result = result +'<span class="input-group-addon" onClick="clearDueDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>';
			 result = result +'</div>';
			 
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			result = result	+ '         <div class="form-group">';
			result = result	+ '           <label class="col-md-3">Priority</label>';
			result = result	+ '           <div class="col-md-9 dropdown-select">';
			result = result	+ '             <select class="form-control" id="priority" name="priority">';
			result = result	+ '           <c:forEach var="priority" items="${taskPriorityList}"> ';        
			result = result	+ '		             <option value="${priority.id}" ${priority.name  == "P2" ? "selected" : ''} >${priority.name}</option>';   
			result = result	+ '			</c:forEach>   ';	
			result = result	+ '             </select>'; 
			result = result	+ '           </div>';
			result = result	+ '         </div>';
			result = result	+ '         <div class="form-group">';
			result = result	+ '           <label class="col-md-3">Estimated Time <i class="fa fa-info-circle" title="The estimated of time taken for the project task"></i></label>';
			result = result	+ '           <div class="col-md-9">';
			result = result	+ '             <input class="form-control"  placeholder="Estimated Time" type="text" id="estimatedTime" name="estimatedTime" field-name="Estimated Time"  data-validation="validate_Space validate_float validate_length length1-10" data-validation-optional="true" >';
			result = result	+ '           </div>';
			result = result	+ '         </div>';
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
				  
			    
			    $('#dueDate').datepicker({
					dateFormat : finalDateFormatProjectSave,
					changeMonth: true,
					changeYear: true, 
					minDate : projectStartDate,
					 
				});
    }
    $(".proj_close, .side_mask").click(function(){
		$(".tasks_side_wrap").removeClass("tasks_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	 
    
  
    
    
    
	var fileCounter = 0;
	function addFileUpload(){
		
		$('<div>').attr('style','float:left; width:325px; border:1px solid #ddd; margin:0 15px 15px 0; padding:5px;overflow: hidden; background-color:#367fa9; color:#fff; ').append('<input id="file'+fileCounter+'"  type="file" class="pull-left" onchange="uploadButton('+fileCounter+');"/>' +
	            '<a id="Button' + fileCounter + '" onclick = "RemoveFileUpload(this)"><i class="fa fa-times btn btn-danger btn-sm btn-sm2 pull-right"></i></a>').appendTo($('#FileUploadContainer'));

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
function getOwnerList(){
	var sourceleadUrl ='${sourceleadUrl}';
	var orgLocationId = '${SourceLeadWeb_orgLocations.id}';
	$.ajax({
	    	type: "GET",
	    	 url: sourceleadUrl+"/getResourceRequestApproversList?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId,
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
    	type: "GET",
        url: sourceleadUrl+"/getProjectEmployeeListById?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&status="+values, 
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
function saveTasks(){
	var ee = $("#txtEditor").val($('.Editor-editor').html()); 
	 if ($('#projectTask_form').validate(false, validationSettings)) {
		var form = $('#projectTask_form')[0];
		var formData = new FormData(form);
		var projectId = $("#projectId").val();
		var userId = '${SourceLeadWeb_userId}';
		var projectClientUUID = '${projectClientUUID}';
		
	  	$("#save_task").attr("disabled", "disabled");
		$("#save_task").addClass('button').val('Processing..');
		$.ajax({
			 type : "POST",
			 data: formData,
	         processData: false,
	         contentType:false, 
	         url : sourceleadUrl + "/ProjectTasks/saveProjectTasks?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&userId="+userId+"&projectClientUUID="+projectClientUUID,
			  success : function(result) {
				  if (result && result.response === "INVALID_DATA") {
	                    showError(result.responseMessage);
	                } else if (result.response === "AWKWARD") {
	                    location.href = "/errorPage";
	                } else if (result.response === "SUCCESS") {
	                	var status = $("#projectStatus").val();
	    				projectTask(projectId, status);
	    				cancelTask();
	                }else if (result.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
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
 
 <script>
function clearDueDate(){
	$("#dueDate").val("");
	};

</script>

<script>
function getCustomDependentFieldProjectTask(sectionId){
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




<!-- ------------------------ Filter related script By Laxman ------------------------------- -->

<script>
function closeFilterTaskPop(){
	$("div.cand-advsrch-popup").hide();
	$(".advancedsearch-mask").fadeOut();
}
function openFilterTaskPop(){
	$("#projTaskPop").toggle();
	 $(".advancedsearch-mask").fadeIn();
}

</script>

  <div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search" id="projTaskPop">
				<h5 class="headerAdsrch">Filter Search
					<a href="javascript:void(0)" class="close-div" onClick="closeFilterTaskPop()"><i
						class="fa fa-close"></i></a>
				</h5>
				<div class="addInner paddingBtm">
					<div class="row">
						<div class="col-md-2">
						<div class="row">
							   <div class="col-md-12 mb">
								  <label><strong>Tasks</strong></label>
									 <div class="clearfix">
										<ul class="list-unstyled" id="taskFilter">
											<li  class="radioIput positionRelative"><input  name="tasksFilter"  checked type="radio" value="my" id="myTasks" ><label>My Tasks</label></li>
											<li  class="radioIput positionRelative"><input  name="tasksFilter"  type="radio" value="all"><label>All Tasks</label></li>
				                        </ul>
									 </div>
								 </div>
						  </div>
							  
						 <div class="row">
						   <div class="col-md-12 mb5">
							  <label><strong>Task Type</strong></label>
								 <div class="clearfix">
									<ul class="list-unstyled" id="taskTypeFilter">
										<c:forEach var="type" items="${taskTypeList}"> 
											<c:if test="${taskTypeList[1] == type || taskTypeList[2] == type}">
												 <li class="checkBox positionRelative"><input  name="taskTypeId" value ="${type.id}"   type="checkbox"><label for="taskTypeId${type.id}">${type.name}</label></li>
											</c:if>
											<c:if test="${taskTypeList[1] != type && taskTypeList[2] != type}">
											<li  class="checkBox positionRelative"><input  name="taskTypeId" value ="${type.id}"   type="checkbox"><label for="taskTypeId${type.id}">${type.name}</label></li>
											</c:if>
										</c:forEach>
			                        </ul>
								 </div>
							 </div>
							 </div>
							 </div> 
							 
							 
							 <div class="col-md-2">
								<div class="row">
									 <div class="col-md-12 mb">
									  <label><strong>Status</strong></label>
										 <div class="clearfix">
											<ul class="list-unstyled" id="taskStatusFilter">
												<c:forEach var="status" items="${taskStatusList}">  
													<c:if test="${taskStatusList[0] == status || taskStatusList[1] == status}">
														 <li class="checkBox positionRelative"><input   name="taskStatusId" value ="${status.id}" checked   id="taskStatusId${status.id}"  type="checkbox"><label for="taskStatusId${status.id}">${status.name}</label></li>
													</c:if>
													<c:if test="${taskStatusList[0] != status  && taskStatusList[1] != status}">
													<li  class="checkBox positionRelative"><input   name="taskStatusId" value ="${status.id}"    type="checkbox"><label for="taskStatusId${status.id}">${status.name}</label></li>
													</c:if>
												</c:forEach>
					                        </ul>
										 </div>
									 </div> 
						        </div>
						     </div>
						     
						     <div class="col-md-3 mb10 positionRelative">
									<div class="clearfix dynamic_search filterDynamicsearch">
										<input type="text" id="myAssignedInput" onkeyup="myAssignedFunction()" placeholder="Assigned To" title="Type in a managers">
										<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
									</div>
									 <div class="clearfix filterSearch-ul">
										<ul class="list-unstyled positionRelative filterSearch mt5" id="myAssignedUL">
<!-- 									        <li class="checkBox positionRelative clearfix" ><input   name="assignedtaskId" value ="unAssigned"  type="checkbox" class="pull-left"><label title="Un-Assigned"  class="tbldata-dotdot-me pull-left mt5">Un Assigned</label></li> -->
										<c:forEach var="assignedMembers" items="${projectAssignMemberList}">  
										    <li class="checkBox positionRelative clearfix" ><input   name="assignedtaskId" value ="${assignedMembers.id}"  type="checkbox" class="pull-left"><label for="assignedtaskId${assignedMembers.id}" title="${assignedMembers.candidateName}"  class="tbldata-dotdot-me pull-left">${assignedMembers.candidateName}</label></li>
										</c:forEach>
										</ul>
									 </div> 
							 </div> 
							 
							 <div class="col-md-3 mb10	 positionRelative">
									<div class="clearfix dynamic_search filterDynamicsearch">
										<input type="text" id="myTaskTeamInput" onkeyup="myTaskTeamFunction()" placeholder="Team" title="Type in a managers">
										<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
									</div>
									 <div class="clearfix filterSearch-ul">
										<ul class="list-unstyled positionRelative filterSearch mt5" id="myTaskTeamUL">
											<c:forEach var="team" items="${teamList}"> 
											<li class="checkBox positionRelative clearfix"><input value="${team.id}" name="teams"  type="checkbox" ><label>${team.name}</label></li>
											</c:forEach>
										</ul>
									 </div> 
							 </div> 
							 
							  <div class="col-md-2 mb positionRelative">
									<div class="clearfix dynamic_search filterDynamicsearch">
										<input type="text" id="myPriorityInput" onkeyup="myPriorityFunction()" placeholder="Priority" title="Type in a priority">
										<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
									</div>
									 <div class="clearfix filterSearch-ul">
										<ul class="list-unstyled positionRelative filterSearch mt5" id="myPriorityUL">
											<c:forEach var="priority" items="${taskPriorityList}"> 
											<li class="checkBox positionRelative clearfix"><input value="${priority.id}" name="priorityFilter"  type="checkbox" ><label>${priority.name}</label></li>
											</c:forEach>
										</ul>
									 </div> 
							 </div> 
							 <div class="clearfix"></div>
							 
							 <div class="col-md-4 positionRelative">
									<div class="clearfix dynamic_search filterDynamicsearch">
										<input type="text" id="myTagsInput" onkeyup="myTagsFunction()" placeholder="Tags" title="Type in a tags">
										<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
									</div>
									 <div class="clearfix filterSearch-ul">
										<ul class="list-unstyled positionRelative filterSearch mt5" id="myTagsTeamUL">
											<c:forEach var="tag" items="${taskTagsList}"> 
											<li class="checkBox positionRelative clearfix"><input value="${tag}" name="tags"  type="checkbox" ><label>${tag}</label></li>
											</c:forEach>
										</ul>
									 </div> 
							 </div> 
							 
							 <div class="col-md-4 positionRelative">
									<div class="clearfix dynamic_search filterDynamicsearch">
										<input type="text" id="myCreatedByInput" onkeyup="myCreatedByFunction()" placeholder="Created By" title="Type in a created By">
										<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
									</div>
									 <div class="clearfix filterSearch-ul">
										<ul class="list-unstyled positionRelative filterSearch mt5" id="myCreatedByUL">
											<c:forEach var="assignedMembers" items="${projectAssignMemberList}">  
											    <li class="checkBox positionRelative clearfix" ><input   name="createdByFilter" value ="${assignedMembers.id}"  type="checkbox" class="pull-left"><label for="createdBy${assignedMembers.id}" title="${assignedMembers.candidateName}"  class="tbldata-dotdot-me pull-left">${assignedMembers.candidateName}</label></li>
											</c:forEach>
										</ul>
									 </div> 
							 </div>  
							 
							 <div class="col-md-3 positionRelative">
									<div class="clearfix dynamic_search filterDynamicsearch">
										<input type="text" id="mySeverityInput" onkeyup="mySeverityFunction()" placeholder="Severity" title="Type in a severity">
										<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
									</div>
									 <div class="clearfix filterSearch-ul">
										<ul class="list-unstyled positionRelative filterSearch mt5" id="mySeverityUL">
											<c:forEach var="severit" items="${taskSeverityList}"> 
											<li class="checkBox positionRelative clearfix"><input value="${severit.id}" name="severityFilter"  type="checkbox" ><label>${severit.name}</label></li>
											</c:forEach>
										</ul>
									 </div> 
							 </div> 
							 
					</div>
					</div>
					<div class="clearfix advancedsearch-footer">
						<div class="pull-right mr">
							<input type="button" value="Apply" id="search_requiremet" onclick="closeFilterTaskPop();projectTaskList();" class="btn btn-sm btn-success" />
							<input type="button" value="Reset" id="clear_search" onclick="resetTaskFilter();" class="btn btn-sm btn-primary" />
						</div>
					</div>
	  </div>
				
<script>
function resetTaskFilter(){
	$("ul#taskTypeFilter > li input:checkbox").prop("checked",false);
	$("ul#taskStatusFilter > li input:checkbox").prop("checked",false);
	$("ul#myAssignedUL > li input:checkbox").prop("checked",false);
	$("ul#myTaskTeamUL > li input:checkbox").prop("checked",false);
	$("ul#myTagsTeamUL > li input:checkbox").prop("checked",false);
	$("ul#myPriorityUL > li input:checkbox").prop("checked",false);
	$("ul#mySeverityUL > li input:checkbox").prop("checked",false);
	$("ul#myCreatedByUL > li input:checkbox").prop("checked",false);
	$("ul#taskFilter > li input:radio").prop("checked",false);
	
	document.getElementById("myTasks").checked = true;
	document.getElementById("taskStatusId1069").checked = true;
	document.getElementById("taskStatusId1070").checked = true;
	projectTaskList();
}
function myAssignedFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myAssignedInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myAssignedUL");
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

function myTaskTeamFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myTaskTeamInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myTaskTeamUL");
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
function myTagsFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myTagsInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myTagsTeamUL");
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

function myPriorityFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myPriorityInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myPriorityUL");
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
function myCreatedByFunction(){
	 var input, filter, ul, li, a, i;
	    input = document.getElementById("myCreatedByInput");
	    filter = input.value.toUpperCase();
	    ul = document.getElementById("myCreatedByUL");
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

function mySeverityFunction(){
	 var input, filter, ul, li, a, i;
	    input = document.getElementById("mySeverityInput");
	    filter = input.value.toUpperCase();
	    ul = document.getElementById("mySeverityUL");
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

<script>
function projectTaskExport(type){
	if(type == "Pdf"){
		exportType = "pdf";
	}else{
		exportType = "xls";
	}
	
	
	var projectClientUUID = '${projectClientUUID}';
	var projectId = $("#projectId").val();
	var status =  $("#projectStatus").val();
	 var projectTaskStatus = [];
	$('input:checkbox[name=taskStatusId]:checked').each(function() 
    {
		projectTaskStatus.push($(this).val());
    });
	
	var projectTaskType = [];
	$('input:checkbox[name=taskTypeId]:checked').each(function() 
     {
		projectTaskType.push($(this).val());
	 });
	
    var assignedMembers = [];
	$('input:checkbox[name=assignedtaskId]:checked').each(function() 
    {
		assignedMembers.push($(this).val());
    });
	
	var teams = [];
	$('input:checkbox[name=teams]:checked').each(function() 
    {
		teams.push($(this).val());
	});
	
	var priorityFilter = [];
	$('input:checkbox[name=priorityFilter]:checked').each(function() 
    {
		priorityFilter.push($(this).val());
	});
	
	var severityFilter = [];
	$('input:checkbox[name=severityFilter]:checked').each(function() 
    {
		severityFilter.push($(this).val());
	});
	
	var createdByFilter = [];
	$('input:checkbox[name=createdByFilter]:checked').each(function() 
    {
		createdByFilter.push($(this).val());
	});
	
	var tasksFilter = [];
	$('input:radio[name=tasksFilter]:checked').each(function() 
    {
		tasksFilter.push($(this).val());
	});
	
	var searchValue = $("#taskDataTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 window.location.href = sourceleadUrl + "/getExportTasks?searchValue="+searchValue+"&projectId="+projectId+"&projectTaskStatus="+projectTaskStatus+"&projectTaskType="+projectTaskType+"&assignedMembers="+assignedMembers+"&teams="+teams+"&exportType="+exportType+"&tasksFilter="+tasksFilter+"&createdByFilter="+createdByFilter+"&severityFilter="+severityFilter+"&priorityFilter="+priorityFilter;

	
}
</script>

