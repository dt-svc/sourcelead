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
            <label class="col-md-3">Name</label>
            <div class="col-md-9">
              <input type="text" class="form-control" id="workFlowName" name="workFlowName">
            </div>
          </div>
        </div>
        <div class="clearfix mt col-md-6">
          <div class="form-group clearfix">
            <label class="col-md-3">Description</label>
            <div class="col-md-9">
              <textarea rows="2" class="form-control" name="description" id="description" ></textarea>
            </div>
          </div>
        </div>
      </div>
    </div>
    

      <div class="clearfix"></div>
      
      <!-- dynamic stages content starts here-->
      
      <div id = "dynamicWorkflow">
		
		
		</div>
		
		 <!-- dynamic stages content ends here-->
		
		
		<div class="footerstyelsNew">
	<div class="clearfix">
		<div class="pull-right">
		 <input type="button" value="Save" id="saveReqWorkFlow" onclick="saveRequirementWorkFlow();" class="btn btn-primary">
		<input value="Cancel" class="btn btn-default ml" type="button">
		</div>
	</div>
	</div>
	</form>
    </div>
  </section>
</div>
<script>

$(document).ready(function() {
	getDefaultWorkflow();
	
});
var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
</script>
<script>

	var iconSpan = "";
	var editAndDeleteAnchor = "";
	function getDefaultWorkflow() {
		var formData = getFormData("requirementWorkFlowForm");
		$.ajax({
			type : "GET",
			data : formData,
			url : sourceleadUrl + "/createRequirementDefaultWorkFlow",
			success : function(dataList) {
				var result = "";
				if(dataList != null && dataList.length > 0){
					 result = result + "<ul class='list-unstyled widgetDivLi'>"; 
					for(var i = 0 ; i < dataList.length; i++){
						
					//changing dynamic spans based on stage names and anchors for stages based on editable. 
					iconSpan = dynamicSpan(dataList[i].stageName);
					editAndDeleteAnchor = dynamicAnchor(dataList[i].editable);
					
					result = result + "<li class='widgetDiv mb clearfix'>";
					result = result + "<div class='row'>";
					result = result + "<div class='col-md-10'>";
					result = result + iconSpan
					result = result + "<h4 class='pull-left mt10 ml'>"+dataList[i].stageName+"</h4>"; 
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
		
	function dynamicAnchor(editable){
		if(editable != null){
			if(editable == "Y"){
				return "<li><a title='Edit' href='${SourceLeadWeb_sourceleadUrl}/addStage'><i class='fa fa-pencil'></i></a></li>" +
						"<li><a title='Delete' href='addWorkflow.html'><i class='fa fa-trash'></i></a></li>"
			}
			if(editable == "N"){
				return "<li></li>";
			}
		}
	}
	
</script>
<script type="text/javascript">
function saveRequirementWorkFlow(){
	 
      var validationSettings = {
           errorMessagePosition: 'element',
           borderColorOnError: '',
           scrollToTopOnError: true,
           dateFormat: 'yyyy/mm/dd'
       };
      
	/*  if ($('#requirementWorkFlowForm').validate(false, validationSettings)) { */
		  var formData = getFormData("requirementWorkFlowForm");
		 /*  $("#saveReqWorkFlow").attr("disabled", "disabled");
         $("#saveReqWorkFlow").addClass('button').val('Processing..'); */
	 $.ajax({
           type: "POST",
           data : formData,
           url:sourceleadUrl+"/saveWorkFlowDetailsPage",
           success: function (data) {
           alert("success")
           },
       });
    /*  } */
}
</script>
<jsp:include page="../masterFooter.jsp" /> 	