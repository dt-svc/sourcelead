
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

<script type="text/javascript">
	window.onload = function () {
		vendorJobsApplicantsDeclineList();
	 };
</script>
 <div id="reportLoadingDivId"></div>
    <div class="reportHead">
    
    	<div class="clearfix"></div>
    	 	<div class="candidate_wrap_table positionRelative datatable_filter clearfix">
              <b class='col-md-12 mb10 mt10'>Vendor Decline Candidates</b>
              <div id="releasedApplicantsList" class="positionRelative"></div> 
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
	  $("#reportLoadingDivId").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');

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
                $("#reportLoadingDivId").removeAttr("style");
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


