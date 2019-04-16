<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<div class="wraper"> 
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Application Workflow</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; 
      <a href="${SourceLeadWeb_sourceleadUrl}/workflowDashboard">Workflow Dashboard</a> &raquo;
      Application Workflow
      </div>
    </section>
    <div class="project_wrap clearfix">
      <div class="timesheet_out" id="payrollRegister" style="display: block;">
        <div class="clearfix">
          <div class="positionRelative reportHead clearfix">
            <ul class="pull-right list-unstyled list-inline">
                <li>
                    <a class="newAdBtn mr5" href="${SourceLeadWeb_sourceleadUrl}/defaultWorkflow" title="Add">+ Add</a>	
                    </li>
            
              <li>
				<a class="newAdBtn mr5" href="${SourceLeadWeb_sourceleadUrl}/questionnaireList" title="Add">+ Questionnaire</a>	
				</li>
              <li class="">
                <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
                <span class="repSearchIcon" onclick="getWorkflowList();"></span></li>
            </ul>
          </div>
          
          <!-- dynamic content starts here.. -->
          <div class="project_wrap_table datatable_filter" id = "dynamicWorkFlowList">
            
            </div>
          <!-- dynamic content ends here.. -->
          
        </div>
      </div>
    </div>
  </section>
</div>

<script>
$(document).ready(function() {
	getWorkflowList();
	
});
var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
</script>

<script>
function getWorkflowList(){
	
	$("#dynamicWorkFlowList").empty();
	var result = "";
  	var searchValue = $("#dynamicUserDataTableSearch").val();
  	var url = "";
  	result = result +"<table id='workflowListTable' class='display' cellspacing='0' width='100%'>";
  	result = result +"<thead>";
  	result = result +"<tr>"
  	result = result +"<th>Name</th>";
  	result = result +"<th>Stages</th>";
  	result = result +"<th>Status</th>";
  	result = result +"<th>Action</th>";  
  	result = result +"</tr>";
  	result = result +"</thead>";
  	result = result +"</table>";
  	$("#dynamicWorkFlowList").html(result);
	getDynamicWorkflowList();
}

