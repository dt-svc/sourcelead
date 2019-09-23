
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
/* Ensure that the demo table scrolls fixedColumntable */
</style>
<script type="text/javascript">
	window.onload = function () {
		releasedToVendorJobsList();
	 };
</script>
  <div id="reportJobsLoadingDivId"></div>
    <div class="reportHead">
    	
    
    	<div class="clearfix"></div>
    	 	<div class="candidate_wrap_table positionRelative datatable_filter clearfix">
              <div id="releasedJobsList" class="positionRelative"></div> 
          </div> 
    </div>
  </section>
 

<script>
function releasedToVendorJobsList(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 
	 var orgRelationId = '${orgRelationId}';
	 var searchValue = $("#jobsDataTableSearch").val();
	 var spocId = '${spocId}';
	var	searchValue = searchValue ? searchValue : "";
	  $("#reportJobsLoadingDivId").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({  
        type : "GET",
        url: sourceleadUrl+"/getVendorJobsForReleasedJobReportForList?searchValue="+searchValue+"&orgRelationId="+orgRelationId+"&spocId="+spocId,
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
           	 $("#releasedJobsList").empty();
           	 var result = "";
           	 result = result +"<table id='releasedJobsPaginationId' class='display' cellspacing='0' width='100%' >"; 
                result = result +"<thead>"; 
                result = result +"  <tr>"; 
                result = result +"<th><div>Generated ID</div></th>";
                result = result +"<th><div>Job Title</div></th>";
                result = result +"<th><div>Emp Type</div></th>";
                result = result +"<th><div>Location</div></th>";
                result = result +"<th><div>No of Applicants</div></th>";
                result = result +"<th><div>Owner</div></th>"; 
                result = result +"<th><div>Joined</div></th>"; 
                result = result +"<th><div>Released On</div></th>"; 
                result = result +"<th><div>Status</div></th>"; 
                result = result +"  </tr>"; 
                result = result +"</thead>";
                result = result +"<tbody>"; 
                for (var i = 0; i < response.data.length; i++) { 
               	 var opt = response.data[i];  
               	 result  = result + '         <tr>';
               	 result = result +"<td><div> <span title='"+opt.generatedId+"'>"+opt.generatedId+"</span></div></td>";
               	 result  = result + '<td><div><span title = "'+opt.jobTitle+'">'+opt.jobTitle+'</span></div></td>';
               	 if(opt.jobType != null && opt.jobType != '' && opt.jobType != 'undefined'){
               	 result  = result + ' <td><div><span title="'+opt.jobType+'">'+opt.jobType+'</span></div></td>';
               	 }else{
               		 result  = result + ' <td><div><span>----</span></div></td>';
               	 }
               		result  = result + ' <td>';
               		result = result +'<div class="positionRelative">';
	   		         for(var k=0; k<1; k++){
	   		        	result = result + '<p class="tbldata-dotdot-me pull-left">'+opt.locationsList[k]+'</p>';
	   		         }
 		    	
	   		        result = result +'<div class="show_Empdetails showjoblist">';
 		        	result = result +'<i class="fa fa-ellipsis-v"></i>';

 		        	result = result +'<div class="emailvalues">';
 		        	
						for(var k=0; k<opt.locationsList.length; k++){
							result = result +'<p><i class="fa fa-map-marker"></i>'+opt.locationsList[k]+'</p>';
		   		        	
 		    	 	}
 		        	result = result +'</div>';
 		        	result = result +'</div>';
   		        	result = result +'</div>';
 		        	result  = result + ' </td>';
               	 
               	 if(opt.totalApplicants != null && opt.totalApplicants != '' && opt.totalApplicants != 'undefined'){ 
               	 result  = result + '<td><div><span>'+opt.totalApplicants+'</span></div></td>';
               	 }else{
               		 result  = result + '<td><div><span>0</span></div></td>';
               	 }
               	 result  = result + '<td><div><span title ="'+opt.jobOwner+'">'+opt.jobOwner+'</span></div></td>';
               	 
               	 if(opt.joinedCount != null && opt.joinedCount != '' && opt.joinedCount  != 'undefined'){
                 result  = result + '<td><div><span>'+opt.joinedCount+'</span></div></td>';
                 }else{
                 result  = result + '<td><div><span>0</span></div></td>'; 
                 }
               	 result  = result + ' <td><div><span title="'+opt.releasedDate+'">'+opt.releasedDate+'</span></div></td>';

               	 result  = result + ' <td><div><span title="'+opt.requirementSubmissionStatus+'">'+opt.requirementSubmissionStatus+'</span></div></td>';
               	 
               	 result  = result + '</tr>';
                 } 
                result = result +" </tbody>";
                result = result +"</table>";
                $("#reportJobsLoadingDivId").removeAttr("style");
                $("#releasedJobsList").append(result); 
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
    $('#releasedJobsPaginationId').DataTable( { 
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


