<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<div class="wraper"> 
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Add Workflow</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; 
      <a href="${SourceLeadWeb_sourceleadUrl}/workflowList">Application Workflow</a> &raquo;
      Add Workflow</div>
    </section>
    <div class="clearfix">
		
       <div class="positionRelative reportHead clearfix mb">
        <ul class="pull-right list-unstyled list-inline">
          <li><a class="newAdBtn mr5" href="${SourceLeadWeb_sourceleadUrl}/addStage" title="Add">+ Add</a></li>
        <li class="">
            <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
            <span class="repSearchIcon" onclick="getDynamciUserList();"></span>
		</li>
        </ul>
        <div class="clearfix"></div>
      </div>
    </div>
    
    <form id="requirementWorkFlowForm" name="requirementWorkFlowForm">
<div class="clearfix backgroundWhite mb">
  <div class="row">
      <div class="clearfix mt col-md-6">
          <div class="form-group clearfix">
            <label class="col-md-3">Name<span class="text-danger paddingLeft">*</span></label>
            <div class="col-md-9">
              <input type="text" class="form-control" id="workFlowName" name="workFlowName" placeholder="WorkFlow Name" field-name="WorkFlow Name"
								data-validation="required validate_length length1-100">
            </div>
          </div>
        </div>
        <div class="clearfix mt col-md-6">
          <div class="form-group clearfix">
            <label class="col-md-3">Description</label>
            <div class="col-md-9">
              <textarea rows="2" class="form-control" name="description" id="description" placeholder="Description" field-name="Description"
								data-validation="validate_length length1-500" data-validation-optional="true"></textarea>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="clearfix"></div>
      
      <!-- dynamic stages content starts here-->
        <div id ="dynamicWorkflow">
		</div>
	<!-- dynamic stages content ends here-->
		
	<div class="footerstyelsNew">
	<div class="clearfix">
		<div class="pull-right">
		 <input type="button" value="Save" id="saveReqWorkFlow" onclick="saveRequirementWorkFlow();" class="btn btn-primary">
		 <input type="button" value="Cancel"
							onClick="javascript:window.location.href = '${SourceLeadWeb_sourceleadUrl}/workflowList'"
							class="btn btn-default ml">
		</div>
	</div>
	</div>
	</form>
    </div>
  </section>
</div>
<script>

$(document).ready(function() {
	//getDefaultWorkflowNameAndDescription();
	getDefaultWorkflowStages();
	
});
var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
</script>
<script>
var workflowId;
function getDefaultWorkflowNameAndDescription(){
	$.ajax({
		type : "GET",
		url : sourceleadUrl + "/createRequirementDefaultWorkFlow",
		success : function(dataList) {
			$("#workFlowName").val(dataList.workFlowName);
			$("textarea#description").val(dataList.description);
			workflowId = dataList.id;
		}
		});
}

	var iconSpan = "";
	var editAndDeleteAnchor = "";
	function getDefaultWorkflowStages() {
		$.ajax({
			type : "GET",
			url : sourceleadUrl + "/createRequirementDefaultWorkFlowStages",
			success : function(dataList) {
				var result = "";
				if(dataList != null && dataList.length > 0){
					 result = result + "<ul class='list-unstyled widgetDivLi'>"; 
					for(var i = 0 ; i < dataList.length; i++){
						
					//changing dynamic spans based on stage names and anchors for stages based on editable. 
					iconSpan = dynamicSpan(dataList[i].stageName);
					editAndDeleteAnchor = dynamicAnchor(dataList[i].editable, dataList[i].uuid);
					
					result = result + "<li class='widgetDiv mb clearfix'>";
					result = result + "<div class='row'>";
					result = result + "<div class='col-md-10'>";
					result = result + iconSpan
					result = result + "<h4 class='pull-left mt10 ml' id = 'noOfStages'>"+dataList[i].stageName+"</h4>"; 
					result = result + "</div>";
					result = result + "<div class='col-md-2'>";
					result = result + "<ul class='list-inline list-unstyled pull-right mb0'>";
					result = result + editAndDeleteAnchor
					result = result + "</ul></div></div></li>";
					}
					 result = result + "</ul>"; 
				}
				$("#dynamicWorkflow").empty().append(result);
			},
		});
	}
	
	function dynamicSpan(stageName){
		if(stageName != null){
				if(stageName == "Submission"){
					return "<span class='submissionShare pull-left'></span>";
				}
				if(stageName == "Short List"){
					return "<span class='shortlilstedIC pull-left'></span>";
				}
				if(stageName == "Submit to Client"){
					return "<span class='shortlilstedIC pull-left'></span>";
				}
				if(stageName == "Interview"){
					return "<span class='invterviewIC pull-left'></span>";
				}
				if(stageName == "Offer"){
					return "<span class='jobOfferIC pull-left'></span>";
				}
				if(stageName == "Reject"){
					return "<span class='submissionShare pull-left'></span>";
				}
				if(stageName == "Joined"){
					return "<span class='joinedIC pull-left'></span>";
				}
				if(stageName == "Backout"){
					return "<span class='submissionShare pull-left'></span>";
				}
				if(stageName == "On-hold"){
					return "<span class='submissionShare pull-left'></span>";
				}
				if(stageName == "No Response"){
					return "<span class='submissionShare pull-left'></span>";
				}
			}
		}
		
	function dynamicAnchor(editable, uuid){
		if(editable != null){
			if(editable == "Y"){
				 /* "<li><a title='Edit' href='${SourceLeadWeb_sourceleadUrl}/addStage'><i class='fa fa-pencil'></i></a></li>" + */
				return	"<li><a title='Delete' onclick ='deleteWorkflowStage(\""+uuid+"\");'><i class='fa fa-trash'></i></a></li>"
			}
			if(editable == "N"){
				return "<li></li>";
			}
		}
	}
	
	function deleteWorkflowStage(uuid){
		$.ajax({
			type : "GET",
			url : sourceleadUrl + "/deleteWorkflowStage?uuid="+uuid,
			success : function(dataList) {
				getDefaultWorkflowStages();
			}
			});
	}
	
</script>
<script type="text/javascript">
function saveRequirementWorkFlow(){
	var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
      var validationSettings = {
           errorMessagePosition: 'element',
           borderColorOnError: '',
           scrollToTopOnError: true,
           dateFormat: 'yyyy/mm/dd'
       };
     
	  if ($('#requirementWorkFlowForm').validate(false, validationSettings)) {
		  var formData = getFormData("requirementWorkFlowForm");
		   $("#saveReqWorkFlow").attr("disabled", "disabled");
           $("#saveReqWorkFlow").addClass('button').val('Processing..'); 
	 $.ajax({
           type: "POST",
           data : formData,
           url:sourceleadUrl+"/saveWorkFlowDetailsPage",
           success: function (result) {
				if (result.response === "AWKWARD") {
	        	    location.href = "/errorPage";
	        	} else if (result.response === "SUCCESS") {
	        		if(result.object === "DataMissing") {
						$("#saveReqWorkFlow").removeAttr("disabled");
						$("#saveReqWorkFlow").removeAttr('button').val('Save');	
						showError("Invalid data");
					}else{
						
						location.replace(sourceleadUrl+"/workflowList");	
						
					}
	        	} else if (result.indexOf('Sign In') !== -1) {
	        		location.replace(sourceleadUrl);
	        	} else {
	        	    showError("Operation failed, please try again");
	        	}
           },
       });
     } 
}
</script>
<jsp:include page="../masterFooter.jsp" /> 	