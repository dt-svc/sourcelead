<section class="addImmiWrap">
	<div class="addImmiInnWrap">
		<div class="immiStepsHead clearfix">
			<h2>Task Details</h2>
			<ul class="addImmiSteps">
				<li class="active page-updated">1</li>
				<li class="active page-updated">2</li>
				<li class="active page-updated">3</li>
				<li class="active">4</li>
				<b></b>
				<div class="clearfix"></div>
			</ul>
		</div>
		
		<div class="addImmiContent clearfix">
			<div class="immigraTabsHead">
				<a href="javascript:void(0)"  onclick="getAssignToUsersList(null);" id="addTask"><i class="glyphicon glyphicon-plus"></i> Add Task</a> 
				<span><input id="immigraListTabSerach" type="text"></span>
			</div>	
			<div id="immigrationTempDynamicTaskList" class="project_wrap_table datatable_filter">
			</div>
 		</div>
   
		<div class="addImmiFoot">
			<a href="javascript:void(0)" onclick="immigration.gotoTemplatePreviousPage('formDetails')" class="pull-left">Prev</a>
			<a href="javascript:void(0)" class="nextBtn">Add Template</a>
			<a href="javascript:void(0)">Reset</a>
			<div class="clearfix"></div>
		</div>   
   </div>
   </section>
   <!--   Popup Code  -->
<div class="popMask"></div>

<div class="popUpOut popUpOutBig" id="taskViewPopup">
		<div class="popupHead">
			<h2>Add Task</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		<form id="petitionTemplateTaskDetails" name="petitionTemplateTaskDetails">
		<input type="hidden" name="id" id="templateId"/>
		<div class="popupMid popupMidOver clearfix mt">
			<div class="row clearfix">
				<div class="col-md-6 noPad">
					<div class="col-md-12 mb10">
					  <label>Subject<span class="text-danger">*</span></label>
					  <input type="text" id ="taskName" name="taskName" class="form-control input-sm error" placeholder="Subject" field-name= "Subject" data-validation = "required validate_Space validate_length length2-500">
					</div>
					<div class="col-md-12 mb10">
					  <label>Priority</label>
						<select class="form-control input-sm valid" id="priority1" name="priority">
							<option selected="" disabled="">-- Select Priority --</option>
							<option>High</option>
							<option>Low</option>
							<option>Medium</option>
						  </select>
					</div>
					<div class="col-md-12 mb10">
					  <label>Repeat</label>
						<select class="form-control input-sm valid" id="taskRepeat" name="taskRepeat">
							<option selected="" disabled="">-- Select Repeat --</option>
							<option>Never</option>
							<option>Daily</option>
							<option>Weekly</option>
							<option>Monthly</option>
							<option>Yearly</option>
						  </select>
					</div>
					
					<div class="col-md-12">
					  <div class="clearfix">
						<label class="paddingRight checkBox"><input id="reminder" name="Reminder" type="checkbox">Reminder</label>
					  </div>
					   <div class="mt5" id="reminderdropdown" style="display:none;">
						<select class="chosen-select" id="taskReminder" name="taskReminder"  multiple>
						<option>1 days</option><option>1 Week</option><option>15 days</option><option>1 Month</option><option>90 days</option><option>180 days</option>
						</select>
					  </div>
					</div>
					<div class="col-md-12 mt">
					 <label>Alert</label>
					  <div class="mt2">
						<label class="paddingRight"><input id="taskEmail" name="taskEmail"  type="checkbox" value="N" onclick="validateTaskEmail();"> Email </label>
						<label class="paddingLeft"><input id="taskNotification" name="taskNotification"  type="checkbox" value="N" onclick="validateTaskNotification();"> Notification </label>
					  </div>
					</div>															
				</div>
				
				<div class="col-md-6 noPad">
					<div class="col-md-12 mb10">
					      
					      <div class="clearfix">
						  <label>Assign To<span class="text-danger">*</span></label>
						  <div class="clearfix">
							<label class="paddingRight"> <input type="radio"  name="assignToType" id="assignedToB" checked="checked" onclick="validateAssignToType('assingToCandidate');"> Beneficiary </label>
							<label class="paddingLeft"> <input type="radio"  name="assignToType" id="assignedToU"  onclick="validateAssignToType('assingToUser');"> User </label>
						  </div>
						  </div>
						  
						  <div class="clearfix" id="assignToUserDiv" style="display:none;">
							<select class="chosen-select" multiple data-placeholder="Select User"  id="petitionAssignedTo1" name="petitionAssignedTo"  field-name="AssignTo"  data-validation-optional="true">
							  </select>
						  </div>
						  
						  
						</div>
					<div class="col-md-12 mb10">
					  <div class="row">
						<div class="col-md-12">
						  <label><strong>Due after:<span class="text-danger">*</span></strong></label>        
						  <!-- Form inline Due after -->
						  <div class="row">
							   <div class="col-md-6 paddingRight">
								<label>Months</label> <br>
									<div class="select-field">
										<select id="dueAfterMonths" name="dueAfterMonths" field-name= "Months" class="form-control input-sm" data-validation = "required">
											<option selected="" value="">--Select Month--</option>
												<option>1</option>
												<option>2</option>
												<option>3</option>
												<option>4</option>
												<option>5</option>
												<option>6</option>
												<option>7</option>
												<option>8</option>
												<option>9</option>
												<option>10</option>
												<option>11</option>
												<option>12</option>
											</select>
									</div>
								</div> 

							<div class="col-md-6 paddingLeft">
							<label>Days</label>
								<div class="select-field">
									<select id="dueAfterDays" name="dueAfterDays" class="form-control input-sm" field-name = "Days" data-validation = "required">
										<option selected="" value="">--Select Days--</option>
										<option>1</option>
										<option>2</option>
										<option>3</option>
										<option>4</option>
										<option>5</option>
										<option>6</option>
										<option>7</option>
										<option>8</option>
										<option>9</option>
										<option>10</option>
										<option>11</option>
										<option>12</option>
										<option>13</option>
										<option>14</option>
										<option>15</option>
										<option>16</option>
										<option>17</option>
										<option>18</option>
										<option>19</option>
										<option>20</option>
										<option>21</option>
										<option>22</option>
										<option>23</option>
										<option>24</option>
										<option>25</option>
										<option>26</option>
										<option>27</option>
										<option>28</option>
										<option>29</option>
										<option>30</option>
									</select>
								</div>
						  </div>
						</div>
						</div>
						  <!-- Form inline Due after --> 
						</div>
					  </div>
					  
					<div class="col-md-12 clearfix mb10">
					  <label>Description</label>
					  <textarea id="taskDescription" name="taskDescription" rows="2" placeholder="Description" class="form-control valid" field-name= "Description" data-validation = "validate_length length2-500" data-validation-optional = "true"></textarea>
					</div>	
																			
				</div>				
				
			</div>				
		</div>
		</form>
		<div class="immiPopFoot">
            <input value="Add" onclick ="immigration.savePetitionTemplateTaskDetails()" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>	


