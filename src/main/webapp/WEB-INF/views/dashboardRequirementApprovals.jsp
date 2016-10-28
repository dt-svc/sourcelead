<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
 <div class="col-md-6 mb">
        <div class="clients_wrap_table dashboard-widget">
          <h4 class="pull-left">Requirement Approvals &nbsp;&nbsp;<a href="#" class="pull-right" title="Calendar"><i aria-hidden="true" class="fa fa-calendar"></i></a></h4>
          <a href="javascript:void(0)" class="collpase-div pull-right" title="SlideUp/SlideDown"><i aria-hidden="true" class="fa fa-angle-down"></i></a>
          <div class="clearfix require_wrap_table dashboard-data">
          		 <div id="requirementApproverList"></div>
          </div>
        </div>
        <!-- Pagenation Ends --> 
      </div>
      <!-- Requirement Approvals Ends --> 

<%-- 
     @author : sarath kumar.
     this script is for get candidate list.
     1.usage     : onload.
     2.parameter : csrf token .
     3.ajax url implementation in requirementApproverListRestController.
--%>

<script> 
$(document).ready(function(){
	requirementApproverList();
});
</script>

<script>
function requirementApproverList(){  
	
	  var sourceleadUrl ='${sourceleadUrl}';
	  $("#requirementApproverList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getAssignedRequirementApproverListByOrgLocationIDAndUserId?${_csrf.parameterName}=${_csrf.token}",    
            success :function(data) {
                 if(data != null && data != 'undefined' && data != "" && data != '[]'){
               		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
               		 else if (data.indexOf('exception') >= 0) {
               			 location.replace(sourceleadUrl+"/errorPage");
               		 }
               		 else{
			    	     $("#requirementApproverList").removeAttr("style");
	                	 $("#requirementApproverList").empty();
	                	 var result = "";
	                	 result = result +"<table id='requirementApproverPagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>";  
	                     result = result +"    <th>ID</th>"; 
	                     result = result +"    <th>Requirement</th>"; 
	                     result = result +"    <th>Rate</th>"; 
	                     result = result +"    <th>Exp</th>"; 
	                     result = result +"    <th>Action</th>"; 
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     var json = JSON.parse(data);
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i]; 
	                    	 result = result +"<tr>";
	                    	 result = result +"<td><div><span>"+opt.id+"</span></div></td>";
		                     result = result +"<td><a href='<c:url value='/requirementSummary?requirementId="+opt.id+"'/>'><div class='proj_show'><span>"+opt.jobTitle+"</span><i class='fa fa-binoculars'></i></div></a></td>";
		                     if(opt.rate!=null && opt.rate !='' && opt.rate !='undefined'){
		                    	 result = result +"<td><div>"+opt.rate+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
	                    	  if(opt.minExp !=null && opt.minExp !='' && opt.minExp !='undefined'){
                                  
		                     result = result +"<td><div><b class='proj_process'>"+opt.minExp+"</b></div></td>";
                                  }else{
                                	  result = result +"<td><div>-----</div></td>";
                                	  }
                                  
		                     if(opt.requirementSubmissionStatus == 'APPROVED' ||  opt.requirementSubmissionStatus == 'OPEN'){
			                     result = result +"<td><div><a href='<c:url value='/releaseRequirementToClientOrVendors?requirementId="+opt.id+"'/>'><i class='fa fa-bullhorn'></i></a></div></td>";

		                     }else{
			                     result = result +"<td><div>--</div></td>";
 
		                     }
		                     
		                     result = result +"</tr>";
	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#requirementApproverList").append(result); 
	                     requirementApprovalPagination(); 
			    	} 
                 }else{
                	 $("#requirementApproverList").removeAttr("style");
  	    	         var result ="";
		    	    
					 result = result+"			<div class='blank-pages'>";
					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"			        <p>No Requirement Found</p>";
					 result = result+"			 </div>";
					
					$("#requirementApproverList").empty();
				    $("#requirementApproverList").append(result); 
                }
          },
            
       }); 
  } 
</script>   

<script>
function requirementApprovalPagination(){ 
    $('#requirementApproverPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
            "sSearch": ""
       }
    } );
}
</script>