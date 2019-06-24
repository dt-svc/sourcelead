

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
			        	    result = result + '<div class="newRolLeft" >';
			        	    result = result + '<ul id="feedbackTemplate" class="clearfix">';
			        	    
			        	    for (var i=0; i<response.data.length; i++)
			        	    	{
			        	    	 var opt = response.data[i];
				        	        result = result + '<li rel="'+opt.feedbackTemplateUUID+'"><a id="feedbackTemplate'+opt.feedbackTemplateUUID+'" value="'+opt.feedbackTemplateUUID+'" onclick="getFeedbackCategoryForm(\''+opt.feedbackTemplateUUID+'\');">'+opt.templateName+'</a></li>';
			        	    	}
			        	
			        	    result = result + '</ul>';
			        	    result = result + '</div>';
			        	    
			        	    result = result + '<div class="newRolLRight">';
			        	    result = result + '<div class="clearfix" id="tab-content">';
			        	    result = result + '<div id="feedBackCategoryTemplateDiv">';
			                result = result + '</div>';
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

<script>
function getFeedbackCategoryForm(feedbackTemplateUUID){
	
	
	$('#feedbackTemplate li').each(function(){
		document.getElementById("feedbackTemplate"+$(this).attr('rel')).removeAttribute("class")
	});
	
	document.getElementById("feedbackTemplate"+feedbackTemplateUUID).className = 'active'
	
	var result = "";  
	result = result + '<div id="tab1" class="appraQueOut">';
	result = result + '<span class="appraQueInn">';
	result = result + '<h3><b>Q</b>Question Text here...'+feedbackTemplateUUID+'</h3>';
	result = result + '<ul class="clearfix">';
	result = result + '<li class="clearfix">';
	result = result + '<input id="a1" value=" " class="checkbox-custom" name="a" type="radio">';
	result = result + '<label for="a1" class="checkbox-custom-label paddingright">Answer 1</label>';
    result = result + '</li>';
    result = result + '<li class="clearfix">';
    result = result + '<input id="a2" value=" " class="checkbox-custom" name="a" type="radio">';
    result = result + '<label for="a2" class="checkbox-custom-label paddingright">Answer 2</label>';
    result = result + '</li>';
    result = result + '<li class="clearfix">';
    result = result + '<input id="a3" value=" " class="checkbox-custom" name="a" type="radio">';
    result = result + '<label for="a3" class="checkbox-custom-label paddingright">Answer 3</label>';
    result = result + '</li>';
    result = result + '<li class="clearfix">';
    result = result + '<input id="a4" value=" " class="checkbox-custom" name="a" type="radio">';
    result = result + '<label for="a4" class="checkbox-custom-label paddingright">Answer 4</label>';
    result = result + '</li>';
    result = result + '</ul>';
    result = result + '</span>';
    $("#feedBackCategoryTemplateDiv").empty(); 
    $("#feedBackCategoryTemplateDiv").append(result);
	
}
</script>