<!-- Template List code -->	
<script type="text/javascript">
function immigrationDataTableList(){
	var result = "";
 	result = result +"<table id='templateTaskListTable'><thead><tr><th>Subject</th><th>Assign To</th><th>Priority</th><th>Repeat</th><th>Action</th></tr></thead></table>";
    $("#immigrationTempDynamicTaskList").empty();
    $("#immigrationTempDynamicTaskList").append(result);
	var sourceleadUrl ='${sourceleadUrl}';
	 var searchValue = $("#immigraListTabSerach").val();
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+immiModulePath+"/getTemplateTaskList?searchValue="+searchValue;
	 immigrationPagination(url);
}
	
function immigrationPagination(url){
	var colIndex = 0;
    $('#templateTaskListTable').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 10
        }),
        "columns": [
            { "data": "taskName" },
            { "data": "assignedTo"},
            { "data": "priority"},
            { "data": "taskRepeat"}
            /*{ "data": "id"},*/
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "taskName",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		  if(opt.taskName !=null && opt.taskName !='' && opt.taskName !='undefined'){
			        		result = result + '<td>'+opt.taskName+'</td><input type="hidden" id="startDate'+opt.id+'" value="'+opt.taskName+'"/>';
			        	}else{
			        	  	result = result + '<td>---</td>';
			        	}
		             	return result;
	    	  		}
	      		},
	      	{
	    	  "targets": colIndex++,
              "data": "assignedTo",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
    	  		  if(opt.assignedTo !=null && opt.assignedTo !='' && opt.assignedTo !='undefined'){
		        		result = result + '<td>'+opt.assignedTo+'</td><input type="hidden" id="endDate'+opt.id+'" value="'+opt.assignedTo+'"/>';
		        		}else{
		        		  	result = result + '<td>---</td>';
		        		}
	             return result;
              }
	          },{
		    	  "targets": colIndex++,
	              "data": "priority",
	              "render": function ( data, type, opt, meta ) {
	              		var result = "";
	        	  		if(opt.priority !=null && opt.priority !='' && opt.priority !='undefined'){
	    		        	result = result + '<td>'+opt.priority+'</td><input type="hidden" id="scheduleMessage'+opt.id+'" value="'+opt.priority+'"/>';
	    		        }else{
	    		          	result = result + '<td>---</td>';
	    		        }
	    	     	 return result;
	             	 }
	              },
	              {
			    	  "targets": colIndex++,
		              "data": "taskRepeat",
		              "render": function ( data, type, opt, meta ) {
		              		var result = "";
		        	  		if(opt.taskRepeat !=null && opt.taskRepeat !='' && opt.taskRepeat !='undefined'){
		    		        	result = result + '<td>'+opt.taskRepeat+'</td><input type="hidden" id="scheduleMessage'+opt.id+'" value="'+opt.taskRepeat+'"/>';
		    		        }else{
		    		          	result = result + '<td>---</td>';
		    		        }
		    	     	 return result;
		             	 }
		              },
				{
			    	"targets": colIndex++,
		          	/* "data": "id", */
		          	"render": function ( data, type, opt, meta ) {
		            	var result = "";
		        	  	if(opt.id !=null && opt.id !='' && opt.id !='undefined'){
		    		       	result = result + '<td><div><i id="edit'+opt.id+'" onclick="editImmiTaskList('+opt.id+');" class="glyphicon glyphicon-pencil"></i><i id="delete'+opt.id+'" onclick="deleteImmiTaskList('+opt.id+');" class="glyphicon glyphicon-trash ml10"></i></div></td>';
		    		    }
		    	 return result;
		        }
		     }],
		     "scrollCollapse": false,
		    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
		    	"aaSorting": [[3,'desc']],
		    	"bLengthChange": true,
		    	"bDestroy": true,
		    	"ordering": true,
		    	"initComplete": function( settings, json ) {
		    		 $('#immigraListTabSerach').removeAttr('disabled');
		    	  },
		    	"oLanguage": {
		     		"sSearch": "",
		     		 "sZeroRecords": "", "sEmptyTable": "", 
		     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
		     }
    });
    $("#templateTaskListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("#immigraListTabSerach").keyup(function(e) {
    	   if(e.which === 13) {
    		   immigrationDataTableList();
    	   }
    });
}
</script>
<!--   Popup Code  --> 
<script>
// Error pop addSen
	$(document).ready(function() {
		immigrationDataTableList();
		$("#addTask").click(function(){
			document.getElementById("petitionTemplateTaskDetails").reset();
			$(".popMask").fadeIn(200);
			$("#taskViewPopup").fadeIn(100);
		});
		$(".popCan").click(function(){
			$(".popMask").fadeOut(200);
			$("#taskViewPopup").fadeOut(100);
		});
		
	});	
