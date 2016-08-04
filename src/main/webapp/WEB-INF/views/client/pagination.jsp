<link href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css" rel="stylesheet">
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<script src="resources/js/jquery-fun.js"></script> 


<script src="https://code.jquery.com/jquery-1.12.3.js"></script>
<!-- <script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> -->
<script src="resources/js/jquery.dataTables.min.js"></script> 

<!-- Content Area -->
<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Create Client</h2>
		</div>
		<div class="pull-right brud-crum"><a href="dashboard">Home</a>&raquo;<a href="clientDashboard"> Client List</a>
			&raquo; Client</div>
	</section>
	<div class="content-area clearfix">
		<!-- <section class="action-btns clearfix">
			<ul class="pull-right list-unstyled list-inline">
				<li><a href="#" class="faclose"><i aria-hidden="true"
						class="fa fa-close"></i></a></li>
				<li><a href="#" class="collpase-div"><i aria-hidden="true"
						class="fa fa-angle-down"></i></a></li>
			</ul>
		</section> -->

		<div class="clearfix form-area form-horizontal toggle-div remove-div">
		<div class="project_wrap_table">
				<table id="example" class="display" cellspacing="0" width="100%">
				</table>   
		</div>		 
		</div>

	</div>
</section>
<!-- Content Area Ends-->

<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->
 


<script>
function pagination(){   
    $('#example').DataTable( {
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false
		
    } );
}
</script>



 
<script>
	$(document).ready(function() {  
			var sourceleadUrl ='${sourceleadUrl}';
			
			var result; 
			result = result +" 	  <thead>                       ";
			result = result +"       <tr>                       ";
			result = result +"           <th>Name</th>          ";
			result = result +"           <th>Position</th>      ";
			result = result +"           <th>Office</th>        ";
			result = result +"           <th>Age</th>           ";
			result = result +"           <th>Start date</th>    ";
			result = result +"           <th>Salary</th>        ";
			result = result +"       </tr>                      ";
			result = result +"   </thead>                       ";
		/* 	result = result +"   <tfoot>                        ";
			result = result +"       <tr>                       ";
			result = result +"           <th>Name</th>          ";
			result = result +"           <th>Position</th>      ";
			result = result +"           <th>Office</th>        ";
			result = result +"           <th>Age</th>           ";
			result = result +"           <th>Start date</th>    ";
			result = result +"           <th>Salary</th>        ";
			result = result +"       </tr>                      ";
			result = result +"   </tfoot>                       "; */
			
		  	result = result +'<tbody>';
   	    	$.ajax({
				   	type: "POST",
				    url: sourceleadUrl+"/getList?${_csrf.parameterName}=${_csrf.token}", 	   
		   		    success: function(data) {
		   		    	var json = JSON.parse(data);
		   		    	for (var i=0; i<json.length; i++)
		   				{   
		   		    		var report = json[i];
		   		    		result = result +'<tr>';
		   		    		result = result +'<td data-title="Id"><div>'+report.id+'</div></td>';
		   		    		result = result +'<td data-title="Name"><div class="proj_show" onclick="projectSideBar('+report.id+',\''+report.projectName+'\');"><span>'+report.projectName+'</span><i class="fa fa-binoculars"></i></div></td>';
		   		    		result = result +'<td data-title="Manager">';
		   		    		result = result +'<div><span>';
		   		    		if(report.managerLevel1 !=null && report.managerLevel1 !='' && report.managerLevel1 !='undefined'){
		   		    			result = result +report.managerLevel1;
		   		    		}
		   		    		if(report.managerLevel2!=null && report.managerLevel2!='' && report.managerLevel2 !='undefined'){
		   		    			result = result +report.managerLevel2;
		   		    		} 
		   		    		result = result +'</span></div>';
		   		    		result = result +'</td>';
		   		    		result = result +'<td data-title="Date"><div>'+report.startDateStr+' ';
		   		    		if(report.endDateStr != null && report.endDateStr != '' && report.endDateStr != 'undefined'){
		   	   		    		result = result +'- '+report.endDateStr+' </div></td>';
		   		    		}
		   		    		if(report.client != null && report.client != '' && report.client != 'undefined'){
		   		    			result = result +'<td data-title="Client"><div><span>'+report.client+'</span></div></td>';  
		   		    		}else{
		   		    			result = result +'<td data-title="Client"><div>---</div></td>';  
		   		    		}
		   		    		result = result +'<td data-title="Status"><div>';
		   		    		if(report.projectStatus == 'STARTED'){
			    			  result = result +'<b>'+report.projectStatus+'</b>';
			    			}
		   		    		if(report.projectStatus == 'INPROGRESS'){
		  	    			  result = result +'<b class="proj_process">'+report.projectStatus+'</b>';
		  	    			}
		   		    		if(report.projectStatus == 'COMPLETED'){
		  	    			  result = result +'<b class="proj_active">'+report.projectStatus+'</b>';
		  	    			}
		   		    		if(report.projectStatus == 'ONHOLD'){
		  	    			  result = result +'<b class="proj_deactivate">'+report.projectStatus+'</b>';
		  	    			}
		   		    		
		   		    		result = result +'</div></td>';
		   		    		
		                    result = result +'</tr>';
		   				} 
		   				result = result +"</tbody>";
		   	            $("#example").empty();  
		   		    	$("#example").append(result); 
		   		     	pagination();	
		   		    }
				    
		    });  
   	    	
			    
			
			
	});
</script>

