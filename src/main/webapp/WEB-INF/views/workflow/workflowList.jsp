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
	    	  		
	    	  		result = result + checkConfigureType(workflow.configureType, workflow.status, workflow.uuid, workflow.workFlowName);
	    	  		
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

function checkConfigureType(configureType, status, uuid, workFlowName){
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
  		result = result +"<li><i class='fa fa-trash cursorPointer' title='Delete' onclick='deleteWorkflowEntry(\""+uuid+"\", \""+workFlowName+"\")'></i></li>";
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

function viewWorkFlow(uuid){
	location.href = sourceleadUrl+ "/viewWorkflow?uuid="+uuid
}
</script>

<div id="popUp"></div>
<script>
	function deleteWorkflowEntry(workflowuuid, workFlowName){ 
		 var uid = workflowuuid; 
		var result ="";
		result = result +"	<div class='project_inn_mask'></div>"; 
		result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Do you want to delete "+workFlowName+"</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"	<b href='javascript:void(0)'><input type='button' value='Ok' onclick='removeWorkFlowEntry(\""+workflowuuid+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
		result = result +"	</div>";
		$("#popUp").append(result);
		$("#remove_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
</script>	
	
<script>	
	function cancel(){
		$('.mask').hide();
		$("#popUp").empty();
		$(".project_inn_mask").fadeOut(200);
		}	
</script>

<script>	
	function removeWorkFlowEntry(workflowuuid){
		$("#popUp").empty();
		 $(".project_inn_mask").fadeOut(200);
		 
		 $("#workFlow"+workflowuuid).empty();
		 $("#workFlow"+workflowuuid).html("<td colspan='7'  id='loading"+workflowuuid+"'></td>");
		 $("#loading"+workflowuuid).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
		 
		 	$.ajax({
		 		type : "POST",
		 		url : sourceleadUrl + "/removeWorkFlowEntry?workflowuuid="+workflowuuid,
		 		success: function(result) {
 		  			if(result = "Success"){
 		  				getWorkflowList();
 		  			}
			    }		
		 	})
	}
</script>

<jsp:include page="../masterFooter.jsp" /> 	