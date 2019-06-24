var projects = new Projects();

function Projects() {
	this.saveProject = function (){
		var projectId = document.getElementById("projectId").value;
		if(projectId == null || projectId == '' || projectId == 'undefined'){
			projects.validateProjectSequeneNumber();
		}
		var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
		
		var ee = $("#txtEditor").val($('.Editor-editor').html()); 

		if ($('#project_form').validate(false, validationSettings)) {
			 
		    var formData = getFormData1("project_form");
		    var estimatedBudget = document.getElementById("estimatedBudget").value;
		    var estimatedHours = document.getElementById("estimatedHours").value;
		    
		    	formData.append("estimatedBudget", estimatedBudget === "" ? "0" : estimatedBudget);
		    	formData.append("estimatedHours", estimatedHours === "" ? "0" : estimatedHours);
		    
     		$("#projectDetailsloading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:1000;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.ajax({
						type : "POST",
						data: formData,
		  		        processData: false,
		  		        contentType:false, 
						url : sourceleadUrl+ "/storeProject",
						success : function(result) {
								if (result && result.response === "INVALID_DATA") {
	  								$("#projectDetailsloading").removeAttr("style");
	  			                    showError(result.responseMessage);
	  			                } else if (result.response === "AWKWARD") {
	  			                    location.href = "/errorPage";
	  			                } else if (result.response === "SUCCESS") {
	  			                	var projects = result.object;
	  			                	 $("#projectId").val(projects.id);
									 $("#projectUUID").val(projects.projectUUID);
									 $("#startDateStr").val(projects.startDateStr);
									 $("#endDateStr").val(projects.endDateStr);
									 
									 $("#projectDetailsloading").removeAttr("style");
									 var assignedMembers = document.getElementById("assignedMembers");
									 document.getElementById("assignedMembers").removeAttribute("class");
									 assignedMembers.href="#tab-b"; 
									 $("#assignedMembers").click();
	  			                } else if (result.indexOf('Sign In') !== -1) {
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
	$('body').on('blur', '#project_form', function() {
		$("#project_form").showHelpOnFocus();
		$("#project_form").validateOnBlur(false, validationSettings);
	});
	
	
	
	this.getProjectDetails = function (){
		var projectId = document.getElementById("projectId").value;
		if(projectId){
			$("#FileUploadContainer").empty();
			
			$("#projectDetailsloading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:1000;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	        $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/getProjectDetailsByProjectId?projectId="+projectId, 
		        success: function(json) {
		        	
		        	var level1ApproverUser = '';
		        	var level2ApproverUser = '';
		        	
		        	var level1ExpenseApproverUser = '';
		        	var level2ExpenseApproverUser = '';
		        	
		        	if(json.projectSequeneNumber != null && json.projectSequeneNumber != 'undefined'){
		        		 $("input[name=projectSequeneNumber]").val(json.projectSequeneNumber);
		        		 $('#projectSequeneNumber').removeAttr('onblur');
		        	}
		        	
		        	if(json.isProjectShared != null && json.isProjectShared != 'undefined' && json.isProjectShared == 'Y'){
		        		$("#savedSharedClientOrgLocId").val(json.sharedClientOrgLocationId);
		        		 $("select[name=sharedClientOrgLocId]").val(json.sharedClientOrgLocationId);
		        		 $('#sharedClientOrgLocId').prop('disabled', true).trigger("chosen:updated");
		        	}
		        	 
		        	
		        	 $("input[name=projectName]").val(json.projectName);
		        	 $("input[name=startDate]").val(json.startDateStr);
		        	 $("input[name=endDate]").val(json.endDateStr);
		        	 if(json.projectShortCode != null && json.projectShortCode != '' && json.projectShortCode != 'undefined'){
		        		 $("#projectShortCode").val(json.projectShortCode);
		        		 $("input[name=projectShortCode]").attr("readonly","readonly");
		        		 $('#projectShortCode').removeAttr('onblur');
		        	 }
		        	 
		        	 if(json.isProjectForTimeSheets == 'Y' && json.isProjectForExpenses == 'Y'){
		        		 document.getElementById("timesheetsandexpenses").checked = true;
		        		 $("#projectForValue").val('TimesheetsAndExpences');
		        	 }else  if(json.isProjectForTimeSheets == 'Y' && json.isProjectForExpenses == 'N'){
		        		 document.getElementById("timesheets").checked = true;
		        		 $("#projectForValue").val('Timesheets');
		        	 }else  if(json.isProjectForTimeSheets == 'N' && json.isProjectForExpenses == 'Y'){
		        		 document.getElementById("expenses").checked = true;
		        		 $("#projectForValue").val('Expences');
		        	 }
		        	 
		        	 
	        	   $("#notifyBudgetAt").val(json.notifyBudgetAt);
		        	 
		           var projectStatus = '';
		           if(json.projectStatus == 'NOTSTARTED'){
		        	   projectStatus = "Not Started"
		           }else if(json.projectStatus == 'INPROGRESS'){
		        	   projectStatus = "In-Progress"
		           }else if(json.projectStatus == 'COMPLETED'){
		        	   projectStatus = "Completed"
		           }else if(json.projectStatus == 'ONHOLD'){
		        	   projectStatus = "On-Hold"
		           }else if(json.projectStatus == 'DROPPED'){
		        	   projectStatus = "Dropped"
		           }
		           
		           
		        /*  if(json.projectApprovalType != null && json.projectApprovalType != '' && json.projectApprovalType != 'undefined'){
		        	   $("#parallelTimesheetsApproval").prop("checked", false); 
		        	   $("#sequentialTimesheetsApproval").prop("checked", false);  
		        	   
		        	   if(json.projectApprovalType == 'Parallel'){
		        		         $("#parallelTimesheetsApproval").prop("checked", true); 
				        		 $("#projectApprovalTypeValue").val(json.projectApprovalType);
	        			  }else{
	        				     $("#sequentialTimesheetsApproval").prop("checked", true);  
				        		 $("#projectApprovalTypeValue").val(json.projectApprovalType);
	        			  }
	        		  }
		           
		           
		           if(json.expenseApprovalType != null && json.expenseApprovalType != '' && json.expenseApprovalType != 'undefined'){
		        	   $("#parallelExpenseApproval").prop("checked", false); 
		        	   $("#sequentialExpenseApproval").prop("checked", false);  
		        	   
		        	   if(json.expenseApprovalType == 'Parallel'){
		        		         $("#parallelExpenseApproval").prop("checked", true); 
				        		 $("#expenseApprovalTypeValue").val(json.expenseApprovalType);
	        			  }else{
	        				     $("#sequentialExpenseApproval").prop("checked", true);  
				        		 $("#expenseApprovalTypeValue").val(json.expenseApprovalType);
	        			  }
	        		  }*/
		           
		           
	        	   $("select[name=status]").val(projectStatus);
	        	   
	        	   $('#invoiceFrequency').val(json.invoiceFrequency).trigger('chosen:updated');
	        	   
	        	   if(json.allowFreeLance == 1){
	        		   document.getElementById("isAllowFreelanceDevelopers").checked = true;
	        	   }
	        	   
	        	   if(json.allowRemote == 1){
	        		   document.getElementById("isAllowRemoteWork").checked = true;
	        	   }
	        	   
	        	   if(json.allowOvertime == 1){
	        		   document.getElementById("isAllowOverTime").checked = true;
	        		   $('#flatPercentageId').attr('style','visibility:visible');
	        		   $('#flatPercentage').attr('style','visibility:visible');
	        		   $('#flatPercentId').val(json.overtimeRateType).trigger('chosen:updated');
	        		   $("input[name=flatPercentage]").val(json.flatPercent);
	        		   
	        	   }
	        	   
	        	   
	        	   if(json.isTimesheetAttachmentRequired == 1){
	        		   document.getElementById("isTimesheetAttachmentRequired").checked = true;
	        	   }
	        	   
	        	   if(json.allowOffShore == 1){
	        		   document.getElementById("isAllowOffshoreResource").checked = true;
	        	   }
	        	   
	        	   if(json.allowTimeEntryAfterDeadLine == 1){
	        		   document.getElementById("isTimeEntryAfterDeadline").checked = true;
	        	   }
	        	   
	        	   if(json.allowBidding == 1){
	        		   document.getElementById("isAllowBidding").checked = true;
	        	   }
	        	   
	        	   
	        	   if(json.isReportingManagerAsApprover == 'Y'){
	        		   document.getElementById("isReportingManagerAsApprover").checked = true;
	        	   }else{
	        		   document.getElementById("isReportingManagerAsApprover").checked = false;
	        	   }
	        	   
	        	   if(json.isTicketNumberRequired == 'Y'){
	        		   document.getElementById("isTicketNumberRequired").checked = true;
	        	   }else{
	        		   document.getElementById("isTicketNumberRequired").checked = false;
	        	   }
	        	   
	        	   if(json.isTaskCommentsRequired == 'Y'){
	        		   document.getElementById("isTaskCommentsRequired").checked = true;
	        	   }else{
	        		   document.getElementById("isTaskCommentsRequired").checked = false;
	        	   }
	        	   
	        	   
	        	   $("textarea[name=projectDescription]").val(json.description);
	        	   $(".Editor-editor").html(json.description);
	        	   
	        	   
	        	   //Project Budget details
	        	   if(json.projectBudget.length > 0){
	        		   for(var i=0; i<json.projectBudget.length; i++){
	        			   var report = json.projectBudget[i];
	        			   $("#estimatedBudgetCurrency").val(report.estimatedBudgetCurrency);
	        			   $("#estimatedHours").val(report.estimatedHours);
	        			   $("#estimatedBudget").val(report.estimatedBudgetValue);
	        			   projects.getBudgetApprover(report.userId);
	        		   }
	        		   
	        	   }else{
	        		   projects.getBudgetApprover();
	        	   }
	        	   
	        	   
	        	   if(json.projectDocumentsList != null && json.projectDocumentsList.length > 0){
	        		   var result = '';
	        		   for(var i=0; i<json.projectDocumentsList.length; i++){
	        			   var opt = json.projectDocumentsList[i];
	        			  
	        			     result = result +"<strong id='projectDocument"+opt.id+"'>";
	      	    		     result = result +"<i class=''><a class='glyphicon glyphicon-save' target='_blank' href=\""+opt.fileUrl+"\"></a></i>";
	      					 result = result +"<input type='button' value='"+opt.fileName+"'>";
	      					 result = result +"</a>";
	      					 result = result +"<input class='dele' type='button' onClick='projects.removeProjectDocuments("+opt.id+","+json.id+");' >";
	      					 result = result +"</strong>";
	        		   }
	        		   
	        		   
	        		   $("#savedProjectDocuments").empty();
	      			   $("#savedProjectDocuments").append(result);
	        	   }
	        	   
	        	   $("input[type=hidden][name*=uploadedFileInfo]").remove();
	      			   
	      			   for(var i=0; i<json.approverHierarchyList.length; i++){
		        		  var opt = json.approverHierarchyList[i];
		        		  
		        		  if(opt.level == "1"){
		        			  level1ApproverUser = opt.projectApproverUserId;
		        			  projects.getProjectLevel1Approvers(opt.projectApproverUserId);
		        		  }
		        		  
		        		  if(opt.level == "2"){
		        				 $("#level2Toggle").hide(); 
		        				 document.getElementById("level2Div").removeAttribute("class");
		        				 document.getElementById('level2Div').className = 'form-group level-second level-second-active';
		        				 level2ApproverUser = opt.projectApproverUserId;
		        		  }
		        	  }  
	      			   
	      			   if(level1ApproverUser != '' && level2ApproverUser != ''){
	      				  projects.getChildLevel1Approvers(level1ApproverUser,level2ApproverUser);
	      			   }
	      			   
	      			
	      			 for(var i=0; i<json.expenseApproverHierarchyList.length; i++){
		        		  var opt = json.expenseApproverHierarchyList[i];
		        		  if(opt.level == "1"){
		        			  level1ExpenseApproverUser = opt.expenseApproverUserId;
		        			  projects.getProjectLevel1ExpenseApprovers(opt.expenseApproverUserId);
		        		  }
		        		  
		        		  if(opt.level == "2"){
		        				 $("#level2ExpenseApproverToggle").hide(); 
		        				 document.getElementById("level2ExpenseApproverDiv").removeAttribute("class");
		        				 document.getElementById('level2ExpenseApproverDiv').className = 'form-group expenselevel-second expenselevel-second-active';
		        				 level2ExpenseApproverUser = opt.expenseApproverUserId;
		        		  }
		        	  } 
	      			 
	     			   
	     			   if(level1ExpenseApproverUser != '' && level2ExpenseApproverUser != ''){
	     				  projects.getChildLevel1ExpenseApprovers(level1ExpenseApproverUser,level2ExpenseApproverUser);
	     			   }
	      			 
	      			   
	      			   $("#projectDetailsloading").removeAttr("style");
			        
		        },
		    });  
		}
	}
	
	
	 this.getProjectLevel1Approvers = function(approverUserId){
		$("#level1Manager").empty();
			 $.ajax({
			    	type: "GET",
			        url: sourceleadUrl+"/getProjectLevel1Approvers",
			        success: function(json) {
			        	var output = "";
			        	output = output + "<option value=''>-- Select Level1 Manager --</option>";
			        	for (var i=0; i<json.length; i++)
						{
			        		var selected = "";
			        		var opt = json[i];
			        		if(approverUserId != null && approverUserId != 'undefined' && approverUserId != '' && approverUserId == opt.id){
			        			selected = "selected";
			        		}
				        		output = output + "<option value='" +opt.id+ ","+opt.userType+"' "+selected+">"+opt.fullName+" ("+opt.userType+")</option>";
						}
			        	
				        $("#level1Manager").append(output);
				        $('#level1Manager').trigger("chosen:updated");
				        
			        }
			    });
		 }
	 
	 
	 
	 this.getLevel2Approvers = function(level1ApproverUser){
			var currentApproverId = '';
			$('#chosen-level1Manager').find('p.jquery_form_error_message').remove();
			if(level1ApproverUser != null && level1ApproverUser != '' && level1ApproverUser != 'undefined'){
				currentApproverId = level1ApproverUser;
			}else{
				currentApproverId = document.getElementById("level1Manager").value;
			}
			var currentUser = currentApproverId.split(',');
			var currentUserId = currentUser[0];
			var currentUserType = currentUser[1];
			var count = 1;
			$(".myCheckbox").each(
							function() {
								var localId = this.id;
								document.getElementById(localId).checked = false; 
			});
			$(".myCheckbox").val('');
			
			$("#level2Manager").empty();    
			$('#level2Manager').val('').trigger('chosen:updated');
			if(currentUserType != null && currentUserType != '' && currentUserType !='undefined' && currentUserType != 'Admin'){
			    $("#level2Toggle").show();
			    projects.getChildLevel1Approvers(currentUserId);
			}else if(currentUserType != null && currentUserType != '' && currentUserType !='undefined' && currentUserType == 'Admin'){
			    $("#level2Toggle").hide();   
			    $("#level2Div").removeClass("level-second-active");
			}
			
			
		} 
	 
	 
	 
	  this.getLevel2ExpenseApprovers = function(level1ExpenseApprover){
			var currentExpenseApproverId = '';
			$('#chosen-level1ExpenseApprover').find('p.jquery_form_error_message').remove();
			if(level1ExpenseApprover != null && level1ExpenseApprover != '' && level1ExpenseApprover != 'undefined'){
				currentExpenseApproverId = level1ExpenseApprover;
			}else{
				currentExpenseApproverId = document.getElementById("level1ExpenseApprover").value;
			}
			
			var currentExpenseApprover = currentExpenseApproverId.split(',');
			var currentExpenseApproverId = currentExpenseApprover[0];
			var currentExpenseApproverType = currentExpenseApprover[1];
			var count = 1;
			$(".myCheckbox").each(
							function() {
								var localId = this.id;
								document.getElementById(localId).checked = false; 
			});
			$(".myCheckbox").val('');
			
			$("#level2ExpenseApprover").empty();    
			$('#level2ExpenseApprover').val('').trigger('chosen:updated');
			if(currentExpenseApproverType != null && currentExpenseApproverType != '' && currentExpenseApproverType !='undefined' && currentExpenseApproverType != 'Admin'){
			    $("#level2ExpenseApproverToggle").show();
				projects.getChildLevel1ExpenseApprovers(currentExpenseApproverId);
			}else if(currentExpenseApproverType != null && currentExpenseApproverType != '' && currentExpenseApproverType !='undefined' && currentExpenseApproverType == 'Admin'){
			    $("#level2ExpenseApproverToggle").hide();   
			    $("#level2ExpenseApproverDiv").removeClass("expenselevel-second-active");
			}
			
			
		} 
	  
	  
	  
	  this.getChildLevel1Approvers = function(currentApproverId,level2ApproverUserId){
			 if(currentApproverId != null && currentApproverId != ''  && currentApproverId != 'undefined'){
				 $.ajax({
				    	type: "POST",
				        url: sourceleadUrl+"/getApproversByUserId?currentApproverId="+currentApproverId,
				        success: function(json) {
				        	var output = "";
				        	output = output + "<option value=''>-Select Manager-</option>"; 
				        	for (var i=0; i<json.length; i++)
							{
					        		var opt = json[i];
					        		var selected = '';
					        		if(level2ApproverUserId != null && level2ApproverUserId != 'undefied' && level2ApproverUserId != '' && level2ApproverUserId == opt.id){
					        			selected = "selected";
					        		}
					        		output = output + "<option value='" +opt.id+ "' "+selected+">"+opt.fullName+" ("+opt.userType+")</option>";
							}
				        	$("#level2Manager").empty();
					        $("#level2Manager").append(output);
					        $('#level2Manager').trigger("chosen:updated");
				        },
				        error: function(e){
				            
				        }
				    });
			 }
		} 
	  
	  
	  this.getChildLevel1ExpenseApprovers = function(currentExpenseApproverId,level2ExpenseApproverUserId){
			 if(currentExpenseApproverId != null && currentExpenseApproverId != ''  && currentExpenseApproverId != 'undefined'){
				 $.ajax({
				    	type: "POST",
				        url: sourceleadUrl+"/getApproversByUserId?currentApproverId="+currentExpenseApproverId,
				        success: function(json) {
				        	var output = "";
				        	output = output + "<option value=''>-Select Approver-</option>"; 
				        	for (var i=0; i<json.length; i++)
							{
					        		var opt = json[i];
					        		var selected = '';
					        		if(level2ExpenseApproverUserId != null && level2ExpenseApproverUserId != 'undefied' && level2ExpenseApproverUserId != '' && level2ExpenseApproverUserId == opt.id){
					        			selected = "selected";
					        		}
					        		output = output + "<option value='" +opt.id+ "' "+selected+">"+opt.fullName+" ("+opt.userType+")</option>";
							}
				        	$("#level2ExpenseApprover").empty();
					        $("#level2ExpenseApprover").append(output);
					        $('#level2ExpenseApprover').trigger("chosen:updated");
				        },
				        error: function(e){
				            
				        }
				    });
			 }
		} 
	  
	  
	  

	  this.getProjectLevel1ExpenseApprovers =  function(approverUserId){
	  	 $("#level1ExpenseApprover").empty();
	  		 $.ajax({
	  		    	type: "GET",
	  		        url: sourceleadUrl+"/getProjectLevel1Approvers",
	  		        success: function(json) {
	  		        	var output = "";
	  		        	output = output + "<option value=''>-- Select Level1 Approver --</option>";
	  		        	for (var i=0; i<json.length; i++)
	  					{
	  		        		var selected = "";
	  		        		var opt = json[i];
	  		        		if(approverUserId != null && approverUserId != 'undefied' && approverUserId != '' && approverUserId == opt.id){
	  		        			selected = "selected";
	  		        		}
	  			        		output = output + "<option value='" +opt.id+ ","+opt.userType+"' "+selected+">"+opt.fullName+" ("+opt.userType+")</option>";
	  					}
	  		        	
	  			        
	  			        $("#level1ExpenseApprover").append(output);
	  			        $('#level1ExpenseApprover').trigger("chosen:updated");
	  		        }
	  		    });
	  	 }
	  
	  
	  this.validateProjectSequeneNumber = function() {
			var ProjectSequeneNumber = document.getElementById("projectSequeneNumber").value.trim();
			if(ProjectSequeneNumber.match(/^[^\s-](.*)*$/) !== null){
			var orgLocId =  document.getElementById("loginOrgLocationId").value;
			$('#ProjectIdDiv').find('p.jquery_form_error_message').remove();
			if (ProjectSequeneNumber != null && ProjectSequeneNumber != "") {
				$.ajax({
							type : "POST",
							url : sourceleadUrl+ "/validateProjectSequeneNumberById?ProjectSequeneNumber="+ ProjectSequeneNumber+"&orgLocId="+orgLocId,
							success : function(data) {
								if (data == true) {
									$("input#projectSequeneNumber").after("<p class='jquery_form_error_message'>"+ ProjectSequeneNumber+ " is Already Existed</span>");
									document.getElementById("projectSequeneNumber").setAttribute('record-exist', 'yes');
									document.getElementById("projectSequeneNumber").setAttribute('record-exist-errorMsg',ProjectSequeneNumber+ ' is Already Existed');
									$('input#projectSequeneNumber').removeAttr( "record-exist record-exist-errorMsg");
									$("#save_project").attr("disabled", "disabled");
				    				
								} else {
									$("#save_project").removeAttr("disabled");
									$('input#projectSequeneNumber').removeAttr("record-exist record-exist-errormsg");
									
								}
							},
						});
		     	}
			}
		}
	  
	
	  this.getBudgetApprover = function(approverUserId){
		  var orgLocationId = document.getElementById("loginOrgLocationId").value;
			var actionType = "ROLE_BUDGET_APPROVER";
			 $.ajax({
			    	type: "GET",
			        url: sourceleadUrl+"/getSourceLeadActionApproversList?orgLocationId="+orgLocationId+"&actionType="+actionType,
			        success: function(json) {
			        	var output = "";
			        	for (var i=0; i<json.length; i++)
						{
			        		 var opt = json[i];
			        		 if(opt.id == approverUserId){
				        			output = output + "<option value='"+opt.id+"' selected>"+opt.name+" ("+opt.userRole+")</option>";
				        		}else{
				        			output = output + "<option value='"+opt.id+"'> "+opt.name+" ("+opt.userRole+")</option>";
				        		}
						}
				        $("#budgetApprover").append(output);
				        $('#budgetApprover').chosen();
				        $('#budgetApprover').trigger("chosen:updated");
			        }
			    });
		}
	  
	  
	  
	  this.validateProjectName = function(){
			var projectUUID = document.getElementById("projectUUID").value;
			var projectName = document.getElementById("projectName").value;
			
			$.ajax({
				type : "GET",
				url : sourceleadUrl+ "/validateOrgLocationProjectName?projectUUID="+projectUUID+"&projectName="+projectName,
				success : function(data) {
					if (data == true) {
						$("input#projectName").after("<p class='jquery_form_error_message'>"+ projectName+ " is Already Existed</span>");
						document.getElementById("projectName").setAttribute('record-exist', 'yes');
						document.getElementById("projectName").setAttribute('record-exist-errorMsg',projectName+ ' is Already Existed');
						$('input#projectName').removeAttr( "record-exist record-exist-errorMsg");
						$("#save_project").attr("disabled", "disabled");
					} else {
						$('input#projectName').removeAttr("record-exist record-exist-errormsg");
						$("#save_project").removeAttr("disabled");
					}
				},
			});
		}
	  
	  
		this.validateShortCode = function(){
			var projectShortCode = $('#projectShortCode').val();
			var orgLocId = document.getElementById("loginOrgLocationId").value;
			$.ajax({
				type : "POST",
				url : sourceleadUrl+ "/validateProjectShortCodeByOrgLocationId?projectShortCode="+projectShortCode+"&orgLocId="+orgLocId,
				success : function(data) {
					if (data == true) {
						$("input#projectShortCode").after("<p class='jquery_form_error_message'>"+ projectShortCode+ " is Already Existed</span>");
						document.getElementById("projectShortCode").setAttribute('record-exist', 'yes');
						document.getElementById("projectShortCode").setAttribute('record-exist-errorMsg',projectShortCode+ ' is Already Existed');
						$('input#projectShortCode').removeAttr( "record-exist record-exist-errorMsg");
						$("#save_project").attr("disabled", "disabled");
					} else {
						$('input#projectShortCode').removeAttr("record-exist record-exist-errormsg");
						$("#save_project").removeAttr("disabled");
					}
				},
			});
			}
		
		
		
		this.removeProjectDocuments = function(documentId,projectId){
			$.ajax({
			   	type: "POST",
			    url: sourceleadUrl+"/terminateProjectDoc?porjectDocumentId="+documentId, 	   
				    success: function(data) {
				    	$("#projectDocument"+documentId).remove();
				    },
		     });

		}
		
		this.toUpperCase = function(){
			var toupper = $('#projectShortCode').val().toUpperCase().trim();
			$('#projectShortCode').val(toupper);
			}
	
}


var fileCounter = 0;
function addFileUpload(){
	$('<div>').attr('style','float:left; width:325px; border:1px solid #ddd; margin:0 15px 15px 0; padding:5px;overflow: hidden; background-color:#367fa9; color:#fff; ').append('<input id="file'+fileCounter+'"  type="file" class="pull-left" onchange="uploadButton('+fileCounter+');"/>' +
            '<a id="Button' + fileCounter + '' +
            'value="" onclick = "RemoveFileUpload(this)"><i class="fa fa-times btn btn-danger btn-sm btn-sm2 pull-right"></i></a>').appendTo($('#FileUploadContainer'));

    		fileCounter++;
    		
}

function RemoveFileUpload(div)
{
	 document.getElementById("FileUploadContainer").removeChild(div.parentNode);
}

function uploadButton(id){
fileUpload("file"+id+"", "projects", "project_form","uploadedFileInfo");
}


function flushProject() {
	$("#save_client").removeAttr("disabled");
	$('#orgName').val('');
	$('#companyWebsite').val('');
	$(".error").removeClass("error");
	$('#newClient').find('p.jquery_form_error_message').remove();
}
	
function assignEmployeeCancel(){
	$(".myCheckbox").each(function() {
		var empAssigned = $(this).attr('emp-assigned');
		if(empAssigned != null && empAssigned != 'undefined' && empAssigned != '' && empAssigned == 'true'){
		}
		else{
			$(this).prop('checked', false);
		}
		
    });	
}

