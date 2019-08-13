var performanceGoals = new PerformanceGoals();

function PerformanceGoals(){
	
		 this.goalStartDateAndEndDatePickers = function(dateFormat){
			 var finalDateFormat = getJSDateFormat(dateFormat);
			 $(function() {
				  $('#goalStartDate').datepicker({
				      dateFormat: finalDateFormat,
				      changeMonth: true,
				  	  changeYear: true, 
				  	  onSelect : function(selected) {
				  		var dt = $(this).datepicker('getDate', '+1d');
						dt.setDate(dt.getDate() + 1);
						$("#goalEndDate").datepicker("option", "minDate", dt);
						performanceGoals.validGoalDates();
						$("#goalEndDate").removeAttr("disabled");
				 	  }
				  });
			  });
			 
			 
			   $(function() {
					  $('#goalEndDate').datepicker({
					      dateFormat: finalDateFormat,
					      changeMonth: true,
					  	  changeYear: true, 
					  	  onSelect : function(selected) {
					  		var dt = $(this).datepicker('getDate', '-1d');
							dt.setDate(dt.getDate() - 1);
							$("#goalStartDate").datepicker("option", "maxDate", dt);
							performanceGoals.validGoalDates();
					 	  }
					  });
				  });
			   
		 }
		 
		 this.validGoalDates = function(){
			 var goalStartDate = document.getElementById("goalStartDate").value;
			 if(goalStartDate){
				 $('#goalStartDateDiv').find('p.jquery_form_error_message').remove(); 
			 }
			
		 var goalEndDate = document.getElementById("goalEndDate").value;
			 if(goalEndDate){
				 $('#goalEndDateDiv').find('p.jquery_form_error_message').remove(); 
			 }
		 }
		 
		 
		  this.clearGoalStartDate = function(){
			  $("#goalStartDate").val("");
			  $("#goalEndDate").val("");
			  $("#goalEndDate").attr("disabled", "disabled");
			  
			  $("input[id$='goalStartDate'], input[id$='goalEndDate']").datepicker( "option", "maxDate", null );
			  $("input[id$='goalStartDate'], input[id$='goalEndDate']").datepicker( "option", "minDate", null );
		  }
		  
		  this.clearGoalEndDate = function(){
			  $("#goalEndDate").val("");
			  $("input[id$='goalStartDate'], input[id$='goalEndDate']").datepicker( "option", "maxDate", null );
		  }
		  
		  this.goalsDescriptionEditor = function(){
			    $("#txtEditor").Editor();
				$("#txtEditor").find(".Editor-editor").data-validation('validate_length length2-4000');
				$("#txtEditor").find(".Editor-editor").data-validation-optional('rule-required',true);
		  }
		  
		  
		  
		  this.getGoalsDetails = function(goalUUID){
			  $("#goalsLoading").attr('style','position:absolute; width:100%; height:150%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			  $.ajax({
			    	type: "GET",
			        url: pmsUrl+"/getPerformanceGoalsDetails?goalUUID="+goalUUID, 
			        success: function(response) {
			        	if (response.response === "AWKWARD") {
			        		location.href = "/errorPage";
			        	} else if (response.response === "SUCCESS") {
			        		
			        		$("#goalUUID").val(goalUUID);
			        		$("#goalId").val(response.object.goalId);
			        		$("#goalName").html(response.object.goalName);
			        		
			        		if(response.object.goalStartDate != null 
			        				 && response.object.goalStartDate != ''
			        					 && response.object.goalStartDate != 'undefined'){
			        			 $("#goalStartDate").html(response.object.goalStartDate);
			        		 }
			        		
			        		 if(response.object.goalEndDate != null 
			        				 && response.object.goalEndDate != ''
			        					 && response.object.goalEndDate != 'undefined'){
			        			 $("#goalEndDate").html(response.object.goalEndDate);
			        		 }
			        		 
			        		 if(response.object.teamName != null 
			        				 && response.object.teamName != ''
			        					 && response.object.teamName != 'undefined'){
			        			 $("#performanceTeamName").html(response.object.teamName);
			        		 }
			        		 
			        		 if(response.object.description != null 
			        				 && response.object.description != ''
			        					 && response.object.description != 'undefined'){
					        	 $("#goalsDescription").html(response.object.description);
			        		 }
			        		 
			        		 document.getElementById("percentageDoneDiv").style.display="";
			        		 $("#goalPercentage").attr("data-validation", "validate_Space validate_float");
			        		 $("#goalPercentage").val(response.object.percentageDone.toFixed(2));
			        		 
			        		 if(response.object.attachmentsList != null && response.object.attachmentsList.length > 0){
				        		   var result = '';
				        		   for(var i=0; i<response.object.attachmentsList.length; i++){
				        			   var opt = response.object.attachmentsList[i];
				        			  
				        			     result = result +"<strong id='goalDocument"+opt.id+"'>";
				      	    		     result = result +"<i class=''><a class='glyphicon glyphicon-save' target='_blank' href=\""+opt.fileUrl+"\"></a></i>";
				      					 result = result +"<input type='button' value='"+opt.fileName+"'>";
				      					 result = result +"</a>";
				      					 result = result +"</strong>";
				        		   }
				        		   
				        		   $("#savedGoalsDocuments").empty();
				      			   $("#savedGoalsDocuments").append(result);
			        		 }
			        		 
			        		
			        		$("#goalsLoading").removeAttr('style');
			        	}
			        },
			    });
		  }
		  
		  
		  this.getPerformanceGoalsDetails = function(goalUUID){
			  $("#goalsLoading").attr('style','position:absolute; width:100%; height:150%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			  $.ajax({
			    	type: "GET",
			        url: pmsUrl+"/getPerformanceGoalsDetails?goalUUID="+goalUUID, 
			        success: function(response) {
			        	if (response.response === "AWKWARD") {
			        		location.href = "/errorPage";
			        	} else if (response.response === "SUCCESS") {
			        		
			        		 if(response.object.teamId != null 
			        				 && response.object.teamId != ''
			        					 && response.object.teamId != 'undefined'){
			        			 performanceGoals.getPerformanceGoalTeamsMembers(goalUUID,response.object.goalsAssignTo); 
			        		 }else{
			        			 performanceGoals.goalsAssignedToUsersList(response.object.goalsAssignTo);
			        		 }
			        		
			        		$("#goalId").val(response.object.goalId);
			        		$("#goalName").val(response.object.goalName);
			        		
			        		if(response.object.goalStartDate != null 
			        				 && response.object.goalStartDate != ''
			        					 && response.object.goalStartDate != 'undefined'){
			        			 $("#goalEndDate").removeAttr("disabled");
			        			 $("#goalStartDate").val(response.object.goalStartDate);
			        		 }
			        		
			        		 if(response.object.goalEndDate != null 
			        				 && response.object.goalEndDate != ''
			        					 && response.object.goalEndDate != 'undefined'){
			        			 $("#goalEndDate").val(response.object.goalEndDate);
			        		 }
			        		 
			        		 if(response.object.teamName != null 
			        				 && response.object.teamName != ''
			        					 && response.object.teamName != 'undefined'){
			        			 $("#performanceTeamName").html(response.object.teamName);
			        		 }
			        		 
			        		 if(response.object.description != null 
			        				 && response.object.description != ''
			        					 && response.object.description != 'undefined'){
			        			 $("textarea[name=description]").val(response.object.description);
					        	 $(".Editor-editor").html(response.object.description);
			        		 }
			        		 
			        		 document.getElementById("percentageDoneDiv").style.display="";
			        		 $("#goalPercentage").attr("data-validation", "validate_Space validate_float");
			        		 $("#goalPercentage").val(response.object.percentageDone.toFixed(2));
			        		 
			        		 if(response.object.attachmentsList != null && response.object.attachmentsList.length > 0){
				        		   var result = '';
				        		   for(var i=0; i<response.object.attachmentsList.length; i++){
				        			   var opt = response.object.attachmentsList[i];
				        			  
				        			     result = result +"<strong id='goalDocument"+opt.id+"'>";
				      	    		     result = result +"<i class=''><a class='glyphicon glyphicon-save' target='_blank' href=\""+opt.fileUrl+"\"></a></i>";
				      					 result = result +"<input type='button' value='"+opt.fileName+"'>";
				      					 result = result +"</a>";
				      					 result = result +"<input class='dele' type='button' onClick='performanceGoals.alertMessage(\""+opt.uuid+"\",\""+goalUUID+"\");' >";
				      					 result = result +"</strong>";
				        		   }
				        		   
				        		   $("#savedGoalsDocuments").empty();
				      			   $("#savedGoalsDocuments").append(result);
			        		 }
			        		 
			        		
			        		$("#goalsLoading").removeAttr('style');
			        	}
			        },
			    });
		  }
		  
		  this.alertMessage = function(attachmentUUID,goalUUID){
			  var result = "";
				result = result +"	<div class='project_inn_mask'></div>";
				result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
				result = result +"		<p>Are you sure to delete</p>";
				result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
				result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='performanceGoals.removeGoalAttachment(\""+attachmentUUID+"\",\""+goalUUID+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='performanceGoals.cancel();'></b>";
				result = result +"	</div>";
				$("#choicepopUp").append(result);
				$("#remove_user_pop").slideDown(200);
				$(".project_inn_mask").fadeIn(200);
		  }
		  
		  
		  this.cancel = function(){
		  	$("#choicepopUp").empty();
		  	$(".project_inn_mask").fadeOut(200);
		  }
		  
		  
		  this.removeGoalAttachment = function(attachmentUUID,goalUUID){
		        $("#goalsLoading").attr('style','position:absolute; width:100%; height:150%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		         $.ajax({
			    	type: "POST",
			    	url: pmsUrl+"/deletePerformanceGoalsAttachment?attachmentUUID="+attachmentUUID,
			        success: function(response) {
			        	if (response.response === "AWKWARD") {
			        		$("#goalsLoading").removeAttr('style');
			        	    location.href = "/errorPage";
			        	} else if (response.response === "SUCCESS") {
			        		performanceGoals.cancel();
			        		location.replace(pmsUrl+"/addNewGoal?goalUUID="+goalUUID);
			        		$("#goalsLoading").removeAttr('style');
			        	} else if (response.indexOf('Sign In') !== -1) {
			        		location.replace(sourceleadUrl);
			        	} else {
			        	    showError("Operation failed, please try again");
			        	}
			        },
			    }); 
		  }
		  
		  this.goalsAssignedToUsersList = function(userIds){
			  $.ajax({
			    	type: "GET",
			        url: pmsUrl+"/getReportingManagersUsersForGoalsAssignment", 
			        success: function(response) {
				    	if (response.response === "AWKWARD") {
			        		location.href = "/errorPage";
			        	} else if (response.response === "SUCCESS") {
			        		var output = "";
			    	   		  
					        if(response.data.length > 0){
					        	 for(var i=0; i<response.data.length; i++){
					        		 var opt = response.data[i];
					        		 if(userIds != null && userIds != '' && userIds != 'undefined'){
						        			var assignedUserIds = userIds.split(",");
						        			if (assignedUserIds.indexOf(opt.assignedUserId.toString()) > -1) {
						        				output = output + "<option value='" +opt.assignedUserId+ "' selected>"+opt.assignedUser+"</option>";
						        			}else{
						        				output = output + "<option value='" +opt.assignedUserId+ "'>"+opt.assignedUser+"</option>";
						        			}
						        		}else{
						        			output = output + "<option value='" +opt.assignedUserId+ "'>"+opt.assignedUser+"</option>";
						        		}
					        	 }
					         }
					        $("#goalsAssignTo").empty();
					        $("#goalsAssignTo").append(output);
					        $('#goalsAssignTo').trigger("chosen:updated");
			        	}
			        },
			    });
		  }
		  
		  var fileCounter = 0;
		 
		  this.addGoalsDocumets = function(){
		  	$('<div>').attr('style','float:left; width:325px; border:1px solid #ddd; margin:0 15px 15px 0; padding:5px;overflow: hidden; background-color:#367fa9; color:#fff; ').append('<input id="file'+fileCounter+'"  type="file" class="pull-left" onchange="performanceGoals.uploadButton('+fileCounter+');"/>' +
		              '<a id="Button' + fileCounter + '' +
		              'value="" onclick = "performanceGoals.removeFileUpload(this)"><i class="fa fa-times btn btn-danger btn-sm btn-sm2 pull-right"></i></a>').appendTo($('#FileUploadContainer'));

		      		fileCounter++;
		  }
		  
		  this.removeFileUpload = function(div)
		  {
		  	 document.getElementById("FileUploadContainer").removeChild(div.parentNode);
		  }

		  this.uploadButton = function(id){
		  fileUpload("file"+id+"", "goals", "addGoal_form","uploadedFileInfo");
		  }
		  
		  
		  this.savePerformanceGoals  = function (){
				 var validationSettings = {
					        errorMessagePosition : 'element',
					       borderColorOnError : '',
							scrollToTopOnError : true,
							dateFormat : 'yyyy/mm/dd' 
				         }; 
				 
				 var editor = $("#txtEditor").val($('.Editor-editor').html()); 
				 
				 if ($('#addGoal_form').validate(false, validationSettings)){ 
				        var formData = getFormData1("addGoal_form");
				        $("#goalsLoading").attr('style','position:absolute; width:100%; height:150%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
				         $.ajax({
					    	type: "POST",
					    	data: formData,
			  		        processData: false,
			  		        contentType:false, 
					    	url: pmsUrl+"/savePerformanceGoals?",
					        success: function(response) {
					        	
					        	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
					        		$("#goalsLoading").removeAttr('style');
			     	    		    showError(response.responseMessage);
			     	    		}
					        	else if (response.response === "AWKWARD") {
					        	    location.href = "/errorPage";
					        	} else if (response.response === "SUCCESS") {
					        		var performanceGoalType =  $("#performanceGoalType").val();
					        		if(performanceGoalType == "Self_Goal"){
					        			location.replace(pmsUrl+"/performanceGoalsList");
					        		}else if(performanceGoalType == "Team_Goal"){
					        			location.replace(pmsUrl+"/performanceTeamGoalsList");
					        		}
					        		
					        	} else if (response.indexOf('Sign In') !== -1) {
					        		location.replace(sourceleadUrl);
					        	} else {
					        	    showError("Operation failed, please try again");
					        	}
					        },
					    });  
				        return true;
				  } else {
					return false;
				  }
			}

			$('body').on('blur', '#addGoal_form', function() {
					$("#addGoal_form").showHelpOnFocus();
					$("#addGoal_form").validateOnBlur(false, validationSettings);  
				});
			
			
			 this.addSubGoalsDocumets = function(){
				  	$('<div>').attr('style','float:left; width:325px; border:1px solid #ddd; margin:0 15px 15px 0; padding:5px;overflow: hidden; background-color:#367fa9; color:#fff; ').append('<input id="file'+fileCounter+'"  type="file" class="pull-left" onchange="performanceGoals.subGoalsUploadButton('+fileCounter+');"/>' +
				              '<a id="Button' + fileCounter + '' +
				              'value="" onclick = "performanceGoals.removeFileUpload(this)"><i class="fa fa-times btn btn-danger btn-sm btn-sm2 pull-right"></i></a>').appendTo($('#FileUploadContainer'));

				      		fileCounter++;
				  }
			
			
			 this.subGoalsUploadButton = function(id){
				  fileUpload("file"+id+"", "goals", "addSubGoal_form","uploadedFileInfo");
				  }
				  
				  
				  this.savePerformanceSubGoals  = function (){
						 var validationSettings = {
							        errorMessagePosition : 'element',
							       borderColorOnError : '',
									scrollToTopOnError : true,
									dateFormat : 'yyyy/mm/dd' 
						         }; 
						 
						 var editor = $("#txtEditor").val($('.Editor-editor').html()); 
						 
						 if ($('#addSubGoal_form').validate(false, validationSettings)){ 
						        var formData = getFormData1("addSubGoal_form");
						        $("#goalsLoading").attr('style','position:absolute; width:100%; height:150%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
						         $.ajax({
							    	type: "POST",
							    	data: formData,
					  		        processData: false,
					  		        contentType:false, 
							    	url: pmsUrl+"/savePerformanceSubGoals?",
							        success: function(response) {
							        	
							        	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
							        		$("#goalsLoading").removeAttr('style');
					     	    		    showError(response.responseMessage);
					     	    		}
							        	else if (response.response === "AWKWARD") {
							        	    location.href = "/errorPage";
							        	} else if (response.response === "SUCCESS") {
							        		location.replace(pmsUrl+"/performanceGoalsList");
							        	} else if (response.indexOf('Sign In') !== -1) {
							        		location.replace(sourceleadUrl);
							        	} else {
							        	    showError("Operation failed, please try again");
							        	}
							        },
							    });  
						        return true;
						  } else {
							return false;
						  }
					}

					$('body').on('blur', '#addSubGoal_form', function() {
							$("#addSubGoal_form").showHelpOnFocus();
							$("#addSubGoal_form").validateOnBlur(false, validationSettings);  
						});
					
					
					
					 this.getPerformanceGoalsSummaryDetails = function(goalUUID){
						  $("#goalsDetailsLoading").attr('style','position:absolute; width:100%; height:150%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
						  $.ajax({
						    	type: "GET",
						        url: pmsUrl+"/getPerformanceGoalsDetails?goalUUID="+goalUUID, 
						        success: function(response) {
						        	if (response.response === "AWKWARD") {
						        		location.href = "/errorPage";
						        	} else if (response.response === "SUCCESS") {
						        		
						        		$("#goalName").html(response.object.goalName);
						        		
						        		if(response.object.goalStartDate != null 
						        				 && response.object.goalStartDate != ''
						        					 && response.object.goalStartDate != 'undefined'){
						        			 $("#goalStartDate").html(response.object.goalStartDate);
						        		 }
						        		
						        		 if(response.object.goalEndDate != null 
						        				 && response.object.goalEndDate != ''
						        					 && response.object.goalEndDate != 'undefined'){
						        			 $("#goalEndDate").html(response.object.goalEndDate);
						        		 }
						        		 
						        		 if(response.object.createdDate != null 
						        				 && response.object.createdDate != ''
						        					 && response.object.createdDate != 'undefined'){
						        			 $("#createdDate").html(response.object.createdDate);
						        		 }
						        		 
						        		var percentage = 0;
						        		 if(response.object.percentageDone != null 
						        				 && response.object.percentageDone != ''
						        					 && response.object.percentageDone != 'undefined'){
						        			 $("#goalPercentage").html(response.object.percentageDone.toFixed(2)+"% Completed (info)");
						        			 percentage = response.object.percentageDone.toFixed(2);
						        		 }else{
						        			 $("#goalPercentage").html("0% Completed (info)");
						        		 }
						        		
						        		 var result = "";
						        		 result = result+'<div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="'+percentage+'" aria-valuemin="0" aria-valuemax="100" style="width:'+percentage+'%"></div>';
						        		
						        		 $("#progressBar").empty();
								         $("#progressBar").html(result);
								         
								        
								         
								         var markAsComplete = "";
								         var goalType = "parentGoal";
								         markAsComplete = markAsComplete+"<a class='text-primary textBold' href='javascript:void(0)' onclick='performanceGoals.markGoalAsComplete(\""+goalUUID+"\",\""+goalType+"\")'>Mark as completed</a>&nbsp;&nbsp;&nbsp;";
								         
								         $("#markAsComplete").empty();
								         $("#markAsComplete").html(markAsComplete);
								         
								         
								         var goalEdit = "";
								         if(response.object.teamId != null 
						        				 && response.object.teamId != ''
						        					 && response.object.teamId != 'undefined'){
								        	 goalEdit = goalEdit+'<a class="text-primary textBold" href='+pmsUrl+'/addNewTeamGoal?goalUUID='+goalUUID+'><i class="fa fa-pencil paddingRight"></i></a>&nbsp;';
								         }else{
								        	 goalEdit = goalEdit+'<a class="text-primary textBold" href='+pmsUrl+'/addNewGoal?goalUUID='+goalUUID+'><i class="fa fa-pencil paddingRight"></i></a>&nbsp;';
								         }
								         
						        		
								         $("#goaledit").empty();
								         $("#goaledit").html(goalEdit);
								         
								         var performanceGoalType = "";
								         
								         if(response.object.teamId != null 
						        				 && response.object.teamId != ''
						        					 && response.object.teamId != 'undefined'){
								        	 performanceGoalType = "teamGoal";
						        		 }else{
						        			 performanceGoalType = "selfGoal";
						        		 }
								         
								         if(opt.createdUserId == loginUserId){
								        	 var goalDelete = "";
									         goalDelete = goalDelete+"<a class='text-primary textBold' href='javascript:void(0)' onclick='performanceGoals.deletePerformanceParentGoal(\""+goalUUID+"\",\""+performanceGoalType+"\")'><i class='fa fa-trash  cursorPointer paddingRight'></i></a>";
								         }
								        
								         
								         $("#goalDelete").empty();
								         $("#goalDelete").html(goalDelete);
								         
								         var dateFormatStr = '';
								         if(dateFormat.match("yyyy-MM-dd")){
								   		     dateFormatStr = 'YYYY-MM-DD';
								   		  }if(dateFormat.match("dd-MM-yyyy")){
								   			  dateFormatStr = 'DD-MM-YYYY'; 
								   		  }if(dateFormat.match("MM-dd-yyyy")){
								   			  dateFormatStr = 'MM-DD-YYYY'; 
								   		  }if(dateFormat.match("yyyy-dd-MM")){
								   			  dateFormatStr = 'YYYY-DD-MM'; 
								   		  }

								   		  $("#startDateFormat").html(dateFormatStr)
								   		  $("#endDateFormat").html(dateFormatStr)
								   		  $("#createdDateFormat").html(dateFormatStr)
								   		  
						        		 
						        		$("#goalsDetailsLoading").removeAttr('style');
						        	}
						        },
						    });
					  }
					 
					 
					 
					 this.getPerformanceSubGoalsList = function(goalUUID){
						  $("#goalsDetailsLoading").attr('style','position:absolute; width:100%; height:150%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
						  $.ajax({
						    	type: "GET",
						        url: pmsUrl+"/getPerformanceSubGoalsList?goalUUID="+goalUUID, 
						        success: function(response) {
						        	if (response.response === "AWKWARD") {
						        		location.href = "/errorPage";
						        	} else if (response.response === "SUCCESS") {
						        		 var result = '';
						        		 var goalType = "subGoal";
						        		 
						        		  if(dateFormat.match("yyyy-MM-dd")){
						        			  dateFormatStr = 'YYYY-MM-DD';
						    	   		  }if(dateFormat.match("dd-MM-yyyy")){
						    	   			  dateFormatStr = 'DD-MM-YYYY'; 
						    	   		  }if(dateFormat.match("MM-dd-yyyy")){
						    	   			  dateFormatStr = 'MM-DD-YYYY'; 
						    	   		  }if(dateFormat.match("yyyy-dd-MM")){
						    	   			  dateFormatStr = 'YYYY-DD-MM'; 
						    	   		  }
						    	   		  
								         if(response.data.length > 0){
								        	 for(var i=0; i<response.data.length; i++){
								        		 var opt = response.data[i];
								        		 var percentage = 0;
								        		 if(opt.percentageDone != null){
								        			 percentage = opt.percentageDone.toFixed(2);
								        		 }
								        		 result = result+'<ul class="list-unstyled mb0 goalsList-list">' ;
								        		 result = result+'<li>' ;
								        		 result = result+'<div class="clearfix">' ;
								        		 result = result+'<div class="col-md-5">' ;
								        		 result = result+'<a href="javascript:void(0) mt5">' ;
								        		 result = result+'<h5 class="media-heading">'+opt.goalName+'</h5>' ;
								        		 result = result+'<p class="text-muted mb0 fontSmall"><span class="paddingRight">Created Date (<small class="custom-small">'+dateFormatStr+'</small>) :&nbsp;&nbsp;<span id="createdDate" class="textBold">'+opt.createdDate+'</span></span></p>' ;
								        		 result = result+'</a>' ;
								        		 result = result+'</div>' ;
								        		 result = result+'<div class="col-md-4">' ;
								        		 result = result+'<div class="clearfix mt5">' ;
								        		 result = result+'<div class="progress mb0 progressGoals">' ;
								        		 result = result+'<div class="progress-bar progress-bar-warning" role="progressbar"' ;
								        		 result = result+'aria-valuenow="'+percentage+'" aria-valuemin="0" aria-valuemax="100"' ;
								        		 result = result+'style="width:'+percentage+'%">' ;
								        		 result = result+'</div>' ;
								        		 result = result+'</div>' ;
								        		 result = result+'<p class="fontSmall mt5 mb0">'+percentage+'% Completed (info)</p>' ;
								        		 result = result+'<div class="clearfix"></div>' ;
								        		 result = result+'</div>' ;
								        		
								        		 
								        		 result = result+'<div class="col-md-10 mt paddingLeft">' ;
								        		 result = result+'<div class="mb0"><span class="mr10 text-muted" >Start Date (<small class="custom-small">'+dateFormatStr+'</small>) :</span><span class="textBold">'+opt.goalStartDate+'</span></div>' ;
								        		 result = result+'</div>' ;
								        		 result = result+'<div class="col-md-10 mt paddingLeft">' ;
								        		 result = result+'<div class="mb0"><span class="mr10 text-muted ">End Date (<small class="custom-small">'+dateFormatStr+'</small>) : </span><span class="textBold">'+opt.goalEndDate+'</span></div>' ;
								        		 result = result+'</div>' ;
								        		 
								        		 result = result+'</div>' ;
								        		 
								        		 result = result+'<div class="clearfix col-md-3">' ;
								        		 result = result+"<a class='text-primary textBold cursorPointer' onclick='performanceGoals.markGoalAsComplete(\""+opt.uuid+"\",\""+goalType+"\",\""+goalUUID+"\")'>Mark as completed</a>&nbsp;&nbsp;&nbsp;" ;
								        		 result = result+'<a href='+pmsUrl+'/addNewGoal?goalUUID='+opt.uuid+'><i class="fa fa-pencil cursorPointer" title="Edit"></i></a>&nbsp;&nbsp;&nbsp;' ;
								        		 result = result+"<a onclick='performanceGoals.deletePerformanceSubGoal(\""+opt.uuid+"\",\""+goalUUID+"\")'><i class='fa fa-trash cursorPointer' title='Delete'></i></a>&nbsp;&nbsp;&nbsp;" ;
								        		 result = result+'</div>' ;
								        		 result = result+'</div>' ;
								        		 result = result+'<div class="clearfix"></div>' ;
								        		 result = result+'</li>' ;
								        		 result = result+'</ul>' ;
								        		  
								        	 }
								        	 
								         }else{
								        	 
								        	 result = result + '<div class="dataTables_empty"></div>' ;
							        	 }
								         
								         $("#subGoalsList").empty();
								         $("#subGoalsList").html(result);
						        		 
						        		$("#goalsDetailsLoading").removeAttr('style');
						        	}
						        },
						    });
					  }
					 
					 
					 
					 
					 this.getPerformanceGoalsAssignToList = function(goalUUID){
						  $("#goalsDetailsLoading").attr('style','position:absolute; width:100%; height:150%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
						  $.ajax({
						    	type: "GET",
						        url: pmsUrl+"/getPerformanceGoalsAssignToList?goalUUID="+goalUUID, 
						        success: function(response) {
						        	if (response.response === "AWKWARD") {
						        		location.href = "/errorPage";
						        	} else if (response.response === "SUCCESS") {
						        		 var result = '';
						        		 
						        		 if(dateFormat.match("yyyy-MM-dd")){
						        			  dateFormatStr = 'YYYY-MM-DD';
						    	   		  }if(dateFormat.match("dd-MM-yyyy")){
						    	   			  dateFormatStr = 'DD-MM-YYYY'; 
						    	   		  }if(dateFormat.match("MM-dd-yyyy")){
						    	   			  dateFormatStr = 'MM-DD-YYYY'; 
						    	   		  }if(dateFormat.match("yyyy-dd-MM")){
						    	   			  dateFormatStr = 'YYYY-DD-MM'; 
						    	   		  }
						    	   		  
								         if(response.data.length > 0){
								        	 for(var i=0; i<response.data.length; i++){
								        		 var opt = response.data[i];
								        		 
								        		 result = result+'<ul class="list-unstyled mb0 goalsList-list">' ;
								        		 result = result+'<li>' ;
								        		 result = result+'<div class="clearfix">' ;
								        		 result = result+'<div class="col-md-5">' ;
								        		 result = result+'<div class="media-left goalsImg mr">' ;
								        		 result = result+'<img data-id= "userProfileImage" onerror="performanceGoals.predefineGoalUserImage(this);" src="'+opt.userImgUrl+'" class="media-object">' ;
								        		 result = result+'</div>' ;
								        		 result = result+'<div class="media-body">' ;
								        		 result = result+'<a href="javascript:void(0)">' ;
								        		 result = result+'<h5>'+opt.assignedUser+'</h5>' ;
								        		 result = result+'</a>' ;
								        		 result = result+'</div>' ;
								        		 result = result+'</div>' ;
								        		 result = result+'<div class="col-md-3">' ;
								        		 result = result+'<label class="text-muted paddingRight">Assigned on (<small class="custom-small">'+dateFormatStr+'</small>) :</label>' ;
								        		 result = result+'<span class="textBold">'+opt.assignedDate+'</span>' ;
								        		 result = result+'</div>' ;
								        		 result = result+'<div class="col-md-3">' ;
								        		 result = result+'<label class="text-muted paddingRight">Goals:</label>' ;
								        		 result = result+'<span class="textBold">'+opt.goalName+'</span>' ;
								        		 result = result+'</div>' ;
								        		 result = result+'<div class="clearfix"></div>' ;
								        		 result = result+'</li>' ;
								        		 result = result+'</ul>' ;
								        		  
								        	 }
								        	 
								        	 $("#goalsAssignToList").empty();
									         $("#goalsAssignToList").html(result);
								         }
						        		 
						        		$("#goalsDetailsLoading").removeAttr('style');
						        	}
						        },
						    });
					  }
					 
					 
					 this.addGoalsCommentsPopUp = function(){
							$(".commentPopUp").fadeIn();
							performanceGoals.addComment();
						}
					 this.closeGoalsComment = function(){
							$(".commentPopUp").fadeOut();
						}
					 this.addComment = function(){
							result = "";
							result  = result + '	<div class="clearfix">';
							result  = result + '   <button type="button" class="close mr5 mt5" onclick="performanceGoals.closeGoalsComment()">&times;</button>';
							result  = result + '   <p class="ml5 mt10 mb0"><strong>Add Comment</strong></p>';
							result  = result + ' </div>';
							result = result + '  <form id="addGoalsComment_form" name="addGoalsComment_form">';
							result  = result + ' <div class="row clearfix">';
							result  = result + '	<div class="ticketCommentCont">';
							result  = result + '	<div class="clearfix">';
							result  = result + '		<div contenteditable="true" id="goalsComment" data-validation="required" field-name="Comments" class="tkt_comment_div tkt_comment_custom"></div>';
							result  = result + '	</div>';
							result  = result + '</div>';
							result  = result + '</div>';
							result  = result + ' <div class="modalFooter  mb10 clearfix">';
							result  = result + '   <div class="pull-right">';
							result  = result + '     <button type="button" class="btn btn-primary btn-sm" id="save_comment" data-dismiss="modal" onclick="performanceGoals.saveGoalsComment();">Add</button>';
							result  = result + '     <button type="button" class="btn btn-default btn-sm" data-dismiss="modal" onclick="performanceGoals.closeGoalsComment()">Cancel</button>';
							result  = result + '   </div>';
							result  = result + ' </div>';
							result = result + ' </form>';
							$("#commnetPopUpDivId").empty();  
							$("#commnetPopUpDivId").append(result); 
							
							performanceGoals.commentMembers();
						}
					
					 var commentUsers = [], selectedUsers = [], commentConfig = {}; 
					 
					 this.commentMembers = function(){
						   commentUsers = [];
						   selectedUsers = [];
						   commentConfig = {};
								
								 $.ajax({
								    	type: "GET",
								        url: pmsUrl+"/getAllUserListForGoals?", 
								        success: function(response) {
								        	if (response.response === "AWKWARD") {
								        		location.href = "/errorPage";
								        	} else if (response.response === "SUCCESS") {
								        		
										         if(response.data.length > 0){
										        	 for(var i=0; i<response.data.length; i++){
										        		 var opt = response.data[i];
										        		 commentUsers.push({"id": opt.userId, "fullName": opt.fullName, "email": opt.email, "fileUrl" : opt.fileURL});
										        	 }
										        	 performanceGoals.initializeFroalaEditor();
										         }
								        	}
								        },
								    });
								
						} 


						this.initializeFroalaEditor = function() {
							
						    commentConfig = {
						      at: "@",
						      data: commentUsers,
						      displayTpl: '<li class="clearfix" data-id="${id}"><div class="goalsImg pull-left mr"><img data-id= "userProfileImage" onerror="performanceGoals.predefineGoalUserImage(this);" src="${fileUrl}" class="media-object"></div><div class="pull-left" style="paddding:5px 0px 0px 15px;"> ${fullName} <small style="display:block;">${email}</small></div></li>',
						      limit: 200
						    }
						    
						    $('#goalsComment').atwho(commentConfig);
						    $('#goalsComment').on('inserted.atwho', function (event, $li, bEvent) {
						        selectedUsers.push($($li).data("id"));
						      }).on('reposition.atwho', function (event, offset) {
						    	  var removeUser = $("#goalsComment").find("span[class='atwho-query']").data("id");
						    	  if(removeUser) {
							    	  selectedUsers.splice($.inArray(removeUser, selectedUsers), 1);
						    	  }
						        })
						}
						
						
						
						
						 this.saveGoalsComment  = function (){
							 var validationSettings = {
								        errorMessagePosition : 'element',
								       borderColorOnError : '',
										scrollToTopOnError : true,
										dateFormat : 'yyyy/mm/dd' 
							         }; 
							 
							 
							 if ($('#addGoalsComment_form').validate(false, validationSettings)){ 
								 $("#goalsDetailsLoading").attr('style','position:absolute; width:100%; height:250%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
							        var formData = {};
									var mentionedUserIds = selectedUsers.toString();
									var goalComments = $("#goalsComment").html();
									formData["goalsComment"] = goalComments;
									formData["goalId"] = goalId;
									formData["mentionedUserIds"] =  mentionedUserIds;
							         $.ajax({
								    	type: "POST",
								    	data: formData,
								    	url: pmsUrl+"/savePerformanceGoalsComments?",
								        success: function(response) {
								        	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
						     	    		    showError(response.responseMessage);
						     	    		   $("#goalsDetailsLoading").removeAttr('style');
						     	    		}
								        	else if (response.response === "AWKWARD") {
								        	    location.href = "/errorPage";
								        	} else if (response.response === "SUCCESS") {
								        		performanceGoals.closeGoalsComment();
								        		performanceGoals.goalsCommentsTableList();
								        		$("#goalsDetailsLoading").removeAttr('style');
								        	} else if (response.indexOf('Sign In') !== -1) {
								        		location.replace(sourceleadUrl);
								        	} else {
								        	    showError("Operation failed, please try again");
								        	}
								        },
								    });  
							        return true;
							  } else {
								return false;
							  }
						}
						 
						 $('body').on('blur', '#addGoalsComment_form', function() {
								$("#addGoalsComment_form").showHelpOnFocus();
								$("#addGoalsComment_form").validateOnBlur(false, validationSettings);  
							});
						 
						 
						 
						 
						 this.goalsCommentsTableList = function(){
							 
							  $.ajax({
							    	type: "GET",
							        url: pmsUrl+"/getPerformanceGoalsCommentsList?goalId="+goalId, 
							        success: function(response) {
							        	if (response.response === "AWKWARD") {
							        		location.href = "/errorPage";
							        	} else if (response.response === "SUCCESS") {
							        		 var result = '';
							        		 
							        		 if(dateFormat.match("yyyy-MM-dd")){
							        			  dateFormatStr = 'YYYY-MM-DD';
							    	   		  }if(dateFormat.match("dd-MM-yyyy")){
							    	   			  dateFormatStr = 'DD-MM-YYYY'; 
							    	   		  }if(dateFormat.match("MM-dd-yyyy")){
							    	   			  dateFormatStr = 'MM-DD-YYYY'; 
							    	   		  }if(dateFormat.match("yyyy-dd-MM")){
							    	   			  dateFormatStr = 'YYYY-DD-MM'; 
							    	   		  }
							    	   		  
									         if(response.data.length > 0){
									        	 for(var i=0; i<response.data.length; i++){
									        		 var opt = response.data[i];
									        		 
									        		    result = result + '	<div class="media clearfix col-md-12">';
														result = result + '   <div class="media-left">';
														if(opt.imgUrl != null){
															result = result  +'	   <img onerror="predefineGoalUserImage(this);" src="'+opt.imgUrl+'" class="media-object img-circle" style="width:60px"/>';
														}else{
															result = result  +'	   <img onerror="predefineGoalUserImage(this);" src="" class="media-object" style="width:60px"/>';
														}
														result = result + '   </div>';
														result = result + '   <div class="media-body">';
														result = result + '     <h5 class="media-heading text-primary mt10">'+opt.createdUserName+'';
														result = result + '         <div class="pull-right small text-muted">'+opt.createdTime+'<br/><span class="mt5">'+opt.createdDate+' &nbsp;(<small class="custom-small">'+dateFormatStr+'</small>)</span></div>';
														result = result + '       </h5>';
														result = result + '       <p>'+opt.goalsComment+'</p>';
														result = result + '   </div>';
														result = result + ' </div>';
									        		  
									        	 }
									        	 
									         }else{
													result = result+"   <div class='dataTables_empty'>"; 
													result = result+"  </div> ";
											}
									         
									         $("#goalsCommentsListDivId").empty();
									         $("#goalsCommentsListDivId").html(result);
							        		 
							        	}
							        },
							    });
							 
						 }
					 
						 this.predefineGoalUserImage = function(imageId){
							 $(imageId).attr('src','/resources/images/userprofileImg.jpg');
						 }
						 
						 
						 this.validateGoalPercentage = function(){
								var goalPercentage = $('#goalPercentage').val();
								
								 if(goalPercentage > 100 || goalPercentage < 0){
									 if(goalPercentage < 0){
										    $('#goalPercentageId').find('p.jquery_form_error_message').remove(); 
											$('input#goalPercentageValidation').attr('class','error signin_fild');
											$("input#goalPercentageValidation").after("<p class='jquery_form_error_message'>Interest Rate should not in negitive</p>");
											$('input#goalPercentageValidation').attr('record-exist','yes');
											$('input#goalPercentageValidation').attr('record-exist-errorMsg','Min Rate should not in negitive'); 
									 }else{
										 $('#goalPercentageId').find('p.jquery_form_error_message').remove(); 
											$('input#goalPercentageValidation').attr('class','error signin_fild');
											$("input#goalPercentageValidation").after("<p class='jquery_form_error_message'>Interest Rate should not more than 100</p>");
											$('input#goalPercentageValidation').attr('record-exist','yes');
											$('input#goalPercentageValidation').attr('record-exist-errorMsg','Min Rate should not more than 100'); 
									 }
								 }else{
									 
								     $('#goalPercentageId').find('p.jquery_form_error_message').remove(); 
							    	 $('input#goalPercentageValidation').removeAttr( "class record-exist record-exist-errormsg");
									 $('#goalPercentageId').find('p.jquery_form_error_message').remove();
								 }
							}
						 
						 
						 this.markGoalAsComplete = function(goalUUID,goalType,parentGoalUUID){
							 $("#goalsDetailsLoading").attr('style','position:absolute; width:100%; height:250%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
							  $.ajax({
							    	type: "POST",
							        url: pmsUrl+"/makeGoalAsCompleted?goalUUID="+goalUUID, 
							        success: function(response) {
							        	if (response.response === "AWKWARD") {
							        		location.href = "/errorPage";
							        	} else if (response.response === "SUCCESS") {
							        		$("#goalsDetailsLoading").removeAttr('style');
							        		if(goalType == "parentGoal"){
							        			performanceGoals.getPerformanceGoalsSummaryDetails(goalUUID);
							        		}else if(goalType == "subGoal"){
							        			performanceGoals.getPerformanceGoalsSummaryDetails(parentGoalUUID);
							        			performanceGoals.getPerformanceSubGoalsList(parentGoalUUID);
							        		}
							        		
							        	}
							        },
							    });
						 }
						 
						 
						 
						 this.getPerformanceGoalTeams = function(teamId){
							  $.ajax({
							    	type: "GET",
							        url: pmsUrl+"/getPerformanceGoalsTeamsList", 
							        success: function(response) {
							        
							    	if (response.response === "AWKWARD") {
						        		location.href = "/errorPage";
						        	} else if (response.response === "SUCCESS") {
						        		var output = "";
						    	   		  
								         if(response.data.length > 0){
								        	 output = output + "<option value=''>--Select Team--</option>";
								        	 for(var i=0; i<response.data.length; i++){
								        		 var opt = response.data[i];
									        		if(teamId != null && teamId != '' && teamId != 'undefined'){
									        			if (teamId.indexOf(opt.id.toString()) > -1) {
									        				output = output + "<option value='" +opt.id+ "' selected>"+opt.name+"</option>";
									        			}else{
									        				output = output + "<option value='" +opt.id+ "'>"+opt.name+"</option>";
									        			}
									        		}else{
									        			output = output + "<option value='" +opt.id+ "'>"+opt.name+"</option>";
									        		}
								        	 }
								         }
								         $("#performanceTeams").empty();
								         $("#performanceTeams").append(output);
								         $('.selectpicker').selectpicker('refresh');
						        	}
							        
							        },   
							    });
						  }
						 
						 
						 this.getPerformanceGoalTeamsMembers = function(goalUUID,userIds){
							  $.ajax({
							    	type: "GET",
							        url: pmsUrl+"/getPerformanceGoalsTeamsMembersList?goalUUID="+goalUUID, 
							        success: function(response) {
							        
							    	if (response.response === "AWKWARD") {
						        		location.href = "/errorPage";
						        	} else if (response.response === "SUCCESS") {
						        		var output = "";
						    	   		  
								        if(response.data.length > 0){
								        	 for(var i=0; i<response.data.length; i++){
								        		 var opt = response.data[i];
								        		 if(userIds != null && userIds != '' && userIds != 'undefined'){
									        			var assignedUserIds = userIds.split(",");
									        			if (assignedUserIds.indexOf(opt.teamMemberId.toString()) > -1) {
									        				output = output + "<option value='" +opt.teamMemberId+ "' selected>"+opt.teamMemberName+"</option>";
									        			}else{
									        				output = output + "<option value='" +opt.teamMemberId+ "'>"+opt.teamMemberName+"</option>";
									        			}
									        		}else{
									        			output = output + "<option value='" +opt.teamMemberId+ "'>"+opt.teamMemberName+"</option>";
									        		}
								        	 }
								         }
								        $("#goalsAssignTo").empty();
								        $("#goalsAssignTo").append(output);
								        $('#goalsAssignTo').trigger("chosen:updated");
						        	}
							        
							        },   
							    });
						  }
						 
						 
						 
						 
						 this.addTeamSubGoalsDocumets = function(){
							  	$('<div>').attr('style','float:left; width:325px; border:1px solid #ddd; margin:0 15px 15px 0; padding:5px;overflow: hidden; background-color:#367fa9; color:#fff; ').append('<input id="file'+fileCounter+'"  type="file" class="pull-left" onchange="performanceGoals.teamSubGoalsUploadButton('+fileCounter+');"/>' +
							              '<a id="Button' + fileCounter + '' +
							              'value="" onclick = "performanceGoals.removeFileUpload(this)"><i class="fa fa-times btn btn-danger btn-sm btn-sm2 pull-right"></i></a>').appendTo($('#FileUploadContainer'));

							      		fileCounter++;
							  }
						
						
						 this.teamSubGoalsUploadButton = function(id){
							  fileUpload("file"+id+"", "goals", "addTeamSubGoal_form","uploadedFileInfo");
							  }
							  
							  
							  this.savePerformanceTeamSubGoals  = function (){
									 var validationSettings = {
										        errorMessagePosition : 'element',
										       borderColorOnError : '',
												scrollToTopOnError : true,
												dateFormat : 'yyyy/mm/dd' 
									         }; 
									 
									 var editor = $("#txtEditor").val($('.Editor-editor').html()); 
									 
									 if ($('#addTeamSubGoal_form').validate(false, validationSettings)){ 
									        var formData = getFormData1("addTeamSubGoal_form");
									        $("#goalsLoading").attr('style','position:absolute; width:100%; height:150%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
									         $.ajax({
										    	type: "POST",
										    	data: formData,
								  		        processData: false,
								  		        contentType:false, 
										    	url: pmsUrl+"/savePerformanceTeamSubGoals?",
										        success: function(response) {
										        	
										        	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
										        		$("#goalsLoading").removeAttr('style');
								     	    		    showError(response.responseMessage);
								     	    		}
										        	else if (response.response === "AWKWARD") {
										        	    location.href = "/errorPage";
										        	} else if (response.response === "SUCCESS") {
										        		location.replace(pmsUrl+"/performanceTeamGoalsList");
										        	} else if (response.indexOf('Sign In') !== -1) {
										        		location.replace(sourceleadUrl);
										        	} else {
										        	    showError("Operation failed, please try again");
										        	}
										        },
										    });  
									        return true;
									  } else {
										return false;
									  }
								}

								$('body').on('blur', 'addTeamSubGoal_form', function() {
										$("#addTeamSubGoal_form").showHelpOnFocus();
										$("#addTeamSubGoal_form").validateOnBlur(false, validationSettings);  
									});
								
						 
								 this.deletePerformanceSubGoal = function(goalUUID,parentGoalUUID){
									 $("#goalsDetailsLoading").attr('style','position:absolute; width:100%; height:250%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
									  $.ajax({
									    	type: "POST",
									        url: pmsUrl+"/deletePerformanceGoalByGoalUUID?goalUUID="+goalUUID, 
									        success: function(response) {
									        	if (response.response === "AWKWARD") {
									        		location.href = "/errorPage";
									        	} else if (response.response === "SUCCESS") {
									        		$("#goalsDetailsLoading").removeAttr('style');
									        		performanceGoals.getPerformanceGoalsSummaryDetails(parentGoalUUID);
								        			performanceGoals.getPerformanceSubGoalsList(parentGoalUUID);
									        		
									        	}
									        },
									    });
								 }
								 
								 this.deletePerformanceParentGoal = function(goalUUID,performanceGoalType){
									 $("#goalsDetailsLoading").attr('style','position:absolute; width:100%; height:250%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
									  $.ajax({
									    	type: "POST",
									        url: pmsUrl+"/deletePerformanceGoalByGoalUUID?goalUUID="+goalUUID, 
									        success: function(response) {
									        	if (response.response === "AWKWARD") {
									        		location.href = "/errorPage";
									        	} else if (response.response === "SUCCESS") {
									        		$("#goalsDetailsLoading").removeAttr('style');
									        		if(performanceGoalType == "teamGoal"){
									        			location.replace(pmsUrl+"/performanceTeamGoalsList");
									        		}else if(performanceGoalType == "selfGoal"){
									        			location.replace(pmsUrl+"/performanceGoalsList");
									        		}
									        		
									        	}
									        },
									    });
								 }
								 
								 
								 
								 this.savePerformanceGoalPercentage  = function (){
									 var validationSettings = {
										        errorMessagePosition : 'element',
										       borderColorOnError : '',
												scrollToTopOnError : true,
												dateFormat : 'yyyy/mm/dd' 
									         }; 
									 
									 
									 if ($('#addGoalPercentage_form').validate(false, validationSettings)){ 
									        var formData = getFormData1("addGoalPercentage_form");
									        $("#goalsLoading").attr('style','position:absolute; width:100%; height:150%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
									         $.ajax({
										    	type: "POST",
										    	data: formData,
								  		        processData: false,
								  		        contentType:false, 
										    	url: pmsUrl+"/savePerformanceGoalPercentage?",
										        success: function(response) {
										        	
										        	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
										        		$("#goalsLoading").removeAttr('style');
								     	    		    showError(response.responseMessage);
								     	    		}
										        	else if (response.response === "AWKWARD") {
										        	    location.href = "/errorPage";
										        	} else if (response.response === "SUCCESS") {
										        		var performanceGoalType =  $("#performanceGoalType").val();
										        		if(performanceGoalType == "Self_Goal"){
										        			location.replace(pmsUrl+"/performanceGoalsList");
										        		}else if(performanceGoalType == "Team_Goal"){
										        			location.replace(pmsUrl+"/performanceTeamGoalsList");
										        		}
										        		
										        	} else if (response.indexOf('Sign In') !== -1) {
										        		location.replace(sourceleadUrl);
										        	} else {
										        	    showError("Operation failed, please try again");
										        	}
										        },
										    });  
									        return true;
									  } else {
										return false;
									  }
								}

								$('body').on('blur', '#addGoalPercentage_form', function() {
										$("#addGoalPercentage_form").showHelpOnFocus();
										$("#addGoalPercentage_form").validateOnBlur(false, validationSettings);  
									});
								 
								 
}