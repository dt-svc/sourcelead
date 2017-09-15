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
						<table class="table table-bordered table-striped  clearfix"
							id="projectTaskTbl" cols="0">
							<tr id="divTsRow0">
								<th><input type="hidden" id="maxTsRowNum"
									name="maxTsRowNum" value="1" />Task Name</th>
								<th>Type</th>
								<th>Description</th>
								<th>Action</th>
							</tr>
						</table>
					</div>
					<div class="form-horizontal row custom-form">
						<div class="col-xs-12 col-lg-7">
							<div class="form-group" id="taskNamesDIV">
								<label class="col-md-3">Task Name</label>
								<div class="col-md-9">
									<input type="text" id="taskName" name="taskName" onblur="taskNameValidation();" placeholder="Enter Task Name" class="form-control"
										field-name="Task Name" data-validation="required validate_length length2-250">
								</div>
							</div>
							<div class="form-group" id="taskBillableTypeDiv">
								<label class="col-md-3">Type</label>
								<div class="col-md-9">
									<select id="taskBillableType" name="taskBillableType" class="form-control selectpicker">
									  <option value="Billable">Billable</option>
									  <option value="Non-Billable">Non-Billable</option>
									</select>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-3">Description</label>
								<div class="col-md-9">
									<textarea class="form-control"
										placeholder="Enter Task Description" id="taskDescription"
										name="taskDescription" rows="2" field-name="Description Name" data-validation="validate_length length2-250" data-validation-optional="true"></textarea>
								</div>
							</div>
							
						</div>
						<div class="col-xs-12 col-lg-5">
							<a href="javascript:void(0)" class="btn btn-primary"
								id="save_task" onclick="addTasks();" data-target="addTask">
								<i class="fa fa-plus"></i> <span id="addTaskText">Add Task</span>
							</a>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal"
					onclick="saveTasks();">Save</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>
<!-- Modal contente Ends-->


<!-- this script is for Tasks list functionality at Projects screen -->
<script>
	function projectTask(projectId, status) {
		var result = "";
		$("#tasksLoadingImg").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var sourceleadUrl = '${sourceleadUrl}';
		var status = status;
		$.ajax({
					type : "POST",
					url : sourceleadUrl	+ "/getProjectTasks?${_csrf.parameterName}=${_csrf.token}&projectId="+ projectId,
					success : function(data) {
						var json = JSON.parse(data);

						var result = "";
						result = result + '<div class="project_tabs_inn_head">';
						if (status != 'COMPLETED' && status != '' && status != 'undefined' && status != null) {
							result = result	+ '<span class="pad-sea">';
							
							'<sec:authorize access="hasAnyRole(\'ROLE_CREATE_PROJECT_TASKS\')">';  
							result = result	+ '<a href="javascript:void(0)" data-toggle="modal" data-target="#addTask" onclick="flushTasks();"><i class="glyphicon glyphicon-plus"></i>Add Task</a>';
		   		    		'</sec:authorize>'; 
		   		    		
							result = result	+ '</span>';
						}
						result = result	+ '<input type="hidden" id="projectStatus" value="'+status+'">';
						
						
						result = result	+ '</div><div class="project_tabs_inn_table project_tabs_inn_table_a">';
						result = result	+ '<div id="tasksLoadingImg"></div>';
						result = result + '<table id="projectTaskPagination" class="display" cellspacing="0" width="100%">';
						result = result + '<thead>';
						result = result + '<tr id="projectTaskTableId">';
						result = result + '<th>Task name</th>';
						result = result + '<th>Type</th>';
						result = result + '<th>Task description</th>';
						result = result + '<th>Action</th>';
						result = result + '</tr>';
						result = result + '</thead>';
						result = result + '<tbody>';
						for (var i = 0; i < json.length; i++) {
							var opt = json[i];
							result = result + '<tr>';
							'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_PROJECT_TASKS\')">'; 
							result = result+ '<td><div><span title="'+ opt.taskName+ '" >'+ opt.taskName+ '</span></div></td>';
							result = result	+ '<td><div><span>'+ opt.billableType+ '</span></div></td>';
							result = result	+ '<td><div><span title="'+ opt.description+ '">'+ opt.description+ '</span></div></td>';
							result = result	+ '<td><div><span><i class="glyphicon glyphicon-edit" onclick="eidtTasks('+projectId+',\''+status+'\',\''+opt.taskName+'\',\''+opt.billableType+'\',\''+opt.description+'\',\''+opt.Id+'\');"></i></span></div></td>';
							'</sec:authorize>'; 
							
							'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_EDIT_PROJECT_TASKS\')">';
							result = result+ '<td><div><span title="'+ opt.taskName+ '" >'+ opt.taskName+ '</span></div></td>';
							result = result	+ '<td><div><span>'+ opt.billableType+ '</span></div></td>';
							result = result	+ '<td><div><span title="'+ opt.description+ '">'+ opt.description+ '</span></div></td>';
							result = result	+ '<td><div><span><i class="glyphicon glyphicon-edit"></i></span></div></td>';
							'</sec:authorize>'; 
							result = result + '</tr>';

						}
						result = result + '</tbody>';
						result = result + '</table>   ';
						result = result + '</div>';
						$("#tab-1").empty();
						$("#tab-3").empty();
						$("#tab-2").empty();
						$("#tab-4").empty();
						$("#tasksLoadingImg").empty();
						$("#tab-3").append(result);
						$("#tab-3").show();
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
						taskPagination();

					}
				});

	}
