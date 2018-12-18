var performanceManagement = new PerformanceManagement();

function PerformanceManagement(){
	
	this.savePerformanceBadgeDetails  = function (){
		 var validationSettings = {
			        errorMessagePosition : 'element',
			       borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd' 
		         }; 
		 
		 if ($('#performance_badge_form').validate(false, validationSettings)){ 
		        var formData = getFormData("performance_badge_form");
		        $("#badgeDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		         $.ajax({
			    	type: "POST",
			    	data: formData,
			    	 url: sourceleadUrl+"/savePerformanceBudgetDetails?",
			        success: function(response) {
			        	$("#badgeDetailsLoading").removeAttr('style');
			        	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
	     	    		    showError(response.responseMessage);
	     	    		}
			        	else if (response.response === "AWKWARD") {
			        	    location.href = "/errorPage";
			        	} else if (response.response === "SUCCESS") {
			        		performanceManagement.closeBadgePopup();
			        		performanceManagement.getBadgesList();
			        		$(".mask, #modalTasktype").fadeOut();
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

	$('body').on('blur', '#performance_badge_form', function() {
			$("#performance_badge_form").showHelpOnFocus();
			$("#performance_badge_form").validateOnBlur(false, validationSettings);  
		});
	
	
	
	this.getBadgesList = function(){
    	$.ajax({
    		  type: "GET",
    		    url: sourceleadUrl+"/getPerformanceRewardsAndBadgesList",
    		     success :function(response) {
    		    	    $("#performanceManagementDiv").empty();  
    		    	    $("#performanceManagementDiv").append(response);
    				},
    			}); 
    			
    }

    this.addFeedbackTemplate = function(templateId){
    	$.ajax({
    		  type: "GET",
    		    url: sourceleadUrl+"/addNewFeedbackTemplate",
    		     success :function(response) {
    		    	    $("#performanceManagementDiv").empty();  
    		    	    $("#performanceManagementDiv").append(response);
    		    	    customizeFields(sourceleadUrl,templateId);
    				},
    			}); 
    			
    }
    
    this.addNewAppraisalCycle = function(templateId){
    	$.ajax({
  		  type: "GET",
  		    url: sourceleadUrl+"/addNewAppraisalCycle",
  		     success :function(response) {
  		    	    $("#performanceManagementDiv").empty();  
  		    	    $("#performanceManagementDiv").append(response);
  		    	    
  		    	    if(templateId != null && templateId != '' && templateId != 'undefined'){
  		    	    	 performanceManagement.editAppraisalCycle(templateId);
  		    	    }
  		    	   
  		    	   if(templateId == null || templateId == '' || templateId == 'undefined'){
  		    		performanceManagement.departmentsList();
  		    		performanceManagement.rolesList();
  		    		performanceManagement.employeeTypeList();
  		    		performanceManagement.feedbackCategoriesList();
  		    	   }
  		    	    
  		    	    performanceManagement.appraisalStartDateAndEndDatePickers();
		    	    performanceManagement.appraisalDescriptionEditor();
  				},
  			}); 
    }
    
    this.viewAppraisalCycle = function(){
    	$.ajax({
  		  type: "GET",
  		    url: sourceleadUrl+"/getPerformanceAppraisalCyclesList",
  		     success :function(response) {
  		    	    $("#performanceManagementDiv").empty();  
  		    	    $("#performanceManagementDiv").append(response);
  		    	    performanceManagement.appraisalTemplatespagination();
  				},
  			}); 
    }


    this.getFeedbackList = function(){
    	$.ajax({
    		  type: "GET",
    		    url: sourceleadUrl+"/getPerformanceFeedbackTemplateList",
    		     success :function(response) {
    		    	    $("#performanceManagementDiv").empty();  
    		    	    $("#performanceManagementDiv").append(response);
    				},
    			}); 
    }
    
    
    this.viewFeedbackTemplate = function(templateId){
    	$.ajax({
    		  type: "GET",
    		    url: sourceleadUrl+"/viewPerformanceFeedbackTemplate",
    		     success :function(response) {
    		    	    $("#performanceManagementDiv").empty();  
    		    	    $("#performanceManagementDiv").append(response);
    		    	    generateForm(sourceleadUrl,templateId);
    				},
    			}); 
    			
    }
    
    
   this.deleteFeedbackTemplate = function(templateId){
    	$.ajax({
    		  type: "POST",
    		    url: sourceleadUrl+"/deletePerformanceFeedbackTemplate?templateId="+templateId,
    		     success :function(response) {
    		    	 
    		           if (response === "AWKWARD") {
			        	    location.href = "/errorPage";
			        	} else if (response === "SUCCESS") {
			        		performanceManagement.getFeedbackList();	
			        	} else if (response.indexOf('Sign In') !== -1) {
			        		location.replace(sourceleadUrl);
			        	} else {
			        	    showError("Operation failed, please try again");
			        	}
    		     },
			}); 
    			
    }
	
  this.addBadgePopup = function() {
 	  $(".mask, #modalTasktype").fadeIn();
   }
   
   this.closeBadgePopup = function(){
 	  $(".mask, #modalTasktype").fadeOut();
 	  $('#badgeNameDiv').find('p.jquery_form_error_message').remove();
 	  $('#badgeImageDiv').find('p.jquery_form_error_message').remove();
 	  $('#badgeDescriptionDiv').find('p.jquery_form_error_message').remove();
 	  $('#uploadBtn').val('');
 	  
 	     $("#badgeId").val('');
 		 $("#badgeName").val('');
 		 $("textarea[id='badgeDescription']").text('');
 		 document.getElementById("badgeImg").src = '/resources/images/rewards_badges.svg';
 	  
   }
   
   
   this.editBadge = function(badgeId){
	   performanceManagement.addBadgePopup();
 	  $("#badgeDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
 		$.ajax({
 			  type: "GET",
 			    url: sourceleadUrl+"/getPerformanceRewardsAndBadgeDetailsById?badgeId="+badgeId,
 			     success :function(response) {
 			    	 
 			    		if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
 	     	    		    showError(response.responseMessage);
 	     	    		    $("#badgeDetailsLoading").removeAttr('style');
 	     	    		}
 			        	else if (response.response === "AWKWARD") {
 			        	    location.href = "/errorPage";
 			        	} else if (response.response === "SUCCESS") {
 			        		 $("#badgeId").val(response.object.badgeId);
 			        		 $("#badgeName").val(response.object.name);
 			        		 $("textarea[id='badgeDescription']").text(response.object.description);
 			        		 if(response.object.fileURL != null && response.object.fileURL != 'undefined' && response.object.fileURL != ''){
 			        			 document.getElementById("badgeImg").src = response.object.fileURL;
 			        		 }
 			        		
 			        		 $("#badgeDetailsLoading").removeAttr('style');
 			        	} else if (response.indexOf('Sign In') !== -1) {
 			        		location.replace(sourceleadUrl);
 			        	} else {
 			        	    showError("Operation failed, please try again");
 			        	}
 			    	  
 					},
 				}); 
   }
   
   
   this.deleteBadge = function(badgeId){
 		$.ajax({
 			  type: "POST",
 			    url: sourceleadUrl+"/deletePerformanceRewardsAndBadgeById?badgeId="+badgeId,
 			     success :function(response) {
 			    	 
 			    		if (response && (response === "INVALID_DATA" || response === "INVALID_USER")) {
 	     	    		    showError("Invalid User");
 	     	    		}
 			        	else if (response === "AWKWARD") {
 			        	    location.href = "/errorPage";
 			        	} else if (response === "SUCCESS") {
 			        		performanceManagement.getBadgesList();
 			        	} else if (response.indexOf('Sign In') !== -1) {
 			        		location.replace(sourceleadUrl);
 			        	} else {
 			        	    showError("Operation failed, please try again");
 			        	}
 			    	  
 					},
 				}); 
   }
   
    
  this.applicableType = function(){
  	 var applicableForRadio = $('input[name=applicableFor]');
       var applicableForType = applicableForRadio.filter(':checked').val();
       if(applicableForType == 'Experienced'){
      	 $('#experienceYears').removeAttr('data-validation-optional');
   		 $('#experienceYears').attr("data-validation","required");
   		 
   		 $('#experienceMonths').removeAttr('data-validation-optional');
   		 $('#experienceMonths').attr("data-validation","required");
   		 document.getElementById("experienceDiv").style.display="";
   		 performanceManagement.getYearsDropDownCount();
    	 performanceManagement.getMonthsDropDownCount();
   		
       }else{
      	 $('#experienceYears').val('');
      	 $('#experienceMonths').val('');
      	 
      	 $('#experienceYears').attr('data-validation-optional');
   		 $('#experienceYears').removeAttr("data-validation","required");
   		 
   		 $('#experienceMonths').attr('data-validation-optional');
   		 $('#experienceMonths').removeAttr("data-validation","required"); 
   		 
   		 $('#experienceYearsDiv').find('p.jquery_form_error_message').remove(); 
   		 $('#experienceMonthsDiv').find('p.jquery_form_error_message').remove(); 
   		 document.getElementById("experienceDiv").style.display="none";
       }
  }

  this.getYearsDropDownCount = function(){
  	var i = "";
  	result = "";
  	result = result
  	+ '("<option selected value="">Select Years</option>")';
  	for (var i = 0; i <= 50; i++) {
  		result = result + '("<option value='+i+'>' + i + '</option>")';
  		
  	}
  	result = result + '</select>';
  	$('#experienceYears').empty();
  	$('#experienceYears').append(result);
  }

  this.getMonthsDropDownCount = function(){
  	var i = "";
  	result = "";
  	result = result
  	+ '("<option selected value="">Select Months</option>")';
  	for (var i = 0; i <= 12; i++) {
  		result = result + '("<option value='+i+'>' + i + '</option>")';
  	}
  	result = result + '</select>';
  	$('#experienceMonths').empty();
  	$('#experienceMonths').append(result);
  }

  this.appraisalStartDateAndEndDatePickers = function(){
	  var finalDateFormat = getJSDateFormat(locationDateFormat);
	   $(function() {
			  $('#appraisalStartDate').datepicker({
			      dateFormat: finalDateFormat,
			      changeMonth: true,
			  	  changeYear: true, 
			  	  minDate:0,
			  	  onSelect : function(selected) {
			  		var dt = $(this).datepicker('getDate', '+1d');
					dt.setDate(dt.getDate() + 1);
					$("#appraisalEndDate").datepicker("option", "minDate", dt);
					performanceManagement.validAppraisalDates();
					$("#appraisalEndDate").removeAttr("disabled");
			 	  }
			  });
		  });
	   
	   
	   
	   $(function() {
			  $('#appraisalEndDate').datepicker({
			      dateFormat: finalDateFormat,
			      changeMonth: true,
			  	  changeYear: true, 
			  	  minDate:0,
			  	  onSelect : function(selected) {
			  		var dt = $(this).datepicker('getDate', '-1d');
					dt.setDate(dt.getDate() - 1);
					$("#appraisalStartDate").datepicker("option", "maxDate", dt);
					performanceManagement.validAppraisalDates();
			 	  }
			  });
		  });
	   
	   
	   
		    $(function() {
			  $('#selfAppraisalStartDate').datepicker({
			      dateFormat: finalDateFormat,
			      changeMonth: true,
			  	  changeYear: true, 
			  	  minDate:0,
			  	  onSelect : function(selected) {
			  		var dt = $(this).datepicker('getDate', '+1d');
					dt.setDate(dt.getDate() + 1);
					$("#selfAppraisalEndDate").datepicker("option", "minDate", dt);
					performanceManagement.validAppraisalDates();
					$("#selfAppraisalEndDate").removeAttr("disabled");
			 	  }
			  });
		  });
		
		
		
			$(function() {
					  $('#selfAppraisalEndDate').datepicker({
					      dateFormat: finalDateFormat,
					      changeMonth: true,
					  	  changeYear: true, 
					  	  minDate:0,
					  	  onSelect : function(selected) {
					  		var dt = $(this).datepicker('getDate', '-1d');
							dt.setDate(dt.getDate() - 1);
							$("#selfAppraisalStartDate").datepicker("option", "maxDate", dt);
							performanceManagement.validAppraisalDates();
					 	  }
					  });
		  });
			
			
			
		$(function() {
			  $('#appraisalApprovingStartDate').datepicker({
			      dateFormat: finalDateFormat,
			      changeMonth: true,
			  	  changeYear: true, 
			  	  minDate:0,
			  	  onSelect : function(selected) {
			  		var dt = $(this).datepicker('getDate', '+1d');
					dt.setDate(dt.getDate() + 1);
					$("#appraisalApprovingEndDate").datepicker("option", "minDate", dt);
					performanceManagement.validAppraisalDates();
					$("#appraisalApprovingEndDate").removeAttr("disabled");
			 	  }
			  });
		  });
		
		
		
			$(function() {
					  $('#appraisalApprovingEndDate').datepicker({
					      dateFormat: finalDateFormat,
					      changeMonth: true,
					  	  changeYear: true, 
					  	  minDate:0,
					  	  onSelect : function(selected) {
					  		var dt = $(this).datepicker('getDate', '-1d');
							dt.setDate(dt.getDate() - 1);
							$("#appraisalApprovingStartDate").datepicker("option", "maxDate", dt);
							performanceManagement.validAppraisalDates();
					 	  }
					  });
		  });
			
			
  }
  
  
  this.validAppraisalDates = function(){
	  var appraisalStartDate = document.getElementById("appraisalStartDate").value;
		 if(appraisalStartDate){
			 $('#appraisalStartDateDiv').find('p.jquery_form_error_message').remove(); 
		 }
		
	 var appraisalEndDate = document.getElementById("appraisalEndDate").value;
		 if(appraisalEndDate){
			 $('#appraisalEndDateDiv').find('p.jquery_form_error_message').remove(); 
		 }
		 
		 
     var selfAppraisalStartDate = document.getElementById("selfAppraisalStartDate").value;
		 if(selfAppraisalStartDate){
			 $('#selfAppraisalStartDateDiv').find('p.jquery_form_error_message').remove(); 
		 }
		 
     var selfAppraisalEndDate = document.getElementById("selfAppraisalEndDate").value;
		 if(selfAppraisalEndDate){
			 $('#selfAppraisalEndDateDiv').find('p.jquery_form_error_message').remove(); 
		 }
		 
	 var appraisalApprovingStartDate = document.getElementById("appraisalApprovingStartDate").value;
		 if(appraisalApprovingStartDate){
			 $('#appraisalApprovingStartDateDiv').find('p.jquery_form_error_message').remove(); 
		 }
		 
     var appraisalApprovingEndDate = document.getElementById("appraisalApprovingEndDate").value;
		 if(appraisalApprovingEndDate){
			 $('#appraisalApprovingEndDateDiv').find('p.jquery_form_error_message').remove(); 
		 }
  }
  
  this.appraisalDescriptionEditor = function(){
	    $("#txtEditor").Editor();
		$("#txtEditor").find(".Editor-editor").data-validation('validate_length length2-4000');
		$("#txtEditor").find(".Editor-editor").data-validation-optional('rule-required',true);
  }
  
  $(document).on('change', '#appraisalCategories', function(evt, params) {
		if (params.selected != undefined) {
			performanceManagement.updateSelectedTemplatesRows(params.selected, false);
		}
		if (params.deselected != undefined) {
			performanceManagement.updateSelectedTemplatesRows(params.deselected,true);
		}
	});
  
  this.updateSelectedTemplatesRows = function(id,removedFlag){
	  if(id===0 && removedFlag){
			$("#selectedTemplatesDiv").empty();
		} else {	
			$("#appraisalCategories option").each(function () {
				if($(this).val() === id) {
					if(removedFlag) {
						$("#selectedTemplatesDiv").find("div[data-template_id=" + $(this).val() + "]").remove();
						return;
					} else {
						var text="<div class='multiTemplates positionRelative' id="+$(this).val()+" data-template_id=" + $(this).val() + ">";
						text += "<i class='fa fa-close close_multi_job' onclick='performanceManagement.closeSelectedJobDiv(this)'></i>";
						text += "<div class='multiJobTitle clearfix'>";
						text += "<h2 class='pull-left'>" + $(this).text() + "</h2>";
						text += "<span class='pull-right mr'><input type='text' onblur='performanceManagement.validatePercentage("+$(this).val()+");performanceManagement.validatePercentageTotalSumValue();' id='percent"+$(this).val()+"' class='form-control' field-name='Percentage' placeholder='Percentage' data-validation='validate_int required'></span>";
						text += "</div>";
						text += "<div class='row mxrateerror' id='percentageDiv"+$(this).val()+"'>";
						text += "<input type='hidden' name='percent"+$(this).val()+"' id='percentageValidation"+$(this).val()+"'>";
						text += "</div>";
						text += "</div>";
						$("#selectedTemplatesDiv").append(text);
						return;
					}
				}
			});
		}
  }
  
  
  this.closeSelectedJobDiv = function(ele) {
		$(ele).parent().remove();
		var id = $(ele).parent().data("template_id");
		$('#appraisalCategories').find("option[value='" + id + "']").prop("selected",false).end().trigger('chosen:updated');
	}
  
  
  
  this.saveAppraisalCycleDetails = function(){
	  
	  var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
	  
	  
	  if ($('#createAppraisal_form').validate(false, validationSettings)){ 
		  var ee = $("#txtEditor").val($('.Editor-editor').html()); 
		   var templates = [];
			$(".multiTemplates").each(function() {				
				templates.push({
					templateId : $(this).data("template_id") ? $(this).data("template_id") : 0,
					percentage : $("#percent"+$(this).data("template_id")).val()
				});
			});
			var con = JSON.stringify(templates);
			$("#jsonCategoriesString").val(con);
	        var appraisalFormData = getFormData1("createAppraisal_form");
	        $("#appraisalDetailsLoading").attr('style','position:absolute; width:100%; height:200%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	        $.ajax({
		    	type: "POST",
		    	 processData: false,
                 contentType: false,
		    	 data: appraisalFormData,
		    	 url: sourceleadUrl+"/saveAppraisalCycleTemplate",
		        success: function(response) {
		        	$("#appraisalDetailsLoading").removeAttr('style');
		        	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
   	    		    showError(response.responseMessage);
   	    		}
		        	else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		performanceManagement.viewAppraisalCycle();
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
  
  $('body').on('blur', '#createAppraisal_form', function() {
		$("#createAppraisal_form").showHelpOnFocus();
		$("#createAppraisal_form").validateOnBlur(false, validationSettings);  
	});
  
  
  
  this.validatePercentage = function(id){
	  var profilePercentage = $('#percent'+id).val();
		
		 if(profilePercentage > 100 || profilePercentage < 0){
			 if(profilePercentage < 0){
				    $('#percentageDiv'+id).find('p.jquery_form_error_message').remove(); 
					$('input#percentageValidation'+id).attr('class','error signin_fild');
					$("input#percentageValidation"+id).after("<p class='jquery_form_error_message'>Percentage should not in negitive</p>");
					$('input#percentageValidation'+id).attr('record-exist','yes');
					$('input#percentageValidation'+id).attr('record-exist-errorMsg','Profile Percentage should not in negitive'); 
			 }else{
				    $('#percentageDiv'+id).find('p.jquery_form_error_message').remove(); 
				    $('input#percentageValidation'+id).attr('class','error signin_fild');
				    $('input#percentageValidation'+id).after("<p class='jquery_form_error_message'>Percentage should not more than 100</p>");
				    $('input#percentageValidation'+id).attr('record-exist','yes');
				    $('input#percentageValidation'+id).attr('record-exist-errorMsg','Percentage should not more than 100'); 
			 }
		 }else{
			 
			 $('#percentageDiv'+id).find('p.jquery_form_error_message').remove(); 
			 $('input#percentageValidation'+id).removeAttr( "class record-exist record-exist-errormsg");
			 $('#percentageDiv'+id).find('p.jquery_form_error_message').remove();
		 }
  }
  
  this.validatePercentageTotalSumValue = function(){
	  var percentage = 0;
	  $(".multiTemplates").each(function() {	
		  percentage = Number(percentage)+Number($("#percent"+$(this).data("template_id")).val());
	  });
	  
	  if(percentage > 100){
		    $('#percentSumDiv').find('p.jquery_form_error_message').remove(); 
		    $('#percentTotalSum').attr('class','error signin_fild');
		    $('#percentTotalSum').after("<p class='jquery_form_error_message'>Total percentage should not more than 100</p>");
		    $('#percentTotalSum').attr('record-exist','yes');
		    $('#percentTotalSum').attr('record-exist-errorMsg','Total percentage should not more than 100'); 
		    $("#savePerformanceBudget").attr("disabled", "disabled");
	  }else{
		     $('#percentSumDiv').find('p.jquery_form_error_message').remove(); 
			 $('input#percentTotalSum').removeAttr( "class record-exist record-exist-errormsg");
			 $('#percentSumDiv').find('p.jquery_form_error_message').remove();
			 $("#savePerformanceBudget").removeAttr("disabled");
	  }
  }
  
  
  this.clearAppraisalStartDate = function(){
	  $("#appraisalStartDate").val("");
	  $("#appraisalEndDate").val("");
  }
  
  this.clearAppraisalEndDate = function(){
	  $("#appraisalEndDate").val("");
  }
  
  this.clearSelfAppraisalStartDate = function(){
	  $("#selfAppraisalStartDate").val("");
	  $("#selfAppraisalEndDate").val("");
  }
  
  this.clearSelfAppraisalEndDate = function(){
	  $("#selfAppraisalEndDate").val("");
  }
  
  this.clearAppraisalApprovingStartDate = function(){
	  $("#appraisalApprovingStartDate").val("");
	  $("#appraisalApprovingEndDate").val("");
  }
  
  this.clearAppraisalApprovingEndDate = function(){
	  $("#appraisalApprovingEndDate").val("");
  }
  
  this.changeDescriptionDisplayStatus = function(){
	  var isChecked = $("#descriptionDisplay").is(":checked");
	  if(isChecked){
		  $("#descriptionDisplay").val("Y");
	  }else{
		  $("#descriptionDisplay").val("N");
	  }
  }
	
  this.appraisalTemplatespagination = function(){
	  $('#appraisalTemplatesPagination').DataTable( { 
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": ""
	     	}
	    } );
  }
  
  
  this.editAppraisalCycle = function(templateId){
	  
 	  $("#appraisalDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
			  type: "GET",
			    url: sourceleadUrl+"/getAppraisalCycleDetailsById?templateId="+templateId,
			     success :function(response) {
			    	 
			    		if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
	     	    		    showError(response.responseMessage);
	     	    		    $("#appraisalDetailsLoading").removeAttr('style');
	     	    		}
			        	else if (response.response === "AWKWARD") {
			        	    location.href = "/errorPage";
			        	} else if (response.response === "SUCCESS") {
			        		 $("#appraisalTemplateId").val(response.object.appraisalTemplateId);
			        		 $("#appraisalName").val(response.object.name);
			        		 $("#appraisalStartDate").val(response.object.appraisalStartDate);
			        		 $("#appraisalEndDate").val(response.object.appraisalEndDate);
			        		 performanceManagement.departmentsList(response.object.appraisalDepartment);
			        		 performanceManagement.rolesList(response.object.appraisalRoles);
			        		 performanceManagement.employeeTypeList(response.object.appraisalEmpType);
			        		 performanceManagement.feedbackCategoriesList(response.object.appraisalCategories);
			        		 $("#appraisalDetailsLoading").removeAttr('style');
			        	} else if (response.indexOf('Sign In') !== -1) {
			        		location.replace(sourceleadUrl);
			        	} else {
			        	    showError("Operation failed, please try again");
			        	}
			    	  
					},
				});
  }
  
  
  this.departmentsList = function(departmentIds){
	  $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getDepartmentsListForPerformanceAppraisal", 
	        success: function(json) {
	        	var output = "";
	        	for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		if(departmentIds != null && departmentIds != '' && departmentIds != 'undefined'){
		        			var deptIds = departmentIds.split(",");
		        			if (deptIds.indexOf(opt.deptId.toString()) > -1) {
		        				output = output + "<option value='" +opt.deptId+ "' selected>"+opt.departmentName+"</option>";
		        			}else{
		        				output = output + "<option value='" +opt.deptId+ "'>"+opt.departmentName+"</option>";
		        			}
		        		}else{
		        			output = output + "<option value='" +opt.deptId+ "'>"+opt.departmentName+"</option>";
		        		}
		        		
		        		
				}
	        	$("#appraisalDepartment").empty();
		        $("#appraisalDepartment").append(output);
		        $('#appraisalDepartment').trigger("chosen:updated");
	        },
	    });
  }
  
  this.rolesList = function(rolesIds){
	  $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getRolesListForPerformanceAppraisal", 
	        success: function(json) {
	        	var output = "";
	        	for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		if(rolesIds != null && rolesIds != '' && rolesIds != 'undefined'){
		        			var userRoleIds = rolesIds.split(",");
		        			if (userRoleIds.indexOf(opt.rolesId.toString()) > -1) {
		        				output = output + "<option value='" +opt.rolesId+ "' selected>"+opt.displayName+"</option>";
		        			}else{
		        				output = output + "<option value='" +opt.rolesId+ "'>"+opt.displayName+"</option>";
		        			}
		        		}else{
		        			output = output + "<option value='" +opt.rolesId+ "'>"+opt.displayName+"</option>";
		        		}
		        		
		        		
				}
	        	$("#appraisalRoles").empty();
		        $("#appraisalRoles").append(output);
		        $('#appraisalRoles').trigger("chosen:updated");
	        },
	    });
  }
  
  this.employeeTypeList = function(employeeTypes){
	  $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getEmployeeTypesForPerformanceAppraisal", 
	        success: function(json) {
	        	var output = "";
	        	for (var i=0; i<json.length; i++)
				{
		        		var empType = json[i];
		        		if(employeeTypes != null && employeeTypes != '' && employeeTypes != 'undefined'){
		        			var employeeType = employeeTypes.split(",");
		        			if (employeeType.indexOf(empType.toString()) > -1) {
		        				output = output + "<option value='" +empType+ "' selected>"+empType+"</option>";
		        			}else{
		        				output = output + "<option value='" +empType+ "'>"+empType+"</option>";
		        			}
		        		}else{
		        			output = output + "<option value='" +empType+ "'>"+empType+"</option>";
		        		}
		        		
		        		
				}
	        	$("#appraisalEmpType").empty();
		        $("#appraisalEmpType").append(output);
		        $('#appraisalEmpType').trigger("chosen:updated");
	        },
	    });
  }
  
  
  this.feedbackCategoriesList = function(categoriesIds){
	  $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getFeedBackCategoriesListForPerformanceAppraisal", 
	        success: function(json) {
	        	var output = "";
	        	for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		if(categoriesIds != null && categoriesIds != '' && categoriesIds != 'undefined'){
		        			var categIds = categoriesIds.split(",");
		        			if (categIds.indexOf(opt.templateId.toString()) > -1) {
		        				output = output + "<option value='" +opt.templateId+ "' selected>"+opt.templateName+"</option>";
		        			}else{
		        				output = output + "<option value='" +opt.templateId+ "'>"+opt.templateName+"</option>";
		        			}
		        		}else{
		        			output = output + "<option value='" +opt.templateId+ "'>"+opt.templateName+"</option>";
		        		}
		        		
		        		
				}
	        	$("#appraisalCategories").empty();
		        $("#appraisalCategories").append(output);
		        $('#appraisalCategories').trigger("chosen:updated");
	        },
	    });
  }
  
}