</script>

<script type="text/javascript">
        var config = {
        '.chosen-select'           : {},
        '.chosen-select-deselect'  : {allow_single_deselect:true},
        '.chosen-select-no-single' : {disable_search_threshold:10},
        '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
        '.chosen-select-width'     : {width:"95%"}
        }
        for (var selector in config) {
        $(selector).chosen(config[selector]);
        }
        $('.chosen-select').each(function() {
        var $this = $(this);
        $this.next().css({'width': '100%'});
        })
        $(document).ready(function(){
        $("#cd").click(function(){
        $(".clientDetails").toggleClass('hidden', 500);
        });

        });
    </script>
    <script>
    //Reminder Funciton
    $(function () {
        $("#reminder").click(function () {
            if ($(this).is(":checked")) {
            	$("#reminderdropdown").show();
            } else {
            	$("#reminderdropdown").hide();
            }
        });
    });
    
    function editImmiTaskList(id){
    	$.ajax({
    	   	type: "GET",
    	    url: sourceleadUrl+immiModulePath+"/editImmigrationTaskList?id="+id,	    	   
    	    success: function(data) {
    	    	console.log(data);
    	    	document.getElementById("petitionTemplateTaskDetails").reset();
    	    	$(".popMask").fadeIn(200);
    			$("#taskViewPopup").fadeIn(100);
    			$("#taskName").val(data.taskName);
    			$("#priority1").val(data.priority);
    			$("#taskRepeat").val(data.taskRepeat);
    			if(data.taskReminder != null){
    				$("#reminder").prop('checked', true);
    				$("#taskReminder").val(data.taskReminder);
    			}
    			if(data.taskEmail != null){
    				$("#taskEmail").prop('checked', true);
    			}
    			if(data.taskNotification != null){
    				$("#taskNotification").prop('checked', true);
    			}
    			$("#taskEmail").val(data.taskEmail);
    			$("#taskNotification").val(data.taskNotification);
    			if(data.petitionAssignedTo != null){
    				$("#assignedToU").prop('checked', true);
    				getAssignToUsersList(data.petitionAssignedTo);
    				document.getElementById("assignToUserDiv").style.display="";
    				$("#petitionAssignedTo1").removeAttr('data-validation-optional');
    				$("#petitionAssignedTo1").attr("data-validation", "required"); 
    			}else if(data.petitionAssignedTo == null){
    				$("#assignedToB").prop('checked', true);
    				document.getElementById("assignToUserDiv").style.display="none";
    				$("#petitionAssignedTo1").removeAttr('data-validation');
    				$("#petitionAssignedTo1").attr("data-validation-optional", "true");
    			}
    			$("#dueAfterMonths").val(data.dueAfterMonths);
    			$("#dueAfterDays").val(data.dueAfterDays);
    			$("#taskDescription").val(data.taskDescription);
    	    }
    	});
    }
    </script>
    <script>
