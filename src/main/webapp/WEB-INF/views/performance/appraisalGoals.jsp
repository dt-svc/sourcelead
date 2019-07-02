
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<script>
function getAppraisalGoalsDetails(){
	    
	     document.getElementById("goalsWidget").className = 'activeApprisalwidget';
	     document.getElementById("feedbackWidget").className = 'activeApprisalwidget';
	     document.getElementById("tasksWidget").removeAttribute("class");
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
</script>
