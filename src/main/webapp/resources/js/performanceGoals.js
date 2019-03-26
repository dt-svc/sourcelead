var performanceGoals = new PerformanceGoals();

function PerformanceGoals(){
	
		 this.goalStartDateAndEndDatePickers = function(dateFormat){
			 var finalDateFormat = getJSDateFormat(dateFormat);
			 $(function() {
				  $('#goalStartDate').datepicker({
				      dateFormat: finalDateFormat,
				      changeMonth: true,
				  	  changeYear: true, 
				  	  minDate:0,
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
					  	  minDate:0,
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
		  
		  
		  
		  this.goalsAssignedToUsersList = function(userIds){
			  $.ajax({
			    	type: "GET",
			        url: pmsUrl+"/getResourceManagersUsersForGoalsAssignment", 
			        success: function(json) {
			        	var output = "";
			        	for (var i=0; i<json.length; i++)
						{
				        		var opt = json[i];
				        		if(userIds != null && userIds != '' && userIds != 'undefined'){
				        			var assignedUserIds = userIds.split(",");
				        			if (assignedUserIds.indexOf(opt.userId.toString()) > -1) {
				        				output = output + "<option value='" +opt.userId+ "' selected>"+opt.userName+"</option>";
				        			}else{
				        				output = output + "<option value='" +opt.userId+ "'>"+opt.userName+"</option>";
				        			}
				        		}else{
				        			output = output + "<option value='" +opt.userId+ "'>"+opt.userName+"</option>";
				        		}
				        		
				        		
						}
			        	$("#goalsAssignTo").empty();
				        $("#goalsAssignTo").append(output);
				        $('#goalsAssignTo').trigger("chosen:updated");
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
				        $("#goalsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
				         $.ajax({
					    	type: "POST",
					    	data: formData,
			  		        processData: false,
			  		        contentType:false, 
					    	url: pmsUrl+"/savePerformanceGoals?",
					        success: function(response) {
					        	$("#goalsLoading").removeAttr('style');
					        	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
			     	    		    showError(response.responseMessage);
			     	    		}
					        	else if (response.response === "AWKWARD") {
					        	    location.href = "/errorPage";
					        	} else if (response.response === "SUCCESS") {
					        		
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
		
}