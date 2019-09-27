<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<script>
var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
</script>
	<script src="/resources/js/requirementWorkflow.js?staticTime=${staticTime}"></script>
	<link href="/resources/css/summernote.css" rel="stylesheet" type="text/css">
	<script src="/resources/js/summernote.js?staticTime=${staticTime}"></script>

<div class="wraper">
<div id="popUp"></div>
<section class="content-wraper">
<div class="success_pop" id="selector" >
	       <i class="fa fa-check-circle"></i><span id="msgDiv"></span>
			</div>
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
      <div class="clearfix mt row">
        <div class="col-md-6">
          <label class="col-md-3">Stage Name<span class="text-danger paddingLeft">&nbsp;*</span></label>
          <div class="col-md-9" id = "StageNameDiv">
            <input type="text" class="form-control" id = "stageName" name="stageName" placeholder="Stage Name" field-name="Stage Name"
            value='<c:out value="${stageName}"/>' data-validation="required validate_length length4-100"
            onkeyup= "requirementWorkflow.validateWorkflowStageName('${requirementWorkFlowStagesDTO[0].requirementWorkFlowUuid}');">
			<input type = "hidden" id = "currentStageUuid" value ="<c:out value='${currentStageUuid}'/>">					
          </div>
        </div>
      <div class="col-md-6">
          <label class="col-md-3">Short Code<span class="text-danger paddingLeft">&nbsp;*</span></label>
          <div class="col-md-5" id = "ShortCodeDiv">
          
          <c:choose>
          <c:when test="${configureType == 'Customize'}">
           <input type="text" class="form-control" id = "shortCode" name="stageShortCode" placeholder="Short Code" field-name="Short Code"
            value = "<c:out value='${stageShortCode}'/>" data-validation="required validate_length length4-20"
             onkeyup="requirementWorkflow.validateStageShortCode('${requirementWorkFlowStagesDTO[0].requirementWorkFlowUuid}');"
             style="text-transform:uppercase" disabled>
          </c:when>
          <c:otherwise>
           <input type="text" class="form-control" id = "shortCode" name="stageShortCode" placeholder="Short Code" field-name="Short Code"
            value = "<c:out value='${stageShortCode}'/>" data-validation="required validate_length length4-20"
             onkeyup="requirementWorkflow.validateStageShortCode('${requirementWorkFlowStagesDTO[0].requirementWorkFlowUuid}');"
             style="text-transform:uppercase" >
          </c:otherwise>
          </c:choose>
           
          
          
          </div>
        </div>
      <div class="clearfix"></div>
        <div class="col-md-6 clearfix mt">
          <label class="col-md-3">Description</label>
          <div class="col-md-9">
            <textarea rows="2"  class="form-control" id="stageDescription" placeholder="Description" field-name="Description"
								data-validation="validate_length length1-500" data-validation-optional="true"><c:out value="${description}"/></textarea>
          </div>
        </div>
      </div>
      
		</c:when>
							<c:otherwise>
	<div class="clearfix mt row">
        
        <div class="col-md-6">
          <label class="col-md-3">Stage Name<span class="text-danger paddingLeft">&nbsp;*</span></label>
          <div class="col-md-9" id = "StageNameDiv">
            <input type="text" class="form-control" id = "stageName" name="stageName" placeholder="Stage Name" field-name="Stage Name"
            data-validation="required validate_length length4-100"
            onkeyup="requirementWorkflow.validateWorkflowStageName('${requirementWorkFlowStagesDTO[0].requirementWorkFlowUuid}');">
			<input type = "hidden" id = "currentStageUuid" value ="<c:out value='${currentStageUuid}'/>">					
          </div>
        </div>

		<div class="col-md-6">
          <label class="col-md-3">Short Code<span class="text-danger paddingLeft">&nbsp;*</span></label>
          <div class="col-md-5" id = "ShortCodeDiv">
            <input type="text" class="form-control" id = "shortCode" name="stageShortCode" placeholder="Short Code" field-name="Short Code"
            onkeyup="requirementWorkflow.validateStageShortCode('${requirementWorkFlowStagesDTO[0].requirementWorkFlowUuid}');"
            data-validation="required validate_length length1-20" style="text-transform:uppercase" >
          </div>
        </div>
      <div class="clearfix"></div>
      <div class="col-md-6 clearfix mt">
          <label class="col-md-3">Description</label>
          <div class="col-md-9">
            <textarea rows="2"  class="form-control" id="stageDescription" placeholder="Description" field-name="Description" data-validation="validate_length length1-500" data-validation-optional="true"></textarea>
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
   
   <form id="addTaskForm" name="addTaskForm">
