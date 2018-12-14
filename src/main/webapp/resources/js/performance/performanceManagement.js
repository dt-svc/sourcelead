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
    
    this.addNewAppraisalCycle = function(){
    	$.ajax({
  		  type: "GET",
  		    url: sourceleadUrl+"/addNewAppraisalCycle",
  		     success :function(response) {
  		    	    $("#performanceManagementDiv").empty();  
  		    	    $("#performanceManagementDiv").append(response);
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
 	     	    		   // $("#badgeDetailsLoading").removeAttr('style');
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
	
}