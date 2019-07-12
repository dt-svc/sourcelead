<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
function getAppraisalFinalScoreDetails(appraisalType){
	
	 document.getElementById("feedbackWidget").className = 'activeApprisalwidget';
	 document.getElementById("finalScoreWidget").className = 'activeApprisalwidget'
	  document.getElementById("tasksWidget").className = 'activeApprisalwidget';
		 if('${isGolasConfigured}' == 'Y'){
			 document.getElementById("goalsWidget").className = 'activeApprisalwidget';
		  }
	 var submissionStage = '${submissionStage}';
	
	 
	 var result = "";  
	 var draft = "Draft";
	 var submit = "Submitted_For_Review";
	 
	 result = result + '<div class="clearfix mt mb">';
	 if(submissionStage != 'Submitted_For_Review'){
		 
		 result = result + '<div class="goalssummar_widget clearfix backgroundGrey">';
		 result = result + '<div class="row">';
		 result = result + '<div class="col-xs-12 col-sm-12 col-md-9 col-lg-9 backgroundWhite">';
	              
		 result = result + '<div class="padding15">';
		
		 result = result + '<div class="clearfix mt mb">';
		 result = result + '<h4 class="text-muted">Self</h4>';
		 result = result + '<div class="clearfix"></div>';
		 result = result + '<div class="row">';
		 result = result + '<div class="col2Custom10">';
		 result = result + '<label class="clearfix col-md-2">Score</label>';
		 result = result + '</div>';
		 result = result + '<div class="clearfix col-md-2">';
		 result = result + '<input type="text" id="selfScore" disabled class="form-control">';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '</div>';
		 
		 result = result + '<div class="clearfix mt mb">';
		 result = result + '<h4 class="text-muted">Peers</h4>';
		 result = result + '<div class="clearfix"></div>';
		 result = result + '<div class="row">';
		 result = result + '<div class="col2Custom10">';
		 result = result + '<label class="clearfix col-md-2">Score</label>';
		 result = result + '</div>';
		 result = result + '<div class="clearfix col-md-2">';
		 result = result + '<input type="text" id="peerScore" disabled class="form-control">';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '</div>';

		 result = result + '<form id="appraisalFinalScore_form" name="appraisalFinalScore_form"  method="post">';
		 result = result + '<div class="clearfix mt mb">';
		 result = result + '<h4 class="text-muted">Manager</h4>';
		 result = result + '<div class="clearfix"></div>';
		 result = result + '<div class="row">';
		 result = result + '<div class="col2Custom10">';
		 result = result + '<label class="clearfix col-md-2">Score</label>';
		 result = result + '</div>';
		 result = result + '<div class="clearfix col-md-2">';
		 result = result + '<input type="text" id="finalScore" disabled class="form-control">';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '<div class="row mt">';
		 result = result + '<div class="col2Custom10">';
		 result = result + '<label class="clearfix col-md-2">Comment</label>';
		 result = result + '</div>';
	                     
		 result = result + '<div class="clearfix col-md-10 colmd10">';
		 result = result + '<textarea class="form-control" rows="4" name="comments" id="comments" field-name="Comments" data-validation="validate_Space required"></textarea>';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '</div>';

		 result = result + '<div class="quetionwidgetFooter clearfix mt">';
		 result = result + '<div class="pull-right">';
		 result = result + '<button type="button" class="btn btn-info" onclick="saveFinalScore(\''+draft+'\',\''+assignedUUID+'\');">Save As Draft</button>';
		 result = result + '<button type="button" class="btn btn-primary mr ml" onclick="saveFinalScore(\''+submit+'\',\''+assignedUUID+'\');">Submit for Review</button>';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '</form>';
		 result = result + '</div>';
		 result = result + '</div>';

		 result = result + '<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3">';
		 result = result + '<div class="clearfix padding15">';
		 result = result + '<h4 class="margin-top0 text-muted">Work History</h4>';
	            
		/*  result = result + '<div class="form-group clearfix mt">';
		 result = result + '<label class="textBold text-muted">Total Working Days</label>';
		 result = result + '<div class="clearfix mt5"></div>';
		 result = result + '<input type="text" class="form-control-custom" value="46/60">';
		 result = result + '</div>'; */

		 result = result + '<div class="form-group clearfix mt">';
		 result = result + '<label class="textBold text-muted">No of Leaves</label>';
		 result = result + '<div class="clearfix mt5"></div>';
		 result = result + '<input type="text" class="form-control-custom" disabled id="appliedLeaves">';
		 result = result + '</div>';
		 result = result + '<div class="form-group clearfix mt">';
		 result = result + '<label class="textBold text-muted">Timesheet Hours</label>';
		 result = result + '<div class="clearfix mt5"></div>';
		 result = result + '<input type="text" class="form-control-custom" disabled id="timeSheetHours">';
		 result = result + '</div>';

		 result = result + '<div class="form-group clearfix mt">';
		 result = result + '<label class="textBold text-muted">Badge</label>';
		 result = result + '<div class="clearfix mt5"></div>';
		 result = result + '<div class="media-left rewardImage">';
		 result = result + '<img src="../images/rewards_badges.svg" class="media-object inlineBlock" alt="Image" style="width: 55px">';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '</div>';
		 
	 }else{
		 
		 
		 result = result + '<div class="goalssummar_widget clearfix backgroundGrey">';
		 result = result + '<div class="row">';
		 result = result + '<div class="col-xs-12 col-sm-12 col-md-9 col-lg-9 backgroundWhite">';
	              
		 result = result + '<div class="padding15">';
		
		 result = result + '<div class="clearfix mt mb">';
		 result = result + '<h4 class="text-muted">Self</h4>';
		 result = result + '<div class="clearfix"></div>';
		 result = result + '<div class="row">';
		 result = result + '<div class="col2Custom10">';
		 result = result + '<label class="clearfix col-md-2">Score</label>';
		 result = result + '</div>';
		 result = result + '<div class="clearfix col-md-2">';
		 result = result + '<input type="text" id="selfScore" disbled class="form-control">';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '</div>';
		 
		 result = result + '<div class="clearfix mt mb">';
		 result = result + '<h4 class="text-muted">Peers</h4>';
		 result = result + '<div class="clearfix"></div>';
		 result = result + '<div class="row">';
		 result = result + '<div class="col2Custom10">';
		 result = result + '<label class="clearfix col-md-2">Score</label>';
		 result = result + '</div>';
		 result = result + '<div class="clearfix col-md-2">';
		 result = result + '<input type="text" id="peerScore" disbled class="form-control">';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '</div>';

		 result = result + '<div class="clearfix mt mb">';
		 result = result + '<h4 class="text-muted">Manager</h4>';
		 result = result + '<div class="clearfix"></div>';
		 result = result + '<div class="row">';
		 result = result + '<div class="col2Custom10">';
		 result = result + '<label class="clearfix col-md-2">Score</label>';
		 result = result + '</div>';
		 result = result + '<div class="clearfix col-md-2">';
		 result = result + '<input type="text" id="finalScore" disabled class="form-control">';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '<div class="row mt">';
		 result = result + '<div class="col2Custom10">';
		 result = result + '<label class="clearfix col-md-2">Comment</label>';
		 result = result + '</div>';
	                     
		 result = result + '<div class="clearfix col-md-10 colmd10">';
		 result = result + '<textarea class="form-control" rows="4" disabled name="comments" id="comments" ></textarea>';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '</div>';

		 result = result + '<div class="quetionwidgetFooter clearfix mt">';
		 result = result + '<div class="pull-right">';
		 result = result + '<button type="button" class="btn btn-info" onclick="saveFinalScore(\''+draft+'\',\''+assignedUUID+'\');">Save As Draft</button>';
		 result = result + '<button type="button" class="btn btn-primary mr ml" onclick="saveFinalScore(\''+submit+'\',\''+assignedUUID+'\');">Submit for Review</button>';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '</div>';

		 result = result + '<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3">';
		 result = result + '<div class="clearfix padding15">';
		 result = result + '<h4 class="margin-top0 text-muted">Work History</h4>';
	            
		/*  result = result + '<div class="form-group clearfix mt">';
		 result = result + '<label class="textBold text-muted">Total Working Days</label>';
		 result = result + '<div class="clearfix mt5"></div>';
		 result = result + '<input type="text" class="form-control-custom" value="46/60">';
		 result = result + '</div>'; */

		 result = result + '<div class="form-group clearfix mt">';
		 result = result + '<label class="textBold text-muted">No of Leaves</label>';
		 result = result + '<div class="clearfix mt5"></div>';
		 result = result + '<input type="text" class="form-control-custom" disabled id="appliedLeaves">';
		 result = result + '</div>';
		 result = result + '<div class="form-group clearfix mt">';
		 result = result + '<label class="textBold text-muted">Timesheet Hours</label>';
		 result = result + '<div class="clearfix mt5"></div>';
		 result = result + '<input type="text" class="form-control-custom" disabled id="timeSheetHours">';
		 result = result + '</div>';

		/*  result = result + '<div class="form-group clearfix mt">';
		 result = result + '<label class="textBold text-muted">Badge</label>';
		 result = result + '<div class="clearfix mt5"></div>';
		 result = result + '<div class="media-left rewardImage">';
		 result = result + '<img src="../images/rewards_badges.svg" class="media-object inlineBlock" alt="Image" style="width: 55px">';
		 result = result + '</div>';
		 result = result + '</div>'; */
		 
		 
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '</div>';
		 
		 
		
	 }
	 result = result + '</div>';
	 
	 $("#performanceAppraisalDiv").empty(); 
	 $("#performanceAppraisalDiv").append(result);
	 
	 getFinalScore(assignedUUID);
	 getAppraisalUserLeavesAndTimesheetDetails(assignedUUID);
   
}
</script>