function getAssignToUsersList(id){
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/getOnboardTasksAssignToUsersList?${_csrf.parameterName}=${_csrf.token}",	    	   
	    success: function(json) {
	    	 var result = "";
        	 for (var i=0; i<json.length; i++){
        		var report = json[i];
        		if(id != null){
        			var ids = id.split(',');
	        		for (j=0;j<ids.length;j++){
	        		     if(ids[j] == report.userId){
	         				result = result +'<option selected value="'+report.userId+'" >'+report.userName+'</option>';
	         			}
	        		}
        			
        		}else{
        			result = result +'<option value="'+report.userId+'" >'+report.userName+'</option>';
        		}
			}
        	 /* if(id!=null){
        		 $("#petitionAssignedTo1 :selected").val(id); 
        	 } */
        	 $("#petitionAssignedTo1").empty();
           	 $("#petitionAssignedTo1").append(result); 
           	 $('#petitionAssignedTo1').trigger("chosen:updated");
	    }
   }); 
}
</script>
<script>
function validateDueAfter(){
	  
	  var taskMonths = document.getElementById("dueAfterMonths").value;
	  var taskDays = document.getElementById("dueAfterDays").value;
	  
	  if(taskMonths == '' && taskDays == '' ){
			  $('#dueAfterMonths').removeAttr('data-validation-optional');
			  $("#dueAfterMonths").attr("data-validation", "required");
		    
	  }else{
		  $('#dueAfterDays').removeAttr('data-validation');
		  $("#dueAfterMonths").attr("data-validation-optional", "true");
		    
	  }  
	  
  }
</script>  
 <script>
  function validateTaskEmail(){
	  var taskEmail = document.getElementById("taskEmail").checked;
	  if(taskEmail == 1){
		  document.getElementById("taskEmail").value = 'Y';
	  }else{
		  document.getElementById("taskEmail").value = 'N';
	  }
	  
  }
  </script>
  <script>
  function validateAssignToType(assignToType){
	  if(assignToType == 'assingToUser'){
		  document.getElementById("assignToUserDiv").style.display="";
		  $("#petitionAssignedTo").removeAttr('data-validation-optional');
		  $("#petitionAssignedTo").attr("data-validation", "required");
		 
	  }else{
		  document.getElementById("assignToUserDiv").style.display="none";
		  $("#petitionAssignedTo").removeAttr('data-validation');
		  $("#petitionAssignedTo").attr("data-validation-optional", "true");
		  getAssignToUsersList(null);
	  }
  }
  </script>
  
  <script>
  function validateTaskNotification(){
	  var taskEmail = document.getElementById("taskNotification").checked;
	  if(taskEmail == 1){
		  document.getElementById("taskNotification").value = 'Y';
	  }else{
		  document.getElementById("taskNotification").value = 'N';
	  }
	  
  }
  </script>
  <script>
$(document).ready(function(){
	$("#templateId").val(jsonObj.id);
});
</script> 