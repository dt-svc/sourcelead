
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link href="/resources/css/jquery-tagit.css" rel="stylesheet">
<script src="/resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<script src="/resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="/resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>
<!-- end files for autocomplete with tagit -->

<style>
#billing_chartdiv {
  	width:100%; height:300px;
}
/* Ensure that the demo table scrolls */
</style>
<script type="text/javascript">
	window.onload = function () {
		vendorJobsApplicantsDeclineList();
	 };
</script>
 
    <div class="reportHead">
    	
    
    	<div class="clearfix"></div>
    	 	<div class="candidate_wrap_table recruiterReports_table fixedColumntable minHeight positionRelative datatable_filter dashboard-widget clearfix">
              <div id="releasedApplicantsList" class="filter_position positionRelative"></div> 
          </div> 
    </div>
  </section>
 

<script>
function vendorJobsApplicantsDeclineList(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 
	 var orgRelationId = '${orgRelationId}';
	 var searchValue = $("#jobsDataTableSearch").val();
	 var spocId = '${spocId}';
	var	searchValue = searchValue ? searchValue : "";
	$.ajax({  
        type : "GET",
        url: sourceleadUrl+"/getApplicantDeclineListForVendor?orgRelationId="+orgRelationId+"&spocId="+spocId+"&status=REJECTED",
		 success :function(response) {
     	  	if (response && response.response === "INVALID_DATA") {
     		    showError(response.responseMessage);
     		} else if (response && response.response === "INVALID_USER") {
     		    showError(response.responseMessage);
     		} else if (response.response === "AWKWARD") {
     		    location.href = "/errorPage";
     		} else if (response.response === "SUCCESS") {
     			if (response.data.redirect) {
		        		  window.location.href = response.data.redirect;
		        	  }
           	 $("#releasedApplicantsList").empty();
           	 var result = "";
           	 result = result +"<table id='releasedApplicantsPaginationId' class='display' cellspacing='0' width='100%' >"; 
                result = result +"<thead>"; 
                result = result +"  <tr>"; 
                result = result +"<th><div>Applicant Name</div></th>";
                result = result +"<th><div>Email Id</div></th>";
                result = result +"<th><div>Job</div></th>";
                result = result +"<th><div>Submitted On</div></th>";
                result = result +"<th><div>Previous Stage</div></th>";
                result = result +"<th><div>Rejected Category</div></th>"; 
                result = result +"  </tr>"; 
                result = result +"</thead>";
                result = result +"<tbody>"; 
                for (var i = 0; i < response.data.length; i++) { 
               	 var opt = response.data[i];  
               	 result  = result + '         <tr>';
               	 result = result +"<td><div> <span title='"+opt.candidate.firstName+"'>"+opt.candidate.firstName+"</span></div></td>";
               	 result  = result + '<td><div><span title = "'+opt.candidate.email+'">'+opt.candidate.email+'</span></div></td>';
               	 result  = result + ' <td><div><span title="'+opt.requirement.jobTitle+'">'+opt.requirement.jobTitle+'</span></div></td>';
               	 
               		 result  = result + '<td><div><span title="'+opt.submittedOn+'">'+opt.submittedOn+'</span></div></td>';
               	 result  = result + '<td><div><span title="'+opt.previousSubmissionStatus+'">'+opt.previousSubmissionStatus+'</span></div></td>';
               	 
               	 
                 result  = result + '<td><div><span title="'+opt.rejectedCategoryType+'">'+opt.rejectedCategoryType+'</span></div></td>'; 
               	 
               	 result  = result + '</tr>';
                 } 
                result = result +" </tbody>";
                result = result +"</table>";
                $("#releasedApplicantsList").append(result); 
                releasedJobsPagination(); 
     		} else if (response.indexOf('Sign In') !== -1) {
     			location.replace(sourceleadUrl);
     		} else {
     		    showError("Operation failed, please try again");
     		}
     },
	 }); 
}
</script>


<script>

function releasedJobsPagination(){  
    $('#releasedApplicantsPaginationId').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}

</script>