<script>


function getFinalScore(assignedUUID){
	$("#appraisalLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
		  type: "GET",
		    url: pmsUrl+"/getEmployeePerformanceFeedbackDetails?assignedUUID="+assignedUUID,
		     success :function(response) {
		    	 
		    	 
		    	 if(response.data != null && response.data.length > 0){
		    		 
		    		 for (var i=0; i<response.data.length; i++)
	        	    	{
		    			 var opt = response.data[i];
		     			 if(opt.scoreType == 'Manager'){
		    				 var finalScore = 0;
		    				 if(opt.finalScore != null && typeof opt.finalScore != 'undefined'){
		    					 finalScore = opt.finalScore;
		    				 }
		    				 $("#finalScore").val(finalScore);
		    				
		    				 if(opt.comments != null){
		    		    		 $("textarea[name=comments]").val(opt.comments);
		    		    	 } 
		    			 }else if(opt.scoreType == 'Self'){
		    				 var selfScore = 0;
		    				 if(opt.finalScore != null && typeof opt.finalScore != 'undefined'){
		    					 selfScore = opt.finalScore;
		    				 }
		    				 $("#selfScore").val(selfScore);
		    			 }else if(opt.scoreType == 'Peers'){
		    				 var peerScore = 0;
		    				 if(opt.peersScore != null && typeof opt.peersScore != 'undefined'){
		    					 peerScore = opt.peersScore;
		    				 }
		    				 $("#peerScore").val(peerScore);
		    			 }
		    			 
	        	    	}
		    		 
		    	 }
		    	 
		    	 $("#appraisalLoading").removeAttr("style");
		    	
		    	
		     },
	});
}


