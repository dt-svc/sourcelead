
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<script>
function getAppraisalGoalsDetails(){
	    
	     document.getElementById("goalsWidget").className = 'activeApprisalwidget'
	     document.getElementById("feedbackWidget").className = 'activeApprisalwidget'
	    	
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