</script>


<script type="text/javascript">
	function flushTasks() {
		$("#save_task").show();
		$('#taskName').val('');
		$('#taskNamesDIV').find('p.jquery_form_error_message').remove();
		$("#taskBillableType").val('Billable');
		$('#taskBillableType').selectpicker('refresh');
		var maxRowNum = parseInt($("input[name = 'maxTsRowNum']").val());
		if (maxRowNum != null && maxRowNum != '' && maxRowNum != 'undefined'
				&& maxRowNum != 'NaN') {
			$("#maxTsRowNum").val("1");
			for (var i = 1; i <= maxRowNum; i++) {
				document.getElementById("projectTaskTbl").deleteRow(1);
			}
		}
	}
</script>

<!-- =============================== AddTask Divs  ============================ -->
<div id="divTaskTd1" style="display: none; float: left;">
	<input type="hidden" id="REPLACEROWIDtaskName"
		name="REPLACEROWIDtaskName" value="TASKNAMEVALUE" /><span
		id="REPLACEROWIDtaskNameValue">TASKNAMEVALUE</span>
</div>

<div id="divTaskTd2" style="display: none; float: left;">
	<input type="hidden" id="REPLACEROWIDtaskBillableType"
		name="REPLACEROWIDtaskBillableType" value="TASKBILLABLETYPEVALUE" /><span
		id="REPLACEROWIDtaskBillableTypeValue">TASKBILLABLETYPEVALUE</span>
</div>

<div id="divTaskTd3" style="display: none; float: left;">
	<input type="hidden" id="REPLACEROWIDtaskDescription"
		name="REPLACEROWIDtaskDescription" value="TASKDESCRIPTIONVALUE" /><span
		id="REPLACEROWIDtaskDescriptionValue">TASKDESCRIPTIONVALUE</span>
</div>

<div id="divTaskTd4" style="display: none; float: left;">
	<span><a title="Edit" href="javascript:void(0)"
		onclick="editTask(REPLACEROWID)"><i class="fa fa-edit"></i></a></span>&nbsp;&nbsp;&nbsp;
	<span><a title="Remove" href="javascript:void(0)"
		onclick="removeRow(REPLACEROWID)"><i class="fa fa-close"></i></a></span>
