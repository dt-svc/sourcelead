var requirementWorkflow = new RequirementWorkflow();

var iconSpan = "";
var editAndDeleteAnchor = "";
var lastStage = "";
var commentReq = "";
var questionnaireId = null;
var questionnaireName = null;
var deletedStageUuids = new Array();
var workflowName = "";
var configureType = "";

function RequirementWorkflow(){
	
		this.getDefaultWorkflowStages = function() {
			$.ajax({
				type : "GET",
				url : sourceleadUrl + "/getDefaultWorkFlowStages",
				success : function(dataList) {
					var result = "";
					if(dataList != null && dataList.length > 0){
						 result = result + "<ul class='list-unstyled widgetDivLi'>"; 
						for(var i = 0 ; i < dataList.length; i++){
							
						//changing dynamic spans based on stage names and anchors for stages based on editable. 
						iconSpan = requirementWorkflow.dynamicSpan(dataList[i].stageName);
						
						result = result + "<li class='widgetDiv mb clearfix'>";
						result = result + "<div class='row'>";
						result = result + "<div class='col-md-10'>";
						result = result + iconSpan
						result = result + "<h4 class='pull-left mt10 ml' id = 'noOfStages'>"+dataList[i].stageName+"</h4>"; 
						result = result + "</div>";
						result = result + "<div class='col-md-2'>";
						result = result + "<ul class='list-inline list-unstyled pull-right mb0'>";
						result = result + "</ul></div></div></li>";
						}
						 result = result + "</ul>"; 
					}
					$("#dynamicWorkflow").empty().append(result);
				},
			});
		}
		
		this.dynamicSpan = function(stageName){
			if(stageName != null){
					if(stageName == "Submission"){
						return "<i class='fa fa-check-square-o fa-2x text-muted pull-left'></i>";
					}
					if(stageName == "Short List"){
						return "<i class='fa fa-list-ul fa-2x text-muted pull-left'></i>";
					}
					if(stageName == "Submit to Client"){
						return "<i class='fa fa-send fa-2x text-muted pull-left'></i>";
					}
					if(stageName == "Interview"){
						return "<i class='fa fa-handshake-o fa-2x text-muted pull-left'></i>";
					}
					if(stageName == "Offer"){
						return "<i class='fa fa-envelope-open-o fa-2x text-muted pull-left'></i>";
					}
					if(stageName == "Reject"){
						return "<i class='fa fa-ban pull-left fa-2x text-muted'></i>"
					}
					if(stageName == "Joined"){
						return "<i class='fa fa-id-badge pull-left fa-2x text-muted'></i>";
					}
					if(stageName == "Backout"){
						return "<i class='fa fa-arrow-circle-left pull-left fa-2x text-muted'></i>"
					}
					if(stageName == "On-hold"){
						return "<i class='fa fa-pause-circle-o pull-left fa-2x text-muted'></i>";
					}
					if(stageName == "No Response"){
						return "<i class='fa fa-exclamation-circle pull-left fa-2x text-muted'></i>";
					}
				}
			}
	
	this.saveRequirementWorkFlow = function(){
	      var validationSettings = {
	           errorMessagePosition: 'element',
	           borderColorOnError: '',
	           scrollToTopOnError: true,
	           dateFormat: 'yyyy/mm/dd'
	       };
	     
		  if ($('#requirementWorkFlowForm').validate(false, validationSettings)) {
			  var formData = getFormData("requirementWorkFlowForm");
			  workflowName = $('#workFlowName').val();
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
							
							location.replace(sourceleadUrl+"/workflowList?workflowName="+workflowName);	
							
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
	
	
	this.getWorkflowList = function(){
		
		$("#dynamicWorkFlowList").empty();
		var result = "";
	  	var searchValue = $("#dynamicUserDataTableSearch").val();
	  	var url = "";
	  	result = result +"<table id='workflowListTable' class='display' cellspacing='0' width='100%'>";
	  	result = result +"<thead>";
	  	result = result +"<tr>"
	  	result = result +"<th>Name</th>";
	  	result = result +"<th>Description</th>";
	  	result = result +"<th>Stages</th>";
	  	result = result +"<th>Status</th>";
	  	result = result +"<th>UpdatedBy</th>";
	  	result = result +"<th>Action</th>";  
	  	result = result +"</tr>";
	  	result = result +"</thead>";
	  	result = result +"</table>";
	  	$("#dynamicWorkFlowList").html(result);
	  	requirementWorkflow.getDynamicWorkflowList();
	}

	this.getDynamicWorkflowList = function(){
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
		        	{ "data": "description" },
		        	{ "data": "noOfStages" },
		        	{ "data": "status" },
		        	{ "data": "updatedBy" }
		        ],
		        "columnDefs": [ {
		    	  	"targets": colIndex++,
		    	  	"data": "workFlowName",
		    	  	"render": function(data, type, workflow, meta) {
		    	  		var result = "";
	              		if(workflow.workFlowName != null && workflow.workFlowName != '' && workflow.workFlowName != "undefined"){
	              			result = requirementWorkflow.checkDefaultWorkflow(workflow.configureType, workflow.uuid, workflow.workFlowName); 
	  	              	}else{
	  	            	  		result = result +"<div>---</div>";
	  	             	}
	  	             	 return result;
		    	  	}
		        },{"targets": colIndex++,
		    	  	"data": "description",
		    	  	"render": function(data, type, workflow, meta) {
		    	  		var result = "";
	              		if(workflow.description != null && workflow.description != '' && workflow.description != "undefined"){
	              			result = result +"<div class='tbldata-dotdot-vsm'><span title='"+workflow.description+"'>"+workflow.description+"</span></div>";
	  	              	}else{
	  	            	  		result = result +"<div>---</div>";
	  	             	}
	  	             	 return result;
		    	  	}
		        },{"targets": colIndex++,
		    	  	"data": "noOfStages",
		    	  	"render": function(data, type, workflow, meta) {
		    	  		var result = "";
		    	  		if(workflow.noOfStages != null && workflow.noOfStages != '' && workflow.noOfStages != "undefined"){
	              			result = result +"<div class='tbldata-dotdot-vsm'><span title='"+workflow.noOfStages+"'>"+workflow.noOfStages+"</span></div>";
	  	              	}else{
	  	            	  		result = result +"<div>---</div>";
	  	             	}
	  	             	 return result;
		    	  	}
		        },{
		    	  	"targets": colIndex++,
		    	  	"data": "status",
		    	  	"render": function(data, type, workflow, meta) {
		    	  		var result = "";
		    	  		if(workflow.status != null && workflow.status !== '' && workflow.status != "undefined"){
		    	  			if(workflow.status == 1){
		    	  				result = result +"<div class='tbldata-dotdot-vsm'title = 'Active'>Active</div>";
		    	  			}	
		    	  			else{
		    	  				result = result +"<div class='tbldata-dotdot-vsm' title = 'In-Active'>In-Active</div>";
		    	  			}	
		    	  		}else{
	  	            	  		  result = result +"<div>---</div>"; 
	  	             	}
	  	             	 return result;
		    	  	}
		        },{"targets": colIndex++,
		        	"data": "updatedBy",
		        	"render": function(data, type, workflow, meta) {
		        	var result = "";
		        	if(workflow.updatedBy != null && workflow.updatedBy != '' && workflow.updatedBy != "undefined"){
		        	result = result +"<div class='tbldata-dotdot-100'><span title='"+workflow.updatedBy+"'>"+workflow.updatedBy+"</span></div>";
		        	}else{
		        	result = result +"<div>---</div>";
		        	}
		        	return result;
		        	}
		        	},{
		    	  	"targets": colIndex++,
		    	  	 bSortable: false,
	                 aTargets: [ -1 ],
		    	  	"render": function(data, type, workflow, meta) {
		    	  		var result = "";
		    	  		
	    	  		result = result + requirementWorkflow.checkConfigureType(workflow.configureType, workflow.status, workflow.uuid,workflow.workFlowName,workflow.description);
	  	             	return result;
		    	  	}
		        }
	        ],
		        "scrollCollapse": false,
		    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
		    	/*"aaSorting": [[0,'desc']],*/
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
			        	 requirementWorkflow.getWorkflowList();
			        
			         }
			     });
		        
	}

	this.checkDefaultWorkflow = function(configureType, uuid, workFlowName){
		var result = "";
		result = result +"<div class='tbldata-dotdot-200'><a onclick='requirementWorkflow.viewWorkFlow(\""+uuid+"\")' title='"+workFlowName+"'>"+workFlowName+"</a></div>";
		return result;
	}

	this.checkConfigureType = function(configureType, status, uuid,workFlowName,description){

		var result = "";
		if(configureType != "Default"){
	  		result = result +"<div class='width215px'>";
	  		result = result +"<ul class='list-inline list-unstyled'>";
	  		result = result +"<li>";
	  		result = result +"<label class='switch mr positionRelativeTop10' title='Default'>"
	  		result = result +"<input type='checkbox' name='' "+ (status == 1 ?'checked':'')+" >";
	  		 if(roleActionsArray.indexOf("ROLE_EDIT_VIEW_WORKFLOW")  > -1){
	 	  		result = result +"<div class='slider round' onclick = 'requirementWorkflow.changeWorkflowStatus(\""+uuid+"\", \""+status+"\", \""+workFlowName+"\");'></div>";
				 }
	  		result = result +"</label></li>";
	  		 if(roleActionsArray.indexOf("ROLE_EDIT_VIEW_WORKFLOW")  > -1){
	 	  		result = result +"<li><a onclick='requirementWorkflow.viewWorkFlow(\""+uuid+"\")'><i class='fa fa-pencil cursorPointer' title='Edit'></i></a></li>";
			 }
	  		 if(roleActionsArray.indexOf("ROLE_DELETE_WORKFLOW")  > -1){
	  			result = result +"<li><i class='fa fa-trash cursorPointer' title='Delete' onclick='requirementWorkflow.checkWorkflowInRequirements(\""+uuid+"\", \""+workFlowName+"\")'></i></li>";
				 }
	  		 if(roleActionsArray.indexOf("ROLE_ADD_WORKFLOW")  > -1){
	 			result = result +"<li><i onclick='requirementWorkflow.cloneWorkFlow(\""+uuid+"\",\""+workFlowName+"\",\""+description+"\");'class='fa fa fa-clone cursorPointer text-primary' title='Clone'></i></li>";
					 }
	  		result = result +"</ul></div>";
	  		}
		return result;
	}

	this.changeWorkflowStatus = function(uuid, status, workFlowName){
		$.ajax({
			type : "GET",
			url : sourceleadUrl + "/getWorkflowList?uuid="+uuid+"&status="+status,
			success : function(dataList) {
				requirementWorkflow.getDynamicWorkflowList();
				if(status == 1){
					status = 'In-Active';
				}if(status == 0){
					status = 'Active';
				}
				requirementWorkflow.showSuccessMsg(workFlowName+" is "+status);
			}
			});
				
	}
	this.cloneWorkFlow = function(uuid,workFlowName,description){
		$.ajax({
			type : "POST",
			url : sourceleadUrl + "/cloneWorkFlow?uuid="+uuid+"&workFlowName="+workFlowName+"&description="+description,
			success : function(dataList) {
				requirementWorkflow.getDynamicWorkflowList();
			}
			});
				
	}
	this.viewWorkFlow = function(uuid){
		location.href = sourceleadUrl+ "/viewWorkflow?uuid="+uuid
	}

	
		this.deleteWorkflowEntry = function(workflowuuid, workFlowName){ 
			 var uid = workflowuuid; 
			var result ="";
			result = result +"	<div class='project_inn_mask'></div>"; 
			result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Do you want to delete "+workFlowName+"</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"	<b href='javascript:void(0)'><input type='button' value='Ok' onclick='requirementWorkflow.removeWorkFlowEntry(\""+workflowuuid+"\", \""+workFlowName+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='requirementWorkflow.cancel();'></b>";
			result = result +"	</div>";
			$("#popUp").empty().append(result);
			$("#remove_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
		}
		
		this.cancel = function(){
			$('.mask').hide();
			$("#popUp").empty();
			$(".project_inn_mask").fadeOut(200);
			}	

		this.removeWorkFlowEntry = function(workflowuuid, workFlowName){
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
	 		  				requirementWorkflow.getWorkflowList();
	 		  				requirementWorkflow.showSuccessMsg("Successfully deleted "+workFlowName);
	 		  			}
				    }		
			 	})
		}
		
		 this.editStage = function(stageUuid, workflowUuid, workFlowName){
			window.location.href = sourceleadUrl + "/editStage?uuid="+stageUuid+"&requirementWorkFlowUuid="+workflowUuid+"&workflowName="+workFlowName;
			
		} 

		 this.cancelViewWorkflow = function(){
			window.location.href = sourceleadUrl + "/workflowList";
		}

		 this.saveRequirementAndAddAStage = function(workflowUuid, stageName, workflowName){
		 var validationSettings = {
		           errorMessagePosition: 'element',
		           borderColorOnError: '',
		           scrollToTopOnError: true,
		           dateFormat: 'yyyy/mm/dd'
		       };
		     
			  if ($('#requirementWorkFlowForm').validate(false, validationSettings)) {
				  var formData = getFormData("requirementWorkFlowForm");
				  workflowName = $('#workFlowName').val();
				   $("#stageButton").attr("disabled", "disabled");
		           $("#stageButton").addClass('button').val('Processing..'); 
			 $.ajax({
		           type: "POST",
		           data : formData,
		           url:sourceleadUrl+"/saveWorkFlowDetails?uuid="+workflowUuid,
		           success: function (result) {
						if (result.response === "AWKWARD") {
			        	    location.href = "/errorPage";
			        	} else if (result.response === "SUCCESS") {
			        		if(result.object === "DataMissing") {
								$("#stageButton").removeAttr("disabled");
								$("#stageButton").removeAttr('button').val('Save');	
								showError("Invalid data");
							}else{
								if(deletedStageUuids.length == 0){
									 window.location.href = sourceleadUrl + "/addStage?uuid="+workflowUuid+"&workFlowName="+workflowName;
								}
							}
			        	} else if (result.indexOf('Sign In') !== -1) {
			        		location.replace(sourceleadUrl);
			        	} else {
			        	    showError("Operation failed, please try again");
			        	}
		           },complete: function (result) {
		        	    if(deletedStageUuids.length > 0){
		        	   requirementWorkflow.goTOAddStage(deletedStageUuids, workflowUuid, stageName, workflowName);
		        	   }
		            }
		       });
		     } 
		 }
		 
		 this.goTOAddStage = function(deletedStageUuids, workflowUuid, stageName, workflowName){
				$.ajax({
					type : "GET",
					url : sourceleadUrl + "/deleteRequirementWorkflowStage?deletedStageUuids="+deletedStageUuids+"&requirementWorkFlowUuid="+workflowUuid,
					success : function(dataList){
						if(dataList == "Success"){
							window.location.href = sourceleadUrl + "/addStage?uuid="+workflowUuid+"&workFlowName="+workflowName;
						}else{
							 showError("Operation failed, please try again");
						}
						}
					});
					
			}
		 
		
		this.saveRequirementViewWorkFlow = function(workflowUuid, stageName){
		      var validationSettings = {
		           errorMessagePosition: 'element',
		           borderColorOnError: '',
		           scrollToTopOnError: true,
		           dateFormat: 'yyyy/mm/dd'
		       };
		     
			  if ($('#requirementWorkFlowForm').validate(false, validationSettings)) {
				  var formData = getFormData("requirementWorkFlowForm");
				  workflowName = $('#workFlowName').val();
				   $("#saveReqWorkFlow").attr("disabled", "disabled");
		           $("#saveReqWorkFlow").addClass('button').val('Processing..'); 
			 $.ajax({
		           type: "POST",
		           data : formData,
		           url:sourceleadUrl+"/saveWorkFlowDetails?uuid="+workflowUuid,
		           success: function (result) {
						if (result.response === "AWKWARD") {
			        	    location.href = "/errorPage";
			        	} else if (result.response === "SUCCESS") {
			        		if(result.object === "DataMissing") {
								$("#saveReqWorkFlow").removeAttr("disabled");
								$("#saveReqWorkFlow").removeAttr('button').val('Save');	
								showError("Invalid data");
							}else{
								if(deletedStageUuids.length == 0){
									location.replace(sourceleadUrl+"/workflowList?workflowName="+workflowName);	
								}
							}
			        	} else if (result.indexOf('Sign In') !== -1) {
			        		location.replace(sourceleadUrl);
			        	} else {
			        	    showError("Operation failed, please try again");
			        	}
		           }/*,complete: function (result) {
		        	    if(deletedStageUuids.length > 0){
		        	   requirementWorkflow.deleteRequirementWorkflowStages(deletedStageUuids, workflowUuid, stageName, workflowName);
		        	   }
		            }*/
		       });
		     } 
		}

		this.deleteDynamicStages = function(stageUuid, workflowUuid, stageName){
			$("#popUp").empty();
		     $(".project_inn_mask").fadeOut(200);
			$("ul#stageList li:contains('"+stageName+"')").remove();
			requirementWorkflow.showSuccessMsg("Successfully Deleted "+stageName);
			deletedStageUuids.push(stageUuid);
		}
		
		this.deletepopup = function(stageUuid, stageName, workflowUuid){
			var result = "";
			$("#popUp").empty();
			result = result +" <div class='project_inn_mask'></div>"; 
			result = result +" <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +" <p>Do you want to delete "+stageName+"?</p>";
			result = result +' <b href="javascript:void(0)"><input id="confirm-id" type="button" value="Ok" onclick="requirementWorkflow.deleteRequirementWorkflowStages(\''+stageUuid+'\', \''+workflowUuid+'\', \''+stageName+'\');"><input class="u_can_color" type="button" value="Cancel" onClick="requirementWorkflow.cancelTermination();"></b>';
			result = result +" </div>";
			$("#popUp").append(result);
			$("#remove_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
			}
		
			this.getViewWorkflowStagesList = function() {
				var filter = $("#searchtext").val();
				$("li.widgetDiv").each(function() {
					if ($(this).text().search(new RegExp(filter, "i")) < 0) {
						$(this).hide();
					} else {
						$(this).show();
					}
				});
			}

			
			this.deleteRequirementWorkflowStages = function(deletedStageUuids, workflowUuid, stageName){
				$.ajax({
					type : "GET",
					url : sourceleadUrl + "/deleteRequirementWorkflowStage?deletedStageUuids="+deletedStageUuids+"&requirementWorkFlowUuid="+workflowUuid,
					success : function(dataList){
						if(dataList == "Success"){
							$("#popUp").empty();
						     $(".project_inn_mask").fadeOut(200);
							$("ul#stageList li:contains('"+stageName+"')").remove();
							requirementWorkflow.showSuccessMsg("Successfully Deleted "+stageName);
						}else{
							 showError("Operation failed, please try again");
						}
						}
					});
					
			}
			
			this.cancelTermination = function(){
				 $("#popUp").empty();
			     $(".project_inn_mask").fadeOut(200);
			}
			this.showSuccessMsg = function(msg){
				$('#selector').show();
				$('#msgDiv').text(msg)
				setTimeout(function(){ $('#selector').hide();$('#msgDiv').text("") }, 1000);
			}
			
			
			this.getRequirementWorkflowTasks = function(currentStageUuid){
					$("#dynamicWorkflowTask").empty();
					var result = "";
				  	var url = "";
				  	result = result +"<table id='workflowTaskTable' class='display' cellspacing='0' width='100%'>";
				  	result = result +"<thead>";
				  	result = result +"<tr>"
				  	result = result +"<th>Title</th>";
				  	result = result +"<th>Assign To</th>";
				  	result = result +"<th>Prioirity</th>";
				  	result = result +"<th>Description</th>";  
					result = result +"<th>Action</th>"; 
				  	result = result +"</tr>";
				  	result = result +"</thead>";
				  	result = result +"</table>";
				  	$("#dynamicWorkflowTask").html(result);
				  	requirementWorkflow.getDynamicWorkflowTasks(currentStageUuid);
				}
				
				this.getDynamicWorkflowTasks = function(currentStageUuid){
					   var colIndex = 0;
					    $('#workflowTaskTable').DataTable( { 
					    	"processing": true,
					        "serverSide": true,
					        "searching": false,
					        "searchHighlight": true,
					        "ajax": $.fn.dataTable.pipeline( {
					            url: sourceleadUrl + "/getWorkflowTaskList?currentStageUuid="+currentStageUuid,
					            method: 'GET',
					            pages: 10
					        }),
					        "columns": [
					        	{ "data": "workFlowTaskName" },
					        	{ "data": "assignToUserId" },
					        	{ "data": "priority" },
					        	{ "data": "description" }
					        ],
					        "columnDefs": [ {
					    	  	"targets": colIndex++,
					    	  	"sortable": false,
					    	  	"data": "workFlowTaskName",
					    	  	"render": function(data, type, workflowTask, meta) {
					    	  		var result = "";
				              		if(workflowTask.workFlowTaskName != null && workflowTask.workFlowTaskName != '' && workflowTask.workFlowTaskName != "undefined"){
				              			result = result +"<div class='tbldata-dotdot-sm'>"+workflowTask.workFlowTaskName+"</div>";
				  	              	}else{
				  	            	  		result = result +"<div>---</div>";
				  	             	}
				  	             	 return result;
					    	  	}
					        },{
					    	  	"targets": colIndex++,
					    	  	"sortable": false,
					    	  	"data": "assignToUserId",
					    	  	"render": function(data, type, workflowTask, meta) {
					    	  		var result = "";
					    	  		if(workflowTask.assignToUserId != null && workflowTask.assignToUserId != '' && workflowTask.assignToUserId != "undefined"){
				              			result = result +"<div class='tbldata-dotdot-vsm'>"+workflowTask.assignToUserId+"</div>";
				  	              	}else{
				  	            	  		result = result +"<div>---</div>";
				  	             	}
				  	             	 return result;
					    	  	}
					        },{
					    	  	"targets": colIndex++,
					    	  	"sortable": false,
					    	  	"data": "priority",
					    	  	"render": function(data, type, workflowTask, meta) {
					    	  		var result = "";
					    	  		if(workflowTask.priority != null && workflowTask.priority !== '' && workflowTask.priority != "undefined"){
					    	  				result = result +"<div class='tbldata-dotdot-vsm'>"+workflowTask.priority+"</div>";
					    	  		}else{
				  	            	  		  result = result +"<div>---</div>"; 
				  	             	}
				  	             	 return result;
					    	  	}
					        },{
					    	  	"targets": colIndex++,
					    	  	"sortable": false,
					    	  	"data": "description",
					    	  	"render": function(data, type, workflowTask, meta) {
					    	  		var result = "";
					    	  		if(workflowTask.workFlowTaskDescription != null && workflowTask.workFlowTaskDescription !== '' && workflowTask.workFlowTaskDescription != "undefined"){
					    	  				result = result +"<div class='tbldata-dotdot-big'>"+workflowTask.workFlowTaskDescription+"</div>";
					    	  		}else{
				  	            	  		  result = result +"<div>---</div>"; 
				  	             	}
				  	             	 return result;
					    	  	}
					        },{
					    	  	"targets": colIndex++,
					    	  	"sortable": false,
					    	  	"render": function(data, type, workflowTask, meta) {
					    	  		var result = "";
					    	  		'<sec:authorize access="hasAnyRole(\'ROLE_EDIT_VIEW_WORKFLOW\')">';
					    	  		 result = result +"<div class='tbldata-dotdot-vsm'><a class='glyphicon glyphicon-trash text-danger ml' title='Delete' onclick='requirementWorkflow.deleteAssignTaskpoup(\""+workflowTask.uuid+"\",\""+workflowTask.workFlowTaskName+"\")'></a></div>";
					    	  		'</sec:authorize>';
					    	  		 return result;
				  	             	
					    	  	}
					        }],
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
					     $("#workflowTaskTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
					  
				}
				
				this.validateAndSaveStage = function(workflowUuid){
					$('#addTaskForm').remove();
					requirementWorkflow.saveRequirementWorkflowStage(workflowUuid);
				}
				
				this.saveRequirementWorkflowStage = function(workflowUuid){
					 var validationSettings = {
					           errorMessagePosition: 'element',
					           borderColorOnError: '',
					           scrollToTopOnError: true,
					           dateFormat: 'yyyy/mm/dd'
					 };
					var stageName = $("#stageName").val();
					var description = $("textarea#stageDescription").val();
					var stageUuid = $("#workflowStageUuid").val();
					var qTemplateId = $("#questionnaireTemplateId").val();
					
					var requiredStatsList = $('input[name=list_required]:checked').val();
					var chosenColumns = [];
					$('#chosecolumnsID input:checked').each(function() {
						chosenColumns.push($(this).val());
					});
					
					//var chosenColumnJson = JSON.stringify(chosenColumns.join()).replace(/"/g, '');
					
				     var choosenCloumnString = encodeURI(chosenColumns.join().replace(/"/g, '')); // encodeURI function used to escape special characters in header string.
				     
					 var previousStageUuid = $("#previousStageUuid").val();
					
					
					/*if(previousStageUuid == null || previousStageUuid == "Select"){
					
						$("#previousStage").show();
					}if(previousStageUuid != null){
						$("#previousStage").hide();
					}*/
					 
					if(qTemplateId == "Select"){
						qTemplateId = "";
					}
					
					if(stageUuid == null){
						stageUuid = $("#previousStageUuid").val();
					}
					var currentStageUuid = $("#currentStageUuid").val();

					
					 if ($('#addStageForm').validate(false, validationSettings)) {
						 if ($('#stagesForm').validate(false, validationSettings)) {
							 if ($('#columnsForm').validate(false, validationSettings)) {
						  var formData = getFormData("addStageForm");
						   $("#saveStageButton").attr("disabled", "disabled");
				        $("#saveStageButton").addClass('button').val('Processing..'); 
					$.ajax({
						type : "GET",
						 data : formData,
						url : sourceleadUrl + "/saveRequirementWorkflowStage?description="+description+"&uuid="+stageUuid+
								"&requirementWorkFlowUuid="+workflowUuid+"&requirementQuestionnaireTemplateId="+qTemplateId+"&currentStageUuid="+currentStageUuid+
								"&lastStage="+lastStage+"&commentRequired="+commentReq+"&requiredStatsList="+requiredStatsList+"&chosenColumnStr="+choosenCloumnString+"&configureType="+configureType,
						success : function(dataList) {
							if (dataList.response === "AWKWARD") {
				      	    location.href = "/errorPage";
				      	} else if (dataList.response === "SUCCESS") {
				      		if(dataList.object === "DataMissing") {
									$("#saveStageButton").removeAttr("disabled");
									$("#saveStageButton").removeAttr('button').val('Save');	
									showError("Invalid data");
								}else{
									window.location.replace(sourceleadUrl+"/workflowList?workflowName="+workFlowName);	
								}
				      	} else if (dataList.indexOf('Sign In') !== -1) {
				      		location.replace(sourceleadUrl);
				      	} else {
				      	    showError("Operation failed, please try again");
				      	}
									
						}
						});
					 }}}
				}

				this.getNextWorkflowStage = function(select, worflowUuid, formId){
					$("#"+formId).find('p.jquery_form_error_message').remove();
					var currentStageUuid = $("#currentStageUuid").val();
					var stageUuid = select.value;
						$.ajax({
							type : "GET",
							url : sourceleadUrl + "/getRequirementWorkflowStage?uuid="+stageUuid+"&requirementWorkFlowUuid="+worflowUuid+"&currentStageUuid="+currentStageUuid,
							success : function(dataList){
								var result = "";
								 result = result + "<select class='sel form-control' id = 'workflowStageUuid' name = 'workflowStageUuid'>";
								if(dataList != null && dataList.length > 1){
									lastStage = "";
									for(var i=0 ; i<dataList.length;i++){
										
										if(dataList[i].uuid == stageUuid){
											if(dataList[i+1].uuid == currentStageUuid ){
												result = result + "<option value="+dataList[i+2].uuid+">"+dataList[i+2].stageName+"</option>";
											}
											else{
											result = result + "<option value="+dataList[i+1].uuid+">"+dataList[i+1].stageName+"</option>";
											}
											}
										$("#workflowStageUuid option[value='"+dataList[i].uuid+"']").remove();
									}
								}
								else{
									lastStage = "lastStage";
									result = "<h2>No Stage.</h2>";
								}
								 result = result + "</select>"; 
								$("#nextStages").empty().append(result);
								if(currentStageUuid != null){
								$("#workflowStageUuid option[value='"+currentStageUuid+"']").remove();
								}
							}
						});
				}
			
				this.getRequirementQuestionnaireTemplates = function(){
					$.ajax({
						type : "GET",
						url : sourceleadUrl + "/getRequirementQuestionnaireTemplates",
						success : function(dataList){
							var result = "";
							result = result + "<select class='sel form-control' id = 'questionnaireTemplateId'>";
							result = result + "<option selected>Select</option>";
							if(dataList != null && dataList.length > 0){
								 for(var i = 0 ; i < dataList.length; i++){
									 if(questionnaireId != null && dataList[i].id == questionnaireId){
										 result = result + "<option selected value="+questionnaireId+">"+questionnaireName+"</option>";
									 }else{
										 result = result + "<option value="+dataList[i].id+">"+dataList[i].name+"</option>";
								 }
								 }
							}
							result = result + "</select>";
							$("#questionnaireList").empty().append(result);
						}
						});
				}

					 
					 this.createNewtask = function() {
						 requirementWorkflow.getAllActiveUsersList();
						   $("#workFlowTaskName").val("");
							$("#priority").val("");
							$("#workFlowTaskDescription").val("");
							$("#assignToUserId").val("");
							/*$('#taskDescription').summernote();*/
							document.getElementById("createNewtask").style.display = "block";
						}
					 
					 this.getAllActiveUsersList = function(){
							$.ajax({
								type : "GET",
								url : sourceleadUrl + "/getAllActiveUsersList",
								success : function(dataList){
									 $("#saveStageButton").attr("disabled", "disabled");
									var result = "";
									result = result + "<select class='sel form-control' name = 'assignToUserId' id = 'assignToUserId' field-name='Assign To' data-validation='required'>";
									result = result + "<option selected='' value=''>Select</option>";
									if(dataList != null && dataList.length > 0){
										 for(var i = 0 ; i < dataList.length; i++){
											 result = result + "<option value="+dataList[i].userId+">"+dataList[i].userName+"</option>";
										 }
									}
									result = result + "</select>";
									$("#assignToUserId").empty().append(result);
								}
								});
						}
					 
					 this.deleteTask = function(formId){
						 $("#"+formId).find('p.jquery_form_error_message').remove();
							$("#createNewtask").hide();
							$("#workFlowTaskName").val("");
							$("#priority").val("");
							$("#summerNote").val("");
							$("#saveStageButton").removeAttr("disabled");
						}
					
					 this.saveWorkflowTask = function(worflowUuid){
						 var validationSettings = {
						           errorMessagePosition: 'element',
						           borderColorOnError: '',
						           scrollToTopOnError: true,
						           dateFormat: 'yyyy/mm/dd'
						       };
	/*					var workFlowTaskName = $("#workFlowTaskName").val();
						var priority = $("#workflowTaskPriority").val();
						var taskDescription = $("#workFlowTaskDescription").val();
						var assignToUserId = $("#assignToUserId").val();
	*/					var currentStageUuid = $("#currentStageUuid").val();

						var stageName = $("#stageName").val();
						var description = $("textarea#stageDescription").val();
						
						if(currentStageUuid == ""){
							currentStageUuid = "Default";
						}
						
						if ($('#addTaskForm').validate(false, validationSettings)) {
							  var formData = getFormData("addTaskForm");
						$.ajax({
							type : "POST",
							data : formData,
							/*url : sourceleadUrl + "/saveWorkflowTask?requirementWorkFlowUuid="+worflowUuid+"&workFlowTaskName="+workFlowTaskName+"&priority="+priority
									+"&workFlowTaskDescription="+taskDescription+"&assignToUserId="+assignToUserId+"&currentStageUuid="+currentStageUuid
									+"&stageName="+stageName+"&description="+description,*/
							url : sourceleadUrl + "/saveWorkflowTask?requirementWorkFlowUuid="+worflowUuid+"&currentStageUuid="+currentStageUuid
							+"&stageName="+stageName+"&description="+description,
							success : function(dataList){
								$("#createNewtask").hide();
								if(dataList.response != null){
									requirementWorkflow.getRequirementWorkflowTasks(dataList.response);
									
									$("#currentStageUuid").val(dataList.response);
									$("#saveStageButton").removeAttr("disabled");
								}if(dataList.response == null){
									showError("Operation failed, please try again");
								}
								 /*$("#workFlowTaskName").val("");
									$("#priority").val("");
									$("#workFlowTaskDescription").val("");
									$("#assignToUserId").val("");*/
							}
						});
						}
					} 
	
				 this.getWorkflowQuestionnnaireList = function(){
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
				   	requirementWorkflow.getDynamicWorkflowQuestionnnaireList();
				 }

				 this.getDynamicWorkflowQuestionnnaireList = function(){
				 	   var colIndex = 0;
				 	   var searchValue = $("#dynamicUserDataTableSearch").val();
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
				 	    	  	"data": "name",
				 	    	  	"render": function(data, type, workflow, meta) {
				 	    	  		var result = "";
				               		if(workflow.name != null && workflow.name != '' && workflow.name != "undefined"){
				               			result = result +"<div class ='tbldata-dotdot-200'><span title='"+workflow.name+"'>"+workflow.name+"</span></div>";
				   	              	}else{
				   	            	  		result = result +"<div>---</div>";
				   	             	}
				   	             	 return result;
				 	    	  	}
				 	        },{
				 	    	  	"targets": colIndex++,
				 	    	  	"data": "description",
				 	    	  	"render": function(data, type, workflow, meta) {
				 	    	  		var result = "";
				 	    	  		if(workflow.description != null && workflow.description != '' && workflow.description != "undefined"){
				               			result = result +"<div class='tbldata-dotdot-200'><span title='"+workflow.description+"'>"+workflow.description+"</span></div>";
				   	              	}else{
				   	            	  		result = result +"<div>---</div>";
				   	             	}
				   	             	 return result;
				 	    	  	}
				 	        },{
				 	    	  	"targets": colIndex++,
				 	    	  	"data": "createdBy",
				 	    	  	"render": function(data, type, workflow, meta) {
				 	    	  		var result = "";
				 	    	  		var workflowStatus;
				 	    	  		if(workflow.createdBy != null && workflow.createdBy != '' && workflow.createdBy != "undefined"){
				               			result = result +"<div class='tbldata-dotdot-vsm'><span title='"+workflow.createdBy+"'>"+workflow.createdBy+"</span></div>";
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
				 	    	  		if(roleActionsArray.indexOf("ROLE_EDIT_VIEW_WORKFLOW")  > -1){
				 	    	  		result = result +"<li><a href= "+sourceleadUrl+"/editQuestionnaire?templateUuid="+workflow.uuid+"><i class='fa fa-pencil cursorPointer' title='Edit'></i></a></li>";
				 	    	  		}
				 	    	  		if(roleActionsArray.indexOf("ROLE_DELETE_WORKFLOW")  > -1){
					 	    	  	result = result +"<li><i class='fa fa-trash cursorPointer' title='Delete' onclick=  'requirementWorkflow.deletepoup(\""+workflow.uuid+"\",\""+workflow.name+"\")'></i></li>"
					 	    	  	}
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
				 	        	requirementWorkflow.getWorkflowQuestionnnaireList();
				 	        
				 	         }
				 	     });
				 	        
				 }
				 this.deletepoup = function(uuid,name){
				 var result = "";
				 $("#popUp").empty();
				 result = result +" <div class='project_inn_mask'></div>"; 
				 result = result +" <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
				 result = result +" <p>Do you want to delete "+name+"?</p>";
				 result = result + ' <div id="voidInvoiceId"/>';
				 result = result +' <b href="javascript:void(0)"><input id="confirm-id" type="button" value="Ok" onclick="requirementWorkflow.deleteQuestionnaire(\''+uuid+'\',\''+name+'\');"><input class="u_can_color" type="button" value="Cancel" onClick="requirementWorkflow.cancelTermination();"></b>';
				 result = result +" </div>";
				 $("#popUp").append(result);
				 $("#remove_user_pop").slideDown(200);
				 $(".project_inn_mask").fadeIn(200);
				 }
				 
				 this.deleteAssignTaskpoup = function(uuid,name){
					 var result = "";
					 $("#popUp").empty();
					 result = result +" <div class='project_inn_mask'></div>"; 
					 result = result +" <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
					 result = result +" <p>Do you want to delete "+name+"?</p>";
					 result = result + ' <div id="voidInvoiceId"/>';
					 result = result +' <b href="javascript:void(0)"><input id="confirm-id" type="button" value="Ok" onclick="requirementWorkflow.deleteAssignTask(\''+uuid+'\');"><input class="u_can_color" type="button" value="Cancel" onClick="requirementWorkflow.cancelTermination();"></b>';
					 result = result +" </div>";
					 $("#popUp").append(result);
					 $("#remove_user_pop").slideDown(200);
					 $(".project_inn_mask").fadeIn(200);
					 }
				 this.deleteQuestionnaire = function(uuid,name){
				 	  $.ajax({
				 	    	type: "POST",
				 	        url: sourceleadUrl+"/deleteQuestionnaireTemplateById?templateUuid="+uuid, 
				 	       success: function(result) {
				 	           if (result.response === "AWKWARD") {
				 	        	    location.href = "/errorPage";
				 	        	} else if (result.response === "SUCCESS")
				 	        	{
				 	        		 requirementWorkflow.showSuccessMsg("Successfully Deleted " +name); 
				 	        		requirementWorkflow.getWorkflowQuestionnnaireList();
				 	        		$(".mask, #modalTasktype").fadeOut();
				 	        	} else {
				 	        	    showError("Operation failed, please try again");
				 	        	}
				 	           $("#popUp").empty();
				 	           $("#remove_user_pop").fadeOut(200);
				 	           $(".project_inn_mask").fadeOut(200);
				 	        },
				 	    });
				 }
				 this.cancelTermination = function(){
				 	 $("#popUp").empty();
				      $(".project_inn_mask").fadeOut(200);
				 }


				 this.prepareDynamicStages = function(){
				  var previousStageResult = "";
				 var nextstageResult = "";

				 if(stageIndex > -1) {
				 	$("#previousStageUuid option[value='"+stageUuid[stageIndex-1]+"']").attr('selected', 'selected');

				 if((stageIndex+1) == workflowStages.length){
				 	lastStage = "lastStage";
				 	nextstageResult = "<h2>No Stage.</h2>";
				 	$("#nextStages").empty().append(nextstageResult);
				 	}else{
				 		lastStage = "";
				 		nextstageResult = nextstageResult + "<select class='sel form-control' id = 'workflowStageUuid'>";	
				 		nextstageResult = nextstageResult + "<option value = "+stageUuid[stageIndex+1]+">"+workflowStages[stageIndex+1]+"</option>";	
				 		nextstageResult = nextstageResult + "</select>";
				 		$("#nextStages").empty().append(nextstageResult);
				 }
				 $("#previousStageUuid option[value='"+stageUuid[stageIndex]+"']").remove();
				 }
				 }
				 
				 this.validateWorkflowName = function(workflowUuid){
					 var workFlowName = $("#workFlowName").val();
					 if(workFlowName != null)
					 $.ajax({
						 type: "GET",
						 url : sourceleadUrl+"/validateWorkflowName?workflowUuid="+workflowUuid+"&workFlowName="+workFlowName,
						 success:function(dataList){
							 if (dataList.response =="true") {
				                    if ($('#workflowNameDiv').find('p.jquery_form_error_message').length === 0)
				                        $("#workFlowName").after("<p class='jquery_form_error_message'>" + workFlowName + " Already Exists</span>");
				                    $("#saveReqWorkFlow").attr("disabled", "disabled");
				                } else {
				                    $('#workflowNameDiv').find('p.jquery_form_error_message').remove();
				                    $("#saveReqWorkFlow").removeAttr("disabled");
				                }
						 }
					 })
					 
				 }
				 
				 this.validateWorkflowStageName = function(workflowUuid){
					 var stageName = $("#stageName").val();
					
					 if(stageName != null && stageName.length > 0){
					 $.ajax({
						 type: "GET",
						 url : sourceleadUrl+"/validateWorkflowStageName?workflowUuid="+workflowUuid+"&stageName="+stageName+"&currentStageUuid="+currentStageUuid,
						 success:function(dataList){
							 if (dataList.response =="true" || dataList.response =="special" || dataList.response =="Minimum") {
								 $('#StageNameDiv').find('p.jquery_form_error_message').remove();
								 if ($('#StageNameDiv').find('p.jquery_form_error_message').length === 0)
				                 if(dataList.response =="true"){
									 $("#stageName").after("<p class='jquery_form_error_message'>" + stageName + " Already Exists</span>");
				                 }if(dataList.response =="special"){
								 $("#stageName").after("<p class='jquery_form_error_message'> Invalid format !</span>");
				                 }
				                 if(dataList.response =="Minimum"){
									 $("#stageName").after("<p class='jquery_form_error_message'> Minimum 4 Chars requried</span>");
					              }
				                    $("#saveStageButton").attr("disabled", "disabled");
				                    $("#saveTaskButton").attr("disabled", "disabled");
				                 }else {
				                    $('#StageNameDiv').find('p.jquery_form_error_message').remove();
				                    $("#saveStageButton").removeAttr("disabled");
				                    $("#saveTaskButton").removeAttr("disabled");
				                }
						 }
					 })
					 }
				 }
				 
				 this.selectChosenColumns = function(){
					 $("ul#chosenColumnList > li input:checkbox").prop("checked",true);
				 }
				 
				 this.resetChosenColumns = function(){
					 $("ul#chosenColumnList > li input:checkbox").prop("checked",false);
					$("#applicantname").prop("checked", true);
					$("#applicantmailid").prop("checked", true);
					$("#applicantphonenumber").prop("checked", true);
					$("#location").prop("checked", true);
					$("#Status").prop("checked", true);
					$("#SubmittedQuestionnaire").prop("checked", true);
				 }
				 
				 this.prepareChosenColumnList = function(chosenColumns){
					var chosenColumnArr = chosenColumns.split(',');
					for(var i = 0; i < chosenColumnArr.length; i++){
						$("#"+chosenColumnArr[i].replace(/[^a-zA-Z]/g, "").toLowerCase()).prop("checked", true);
					 }
				 }
				 
				 this.validateStageShortCode = function(workflowUuid){
					 var stageShortCode = $("#shortCode").val();
					 if(stageShortCode != null && stageShortCode.length > 0){
						 $.ajax({
							 type: "GET",
							 url : sourceleadUrl+"/validateStageShortCode?workflowUuid="+workflowUuid+"&stageShortCode="+stageShortCode+"&currentStageUuid="+currentStageUuid,
							 success:function(dataList){
								 if (dataList.response =="true" || dataList.response =="special" || dataList.response =="Minimum") {
									 $('#ShortCodeDiv').find('p.jquery_form_error_message').remove();
									 if ($('#ShortCodeDiv').find('p.jquery_form_error_message').length === 0)
					                 if(dataList.response =="true"){
										 $("#shortCode").after("<p class='jquery_form_error_message'>" + stageShortCode + " Already Exists</span>");
					                 }if(dataList.response =="special"){
									 $("#shortCode").after("<p class='jquery_form_error_message'> Invalid format !</span>");
					                 }if(dataList.response =="Minimum"){
										 $("#shortCode").after("<p class='jquery_form_error_message'> Minimum 4 Chars requried</span>");
						              }
					                    $("#saveStageButton").attr("disabled", "disabled");
					                 }else {
					                    $('#ShortCodeDiv').find('p.jquery_form_error_message').remove();
					                    $("#saveStageButton").removeAttr("disabled");
					                }
								
							 }
						 })
						 }
				 }
				 
				 this.checkWorkflowInRequirements = function(workflowUuid, workflowName){
					 var jobId = null; 
					 var jobTitle = null;
					 var jobsArray = new Array();
					 var allJobsArray = new Array();
					 var txtMessage = "Can't Delete "+workflowName+", Mapped To The Following Jobs !";
					 $.ajax({
						 type : "GET",
						 url : sourceleadUrl + "/checkWorkflowInRequirements?workflowUuid="+workflowUuid,
						 success : function(dataList){
							 if(dataList != null){
								 var obj = JSON.stringify(dataList);
								 var requirementList = obj.replace(/[{}]/g, "").split(',');
								 for(var j=0;j< requirementList.length; j++){
									 var count =j;
										var n = requirementList[j].indexOf(':');
										jobId = requirementList[j].slice(1, n-1);
										jobTitle = requirementList[j].slice(n+2, requirementList[j].length-1);
										if(j<5){
											jobsArray.push(++count+'. '+jobTitle);
										}
										else{
											allJobsArray.push(' '+(++count)+'. '+jobTitle);
										}
									}
								$(".project_inn_mask").remove();
								var result = "";
								result = result +"<div class='project_inn_mask'></div>";
								result = result +"<div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
								result = result +"<div class='clearfix'><p id='popupMsg'></p></div>";
								result = result+"<div class='modal-body'>"	
								for(var i=0; i< jobsArray.length; i++){
									if(jobsArray.length == 1){
										result = result + "&nbsp"+jobsArray[i]+".";
									}else{
										result = result + "&nbsp"+jobsArray[i]+" ";
									}
								}
								if(allJobsArray.length > 1){
								result = result + '<span class="text-success" title="'+allJobsArray+'">more..</span>';
								}
								result = result+"</div>"
								result = result +"<div class='clearfix'><input class='u_can_color width100percnt' type='button'  value='Ok' onClick='requirementWorkflow.cancelWorkflowPopup();'></div>";
								result = result +"</div>";
								$("#popUp").empty().append(result);
								$("#popupMsg").empty().append('<strong>'+txtMessage+'</strong>');
								$("#remove_user_pop").slideDown(200);
								$(".project_inn_mask").fadeIn(200);
						 }
							 else{
								 requirementWorkflow.deleteWorkflowEntry(workflowUuid, workflowName);
							 }
						 }
					 })
				 }
				 
				 this.cancelWorkflowPopup = function(){
						$("#remove_user_pop").fadeOut(200);
						$(".project_inn_mask").fadeOut(200);
					}
				 
				 this.alertStageCount = function(){
					 $(".project_inn_mask").remove();
						var result = "";
						result = result +" <div class='project_inn_mask'></div>";
						result = result +" <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
						result = result +" <div class='modal-body'><p id='popupMsg'></p></div>";
						result = result +" <div class='clearfix'><input class='u_can_color width100percnt' type='button'  value='Ok' onClick='requirementWorkflow.cancelWorkflowPopup();'></div>";
						result = result +" </div>";
						$("#popUp").empty().append(result);
						$("#popupMsg").empty().append("<strong>Can't Add More Than 15 Stages Per Workflow !</strong>");
						$("#remove_user_pop").slideDown(200);
						$(".project_inn_mask").fadeIn(200);
				 }
				 
				 this.deleteAssignTask = function(uuid){
				 $.ajax({
						type : "POST",
						url : sourceleadUrl + "/deleteAssignTask?uuid="+uuid,
						success : function(response){
							$("#createNewtask").hide();
							if(response =='Success'){
								var currentStageUuid = $("#currentStageUuid").val();
								requirementWorkflow.getRequirementWorkflowTasks(currentStageUuid);
								requirementWorkflow.cancelTermination();
							}if(response == 'Failed'){
								showError("Operation failed, please try again");
							}
							
						}
					});
   }
}
				 