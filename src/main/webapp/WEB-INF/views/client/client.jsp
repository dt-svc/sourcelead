<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
 
 
<style>
.clsDatePicker {
	z-index: 100000 !important;
}
</style>

<!-- Content Area -->
<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Create Client</h2>
		</div>
		<div class="pull-right brud-crum"><a href="dashboard">Home</a>&raquo;<a href="clientDashboard"> Client List</a>
			&raquo; Client</div>
	</section>
	<div class="content-area clearfix">
		<section class="action-btns clearfix">
			<ul class="pull-right list-unstyled list-inline">
				<li><a href="#" class="faclose"><i aria-hidden="true"
						class="fa fa-close"></i></a></li>
				<li><a href="#" class="collpase-div"><i aria-hidden="true"
						class="fa fa-angle-down"></i></a></li>
			</ul>
		</section>

		<div class="clearfix form-area form-horizontal toggle-div remove-div">
			<form:form id="client_form" name="client_form" method="post">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
						<div class="form-group" ><label class="col-md-3" for="Project Id">Client Name<span class="text-danger">*</span></label>
							<div class="col-md-9" id="clientNameDiv">
								<input type="text" class="form-control" id="orgName" name="orgName" onblur="validateClientName();" field-name="Client Name" data-validation="required validate_Space validate_length length2-250" value="">
								<span class="hidden">Enter Client Name</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3">Client Country</label>
							<div class="col-md-9">
								<input type="text" class="form-control" id="country"
									name="country" field-name="Client Country" value="" data-validation="validate_Space" data-validation-optional="true"> <span
									class="hidden">Client Country</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3">Client State</label>
							<div class="col-md-9">
								<input type="text" id="state" name="state"
									field-name="Client State" class="form-control" value="" data-validation="validate_Space" data-validation-optional="true">
								<span class="hidden">Client State</span>
							</div>
						</div>


						<div class="form-group">
							<label class="col-md-3">Client City</label>
							<div class="col-md-9">
								<input type="text" class="form-control" id="city" name="city"
									field-name="Client City" value="" data-validation="validate_Space" data-validation-optional="true"> <span class="hidden">Client
									City</span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3">Projects</label>
							<div class="col-md-9">
								<div class="input-group">
									<div class="input-group-btn">
										<button class="btn btn-default" type="button">
											<span class="badge" id="addTaskCount">0
										</button>
										<button class="btn btn-primary" type="button" data-toggle="modal"  onclick="flushProject();" data-target="#addTask"> <i class="fa fa-plus" title="Add Tasks"></i> &nbsp;&nbsp;Add Projects
										</button>
									</div>
								</div>
								<!-- Modal -->

								<div class="modal fade" id="addTask" role="dialog">
									<div class="modal-dialog modal-lg">
										<!-- Modal content-->
										
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal">&times;</button>
												<h4 class="modal-title">Add Project</h4>
											</div>
											<div class="modal-body clearfix">
												<div class="modal-body-custom custom-table">
													<table class="table table-bordered table-striped  clearfix"
														id="projectTaskTbl" cols="0">
														<tr id="divTsRow0">
															<th><input type="hidden" id="maxTsRowNum"
																name="maxTsRowNum" value="1" />Project Id</th>
															<th>Project Name</th>
															<th>Manager</th>
															<th>Start Date</th>
															<th>End Date</th>
															<th>Action</th>
														</tr>
													</table>
												</div>
												<div class="form-horizontal row custom-form">
													<div class="col-xs-12 col-lg-7">
														<div class="form-group" id="ProjectIdDiv">
															<label class="col-md-3">Project Id</label>
															<div class="col-md-9">
																<input type="text" value="" id="projectId"
																	name="projectId" placeholder="Enter Project Id"
																	class="form-control" onblur="validateProjectId();">
															</div>
														</div>

														<div class="form-group" id="ProjectNameDiv">
															<label class="col-md-3">Project Name<span class="text-danger">*</span></label>
															<div class="col-md-9">
																<input type="text" value="" id="projectName"
																	name="projectName" placeholder="Enter Project Name"
																	class="form-control" field-name="Project Name" data-validation="validate_Space validate_length length2-250" data-validation-optional="true">
																	<span class="hidden">Enter Project Name</span>
															</div>
														</div>
														<div class="form-group">
															<label class="col-md-3" for="Project Manager">Manager<span class="text-danger">*</span></label>
															<div class="col-md-9">
																<div class="form-icon-min">
																	<!-- <input id="projectManager" name="projectManager"/>  -->
																	<select class="form-control select-bg" name="projectManager" id="projectManager" onchange="getLevel2Approvers();" data-validation="validate_Space" data-validation-optional="true">
																		<option value="">-Select Manager-</option>
																		<c:forEach var="option" items="${user_List}">
																			<option value="${option.id}&${option.firstName}${option.lastName}">${option.firstName} ${option.lastName}</option>
																		</c:forEach>
																	</select> <span class="btn btn-default formic1 addLevel" onclick="getLevel2Approvers();"">Add Level-II</span>
																</div>
																<span class="hidden">Select Project Manager</span>
															</div>
														</div>
														<div class="form-group level-second">
															<label class="col-md-3" for="Level-II">Level-II</label>
															<div class="col-md-9">
																<select class="form-control select-bg" name="level2Manager" id="level2Manager">
																	<option value=''>-- Select Manager --</option>id="companyWebsiteDiv"
																	<c:forEach var="option" items="${user_List}">
																		<option value="${option.id}">${option.firstName} ${option.lastName}</option>
																	</c:forEach>
																</select>
															</div>
														</div>

														<div class="form-group" id="ProjectStartDateDiv">
															<label class="col-md-3">Date<span class="text-danger">*</span></label>
															<div class="col-md-9">
																<div class="row">
																	<div class="col-lg-6 positionRelative">
																		
																     <input type="text" class="form-control" value="" id="projectStartDate" name="StartDate" field-name="Start Date" readonly="readonly" >
																	 <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>
																	 <span class="hidden">Enter Start Date</span>																			
																	</div>
																	<div class="col-lg-6 positionRelative">
																		
																	<input type="text" class="form-control" value="" id="projectEndDate" name="EndDate"  readonly="readonly" >
																		<span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span> 
																	</div>
																</div>
															</div>
														</div>

													</div>
													
												</div>
											</div>
											
											<div class="modal-footer">
											<div class="col-xs-8 col-lg-11">
											 <a href="javascript:void(0)" class="btn btn-primary"
															id="save_task" onclick="addTasks();flushProject();"
															data-target="addTask"> <i class="fa fa-plus"></i><span
															id="addTaskText"> Add Project</span></a>
											</div>
												<button type="button" class="btn btn-default"
													data-dismiss="modal">Close</button>
											</div>
										</div>
									</div>
								</div>
								<!-- Modal contente Ends-->

							</div>
						</div>


					</div>
					<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
						<div class="form-group " id="companyWebsiteDiv">
							<label class="col-md-3">Website</label>
							<div class="col-md-9">
								<input type="text" class="form-control" id="webSite"
									name="webSite" onblur="validateWebsite();" field-name="Website"
									data-validation="validate_Space validate_length length2-100 validate_domain"
									data-validation-optional="true" value=""> <span
									class="hidden">Website</span>
							</div>
						</div>
						<div class="form-group" id="EmailIdDiv">
							<label class="col-md-3" for="Project Manager">Email<span class="text-danger">*</span></label>
							<div class="col-md-9">
								<input type="text" class="form-control" id="EmailId" name="EmailId" value="" field-name="Email Id" data-validation="required validate_Space validate_email" onKeyUp="validateClientEmail();">
                                <span class="hidden">Valid Email</span>
							</div>
						</div>
						

						<div class="form-group">
							<label class="col-md-3">Mobile Number</label>
							<div class="col-md-9">
								<input type="text" class="form-control" id="phone" name="phone"
									data-validation="validate_Space validate_phone  validate_length length9-20"
									data-validation-optional="true" field-name="Mobile Number"
									value=""> <span class="hidden">Mobile Number</span>
							</div>
						</div>


					</div>
				</div>

				<div class="form-footer clearfix row">
					<div class="col-lg-12">
						<div class="pull-right">
							<input type="button" value="Save" id="save_client" onclick="saveClient();" class="btn btn-primary"> 
							<input type="button" value="Cancel" onClick="javascript:location.href = '<c:url value='/clientDashboard'/>'" class="btn btn-default ml">
						</div>
					</div>
				</div>
			</form:form>
		</div>

	</div>
