<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
function getAppraisalFinalScoreDetails(appraisalType){
	
	 document.getElementById("feedbackWidget").className = 'activeApprisalwidget';
	 document.getElementById("finalScoreWidget").className = 'activeApprisalwidget'
	 
	  if(appraisalType == 'self'){
		  document.getElementById("tasksWidget").className = 'activeApprisalwidget';
			 if('${isGolasConfigured}' == 'Y'){
				 document.getElementById("goalsWidget").className = 'activeApprisalwidget';
			  }
	  }
	 var submissionStage = '${submissionStage}';
	
	 
	 var result = "";  
	 var draft = "Draft";
	 var submit = "Submitted_For_Review";
	 
	 result = result + '<div class="clearfix mt mb">';
	 if(submissionStage != 'Submitted_For_Review'){
		 result = result + '<form id="appraisalFinalScore_form" name="appraisalFinalScore_form"  method="post">';
		 result = result + '<div class="clearfix col-md-8">';
		 result = result + '</div>';
		 result = result + '<div class="col-md-8 mb">';
		 result = result + '<h4>Final Score</h4>';
		 result = result + '<div class="row">';
		 result = result + '<label class="clearfix col-md-2">Score</label>';
		 result = result + '<div class="clearfix col-md-4">';
		 result = result + '<input type="text" id="finalScore" disabled class="form-control">';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '<div class="clearfix"></div>';
		 result = result + '<div class="col-md-8 mb">';
		 result = result + '<div class="row">';
		 result = result + '<label class="clearfix col-md-2">Comments</label>';
		 result = result + '<div class="clearfix col-md-9">';
		 result = result + '<textarea class="form-control" rows="4" name="comments" id="comments" field-name="Comments" data-validation="validate_Space required"></textarea>';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '<div class="clearfix"></div>';
		 result = result + '<div class="quetionwidgetFooter clearfix mt">';
		
		 result = result + '<div class="pull-right">';
		 result = result + '<button type="button" class="btn btn-info" onclick="saveFinalScore(\''+draft+'\',\''+assignedUUID+'\');">Save As Draft</button>';
		 result = result + '<button type="button" class="btn btn-primary mr ml" onclick="saveFinalScore(\''+submit+'\',\''+assignedUUID+'\');">Submit for Review</button>';
		 result = result + '</div>';
		 result = result + '</form>';
	 }else{
		 result = result + '<div class="col-md-8 mb">';
		 result = result + '<h4>Final Score</h4>';
		 result = result + '<div class="row">';
		 result = result + '<label class="clearfix col-md-2">Score</label>';
		 result = result + '<div class="clearfix col-md-4">';
		 result = result + '<input type="text" id="finalScore" disabled class="form-control">';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '<div class="clearfix"></div>';
		 result = result + '<div class="col-md-8 mb">';
		 result = result + '<div class="row">';
		 result = result + '<label class="clearfix col-md-2">Comments</label>';
		 result = result + '<div class="clearfix col-md-9">';
		 result = result + '<textarea class="form-control" rows="4" disabled name="comments" id="comments" field-name="Comments" data-validation="validate_Space required"></textarea>';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '</div>';
		 result = result + '<div class="clearfix"></div>';
	 }
	 result = result + '</div>';
	 
	 $("#performanceAppraisalDiv").empty(); 
	 $("#performanceAppraisalDiv").append(result);
	 
	 getFinalScore(assignedUUID);
   
}
</script>


<script>


function getFinalScore(assignedUUID){
	$.ajax({
		  type: "GET",
		    url: pmsUrl+"/getAppraisalFeedbackDetails?assignedUUID="+assignedUUID,
		     success :function(response) {
		    	 
		    	 var finalScore = 0;
		    	 if(response.object.finalScore != null){
		    		 finalScore = response.object.finalScore;
		    	 }
		    	 $("#finalScore").val(finalScore);
		    	 
		    	 if(response.object.comments != null){
		    		 $("textarea[name=comments]").val(response.object.comments);
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