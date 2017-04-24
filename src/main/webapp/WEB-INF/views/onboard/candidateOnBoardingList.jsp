<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link rel="stylesheet" href="resources/css/chosen.min.css">
<script src="resources/js/chosen.jquery.min.js"></script> 
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<script> 
	$(document).ready(function(){
		
		$("#onboardCount").html('${onboardCount}');
		$("#offboardCount").html('${offboardCount}');
		
		getOnboardCandidatesList();
		
	});
</script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Onboard & Offboard Dashboard</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='/usersList'/>">Users List</a> &raquo; Onboard & Offboard List </div>
 </section>
 
 <div class="content-area content-new clearfix">
      <div class="onboardDashboard_counts">
        <div class="tabbable positionRelative">
          <div class="onboardcreateButtons"> 
	          <a href="<c:url value='/candidateOnBoardCreation'/>" class="btn btn-success btn-sm mr" title="Create onboard">
	          	<i class="fa fa-plus paddingRight"></i>Create onboard
	          </a> 
	          <a href="<c:url value='/candidateOffBoardCreation'/>" class="btn btn-primary btn-sm" title="Create offboard">
	          	<i class="fa fa-plus paddingRight"></i>Create offboard
	          </a>
          </div>
          <ul class="nav nav-tabs" id="tab_bar">
            
            <li class="active"> <a href="#" data-toggle="tab" class="onboardCount" onclick="getOnboardCandidatesList();" title="Current onboard count">
              <div class="count" id="onboardCount"></div>
              <h4>Current onboard</h4>
              </a> 
            </li>
            
            <li> <a href="#documents" data-toggle="tab" class="offboardCount" onclick="getOffboardCandidatesList();" title="Current offboard count">
              <div class="count" id="offboardCount"></div>
              <h4>Current offboard</h4>
              </a> 
            </li>
          </ul>
          </div>
          </div>
        <div class="clearfix"></div>  
          
          
          <div class="candidate_wrap_table">
	          <div id="onboardingLoading"></div>
	          <div id="onboardingCandidatesList"></div>
	          <div id="offboardingCandidatesList"></div>
          </div>
        
      </div>
    </div>
 </section>
 
 
 <script>
 function getOnboardCandidatesList(){
	 
	  var sourceleadUrl ='${sourceleadUrl}';
	  $("#onboardingLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getOnboardCandidateList?${_csrf.parameterName}=${_csrf.token}",    
            success :function(data) {
               		
			    	     $("#onboardingLoading").removeAttr("style");
	                	 $("#onboardingCandidatesList").empty();
	                	 $("#offboardingCandidatesList").empty();
	                	 
	                	 var result = "";
	                	 result = result +"<table id='onboardingPagination' class='display' cellspacing='0' width='99.9%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>Candidate Name</th>";
	                     result = result +"    <th>Type</th>";
	                     result = result +"    <th>Date Of Join</th>";
	                     result = result +"    <th>Onboard<br/>Documents</th>";
	                     result = result +"    <th>Stage</th>"; 
	                     result = result +"    <th>Candidate Onboard Stage</th>"; 
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     var json = JSON.parse(data);
	                     
	                    	 for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i]; 
	                    	 var joiningDate = '';
	                    	 
	                    	 if(opt.joiningDateStr != null && opt.joiningDateStr != '' && opt.joiningDateStr != 'undefined'){
	                    		 joiningDate = opt.joiningDateStr;
	                    	 }else{
	                    		 joiningDate = "----";
	                    	 }
	                    	     
	                    	 result = result +"<tr>";
	                    	 
	                    	 result = result +"<td>";
	                    	 result = result +"<div class='width300px'>";
	                    	 result = result +"<div class='tbldata-dotdot-big pull-left'>"+opt.candidateName+"</div>";
	                    	 
	                    	 if(opt.candidateOnBoardStatus == 'Draft'){
	                    		 result = result +"<b class='pull-right proj_show'> <a href='"+sourceleadUrl+"/viewOnboardCandidateDetails?onboardUUID="+opt.onboardCandidateUUID+"'> <i class='fa fa-binoculars'></i> </a> </b> </div>";
	                    	 }else if(opt.candidateOnBoardStatus == 'Finished'){
	                    		 result = result +"<b class='pull-right proj_show'> <a href='"+sourceleadUrl+"/viewOnboardCandidateSummary?onboardUUID="+opt.onboardCandidateUUID+"'> <i class='fa fa-binoculars'></i> </a> </b> </div>";
	                    	 }
	                    	 
	                    	 
	                    	 result = result +"</td>";
	                    	 
	                    	 
                    		 result = result +"<td><div><span>Onboard</span></div></td>";
                    		 result = result +"<td><div><span>"+joiningDate+"</span></div></td>";
                    		 result = result +"<td><div><span>"+opt.documentsAcceptedCount+" / "+opt.documentsCount+"</span></div></td>";
                    		 result = result +"<td><div><span>"+opt.candidateOnBoardStatus+"</span></div></td>";
                    		 
                    		 if(opt.onboardSubmitted == 'Y'){
                    			 result = result +"<td><div><span class='text-success'>Submitted</span></div></td>";
                    		 }else{
                    			 result = result +"<td><div><span class='text-warning'>Pending</span></div></td>";
                    		 }
                    
		                     
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
 function getOffboardCandidatesList(){
	 
	  var sourceleadUrl ='${sourceleadUrl}';
	  $("#onboardingLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getOffboardCandidateList?${_csrf.parameterName}=${_csrf.token}",    
            success :function(data) {
               		
			    	     $("#onboardingLoading").removeAttr("style");
	                	 $("#onboardingCandidatesList").empty();
	                	 $("#offboardingCandidatesList").empty();
	                	 
	                	 var result = "";
	                	 result = result +"<table id='offboardingPagination' class='display' cellspacing='0' width='99.9%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>Candidate Name</th>";
	                     result = result +"    <th>Type</th>";
	                     result = result +"    <th>Date Of Join</th>";
	                     result = result +"    <th>End Date</th>";
	                     result = result +"    <th>Stage</th>"; 
	                     result = result +"    <th>Candidate Offboard Stage</th>"; 
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     var json = JSON.parse(data);
	                     
	                    	 for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i]; 
	                    	 var joiningDate = '';
	                    	 
	                    		                    	     
	                    	 result = result +"<tr>";
	                    	 
	                    	 result = result +"<td>";
	                    	 result = result +"<div class='width300px'>";
	                    	 result = result +"<div class='tbldata-dotdot-big pull-left'>"+opt.candidateName+"</div>";
	                    	 
	                    	 if(opt.offBoardStatus == 'Draft'){
	                    		 result = result +"<b class='pull-right proj_show'> <a href='"+sourceleadUrl+"/viewOffboardCandidateDetails?offboardUUID="+opt.offboardUUID+"'> <i class='fa fa-binoculars'></i> </a> </b> </div>";
	                    	 }else if(opt.offBoardStatus == 'Finished'){
	                    		 //result = result +"<b class='pull-right proj_show'> <a href='"+sourceleadUrl+"/viewOnboardCandidateSummary?onboardUUID="+opt.offboardUUID+"'> <i class='fa fa-binoculars'></i> </a> </b> </div>";
	                    	 }
	                    	 
	                    	 
	                    	 result = result +"</td>";
	                    	 
	                    	 
                    		 result = result +"<td><div><span>Offboard</span></div></td>";
                    		 result = result +"<td><div><span>"+opt.dateOfJoinStr+"</span></div></td>";
                    		 result = result +"<td><div><span>"+opt.endDateStr+"</span></div></td>";
                    		 result = result +"<td><div><span>"+opt.offBoardStatus+"</span></div></td>";
                    		 
                    		 if(opt.offboardSubmitted == 'Y'){
                    			 result = result +"<td><div><span class='text-success'>Submitted</span></div></td>";
                    		 }else{
                    			 result = result +"<td><div><span class='text-warning'>Pending</span></div></td>";
                    		 }
                    
		                     
		                     result = result +"</tr>";
		                     result = result +"</tr>";
	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#onboardingLoading").removeAttr("style");
	                     $("#offboardingCandidatesList").append(result); 
	                     
	                     offboardingPagenation(); 
                    
          },
            
       }); 
 }
 </script>
 
 
 <script>
function onboardingPagenation(){ 
	 $('#onboardingPagination').DataTable( { 
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20, -1], [2, 15, 20, "All"]],
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

<script>
function offboardingPagenation(){ 
	 $('#offboardingPagination').DataTable( { 
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20, -1], [2, 15, 20, "All"]],
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
 
    
 <jsp:include page="../masterFooter.jsp" />