<div id="createNewtask" style="display: none">

		<div class="clearfix row">
		  <div class="form-horizontal mt myForm">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt">
			  <div class="form-group">
				<label class="col-md-3">Title<span class="text-danger paddingLeft">&nbsp;*</span></label>
				<div class="col-md-9">
				<input type='text' class='form-control' name = 'workFlowTaskName' id = 'workFlowTaskName'  placeholder='Task Name' field-name='Task Name'
				data-validation="required validate_length length1-100">
				</div>
			  </div>
			  <div class="form-group">
				<label class="col-md-3">Assign To *</label>
				<div class="col-md-9" id = "assignToUserId">
				  <select class="sel form-control" name = 'assignToUserId' id = 'assignToUserId' field-name='Assign To' data-validation='required'>
					<option selected="" value="">Select</option>
					<!-- <option>Applicant Owner</option>
					<option>Job Owner</option>
					<option>Primary Recruiter</option> -->
				  </select>
				</div>
			  </div>
			  <div class="form-group">
				<label class="col-md-3">Priority *</label>
				<div class="col-md-9">
				  <select class="sel form-control" name = 'priority' id ='priority' field-name='Priority' data-validation="required">
					<option selected="" value="">Select</option>
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
				<textarea class='form-control' cols='2' name = 'workFlowTaskDescription' id='workFlowTaskDescription'  placeholder='Description'
				field-name='Description' data-validation='required'></textarea>
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
            <input value="Cancel" class="btn btn-default ml" onclick = "requirementWorkflow.deleteTask('addTaskForm');" type="button">
          </div>
        </div>
      </div>
  </div>
  </form>
  
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
					<div class="col-md-9 padding_right" id = "questionnaireList">
					<select class="sel form-control" id = "questionnaireTemplateId">
						<option selected>Select</option>
					  <!-- 
						<option>Question1</option>
						<option>Question2</option>
					   -->
					   </select>
					</div>
					<span class="cursorPointer btn btn-primary pull-left" onclick="requirementWorkflow.getRequirementQuestionnaireTemplates();"><i class="fa fa-refresh "></i></span>
					 <!-- dynamic stage content ends here -->
					
					<div class="col-md-1"> <a class="fa fa-plus btn btn-default minHeBtn" href="${SourceLeadWeb_sourceleadUrl}/addQuestionnaire" target="_blank"></a> </div>
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
					<input id = "commentRequired" type="checkbox" checked="" value = "Y" >
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
		<div class="clearfix mb">
		  
		  <form id="columnsForm" name="columnsForm">
		  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
          <div class="clearfix">
              <div class="checkbox-new">
              <c:choose>
  				<c:when test = "${requiredList == 'Y'}">
                <input id="columnsCHkbx" class="checkbox-custom form-control" value="" name="list_required" type="checkbox" data-validation = "required" field-name= "Required Stats" checked>
                </c:when>
                <c:otherwise>
                <input id="columnsCHkbx" class="checkbox-custom form-control" value="" name="list_required" type="checkbox" data-validation = "required" field-name= "Required Stats">
                </c:otherwise>
                </c:choose>
                <label for="columnsCHkbx" class="checkbox-custom-label"></label>
                <span class="ml10">Required Stats/List</span>
              </div>
      </div>
      </div>
     </form>
      
      <c:choose>
  	<c:when test = "${requiredList == 'Y'}">
      <div class="clearfix col-xs-12 col-sm-12 col-md-12 col-lg-12 chosecolumnsID" id="chosecolumnsID">
      </c:when>
      <c:otherwise>
      <div class="clearfix col-xs-12 col-sm-12 col-md-12 col-lg-12 chosecolumnsID" id="chosecolumnsID" style="display: none;">
      </c:otherwise>
      </c:choose>
      
          <ul class="list-unstyled" id = "chosenColumnList">
                  <li>
                    <div class="checkbox-new clearfix">
                        <input id="applicantname" class="checkbox-custom" name="chosenColumn" type="checkbox" value = "Applicant Name" disabled = "disabled"  checked>
                        <label for="applicantName" class="checkbox-custom-label"></label>
                        <span class="ml10">Applicant Name *</span>
                      </div>
                    </li>
                    <li>
                        <div class="checkbox-new clearfix">
                            <input id="applicantmailid" class="checkbox-custom" name="chosenColumn" type="checkbox" value = "Applicant Mail Id" disabled = "disabled"  checked>
                            <label for="applicantmailid" class="checkbox-custom-label"></label>
                            <span class="ml10">Applicant Mail Id *</span>
                          </div>
                        </li>
                        <li>
                            <div class="checkbox-new clearfix">
                                <input id="applicantphonenumber" class="checkbox-custom" name="chosenColumn" type="checkbox" value = "Phone Number" disabled = "disabled"  checked>
                                <label for="applicantphonenumber" class="checkbox-custom-label"></label>
                                <span class="ml10">Phone Number *</span>
                              </div>
                            </li>
                            <li>
                                <div class="checkbox-new clearfix">
                                    <input id="alternatenumber" class="checkbox-custom" name="chosenColumn" type="checkbox" value = "Alternate Number">
                                    <label for="alternatenumber" class="checkbox-custom-label"></label>
                                    <span class="ml10">Alternate Number</span>
                                  </div>
                                </li>
                                <li>
                                    <div class="checkbox-new clearfix">
                                        <input id="location" class="checkbox-custom" name="chosenColumn" type="checkbox" value = "location" disabled = "disabled" checked>
                                        <label for="location" class="checkbox-custom-label"></label>
                                        <span class="ml10">Location *</span>
                                      </div>
                                    </li>
                                    <li>
                                        <div class="checkbox-new clearfix">
                                            <input id="dob" class="checkbox-custom" name="chosenColumn" type="checkbox" value = "DOB">
                                            <label for="dob" class="checkbox-custom-label"></label>
                                            <span class="ml10">DOB</span>
                                          </div>
                                        </li>
                                        <li>
                                            <div class="checkbox-new clearfix">
                                                <input id="experience" class="checkbox-custom" name="chosenColumn" type="checkbox" value = "Experience">
                                                <label for="experience" class="checkbox-custom-label"></label>
                                                <span class="ml10">Experience</span>
                                              </div>
                                        </li>
                                        <li>
                                            <div class="checkbox-new clearfix">
                                                <input id="currentctc" class="checkbox-custom" name="chosenColumn" type="checkbox" value = "Current CTC">
                                                <label for="currentctc" class="checkbox-custom-label"></label>
                                                <span class="ml10">Current CTC</span>
                                              </div>
                                        </li>
                                        <li>
                                            <div class="checkbox-new clearfix">
                                                <input id="rateexpectedctc" class="checkbox-custom" name="chosenColumn" type="checkbox" value = "Rate [Expected CTC]">
                                                <label for="rateexpectedctc" class="checkbox-custom-label"></label>
                                                <span class="ml10">Rate [Expected CTC]</span>
                                              </div>
                                        </li>
                                        <li>
                                            <div class="checkbox-new clearfix">
                                                <input id="education" class="checkbox-custom" name="chosenColumn" type="checkbox" value = "Education">
                                                <label for="education" class="checkbox-custom-label"></label>
                                                <span class="ml10">Education</span>
                                              </div>
                                        </li>
                                        <li>
                                            <div class="checkbox-new clearfix">
                                                <input id="noticeperiod" class="checkbox-custom" name="chosenColumn" type="checkbox" value = "Notice Period">
                                                <label for="noticeperiod" class="checkbox-custom-label"></label>
                                                <span class="ml10">Notice Period</span>
                                              </div>
                                        </li>
                                        <li>
                                            <div class="checkbox-new clearfix">
                                                <input id="appliedon" class="checkbox-custom" name="chosenColumn" type="checkbox" value = "Applied on">
                                                <label for="appliedon" class="checkbox-custom-label"></label>
                                                <span class="ml10">Applied on</span>
                                              </div>
                                        </li>
                                         <li>
                                            <div class="checkbox-new clearfix">
                                                <input id="SubmittedQuestionnaire" class="checkbox-custom" name="chosenColumn" type="checkbox" value = "Submitted Questionnaire" disabled = "disabled" checked>
                                                <label for="SubmittedQuestionnaire" class="checkbox-custom-label"></label>
                                                <span class="ml10">Submitted Questionnaire *</span>
                                              </div>
                                        </li>
                                        <li>
                                            <div class="checkbox-new clearfix">
                                                <input id="source" class="checkbox-custom" name="chosenColumn" type="checkbox" value = "Source">
                                                <label for="source" class="checkbox-custom-label"></label>
                                                <span class="ml10">Source</span>
                                              </div>
                                        </li>
                                        <li>
                                            <div class="checkbox-new clearfix">
                                                <input id="Status" class="checkbox-custom" name="chosenColumn" type="checkbox" value = "Status" disabled = "disabled" checked>
                                                <label for="Status" class="checkbox-custom-label"></label>
                                                <span class="ml10">Status *</span>
                                              </div>
                                        </li>
                                        </ul>
                                        
      </div>
