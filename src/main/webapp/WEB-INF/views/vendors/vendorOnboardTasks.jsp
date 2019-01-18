<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="tab-c" class="newPrTabs_inn">

<div class="tab-pane" id="tasks">
              <div class="clearfix">
                <h4 class="pull-left">Task List</h4>
                <div class="pull-right">
                  <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addTask" onclick="getAssignToUsersList();"><i class="fa fa-plus paddingRight"></i>Add Task</button>
                  <!-- Modal -->
                  <div id="addTask" class="modal fade add_Task" role="dialog">
                    <div class="modal-dialog modal-lg">
                      <div class="modal-content">
                        <div class="modal-header">
                          <button type="button" class="close" data-dismiss="modal" onclick="flushOnboardTask()">&times;</button>
                          <h4 class="modal-title">Add Tasks</h4>
                        </div>
                        
                        <div class="success_pop" id="selector"  style="display: none;">
	         				<i class="fa fa-check-circle"></i><span>Task Saved Successfully.</span>
					    </div>
                        
                        <div class="modal-body clearfix"> 
                          <!-- Add New Task Widget -->
                          <div class="col-md-12 clearfix">
                          <div id="onboardtasksLoading"></div>
                             <form:form class="login-form clearfix"  id="addonboardTask_form"  name="addonboardTask_form" method="post" > 
                              <div class="row">
                              
                              <div class="col-md-6">
                                <div class="row">
                                
                                <div class="col-md-12 mb10" id="taskNameDiv">
                                  <label>Subject<span class="text-danger">*</span></label>
                                  <input type="text" class="form-control input-sm" placeholder="Subject" field-name="Subject" id="taskName" name="taskName"  data-validation="required validate_Space validate_length length2-100">
                                </div>
                                
                                <div class="col-md-12 dropdown-select mb10">
                                  <label>Priority</label>
                                  <select class="form-control" id="taskPriority" name="taskPriority">
                                    <option selected disabled>-- Select Priority --</option>
                                    <option>High</option>
                                    <option>Low</option>
                                    <option>Medium</option>
                                  </select>
                                </div>
                                
                                <div class="col-md-12 dropdown-select mb10">
                                  <label>Repeat</label>
                                  <select class="form-control input-sm" id="taskRepeat" name="taskRepeat">
                                    <option selected disabled>-- Select Repeat --</option>
                                    <option>Never</option>
                                    <option>Daily</option>
                                    <option>Weekly</option>
                                    <option>Monthly</option>
                                    <option>Yearly</option>
                                  </select>
                                </div>
                                
                                <div class="col-md-12 mb10">
                                  <div class="mt">
                                    <label class="paddingRight">
                                      <input type="checkbox" id="taskEmail" name="taskEmail" value="N" onclick="validateTaskEmail();">
                                      Email </label>
                                    <label class="paddingLeft">
                                      <input type="checkbox" id="taskNotification" name="taskNotification" value="N" onclick="validateTaskNotification();">
                                      Notification </label>
                                  </div>
                                </div>
                                
                                </div>
                                </div>
                                
                                
                                <div class="col-md-6">
	                                <div class="row">
	                                
	                                <div class="col-md-12 mb10">
                                  <label>Assign To<span class="text-danger">*</span></label>
                                  
                                  <div class="">
                                    <label class="paddingRight">
                                      <input type="radio"  name="assignedToType" checked="checked" onclick="validateAssignToType('assingToVendor');">
                                      Vendor </label>
                                    <label class="paddingLeft">
                                      <input type="radio" name="assignedToType"  onclick="validateAssignToType('assingToUser');">
                                      Associate </label>
                                  </div>
                                  
                                  <div id="assignToUserDiv" style="display: none;" class="mt10">
	                                  <select class="chosen-select" multiple data-placeholder="Select User" id="onboardAssignedTo" name="onboardAssignedTo" field-name="AssignTo"  data-validation-optional="true" >
	                                  </select>
                                  </div>
                                </div>
	                                
	                                
	                               <div class="col-md-12 mb10">
                                  <div class="row">
                                    <div class="col-md-12">

                                      <label><strong>Due date:<span class="text-danger">*</span></strong></label>

                                                                            
                                      <!-- Form inline Due after -->
                                      
                                      <div class="row" id="dueAfterDiv">
										   <div class="col-md-6 paddingRight">
											<label class="small">Months</label> <br>
												<div class="select-field">
													<select class="form-control input-sm" id="taskMonths" data-validation="required" field-name="Due date" name="taskMonths" onchange="validateDueAfter();">
														<option selected value=''>--Select Month--</option>
														<c:forEach begin="1" end="12" varStatus="loop">
															<option>${loop.index}</option>
														</c:forEach>
														</select>
												</div>
											</div> 
                                      
                                        
                                        <div class="col-md-6 paddingLeft">
										<label class="small">Days</label>
											<div class="select-field">
												<select class="form-control input-sm" id="taskDays" name="taskDays" onchange="validateDueAfter();">
													<option selected value=''>--Select Days--</option>
													<c:forEach begin="1" end="30" varStatus="loop">
															<option>${loop.index}</option>
													</c:forEach>
												</select>
											</div>
                                      </div>
                                    </div>
                                    </div>
                                      
                                      <!-- Form inline Due after --> 
                                      
                                    </div>
                                    
                                  </div>
                                </div>
	                               <div class="clearfix mb10">
                                  <label>Description</label>
                                  <textarea rows="2" placeholder="Description" id="taskdescription" name="taskdescription" class="form-control"></textarea>
                                </div>
	                                
	                                </div>
                                </div>
                                
                                <div class="clearfix"></div>
                                <div class="clearfix mt col-md-12 form-footer">
                                  <div class="pull-right">
                                    <input type="button" id="onboardtask_submit" class="btn btn-primary btn-sm" onclick="saveOnboardTask();"  value="Save">
                                    <input type="button" class="btn btn-default btn-sm ml10" value="Cancel" data-dismiss="modal" onclick="flushOnboardTask();">
                                  </div>
                                </div>
                              </div>
                            </form:form>
                          </div>
                          <!-- Add New Task Widget --> 
                          
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            
             <form:form id="finishOnboardTask_form" name="finishOnboardTask_form" method="post">    
                 <div id="onboardTasksLoading"></div>
                 <div id="onboardTasksList"></div>
            </form:form>  
              
              
