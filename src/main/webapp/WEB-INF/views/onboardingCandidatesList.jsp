<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>



<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="resources/js/formvalidator.js"></script>

<div class="content-dashboard clearfix"> 
      <div class="clearfix dashboard-widget mb col-md-12 hdrPosition">
        <h4 class="clearfix">
        Onboard Candidates
        &nbsp;&nbsp;
        </h4>
        <div class="clearfix require_wrap_table dashboard-data filter_position"> 
                 <div id="onboardingLoading"></div>
        		 <div id="onboardingCandidatesList"></div>
        </div>
      </div>
    </div>
    
<script> 
$(document).ready(function(){
	onboardingCandidatesList();
});
</script>

<script>
function onboardingCandidatesList(){  
	  var sourceleadUrl ='${sourceleadUrl}';
	  $("#onboardingLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getOnboardingCandidatesList?${_csrf.parameterName}=${_csrf.token}",    
            success :function(data) {
               		
			    	     $("#onboardingLoading").removeAttr("style");
	                	 $("#onboardingCandidatesList").empty();
	                	 var result = "";
	                	 result = result +"<table id='onboardingPagination' class='display' cellspacing='0' width='99.9%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>Candidate Name</th>";
	                     result = result +"    <th>Email</th>";
	                     result = result +"    <th>Title</th>";
	                     result = result +"    <th>DOJ</th>"; 
	                     result = result +"    <th>Rate</th>"; 
	                     result = result +"    <th>Position Type</th>";
	                     result = result +"    <th>Onboard Status</th>";
	                     '<sec:authorize access="hasRole(\'ROLE_VIEW_EDIT_ON_BOARD_USER\')">'
	                    	 result = result +"<th>Actions</th>";
	                     '</sec:authorize>';
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     var json = JSON.parse(data);
	                     
	                    	 for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i]; 
	                    	 result = result +"<tr>";
	                    	 
	                    		 result = result +"<td><div class='tbldata-dotdot-sm'><span title='"+opt.candidateName+"'>"+opt.candidateName+"</span></div></td>";
	                    		
	                    		 result = result +"<td><div><span title='"+opt.candidateEmail+"'>"+opt.candidateEmail+"</span></div></td>";
	                    		 
	                    		 result = result +"<td><div class='tbldata-dotdot-sm'> <a href='"+sourceleadUrl+"/requirementSummary?requirementUUID="+opt.requirementUUID+"'><span title='"+opt.requirementName+"'>"+opt.requirementName+"</span></a></div></td>";
		                    
	                    		 result = result +"<td><div><span>"+opt.offerJoinDate+"</span></div></td>";
	                    		 
			                     if(opt.rate != null && opt.rate != '' && opt.rate != 'undefined'){
				                     result = result +"<td><div><span>"+opt.paymentType+" "+opt.rate+" / "+opt.rateType+"</span></div></td>";
			                     }else{
				                     result = result +"<td><div><span>-----</span></div></td>";
			                     }
		                     
			                     if(opt.positionType != null && opt.positionType != '' && opt.positionType != 'undefined'){
			                    	 result = result +"<td><div><span>"+opt.positionType+"</span></div></td>";
			                     }else{
				                     result = result +"<td><div><span>-----</span></div></td>";
			                     }
			                     
			                     if(opt.onboardStatus != null && opt.onboardStatus != '' && opt.onboardStatus != 'undefined'){
			                    	 result = result +"<td><div><span>"+opt.onboardStatus+"</span></div></td>";
			                     }else{
				                     result = result +"<td><div><span>Ready to onboard</span></div></td>";
			                     }
			                     
			                     '<sec:authorize access="hasRole(\'ROLE_VIEW_EDIT_ON_BOARD_USER\')">'
				                     if(opt.onboardUUId != null && opt.onboardUUId != '' && opt.onboardUUId != 'undefined'){
				                    	 if(opt.onboardStatus == 'Draft'){
				                    		 result = result +"<td><div class='tbldata-dotdot-sm'> <a href='"+sourceleadUrl+"/viewOnboardCandidateDetails?onboardUUID="+opt.onboardUUId+"'> <span class='text-success'>View Onboard</span> </a> </div></td>";
				                    	 }else if(opt.onboardStatus == 'Finished' || opt.onboardStatus == 'Submitted'){
				                    		 result = result +"<td><div class='tbldata-dotdot-sm'> <a href='"+sourceleadUrl+"/viewOnboardCandidateSummary?onboardUUID="+opt.onboardUUId+"'> <span class='text-success'>View Onboard</span> </a> </div></td>";
				                    	 }
				                    	
				                    }else{
				                    	result = result +"<td><div class='tbldata-dotdot-sm'><a href='"+sourceleadUrl+"/candidateOnBoardingDetails?candidateUUId="+opt.candidateUUID+"&offerUUID="+opt.offerUUID+"'><span class='text-success'>Onboard</span></a></div></td>";
				                    }
			                     '</sec:authorize>';  

		                     
		                     result = result +"</tr>";
		                     result = result +"</tr>";
	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#onboardingLoading").removeAttr("style");
	                     $("#onboardingCandidatesList").append(result); 
	                     
	                     onboardingPagenation(); 
                    
          },
            
       }); 
  } 
</script>   

<script>
function onboardingPagenation(){ 
	 $('#onboardingPagination').DataTable( { 
	    	"scrollCollapse": false,
	    	"lengthMenu": [[5, 15, 20, -1], [2, 15, 20, "All"]],
	    	"bLengthChange": false,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	            "sSearch": "",
	            "sZeroRecords": "", "sEmptyTable": ""
	       }
	    } );
}
</script>

