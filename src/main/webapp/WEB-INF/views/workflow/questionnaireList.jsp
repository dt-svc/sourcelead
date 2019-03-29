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
                <span class="repSearchIcon" onclick="getDynamciUserList();"></span></li>
            </ul>
          </div>
          <div class="require_wrap_table clearfix">
           <div id ="questionnaireList" class="project_wrap_table datatable_filter"></div>
          </div>
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
  /* 	result = result +"<th>Description</th>";
  	result = result +"<th>Created By</th>";
  	result = result +"<th>Action</th>";   */
  	result = result +"</tr>";
  	result = result +"</thead>";
  	result = result +"</table>";
  	$("#questionnaireList").html(result);
	getDynamicWorkflowQuestionnnaireList();
}

function getDynamicWorkflowQuestionnnaireList(){
	   var colIndex = 0;
	    $('#questionnaireListTable').DataTable( { 
	    	"processing": true,
	        "serverSide": true,
	        "searching": false,
	        "searchHighlight": true,
	        "ajax": $.fn.dataTable.pipeline( {
	            url: sourceleadUrl + "/getQuestionnaireList",
	            method: 'GET',
	            pages: 10
	        }),
	        "columns": [
	        	{ "data": "name"}
	        	
	        ],
	        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"sortable": false,
	    	  	"data": "name",
	    	  	"render": function(data, type, workflow, meta) {
	    	  		var result = "";
              		if(workflow.name != null && workflow.name != '' && workflow.name != "undefined"){
              			result = result +"<div class ='tbldata-dotdot-me'>"+workflow.name+"</a></div>";
  	              	}else{
  	            	  		result = result +"<div>---</div>";
  	             	}
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
	  
	        
}
</script>
<jsp:include page="../masterFooter.jsp" /> 