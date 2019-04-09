<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<script>
var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
</script>
<div class="wraper"> 
   <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Questionnaire</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home </a>&raquo; 
      <a href="${SourceLeadWeb_sourceleadUrl}/workflowList">Application Workflow</a> &raquo;
      Questionnaire</div>
    </section>
    <div class="project_wrap clearfix">
      <div class="timesheet_out" id="payrollRegister" style="display: block;">
        <div class="clearfix">
          <div class="positionRelative reportHead clearfix">
            <ul class="pull-right list-unstyled list-inline">
				<li>
				<a class="newAdBtn mr5" href="${SourceLeadWeb_sourceleadUrl}/addQuestionnaire" title="Add">+ Add questionnaire</a>	
					
				</li>
              <li class="">
                <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
                <span class="repSearchIcon" onclick="getWorkflowQuestionnnaireList();"></span></li>
            </ul>
          </div>
           <div id ="questionnaireList" class="project_wrap_table datatable_filter"></div>
          </div>
      </div>
    </div>
  </section>
</div>
<script>
$(document).ready(function() {
	getWorkflowQuestionnnaireList();
	
});

</script>

<script>
function getWorkflowQuestionnnaireList(){
	$("#questionnaireList").empty();
	var result = "";
  //	var searchValue = $("#dynamicUserDataTableSearch").val();
  	//var url = "";
  	result = result +"<table id='questionnaireListTable' class='display' cellspacing='0' width='100%'>";
  	result = result +"<thead>";
  	result = result +"<tr>"
  	result = result +"<th>Name</th>";
  	result = result +"<th>Description</th>";
  	result = result +"<th>Created By</th>";
  	result = result +"<th>Action</th>";  
  	result = result +"</tr>";
  	result = result +"</thead>";
  	result = result +"</table>";
  	$("#questionnaireList").html(result);
	getDynamicWorkflowQuestionnnaireList();
}

function getDynamicWorkflowQuestionnnaireList(){
	   var colIndex = 0;
	   var searchValue = $("#dynamicUserDataTableSearch").val();
	   console.log(searchValue);
	    $('#questionnaireListTable').DataTable( { 
	    	"processing": true,
	        "serverSide": true,
	        "searching": false,
	        "searchHighlight": true,
	        "ajax": $.fn.dataTable.pipeline( {
	            url: sourceleadUrl + "/getQuestionnaireList?searchVal="+searchValue,
	            method: 'GET',
	            pages: 10
	        }),
	        "columns": [
	        	{ "data": "name"},
	        	{ "data": "description"},
	        	{ "data": "createdBy"}
	        	
	        ],
	        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"sortable": false,
	    	  	"data": "name",
	    	  	"render": function(data, type, workflow, meta) {
	    	  		var result = "";
              		if(workflow.name != null && workflow.name != '' && workflow.name != "undefined"){
              			result = result +"<div class ='tbldata-dotdot-me'>"+workflow.name+"</div>";
  	              	}else{
  	            	  		result = result +"<div>---</div>";
  	             	}
  	             	 return result;
	    	  	}
	        },{
	    	  	"targets": colIndex++,
	    	  	"sortable": false,
	    	  	"data": "description",
	    	  	"render": function(data, type, workflow, meta) {
	    	  		var result = "";
	    	  		if(workflow.description != null && workflow.description != '' && workflow.description != "undefined"){
              			result = result +"<div class='tbldata-dotdot-verybig'>"+workflow.description+"</div>";
  	              	}else{
  	            	  		result = result +"<div>---</div>";
  	             	}
  	             	 return result;
	    	  	}
	        },{
	    	  	"targets": colIndex++,
	    	  	"sortable": false,
	    	  	"data": "createdBy",
	    	  	"render": function(data, type, workflow, meta) {
	    	  		var result = "";
	    	  		var workflowStatus;
	    	  		if(workflow.createdBy != null && workflow.createdBy != '' && workflow.createdBy != "undefined"){
              			result = result +"<div class='tbldata-dotdot-vsm'>"+workflow.createdBy+"</div>";
  	              	}else{
  	            	  		result = result +"<div>---</div>";
  	             	}
  	             	 return result;
	    	  	}
	        },{
	    	  	"targets": colIndex++,
	    	  	"sortable": false,
	    	  /* 	 "data": "uuid", */
	    	  	"render": function(data, type, workflow, meta) {
	    	  		var result = "";
	    	    	result = result +"<div class='width90px'>";
	    	  		result = result +"<ul class='list-inline list-unstyled'>";
	    	  		result = result +"<li><a href= "+sourceleadUrl+"/editQuestionnaire?templateUuid="+workflow.uuid+"><i class='fa fa-pencil cursorPointer' title='Edit'></i></a></li>";
	    	  		result = result +"<li><i class='fa fa-trash cursorPointer' title='Delete' onclick= deleteQuestionnaire('"+workflow.uuid+"')></i></li>"
	    	  		result = result +"</ul>	";
	    	  		result = result +"</div>";
  	             	return result;
	    	  	}
	        }
	        ],
	        "scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
	        
	    } );
	  $("#questionnaireListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
	  $("#dynamicUserDataTableSearch").keyup(function (e) {
	         if (e.which == 13) {
	        	 getWorkflowQuestionnnaireList();
	        
	         }
	     });
	        
}
</script>
<script>

function deleteQuestionnaire(uuid){
	console.log("delete uuid "+uuid);
	  $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/deleteQuestionnaireTemplateById?templateUuid="+uuid, 
	       success: function(result) {
	           if (result.response === "AWKWARD") {
	        	    location.href = "/errorPage";
	        	} else if (result.response === "SUCCESS") {
	        		getWorkflowQuestionnnaireList();
	        		$(".mask, #modalTasktype").fadeOut();
	        	} else {
	        	    showError("Operation failed, please try again");
	        	}
	        },
	    });
}
</script>
<jsp:include page="../masterFooter.jsp" /> 