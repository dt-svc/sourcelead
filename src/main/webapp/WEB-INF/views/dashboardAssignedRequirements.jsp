<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> 

<div class="content-dashboard clearfix"> 
      <!-- Assigned Requirements -->
      <div class="clearfix dashboard-widget mb col-md-12">
        <h4 class="pull-left">
        Assigned Requirements
        &nbsp;&nbsp;<span class="pull-right" title="Calendar"><i aria-hidden="true" class="fa fa-calendar" id="datepicker"></i>
        </span>
        </h4>
      <a href="#" class="collpase-div pull-right" title="SlideUp/SlideDown"><i aria-hidden="true" class="fa fa-angle-down"></i></a>
        <div class="clearfix require_wrap_table dashboard-data">
        		 <div id="requirementList"></div>
        </div>
      </div>
    </div>
    
    
 <%-- 
     @author : sarath kumar.
     this script is for get candidate list.
     1.usage     : onload.
     2.parameter : csrf token .
     3.ajax url implementation in requirementListRestController.
--%>

<script> 
$(document).ready(function(){
	requirementList();
});
</script>

<script>
function requirementList(){  
	
	  var sourceleadUrl ='${sourceleadUrl}';
	  $("#requirementList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getAssignedRequirementListByOrgLocationIDAndUserId?${_csrf.parameterName}=${_csrf.token}",    
            success :function(data) {
                 if(data != null && data != 'undefined' && data != "" && data != '[]'){
               		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
               		 else if (data.indexOf('exception') >= 0) {
               			 location.replace(sourceleadUrl+"/errorPage");
               		 }
               		 else{
			    	     $("#requirementList").removeAttr("style");
	                	 $("#requirementList").empty();
	                	 var result = "";
	                	 result = result +"<table id='requirementPagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>ID</th>"; 
	                     result = result +"    <th>Requirement</th>"; 
	                     result = result +"    <th>Client</th>"; 
	                     result = result +"    <th>Rate</th>"; 
	                     result = result +"    <th>Priority</th>"; 
	                     result = result +"    <th>Applicants</th>"; 
	                     result = result +"    <th>Shortlisted</th>"; 
	                     result = result +"    <th>Submited</th>"; 
	                     result = result +"    <th>Interviewed</th>"; 
	                     result = result +"    <th>Offered</th>"; 
	                     result = result +"    <th>Action</th>"; 
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     var json = JSON.parse(data);
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i]; 
	                    	 result = result +"<tr>";
		                     result = result +"<td><a href='<c:url value='/requirementSummary?requirementId="+opt.id+"'/>'><div class='proj_show'><span>"+opt.id+"</span><i class='fa fa-binoculars'></i></div></a></td>";
		                     result = result +"<td><div><span>"+opt.jobTitle+"</span></div></td>";
		                     if(opt.clientName != null && opt.clientName != '' && opt.clientName != 'undefined'){
		                    	 result = result +"<td><div>"+opt.clientName+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                    
		                     if(opt.rate != null && opt.rate != '' && opt.rate != 'undefined'){
			                     result = result +"<td><div><span><span>"+opt.rate+"</span></span></div></td>";
		                     }else{
			                     result = result +"<td><div><span><span>-----</span></span></div></td>";
		                     }
		                     if(opt.priority != null && opt.priority != '' && opt.priority != 'undefined'){
		                    	 result = result +"<td><div>"+opt.priority+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>----</div></td>";
		                     }
	                    	 result = result +"<td><div>"+opt.noOfApplicants+"</div></td>";

		                     result = result +"<td><div>"+opt.noOfShortlistedApplicants+"</div></td>";
			                 result = result +"<td><div>"+opt.noOfSubmittedApplicants+"</div></td>";
		                     result = result +"<td><div>"+opt.noOfInterviewedApplicants+"</div></td>";
		                     result = result +"<td><div>"+opt.noOfOfferedApplicants+"</div></td>";
		                     result = result +"<td><div>--</div></td>";
		                     result = result +"</tr>";
		                     result = result +"</tr>";
	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#requirementList").append(result); 
	                     pagination(); 
			    	} 
                 }else{
                	 $("#requirementList").removeAttr("style");
  	    	         var result ="";
		    	    
					 result = result+"			<div class='blank-pages'>";
					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"			        <p>No Requirement Found</p>";
					 result = result+"			 </div>";
					
					$("#requirementList").empty();
				    $("#requirementList").append(result); 
                }
          },
            
       }); 
  } 
</script>   

<script>
function pagination(){ 
    $('#requirementPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[1, 15, 20, -1], [1, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
            "sSearch": ""
       }
    } );
}
</script>