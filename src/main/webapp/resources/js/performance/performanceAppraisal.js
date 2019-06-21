var performanceAppraisal = new PerformanceAppraisal();

function PerformanceAppraisal(){
	
	
    this.getTemplateOverView = function(){
    	$.ajax({
  		  type: "GET",
  		    url: pmsUrl+"/getTemplateOverView",
  		     success :function(response) {
  		    	    $("#performanceAppraisalDiv").empty();  
  		    	    $("#appraisalLoadingDiv").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
  		    	    getTemplateOverViewDetails(assignedUUID);
  				},
  			}); 
    }
    
    this.getAppraisalFeedback = function(){
    	$.ajax({
  		  type: "GET",
  		    url: pmsUrl+"/getAppraisalFeedbackDetails",
  		     success :function(response) {
  		    	    $("#performanceAppraisalDiv").empty();  
  		    	    $("#performanceAppraisalDiv").append(response);
  		    	   getAppraisalFeedbackDetails(assignedUUID);
  				},
  			}); 
    }
}