<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

  <!-- Header Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
       <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/jobsPerformanceReport'/>">Jobs Report</a> &raquo; Applicants List</div>
    </section>
    
    <!-- Project -->
    <div class="timesheet_out">
      <div class="clients_wrap">
	      <div class="positionRelative reportHead clearfix">
	      <span class="pull-left refreIcon" id="advanceSearchDiv">
	      		<a href="javascript:void(0)" class="refeIcon" id="refresh" title="Refresh" onclick="refreshList();"></a>
	      </span>
	      <ul class="pull-right list-unstyled list-inline">
	      	  <li>
	      	  <%-- <jsp:include page="JobCountFilter.jsp"></jsp:include> --%>
	      	  </li>
			  <li class=""><input type="text" class="repSearchBar" placeholder="Search" id="dataTableSearch"><span class="repSearchIcon" onclick="candidateAssignedJobList();"></span></li>
	        </ul>
	      </div>
        <div class="timesheet_head">
       
       </div>
        <div class="clients_wrap_table datatable_filter">
        
        <div id="allStagesApplicantsDiv"></div>
        
        </div>
      </div>
      <div class="side_mask">
        <div class="proj_close"></div>
      </div>
    </div>
  </section>
  
  
  
<script>
 $(document).ready(function() {
	 var statusType = '${statusType}';
	 if(statusType == "Interview"){
		 candidateAssignedJobInterviewList();
	 }else if(statusType == "Shortlisted"){
		 candidateAssignedJobShortlistList(); 
	 }else if(statusType == "Offered"){
		 candidateAssignedJobOfferedList(); 
	 }else if(statusType == "SubmitToClient"){
		 candidateAssignedJobSubmitTOClientList(); 
	 }else if(statusType == "BackOut"){
		 candidateAssignedJobBackOutList(); 
	 }else if(statusType == "Joined"){
		 candidateAssignedJobJoinedList(); 
	 }else if(statusType == "Rejected"){
		 candidateAssignedJobRejectedList(); 
	 }else{
		 candidateAssignedJobAllList(); 
	 }
 });
 
 </script>
 
 <script type="text/javascript">
       function candidateAssignedJobAllList(){
    	   var sourceleadUrl ='${sourceleadUrl}';
   		   var tab = '${tab}';
    	   var frequency = $('input[name=customDates]:checked').val(); 
    	    var fromDate = $("#custFromDate").val();
    	    var toDate = $("#custToDate").val();
    	    var requirementId = '${requirementId}';
    		var searchValue = $("#dataTableSearch").val();
    		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
    		searchValue = searchValue ? searchValue : "";
    	      
    	       var url = sourceleadUrl+"/applicantListForJob?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&requirementId="+requirementId+"&submissionStatus=All+&tabType="+tab;
    	       preparecandidateAssignedList(url);
		
}
</script>
 
 <script type="text/javascript">
       function candidateAssignedJobInterviewList(){
    	   var sourceleadUrl ='${sourceleadUrl}';
   		   var tab = '${tab}';
    	   var frequency = $('input[name=customDates]:checked').val(); 
    	    var fromDate = $("#custFromDate").val();
    	    var toDate = $("#custToDate").val();
    		var searchValue = $("#dataTableSearch").val();
    		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
    		var requirementId = '${requirementId}';
    		searchValue = searchValue ? searchValue : "";
    	     
    	        var url = sourceleadUrl+"/applicantListForJob?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&requirementId="+requirementId+"&submissionStatus=INTERVIEW&tabType="+tab;
    		    preparecandidateAssignedList(url);
		 
}
</script> 


<script type="text/javascript">
       function candidateAssignedJobShortlistList(){
    	   var sourceleadUrl ='${sourceleadUrl}';
   		   var tab = '${tab}';
    	   var frequency = $('input[name=customDates]:checked').val(); 
    	    var fromDate = $("#custFromDate").val();
    	    var toDate = $("#custToDate").val();
    		var searchValue = $("#dataTableSearch").val();
    		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
    		var requirementId = '${requirementId}';
    		searchValue = searchValue ? searchValue : "";
    	     
    	        var url = sourceleadUrl+"/applicantListForJob?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&requirementId="+requirementId+"&submissionStatus=SHORTLIST&tabType="+tab;
    	        preparecandidateAssignedList(url);
		 
}
</script> 