</div>

<script>
function getVendorOnboardTasksList(){
	var orgRelationId = document.getElementById("orgRelationId").value;
	var vendorOnboardId = document.getElementById("vendorOnboardId").value;
	if(orgRelationId && vendorOnboardId){
		
		 $("#onboardTasksLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.ajax({
			   	type: "GET",
			    url: vmsUrl+"/getVendorOnboardTasksListByTemplateId?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationId+"&vendorOnboardId="+vendorOnboardId,	    	   
			    success: function(json) {
		        	 var result = "";
		        	if(json.length > 0){ 
			        	for (var i=0; i<json.length; i++)
						{
			        		var report = json[i];
			        		
			        		var prority = '';
			        		var repeat = '';
			        		
			        		if(report.priority != null && report.priority != '' && report.priority != 'undefined'){
			        			prority = report.priority;
			        		}else{
			        			prority = '----';
			        		}
			        		
			        		if(report.taskRepeat != null && report.taskRepeat != '' && report.taskRepeat != 'undefined'){
			        			repeat = report.taskRepeat;
			        		}else{
			        			repeat = '----';
			        		}
			        		
			        		result = result +'<div class="clearfix taskwidget_inner" id="onboardTask'+report.id+'">';
			        		result = result +'<div class="media-body">';
			        		result = result +'<div class="clearfix taskheder">';
			        		
			        		if(report.taskType == 'Onboard Task'){
			        			result = result +'<label> <span class="paddingRight">Subject:</span> <strong class="text-primary" title="'+report.taskName+'" >'+report.taskName+'</strong><a href="#"><i class="fa fa-trash text-danger paddingLeft" onClick="deleteOnboardTask('+report.id+');"></i></a></label>';
			        		}else{
			        			result = result +'<label> <span class="paddingRight">Subject:</span> <strong class="text-primary" title="'+report.taskName+'">'+report.taskName+'</strong><a href="#"><i class="fa fa-trash text-danger paddingLeft" onClick="deleteTask('+report.id+');"></i></a></label>';
			        		}
			        		
			        		
			        		result = result +'<label class="small pull-right text-muted"> <span>'+report.dueAfterString+'</span> </label>';
			        		result = result +'</div>';
			        		result = result +'<ul class="list-inline list-description mt10">';
			        		
			        		result = result +'<li><span>Assign To:</span>';
			        		
			        		if(report.usersList.length > 0){
			        			for(var j=0; j<report.usersList.length; j++){
				 					result = result +'<label>'+report.usersList[j]+'</label>';
				 				}
			        		}else{
			 					result = result +'Onboarding Vendor';
			 				}
			        		result = result +'</li>';
			        		
			        		result = result +'<li><span>Priority:</span>'+prority+'</li>';
			        		result = result +'<li><span>Repeat:</span> '+repeat+'</li>';
			        		result = result +'</ul>';
			        		result = result +'</div>';
			        		result = result +'</div>';
			        		
			        		if(report.taskType != 'Vendor Task'){
		       					result = result +"<input type='hidden'  name='vendorOnboardTasksInfo["+i+"].vendorOnboardTaskId' id='templateTask"+report.id+"' value='"+report.id+"' />";
		       				}
						}
		        	}else{
		        		result = result +'<div><h4>No Tasks Found</h4></div>';
		        	}
		        	
		        	result = result +'<div class="form-footer clearfix mt">';
		        	result = result +'<span class="pull-left">';
		        	//changed to hidden by Pavan Jampana.
		        	result = result +'&nbsp;&nbsp;<input type="hidden"  name="isMailSent" id="isMailSent" value="Y" checked>&nbsp;&nbsp;';
		        	result = result +' </span>';
		    		result = result +'<div class="pull-right mr">';
		    		result = result +'<input type="button" value="Finish" id="finish_task" onclick="saveAndFinishVendorOnbord();" class="btn btn-sm btn-primary">';
		    		result = result +'</div>';
		    		result = result +'</div>';
		    		
		    		 $("#onboardTasksList").empty();
		           	 $("#onboardTasksList").append(result); 
		    		
		           	 $("#onboardTasksLoading").removeAttr("style");
			    }
		   });
	}
}
</script>