</section>
<!-- Content Area Ends-->

<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->

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

<script type="text/javascript">
	function saveClient() {
		var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
		if ($('#client_form').validate(false, validationSettings)) {
			var frm = $('#client_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			  //It will escape all the special characters
	        var jsonData = encodeURIComponent(con);
			var sourceleadUrl = '${sourceleadUrl}';
			$("#save_client").attr("disabled", "disabled");
			$("#save_client").addClass('button').val('Processing..');
			$
					.ajax({
						type : "POST",
						url : sourceleadUrl
								+ "/saveClient?${_csrf.parameterName}=${_csrf.token}&json="
								+ jsonData,
						success : function(result) {
							if (result == 'clientDashboard') {
								location.replace(sourceleadUrl
										+ "/clientDashboard");
							}

						},
					});
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#client_form', function() {
		$("#client_form").showHelpOnFocus();
		$("#client_form").validateOnBlur(false, validationSettings);
	});
</script>



<!-- =============================== AddTask Divs  ============================ -->

<div id="divTaskTd1" style="display: none; float: left;">
	<input type="hidden" id="REPLACEROWIDprojectId" name="REPLACEROWIDprojectId" value="PROJECTIDVALUE" />
	<span  class="tbldata-dotdot-vsm" id="REPLACEROWIDprojectIdValue" title='PROJECTIDVALUE'>PROJECTIDVALUE</span>
</div>

<div id="divTaskTd2" style="display: none; float: left;">
	<input type="hidden" id="REPLACEROWIDprojectName" name="REPLACEROWIDprojectName" value="TASKNAMEVALUE" />
	<span class="tbldata-dotdot-vsm" id="REPLACEROWIDprojectNameValue" title='TASKNAMEVALUE'>TASKNAMEVALUE</span>
</div>
<div id="divTaskTd3" style="display: none; float: left;">
	<input type="hidden" id="REPLACEROWIDprojectManager" name="REPLACEROWIDprojectManager" value="TASKDESCRIPTIONVALUE" />
	<span class="tbldata-dotdot-vsm" id="REPLACEROWIDprojectManagerValue" title='TASKDESCRIPTIONName'>TASKDESCRIPTIONName</span>
</div>

<div id="divTaskTd4" style="display: none; float: left;">
	<input type="hidden" id="REPLACEROWIDprojectStartDate" name="REPLACEROWIDprojectStartDate" value="PROJECTSTARTDATEVALUE" />
	<span id="REPLACEROWIDprojectStartDateValue">PROJECTSTARTDATEVALUE</span>
</div>

<div id="divTaskTd5" style="display:none; float:left;">
     <input type="hidden" id="REPLACEROWIDprojectEndDate" name="REPLACEROWIDprojectEndDate" value="PROJECTENDDATEVALUE"/>
     <span id="REPLACEROWIDprojectEndDateValue">PROJECTENDDATEVALUE</span> 
</div> 

<div id="divTaskTd6" style="display: none; float: left;">
	<span><a title="Edit" href="javascript:void(0)"onclick="editTask(REPLACEROWID)"><i class="fa fa-edit"></i></a></span>&nbsp;&nbsp;&nbsp;
	<span><a title="Remove" href="javascript:void(0)" onclick="removeRow(REPLACEROWID)"><i class="fa fa-close"></i></a></span>
</div>



<!-- =============================== AddTask Divs  ============================ -->

<!--  Date picker starts here -->

<script>
  $( function() {
	  $('#projectStartDate').datepicker({
	      dateFormat: 'yy-mm-dd',
	      onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() + 1);
	            $("#projectEndDate").datepicker("option", "minDate", dt);
	        },
	        onClose: function() {
	  		  $('#ProjectStartDateDiv').find('p.jquery_form_error_message').remove();
	        }
	  });
	  
	  $('#projectEndDate').datepicker({
	      dateFormat: 'yy-mm-dd',
	        onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() - 1);
	            $("#projectStartDate").datepicker("option", "maxDate", dt);
	        }
	  });
	  
  } );
