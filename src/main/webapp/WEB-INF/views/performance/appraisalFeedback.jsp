

<script>
function getAppraisalFeedbackDetails(){
	    
	    document.getElementById("feedbackWidget").className = 'activeApprisalwidget'
	    	$("#appraisalLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	    
	    	$.ajax({
	  		  type: "GET",
	  		    url: pmsUrl+"/getAssignedFeedbackTemplateList?assignedUUID="+assignedUUID,
	  		     success :function(response) {
	  		    	 
	  		    	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
	  	    		    showError(response.responseMessage);
	  	    		    $("#appraisalLoading").removeAttr('style');
	  	    		}
		        	else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		
		        		var result = "";  
		        	    result = result + '<div class="goalssummar_widget goalssummar_widgetPad clearfix">';
		        	    
		        	    
		        	    if(response.data != null && response.data.length > 0){
		        	    	
		        	    	result = result + '<div class="clearfix">';
			        	    result = result + '<div class="col-md-2 col-md-offset-10">';
			        	    result = result + '<div class="row">';
			        	    result = result + '<label class="col-md-3 mt10">Score</label> <div class="col-md-9"><input type="text" class="form-control"></div>';
			        	    result = result + '</div>';
			        	    result = result + '</div>';
			        	    result = result + '<div class="clearfix"></div>';
			        	    result = result + '</div>';
			        	    
			        	    result = result + '<div class="newRolWrap appraiQue clearfix">';
			        	    result = result + '<div class="newRolLeft">';
			        	    result = result + '<ul id="nav" class="clearfix">';
			        	    
			        	    for (var i=0; i<response.data.length; i++)
			        	    	{
			        	    	 var opt = response.data[i];
				        	        result = result + '<li><a href="#">'+opt.templateName+'</a></li>';
			        	    	}
			        	
			        	    result = result + '</ul>';
			        	    result = result + '</div>';
			        	    result = result + '</div>';
		        	    }else{
		        	    	 result = result + '<div class="odd"><div valign="top" colspan="4" class="dataTables_empty"></div></div>';
		        	    }

		        	    $("#performanceAppraisalDiv").empty(); 
		        	    $("#performanceAppraisalDiv").append(result);
		        	    
		        	    $("#appraisalLoading").removeAttr("style");
		        		
		        	}else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
	  			},
			}); 
	    
		
}
</script>