<script type="text/javascript">
       function candidateAssignedJobOfferedList(){
    	   var sourceleadUrl ='${sourceleadUrl}';
   		   var tab = '${tab}';
    	   var frequency = $('input[name=customDates]:checked').val(); 
    	    var fromDate = $("#custFromDate").val();
    	    var toDate = $("#custToDate").val();
    		var searchValue = $("#dataTableSearch").val();
    		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
    		var requirementId = '${requirementId}';
    		searchValue = searchValue ? searchValue : "";
    	     
    	        var url = sourceleadUrl+"/applicantListForJob?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&requirementId="+requirementId+"&submissionStatus=OFFERMADE&tabType="+tab;
    	        preparecandidateAssignedList(url);
		 
}
</script> 

<script type="text/javascript">
       function candidateAssignedJobSubmitTOClientList(){
    	   var sourceleadUrl ='${sourceleadUrl}';
   		   var tab = '${tab}';
    	   var frequency = $('input[name=customDates]:checked').val(); 
    	    var fromDate = $("#custFromDate").val();
    	    var toDate = $("#custToDate").val();
    		var searchValue = $("#dataTableSearch").val();
    		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
    		var requirementId = '${requirementId}';
    		searchValue = searchValue ? searchValue : "";
    	     
    	        var url = sourceleadUrl+"/applicantListForJob?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&requirementId="+requirementId+"&submissionStatus=SUBMIT_TO_CLIENT&tabType="+tab;
    	        preparecandidateAssignedList(url);
		 
}
</script>

<script type="text/javascript">
       function candidateAssignedJobBackOutList(){
    	   var sourceleadUrl ='${sourceleadUrl}';
   		   var tab = '${tab}';
    	   var frequency = $('input[name=customDates]:checked').val(); 
    	    var fromDate = $("#custFromDate").val();
    	    var toDate = $("#custToDate").val();
    		var searchValue = $("#dataTableSearch").val();
    		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
    		var requirementId = '${requirementId}';
    		searchValue = searchValue ? searchValue : "";
    	     
    	        var url = sourceleadUrl+"/applicantListForJob?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&requirementId="+requirementId+"&submissionStatus=BACKOUT&tabType="+tab;
    	        preparecandidateAssignedList(url);
		 
}
</script>

<script type="text/javascript">
       function candidateAssignedJobJoinedList(){
    	   var sourceleadUrl ='${sourceleadUrl}';
   		   var tab = '${tab}';
    	   var frequency = $('input[name=customDates]:checked').val(); 
    	    var fromDate = $("#custFromDate").val();
    	    var toDate = $("#custToDate").val();
    		var searchValue = $("#dataTableSearch").val();
    		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
    		var requirementId = '${requirementId}';
    		searchValue = searchValue ? searchValue : "";
    	     
    	        var url = sourceleadUrl+"/applicantListForJob?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&requirementId="+requirementId+"&submissionStatus=JOINED&tabType="+tab;
    	        preparecandidateAssignedList(url);
		 
}
</script>

<script type="text/javascript">
       function candidateAssignedJobRejectedList(){
    	   var sourceleadUrl ='${sourceleadUrl}';
   		   var tab = '${tab}';
    	   var frequency = $('input[name=customDates]:checked').val(); 
    	    var fromDate = $("#custFromDate").val();
    	    var toDate = $("#custToDate").val();
    		var searchValue = $("#dataTableSearch").val();
    		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
    		var requirementId = '${requirementId}';
    		searchValue = searchValue ? searchValue : "";
    	     
    	        var url = sourceleadUrl+"/applicantListForJob?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&requirementId="+requirementId+"&submissionStatus=REJECTED&tabType="+tab;
    	        preparecandidateAssignedList(url);
		 
}
</script>

<script>
function preparecandidateAssignedList(url){
        var result = "";
        $("#allStagesApplicantsDiv").empty();
        result = result +'<table id="allApplicantsPagination" class="display" cellspacing="0" width="100%" >'; 
        result = result +'<thead>'; 
        result = result + '<tr>';
		result = result + '<th>Applicant name</th>';
		result = result + '<th>Job</th>';
		result = result + '<th>Client</th>';
		result = result + '<th>Applied On</th>';
		result = result + '<th>Source</th>';
		result = result + '<th>Status</th>';
		result = result + '</tr>';
        result = result +'</thead>';
        result = result +'<tbody>'; 
        result = result +' </tbody>';
        result = result +'</table>';
        $("#allStagesApplicantsDiv").empty();
        $("#allStagesApplicantsDiv").append(result);
        preparecandidateAssignedPagination(url);
}
</script>