<script>
function deleteTask(taskId){
	   $.ajax({
	    	type: "POST",
	    	 url: vmsUrl+"/deleteVendorOnboardTaskByTaskId?${_csrf.parameterName}=${_csrf.token}&taskId="+taskId, 
	        success: function(result) {
	        	if (result && result.response === "INVALID_DATA") {
          		    showError(result.responseMessage);
          		} else if (result.response === "AWKWARD") {
          		    location.href = "/errorPage";
          		} else if (result.response === "SUCCESS") {
          			document.getElementById("onboardTask"+taskId).remove();
	        		getVendorOnboardTasksList();
          		} else if (result.indexOf('Sign In') !== -1) {
          			location.replace(vmsUrl);
          		} else {
          		    showError("Operation failed, please try again");
          		}
	        		
	        },
	    });
}
</script>

<script>
function deleteOnboardTask(taskId){
	document.getElementById("onboardTask"+taskId).remove();
	$("#templateTask"+taskId).val('');
}
</script>

<script>
function saveOnboardTask(){
	
	var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         }; 
	
	  if ($('#addonboardTask_form').validate(false, validationSettings)){ 
		  	
	        var formData = getFormData1("addonboardTask_form");
	        var vendorOnboardId = document.getElementById("vendorOnboardId").value;
	        
	        $("#onboardtasksLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	        $.ajax({
	        	type : "POST",
  	    	    data: formData,
  	    	    processData: false,
		         contentType:false, 
	    	    url: vmsUrl+"/saveVendorOnBoardTasks?${_csrf.parameterName}=${_csrf.token}&vendorOnboardId="+vendorOnboardId, 
		        success: function(result) {
		        	
		        	if(result == 'success'){
		        		$("#onboardtasksLoading").removeAttr("style");
			        	flushOnboardTask();
			        	getAssignToUsersList();
			        	getVendorOnboardTasksList();
			        	$("#addTask").hide();
			        	$(".modal-backdrop").fadeOut(150);
			        	
			        	document.getElementById("selector").style.display = 'block';
			        	$("#selector").fadeIn(150);
			        	$('#selector').delay(1000).fadeOut('slow');
			        	$("body").removeClass("modal-open");
		        	}
		        	
		        },
		    });  
	        return true;
		
	  }else{
		return false;
	  }
}

