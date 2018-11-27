var performanceBadge = new PerformanceBadge();

function PerformanceBadge(){
	
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
			        		closeBadgePopup();
			        		getBadgesList();
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
}