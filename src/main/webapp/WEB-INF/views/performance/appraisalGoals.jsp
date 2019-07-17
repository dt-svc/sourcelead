
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<script>
function getAppraisalGoalsDetails(){
	     var submissionStage = '${submissionStage}';
	     document.getElementById("goalsWidget").className = 'activeApprisalwidget';
	     document.getElementById("feedbackWidget").className = 'activeApprisalwidget';
	     document.getElementById("tasksWidget").removeAttribute("class");
	     document.getElementById("finalScoreWidget").removeAttribute("class");
	     $("#performanceAppraisalDiv").empty();  
	    	
	     $("#appraisalLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	    
	    	$.ajax({
	  		  type: "GET",
	  		    url: pmsUrl+"/getAssignedGoalsDetailsList?assignedUUID="+assignedUUID,
	  		     success :function(response) {
	  		    	 
	  		    	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
	  	    		    showError(response.responseMessage);
	  	    		    $("#appraisalLoading").removeAttr('style');
	  	    		}
		        	else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		
		        		var result = "";  
		        		
		        		var assignedGoals = 0;
		        		var inprogressGoals = 0;
		        		var completedGoals = 0;
		        		var overDueGoals = 0;
		        		var notStartedGoals = 0;
		        		
		        		if(response.object.toalAssignedGoalsCount != null){
		        			assignedGoals = response.object.toalAssignedGoalsCount;
		        		}
		        		
		        		if(response.object.toalAssignedInprogressGoalsCount != null){
		        			inprogressGoals = response.object.toalAssignedInprogressGoalsCount;
		        		}
		        		
		        		if(response.object.toalAssignedCompletedGoalsCount != null){
		        			completedGoals = response.object.toalAssignedCompletedGoalsCount;
		        		}
		        		
		        		if(response.object.toalAssignedOverDueGoalsCount != null){
		        			overDueGoals = response.object.toalAssignedOverDueGoalsCount;
		        		}
		        		
		        		if(response.object.toalAssignedNotStartedGoalsCount != null){
		        			notStartedGoals = response.object.toalAssignedNotStartedGoalsCount;
		        		}
		        		
		        		
		        	    if(response.object.categoriesJsonString != null 
		       				 && response.object.categoriesJsonString != '' 
		       					 && response.object.categoriesJsonString != 'undefined'){
		       			var data = eval(response.object.categoriesJsonString);
		           		 if (data) {
		           			  $.each( data, function( k, v ) {
		           				  if(v['type'] == "goalsWeightage"){
		               				   	  var goalsWeightage = v['weightage'];
		               				   	  var options = '<option value="">-Select Score-</option>';
		               				   	  for(var i=0; i<=goalsWeightage; i++){
		               				   		var selected = '';
		               				   		 if(response.object.goalsScore != null){
		               				   			if(i == response.object.goalsScore){
		               				   				selected = "selected";
		               				   			}
		               				   		 } 
		               				   		options = options+'<option value="'+i+'" '+selected+'>'+i+'</option>';
		               				   	  }
		               				   	  
	               				    result = result + '<div class="clearfix mb10"><div class="subScoreWrap">';
		   		        		    result = result + '<label>Goals Score : </label>';
		   		        		   
		   		        		 if(submissionStage != 'Submitted_For_Review'){
		   		        		    result = result + '<select class="form-control pull-left" id="goalsScore" onchange="saveGoalsScore(\''+assignedUUID+'\');" name="goalsScore"  field-name="Goals Score">';
		   		        		    result = result + options;
		   		        		    result = result + '</select>';
		   		        		 }else{
		   		        			 if(response.object.goalsScore != null){
		   		        				 result = result + '<input type="text" id="totalTasksScore" disabled value="'+response.object.goalsScore+'" class="form-control pull-left"></div>';
		   		        			 }else{
		   		        				result = result + '<input type="text" id="totalTasksScore" disabled  value="0" class="form-control pull-left"></div>';
		   		        			 }
		   		        		 }
		   		        		    result = result + '</div></div>';
		           						   
		           				  }
		           				 
		           			  });
		           		 }
		       		 }
		        		
	        		   
		        		    
		        		result = result + '<div class="clearfix mt">';
		        		result = result + '<ul class="assiGoalWrap clearfix">';
		        		result = result + '<li>';
		        		result = result + '<div class="goalsIcon"></div>';
		        		result = result + '<p>Total Goals Assigned</p>';
		        		result = result + '<span>'+assignedGoals+'</span>';
		        		result = result + '</li>';
		        		result = result + '<li>';
		        		result = result + '<div class="goalsIcon"></div>'; 
		        		result = result + '<p>In-progress Goals</p>';
		        		result = result + '<span class="inprogC">'+inprogressGoals+'</span>';
		        		result = result + '</li>';
		        		result = result + '<li>';
		        		result = result + '<div class="goalsIcon"></div>';
		        		result = result + '<p>Completed Goals</p>';
		        		result = result + '<span class="compleC">'+completedGoals+'</span>';
		        		result = result + '</li>';
		        		result = result + '<li>';
		        		result = result + '<div class="goalsIcon"></div>';
		        		result = result + '<p>Overdue Goals</p>';
		        		result = result + '<span class="overC">'+overDueGoals+'</span>';
		        		result = result + '</li>';
		        		result = result + '<li>';
		        		result = result + '<div class="goalsIcon"></div>';
		        		result = result + '<p>Goals Not Started</p>';
		        		result = result + '<span class="notC">'+notStartedGoals+'</span>';
		        		result = result + '</li>';
		        		result = result + '</ul>';
		        		result = result + '</div>';
	                    
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



function saveGoalsScore(assignedUUID){
	var goalsScore = $("#goalsScore").val();
	if(goalsScore){
		$("#appraisalLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
			  type: "POST",
			    url: pmsUrl+"/saveAssignedPerformanceFeedback?assignedUUID="+assignedUUID+"&goalsScore="+goalsScore,
			     success :function(response) {
			    	 if (response.response === "SUCCESS") {
			    		 $("#appraisalLoading").removeAttr("style");
			    	 }
			    	 else if (response.response === "AWKWARD") {
			        	    location.href = "/errorPage";
			    	 }
			     },
		}); 
	}

}
</script>
