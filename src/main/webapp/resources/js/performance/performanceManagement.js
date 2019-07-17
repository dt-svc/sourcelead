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
	
	
	
	
	this.validateCompetenceName = function(){
		var competenceName = document.getElementById("competenceName").value.trim(); 
		var competenceId = $("#competenceId").val();
		if(competenceId == 'undefined' || competenceId == null){
			competenceId = '';
		}
		
		if(competenceName != null && competenceName != '' && competenceName != 'undefined'){
			$.ajax({
		    	type: "GET",
		    	 url: sourceleadUrl+"/validateCompetenceName?competenceName="+competenceName+"&competenceId="+competenceId,
		        success: function(result) {
		        	if (result == 'invalid') {
		        		$("#savePerformanceCompetence").attr("disabled", "disabled");
		        		$('#competenceNameDiv').find('p.jquery_form_error_message').remove();
		        		$("input#competenceName").after( "<p class='jquery_form_error_message'>" + competenceName + " is Already Existed</span>");
						document.getElementById("competenceName").setAttribute( 'record-exist', 'yes');
						document.getElementById("competenceName").setAttribute( 'record-exist-errorMsg', competenceName +"  already existed");
		        	}else{
		        		$('input#competenceName').removeAttr("class record-exist record-exist-errormsg");
						$('input#competenceName').attr('class','valid form-control');
						$('#competenceNameDiv').find('p.jquery_form_error_message').remove();
						
		        		$("#savePerformanceCompetence").removeAttr("disabled")
		        	}
		        }
			 });
		}
	}
	
	
	this.savePerformanceCompetence  = function (){
		 var validationSettings = {
			        errorMessagePosition : 'element',
			       borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd' 
		         }; 
		 
		 if ($('#competence_form').validate(false, validationSettings)){ 
		        var formData = getFormData("competence_form");
		        $("#competenceLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		         $.ajax({
			    	type: "POST",
			    	data: formData,
			    	 url: sourceleadUrl+"/savePerformanceCompetence?",
			        success: function(response) {
			        	$("#competenceLoading").removeAttr('style');
			        	performanceManagement.performanceCompetenceList();
			        	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
	     	    		    showError(response.responseMessage);
	     	    		}
			        	else if (response.response === "AWKWARD") {
			        	    location.href = "/errorPage";
			        	} else if (response.response === "SUCCESS") {
			        		performanceManagement.closeCompetencePopup();
			        		$(".mask, #competenceModalTasktype").fadeOut();
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

	$('body').on('blur', '#competence_form', function() {
			$("#competence_form").showHelpOnFocus();
			$("#competence_form").validateOnBlur(false, validationSettings);  
		});
	
	
	this.savePerformanceRatingLevel  = function (){
		 var validationSettings = {
			        errorMessagePosition : 'element',
			       borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd' 
		         }; 
		 
		 if ($('#ratingLevel_form').validate(false, validationSettings)){ 
		        var formData = getFormData("ratingLevel_form");
		        $("#ratingScaleLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		         $.ajax({
			    	type: "POST",
			    	data: formData,
			    	 url: sourceleadUrl+"/savePerformanceRatingLevel?",
			        success: function(response) {
			        	$("#ratingScaleLoading").removeAttr('style');
			        	performanceManagement.performanceRatingScaleList();
			        	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
	     	    		    showError(response.responseMessage);
	     	    		}
			        	else if (response.response === "AWKWARD") {
			        	    location.href = "/errorPage";
			        	} else if (response.response === "SUCCESS") {
			        		performanceManagement.closeRatingScalePopup();
			        		$(".mask, #ratingScaleModalTasktype").fadeOut();
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

	$('body').on('blur', '#ratingLevel_form', function() {
			$("#ratingLevel_form").showHelpOnFocus();
			$("#ratingLevel_form").validateOnBlur(false, validationSettings);  
		});
	
	
	this.validateFeedbackTemplateName = function(){
		var templateName = document.getElementById("templateName").value.trim(); 
		var templateId = $("#templateId").val();
		if(templateId == 'undefined' || templateId == null){
			templateId = '';
		}
		
		if(templateName != null && templateName != '' && templateName != 'undefined'){
			$.ajax({
		    	type: "GET",
		    	 url: sourceleadUrl+"/validateFeedbackTemplateName?templateName="+templateName+"&templateId="+templateId,
		        success: function(result) {
		        	if (result == 'invalid') {
		        		$("#template_save_button").attr("disabled", "disabled");
		        		$('#templateNameDiv').find('p.jquery_form_error_message').remove();
		        		$("input#templateName").after( "<p class='jquery_form_error_message'>" + templateName + " is Already Existed</span>");
						document.getElementById("templateName").setAttribute( 'record-exist', 'yes');
						document.getElementById("templateName").setAttribute( 'record-exist-errorMsg', templateName +"  already existed");
		        	}else{
		        		$('input#templateName').removeAttr("class record-exist record-exist-errormsg");
						$('input#templateName').attr('class','valid form-control');
						$('#templateNameDiv').find('p.jquery_form_error_message').remove();
						
		        		$("#template_save_button").removeAttr("disabled")
		        	}
		        }
			 });
		}
	}	
	
	
	
	this.validateAppraisalTemplateName = function(){
		var appraisalName = document.getElementById("appraisalName").value.trim(); 
		var appraisalTemplateId = $("#appraisalTemplateId").val();
		if(appraisalTemplateId == 'undefined' || appraisalTemplateId == null){
			appraisalTemplateId = '';
		}
		
		if(appraisalName != null && appraisalName != '' && appraisalName != 'undefined'){
			$.ajax({
		    	type: "GET",
		    	 url: sourceleadUrl+"/validateAppraisalTemplateName?appraisalName="+appraisalName+"&appraisalTemplateId="+appraisalTemplateId,
		        success: function(result) {
		        	if (result == 'invalid') {
		        		$("#savePerformanceBudget").attr("disabled", "disabled");
		        		$('#appraisalNameDiv').find('p.jquery_form_error_message').remove();
		        		$("input#appraisalName").after( "<p class='jquery_form_error_message'>" + appraisalName + " is Already Existed</span>");
						document.getElementById("appraisalName").setAttribute( 'record-exist', 'yes');
						document.getElementById("appraisalName").setAttribute( 'record-exist-errorMsg', appraisalName +"  already existed");
		        	}else{
		        		$('input#appraisalName').removeAttr("class record-exist record-exist-errormsg");
						$('input#appraisalName').attr('class','valid form-control');
						$('#appraisalNameDiv').find('p.jquery_form_error_message').remove();
						
		        		$("#savePerformanceBudget").removeAttr("disabled")
		        	}
		        }
			 });
		}
	}
	
	
	
	this.validatePerformanceBadgeName = function(){
		var badgeName = document.getElementById("badgeName").value.trim(); 
		var badgeId = $("#badgeId").val();
		if(badgeId == 'undefined' || badgeId == null){
			badgeId = '';
		}
		
		if(badgeName != null && badgeName != '' && badgeName != 'undefined'){
			$.ajax({
		    	type: "GET",
		    	 url: sourceleadUrl+"/validatePerformanceBadgeName?badgeName="+badgeName+"&badgeId="+badgeId,
		        success: function(result) {
		        	if (result == 'invalid') {
		        		$("#savePerformanceBadge").attr("disabled", "disabled");
		        		$('#badgeNameDiv').find('p.jquery_form_error_message').remove();
		        		$("input#badgeName").after( "<p class='jquery_form_error_message'>" + badgeName + " is Already Existed</span>");
						document.getElementById("badgeName").setAttribute( 'record-exist', 'yes');
						document.getElementById("badgeName").setAttribute( 'record-exist-errorMsg', badgeName +"  already existed");
		        	}else{
		        		$('input#badgeName').removeAttr("class record-exist record-exist-errormsg");
						$('input#badgeName').attr('class','valid form-control');
						$('#badgeNameDiv').find('p.jquery_form_error_message').remove();
						
		        		$("#savePerformanceBadge").removeAttr("disabled")
		        	}
		        }
			 });
		}
	}
	

	this.validateRatingLevelName = function(){
		var ratingLevelName = document.getElementById("ratingLevelName").value.trim(); 
		var ratingLevelId = $("#ratingLevelId").val();
		if(ratingLevelId == 'undefined' || ratingLevelId == null){
			ratingLevelId = '';
		}
		
		if(ratingLevelName != null && ratingLevelName != '' && ratingLevelName != 'undefined'){
			$.ajax({
		    	type: "GET",
		    	 url: sourceleadUrl+"/validateRatingLevelName?ratingLevelName="+ratingLevelName+"&ratingLevelId="+ratingLevelId,
		        success: function(result) {
		        	if (result == 'invalid') {
		        		$("#savePerformanceRatingLevel").attr("disabled", "disabled");
		        		$('#ratingLevelNameDiv').find('p.jquery_form_error_message').remove();
		        		$("input#ratingLevelName").after( "<p class='jquery_form_error_message'>" + ratingLevelName + " is Already Existed</span>");
						document.getElementById("ratingLevelName").setAttribute( 'record-exist', 'yes');
						document.getElementById("ratingLevelName").setAttribute( 'record-exist-errorMsg', ratingLevelName +"  already existed");
		        	}else{
		        		$('input#ratingLevelName').removeAttr("class record-exist record-exist-errormsg");
						$('input#ratingLevelName').attr('class','valid form-control');
						$('#ratingLevelNameDiv').find('p.jquery_form_error_message').remove();
						
		        		$("#savePerformanceRatingLevel").removeAttr("disabled")
		        	}
		        }
			 });
		}
	}
	
	
	this.savePerformanceRatingScale  = function (){
		 var validationSettings = {
			        errorMessagePosition : 'element',
			       borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd' 
		         }; 
		 
		 if ($('#ratingScale_form').validate(false, validationSettings)){ 
			    var desc = $("#txtEditor").val($('.Editor-editor').html()); 
		        var formData = getFormData("ratingScale_form");
		        $("#ratingScaleLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		         $.ajax({
			    	type: "POST",
			    	data: formData,
			    	 url: sourceleadUrl+"/savePerformanceRatingScale?",
			        success: function(response) {
			        	$("#ratingScaleLoading").removeAttr('style');
			        	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
	     	    		    showError(response.responseMessage);
	     	    		}
			        	else if (response.response === "AWKWARD") {
			        	    location.href = "/errorPage";
			        	} else if (response.response === "SUCCESS") {
			        		performanceManagement.performanceRatingScaleList();
			        		
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

	$('body').on('blur', '#ratingScale_form', function() {
			$("#ratingScale_form").showHelpOnFocus();
			$("#ratingScale_form").validateOnBlur(false, validationSettings);  
		});
	
	

	this.validateRatingScaleName = function(){
		var ratingScaleName = document.getElementById("ratingScaleName").value.trim(); 
		var ratingScaleId = $("#ratingScaleId").val();
		if(ratingScaleId == 'undefined' || ratingScaleId == null){
			ratingScaleId = '';
		}
		
		if(ratingScaleName != null && ratingScaleName != '' && ratingScaleName != 'undefined'){
			$.ajax({
		    	type: "GET",
		    	 url: sourceleadUrl+"/validateRatingScaleName?ratingScaleName="+ratingScaleName+"&ratingScaleId="+ratingScaleId,
		        success: function(result) {
		        	if (result == 'invalid') {
		        		$("#savePerformanceRatingScale").attr("disabled", "disabled");
		        		$('#ratingScaleNameDiv').find('p.jquery_form_error_message').remove();
		        		$("input#ratingScaleName").after( "<p class='jquery_form_error_message'>" + ratingScaleName + " is Already Existed</span>");
						document.getElementById("ratingScaleName").setAttribute( 'record-exist', 'yes');
						document.getElementById("ratingScaleName").setAttribute( 'record-exist-errorMsg', ratingScaleName +"  already existed");
		        	}else{
		        		$('input#ratingScaleName').removeAttr("class record-exist record-exist-errormsg");
						$('input#ratingScaleName').attr('class','valid form-control');
						$('#ratingScaleNameDiv').find('p.jquery_form_error_message').remove();
						
		        		$("#savePerformanceRatingScale").removeAttr("disabled")
		        	}
		        }
			 });
		}
	}
	
	
	this.validateRatingShortCode = function(){
		var ratingShortCode = document.getElementById("ratingShortCode").value.trim(); 
		var ratingScaleId = $("#ratingScaleId").val();
		if(ratingScaleId == 'undefined' || ratingScaleId == null){
			ratingScaleId = '';
		}
		
		if(ratingShortCode != null && ratingShortCode != '' && ratingShortCode != 'undefined'){
			$.ajax({
		    	type: "GET",
		    	 url: sourceleadUrl+"/validateRatingShortCode?ratingShortCode="+ratingShortCode+"&ratingScaleId="+ratingScaleId,
		        success: function(result) {
		        	if (result == 'invalid') {
		        		$("#savePerformanceRatingScale").attr("disabled", "disabled");
		        		$('#ratingShortCodeDiv').find('p.jquery_form_error_message').remove();
		        		$("input#ratingShortCode").after( "<p class='jquery_form_error_message'>" + ratingShortCode + " is Already Existed</span>");
						document.getElementById("ratingShortCode").setAttribute( 'record-exist', 'yes');
						document.getElementById("ratingShortCode").setAttribute( 'record-exist-errorMsg', ratingShortCode +"  already existed");
		        	}else{
		        		$('input#ratingShortCode').removeAttr("class record-exist record-exist-errormsg");
						$('input#ratingShortCode').attr('class','valid form-control');
						$('#ratingShortCodeDiv').find('p.jquery_form_error_message').remove();
						
		        		$("#savePerformanceRatingScale").removeAttr("disabled")
		        	}
		        }
			 });
		}
	}
	
	
	
	 this.validateRatingScaleValue = function(){
			var ratingScaleValue = $('#ratingScaleValue').val();
			
			 if(ratingScaleValue > 100 || ratingScaleValue < 0){
				 if(ratingScaleValue < 0){
					    $('#ratingScaleValueId').find('p.jquery_form_error_message').remove(); 
						$('input#ratingScaleValueValidation').attr('class','error signin_fild');
						$("input#ratingScaleValueValidation").after("<p class='jquery_form_error_message'>Scale value should not in negitive</p>");
						$('input#ratingScaleValueValidation').attr('record-exist','yes');
						$('input#ratingScaleValueValidation').attr('record-exist-errorMsg','Scale value should not in negitive'); 
				 }else{
					    $('#ratingScaleValueId').find('p.jquery_form_error_message').remove(); 
						$('input#ratingScaleValueValidation').attr('class','error signin_fild');
						$("input#ratingScaleValueValidation").after("<p class='jquery_form_error_message'>Scale value should not more than 100</p>");
						$('input#ratingScaleValueValidation').attr('record-exist','yes');
						$('input#ratingScaleValueValidation').attr('record-exist-errorMsg','Scale value should not more than 100'); 
				 }
			 }else{
				 
			     $('#ratingScaleValueId').find('p.jquery_form_error_message').remove(); 
		    	 $('input#ratingScaleValueValidation').removeAttr( "class record-exist record-exist-errormsg");
				 $('#ratingScaleValueId').find('p.jquery_form_error_message').remove();
			 }
		}
	 
	
	
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
    
    this.addNewAppraisalCycle = function(templateUUId){
    	$.ajax({
  		  type: "GET",
  		    url: sourceleadUrl+"/addNewAppraisalCycle",
  		     success :function(response) {
  		    	    $("#performanceManagementDiv").empty();  
  		    	    $("#performanceManagementDiv").append(response);
  		    	    
  		    	    if(templateUUId != null && templateUUId != '' && templateUUId != 'undefined'){
  		    	    	 performanceManagement.editAppraisalCycle(templateUUId);
  		    	    }
  		    	   
  		    	   if(templateUUId == null || templateUUId == '' || templateUUId == 'undefined'){
  		    		performanceManagement.departmentsList();
  		    		performanceManagement.rolesList();
  		    		performanceManagement.employeeTypeList();
  		    		performanceManagement.feedbackCategoriesList();
  		    		performanceManagement.appraisalCompetenciesList();
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
    
    
    this.viewAppraisalTemplate = function(templateUUId){
    	$.ajax({
  		  type: "GET",
  		    url: sourceleadUrl+"/getPerformanceAppraisalTemplateDetails",
  		     success :function(response) {
  		    	    $("#performanceManagementDiv").empty();  
  		    	    $("#performanceManagementDiv").append(response);
  		    	    performanceManagement.appraisalTemplateDetails(templateUUId);
  				},
  			}); 
    }
    
    this.assignAppraisalCycle = function(uuid){
    	$.ajax({
  		  type: "GET",
  		    url: sourceleadUrl+"/getPerformanceAppraisalAssignToList?uuid="+uuid,
  		     success :function(response) {
  		    	    $("#performanceManagementDiv").empty();  
  		    	    $("#performanceManagementDiv").append(response);
  		    	    getAppraisalAssignToList(uuid);
  				},
  			}); 
    }
    
    this.templateAssignedAssociates = function(uuid){
    	$.ajax({
    		  type: "GET",
    		    url: sourceleadUrl+"/getTemplateAssignedAssociatesList?uuid="+uuid,
    		     success :function(response) {
    		    	    $("#performanceManagementDiv").empty();  
    		    	    $("#performanceManagementDiv").append(response);
    		    	    getAppraisalAssignedAssociatesList(uuid);
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
   
   
   this.addNewPerformanceCompetence = function(){
	   $.ajax({
 		  type: "GET",
 		    url: sourceleadUrl+"/addNewPerformanceCompetence",
 		     success :function(response) {
 		    	    $("#performanceManagementDiv").empty();  
 		    	    $("#performanceManagementDiv").append(response);
 		    	    performanceManagement.feedbackCategoriesList();
 				},
 			}); 
   }
   
   
   this.performanceCompetenceList = function(){
   	$.ajax({
   		  type: "GET",
   		    url: sourceleadUrl+"/performanceCompetenceList",
   		     success :function(response) {
   		    	    $("#performanceManagementDiv").empty();  
   		    	    $("#performanceManagementDiv").append(response);
   		    	    getCompetenceList();
   				},
   			}); 
   }
   
   this.performanceRatingScaleList = function(){
	   	$.ajax({
	   		  type: "GET",
	   		    url: sourceleadUrl+"/performanceRatingScaleList",
	   		     success :function(response) {
	   		    	    $("#performanceManagementDiv").empty();  
	   		    	    $("#performanceManagementDiv").append(response);
	   		    	    getRatingScaleList();
	   				},
	   			}); 
	   }
   
   this.addNewRatingScale = function(ratingScaleId){
	   	$.ajax({
	   		  type: "GET",
	   		    url: sourceleadUrl+"/addNewRatingScale",
	   		     success :function(response) {
	   		    	    $("#performanceManagementDiv").empty();  
	   		    	    $("#performanceManagementDiv").append(response);
	   		    	    
	   		    	   if(ratingScaleId != null && ratingScaleId != 'undefined' && ratingScaleId != ''){
 		    	    	 performanceManagement.editPerformanceRatingScale(ratingScaleId);
 		    	       }
	   		    	   
		   		    	 if(ratingScaleId == null || ratingScaleId == 'undefined' || ratingScaleId == ''){
		   		    		performanceManagement.getPerformanceScaleLevels();  
		   		    	 }
 		    	    
 		    	       
	   		    	   performanceManagement.scaleDescriptionEditor();
	   				},
	   			}); 
	   }
   
   this.scaleDescriptionEditor = function(){
	    $("#txtEditor").Editor();
		$("#txtEditor").find(".Editor-editor").data-validation('validate_length length2-4000');
		$("#txtEditor").find(".Editor-editor").data-validation-optional('rule-required',true);
   }
   
   
   
   this.editPerformanceRatingScale = function(ratingScaleId){
		  
	 	  $("#ratingListLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.ajax({
				  type: "GET",
				    url: sourceleadUrl+"/getPerformanceRatingScaleDetailsById?ratingScaleId="+ratingScaleId,
				     success :function(response) {
				    	 
				    		if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
		     	    		    showError(response.responseMessage);
		     	    		    $("#ratingListLoading").removeAttr('style');
		     	    		}
				        	else if (response.response === "AWKWARD") {
				        	    location.href = "/errorPage";
				        	} else if (response.response === "SUCCESS") {
				        		$("#ratingScaleId").val(ratingScaleId);
				        		$("#ratingScaleName").val(response.object.ratingName);
				        		$("#ratingShortCode").val(response.object.shortCode);
				        		$("#ratingScaleValue").val(response.object.ratingScaleValue);
				        		
				        		
				        		 if(response.object.description != null 
				        				 && response.object.description != ''
				        					 && response.object.description != 'undefined'){
				        			 $("textarea[name=description]").val(response.object.description);
						        	 $(".Editor-editor").html(response.object.description);
				        		 }
				        		 
				        		performanceManagement.getPerformanceScaleLevels(response.object.levelId);  
				        		 
				        		$("#ratingListLoading").removeAttr('style');
				        	} else if (response.indexOf('Sign In') !== -1) {
				        		location.replace(sourceleadUrl);
				        	} else {
				        	    showError("Operation failed, please try again");
				        	}
				    	  
						},
					});
	  }

   
   
   this.getPerformanceScaleLevels = function(levelId){
	   if(levelId == null || levelId == 'undefined' || levelId == ''){
		   levelId = 0;
	   }
	   
		  $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getPerformanceScaleLevels?ratingLevelId="+levelId, 
		        success: function(json) {
		        	var output = "";
		        	
		        	output = output + "<option value=''>--Select Level--</option>";
		        	for (var i=0; i<json.length; i++)
					{
		        		var opt = json[i];
		        		
		        		if(levelId != null && levelId != 'undefined' && levelId != '' && levelId == opt.id){
		        			output = output + "<option value='" +opt.id+ "' selected>"+opt.name+"</option>";
		        		}else{
		        			output = output + "<option value='" +opt.id+ "'>"+opt.name+"</option>";
		        		}
			        		
					}
		        	$("#performanceScaleLevel").empty();
			        $("#performanceScaleLevel").append(output);
			        $('#performanceScaleLevel').chosen();
		        },
		    });
	  }
   
   
   this.tagPerformanceCompetence = function(tagCompetenceId){
	   $.ajax({
 		  type: "GET",
 		    url: sourceleadUrl+"/tagPerformanceCompetence",
 		     success :function(response) {
 		    	    $("#performanceManagementDiv").empty();  
 		    	    $("#performanceManagementDiv").append(response);
 		    	    
 		    	    if(tagCompetenceId != null && tagCompetenceId != 'undefined' && tagCompetenceId != ''){
 		    	    	 performanceManagement.editTagPerformanceCompetence(tagCompetenceId);
 		    	    }
 		    	    
 		    	   if(tagCompetenceId == null || tagCompetenceId == 'undefined' || tagCompetenceId != ''){
 		    		    performanceManagement.competenceRolesList();
 	 		    	    performanceManagement.performanceCompetenciesList();
 		    	   }
 		    	   
 				},
 			}); 
   }
   
   
   this.editTagPerformanceCompetence = function(tagCompetenceId){
		  
	 	  $("#tagCompetenceLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.ajax({
				  type: "GET",
				    url: sourceleadUrl+"/getTagPerformanceCompetenceDetailsById?tagCompetenceId="+tagCompetenceId,
				     success :function(response) {
				    	 
				    		if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
		     	    		    showError(response.responseMessage);
		     	    		    $("#tagCompetenceLoading").removeAttr('style');
		     	    		}
				        	else if (response.response === "AWKWARD") {
				        	    location.href = "/errorPage";
				        	} else if (response.response === "SUCCESS") {
				        		$("#tagCompetenceId").val(response.object.tagCompetenceId);
				        		 performanceManagement.competenceRolesList(response.object.competenceRole);
				        		 var weightageMap = new Map();
				        		 if(response.object.jsonCompetenceString != null 
				        				 && response.object.jsonCompetenceString != '' 
				        					 && response.object.jsonCompetenceString != 'undefined'){
				        			var data = eval(response.object.jsonCompetenceString);
					        		 if (data) {
					        			  $.each( data, function( k, v ) {
					        				  weightageMap.set(v['competenceId'],v['weightage'])
					        			  });
					        		 }
				        		}
				        		 
				        		 performanceManagement.performanceCompetenciesList(response.object.competenceIds,weightageMap);
				        		 
				        		 
				        		 $("#tagCompetenceLoading").removeAttr('style');
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
  
  this.addNewCompetence = function() {
 	  $(".mask, #competenceModalTasktype").fadeIn();
   }
  
  this.addRatingScaleLevels = function() {
 	  $(".mask, #ratingScaleModalTasktype").fadeIn();
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
   
   this.closeCompetencePopup = function(){
	      $(".mask, #competenceModalTasktype").fadeOut();
	 	  $('#competenceNameDiv').find('p.jquery_form_error_message').remove();
	 	  $('#competenceDescriptionDiv').find('p.jquery_form_error_message').remove();
	 	  document.getElementById("competence_form").reset();

	 		
   }
   
   this.closeRatingScalePopup = function(){
	      $(".mask, #ratingScaleModalTasktype").fadeOut();
	      $("#ratingLevelId").val('');
	      $("#ratingLevelName").val('');
 	 	  $("textarea[id='ratingLevelDescription']").text('');
	 	  $('#ratingLevelNameDiv').find('p.jquery_form_error_message').remove();
	 	  $('#ratingLevelDescriptionDiv').find('p.jquery_form_error_message').remove();
	 	  document.getElementById("ratingLevel_form").reset();
	 	  $("#savePerformanceRatingLevel").removeAttr("disabled");
	 	  
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
   
   
   
   this.editCompetence = function(competenceId){
	   performanceManagement.addNewCompetence();
 	  $("#competenceLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
 		$.ajax({
 			  type: "GET",
 			    url: sourceleadUrl+"/getPerformanceCompetenceDetailsById?competenceId="+competenceId,
 			     success :function(response) {
 			    	 
 			    		if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
 	     	    		    showError(response.responseMessage);
 	     	    		    $("#badgeDetailsLoading").removeAttr('style');
 	     	    		}
 			        	else if (response.response === "AWKWARD") {
 			        	    location.href = "/errorPage";
 			        	} else if (response.response === "SUCCESS") {
 			        		 $("#competenceId").val(response.object.competenceId);
 			        		 $("#competenceName").val(response.object.name);
 			        		 $("textarea[id='competenceDescription']").text(response.object.description);
 			        		 $("#competenceLoading").removeAttr('style');
 			        	} else if (response.indexOf('Sign In') !== -1) {
 			        		location.replace(sourceleadUrl);
 			        	} else {
 			        	    showError("Operation failed, please try again");
 			        	}
 			    	  
 					},
 				}); 
   }
   
   
   this.editPerformanceRatingLevel = function(ratingLevelId){
	   performanceManagement.addRatingScaleLevels();
 	  $("#ratingScaleLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
 		$.ajax({
 			  type: "GET",
 			    url: sourceleadUrl+"/getPerformanceLevelDetailsById?ratingLevelId="+ratingLevelId,
 			     success :function(response) {
 			    	 
 			    		if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
 	     	    		    showError(response.responseMessage);
 	     	    		    $("#ratingScaleLoading").removeAttr('style');
 	     	    		}
 			        	else if (response.response === "AWKWARD") {
 			        	    location.href = "/errorPage";
 			        	} else if (response.response === "SUCCESS") {
 			        		 $("#ratingLevelId").val(ratingLevelId);
 			        		 $("#ratingLevelName").val(response.object.name);
 			        		 $("textarea[id='ratingLevelDescription']").text(response.object.description);
 			        		 $("#ratingScaleLoading").removeAttr('style');
 			        	} else if (response.indexOf('Sign In') !== -1) {
 			        		location.replace(sourceleadUrl);
 			        	} else {
 			        	    showError("Operation failed, please try again");
 			        	}
 			    	  
 					},
 				}); 
   }
   
   
   this.deletePerformanceRatingLevel = function(ratingLevelId){
		$.ajax({
			  type: "POST",
			    url: sourceleadUrl+"/deletePerformanceRatingLevel?ratingLevelId="+ratingLevelId,
			     success :function(response) {
			    	 
			    	 if (response && (response === "INVALID_DATA" || response === "INVALID_USER")) {
	     	    		    showError("Invalid User");
	     	    		}
			        	else if (response === "AWKWARD") {
			        	    location.href = "/errorPage";
			        	} else if (response === "SUCCESS") {
			        		performanceManagement.performanceRatingScaleList();
			        	} else if (response.indexOf('Sign In') !== -1) {
			        		location.replace(sourceleadUrl);
			        	} else {
			        	    showError("Operation failed, please try again");
			        	}
			    	  
					},
				});
  }
   
  
   this.deletePerformanceRatingScale = function(ratingScaleId){
		$.ajax({
			  type: "POST",
			    url: sourceleadUrl+"/deletePerformanceRatingScale?ratingScaleId="+ratingScaleId,
			     success :function(response) {
			    	 
			    	 if (response && (response === "INVALID_DATA" || response === "INVALID_USER")) {
	     	    		    showError("Invalid User");
	     	    		}
			        	else if (response === "AWKWARD") {
			        	    location.href = "/errorPage";
			        	} else if (response === "SUCCESS") {
			        		performanceManagement.performanceRatingScaleList();
			        	} else if (response.indexOf('Sign In') !== -1) {
			        		location.replace(sourceleadUrl);
			        	} else {
			        	    showError("Operation failed, please try again");
			        	}
			    	  
					},
				});
 }
  
   
   
   
   this.deleteCompetence = function(competenceId){
		$.ajax({
			  type: "POST",
			    url: sourceleadUrl+"/deletePerformanceCompetenceById?competenceId="+competenceId,
			     success :function(response) {
			    	 
			    	 if (response && (response === "INVALID_DATA" || response === "INVALID_USER")) {
	     	    		    showError("Invalid User");
	     	    		}
			        	else if (response === "AWKWARD") {
			        	    location.href = "/errorPage";
			        	} else if (response === "SUCCESS") {
			        		performanceManagement.performanceCompetenceList();
			        	} else if (response.indexOf('Sign In') !== -1) {
			        		location.replace(sourceleadUrl);
			        	} else {
			        	    showError("Operation failed, please try again");
			        	}
			    	  
					},
				});
   }
   
   
   this.deleteTagCompetence = function(tagCompetenceId){
		$.ajax({
			  type: "POST",
			    url: sourceleadUrl+"/deletePerformanceTagCompetenceById?tagCompetenceId="+tagCompetenceId,
			     success :function(response) {
			    	 
			    	 if (response && (response === "INVALID_DATA" || response === "INVALID_USER")) {
	     	    		    showError("Invalid User");
	     	    		}
			        	else if (response === "AWKWARD") {
			        	    location.href = "/errorPage";
			        	} else if (response === "SUCCESS") {
			        		performanceManagement.performanceCompetenceList();
			        	} else if (response.indexOf('Sign In') !== -1) {
			        		location.replace(sourceleadUrl);
			        	} else {
			        	    showError("Operation failed, please try again");
			        	}
			    	  
					},
				});
  }
   
  this.appraisalStartDateAndEndDatePickers = function(){
	  var finalDateFormat = getJSDateFormat(locationDateFormat);
	   $(function() {
			  $('#appraisalStartDate').datepicker({
			      dateFormat: finalDateFormat,
			      changeMonth: true,
			  	  changeYear: true, 
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
			  	  onSelect : function(selected) {
			  		var dt = $(this).datepicker('getDate', '-1d');
					dt.setDate(dt.getDate() - 1);
					$("#appraisalStartDate").datepicker("option", "maxDate", dt);
					performanceManagement.validAppraisalDates();
			 	  }
			  });
		  });
	   
  }
  
  this.getProcessPeriodCalendars = function(){
	  
	    var appraisalStartDate = $('#appraisalStartDate').val();
		var appraisalEndDate =   $('#appraisalEndDate').val();
		var finalDateFormat = getJSDateFormat(locationDateFormat);
		$(function() {
			  $('#appraisalProcessStartDate').datepicker({
			      dateFormat: finalDateFormat,
			      changeMonth: true,
			  	  changeYear: true, 
			      minDate : appraisalStartDate,
				  maxDate : appraisalEndDate,
			  	  onSelect : function(selected) {
			  		var dt = $(this).datepicker('getDate', '+1d');
					dt.setDate(dt.getDate() + 1);
					$("#appraisalProcessEndDate").datepicker("option", "minDate", dt);
					performanceManagement.validAppraisalDates();
					$("#appraisalProcessEndDate").removeAttr("disabled");
			 	  }
			  });
		  });
		
		
		
		$(function() {
			  $('#appraisalProcessEndDate').datepicker({
			      dateFormat: finalDateFormat,
			      changeMonth: true,
			  	  changeYear: true, 
			  	  minDate : appraisalStartDate,
				  maxDate : appraisalEndDate,
			  	  onSelect : function(selected) {
			  		var dt = $(this).datepicker('getDate', '-1d');
					dt.setDate(dt.getDate() - 1);
					$("#appraisalProcessStartDate").datepicker("option", "maxDate", dt);
					performanceManagement.validAppraisalDates();
			 	  }
			  });
	  });
	   
  }   
	this.getAppraisalTypesCalendars = function(){  
		
		    var appraisalProcessStartDate = $('#appraisalProcessStartDate').val();
			var appraisalProcessEndDate =   $('#appraisalProcessEndDate').val();
			var finalDateFormat = getJSDateFormat(locationDateFormat);
			
	    $(function() {
			  $('#selfAppraisalStartDate').datepicker({
			      dateFormat: finalDateFormat,
			      changeMonth: true,
			  	  changeYear: true, 
			  	  minDate : appraisalProcessStartDate,
				  maxDate : appraisalProcessEndDate,
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
					  	  minDate : appraisalProcessStartDate,
						  maxDate : appraisalProcessEndDate,
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
			  	  minDate : appraisalProcessStartDate,
				  maxDate : appraisalProcessEndDate,
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
				  	  minDate : appraisalProcessStartDate,
					  maxDate : appraisalProcessEndDate,
				  	  onSelect : function(selected) {
				  		var dt = $(this).datepicker('getDate', '-1d');
						dt.setDate(dt.getDate() - 1);
						$("#appraisalApprovingStartDate").datepicker("option", "maxDate", dt);
						performanceManagement.validAppraisalDates();
				 	  }
				  });
	  });
		
		
		$(function() {
			  $('#peerReviewStartDate').datepicker({
			      dateFormat: finalDateFormat,
			      changeMonth: true,
			  	  changeYear: true, 
			  	  minDate : appraisalProcessStartDate,
				  maxDate : appraisalProcessEndDate,
			  	  onSelect : function(selected) {
			  		var dt = $(this).datepicker('getDate', '+1d');
					dt.setDate(dt.getDate() + 1);
					$("#peerReviewEndDate").datepicker("option", "minDate", dt);
					performanceManagement.validAppraisalDates();
					$("#peerReviewEndDate").removeAttr("disabled");
			 	  }
			  });
		  });
		
		
		
		$(function() {
			  $('#peerReviewEndDate').datepicker({
			      dateFormat: finalDateFormat,
			      changeMonth: true,
			  	  changeYear: true, 
			  	  minDate : appraisalProcessStartDate,
				  maxDate : appraisalProcessEndDate,
			  	  onSelect : function(selected) {
			  		var dt = $(this).datepicker('getDate', '-1d');
					dt.setDate(dt.getDate() - 1);
					$("#peerReviewStartDate").datepicker("option", "maxDate", dt);
					performanceManagement.validAppraisalDates();
			 	  }
			  });
	  });
		
		
	$(function() {
			  $('#resourceManagerReviewStartDate').datepicker({
			      dateFormat: finalDateFormat,
			      changeMonth: true,
			  	  changeYear: true, 
			  	  minDate : appraisalProcessStartDate,
				  maxDate : appraisalProcessEndDate,
			  	  onSelect : function(selected) {
			  		var dt = $(this).datepicker('getDate', '+1d');
					dt.setDate(dt.getDate() + 1);
					$("#resourceManagerReviewEndDate").datepicker("option", "minDate", dt);
					performanceManagement.validAppraisalDates();
					$("#resourceManagerReviewEndDate").removeAttr("disabled");
			 	  }
			  });
		  });
		
		
		
	$(function() {
			  $('#resourceManagerReviewEndDate').datepicker({
			      dateFormat: finalDateFormat,
			      changeMonth: true,
			  	  changeYear: true, 
			  	  minDate : appraisalProcessStartDate,
				  maxDate : appraisalProcessEndDate,
			  	  onSelect : function(selected) {
			  		var dt = $(this).datepicker('getDate', '-1d');
					dt.setDate(dt.getDate() - 1);
					$("#resourceManagerReviewStartDate").datepicker("option", "maxDate", dt);
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
		 
		 
	 var appraisalProcessStartDate = document.getElementById("appraisalProcessStartDate").value;
		 if(appraisalProcessStartDate){
			 $('#appraisalProcessStartDateDiv').find('p.jquery_form_error_message').remove(); 
		 }
		 
	 var appraisalProcessEndDate = document.getElementById("appraisalProcessEndDate").value;
		 if(appraisalProcessEndDate){
			 $('#appraisalProcessEndDateDiv').find('p.jquery_form_error_message').remove(); 
		 }
		 
	 var peerReviewStartDate = document.getElementById("peerReviewStartDate").value;
		 if(peerReviewStartDate){
			 $('#peerReviewStartDateDiv').find('p.jquery_form_error_message').remove(); 
		 }
		 
	 var peerReviewEndDate = document.getElementById("peerReviewEndDate").value;
		 if(peerReviewEndDate){
			 $('#peerReviewEndDateDiv').find('p.jquery_form_error_message').remove(); 
		 }
		 
	 var resourceManagerReviewStartDate = document.getElementById("resourceManagerReviewStartDate").value;
		 if(resourceManagerReviewStartDate){
			 $('#resourceManagerReviewStartDateDiv').find('p.jquery_form_error_message').remove(); 
		 }
		 
	 var resourceManagerReviewEndDate = document.getElementById("resourceManagerReviewEndDate").value;
		 if(resourceManagerReviewEndDate){
			 $('#resourceManagerReviewEndDateDiv').find('p.jquery_form_error_message').remove(); 
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
						
						var selfRevText = "";
						var peerRevText = "";
						var reportingMngText = "";
						var resMngText = "";
						
						var isSelfChecked = $("#isSelfAppraisalRequired").is(":checked");
						if(isSelfChecked){
							selfRevText = '<div class="categorySubCta" id="selfCategoryPercentDiv'+$(this).val()+'"><span>Self Appraisal<i class="fa fa-close" onclick="performanceManagement.closeSelectedCategoryDiv('+$(this).val()+',\'self\')"></i></span><input type="text" onblur="performanceManagement.validatePercentage('+$(this).val()+');performanceManagement.validateCategoryPercentageTotalSumValue();" id="selfCategoryPercent'+$(this).val()+'" class="form-control" field-name="Percentage" placeholder="Percentage" data-validation="validate_int required"></div>'
						}
						
						var isPeerChecked = $("#isPeerReviewRequired").is(":checked");
						if(isPeerChecked){
							peerRevText = '<div class="categorySubCta" id="peerCategoryPercentDiv'+$(this).val()+'"><span>Peer Review<i class="fa fa-close" onclick="performanceManagement.closeSelectedCategoryDiv('+$(this).val()+',\'peer\')"></i></span><input type="text" onblur="performanceManagement.validatePercentage('+$(this).val()+');performanceManagement.validateCategoryPercentageTotalSumValue();" id="peerCategoryPercent'+$(this).val()+'" class="form-control" field-name="Percentage" placeholder="Percentage" data-validation="validate_int required"></div>'
						}
						
						var isRepMngChecked = $("#isReportingManagerAppraisalRequired").is(":checked");
						if(isRepMngChecked){
							reportingMngText = '<div class="categorySubCta" id="reportingMngCategoryPercentDiv'+$(this).val()+'"><span>Reporting Manager<i class="fa fa-close" onclick="performanceManagement.closeSelectedCategoryDiv('+$(this).val()+',\'reportingManager\')"></i></span><input type="text" onblur="performanceManagement.validatePercentage('+$(this).val()+');performanceManagement.validateCategoryPercentageTotalSumValue();" id="reportingMngCategoryPercent'+$(this).val()+'" class="form-control" field-name="Percentage" placeholder="Percentage" data-validation="validate_int required"></div>'
						}
						
						var isResMngChecked = $("#isResourceManagerAppraisalRequired").is(":checked");
						if(isResMngChecked){
							resMngText = '<div class="categorySubCta" id="resourceMngcategoryPercentDiv'+$(this).val()+'"><span>Resource Manager<i class="fa fa-close" onclick="performanceManagement.closeSelectedCategoryDiv('+$(this).val()+',\'resourceManager\')"></i></span><input type="text" onblur="performanceManagement.validatePercentage('+$(this).val()+');performanceManagement.validateCategoryPercentageTotalSumValue();" id="resourceMngcategoryPercent'+$(this).val()+'" class="form-control" field-name="Percentage" placeholder="Percentage" data-validation="validate_int required"></div>'
						}
						 
						
						var text="<div class='multiTemplates positionRelative categorySub' id="+$(this).val()+" data-template_id=" + $(this).val() + ">";
						text += "<i class='fa fa-close close_multi_job' onclick='performanceManagement.closeSelectedJobDiv(this)'></i>";
						text += "<div class='multiJobTitle categorySubHead clearfix'>";
						text += "" + $(this).text() + "";
						text += "<span class='pull-right mr'><input type='text' onblur='performanceManagement.validatePercentage("+$(this).val()+");performanceManagement.validatePercentageTotalSumValue();' id='percent"+$(this).val()+"' class='form-control' field-name='Percentage' placeholder='Percentage' data-validation='validate_int required'>";
						text += "</span>";
						text += "</div>";
						text += "<div class='clearfix'>";
						text += selfRevText
						text += peerRevText
						text += reportingMngText
						text += resMngText
						text += "</div>";
						text += "<div class='clearfix mxrateerror' id='categoryPercentageDiv"+$(this).val()+"'>";
						text += "<input type='hidden' name='categoryPercent"+$(this).val()+"' id='percentageCatgValidation"+$(this).val()+"'>";
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
  
  this.closeSelectedCategoryDiv = function(templateId,categoryType){
	     if(categoryType == 'self'){
	    	 $("#selfCategoryPercentDiv"+templateId).remove();
	     }else if(categoryType == 'peer'){
	    	 $("#peerCategoryPercentDiv"+templateId).remove();
	     }else if(categoryType == 'reportingManager'){
	    	 $("#reportingMngCategoryPercentDiv"+templateId).remove();
	     }else if(categoryType == 'resourceManager'){
	    	 $("#resourceMngcategoryPercentDiv"+templateId).remove();
	     }
		 performanceManagement.validateCategoryPercentageTotalSumValue();
  }
  
 
  
  
  this.validateCategoryPercentageTotalSumValue = function(){
	  
	 
	  
	  $(".multiTemplates").each(function() {	
		  
		  var percentage = 0;
		  var selfPercentage = 0;
		  var peerPercentage = 0;
		  var reportingMngPercentage = 0;
		  var resourceMngPercentage = 0;
		  
		   percentage = Number($("#percent"+$(this).data("template_id")).val());
		   
		   var selfCatg = $("#selfCategoryPercent"+$(this).data("template_id")).val();
		   if(selfCatg != null && selfCatg != '' && selfCatg != 'undefined'){
			   selfPercentage = $("#selfCategoryPercent"+$(this).data("template_id")).val();
		   }
		   
		   var peerCatg = $("#peerCategoryPercent"+$(this).data("template_id")).val();;
		   if(peerCatg != null && peerCatg != '' && peerCatg != 'undefined'){
			   peerPercentage = $("#peerCategoryPercent"+$(this).data("template_id")).val()
		   }
		   
		   var reportingMngCatg = $("#reportingMngCategoryPercent"+$(this).data("template_id")).val();
		   if(reportingMngCatg != null && reportingMngCatg != '' && reportingMngCatg != 'undefined'){
			   reportingMngPercentage = $("#reportingMngCategoryPercent"+$(this).data("template_id")).val();
		   }
		   
		   var resourceMngCatg = $("#resourceMngcategoryPercent"+$(this).data("template_id")).val();
		   if(resourceMngCatg != null && resourceMngCatg != '' && resourceMngCatg != 'undefined'){
			   resourceMngPercentage = $("#resourceMngcategoryPercent"+$(this).data("template_id")).val();
		   }
		  
		   var totalPercentage = Number(selfPercentage)+Number(peerPercentage)+Number(reportingMngPercentage)+Number(resourceMngPercentage);
		   
		  
		  if(totalPercentage > percentage){
			    $('#categoryPercentageDiv'+$(this).data("template_id")).find('p.jquery_form_error_message').remove(); 
			    $('#percentageCatgValidation'+$(this).data("template_id")).attr('class','error signin_fild');
			    $('#percentageCatgValidation'+$(this).data("template_id")).after("<p class='jquery_form_error_message'>Total percentage should not more than given category Percentage</p>");
			    $('#percentageCatgValidation'+$(this).data("template_id")).attr('record-exist','yes');
			    $('#percentageCatgValidation'+$(this).data("template_id")).attr('record-exist-errorMsg','Total percentage should not more than given category Percentage'); 
			    $("#savePerformanceBudget").attr("disabled", "disabled");
		  }else if(totalPercentage < percentage){
			    $('#categoryPercentageDiv'+$(this).data("template_id")).find('p.jquery_form_error_message').remove(); 
			    $('#percentageCatgValidation'+$(this).data("template_id")).attr('class','error signin_fild');
			    $('#percentageCatgValidation'+$(this).data("template_id")).after("<p class='jquery_form_error_message'>Total percentage should not less than given category Percentage</p>");
			    $('#percentageCatgValidation'+$(this).data("template_id")).attr('record-exist','yes');
			    $('#percentageCatgValidation'+$(this).data("template_id")).attr('record-exist-errorMsg','Total percentage should not less than given category Percentage'); 
			    $("#savePerformanceBudget").attr("disabled", "disabled");
		  }else{
			     $('#categoryPercentageDiv'+$(this).data("template_id")).find('p.jquery_form_error_message').remove(); 
				 $('input#percentageCatgValidation'+$(this).data("template_id")).removeAttr( "class record-exist record-exist-errormsg");
				 $('#categoryPercentageDiv'+$(this).data("template_id")).find('p.jquery_form_error_message').remove();
				 $("#savePerformanceBudget").removeAttr("disabled");
		  }
		  
	  });
  }
  
  
  this.closeSelectedJobDiv = function(ele) {
		$(ele).parent().remove();
		var id = $(ele).parent().data("template_id");
		$('#appraisalCategories').find("option[value='" + id + "']").prop("selected",false).end().trigger('chosen:updated');
		performanceManagement.validatePercentageTotalSumValue();
	}
  
  
  
this.appraisalCompetenciesList = function(categoriesIds,percentageMap){
	  $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getPerformanceCompetenceListForTagging", 
	        success: function(json) {
	        	var output = "";
	        	for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		if(categoriesIds != null && categoriesIds != '' && categoriesIds != 'undefined'){
		        			var categIds = categoriesIds.split(",");
		        			if (categIds.indexOf(opt.id.toString()) > -1) {
		        				performanceManagement.editSelectedApprisalCompetenciesRows(opt.id,opt.name,percentageMap.get(opt.id));
		        				output = output + "<option value='" +opt.id+ "' selected>"+opt.name+"</option>";
		        			}else{
		        				output = output + "<option value='" +opt.id+ "'>"+opt.name+"</option>";
		        			}
		        		}else{
		        			output = output + "<option value='" +opt.id+ "'>"+opt.name+"</option>";
		        		}
		        		
		        		
				}
	        	$("#comptencyCategories").empty();
		        $("#comptencyCategories").append(output);
		        $('#comptencyCategories').trigger("chosen:updated");
	        },
	    });
  }

  
  $(document).on('change', '#comptencyCategories', function(evt, params) {
		if (params.selected != undefined) {
			performanceManagement.updateSelectedCompetenciesRows(params.selected, false);
		}
		if (params.deselected != undefined) {
			performanceManagement.updateSelectedCompetenciesRows(params.deselected,true);
		}
	});
  
  
  
  this.updateSelectedCompetenciesRows = function(id,removedFlag){
	  if(id===0 && removedFlag){
			$("#selectedComptenciesDiv").empty();
		} else {	
			$("#comptencyCategories option").each(function () {
				if($(this).val() === id) {
					if(removedFlag) {
						$("#selectedComptenciesDiv").find("div[data-template_id=" + $(this).val() + "]").remove();
						return;
					} else {
						var text="<div class='multiCompetencies positionRelative categorySub' id="+$(this).val()+" data-template_id=" + $(this).val() + ">";
						text += "<i class='fa fa-close close_multi_job' onclick='performanceManagement.closeAppriasalCompetenceDiv(this)'></i>";
						text += "<div class='multiJobTitle categorySubHead clearfix'>";
						text += "" + $(this).text() + "";
						text += "<span class='pull-right mr'><input type='text' onblur='performanceManagement.validatePercentage("+$(this).val()+");performanceManagement.validatePercentageTotalSumValue();' id='competenciesPercent"+$(this).val()+"' class='form-control' field-name='Percentage' placeholder='Percentage' data-validation='validate_int required'></span>";
						text += "</div>";
						text += "<div class='row mxrateerror' id='percentageDiv"+$(this).val()+"'>";
						text += "<input type='hidden' name='percent"+$(this).val()+"' id='percentageValidation"+$(this).val()+"'>";
						text += "</div>";
						text += "</div>";
						$("#selectedComptenciesDiv").append(text);
						return;
					}
				}
			});
		}
  }
  
  this.closeAppriasalCompetenceDiv = function(ele) {
		$(ele).parent().remove();
		var id = $(ele).parent().data("template_id");
		$('#comptencyCategories').find("option[value='" + id + "']").prop("selected",false).end().trigger('chosen:updated');
		performanceManagement.validatePercentageTotalSumValue();
	}
  
  
  this.editSelectedApprisalCompetenciesRows = function(id,templateName,percentage){
	    var text="<div class='multiCompetencies positionRelative categorySub' id="+id+" data-template_id=" + id + ">";
		text += "<i class='fa fa-close close_multi_job' onclick='performanceManagement.closeAppriasalCompetenceDiv(this)'></i>";
		text += "<div class='multiJobTitle categorySubHead clearfix'>";
		text += "<h2 class='pull-left'>" + templateName + "</h2>";
		text += "<span class='pull-right mr'><input type='text' value='"+percentage+"' onblur='performanceManagement.validatePercentage("+id+");performanceManagement.validatePercentageTotalSumValue();' id='competenciesPercent"+id+"' class='form-control' field-name='Percentage' placeholder='Percentage' data-validation='validate_int required'></span>";
		text += "</div>";
		text += "<div class='row mxrateerror' id='percentageDiv"+id+"'>";
		text += "<input type='hidden' name='percent"+id+"' id='percentageValidation"+id+"'>";
		text += "</div>";
		text += "</div>";
		$("#selectedComptenciesDiv").append(text);
		return;
}
  
  this.checkFeedbackCategories = function(){
	  var feedbackCategories = $("#feedbackCategory").prop('checked');
	  if(feedbackCategories == true){
		  document.getElementById("fedbackCategoriesDiv").style.display="";
		  $('#fedbackCategoriesDiv').find('p.jquery_form_error_message').remove(); 
	   	  $('#appraisalCategories').attr("data-validation","required"); 
	   	  $('#appraisalCategories').removeAttr('data-validation-optional');
	  }else{
		  document.getElementById("fedbackCategoriesDiv").style.display="none";
		  $('#fedbackCategoriesDiv').find('p.jquery_form_error_message').remove(); 
		  $('#appraisalCategories').attr('data-validation-optional',"true");
	   	  $('#appraisalCategories').removeAttr("data-validation","required"); 
	   	  $('#appraisalCategories').val('').trigger('chosen:updated');
	   	  $("#selectedTemplatesDiv").empty();
	   	  $('#percentSumDiv').find('p.jquery_form_error_message').remove(); 
	  }
  }
  
  
  this.checkPerformanceComptencies = function(){
	  var performanceComptencies = $("#performanceComptencies").prop('checked');
	  if(performanceComptencies == true){
		  document.getElementById("performanceComptenciesDiv").style.display="";
		  $('#performanceComptenciesDiv').find('p.jquery_form_error_message').remove(); 
	   	  $('#comptencyCategories').attr("data-validation","required"); 
	   	  $('#comptencyCategories').removeAttr('data-validation-optional');
	  }else{
		  document.getElementById("performanceComptenciesDiv").style.display="none";
		  $('#performanceComptenciesDiv').find('p.jquery_form_error_message').remove(); 
		  $('#comptencyCategories').attr('data-validation-optional',"true");
	   	  $('#comptencyCategories').removeAttr("data-validation","required"); 
	   	  $('#comptencyCategories').val('').trigger('chosen:updated');
	   	  $("#selectedComptenciesDiv").empty();
	   	  $('#percentSumDiv').find('p.jquery_form_error_message').remove(); 
	  }
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
					type : "feedbackTemplate",
					templateId : $(this).data("template_id") ? $(this).data("template_id") : 0,
					percentage : $("#percent"+$(this).data("template_id")).val(),
					self : $("#selfCategoryPercent"+$(this).data("template_id")).val(),
					peer : $("#peerCategoryPercent"+$(this).data("template_id")).val(),
					resourceManager : $("#reportingMngCategoryPercent"+$(this).data("template_id")).val(),
					reportingManager : $("#resourceMngcategoryPercent"+$(this).data("template_id")).val()
				});
			});
			
			var appraisalGoals = $('#appraisalGoals').val();
			if(appraisalGoals == 'Y'){
				var goalsWeightage = $('#goalsWeightage').val();
				templates.push({
					type : "goalsWeightage",
					name : "Goals",
					weightage : goalsWeightage
				});
			}
			
			var appraisalProjectTasks = $('#appraisalProjectTasks').val();
			if(appraisalProjectTasks == 'Y'){
				var projectTasksWeightage = $('#projectTasksWeightage').val();
				templates.push({
					type : "projectTasksWeightage",
					name : "Project Tasks",
					weightage : projectTasksWeightage
				});
			}
			
			$(".multiCompetencies").each(function() {	
				templates.push({
					type : "competencies",
					competenciesId : $(this).data("template_id") ? $(this).data("template_id") : 0,
					percentage : $("#competenciesPercent"+$(this).data("template_id")).val()
				});
			});
			
			var con = JSON.stringify(templates);
			$("#jsonCategoriesString").val(con);
	        var appraisalFormData = getFormData1("createAppraisal_form");
	        $("#appraisalDetailsLoading").attr('style','position:absolute; width:100%; height:350%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
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
					$('input#percentageValidation'+id).attr('record-exist-errorMsg','Percentage should not in negitive'); 
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
			 performanceManagement.validateCategoryPercentageTotalSumValue();
		 }
		 
  }
  
  this.validatePercentageTotalSumValue = function(){
	  var percentage = 0;
	  $(".multiTemplates").each(function() {	
		  percentage = Number(percentage)+Number($("#percent"+$(this).data("template_id")).val());
	  });
	  
	  var appraisalGoals = $('#appraisalGoals').val();
		if(appraisalGoals == 'Y'){
			var goalsWeightage = $('#goalsWeightage').val();
			 percentage = Number(percentage)+Number(goalsWeightage);
			
		}
		
	  var appraisalProjectTasks = $('#appraisalProjectTasks').val();
		if(appraisalProjectTasks == 'Y'){
			var projectTasksWeightage = $('#projectTasksWeightage').val();
			 percentage = Number(percentage)+Number(projectTasksWeightage);
			
		}
		
	 $(".multiCompetencies").each(function() {	
		  percentage = Number(percentage)+Number($("#competenciesPercent"+$(this).data("template_id")).val());
	  });
	  
	  if(percentage > 100){
		    $('#percentSumDiv').find('p.jquery_form_error_message').remove(); 
		    $('#percentTotalSum').attr('class','error signin_fild');
		    $('#percentTotalSum').after("<p class='jquery_form_error_message'>Total percentage should not more than 100</p>");
		    $('#percentTotalSum').attr('record-exist','yes');
		    $('#percentTotalSum').attr('record-exist-errorMsg','Total percentage should not more than 100'); 
		    $("#savePerformanceBudget").attr("disabled", "disabled");
	  }if(percentage < 100){
		    $('#percentSumDiv').find('p.jquery_form_error_message').remove(); 
		    $('#percentTotalSum').attr('class','error signin_fild');
		    $('#percentTotalSum').after("<p class='jquery_form_error_message'>Total percentage should equal 100</p>");
		    $('#percentTotalSum').attr('record-exist','yes');
		    $('#percentTotalSum').attr('record-exist-errorMsg','Total percentage should equal 100'); 
		    $("#savePerformanceBudget").attr("disabled", "disabled");
	  }else{
		     $('#percentSumDiv').find('p.jquery_form_error_message').remove(); 
			 $('input#percentTotalSum').removeAttr( "class record-exist record-exist-errormsg");
			 $('#percentSumDiv').find('p.jquery_form_error_message').remove();
			 $("#savePerformanceBudget").removeAttr("disabled");
	  }
	  
	 
  }
  
  
  this.enableSelfAppraisalPeriod = function(){
	  
	  $("#selfAppraisalStartDate").val("");
	  $("#selfAppraisalEndDate").val("");
	  
	  var isChecked = $("#isSelfAppraisalRequired").is(":checked");
	  if(isChecked){
		  $("#selfAppraisalStartDate").removeAttr("disabled");
		  
		  $("#selfAppraisalStartDate").removeAttr("data-validation-optional");
		  $("#selfAppraisalStartDate").attr("data-validation","required");
		  $("#selfAppraisalEndDate").removeAttr("data-validation-optional");
		  $("#selfAppraisalEndDate").attr("data-validation","required");
		  $("#isSelfAppraisalRequired").val("Y");
		  
	  }else{
		  $("#selfAppraisalStartDate").attr("disabled", "disabled");
		  $("#selfAppraisalEndDate").attr("disabled", "disabled");
		  $("#isSelfAppraisalRequired").val("N");
		  
		  $("#selfAppraisalStartDate").removeAttr("data-validation");
		  $("#selfAppraisalStartDate").attr("data-validation-optional","true");
		  $("#selfAppraisalEndDate").removeAttr("data-validation");
		  $("#selfAppraisalEndDate").attr("data-validation-optional","true");
		  $('#selfAppraisalStartDateDiv').find('p.jquery_form_error_message').remove(); 
		  $('#selfAppraisalEndDateDiv').find('p.jquery_form_error_message').remove(); 
		  
		  $("input[id$='selfAppraisalStartDate'], input[id$='selfAppraisalEndDate']").datepicker( "option", "maxDate", null );
		  $("input[id$='selfAppraisalStartDate'], input[id$='selfAppraisalEndDate']").datepicker( "option", "minDate", null );
		  
		  $(".multiTemplates").each(function() {	
			 var selfCatg =  $("#selfCategoryPercentDiv"+$(this).data("template_id"));
			 if(selfCatg != null && selfCatg != 'undefined' && selfCatg != ''){
				 $("#selfCategoryPercentDiv"+$(this).data("template_id")).remove();
				 performanceManagement.validateCategoryPercentageTotalSumValue();
			 }
		  });
		  
	  }
  }
  
  
  this.enableReportingManagerAppraisalPeriod = function(){
	  
	  $("#appraisalApprovingStartDate").val("");
	  $("#appraisalApprovingEndDate").val("");
	  
	  var isChecked = $("#isReportingManagerAppraisalRequired").is(":checked");
	  if(isChecked){
		  $("#appraisalApprovingStartDate").removeAttr("disabled");
		  
		  $("#appraisalApprovingStartDate").removeAttr("data-validation-optional");
		  $("#appraisalApprovingStartDate").attr("data-validation","required");
		  $("#appraisalApprovingEndDate").removeAttr("data-validation-optional");
		  $("#appraisalApprovingEndDate").attr("data-validation","required");
		  
		  $("#isReportingManagerAppraisalRequired").val("Y");
		  
	  }else{
		  $("#appraisalApprovingStartDate").attr("disabled", "disabled");
		  $("#appraisalApprovingEndDate").attr("disabled", "disabled");
		  
		  $("#isReportingManagerAppraisalRequired").val("N");
		  
		  $("#appraisalApprovingStartDate").removeAttr("data-validation");
		  $("#appraisalApprovingStartDate").attr("data-validation-optional","true");
		  $("#appraisalApprovingEndDate").removeAttr("data-validation");
		  $("#appraisalApprovingEndDate").attr("data-validation-optional","true");
		  $('#appraisalApprovingStartDateDiv').find('p.jquery_form_error_message').remove(); 
		  $('#appraisalApprovingEndDateDiv').find('p.jquery_form_error_message').remove(); 
		  
		  $("input[id$='appraisalApprovingStartDate'], input[id$='appraisalApprovingEndDate']").datepicker( "option", "maxDate", null );
		  $("input[id$='appraisalApprovingStartDate'], input[id$='appraisalApprovingEndDate']").datepicker( "option", "minDate", null );
		  
		  $(".multiTemplates").each(function() {	
				 var reportingMngCatg =  $("#reportingMngCategoryPercentDiv"+$(this).data("template_id"));
				 if(reportingMngCatg != null && reportingMngCatg != 'undefined' && reportingMngCatg != ''){
					 $("#reportingMngCategoryPercentDiv"+$(this).data("template_id")).remove();
					 performanceManagement.validateCategoryPercentageTotalSumValue();
				 }
			  });
	  }
  }
  
  
this.enablePeerReviewPeriod = function(){
	  
	  $("#peerReviewStartDate").val("");
	  $("#peerReviewEndDate").val("");
	  
	  var isChecked = $("#isPeerReviewRequired").is(":checked");
	  if(isChecked){
		  $("#peerReviewStartDate").removeAttr("disabled");
		  
		  $("#peerReviewStartDate").removeAttr("data-validation-optional");
		  $("#peerReviewStartDate").attr("data-validation","required");
		  $("#peerReviewEndDate").removeAttr("data-validation-optional");
		  $("#peerReviewEndDate").attr("data-validation","required");
		  
		  $("#isPeerReviewRequired").val("Y");
		  
	  }else{
		  $("#peerReviewStartDate").attr("disabled", "disabled");
		  $("#peerReviewEndDate").attr("disabled", "disabled");
		  
		  $("#isPeerReviewRequired").val("N");
		  
		  $("#peerReviewStartDate").removeAttr("data-validation");
		  $("#peerReviewStartDate").attr("data-validation-optional","true");
		  $("#peerReviewEndDate").removeAttr("data-validation");
		  $("#peerReviewEndDate").attr("data-validation-optional","true");
		  $('#peerReviewStartDateDiv').find('p.jquery_form_error_message').remove(); 
		  $('#peerReviewEndDateDiv').find('p.jquery_form_error_message').remove(); 
		  
		  $("input[id$='peerReviewStartDate'], input[id$='peerReviewEndDate']").datepicker( "option", "maxDate", null );
		  $("input[id$='peerReviewStartDate'], input[id$='peerReviewEndDate']").datepicker( "option", "minDate", null );
		  
		  $(".multiTemplates").each(function() {	
				 var peerCatg =  $("#peerCategoryPercentDiv"+$(this).data("template_id"));
				 if(peerCatg != null && peerCatg != 'undefined' && peerCatg != ''){
					 $("#peerCategoryPercentDiv"+$(this).data("template_id")).remove();
					 performanceManagement.validateCategoryPercentageTotalSumValue();
				 }
			  });
	  }
  }

this.enableResourceManagerAppraisalPeriod = function(){
	  
	  $("#resourceManagerReviewStartDate").val("");
	  $("#resourceManagerReviewEndDate").val("");
	  
	  var isChecked = $("#isResourceManagerAppraisalRequired").is(":checked");
	  if(isChecked){
		  $("#resourceManagerReviewStartDate").removeAttr("disabled");
		  
		  $("#isResourceManagerAppraisalRequired").val("Y");
		  
		  $("#resourceManagerReviewStartDate").removeAttr("data-validation-optional");
		  $("#resourceManagerReviewStartDate").attr("data-validation","required");
		  $("#resourceManagerReviewEndDate").removeAttr("data-validation-optional");
		  $("#resourceManagerReviewEndDate").attr("data-validation","required");
	  }else{
		  $("#resourceManagerReviewStartDate").attr("disabled", "disabled");
		  $("#resourceManagerReviewEndDate").attr("disabled", "disabled");
		  
		  $("#isResourceManagerAppraisalRequired").val("N");
		  
		  $("#resourceManagerReviewStartDate").removeAttr("data-validation");
		  $("#resourceManagerReviewStartDate").attr("data-validation-optional","true");
		  $("#resourceManagerReviewEndDate").removeAttr("data-validation");
		  $("#resourceManagerReviewEndDate").attr("data-validation-optional","true");
		  $('#resourceManagerReviewStartDateDiv').find('p.jquery_form_error_message').remove(); 
		  $('#resourceManagerReviewEndDateDiv').find('p.jquery_form_error_message').remove(); 
		  
		  $("input[id$='resourceManagerReviewStartDate'], input[id$='resourceManagerReviewEndDate']").datepicker( "option", "maxDate", null );
		  $("input[id$='resourceManagerReviewStartDate'], input[id$='resourceManagerReviewEndDate']").datepicker( "option", "minDate", null );
		  
		  $(".multiTemplates").each(function() {	
				 var resourceMngCatg =  $("#resourceMngcategoryPercentDiv"+$(this).data("template_id"));
				 if(resourceMngCatg != null && resourceMngCatg != 'undefined' && resourceMngCatg != ''){
					 $("#resourceMngcategoryPercentDiv"+$(this).data("template_id")).remove();
					 performanceManagement.validateCategoryPercentageTotalSumValue();
				 }
			  });
	  }
}
  
  
  this.clearAppraisalStartDate = function(){
	  $("#appraisalStartDate").val("");
	  $("#appraisalEndDate").val("");
	  $("#appraisalEndDate").attr("disabled", "disabled");
	  
	  $("input[id$='appraisalStartDate'], input[id$='appraisalEndDate']").datepicker( "option", "maxDate", null );
	  $("input[id$='appraisalStartDate'], input[id$='appraisalEndDate']").datepicker( "option", "minDate", null );
  }
  
  this.clearAppraisalEndDate = function(){
	  $("#appraisalEndDate").val("");
	  $("input[id$='appraisalStartDate'], input[id$='appraisalEndDate']").datepicker( "option", "maxDate", null );
  }
  
  this.clearSelfAppraisalStartDate = function(){
	  $("#selfAppraisalStartDate").val("");
	  $("#selfAppraisalEndDate").val("");
	  $("#selfAppraisalEndDate").attr("disabled", "disabled");
	  
	  $("input[id$='selfAppraisalStartDate'], input[id$='selfAppraisalEndDate']").datepicker( "option", "maxDate", null );
	  $("input[id$='selfAppraisalStartDate'], input[id$='selfAppraisalEndDate']").datepicker( "option", "minDate", null );
  }
  
  this.clearSelfAppraisalEndDate = function(){
	  $("#selfAppraisalEndDate").val("");
	  $("input[id$='selfAppraisalStartDate'], input[id$='selfAppraisalEndDate']").datepicker( "option", "maxDate", null );
  }
  
  this.clearAppraisalApprovingStartDate = function(){
	  $("#appraisalApprovingStartDate").val("");
	  $("#appraisalApprovingEndDate").val("");
	  $("#appraisalApprovingEndDate").attr("disabled", "disabled");
	  
	  $("input[id$='appraisalApprovingStartDate'], input[id$='appraisalApprovingEndDate']").datepicker( "option", "maxDate", null );
	  $("input[id$='appraisalApprovingStartDate'], input[id$='appraisalApprovingEndDate']").datepicker( "option", "minDate", null );
  }
  
  this.clearAppraisalApprovingEndDate = function(){
	  $("#appraisalApprovingEndDate").val("");
	  $("input[id$='appraisalApprovingStartDate'], input[id$='appraisalApprovingEndDate']").datepicker( "option", "maxDate", null );
  }
  
  this.clearAppraisalProcessStartDate = function(){
	  $("#appraisalProcessStartDate").val("");
	  $("#appraisalProcessEndDate").val("");
	  $("#appraisalProcessEndDate").attr("disabled", "disabled");
	  
	  $("input[id$='appraisalProcessStartDate'], input[id$='appraisalProcessEndDate']").datepicker( "option", "maxDate", null );
	  $("input[id$='appraisalProcessStartDate'], input[id$='appraisalProcessEndDate']").datepicker( "option", "minDate", null );
  }
  
  this.clearAppraisalProcessEndDate = function(){
	  $("#appraisalProcessEndDate").val("");
	  $("input[id$='appraisalProcessStartDate'], input[id$='appraisalProcessEndDate']").datepicker( "option", "maxDate", null );
  }
  
  this.clearPeerReviewStartDate = function(){
	  $("#peerReviewStartDate").val("");
	  $("#peerReviewEndDate").val("");
	  $("#peerReviewEndDate").attr("disabled", "disabled");
	  
	  $("input[id$='peerReviewStartDate'], input[id$='peerReviewEndDate']").datepicker( "option", "maxDate", null );
	  $("input[id$='peerReviewStartDate'], input[id$='peerReviewEndDate']").datepicker( "option", "minDate", null );
  }
  
  this.clearPeerReviewEndDate = function(){
	  $("#peerReviewEndDate").val("");
	  $("input[id$='peerReviewStartDate'], input[id$='peerReviewEndDate']").datepicker( "option", "maxDate", null );
  }
  
  this.clearResourceManagerReviewStartDate = function(){
	  $("#resourceManagerReviewStartDate").val("");
	  $("#resourceManagerReviewEndDate").val("");
	  $("#resourceManagerReviewEndDate").attr("disabled", "disabled");
	  
	  $("input[id$='resourceManagerReviewStartDate'], input[id$='resourceManagerReviewEndDate']").datepicker( "option", "maxDate", null );
	  $("input[id$='resourceManagerReviewStartDate'], input[id$='resourceManagerReviewEndDate']").datepicker( "option", "minDate", null );
  }
  
  this.clearResourceManagerReviewEndDate = function(){
	  $("#resourceManagerReviewEndDate").val("");
	  $("input[id$='resourceManagerReviewStartDate'], input[id$='resourceManagerReviewEndDate']").datepicker( "option", "maxDate", null );
  }
  
  
  this.changeDescriptionDisplayStatus = function(){
	  var isChecked = $("#descriptionDisplay").is(":checked");
	  if(isChecked){
		  $("#descriptionDisplay").val("Y");
	  }else{
		  $("#descriptionDisplay").val("N");
	  }
  }
  
  this.changeAppraisalGoalsStatus = function(){
	  var isChecked = $("#appraisalGoals").is(":checked");
	  if(isChecked){
		  $("#appraisalGoals").val("Y");
		  $('#goalsWeightage').attr("data-validation","required validate_int"); 
	   	  $('#goalsWeightage').removeAttr('data-validation-optional');
	   	  $("#goalsWeightage").removeAttr("disabled");
	   	
	  }else{
		  $("#appraisalGoals").val("N");
		  $('#goalsWeightageDiv').find('p.jquery_form_error_message').remove(); 
		  $('#goalsWeightage').removeAttr("data-validation"); 
	   	  $('#goalsWeightage').attr('data-validation-optional',"true");
	      $('#goalsWeightage').val('');
	      $("#goalsWeightage").attr("disabled", "disabled");
	  }
  }
  
  this.changeAppraisalProjectTasksStatus = function(){
	  var isChecked = $("#appraisalProjectTasks").is(":checked");
	  if(isChecked){
		  $("#appraisalProjectTasks").val("Y");
		  $('#projectTasksWeightage').attr("data-validation","required validate_int"); 
	   	  $('#projectTasksWeightage').removeAttr('data-validation-optional');
	      $("#projectTasksWeightage").removeAttr("disabled");
	  }else{
		  $("#appraisalProjectTasks").val("N");
		  $('#projectTasksWeightageDiv').find('p.jquery_form_error_message').remove(); 
		  $('#projectTasksWeightage').removeAttr("data-validation"); 
	   	  $('#projectTasksWeightage').attr('data-validation-optional',"true");
	   	  $('#projectTasksWeightage').val('');
	   	  $("#projectTasksWeightage").attr("disabled", "disabled");
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
  
  
  this.appraisalTemplateDetails = function(templateUUId){
	  $("#appraisalDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  
	  $.ajax({
		  type: "GET",
		    url: sourceleadUrl+"/getAppraisalCycleTemplateDetailsById?templateUUId="+templateUUId,
		     success :function(response) {
		    	 
		    		if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
     	    		    showError(response.responseMessage);
     	    		    $("#appraisalDetailsLoading").removeAttr('style');
     	    		}
		        	else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		 $("#appraisalName").html(response.object.name);
		        		 $("#appraisalStartDate").html(response.object.appraisalStartDate);
		        		 $("#appraisalEndDate").html(response.object.appraisalEndDate);
		        		 
		        		 if(response.object.appraisalDepartment != null && response.object.appraisalDepartment != 'undefined' && response.object.appraisalDepartment != ''){
		        			 performanceManagement.appraisalDepartmentsList(response.object.appraisalDepartment);
		        		 }
		        		
		        		 
		        		 if(response.object.appraisalRoles != null && response.object.appraisalRoles != 'undefined' && response.object.appraisalRoles != ''){
		        			 performanceManagement.appraisalRolesList(response.object.appraisalRoles);
		        		 }
		        		
		        		 
		        		 if(response.object.appraisalEmpType != null && response.object.appraisalEmpType != 'undefined' && response.object.appraisalEmpType != ''){
		        			 performanceManagement.appraisalEmployeeTypeList(response.object.appraisalEmpType);
		        		 }
		        		 
		        		 if(response.object.descriptionDisplay != null 
		        				 && response.object.descriptionDisplay != ''
		        					 && response.object.descriptionDisplay != 'undefined'){
		        			 $("#descriptionDisplay").html("Yes");
		        			 
		        		 }else{
		        			 $("#descriptionDisplay").html("No");
		        		 }
		        		 
		        		 if(response.object.description != null 
		        				 && response.object.description != ''
		        					 && response.object.description != 'undefined'){
		        			 
		        			 $("#appraisalDescription").html(response.object.description);
		        		 }
		        		 
		        		 if(response.object.appraisalProcessStartDate != null 
		        				 && response.object.appraisalProcessStartDate != ''
		        					 && response.object.appraisalProcessStartDate != 'undefined'){
		        			 $("#appraisalProcessStartDate").html(response.object.appraisalProcessStartDate);
		        			 $("#appraisalProcessEndDate").html(response.object.appraisalProcessEndDate);
		        		 }
		        		 
		        		 if(response.object.selfAppraisalStartDate != null 
		        				 && response.object.selfAppraisalStartDate != ''
		        					 && response.object.selfAppraisalStartDate != 'undefined'){
		        			 $("#selfAppraisalStartDate").html(response.object.selfAppraisalStartDate);
		        			 $("#selfAppraisalEndDate").html(response.object.selfAppraisalStartDate);
		        		 }
		        		 
		        		 if(response.object.peerReviewStartDate != null 
		        				 && response.object.peerReviewStartDate != ''
		        					 && response.object.peerReviewStartDate != 'undefined'){
		        			 $("#peerReviewStartDate").html(response.object.peerReviewStartDate);
		        			 $("#peerReviewEndDate").html(response.object.peerReviewEndDate);
		        		 }
		        		
		        		 if(response.object.resourceManagerReviewStartDate != null 
		        				 && response.object.resourceManagerReviewStartDate != ''
		        					 && response.object.resourceManagerReviewStartDate != 'undefined'){
		        			 $("#resourceManagerReviewStartDate").html(response.object.resourceManagerReviewStartDate);
		        			 $("#resourceManagerReviewEndDate").html(response.object.resourceManagerReviewEndDate);
		        		 }
		        		 
		        		 if(response.object.appraisalApprovingStartDate != null 
		        				 && response.object.appraisalApprovingStartDate != ''
		        					 && response.object.appraisalApprovingStartDate != 'undefined'){
		        			 $("#reportingManagerReviewStartDate").html(response.object.appraisalApprovingStartDate);
		        			 $("#reportingManagerReviewEndDate").html(response.object.appraisalApprovingEndDate);
		        		 }
		        		 
		        		 var percentageMap = new Map();
		        		 var selfPercentageMap = new Map();
		        		 var peerPercentageMap = new Map();
		        		 var resourceMgrPercentageMap = new Map();
		        		 var reportingMgrPercentageMap = new Map();
		        		 var selfPercentage = 0;
		        		 var peerPercentage = 0;
		        		 var reportingMgrPercentage = 0;
		        		 var resourceMgrPercentage = 0;
		        		 var competenciesPercentageMap = new Map();
		        		 if(response.object.jsonCategoriesString != null 
		        				 && response.object.jsonCategoriesString != '' 
		        					 && response.object.jsonCategoriesString != 'undefined'){
		        			var data = eval(response.object.jsonCategoriesString);
		        			
			        		 if (data) {
			        			  $.each( data, function( k, v ) {
			        				  
			        				  if(v['type'] == "feedbackTemplate"){
			        					  percentageMap.set(v['templateId'],v['percentage'])
			        				   	  
			        				   	  if(v['self'] != 'undefined'){
			        				   		selfPercentageMap.set(v['templateId'],v['self']);
			        				   	  }
			        				   	  
			        				   	 if(v['peer'] != 'undefined'){
			        				   		peerPercentageMap.set(v['templateId'],v['peer']);
				        				   	  }
			        				   	 
			        				   	 if(v['resourceManager'] != 'undefined'){
			        				   		resourceMgrPercentageMap.set(v['templateId'],v['resourceManager']);
				        				   	  }
			        				   	 
			        				   	 if(v['reportingManager'] != 'undefined'){
			        				   		reportingMgrPercentageMap.set(v['templateId'],v['reportingManager']);
				        				   	  }
			        				   	
			        				  }
			        				  
			        				  if(v['type'] == "goalsWeightage"){
						        			  $("#appraisalGoalsWeightage").html(v['weightage']) 
			        				  }
			        				  
			        				  if(v['type'] == "projectTasksWeightage"){
						        			  $("#appraisalTasksWeightage").html(v['weightage']) 
			        				  }
			        				  
			        				  if(v['type'] == "competencies"){
			        					  competenciesPercentageMap.set(v['competenciesId'],v['percentage'])
			        				   	
			        				  }
			        			  });
			        		 }
		        		}
		        		
		        		 if(percentageMap.size > 0){
		        			 performanceManagement.appraisalFeebackCategoriesDetails(response.object.appraisalCategories,percentageMap,selfPercentageMap,peerPercentageMap,resourceMgrPercentageMap,reportingMgrPercentageMap);
		        		 }
		        		
	        			  if(competenciesPercentageMap.size > 0){
		        			 performanceManagement.appraisalCompetenciesDetails(response.object.comptencyCategories,competenciesPercentageMap);
		        		 }
		        		
		        		 
		        		 $("#appraisalDetailsLoading").removeAttr('style');
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		    	  
				},
			});
  }
  
  
  
  this.appraisalDepartmentsList = function(departmentIds){
	  $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getDepartmentsListForPerformanceAppraisal", 
	        success: function(json) {
	        	var departments = '';
	        	for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		var deptIds = departmentIds.split(",");
	        			if (deptIds.indexOf(opt.deptId.toString()) > -1) {
	        				departments = departments + opt.departmentName+", ";
	        			}
		        		
		        		
				}
		        $('#appraisalDepartments').html(departments.substring(0, departments.length - 2));
	        },
	    });
  }
  
  this.appraisalRolesList = function(rolesIds){
	  $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getRolesListForPerformanceAppraisal", 
	        success: function(json) {
	        	var roles = '';
	        	for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		var userRoleIds = rolesIds.split(",");
	        			if (userRoleIds.indexOf(opt.rolesId.toString()) > -1) {
	        				roles = roles + opt.displayName+", ";
	        				
	        			}
		        		
		        		
				}
	        	$('#appraisalRoles').html(roles.substring(0, roles.length - 2));
	        },
	    });
  }
  
  
  this.appraisalEmployeeTypeList = function(employeeTypes){
	  $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getEmployeeTypesForPerformanceAppraisal", 
	        success: function(json) {
	        	var employeeType = ''
	        	for (var i=0; i<json.length; i++)
				{
		        		var empType = json[i];
		        		var employeeType = employeeTypes.split(", ");
	        			if (employeeType.indexOf(empType.toString()) > -2) {
	        				employeeType = employeeType + empType+",";
	        			}
		        		
		        		
				}
	        	$('appraisalEmlployeeType').html(employeeType.substring(0, employeeType.length - 1));
	        },
	    });
  }
  
  
  this.editAppraisalCycle = function(templateUUId){
	  
 	  $("#appraisalDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
			  type: "GET",
			    url: sourceleadUrl+"/getAppraisalCycleTemplateDetailsById?templateUUId="+templateUUId,
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
			        		 $("#appraisalEndDate").removeAttr("disabled");
			        		 $("#appraisalEndDate").val(response.object.appraisalEndDate);
			        		 
			        		 if(response.object.isSelfAppraisalRequired == 'Y'){
			        			 document.getElementById("isSelfAppraisalRequired").checked = true;
			        			 $("#isSelfAppraisalRequired").val("Y");
			        			 $("#selfAppraisalStartDate").removeAttr("disabled");
			        		 }
			        		 
			        		 if(response.object.selfAppraisalStartDate != null 
			        				 && response.object.selfAppraisalStartDate != ''
			        					 && response.object.selfAppraisalStartDate != 'undefined'){
			        			 $("#selfAppraisalEndDate").removeAttr("disabled");
			        			 $("#selfAppraisalStartDate").val(response.object.selfAppraisalStartDate);
			        		 }
			        		 
			        		 if(response.object.selfAppraisalEndDate != null 
			        				 && response.object.selfAppraisalEndDate != ''
			        					 && response.object.selfAppraisalEndDate != 'undefined'){
			        			 $("#selfAppraisalEndDate").val(response.object.selfAppraisalEndDate);
			        		 }
			        		 
			        		 
			        		 if(response.object.isReportingManagerAppraisalRequired == 'Y'){
			        			 document.getElementById("isReportingManagerAppraisalRequired").checked = true;
			        			 $("#isReportingManagerAppraisalRequired").val("Y");
			        			 $("#appraisalApprovingStartDate").removeAttr("disabled");
			        		 }
			        		 
			        		 if(response.object.appraisalApprovingStartDate != null 
			        				 && response.object.appraisalApprovingStartDate != ''
			        					 && response.object.appraisalApprovingStartDate != 'undefined'){
			        			 $("#appraisalApprovingEndDate").removeAttr("disabled");
			        			 $("#appraisalApprovingStartDate").val(response.object.appraisalApprovingStartDate);
			        		 }
			        		 
			        		 if(response.object.appraisalApprovingEndDate != null 
			        				 && response.object.appraisalApprovingEndDate != ''
			        					 && response.object.appraisalApprovingEndDate != 'undefined'){
			        			 $("#appraisalApprovingEndDate").val(response.object.appraisalApprovingEndDate);
			        		 }
			        		 
			        		 
			        		 if(response.object.appraisalProcessStartDate != null 
			        				 && response.object.appraisalProcessStartDate != ''
			        					 && response.object.appraisalProcessStartDate != 'undefined'){
			        			 $("#appraisalProcessEndDate").removeAttr("disabled");
			        			 $("#appraisalProcessStartDate").val(response.object.appraisalProcessStartDate);
			        		 }
			        		 
			        		 if(response.object.isPeerReviewRequired == 'Y'){
			        			 document.getElementById("isPeerReviewRequired").checked = true; 
			        			 $("#isPeerReviewRequired").val("Y");
			        			 $("#peerReviewStartDate").removeAttr("disabled");
			        		 }
			        		 
			        		 if(response.object.peerReviewStartDate != null 
			        				 && response.object.peerReviewStartDate != ''
			        					 && response.object.peerReviewStartDate != 'undefined'){
			        			 $("#peerReviewEndDate").removeAttr("disabled");
			        			 $("#peerReviewStartDate").val(response.object.peerReviewStartDate);
			        		 }
			        		 
			        		 if(response.object.peerReviewEndDate != null 
			        				 && response.object.peerReviewEndDate != ''
			        					 && response.object.peerReviewEndDate != 'undefined'){
			        			 $("#peerReviewEndDate").val(response.object.peerReviewEndDate);
			        		 }
			        		 
			        		 
			        		 if(response.object.isResourceManagerAppraisalRequired == 'Y'){
			        			 document.getElementById("isResourceManagerAppraisalRequired").checked = true; 
			        			 $("#isResourceManagerAppraisalRequired").val("Y");
			        			 $("#resourceManagerReviewStartDate").removeAttr("disabled");
			        		 }
			        		 
			        		 if(response.object.resourceManagerReviewStartDate != null 
			        				 && response.object.resourceManagerReviewStartDate != ''
			        					 && response.object.resourceManagerReviewStartDate != 'undefined'){
			        			 $("#resourceManagerReviewEndDate").removeAttr("disabled");
			        			 $("#resourceManagerReviewStartDate").val(response.object.resourceManagerReviewStartDate);
			        		 }
			        		 
			        		 if(response.object.resourceManagerReviewEndDate != null 
			        				 && response.object.resourceManagerReviewEndDate != ''
			        					 && response.object.resourceManagerReviewEndDate != 'undefined'){
			        			 $("#resourceManagerReviewEndDate").val(response.object.resourceManagerReviewEndDate);
			        		 }
			        		 
			        		 if(response.object.appraisalProcessEndDate != null 
			        				 && response.object.appraisalProcessEndDate != ''
			        					 && response.object.appraisalProcessEndDate != 'undefined'){
			        			 $("#appraisalProcessEndDate").val(response.object.appraisalProcessEndDate);
			        		 }
			        		 
			        		 if(response.object.descriptionDisplay != null 
			        				 && response.object.descriptionDisplay != ''
			        					 && response.object.descriptionDisplay != 'undefined'){
			        			 $("#descriptionDisplay").val("Y");
			        			 document.getElementById("descriptionDisplay").checked = true;
			        			 
			        		 }
			        		 if(response.object.description != null 
			        				 && response.object.description != ''
			        					 && response.object.description != 'undefined'){
			        			 $("textarea[name=description]").val(response.object.description);
					        	 $(".Editor-editor").html(response.object.description);
			        		 }
			        		 
			        		 performanceManagement.departmentsList(response.object.appraisalDepartment);
			        		 performanceManagement.rolesList(response.object.appraisalRoles);
			        		 performanceManagement.employeeTypeList(response.object.appraisalEmpType);
			        		 var percentageMap = new Map();
			        		 var selfPercentageMap = new Map();
			        		 var peerPercentageMap = new Map();
			        		 var resourceMgrPercentageMap = new Map();
			        		 var reportingMgrPercentageMap = new Map();
			        		 var selfPercentage = 0;
			        		 var peerPercentage = 0;
			        		 var reportingMgrPercentage = 0;
			        		 var resourceMgrPercentage = 0;
			        		 var competenciesPercentageMap = new Map();
			        		 if(response.object.jsonCategoriesString != null 
			        				 && response.object.jsonCategoriesString != '' 
			        					 && response.object.jsonCategoriesString != 'undefined'){
			        			var data = eval(response.object.jsonCategoriesString);
				        		 if (data) {
				        			  $.each( data, function( k, v ) {
				        				  
				        				  
				        				  if(v['type'] == "feedbackTemplate"){
				        					  percentageMap.set(v['templateId'],v['percentage'])
				        					  document.getElementById("feedbackCategory").checked = true; 
				        					  document.getElementById("fedbackCategoriesDiv").style.display="";
				        				   	  $('#appraisalCategories').attr("data-validation","required"); 
				        				   	  $('#appraisalCategories').removeAttr('data-validation-optional');
				        				   	  
				        				   	  if(v['self'] != 'undefined'){
				        				   		selfPercentageMap.set(v['templateId'],v['self']);
				        				   	  }
				        				   	  
				        				   	 if(v['peer'] != 'undefined'){
				        				   		peerPercentageMap.set(v['templateId'],v['peer']);
					        				   	  }
				        				   	 
				        				   	 if(v['resourceManager'] != 'undefined'){
				        				   		resourceMgrPercentageMap.set(v['templateId'],v['resourceManager']);
					        				   	  }
				        				   	 
				        				   	 if(v['reportingManager'] != 'undefined'){
				        				   		reportingMgrPercentageMap.set(v['templateId'],v['reportingManager']);
					        				   	  }
				        				   	
				        				  }
				        				  
				        				  if(v['type'] == "goalsWeightage"){
				        					     $("#appraisalGoals").val("Y");
							        			  document.getElementById("appraisalGoals").checked = true;
							        			  $("#appraisalGoals").val("Y");
							        			  $('#goalsWeightage').attr("data-validation","required validate_int"); 
							        		   	  $('#goalsWeightage').removeAttr('data-validation-optional');
							        		   	  $("#goalsWeightage").removeAttr("disabled");
							        			  $("#goalsWeightage").val(v['weightage']) 
				        				  }
				        				  
				        				  if(v['type'] == "projectTasksWeightage"){
				        					     $("#appraisalProjectTasks").val("Y");
							        			 document.getElementById("appraisalProjectTasks").checked = true;
							        			  $("#appraisalProjectTasks").val("Y");
							        			  $('#projectTasksWeightage').attr("data-validation","required validate_int"); 
							        		   	  $('#projectTasksWeightage').removeAttr('data-validation-optional');
							        		   	  $("#projectTasksWeightage").removeAttr("disabled");
							        			  $("#projectTasksWeightage").val(v['weightage']) 
				        				  }
				        				  
				        				  if(v['type'] == "competencies"){
				        					  competenciesPercentageMap.set(v['competenciesId'],v['percentage'])
				        					  document.getElementById("performanceComptencies").checked = true; 
				        					  document.getElementById("performanceComptenciesDiv").style.display="";
				        				   	  $('#performanceComptencies').attr("data-validation","required"); 
				        				   	  $('#performanceComptencies').removeAttr('data-validation-optional');
				        				   	
				        				  }
				        			  });
				        		 }
			        		}
			        		 
			        		 if(percentageMap.size > 0){
			        			 performanceManagement.feedbackCategoriesList(response.object.appraisalCategories,percentageMap,selfPercentageMap,peerPercentageMap,resourceMgrPercentageMap,reportingMgrPercentageMap);
			        		 }else{
			        			 performanceManagement.feedbackCategoriesList();
			        		 }
			        		
			        		 if(competenciesPercentageMap.size > 0){
			        			 performanceManagement.appraisalCompetenciesList(response.object.comptencyCategories,competenciesPercentageMap);
			        		 }else{
			        			 performanceManagement.appraisalCompetenciesList();
			        		 }
			        		
			        		 
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
  
  
  
  this.competenceRolesList = function(rolesIds){
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
	        	$("#competenceRole").empty();
		        $("#competenceRole").append(output);
		        $('#competenceRole').trigger("chosen:updated");
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
  
 
  this.appraisalFeebackCategoriesDetails = function(categoriesIds,percentageMap,selfPercentageMap,peerPercentageMap,resourceMgrPercentageMap,reportingMgrPercentageMap){
	  $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getFeedBackCategoriesListForPerformanceAppraisal", 
	        success: function(json) {
	        	var output = "";
	        	for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		var categIds = categoriesIds.split(",");
	        			if (categIds.indexOf(opt.templateId.toString()) > -1) {
        					output = output + '<span class="clearfix mt"><strong>'+opt.templateName+'</strong></span>';	
        					output = output + '<div class="clearfix mt10"></div>';
	        				if(typeof selfPercentageMap.get(opt.templateId) != "undefined"){
	        					output = output + '<span class="clearfix mt">Self : <span>'+selfPercentageMap.get(opt.templateId);
	        				}
	        				if(typeof peerPercentageMap.get(opt.templateId) != "undefined"){
	        					output = output + '<span class="clearfix mt">Peer : <span>'+peerPercentageMap.get(opt.templateId);
	        				}
	        				if(typeof resourceMgrPercentageMap.get(opt.templateId) != "undefined"){
	        					output = output + '<span class="clearfix mt">Resource Manager : <span>'+resourceMgrPercentageMap.get(opt.templateId);
	        				}
	        				if(typeof reportingMgrPercentageMap.get(opt.templateId) != "undefined"){
	        					output = output + '<span class="clearfix mt">Reporting Manager : <span>'+reportingMgrPercentageMap.get(opt.templateId);
	        				}
	        				output = output + '<div class="clearfix mt10"></div>';	
	        				
	        			}
		        		
		        		
				}
		        $('#appraisalFeedbackCategories').html(output);
	        },
	    });
	  
  }
  
  

  this.appraisalCompetenciesDetails = function(categoriesIds,percentageMap){
  	  $.ajax({
  	    	type: "GET",
  	        url: sourceleadUrl+"/getPerformanceCompetenceListForTagging", 
  	        success: function(json) {
  	        	var output = "";
  	        	for (var i=0; i<json.length; i++)
  				{
  		        		var opt = json[i];
  		        		var categIds = categoriesIds.split(",");
  	        			if (categIds.indexOf(opt.id.toString()) > -1) {
  	        				output = output + '<span class="clearfix mt"><strong>'+opt.name+'</strong></span>';	
        					output = output + '<div class="clearfix mt10"></div>';
        					output = output + '<span class="clearfix mt">Percentage : <span>'+percentageMap.get(opt.id);
        					
  	        			}
  		        		
  		        		
  				}
  		        $('#appraisalPerformanceCompetencies').html(output);
  	        },
  	    });
  }
    
  
  this.feedbackCategoriesList = function(categoriesIds,percentageMap,selfPercentageMap,peerPercentageMap,resourceMgrPercentageMap,reportingMgrPercentageMap){
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
		        				performanceManagement.editSelectedTemplateRows(opt.templateId,opt.templateName,percentageMap.get(opt.templateId),selfPercentageMap.get(opt.templateId),peerPercentageMap.get(opt.templateId),resourceMgrPercentageMap.get(opt.templateId),reportingMgrPercentageMap.get(opt.templateId));
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
  
  
  this.editSelectedTemplateRows = function(id,templateName,percentage,selfPercentage,peerPercentage,resourceMgrPercentage,reportingMgrPercentage){
	  
	    var selfRevText = "";
		var peerRevText = "";
		var reportingMngText = "";
		var resMngText = "";
		
		if(typeof selfPercentage != "undefined"){
			var isSelfChecked = $("#isSelfAppraisalRequired").is(":checked");
			if(isSelfChecked){
				selfRevText = '<div class="categorySubCta" id="selfCategoryPercentDiv'+id+'"><span>Self Appraisal<i class="fa fa-close" onclick="performanceManagement.closeSelectedCategoryDiv('+id+',\'self\')"></i></span><input type="text" value="'+selfPercentage+'" onblur="performanceManagement.validatePercentage('+id+');performanceManagement.validateCategoryPercentageTotalSumValue();" id="selfCategoryPercent'+id+'" class="form-control" field-name="Percentage" placeholder="Percentage" data-validation="validate_int required"></div>'
			}
		}
		
		
		if(typeof peerPercentage != "undefined"){
			var isPeerChecked = $("#isPeerReviewRequired").is(":checked");
			if(isPeerChecked){
				peerRevText = '<div class="categorySubCta" id="peerCategoryPercentDiv'+id+'"><span>Peer Review<i class="fa fa-close" onclick="performanceManagement.closeSelectedCategoryDiv('+id+',\'peer\')"></i></span><input type="text" value="'+peerPercentage+'" onblur="performanceManagement.validatePercentage('+id+');performanceManagement.validateCategoryPercentageTotalSumValue();" id="peerCategoryPercent'+id+'" class="form-control" field-name="Percentage" placeholder="Percentage" data-validation="validate_int required"></div>'
			}
		}
	
		if(typeof reportingMgrPercentage != "undefined"){
			var isRepMngChecked = $("#isReportingManagerAppraisalRequired").is(":checked");
			if(isRepMngChecked){
				reportingMngText = '<div class="categorySubCta" id="reportingMngCategoryPercentDiv'+id+'"><span>Reporting Manager<i class="fa fa-close" onclick="performanceManagement.closeSelectedCategoryDiv('+id+',\'reportingManager\')"></i></span><input type="text" value="'+reportingMgrPercentage+'" onblur="performanceManagement.validatePercentage('+id+');performanceManagement.validateCategoryPercentageTotalSumValue();" id="reportingMngCategoryPercent'+id+'" class="form-control" field-name="Percentage" placeholder="Percentage" data-validation="validate_int required"></div>'
			}
		}
		
		
		if(typeof resourceMgrPercentage != "undefined"){
			var isResMngChecked = $("#isResourceManagerAppraisalRequired").is(":checked");
			if(isResMngChecked){
				resMngText = '<div class="categorySubCta" id="resourceMngcategoryPercentDiv'+id+'"><span>Resource Manager<i class="fa fa-close" onclick="performanceManagement.closeSelectedCategoryDiv('+id+',\'resourceManager\')"></i></span><input type="text" value="'+resourceMgrPercentage+'" onblur="performanceManagement.validatePercentage('+id+');performanceManagement.validateCategoryPercentageTotalSumValue();" id="resourceMngcategoryPercent'+id+'" class="form-control" field-name="Percentage" placeholder="Percentage" data-validation="validate_int required"></div>'
			}
		}
		
		
	    var text="<div class='multiTemplates positionRelative categorySub' id="+id+" data-template_id=" + id + ">";
		text += "<i class='fa fa-close close_multi_job' onclick='performanceManagement.closeSelectedJobDiv(this)'></i>";
		text += "<div class='multiJobTitle categorySubHead clearfix'>";
		text += "" + templateName + "";
		text += "<span class='pull-right mr'><input type='text' value='"+percentage+"' onblur='performanceManagement.validatePercentage("+id+");performanceManagement.validatePercentageTotalSumValue();' id='percent"+id+"' class='form-control' field-name='Percentage' placeholder='Percentage' data-validation='validate_int required'></span>";
		text += "</div>";
		text += "<div class='clearfix'>";
		text += selfRevText
		text += peerRevText
		text += reportingMngText
		text += resMngText
		text += "</div>";
		text += "<div class='clearfix mxrateerror' id='categoryPercentageDiv"+id+"'>";
		text += "<input type='hidden' name='categoryPercent"+id+"' id='percentageCatgValidation"+id+"'>";
		text += "</div>";
		text += "<div class='row mxrateerror' id='percentageDiv"+id+"'>";
		text += "<input type='hidden' name='percent"+id+"' id='percentageValidation"+id+"'>";
		text += "</div>";
		text += "</div>";
		$("#selectedTemplatesDiv").append(text);
		return;
  }
  
  
  this.deleteAppraisalCycle = function(templateUUId){
	  $("#appraisalListLoading").attr('style','position:absolute; width:100%; height:200%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
			  type: "POST",
			    url: sourceleadUrl+"/deleteAppraisalCycleTemplateById?templateUUId="+templateUUId,
			     success :function(response) {
			    	 
			    		if (response && (response === "INVALID_DATA" || response === "INVALID_USER")) {
	     	    		    showError("Invalid User");
	     	    		}
			        	else if (response === "AWKWARD") {
			        	    location.href = "/errorPage";
			        	} else if (response === "SUCCESS") {
			        		$("#appraisalListLoading").removeAttr('style');
			        		performanceManagement.viewAppraisalCycle();
			        	} else if (response.indexOf('Sign In') !== -1) {
			        		location.replace(sourceleadUrl);
			        	} else {
			        	    showError("Operation failed, please try again");
			        	}
			    	  
					},
				}); 
 
  }
  
  
  
$(document).on('change', '#performanceCompetence', function(evt, params) {
		if (params.selected != undefined) {
			performanceManagement.updateSelectedCompetenceRows(params.selected, false);
		}
		if (params.deselected != undefined) {
			performanceManagement.updateSelectedCompetenceRows(params.deselected,true);
		}
	});

this.updateSelectedCompetenceRows = function(id,removedFlag){
	  if(id===0 && removedFlag){
			$("#selectedCompetenceDiv").empty();
		} else {	
			$("#performanceCompetence option").each(function () {
				if($(this).val() === id) {
					if(removedFlag) {
						$("#selectedCompetenceDiv").find("div[data-template_id=" + $(this).val() + "]").remove();
						return;
					} else {

						var text="<div class='multiTemplates positionRelative categorySub' id="+$(this).val()+" data-template_id=" + $(this).val() + ">";
						text += "<i class='fa fa-close close_multi_job' onclick='performanceManagement.closeCompetenceDiv(this)'></i>";
						text += "<div class='multiJobTitle categorySubHead clearfix'>";
						text += "" + $(this).text() + "";
						text += "<span class='pull-right mr'><input type='text' onblur='performanceManagement.validateWeightage("+$(this).val()+");' id='weightage"+$(this).val()+"' class='form-control' field-name='Weightage' placeholder='Weightage' data-validation='validate_int required'></span>";
						text += "</div>";
						text += "<div class='row mxrateerror' id='weightageDiv"+$(this).val()+"'>";
						text += "<input type='hidden' name='weightage"+$(this).val()+"' id='weightageValidation"+$(this).val()+"'>";
						text += "</div>";
						text += "</div>";
						$("#selectedCompetenceDiv").append(text);
						return;
					
						
						
					}
				}
			});
		}
}


this.validateWeightage = function(id){

	  var competenceWeightage = $('#weightage'+id).val();
		
		 if(competenceWeightage > 100 || competenceWeightage < 0){
			 if(competenceWeightage < 0){
				    $('#weightageDiv'+id).find('p.jquery_form_error_message').remove(); 
					$('input#weightageValidation'+id).attr('class','error signin_fild');
					$("input#weightageValidation"+id).after("<p class='jquery_form_error_message'>Weightage should not in negitive</p>");
					$('input#weightageValidation'+id).attr('record-exist','yes');
					$('input#weightageValidation'+id).attr('record-exist-errorMsg','Weightage should not in negitive'); 
			 }else{
				    $('#weightageDiv'+id).find('p.jquery_form_error_message').remove(); 
				    $('input#weightageValidation'+id).attr('class','error signin_fild');
				    $('input#weightageValidation'+id).after("<p class='jquery_form_error_message'>Weightage should not more than 100</p>");
				    $('input#weightageValidation'+id).attr('record-exist','yes');
				    $('input#weightageValidation'+id).attr('record-exist-errorMsg','Weightage should not more than 100'); 
			 }
		 }else{
			 
			 $('#weightageDiv'+id).find('p.jquery_form_error_message').remove(); 
			 $('input#weightageValidation'+id).removeAttr( "class record-exist record-exist-errormsg");
			 $('#weightageDiv'+id).find('p.jquery_form_error_message').remove();
		 }

}


this.closeCompetenceDiv = function(ele) {
	$(ele).parent().remove();
	var id = $(ele).parent().data("template_id");
	$('#performanceCompetence').find("option[value='" + id + "']").prop("selected",false).end().trigger('chosen:updated');
}

  

  
  this.performanceCompetenciesList = function(competenciesIds,weightageMap){
	  
	  $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getPerformanceCompetenceListForTagging", 
	        success: function(json) {
	        	var output = "";
	        	for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		if(competenciesIds != null && competenciesIds != '' && competenciesIds != 'undefined'){
		        			var categIds = competenciesIds.split(",");
		        			if (categIds.indexOf(opt.id.toString()) > -1) {
		        				performanceManagement.editSelectedCompetenciesRows(opt.id,opt.name,weightageMap.get(opt.id));
		        				output = output + "<option value='" +opt.id+ "' selected>"+opt.name+"</option>";
		        			}else{
		        				output = output + "<option value='" +opt.id+ "'>"+opt.name+"</option>";
		        			}
		        		}else{
		        			output = output + "<option value='" +opt.id+ "'>"+opt.name+"</option>";
		        		}
		        		
		        		
		        		
				}
	        	$("#performanceCompetence").empty();
		        $("#performanceCompetence").append(output);
		        $('#performanceCompetence').trigger("chosen:updated");
	        },
	    });
  }
  
  
 this.editSelectedCompetenciesRows = function(id,competenceName,weightage){
	    var text="<div class='multiTemplates positionRelative categorySub' id="+id+" data-template_id=" + id + ">";
		text += "<i class='fa fa-close close_multi_job' onclick='performanceManagement.closeCompetenceDiv(this)'></i>";
		text += "<div class='multiJobTitle categorySubHead clearfix'>";
		text += "" + competenceName + "";
		text += "<span class='pull-right mr'><input type='text' value='"+weightage+"' onblur='performanceManagement.validateWeightage("+id+");' id='weightage"+id+"' class='form-control' field-name='Percentage' placeholder='Weightage' data-validation='validate_int required'></span>";
		text += "</div>";
		text += "<div class='row mxrateerror' id='weightageDiv"+id+"'>";
		text += "<input type='hidden' name='weightage"+id+"' id='weightageValidation"+id+"'>";
		text += "</div>";
		text += "</div>";
		$("#selectedCompetenceDiv").append(text);
		return;
  }
  
  
  
 this.saveCompetenceTagging = function(){
	  
	  var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
	  
	  
	  if ($('#tagCompetence_form').validate(false, validationSettings)){ 
		  var ee = $("#txtEditor").val($('.Editor-editor').html()); 
		   var compentencies = [];
			$(".multiTemplates").each(function() {				
				compentencies.push({
					competenceId : $(this).data("template_id") ? $(this).data("template_id") : 0,
					weightage : $("#weightage"+$(this).data("template_id")).val()
				});
			});
			var con = JSON.stringify(compentencies);
			$("#jsonCompetenceString").val(con);
	        var appraisalFormData = getFormData1("tagCompetence_form");
	        $("#tagCompetenceLoading").attr('style','position:absolute; width:100%; height:200%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	        $.ajax({
		    	type: "POST",
		    	 processData: false,
                 contentType: false,
		    	 data: appraisalFormData,
		    	 url: sourceleadUrl+"/savePerformanceTagCompetencies",
		        success: function(response) {
		        	$("#tagCompetenceLoading").removeAttr('style');
		        	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
   	    		    showError(response.responseMessage);
   	    		}
		        	else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		performanceManagement.performanceCompetenceList();
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
  
  $('body').on('blur', '#tagCompetence_form', function() {
		$("#tagCompetence_form").showHelpOnFocus();
		$("#tagCompetence_form").validateOnBlur(false, validationSettings);  
	});
  
}