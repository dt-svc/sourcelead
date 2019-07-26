<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<script>
var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
</script>
	<script src="/resources/js/requirementWorkflow.js?staticTime=${staticTime}"></script>

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
   
    <div class="clearfix">
      <h4 class="col-md-9">Assign Task</h4>
      <div class="col-md-3"> <a href="javascript:void(0)" id = "saveTaskButton" class="btn btn-primary pull-right btn-sm" onClick="requirementWorkflow.createNewtask();">Create New Task</a> </div>
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
            <input value="Save" class="btn btn-primary" onclick = "requirementWorkflow.saveWorkflowTask('+<c:out value='${requirementWorkFlowStagesDTO[0].requirementWorkFlowUuid}'/>+');" 
            type="button" id = "saveTaskButton">
            <input value="Cancel" class="btn btn-default ml" onclick = "requirementWorkflow.deleteTask();" type="button">
          </div>
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
				  <c:choose>
					<c:when test = "${commentRequired == 'Y'}">
					<input id = "commentRequired" type="checkbox" checked="" value = "Y">
					</c:when>
					<c:otherwise>
					<input id = "commentRequired" type="checkbox" value = "N">
					</c:otherwise>
					</c:choose>
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
         onchange = 'requirementWorkflow.getNextWorkflowStage(this,"+<c:out value='${requirementWorkFlowStagesDTO[0].requirementWorkFlowUuid}'/>+")'>
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
            onclick = "requirementWorkflow.saveRequirementWorkflowStage('+<c:out value='${requirementWorkFlowStagesDTO[0].requirementWorkFlowUuid}'/>+');">
		  </c:if>  
          
            <input value="Cancel" class="btn btn-default ml" type="button" onclick = "requirementWorkflow.cancelAddStage();">
          </div>
        </div>
      </div>
	  </div>
	 
</section>
</div>
<script>
var currentStageUuid = $("#currentStageUuid").val();
$(document).ready(function() {
	commentReq = $("#commentRequired").val();
	
	$("#commentRequired").click(function(){
		if(commentReq == "Y"){
			commentReq = "N";
		}
		else{
			commentReq = "Y";
		}
	})
		
	
	requirementWorkflow.getRequirementQuestionnaireTemplates();
	
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
	
	requirementWorkflow.getRequirementWorkflowTasks(currentStageUuid);
});


  var stagename = $('#stageName').val();
 <c:if test="${not empty requirementWorkFlowStagesDTO}">
 if(stagename != "" && stagename != "undefined"){
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

var stageIndex; 
workflowStages.forEach(checkStageIndex);

function checkStageIndex(value){
	var a = workflowStages.indexOf(stagename);
	stageIndex = a;
}
 
requirementWorkflow.prepareDynamicStages();

}
</c:if>   
</script>
<jsp:include page="../masterFooter.jsp" /> 