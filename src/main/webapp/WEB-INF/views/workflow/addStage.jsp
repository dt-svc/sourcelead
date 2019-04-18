<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	
	<script>
	function getRequirementWorkflowTasks(){
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
			getDynamicWorkflowTasks();
		}
		
		function getDynamicWorkflowTasks(){

			   var colIndex = 0;
			    $('#workflowTaskTable').DataTable( { 
			    	"processing": true,
			        "serverSide": true,
			        "searching": false,
			        "searchHighlight": true,
			        "ajax": $.fn.dataTable.pipeline( {
			            url: sourceleadUrl + "/getWorkflowTaskList",
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
			    	  		if(workflowTask.description != null && workflowTask.description !== '' && workflowTask.description != "undefined"){
			    	  				result = result +"<div class='tbldata-dotdot-vsm'>"+workflowTask.description+"</div>";
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
  
    <div class="clearfix mt col-md-6">
      <div class="clearfix row">
        <div class="form-group clearfix">
          <label class="col-md-3">Stage Name<span class="text-danger paddingLeft">&nbsp;*</span></label>
          <div class="col-md-9">
            <input type="text" class="form-control" id = "stageName" name="stageName" placeholder="Stage Name" field-name="Stage Name"
								data-validation="required validate_length length1-100"">
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
    
   
    <div class="clearfix" id = "createTaskDiv">
      <h4 class="col-md-9">New Task</h4>
    </div>
    
     
    
 
  
  <div id = "dynamicworkflowTask"></div>

   
      
    <div class="footerstyelsNew">
        <div class="clearfix col-md-12 mb">
          <div class="pull-right mr">
            <input value="Add Task" class="btn btn-primary" type="button" id = "saveTaskButton" 
            onclick = "appendDynamicworkflowTask();">
          </div>
        </div>
      </div>
  
  
  
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
          <label class="col-md-3">Previous Stage</label>
        <div class="col-md-9">
        
          
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
			<div class="form-group" id = "nextStageDiv">
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
	  </form>
</section>
</div>
<script>
$(document).ready(function() {
	getRequirementQuestionnaireTemplates();
	//getRequirementWorkflowTasks();
	
	$("#saveTaskButton").attr("disabled", true);
	$('#stageName').keyup(function(){
	    if($(this).val().length !=0)
	    	$("#saveTaskButton").attr("disabled", false);           
	    else
	    	$("#saveTaskButton").attr("disabled", true);
	})
	
});
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
	
	
	if(qTemplateId == "Select"){
		qTemplateId = "";
	}
	
	if(stageUuid == null){
		stageUuid = $("#previousStageUuid").val();
	}

	
	 if ($('#addStageForm').validate(false, validationSettings)) {
		  var formData = getFormData("addStageForm");
		   $("#saveStageButton").attr("disabled", "disabled");
        $("#saveStageButton").addClass('button').val('Processing..'); 
	$.ajax({
		type : "GET",
		 data : formData,
		url : sourceleadUrl + "/saveRequirementWorkflowStage?description="+description+"&uuid="+stageUuid+
				"&requirementWorkFlowUuid="+workflowUuid+"&requirementQuestionnaireTemplateId="+qTemplateId,
		success : function(dataList) {
			if (dataList.response === "AWKWARD") {
      	    location.href = "/errorPage";
      	} else if (dataList.response === "SUCCESS") {
      		if(dataList.object === "DataMissing") {
					$("#saveStageButton").removeAttr("disabled");
					$("#saveStageButton").removeAttr('button').val('Save');	
					showError("Invalid data");
				}else{
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
	
	var stageUuid = select.value;
		$.ajax({
			type : "GET",
			url : sourceleadUrl + "/getRequirementWorkflowStage?uuid="+stageUuid+"&requirementWorkFlowUuid="+worflowUuid,
			success : function(dataList){
				var result = "";
				 result = result + "<select class='sel form-control' id = 'workflowStageUuid'>";
				 /*result = result + "<option selected>Select</option>"; */
				if(dataList != null){
						$("#nextStageDiv").show();
						 result = result + "<option value="+dataList.uuid+">"+dataList.stageName+"</option>";
				}
				else{
					//$("#nextStageDiv").hide();
					result = "";
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
</script>
<script>
function getAllActiveUsersList(rowId){
	$.ajax({
		type : "GET",
		url : sourceleadUrl + "/getAllActiveUsersList",
		success : function(dataList){
			var result = "";
			result = result + "<select class='sel form-control' id = 'user"+rowId+"' name ='taskList["+rowId+"].assignToUserId' >";
			result = result + "<option selected>Select</option>";
			if(dataList != null && dataList.length > 0){
				 for(var i = 0 ; i < dataList.length; i++){
					 result = result + "<option value="+dataList[i].userId+">"+dataList[i].userName+"</option>";
				 }
			}
			result = result + "</select>";
			$("#dynamicUserDiv"+rowId).empty().append(result);
		}
		});
}
</script>

<script>
function prepareDynamicworkflowTask(rowId){
	$("#createTaskDiv").hide();
	var dynamicTask = "";
	dynamicTask = dynamicTask + "<div class='clearfix mt' id = 'dynamicTaskDiv"+rowId+"'><div class='clearfix'>";
	dynamicTask = dynamicTask + "<h4 class='col-md-9'>New Task</h4>";
	dynamicTask = dynamicTask + "<div class='col-md-3'> <a href='javascript:void(0)' class='btn btn-primary pull-right btn-sm' onClick='deleteTask("+rowId+");'>";
	dynamicTask = dynamicTask + "Delete Task</a> </div>";
	dynamicTask = dynamicTask + "</div>";
	dynamicTask = dynamicTask + "<div id='createNewtask"+rowId+"'>";

	dynamicTask = dynamicTask + "<div class='clearfix row'>";
	dynamicTask = dynamicTask + "<div class='form-horizontal mt myForm'>";
	dynamicTask = dynamicTask + "<div class='col-xs-12 col-sm-12 col-md-12 col-lg-12 mt'>";
	dynamicTask = dynamicTask + "<div class='form-group'>";
	dynamicTask = dynamicTask + "<label class='col-md-3'>Title<span class='text-danger paddingLeft'>&nbsp;*</span></label>";
	dynamicTask = dynamicTask + "<div class='col-md-9'>";
	dynamicTask = dynamicTask + "<input type='text' class='form-control' id = 'workFlowTaskName"+rowId+"' name='taskList["+rowId+"].workFlowTaskName' placeholder='Task Name'";
	dynamicTask = dynamicTask + "field-name='Task Name'";
	dynamicTask = dynamicTask + "data-validation='required validate_length length1-100'>";
	dynamicTask = dynamicTask + "</div> </div>";
	
	//dynamic user list
	dynamicTask = dynamicTask + "<div class='form-group'>";
	dynamicTask = dynamicTask + "<label class='col-md-3'>Assign To</label>";
	dynamicTask = dynamicTask + "<div class='col-md-9' id = 'dynamicUserDiv"+rowId+"' >";
	dynamicTask = dynamicTask + "<select class='sel form-control' id = 'user"+rowId+"' name ='taskList["+rowId+"].assignToUserId' >";
	dynamicTask = dynamicTask + "<option selected>Select</option>";
	dynamicTask = dynamicTask + " </select>	</div>  </div>";
	
	dynamicTask = dynamicTask + "<div class='form-group'>";
	dynamicTask = dynamicTask + "<label class='col-md-3'>Priority</label>";
	dynamicTask = dynamicTask + "<div class='col-md-9'>";
	dynamicTask = dynamicTask + "<select class='sel form-control' id ='priority"+rowId+"' name ='taskList["+rowId+"].priority' >";
	dynamicTask = dynamicTask + "<option selected>Select</option>";
	dynamicTask = dynamicTask + "<option value = 'Urgent'>Urgent</option>";
	dynamicTask = dynamicTask + "<option value = 'High'>High</option>";
	dynamicTask = dynamicTask + "<option value = 'Normal'>Normal</option>";
	dynamicTask = dynamicTask + "<option value = 'Low'>Low</option>";
	dynamicTask = dynamicTask + "</select>";
	dynamicTask = dynamicTask + "</div> </div> </div>";
	dynamicTask = dynamicTask + "<div class='col-xs-12 col-sm-12 col-md-12 col-lg-12'>";
	dynamicTask = dynamicTask + "<div class='clearfix'>";
	dynamicTask = dynamicTask + "<label class='col-md-12'>Description</label>";
	dynamicTask = dynamicTask + "<div class='col-md-12 descriptionTxtarea'>";
	dynamicTask = dynamicTask + "<textarea class='form-control' cols='2' id='taskDescription"+rowId+"' name ='taskList["+rowId+"].workFlowTaskDescription'  placeholder='Description'";
	dynamicTask = dynamicTask + "field-name='summerNote' data-validation='validate_length length1-500' data-validation-optional='true' ></textarea>";
	dynamicTask = dynamicTask + "</div> </div>";
	dynamicTask = dynamicTask + "<div class='clearfix'></div>";
	dynamicTask = dynamicTask + "</div> </div>";
	dynamicTask = dynamicTask + "<div class='clearfix'></div>";
	dynamicTask = dynamicTask + "</div></div></div>";

	//dynamic user list
	getAllActiveUsersList(rowId);
	return dynamicTask;
}

var rowId = 0;
function appendDynamicworkflowTask(){
	   $("#dynamicworkflowTask").append(prepareDynamicworkflowTask(rowId++)); 
}

function deleteTask(id){
	if(id == 0){
		$("#createTaskDiv").show();
	}
	   $("#dynamicTaskDiv"+id).remove();
	   rowId--;
} 
</script>
<jsp:include page="../masterFooter.jsp" /> 