</div>
<!-- =============================== AddTask Divs  ============================ -->
<div class="vendor_wrap_mask"></div>
<div id="editTaskId"></div>
	
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
<!-- Add Task Script start -->
<script type="text/javascript">
	function addTasks() {

		if ($('#project_form').validate(false, validationSettings)) {
			var tbl = document.getElementById("projectTaskTbl");
			var maxRowNum = parseInt($("input[name = 'maxTsRowNum']").val());
			$("input[name = 'maxTsRowNum']").val(maxRowNum + 1);
			$("#addTaskCount").html(maxRowNum);
			var row = tbl.insertRow(maxRowNum);
			row.setAttribute("id", "divTsRow" + maxRowNum);

			var taskName = $('#taskName').val();
			var taskDescription = $('#taskDescription').val();
			var taskBillableType = $('#taskBillableType').val();

			var cell0 = row.insertCell(0);
			var divTsTd1Str = document.getElementById("divTaskTd1").innerHTML;
			divTsTd1Str = divTsTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
			divTsTd1Str = divTsTd1Str.replace(/TASKNAMEVALUE/g, (taskName));
			cell0.innerHTML = divTsTd1Str;
			
			var cell1 = row.insertCell(1);
			var divTsTd2Str = document.getElementById("divTaskTd2").innerHTML;
			divTsTd2Str = divTsTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
			divTsTd2Str = divTsTd2Str.replace(/TASKBILLABLETYPEVALUE/g,
					(taskBillableType));
			cell1.innerHTML = divTsTd2Str;

			var cell2 = row.insertCell(2);
			var divTsTd3Str = document.getElementById("divTaskTd3").innerHTML;
			divTsTd3Str = divTsTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
			divTsTd3Str = divTsTd3Str.replace(/TASKDESCRIPTIONVALUE/g,
					(taskDescription));
			cell2.innerHTML = divTsTd3Str;

			var cell3 = row.insertCell(3);
			var divTsTd4Str = document.getElementById("divTaskTd4").innerHTML;
			divTsTd4Str = divTsTd4Str.replace(/REPLACEROWID/g, (maxRowNum));
			cell3.innerHTML = divTsTd4Str;

			$("#taskName").val('');
			$("#taskDescription").val('');
			$("#taskBillableType").val('Billable');
			$('#taskBillableType').selectpicker('refresh');
			return true;
		} else {
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
		var updateTaskName = $("#" + rowId + "taskName").val();
		var updateTaskDescription = $("#" + rowId + "taskDescription").val();
		var updateTaskBillableType = $("#" + rowId + "taskBillableType").val();
		$("#taskName").val('');
		$("#taskDescription").val('');
		$("#addTaskText").html('Update Task');
		$("#taskName").val(updateTaskName);
		$("#taskDescription").val(updateTaskDescription);
		$("#taskBillableType").val(updateTaskBillableType);
		$('#taskBillableType').selectpicker('refresh');
		$("#taskId").val(rowId);
		$("#save_task").removeAttr('onclick');
		$("#save_task").attr('onclick', 'updateTask(' + rowId + ');');
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
		$("#" + rowId + "taskName").val($("#taskName").val());
		$("#" + rowId + "taskDescription").val($("#taskDescription").val());
		$("#" + rowId + "taskBillableType").val($("#taskBillableType").val());
		
		$("#" + rowId + "taskNameValue").html($("#taskName").val());
		$("#" + rowId + "taskDescriptionValue").html($("#taskDescription").val());
		$("#" + rowId + "taskBillableTypeValue").html($("#taskBillableType").val());
		
		$("#taskName").val('');
		$("#taskDescription").val('');
		$("#taskBillableType").val('Billable');
		$('#taskBillableType').selectpicker('refresh');
		$("#addTaskText").html('Add Task');
		$("#save_task").removeAttr('onclick');
		$("#save_task").attr('onclick', 'addTasks();');

	}
</script>
<script>
	$(document).ready(function() {
		$("#maxTsRowNum").val('1');
		document.getElementById("project_form").reset();
	});
</script>
<!-- Add Task Script Ends -->

<!-- this script is for Save Tasks functionality at Projects screen -->
<script type="text/javascript">
	function saveTasks() {
		var sourceleadUrl = '${sourceleadUrl}';
		var projectId = $("#projectId").val();
		var frm = $('#project_form').serializeFormJSON();
		var con = JSON.stringify(frm);
		con = con.replace(/[{}]/g, "");
		//It will escape all the special characters
		var jsonData = encodeURIComponent(con);

		$.ajax({
					type : "POST",
					url : sourceleadUrl + "/saveProjectTasks?${_csrf.parameterName}=${_csrf.token}&json="+ jsonData + "&projectId=" + projectId,
					success : function(data) {
						var status = $("#projectStatus").val();
						projectTask(projectId, status);
					},
				});
		

	}
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
function eidtTasks(projectId,status,taskName,billableType,description,taskId){
	 var result = "";
	result = result + '	<div class="settPop">';
	result = result + '<form id="editTask_form" name="editTask_form">';
	result = result + '	<h3>Edit Task<i class="fa fa-times" onclick="editCancel();"></i></h3>  ';
	result = result + '	<div class="settPopInn"> ';
	result = result + '		<div class="form-group col-md-6"> ';
	result = result + '		  <label class="col-md-12" for="Project Id">Task Name <span class="text-danger">*</span></label> ';
	result = result + '		  <div class="col-md-12" id="editTaskNamesDIV"> ';
	result = result + '			<input type="text" class="form-control" name="taskName" id="editTaskName" value="'+taskName+'" onkeyup="validateTaskName('+projectId+','+taskId+');" data-validation="required validate_Space validate_length length2-100" field-name="Task Name"> ';
	result = result + '		   </div>';
	result = result + '		</div>  ';
	result = result + '		<div class="form-group col-md-6">   ';
	result = result + '		  <label class="col-md-12" for="Project Id">Type</label> ';
	result = result + '		  <div class="col-md-12"> ';
	result = result	+ '         <select class="form-control" id="billableType" name="billableType">';
	result = result  +' 			<option '+(billableType == "Billable" ? "selected" : '') +'>Billable</option>';
    result = result  +'				<option '+(billableType == "Non-Billable" ? "selected" : '') +'>Non-Billable</option>';
	result = result + ' 		</select>';
	result = result + '		</div>  ';
	result = result + '		</div> ';
	result = result + '		<div class="form-group col-md-12">  ';
	result = result + '		  <label class="col-md-12" for="Project Id">Task description</label> ';
	result = result + '		  <div class="col-md-12 taskdesp">   ';
	result = result + '			<textarea class="form-control" name="description" id="description" value="'+description+'" colspan="5">'+description+'</textarea>   ';
	result = result + '			<span class="hidden">Enter Project Id</span> </div>  ';
	result = result + '		</div>   ';
	result = result + '		<div class="col-md-12 mt5 clearfix"> ';
	result = result + '		<div class="pull-right">  ';
	result = result + '		  <input type="button" value="Save" class="btn btn-primary" onclick="saveUpdateTask('+taskId+','+projectId+',\''+status+'\');">';
	result = result + '		  <input type="button" value="Cancel" class="btn btn-default ml10" onclick="editCancel();">';
	result = result + '		</div>';
	result = result + '		</div>';
	result = result + '		<div class="clearfix"></div>   ';
	result = result + '	</div>  ';
	result = result + '</form>';
	result = result + '	</div>';
    $("#editTaskId").empty();
    $("#editTaskId").append(result);
    
	$(".settPop").slideDown(200);
	$(".vendor_wrap_mask").fadeIn(200);
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