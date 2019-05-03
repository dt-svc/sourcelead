<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	<%-- <script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/requirementWorkflow.js"></script> --%>
	<script>
	
	function getRequirementWorkflowTasks(currentStageUuid){
			$("#dynamicWorkflowTask").empty();
			var result = "";
		  	var url = "";
		  	result = result +"<table id='workflowTaskTable' class='display' cellspacing='0' width='100%'>";
		  	result = result +"<thead>";
		  	result = result +"<tr>"
		  	result = result +"<th>Title</th>";
		  	result = result +"<th>Assign To</th>";
		  	result = result +"<th>Prioirity</th>";
		  	result = result +"<th>Description</th>";  
		  	result = result +"</tr>";
		  	result = result +"</thead>";
		  	result = result +"</table>";
		  	$("#dynamicWorkflowTask").html(result);
			getDynamicWorkflowTasks(currentStageUuid);
		}
		
		function getDynamicWorkflowTasks(currentStageUuid){
			   var colIndex = 0;
			    $('#workflowTaskTable').DataTable( { 
			    	"processing": true,
			        "serverSide": true,
			        "searching": false,
			        "searchHighlight": true,
			        "ajax": $.fn.dataTable.pipeline( {
			            url: sourceleadUrl + "/getWorkflowTaskList?currentStageUuid="+currentStageUuid,
			            method: 'GET',
			            pages: 10
			        }),
			        "columns": [
			        	{ "data": "workFlowTaskName" },
			        	{ "data": "assignToUserId" },
			        	{ "data": "priority" },
			        	{ "data": "description" }
			        ],
			        "columnDefs": [ {
			    	  	"targets": colIndex++,
			    	  	"sortable": false,
			    	  	"data": "workFlowTaskName",
			    	  	"render": function(data, type, workflowTask, meta) {
			    	  		var result = "";
		              		if(workflowTask.workFlowTaskName != null && workflowTask.workFlowTaskName != '' && workflowTask.workFlowTaskName != "undefined"){
		              			result = result +"<div class='tbldata-dotdot-verybig'>"+workflowTask.workFlowTaskName+"</div>";
		  	              	}else{
		  	            	  		result = result +"<div>---</div>";
		  	             	}
		  	             	 return result;
			    	  	}
			        },{
			    	  	"targets": colIndex++,
			    	  	"sortable": false,
			    	  	"data": "assignToUserId",
			    	  	"render": function(data, type, workflowTask, meta) {
			    	  		var result = "";
			    	  		if(workflowTask.assignToUserId != null && workflowTask.assignToUserId != '' && workflowTask.assignToUserId != "undefined"){
		              			result = result +"<div class='tbldata-dotdot-vsm'>"+workflowTask.assignToUserId+"</div>";
		  	              	}else{
		  	            	  		result = result +"<div>---</div>";
		  	             	}
		  	             	 return result;
			    	  	}
			        },{
			    	  	"targets": colIndex++,
			    	  	"sortable": false,
			    	  	"data": "priority",
			    	  	"render": function(data, type, workflowTask, meta) {
			    	  		var result = "";
			    	  		if(workflowTask.priority != null && workflowTask.priority !== '' && workflowTask.priority != "undefined"){
			    	  				result = result +"<div class='tbldata-dotdot-vsm'>"+workflowTask.priority+"</div>";
			    	  		}else{
		  	            	  		  result = result +"<div>---</div>"; 
		  	             	}
		  	             	 return result;
			    	  	}
			        },{
			    	  	"targets": colIndex++,
			    	  	"sortable": false,
			    	  	"data": "description",
			    	  	"render": function(data, type, workflowTask, meta) {
			    	  		var result = "";
			    	  		if(workflowTask.workFlowTaskDescription != null && workflowTask.workFlowTaskDescription !== '' && workflowTask.workFlowTaskDescription != "undefined"){
			    	  				result = result +"<div class='tbldata-dotdot-vsm'>"+workflowTask.workFlowTaskDescription+"</div>";
			    	  		}else{
		  	            	  		  result = result +"<div>---</div>"; 
		  	             	}
		  	             	 return result;
			    	  	}
			        }],
			        "scrollCollapse": false,
			    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
			    	"aaSorting": [[2,'desc']],
			    	"bLengthChange": true,
			    	"bDestroy": true,
			    	"ordering": true,
			    	"oLanguage": {
			     		"sSearch": "",
			     		 "sZeroRecords": "", "sEmptyTable": "", 
			     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
			     }
			        
			    } );
			     $("#workflowTaskTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
			  
		}
	</script>
<div class="wraper">
<div id="popUp"></div>
<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Add Stage</h2>
    </div>
    <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; 
    <a href="${SourceLeadWeb_sourceleadUrl}/workflowList">Application Workflow</a> &raquo;
    Add Stage</div>
  </section>
  <form id="addStageForm" name="addStageForm">
  <div class="project_wrap clearfix">
  
  
  <c:choose>
  <c:when test = "${not empty stageName}">
  <div class="clearfix mt col-md-6">
      <div class="clearfix row">
        <div class="form-group clearfix">
          <label class="col-md-3">Stage Name<span class="text-danger paddingLeft">&nbsp;*</span></label>
          <div class="col-md-9">
            <input type="text" class="form-control" id = "stageName" name="stageName" placeholder="Stage Name" field-name="Stage Name"
				value='<c:out value="${stageName}"/>' data-validation="required validate_length length1-100"">
			<input type = "hidden" id = "currentStageUuid" value ="<c:out value='${currentStageUuid}'/>">					
          </div>
        </div>
      </div>
      <div class="clearfix row">
        <div class="form-group clearfix">
          <label class="col-md-3">Description</label>
          <div class="col-md-9">
            <textarea rows="2"  class="form-control" id="stageDescription" placeholder="Description" field-name="Description"
								data-validation="validate_length length1-500" data-validation-optional="true"><c:out value="${description}"/></textarea>
          </div>
        </div>
      </div>
    </div>
    
		</c:when>
							<c:otherwise>
							<div class="clearfix mt col-md-6">
      <div class="clearfix row">
        <div class="form-group clearfix">
          <label class="col-md-3">Stage Name<span class="text-danger paddingLeft">&nbsp;*</span></label>
          <div class="col-md-9">
            <input type="text" class="form-control" id = "stageName" name="stageName" placeholder="Stage Name" field-name="Stage Name"
								data-validation="required validate_length length1-100"">
			<input type = "hidden" id = "currentStageUuid" value ="<c:out value='${currentStageUuid}'/>">					
          </div>
        </div>
      </div>
      <div class="clearfix row">
        <div class="form-group clearfix">
          <label class="col-md-3">Description</label>
          <div class="col-md-9">
            <textarea rows="2"  class="form-control" id="stageDescription" placeholder="Description" field-name="Description"
								data-validation="validate_length length1-500" data-validation-optional="true"></textarea>
          </div>
        </div>
      </div>
    </div>
							
							</c:otherwise>
						</c:choose>
    
    
    
    
     </form>
   
    <!-- <div class="clearfix" id = "createTaskDiv">
      <h4 class="col-md-9">New Task</h4>
    </div>
    
  <div id = "dynamicworkflowTask"></div> -->



    <div class="clearfix">
      <h4 class="col-md-9">Assign Task</h4>
      <div class="col-md-3"> <a href="javascript:void(0)" id = "saveTaskButton" class="btn btn-primary pull-right btn-sm" onClick="createNewtask();">Create New Task</a> </div>
    </div>
   
<div id="createNewtask" style="display: none">
		<div class="clearfix row">
		  <div class="form-horizontal mt myForm">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt">
			  <div class="form-group">
				<label class="col-md-3">Title<span class="text-danger paddingLeft">&nbsp;*</span></label>
				<div class="col-md-9">
				<input type='text' class='form-control' id = 'workFlowTaskName'  placeholder='Task Name' field-name='Task Name'>
				</div>
			  </div>
			  <div class="form-group">
				<label class="col-md-3">Assign To</label>
				<div class="col-md-9" id = "dynamicUserDiv">
				  <select class="sel form-control">
					<option selected>Select</option>
					<!-- <option>Applicant Owner</option>
					<option>Job Owner</option>
					<option>Primary Recruiter</option> -->
				  </select>
				</div>
			  </div>
			  <div class="form-group">
				<label class="col-md-3">Priority</label>
				<div class="col-md-9">
				  <select class="sel form-control" id ='workflowTaskPriority'>
					<option selected>Select</option>
					<option>Urgent</option>
					<option>High</option>
					<option>Normal</option>
					<option>Low</option>
				  </select>
				</div>
			  </div>
			</div>
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			  <div class="clearfix">
				<label class="col-md-12">Description</label>
				<div class="col-md-12 descriptionTxtarea">
				<textarea class='form-control' cols='2' id='taskDescription'  placeholder='Description'
				field-name='summerNote' data-validation='validate_length length1-500' data-validation-optional='true' ></textarea>
				</div>
			  </div>
			  <div class="clearfix"></div>
			</div>
		  </div>
		  <div class="clearfix"></div>
    </div>
    <div class="footerstyelsNew">
        <div class="clearfix col-md-12 mb">
          <div class="pull-right mr">
            <input value="Save" class="btn btn-primary" onclick = "saveWorkflowTask('+<c:out value='${requirementWorkFlowStagesDTO[0].requirementWorkFlowUuid}'/>+');" 
            type="button" id = "saveTaskButton">
            <input value="Cancel" class="btn btn-default ml" onclick = "deleteTask();" type="button">
          </div>
        </div>
      </div>
  </div>
  
  
   
      
    <!-- <div class="footerstyelsNew">
        <div class="clearfix col-md-12 mb">
          <div class="pull-right mr">
            <input value="Add Task" class="btn btn-primary" type="button" id = "saveTaskButton" 
            onclick = "appendDynamicworkflowTask();">
          </div>
        </div>
      </div>
   -->
  
  
  <div class="project_wrap_table datatable_filter" id = "dynamicWorkflowTask">
     <!--
     dynamic content here
     -->
    </div> 
  
		<div class="clearfix row">
		  <div class="form-horizontal mt myForm">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt">
			  <div class="form-group">
				<label class="col-md-3 paddingRight">Assign Questionare</label>
				<div class="col-md-9">
				  <div class="row">
					
					 <!-- dynamic stage content starts here -->
					<div class="col-md-11" id = "questionnaireList">
					<select class="sel form-control" id = "questionnaireTemplateId">
						<option selected>Select</option>
					  <!-- 
						<option>Question1</option>
						<option>Question2</option>
					   -->
					   </select>
					</div>
					 <!-- dynamic stage content ends here -->
					
					<div class="col-md-1"> <a class="fa fa-plus btn btn-default" href="${SourceLeadWeb_sourceleadUrl}/addQuestionnaire"></a> </div>
				  </div>
				</div>
			  </div>
			  <div class="clearfix"></div>
			  <div class="form-group">
				<label class="col-md-3">Add Comment</label>
				<div class="col-md-9">
				  <label class="switch">
					<input type="checkbox" checked="">
					<span class="slider round"></span> </label>
				</div>
			  </div>
			</div>
		  </div>
		</div>
		<div class="clearfix row">
		  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<h4 class="col-md-9">Action Buttons</h4>
			<div class="clearfix"></div>
		  </div>
		  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt myForm">
			<div class="form-group">
          <label class="col-md-3" id="previousStagelabel">Previous Stage</label>
        <div class="col-md-9" id = "previousStages">
      
         <select class="sel form-control" id = "previousStageUuid"
         onchange = 'getNextWorkflowStage(this,"+<c:out value='${requirementWorkFlowStagesDTO[0].requirementWorkFlowUuid}'/>+")'>
         <option selected>Select</option>
         <c:if test="${not empty requirementWorkFlowStagesDTO}">  
        	<c:forEach var="requirementWorkFlowStages" items="${requirementWorkFlowStagesDTO}">	
         		<option value="${requirementWorkFlowStages.uuid}">${requirementWorkFlowStages.stageName}</option>
     		</c:forEach>
     		
     		   </c:if> 
     		   </select> 
			  </div>
			  
			</div>
			<div class="form-group">
			  <label class="col-md-3">Next Stage</label>
			  <div class="col-md-9" id = "nextStages">		
			  	
			  	 <!-- dynamic stage content starts here -->
			 	<select class="sel form-control" >
				  <option selected>Select</option>
				 
				</select>				
			  <!-- dynamic stage content ends here -->
			 	
			  </div>
			  
			</div>
		  </div>
    </div>
    
    <div class="clearfix">
        <h4 class="col-md-2">Email</h4>
        <div class="col-md-3"> <a href="javascript:void(0)" class="btn btn-primary btn-sm" onclick="">Add Email</a> </div>
    </div>
    <div class="footerstyelsNew">
        <div class="clearfix col-md-12 mb">
          <div class="pull-right mr">
          
          <c:if test="${not empty requirementWorkFlowStagesDTO}">  
            <input value="Save" class="btn btn-primary" type="button" id="saveStageButton"
            onclick = "saveRequirementWorkflowStage('+<c:out value='${requirementWorkFlowStagesDTO[0].requirementWorkFlowUuid}'/>+');">
		  </c:if>  
          
            <input value="Cancel" class="btn btn-default ml" type="button" onclick = "cancelAddStage();">
          </div>
        </div>
      </div>
	  </div>
	 
</section>
</div>
<script>
var currentStageUuid = $("#currentStageUuid").val();
$(document).ready(function() {
	getRequirementQuestionnaireTemplates();
	
	
	
	$("#saveTaskButton").attr("disabled", true);
	$('#stageName').keyup(function(){
	    if($(this).val().length !=0)
	    	$("#saveTaskButton").attr("disabled", false);           
	    else
	    	$("#saveTaskButton").attr("disabled", true);
	})
	
	 if($('#stageName').val().length !=0){
	    	$("#saveTaskButton").attr("disabled", false);           
	 }else{
	    	$("#saveTaskButton").attr("disabled", true);
	 }
	
	getRequirementWorkflowTasks(currentStageUuid);
});


  var stagename = $('#stageName').val();
 <c:if test="${not empty requirementWorkFlowStagesDTO}">
var workflowStages = new Array();
var stageUuid = new Array();

workflowStages.push(
<c:forEach var="requirementWorkFlowStages" items="${requirementWorkFlowStagesDTO}">
'${requirementWorkFlowStages.stageName}',
</c:forEach>
);

stageUuid.push(
		<c:forEach var="requirementWorkFlowStages" items="${requirementWorkFlowStagesDTO}">
		'${requirementWorkFlowStages.uuid}',
		</c:forEach>
		);

if(stagename != "" && stagename != "undefined"){
var stageIndex; 
workflowStages.forEach(checkStageIndex);

function checkStageIndex(value){
	var a = workflowStages.indexOf(stagename);
	stageIndex = a;
}
 
var previousStageResult = "";
var nextstageResult = "";
previousStageResult = previousStageResult + '<select class="sel form-control" id = "previousStageUuid">';

if(stageIndex > -1) {
	previousStageResult = previousStageResult + "<option value = "+stageUuid[stageIndex-1]+">"+workflowStages[stageIndex-1]+"</option>";
}
previousStageResult = previousStageResult + "</select>"; 

$("#previousStages").empty().append(previousStageResult);

if((stageIndex+1) == workflowStages.length){
	nextstageResult = "<h2>No Stage.</h2>";
	$("#nextStages").empty().append(nextstageResult);
	}else{
		nextstageResult = nextstageResult + "<select class='sel form-control' id = 'workflowStageUuid'>";	
		if(stageIndex > -1) {
			nextstageResult = nextstageResult + "<option value = "+stageUuid[stageIndex+1]+">"+workflowStages[stageIndex+1]+"</option>";		
			console.log(stageUuid[stageIndex+1] + " "+workflowStages[stageIndex+1]);
		}
		nextstageResult = nextstageResult + "</select>";
		$("#nextStages").empty().append(nextstageResult);
}

}
</c:if>   

</script>

<script>
function saveRequirementWorkflowStage(workflowUuid){
	 var validationSettings = {
	           errorMessagePosition: 'element',
	           borderColorOnError: '',
	           scrollToTopOnError: true,
	           dateFormat: 'yyyy/mm/dd'
	 };
	var stageName = $("#stageName").val();
	var description = $("textarea#stageDescription").val();
	var stageUuid = $("#workflowStageUuid").val();
	var qTemplateId = $("#questionnaireTemplateId").val();
	
	 var previousStageUuid = $("#previousStageUuid").val();
	
	
	if(previousStageUuid == null || previousStageUuid == "Select"){
		$('#previousStageUuid').focus();
		$("#previousStagelabel").html("Previous Stage<span class='text-danger paddingLeft'>&nbsp;*</span>");
		return false;
	}if(previousStageUuid != null){
		$('#previousStageUuid').focusout();
	}
	 
	if(qTemplateId == "Select"){
		qTemplateId = "";
	}
	
	if(stageUuid == null){
		stageUuid = $("#previousStageUuid").val();
	}
	var currentStageUuid = $("#currentStageUuid").val();
	
	 if ($('#addStageForm').validate(false, validationSettings)) {
		  var formData = getFormData("addStageForm");
		   $("#saveStageButton").attr("disabled", "disabled");
        $("#saveStageButton").addClass('button').val('Processing..'); 
	$.ajax({
		type : "GET",
		 data : formData,
		url : sourceleadUrl + "/saveRequirementWorkflowStage?description="+description+"&uuid="+stageUuid+
				"&requirementWorkFlowUuid="+workflowUuid+"&requirementQuestionnaireTemplateId="+qTemplateId+"&currentStageUuid="+currentStageUuid,
		success : function(dataList) {
			if (dataList.response === "AWKWARD") {
      	    location.href = "/errorPage";
      	} else if (dataList.response === "SUCCESS") {
      		if(dataList.object === "DataMissing") {
					$("#saveStageButton").removeAttr("disabled");
					$("#saveStageButton").removeAttr('button').val('Save');	
					showError("Invalid data");
				}else{
					sessionStorage.clear();
					window.location.replace("${SourceLeadWeb_sourceleadUrl}/workflowList");
				}
      	} else if (dataList.indexOf('Sign In') !== -1) {
      		location.replace(sourceleadUrl);
      	} else {
      	    showError("Operation failed, please try again");
      	}
					
		}
		});
	 }
}

function getNextWorkflowStage(select, worflowUuid){
	
	var currentStageUuid = $("#currentStageUuid").val();
	var stageUuid = select.value;
		$.ajax({
			type : "GET",
			url : sourceleadUrl + "/getRequirementWorkflowStage?uuid="+stageUuid+"&requirementWorkFlowUuid="+worflowUuid+"&currentStageUuid="+currentStageUuid,
			success : function(dataList){
				var result = "";
				 result = result + "<select class='sel form-control' id = 'workflowStageUuid'>";
				if(dataList != null){
						 result = result + "<option value="+dataList.uuid+">"+dataList.stageName+"</option>";
				}
				else{
					result = "<h2>No Stage.</h2>";
				}
				 result = result + "</select>"; 
				$("#nextStages").empty().append(result);
			}
			});
 }

function getRequirementQuestionnaireTemplates(){
	$.ajax({
		type : "GET",
		url : sourceleadUrl + "/getRequirementQuestionnaireTemplates",
		success : function(dataList){
			var result = "";
			result = result + "<select class='sel form-control' id = 'questionnaireTemplateId'>";
			result = result + "<option selected>Select</option>";
			if(dataList != null && dataList.length > 0){
				 for(var i = 0 ; i < dataList.length; i++){
					 result = result + "<option value="+dataList[i].id+">"+dataList[i].name+"</option>";
				 }
			}
			result = result + "</select>";
			$("#questionnaireList").empty().append(result);
		}
		});
}

	 function cancelAddStage(){
		 window.location.href = "${SourceLeadWeb_sourceleadUrl}/workflowList";
	 }
	 
	 function createNewtask() {
		 getAllActiveUsersList();
		 $("#workFlowTaskName").val("");
			$("#priority").val("");
			$("#taskDescription").val("");
			$("#assignToUserId").val("");
			document.getElementById("createNewtask").style.display = "block";
		}
	 
	 function getAllActiveUsersList(){
			$.ajax({
				type : "GET",
				url : sourceleadUrl + "/getAllActiveUsersList",
				success : function(dataList){
					var result = "";
					result = result + "<select class='sel form-control' id = 'assignToUserId'>";
					result = result + "<option selected>Select</option>";
					if(dataList != null && dataList.length > 0){
						 for(var i = 0 ; i < dataList.length; i++){
							 result = result + "<option value="+dataList[i].userId+">"+dataList[i].userName+"</option>";
						 }
					}
					result = result + "</select>";
					$("#dynamicUserDiv").empty().append(result);
				}
				});
		}
	 
	 function deleteTask(){
			$("#createNewtask").hide();
			$("#workFlowTaskName").val("");
			$("#priority").val("");
			$("#summerNote").val("");
		} 
</script>
<script>

 function saveWorkflowTask(worflowUuid){
	var workFlowTaskName = $("#workFlowTaskName").val();
	var priority = $("#workflowTaskPriority").val();
	var taskDescription = $("#taskDescription").val();
	var assignToUserId = $("#assignToUserId").val();
	var currentStageUuid = $("#currentStageUuid").val();

	var stageName = $("#stageName").val();
	var description = $("textarea#stageDescription").val();
	
	if(currentStageUuid == ""){
		currentStageUuid = "Default";
	}
	 //var taskList = [workFlowTaskName, priority, taskDescription, assignToUserId];
	
	$.ajax({
		type : "GET",
		url : sourceleadUrl + "/saveWorkflowTask?requirementWorkFlowUuid="+worflowUuid+"&workFlowTaskName="+workFlowTaskName+"&priority="+priority
				+"&workFlowTaskDescription="+taskDescription+"&assignToUserId="+assignToUserId+"&currentStageUuid="+currentStageUuid
				+"&stageName="+stageName+"&description="+description,
		success : function(dataList){
			$("#createNewtask").hide();
			if(dataList.response != null){
				getRequirementWorkflowTasks(dataList.response);
				
				$("#currentStageUuid").val(dataList.response);
				sessionStorage.setItem("currentStage", $("#currentStageUuid").val());
				
				
			
			}if(dataList.response == null){
				showError("Operation failed, please try again");
			}
			 $("#workFlowTaskName").val("");
				$("#priority").val("");
				$("#taskDescription").val("");
				$("#assignToUserId").val("");
		}
	});
} 
</script>
<jsp:include page="../masterFooter.jsp" /> 