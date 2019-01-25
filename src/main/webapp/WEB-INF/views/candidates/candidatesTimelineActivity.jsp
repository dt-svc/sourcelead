<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>  
 function getActivityDiv(candidateId){
	 var locationId = '${SourceLeadWeb_orgLocationId}';
	 $("#candidateTimelineDiv").empty();
	 var result = "";
	 $.ajax({
         type : "POST",
          url: atsUrl+"/getCandidateActivitiesByCandidateIdAndLocationCode?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&locationId="+locationId,        
          success :function(response) {
        	 	 if (response.response === "AWKWARD") {
        		    location.href = "/errorPage";
        		} else if (response.response === "SUCCESS") {
        			result = result + '<div class="content-area clearfix">';
           		 	result = result + '<div id="accordion-sl" class="release-jobs"> ';
           	       
           	       	result = result + '  <div class="clearfix timeline">';
           	       	/* result = result + '   <h2>11/07/2017</h2>'; */
           	       	result = result + '  <div class="content content-custom clearfix">';
           	       	result = result + '    <div class="timeline_inner">';
           	       	result = result + '      <div class="row mt">';
           	       	result = result + '        <div class="col-md-12"> ';
           	     	var data = response.data; 
                  	if(data && data.length > 0){
               	   		result = result + '          <ul class="timeline">';
                  		for (var i = 0; i < data.length; i++) {
                  			var opt = data[i];
   		   	         		result = result + '      <li> <i class="'+opt.actionIconClass+' bg-blue"></i>';
   		                	result = result + '      <div class="timeline-item"> <span class="time"><i class="fa fa-clock-o"></i> '+opt.actionTimeStr+' </span>';
   		                	result = result + '        <h3 class="timeline-header"><a href="'+atsUrl+'/userSummaryDetails?userId='+opt.createdUserId+'">'+opt.actionBy+'</a> has '+opt.actionDesc+' <a href="#">'+opt.actionOn+'</a></h3>';
   		                	result = result + '        <div class="timeline-body"> '+opt.actionSummary+' </div>';

   		                	/* result = result + '        <div class="timeline-footer"> <a class="btn btn-primary btn-xs">Read more</a></div>'; */
   		                	result = result + '      </div>';
   		                	result = result + '     </li>';
                  		}
                  		result = result + '  </ul>';
	                } else {
	               		result = result + '      <div class="noActivities">';
	               	   	result = result + '      </div>';
	                }
                  	result = result + '  </div>';
              		result = result + ' </div>';
              		result = result + ' </div>';
              		result = result + ' </div>';
              		result = result + ' </div>';
      				result = result + ' </div>';
      				result = result + ' </div>';
      		 		$("#candidateTimelineDiv").empty();
            		$("#candidateTimelineDiv").append(result);
        		} else if (response.indexOf('Sign In') !== -1) {
        			location.replace(sourceleadUrl);
        		} else {
        		    showError("Operation failed, please try again");
        		}
          },
     }); 
 }
</script>