$('body').on('blur', '#addonboardTask_form', function() {
	$("#addonboardTask_form").showHelpOnFocus();
	$("#addonboardTask_form").validateOnBlur(false, validationSettings);  
});
</script>


<script>
function saveAndFinishVendorOnbord(){
	
	   var formData = getFormData("finishOnboardTask_form");
	   var vendorOnboardId = document.getElementById("vendorOnboardId").value;
	   $("#onboardTasksLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
       
      $.ajax({
    	type : "POST",
  	    data: formData,
	    url: vmsUrl+"/saveAndFinishVendorOnboard?${_csrf.parameterName}=${_csrf.token}&vendorOnboardId="+vendorOnboardId, 
        success: function(result) {
        	
        	location.replace(vmsUrl+"/vendorsList"); 
        },
    }); 
}
</script>



<script>
function getAssignToUsersList(){
	var atsUrl = '${SourceLeadWeb_atsUrl}';
	$.ajax({
	   	type: "POST",
	    url: atsUrl+"/getOnboardTasksAssignToUsersList?${_csrf.parameterName}=${_csrf.token}",	    	   
	    success: function(response) {
	    	if (response.response === "AWKWARD") {
	    	    location.href = "/errorPage";
	    	} else if (response.response === "SUCCESS") {
	    		var result = "";
	    		var json = response.data;
	        	 for (var i=0; i<json.length; i++) {
	        		var report = json[i];
	        		result = result +'<option value='+report.userId+' >'+report.userName+'</option>';
				}
	        	 $("#onboardAssignedTo").empty();
	           	 $("#onboardAssignedTo").append(result); 
	           	 $('#onboardAssignedTo').trigger("chosen:updated");
	    	} else if (response.indexOf('Sign In') !== -1) {
	    		location.replace(vmsUrl);
	    	} else {
	    	    showError("Operation failed, please try again");
	    	}
	    }
   }); 
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
  function flushOnboardTask(){
	  document.getElementById("addonboardTask_form").reset();
	  $('#taskMonths').removeAttr('data-validation-optional');
	  $("#taskMonths").attr("data-validation", "required");
	  
	  $('#dueAfterDiv').find('p.jquery_form_error_message').remove();	
	  $('#taskNameDiv').find('p.jquery_form_error_message').remove();
	  document.getElementById("assignToUserDiv").style.display="none";
	  $('#onboardAssignedTo').removeAttr('data-validation');
	  $("#onboardAssignedTo").attr("data-validation-optional", "true");
	  $('#assignToUserDiv').find('p.jquery_form_error_message').remove();
  }
  </script>
  
  <script>
  function validateAssignToType(assignToType){
	  if(assignToType == 'assingToUser'){
		  document.getElementById("assignToUserDiv").style.display="";
		  $('#onboardAssignedTo').removeAttr('data-validation-optional');
		  $("#onboardAssignedTo").attr("data-validation", "required");
		 
	  }else{
		  document.getElementById("assignToUserDiv").style.display="none";
		  $('#onboardAssignedTo').removeAttr('data-validation');
		  $("#onboardAssignedTo").attr("data-validation-optional", "true");
		  getAssignToUsersList();
	  }
  }
  </script>
  
  
  <script>
  function validateDueAfter(){
	  
	  var taskMonths = document.getElementById("taskMonths").value;
	  var taskDays = document.getElementById("taskDays").value;
	  
	  if(taskMonths == '' && taskDays == '' ){
			  $('#taskMonths').removeAttr('data-validation-optional');
			  $("#taskMonths").attr("data-validation", "required");
		    
	  }else{
		  $('#taskMonths').removeAttr('data-validation');
		  $("#taskMonths").attr("data-validation-optional", "true");
		    
	  }  
	  
  }
  </script>