</script>
<!--  Date picker ends here -->
<!-- Add Task Script start -->
<script type="text/javascript">
	function addTasks() {
		if(projectvalidation()==false)
			{
			return false;
			}
		
		var tbl = document.getElementById("projectTaskTbl");
		var maxRowNum = parseInt($("input[name = 'maxTsRowNum']").val());
		$("input[name = 'maxTsRowNum']").val(maxRowNum + 1);
		$("#addTaskCount").html(maxRowNum);
		var row = tbl.insertRow(maxRowNum);
		row.setAttribute("id", "divTsRow" + maxRowNum);

		var projectId = $('#projectId').val();
		var projectName = $('#projectName').val();
		var projectManager = $('#projectManager').val();
		var projectStartDate = $('#projectStartDate').val();
		var projectEndDate = $('#projectEndDate').val();

		var cell0 = row.insertCell(0);
		var divTsTd1Str = document.getElementById("divTaskTd1").innerHTML;
		divTsTd1Str = divTsTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
		divTsTd1Str = divTsTd1Str.replace(/PROJECTIDVALUE/g, (projectId));
		cell0.innerHTML = divTsTd1Str;

		var cell1 = row.insertCell(1);
		var divTsTd2Str = document.getElementById("divTaskTd2").innerHTML;
		divTsTd2Str = divTsTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
		divTsTd2Str = divTsTd2Str.replace(/TASKNAMEVALUE/g, (projectName));
		cell1.innerHTML = divTsTd2Str;

		var cell1 = row.insertCell(2);
		var divTsTd2Str = document.getElementById("divTaskTd3").innerHTML;
		divTsTd2Str = divTsTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
		var mgr = projectManager.split("&");
		divTsTd2Str = divTsTd2Str.replace(/TASKDESCRIPTIONVALUE/g,(mgr[0]));
		divTsTd2Str = divTsTd2Str.replace(/TASKDESCRIPTIONName/g,(mgr[1]));
		cell1.innerHTML = divTsTd2Str;

		var cell1 = row.insertCell(3);
		var divTsTd2Str = document.getElementById("divTaskTd4").innerHTML;
		divTsTd2Str = divTsTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
		divTsTd2Str = divTsTd2Str.replace(/PROJECTSTARTDATEVALUE/g,(projectStartDate));
		cell1.innerHTML = divTsTd2Str;

		var cell1 = row.insertCell(4);
		var divTsTd2Str = document.getElementById("divTaskTd5").innerHTML;
		divTsTd2Str = divTsTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
		divTsTd2Str = divTsTd2Str.replace(/PROJECTENDDATEVALUE/g, (projectEndDate));
		cell1.innerHTML = divTsTd2Str; 

		var cell2 = row.insertCell(5);
		var divTsTd3Str = document.getElementById("divTaskTd6").innerHTML;
		divTsTd3Str = divTsTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell2.innerHTML = divTsTd3Str;

	
		
		$("#projectId").val('');
		$("#projectName").val('');
		$("#projectManager").val('');
		$("#projectStartDate").val('');
		$("#projectEndDate").val('');
		addtasksCountafterAddingTask();
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
	function editTask(rowId) {
		var updateProjectId = $("#" + rowId + "projectId").val();
		var updateTaskName = $("#" + rowId + "projectName").val();
		var updateprojectManager = $("#" + rowId + "projectManager").val();
		var updateProjectStartDate = $("#" + rowId + "projectStartDate").val();
		var updateProjectEndDate = $("#"+rowId+"projectEndDate").val(); 
		
		
		$("#projectManager").empty();
		var outPut= "";
		<c:forEach var="option" items="${user_List}">
		        var localId = "${option.id}";
		               if(localId == updateprojectManager){
		            	   outPut = outPut +'<option value="${option.id}&${option.firstName}" selected >${option.firstName}</option>';  
		               }else{
		            	   outPut = outPut +'<option value="${option.id}&${option.firstName}">${option.firstName}</option>';
		               }
		     
		</c:forEach>
		$("#projectManager").append(outPut);
		
	
		
		$("#projectId").val('');
		$("#projectName").val('');
		/* $("#projectManager").val(''); */
		$("#projectStartDate").val('');
		$("#projectEndDate").val('');
		$("#addTaskText").html('Update Task');
		$("#projectId").val(updateProjectId);
		$("#projectName").val(updateTaskName);
		/* $("#projectManager").val(updateprojectManager); */
		$("#projectStartDate").val(updateProjectStartDate);
		$("#projectEndDate").val(updateProjectEndDate);
		$("#taskId").val(rowId);
		$("#save_task").removeAttr('onclick');
		$("#save_task").attr('onclick', 'updateTask(' + rowId + ');');
	}
</script>

<script type="text/javascript">
	function updateTask(rowId) {
		
		var mgr = projectManager.val();
		var mgr = projectManager.split("&");

		
		//$("#"+rowId+"projectManager").val(); 
		$("#" + rowId + "projectIdValue").html($("#projectId").val());

		$("#" + rowId + "projectNameValue").html($("#projectName").val());

		$("#" + rowId + "projectManagerValue").html(mgr[1]);

		$("#" + rowId + "projectStartDateValue").html(
				$("#projectStartDate").val());

		$("#" + rowId + "projectEndDateValue").html($("#projectEndDate").val());
		$("#projectId").val('');
		$("#projectName").val('');
		$("#projectManager").val('');
		$("#projectStartDate").val('');
		$("#projectEndDate").val('');
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



<script type="text/javascript">
	function validateProjectId() {
		var ProjectSequeneNumber = document.getElementById("projectId").value;
		var sourceleadUrl = '${sourceleadUrl}';
		$('#ProjectIdDiv').find('p.jquery_form_error_message').remove();
		if (ProjectSequeneNumber != null && ProjectSequeneNumber != "") {
			$
					.ajax({
						type : "POST",
						url : sourceleadUrl
								+ "/validateProjectSequeneNumberId?${_csrf.parameterName}=${_csrf.token}&ProjectSequeneNumber="
								+ ProjectSequeneNumber,
						success : function(data) {
							if (data == true) {
								$("input#projectId").after(
										"<p class='jquery_form_error_message'>"
												+ ProjectSequeneNumber
												+ " is Already Existed</span>");
								document.getElementById("projectId")
										.setAttribute('record-exist', 'yes');
								document
										.getElementById("projectId")
										.setAttribute(
												'record-exist-errorMsg',
												projectId
														+ ' is Already Existed');
							} else {
								$('input#projectId').removeAttr(
										"record-exist record-exist-errormsg");
								$('#ProjectIdDiv').find(
										'p.jquery_form_error_message').remove();
							}
						},
					});
		}
	}
</script>


<script type="text/javascript">
function addtasksCountafterRemoving(){
	
var tasksCount =  $("#maxTsRowNum").val();
var count = tasksCount;
var addtaskcount =null;
 for(i = 0 ;i<count;i++){
	 
		if($('#divTsRow'+i).filter(function() {
		    return $(this).css('display') !== 'none';
		}).length){
			addtaskcount++
		}
 }
 $("#addTaskCount").html(addtaskcount-2); 
 
}
</script> 

<script type="text/javascript">
function addtasksCountafterAddingTask(){
	
var tasksCount =  $("#maxTsRowNum").val();
var count = tasksCount;
var addtaskcount =null;
 for(i = 0 ;i<count;i++){
	 
		if($('#divTsRow'+i).filter(function() {
		    return $(this).css('display') !== 'none';
		}).length){
			addtaskcount++
		}
 }
 $("#addTaskCount").html(addtaskcount-1); 
 
}
</script> 

<script type="text/javascript">
 function removeRow(rowId) {
	 addtasksCountafterRemoving()
		document.getElementById('divTsRow' + rowId).style.display = 'none';
		  var r = document.getElementById('projectTaskTbl').rows[rowId];
             var count = 2*(r.cells.length);
			    for(i=0;i<=count;i++){
			        r.deleteCell(0);
			    }
	}
 
</script>


<script type="text/javascript">
function getLevel2Approvers(fieldName){
	   var managerID = document.getElementById("projectManager").value;
		var mgr = managerID.split("&");
		var currentApproverId = mgr[0];
    	 getChildLevel1Approvers(currentApproverId); 
} 
</script>


<script type="text/javascript">
function getChildLevel1Approvers(currentApproverId){
	var sourceleadUrl ='${sourceleadUrl}';
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
	 }else{
		 getApproversLevel2();
	 }
} 
</script> 

<script type="text/javascript">
function flushProject(){
	 $('#level2Manager').val('');
	 $('#projectId').val('');
	 $('#projectName').val('');
	 $('#projectManager').val('');
	 $('#projectStartDate').val('');
	 $('#projectEndDate').val('');
}
</script>



<script type="text/javascript">
function projectvalidation(){
	
	/*  validation for projectId start */
		var namelength = document.getElementById("projectId").value.length;
		$('#ProjectIdDiv').find('p.jquery_form_error_message').remove();
		if ($('#projectId').val() == '' || $('#projectId').val() == 0 || $('#projectId').val() == null) {
			$("input#projectId").after("<p class='jquery_form_error_message'> <span>Enter Project Id </span> ");
			return false;
		}
		 else if ((namelength < 2) || (namelength >= 100)) {
			$("input#projectId").after("<p class='jquery_form_error_message'><span>The values Entered must be between2-100 characters</span>");
			return false;
		}
		/* validation for projectId end */

		/*  validation for project name start */
		$('#ProjectNameDiv').find('p.jquery_form_error_message').remove();
		if ($('#projectName').val() == '' || $('#projectName').val() == 0|| $('#projectName').val() == null) {
			$("input#projectName").after("<p class='jquery_form_error_message'><span>Enter Project Name</span>");
			return false;
		}
		var namelength = document.getElementById("projectName").value.length;
		if ((namelength < 2) || (namelength >= 100)) {
			$("input#projectName").after("<p class='jquery_form_error_message'><span>The values Entered must be between2-100 characters</span>");
			return false;
		}
		/*validation for project name end */

		/*validation for project Start Date */
		$('#ProjectStartDateDiv').find('p.jquery_form_error_message').remove();
		if ($('#projectStartDate').val() == ''|| $('#projectStartDate').val() == 0	|| $('#projectStartDate').val() == null) {
			$("input#projectStartDate").after("<p class='jquery_form_error_message'><span>Enter Project Start Date</span>");
			return false;
		}
		/* validation for project Start Date end */
	}
</script>

<script type="text/javascript">
function validateClientName(){
	var clientName = document.getElementById("orgName").value;
	var sourceleadUrl = '${sourceleadUrl}';
	var sourceleadBLockedwebsites = '${sourceLeadBlockedSocialDomains}';
	$('#clientNameDiv').find('p.jquery_form_error_message').remove();
	if (clientName != null && clientName != '') {
		//$("#save_client").attr("disabled", "disabled");
		// $( "#save_client" ).addClass('button').val('Processing..');
		$.ajax({
			type : "GET",
			url : sourceleadUrl + "/ws/isClientNameValid?${_csrf.parameterName}=${_csrf.token}&clientName="+clientName,
			success : function(data) {
				var result = data;
				if (result == true) {
					//duplicate client
					$("input#orgName").after(
							"<p class='jquery_form_error_message'>"
									+ clientName
									+ " is Already Existed</span>");
					document.getElementById("orgName").setAttribute(
							'record-exist', 'yes');
					document.getElementById("orgName").setAttribute(
							'record-exist-errorMsg',
							clientName +"  already existed");
				} else {
					//New WebSite class="form-control"
					//$("#save_client").removeAttr("disabled");
					$('input#orgName').removeAttr("class record-exist record-exist-errormsg");
					$('input#orgName').attr('class','valid form-control');
					$('#clientNameDiv').find('p.jquery_form_error_message').remove();
					$("#save_client").click(function() {
						if ($(this).validate(false, validationSettings)) {
						}
					});
				}
			},

		});
	}
}
</script>



<script type="text/javascript">
 function validateClientEmail() {
	 var EmailId = document.getElementById("EmailId").value; 
	 $('#EmailIdDiv').find('p.jquery_form_error_message').remove();
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(EmailId != null && EmailId != ""){
		/*  $("#save_client").attr("disabled", "disabled");
         $("#save_client" ).addClass('button').val('Processing..'); */
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getClientEmailVerification?${_csrf.parameterName}=${_csrf.token}&EmailId="+EmailId, 
		        success: function(data) {
		        	if(data == 'true'){
		        		  $('#EmailIdDiv').find('p.jquery_form_error_message').remove();
						  $("input#EmailId").after("<p class='jquery_form_error_message'>"+EmailId+" is Already Existed</span>");
						  document.getElementById("EmailId").setAttribute('record-exist','yes');
						  document.getElementById("EmailId").setAttribute('record-exist-errorMsg',EmailId+' is Already Existed'); 
		        	}
		        	else{
		        		 $('input#EmailId').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#EmailIdDiv').find('p.jquery_form_error_message').remove(); 
		        		 $("#save_client" ).addClass('button').val('Save');
                         $("#save_client").removeAttr("disabled");
		        	}
		        },
		    });
	 }
 }
 </script>     

 
 <!-- <script type="text/javascript">
	function validateWebsite() {
		var website = document.getElementById("webSite").value;
		var sourceleadUrl = '${sourceleadUrl}';
		if(website.match(/^[^\s-](.*)*$/) !== null){
		var sourceleadBLockedwebsites = '${sourceLeadBlockedSocialDomains}';
		$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove();
		if (website != null && website != '') {

			//$("#save_client").attr("disabled", "disabled");
			// $( "#save_client" ).addClass('button').val('Processing..');
			$.ajax({
				type : "GET",
				url : sourceleadUrl + "/ws/validateClientWebsite?${_csrf.parameterName}=${_csrf.token}&website="+ website + "&sourceleadBLockedwebsites="+ sourceleadBLockedwebsites,
				success : function(data) {
					var result = data;
					if (result == 'Website Duplicated' || result == 'Invalid Website') {
						//existed website
						$("input#webSite").after("<p class='jquery_form_error_message'>"+ website+ " "+result+"</span>");
						document.getElementById("webSite").setAttribute('record-exist', 'yes');
						document.getElementById("webSite").setAttribute('record-exist-errorMsg',website + ' is '+result);
						$('input#webSite').removeAttr( "record-exist record-exist-errorMsg");
					} else {
						//New WebSite class="form-control"
						//$("#save_client").removeAttr("disabled");
						$('input#webSite').removeAttr("class record-exist record-exist-errormsg");
						$('input#webSite').attr('class','valid form-control');
						$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove();
						$("#save_client").click(function() {
							if ($(this).validate(false, validationSettings)) {
							}
						});
					}
				},

			});
		}
	 }
	}