<div class="clearfix"></div>
<div class="clearfix col-xs-12 col-sm-12 col-md-12 col-lg-12">
  <div class="pull-right">
    <button type="button" class="btn btn-primary mr" onclick = "requirementWorkflow.selectChosenColumns();">Select All</button>
    <button type="button" class="btn btn-default" onclick = "requirementWorkflow.resetChosenColumns();">Reset</button>
  </div>
</div>

    </div>
    <%-- <c:if test ="${onlyQuestAndTasksEditable == false}"> --%>
    <form id="stagesForm" name="stagesForm">
		<div class="clearfix row">
		  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<h4 class="col-md-9">Action Buttons</h4>
			<div class="clearfix"></div>
		  </div>
		  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt myForm">
			<div class="form-group">
          <label class="col-md-3" id="previousStagelabel">Previous Stage *</label>
        <div class="col-md-9" id = "previousStages">
         <select class="sel form-control" id = "previousStageUuid" name = "previousStageUuid" data-validation = "required" field-name= "Previous Stage"
         onchange = 'requirementWorkflow.getNextWorkflowStage(this,"+<c:out value='${requirementWorkFlowStagesDTO[0].requirementWorkFlowUuid}'/>+","stagesForm")'>
         <option selected="" value="">Select</option>
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
			 	<select class="sel form-control" id = 'workflowStageUuid' data-validation = "required" field-name= "Next Stage">
				  <option selected="" value="">Select</option>
				 
				</select>				
			  <!-- dynamic stage content ends here -->
			 	 <input type = "hidden" id = "nextStage">	
			  </div>
			  
			</div>
		  </div>
    </div>
    </form>
  <%--   </c:if> --%>
   
    <!-- functionality is not implemented commented as of now for calrification -->
    <!-- <div class="clearfix">
        <h4 class="col-md-2">Email</h4>
        <div class="col-md-3"> <a href="javascript:void(0)" class="btn btn-primary btn-sm" onclick="">Add Email</a> </div>
    </div> -->
    <div class="footerstyelsNew">
        <div class="clearfix col-md-12 mb">
          <div class="pull-right mr">
          
          <c:if test="${not empty requirementWorkFlowStagesDTO}">  
            <input value="Save & Finish" class="btn btn-primary" type="button" id="saveStageButton"
            onclick = "requirementWorkflow.validateAndSaveStage('+<c:out value='${requirementWorkFlowStagesDTO[0].requirementWorkFlowUuid}'/>+');">
		  </c:if>  
          
            <input value="Cancel" class="btn btn-default ml" type="button" onclick = "requirementWorkflow.viewWorkFlow('<c:out value='${requirementWorkFlowStagesDTO[0].requirementWorkFlowUuid}'/>');">
          </div>
        </div>
      </div>
	  </div>
	 
