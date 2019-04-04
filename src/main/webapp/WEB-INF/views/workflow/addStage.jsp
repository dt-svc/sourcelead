<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
  <div class="project_wrap clearfix">
  
    <div class="clearfix mt col-md-6">
      <div class="clearfix row">
        <div class="form-group clearfix">
          <label class="col-md-3">Stage Name<span class="text-danger paddingLeft">&nbsp;*</span></label>
          <div class="col-md-9">
            <input type="text" class="form-control" id = "stageName">
          </div>
        </div>
      </div>
      <div class="clearfix row">
        <div class="form-group clearfix">
          <label class="col-md-3">Description</label>
          <div class="col-md-9">
            <textarea rows="2"  class="form-control" id = "description"></textarea>
          </div>
        </div>
      </div>
    </div>
    
    <div class="clearfix">
      <h4 class="col-md-9">Assign Taks</h4>
      <div class="col-md-3"> <a href="javascript:void(0)" class="btn btn-primary pull-right btn-sm" onClick="createNewtask();">Create New Taks</a> </div>
    </div>
    <div class="clearfix col-md-12 mt">
      <table class="table table-striped">
        <thead>
          <tr>
            <th>Title</th>
            <th>Assign Top </th>
            <th>Priority </th>
            <th>Description </th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td><div class="tblminwidth-175 text-left">Recruiter Name</div></td>
            <td><div class="tblminwidth-175 text-left">1000</div></td>
            <td><div class="tblminwidth-150 text-left">Client</div></td>
            <td><div class="tblminwidth-175 text-left">1000</div></td>
          </tr>
          <tr>
            <td><div class="tblminwidth-175 text-left">Recruiter Name</div></td>
            <td><div class="tblminwidth-175 text-left">1000</div></td>
            <td><div class="tblminwidth-150 text-left">Client</div></td>
            <td><div class="tblminwidth-175 text-left">1000</div></td>
          </tr>
        </tbody>
      </table>
    </div>
<div id="createNewtask" style="display: none">
		<div class="clearfix row">
		  <div class="form-horizontal mt myForm">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt">
			  <div class="form-group">
				<label class="col-md-3">Title<span class="text-danger paddingLeft">&nbsp;*</span></label>
				<div class="col-md-9">
				  <input type="text" class="form-control">
				</div>
			  </div>
			  <div class="form-group">
				<label class="col-md-3">Assign To</label>
				<div class="col-md-9">
				  <select class="sel form-control">
					<option selected>Select</option>
					<option>Applicant Owner</option>
					<option>Job Owner</option>
					<option>Primary Recruiter</option>
				  </select>
				</div>
			  </div>
			  <div class="form-group">
				<label class="col-md-3">Priority</label>
				<div class="col-md-9">
				  <select class="sel form-control">
					<option selected>Select</option>
					<option>Urget</option>
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
				  <textarea placeholder="Description" class="form-control" cols="2" id="summernote"></textarea>
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
            <input value="Save" class="btn btn-primary" type="button">
            <input value="Cancel" class="btn btn-default ml" type="button">
          </div>
        </div>
      </div>
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
            <input value="Save" class="btn btn-primary" type="button" 
            onclick = "saveRequirementWorkflowStage('+<c:out value='${requirementWorkFlowStagesDTO[0].requirementWorkFlowUuid}'/>+');">
		  </c:if>  
          
            <input value="Cancel" class="btn btn-default ml" type="button">
          </div>
        </div>
      </div>
	  </div>
</section>
</div>
<script>
$(document).ready(function() {
	getRequirementQuestionnaireTemplates();
	
});
</script>
<script>
function createNewtask() {
	document.getElementById("createNewtask").style.display = "block";
}
</script>
<script>
function saveRequirementWorkflowStage(workflowUuid){
	var stageName = $("#stageName").val();
	var description = $("textarea#description").val();
	var stageUuid = $("#workflowStageUuid").val();
	if(stageUuid == null){
		stageUuid = $("#previousStageUuid").val();
	}
	alert(stageUuid);
	var qTemplateId = $("#questionnaireTemplateId").val();
	$.ajax({
		type : "GET",
		url : sourceleadUrl + "/saveRequirementWorkflowStage?stageName="+stageName+"&description="+description+"&uuid="+stageUuid+
				"&requirementWorkFlowUuid="+workflowUuid+"&requirementQuestionnaireTemplateId="+qTemplateId,
		success : function(dataList) {
					window.location.replace("${SourceLeadWeb_sourceleadUrl}/workflowList");
		}
		});
}

function getNextWorkflowStage(select, worflowUuid){
	
	var stageUuid = select.value;
		$.ajax({
			type : "GET",
			url : sourceleadUrl + "/getRequirementWorkflowStage?uuid="+stageUuid+"&requirementWorkFlowUuid="+worflowUuid,
			success : function(dataList){
				var result = "";
				result = result + "<select class='sel form-control' id = 'workflowStageUuid'>";
				 result = result + "<option selected>Select</option>";
				if(dataList != null){
						$("#nextStageDiv").show();
						 result = result + "<option value="+dataList.uuid+">"+dataList.stageName+"</option>";
				}
				else{
					$("#nextStageDiv").hide();
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
//questionnaireList
</script>
<jsp:include page="../masterFooter.jsp" /> 