<script>
function preparecandidateAssignedPagination(url){
	var table = "";
	var colIndex = 0;
	table = $('#allApplicantsPagination').DataTable( { 
    		"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'POST',
            pages: 10
        }),
        "columns": [
            { "data": "applicantName" },
            { "data": "job"},
            { "data": "client"},
            { "data": "appliedOn"},
            { "data": "source"},
            { "data": "status"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "applicantName",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		 if(opt.candidateName !=null && opt.candidateName !='' && opt.candidateName !='undefined'){
		    	  			result = result + ' <td>';
							result = result + ' <div title='+opt.candidateName+' class="clearfix mt5 proj_show"><span><a href="'+sourceleadUrl+'/requirementSummary?type=applicants&requirementUUID='+opt.requirementUUID+'&candidateUUID='+opt.candidateUUID+'&submissionId='+opt.submissionId+'" >'+opt.candidateName+'<i class="fa fa-binoculars"></i></a></span></div>';
							result = result + ' <div title='+opt.email+' class="clearfix mt5 mb5">'+opt.email+'</div>';
							result = result + ' </td>';	
						}else{
							result = result + ' <td><div>--</div></td>';
						}
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "job",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.requirementUUID !=null && opt.requirementUUID !='' && opt.requirementUUID !='undefined'){
            		  if(opt.jobGeneratedId != null && opt.jobGeneratedId != ''  && opt.jobGeneratedId !="undefined"){
                		  result = result + '<td><div class="proj_show"><span title='+opt.jobTitle+'><a href="'+sourceleadUrl+'/requirementSummary?requirementUUID='+opt.requirementUUID+'">'+opt.jobGeneratedId+' - '+opt.jobTitle+'</a></span></div></td>';
            		  }else{
                		  result = result + '<td><div class="proj_show"><span title='+opt.jobTitle+'><a href="'+sourceleadUrl+'/requirementSummary?requirementUUID='+opt.requirementUUID+'">'+opt.jobTitle+'</a></span></div></td>';
            		  }				
            		  }else{
						result = result + ' <td><div>--</div></td>';
					}
            	  return result;
              		
              }
	          },{
            "targets": colIndex++,
            "data": "client",
            "render": function ( data, type, opt, meta ) {
            	var result = "";
            	
   				if(opt.orgLocationName != null && opt.orgLocationName != '' && opt.orgLocationName !='undefined'){
   					result = result + '<td><div><span>'+opt.orgLocationName+' </span></div></td>';
            	 }else{
            		 result = result + '<td><div><span>---</span></div></td>';
            	 }
   				return result;
            }
          }	, {
                "targets": colIndex++,
                "data": "appliedOn",
                "render": function ( data, type, opt, meta ) {
                		var result = "";
                		if(opt.appliedOn !=null && opt.appliedOn !='' && opt.appliedOn !='undefined'){
							result = result + ' <td><div><span>'+opt.appliedOn+'</span></div></td>';
						}else{
							result = result + ' <td><div><span>---</span></div></td>';
						}
    	              return result;
                }
          },{
	    	  	"targets": colIndex++,
	    	  	"data": "source",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.createdUser != null && opt.createdUser !='' && opt.createdUser != 'undefined'){
	    	  			result = result + ' <td><div><span>'+opt.createdUser+'</span></div></td>';
	    	  		}else{
	    	  			result = result + ' <td><div><span>---</span></div></td>';
	    	  		}
	    	  		return result;
	    	  	}
	    	  }, {
                      "targets": colIndex++,
                      "data": "status",
                      "render": function ( data, type, opt, meta ) {
                    	  var result = "";
      	    	  		   if(opt.finalsSubmissionStatus != null && opt.finalsSubmissionStatus !='' && opt.finalsSubmissionStatus != 'undefined'){
      	    	  			result = result + ' <td><div><span>'+opt.finalsSubmissionStatus+'</span></div></td>';
      	    	  		   }else{
      	    	  			result = result + ' <td><div><span>---</span></div></td>';
      	    	  		   }
      	    	  		return result;
                      }
                        } ],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
// 	    	"aaSorting": [[3,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#allApplicantsPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		candidateAssignedJobList();
    	   }
    	});
}

</script>
  
<script>
function refreshList(){
	location.reload();
}

function candidateAssignedJobList(){
	var statusType = '${statusType}';
		   if(statusType == "Interview"){
			 candidateAssignedJobInterviewList();
		 }else if(statusType == "Shortlisted"){
			 candidateAssignedJobShortlistList(); 
		 }else if(statusType == "Offered"){
			 candidateAssignedJobOfferedList(); 
		 }else if(statusType == "SubmitToClient"){
			 candidateAssignedJobSubmitTOClientList(); 
		 }else if(statusType == "BackOut"){
			 candidateAssignedJobBackOutList(); 
		 }else if(statusType == "Joined"){
			 candidateAssignedJobJoinedList(); 
		 }else if(statusType == "Rejected"){
			 candidateAssignedJobRejectedList(); 
		 }else{
			 candidateAssignedJobAllList(); 
		 }
	   }

</script>
  
<jsp:include page="../masterFooter.jsp" />