function getAppraisalUserLeavesAndTimesheetDetails(assignedUUID){
	$("#appraisalLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
		  type: "GET",
		    url: pmsUrl+"/getAppraisalUserLeavesAndTimesheetDetails?assignedUUID="+assignedUUID,
		     success :function(response) {
		    	 
		    	 if (response.response === "SUCCESS") {
		    		 var appliedLeaves = 0;
		    		 var timeSheetHours = 0;
		    		 if(response.object.appliedLeaves != null){
		    			 appliedLeaves = response.object.appliedLeaves
		    		 }
		    		 if(response.object.timeSheetHours != null){
		    			 timeSheetHours = response.object.timeSheetHours
		    		 }
		    		 $("#appliedLeaves").val(appliedLeaves);
		    		 $("#timeSheetHours").val(timeSheetHours);
					 $("#appraisalLoading").removeAttr("style");
					 
			    	 }
			    	 else if (response.response === "AWKWARD") {
			        	location.href = "/errorPage";
			    	 }
		    	
		     },
	});
}


 function saveFinalScore(submissionStage,assignedUUID){
	   if ($('#appraisalFinalScore_form').validate(false, validationSettings)){
		    var formData = getFormData("appraisalFinalScore_form");
		    $("#appraisalLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
         
         $.ajax({
				type : "POST",
     	        data: formData,
     	        url: pmsUrl+"/saveAssignedPerformanceFeedback?assignedUUID="+assignedUUID+"&submissionStage="+submissionStage,
				success : function(response) {
					
					if (response.response === "SUCCESS") {
						$("#appraisalLoading").removeAttr("style");
			    	 }
			    	 else if (response.response === "AWKWARD") {
			        	location.href = "/errorPage";
			    	 }
				},
			});
	        return true;
		
	  }else{
		return false;
	  } 
}

$('body').on('blur', '#appraisalFinalScore_form', function() {
	$("#appraisalFinalScore_form").showHelpOnFocus();
	$("#appraisalFinalScore_form").validateOnBlur(false, validationSettings);  
}); 

</script>