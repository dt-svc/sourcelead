 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
 <link href="resources/css/jquery-ui.css" rel="stylesheet">
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> 
<!-- Modal -->
<div class="modal fade" id="addTask" role="dialog">
	<div class="modal-dialog modal-lg">

		<!-- Modal content-->
		Project name here
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
									<input type="text" id="taskName" name="taskName"
										placeholder="Enter Task Name" class="form-control"
										field-name="Task Name"
										data-validation="required validate_length length2-250">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3">Description</label>
								<div class="col-md-9">
									<textarea class="form-control"
										placeholder="Enter Task Description" id="taskDescription"
										name="taskDescription" rows="2"></textarea>
								</div>
							</div>
						</div>
						<div class="col-xs-12 col-lg-5">
							<a href="javascript:void(0)" class="btn btn-primary"
								id="save_task" onclick="addTasks();" data-target="addTask">
								<i class="fa fa-plus"></i> Add Task
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
		
		$("#tasksLoadingImg").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');

		
		var sourceleadUrl = '${sourceleadUrl}';
		var status = status;
		$.ajax({
					type : "POST",
					url : sourceleadUrl	+ "/getProjectTasks?${_csrf.parameterName}=${_csrf.token}&projectId="+ projectId,
					success : function(data) {
						var json = JSON.parse(data);

						var result = "";
						result = result + '<div class="project_tabs_inn_head">';
						if (status != 'COMPLETED' && status != ''
								&& status != 'undefined' && status != null) {
							result = result	+ '<span class="pad-sea">';
							
							result = result +'<sec:authorize access="hasAnyRole(\'ROLE_CREATE_PROJECT_TASKS\')">'; 
							result = result	+ '<a href="javascript:void(0)" data-toggle="modal" data-target="#addTask" onclick="flushTasks();"><i class="glyphicon glyphicon-plus"></i>Add Task</a>';
		   		    		result = result +'</sec:authorize>';
		   		    		
							result = result	+ '</span>';
						}
						result = result	+ '</div><div class="project_tabs_inn_table project_tabs_inn_table_a">';
						result = result	+ '<div id="tasksLoadingImg"></div>';
						result = result + '<table id="projectTaskPagination" class="display" cellspacing="0" width="100%">';
						result = result + '<thead>';
						result = result + '<tr id="projectTaskTableId">';
						result = result + '<th>Task name</th>';
						result = result + '<th>Task description</th>';
						result = result + '</tr>';
						result = result + '</thead>';
						result = result + '<tbody>';
						for (var i = 0; i < json.length; i++) {
							var opt = json[i];
							result = result + '<tr>';
							
							result = result +'<sec:authorize access="hasAnyRole(\'ROLE_EDIT_PROJECT_TASKS\')">'; 
							result = result+ '<td><div><input type="text" onBlur="inlineEditForProjectsTasks('+ opt.Id+ ',\'taskName\');"  id="taskName'+ opt.Id+ '" value="'+ opt.taskName+ '"  class="project_inline_field"></div></td>';
							result = result +'</sec:authorize>';
							
							result = result +'<sec:authorize access="hasAnyRole(\'ROLE_EDIT_PROJECT_TASKS\')">';
							result = result	+ '<td><div><input type="text" onBlur="inlineEditForProjectsTasks('+ opt.Id+ ',\'description\');" id="description'+ opt.Id+ '" value="'+ opt.description+ '" class="project_inline_field"></div></td>';
							result = result +'</sec:authorize>';
							
							result = result + '</tr>';

						}
						result = result + '</tbody>';
						result = result + '</table>   ';
						result = result + '</div>';
						$("#tab-1").empty();
						$("#tab-3").empty();
						$("#tab-2").empty();
						$("#tasksLoadingImg").empty();
						$("#tab-2").append(result);
						taskPagination();

					}
				});

	}
</script>


<script type="text/javascript">
	function flushTasks() {
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
	<input type="hidden" id="REPLACEROWIDtaskDescription"
		name="REPLACEROWIDtaskDescription" value="TASKDESCRIPTIONVALUE" /><span
		id="REPLACEROWIDtaskDescriptionValue">TASKDESCRIPTIONVALUE</span>
</div>
<div id="divTaskTd3" style="display: none; float: left;">
	<span><a title="Edit" href="javascript:void(0)"
		onclick="editTask(REPLACEROWID)"><i class="fa fa-edit"></i></a></span>&nbsp;&nbsp;&nbsp;
	<span><a title="Remove" href="javascript:void(0)"
		onclick="removeRow(REPLACEROWID)"><i class="fa fa-close"></i></a></span>
</div>
<!-- =============================== AddTask Divs  ============================ -->
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

			var cell0 = row.insertCell(0);
			var divTsTd1Str = document.getElementById("divTaskTd1").innerHTML;
			divTsTd1Str = divTsTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
			divTsTd1Str = divTsTd1Str.replace(/TASKNAMEVALUE/g, (taskName));
			cell0.innerHTML = divTsTd1Str;

			var cell1 = row.insertCell(1);
			var divTsTd2Str = document.getElementById("divTaskTd2").innerHTML;
			divTsTd2Str = divTsTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
			divTsTd2Str = divTsTd2Str.replace(/TASKDESCRIPTIONVALUE/g,
					(taskDescription));
			cell1.innerHTML = divTsTd2Str;

			var cell2 = row.insertCell(2);
			var divTsTd3Str = document.getElementById("divTaskTd3").innerHTML;
			divTsTd3Str = divTsTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
			cell2.innerHTML = divTsTd3Str;

			$("#taskName").val('');
			$("#taskDescription").val('');
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
		$("#taskName").val('');
		$("#taskDescription").val('');
		$("#addTaskText").html('Update Task');
		$("#taskName").val(updateTaskName);
		$("#taskDescription").val(updateTaskDescription);
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
		$("#" + rowId + "taskNameValue").html($("#taskName").val());
		$("#" + rowId + "taskDescriptionValue").html(
				$("#taskDescription").val());
		$("#taskName").val('');
		$("#taskDescription").val('');
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


<script>
	function inlineEditForProjectsTasks(taskId, localFieldName) {
		var fieldName = localFieldName;
		var fieldValue = $("#" + localFieldName + "" + taskId).val();
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
					type : "POST",
					url : sourceleadUrl
							+ "/updateProjectTaskById?${_csrf.parameterName}=${_csrf.token}&taskId="
							+ taskId + "&fieldName=" + fieldName
							+ "&fieldValue=" + fieldValue,
					success : function(data) {

					}
				});
	}
</script>


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
						var json = JSON.parse(data);
						var result = "";
						result = result + '<div class="project_tabs_inn_head">';
						result = result
								+ '<a href="javascript:void(0)" data-toggle="modal" data-target="#addTask" onclick="flushTasks();"><i class="glyphicon glyphicon-plus"></i>Add Task</a></div>';
						result = result
								+ '<div class="project_tabs_inn_table project_tabs_inn_table_a">';
						result = result + '<table>';
						result = result + '<tbody>';
						result = result + '<tr id="projectTaskTableId">';
						result = result + '<th>Task name</th>';
						result = result + '<th>Task description</th>';
						result = result + '</tr>';
						for (var i = 0; i < json.length; i++) {
							var opt = json[i];
							result = result + '<tr>';
							result = result
									+ '<td><div><input type="text" value="'+opt.taskName+'" class="project_inline_field"></div></td>';
							result = result
									+ '<td><div><input type="text" value="'+opt.description+'" class="project_inline_field"></div></td>';
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
<script>
function taskPagination(){  
    $('#projectTaskPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
     	}
    } );
}
</script>