</section>
</div>
<script>
var currentStageUuid = $("#currentStageUuid").val();
$(document).ready(function() {
	workFlowName = '${workFlowName}';
	commentReq = $("#commentRequired").val();
	configureType = "${configureType}";	
		$("#commentRequired").click(function(){
			if(commentReq == "Y"){
				commentReq = "N";
				requirementWorkflow.showSuccessMsg("Comment Turned Off");
			}
			else{
				commentReq = "Y";
				requirementWorkflow.showSuccessMsg("Comment Turned On");
			}
		})
		
		questionnaireId = '${questionnaireId}';
		questionnaireName = '${questionnaireName}';
		
		requirementWorkflow.getRequirementQuestionnaireTemplates();
	
	
	if($('#stageName').val().length < 4){
		$("#saveTaskButton").addClass("disabled");
	}else{
		$("#saveTaskButton").removeClass("disabled");
	}
	
	$('#stageName').keyup(function(){
	    if($(this).val().length < 4 || $('#shortCode').val().length < 4 ){
	    	$("#saveTaskButton").addClass("disabled");
	    }
	    else{
	    	$("#saveTaskButton").removeClass("disabled");
	   }
	})
	$('#shortCode').keyup(function(){
	    if($(this).val().length < 4 ||  $('#stageName').val().length < 4 ){
	    	$("#saveTaskButton").addClass("disabled");    
	    }
	    else{
	    	$("#saveTaskButton").removeClass("disabled");
	   }
	})
	
	requirementWorkflow.getRequirementWorkflowTasks(currentStageUuid);
	checkValidEditableFields('${onlyQuestAndTasksEditable}')
});
// below function disabled the fileds if workflow initiated any requirement for the job.
function checkValidEditableFields(checkValidEditableFields){
	//console.log("checkValidEditableFields value is :"+checkValidEditableFields);
	if(checkValidEditableFields == 'true'){
		  $('#stagesForm').css("display","none");
		  $("#columnsCHkbx").val("on");
		  $("#stageName").attr("disabled", "disabled");
		  $("#shortCode").attr("disabled", "disabled");
	 }else{
		  if($("#columnsCHkbx").is(':checked')){
			  $("#columnsCHkbx").val("on");
		  }else{
			  $("#columnsCHkbx").val("");
		  }
	 }
}


  var stagename = $('#stageName').val();
 <c:if test="${not empty requirementWorkFlowStagesDTO}">
 if(stagename != "" && stagename != "undefined"){
 var workflowStages = new Array();
var stageUuid = new Array();
var chosenColumns = '${chosenColumns}';
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
requirementWorkflow.prepareChosenColumnList(chosenColumns);
}
</c:if>   
</script>
<script>
$("#columnsCHkbx").click(function(){
  $("#chosecolumnsID").toggle();
  if($(this).is(':checked')){
	  $("#columnsCHkbx").val("on");
  }
  else{
	  $("#columnsCHkbx").val("");
  }
})
</script>
<jsp:include page="../masterFooter.jsp" /> 