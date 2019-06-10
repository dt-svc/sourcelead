
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>
 
<link rel="icon" type="image/png" href="/resources/images/fav.png" />

<title>Welcome to Source Lead :: The Recruitment Application</title>
<!-- Bootstrap -->
<link href="/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/sourcelead-main.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/timesheet.css?staticTime=${staticTime}" rel="stylesheet">

<script src="/resources/js/jquery-1.11.1.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/bootstrap.min.js?staticTime=${staticTime}"></script> 
</head>

 <script src="/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
 <script src="/resources/js/jquery.dataTables.min.js?staticTime=${staticTime}"></script>
 <script src="/resources/js/slead-common.js"></script> 
<script>
		$(document).ready(function() { 
			getAllCampaignJobs();
		});
</script>

<script>
var sourceleadUrl = '${sourceleadUrl}';
</script>
<body style="background-color:#e1e6fa;">
<div class="wraper"> 
  <!-- Header Area -->
  <header class="main-header">
   <span class="logo">
   	 	<a href="javascript:void(0)" title="Source Lead"> <i> <img src="/resources/images/logo-sourceld.png" alt="Source Lead"> </i>
    <h1>Source Lead</h1>
    </a>
    </span>
    <nav class="header-nav navbar">
    </nav>
   </header>
  <!-- Header Area --> 

  <!-- Content Area -->
  <section class="container" style="padding-bottom: 55px;">
		  		<div id="loading"></div>
				          <div class="col-md-8  project_wrap_table publicpageTop project_wrap_table_publicpage_styles">
				          
							  <div class="clearfix">
							  <h4>Jobs List</h4>
							  </div>
							  <div id="campaigncandidatesList" class="datatable_filter positionRelative campaigncandidatesList_info"></div>
							  
							  
						 </div>
  </section>
  <!-- Footer Area -->
  <footer class="footer-publicpages clearfix">
  <div class="container" style="padding-bottom: 55px;">
    <div class="copyright"> @ Copyright : Source Lead, All right reserved </div>
    <div class="powered-by"> Powered by: businessintelli.com </div></div>
  </footer>
  <!-- Footer Area --> 
</div>
</body>
</html>




<script>
 function getAllCampaignJobs(){
	 var jobCampaignUUID = '${jobCampaignUUID}'; 
	 var sourceleadUrl = '${sourceleadUrl}';
	 
	 $.ajax({
         type : "GET",
          url: sourceleadUrl+"/getAllJobCampaignsList?jobCampaignUUID="+jobCampaignUUID,       
          success :function(json) {
        	  
        	  
        	  
        	    var result = "";
			   
        	   result = result +"<html>";
          	   result = result +"<table id='jobCampaignsPagination' width='100%' cellpadding='0' border='2' cellspacing='0'>"; 
               result = result +"<thead>"; 
               result = result +"<tr>"; 
               result = result +"    <th>Job Title</th>"; 
               result = result +"    <th>Location</th>"; 
               result = result +"    <th>Employment Type</th>"; 
               result = result +"    <th>Skills</th>";
               result = result +"  </tr>"; 
               result = result +"</thead>";
               result = result +"<tbody>"; 
               for (var i = 0; i < json.length; i++) { 
            	   var opt = json[i]; 
               	
              	 var jobType = '';
              	 var skills = '';
              	 var location = '';
              	 
              	 if(opt.location != null && opt.location != '' && opt.location != 'undefined'){
              		location = opt.location;
              	 }else{
              		location = "--------";
              	 }
              	 
              	 if(opt.jobType != null && opt.jobType != '' && opt.jobType != 'undefined'){
              		 jobType = opt.jobType;
              	 }else{
              		 jobType = "--------";
              	 }
              	 
              	 if(opt.skills != null && opt.skills != '' && opt.skills != 'undefined'){
              		 skills = opt.skills;
              	 }else{
              		 skills = "--------";
              	 }
              	 
              	 
              	 result = result +'<td><div><span><a class="anchorBlue" href="'+sourceleadUrl+'/jobCampaignSummary?requirementUUID='+opt.requirementUUID+'" target="_blank"><div class="tbldata-dotdot_140"><span class="pull-left" title="'+opt.jobTitle+'">'+opt.jobTitle+'</div></a></td>';
              	 result  = result + ' <td><div class="tblminwidth-175">'+location+'</div></td>';
              	 result  = result + ' <td><div><span>'+jobType+'</span></div></td>';
              	 result  = result + ' <td><div>'+skills+'</div></td>';
              	
              	 result  = result + '</tr>';
                } 
               result = result +" </tbody>";
               result = result +"</table>";
               result = result +"</html>";
               
               $("#campaigncandidatesList").empty();
               $("#campaigncandidatesList").append(result);
               pagination(); 
        },
          
     }); 
	 
 }
 </script>
 
 
 <script>
function pagination(){  
    $('#jobCampaignsPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>





<script type="text/javascript" src='/resources/js/slead-common.js'></script>