</script> -->
 
 <script type="text/javascript">
function validateWebsite(){
      var website = document.getElementById("webSite").value; 
     var sourceleadUrl ='${sourceleadUrl}';
     var sourceleadBLockedwebsites ='${sourceLeadBlockedSocialDomains}';
	 $('input#webSite').removeAttr( "record-exist record-exist-errormsg");
	 if(website != null && website != ''){
		
		 $("#register_button").attr("disabled", "disabled");
		 $( "#register_button" ).addClass('button').val('Processing..');
			 $.ajax({
				    type: "GET",
       	            url: sourceleadUrl+"/ws/validateOrgWebsite?website="+website+"&sourceleadBLockedwebsites="+sourceleadBLockedwebsites,
			        success: function(data) {
			        	 var result = data;
			        		if (result == "Website Duplicated" || result == "Invalid Website") {
		        			//existed website
		    	    		$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove(); 
		    	    		$("input#webSite").closest('b').after("<p class='jquery_form_error_message'> "+website+" "+result+"</p>");
		    				$('input#webSite').attr('record-exist','yes');
		    				$('input#webSite').attr('record-exist-errorMsg',website+' '+result+''); 
			        	}
			        	else{
			        		//New WebSite
			        		$("#register_button").removeAttr("disabled");
				    		$('input#webSite').removeAttr( "class record-exist record-exist-errormsg");
							$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove();
							$( "#save_client" ).click(function() {
							 if ($(this).validate(false, validationSettings)){
							 }
							});
			        	}  
			       },
			        
		   }); 
	}
}
</script>