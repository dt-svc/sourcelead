<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
var assignedUUID = '';
function getAppraisalFinalScoreDetails(performanceAssignedUUID){
	assignedUUID = performanceAssignedUUID;
	 document.getElementById("feedbackWidget").className = 'activeApprisalwidget';
	 document.getElementById("finalScoreWidget").className = 'activeApprisalwidget'
     document.getElementById("tasksWidget").className = 'activeApprisalwidget';
	 document.getElementById("goalsWidget").className = 'activeApprisalwidget';
	 var submissionStage = '${submissionStage}';
	
	 
	 var result = "";  
	 var draft = "Draft";
	 var submit = "Submitted_For_Review";
	 
	 result = result + '<div class="clearfix mt mb">';
		 
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

		 result = result + '<div class="clearfix mt mb">';
		 result = result + '<h4 class="text-muted">Reporting Manager</h4>';
		 result = result + '<div class="clearfix"></div>';
		 result = result + '<div class="row">';
		 result = result + '<div class="col2Custom10">';
		 result = result + '<label class="clearfix col-md-2">Score</label>';
		 result = result + '</div>';
		 result = result + '<div class="clearfix col-md-2">';
		 result = result + '<input type="text" id="reportingMgrFinalScore" disabled class="form-control">';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '<div class="row mt">';
		 result = result + '<div class="col2Custom10">';
		 result = result + '<label class="clearfix col-md-2">Comment</label>';
		 result = result + '</div>';
		 
		 result = result + '<div class="clearfix col-md-10 colmd10">';
		 result = result + '<textarea class="form-control" rows="4" disabled name="reportingMgrComments" id="comments" ></textarea>';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '</div>';
		 
		 
		 result = result + '<div class="clearfix mt mb">';
		 result = result + '<h4 class="text-muted">Resource Manager</h4>';
		 result = result + '<div class="clearfix"></div>';
		 result = result + '<div class="row">';
		 result = result + '<div class="col2Custom10">';
		 result = result + '<label class="clearfix col-md-2">Score</label>';
		 result = result + '</div>';
		 result = result + '<div class="clearfix col-md-2">';
		 result = result + '<input type="text" id="resourceMgrFinalScore" disabled class="form-control">';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '<div class="row mt">';
		 result = result + '<div class="col2Custom10">';
		 result = result + '<label class="clearfix col-md-2">Comment</label>';
		 result = result + '</div>';
	                     
		 result = result + '<div class="clearfix col-md-10 colmd10">';
		 result = result + '<textarea class="form-control" rows="4" disabled name="resourceMgrComments" id="comments" ></textarea>';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '</div>';
		 
		 
		 result = result + '<div class="clearfix mt mb">';
		 result = result + '<h4 class="text-muted">Tasks</h4>';
		 result = result + '<div class="clearfix"></div>';
		 result = result + '<div class="row">';
		 result = result + '<div class="col2Custom10">';
		 result = result + '<label class="clearfix col-md-2">Avg Score</label>';
		 result = result + '</div>';
		 result = result + '<div class="clearfix col-md-2">';
		 result = result + '<input type="text" id="tasksScore" disabled class="form-control">';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '</div>';
		 
		 
		 result = result + '<div class="clearfix mt mb">';
		 result = result + '<h4 class="text-muted">Goals</h4>';
		 result = result + '<div class="clearfix"></div>';
		 result = result + '<div class="row">';
		 result = result + '<div class="col2Custom10">';
		 result = result + '<label class="clearfix col-md-2">Avg Score</label>';
		 result = result + '</div>';
		 result = result + '<div class="clearfix col-md-2">';
		 result = result + '<input type="text" id="goalsScore" disabled class="form-control">';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '</div>';
		 
		 
		 result = result + '<div class="clearfix mt mb">';
		 result = result + '<h4 class="text-muted">Final Score</h4>';
		 result = result + '<div class="clearfix"></div>';
		 result = result + '<div class="row">';
		 result = result + '<div class="col2Custom10">';
		 result = result + '<label class="clearfix col-md-2">Score</label>';
		 result = result + '</div>';
		 result = result + '<div class="clearfix col-md-2">';
		 result = result + '<input type="text" id="appraisalFinalScore" disabled class="form-control">';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '</div>';
		 
		 

		 result = result + '</div>';
		 result = result + '</div>';

		 result = result + '<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3">';
		 result = result + '<div class="clearfix padding15">';
		 result = result + '<h4 class="margin-top0 text-muted">Work History</h4>';
	            

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

		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '</div>';
		 
		 
		
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
		    		 
		    		 var tasksFlagCount = 0;
		    		 var tasksCount = 0;
		    		 
		    		 var goalsFlagCount = 0;
		    		 var goalsCount = 0;
		    		 
		    		 var appraisalFinalScore = 0;
		    		 
		    		 for (var i=0; i<response.data.length; i++)
	        	    	{
		    			 var opt = response.data[i];
		    			 if(opt.scoreType == 'Reporting Manager'){
		    				 var finalScore = 0;
		    				 if(opt.finalAvgScore != null && typeof opt.finalAvgScore != 'undefined'){
		    					 finalScore = opt.finalAvgScore;
		    				 }
		    				 $("#reportingMgrFinalScore").val(finalScore);
		    				
		    				 if(opt.comments != null){
		    		    		 $("textarea[name=reportingMgrComments]").val(opt.comments);
		    		    	 } 
		    				 
		    				 if(opt.tasksAvgScore > 0){
		    					 tasksFlagCount = tasksFlagCount+1;
		    					 tasksCount = tasksCount+opt.tasksAvgScore;
		    				 }
		    				 
		    				 if(opt.goalsAvgScore > 0){
		    					 goalsFlagCount = goalsFlagCount+1;
		    					 goalsCount = goalsCount+opt.goalsAvgScore;
		    				 }
		    				 appraisalFinalScore = appraisalFinalScore+opt.finalAvgScore;
		    				
		    				 
		    			 }else if(opt.scoreType == 'Resource Manager'){
		    				 var finalScore = 0;
		    				 if(opt.finalAvgScore != null && typeof opt.finalAvgScore != 'undefined'){
		    					 finalScore = opt.finalAvgScore;
		    				 }
		    				 $("#resourceMgrFinalScore").val(finalScore);
		    				
		    				 if(opt.comments != null){
		    		    		 $("textarea[name=resourceMgrComments]").val(opt.comments);
		    		    	 } 
		    				 
		    				 if(opt.tasksAvgScore > 0){
		    					 tasksFlagCount = tasksFlagCount+1;
		    					 tasksCount = tasksCount+opt.tasksAvgScore;
		    				 }
		    				 
		    				 if(opt.goalsAvgScore > 0){
		    					 goalsFlagCount = goalsFlagCount+1;
		    					 goalsCount = goalsCount+opt.goalsAvgScore;
		    				 }
		    				 appraisalFinalScore = appraisalFinalScore+opt.finalAvgScore;
		    				 
		    			 }else if(opt.scoreType == 'Self'){
		    				 var selfScore = 0;
		    				 if(opt.finalAvgScore != null && typeof opt.finalAvgScore != 'undefined'){
		    					 selfScore = opt.finalAvgScore;
		    				 }
		    				 $("#selfScore").val(selfScore);
		    				 
		    				 if(opt.tasksAvgScore > 0){
		    					 tasksFlagCount = tasksFlagCount+1;
		    					 tasksCount = tasksCount+opt.tasksAvgScore;
		    				 }
		    				 
		    				 if(opt.goalsAvgScore > 0){
		    					 goalsFlagCount = goalsFlagCount+1;
		    					 goalsCount = goalsCount+opt.goalsAvgScore;
		    				 }
		    				 appraisalFinalScore = appraisalFinalScore+opt.finalAvgScore;
		    				 
		    			 }else if(opt.scoreType == 'Peers'){
		    				 var peerScore = 0;
		    				 if(opt.peersScore != null && typeof opt.peersScore != 'undefined'){
		    					 peerScore = opt.peersScore;
		    				 }
		    				 $("#peerScore").val(peerScore);
		    			 }
		    			 appraisalFinalScore = appraisalFinalScore+opt.peersScore;
		    			 
	        	    	}
		    		 
		    		 
		    		 if(tasksCount > 0){
		    			 var taksAvgCount = tasksCount/tasksFlagCount;
			    		 $("#tasksScore").val(taksAvgCount.toFixed(2));
			    		appraisalFinalScore = appraisalFinalScore+Number(taksAvgCount.toFixed(2));
		    		 }else{
			    		 $("#tasksScore").val(0);
		    		 }
		    		 
		    		 if(goalsCount > 0){
		    			 var goalsAvgCount = goalsCount/goalsFlagCount;
			    		 $("#goalsScore").val(goalsAvgCount.toFixed(2));
			    		 appraisalFinalScore = appraisalFinalScore+Number(goalsAvgCount.toFixed(2));
		    		 }else{
			    		 $("#goalsScore").val(0);
		    		 }
		    		 
		    		 $("#appraisalFinalScore").val(appraisalFinalScore);
		    		 
		    		 
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