function getDynamicWorkflowList(){
	   var searchValue = $("#dynamicUserDataTableSearch").val();
	   var colIndex = 0;
	    $('#workflowListTable').DataTable( { 
	    	"processing": true,
	        "serverSide": true,
	        "searching": false,
	        "searchHighlight": true,
	        "ajax": $.fn.dataTable.pipeline( {
	            url: sourceleadUrl + "/getWorkflowList?searchValue="+searchValue,
	            method: 'GET',
	            pages: 10
	        }),
	        "columns": [
	        	{ "data": "workFlowName" },
	        	{ "data": "noOfStages" },
	        	{ "data": "status" }
	        ],
	        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"sortable": false,
	    	  	"data": "workFlowName",
	    	  	"render": function(data, type, workflow, meta) {
	    	  		var result = "";
              		if(workflow.workFlowName != null && workflow.workFlowName != '' && workflow.workFlowName != "undefined"){
              			result = checkDefaultWorkflow(workflow.configureType, workflow.uuid, workflow.workFlowName); 
  	              	}else{
  	            	  		result = result +"<div>---</div>";
  	             	}
  	             	 return result;
	    	  	}
	        },{
	    	  	"targets": colIndex++,
	    	  	"sortable": false,
	    	  	"data": "noOfStages",
	    	  	"render": function(data, type, workflow, meta) {
	    	  		var result = "";
	    	  		if(workflow.noOfStages != null && workflow.noOfStages != '' && workflow.noOfStages != "undefined"){
              			result = result +"<div class='tbldata-dotdot-vsm'>"+workflow.noOfStages+"</div>";
  	              	}else{
  	            	  		result = result +"<div>---</div>";
  	             	}
  	             	 return result;
	    	  	}
	        },{
	    	  	"targets": colIndex++,
	    	  	"sortable": false,
	    	  	"data": "status",
	    	  	"render": function(data, type, workflow, meta) {
	    	  		var result = "";
	    	  		if(workflow.status != null && workflow.status !== '' && workflow.status != "undefined"){
	    	  			if(workflow.status == 1){
	    	  				result = result +"<div class='tbldata-dotdot-vsm'>Active</div>";
	    	  			}	
	    	  			else{
	    	  				result = result +"<div class='tbldata-dotdot-vsm'>In-Active</div>";
	    	  			}	
	    	  		}else{
  	            	  		  result = result +"<div>---</div>"; 
  	             	}
  	             	 return result;
	    	  	}
	        },{
	    	  	"targets": colIndex++,
	    	  	"sortable": false,
	    	  	"render": function(data, type, workflow, meta) {
	    	  		var result = "";
	    	  		result = result +"<div class='width215px'>";
	    	  		result = result +"<ul class='list-inline list-unstyled'>";
	    	  		result = result +"<li>";
	    	  		result = result +"<label class='switch mr positionRelativeTop10' title='Default'>"
	    	  		result = result +"<input type='checkbox' name='' "+ (workflow.status == 1 ?'checked':'')+" >";
	    	  		result = result +"<div class='slider round' onclick = 'changeWorkflowStatus(\""+workflow.uuid+"\", \""+workflow.status+"\");'></div>";
	    	  		result = result +"</label></li>";
	    	  		result = result +"<li><a onclick='viewWorkFlow(\""+workflow.uuid+"\")'><i class='fa fa-pencil cursorPointer' title='Edit'></i></a></li>";
	    	  		result = result +"<li><i class='fa fa-trash cursorPointer' title='Delete'></i></li>";
	    	  		result = result +"<li><i onclick='saveWorkFlow(\""+workflow.uuid+"\",\""+workflow.workFlowName+"\",\""+workflow.description+"\");'class='fa fa fa-clone cursorPointer text-primary' title='Clone'></i></li>";
	    	  		result = result +"</ul></div>";
	    	  		result = result + checkConfigureType(workflow.configureType, workflow.status, workflow.uuid);
  	             	return result;
	    	  	}
	        }
	        ],
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
	     $("#workflowListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
		  $("#dynamicUserDataTableSearch").keyup(function (e) {
		         if (e.which == 13) {
		        	 getWorkflowList();
		        
		         }
		     });
	        
}

function checkDefaultWorkflow(configureType, uuid, workFlowName){
	var result = "";
	if(configureType != "Default"){
		result = result +"<div class='tbldata-dotdot-verybig'><a onclick='viewWorkFlow(\""+uuid+"\")' title='view'>"+workFlowName+"</a></div>";
	}else{
		result = result +"<div class='tbldata-dotdot-verybig'>"+workFlowName+"</div>";
	}
	return result;
}

function checkConfigureType(configureType, status, uuid){
	var result = "";
	if(configureType != "Default"){
  		result = result +"<div class='width215px'>";
  		result = result +"<ul class='list-inline list-unstyled'>";
  		result = result +"<li>";
  		result = result +"<label class='switch mr positionRelativeTop10' title='Default'>"
  		result = result +"<input type='checkbox' name='' "+ (status == 1 ?'checked':'')+" >";
  		result = result +"<div class='slider round' onclick = 'changeWorkflowStatus(\""+uuid+"\", \""+status+"\");'></div>";
  		result = result +"</label></li>";
  		result = result +"<li><a onclick='viewWorkFlow(\""+uuid+"\")'><i class='fa fa-pencil cursorPointer' title='Edit'></i></a></li>";
  		result = result +"<li><i class='fa fa-trash cursorPointer' title='Delete'></i></li>";
  		result = result +"<li><i class='fa fa fa-clone cursorPointer text-primary' title='Clone'></i></li>";
  		result = result +"</ul></div>";
  		}
	return result;
}

function changeWorkflowStatus(uuid, status){
	$.ajax({
		type : "GET",
		url : sourceleadUrl + "/getWorkflowList?uuid="+uuid+"&status="+status,
		success : function(dataList) {
			getDynamicWorkflowList();
		}
		});
			
}
function saveWorkFlow(uuid,workFlowName,description){
	$.ajax({
		type : "POST",
		url : sourceleadUrl + "/saveWorkFlowDetailsPage?workFlowName="+workFlowName+"&description="+description,
		success : function(dataList) {
			getDynamicWorkflowList();
		}
		});
			
}
function viewWorkFlow(uuid){
	location.href = sourceleadUrl+ "/viewWorkflow?uuid="+uuid
}
</script>

<jsp:include page="../masterFooter.jsp" /> 	