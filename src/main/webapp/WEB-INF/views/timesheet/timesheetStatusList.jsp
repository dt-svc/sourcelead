<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<link href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css" rel="stylesheet">

<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> 

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png"
	href="resources/images/fav.png" />
<link
	href="https://fonts.googleapis.com/css?family=Roboto:400,300italic,300,400italic,500,700,900"
	rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/register-style.css" rel="stylesheet">
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="resources/js/formvalidator.js"></script>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/register-style.css" rel="stylesheet">
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/register-style.css" rel="stylesheet">
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<div class="wraper">
	<!-- Content Area -->
	<section class="content-wraper">
		<section class="content-header clearfix">
			<div class="pull-left">
				<h2>Timesheet Submissions List</h2>
			</div>
		</section>

		
		
		  <div id="projectFilterList"></div>
		    <div class="timesheet_appr_table">
		    				<table id="example" class="display" cellspacing="0" width="100%">
							</table>
			</div>	  

</section>
</div>
<div id="TimeshgeetStatusListId"></div>
<script type="text/javascript">
window.onload = function () {
	filter();
 };
</script>



 <script type="text/javascript">
function filter(){
	var status = '${status}';
	 var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getTimesheetListByStatus?${_csrf.parameterName}=${_csrf.token}&status="+status,
		        success: function(data) {
		        
		        	var json = JSON.parse(data);
		         	var result = "";
		         	
		         	result = result +'<thead>';
	            	result = result +'<tr>';
	                result = result +'<th>Project name</th>';
	                result = result +'<th>Frequency</th>';
	                result = result +'<th>Employee</th>';
	                result = result +'<th>Appprovers</th>';
	                result = result +'<th>Total Hours</th>';
	                result = result +'<th>Status</th>';
	                result = result +'</tr>';
				    result = result +'</thead>';
				    
			         	for (var i=0; i<json.length; i++){
			         		var report = json[i];
			         					result = result +'<tr>';
			         					result = result +'<td><div>'+report.projectName+'</div></td>';
			         					result = result +'<td data-title="Date"><div>'+report.startDate+' ';
			           	   		    	result = result +'- '+report.endDate+' </div></td>';
			         					result = result +'<td><div>'+report.employeeName+'</div></td>';
			         					result = result +'<td><div>';
			         					if(report.managerLevel1 != null && report.managerLevel1 !=''){
		         	                    	result = result + ''+report.managerLevel1+'';
			         					}
			         					if(report.managerLevel2 != null && report.managerLevel2 !='' && report.managerLevel1 != null && report.managerLevel1 !=''){
			         						result = result + ',';
			         					}
			         					if(report.managerLevel2 != null && report.managerLevel2 !=''){
		         	                    	result = result +''+report.managerLevel2+'';
			         					}
			         					result = result +'</div></td>';
			         					if(report.totalHours != null && report.totalHours !=''){
			         						result = result +'<td><div>'+report.totalHours+'</div></td>';
			         					}else{
			         						
			         						result = result +'<td><div>'+0.0+'</div></td>';
			         					}
			         					if(report.submissionApprovalStatus == 'APPROVED' || report.submissionApprovalStatus == 'REJECTED' ){
			         						 result = result +'<td><div><h3>'+report.submissionApprovalStatus+'</h3></div>';
			         					}
			         					if(report.submissionStatus == 'NEW' || report.submissionStatus == 'SUBMITTED' || report.submissionStatus == 'DRAFT'){
			         					   result = result +'<td><div><h3>'+report.submissionStatus+'</h3></div>';
			         					}
			         					 result = result +'</td>';
			         					 result = result +' </tr>';
			         	                   
			         					}
		         	                result = result +' </tbody>';
		         	                result = result +'</table>';

		        $("#example").empty();  
		   	    $("#example").append(result);
		    	 pagination()
		        },
		    });
} 
</script>
 
 <script>
function pagination(){  
    $('#example').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	 "ordering": false
		
    } );
}
</script> -->
 